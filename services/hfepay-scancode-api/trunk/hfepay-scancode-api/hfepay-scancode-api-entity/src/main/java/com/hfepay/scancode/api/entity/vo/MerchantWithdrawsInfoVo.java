package com.hfepay.scancode.api.entity.vo;

import java.io.Serializable;

/**
 * 预订单VO(API)
 * @author lemon
 */
public class MerchantWithdrawsInfoVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -282813963125907760L;
	
	private String merchantNo;
	private String userOrderNo;
	private String payCode;
	private String remark;
	private String returnURL;
	
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getUserOrderNo() {
		return userOrderNo;
	}
	public void setUserOrderNo(String userOrderNo) {
		this.userOrderNo = userOrderNo;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReturnURL() {
		return returnURL;
	}
	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}
}
