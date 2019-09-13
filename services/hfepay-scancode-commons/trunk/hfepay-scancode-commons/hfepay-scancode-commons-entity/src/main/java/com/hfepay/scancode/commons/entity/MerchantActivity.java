/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.entity;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "CHANNEL_NO"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "AGENT_NO"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "MERCHANT_NO"),
	@SelectColumnMapping(property = "activityId", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "ACTIVITY_ID"),
	@SelectColumnMapping(property = "activityBeginTime", type = Date.class, table = "t_merchant_activity", tableAlias = "A", column = "ACTIVITY_BEGIN_TIME"),
	@SelectColumnMapping(property = "activityEndTime", type = Date.class, table = "t_merchant_activity", tableAlias = "A", column = "ACTIVITY_END_TIME"),
	@SelectColumnMapping(property = "activityType", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "ACTIVITY_TYPE"),
	@SelectColumnMapping(property = "activityContent", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "ACTIVITY_CONTENT"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "STATUS"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "RECORD_STATUS"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_merchant_activity", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "updateTime", type = Date.class, table = "t_merchant_activity", tableAlias = "A", column = "UPDATE_TIME"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "OPERATOR"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "REMARK"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "TEMP_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "TEMP_2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "TEMP_3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_merchant_activity", tableAlias = "A", column = "TEMP_4")
})

@Generated("2017-02-14 10:45:02")
public class MerchantActivity implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String channelName;//渠道编号
	private String agentNo;//代理商编号
	private String agentName;//代理商编号
	private String merchantNo;//商户编号
	private String merchantName;//商户编号
	private String activityId;//活动ID
	private Date activityBeginTime;//活动开始时间
	private Date activityEndTime;//活动结束时间
	private String activityType;//活动类型 0 折扣 1 满减 3 随机
	private String activityContent;//活动描述
	private String status;//状态，1：正常，2：关闭
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4
	
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
	public void setActivityId(String value) {
		this.activityId = value;
	}
	
	public String getActivityId() {
		return this.activityId;
	}
	public void setActivityBeginTime(Date value) {
		this.activityBeginTime = value;
	}
	
	public Date getActivityBeginTime() {
		return this.activityBeginTime;
	}
	public void setActivityEndTime(Date value) {
		this.activityEndTime = value;
	}
	
	public Date getActivityEndTime() {
		return this.activityEndTime;
	}
	public void setActivityType(String value) {
		this.activityType = value;
	}
	
	public String getActivityType() {
		return this.activityType;
	}
	public void setActivityContent(String value) {
		this.activityContent = value;
	}
	
	public String getActivityContent() {
		return this.activityContent;
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

