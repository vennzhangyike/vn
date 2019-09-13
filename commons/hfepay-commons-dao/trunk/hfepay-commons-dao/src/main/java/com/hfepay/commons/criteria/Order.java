package com.hfepay.commons.criteria;

import com.hfepay.commons.base.lang.Strings;


/**
 * 排序值枚举类
 * @author Sam
 *
 */
public enum Order {
	ASC("ASC"),
	DESC("DESC");
	
	private Order(String value) {
		if(Strings.isBlank(value)){
			this.value="ASC";
		}else{
			this.value=value.trim().toUpperCase();
		}
	}
	private String value ;
	
	public String toString() {
		return value;
	}
}
