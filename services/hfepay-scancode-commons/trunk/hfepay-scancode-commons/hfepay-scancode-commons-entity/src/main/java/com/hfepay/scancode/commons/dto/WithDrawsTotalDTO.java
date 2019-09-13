package com.hfepay.scancode.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithDrawsTotalDTO implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 9015088719872039228L;
	private BigDecimal totalAmt;//提现总金额汇总
	private String payCode;//支付方式

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	
}
