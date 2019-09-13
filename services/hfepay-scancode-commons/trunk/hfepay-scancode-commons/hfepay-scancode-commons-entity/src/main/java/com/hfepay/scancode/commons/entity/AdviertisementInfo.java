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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "adviertNo", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "adviert_no"),
	@SelectColumnMapping(property = "organNo", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "organ_no"),
	@SelectColumnMapping(property = "adviertScope", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "adviert_scope"),
	@SelectColumnMapping(property = "adviertPosition", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "adviert_position"),
	@SelectColumnMapping(property = "imgUrl", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "img_url"),
	@SelectColumnMapping(property = "adviertLink", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "adviert_link"),
	@SelectColumnMapping(property = "startDate", type = java.util.Date.class, table = "t_adviertisement_info", tableAlias = "A", column = "start_date"),
	@SelectColumnMapping(property = "endDate", type = java.util.Date.class, table = "t_adviertisement_info", tableAlias = "A", column = "end_date"),
	@SelectColumnMapping(property = "priority", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "priority"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "record_status"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_adviertisement_info", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_adviertisement_info", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "temp_2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "temp_3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_adviertisement_info", tableAlias = "A", column = "temp_4")
})

@Generated("2017-01-06 10:31:38")
public class AdviertisementInfo implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String adviertNo;//广告编号
	private String organNo;//机构编号
	private String adviertScope;//推广范围
	private String adviertPosition;//广告位：0 首页广告 1 支付成功页广告
	private String imgUrl;//广告图片地址
	private String adviertLink;//广告链接地址
	private Date startDate;//生效日期
	private Date endDate;//结束日期
	private String priority;//优先级别：0 最高级 1 一级 2 二级 3 三级
	private String status;//状态，1：申请中，2：审核通过，3：审核拒绝
	private String recordStatus;//记录状态，Y:正常，N:删除
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4
	private String organName;//机构名称

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setAdviertNo(String value) {
		this.adviertNo = value;
	}
	
	public String getAdviertNo() {
		return this.adviertNo;
	}
	public void setOrganNo(String value) {
		this.organNo = value;
	}
	
	public String getOrganNo() {
		return this.organNo;
	}
	public void setAdviertScope(String value) {
		this.adviertScope = value;
	}
	
	public String getAdviertScope() {
		return this.adviertScope;
	}
	public void setAdviertPosition(String value) {
		this.adviertPosition = value;
	}
	
	public String getAdviertPosition() {
		return this.adviertPosition;
	}
	public void setImgUrl(String value) {
		this.imgUrl = value;
	}
	
	public String getImgUrl() {
		return this.imgUrl;
	}
	public void setAdviertLink(String value) {
		this.adviertLink = value;
	}
	
	public String getAdviertLink() {
		return this.adviertLink;
	}
	public void setStartDate(Date value) {
		this.startDate = value;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}
	public void setEndDate(Date value) {
		this.endDate = value;
	}
	
	public Date getEndDate() {
		return this.endDate;
	}
	public void setPriority(String value) {
		this.priority = value;
	}
	
	public String getPriority() {
		return this.priority;
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

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	
}

