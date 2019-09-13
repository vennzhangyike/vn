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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "qrCode", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "qr_code"),
	@SelectColumnMapping(property = "merchantNo", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "merchant_no"),
	@SelectColumnMapping(property = "merchantName", type = java.lang.String.class, table = "t_merchant_info", tableAlias = "C", column = "merchant_name"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "qrName", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "qr_name"),
	@SelectColumnMapping(property = "qrDesc", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "qr_desc"),
	@SelectColumnMapping(property = "qrType", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "qr_Type"),
	@SelectColumnMapping(property = "image", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "image"),
	@SelectColumnMapping(property = "storeId", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "store_id"),
	@SelectColumnMapping(property = "storeName", type = java.lang.String.class, table = "t_merchant_store", tableAlias = "D", column = "store_name"),
	@SelectColumnMapping(property = "qrStatus", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "qr_status"),
	@SelectColumnMapping(property = "recordStatus", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "record_status"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_merchant_qrcode", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = Date.class, table = "t_merchant_qrcode", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "temp_2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "temp_3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_merchant_qrcode", tableAlias = "A", column = "temp_4")
})

@Generated("2016-10-24 10:47:29")
public class MerchantQrcode implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键ID
	private String qrCode;//二维码编号
	private String merchantNo;//商户编号
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String qrName;//二维码名称
	private String qrDesc;//二维码描述
	private String image;//二维码图片路径
	private String storeId;//商户的门店编号
	private String qrStatus;//状态：1启用 2禁用
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4
	private String operatorName;	
	private String qrType;
	private String merchantName;
	private String storeName;
	private String channelName;
	private String agentName;

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
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
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
	public void setQrName(String value) {
		this.qrName = value;
	}
	
	public String getQrName() {
		return this.qrName;
	}
	public void setQrDesc(String value) {
		this.qrDesc = value;
	}
	
	public String getQrDesc() {
		return this.qrDesc;
	}
	public void setImage(String value) {
		this.image = value;
	}
	
	public String getImage() {
		return this.image;
	}
	public void setStoreId(String value) {
		this.storeId = value;
	}
	
	public String getStoreId() {
		return this.storeId;
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

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getQrType() {
		return qrType;
	}

	public void setQrType(String qrType) {
		this.qrType = qrType;
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

}

