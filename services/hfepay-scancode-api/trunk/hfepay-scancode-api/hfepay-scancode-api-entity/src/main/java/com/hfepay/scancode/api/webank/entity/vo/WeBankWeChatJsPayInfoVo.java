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
public class WeBankWeChatJsPayInfoVo implements Serializable{
	private String version;//版本号
	private String charset;//字符集
	private String sign_type;//签名方式
	private String mch_id;//商户号
	private String isRaw;//是否原生态
	private String out_trade_no;//商户订单号
	private String device_info;//设备号
	private String body;//商品描述
	private String sub_openid;//用户openid
	private String sub_appid;//商户appid
	private String attach;//附加信息
	private int total_fee;//总金额
	private String mch_create_ip;//终端ip
	private String notify_url;//通知地址
	private String time_start;//订单生成时间
	private String time_expire;//订单超时时间
	private String goods_tag;//商品标记
	private String nonce_str;//随机字符串
	private String limit_credit_pay;//是否限制信用卡
	
	private String orderid;//微众银行订单号
	private String transaction_id;//微信支付订单号
	
	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getMch_id() {
		return mch_id;
	}




	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getIsRaw() {
		return isRaw;
	}

	public void setIsRaw(String isRaw) {
		this.isRaw = isRaw;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getDevice_info() {
		return device_info;
	}
	
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSub_openid() {
		return sub_openid;
	}

	public void setSub_openid(String sub_openid) {
		this.sub_openid = sub_openid;
	}

	public String getSub_appid() {
		return sub_appid;
	}

	public void setSub_appid(String sub_appid) {
		this.sub_appid = sub_appid;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public String getMch_create_ip() {
		return mch_create_ip;
	}

	public void setMch_create_ip(String mch_create_ip) {
		this.mch_create_ip = mch_create_ip;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getLimit_credit_pay() {
		return limit_credit_pay;
	}

	public void setLimit_credit_pay(String limit_credit_pay) {
		this.limit_credit_pay = limit_credit_pay;
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
