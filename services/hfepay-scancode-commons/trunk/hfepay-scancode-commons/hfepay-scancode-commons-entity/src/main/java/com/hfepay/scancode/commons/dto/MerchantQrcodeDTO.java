package com.hfepay.scancode.commons.dto;

import com.hfepay.scancode.commons.entity.MerchantQrcode;

public class MerchantQrcodeDTO extends MerchantQrcode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2594358284765446852L;

	private String isCashier;//是否收银
	private String qid;//收银员二维码
	public String getIsCashier() {
		return isCashier;
	}
	public void setIsCashier(String isCashier) {
		this.isCashier = isCashier;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	
	
}
