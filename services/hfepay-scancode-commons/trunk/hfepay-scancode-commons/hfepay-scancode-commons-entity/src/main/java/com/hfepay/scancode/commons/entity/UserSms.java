/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_user_sms", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_user_sms", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_user_sms", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_user_sms", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "phone", type = java.lang.String.class, table = "t_user_sms", tableAlias = "A", column = "phone"),
	@SelectColumnMapping(property = "content", type = java.lang.String.class, table = "t_user_sms", tableAlias = "A", column = "content"),
	@SelectColumnMapping(property = "sendResult", type = java.lang.String.class, table = "t_user_sms", tableAlias = "A", column = "send_result"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_user_sms", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_user_sms", tableAlias = "A", column = "remark")
})

@Generated("2016-11-28 17:28:09")
public class UserSms implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String phone;//手机号
	private String content;//短信内容
	private String sendResult;//发送结果
	private Date createTime;//发送时间
	private String remark;//备注
	
	private String agentName;//代理商名称
	private String merchantName;//商户名称
	private String channelName;

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
	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setSendResult(String value) {
		this.sendResult = value;
	}
	
	public String getSendResult() {
		return this.sendResult;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
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

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	
}

