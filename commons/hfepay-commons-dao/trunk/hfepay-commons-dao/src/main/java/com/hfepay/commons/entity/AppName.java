package com.hfepay.commons.entity;
 

import com.hfepay.commons.base.lang.Assert;
import com.hfepay.commons.base.lang.Throwables;

/**
 * 用户抽象各个应用的枚举信息 
 * @author Sam
 *
 */
public enum AppName {
	/**
	 * 支付导航管理平台
	 */
	ZFDH("hfepay manager","10"),
	
	/**
	 * 支付导航商户平台
	 */
	MECT("merchant platform","20"),
	
	/**
	 * 支付导航开放平台
	 */
	OPEN("open epay platform","30"),
	
	/**
	 * 移动版本管理平台
	 */
	MBIL("mobile manager platform","40"),
	
	/**
	 * 小二买单管理平台
	 */
	XEMD("xiao er manager","50"),
	
	/**
	 * 公共平台
	 */
	COMM("commons manager","60"),
	
	/**
	 * 用户管理平台
	 */
	USER("user manager","70"),
	
	/**
	 * Mobile app
	 */
	MAPP("mobile app","80");
	
	private String appcode;
	private String appdesc;
	
	AppName(String desc,String appcode) {
		this.appdesc = desc;
		this.appcode = appcode;
	}
	
	public String toString() {
		return this.appdesc;
	}
	
	public String getCode() {
		return appcode;
	}
	
	public static AppName byCode(String str) {
		Assert.notNull(str); 
		str = str.trim();
		if (str.equals("10"))
			return AppName.ZFDH;
		else if (str.equals("20"))
			return AppName.MECT;
		else if (str.equals("30"))
			return AppName.OPEN;
		else if (str.equals("40"))
			return AppName.MBIL;
		else if (str.equals("50"))
			return AppName.XEMD;
		else if (str.equals("60"))
			return AppName.COMM;
		else if (str.equals("70"))
			return AppName.USER;
		else if (str.equals("80"))
			return AppName.MAPP;
		else 
			throw Throwables.makeThrow("Can't Found Code Of Mapping AppName");
	}
}
