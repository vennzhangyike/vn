/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-11-26 17:49:05")
public class UserMessageCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
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

