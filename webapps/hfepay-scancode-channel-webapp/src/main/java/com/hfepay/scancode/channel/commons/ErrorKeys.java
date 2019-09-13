package com.hfepay.scancode.channel.commons;

public interface ErrorKeys {
	/**
	 * 登陆名不存在
	 */
	String INVALID_LOGIN_ACCOUNT = "11001001";
	/**
	 * 登陆密码不正确
	 */
	String INVALID_PASSWORD = "11001002";
	/**
	 * 无效的授权票据
	 */
	String INVALID_TOKEN = "11001003";
	/**
	 * 票据绑定的用户不存在，请退出，重新登录
	 */
	String INVALID_TOKEN_USER = "11001004";
	/**
	 * 帐号被锁定
	 */
	String LOCK_USER = "11001005";
	/**
	 * 帐号失效
	 */
	String INVALID_USER = "11001006";
	/**
	 * 帐号过期
	 */
	String EXPIRE_USER = "11001007";
}
