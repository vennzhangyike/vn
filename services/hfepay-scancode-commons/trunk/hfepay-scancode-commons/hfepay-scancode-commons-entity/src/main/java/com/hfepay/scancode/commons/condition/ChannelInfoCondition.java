/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-06-15 20:42:11")
public class ChannelInfoCondition extends PagingCondition {

	private static final long serialVersionUID = 1L;

	private String id;// ID
	private String code;// 渠道代码
	private String number;// 渠道编号
	private String nickName;// 昵称
	private String status;// 状态
	private String indexTopImg;// 首页上部logo图
	private String indexBottomImg;// 首页底部logo图
	private String indexWxImg;// 官方微信公众号二维码
	private String address;// 地址
	private String phone;// 电话
	private String technicalSupportEmail;// 技术支持邮箱
	private String qqGroup;// QQ群 多个逗号隔开
	private String customServiceQq;// 客服QQ
	private String businessCooperationQq;// 商务QQ
	private String businessCooperationEmail;// 商务邮箱
	private String microblogUrl;// 官方微博URL
	private String contactAddressImg;// 联系地址图
	private String loginBackgroundImg;// 登陆背景图
	private String centerHeadImg;// 个人中心头部图
	private String isActive;// Y有效 N无效
	private Date createTime;// 创建时间
	private Date updateTime;// 修改时间
	private String operatorId;// 操作人

	private String remark;// 备注
	private String companyName;// 公司名称---全称
	private String preCode;// 编码抬头
	private String recordNumber;// 备案号
	private String aboutUs;// 關於我們
	private String icon;// 渠道图标
	private String title;// 渠道官网SEO标题
	private String keyWords;// 渠道官网SEO关键字
	private String description;// 渠道官网SEO描述
	private String paySwitch;// 渠道支付开关
	private String skin;// 皮肤
	private String domainName;// 域名
	private String telephone;// 电话号码,phone为400电话

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSkin() {
		return skin;
	}

	public void setSkin(String skin) {
		this.skin = skin;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getPaySwitch() {
		return paySwitch;
	}

	public void setPaySwitch(String paySwitch) {
		this.paySwitch = paySwitch;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getAboutUs() {
		return aboutUs;
	}

	public void setAboutUs(String aboutUs) {
		this.aboutUs = aboutUs;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPreCode() {
		return preCode;
	}

	public void setPreCode(String preCode) {
		this.preCode = preCode;
	}

	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	public void setId(String value) {
		this.id = value;
	}

	public String getId() {
		return this.id;
	}

	public void setCode(String value) {
		this.code = value;
	}

	public String getCode() {
		return this.code;
	}

	public void setNumber(String value) {
		this.number = value;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNickName(String value) {
		this.nickName = value;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setStatus(String value) {
		this.status = value;
	}

	public String getStatus() {
		return this.status;
	}

	public void setIndexTopImg(String value) {
		this.indexTopImg = value;
	}

	public String getIndexTopImg() {
		return this.indexTopImg;
	}

	public void setIndexBottomImg(String value) {
		this.indexBottomImg = value;
	}

	public String getIndexBottomImg() {
		return this.indexBottomImg;
	}

	public void setIndexWxImg(String value) {
		this.indexWxImg = value;
	}

	public String getIndexWxImg() {
		return this.indexWxImg;
	}

	public void setAddress(String value) {
		this.address = value;
	}

	public String getAddress() {
		return this.address;
	}

	public void setPhone(String value) {
		this.phone = value;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setTechnicalSupportEmail(String value) {
		this.technicalSupportEmail = value;
	}

	public String getTechnicalSupportEmail() {
		return this.technicalSupportEmail;
	}

	public void setQqGroup(String value) {
		this.qqGroup = value;
	}

	public String getQqGroup() {
		return this.qqGroup;
	}

	public void setCustomServiceQq(String value) {
		this.customServiceQq = value;
	}

	public String getCustomServiceQq() {
		return this.customServiceQq;
	}

	public void setBusinessCooperationQq(String value) {
		this.businessCooperationQq = value;
	}

	public String getBusinessCooperationQq() {
		return this.businessCooperationQq;
	}

	public void setBusinessCooperationEmail(String value) {
		this.businessCooperationEmail = value;
	}

	public String getBusinessCooperationEmail() {
		return this.businessCooperationEmail;
	}

	public void setMicroblogUrl(String value) {
		this.microblogUrl = value;
	}

	public String getMicroblogUrl() {
		return this.microblogUrl;
	}

	public void setContactAddressImg(String value) {
		this.contactAddressImg = value;
	}

	public String getContactAddressImg() {
		return this.contactAddressImg;
	}

	public void setLoginBackgroundImg(String value) {
		this.loginBackgroundImg = value;
	}

	public String getLoginBackgroundImg() {
		return this.loginBackgroundImg;
	}

	public void setCenterHeadImg(String value) {
		this.centerHeadImg = value;
	}

	public String getCenterHeadImg() {
		return this.centerHeadImg;
	}

	public void setIsActive(String value) {
		this.isActive = value;
	}

	public String getIsActive() {
		return this.isActive;
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
}
