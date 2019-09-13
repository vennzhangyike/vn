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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "channelName", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "channel_name"),
	@SelectColumnMapping(property = "channelType", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "channel_type"),
	@SelectColumnMapping(property = "qrTotal", type = java.lang.Integer.class, table = "t_channel_base", tableAlias = "A", column = "qr_total"),
	@SelectColumnMapping(property = "useTotal", type = java.lang.Integer.class, table = "t_channel_base", tableAlias = "A", column = "use_total"),
	@SelectColumnMapping(property = "lessTotal", type = java.lang.Integer.class, table = "t_channel_base", tableAlias = "A", column = "less_total"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "record_status"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_channel_base", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_channel_base", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "temp_2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "temp_3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_channel_base", tableAlias = "A", column = "temp_4")
})

@Generated("2016-10-13 17:45:06")
public class ChannelBase implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String channelName;//渠道名称
	private String channelType;//渠道类型：0 个人、1 机构
	private Integer qrTotal;//总二维码数量
	private Integer useTotal;//已使用二维码数量
	private Integer lessTotal;//剩余二维码数量
	private String status;//状态，1：正常，2：关闭
	private String recordStatus;//记录状态，Y:正常，N:删除
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setChannelNo(String value) {
		this.channelNo = value;
	}
	
	public String getChannelNo() {
		return this.channelNo;
	}
	public void setChannelName(String value) {
		this.channelName = value;
	}
	
	public String getChannelName() {
		return this.channelName;
	}
	public void setChannelType(String value) {
		this.channelType = value;
	}
	
	public String getChannelType() {
		return this.channelType;
	}
	public void setQrTotal(Integer value) {
		this.qrTotal = value;
	}
	
	public Integer getQrTotal() {
		return this.qrTotal;
	}
	public void setUseTotal(Integer value) {
		this.useTotal = value;
	}
	
	public Integer getUseTotal() {
		return this.useTotal;
	}
	public void setLessTotal(Integer value) {
		this.lessTotal = value;
	}
	
	public Integer getLessTotal() {
		return this.lessTotal;
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
	public void setTemp3(String value) {
		this.temp3 = value;
	}
	
	public String getTemp3() {
		return this.temp3;
	}
	public void setTemp4(String value) {
		this.temp4 = value;
	}
	
	public String getTemp4() {
		return this.temp4;
	}

	
}

