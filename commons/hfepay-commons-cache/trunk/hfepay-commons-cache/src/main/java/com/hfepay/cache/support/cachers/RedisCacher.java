package com.hfepay.cache.support.cachers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.exceptions.JedisMovedDataException;

/**   
* @Title: RedisCacher.java 
* @Package cn.hfepay.cache.support.cachers 
* @Description: TODO
* @author maozk
* @date 2016年3月18日 下午4:32:07 
*/
public class RedisCacher extends TimeoutableCacher{
	
	protected ShardedJedis redisClient = null;
	
	public RedisCacher(String host,int port){
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo info = new JedisShardInfo(host, port);
		shards.add(info);
		redisClient = new ShardedJedis(shards);
	}
	
	@Override
	public void doPut(Object key, Object value) {
		try {
			redisClient.set(serialize(key), serialize(value));
		} catch (Exception e) {
			throw new RuntimeException("写入缓存出错",e);
		}
	}

	@Override
	protected void doRemove(Object key) {
		try {
			redisClient.del(serialize(key));
		}catch (JedisMovedDataException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw new RuntimeException("删除缓存出错",e);
		}
	}

	@Override
	public Object doGet(Object key) {
		try {
			return deserialize(redisClient.get(serialize(key)));
		} catch (Exception e) {
			throw new RuntimeException("读取缓存出错",e);
		}
	}
	
	private byte[] serialize(Object obj)throws Exception{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		return bos.toByteArray();
	}
	
	private Object deserialize(byte[] objectBytes)throws Exception{
		if(null==objectBytes){
			return null;
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(objectBytes);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object result = ois.readObject();
		return result;
	}
	
}
