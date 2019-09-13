/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.entity;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_node_relation", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "currentNode", type = java.lang.String.class, table = "t_node_relation", tableAlias = "A", column = "current_node"),
	@SelectColumnMapping(property = "parentNode", type = java.lang.String.class, table = "t_node_relation", tableAlias = "A", column = "parent_node"),
	@SelectColumnMapping(property = "currentNodeLevel", type = java.lang.String.class, table = "t_node_relation", tableAlias = "A", column = "current_node_level"),
	@SelectColumnMapping(property = "identityFlag", type = java.lang.String.class, table = "t_node_relation", tableAlias = "A", column = "identity_flag"),
	@SelectColumnMapping(property = "nodeSeq", type = java.lang.String.class, table = "t_node_relation", tableAlias = "A", column = "node_seq"),
	@SelectColumnMapping(property = "nextNodeSeq", type = java.lang.String.class, table = "t_node_relation", tableAlias = "A", column = "next_node_seq"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_node_relation", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_node_relation", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = Date.class, table = "t_node_relation", tableAlias = "A", column = "update_time")
})

@Generated("2017-01-22 15:16:03")
public class NodeRelation implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String currentNode;//当前节点
	private String parentNode;//根节点
	private String currentNodeLevel;//当前节点级别-1平台
	private String identityFlag;//1:渠道2代理商3商户0平台
	private String nodeSeq;//当前节点的标示符
	private String nextNodeSeq;//下一个子节点标示符
	private String operator;//操作人
	private Date createTime;//创建时间
	private Date updateTime;//修改时间

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setCurrentNode(String value) {
		this.currentNode = value;
	}
	
	public String getCurrentNode() {
		return this.currentNode;
	}
	public void setParentNode(String value) {
		this.parentNode = value;
	}
	
	public String getParentNode() {
		return this.parentNode;
	}
	public void setCurrentNodeLevel(String value) {
		this.currentNodeLevel = value;
	}
	
	public String getCurrentNodeLevel() {
		return this.currentNodeLevel;
	}
	public void setIdentityFlag(String value) {
		this.identityFlag = value;
	}
	
	public String getIdentityFlag() {
		return this.identityFlag;
	}
	public void setNodeSeq(String value) {
		this.nodeSeq = value;
	}
	
	public String getNodeSeq() {
		return this.nodeSeq;
	}
	public void setNextNodeSeq(String value) {
		this.nextNodeSeq = value;
	}
	
	public String getNextNodeSeq() {
		return this.nextNodeSeq;
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

	
}

