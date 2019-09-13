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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_order_pay", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_order_pay", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "cashierNo", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "cashier_no"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "qrCode", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "qr_code"),
	@SelectColumnMapping(property = "isCashier", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "is_cashier"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "record_status"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = Date.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_merchant_cashier_qr", tableAlias = "A", column = "temp_2")
})

@Generated("2016-11-10 16:13:31")
public class MerchantCashierQr implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String cashierNo;//收银员编号
	private String merchantNo;//商户编号
	private String merchantName;//商户名称
	private String qrCode;//二维码编号
	private String isCashier;//是否开启收银：1 停止收银 2开始收银
	private String status;//是否有效：1：有效，2：无效
	private String recordStatus;//记录状态
	private Date createTime;//绑定时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setCashierNo(String value) {
		this.cashierNo = value;
	}
	
	public String getCashierNo() {
		return this.cashierNo;
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
	public void setIsCashier(String value) {
		this.isCashier = value;
	}
	
	public String getIsCashier() {
		return this.isCashier;
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

}

