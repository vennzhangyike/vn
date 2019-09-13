package com.hfepay.scancode.bean;

import java.io.Serializable;


/**
 * 返回报文格式
 */
public class ResponseBody implements Serializable {

	private static final long serialVersionUID = 3056731481624824988L;
	
	private String returnCode;
	private String returnMsg;
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
	
}
