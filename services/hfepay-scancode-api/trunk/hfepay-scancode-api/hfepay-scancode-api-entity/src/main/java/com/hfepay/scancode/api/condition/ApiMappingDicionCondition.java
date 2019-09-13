/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-10-19 15:18:48")
public class ApiMappingDicionCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//ID
	private String name;//名称
	private String keyNo;//KEY
	private String paraName;//参数名称
	private String paraVal;//参数值
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
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

