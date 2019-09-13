package com.hfepay.cache.jedis.impl;

import java.util.ArrayList;
import java.util.List;

import com.hfepay.cache.jedis.JedisTemplet;
import com.hfepay.cache.jedis.RedisCacheManage;
import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.utils.ListTranscoder;
import com.hfepay.commons.base.lang.util.SerializeUtil;

import redis.clients.jedis.ShardedJedis;

public class RedisSharedCache extends RedisCacheManage implements JedisTemplet {

	@Override
	public String set(RedisKey redisKey, String value, int cacheSeconds) {
		this.initRedis();
		ShardedJedis jedis = getResource();
		String result = jedis.set(redisKey.getRedisKey(), value);
		if (cacheSeconds >= 0) {
			jedis.expire(redisKey.getRedisKey(), cacheSeconds);
		}
		returnResource(jedis);
		return result;
	}
	
	@Override
	public String get(RedisKey redisKey) throws Exception {
		this.initRedis();
		ShardedJedis jedis = getResource();
		String result = jedis.get(redisKey.getRedisKey());
		returnResource(jedis);
		return result;
	}

	@Override
	public String set(RedisKey redisKey, String value) throws Exception {
		this.initRedis();
		ShardedJedis jedis = getResource();
		String result = jedis.set(redisKey.getRedisKey(), value);
		returnResource(jedis);
		return result;
	}

	@Override
	public String mset(ArrayList<RedisKey> redisKeys, ArrayList<String> values) throws Exception {
		return null;
	}

	@Override
	public Long del(RedisKey redisKey) throws Exception {
		this.initRedis();
		ShardedJedis jedis = getResource();
		Long result = jedis.del(redisKey.getRedisKey());
		returnResource(jedis);
		return result;
	}

	@Override
	public Long del(ArrayList<RedisKey> redisKeys) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setByte(RedisKey redisKey, byte[] value) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setByte(String redisKey, byte[] value) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getByte(RedisKey redisKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long expire(RedisKey redisKey, int seconds) throws Exception {
		this.initRedis();
		ShardedJedis jedis = getResource();
		Long result = jedis.expire(redisKey.getRedisKey(), seconds);
		returnResource(jedis);
		return result;
	}
	
	public String setObj(RedisKey redisKey,Object obj) throws Exception{
		this.initRedis();
		ShardedJedis jedis = getResource();
		String result = jedis.set(redisKey.getRedisKey().getBytes(), SerializeUtil.serialize(obj));
		returnResource(jedis);
		return result;
	}
	
	public String setObj(RedisKey redisKey,Object obj, int cacheSeconds) throws Exception{
		this.initRedis();
		ShardedJedis jedis = getResource();
		String result = jedis.set(redisKey.getRedisKey().getBytes(), SerializeUtil.serialize(obj));
		if (cacheSeconds >= 0) {
			jedis.expire(redisKey.getRedisKey(), cacheSeconds);
		}
		returnResource(jedis);
		return result;
	}
	
	public Object getObj(RedisKey redisKey) throws Exception{
		this.initRedis();
		ShardedJedis jedis = getResource();
		byte[] value = jedis.get(redisKey.getRedisKey().getBytes());
		returnResource(jedis);
		Object object = SerializeUtil.unserialize(value);
		return object;
	}
	
	public String setObjList(RedisKey redisKey,Object obj) throws Exception{
		this.initRedis();
		ShardedJedis jedis = getResource();
		String result = jedis.set(redisKey.getRedisKey().getBytes(), ListTranscoder.serialize(obj));
		returnResource(jedis);
		return result;
	}
	
	public String setObjList(RedisKey redisKey,List<Object> obj, int cacheSeconds) throws Exception{
		this.initRedis();
		ShardedJedis jedis = getResource();
		String result = jedis.set(redisKey.getRedisKey().getBytes(), ListTranscoder.serialize(obj));
		if (cacheSeconds >= 0) {
			jedis.expire(redisKey.getRedisKey(), cacheSeconds);
		}
		returnResource(jedis);
		return result;
	}
	
	public List<Object> getObjList(RedisKey redisKey) throws Exception{
		this.initRedis();
		ShardedJedis jedis = getResource();
		byte[] value = jedis.get(redisKey.getRedisKey().getBytes());
		returnResource(jedis);
		List<Object> object = (List<Object>)ListTranscoder.deserialize(value);
		return object;
	}
}
