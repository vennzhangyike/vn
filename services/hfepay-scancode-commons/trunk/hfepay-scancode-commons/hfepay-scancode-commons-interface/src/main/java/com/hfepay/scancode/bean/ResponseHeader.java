package com.hfepay.scancode.bean;

import java.io.Serializable;

/**
 * Created by Kevin on 2016/9/13 0013.
 */
public class ResponseHeader implements Serializable{

	private static final long serialVersionUID = -7897591862442472461L;

	/**
	 * 版本号
	 */
	private String version = "1.0.0";
	
//	/**
//	 * 请求流水号
//	 */
//	private String userReqNo;
//	
//	/**
//	 * 请求时间
//	 */
//	private String reqTime;
//	
//	/**
//	 * 响应时间
//	 */
//	private String respTime;
//	
//	/**
//	 * 业务服务流水号
//	 */
//	private String respNo;
	
	/**
	 * 业务返回码
	 */
	private String respCode;
	
	/**
	 * 业务处理信息
	 */
	private String respDesc;
	
	/**
	 * 签名
	 */
	private String sign;
	
	/**
	 * 加密密钥
	 */
	private String encryptKey;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

//	public String getUserReqNo() {
//		return userReqNo;
//	}
//
//	public void setUserReqNo(String userReqNo) {
//		this.userReqNo = userReqNo;
//	}
//
//	public String getReqTime() {
//		return reqTime;
//	}
//
//	public void setReqTime(String reqTime) {
//		this.reqTime = reqTime;
//	}
//
//	public String getRespTime() {
//		return respTime;
//	}
//
//	public void setRespTime(String respTime) {
//		this.respTime = respTime;
//	}
//
//
//	public String getRespNo() {
//		return respNo;
//	}
//
//	public void setRespNo(String respNo) {
//		this.respNo = respNo;
//	}
//
	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespDesc() {
		return respDesc;
	}

	public void setRespDesc(String respDesc) {
		this.respDesc = respDesc;
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
