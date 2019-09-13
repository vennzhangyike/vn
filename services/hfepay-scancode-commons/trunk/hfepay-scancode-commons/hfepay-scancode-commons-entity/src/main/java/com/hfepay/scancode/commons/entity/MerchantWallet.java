/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_merchant_wallet", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_merchant_wallet", tableAlias = "A", column = "MERCHANT_NO"),
	@SelectColumnMapping(property = "merchantName", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "C", column = "MERCHANT_NAME"),
	@SelectColumnMapping(property = "balance", type = BigDecimal.class, table = "t_merchant_wallet", tableAlias = "A", column = "BALANCE"),
	@SelectColumnMapping(property = "freezesAmt", type = BigDecimal.class, table = "t_merchant_wallet", tableAlias = "A", column = "FREEZES_AMT"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_merchant_wallet", tableAlias = "A", column = "STATUS"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_merchant_wallet", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_merchant_wallet", tableAlias = "A", column = "UPDATE_TIME"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_merchant_wallet", tableAlias = "A", column = "OPERATOR"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_merchant_wallet", tableAlias = "A", column = "REMARK"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_merchant_wallet", tableAlias = "A", column = "TEMP_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_merchant_wallet", tableAlias = "A", column = "TEMP_2")
})

@Generated("2016-10-18 18:02:16")
public class MerchantWallet implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//id
	private String merchantNo;//商户编号
	private BigDecimal balance;//可用余额
	private BigDecimal freezesAmt;//冻结余额
	private String status;//是否有效：1：有效，2：无效
	private Date createTime;//入网时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
	}
	public void setBalance(BigDecimal value) {
		this.balance = value;
	}
	
	public BigDecimal getBalance() {
		return this.balance;
	}
	public void setFreezesAmt(BigDecimal value) {
		this.freezesAmt = value;
	}
	
	public BigDecimal getFreezesAmt() {
		return this.freezesAmt;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
		return this.operator;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setTemp1(String value) {
		this.temp1 = value;
	}
	
	public String getTemp1() {
		return this.temp1;
	}
	public void setTemp2(String value) {
		this.temp2 = value;
	}
	
	public String getTemp2() {
		return this.temp2;
	}

}

