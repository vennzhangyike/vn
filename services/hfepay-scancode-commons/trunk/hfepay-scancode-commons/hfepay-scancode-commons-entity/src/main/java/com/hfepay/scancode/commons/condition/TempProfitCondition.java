/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.math.BigDecimal;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-12-01 15:05:27")
public class TempProfitCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String identityNo;//编号，代理商或者渠道编号
	private BigDecimal profit;//利润
	private String payCode;//支付方式
	private String tradeType;//支付02提现01

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

