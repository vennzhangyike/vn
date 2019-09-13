/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dto;

import java.io.Serializable;

import com.hfepay.commons.base.annotation.Generated;

@Generated("2016-06-15 17:52:56")
public class MerchantOrderStatisticDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer time;
	private String count;
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	
}

