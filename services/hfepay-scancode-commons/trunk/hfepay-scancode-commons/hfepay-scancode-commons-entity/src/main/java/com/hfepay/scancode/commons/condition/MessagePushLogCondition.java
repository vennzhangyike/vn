/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2017-02-21 19:46:12")
public class MessagePushLogCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
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
	private String beginTimeStr;//开始时间
	private String endTimeStr;//结束时间

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

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
}

