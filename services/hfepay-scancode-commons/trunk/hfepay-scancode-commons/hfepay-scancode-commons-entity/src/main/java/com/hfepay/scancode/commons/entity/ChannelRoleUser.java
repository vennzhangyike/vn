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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_channel_role_user", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "roleId", type = java.lang.String.class, table = "t_channel_role_user", tableAlias = "A", column = "role_id"),
	@SelectColumnMapping(property = "accountId", type = java.lang.String.class, table = "t_channel_role_user", tableAlias = "A", column = "account_id"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_channel_role_user", tableAlias = "A", column = "create_time")
})

@Generated("2016-10-15 14:50:15")
public class ChannelRoleUser implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String roleId;//角色ID
	private String accountId;//用户ID
	private Date createTime;//创建时间

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setRoleId(String value) {
		this.roleId = value;
	}
	
	public String getRoleId() {
		return this.roleId;
	}
	public void setAccountId(String value) {
		this.accountId = value;
	}
	
	public String getAccountId() {
		return this.accountId;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}

	
}

