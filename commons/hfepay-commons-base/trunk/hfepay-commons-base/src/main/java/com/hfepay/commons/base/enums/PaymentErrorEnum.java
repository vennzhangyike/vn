package com.hfepay.commons.base.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PaymentErrorEnum {
	
	
	
	APPID_CODE("APPID不存在",10001),
	MECH_NOT_CODE("商户号不存在",10002),
	APPID_MCHID_CODE("商户号与应用ID不匹配",10003),
	PARAMS_CODE("缺少参数",10004),
	ORDER_NO_CODE("订单号重复",10005),
	ORDERPAID("订单已支付",10006),
	SIGNERROR_CODE("签名验证失败",10007),
	REQUIRE_POST_CODE("请求格式错误",10008),
	UT_CODE("编码格式不正确",10009),
	NOTENOUGH_CODE("余额不足",10010),
	NOAUTH_CODE("该接口无权限",10012),
	SYSTEM_CHANNEL_CODE("系统错误",99999);
	/** 枚举值 */
	private int value;
	/** 描述 */
	private String desc;

	private PaymentErrorEnum(String desc, int value) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	
	public static PaymentErrorEnum getEnum(int value){
		PaymentErrorEnum resultEnum = null;
		PaymentErrorEnum[] enumAry = PaymentErrorEnum.values();
		for(int i = 0;i<enumAry.length;i++){
			if(enumAry[i].getValue() == value){
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List toList(){
		PaymentErrorEnum[] ary = PaymentErrorEnum.values();
		List list = new ArrayList();
		for(int i=0;i<ary.length;i++){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value",String.valueOf(ary[i].getValue()));
			map.put("desc", ary[i].getDesc());
			list.add(map);
		}
		return list;
	}
	
	public static Map<String, Map<String, Object>> toMap() {
		PaymentErrorEnum[] ary = PaymentErrorEnum.values();
		Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
		for (int num = 0; num < ary.length; num++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String key = String.valueOf(getEnum(ary[num].getValue()));
			map.put("value", String.valueOf(ary[num].getValue()));
			map.put("desc", ary[num].getDesc());
			enumMap.put(key,map);
		}
		return enumMap;
	}
	
	
}
