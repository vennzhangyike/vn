/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.entity;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;
import com.hfepay.commons.base.annotation.Generated;

import java.io.Serializable;
import java.util.Date;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "payCode", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "PAY_CODE"),
	@SelectColumnMapping(property = "payName", type = java.lang.String.class, table = "t_mapping_dicion", tableAlias = "C", column = "PARA_NAME"),
	@SelectColumnMapping(property = "joinUserPublicKey", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "JOIN_USER_PUBLIC_KEY"),
	@SelectColumnMapping(property = "joinPublicKey", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "JOIN_PUBLIC_KEY"),
	@SelectColumnMapping(property = "joinPrivateKey", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "JOIN_PRIVATE_KEY"),
	@SelectColumnMapping(property = "joinKey", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "JOIN_KEY"),
	@SelectColumnMapping(property = "joinType", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "JOIN_TYPE"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "STATUS"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "RECORD_STATUS"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_platform_secret_key", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_platform_secret_key", tableAlias = "A", column = "UPDATE_TIME"),
	@SelectColumnMapping(property = "operatorId", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "OPERATOR_ID"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "REMARK"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "TEMP_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_platform_secret_key", tableAlias = "A", column = "TEMP_2")
})

@Generated("2016-10-11 18:56:33")
public class PlatformSecretKey implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//ID
	private String payCode;//华付通道编号
	private String joinUserPublicKey;//用户上传公钥
	private String joinPublicKey;//平台公钥
	private String joinPrivateKey;//平台私钥
	private String joinKey;//接入加解密KEY
	private String joinType;//接入方式1：密文接入，2：明文接入
	private String status;//秘钥状态，1：有效，2：无效或禁止
	private String recordStatus;//记录状态
	private Date createTime;//创建日期
	private Date updateTime;//修改日期
	private String operatorId;//操作人ID
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setPayCode(String value) {
		this.payCode = value;
	}
	
	public String getPayCode() {
		return this.payCode;
	}
	public void setJoinUserPublicKey(String value) {
		this.joinUserPublicKey = value;
	}
	
	public String getJoinUserPublicKey() {
		return this.joinUserPublicKey;
	}
	public void setJoinPublicKey(String value) {
		this.joinPublicKey = value;
	}
	
	public String getJoinPublicKey() {
		return this.joinPublicKey;
	}
	public void setJoinPrivateKey(String value) {
		this.joinPrivateKey = value;
	}
	
	public String getJoinPrivateKey() {
		return this.joinPrivateKey;
	}
	public void setJoinKey(String value) {
		this.joinKey = value;
	}
	
	public String getJoinKey() {
		return this.joinKey;
	}
	public void setJoinType(String value) {
		this.joinType = value;
	}
	
	public String getJoinType() {
		return this.joinType;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setRecordStatus(String value) {
		this.recordStatus = value;
	}
	
	public String getRecordStatus() {
		return this.recordStatus;
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
	public void setOperatorId(String value) {
		this.operatorId = value;
	}
	
	public String getOperatorId() {
		return this.operatorId;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setTemp1(String value) {
		this.temp1 = value;
	}
	
	public String getTemp1() {
		return this.temp1;
	}
	public void setTemp2(String value) {
		this.temp2 = value;
	}
	
	public String getTemp2() {
		return this.temp2;
	}

}

