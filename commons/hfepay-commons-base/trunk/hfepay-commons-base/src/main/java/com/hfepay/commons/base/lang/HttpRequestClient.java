package com.hfepay.commons.base.lang;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.hfepay.commons.base.io.Streams;

import net.sf.json.JSONObject;

public class HttpRequestClient {

	public static String invoke_https(String url,List<NameValuePair> parameters,String charSet) throws Exception{
		HttpClient client = WebClientDevWrapper.wrapClient(new DefaultHttpClient());
		HttpPost httpPost = new HttpPost(url);
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
				parameters, charSet);
		formEntity.setContentEncoding(charSet);
		formEntity.getContentEncoding();
		httpPost.setEntity(formEntity);
		HttpResponse httpResponse = client.execute(httpPost);
		InputStream contentInputStream = httpResponse.getEntity().getContent();
		String data = new String(Streams.readAll(contentInputStream));
		return data;
	} 
	
	public static String invoke_http(String url,List<NameValuePair> parameters,String charSet) throws Exception{
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
				parameters, charSet);
		formEntity.setContentEncoding(charSet);
		formEntity.getContentEncoding();
		httpPost.setEntity(formEntity);
		HttpResponse httpResponse = client.execute(httpPost);
		InputStream contentInputStream = httpResponse.getEntity().getContent();
		String data = new String(Streams.readAll(contentInputStream));
		return data;
	} 
	
	/**
	 * json作为参数格式
	 * @param url
	 * @param params
	 * @param charSet
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static String invoke_http_json(String url,JSONObject params,String charSet,Map<String,Object> headers) throws Exception{
		//SSLProtocolHttpClientFactory factory = new SSLProtocolHttpClientFactory();
		//HttpClient client = factory.createHttpClient(new DefaultHttpClient());
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		//添加请求头
		if(null !=headers && headers.size()>0){
			Set<Entry<String, Object>> set = headers.entrySet();
			for (Entry<String, Object> entry : set) {
				Object value = entry.getValue();
				if(null != value && Strings.isNotEmpty(value.toString())){
					httpPost.setHeader(entry.getKey(), value.toString());
				}
			}
		}
		//请求报文体
		StringEntity s = new StringEntity(params.toString());
		s.setContentEncoding(charSet);
		s.setContentType("application/json");
		httpPost.setEntity(s);
		HttpResponse httpResponse = client.execute(httpPost);
		HttpEntity entity = httpResponse.getEntity();
		String response_charset = EntityUtils.getContentCharSet(entity);
		InputStream in = entity.getContent();
		String data = new String(Streams.readAll(in),response_charset);
		return data;
	} 
	
	/**
	 * json作为参数格式
	 * @param url
	 * @param params
	 * @param charSet
	 * @param headers
	 * @return
	 * @throws Exception
	 */
	public static String invoke_https_json(String url,JSONObject params,String charSet,Map<String,Object> headers) throws Exception{
		SSLProtocolHttpClientFactory factory = new SSLProtocolHttpClientFactory();
		HttpClient client = factory.createHttpClient(new DefaultHttpClient());
		//HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		//添加请求头
		if(null !=headers && headers.size()>0){
			Set<Entry<String, Object>> set = headers.entrySet();
			for (Entry<String, Object> entry : set) {
				Object value = entry.getValue();
				if(null != value && Strings.isNotEmpty(value.toString())){
					httpPost.setHeader(entry.getKey(), value.toString());
				}
			}
		}
		//请求报文体
		StringEntity s = new StringEntity(params.toString());
		s.setContentEncoding(charSet);
		s.setContentType("application/json");
		httpPost.setEntity(s);
		HttpResponse httpResponse = client.execute(httpPost);
		HttpEntity entity = httpResponse.getEntity();
		String response_charset = EntityUtils.getContentCharSet(entity);
		InputStream in = entity.getContent();
		String data = new String(Streams.readAll(in),response_charset);
		return data;
	} 
	
	/**
	 httpClient的get请求方式2
	 * @return
	 * @throws Exception
	 */
	public static byte[] doGet(String url)
			throws Exception {
		/*
		 * 使用 GetMethod 来访问一个 URL 对应的网页,实现步骤: 
		 * 1:生成一个 HttpClinet 对象并设置相应的参数。
		 * 2:生成一个 GetMethod 对象并设置响应的参数。
		 * 3:用 HttpClinet 生成的对象来执行 GetMethod 生成的Get方法。 
		 * 4:处理响应状态码。
		 * 5:若响应正常，处理 HTTP 响应内容。
		 * 6:释放连接。
		 */
		/* 1 生成 HttpClinet 对象并设置参数 */
		org.apache.commons.httpclient.HttpClient httpClient = new org.apache.commons.httpclient.HttpClient();
		// 设置 Http 连接超时为5秒
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		/* 2 生成 GetMethod 对象并设置参数 */
		GetMethod getMethod = new GetMethod(url);
		// 设置 get 请求超时为 5 秒
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,	new DefaultHttpMethodRetryHandler());
		byte[] responseBody = null;
		/* 3 执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			/* 4 判断访问的状态码 */
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("请求出错: "+ getMethod.getStatusLine());
			}
			/* 5 处理 HTTP 响应内容 */
			// HTTP响应头部信息，这里简单打印
			Header[] headers = getMethod.getResponseHeaders();
			for (Header h : headers)
				System.out.println(h.getName() + "------------ " + h.getValue());
			// 读取 HTTP 响应内容，这里简单打印网页内容
			responseBody = getMethod.getResponseBody();// 读取为字节数组
			//PhotoUtil.writeFile(responseBody, "f:/", "3.jpeg");
			//response = new String(responseBody, charset);
			//System.out.println("----------response:" + response);
			// 读取为 InputStream，在网页内容数据量大时候推荐使用
			// InputStream response = getMethod.getResponseBodyAsStream();
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问题
			System.out.println("请检查输入的URL!");
			e.printStackTrace();
		} catch (IOException e) {
			// 发生网络异常
			System.out.println("发生网络异常!");
			e.printStackTrace();
		} finally {
			/* 6 .释放连接 */
			getMethod.releaseConnection();
		}
		return responseBody;
	}
	
	public static void main(String[] args) {
		try {
			JSONObject json = new JSONObject();
			json.put("name", "ss");
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("header", "ssss");
			String data = HttpRequestClient.invoke_http_json("https://210.74.42.39:443/dataservice/apply-for-key.json", json, "utf-8", headers);
			System.out.println("test:"+data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
