/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-10-18 13:53:00")
public class DataNodeCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String parentNode;//父节点
	private String currentNode;//当前节点
	private String currentNodeLevel;//当前节点级别
	private String identityFlag;//1:渠道2代理商3商户
	private String operator;//子节点级别
	private Date createTime;//创建时间

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
}

