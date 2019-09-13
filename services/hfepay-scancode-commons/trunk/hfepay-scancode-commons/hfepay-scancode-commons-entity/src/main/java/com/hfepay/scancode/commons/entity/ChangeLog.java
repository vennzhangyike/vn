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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "tradeNo", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "trade_no"),
	@SelectColumnMapping(property = "batchNo", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "batch_no"),
	@SelectColumnMapping(property = "transCode", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "trans_code"),
	@SelectColumnMapping(property = "transName", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "trans_name"),
	@SelectColumnMapping(property = "ownersNo", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "owners_no"),
	@SelectColumnMapping(property = "before", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "before"),
	@SelectColumnMapping(property = "after", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "after"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_change_log", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_change_log", tableAlias = "A", column = "temp_2"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "C", column = "merchant_no"),
	@SelectColumnMapping(property = "merchantName", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "C", column = "merchant_name")
})

@Generated("2016-10-17 15:21:26")
public class ChangeLog implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String tradeNo;//变更记录编号
	private String batchNo;//变更批次号
	private String transCode;//交易编码
	private String transName;//交易名称
	private String ownersNo;//交易属主编号（渠道编号or代理商编号or商户编号）
	private String before;//变更前内容(以JSON方式存放)
	private String after;//变更后内容(以JSON方式存放)
	private String status;//审核状态：0 初始 1审核通过 2审核失败
	private String operator;//操作人账号
	private Date createTime;//变更时间
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setTradeNo(String value) {
		this.tradeNo = value;
	}
	
	public String getTradeNo() {
		return this.tradeNo;
	}
	public void setBatchNo(String value) {
		this.batchNo = value;
	}
	
	public String getBatchNo() {
		return this.batchNo;
	}
	public void setTransCode(String value) {
		this.transCode = value;
	}
	
	public String getTransCode() {
		return this.transCode;
	}
	public void setTransName(String value) {
		this.transName = value;
	}
	
	public String getTransName() {
		return this.transName;
	}
	public void setOwnersNo(String value) {
		this.ownersNo = value;
	}
	
	public String getOwnersNo() {
		return this.ownersNo;
	}
	public void setBefore(String value) {
		this.before = value;
	}
	
	public String getBefore() {
		return this.before;
	}
	public void setAfter(String value) {
		this.after = value;
	}
	
	public String getAfter() {
		return this.after;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
		return this.operator;
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

	
}

