/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-10-17 15:21:26")
public class ChangeLogCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
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

