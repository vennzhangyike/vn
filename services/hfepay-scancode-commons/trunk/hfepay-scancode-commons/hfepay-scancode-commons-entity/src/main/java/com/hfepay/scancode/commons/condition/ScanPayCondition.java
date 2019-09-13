package com.hfepay.scancode.commons.condition;

import java.io.Serializable;
import java.math.BigDecimal;

public class ScanPayCondition implements Serializable{

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 8211566097650576278L;
	private String payCode;//支付方式
	private BigDecimal orderAmt;//订单金额
	private BigDecimal tradeAmt;//订单原交易金额	
	private BigDecimal discountAmt;//打折金额
	private String discountStrategy;//打折策略
	private String authCode;//支付授权码
	private String merchantNo;//商户编号
	private String scene="1";//1 红外线，2声波
	private String identityNo;
	private String fromH5;//从h5过来的条码不做支付方式校验，
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
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public String getFromH5() {
		return fromH5;
	}
	public void setFromH5(String fromH5) {
		this.fromH5 = fromH5;
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
	@Override
	public String toString() {
		return "ScanPayCondition [payCode=" + payCode + ", orderAmt=" + orderAmt + ", authCode=" + authCode
				+ ", merchantNo=" + merchantNo + ", scene=" + scene+ ", identityNo=" + identityNo+", fromH5=" + fromH5 + "]";
	}

}
