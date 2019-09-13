package com.hfepay.scancode.commons.contants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 限额类型 */
public enum LimitTypeEnum {
	
	LIMIT_TYPE_FRTX("分润提现限额","0"),
	LIMIT_TYPE_JY("交易限额","1"),
	LIMIT_TYPE_TX("提现限额","2"),
	LIMIT_TYPE_XYK("信用卡限额","3");
	
	/** 枚举值 */
	private String value;
	/** 描述 */
	private String desc;

	private LimitTypeEnum(String desc, String value) {
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
	
	public static LimitTypeEnum getEnum(String value){
		LimitTypeEnum resultEnum = null;
		LimitTypeEnum[] enumAry = LimitTypeEnum.values();
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
		LimitTypeEnum[] ary = LimitTypeEnum.values();
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
		LimitTypeEnum[] ary = LimitTypeEnum.values();
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
