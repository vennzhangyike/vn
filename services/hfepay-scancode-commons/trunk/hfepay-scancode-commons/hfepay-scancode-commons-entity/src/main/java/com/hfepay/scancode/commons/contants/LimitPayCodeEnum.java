package com.hfepay.scancode.commons.contants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 限额通道 */
public enum LimitPayCodeEnum {
	
	LIMIT_TYPE_QB("钱包","0"),
	LIMIT_TYPE_WXGZH("微信公众号","1"),
	LIMIT_TYPE_ZFB("支付宝","2");
	
	/** 枚举值 */
	private String value;
	/** 描述 */
	private String desc;

	private LimitPayCodeEnum(String desc, String value) {
		this.value = value;
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static LimitPayCodeEnum getEnum(String value){
		LimitPayCodeEnum resultEnum = null;
		LimitPayCodeEnum[] enumAry = LimitPayCodeEnum.values();
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
		LimitPayCodeEnum[] ary = LimitPayCodeEnum.values();
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
		LimitPayCodeEnum[] ary = LimitPayCodeEnum.values();
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
