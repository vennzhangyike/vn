/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "organNo", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "organ_no"),
	@SelectColumnMapping(property = "limitType", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "limit_type"),
	@SelectColumnMapping(property = "limitPayCode", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "limit_pay_code"),
	@SelectColumnMapping(property = "limitMode", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "limit_mode"),
	@SelectColumnMapping(property = "minLimit", type = BigDecimal.class, table = "t_organ_limit", tableAlias = "A", column = "min_limit"),
	@SelectColumnMapping(property = "maxLimit", type = BigDecimal.class, table = "t_organ_limit", tableAlias = "A", column = "max_limit"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "record_status"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_organ_limit", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_organ_limit", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_organ_limit", tableAlias = "A", column = "temp_2")
})

@Generated("2017-01-17 15:03:25")
public class OrganLimit implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
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
	private Date updateTime;//修改时间
	private String operator;//操作人
	private String remark;//备注
	private String temp1;//限制机构
	private String temp2;//限制机构
	private String organName;//机构名称

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

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	
}

