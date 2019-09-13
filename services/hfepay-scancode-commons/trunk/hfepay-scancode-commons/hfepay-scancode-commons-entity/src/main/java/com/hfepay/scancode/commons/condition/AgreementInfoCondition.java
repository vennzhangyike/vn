/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-12-13 09:57:47")
public class AgreementInfoCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
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

