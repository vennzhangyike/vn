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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "channelName", type = java.lang.String.class, table = "t_channel_base", tableAlias = "C", column = "channel_name"),
	@SelectColumnMapping(property = "bankCode", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "bank_code"),
	@SelectColumnMapping(property = "bankName", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "bank_name"),
	@SelectColumnMapping(property = "bankCard", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "bank_card"),
	@SelectColumnMapping(property = "idCard", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "id_card"),
	@SelectColumnMapping(property = "name", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "name"),
	@SelectColumnMapping(property = "mobile", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "mobile"),
	@SelectColumnMapping(property = "isRealAccount", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "is_real_account"),
	@SelectColumnMapping(property = "accountType", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "account_type"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_channel_bankcard", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_channel_bankcard", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "temp_2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "temp_3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_channel_bankcard", tableAlias = "A", column = "temp_4")
})

@Generated("2016-10-17 10:20:23")
public class ChannelBankcard implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String bankCode;//银行开户行代码
	private String bankName;//开户行银行名称（支行）
	private String bankCard;//银行卡号码
	private String idCard;//身份证号码
	private String name;//姓名
	private String mobile;//手机号码
	private String isRealAccount;//是否实时到账，Y：是，N：否
	private String accountType;//是否对公对私，1：对公，0：对私
	private String status;//状态，1：有效，2：无效/禁用
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setChannelNo(String value) {
		this.channelNo = value;
	}
	
	public String getChannelNo() {
		return this.channelNo;
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
	public void setTemp3(String value) {
		this.temp3 = value;
	}
	
	public String getTemp3() {
		return this.temp3;
	}
	public void setTemp4(String value) {
		this.temp4 = value;
	}
	
	public String getTemp4() {
		return this.temp4;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	
}

