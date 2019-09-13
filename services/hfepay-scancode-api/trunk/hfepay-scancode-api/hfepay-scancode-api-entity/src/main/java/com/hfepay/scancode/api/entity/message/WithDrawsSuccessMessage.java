package com.hfepay.scancode.api.entity.message;

import java.util.Date;

/**
 * 支付成功推送消息
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class WithDrawsSuccessMessage extends PushMessage{
	private String merchantName;
	private String orderAmt;
	private String payCode;
	private Date beginTime;//交易开始时间
	private String remark;
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
