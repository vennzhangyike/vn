package com.hfepay.scancode.ueditor;

import java.util.HashMap;
import java.util.Map;

import com.hfepay.commons.utils.Springs;
import com.hfepay.scancode.commons.FileUploadConfig;

/**
 * TODO
 *
 * @author zhangbo
 * @date CreateDate : 2016年6月13日 
 */
public class UploadContants {
	
//	public final static String FILE_NAME = "application-hfepay.properties";
	static FileUploadConfig fileUploadConfig = Springs.getBean("fileUploadConfig");
	
	public final static String LOCAL_ROOT_PATH = "hfepay.fileupload.root";

	public static Map<String,String> uploadConf = new HashMap<String,String>();
	
	public static String getConfValueByKey(String key){
		String value = uploadConf.get(key);
		if(value==null){
			init();
			value = uploadConf.get(key);
		}
		return value;
	}
	
	public static void init(){
		uploadConf.put(LOCAL_ROOT_PATH, fileUploadConfig.getPath());
	}
}
