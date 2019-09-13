package com.hfepay.scancode.api.entity.vo;

import java.util.Date;
import java.util.List;


public class MerChantAppVo implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6904419689259009482L;
	
	private String merchantCode;//商户编号
	
	private String companyName;//公司名称
	
	private String appCode;//应用编号
	
	private String appName;//应用名称
	
	private String appvKey;//应用私钥
	
	private String platpKey;//平台公钥
	
	private String safetyCode;//安全码

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppvKey() {
		return appvKey;
	}

	public void setAppvKey(String appvKey) {
		this.appvKey = appvKey;
	}

	public String getPlatpKey() {
		return platpKey;
	}

	public void setPlatpKey(String platpKey) {
		this.platpKey = platpKey;
	}

	public String getSafetyCode() {
		return safetyCode;
	}

	public void setSafetyCode(String safetyCode) {
		this.safetyCode = safetyCode;
	}
}
