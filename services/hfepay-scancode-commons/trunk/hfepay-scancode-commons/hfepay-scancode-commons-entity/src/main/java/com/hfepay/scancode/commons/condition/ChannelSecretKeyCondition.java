/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-09-14 10:05:58")
public class ChannelSecretKeyCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//ID
	private String channelNo;//渠道编号
	private String companyName;//公司名称
	private String channelType;//渠道类型
	private String firstPayobj;//优选通道
	private String payCode;//华付通道编号
	private String payName;//华付通道名称
	private String boundIp;//绑定IP
	private String joinUserPublicKey;//用户上传公钥
	private String joinPublicKey;//平台公钥
	private String joinPrivateKey;//平台私钥
	private String joinKey;//接入加解密KEY
	private String joinType;//接入方式1：密文接入，2：明文接入
	private String status;//秘钥状态，1：有效，2：无效或禁止
	private String recordStatus;//记录状态
	private Date createTime;//创建日期
	private Date updateTime;//修改日期
	private String operatorId;//操作人ID
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2

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
	public void setCompanyName(String value) {
		this.companyName = value;
	}
	
	public String getCompanyName() {
		return this.companyName;
	}
	public void setChannelType(String value) {
		this.channelType = value;
	}
	
	public String getChannelType() {
		return this.channelType;
	}
	public void setFirstPayobj(String value) {
		this.firstPayobj = value;
	}
	
	public String getFirstPayobj() {
		return this.firstPayobj;
	}
	public void setPayCode(String value) {
		this.payCode = value;
	}
	
	public String getPayCode() {
		return this.payCode;
	}
	public void setPayName(String value) {
		this.payName = value;
	}
	
	public String getPayName() {
		return this.payName;
	}
	public void setBoundIp(String value) {
		this.boundIp = value;
	}
	
	public String getBoundIp() {
		return this.boundIp;
	}
	public void setJoinPublicKey(String value) {
		this.joinPublicKey = value;
	}
	
	public String getJoinPublicKey() {
		return this.joinPublicKey;
	}
	
	public String getJoinUserPublicKey() {
		return joinUserPublicKey;
	}

	public void setJoinUserPublicKey(String joinUserPublicKey) {
		this.joinUserPublicKey = joinUserPublicKey;
	}

	public String getJoinPrivateKey() {
		return joinPrivateKey;
	}

	public void setJoinPrivateKey(String joinPrivateKey) {
		this.joinPrivateKey = joinPrivateKey;
	}

	public void setJoinKey(String value) {
		this.joinKey = value;
	}
	
	public String getJoinKey() {
		return this.joinKey;
	}
	public void setJoinType(String value) {
		this.joinType = value;
	}
	
	public String getJoinType() {
		return this.joinType;
	}
	public void setStatus(String value) {
		this.status = value;
	}
	
	public String getStatus() {
		return this.status;
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
	public void setOperatorId(String value) {
		this.operatorId = value;
	}
	
	public String getOperatorId() {
		return this.operatorId;
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
}

