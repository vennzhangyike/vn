/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2017-01-22 11:18:09")
public class CityCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private Integer cid;//cid
	private String cname;//cname
	private String cpostcode;//cpostcode
	private Integer pid;//pid

	public void setCid(Integer value) {
		this.cid = value;
	}
	
	public Integer getCid() {
		return this.cid;
	}
	public void setCname(String value) {
		this.cname = value;
	}
	
	public String getCname() {
		return this.cname;
	}
	public void setCpostcode(String value) {
		this.cpostcode = value;
	}
	
	public String getCpostcode() {
		return this.cpostcode;
	}
	public void setPid(Integer value) {
		this.pid = value;
	}
	
	public Integer getPid() {
		return this.pid;
	}
}

