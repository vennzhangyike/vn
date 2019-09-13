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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_channel_resource", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "menuName", type = java.lang.String.class, table = "t_channel_resource", tableAlias = "A", column = "menu_name"),
	@SelectColumnMapping(property = "parentId", type = java.lang.String.class, table = "t_channel_resource", tableAlias = "A", column = "parent_id"),
	@SelectColumnMapping(property = "sort", type = Integer.class, table = "t_channel_resource", tableAlias = "A", column = "sort"),
	@SelectColumnMapping(property = "icon", type = java.lang.String.class, table = "t_channel_resource", tableAlias = "A", column = "icon"),
	@SelectColumnMapping(property = "url", type = java.lang.String.class, table = "t_channel_resource", tableAlias = "A", column = "url"),
	@SelectColumnMapping(property = "level", type = Integer.class, table = "t_channel_resource", tableAlias = "A", column = "level"),
	@SelectColumnMapping(property = "status", type = java.lang.Boolean.class, table = "t_channel_resource", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_channel_resource", tableAlias = "A", column = "create_time")
})

@Generated("2016-10-15 15:07:27")
public class ChannelResource implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String menuName;//资源名称
	private String parentId;//资源父节点ID
	private Integer sort;//排序
	private String icon;//资源小图标
	private String url;//资源url
	private Integer level;//资源等级(一级菜单，二级菜单)
	private String status;//资源是否删除(0删除，1可用)
	private Date createTime;//创建时间
	private String hasPerssion;//是否有权限
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setMenuName(String value) {
		this.menuName = value;
	}
	
	public String getMenuName() {
		return this.menuName;
	}
	public void setParentId(String value) {
		this.parentId = value;
	}
	
	public String getParentId() {
		return this.parentId;
	}
	public void setSort(Integer value) {
		this.sort = value;
	}
	
	public Integer getSort() {
		return this.sort;
	}
	public void setIcon(String value) {
		this.icon = value;
	}
	
	public String getIcon() {
		return this.icon;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setLevel(Integer value) {
		this.level = value;
	}
	
	public Integer getLevel() {
		return this.level;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}

	public String getHasPerssion() {
		return hasPerssion;
	}

	public void setHasPerssion(String hasPerssion) {
		this.hasPerssion = hasPerssion;
	}

	
}

