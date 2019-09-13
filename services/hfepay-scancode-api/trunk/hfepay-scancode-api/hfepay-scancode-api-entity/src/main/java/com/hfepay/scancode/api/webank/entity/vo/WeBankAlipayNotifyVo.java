package com.hfepay.scancode.api.webank.entity.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微众 商户账户信息VO(API)
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class WeBankAlipayNotifyVo implements Serializable{
	private String tradeNo;//支付宝交易号
	private String orderId;//商户订单号
	private String buyerId;//买家支付宝用户号
	private String buyerLogonId;//买家支付宝账号
	private String sellerId;//卖家支付宝用户号
	private String sellerEmail;//卖家支付宝账号
	private String tradeStatus;//交易状态
	private String totalAmount;//订单金额
	private String receiptAmount;//商家在交易中实际收到的款项
	private String invoiceAmount;//用户在交易中支付的可开发票的金额
	private String buyerPayAmount;//用户在交易中支付的金额
	private String pointAmount;//使用集分宝支付的金额
	private String subject;//订单标题
	private String body;//商品描述
	private String gmtCreate;//交易创建时间
	private String gmtPayment;//买家付款时间 
	private String fundBillList;//支付金额信息
	private String notifyId;//回调ID
	private Date notifyTime; //回调时间
	private String notifyType;//回调类型
	
	public String getTradeNo() {
		return tradeNo;
	}


	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}


	public String getBuyerLogonId() {
		return buyerLogonId;
	}


	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}


	public String getSellerId() {
		return sellerId;
	}


	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}


	public String getSellerEmail() {
		return sellerEmail;
	}


	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}


	public String getTradeStatus() {
		return tradeStatus;
	}


	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}


	public String getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}


	public String getReceiptAmount() {
		return receiptAmount;
	}


	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}


	public String getInvoiceAmount() {
		return invoiceAmount;
	}


	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}


	public String getBuyerPayAmount() {
		return buyerPayAmount;
	}


	public void setBuyerPayAmount(String buyerPayAmount) {
		this.buyerPayAmount = buyerPayAmount;
	}


	public String getPointAmount() {
		return pointAmount;
	}


	public void setPointAmount(String pointAmount) {
		this.pointAmount = pointAmount;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public String getGmtCreate() {
		return gmtCreate;
	}


	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}


	public String getGmtPayment() {
		return gmtPayment;
	}


	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
	}


	public String getFundBillList() {
		return fundBillList;
	}


	public void setFundBillList(String fundBillList) {
		this.fundBillList = fundBillList;
	}

	

	public String getNotifyId() {
		return notifyId;
	}


	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}


	public Date getNotifyTime() {
		return notifyTime;
	}


	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}


	public String getNotifyType() {
		return notifyType;
	}


	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}


	public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null && !"".equals(obj)){
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
