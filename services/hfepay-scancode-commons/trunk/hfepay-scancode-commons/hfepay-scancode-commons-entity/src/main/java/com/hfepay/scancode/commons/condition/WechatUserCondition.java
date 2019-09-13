/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2017-01-03 15:54:28")
public class WechatUserCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键ID
	private String identityNo;//channeladmin表中的identityno
	private String userType;//平台用户类型，可以是商户（收银员）或者关注公众号的用户但不是商户等
	private String subscribe;//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	private String openid;//用户的标识，对当前公众号唯一
	private String nickname;//用户的昵称
	private String sex;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String city;//用户所在城市
	private String country;//用户所在国家
	private String province;//用户所在省份
	private String language;//用户的语言，简体中文为zh_CN
	private String headimgurl;//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	private Date subscribeTime;//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	private String unionid;//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	private String remark;//公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	private String groupid;//用户所在的分组ID（兼容旧的用户分组接口）
	private String tagidList;//用户被打上的标签ID列表(实际上一个数组，保存的时候转换为以，分隔的字符串)
	private Date createTime;//获取用户微信信息时间
	private Date updateTime;//修改时间
	private String temp1;//备用字段
	private String temp2;//备用字段
	private String temp3;//备用字段
	private String temp4;//备用字段

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setIdentityNo(String value) {
		this.identityNo = value;
	}
	
	public String getIdentityNo() {
		return this.identityNo;
	}
	public void setUserType(String value) {
		this.userType = value;
	}
	
	public String getUserType() {
		return this.userType;
	}
	public void setSubscribe(String value) {
		this.subscribe = value;
	}
	
	public String getSubscribe() {
		return this.subscribe;
	}
	public void setOpenid(String value) {
		this.openid = value;
	}
	
	public String getOpenid() {
		return this.openid;
	}
	public void setNickname(String value) {
		this.nickname = value;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	public void setSex(String value) {
		this.sex = value;
	}
	
	public String getSex() {
		return this.sex;
	}
	public void setCity(String value) {
		this.city = value;
	}
	
	public String getCity() {
		return this.city;
	}
	public void setCountry(String value) {
		this.country = value;
	}
	
	public String getCountry() {
		return this.country;
	}
	public void setProvince(String value) {
		this.province = value;
	}
	
	public String getProvince() {
		return this.province;
	}
	public void setLanguage(String value) {
		this.language = value;
	}
	
	public String getLanguage() {
		return this.language;
	}
	public void setHeadimgurl(String value) {
		this.headimgurl = value;
	}
	
	public String getHeadimgurl() {
		return this.headimgurl;
	}
	public void setSubscribeTime(Date value) {
		this.subscribeTime = value;
	}
	
	public Date getSubscribeTime() {
		return this.subscribeTime;
	}
	public void setUnionid(String value) {
		this.unionid = value;
	}
	
	public String getUnionid() {
		return this.unionid;
	}
	public void setRemark(String value) {
		this.remark = value;
	}
	
	public String getRemark() {
		return this.remark;
	}
	public void setGroupid(String value) {
		this.groupid = value;
	}
	
	public String getGroupid() {
		return this.groupid;
	}
	public void setTagidList(String value) {
		this.tagidList = value;
	}
	
	public String getTagidList() {
		return this.tagidList;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}

