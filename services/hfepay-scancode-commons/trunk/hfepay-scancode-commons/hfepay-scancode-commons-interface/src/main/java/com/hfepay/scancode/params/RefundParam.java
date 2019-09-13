package com.hfepay.scancode.params;

import org.hibernate.validator.constraints.NotBlank;

public class RefundParam{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3272711893306195425L;
	
	@NotBlank(message="商户编号不能为空")
	private String cashierNo;//收款人编号不能为空
	
//	@NotBlank(message="支付流水号不能为空")
	private String tradeNo;//交易流水号
	
	private String userOrderNo;//上送的请求流水号

	public String getCashierNo() {
		return cashierNo;
	}

	public void setCashierNo(String cashierNo) {
		this.cashierNo = cashierNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getUserOrderNo() {
		return userOrderNo;
	}

	public void setUserOrderNo(String userOrderNo) {
		this.userOrderNo = userOrderNo;
	}

	@Override
	public String toString() {
		return "RefundParam [cashierNo=" + cashierNo + ", tradeNo=" + tradeNo + ", userOrderNo=" + userOrderNo + "]";
	}
	

}
