package com.hfepay.scancode.channel.commons;

import java.io.Serializable;

/**
 * 错误信息返回
 * @ClassName: BaseErrorMsg
 * @Description: TODO
 * @author: husain
 * @date: 2016年10月20日 上午10:32:34
 */
public class BaseErrorMsg implements Serializable{

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = -2092658675906354746L;
	private String errorCode="1";
	private String errorMsg="验证失败";
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public BaseErrorMsg(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public BaseErrorMsg(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}

	public BaseErrorMsg() {
		
	}
}
