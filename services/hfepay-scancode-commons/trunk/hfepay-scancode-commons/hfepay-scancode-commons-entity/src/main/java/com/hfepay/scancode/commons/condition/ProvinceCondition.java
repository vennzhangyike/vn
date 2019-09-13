/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2017-01-22 14:03:17")
public class ProvinceCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private Integer pid;//pid
	private String pname;//pname

	public void setPid(Integer value) {
		this.pid = value;
	}
	
	public Integer getPid() {
		return this.pid;
	}
	public void setPname(String value) {
		this.pname = value;
	}
	
	public String getPname() {
		return this.pname;
	}
}

