package com.hfepay.scancode.params;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class OrderQueryParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3577684537608725296L;
	
	@NotBlank(message="收款人编号不能为空")
	private String cashierNo;//商户编号不能为空
	
	//@NotBlank(message="支付流水号不能为空")
	private String tradeNo;//支付流水号
	
	private String userOrderNo;//上送的流水号
	
	//private String transNo;//微信或者支付宝返回的流水号
	
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
//	public String getTransNo() {
//		return transNo;
//	}
//	public void setTransNo(String transNo) {
//		this.transNo = transNo;
//	}
	@Override
	public String toString() {
		return "OrderQueryParam [cashierNo=" + cashierNo + ", tradeNo=" + tradeNo + ", userOrderNo=" + userOrderNo
				+ "]";
	}
	
	
	

}
