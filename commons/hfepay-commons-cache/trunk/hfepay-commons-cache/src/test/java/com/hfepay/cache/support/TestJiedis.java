package com.hfepay.cache.support;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.JedisUtils;

public class TestJiedis {

	public static void main(String[] args) throws Exception {
		JedisUtils j = new JedisUtils();
		try {
			j.del(new RedisKey("superplat", "hfjk"));
			System.out.println(j.get(new RedisKey("superplat", "hfjk")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
