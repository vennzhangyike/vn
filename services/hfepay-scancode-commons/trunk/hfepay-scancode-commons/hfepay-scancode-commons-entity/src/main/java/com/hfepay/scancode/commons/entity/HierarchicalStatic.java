/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import java.math.BigDecimal;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "organNo", type = java.lang.String.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "organ_no"),
	@SelectColumnMapping(property = "totalProfit", type = BigDecimal.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "total_profit")
})

@Generated("2016-12-20 11:30:01")
public class HierarchicalStatic implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键ID
	private String organNo;//机构编号
	private String organName;//机构名称
	private BigDecimal totalProfit;//总利润
	private BigDecimal totalActualAmount;//总提现
	private BigDecimal balance;//钱包余额

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
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

	
}

