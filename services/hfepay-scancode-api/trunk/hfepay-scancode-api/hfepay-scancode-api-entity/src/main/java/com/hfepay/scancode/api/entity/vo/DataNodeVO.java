/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.entity.vo;

import java.io.Serializable;

public class DataNodeVO implements Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String parentOrganNo;//父节点
	private String organNo;//当前节点
	private String organLevel;//节点级别
	private String identityFlag;//1:渠道2代理商3商户
	private String organName;//节点名称
	public String getParentOrganNo() {
		return parentOrganNo;
	}
	public void setParentOrganNo(String parentOrganNo) {
		this.parentOrganNo = parentOrganNo;
	}
	public String getOrganNo() {
		return organNo;
	}
	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}
	public String getOrganLevel() {
		return organLevel;
	}
	public void setOrganLevel(String organLevel) {
		this.organLevel = organLevel;
	}
	public String getIdentityFlag() {
		return identityFlag;
	}
	public void setIdentityFlag(String identityFlag) {
		this.identityFlag = identityFlag;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	
}

