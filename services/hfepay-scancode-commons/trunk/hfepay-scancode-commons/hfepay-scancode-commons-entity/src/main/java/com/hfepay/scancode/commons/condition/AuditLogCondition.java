/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-10-19 10:14:46")
public class AuditLogCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//id
	private String auditId;//审核记录ID
	private String auditType;//审核类型：0 商户入驻审核 (上级，平台)1：银行卡变更审核(平台)
	private String merchantNo;//商户编号
	private String merchantName;//商户名称
	private String auditStatus;//审核状态
	private String reason;//审核意见
	private String operatorOrganizations;//审核人机构
	private Date createTime;//操作时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setAuditId(String value) {
		this.auditId = value;
	}
	
	public String getAuditId() {
		return this.auditId;
	}
	public void setAuditType(String value) {
		this.auditType = value;
	}
	
	public String getAuditType() {
		return this.auditType;
	}
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
	}
	public void setMerchantName(String value) {
		this.merchantName = value;
	}
	
	public String getMerchantName() {
		return this.merchantName;
	}
	public void setAuditStatus(String value) {
		this.auditStatus = value;
	}
	
	public String getAuditStatus() {
		return this.auditStatus;
	}
	public void setReason(String value) {
		this.reason = value;
	}
	
	public String getReason() {
		return this.reason;
	}
	public void setOperatorOrganizations(String value) {
		this.operatorOrganizations = value;
	}
	
	public String getOperatorOrganizations() {
		return this.operatorOrganizations;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
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

