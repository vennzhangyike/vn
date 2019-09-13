package com.hfepay.scancode.commons.vo;

import com.hfepay.scancode.commons.entity.OrganWithdrawals;

public class OrganWithdrawalsVo extends OrganWithdrawals{
	
private static final long serialVersionUID = 1L;
	
	private String userName;
	private String agentName;
	private String organName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	
	
	
}
