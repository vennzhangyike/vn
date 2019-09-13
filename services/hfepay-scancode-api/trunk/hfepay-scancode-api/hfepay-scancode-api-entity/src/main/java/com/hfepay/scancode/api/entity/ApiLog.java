/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.entity;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;
import com.hfepay.commons.base.annotation.Generated;

import java.io.Serializable;
import java.util.Date;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "payCode", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "pay_code"),
	@SelectColumnMapping(property = "tradeNo", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "trade_no"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "requestParams", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "request_params"),
	@SelectColumnMapping(property = "responseParams", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "response_params"),
	@SelectColumnMapping(property = "notifyParams", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "notify_params"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_api_log", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_api_log", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "notifyTime", type = java.util.Date.class, table = "t_api_log", tableAlias = "A", column = "notify_time"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_api_log", tableAlias = "A", column = "temp_2")
})

@Generated("2016-10-28 14:51:39")
public class ApiLog implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String payCode;//第三方支付通道代码
	private String tradeNo;//交易订单编号
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String requestParams;//请求参数(以JSON方式存放)
	private String responseParams;//返回参数(以JSON方式存放)
	private String notifyParams;//回调参数(以JSON方式存放)
	private Date createTime;//创建时间
	private Date updateTime;//更新时间
	private Date notifyTime;//回调时间
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setPayCode(String value) {
		this.payCode = value;
	}
	
	public String getPayCode() {
		return this.payCode;
	}
	public void setTradeNo(String value) {
		this.tradeNo = value;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
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
	public void setRequestParams(String value) {
		this.requestParams = value;
	}
	
	public String getRequestParams() {
		return this.requestParams;
	}
	public void setResponseParams(String value) {
		this.responseParams = value;
	}
	
	public String getResponseParams() {
		return this.responseParams;
	}
	public void setNotifyParams(String value) {
		this.notifyParams = value;
	}
	
	public String getNotifyParams() {
		return this.notifyParams;
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
	public void setNotifyTime(Date value) {
		this.notifyTime = value;
	}
	
	public Date getNotifyTime() {
		return this.notifyTime;
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

