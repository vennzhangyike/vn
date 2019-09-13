/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-10-15 14:50:14")
public class ChannelAdminCondition extends PagingCondition {
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String userName;//用户名
	private String password;//密码
	private String salt;//加密盐
	private Integer status;//状态(0禁用,1可用)
	private Date createTime;//创建时间
	private String identityNo;

	private String email;
    private String phone;
    private String shortName;
    private String channelCode;
    
    private String agentNo;
    private String merchantNo;
    private String identityFlag;
    
    private String validateCode;
    private String againPwd;
    private String openId;
    
    private String nodeSeq;//当前节点的标识
    
    public String getNodeSeq() {
		return nodeSeq;
	}

	public void setNodeSeq(String nodeSeq) {
		this.nodeSeq = nodeSeq;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
		
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setPassword(String value) {
		this.password = value;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setSalt(String value) {
		this.salt = value;
	}
	
	public String getSalt() {
		return this.salt;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}

	public String getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getIdentityFlag() {
		return identityFlag;
	}

	public void setIdentityFlag(String identityFlag) {
		this.identityFlag = identityFlag;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getAgainPwd() {
		return againPwd;
	}

	public void setAgainPwd(String againPwd) {
		this.againPwd = againPwd;
	}
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	
}

