package com.hfepay.scancode.api.entity.vo;

import java.io.Serializable;

/**
 * 商户费率VO(API)
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class MerchantRateVo implements Serializable{
	
	private String payCode;//通道编号
	private String withdrawRate;//提现手续费
	private String tradeRate;//交易手续费率
	private String withdrawAmt;//提现服务费
	private String settleAmt;//T1清算手续费(元)
	
	public String getSettleAmt() {
		return settleAmt;
	}
	public void setSettleAmt(String settleAmt) {
		this.settleAmt = settleAmt;
	}
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
	public String getWithdrawAmt() {
		return withdrawAmt;
	}
	public void setWithdrawAmt(String withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}
	
}
