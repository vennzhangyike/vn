package com.hfepay.cache.support;

import com.hfepay.cache.support.cachers.RedisCacher;
import com.hfepay.cache.support.proxy.MethodUniqueKey;

/**   
* @Title: Test.java 
* @Package cn.hfepay.cache.support 
* @Description: TODO
* @author maozk
* @date 2016年3月18日 下午5:02:57 
*/
public class Test {

	public static void main(String[] args) {
		Cacher cacher = new RedisCacher("172.168.8.201", 6379);
		//cacher.put("test_key", "测试值",10);
		//cacher.remove("test_key");
		Object key = new MethodUniqueKey("cn.hfepay.service.merchant.IMerchantUserService", "findByUsername", new Class[]{String.class}, new String[]{"merchant"});
		Object result = cacher.get(key);
		System.out.println(result);
	}

}
