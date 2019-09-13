package com.hfepay.scancode.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderPayStaticDTO implements Serializable {

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 9015088719872039228L;
	private String merchantNo;//商户编号
	private BigDecimal orderAmt;//交易总金额汇总
	private String payCode;//支付方式
	private String agentNo;//代理商编号
	private String channelNo;//渠道编号
	private BigDecimal tradeTimes;//交易次数
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public BigDecimal getOrderAmt() {
		return orderAmt;
	}
	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	
	public String getAgentNo() {
		return agentNo;
	}
	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}
	
	public BigDecimal getTradeTimes() {
		return tradeTimes;
	}
	public void setTradeTimes(BigDecimal tradeTimes) {
		this.tradeTimes = tradeTimes;
	}
	
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	@Override
	public String toString() {
		return "OrderPayStaticDTO [merchantNo=" + merchantNo + ", orderAmt=" + orderAmt + ", payCode=" + payCode+ ", channelNo=" + channelNo
				+ ", agentNo=" + agentNo + ", tradeTimes=" + tradeTimes + "]";
	}
	
	
	
	
}
