package com.hfepay.scancode.commons.bo;

import java.io.Serializable;

public class OrderRefundBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String merchantNo;
	private String tradeNo;
	private String cashierNo;
	private String userOrderNo;//上送的请求流水号
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getCashierNo() {
		return cashierNo;
	}
	public void setCashierNo(String cashierNo) {
		this.cashierNo = cashierNo;
	}
	public String getUserOrderNo() {
		return userOrderNo;
	}
	public void setUserOrderNo(String userOrderNo) {
		this.userOrderNo = userOrderNo;
	}
	@Override
	public String toString() {
		return "OrderRefundBo [merchantNo=" + merchantNo + ", tradeNo=" + tradeNo + ", cashierNo=" + cashierNo
				+ ", userOrderNo=" + userOrderNo + "]";
	}
	
}
