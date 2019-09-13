package com.hfepay.scancode.api.entity.vo;

import java.io.Serializable;

/**
 * 退款
 * @author lemon
 */
public class MerchantRefundVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -186232921525348064L;
	private String merchantNo;
	private String orderNo;
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@Override
	public String toString() {
		return "MerchantRefundVo [merchantNo=" + merchantNo + ", orderNo=" + orderNo + "]";
	}
	
}
