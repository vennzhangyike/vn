/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

import java.math.BigDecimal;
import java.util.Date;

@Generated("2016-10-19 16:13:23")
public class HfepayPaywayCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//id
	private String serverNo;//服务码
	private String serverName;//服务名称
	private String payCode;//平台支付通道代码
	private String payName;//平台支付通道名称
	private String payType;//通道类型，1：商户入驻，2：支付通道
	private String payDesc;//支付通道描述
	private String paySeq;//支付通道优先级
	private BigDecimal t0Rate;//垫资费率
	private BigDecimal t1Rate;//交易手续费
	private BigDecimal rate;//提现手续费(成本)
	private String recordStatus;//记录状态，Y:正常，N:删除
	private Date createTime;//创建时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setServerNo(String value) {
		this.serverNo = value;
	}
	
	public String getServerNo() {
		return this.serverNo;
	}
	public void setServerName(String value) {
		this.serverName = value;
	}
	
	public String getServerName() {
		return this.serverName;
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
	public void setPayType(String value) {
		this.payType = value;
	}
	
	public String getPayType() {
		return this.payType;
	}
	public void setPayDesc(String value) {
		this.payDesc = value;
	}
	
	public String getPayDesc() {
		return this.payDesc;
	}
	public void setPaySeq(String value) {
		this.paySeq = value;
	}
	
	public String getPaySeq() {
		return this.paySeq;
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
}

