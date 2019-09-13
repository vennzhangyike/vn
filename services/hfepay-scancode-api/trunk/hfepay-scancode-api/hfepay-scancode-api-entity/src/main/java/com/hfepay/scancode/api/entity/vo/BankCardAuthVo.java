package com.hfepay.scancode.api.entity.vo;

import java.io.Serializable;

/**
 * @Deprecated 银行卡四要素VO
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class BankCardAuthVo implements Serializable{

	private String realName;//持卡人姓名
	
	private String idCard;//身份证号
	
	private String bankCard;//银行卡号
	
	private String mobile;//银行预留手机号

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	
}
