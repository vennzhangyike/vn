package com.hfepay.scancode.service.contants;

/**
 * 配置类型
 * @author panq
 *
 */
public enum ConfigType {
	USER_CHANNEL_TYPE("USER_CHANNEL_TYPE","商户渠道类型"),
	SYS_CONFIG_FLAG("SYS_CONFIG_FLAG","系统开关"),
	SKIN_CONFIG_THEME("SKIN_CONFIG_THEME","系统皮肤配置"),
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
