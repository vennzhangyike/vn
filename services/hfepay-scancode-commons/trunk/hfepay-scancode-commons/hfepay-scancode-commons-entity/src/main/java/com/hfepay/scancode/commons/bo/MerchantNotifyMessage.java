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
public class MerchantNotifyMessage implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -654570126464651004L;
	
	private String userReqNo;

	private String orderNo;//平台交易订单号
	
	private String userOrderNo;//商户交易订单号
	
	private String payType;//支付渠道
	
	private String channelNo;//渠道编号
	
	private String merchantNo;//商户编号
	
	private String merchantName;//商户名称
	
	private String orderTitle;//订单标题
	
	private String orderDesc;//订单描述
	
	private BigDecimal orderAmt;//订单金额
	
	private String status;//订单状态
	
	private String beginTime;//订单开始时间yyyy-MM-dd HH:mm:ss
	
	private String endTime;//订单交易结束时间yyyy-MM-dd HH:mm:ss
	
	private String errorMsg;//订单信息
	
	private String accountType;
	
	private String errorCode;//异常编码信息
	
	private String payStrategy;//支付策略
	
	public String getPayStrategy() {
		return payStrategy;
	}
	public void setPayStrategy(String payStrategy) {
		this.payStrategy = payStrategy;
	}
	
	public String getUserReqNo() {
		return userReqNo;
	}

	public void setUserReqNo(String userReqNo) {
		this.userReqNo = userReqNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
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

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public BigDecimal getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
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
	@Override
	public String toString() {
		return "MerchantNotifyMessage [userReqNo=" + userReqNo + ", orderNo=" + orderNo + ", userOrderNo=" + userOrderNo
				+ ", payType=" + payType + ", channelNo=" + channelNo + ", merchantNo=" + merchantNo + ", merchantName="
				+ merchantName + ", orderTitle=" + orderTitle + ", orderDesc=" + orderDesc + ", orderAmt=" + orderAmt
				+ ", status=" + status + ", beginTime=" + beginTime + ", endTime=" + endTime + ", errorMsg=" + errorMsg
				+ ", accountType=" + accountType + ", errorCode=" + errorCode + ", payStrategy=" + payStrategy + "]";
	}
	
}
