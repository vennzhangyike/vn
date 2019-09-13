package com.hfepay.commons.criteria;
/**
 * 逻辑运算符
 * @author Sam
 *
 */
public enum Logic {
	NONE(""),
	AND("AND"),
	OR("OR");
	
	private Logic(String value) {
		this.value = value;
	}
	
	private String value;
	
	public String toString() {
		return value;
	}
}
