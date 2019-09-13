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
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "title", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "title"),
	@SelectColumnMapping(property = "content", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "content"),
	@SelectColumnMapping(property = "address", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "address"),
	@SelectColumnMapping(property = "qrAddress", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "qr_address"),
	@SelectColumnMapping(property = "qrImgAddress", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "qr_img_address"),
	@SelectColumnMapping(property = "createTime", type = java.util.Date.class, table = "t_agent_promotion", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "updateTime", type = java.util.Date.class, table = "t_agent_promotion", tableAlias = "A", column = "update_time"),
	@SelectColumnMapping(property = "operator", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "operator"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "remark"),
	@SelectColumnMapping(property = "temp1", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "temp_1"),
	@SelectColumnMapping(property = "temp2", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "temp_2"),
	@SelectColumnMapping(property = "temp3", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "temp_3"),
	@SelectColumnMapping(property = "temp4", type = java.lang.String.class, table = "t_agent_promotion", tableAlias = "A", column = "temp_4")
})

@Generated("2016-11-28 09:57:59")
public class AgentPromotion implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	private String id;//主键id
	private String agentNo;//代理商编号
	private String title;//推广标题
	private String content;//推广内容
	private String address;//推广地址
	private String qrAddress;//注册二维码地址
	private String qrImgAddress;//注册码图片地址
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4

	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setAgentNo(String value) {
		this.agentNo = value;
	}
	
	public String getAgentNo() {
		return this.agentNo;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setAddress(String value) {
		this.address = value;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setQrAddress(String value) {
		this.qrAddress = value;
	}
	
	public String getQrAddress() {
		return this.qrAddress;
	}
	public void setQrImgAddress(String value) {
		this.qrImgAddress = value;
	}
	
	public String getQrImgAddress() {
		return this.qrImgAddress;
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

	
}

