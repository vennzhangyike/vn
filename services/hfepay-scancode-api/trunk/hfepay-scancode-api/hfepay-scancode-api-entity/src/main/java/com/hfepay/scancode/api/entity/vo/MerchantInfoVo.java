package com.hfepay.scancode.api.entity.vo;

import java.io.Serializable;

/**
 * 商户基本信息VO(API)
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class MerchantInfoVo implements Serializable{
	
	private String terminalId;
	private String positionCode;
	private String mainBusiness;
	private String businessRange;
	private String merchantTypeCode;
	private String merchantNature;
	private String district;
	private String notifyUrl;
	
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
	private String merchantLicense;//商户营业执照号
	private String remark;//备注
	
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
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
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
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
}
