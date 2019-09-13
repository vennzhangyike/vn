/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.condition;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-12-20 11:30:01")
public class HierarchicalSettlementTotalCondition extends PagingCondition {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键ID
	private String batchNo;//结算批次号
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String agentLevel;//代理商层级
	private String parentNo;//上级编号
	private BigDecimal tranTotalAmount;//交易总金额
	private BigDecimal tranProfit;//交易分润金额
	private BigDecimal outTotalAmount;//已提现总金额
	private BigDecimal outProfit;//提现分润金额
	private BigDecimal totalProfit;//总利润
	private String settleDate;//结算日期
	private Date createTime;//创建日期
	private String remark;//备注

	private String queryStartDate;//查询的时间
	private String queryEndDate;//查询的时间
	private String organNo;//机构编号编号
	private String nodeSeq;//针对渠道自己查询自己的记录，需要做一个特殊处理
	private String identityFlag;
	public void setId(String value) {
		this.id = value;
	}
	
	public String getId() {
		return this.id;
	}
	public void setBatchNo(String value) {
		this.batchNo = value;
	}
	
	public String getBatchNo() {
		return this.batchNo;
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
	public void setAgentLevel(String value) {
		this.agentLevel = value;
	}
	
	public String getAgentLevel() {
		return this.agentLevel;
	}
	public void setParentNo(String value) {
		this.parentNo = value;
	}
	
	public String getParentNo() {
		return this.parentNo;
	}
	public void setTranTotalAmount(BigDecimal value) {
		this.tranTotalAmount = value;
	}
	
	public BigDecimal getTranTotalAmount() {
		return this.tranTotalAmount;
	}
	public void setTranProfit(BigDecimal value) {
		this.tranProfit = value;
	}
	
	public BigDecimal getTranProfit() {
		return this.tranProfit;
	}
	public void setOutTotalAmount(BigDecimal value) {
		this.outTotalAmount = value;
	}
	
	public BigDecimal getOutTotalAmount() {
		return this.outTotalAmount;
	}
	public void setOutProfit(BigDecimal value) {
		this.outProfit = value;
	}
	
	public BigDecimal getOutProfit() {
		return this.outProfit;
	}
	public void setTotalProfit(BigDecimal value) {
		this.totalProfit = value;
	}
	
	public BigDecimal getTotalProfit() {
		return this.totalProfit;
	}
	public void setSettleDate(String value) {
		this.settleDate = value;
	}
	
	public String getSettleDate() {
		return this.settleDate;
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
	
	public String getQueryStartDate() {
		return queryStartDate;
	}

	public void setQueryStartDate(String queryStartDate) {
		this.queryStartDate = queryStartDate;
	}

	public String getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public String getOrganNo() {
		return organNo;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	public String getNodeSeq() {
		return nodeSeq;
	}

	public void setNodeSeq(String nodeSeq) {
		this.nodeSeq = nodeSeq;
	}

	public String getIdentityFlag() {
		return identityFlag;
	}

	public void setIdentityFlag(String identityFlag) {
		this.identityFlag = identityFlag;
	}

	
	
}

