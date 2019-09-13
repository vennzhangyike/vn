package com.hfepay.scancode.commons.bo;

import java.io.Serializable;

public class MerchantJoinMessage  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1544596389728793124L;
	
	private String merchantNo;
	private String merchantName;
	private String shortName;
	private String address;
	private String serPhone;
	private String category;
	private String idCard;
	private String name;
	private String phone;
	private String mobile;
	private String email;
	private String merchantLicense;
	private String terminalId;
	private String remark;
	private String auditStatus;
	private String auditMsg;
	private String positionCode;
	private String mainBusiness;
	private String businessRange;
	private String merchantTypeCode;
	private String merchantNature;
	private String district;
	private String hfmerchantNo;
	
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

	public String getMerchantLicense() {
		return merchantLicense;
	}

	public void setMerchantLicense(String merchantLicense) {
		this.merchantLicense = merchantLicense;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAuditMsg() {
		return auditMsg;
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	public String getBusinessRange() {
		return businessRange;
	}

	public void setBusinessRange(String businessRange) {
		this.businessRange = businessRange;
	}

	public String getMerchantTypeCode() {
		return merchantTypeCode;
	}

	public void setMerchantTypeCode(String merchantTypeCode) {
		this.merchantTypeCode = merchantTypeCode;
	}

	public String getMerchantNature() {
		return merchantNature;
	}

	public void setMerchantNature(String merchantNature) {
		this.merchantNature = merchantNature;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getHfmerchantNo() {
		return hfmerchantNo;
	}

	public void setHfmerchantNo(String hfmerchantNo) {
		this.hfmerchantNo = hfmerchantNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
}
