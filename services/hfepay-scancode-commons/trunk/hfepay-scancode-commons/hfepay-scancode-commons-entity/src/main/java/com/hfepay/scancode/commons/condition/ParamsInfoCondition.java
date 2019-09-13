/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.commons.condition;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-12-02 13:42:35")
public class ParamsInfoCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String paramsKey;//参数名
	private String paramsType;//参数类型:0 短信参数
	private String paramsValue;//参数：JSON
	private String paramsStatus;//状态，1：正常，2：关闭
	private Date createTime;//创建时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setParamsKey(String value) {
		this.paramsKey = value;
	}
	
	public String getParamsKey() {
		return this.paramsKey;
	}
	public void setParamsType(String value) {
		this.paramsType = value;
	}
	
	public String getParamsType() {
		return this.paramsType;
	}
	public void setParamsValue(String value) {
		this.paramsValue = value;
	}
	
	public String getParamsValue() {
		return this.paramsValue;
	}
	public void setParamsStatus(String value) {
		this.paramsStatus = value;
	}
	
	public String getParamsStatus() {
		return this.paramsStatus;
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

