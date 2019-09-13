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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_organ_wallet", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "organNo", type = java.lang.String.class, table = "t_organ_wallet", tableAlias = "A", column = "organ_no"),
	@SelectColumnMapping(property = "balance", type = BigDecimal.class, table = "t_organ_wallet", tableAlias = "A", column = "balance"),
	@SelectColumnMapping(property = "freezesAmt", type = BigDecimal.class, table = "t_organ_wallet", tableAlias = "A", column = "freezes_amt"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_organ_wallet", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_organ_wallet", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = Date.class, table = "t_organ_wallet", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_organ_wallet", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_organ_wallet", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_organ_wallet", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_organ_wallet", tableAlias = "A", column = "temp_2")
})

@Generated("2016-12-20 11:30:02")
public class OrganWallet implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键ID
	private String organNo;//机构编号
	private BigDecimal balance;//可用余额
	private BigDecimal freezesAmt;//冻结余额
	private String status;//是否有效：1：有效，2：无效
	private Date createTime;//绑定时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//备用字段
	private String temp2;//备用字段

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setOrganNo(String value) {
		this.organNo = value;
	}
	
	public String getOrganNo() {
		return this.organNo;
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

