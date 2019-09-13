/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.condition;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2017-01-11 14:21:58")
public class ProfitDetailCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String identityNo;//编号，代理商或者渠道编号
	private BigDecimal profit;//利润
	private String payCode;//支付方式
	private String tradeType;//交易类别：提现01交易02
	private String merchantNo;//商户编号
	private String profitType;//分润类别:提现金额分润01交易金额分润02体现次数分润03
	private BigDecimal profitBase;//交易提现金额或者提现次数
	private String childLevelNo;//identity_no下级编号
	private BigDecimal rate;//identity_no的费率，分为交易，提现金额和提现次数，不同支付方式不同类别值不同
	private BigDecimal rateDiff;//费率差，提现次数对应的是固定费率差
	private Date createTime;//创建时间

	private String transDate;
	private String transDateEnd;
	
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
	public void setTradeType(String value) {
		this.tradeType = value;
	}
	
	public String getTradeType() {
		return this.tradeType;
	}
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
	}
	public void setProfitType(String value) {
		this.profitType = value;
	}
	
	public String getProfitType() {
		return this.profitType;
	}
	public void setProfitBase(BigDecimal value) {
		this.profitBase = value;
	}
	
	public BigDecimal getProfitBase() {
		return this.profitBase;
	}
	public void setChildLevelNo(String value) {
		this.childLevelNo = value;
	}
	
	public String getChildLevelNo() {
		return this.childLevelNo;
	}
	public void setRate(BigDecimal value) {
		this.rate = value;
	}
	
	public BigDecimal getRate() {
		return this.rate;
	}
	public void setRateDiff(BigDecimal value) {
		this.rateDiff = value;
	}
	
	public BigDecimal getRateDiff() {
		return this.rateDiff;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransDateEnd() {
		return transDateEnd;
	}

	public void setTransDateEnd(String transDateEnd) {
		this.transDateEnd = transDateEnd;
	}
	
	
}

