/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

import java.math.BigDecimal;
import java.util.Date;

@Generated("2016-11-18 15:02:00")
public class PlatformLimitCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String limitPayCode;//限额通道：1 不限 2.通道编号
	private String limitType;//限额类型：1 不限 2信用卡 3普通卡
	private BigDecimal dayTransLimit;//全日交易限额
	private BigDecimal dayWithdrawalsLimit;//全日提现限额
	private BigDecimal singleTransLimit;//单笔交易限额
	private BigDecimal singleWithdrawalsLimit;//单笔提现限额
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
	public void setLimitPayCode(String value) {
		this.limitPayCode = value;
	}
	
	public String getLimitPayCode() {
		return this.limitPayCode;
	}
	public void setLimitType(String value) {
		this.limitType = value;
	}
	
	public String getLimitType() {
		return this.limitType;
	}
	public void setDayTransLimit(BigDecimal value) {
		this.dayTransLimit = value;
	}
	
	public BigDecimal getDayTransLimit() {
		return this.dayTransLimit;
	}
	public void setDayWithdrawalsLimit(BigDecimal value) {
		this.dayWithdrawalsLimit = value;
	}
	
	public BigDecimal getDayWithdrawalsLimit() {
		return this.dayWithdrawalsLimit;
	}
	public void setSingleTransLimit(BigDecimal value) {
		this.singleTransLimit = value;
	}
	
	public BigDecimal getSingleTransLimit() {
		return this.singleTransLimit;
	}
	public void setSingleWithdrawalsLimit(BigDecimal value) {
		this.singleWithdrawalsLimit = value;
	}
	
	public BigDecimal getSingleWithdrawalsLimit() {
		return this.singleWithdrawalsLimit;
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

