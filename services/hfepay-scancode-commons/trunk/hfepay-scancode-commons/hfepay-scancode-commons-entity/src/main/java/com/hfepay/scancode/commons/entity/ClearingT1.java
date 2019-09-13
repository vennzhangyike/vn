/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.entity;

import java.math.BigDecimal;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "batchNo", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "batch_no"),
	@SelectColumnMapping(property = "hfTradeNo", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "hf_trade_no"),
	@SelectColumnMapping(property = "tradeNo", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "trade_no"),
	@SelectColumnMapping(property = "clearDate", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "clear_date"),
	@SelectColumnMapping(property = "tradeDate", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "trade_date"),
	@SelectColumnMapping(property = "tradeType", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "trade_type"),
	@SelectColumnMapping(property = "payCode", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "pay_code"),
	@SelectColumnMapping(property = "hfMerchantNo", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "hf_merchant_no"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "transAmt", type = BigDecimal.class, table = "t_clearing_t1", tableAlias = "A", column = "trans_amt"),
	@SelectColumnMapping(property = "merchantFees", type = BigDecimal.class, table = "t_clearing_t1", tableAlias = "A", column = "merchant_fees"),
	@SelectColumnMapping(property = "channelFees", type = BigDecimal.class, table = "t_clearing_t1", tableAlias = "A", column = "channel_fees"),
	@SelectColumnMapping(property = "accountType", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "account_type"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_clearing_t1", tableAlias = "A", column = "temp_2")
})

@Generated("2017-02-09 16:20:29")
public class ClearingT1 implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String batchNo;//批次号
	private String hfTradeNo;//华付通交易订单
	private String tradeNo;//交易订单
	private String clearDate;//对账日期
	private String tradeDate;//交易日期
	private String tradeType;//交易类型
	private String payCode;//支付代码
	private String hfMerchantNo;//华付通商户编号
	private String merchantNo;//商户编号
	private BigDecimal transAmt;//交易金额
	private BigDecimal merchantFees;//商户手续费
	private BigDecimal channelFees;//渠道手续费
	private String accountType;//借贷标识
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
	public void setHfTradeNo(String value) {
		this.hfTradeNo = value;
	}
	
	public String getHfTradeNo() {
		return this.hfTradeNo;
	}
	public void setTradeNo(String value) {
		this.tradeNo = value;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
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
	public void setTradeType(String value) {
		this.tradeType = value;
	}
	
	public String getTradeType() {
		return this.tradeType;
	}
	public void setPayCode(String value) {
		this.payCode = value;
	}
	
	public String getPayCode() {
		return this.payCode;
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
	public void setMerchantFees(BigDecimal value) {
		this.merchantFees = value;
	}
	
	public BigDecimal getMerchantFees() {
		return this.merchantFees;
	}
	public void setChannelFees(BigDecimal value) {
		this.channelFees = value;
	}
	
	public BigDecimal getChannelFees() {
		return this.channelFees;
	}
	public void setAccountType(String value) {
		this.accountType = value;
	}
	
	public String getAccountType() {
		return this.accountType;
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

