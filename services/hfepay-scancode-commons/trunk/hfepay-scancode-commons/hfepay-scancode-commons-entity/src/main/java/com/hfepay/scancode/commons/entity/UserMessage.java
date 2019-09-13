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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_user_message", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "messageId", type = java.lang.String.class, table = "t_user_message", tableAlias = "A", column = "MESSAGE_ID"),
	@SelectColumnMapping(property = "userNo", type = java.lang.String.class, table = "t_user_message", tableAlias = "A", column = "USER_NO"),
	@SelectColumnMapping(property = "readStatus", type = java.lang.String.class, table = "t_user_message", tableAlias = "A", column = "READ_STATUS"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_user_message", tableAlias = "A", column = "RECORD_STATUS"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_user_message", tableAlias = "A", column = "OPERATOR"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_user_message", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "updateTime", type = Date.class, table = "t_user_message", tableAlias = "A", column = "UPDATE_TIME"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_user_message", tableAlias = "A", column = "REMARK"),
	@SelectColumnMapping(property = "userType", type = java.lang.String.class, table = "t_message", tableAlias = "B", column = "USER_TYPE")
})

@Generated("2016-11-26 17:49:05")
public class UserMessage implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String messageId;//站内信ID
	private String userNo;//用户账号
	private String readStatus;//是否已读 1未读 2已读
	private String recordStatus;//Y有效N无效
	private String operator;//操作人账号
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String remark;//备注
	private String userType;//用户类型

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setMessageId(String value) {
		this.messageId = value;
	}
	
	public String getMessageId() {
		return this.messageId;
	}
	public void setUserNo(String value) {
		this.userNo = value;
	}
	
	public String getUserNo() {
		return this.userNo;
	}
	public void setReadStatus(String value) {
		this.readStatus = value;
	}
	
	public String getReadStatus() {
		return this.readStatus;
	}
	
	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
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
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}

