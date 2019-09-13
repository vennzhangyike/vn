package com.hfepay.scancode.commons.vo;

import com.hfepay.scancode.commons.entity.HierarchicalSettlementTotal;

public class HierarchicalSettlementTotalVo extends HierarchicalSettlementTotal  {

	
private static final long serialVersionUID = 1L;
	
	private String channelName;
	private String agentName;
	
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	
	
	
}
