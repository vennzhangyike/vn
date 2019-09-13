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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "assignment", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "assignment"),
	@SelectColumnMapping(property = "assignmentName", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "assignment_name"),
	@SelectColumnMapping(property = "recipients", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "recipients"),
	@SelectColumnMapping(property = "recipientsName", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "recipients_name"),
	@SelectColumnMapping(property = "qrNumber", type = java.lang.Integer.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "qr_number"),
	@SelectColumnMapping(property = "assignedType", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "assigned_type"),
	@SelectColumnMapping(property = "reason", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "reason"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "temp_2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "temp_3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_qrcode_assigned_log", tableAlias = "A", column = "temp_4")
})

@Generated("2016-10-20 09:40:09")
public class QrcodeAssignedLog implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String assignment;//分配人
	private String assignmentName;//分配机构名称
	private String recipients;//接收人
	private String recipientsName;//接收机构名称
	private Integer qrNumber;//分配二维码数
	private String assignedType;//分配类型：0 主动分配 1 申请审核后分配
	private String reason;//分配备注
	private Date createTime;//分配时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setAssignment(String value) {
		this.assignment = value;
	}
	
	public String getAssignment() {
		return this.assignment;
	}
	public void setAssignmentName(String value) {
		this.assignmentName = value;
	}
	
	public String getAssignmentName() {
		return this.assignmentName;
	}
	public void setRecipients(String value) {
		this.recipients = value;
	}
	
	public String getRecipients() {
		return this.recipients;
	}
	public void setRecipientsName(String value) {
		this.recipientsName = value;
	}
	
	public String getRecipientsName() {
		return this.recipientsName;
	}
	public void setQrNumber(Integer value) {
		this.qrNumber = value;
	}
	
	public Integer getQrNumber() {
		return this.qrNumber;
	}
	public void setAssignedType(String value) {
		this.assignedType = value;
	}
	
	public String getAssignedType() {
		return this.assignedType;
	}
	public void setReason(String value) {
		this.reason = value;
	}
	
	public String getReason() {
		return this.reason;
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

	
}

