package com.hfepay.cache.support.cachers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**   
* @Title: MemoryCacher.java 
* @Package cn.hfepay.cache.support.cachers 
* @Description: TODO
* @author maozk
* @date 2016年3月21日 下午4:10:07 
*/
public class MemoryCacher extends TimeoutableCacher{
	
	Map<Object,Object> cacheMap = new ConcurrentHashMap<Object,Object>();
	
	@Override
	protected void doPut(Object key, Object value) {
		cacheMap.put(key, value);
	}

	@Override
	protected void doRemove(Object key) {
		cacheMap.remove(key);
	}

	@Override
	protected Object doGet(Object key) {
		return cacheMap.get(key);
	}

}
