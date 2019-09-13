/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2017-02-16 18:11:14")
public class MessagePushRuleCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
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
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private Date firstPushBeginDate;//首次推送开始日期
	private Date firstPushEndDate;//首次推送结束日期
	private Date nextPushBeginDate;//下次推送开始日期
	private Date nextPushEndDate;//下次推送结束日期

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

	public Date getNextPushBeginDate() {
		return nextPushBeginDate;
	}

	public void setNextPushBeginDate(Date nextPushBeginDate) {
		this.nextPushBeginDate = nextPushBeginDate;
	}

	public Date getNextPushEndDate() {
		return nextPushEndDate;
	}

	public void setNextPushEndDate(Date nextPushEndDate) {
		this.nextPushEndDate = nextPushEndDate;
	}

	public Date getFirstPushBeginDate() {
		return firstPushBeginDate;
	}

	public void setFirstPushBeginDate(Date firstPushBeginDate) {
		this.firstPushBeginDate = firstPushBeginDate;
	}

	public Date getFirstPushEndDate() {
		return firstPushEndDate;
	}

	public void setFirstPushEndDate(Date firstPushEndDate) {
		this.firstPushEndDate = firstPushEndDate;
	}
}

