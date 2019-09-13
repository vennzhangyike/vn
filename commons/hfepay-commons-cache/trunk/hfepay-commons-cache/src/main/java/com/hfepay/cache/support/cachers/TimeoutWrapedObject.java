package com.hfepay.cache.support.cachers;

import java.io.Serializable;

/**   
* @Title: TimeoutWrapedObject.java 
* @Package cn.hfepay.cache.support.cachers 
* @Description: TODO
* @author maozk
* @date 2016年3月21日 下午3:42:23 
*/
public class TimeoutWrapedObject implements Serializable{

	private static final long serialVersionUID = -1017349976470836413L;

	private Object sourceObject;
	
	private long cacheTime;
	
	private long timeout;
	
	public TimeoutWrapedObject(Object sourceObject, long cacheTime, long timeout) {
		super();
		this.sourceObject = sourceObject;
		this.cacheTime = cacheTime;
		this.timeout = timeout;
	}

	public Object getSourceObject() {
		return sourceObject;
	}

	public void setSourceObject(Object sourceObject) {
		this.sourceObject = sourceObject;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public long getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(long cacheTime) {
		this.cacheTime = cacheTime;
	}
	
	
}
