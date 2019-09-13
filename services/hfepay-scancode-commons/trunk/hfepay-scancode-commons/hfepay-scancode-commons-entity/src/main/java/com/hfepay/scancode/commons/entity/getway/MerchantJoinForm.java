package com.hfepay.scancode.commons.entity.getway;

import java.io.Serializable;

public class MerchantJoinForm implements Serializable {
	private static final long serialVersionUID = 1L;
	//商户基本信息
	private String merchantNo;//第三方商户编号
	private String merchantName;//商户名称
	private String shortName;//商户简称
	private String address;//地址
	private String serPhone;//联系地址
	private String category;//行业类别
	private String idCard;//身份证ID
	private String name;//法人，个人姓名
	private String phone;//联系方式
	private String mobile;//联系方式
	private String email;//邮件
	private String remark;//备注
	private String merchantLicense;
	//商户账户信息
	private String bankCode;//开户行联行号
	private String bankName;//银行名称
	private String accountName;//持卡人姓名
	private String bankCard;//银行卡号
	private String isRealAccount;//是否实时到账
	
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSerPhone() {
		return serPhone;
	}
	public void setSerPhone(String serPhone) {
		this.serPhone = serPhone;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMerchantLicense() {
		return merchantLicense;
	}
	public void setMerchantLicense(String merchantLicense) {
		this.merchantLicense = merchantLicense;
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
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getIsRealAccount() {
		return isRealAccount;
	}
	public void setIsRealAccount(String isRealAccount) {
		this.isRealAccount = isRealAccount;
	}
	@Override
	public String toString() {
		return "MerchantJoinForm [merchantNo=" + merchantNo + ", merchantName=" + merchantName + ", shortName="
				+ shortName + ", address=" + address + ", serPhone=" + serPhone + ", category=" + category + ", idCard="
				+ idCard + ", name=" + name + ", phone=" + phone + ", mobile=" + mobile + ", email=" + email
				+ ", remark=" + remark + ", merchantLicense=" + merchantLicense + ", bankCode=" + bankCode
				+ ", bankName=" + bankName + ", accountName=" + accountName + ", bankCard=" + bankCard
				+ ", isRealAccount=" + isRealAccount + "]";
	}
	
	
}
