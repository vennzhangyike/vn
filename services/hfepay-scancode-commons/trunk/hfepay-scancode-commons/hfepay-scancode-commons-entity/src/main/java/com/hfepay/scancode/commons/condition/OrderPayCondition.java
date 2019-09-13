/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-10-17 11:37:40")
public class OrderPayCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键
	private String payNo;//支付订单编号
	private String tradeNo;//交易订单编号
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String qrCode;//支付二维码编号
	private String payCode;//平台支付通道代码
	private String tradeType;//交易订单类型 01:提现、02:支付
	private BigDecimal orderAmt;//订单金额
	private String productName;//商品名称
	private String productDesc;//商品描述
	private String buyerId;//支付的账号id
	private String buyerAccount;//支付账号
	private BigDecimal totalAmount;//支付额度（用户支付的款+积分优惠）
	private BigDecimal buyerPayAmount;//用户支付的款
	private BigDecimal pointAmount;//积分优惠
	private String rateDetail;
	private String businessType;//业务类型 0：基础商品类业务、1：普通消费类业务
	private String payType;//支付方式：0000：钱包支付（默认）、0001：支付宝支付、0002：微信支付、0003：银联支付
	private String isMainPay;//是否是主支付订单 0：否  1：是
	private String accountTime;//账务账期，YYYYMMDD
	private String errorCode;//异常编码
	private String errorMsg;//异常信息
	private String payTradeNo;//支付通道流水号
	private String payOrderType;//支付订单类型 0：业务支付订单、1：手续费支付订单
	private String notifyUrl;//后台异步通知url
	private String returnUrl;//页面回调通知url
	private String refundStatus;//退款状态（Y 已退款，N 未退款）
	private String accountType;
	private String payStatus;//支付状态 00：支付成功、01：支付失败、02：渠道处理中、03：记账处理中
	private String batchId;//批量批次号
	private String optCode;//操作编码
	private String recordStatus;//记录状态（Y有效，N无效）
	private Date beginTime;//交易开始时间
	private Date endTime;//交易结束时间
	private Date updateTime;//回调时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4
	private String cashierNo;
	private BigDecimal merchantCost;
	private String storeNo;
	private String queryStartTime;//查询条件
	
	private String queryEndTime;
	private BigDecimal tradeAmt;//订单原交易金额	
	private BigDecimal discountAmt;//打折金额
	private String discountStrategy;//打折策略

	private String tradeSource;//交易来源0网页1接口
	private String extraTradeNo;//外部订单编号,下游上送的流水号
	private String channelTradeNo;//记录支付宝,微信的订单编号
	private String extraCallBack;//外部接口上送的回调地址
	
	public String getTradeSource() {
		return tradeSource;
	}

	public void setTradeSource(String tradeSource) {
		this.tradeSource = tradeSource;
	}

	public String getExtraTradeNo() {
		return extraTradeNo;
	}

	public void setExtraTradeNo(String extraTradeNo) {
		this.extraTradeNo = extraTradeNo;
	}

	public String getChannelTradeNo() {
		return channelTradeNo;
	}

	public void setChannelTradeNo(String channelTradeNo) {
		this.channelTradeNo = channelTradeNo;
	}

	public String getExtraCallBack() {
		return extraCallBack;
	}

	public void setExtraCallBack(String extraCallBack) {
		this.extraCallBack = extraCallBack;
	}
	
	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getRateDetail() {
		return rateDetail;
	}

	public void setRateDetail(String rateDetail) {
		this.rateDetail = rateDetail;
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

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setPayNo(String value) {
		this.payNo = value;
	}
	
	public String getPayNo() {
		return this.payNo;
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
	public void setQrCode(String value) {
		this.qrCode = value;
	}
	
	public String getQrCode() {
		return this.qrCode;
	}
	public void setPayCode(String value) {
		this.payCode = value;
	}
	
	public String getPayCode() {
		return this.payCode;
	}
	public void setTradeType(String value) {
		this.tradeType = value;
	}
	
	public String getTradeType() {
		return this.tradeType;
	}
	public void setProductName(String value) {
		this.productName = value;
	}
	
	public String getProductName() {
		return this.productName;
	}
	public void setProductDesc(String value) {
		this.productDesc = value;
	}
	
	public String getProductDesc() {
		return this.productDesc;
	}
	public void setBuyerId(String value) {
		this.buyerId = value;
	}
	
	public String getBuyerId() {
		return this.buyerId;
	}
	public void setBuyerAccount(String value) {
		this.buyerAccount = value;
	}
	
	public String getBuyerAccount() {
		return this.buyerAccount;
	}
	
	public BigDecimal getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getBuyerPayAmount() {
		return buyerPayAmount;
	}

	public void setBuyerPayAmount(BigDecimal buyerPayAmount) {
		this.buyerPayAmount = buyerPayAmount;
	}

	public BigDecimal getPointAmount() {
		return pointAmount;
	}

	public void setPointAmount(BigDecimal pointAmount) {
		this.pointAmount = pointAmount;
	}

	public void setBusinessType(String value) {
		this.businessType = value;
	}
	
	public String getBusinessType() {
		return this.businessType;
	}
	public void setPayType(String value) {
		this.payType = value;
	}
	
	public String getPayType() {
		return this.payType;
	}
	public void setIsMainPay(String value) {
		this.isMainPay = value;
	}
	
	public String getIsMainPay() {
		return this.isMainPay;
	}
	public void setAccountTime(String value) {
		this.accountTime = value;
	}
	
	public String getAccountTime() {
		return this.accountTime;
	}
	public void setErrorCode(String value) {
		this.errorCode = value;
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}
	public void setErrorMsg(String value) {
		this.errorMsg = value;
	}
	
	public String getErrorMsg() {
		return this.errorMsg;
	}
	public void setPayTradeNo(String value) {
		this.payTradeNo = value;
	}
	
	public String getPayTradeNo() {
		return this.payTradeNo;
	}
	public void setPayOrderType(String value) {
		this.payOrderType = value;
	}
	
	public String getPayOrderType() {
		return this.payOrderType;
	}
	public void setNotifyUrl(String value) {
		this.notifyUrl = value;
	}
	
	public String getNotifyUrl() {
		return this.notifyUrl;
	}
	public void setReturnUrl(String value) {
		this.returnUrl = value;
	}
	
	public String getReturnUrl() {
		return this.returnUrl;
	}
	public void setRefundStatus(String value) {
		this.refundStatus = value;
	}
	
	public String getRefundStatus() {
		return this.refundStatus;
	}
	public void setPayStatus(String value) {
		this.payStatus = value;
	}
	
	public String getPayStatus() {
		return this.payStatus;
	}
	public void setBatchId(String value) {
		this.batchId = value;
	}
	
	public String getBatchId() {
		return this.batchId;
	}
	public void setOptCode(String value) {
		this.optCode = value;
	}
	
	public String getOptCode() {
		return this.optCode;
	}
	public void setRecordStatus(String value) {
		this.recordStatus = value;
	}
	
	public String getRecordStatus() {
		return this.recordStatus;
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

	public String getQueryStartTime() {
		return queryStartTime;
	}

	public void setQueryStartTime(String queryStartTime) {
		this.queryStartTime = queryStartTime;
	}

	public String getQueryEndTime() {
		return queryEndTime;
	}

	public void setQueryEndTime(String queryEndTime) {
		this.queryEndTime = queryEndTime;
	}

	public String getCashierNo() {
		return cashierNo;
	}

	public void setCashierNo(String cashierNo) {
		this.cashierNo = cashierNo;
	}

	public BigDecimal getMerchantCost() {
		return merchantCost;
	}

	public void setMerchantCost(BigDecimal merchantCost) {
		this.merchantCost = merchantCost;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
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

	public String getDiscountStrategy() {
		return discountStrategy;
	}

	public void setDiscountStrategy(String discountStrategy) {
		this.discountStrategy = discountStrategy;
	}
	
	
}

