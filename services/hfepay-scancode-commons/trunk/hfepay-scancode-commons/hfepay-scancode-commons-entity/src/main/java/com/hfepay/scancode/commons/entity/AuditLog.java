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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "auditId", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "AUDIT_ID"),
	@SelectColumnMapping(property = "auditType", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "AUDIT_TYPE"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "MERCHANT_NO"),
	@SelectColumnMapping(property = "merchantName", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "C", column = "MERCHANT_NAME"),
	@SelectColumnMapping(property = "auditStatus", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "AUDIT_STATUS"),
	@SelectColumnMapping(property = "reason", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "REASON"),
	@SelectColumnMapping(property = "operatorOrganizations", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "OPERATOR_ORGANIZATIONS"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_audit_log", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "OPERATOR"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "REMARK"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "TEMP_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_audit_log", tableAlias = "A", column = "TEMP_2")
})

@Generated("2016-10-19 10:14:46")
public class AuditLog implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//id
	private String auditId;//审核记录ID
	private String auditType;//审核类型：0 商户入驻审核 (上级，平台)1：银行卡变更审核(平台)
	private String merchantNo;//商户编号
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

