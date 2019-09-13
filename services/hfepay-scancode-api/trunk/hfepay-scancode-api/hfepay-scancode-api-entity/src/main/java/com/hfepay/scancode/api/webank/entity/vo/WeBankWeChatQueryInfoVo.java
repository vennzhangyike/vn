package com.hfepay.scancode.api.webank.entity.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 微众 微信扫码下单VO(API)
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class WeBankWeChatQueryInfoVo implements Serializable{
	private String merchant_code;//商户号
	private String terminal_code;//终端号
	private String terminal_serialno;//商户订单号
	private String orderid;//微众银行订单号
	private String o_terminal_serialno;//原商户订单号
	private String amount;//金额
	private String transaction_id;//微信支付订单号
	private String refund_amount;//退款金额
	
	public String getMerchant_code() {
		return merchant_code;
	}

	public void setMerchant_code(String merchant_code) {
		this.merchant_code = merchant_code;
	}

	public String getTerminal_code() {
		return terminal_code;
	}

	public void setTerminal_code(String terminal_code) {
		this.terminal_code = terminal_code;
	}

	public String getTerminal_serialno() {
		return terminal_serialno;
	}

	public void setTerminal_serialno(String terminal_serialno) {
		this.terminal_serialno = terminal_serialno;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getO_terminal_serialno() {
		return o_terminal_serialno;
	}

	public void setO_terminal_serialno(String o_terminal_serialno) {
		this.o_terminal_serialno = o_terminal_serialno;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
	

	public String getRefund_amount() {
		return refund_amount;
	}

	public void setRefund_amount(String refund_amount) {
		this.refund_amount = refund_amount;
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
