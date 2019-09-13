package com.hfepay.scancode.api.webank.entity.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 微众 商户账户信息VO(API)
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class WeBankPreCreateTradeVo implements Serializable{
	private String wbMerchantId;//是  微众分配的商户号
	private String orderId;//是  订单号
	private String authCode;//支付授权码
	private String sellerId;//否 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
	private String totalAmount;//// 是   订单总金额，单位为元
	private String discountableAmount;////否  可打折金额.
	private String undiscountableAmount;//// 否    不可打折金额
	private String buyerLogonId;//否  买家支付宝帐号
	private String subject;//是  订单标题
	private String body;//否 对交易或者商品的描述
	private String operatorId;//否  商户操作员编号
	private String storeId;//否  商户门店编号
	private String terminalId;//否  商户机具终端编号
	private String alipayStoreId;//否 支付宝的店铺编号
	private String timeoutExpress;//否  该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d
	private String royaltyInfo;//描述分账信息，Json格式
	private String externalInfo;//扩展信息
	

	public String getWbMerchantId() {
		return wbMerchantId;
	}

	public void setWbMerchantId(String wbMerchantId) {
		this.wbMerchantId = wbMerchantId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDiscountableAmount() {
		return discountableAmount;
	}

	public void setDiscountableAmount(String discountableAmount) {
		this.discountableAmount = discountableAmount;
	}

	public String getUndiscountableAmount() {
		return undiscountableAmount;
	}

	public void setUndiscountableAmount(String undiscountableAmount) {
		this.undiscountableAmount = undiscountableAmount;
	}

	public String getBuyerLogonId() {
		return buyerLogonId;
	}

	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getAlipayStoreId() {
		return alipayStoreId;
	}

	public void setAlipayStoreId(String alipayStoreId) {
		this.alipayStoreId = alipayStoreId;
	}

	public String getTimeoutExpress() {
		return timeoutExpress;
	}

	public void setTimeoutExpress(String timeoutExpress) {
		this.timeoutExpress = timeoutExpress;
	}

	public String getRoyaltyInfo() {
		return royaltyInfo;
	}

	public void setRoyaltyInfo(String royaltyInfo) {
		this.royaltyInfo = royaltyInfo;
	}

	public String getExternalInfo() {
		return externalInfo;
	}

	public void setExternalInfo(String externalInfo) {
		this.externalInfo = externalInfo;
	}
	
	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
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
