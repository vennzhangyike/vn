package com.hfepay.scancode.bean;

import java.io.Serializable;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * @author lemon
 * @Date 2016年9月20日 下午3:34:03 
 */
public class ParamsMessage implements Serializable{

	private static final long serialVersionUID = 1460555946890382576L;
	
	/**
	 * Header
	 */
	@Valid
	private HeaderMessage head;
	
	/**
	 * body加密字符串
	 */
	@NotBlank
	private String body;
	
	public HeaderMessage getHead() {
		return head;
	}

	public void setHead(HeaderMessage head) {
		this.head = head;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
}
