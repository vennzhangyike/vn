/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-06-15 20:42:11")
public class MerchantPayCondition extends PagingCondition {

	private static final long serialVersionUID = 1L;
	
	private String merchantNo;//商户支付编号
	private String userOrderNo;//平台方订单号
	private String payCode;//支付通道编码
	private String orderAmt;//交易金额
	private String returnType;//返回类型 01:微信jsapi字符串 02:微信授权地址

	private String appId;//公众号APPID，returnType=01时必填
	private String openId;//用户OpendId，returnType=01时必填
	private String orderTitle;//订单标题
	private String orderDesc;//订单描述
	private String opertorId;//操作员编号
	private String storeId;//消费店面编号
	private String terminalId;//终端编号
	private String limitPay;//limitPay:0:所有银行卡1:不支持信用卡支付

	private String remark;//备注
	private String ip;//消费来源IP
	private String appNo;//应用ID
	private String notifyUrl;//通知地址:接收平台通知的 URL，需给绝对路径，255 字符内格式，确保平台能通过互联网访问该地址。该地址不能带参数
	private String returnUrl;//前台地址:交易完成后跳转的 URL，需给绝对路径
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
	public String getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getOrderTitle() {
		return orderTitle;
	}
	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public String getOpertorId() {
		return opertorId;
	}
	public void setOpertorId(String opertorId) {
		this.opertorId = opertorId;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getLimitPay() {
		return limitPay;
	}
	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
}
