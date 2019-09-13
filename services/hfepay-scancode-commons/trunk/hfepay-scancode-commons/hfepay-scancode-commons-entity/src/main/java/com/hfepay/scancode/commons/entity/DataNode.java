/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_data_node", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "parentNode", type = java.lang.String.class, table = "t_data_node", tableAlias = "A", column = "parent_node"),
	@SelectColumnMapping(property = "currentNode", type = java.lang.String.class, table = "t_data_node", tableAlias = "A", column = "current_node"),
	@SelectColumnMapping(property = "currentNodeLevel", type = java.lang.String.class, table = "t_data_node", tableAlias = "A", column = "current_node_level"),
	@SelectColumnMapping(property = "identityFlag", type = java.lang.String.class, table = "t_data_node", tableAlias = "A", column = "identity_flag"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_data_node", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_data_node", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_data_node", tableAlias = "A", column = "update_time")
})

@Generated("2016-10-18 13:53:00")
public class DataNode /*implements IdEntity<String>*/ {
	
	/*private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String parentNode;//父节点
	private String currentNode;//当前节点
	private String currentNodeLevel;//节点级别
	private String identityFlag;//1:渠道2代理商3商户
	private String operator;//子节点级别
	private Date createTime;//创建时间
	private Date updateTime;//创建时间

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setParentNode(String value) {
		this.parentNode = value;
	}
	
	public String getParentNode() {
		return this.parentNode;
	}
	
	public String getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(String currentNode) {
		this.currentNode = currentNode;
	}

	public String getCurrentNodeLevel() {
		return currentNodeLevel;
	}

	public void setCurrentNodeLevel(String currentNodeLevel) {
		this.currentNodeLevel = currentNodeLevel;
	}

	public void setIdentityFlag(String value) {
		this.identityFlag = value;
	}
	
	public String getIdentityFlag() {
		return this.identityFlag;
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
*/
	
}

