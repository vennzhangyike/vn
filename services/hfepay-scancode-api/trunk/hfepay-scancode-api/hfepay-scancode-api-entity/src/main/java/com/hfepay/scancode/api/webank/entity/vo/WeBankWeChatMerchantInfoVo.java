package com.hfepay.scancode.api.webank.entity.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 微众 商户账户信息VO(API)
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class WeBankWeChatMerchantInfoVo implements Serializable{
	private String serialNo;//微信扫码  流水号
	private String mch_id;//商户号
	private String merchantName;//商户名称
	private String merchantAlis;//微信扫码  商户简称
	private String merchantArea;//微信扫码 地区
	private String bankName;//开户行
	private String revactBankNo;//开户行号
	private String bankAccoutName;//户名
	private String bankAccout;//银行账户 
	private String agency;//代理机构
	private String servicePhone;//客服电话
	private String contact;//联系人
	private String contactPhone;//手机号
	private String contactEmail;//联系邮箱
	private String business;//经营类目
	private String merchantNature;////商户性质（国有企业，三资企业，私营企业，集体企业)
	private String openYear;//经营年限
	private String contractNo;//合同编号
	private String contractLicence;//营业执照
	private String businessStartDate;//执照开始时间
	private String businessEndDate;//执照结束时间
	private String wxCostRate;//商户扣率
	private String companyFlag;//账号性质
	
	
	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getMerchantAlis() {
		return merchantAlis;
	}

	public void setMerchantAlis(String merchantAlis) {
		this.merchantAlis = merchantAlis;
	}

	public String getMerchantArea() {
		return merchantArea;
	}

	public void setMerchantArea(String merchantArea) {
		this.merchantArea = merchantArea;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getRevactBankNo() {
		return revactBankNo;
	}

	public void setRevactBankNo(String revactBankNo) {
		this.revactBankNo = revactBankNo;
	}

	public String getBankAccoutName() {
		return bankAccoutName;
	}

	public void setBankAccoutName(String bankAccoutName) {
		this.bankAccoutName = bankAccoutName;
	}

	public String getBankAccout() {
		return bankAccout;
	}

	public void setBankAccout(String bankAccout) {
		this.bankAccout = bankAccout;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getMerchantNature() {
		return merchantNature;
	}

	public void setMerchantNature(String merchantNature) {
		this.merchantNature = merchantNature;
	}

	public String getWxCostRate() {
		return wxCostRate;
	}

	public void setWxCostRate(String wxCostRate) {
		this.wxCostRate = wxCostRate;
	}

	public String getCompanyFlag() {
		return companyFlag;
	}

	public void setCompanyFlag(String companyFlag) {
		this.companyFlag = companyFlag;
	}
	

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getOpenYear() {
		return openYear;
	}

	public void setOpenYear(String openYear) {
		this.openYear = openYear;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractLicence() {
		return contractLicence;
	}

	public void setContractLicence(String contractLicence) {
		this.contractLicence = contractLicence;
	}

	public String getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(String businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	public String getBusinessEndDate() {
		return businessEndDate;
	}

	public void setBusinessEndDate(String businessEndDate) {
		this.businessEndDate = businessEndDate;
	}

	public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null && !"".equals(obj)){
                    map.put(field.getName(), String.valueOf(obj));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	
}
