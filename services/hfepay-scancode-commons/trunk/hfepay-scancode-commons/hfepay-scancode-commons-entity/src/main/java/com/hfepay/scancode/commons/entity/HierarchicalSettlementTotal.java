/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.annotations.SelectColumnMapping;
import com.hfepay.commons.entity.annotations.SelectColumnMappings;

@SelectColumnMappings({
	@SelectColumnMapping(property = "id", type = java.lang.String.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "id"),
	@SelectColumnMapping(property = "batchNo", type = java.lang.String.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "batch_no"),
	@SelectColumnMapping(property = "channelNo", type = java.lang.String.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "channel_no"),
	@SelectColumnMapping(property = "agentNo", type = java.lang.String.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "agent_no"),
	@SelectColumnMapping(property = "agentLevel", type = java.lang.String.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "agent_level"),
	@SelectColumnMapping(property = "parentNo", type = java.lang.String.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "parent_no"),
	@SelectColumnMapping(property = "tranTotalAmount", type = BigDecimal.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "tran_total_amount"),
	@SelectColumnMapping(property = "tranProfit", type = BigDecimal.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "tran_profit"),
	@SelectColumnMapping(property = "outTotalAmount", type = BigDecimal.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "out_total_amount"),
	@SelectColumnMapping(property = "outProfit", type = BigDecimal.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "out_profit"),
	@SelectColumnMapping(property = "totalProfit", type = BigDecimal.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "total_profit"),
	@SelectColumnMapping(property = "settleDate", type = java.lang.String.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "settle_date"),
	@SelectColumnMapping(property = "createTime", type = Date.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "create_time"),
	@SelectColumnMapping(property = "remark", type = java.lang.String.class, table = "t_hierarchical_settlement_total", tableAlias = "A", column = "remark")
})

@Generated("2016-12-20 11:30:01")
public class HierarchicalSettlementTotal implements IdEntity<String> {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
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
	private String parentName;//上级名称
	private String channelName;//渠道名称
	private String agentName;//代理商名称
	

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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	
}

