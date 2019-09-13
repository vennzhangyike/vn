/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import java.util.Date;
import java.math.BigDecimal;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "channelNo"),
	@SelectColumnMapping(property = "organNo", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "organNo"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "merchantNo"),
	@SelectColumnMapping(property = "mark_tradeNo", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "mark_tradeNo"),
	@SelectColumnMapping(property = "tradeNo", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "tradeNo"),
	@SelectColumnMapping(property = "payCode", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "payCode"),
	@SelectColumnMapping(property = "tradeAmt", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "tradeAmt"),
	@SelectColumnMapping(property = "discountAmt", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "C", column = "discountAmt"),
	@SelectColumnMapping(property = "paidAmt", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "paidAmt"),
	@SelectColumnMapping(property = "discountType", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "discountType"),
	@SelectColumnMapping(property = "discountStrategy", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "discountStrategy"),
	@SelectColumnMapping(property = "orderStatus", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "orderStatus"),
	@SelectColumnMapping(property = "cashierNo", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "cashierNo"),
	@SelectColumnMapping(property = "beginTime", type = java.util.Date.class, table = "t_marketing_pay_log", tableAlias = "A", column = "beginTime"),
	@SelectColumnMapping(property = "endTime", type = java.util.Date.class, table = "t_marketing_pay_log", tableAlias = "A", column = "endTime"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_marketing_pay_log", tableAlias = "A", column = "createTime"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_marketing_pay_log", tableAlias = "A", column = "updateTime"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "C", column = "operator"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "temp1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "temp2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "temp3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_marketing_pay_log", tableAlias = "A", column = "temp4"),
})

@Generated("2016-10-19 16:13:23")
public class MarketingPayLog implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635542L;
	
	private String id;//主键id
	private String channelNo;//渠道编号  
	private String organNo;//代理商编号
	private String merchantNo;//商户编号
	private String markTradeNo  ;//营销交易订单号 
	private String tradeNo  ;//交易订单号
	private String payCode  ;//平台支付通道代码
	private BigDecimal tradeAmt;//交易金额
	private BigDecimal discountAmt;//优惠金额
	private BigDecimal paidAmt;//实付金额
	private String discountType;//优惠类型
	private String discountStrategy;//优惠策略JSON
	private String orderStatus;//交易订单状态 00：等待付款、01：交易成功、03：交易失败
	private String cashierNo;//收银员编号
	private Date beginTime ;//交易开始时间
	private Date endTime ;//交易结束时间
	private Date createTime;//创建日期
	private Date updateTime;//修改日期
	private String operator;//操作人
	private String temp1;//
	private String temp2;//
	private String temp3;//
	private String temp4;//
	private String channelName;
	private String agentName;
	private String merchantName;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getOrganNo() {
		return organNo;
	}
	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getMarkTradeNo() {
		return markTradeNo;
	}
	public void setMarkTradeNo(String markTradeNo) {
		this.markTradeNo = markTradeNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public BigDecimal getTradeAmt() {
		return tradeAmt;
	}
	public void setTradeAmt(BigDecimal tradeAmt) {
		this.tradeAmt = tradeAmt;
	}
	public BigDecimal getDiscountAmt() {
		return discountAmt;
	}
	public void setDiscountAmt(BigDecimal discountAmt) {
		this.discountAmt = discountAmt;
	}
	public BigDecimal getPaidAmt() {
		return paidAmt;
	}
	public void setPaidAmt(BigDecimal paidAmt) {
		this.paidAmt = paidAmt;
	}
	public String getDiscountType() {
		return discountType;
	}
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	public String getDiscountStrategy() {
		return discountStrategy;
	}
	public void setDiscountStrategy(String discountStrategy) {
		this.discountStrategy = discountStrategy;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCashierNo() {
		return cashierNo;
	}
	public void setCashierNo(String cashierNo) {
		this.cashierNo = cashierNo;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getTemp3() {
		return temp3;
	}
	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}
	public String getTemp4() {
		return temp4;
	}
	public void setTemp4(String temp4) {
		this.temp4 = temp4;
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

