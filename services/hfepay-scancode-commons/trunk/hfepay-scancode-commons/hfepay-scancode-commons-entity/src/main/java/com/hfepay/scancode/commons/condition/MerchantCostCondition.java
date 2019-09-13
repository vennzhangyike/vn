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

@Generated("2016-11-10 13:59:57")
public class MerchantCostCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String tradeNo;//进出账流水号（进账:订单流水，出账：出账流水）
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String agentLevel;//代理商层级
	private String merchantNo;//商户编号
	private String qrCode;//支付二维码编号
	private String payCode;//平台支付通道代码
	private String type;//进出账类型，1：交易，2：提现
	private String rateType;
	private BigDecimal amount;//进出账金额
	private BigDecimal merchantRate;//进出账费率
	private BigDecimal fixedAmount;//进出账固定成本金额
	private BigDecimal merchantCost;//商户总成本
	private String status;//状态：只记进出账成功的交易
	private Date createTime;//进出帐时间
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String beginTimeStr;//开始时间
	private String endTimeStr;//结束时间

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

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
	public void setAgentLevel(String value) {
		this.agentLevel = value;
	}
	
	public String getAgentLevel() {
		return this.agentLevel;
	}
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
	}
	public void setQrCode(String value) {
		this.qrCode = value;
	}
	
	public String getQrCode() {
		return this.qrCode;
	}
	public void setPayCode(String value) {
		this.payCode = value;
	}
	
	public String getPayCode() {
		return this.payCode;
	}
	public void setType(String value) {
		this.type = value;
	}
	
	public String getType() {
		return this.type;
	}
	public void setAmount(BigDecimal value) {
		this.amount = value;
	}
	
	public BigDecimal getAmount() {
		return this.amount;
	}
	public void setMerchantRate(BigDecimal value) {
		this.merchantRate = value;
	}
	
	public BigDecimal getMerchantRate() {
		return this.merchantRate;
	}
	public void setFixedAmount(BigDecimal value) {
		this.fixedAmount = value;
	}
	
	public BigDecimal getFixedAmount() {
		return this.fixedAmount;
	}
	public void setMerchantCost(BigDecimal value) {
		this.merchantCost = value;
	}
	
	public BigDecimal getMerchantCost() {
		return this.merchantCost;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
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

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
}

