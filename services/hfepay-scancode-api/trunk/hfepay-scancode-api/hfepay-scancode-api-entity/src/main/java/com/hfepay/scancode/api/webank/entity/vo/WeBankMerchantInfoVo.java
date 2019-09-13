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
public class WeBankMerchantInfoVo implements Serializable{
	private String agencyId;//代理商编号，微众银行提供
	private String appId;//渠道号，微众银行提供
	private String idType;//商户法人的证件类型
	private String idNo;//商户法人证件号码
	private String merchantName;//商户名称
	private String legalRepresent;//法人代表
	private String licenceNo;//营业执照编号
	private String licenceBeginTime;//执照开始时间，格式“2012-12-12”
	private String licenceEndTime;//执照结束时间，格式“2015-12-12”
	private String taxRegisterNo;//税务登记号
	private String positionCode;//单位代码，如果没有填“0”
	private String contactName;//联系人姓名
	private String contactPhoneNo;//联系人电话
	private String mainBusiness;//主营业务
	private String businessRange;//经营范围
	private String registerAddr;//注册地址
	private String merchantTypeCode;//MCC类别码
	private String merchantLevel;//默认填1
	private String parentMerchantId;//可不填
	private String merchantNature;//商户性质（国有企业，三资企业，私营企业，集体企业)
	private String contractNo;//合同编号
	private String openYear;//商户开业时间
	private String categoryId;//类目
	
	
	public WeBankMerchantInfoVo() {
		setPositionCode("0");
		setMerchantLevel("1");
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getLegalRepresent() {
		return legalRepresent;
	}

	public void setLegalRepresent(String legalRepresent) {
		this.legalRepresent = legalRepresent;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public String getLicenceBeginTime() {
		return licenceBeginTime;
	}

	public void setLicenceBeginTime(String licenceBeginTime) {
		this.licenceBeginTime = licenceBeginTime;
	}

	public String getLicenceEndTime() {
		return licenceEndTime;
	}

	public void setLicenceEndTime(String licenceEndTime) {
		this.licenceEndTime = licenceEndTime;
	}

	public String getTaxRegisterNo() {
		return taxRegisterNo;
	}

	public void setTaxRegisterNo(String taxRegisterNo) {
		this.taxRegisterNo = taxRegisterNo;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhoneNo() {
		return contactPhoneNo;
	}

	public void setContactPhoneNo(String contactPhoneNo) {
		this.contactPhoneNo = contactPhoneNo;
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

	public String getRegisterAddr() {
		return registerAddr;
	}

	public void setRegisterAddr(String registerAddr) {
		this.registerAddr = registerAddr;
	}

	public String getMerchantTypeCode() {
		return merchantTypeCode;
	}

	public void setMerchantTypeCode(String merchantTypeCode) {
		this.merchantTypeCode = merchantTypeCode;
	}

	public String getMerchantLevel() {
		return merchantLevel;
	}

	public void setMerchantLevel(String merchantLevel) {
		this.merchantLevel = merchantLevel;
	}

	public String getParentMerchantId() {
		return parentMerchantId;
	}

	public void setParentMerchantId(String parentMerchantId) {
		this.parentMerchantId = parentMerchantId;
	}

	public String getMerchantNature() {
		return merchantNature;
	}

	public void setMerchantNature(String merchantNature) {
		this.merchantNature = merchantNature;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getOpenYear() {
		return openYear;
	}

	public void setOpenYear(String openYear) {
		this.openYear = openYear;
	}
	
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
