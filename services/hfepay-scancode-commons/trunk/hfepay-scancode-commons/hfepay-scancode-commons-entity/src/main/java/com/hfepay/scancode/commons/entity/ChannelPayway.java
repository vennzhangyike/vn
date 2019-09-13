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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_channel_payway", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_channel_payway", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "channelName", type = java.lang.String.class, table = "t_channel_payway", tableAlias = "C", column = "channel_name"),
	@SelectColumnMapping(property = "payCode", type = java.lang.String.class, table = "t_channel_payway", tableAlias = "A", column = "pay_code"),
	@SelectColumnMapping(property = "payName", type = java.lang.String.class, table = "t_mapping_dicion", tableAlias = "D", column = "para_name"),
	@SelectColumnMapping(property = "t0Rate", type = BigDecimal.class, table = "t_channel_payway", tableAlias = "A", column = "t0_rate"),
	@SelectColumnMapping(property = "t1Rate", type = BigDecimal.class, table = "t_channel_payway", tableAlias = "A", column = "t1_rate"),
	@SelectColumnMapping(property = "rate", type = BigDecimal.class, table = "t_channel_payway", tableAlias = "A", column = "rate"),
	@SelectColumnMapping(property = "rateAmount", type = BigDecimal.class, table = "t_channel_payway", tableAlias = "A", column = "rate_amount"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_channel_payway", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_channel_payway", tableAlias = "A", column = "record_status"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_channel_payway", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_channel_payway", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_channel_payway", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_channel_payway", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_channel_payway", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_channel_payway", tableAlias = "A", column = "temp_2")
})

@Generated("2016-10-18 15:28:16")
public class ChannelPayway implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String payCode;//平台支付通道代码
	private BigDecimal t0Rate;//T0费率
	private BigDecimal t1Rate;//T1费率
	private BigDecimal rate;//提现手续费率
	private BigDecimal rateAmount;//固定手续费金额
	private String status;//状态，1：正常，2：关闭
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
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
	public void setPayCode(String value) {
		this.payCode = value;
	}
	
	public String getPayCode() {
		return this.payCode;
	}
	
	public BigDecimal getT0Rate() {
		return t0Rate;
	}

	public void setT0Rate(BigDecimal t0Rate) {
		this.t0Rate = t0Rate;
	}

	public BigDecimal getT1Rate() {
		return t1Rate;
	}

	public void setT1Rate(BigDecimal t1Rate) {
		this.t1Rate = t1Rate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getRateAmount() {
		return rateAmount;
	}

	public void setRateAmount(BigDecimal rateAmount) {
		this.rateAmount = rateAmount;
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

	
}

