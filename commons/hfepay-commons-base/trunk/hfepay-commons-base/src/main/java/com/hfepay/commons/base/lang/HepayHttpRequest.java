package com.hfepay.commons.base.lang;

import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.hfepay.commons.base.io.Streams;

public class HepayHttpRequest {

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
}
