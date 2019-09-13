/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.vo;

import java.math.BigDecimal;

import com.hfepay.scancode.commons.entity.MerchantCashier;


public class MerchantCashierVo extends MerchantCashier {
	
	
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1725792681918326560L;
	private String storeName;//门店名称
	private String bindStatus;//是否绑定二维码
	private BigDecimal cashierAmt;
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getBindStatus() {
		return bindStatus;
	}
	public void setBindStatus(String bindStatus) {
		this.bindStatus = bindStatus;
	}
	public BigDecimal getCashierAmt() {
		return cashierAmt;
	}
	public void setCashierAmt(BigDecimal cashierAmt) {
		this.cashierAmt = cashierAmt;
	}

	

	

}

