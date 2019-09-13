package com.hfepay.scancode.channel.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MessagePushTime {
	
	
	
	PUSH_TIME_1("每一天","1"),
	PUSH_TIME_2("每二天","2"),
	PUSH_TIME_3("每三天","3"),
	PUSH_TIME_4("每四天","4"),
	PUSH_TIME_5("每五天","5"),
	PUSH_TIME_6("每六天","6"),
	PUSH_TIME_7("每七天","7");
	/** 枚举值 */
	private String value;
	/** 描述 */
	private String desc;

	private MessagePushTime(String desc, String value) {
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
	
	
	public static MessagePushTime getEnum(String value){
		MessagePushTime resultEnum = null;
		MessagePushTime[] enumAry = MessagePushTime.values();
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
		MessagePushTime[] ary = MessagePushTime.values();
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
		MessagePushTime[] ary = MessagePushTime.values();
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
