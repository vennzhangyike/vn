/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "withdrawalsNo", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "withdrawals_no"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "organNo", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "organ_no"),
	@SelectColumnMapping(property = "isChannel", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "is_channel"),
	@SelectColumnMapping(property = "balance", type = BigDecimal.class, table = "t_organ_withdrawals", tableAlias = "A", column = "balance"),
	@SelectColumnMapping(property = "actualAmount", type = BigDecimal.class, table = "t_organ_withdrawals", tableAlias = "A", column = "actual_amount"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "bankCode", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "bank_code"),
	@SelectColumnMapping(property = "bankName", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "bank_name"),
	@SelectColumnMapping(property = "bankCard", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "bank_card"),
	@SelectColumnMapping(property = "idCard", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "id_card"),
	@SelectColumnMapping(property = "name", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "name"),
	@SelectColumnMapping(property = "mobile", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "mobile"),
	@SelectColumnMapping(property = "accountType", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "account_type"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_organ_withdrawals", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = Date.class, table = "t_organ_withdrawals", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_organ_withdrawals", tableAlias = "A", column = "temp_2")
})

@Generated("2016-12-21 10:49:29")
public class OrganWithdrawals implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键ID
	private String withdrawalsNo;//提现流水号
	private String channelNo;//渠道编号
	private String organNo;//申请机构编号
	private String isChannel;//是否是渠道1是0否
	private BigDecimal balance;//申请提现金额
	private BigDecimal actualAmount;//实际到账金额
	private String status;//是否有效：1：申请中，2：审核通过，3：审核拒绝（渠道审核）
	private String bankCode;//银行开户行代码
	private String bankName;//开户行银行名称（支行）
	private String bankCard;//银行卡号码
	private String idCard;//身份证号码
	private String name;//姓名
	private String mobile;//手机号码
	private String accountType;//账户类型，0：对私，1：对公
	private Date createTime;//申请时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//备用字段
	private String temp2;//备用字段
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWithdrawalsNo() {
		return withdrawalsNo;
	}
	public void setWithdrawalsNo(String withdrawalsNo) {
		this.withdrawalsNo = withdrawalsNo;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getOrganNo() {
		return organNo;
	}
	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}
	public String getIsChannel() {
		return isChannel;
	}
	public void setIsChannel(String isChannel) {
		this.isChannel = isChannel;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	
	
	
	
}

