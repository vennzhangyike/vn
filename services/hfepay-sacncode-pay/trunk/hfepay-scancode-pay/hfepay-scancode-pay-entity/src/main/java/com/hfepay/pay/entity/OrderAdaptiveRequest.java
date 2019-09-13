package com.hfepay.pay.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付订单
 * @author Administrator
 *
 */
public class OrderAdaptiveRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2383675575304544276L;

	private String payMerchantNo;//商户编号
	private String tradeNo;//支付订单号
	private String payCode;//支付通道编码
	private BigDecimal orderAmt;//交易金额
	private BigDecimal discountableAmount;//打折金额
	private String limitPay;//是否支持信用卡
	private String orderTitle;//订单标题
	private String orderDesc;//订单描述
	private String version;//版本
	private String notifyUrl;//通知地址
	private String returnUrl;//前台地址
	private String ip;//来源IP
	private String opertorId;//操作员编号
	private String storeId;//消费店面编号
	private String terminalId;//终端编号
	private Map<String, Object> externalInfo;//扩展信息
	
	public String getPayMerchantNo() {
		return payMerchantNo;
	}
	public void setPayMerchantNo(String payMerchantNo) {
		this.payMerchantNo = payMerchantNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public BigDecimal getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}
	public BigDecimal getDiscountableAmount() {
		return discountableAmount;
	}
	public void setDiscountableAmount(BigDecimal discountableAmount) {
		this.discountableAmount = discountableAmount;
	}
	public String getLimitPay() {
		return limitPay;
	}
	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
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
	public Map<String, Object> getExternalInfo() {
		return externalInfo;
	}
	public void setExternalInfo(Map<String, Object> externalInfo) {
		this.externalInfo = externalInfo;
	}
}
