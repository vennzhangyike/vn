/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-10-21 10:21:58")
public class MerchantStoreCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//id
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String storeNo;//门店编号
	private String storeName;//门店名称
	private String storeType;//门店级别：0总店 1 分店
	private String servicePhone;//服务电话
	private String serviceBegin;//营业开始时间
	private String serviceEnd;//营业结束时间
	private String storeAddress;//门店地址
	private String storeAddressImg;//门店地址图片URL
	private String storeDesc;//门店介绍
	private String licenseName;//执照名称
	private String name;//法人姓名
	private String idCard;//身份证号
	private String idcardImg1;//身份证正面
	private String idcardImg2;//身份证反面
	private String idcardImg3;//手持身份证
	private String merchantLicense;//商户营业执照号
	private String merchantLicenseImg;//营业执照图片
	private String storePhotosImg;//门店图片
	private String groupPhotoImg;//法人门店图片
	private String storeImg;//店内图片
	private String isPhoto;//是否有相册照片 0 无 1 有
	private String operator;//门店创建人
	private Date createTime;//门店创建时间
	private String auditOperator;//审核人
	private Date auditDate;//审核日期
	private String auditReson;//审核理由
	private String storeStatus;//门店状态 0 初始 1 待审核 2 审核中 3 审核通过 4审核拒绝
	private String recordStatus;//记录状态
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4
	
	private String busType;//经营类型
	private String jsapiTicket;//检测当前的jsapiticket是否过期
	
	private String channelNo;

	private String nodeSeq;//当前节点的标识
	private String merchantName;//商户名称
    
    public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public String getNodeSeq() {
		return nodeSeq;
	}

	public void setNodeSeq(String nodeSeq) {
		this.nodeSeq = nodeSeq;
	}	
	
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
	}
	public void setStoreNo(String value) {
		this.storeNo = value;
	}
	
	public String getStoreNo() {
		return this.storeNo;
	}
	public void setStoreName(String value) {
		this.storeName = value;
	}
	
	public String getStoreName() {
		return this.storeName;
	}
	public void setStoreType(String value) {
		this.storeType = value;
	}
	
	public String getStoreType() {
		return this.storeType;
	}
	public void setServicePhone(String value) {
		this.servicePhone = value;
	}
	
	public String getServicePhone() {
		return this.servicePhone;
	}
	public void setServiceBegin(String value) {
		this.serviceBegin = value;
	}
	
	public String getServiceBegin() {
		return this.serviceBegin;
	}
	public void setServiceEnd(String value) {
		this.serviceEnd = value;
	}
	
	public String getServiceEnd() {
		return this.serviceEnd;
	}
	public void setStoreAddress(String value) {
		this.storeAddress = value;
	}
	
	public String getStoreAddress() {
		return this.storeAddress;
	}
	public void setStoreAddressImg(String value) {
		this.storeAddressImg = value;
	}
	
	public String getStoreAddressImg() {
		return this.storeAddressImg;
	}
	public void setStoreDesc(String value) {
		this.storeDesc = value;
	}
	
	public String getStoreDesc() {
		return this.storeDesc;
	}
	public void setLicenseName(String value) {
		this.licenseName = value;
	}
	
	public String getLicenseName() {
		return this.licenseName;
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
	public void setStorePhotosImg(String value) {
		this.storePhotosImg = value;
	}
	
	public String getStorePhotosImg() {
		return this.storePhotosImg;
	}
	public void setGroupPhotoImg(String value) {
		this.groupPhotoImg = value;
	}
	
	public String getGroupPhotoImg() {
		return this.groupPhotoImg;
	}
	public void setStoreImg(String value) {
		this.storeImg = value;
	}
	
	public String getStoreImg() {
		return this.storeImg;
	}
	public void setIsPhoto(String value) {
		this.isPhoto = value;
	}
	
	public String getIsPhoto() {
		return this.isPhoto;
	}
	public void setOperator(String value) {
		this.operator = value;
	}
	
	public String getOperator() {
		return this.operator;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setAuditOperator(String value) {
		this.auditOperator = value;
	}
	
	public String getAuditOperator() {
		return this.auditOperator;
	}
	public void setAuditDate(Date value) {
		this.auditDate = value;
	}
	
	public Date getAuditDate() {
		return this.auditDate;
	}
	public void setAuditReson(String value) {
		this.auditReson = value;
	}
	
	public String getAuditReson() {
		return this.auditReson;
	}
	public void setStoreStatus(String value) {
		this.storeStatus = value;
	}
	
	public String getStoreStatus() {
		return this.storeStatus;
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

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getJsapiTicket() {
		return jsapiTicket;
	}

	public void setJsapiTicket(String jsapiTicket) {
		this.jsapiTicket = jsapiTicket;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	
}

