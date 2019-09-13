package com.hfepay.scancode.channel.commons;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 归属地判断工具
 * @author bob zhang
 * @since 2014-09-17
 *
 */
public class CheckCityUtils { 
	
	private static Logger log = LoggerFactory.getLogger(CheckCityUtils.class);
	
	/**
	 * 根据手机号码判断手机归属地
	 * @param phoneNumber
	 * @return 区域地址
	 */
	public static String getAddressByPhone(String phoneNumber) {
		String url = String.format("http://www.ip138.com:8080/search.asp?action=mobile&mobile=%s", phoneNumber); 
		String address = null;
		try {
			Document doc = Jsoup.connect(url).get();
			Elements els = doc.getElementsByClass("tdc2");
			address = els.get(1).text().replace(" ", "-");
		} catch (IOException e) {
			address = "系统异常"+e.getMessage();
			log.error("获取手机号码归属地异常：",e);
		} 
		return address;
	}
	
	/**
	 * 通过IP获取地址(需要联网，调用淘宝的IP库)
	 * 
	 * @param ip
	 * @return
	 */
	public static String getIpInfo(String ip) {
		if (ip.equals("本地")) {
			ip = "127.0.0.1";
		}
		String info = "";
		try {
			URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=" + ip);
			HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();
			htpcon.setRequestMethod("GET");
			htpcon.setDoOutput(true);
			htpcon.setDoInput(true);
			htpcon.setUseCaches(false);

			InputStream in = htpcon.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			StringBuffer temp = new StringBuffer();
			String line = bufferedReader.readLine();
			while (line != null) {
				temp.append(line).append("\r\n");
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			JSONObject obj = (JSONObject) JSON.parse(temp.toString());
			if (obj.getIntValue("code") == 0) {
				JSONObject data = obj.getJSONObject("data");
				info += data.getString("country") + "-";
				info += data.getString("region") + "-";
				info += data.getString("city");
				//info += data.getString("isp");
			}
		} catch (Exception e) {
			log.error("获取IP归属地异常~",e);
		}
		return info;
	}
	
	public static void main(String[] args) throws IOException {  
		String phoneAddre = getAddressByPhone("15188817707").replace(" ", "-");
		String iPAddre = getIpInfo("65.121.5.26");
		System.out.println(phoneAddre);
		System.out.println(iPAddre);
	} 
} 