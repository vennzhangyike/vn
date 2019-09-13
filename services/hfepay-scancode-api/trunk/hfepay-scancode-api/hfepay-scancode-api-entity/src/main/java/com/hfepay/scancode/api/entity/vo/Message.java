package com.hfepay.scancode.api.entity.vo;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Message implements Serializable {
	private String content;
	private String sign;
	private String key;

	public Message(String content, String sign, String key) {
		this.content = content;
		this.sign = sign;
		this.key = key;
	}

	public String getContent() {
		return content;
	}

	public String getSign() {
		return sign;
	}

	public String getKey() {
		return key;
	}
	
	public Map<String,String> toMap(){
        Map<String,String> map = new HashMap<String, String>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
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