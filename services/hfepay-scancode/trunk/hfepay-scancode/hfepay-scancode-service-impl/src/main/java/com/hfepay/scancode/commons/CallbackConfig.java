package com.hfepay.scancode.commons;

import java.io.Serializable;

public class CallbackConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6956694202690710813L;

	private String notifyUrl;
	
	private String returnUrl;
	
	private String withdrawsNotifyUrl;
	
	private String bankChangeUrl;
	
	private String scanPayNotifyUrl;//条码支付回调地址
	
	public String getBankChangeUrl() {
		return bankChangeUrl;
	}

	public void setBankChangeUrl(String bankChangeUrl) {
		this.bankChangeUrl = bankChangeUrl;
	}

	public String getWithdrawsNotifyUrl() {
		return withdrawsNotifyUrl;
	}

	public void setWithdrawsNotifyUrl(String withdrawsNotifyUrl) {
		this.withdrawsNotifyUrl = withdrawsNotifyUrl;
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

	public String getScanPayNotifyUrl() {
		return scanPayNotifyUrl;
	}

	public void setScanPayNotifyUrl(String scanPayNotifyUrl) {
		this.scanPayNotifyUrl = scanPayNotifyUrl;
	}
	
	

}
