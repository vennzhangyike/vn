package com.hfepay.scancode.commons.bo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProfitBo implements Serializable{

	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 3832734214386749099L;
	
	private String parentIdentityNo;//上级节点标示符，可以为代理商，渠道，平台
	private String identityNo;
	private BigDecimal t0RateDifference;//t0费率差
	private BigDecimal t1RateDifference;//t1费率差
	private BigDecimal parentT0Rate;//父节点的T0费率
	private BigDecimal parentT1Rate;//父节点T1费率
	private BigDecimal rateDifference;//提现固定费率差
	private BigDecimal parentRate;//提现固定费率
	private String payCode;//支付方式
	private BigDecimal withDrawRate;//提现费率=T0费率差+T1费率差
	
	public String getParentIdentityNo() {
		return parentIdentityNo;
	}
	public void setParentIdentityNo(String parentIdentityNo) {
		this.parentIdentityNo = parentIdentityNo;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public BigDecimal getT0RateDifference() {
		return t0RateDifference;
	}
	public void setT0RateDifference(BigDecimal toRateDifference) {
		this.t0RateDifference = toRateDifference;
	}
	public BigDecimal getT1RateDifference() {
		return t1RateDifference;
	}
	public void setT1RateDifference(BigDecimal t1RateDifference) {
		this.t1RateDifference = t1RateDifference;
	}
	public String getIdentityNo() {
		return identityNo;
	}
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	public BigDecimal getParentT0Rate() {
		return parentT0Rate;
	}
	public void setParentT0Rate(BigDecimal parentT0Rate) {
		this.parentT0Rate = parentT0Rate;
	}
	public BigDecimal getParentT1Rate() {
		return parentT1Rate;
	}
	public void setParentT1Rate(BigDecimal parentT1Rate) {
		this.parentT1Rate = parentT1Rate;
	}
	public BigDecimal getRateDifference() {
		return rateDifference;
	}
	public void setRateDifference(BigDecimal rateDifference) {
		this.rateDifference = rateDifference;
	}
	public BigDecimal getParentRate() {
		return parentRate;
	}
	public void setParentRate(BigDecimal parentRate) {
		this.parentRate = parentRate;
	}
	
	public BigDecimal getWithDrawRate() {
		return withDrawRate;
	}
	public void setWithDrawRate(BigDecimal withDrawRate) {
		this.withDrawRate = withDrawRate;
	}
	@Override
	public String toString() {
		return "ProfitBo [parentIdentityNo=" + parentIdentityNo + ", identityNo=" + identityNo + ", t0RateDifference="
				+ t0RateDifference + ", t1RateDifference=" + t1RateDifference + ", parentT0Rate=" + parentT0Rate
				+ ", parentT1Rate=" + parentT1Rate + ", rateDifference=" + rateDifference + ", parentRate=" + parentRate
				+ ", payCode=" + payCode + ", withDrawRate=" + withDrawRate + "]";
	}
	
	
	
	
	
	
	
}
