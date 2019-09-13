/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-10-24 10:47:29")
public class MerchantQrcodeCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键ID
	private String qrCode;//二维码编号
	private String merchantNo;//商户编号
	private String merchantName;//商户名称
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String qrName;//二维码名称
	private String qrDesc;//二维码描述
	private String qrType;//二维码类型
	private String image;//二维码图片路径
	private String storeId;//商户的门店编号
	private String storeName;//商户的门店名称
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
	
	private String nodeSeq;//当前节点的标识
    
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
	public void setMerchantName(String value) {
		this.merchantName = value;
	}
	
	public String getMerchantName() {
		return this.merchantName;
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
	public void setStoreName(String value) {
		this.storeName = value;
	}
	
	public String getStoreName() {
		return this.storeName;
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

	public String getQrType() {
		return qrType;
	}

	public void setQrType(String qrType) {
		this.qrType = qrType;
	}
}

