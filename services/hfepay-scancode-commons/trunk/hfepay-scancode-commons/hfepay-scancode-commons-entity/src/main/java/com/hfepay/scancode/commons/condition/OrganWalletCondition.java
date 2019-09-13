/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-12-20 11:30:02")
public class OrganWalletCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键ID
	private String organNo;//机构编号
	private BigDecimal balance;//可用余额
	private BigDecimal freezesAmt;//冻结余额
	private String status;//是否有效：1：有效，2：无效
	private Date createTime;//绑定时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//备用字段
	private String temp2;//备用字段
	private Date updateTime;
	private BigDecimal modifyBalance;
	private BigDecimal freezenBalance;

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

	public BigDecimal getModifyBalance() {
		return modifyBalance;
	}

	public void setModifyBalance(BigDecimal modifyBalance) {
		this.modifyBalance = modifyBalance;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getFreezenBalance() {
		return freezenBalance;
	}

	public void setFreezenBalance(BigDecimal freezenBalance) {
		this.freezenBalance = freezenBalance;
	}
	
	
}

