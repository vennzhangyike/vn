package com.hfepay.scancode.commons.vo;

import com.hfepay.scancode.commons.entity.OrganProfit;

public class OrganProfitVO extends OrganProfit {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 2064219996811791844L;

	private String orgName;//名称
	private String identityFlag;//级别，代理商，渠道，平台等
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getIdentityFlag() {
		return identityFlag;
	}
	public void setIdentityFlag(String identityFlag) {
		this.identityFlag = identityFlag;
	}
	
}
