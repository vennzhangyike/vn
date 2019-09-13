package com.hfepay.scancode.api.entity.message;

import java.util.Date;

/**
 * 支付成功推送消息
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class PaySuccessMessage extends PushMessage{

	private String orderName;//商品名称
	
	private String orderNo;//订单编号
	
	private String orderAmt;//支付金额
	
	private Date beginTime;//交易开始时间
	
	private String payCode;//平台支付

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	
	
}
