package com.hfepay.common.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.base.lang.Sys;
import com.hfepay.commons.base.lang.util.Base64;

/**
 * 提供一系列对request,response对象操作的工具方法
 * 
 * @author Sam
 * 
 */
public class Servlets {
	// -- Content Type 定义 --//
	public static final String TEXT_TYPE = "text/plain";
	public static final String JSON_TYPE = "application/json";
	public static final String XML_TYPE = "text/xml";
	public static final String HTML_TYPE = "text/html";
	public static final String JS_TYPE = "text/javascript";
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";
	public static final String DOWNLOAD_TYPE = "application/x-download;charset=UTF-8";
	
	//-- header 常量定义 --//
	public static final String HEADER_ENCODING = "encoding";
	public static final String HEADER_NOCACHE = "no-cache";
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final boolean DEFAULT_NOCACHE = true;
	
	/**
     * Part of HTTP content type header.
     */
    public static final String MULTIPART = "multipart/";

	// -- Header 定义 --//
	public static final String AUTHENTICATION_HEADER = "Authorization";

	// -- 常用数值定义 --//
	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/**
	 * 设置客户端缓存过期时间 Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response,
			long expiresSeconds) {
		// Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis()
				+ expiresSeconds * 1000);
		// Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age="
				+ expiresSeconds);
	}

	/**
	 * 设置客户端无缓存Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 0);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache");
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response,
			long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified
	 *            内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request,
			HttpServletResponse response, long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag
	 *            内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request,
			HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(
						headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletResponse response,
			String fileName) {
		try {
			// 中文文件名支持
			String encodedfileName = new String(fileName.getBytes(),
					"ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
		}
	}

	/**
	 * 取得带相同前缀的Request Parameters.
	 * 
	 * 返回的结果Parameter名已去除前缀.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getParametersStartingWith(HttpServletRequest request,
			String prefix) {
		Enumeration paramNames = request.getParameterNames();
		Map params = new TreeMap();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {// NOSONAR
					// Do nothing, no values found at all.
				} else if (values.length > 1) {
					params.put(unprefixed, values);
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	/**
	 * 根据HTTP请求头部信息判断是什么请求类型:FormRequest,JsonRequest....
	 * 
	 * @param request
	 *            http客户端请求信息
	 * @return
	 */
	public static RequestType getRequestType(HttpServletRequest request) {

		String contentType = request.getHeader("content-type");
		String accept = Strings.defaultString(request.getHeader("Accept"));
		String headerXRequestWith = Strings.defaultString(request.getHeader("X-Requested-With") );
		if (Strings.isNotEmpty(accept) && Strings.isEmpty(contentType)) {
			if (accept.indexOf(",") != -1) {
				contentType = accept.substring(0, accept.indexOf(","));
			}
		}

		if (Strings.startsWithIgnoreCase(contentType, JSON_TYPE)
				|| headerXRequestWith.equalsIgnoreCase("XMLHttpRequest")
				|| accept.contains("application/json")) {
			return RequestType.JSON;
		} else if (Strings.startsWithIgnoreCase(contentType, "application/x-www-form-urlencoded")) {
			return RequestType.FORM_SIMPLE;
		} else if (isMultipartContent(request)) {
			return RequestType.FORM_UPLOAD;
		} else {
			return RequestType.GENERAL;
		}

	}
	
	public static final boolean isMultipartContent(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType == null) {
            return false;
        }
        if (contentType.toLowerCase(Locale.ENGLISH).startsWith(MULTIPART)) {
            return true;
        }
        return false;
    }
	
	/**
	 * 读取请求体中的信息
	 * 
	 * @param request
	 * @return
	 */
	public static String readRequestBody(HttpServletRequest request) {

		StringBuilder json = new StringBuilder();
		String line = null;
		try {			
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}
		} catch (Exception e) {
			Sys.println(" Error reading JSON string: " + e.toString());
		}
		Sys.println(json);
		return json.toString();
	}

	/**
	 * 对Http Basic验证的 Header进行编码.
	 */
	public static String encodeHttpBasic(String userName, String password) {
		String encode = userName + ":" + password;
		return "Basic " + Base64.encodeToString(encode.getBytes(),true); 
	}

	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain",
	 * "hello", "no-cache:false"); render("text/plain", "hello", "encoding:GBK",
	 * "no-cache:false");
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(HttpServletResponse res,final String contentType, final String content,final String... headers) {
		HttpServletResponse response = initResponseHeader(res, contentType, headers);
		try {
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 直接输出文本.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderText(HttpServletResponse res,final String text, final String... headers) {
		render(res,TEXT_TYPE, text, headers);
	}

	/**
	 * 直接输出HTML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderHtml(HttpServletResponse res,final String html, final String... headers) {
		render(res,HTML_TYPE, html, headers);
	}

	/**
	 * 直接输出XML.
	 * 
	 * @see #render(String, String, String...)
	 */
	public static void renderXml(HttpServletResponse res,final String xml, final String... headers) {
		render(res,XML_TYPE, xml, headers);
	}

	/**
	 * 直接输出JSON.
	 * 
	 * @param jsonString
	 *            json字符串.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(HttpServletResponse res,final String jsonString,final String... headers) {
		render(res,JSON_TYPE, jsonString, headers);
	}

	/**
	 * 直接输出JSON,使用Jackson转换Java对象.
	 * 
	 * @param data
	 *            可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
	 * @see #render(String, String, String...)
	 */
	public static void renderJson(HttpServletResponse res,final Object data, final String... headers) {
		HttpServletResponse response = initResponseHeader(res, JSON_TYPE, headers);
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * 直接输出支持跨域Mashup的JSONP.
	 * 
	 * @param callbackName
	 *            callback函数名.
	 * @param object
	 *            Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
	 */
	public static void renderJsonp(HttpServletResponse res,final String callbackName, final Object object, final String... headers) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

		String result = new StringBuilder().append(callbackName).append("(")
				.append(jsonString).append(");").toString();

		// 渲染Content-Type为javascript的返回内容,输出结果为javascript语句,
		// 如callback197("{html:'Hello World!!!'}");
		render(res,JS_TYPE, result, headers);
	}
	
	/**
	 * 分析并设置contentType与headers.
	 */
	static HttpServletResponse initResponseHeader(HttpServletResponse response, final String contentType, final String... headers) {
		// 分析headers参数
		String encoding = DEFAULT_ENCODING;
		boolean noCache = DEFAULT_NOCACHE;
		for (String header : headers) {
			String headerName = Strings.substringBefore(header, ":");
			String headerValue = Strings.substringAfter(header, ":");

			if (Strings.equalsIgnoreCase(headerName,HEADER_ENCODING)) {
				encoding = headerValue;
			} else if (Strings.equalsIgnoreCase(headerName,HEADER_NOCACHE)) {
				noCache = Boolean.parseBoolean(headerValue);
			} else {
				throw new IllegalArgumentException(headerName + " is not valid  headerType!");
			}
		}

		// 设置headers参数
		String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			setNoCacheHeader(response);
		}

		return response;
	}
	
	/**
	 * 基于jackson的json工具
	 */
	private static ObjectMapper mapper = new ObjectMapper();
}
