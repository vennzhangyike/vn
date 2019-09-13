/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.entity;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.RecordStatus;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;
import com.hfepay.commons.base.annotation.Generated;

import java.io.Serializable;
import java.util.Date;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_mapping_dicion", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "name", type = java.lang.String.class, table = "t_mapping_dicion", tableAlias = "A", column = "NAME"),
	@SelectColumnMapping(property = "keyNo", type = java.lang.String.class, table = "t_mapping_dicion", tableAlias = "A", column = "KEY_NO"),
	@SelectColumnMapping(property = "paraName", type = java.lang.String.class, table = "t_mapping_dicion", tableAlias = "A", column = "PARA_NAME"),
	@SelectColumnMapping(property = "paraVal", type = java.lang.String.class, table = "t_mapping_dicion", tableAlias = "A", column = "PARA_VAL"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_mapping_dicion", tableAlias = "A", column = "RECORD_STATUS"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_mapping_dicion", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_mapping_dicion", tableAlias = "A", column = "UPDATE_TIME"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_mapping_dicion", tableAlias = "A", column = "OPERATOR"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_mapping_dicion", tableAlias = "A", column = "REMARK")
})

@Generated("2016-10-19 15:18:48")
public class ApiMappingDicion implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//ID
	private String name;//名称
	private String keyNo;//KEY
	private String paraName;//参数名称
	private String paraVal;//参数值
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人ID
	private String remark;//备注

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setKeyNo(String value) {
		this.keyNo = value;
	}
	
	public String getKeyNo() {
		return this.keyNo;
	}
	public void setParaName(String value) {
		this.paraName = value;
	}
	
	public String getParaName() {
		return this.paraName;
	}
	public void setParaVal(String value) {
		this.paraVal = value;
	}
	
	public String getParaVal() {
		return this.paraVal;
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
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
		return this.operator;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}

	
}

