package com.hfepay.scancode.channel.commons;

/**
 * 配置类型
 * @author panq
 *
 */
public enum ConfigType {
	/**
	 * 商户渠道类型
	 */
	USER_CHANNEL_TYPE("USER_CHANNEL_TYPE","商户渠道类型"),
	
	/**
	 * 商户应用类型
	 */
	USER_APP_TYPE("APP_TYPE","商户应用类型");
	
	private String type;
	
	ConfigType(String type,String name){
		this.type = type;
		this.name = name;
	}
	
	public String value(){
		return type;
	}
	
	private String name;
	public String getName(){
		return name;
	}
}
