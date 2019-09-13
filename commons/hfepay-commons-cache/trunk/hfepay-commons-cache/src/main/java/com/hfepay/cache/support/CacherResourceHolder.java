package com.hfepay.cache.support;

import com.hfepay.cache.support.cachers.RedisCacher;

/**   
* @Title: CacherResourceHolder.java 
* @Package cn.hfepay.cache.support 
* @Description: TODO
* @author maozk
* @date 2016年3月18日 下午4:11:31 
*/
public class CacherResourceHolder {
	
	private static Cacher defualtCacher;
	static{
		defualtCacher = new RedisCacher("172.168.8.201", 6379);
	}
	public static Cacher get(String cacher){
		return defualtCacher;
	}
	public static Cacher get(){
		return defualtCacher;
	}
}
