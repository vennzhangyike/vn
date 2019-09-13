package com.hfepay.cache.jedis.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.hfepay.cache.jedis.JedisShardedPoolTool;
import com.hfepay.cache.jedis.JedisTemplet;
import com.hfepay.cache.jedis.RedisKey;

import redis.clients.jedis.ShardedJedis;

@Component
public class JedisUtils extends JedisShardedPoolTool implements JedisTemplet{
	
	@Override
	public String set(RedisKey redisKey, String value, int cacheSeconds) {
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
		ShardedJedis jedis = getResource();
		String result = jedis.get(redisKey.getRedisKey());
		returnResource(jedis);
		return result;
	}

	@Override
	public String set(RedisKey redisKey, String value) throws Exception {
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
		ShardedJedis jedis = getResource();
		Long result = jedis.expire(redisKey.getRedisKey(), seconds);
		returnResource(jedis);
		return result;
	}
}
