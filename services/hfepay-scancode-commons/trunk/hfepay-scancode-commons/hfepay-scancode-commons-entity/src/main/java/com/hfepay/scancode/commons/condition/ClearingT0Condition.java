/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.condition;

import java.math.BigDecimal;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2017-03-20 19:07:10")
public class ClearingT0Condition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String batchNo;//批次号
	private String tradeNo;//交易流水
	private String hfTradeNo;//华付通交易流水
	private String hfMerchantNo;//华付通商户编号
	private String merchantNo;//商户编号
	private BigDecimal transAmt;//交易金额
	private BigDecimal arrivalAmt;//到账金额
	private BigDecimal fees;//手续费
	private BigDecimal singleFees;//单笔手续费
	private BigDecimal channelSingleFees;//渠道单笔手续费
	private String clearDate;//结算日期
	private String tradeDate;//提现时间
	private String temp1;//temp1
	private String temp2;//temp2

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setBatchNo(String value) {
		this.batchNo = value;
	}
	
	public String getBatchNo() {
		return this.batchNo;
	}
	public void setTradeNo(String value) {
		this.tradeNo = value;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
	public void setHfTradeNo(String value) {
		this.hfTradeNo = value;
	}
	
	public String getHfTradeNo() {
		return this.hfTradeNo;
	}
	public void setHfMerchantNo(String value) {
		this.hfMerchantNo = value;
	}
	
	public String getHfMerchantNo() {
		return this.hfMerchantNo;
	}
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
	}
	public void setTransAmt(BigDecimal value) {
		this.transAmt = value;
	}
	
	public BigDecimal getTransAmt() {
		return this.transAmt;
	}
	public void setArrivalAmt(BigDecimal value) {
		this.arrivalAmt = value;
	}
	
	public BigDecimal getArrivalAmt() {
		return this.arrivalAmt;
	}
	public void setFees(BigDecimal value) {
		this.fees = value;
	}
	
	public BigDecimal getFees() {
		return this.fees;
	}
	public void setSingleFees(BigDecimal value) {
		this.singleFees = value;
	}
	
	public BigDecimal getSingleFees() {
		return this.singleFees;
	}
	public void setChannelSingleFees(BigDecimal value) {
		this.channelSingleFees = value;
	}
	
	public BigDecimal getChannelSingleFees() {
		return this.channelSingleFees;
	}
	public void setClearDate(String value) {
		this.clearDate = value;
	}
	
	public String getClearDate() {
		return this.clearDate;
	}
	public void setTradeDate(String value) {
		this.tradeDate = value;
	}
	
	public String getTradeDate() {
		return this.tradeDate;
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

