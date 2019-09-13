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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "agreementNo", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "AGREEMENT_NO"),
	@SelectColumnMapping(property = "organNo", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "organ_no"),
	@SelectColumnMapping(property = "agreementtype", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "AGREEMENT_type"),
	@SelectColumnMapping(property = "agreementcontent", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "AGREEMENT_content"),
	@SelectColumnMapping(property = "startDate", type = Date.class, table = "t_agreement_info", tableAlias = "A", column = "START_DATE"),
	@SelectColumnMapping(property = "endDate", type = Date.class, table = "t_agreement_info", tableAlias = "A", column = "END_DATE"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "STATUS"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_agreement_info", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "updateTime", type = Date.class, table = "t_agreement_info", tableAlias = "A", column = "UPDATE_TIME"),
	@SelectColumnMapping(property = "operater", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "OPERATER"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "REMARK"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "TEMP_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "TEMP_2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "TEMP_3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_agreement_info", tableAlias = "A", column = "TEMP_4")
})

@Generated("2016-12-13 09:57:47")
public class AgreementInfo implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//ID
	private String agreementNo;//协议编号
	private String organNo;//机构编号
	private String agreementtype;//协议类型：0 渠道商户入驻电子协议 
	private String agreementcontent;//协议内容
	private Date startDate;//协议开始日期
	private Date endDate;//协议结束日期
	private String status;//status
	private Date createTime;//createTime
	private Date updateTime;//updateTime
	private String operater;//operater
	private String remark;//remark
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
	public void setAgreementNo(String value) {
		this.agreementNo = value;
	}
	
	public String getAgreementNo() {
		return this.agreementNo;
	}
	public void setOrganNo(String value) {
		this.organNo = value;
	}
	
	public String getOrganNo() {
		return this.organNo;
	}
	public void setAgreementtype(String value) {
		this.agreementtype = value;
	}
	
	public String getAgreementtype() {
		return this.agreementtype;
	}
	public void setAgreementcontent(String value) {
		this.agreementcontent = value;
	}
	
	public String getAgreementcontent() {
		return this.agreementcontent;
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
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
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
	public void setOperater(String value) {
		this.operater = value;
	}
	
	public String getOperater() {
		return this.operater;
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

