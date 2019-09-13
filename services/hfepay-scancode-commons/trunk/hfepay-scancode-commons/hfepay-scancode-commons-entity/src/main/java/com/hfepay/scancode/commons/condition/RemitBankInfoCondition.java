/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-10-26 14:40:32")
public class RemitBankInfoCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;//id
	private Integer version;//版本号
	private String bankChannelNo;//银行行号
	private String bankName;//银行名称
	private String province;//省份
	private String city;//城市
	private Integer bankType;//类别：1-清算行，2-开户行
	private String clearBankChannelNo;//清算行行号
	private Integer isInProvince;//是否省内：100-是，101-否
	
	private String bankCard;//银行卡卡号

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setVersion(Integer value) {
		this.version = value;
	}
	
	public Integer getVersion() {
		return this.version;
	}
	public void setBankChannelNo(String value) {
		this.bankChannelNo = value;
	}
	
	public String getBankChannelNo() {
		return this.bankChannelNo;
	}
	public void setBankName(String value) {
		this.bankName = value;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	public void setProvince(String value) {
		this.province = value;
	}
	
	public String getProvince() {
		return this.province;
	}
	public void setCity(String value) {
		this.city = value;
	}
	
	public String getCity() {
		return this.city;
	}
	public void setBankType(Integer value) {
		this.bankType = value;
	}
	
	public Integer getBankType() {
		return this.bankType;
	}
	public void setClearBankChannelNo(String value) {
		this.clearBankChannelNo = value;
	}
	
	public String getClearBankChannelNo() {
		return this.clearBankChannelNo;
	}
	public void setIsInProvince(Integer value) {
		this.isInProvince = value;
	}
	
	public Integer getIsInProvince() {
		return this.isInProvince;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	
	
}

