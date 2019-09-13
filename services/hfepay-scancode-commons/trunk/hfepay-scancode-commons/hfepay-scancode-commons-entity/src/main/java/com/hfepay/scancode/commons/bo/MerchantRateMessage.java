package com.hfepay.scancode.commons.bo;

import java.io.Serializable;

public class MerchantRateMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7745013916519766653L;
	
	private String payCode;
	private String withdrawRate;
	private String tradeRate;
	private String settleAmt;
	private String withdrawAmt;
	private String status;
	
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public String getWithdrawRate() {
		return withdrawRate;
	}
	public void setWithdrawRate(String withdrawRate) {
		this.withdrawRate = withdrawRate;
	}
	public String getTradeRate() {
		return tradeRate;
	}
	public void setTradeRate(String tradeRate) {
		this.tradeRate = tradeRate;
	}
	public String getSettleAmt() {
		return settleAmt;
	}
	public void setSettleAmt(String settleAmt) {
		this.settleAmt = settleAmt;
	}
	public String getWithdrawAmt() {
		return withdrawAmt;
	}
	public void setWithdrawAmt(String withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
