/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "channelName", type = java.lang.String.class, table = "t_channel_info", tableAlias = "B", column = "channel_name"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "agentName", type = java.lang.String.class, table = "t_agent_base", tableAlias = "C", column = "agent_name"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "merchantName", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "merchant_name"),
	@SelectColumnMapping(property = "shortName", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "short_name"),
	@SelectColumnMapping(property = "platformMerchantNo", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "platform_merchant_no"),
	@SelectColumnMapping(property = "busType", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "bus_type"),
	@SelectColumnMapping(property = "name", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "name"),
	@SelectColumnMapping(property = "idCard", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "id_card"),
	@SelectColumnMapping(property = "idcardImg1", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "idcard_img_1"),
	@SelectColumnMapping(property = "idcardImg2", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "idcard_img_2"),
	@SelectColumnMapping(property = "idcardImg3", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "idcard_img_3"),
	@SelectColumnMapping(property = "mobile", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "mobile"),
	@SelectColumnMapping(property = "phone", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "phone"),
	@SelectColumnMapping(property = "email", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "email"),
	@SelectColumnMapping(property = "address", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "address"),
	@SelectColumnMapping(property = "merchantLicense", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "merchant_license"),
	@SelectColumnMapping(property = "merchantLicenseImg", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "merchant_license_img"),
	@SelectColumnMapping(property = "taxNo", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "tax_no"),
	@SelectColumnMapping(property = "taxImg", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "tax_img"),
	@SelectColumnMapping(property = "orgNo", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "org_no"),
	@SelectColumnMapping(property = "qrCode", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "qr_code"),
	@SelectColumnMapping(property = "creditPayStatus", type = java.lang.Integer.class, table = "t_merchant_info", tableAlias = "A", column = "credit_pay_status"),
	@SelectColumnMapping(property = "authenStatus", type = java.lang.Integer.class, table = "t_merchant_info", tableAlias = "A", column = "authen_status"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_merchant_info", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_merchant_info", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "temp_2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "temp_3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "temp_4"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "A", column = "RECORD_STATUS")
})

@Generated("2016-10-20 17:39:26")
public class MerchantInfo implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String merchantName;//商户名称
	private String shortName;//商户简称
	private String platformMerchantNo;//转接平台商户编号
	private String busType;//经营类型
	private String name;//法人姓名
	private String idCard;//法人身份证号码
	private String idcardImg1;//身份证正面
	private String idcardImg2;//身份证反面
	private String idcardImg3;//手持身份证
	private String mobile;//手机号码
	private String phone;//联系电话
	private String email;//邮箱
	private String address;//地址
	private String merchantLicense;//商户营业执照号
	private String merchantLicenseImg;//营业执照图片
	private String taxNo;//税务登记证号码
	private String taxImg;//税务登记证图片
	private String orgNo;//组织机构代码
	private String qrCode;//二维码编号
	private String creditPayStatus;
	private Integer authenStatus;//实名认证情况：0 未认证 1已认证 2认证失败
	private String status;//是否有效：0 申请中 1 上级审核通过 2 上级审核拒绝 3平台审核通过 4 平台审核拒绝 5 停用
	private Date createTime;//入网时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4
	private String recordStatus;//记录状态，Y:正常，N:删除

	public String getCreditPayStatus() {
		return creditPayStatus;
	}

	public void setCreditPayStatus(String creditPayStatus) {
		this.creditPayStatus = creditPayStatus;
	}

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setChannelNo(String value) {
		this.channelNo = value;
	}
	
	public String getChannelNo() {
		return this.channelNo;
	}
	public void setAgentNo(String value) {
		this.agentNo = value;
	}
	
	public String getAgentNo() {
		return this.agentNo;
	}
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
	}
	public void setMerchantName(String value) {
		this.merchantName = value;
	}
	
	public String getMerchantName() {
		return this.merchantName;
	}
	public void setShortName(String value) {
		this.shortName = value;
	}
	
	public String getShortName() {
		return this.shortName;
	}
	public void setPlatformMerchantNo(String value) {
		this.platformMerchantNo = value;
	}
	
	public String getPlatformMerchantNo() {
		return this.platformMerchantNo;
	}
	public void setBusType(String value) {
		this.busType = value;
	}
	
	public String getBusType() {
		return this.busType;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setIdCard(String value) {
		this.idCard = value;
	}
	
	public String getIdCard() {
		return this.idCard;
	}
	public void setIdcardImg1(String value) {
		this.idcardImg1 = value;
	}
	
	public String getIdcardImg1() {
		return this.idcardImg1;
	}
	public void setIdcardImg2(String value) {
		this.idcardImg2 = value;
	}
	
	public String getIdcardImg2() {
		return this.idcardImg2;
	}
	public void setIdcardImg3(String value) {
		this.idcardImg3 = value;
	}
	
	public String getIdcardImg3() {
		return this.idcardImg3;
	}
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	public void setPhone(String value) {
		this.phone = value;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setEmail(String value) {
		this.email = value;
	}
	
	public String getEmail() {
		return this.email;
	}
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setMerchantLicense(String value) {
		this.merchantLicense = value;
	}
	
	public String getMerchantLicense() {
		return this.merchantLicense;
	}
	public void setMerchantLicenseImg(String value) {
		this.merchantLicenseImg = value;
	}
	
	public String getMerchantLicenseImg() {
		return this.merchantLicenseImg;
	}
	public void setTaxNo(String value) {
		this.taxNo = value;
	}
	
	public String getTaxNo() {
		return this.taxNo;
	}
	public void setTaxImg(String value) {
		this.taxImg = value;
	}
	
	public String getTaxImg() {
		return this.taxImg;
	}
	public void setOrgNo(String value) {
		this.orgNo = value;
	}
	
	public String getOrgNo() {
		return this.orgNo;
	}
	public void setQrCode(String value) {
		this.qrCode = value;
	}
	
	public String getQrCode() {
		return this.qrCode;
	}
	public void setAuthenStatus(Integer value) {
		this.authenStatus = value;
	}
	
	public Integer getAuthenStatus() {
		return this.authenStatus;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
		return this.operator;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setTemp1(String value) {
		this.temp1 = value;
	}
	
	public String getTemp1() {
		return this.temp1;
	}
	public void setTemp2(String value) {
		this.temp2 = value;
	}
	
	public String getTemp2() {
		return this.temp2;
	}
	public void setTemp3(String value) {
		this.temp3 = value;
	}
	
	public String getTemp3() {
		return this.temp3;
	}
	public void setTemp4(String value) {
		this.temp4 = value;
	}
	
	public String getTemp4() {
		return this.temp4;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

}

