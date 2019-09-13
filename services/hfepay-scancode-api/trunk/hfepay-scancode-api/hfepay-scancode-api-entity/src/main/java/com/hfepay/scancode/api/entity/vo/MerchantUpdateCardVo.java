package com.hfepay.scancode.api.entity.vo;

import java.io.Serializable;

/**
 * 修改商户结算银行卡信息
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class MerchantUpdateCardVo implements Serializable{
	
	private String merchantNo;//渠道编号
	
	private String mobile;//手机号码
	
	private String newBankCode;//联行号
	
	private String newBankCard;//银行卡号
	
	private String newBankName;//银行名称
	
	private String accountType;//是否对公账号 Y:是，N：否
	
	private String returnURL;//回调地址

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getNewBankCode() {
		return newBankCode;
	}

	public void setNewBankCode(String newBankCode) {
		this.newBankCode = newBankCode;
	}

	public String getNewBankCard() {
		return newBankCard;
	}

	public void setNewBankCard(String newBankCard) {
		this.newBankCard = newBankCard;
	}

	public String getNewBankName() {
		return newBankName;
	}

	public void setNewBankName(String newBankName) {
		this.newBankName = newBankName;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	
}
