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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "batchNo", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "batch_no"),
	@SelectColumnMapping(property = "tradeNo", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "trade_no"),
	@SelectColumnMapping(property = "hfTradeNo", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "hf_trade_no"),
	@SelectColumnMapping(property = "hfMerchantNo", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "hf_merchant_no"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "transAmt", type = BigDecimal.class, table = "t_clearing_t1_err", tableAlias = "A", column = "trans_amt"),
	@SelectColumnMapping(property = "tradeType", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "trade_type"),
	@SelectColumnMapping(property = "payCode", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "pay_code"),
	@SelectColumnMapping(property = "checkFlag", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "check_flag"),
	@SelectColumnMapping(property = "processingStatus", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "processing_status"),
	@SelectColumnMapping(property = "clearDate", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "clear_date"),
	@SelectColumnMapping(property = "tradeDate", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "trade_date"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_clearing_t1_err", tableAlias = "A", column = "temp_2")
})

@Generated("2017-03-17 18:04:17")
public class ClearingT1Err implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String batchNo;//批次号
	private String tradeNo;//交易流水
	private String hfTradeNo;//华付通交易流水
	private String hfMerchantNo;//华付通商户编号
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private BigDecimal transAmt;//交易金额
	private String tradeType;//交易类型
	private String payCode;//支付代码
	private String checkFlag;//错账类型：01对方有本地无 ，10本地有对方无 ， 11双方不一致
	private String processingStatus;//处理标示：0 未处理 处理成功 2 处理失败
	private String clearDate;//对账日期
	private String tradeDate;//交易日期
	private String temp1;//temp1
	private String temp2;//temp2

	private String channelName;//渠道名称
	private String agentName;//代理商名称
	private String merchantName;//商户名称
	
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
	public void setChannelNo(String value) {
		this.channelNo = value;
	}
	
	public String getChannelNo() {
		return this.channelNo;
	}
	public void setAgentNo(String value) {
		this.agentNo = value;
	}
	
	public String getAgentNo() {
		return this.agentNo;
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
	public void setCheckFlag(String value) {
		this.checkFlag = value;
	}
	
	public String getCheckFlag() {
		return this.checkFlag;
	}
	public void setProcessingStatus(String value) {
		this.processingStatus = value;
	}
	
	public String getProcessingStatus() {
		return this.processingStatus;
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

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	
}

