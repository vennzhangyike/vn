/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.commons.condition;

import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-11-21 17:19:45")
public class MerchantAuthDetailCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键id
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String merchantNo;//商户编号
	private String authInterface;//认证接口
	private String authParams;//认证参数
	private String returnAuthCode;//认证返回码
	private String returnAuthMsg;//认证描述
	private Date createTime;//认证时间
	private String remark;//备注
	private String temp1;//temp1
	private String temp2;//temp2
	private String nodeSeq;//当前节点的标识
	private String beginTimeStr;//开始时间
	private String endTimeStr;//结束时间
	
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
	public void setAgentNo(String value) {
		this.agentNo = value;
	}
	
	public String getAgentNo() {
		return this.agentNo;
	}
	public void setMerchantNo(String value) {
		this.merchantNo = value;
	}
	
	public String getMerchantNo() {
		return this.merchantNo;
	}
	public void setAuthInterface(String value) {
		this.authInterface = value;
	}
	
	public String getAuthInterface() {
		return this.authInterface;
	}
	public void setAuthParams(String value) {
		this.authParams = value;
	}
	
	public String getAuthParams() {
		return this.authParams;
	}
	public void setReturnAuthCode(String value) {
		this.returnAuthCode = value;
	}
	
	public String getReturnAuthCode() {
		return this.returnAuthCode;
	}
	public void setReturnAuthMsg(String value) {
		this.returnAuthMsg = value;
	}
	
	public String getReturnAuthMsg() {
		return this.returnAuthMsg;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	
	public Date getCreateTime() {
		return this.createTime;
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
    
    public String getNodeSeq() {
		return nodeSeq;
	}

	public void setNodeSeq(String nodeSeq) {
		this.nodeSeq = nodeSeq;
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
	
}

