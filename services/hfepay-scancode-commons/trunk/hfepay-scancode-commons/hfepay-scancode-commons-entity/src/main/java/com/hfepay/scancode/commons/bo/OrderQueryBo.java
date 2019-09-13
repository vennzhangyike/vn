package com.hfepay.scancode.commons.bo;

import java.io.Serializable;

public class OrderQueryBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3577684537608725296L;
	
	private String cashierNo;
	private String tradeNo;
	private String userOrderNo;
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
		return "OrderQueryBo [cashierNo=" + cashierNo + ", tradeNo=" + tradeNo + ", userOrderNo=" + userOrderNo + "]";
	}
	

}
