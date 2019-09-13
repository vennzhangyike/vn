package com.hfepay.scancode.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 利润中间统计汇总数据
 * @ClassName: HierarchicalSettlementTotalDTO
 * @Description: TODO
 * @author: husain
 * @date: 2016年12月22日 下午1:44:03
 */
public class HierarchicalSettlementTotalDTO implements Serializable {
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = -3582398810446060035L;
	private String channelNo;//渠道编号
	private String agentNo;//代理商编号
	private String agentLevel;//代理商层级
	private String parentNo;//上级编号
	private BigDecimal totalAmount;//交易总金额
	private BigDecimal totalProfit;//交易分润金额
	private String tradeType;//0交易1提现
	private String organNo;//当前统计的机构唯一编号
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	public String getAgentLevel() {
		return agentLevel;
	}
	public void setAgentLevel(String agentLevel) {
		this.agentLevel = agentLevel;
	}
	public String getParentNo() {
		return parentNo;
	}
	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getTotalProfit() {
		return totalProfit;
	}
	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getOrganNo() {
		return organNo;
	}
	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}
	
}
