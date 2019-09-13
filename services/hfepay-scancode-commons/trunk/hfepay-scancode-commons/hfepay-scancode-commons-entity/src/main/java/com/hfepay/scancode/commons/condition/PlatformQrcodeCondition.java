/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-10-20 13:57:56")
public class PlatformQrcodeCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//id
	private String qrCode;//二维码编号
	private String channelNo;//渠道编号
	private String channelName;//渠道名称
	private String agentNo;//代理商编号
	private String agentName;//代理商名称
	private String merchantNo;//商户编号
	private String merchantName;//商户名称
	private String qrName;//二维码名称
	private String image;//二维码图片路径
	private String qrDesc;//二维码描述
	private String storeId;//商户的门店编号
	private String storeName;//商户的门店名称
	private String useStatus;//使用状态：1已使用 2未使用
	private String qrStatus;//状态：1启用 2禁用
	private String recordStatus;//记录状态
	private Date createTime;//创建时间
	private Date updateTime;//创建时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2

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
	public void setChannelName(String value) {
		this.channelName = value;
	}
	
	public String getChannelName() {
		return this.channelName;
	}
	public void setAgentNo(String value) {
		this.agentNo = value;
	}
	
	public String getAgentNo() {
		return this.agentNo;
	}
	public void setAgentName(String value) {
		this.agentName = value;
	}
	
	public String getAgentName() {
		return this.agentName;
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
	public void setStoreName(String value) {
		this.storeName = value;
	}
	
	public String getStoreName() {
		return this.storeName;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}

