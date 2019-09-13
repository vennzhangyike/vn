/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-10-15 15:07:27")
public class ChannelResourceCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String menuName;//资源名称
	private String parentId;//资源父节点ID
	private Integer sort;//排序
	private String icon;//资源小图标
	private String url;//资源url
	private Integer level;//资源等级(一级菜单，二级菜单)
	private String status;//资源是否删除(0删除，1可用)
	private Date createTime;//创建时间

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
}

