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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_temp_profit", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "identityNo", type = java.lang.String.class, table = "t_temp_profit", tableAlias = "A", column = "identity_no"),
	@SelectColumnMapping(property = "profit", type = BigDecimal.class, table = "t_temp_profit", tableAlias = "A", column = "profit"),
	@SelectColumnMapping(property = "payCode", type = java.lang.String.class, table = "t_temp_profit", tableAlias = "A", column = "pay_code"),
	@SelectColumnMapping(property = "tradeType", type = java.lang.String.class, table = "t_temp_profit", tableAlias = "A", column = "trade_type")
})

@Generated("2016-12-01 15:05:27")
public class TempProfit implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String identityNo;//编号，代理商或者渠道编号
	private BigDecimal profit;//利润
	private String payCode;//支付方式
	private String tradeType;//交易类别，交易，提现

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setIdentityNo(String value) {
		this.identityNo = value;
	}
	
	public String getIdentityNo() {
		return this.identityNo;
	}
	public void setProfit(BigDecimal value) {
		this.profit = value;
	}
	
	public BigDecimal getProfit() {
		return this.profit;
	}
	public void setPayCode(String value) {
		this.payCode = value;
	}
	
	public String getPayCode() {
		return this.payCode;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	
}

