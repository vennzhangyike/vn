/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.math.BigDecimal;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-12-20 11:30:01")
public class HierarchicalStaticCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键ID
	private String organNo;//机构编号
	private String organName;//机构名称
	private BigDecimal totalProfit;//总利润
	private BigDecimal totalActualAmount;//总提现
	private BigDecimal balance;//钱包余额
	private String identityFlag;
	
	public String getIdentityFlag() {
		return identityFlag;
	}

	public void setIdentityFlag(String identityFlag) {
		this.identityFlag = identityFlag;
	}

	public String getOrganNo() {
		return organNo;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}

	public BigDecimal getTotalActualAmount() {
		return totalActualAmount;
	}

	public void setTotalActualAmount(BigDecimal totalActualAmount) {
		this.totalActualAmount = totalActualAmount;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
}

