package com.hfepay.scancode.commons.vo;

import java.util.Date;

import com.hfepay.scancode.commons.entity.ProfitDetail;

public class ProfitDetailVO extends ProfitDetail{

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 3832734214386749099L;
	private String organName;
	private String merchantName;
	private Date transDate;
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
}
