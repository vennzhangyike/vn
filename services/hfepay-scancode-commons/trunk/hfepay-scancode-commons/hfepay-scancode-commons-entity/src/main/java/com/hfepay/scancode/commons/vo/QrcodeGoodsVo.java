/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.vo;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.scancode.commons.entity.QrcodeGoods;

@Generated("2016-10-25 17:49:38")
public class QrcodeGoodsVo extends QrcodeGoods{
	private static final long serialVersionUID = 1L;
	
	private String operatorName;
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String value) {
		this.operatorName = value;
	}
	
	private String merchantName;

	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
}
	

