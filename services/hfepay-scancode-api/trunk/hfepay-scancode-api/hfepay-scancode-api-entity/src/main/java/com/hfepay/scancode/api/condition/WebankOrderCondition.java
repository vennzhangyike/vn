/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.api.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

import java.math.BigDecimal;
import java.util.Date;

@Generated("2017-03-13 17:55:20")
public class WebankOrderCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String merchantId;//商户编号
	private String tradeNo;//订单编号
	private BigDecimal tradeAmt;//交易金额
	private Date createTime;//创建时间
	private String operator;//操作人账号
	private String tradeStatus;//支付状态

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setMerchantId(String value) {
		this.merchantId = value;
	}
	
	public String getMerchantId() {
		return this.merchantId;
	}
	public void setTradeNo(String value) {
		this.tradeNo = value;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
	public void setTradeAmt(BigDecimal value) {
		this.tradeAmt = value;
	}
	
	public BigDecimal getTradeAmt() {
		return this.tradeAmt;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	
}

