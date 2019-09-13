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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "messageType", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "message_type"),
	@SelectColumnMapping(property = "messageContent", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "message_content"),
	@SelectColumnMapping(property = "pushRule", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "push_rule"),
	@SelectColumnMapping(property = "pushTime", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "push_time"),
	@SelectColumnMapping(property = "pushAuxiliaryRule", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "push_auxiliary_rule"),
	@SelectColumnMapping(property = "pushWay", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "push_way"),
	@SelectColumnMapping(property = "firstPushDate", type = java.util.Date.class, table = "t_message_push_rule", tableAlias = "A", column = "first_push_date"),
	@SelectColumnMapping(property = "nextPushDate", type = java.util.Date.class, table = "t_message_push_rule", tableAlias = "A", column = "next_push_date"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "record_status"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_message_push_rule", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_message_push_rule", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_message_push_rule", tableAlias = "A", column = "temp_2")
})

@Generated("2017-02-16 18:11:14")
public class MessagePushRule implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String messageType;//消息类型（0商户资料待完善 1销售业绩）
	private String messageContent;//消息内容
	private String pushRule;//推送规则
	private String pushTime;//推送频率
	private String pushAuxiliaryRule;//推送辅助规则
	private String pushWay;//推送方式（0微信公众号 1短信）
	private Date firstPushDate;//首次推送日期
	private Date nextPushDate;//下次推送日期
	private String status;//状态，1：正常，2：关闭
	private String recordStatus;//记录状态，Y:正常，N:删除
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String channelName;//渠道名称

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
	public void setMessageType(String value) {
		this.messageType = value;
	}
	
	public String getMessageType() {
		return this.messageType;
	}
	public void setMessageContent(String value) {
		this.messageContent = value;
	}
	
	public String getMessageContent() {
		return this.messageContent;
	}
	public void setPushRule(String value) {
		this.pushRule = value;
	}
	
	public String getPushRule() {
		return this.pushRule;
	}
	public void setPushTime(String value) {
		this.pushTime = value;
	}
	
	public String getPushTime() {
		return this.pushTime;
	}
	public void setPushAuxiliaryRule(String value) {
		this.pushAuxiliaryRule = value;
	}
	
	public String getPushAuxiliaryRule() {
		return this.pushAuxiliaryRule;
	}
	public void setPushWay(String value) {
		this.pushWay = value;
	}
	
	public String getPushWay() {
		return this.pushWay;
	}
	public void setFirstPushDate(Date value) {
		this.firstPushDate = value;
	}
	
	public Date getFirstPushDate() {
		return this.firstPushDate;
	}
	public void setNextPushDate(Date value) {
		this.nextPushDate = value;
	}
	
	public Date getNextPushDate() {
		return this.nextPushDate;
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

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	
}

