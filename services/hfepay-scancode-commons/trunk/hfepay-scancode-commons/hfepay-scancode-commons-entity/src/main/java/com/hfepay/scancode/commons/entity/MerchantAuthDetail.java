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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "authInterface", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "auth_interface"),
	@SelectColumnMapping(property = "authParams", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "auth_params"),
	@SelectColumnMapping(property = "returnAuthCode", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "return_auth_code"),
	@SelectColumnMapping(property = "returnAuthMsg", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "return_auth_msg"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_merchant_auth_detail", tableAlias = "A", column = "temp_2")
})

@Generated("2016-11-21 17:19:45")
public class MerchantAuthDetail implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String authInterface;//认证接口
	private String authParams;//认证参数
	private String returnAuthCode;//认证返回码
	private String returnAuthMsg;//认证描述
	private Date createTime;//认证时间
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
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
	public void setAuthInterface(String value) {
		this.authInterface = value;
	}
	
	public String getAuthInterface() {
		return this.authInterface;
	}
	public void setAuthParams(String value) {
		this.authParams = value;
	}
	
	public String getAuthParams() {
		return this.authParams;
	}
	public void setReturnAuthCode(String value) {
		this.returnAuthCode = value;
	}
	
	public String getReturnAuthCode() {
		return this.returnAuthCode;
	}
	public void setReturnAuthMsg(String value) {
		this.returnAuthMsg = value;
	}
	
	public String getReturnAuthMsg() {
		return this.returnAuthMsg;
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

