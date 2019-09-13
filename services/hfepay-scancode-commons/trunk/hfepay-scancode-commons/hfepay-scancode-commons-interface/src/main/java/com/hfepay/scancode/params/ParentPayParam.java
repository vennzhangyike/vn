package com.hfepay.scancode.params;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class ParentPayParam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1915514192855202010L;
	@NotBlank(message="金额不能为空")
	@Pattern(regexp="([1-9]\\d*(\\.\\d{0,1}[0-9])?)|(0\\.\\d{0,1}[1-9])",message="支付金额必须是最多保留两位小数且大于0的正数")
	private String price;
	
	@NotBlank(message="收银员不能为空")
	private String cashier;//收银员
	
	@NotBlank(message="门店编号不能为空")
	private String storeNo;//收银员绑定的门店编号
	
	@NotBlank(message="收款类别不能为空")
	@Pattern(regexp="(0|1)",message="收款类别不支持")
	private String payType;
	
	@Pattern(regexp="(^(http|https)://([\\s\\S]*))",message="回调地址格式有误")
	private String notifyUrl;//异步回调地址
	
	@NotBlank(message="请求流水号不能为空")
	@Size(min=16, max=32,message="请求流水号长度为16-32")
	private String userOrderNo;//接口上送的流水号
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getUserOrderNo() {
		return userOrderNo;
	}
	public void setUserOrderNo(String userOrderNo) {
		this.userOrderNo = userOrderNo;
	}
	
}
