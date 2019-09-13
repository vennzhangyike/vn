package com.hfepay.cache.support;

/**   
* @Title: Cacher.java 
* @Package cn.hfepay.cache.support 
* @Description: 缓存操作接口
* @author maozk
* @date 2016年3月18日 下午4:05:18 
*/
public interface Cacher {
	
	/**
	 * 插入缓存
	 * @param key 键
	 * @param value 值
	 * @param timeout 超时时间(单位：秒)
	 */
	void put(Object key,Object value,long timeout);
	
	/**
	 * 删除键
	 * @param key
	 */
	void remove(Object key);
	
	/**
	 * 从缓存中获取
	 * @param key
	 * @return
	 */
	Object get(Object key);
	
}
