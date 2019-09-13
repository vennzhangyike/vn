/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;
import com.hfepay.commons.base.annotation.Generated;

import java.util.Date;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_channel_api", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "apiId", type = java.lang.String.class, table = "t_channel_api", tableAlias = "A", column = "api_id"),
	@SelectColumnMapping(property = "channelCode", type = java.lang.String.class, table = "t_channel_api", tableAlias = "A", column = "channel_code"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_channel_api", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "unitPrice", type = Double.class, table = "t_channel_api", tableAlias = "A", column = "unit_price"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_channel_api", tableAlias = "A", column = "RECORD_STATUS"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_channel_api", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_channel_api", tableAlias = "A", column = "UPDATE_TIME"),
	@SelectColumnMapping(property = "operatorId", type = java.lang.String.class, table = "t_channel_api", tableAlias = "A", column = "OPERATOR_ID"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_channel_api", tableAlias = "A", column = "REMARK")
})

@Generated("2016-06-28 11:09:34")
public class ChannelApi implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String apiId;//apiId
	private String channelCode;//渠道编号
	private String status;//状态(0禁用,1可用)
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operatorId;//操作人ID
	private String remark;//备注
	
	private Double unitPrice;// 单价

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setApiId(String value) {
		this.apiId = value;
	}
	
	public String getApiId() {
		return this.apiId;
	}
	public void setChannelCode(String value) {
		this.channelCode = value;
	}
	
	public String getChannelCode() {
		return this.channelCode;
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

}

