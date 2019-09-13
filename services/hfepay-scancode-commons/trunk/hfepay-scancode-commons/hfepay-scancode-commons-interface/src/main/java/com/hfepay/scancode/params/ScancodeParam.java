package com.hfepay.scancode.params;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class ScancodeParam extends ParentPayParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7776388479389476645L;
	
	@NotBlank(message="支付方式不能为空")
	@Pattern(regexp="zfb|wechat|qq",message="不支持的支付方式")
	private String payCode;

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	
}
