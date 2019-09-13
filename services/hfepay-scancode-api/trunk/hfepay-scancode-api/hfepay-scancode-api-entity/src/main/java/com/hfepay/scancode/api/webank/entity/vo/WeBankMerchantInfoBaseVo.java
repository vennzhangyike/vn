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
public class WeBankMerchantInfoBaseVo implements Serializable{
	private String productType;//支付类型，默认填写“003”
	private String registerType;//普通模式商户有代理商填写“01”商户无代理商(商户直连模式)填写“02”
	private String aliasName;//商户简称（registerType为“02”时必填）
	private String servicePhone;//客服电话（registerType为“02”时必填）
	private String contactPhone;//联系人座机（registerType为“02”时选填）
	private String contactEmail;//联系人邮箱（registerType为“02”时选填）
	private String memo;//备注信息（registerType为“02”时选填）
	private String externalInfo;//扩展信息（registerType为“02”时选填）
	private String acquirerId;//微众在支付宝的商户号，由微众提供（registerType为“02”时选填
	private String district;//地区号，请参考数据字典（如深圳：0755）
	
	public WeBankMerchantInfoBaseVo() {
		setProductType("003");
		setRegisterType("01");
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getExternalInfo() {
		return externalInfo;
	}

	public void setExternalInfo(String externalInfo) {
		this.externalInfo = externalInfo;
	}

	public String getAcquirerId() {
		return acquirerId;
	}

	public void setAcquirerId(String acquirerId) {
		this.acquirerId = acquirerId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
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
