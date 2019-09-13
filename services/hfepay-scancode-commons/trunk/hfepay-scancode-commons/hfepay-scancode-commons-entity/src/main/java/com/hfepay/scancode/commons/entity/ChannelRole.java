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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_channel_role", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "roleName", type = java.lang.String.class, table = "t_channel_role", tableAlias = "A", column = "role_name"),
	@SelectColumnMapping(property = "roleLevel", type = java.lang.String.class, table = "t_channel_role", tableAlias = "A", column = "role_level"),
	@SelectColumnMapping(property = "description", type = java.lang.String.class, table = "t_channel_role", tableAlias = "A", column = "description"),
	@SelectColumnMapping(property = "channelCode", type = java.lang.String.class, table = "t_channel_role", tableAlias = "A", column = "channel_code"),
	@SelectColumnMapping(property = "channelName", type = java.lang.String.class, table = "t_channel_role", tableAlias = "E", column = "channel_name"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_channel_role", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "roleLevel2", type = java.lang.String.class, table = "t_channel_role", tableAlias = "C", column = "role_level")//ChannelAdminMapper.xml中使用
})

@Generated("2016-10-15 14:50:14")
public class ChannelRole implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String roleName;//角色名称
	private String roleLevel;//角色类型
	private String description;//角色描述
	private String channelCode;//渠道编号
	private Date createTime;//创建时间

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setRoleName(String value) {
		this.roleName = value;
	}
	
	public String getRoleName() {
		return this.roleName;
	}
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setChannelCode(String value) {
		this.channelCode = value;
	}
	
	public String getChannelCode() {
		return this.channelCode;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}

	public String getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

	
}

