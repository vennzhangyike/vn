package com.hfepay.commons.criteria;

import java.util.Map;


/**
 * 基类条件接口，封装一些条件的共性。
 * 
 * @author Sam
 *
 */
public interface Condition {
	/**
	 * 是否是组合条件
	 * @return
	 */
	boolean isGroup();
	
	/**
	 * 获取此条件的逻辑操作符
	 */
	Logic getLogic();
	
	/**
	 * 设置此条件的逻辑操作符
	 * @param logic
	 */
	void setLogic(Logic logic);
	
	/**
	 * 条件扩展属性集，用于存放一些用户自定义的控制属性
	 * @return
	 */
	Map<String, Object> getAttributes();
	
	/**
	 * 添加一个扩展属性
	 * @param attrName
	 * @param attrValue
	 */
	void addAttribute(String attrName,Object attrValue);
	
	/**
	 * 获取某一个属性
	 * @param attrName
	 * @return
	 */
	Object getAttribute(String attrName);
	
	
	String toString();
}
