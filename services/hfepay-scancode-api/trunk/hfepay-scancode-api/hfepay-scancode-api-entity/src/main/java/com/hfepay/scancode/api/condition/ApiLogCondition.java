/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-10-28 14:51:39")
public class ApiLogCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
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
	private String queryStartDate;//查询的时间

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

	public String getQueryStartDate() {
		return queryStartDate;
	}

	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}



	
}

