package com.hfepay.scancode.commons.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderBo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3272711893306194253L;
	
	private String qrCode;//二维码编号
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String payCode;
	private BigDecimal price;
	private BigDecimal tradeAmt;//订单原交易金额	
	private BigDecimal discountAmt;//打折金额
	private String discountStrategy;//打折策略
	private String authCode;//条码支付授权码
	private String scene;//支付类别：声波，红外线
	private String cashier;//收银员
	private String storeNo;//收银员绑定的门店编号
	private String ip;
	private String payStrategy;//支付策略：支付宝扫码，微信扫码等（根据支付的入口不同，支付策略不同）
	private String productName;//商品名称
	
	private String platformMerchantNo;//支付商户编号
	private String requestSource="0";//请求发起的源头，0网页，1接口
	private String notifyUrl;//第三方上送的回调地址
	private String userOrderNo;//第三方上送的请求流水号
	
	public String getRequestSource() {
		return requestSource;
	}
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public BigDecimal getTradeAmt() {
		return tradeAmt;
	}
	public void setTradeAmt(BigDecimal tradeAmt) {
		this.tradeAmt = tradeAmt;
	}
	public BigDecimal getDiscountAmt() {
		return discountAmt;
	}
	public void setDiscountAmt(BigDecimal discountAmt) {
		this.discountAmt = discountAmt;
	}
	public String getDiscountStrategy() {
		return discountStrategy;
	}
	public void setDiscountStrategy(String discountStrategy) {
		this.discountStrategy = discountStrategy;
	}
	
	public String getPayStrategy() {
		return payStrategy;
	}
	public void setPayStrategy(String payStrategy) {
		this.payStrategy = payStrategy;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPlatformMerchantNo() {
		return platformMerchantNo;
	}
	public void setPlatformMerchantNo(String platformMerchantNo) {
		this.platformMerchantNo = platformMerchantNo;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getUserOrderNo() {
		return userOrderNo;
	}
	public void setUserOrderNo(String userOrderNo) {
		this.userOrderNo = userOrderNo;
	}
	@Override
	public String toString() {
		return "OrderBo [qrCode=" + qrCode + ", channelNo=" + channelNo + ", agentNo=" + agentNo + ", merchantNo="
				+ merchantNo + ", payCode=" + payCode + ", price=" + price + ", tradeAmt=" + tradeAmt + ", discountAmt="
				+ discountAmt + ", discountStrategy=" + discountStrategy + ", authCode=" + authCode + ", scene=" + scene
				+ ", cashier=" + cashier + ", storeNo=" + storeNo + ", ip=" + ip + ", payStrategy=" + payStrategy
				+ ", productName=" + productName + ", platformMerchantNo=" + platformMerchantNo + ", requestSource="
				+ requestSource + ", notifyUrl=" + notifyUrl + ", userOrderNo=" + userOrderNo + "]";
	}
	
	
	
	
	
	
	
}
