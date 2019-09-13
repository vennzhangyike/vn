package com.hfepay.scancode.commons.condition;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-10-26 13:55:39")
public class BankcardNatureConfCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//ID
	private String bankName;//发卡行名称
	private String bankCode;//发卡行机构代码
	private String bankCardName;//卡名称
	private String atm;//ATM
	private String pos;//POS
	private String track;//磁道
	private String trackStartChar;//磁道起始字节
	private String trackNum;//磁道长度
	private String acountStartChar;//账号起始字节
	private String acountNum;//账号长度
	private String mainAcount;//主帐号
	private String acountReadTrack;//账号读取磁道
	private String identStartChar;//标识起始字节
	private String identNum;//标识长度
	private String identValue;//取值
	private String identReadTrack;//标识读取磁道
	private String bankCardType;//卡种
	private String addCut;//本期增减
	private String telePhone;//银行客服电话

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setBankName(String value) {
		this.bankName = value;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	public void setBankCode(String value) {
		this.bankCode = value;
	}
	
	public String getBankCode() {
		return this.bankCode;
	}
	public void setBankCardName(String value) {
		this.bankCardName = value;
	}
	
	public String getBankCardName() {
		return this.bankCardName;
	}
	public void setAtm(String value) {
		this.atm = value;
	}
	
	public String getAtm() {
		return this.atm;
	}
	public void setPos(String value) {
		this.pos = value;
	}
	
	public String getPos() {
		return this.pos;
	}
	public void setTrack(String value) {
		this.track = value;
	}
	
	public String getTrack() {
		return this.track;
	}
	public void setTrackStartChar(String value) {
		this.trackStartChar = value;
	}
	
	public String getTrackStartChar() {
		return this.trackStartChar;
	}
	public void setTrackNum(String value) {
		this.trackNum = value;
	}
	
	public String getTrackNum() {
		return this.trackNum;
	}
	public void setAcountStartChar(String value) {
		this.acountStartChar = value;
	}
	
	public String getAcountStartChar() {
		return this.acountStartChar;
	}
	public void setAcountNum(String value) {
		this.acountNum = value;
	}
	
	public String getAcountNum() {
		return this.acountNum;
	}
	public void setMainAcount(String value) {
		this.mainAcount = value;
	}
	
	public String getMainAcount() {
		return this.mainAcount;
	}
	public void setAcountReadTrack(String value) {
		this.acountReadTrack = value;
	}
	
	public String getAcountReadTrack() {
		return this.acountReadTrack;
	}
	public void setIdentStartChar(String value) {
		this.identStartChar = value;
	}
	
	public String getIdentStartChar() {
		return this.identStartChar;
	}
	public void setIdentNum(String value) {
		this.identNum = value;
	}
	
	public String getIdentNum() {
		return this.identNum;
	}
	public void setIdentValue(String value) {
		this.identValue = value;
	}
	
	public String getIdentValue() {
		return this.identValue;
	}
	public void setIdentReadTrack(String value) {
		this.identReadTrack = value;
	}
	
	public String getIdentReadTrack() {
		return this.identReadTrack;
	}
	public void setBankCardType(String value) {
		this.bankCardType = value;
	}
	
	public String getBankCardType() {
		return this.bankCardType;
	}
	public void setAddCut(String value) {
		this.addCut = value;
	}
	
	public String getAddCut() {
		return this.addCut;
	}
	public void setTelePhone(String value) {
		this.telePhone = value;
	}
	
	public String getTelePhone() {
		return this.telePhone;
	}
}

