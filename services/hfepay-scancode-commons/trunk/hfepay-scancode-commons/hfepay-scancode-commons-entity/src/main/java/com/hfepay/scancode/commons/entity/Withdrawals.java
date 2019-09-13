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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "tradeNo", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "trade_no"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "payCode", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "pay_code"),
	@SelectColumnMapping(property = "rateDetail", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "rate_detail"),
	@SelectColumnMapping(property = "orderAmt", type = BigDecimal.class, table = "t_withdrawals", tableAlias = "A", column = "order_amt"),
	@SelectColumnMapping(property = "drawFee", type = BigDecimal.class, table = "t_withdrawals", tableAlias = "A", column = "drawFee"),
	@SelectColumnMapping(property = "tradeFee", type = BigDecimal.class, table = "t_withdrawals", tableAlias = "A", column = "tradeFee"),
	@SelectColumnMapping(property = "merchantCost", type = BigDecimal.class, table = "t_withdrawals", tableAlias = "A", column = "merchant_cost"),
	@SelectColumnMapping(property = "payTradeNo", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "pay_trade_no"),
	@SelectColumnMapping(property = "beginTime", type = java.util.Date.class, table = "t_withdrawals", tableAlias = "A", column = "begin_time"),
	@SelectColumnMapping(property = "endTime", type = java.util.Date.class, table = "t_withdrawals", tableAlias = "A", column = "end_time"),
	@SelectColumnMapping(property = "settleTime", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "settle_time"),
	@SelectColumnMapping(property = "settleMerchant", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "settle_merchant"),
	@SelectColumnMapping(property = "refundStatus", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "refund_status"),
	@SelectColumnMapping(property = "batchId", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "batch_id"),
	@SelectColumnMapping(property = "resultCode", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "result_code"),
	@SelectColumnMapping(property = "resultInfo", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "result_info"),
	@SelectColumnMapping(property = "optCode", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "opt_code"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "record_status"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_withdrawals", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "temp_2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "temp_3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_withdrawals", tableAlias = "A", column = "temp_4")
})

@Generated("2016-11-30 09:32:37")
public class Withdrawals implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//id
	private String tradeNo;//提现订单编号
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String payCode;//平台支付通道代码
	private String rateDetail;//交易费率信息
	private BigDecimal orderAmt;//提现金额
	private BigDecimal drawFee;//提现单笔手续费
	private BigDecimal tradeFee;//交易手续费
	private BigDecimal merchantCost;//商户总成本
	private String payTradeNo;//支付通道流水号
	private Date beginTime;//交易开始时间
	private Date endTime;//交易结束时间
	private String settleTime;//结算日期（订单成功）
	private String settleMerchant;//商户清算时间
	private String refundStatus;//撤销状态（Y 已退款，N 未退款）
	private String batchId;//批量批次号
	private String resultCode;//0:订单已创建 1:处理中2:处理成功 3:处理失败
	private String resultInfo;//交易结果信息
	private String optCode;//操作编码
	private String recordStatus;//记录状态（Y有效，N无效）
	private Date updateTime;//回调时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4
	private String channelName;
	private String agentName;
	private String merchantName;

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setTradeNo(String value) {
		this.tradeNo = value;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
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
	public void setPayCode(String value) {
		this.payCode = value;
	}
	
	public String getPayCode() {
		return this.payCode;
	}
	public void setRateDetail(String value) {
		this.rateDetail = value;
	}
	
	public String getRateDetail() {
		return this.rateDetail;
	}
	
	public BigDecimal getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}

	public BigDecimal getDrawFee() {
		return drawFee;
	}

	public void setDrawFee(BigDecimal drawFee) {
		this.drawFee = drawFee;
	}

	public BigDecimal getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(BigDecimal tradeFee) {
		this.tradeFee = tradeFee;
	}

	public BigDecimal getMerchantCost() {
		return merchantCost;
	}

	public void setMerchantCost(BigDecimal merchantCost) {
		this.merchantCost = merchantCost;
	}

	public void setPayTradeNo(String value) {
		this.payTradeNo = value;
	}
	
	public String getPayTradeNo() {
		return this.payTradeNo;
	}
	public void setBeginTime(Date value) {
		this.beginTime = value;
	}
	
	public Date getBeginTime() {
		return this.beginTime;
	}
	public void setEndTime(Date value) {
		this.endTime = value;
	}
	
	public Date getEndTime() {
		return this.endTime;
	}
	public void setSettleTime(String value) {
		this.settleTime = value;
	}
	
	public String getSettleTime() {
		return this.settleTime;
	}
	public void setSettleMerchant(String value) {
		this.settleMerchant = value;
	}
	
	public String getSettleMerchant() {
		return this.settleMerchant;
	}
	public void setRefundStatus(String value) {
		this.refundStatus = value;
	}
	
	public String getRefundStatus() {
		return this.refundStatus;
	}
	public void setBatchId(String value) {
		this.batchId = value;
	}
	
	public String getBatchId() {
		return this.batchId;
	}
	public void setResultCode(String value) {
		this.resultCode = value;
	}
	
	public String getResultCode() {
		return this.resultCode;
	}
	public void setResultInfo(String value) {
		this.resultInfo = value;
	}
	
	public String getResultInfo() {
		return this.resultInfo;
	}
	public void setOptCode(String value) {
		this.optCode = value;
	}
	
	public String getOptCode() {
		return this.optCode;
	}
	public void setRecordStatus(String value) {
		this.recordStatus = value;
	}
	
	public String getRecordStatus() {
		return this.recordStatus;
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
	public void setTemp3(String value) {
		this.temp3 = value;
	}
	
	public String getTemp3() {
		return this.temp3;
	}
	public void setTemp4(String value) {
		this.temp4 = value;
	}
	
	public String getTemp4() {
		return this.temp4;
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

