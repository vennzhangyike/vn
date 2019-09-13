/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.condition;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.utils.PagingCondition;

@Generated("2016-06-15 20:42:11")
public class PaywayCondition extends PagingCondition {

	private static final long serialVersionUID = 1L;
	
	private String payCode;//通道编号
	private String withdrawRate;//通道编号
	private String tradeRate;//交易手续费率
	private String withdrawAmt;//提现服务费
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
