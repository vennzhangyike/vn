package com.hfepay.scancode.api.webank.httputil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.List;

import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.hfepay.scancode.api.webank.commons.ConfigConstants;

public class HttpClientUtils {

	private ConfigConstants configConstants;
	
	public HttpClientUtils() {
		super();
	}
	
	public HttpClientUtils(ConfigConstants configConstants) {
		super();
		this.configConstants = configConstants;
	}

	public String doGet(String url, List<NameValuePair> nameValuePairs){
		HttpGet httpGet = new HttpGet(url);
		URIBuilder builder = null;
		HttpResponse response = null;
		try {
			builder = new URIBuilder(url);
			// 填入查询参数
			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
				builder.setParameters(nameValuePairs);
			}
			httpGet.setURI(builder.build());
			System.out.println("发送地址：" + httpGet.getURI());
			httpGet.setHeader("ssl-s-dn", "CN=107100000000003");
			CloseableHttpClient httpclient = httpBuild();
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String respContent = EntityUtils.toString(entity, "UTF-8").trim();
			System.out.println("服务器返回:"+respContent);
			return respContent;
		} catch (URISyntaxException e2) {
			e2.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			System.out.println("got a exception" + e.getMessage());
		} 
		return null;
	}
	
	public String doPost(String url, String json,List<NameValuePair> nameValuePairs){
		HttpPost httpPost = new HttpPost(url);
		StringEntity reqentity = null;
		URIBuilder builder = null;
		HttpResponse response = null;
		try {
			builder = new URIBuilder(url);
			// 填入查询参数
			if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
				builder.setParameters(nameValuePairs);
			}
			httpPost.setURI(builder.build());
			System.out.println("发送地址：" + httpPost.getURI());
			reqentity = new StringEntity(json.toString(),"UTF-8");
			System.out.println("报文体： "+json.toString());
			reqentity.setContentType("application/xml;charset=UTF-8");
			httpPost.setEntity(reqentity);
			httpPost.setHeader("ssl-s-dn", "CN=107100000000003");
			CloseableHttpClient httpclient = httpBuild();
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String respContent = EntityUtils.toString(entity, "UTF-8").trim();
			System.out.println("服务器返回:"+respContent);
			return respContent;
		} catch (URISyntaxException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			System.out.println("got a exception" + e.getMessage());
		} 
		return null;
	}
	
	public CloseableHttpClient httpBuild(){
		KeyStore keyStore = null;
		KeyStore trustStore = null;
		InputStream ksIn = null;
		InputStream tsIn = null;
		SSLContext sslcontext = null;
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			trustStore = KeyStore.getInstance("JKS");
			ksIn = new FileInputStream(configConstants.getClient_cet());
			tsIn = new FileInputStream(configConstants.getTrust_cet());
			keyStore.load(ksIn, configConstants.getKey_store_password().toCharArray());
			trustStore.load(tsIn, configConstants.getKey_store_trust_password().toCharArray());
			sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, configConstants.getKey_store_password().toCharArray())
					.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
			CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			return httpclient;
		}catch (KeyStoreException e1) {
			e1.printStackTrace();
		}catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("got a exception" + e.getMessage());
		} finally {
			try {
				ksIn.close();
				tsIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
