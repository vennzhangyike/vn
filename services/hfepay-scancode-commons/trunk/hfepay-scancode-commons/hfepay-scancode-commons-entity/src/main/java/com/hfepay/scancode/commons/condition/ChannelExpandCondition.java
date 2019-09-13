/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-10-13 15:29:52")
public class ChannelExpandCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
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
	private String agreement;//电子协议
	private String isActive;//y有效   n无效
	private String status;//状态
	private Date createTime;//创建时间
	private String operator;//操作人
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	//公众号信息
	private String appid;//公众号ID
	private String secret;//公众号key
	private String registerTemplateId;//注册消息模板ID
	private String payTemplateId;//支付消息模板ID
	private String withDrawsTemplateId;//提现模版ID
	private String unperfectTemplateId;//商户待完善模版ID
	
	//短信信息
	private String smsuser;//短信用户名
	private String smspassword;//短信密码
	private String sendUrl;//短信发送地址
	
	//银行账户信息
	private String bankCode;//银行开户行代码
	private String bankName;//开户行银行名称（支行）
	private String bankCard;//银行卡号码
	private String idCard;//身份证号码
	private String name;//姓名
	private String mobile;//手机号码
	private String accountType;//是否对公对私，1：对公，0：对私
	
	public String getSmsuser() {
		return smsuser;
	}

	public void setSmsuser(String smsuser) {
		this.smsuser = smsuser;
	}

	public String getSmspassword() {
		return smspassword;
	}

	public void setSmspassword(String smspassword) {
		this.smspassword = smspassword;
	}

	public String getSendUrl() {
		return sendUrl;
	}

	public void setSendUrl(String sendUrl) {
		this.sendUrl = sendUrl;
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
	public void setChannelName(String value) {
		this.channelName = value;
	}
	
	public String getChannelName() {
		return this.channelName;
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
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRegisterTemplateId() {
		return registerTemplateId;
	}

	public void setRegisterTemplateId(String registerTemplateId) {
		this.registerTemplateId = registerTemplateId;
	}

	public String getPayTemplateId() {
		return payTemplateId;
	}

	public void setPayTemplateId(String payTemplateId) {
		this.payTemplateId = payTemplateId;
	}

	public String getHelpInfo() {
		return helpInfo;
	}

	public void setHelpInfo(String helpInfo) {
		this.helpInfo = helpInfo;
	}

	public String getWithDrawsTemplateId() {
		return withDrawsTemplateId;
	}

	public void setWithDrawsTemplateId(String withDrawsTemplateId) {
		this.withDrawsTemplateId = withDrawsTemplateId;
	}

	public String getAgreement() {
		return agreement;
	}

	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}

	public String getUnperfectTemplateId() {
		return unperfectTemplateId;
	}

	public void setUnperfectTemplateId(String unperfectTemplateId) {
		this.unperfectTemplateId = unperfectTemplateId;
	}
	
}

