package com.hfepay.common.web.security.xss.util;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

import com.hfepay.common.web.security.xss.annotation.XSSParameterAnnotation;

public class XSSUtils {
	/**
	 * 
	 * filterXSS:过滤xss非法输入. <br/>
	 * 
	 * @param value
	 * @param types
	 * @return
	 */
	public static String filterXSS(String value, List<String> types) {
		if (types.contains(XSSParameterAnnotation.HTML)) {
			// htmlEscape 转义html脚本
			value = HtmlUtils.htmlEscape(value);
		}
		if (types.contains(XSSParameterAnnotation.JS)) {
			// javaScriptEscape 转义js脚本
			value = stripXSS(value);
		}
		if (types.contains(XSSParameterAnnotation.SQL)) {
			// escapeSql 提供sql转移功能，防止sql注入攻击，例如典型的万能密码攻击' ' or 1=1 ' '
			value = StringEscapeUtils.escapeSql(value);
		}
		return value;
	}

	private static String stripXSS(String value) {
        try {
			if (value != null) {
			    // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
			    // avoid encoded attacks.
			    // value = ESAPI.encoder().canonicalize(value);

			    // Avoid null characters
			    value = value.replaceAll("", "");

			    // Avoid anything between script tags
			    Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			    value = scriptPattern.matcher(value).replaceAll("");

			    // Avoid anything in a src='...' type of expression
			    scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			    value = scriptPattern.matcher(value).replaceAll("");

			    scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			    value = scriptPattern.matcher(value).replaceAll("");

			    // Remove any lonesome </script> tag
			    scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			    value = scriptPattern.matcher(value).replaceAll("");

			    // Remove any lonesome <script ...> tag
			    scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			    value = scriptPattern.matcher(value).replaceAll("");

			    // Avoid eval(...) expressions
			    scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			    value = scriptPattern.matcher(value).replaceAll("");

			    // Avoid expression(...) expressions
			    scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			    value = scriptPattern.matcher(value).replaceAll("");

			    // Avoid javascript:... expressions
			    scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			    value = scriptPattern.matcher(value).replaceAll("");

			    // Avoid vbscript:... expressions
			    scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			    value = scriptPattern.matcher(value).replaceAll("");

			    // Avoid onload= expressions
			    scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			    value = scriptPattern.matcher(value).replaceAll("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return value;
    }

}
