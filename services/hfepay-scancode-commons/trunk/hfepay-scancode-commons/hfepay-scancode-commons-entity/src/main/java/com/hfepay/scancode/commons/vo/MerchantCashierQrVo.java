/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.vo;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;

@Generated("2016-11-10 16:13:31")
public class MerchantCashierQrVo extends MerchantCashierQr{
	private static final long serialVersionUID = 1L;
	
	private String qrName;
	private String cashierName;
	//private String merchantName;

	public String getQrName() {
		return qrName;
	}

	public void setQrName(String qrName) {
		this.qrName = qrName;
	}
	
	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	/*public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}*/
	
}
	

