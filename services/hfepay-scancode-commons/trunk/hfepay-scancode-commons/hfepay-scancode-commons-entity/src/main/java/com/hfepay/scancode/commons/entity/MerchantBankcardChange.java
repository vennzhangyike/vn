/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_order_pay", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_order_pay", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "MERCHANT_NO"),
	@SelectColumnMapping(property = "merchantName", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "C", column = "MERCHANT_NAME"),
	@SelectColumnMapping(property = "idCard", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "ID_CARD"),
	@SelectColumnMapping(property = "name", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "NAME"),
	@SelectColumnMapping(property = "bankCode", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "BANK_CODE"),
	@SelectColumnMapping(property = "bankName", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "BANK_NAME"),
	@SelectColumnMapping(property = "bankCard", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "BANK_CARD"),
	@SelectColumnMapping(property = "mobile", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "MOBILE"),
	@SelectColumnMapping(property = "isRealAccount", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "IS_REAL_ACCOUNT"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "STATUS"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "UPDATE_TIME"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "OPERATOR"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "REMARK"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "TEMP_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "TEMP_2"),
	@SelectColumnMapping(property = "accountType", type = java.lang.String.class, table = "t_merchant_bankcard_change", tableAlias = "A", column = "account_type")
})

@Generated("2016-12-06 14:19:53")
public class MerchantBankcardChange implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//id
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String idCard;//身份证号码
	private String name;//姓名
	private String bankCode;//银行开户行代码
	private String bankName;//银行名称
	private String bankCard;//银行卡号
	private String mobile;//手机号码
	private String isRealAccount;//是否实时到账，Y：是，N：否
	private String status;//是否有效：1：有效，2：无效 3:审核中 4：审核不通过
	private Date createTime;//入网时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String accountType;//是否对公对私，0：对私，1：对公

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
	}
	public void setIdCard(String value) {
		this.idCard = value;
	}
	
	public String getIdCard() {
		return this.idCard;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setBankCode(String value) {
		this.bankCode = value;
	}
	
	public String getBankCode() {
		return this.bankCode;
	}
	public void setBankName(String value) {
		this.bankName = value;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	public void setBankCard(String value) {
		this.bankCard = value;
	}
	
	public String getBankCard() {
		return this.bankCard;
	}
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	public void setIsRealAccount(String value) {
		this.isRealAccount = value;
	}
	
	public String getIsRealAccount() {
		return this.isRealAccount;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
		return this.operator;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setTemp1(String value) {
		this.temp1 = value;
	}
	
	public String getTemp1() {
		return this.temp1;
	}
	public void setTemp2(String value) {
		this.temp2 = value;
	}
	
	public String getTemp2() {
		return this.temp2;
	}
	public void setAccountType(String value) {
		this.accountType = value;
	}
	
	public String getAccountType() {
		return this.accountType;
	}

	
}

