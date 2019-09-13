/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.Long.class, table = "t_remit_bank_info", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "version", type = Integer.class, table = "t_remit_bank_info", tableAlias = "A", column = "VERSION"),
	@SelectColumnMapping(property = "bankChannelNo", type = java.lang.String.class, table = "t_remit_bank_info", tableAlias = "A", column = "BANK_CHANNEL_NO"),
	@SelectColumnMapping(property = "bankName", type = java.lang.String.class, table = "t_remit_bank_info", tableAlias = "A", column = "BANK_NAME"),
	@SelectColumnMapping(property = "province", type = java.lang.String.class, table = "t_remit_bank_info", tableAlias = "A", column = "PROVINCE"),
	@SelectColumnMapping(property = "city", type = java.lang.String.class, table = "t_remit_bank_info", tableAlias = "A", column = "CITY"),
	@SelectColumnMapping(property = "bankType", type = Integer.class, table = "t_remit_bank_info", tableAlias = "A", column = "BANK_TYPE"),
	@SelectColumnMapping(property = "clearBankChannelNo", type = java.lang.String.class, table = "t_remit_bank_info", tableAlias = "A", column = "CLEAR_BANK_CHANNEL_NO"),
	@SelectColumnMapping(property = "isInProvince", type = Integer.class, table = "t_remit_bank_info", tableAlias = "A", column = "IS_IN_PROVINCE")
})

@Generated("2016-10-26 14:40:32")
public class RemitBankInfo implements IdEntity<Integer> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private Integer id;//id
	private Integer version;//版本号
	private String bankChannelNo;//银行行号
	private String bankName;//银行名称
	private String province;//省份
	private String city;//城市
	private Integer bankType;//类别：1-清算行，2-开户行
	private String clearBankChannelNo;//清算行行号
	private Integer isInProvince;//是否省内：100-是，101-否

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

}

