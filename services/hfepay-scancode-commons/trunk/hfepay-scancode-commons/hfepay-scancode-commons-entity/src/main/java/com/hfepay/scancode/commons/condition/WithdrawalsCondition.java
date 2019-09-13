/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.commons.condition;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-11-30 09:32:37")
public class WithdrawalsCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
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
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4
	private String queryStartTime;//查询条件
	
	private String queryEndTime;

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

	public String getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(String queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public String getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(String queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

}

