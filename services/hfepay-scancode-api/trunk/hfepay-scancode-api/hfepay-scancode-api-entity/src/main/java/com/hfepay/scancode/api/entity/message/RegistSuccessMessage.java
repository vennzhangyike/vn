package com.hfepay.scancode.api.entity.message;

import java.util.Date;

/**
 * 注册成功消息推送Vo
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class RegistSuccessMessage extends PushMessage{
	
	
	private String companyType;//公司类型
	
	private String companyName;//公司名称
	
	private String name;//联系姓名
	
	private String address;//客户地址
	
	private Date registTime;//注册时间
	
	private String mobile;//手机号码

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
