package com.hfepay.scancode.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPayTotalDTO implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 9015088719872039228L;
	private BigDecimal balance;//交易总金额汇总
	private String payCode;//支付方式
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	
}
