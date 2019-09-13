package com.hfepay.scancode.commons.bo;

import java.io.Serializable;

public class AdviertismentBo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String merchantNo;
	private String adviertPosition;
	private String pname;
	private String city;
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getAdviertPosition() {
		return adviertPosition;
	}
	public void setAdviertPosition(String adviertPosition) {
		this.adviertPosition = adviertPosition;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
