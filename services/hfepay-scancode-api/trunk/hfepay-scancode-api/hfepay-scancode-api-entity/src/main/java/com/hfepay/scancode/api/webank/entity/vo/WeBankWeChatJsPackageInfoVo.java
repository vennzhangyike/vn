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
public class WeBankWeChatJsPackageInfoVo implements Serializable{
	private String appId;//版本号
	private String timeStamp;//字符集
	private String nonceStr;//签名方式
	private String packageContent;//商户号
	private String signType;//是否原生态
	private String paySign;//商户订单号
	
	public String getAppId() {
		return appId;
	}



	public void setAppId(String appId) {
		this.appId = appId;
	}



	public String getTimeStamp() {
		return timeStamp;
	}



	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}



	public String getNonceStr() {
		return nonceStr;
	}



	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}



	public String getPackageContent() {
		return packageContent;
	}



	public void setPackageContent(String packageContent) {
		this.packageContent = packageContent;
	}



	public String getSignType() {
		return signType;
	}



	public void setSignType(String signType) {
		this.signType = signType;
	}



	public String getPaySign() {
		return paySign;
	}



	public void setPaySign(String paySign) {
		this.paySign = paySign;
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
