package com.hfepay.cache.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 * @author cc
 *
 */
public class PropertiesUtils {
	private static PropertiesUtils propertiesUtils;
	
	private static Properties pro = null;
	
	public static PropertiesUtils getInstance(String fileName){
		if (propertiesUtils == null) {
			synchronized(PropertiesUtils.class){				
				propertiesUtils = new PropertiesUtils(fileName);  
			}
	    }  
	    return propertiesUtils;  
	}
	
	private PropertiesUtils(String fileName){
		try {
			if (pro == null) {				
				InputStream in = PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName);
				pro = new Properties();
				pro.load(in);
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据key从配置文件获取对应值
	 * @param key
	 * @return value
	 */
	public String getValue(String key){
		return pro.getProperty(key);
	}
	
	public static void main(String[] args) {
		PropertiesUtils pro =PropertiesUtils.getInstance("application-hfepay.properties");
		System.out.println("pro:"+pro.getValue("hfepay.jedis.pool.ip"));
	}
}
