/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-11-01 11:39:57")
public class HfepayCategoryCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//id
	private String code;//第三方通道代码，CMBC:民生银行
	private String payType;//支付类型
	private String name;//类目名称
	private String parentId;//父级ID
	private String level;//层级
	private String certType;//证照类型
	private String categoryNo;//类目编号
	private String mappingCategoryNo;//对照类目编号
	private String status;//状态(0禁用,1可用)
	private String recordStatus;//记录状态，Y:正常，N:删除
	private Date createTime;//创建时间
	private String operater;//操作人账号
	private String remark;//备注

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setCode(String value) {
		this.code = value;
	}
	
	public String getCode() {
		return this.code;
	}
	public void setPayType(String value) {
		this.payType = value;
	}
	
	public String getPayType() {
		return this.payType;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setParentId(String value) {
		this.parentId = value;
	}
	
	public String getParentId() {
		return this.parentId;
	}
	public void setLevel(String value) {
		this.level = value;
	}
	
	public String getLevel() {
		return this.level;
	}
	public void setCertType(String value) {
		this.certType = value;
	}
	
	public String getCertType() {
		return this.certType;
	}
	public void setCategoryNo(String value) {
		this.categoryNo = value;
	}
	
	public String getCategoryNo() {
		return this.categoryNo;
	}
	public void setMappingCategoryNo(String value) {
		this.mappingCategoryNo = value;
	}
	
	public String getMappingCategoryNo() {
		return this.mappingCategoryNo;
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
}

