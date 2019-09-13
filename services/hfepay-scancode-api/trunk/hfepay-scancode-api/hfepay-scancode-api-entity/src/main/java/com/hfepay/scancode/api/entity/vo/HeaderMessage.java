package com.hfepay.scancode.api.entity.vo;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 请求报文头公共类
 * Created by Kevin on 2016/9/12 0012.
 */
public class HeaderMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8094516946457873664L;
	
	/**
	 * 渠道编号
	 */
	@NotBlank(message="渠道编号(channelNo)不能为空")
	@Length(max=32, message="渠道编号不超过32个字符")
	private String channelNo;
	
	/**
	 * 接口名称
	 */
	@NotBlank(message="接口名称(method)不能为空")
	@Length(max=32, message="接口名称不超过32个字符")
	private String method;
	
	/**
	 * 版本号
	 */
	@NotBlank(message="版本号(version)不能为空")
	@Length(max=10, message="版本号不超过6个字符")
	private String version;
	
	/**
	 * 请求流水号
	 */
	@NotBlank(message="请求流水号(userReqNo)不能为空")
	@Length(max=32, message="请求流水号不超过32个字符")
	private String userReqNo;
	
	/**
	 * 请求时间
	 */
	@NotBlank(message="请求时间(reqTime)不能为空")
	@Length(max=14, message="请求时间不超过14个字符")
	private String reqTime;
	
	/**
	 * 签名
	 */
	@NotBlank(message="签名(sign)不能为空")
	private String sign;
	
	/**
	 * 加密密钥
	 */
	@NotBlank(message="RSA加密后的AES key不能为空")
	private String encryptKey;
	
	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	

	public String getUserReqNo() {
		return userReqNo;
	}

	public void setUserReqNo(String userReqNo) {
		this.userReqNo = userReqNo;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}
	
}
