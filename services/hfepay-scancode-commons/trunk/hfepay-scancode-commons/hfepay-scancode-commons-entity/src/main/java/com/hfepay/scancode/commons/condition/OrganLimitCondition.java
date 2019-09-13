/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.condition;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2017-01-17 15:03:25")
public class OrganLimitCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String organNo;//限制机构
	private String limitType;//限额类型：0 分润提现限额 1 交易限额 2 提现限额 3 信用卡限额
	private String limitPayCode;//限额通道：0 钱包 、1微信公众号 2 支付宝
	private String limitMode;//限制方式：0 单笔 1 全日
	private BigDecimal minLimit;//最低限额
	private BigDecimal maxLimit;//最高限额
	private String status;//状态，1：正常，2：关闭
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
	private String operator;//操作人
	private String remark;//备注
	private String temp1;//限制机构
	private String temp2;//限制机构

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setOrganNo(String value) {
		this.organNo = value;
	}
	
	public String getOrganNo() {
		return this.organNo;
	}
	public void setLimitType(String value) {
		this.limitType = value;
	}
	
	public String getLimitType() {
		return this.limitType;
	}
	public void setLimitPayCode(String value) {
		this.limitPayCode = value;
	}
	
	public String getLimitPayCode() {
		return this.limitPayCode;
	}
	public void setLimitMode(String value) {
		this.limitMode = value;
	}
	
	public String getLimitMode() {
		return this.limitMode;
	}
	public void setMinLimit(BigDecimal value) {
		this.minLimit = value;
	}
	
	public BigDecimal getMinLimit() {
		return this.minLimit;
	}
	public void setMaxLimit(BigDecimal value) {
		this.maxLimit = value;
	}
	
	public BigDecimal getMaxLimit() {
		return this.maxLimit;
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

