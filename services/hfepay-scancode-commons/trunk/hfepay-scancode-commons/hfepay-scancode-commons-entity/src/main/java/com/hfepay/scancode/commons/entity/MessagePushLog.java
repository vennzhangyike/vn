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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "messageType", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "message_type"),
	@SelectColumnMapping(property = "messageContent", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "message_content"),
	@SelectColumnMapping(property = "pushRule", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "push_rule"),
	@SelectColumnMapping(property = "pushTime", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "push_time"),
	@SelectColumnMapping(property = "pushWay", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "push_way"),
	@SelectColumnMapping(property = "pushDate", type = java.util.Date.class, table = "t_message_push_log", tableAlias = "A", column = "push_date"),
	@SelectColumnMapping(property = "pushResult", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "push_result"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_message_push_log", tableAlias = "A", column = "temp_2")
})

@Generated("2017-02-21 19:46:12")
public class MessagePushLog implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String messageType;//消息类型（0商户资料待完善 1销售业绩）
	private String messageContent;//消息内容
	private String pushRule;//推送规则
	private String pushTime;//推送频率
	private String pushWay;//推送方式（0微信公众号 1短信）
	private Date pushDate;//推送时间
	private String pushResult;//推送结果
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String channelName;
	private String agentName;
	private String merchantName;

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
	public void setPushWay(String value) {
		this.pushWay = value;
	}
	
	public String getPushWay() {
		return this.pushWay;
	}
	public void setPushDate(Date value) {
		this.pushDate = value;
	}
	
	public Date getPushDate() {
		return this.pushDate;
	}
	public void setPushResult(String value) {
		this.pushResult = value;
	}
	
	public String getPushResult() {
		return this.pushResult;
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

	
}

