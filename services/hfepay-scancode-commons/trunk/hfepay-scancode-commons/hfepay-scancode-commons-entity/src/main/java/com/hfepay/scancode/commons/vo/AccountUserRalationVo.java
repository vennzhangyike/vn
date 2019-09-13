/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.commons.vo;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.scancode.commons.entity.AccountUserRalation;

@Generated("2016-09-21 16:21:30")
public class AccountUserRalationVo extends AccountUserRalation{
	private static final long serialVersionUID = 1L;
	
	private String operatorName;
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String value) {
		this.operatorName = value;
	}
	
	private String accountName;
	private String rechangeName;
	private String userName;
	private String shortName;

	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getRechangeName() {
		return rechangeName;
	}
	public void setRechangeName(String rechangeName) {
		this.rechangeName = rechangeName;
	}
	
}
	

