package com.hfepay.scancode.params;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class BarcodeParam extends ParentPayParam{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3272711893306195425L;
	
//	@NotBlank(message="金额不能为空")
//	@Pattern(regexp="([1-9]\\d*(\\.\\d*[1-9])?)|(0\\.\\d*[1-9])",message="支付金额必须大于0")
//	private String price;
//	
	@NotBlank(message="支付授权码不能为空")
	private String authCode;//条码支付授权码
	
	@NotBlank(message="支付类别不能为空")
	@Pattern(regexp="(1|2)",message="支付类别不支持")
	private String scene;//支付类别：声波，红外线
	
//	@NotBlank(message="收银员不能为空")
//	private String cashier;//收银员
//	
//	@NotBlank(message="门店编号不能为空")
//	private String storeNo;//收银员绑定的门店编号
	
//	public String getPrice() {
//		return price;
//	}
//	public void setPrice(String price) {
//		this.price = price;
//	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	
//	public String getCashier() {
//		return cashier;
//	}
//	public void setCashier(String cashier) {
//		this.cashier = cashier;
//	}
//	
//	public String getStoreNo() {
//		return storeNo;
//	}
//	public void setStoreNo(String storeNo) {
//		this.storeNo = storeNo;
//	}
}
