/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.vo;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.scancode.commons.entity.PlatformLimit;

@Generated("2016-11-18 15:02:00")
public class PlatformLimitVo extends PlatformLimit{
	private static final long serialVersionUID = 1L;
	
	private String operatorName;
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String value) {
		this.operatorName = value;
	}
	
	private String paraName;

	public String getParaName() {
		return paraName;
	}
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
}
	

