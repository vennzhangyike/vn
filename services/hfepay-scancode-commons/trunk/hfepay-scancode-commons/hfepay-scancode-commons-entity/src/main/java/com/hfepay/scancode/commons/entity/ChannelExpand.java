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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "channelName", type = java.lang.String.class, table = "t_channel_base", tableAlias = "C", column = "channel_name"),
	@SelectColumnMapping(property = "channelCode", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "channel_code"),
	@SelectColumnMapping(property = "channelPreCode", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "channel_pre_code"),
	@SelectColumnMapping(property = "nickName", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "nick_name"),
	@SelectColumnMapping(property = "indexTopImg", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "index_top_img"),
	@SelectColumnMapping(property = "indexBottomImg", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "index_bottom_img"),
	@SelectColumnMapping(property = "indexWxImg", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "index_wx_img"),
	@SelectColumnMapping(property = "address", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "address"),
	@SelectColumnMapping(property = "phone", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "phone"),
	@SelectColumnMapping(property = "technicalSupportEmail", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "technical_support_email"),
	@SelectColumnMapping(property = "qqGroup", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "qq_group"),
	@SelectColumnMapping(property = "customServiceQq", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "custom_service_qq"),
	@SelectColumnMapping(property = "businessCooperationQq", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "business_cooperation_qq"),
	@SelectColumnMapping(property = "businessCooperationEmail", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "business_cooperation_email"),
	@SelectColumnMapping(property = "microblogUrl", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "microblog_url"),
	@SelectColumnMapping(property = "contactAddressImg", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "contact_address_img"),
	@SelectColumnMapping(property = "loginBackgroundImg", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "login_background_img"),
	@SelectColumnMapping(property = "centerHeadImg", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "center_head_img"),
	@SelectColumnMapping(property = "companyName", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "company_name"),
	@SelectColumnMapping(property = "preCode", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "pre_code"),
	@SelectColumnMapping(property = "recordNumber", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "record_number"),
	@SelectColumnMapping(property = "icon", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "icon"),
	@SelectColumnMapping(property = "title", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "title"),
	@SelectColumnMapping(property = "keyWords", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "key_words"),
	@SelectColumnMapping(property = "description", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "description"),
	@SelectColumnMapping(property = "domainName", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "domain_name"),
	@SelectColumnMapping(property = "telephone", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "telephone"),
	@SelectColumnMapping(property = "aboutUs", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "about_us"),
	@SelectColumnMapping(property = "isActive", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "is_active"),
	@SelectColumnMapping(property = "status", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "status"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_channel_expand", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_channel_expand", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_channel_expand", tableAlias = "A", column = "temp_2")
})

@Generated("2016-10-13 17:20:57")
public class ChannelExpand implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String channelName;//渠道名称
	private String channelCode;//二级域名编号
	private String channelPreCode;//编码抬头
	private String nickName;//昵称
	private String indexTopImg;//首页上部logo图
	private String indexBottomImg;//首页底部logo图
	private String indexWxImg;//官方微信公众号二维码
	private String address;//地址
	private String phone;//电话
	private String technicalSupportEmail;//技术支持邮箱
	private String qqGroup;//qq群 多个逗号隔开
	private String customServiceQq;//客服qq
	private String businessCooperationQq;//商务qq
	private String businessCooperationEmail;//商务邮箱
	private String microblogUrl;//官方微博url
	private String contactAddressImg;//联系地址图
	private String loginBackgroundImg;//登陆背景图
	private String centerHeadImg;//个人中心头部图
	private String companyName;//公司名称---全称
	private String preCode;//编码抬头
	private String recordNumber;//备案号
	private String icon;//渠道图标
	private String title;//渠道官网seo标题
	private String keyWords;//渠道官网seo关键字
	private String description;//渠道官网seo描述
	private String domainName;//域名
	private String telephone;//电话号码,phone为400电话
	private String aboutUs;//关于我们
	private String helpInfo;//帮助中心
	private String isActive;//y有效   n无效
	private String status;//状态
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人
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
	public void setChannelCode(String value) {
		this.channelCode = value;
	}
	
	public String getChannelCode() {
		return this.channelCode;
	}
	public void setChannelPreCode(String value) {
		this.channelPreCode = value;
	}
	
	public String getChannelPreCode() {
		return this.channelPreCode;
	}
	public void setNickName(String value) {
		this.nickName = value;
	}
	
	public String getNickName() {
		return this.nickName;
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
	public void setCompanyName(String value) {
		this.companyName = value;
	}
	
	public String getCompanyName() {
		return this.companyName;
	}
	public void setPreCode(String value) {
		this.preCode = value;
	}
	
	public String getPreCode() {
		return this.preCode;
	}
	public void setRecordNumber(String value) {
		this.recordNumber = value;
	}
	
	public String getRecordNumber() {
		return this.recordNumber;
	}
	public void setIcon(String value) {
		this.icon = value;
	}
	
	public String getIcon() {
		return this.icon;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setKeyWords(String value) {
		this.keyWords = value;
	}
	
	public String getKeyWords() {
		return this.keyWords;
	}
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}
	public void setDomainName(String value) {
		this.domainName = value;
	}
	
	public String getDomainName() {
		return this.domainName;
	}
	public void setTelephone(String value) {
		this.telephone = value;
	}
	
	public String getTelephone() {
		return this.telephone;
	}
	public void setAboutUs(String value) {
		this.aboutUs = value;
	}
	
	public String getAboutUs() {
		return this.aboutUs;
	}
	public void setIsActive(String value) {
		this.isActive = value;
	}
	
	public String getIsActive() {
		return this.isActive;
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

	public String getHelpInfo() {
		return helpInfo;
	}

	public void setHelpInfo(String helpInfo) {
		this.helpInfo = helpInfo;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	
}

