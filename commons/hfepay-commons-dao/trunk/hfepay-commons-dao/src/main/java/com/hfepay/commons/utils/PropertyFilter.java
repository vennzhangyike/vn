package com.hfepay.commons.utils;

import java.io.Serializable;
/**
 * 用于简化调用服务接口中有可能会有多个过滤条件要传输 
 * @author Sam
 *
 */
public class PropertyFilter<T> implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -5019539221920159381L;

	/**
	 * 过滤器应用于的那个实体类型
	 */
	private Class<T> objectType;
	
	/**
	 * 过滤的属性名称
	 */
	private String name;
	
	/**
	 * 过滤的属性值
	 */
	private Object value;
	
	/**
	 * 过滤的值的类型
	 */
	private ValueType valueType;
	
	/**
	 * 比较符号
	 */
	private Comparator comparator;

	public Class<T> getObjectType() {
		return objectType;
	}

	public void setObjectType(Class<T> objectType) {
		this.objectType = objectType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ValueType getValueType() {
		return valueType;
	}

	public void setValueType(ValueType valueType) {
		this.valueType = valueType;
	}

	public Comparator getComparator() {
		return comparator;
	}

	public void setComparator(Comparator comparator) {
		this.comparator = comparator;
	}
	
	
}
