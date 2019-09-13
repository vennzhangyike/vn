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
public class WeBankWeChatNaoInfoVo implements Serializable{
	private String merchant_code;//商户号
	private String terminal_code;//终端号
	private String terminal_serialno;//商户订单号
	private String amount;//金额
	private String product;//产品
	private String sub_openid;//用户open_id
	private String sub_appid;//商户appid
	private String goods_tag;//商品标记
	private String time_expire;//订单超时时间
	private String notify_url;//通知地址
	private String attach;//附加信息
	private String auth_code;//授权码
	

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



	public String getAmount() {
		return amount;
	}



	public void setAmount(String amount) {
		this.amount = amount;
	}



	public String getProduct() {
		return product;
	}



	public void setProduct(String product) {
		this.product = product;
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



	public String getGoods_tag() {
		return goods_tag;
	}



	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}



	public String getTime_expire() {
		return time_expire;
	}



	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}



	public String getNotify_url() {
		return notify_url;
	}



	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}



	public String getAttach() {
		return attach;
	}



	public void setAttach(String attach) {
		this.attach = attach;
	}


	public String getAuth_code() {
		return auth_code;
	}



	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
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
