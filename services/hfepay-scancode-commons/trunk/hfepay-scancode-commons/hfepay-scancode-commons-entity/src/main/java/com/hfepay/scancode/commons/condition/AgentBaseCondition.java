/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;
import java.util.Date;

@Generated("2016-10-19 16:04:33")
public class AgentBaseCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//id
	private String agentNo;//代理商编号
	private String agentName;//代理商名称
	private String channelNo;//渠道编号
	private String agentPreCode;//编码抬头
	private String agentType;//代理商类型：0 个人、1 机构
	private String name;//联系人姓名
	private String mobile;//联系人手机号码
	private String parentNo;//上级编号
	private String agentLevel;//代理商层级
	private Integer qrTotal;//总二维码数量
	private Integer useTotal;//已使用二维码数量
	private Integer lessTotal;//剩余二维码数量
	private String agentFlag;//是否为渠道默认代理商
	private String status;//状态，1：正常，2：关闭
	private String recordStatus;//记录状态，Y:正常，N:删除
	private Date createTime;//创建时间
	private Date updateTime;//修改时间
	private String operator;//操作人账号
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String temp3;//temp3
	private String temp4;//temp4
	private String parentName;//上级名称

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
	public void setAgentName(String value) {
		this.agentName = value;
	}
	
	public String getAgentName() {
		return this.agentName;
	}
	public void setChannelNo(String value) {
		this.channelNo = value;
	}
	
	public String getChannelNo() {
		return this.channelNo;
	}
	public void setAgentPreCode(String value) {
		this.agentPreCode = value;
	}
	
	public String getAgentPreCode() {
		return this.agentPreCode;
	}
	public void setAgentType(String value) {
		this.agentType = value;
	}
	
	public String getAgentType() {
		return this.agentType;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	public void setParentNo(String value) {
		this.parentNo = value;
	}
	
	public String getParentNo() {
		return this.parentNo;
	}
	public void setAgentLevel(String value) {
		this.agentLevel = value;
	}
	
	public String getAgentLevel() {
		return this.agentLevel;
	}
	public void setQrTotal(Integer value) {
		this.qrTotal = value;
	}
	
	public Integer getQrTotal() {
		return this.qrTotal;
	}
	public void setUseTotal(Integer value) {
		this.useTotal = value;
	}
	
	public Integer getUseTotal() {
		return this.useTotal;
	}
	public void setLessTotal(Integer value) {
		this.lessTotal = value;
	}
	
	public Integer getLessTotal() {
		return this.lessTotal;
	}
	
	public String getAgentFlag() {
		return agentFlag;
	}

	public void setAgentFlag(String agentFlag) {
		this.agentFlag = agentFlag;
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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}

