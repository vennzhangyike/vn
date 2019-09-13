/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.entity;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "cid", type = java.lang.Integer.class, table = "t_city", tableAlias = "A", column = "cid"),
	@SelectColumnMapping(property = "cname", type = java.lang.String.class, table = "t_city", tableAlias = "A", column = "cname"),
	@SelectColumnMapping(property = "cpostcode", type = java.lang.String.class, table = "t_city", tableAlias = "A", column = "cpostcode"),
	@SelectColumnMapping(property = "pid", type = java.lang.Integer.class, table = "t_city", tableAlias = "A", column = "pid")
})

@Generated("2017-01-22 11:18:09")
public class City implements IdEntity<Integer> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
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

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	
}

