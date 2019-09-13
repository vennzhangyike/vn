package com.hfepay.cache.support.cachers;

import com.hfepay.cache.support.Cacher;
import com.hfepay.cache.support.utils.DateUtil;

/**   
* @Title: TimeoutableCacher.java 
* @Package cn.hfepay.cache.support.cachers 
* @Description: TODO
* @author maozk
* @date 2016年3月21日 下午3:44:40 
*/
public abstract class TimeoutableCacher implements Cacher{

	public void put(Object key, Object value, long timeout) {
		Object toput = new TimeoutWrapedObject(value,DateUtil.timeOfNow(), timeout);
		doPut(key, toput);
	}

	public void remove(Object key) {
		doRemove(key);
	}

	public Object get(Object key) {
		Object value = doGet(key);
		if(value == null){
			return null;
		}
		TimeoutWrapedObject wraped = (TimeoutWrapedObject)value;
		if(wraped.getTimeout() > 0){
			long timeoutTime = wraped.getCacheTime() + wraped.getTimeout() * 1000;
			long now = DateUtil.timeOfNow();
			if(now > timeoutTime){
				doRemove(key);
				return null;
			}
		}
		return wraped.getSourceObject();
	}
	
	protected abstract void doPut(Object key, Object value);
	
	protected abstract void doRemove(Object key);
	
	protected abstract Object doGet(Object key);
	
}
