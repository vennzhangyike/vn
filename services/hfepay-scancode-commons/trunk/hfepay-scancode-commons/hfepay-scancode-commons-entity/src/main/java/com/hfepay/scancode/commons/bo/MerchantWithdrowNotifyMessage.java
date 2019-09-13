package com.hfepay.scancode.commons.bo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/** 
 * @author jerry.xie
 * @Date 2016年10月10日
 */
public class MerchantWithdrowNotifyMessage implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -654570126464651004L;
	private String userReqNo;
	private String orderNo;
	private String userOrderNo;
	private String payCode;
	private String status;
	private BigDecimal drawAmount;
	private BigDecimal drawFee;
	private BigDecimal tradeFee;
	private String settleDate;
	private String createDate;
	
	private String withDrawId;
	
	public String getUserReqNo() {
		return userReqNo;
	}

	public void setUserReqNo(String userReqNo) {
		this.userReqNo = userReqNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserOrderNo() {
		return userOrderNo;
	}

	public void setUserOrderNo(String userOrderNo) {
		this.userOrderNo = userOrderNo;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getDrawAmount() {
		return drawAmount;
	}

	public void setDrawAmount(BigDecimal drawAmount) {
		this.drawAmount = drawAmount;
	}

	public BigDecimal getDrawFee() {
		return drawFee;
	}

	public void setDrawFee(BigDecimal drawFee) {
		this.drawFee = drawFee;
	}

	public BigDecimal getTradeFee() {
		return tradeFee;
	}

	public void setTradeFee(BigDecimal tradeFee) {
		this.tradeFee = tradeFee;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getWithDrawId() {
		return withDrawId;
	}

	public void setWithDrawId(String withDrawId) {
		this.withDrawId = withDrawId;
	}

	public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), String.valueOf(obj));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
