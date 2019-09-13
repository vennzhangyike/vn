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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_account_user_ralation", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "accountId", type = java.lang.String.class, table = "t_account_user_ralation", tableAlias = "A", column = "account_id"),
	@SelectColumnMapping(property = "userId", type = java.lang.String.class, table = "t_account_user_ralation", tableAlias = "A", column = "user_id"),
	@SelectColumnMapping(property = "accountName", type = java.lang.String.class, table = "t_sys_admin", tableAlias = "C", column = "user_name"),
	@SelectColumnMapping(property = "shortName", type = java.lang.String.class, table = "t_sys_admin", tableAlias = "C", column = "short_name"),
	@SelectColumnMapping(property = "userName", type = java.lang.String.class, table = "t_user_register", tableAlias = "E", column = "user_name"),
	@SelectColumnMapping(property = "realName1", type = java.lang.String.class, table = "t_user_detail", tableAlias = "F", column = "user_realname"),
	@SelectColumnMapping(property = "realName2", type = java.lang.String.class, table = "t_user_detail", tableAlias = "F", column = "company_name"),
	@SelectColumnMapping(property = "userCode", type = java.lang.String.class, table = "t_account_user_ralation", tableAlias = "A", column = "user_code"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_account_user_ralation", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "operatorId", type = java.lang.String.class, table = "t_account_user_ralation", tableAlias = "A", column = "operator_id"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_account_user_ralation", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "rechangeId", type = java.lang.String.class, table = "t_account_user_ralation", tableAlias = "A", column = "rechange_id"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_account_user_ralation", tableAlias = "A", column = "UPDATE_TIME")
})

@Generated("2016-09-21 16:21:30")
public class AccountUserRalation implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String accountId;//登录账户编号
	private String userId;//商户账号编号
	private String userCode;//商户账号编码
	private String status;//状态，0禁用，1启用
	private String operatorId;//操作人编号
	private Date createTime;//创建时间
	private String rechangeId;//修改人编号
	private Date updateTime;//修改时间

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setAccountId(String value) {
		this.accountId = value;
	}
	
	public String getAccountId() {
		return this.accountId;
	}
	public void setUserId(String value) {
		this.userId = value;
	}
	
	public String getUserId() {
		return this.userId;
	}
	public void setUserCode(String value) {
		this.userCode = value;
	}
	
	public String getUserCode() {
		return this.userCode;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setOperatorId(String value) {
		this.operatorId = value;
	}
	
	public String getOperatorId() {
		return this.operatorId;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setRechangeId(String value) {
		this.rechangeId = value;
	}
	
	public String getRechangeId() {
		return this.rechangeId;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}

}

