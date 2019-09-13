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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "ID"),
	@SelectColumnMapping(property = "qrCode", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "QR_CODE"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "CHANNEL_NO"),
	@SelectColumnMapping(property = "channelName", type = java.lang.String.class, table = "t_channel_base", tableAlias = "C", column = "CHANNEL_NAME"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "AGENT_NO"),
	@SelectColumnMapping(property = "agentName", type = java.lang.String.class, table = "t_agent_base", tableAlias = "D", column = "AGENT_NAME"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "MERCHANT_NO"),
	@SelectColumnMapping(property = "merchantName", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "E", column = "MERCHANT_NAME"),
	@SelectColumnMapping(property = "qrName", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "QR_NAME"),
	@SelectColumnMapping(property = "image", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "IMAGE"),
	@SelectColumnMapping(property = "qrDesc", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "QR_DESC"),
	@SelectColumnMapping(property = "storeId", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "STORE_ID"),
	@SelectColumnMapping(property = "storeName", type = java.lang.String.class, table = "t_merchant_store", tableAlias = "F", column = "STORE_NAME"),
	@SelectColumnMapping(property = "useStatus", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "use_status"),
	@SelectColumnMapping(property = "qrStatus", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "QR_STATUS"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "RECORD_STATUS"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_platform_qrcode", tableAlias = "A", column = "CREATE_TIME"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_platform_qrcode", tableAlias = "A", column = "UPDATE_TIME"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "OPERATOR"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "REMARK"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "TEMP_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_platform_qrcode", tableAlias = "A", column = "TEMP_2")
})

@Generated("2016-10-20 13:57:56")
public class PlatformQrcode implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//id
	private String qrCode;//二维码编号
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String qrName;//二维码名称
	private String image;//二维码图片路径
	private String qrDesc;//二维码描述
	private String storeId;//商户的门店编号
	private String useStatus;//使用状态：1已使用 2未使用
	private String qrStatus;//状态：1启用 2禁用
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String operatorName;
	private String channelName;
	private String agentName;
	private String merchantName;
	private String storeName;

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setQrCode(String value) {
		this.qrCode = value;
	}
	
	public String getQrCode() {
		return this.qrCode;
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
	public void setQrName(String value) {
		this.qrName = value;
	}
	
	public String getQrName() {
		return this.qrName;
	}
	public void setImage(String value) {
		this.image = value;
	}
	
	public String getImage() {
		return this.image;
	}
	public void setQrDesc(String value) {
		this.qrDesc = value;
	}
	
	public String getQrDesc() {
		return this.qrDesc;
	}
	public void setStoreId(String value) {
		this.storeId = value;
	}
	
	public String getStoreId() {
		return this.storeId;
	}
	public void setUseStatus(String value) {
		this.useStatus = value;
	}
	
	public String getUseStatus() {
		return this.useStatus;
	}
	public void setQrStatus(String value) {
		this.qrStatus = value;
	}
	
	public String getQrStatus() {
		return this.qrStatus;
	}
	public void setRecordStatus(String value) {
		this.recordStatus = value;
	}
	
	public String getRecordStatus() {
		return this.recordStatus;
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

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	
}

