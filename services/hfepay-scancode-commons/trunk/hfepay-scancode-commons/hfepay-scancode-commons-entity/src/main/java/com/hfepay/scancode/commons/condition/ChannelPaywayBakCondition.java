/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-10-18 15:28:16")
public class ChannelPaywayBakCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String channelName;//渠道名称
	private String payCode;//平台支付通道代码
	private String payName;//华付支付通道名称
	private BigDecimal t0Rate;//T0费率
	private BigDecimal t1Rate;//T1费率
	private BigDecimal rate;//提现手续费率
	private BigDecimal rateAmount;//固定手续费金额
	private String status;//状态，1：正常，2：关闭
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setChannelNo(String value) {
		this.channelNo = value;
	}
	
	public String getChannelNo() {
		return this.channelNo;
	}
	public void setChannelName(String value) {
		this.channelName = value;
	}
	
	public String getChannelName() {
		return this.channelName;
	}
	public void setPayCode(String value) {
		this.payCode = value;
	}
	
	public String getPayCode() {
		return this.payCode;
	}
	public void setPayName(String value) {
		this.payName = value;
	}
	
	public String getPayName() {
		return this.payName;
	}
	public void setT0Rate(BigDecimal value) {
		this.t0Rate = value;
	}
	
	public BigDecimal getT0Rate() {
		return this.t0Rate;
	}
	public void setT1Rate(BigDecimal value) {
		this.t1Rate = value;
	}
	
	public BigDecimal getT1Rate() {
		return this.t1Rate;
	}
	public void setRate(BigDecimal value) {
		this.rate = value;
	}
	
	public BigDecimal getRate() {
		return this.rate;
	}
	public void setRateAmount(BigDecimal value) {
		this.rateAmount = value;
	}
	
	public BigDecimal getRateAmount() {
		return this.rateAmount;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setRecordStatus(String value) {
		this.recordStatus = value;
	}
	
	public String getRecordStatus() {
		return this.recordStatus;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
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
}

