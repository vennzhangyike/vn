/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.vo;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.scancode.commons.entity.ChannelBankcard;

@Generated("2016-10-20 17:39:26")
public class ChannelBankcardVo extends ChannelBankcard{
	private static final long serialVersionUID = 1L;
	
	private String operatorName;
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String value) {
		this.operatorName = value;
	}
	
	private String channelName;

	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
}
	

