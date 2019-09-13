package com.hfepay.commons.context;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * 一个通用的上下文信息接口类
 * 
 * @author Sam
 *
 */
public interface Context {
	/**
	 * 存放任何一个值于上下文环境中
	 * @param key 存值的键
	 * @param value 值
	 */
	public void set(String key,Object value);
	
	/**
	 * 从上下文环境中取得某一个已存放的对象
	 * @param key 取值的键
	 * @return
	 */
	public <T> T get(String key);
	
	/**
	 * 以map方式返回存在上下文环境中的键值对
	 * @return
	 */
	public Map<?,?> getValuesMap();
	
	/**
	 * 以list方式返回所有存在上下文环境中的对象值
	 * @return
	 */
	public List<?> getValues();
	
	/**
	 * 以iterator方式返回所有存在上下文环境中的对象值
	 * @return
	 */
	public Iterator<?> valuesIterator();
	
	/**
	 * 指定一个正则表达式，并根据这个表正则表达式去匹配存在上下文环境中所有的key
	 * @param regex 正则表达式
	 * @return
	 */
	public List<?> findValuesByREGEX(String regex);
}
