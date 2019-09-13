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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_message", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_message", tableAlias = "A", column = "CHANNEL_NO"),
	@SelectColumnMapping(property = "userType", type = java.lang.String.class, table = "t_message", tableAlias = "A", column = "USER_TYPE"),
	@SelectColumnMapping(property = "title", type = java.lang.String.class, table = "t_message", tableAlias = "A", column = "TITLE"),
	@SelectColumnMapping(property = "content", type = java.lang.String.class, table = "t_message", tableAlias = "A", column = "CONTENT"),
	@SelectColumnMapping(property = "messageType", type = java.lang.String.class, table = "t_message", tableAlias = "A", column = "MESSAGE_TYPE"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_message", tableAlias = "A", column = "STATUS"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_message", tableAlias = "A", column = "OPERATOR"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_message", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "updateTime", type = Date.class, table = "t_message", tableAlias = "A", column = "UPDATE_TIME"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_message", tableAlias = "A", column = "REMARK")
})

@Generated("2016-11-26 17:48:59")
public class Message implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String userType;//用户类型：0：系统，1：商户，2：代理商，3：渠道，9：不限
	private String title;//标题
	private String content;//内容
	private String messageType;//类型：0普通消息、1系统消息、2广告消息
	private String status;//状态，1：正常，2：无效
	private String operator;//操作人账号
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String remark;//备注

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setChannelNo(String value) {
		this.channelNo = value;
	}
	
	public String getChannelNo() {
		return this.channelNo;
	}
	public void setUserType(String value) {
		this.userType = value;
	}
	
	public String getUserType() {
		return this.userType;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setMessageType(String value) {
		this.messageType = value;
	}
	
	public String getMessageType() {
		return this.messageType;
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

}

