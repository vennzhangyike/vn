/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.api.entity;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;
import com.hfepay.commons.base.annotation.Generated;

import java.math.BigDecimal;
import java.util.Date;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_webank_order", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "merchantId", type = java.lang.String.class, table = "t_webank_order", tableAlias = "A", column = "merchant_id"),
	@SelectColumnMapping(property = "tradeNo", type = java.lang.String.class, table = "t_webank_order", tableAlias = "A", column = "trade_no"),
	@SelectColumnMapping(property = "tradeAmt", type = Double.class, table = "t_webank_order", tableAlias = "A", column = "trade_amt"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_webank_order", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_webank_order", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "tradeStatus", type = java.lang.String.class, table = "t_webank_order", tableAlias = "A", column = "trade_status")

})

@Generated("2017-03-13 17:55:20")
public class WebankOrder implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String merchantId;//商户编号
	private String tradeNo;//订单编号
	private BigDecimal tradeAmt;//交易金额
	private Date createTime;//创建时间
	private String operator;//操作人账号
	private String tradeStatus;//交易状态

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

	public BigDecimal getTradeAmt() {
		return tradeAmt;
	}

	public void setTradeAmt(BigDecimal tradeAmt) {
		this.tradeAmt = tradeAmt;
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

