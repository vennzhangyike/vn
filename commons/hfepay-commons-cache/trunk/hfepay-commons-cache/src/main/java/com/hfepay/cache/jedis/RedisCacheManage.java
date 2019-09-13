package com.hfepay.cache.jedis;

import java.util.ArrayList;
import java.util.List;

import com.hfepay.cache.jedis.exceptions.JedisException;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisCacheManage {

	private String hostName = "localhost";
	private String port = "6379";
	private String maxIdle;
	private String maxTotal;
	private String maxWaitMillis;
	private String testOnBorrow;
	
	private static ShardedJedisPool shardedJedisPool;
	
	public void initRedis(){
		if(shardedJedisPool==null){
			// 池基本配置
			JedisPoolConfig config = new JedisPoolConfig();
			
			//空闲对象最大数
	        config.setMaxIdle(Integer.valueOf(maxIdle));
	        
	        //最大链接数
	        config.setMaxTotal(Integer.valueOf(maxTotal));
	        
	        //最长等待时间
	        config.setMaxWaitMillis(Integer.valueOf(maxWaitMillis));
	        
	        //验证链接是否有效，如果失效则被丢弃
	        config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
	        
			config.setTestOnBorrow(false);
			
			// slave链接
			List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
			
			String[] ips = hostName.split(",");
	        String[] ports = port.split(",");
	        if (ips.length != ports.length) {
	        	throw new JedisException("redis ip,prot 未正确配置！");
			}
	        for (int i = 0; i < ports.length; i++) {
	        	shards.add(new JedisShardInfo(ips[i], Integer.valueOf(ports[i])));
			}

			// 构造池
			shardedJedisPool = new ShardedJedisPool(config, shards);

			if (shardedJedisPool.isClosed()) {
				new JedisException("链接池已经关闭！");
			}
			if (shardedJedisPool.getNumActive() < 1) {
				System.out.println(shardedJedisPool.getNumActive());
				new JedisException("链接池链接已使用完！");
			}
		}
	}
	
	/**
	 * @Description: 从链接池获取链接
	 * @param 
	 * @return ShardedJedis
	 * @exception JedisException
	 */
	public ShardedJedis getResource(){
		ShardedJedis jedis = shardedJedisPool.getResource();
		if (null == jedis) {
			throw new JedisException("无法获取连接！");
		}
		return jedis;
	}
	
	/**
	 * @Description: 把连接放回连接池
	 * @param ShardedJedis
	 */
	public void returnResource(ShardedJedis jedis){
		if (null != jedis && !shardedJedisPool.isClosed()) {			
			shardedJedisPool.returnResource(jedis);
		}
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(String maxIdle) {
		this.maxIdle = maxIdle;
	}

	public String getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(String maxTotal) {
		this.maxTotal = maxTotal;
	}

	public String getMaxWaitMillis() {
		return maxWaitMillis;
	}

	public void setMaxWaitMillis(String maxWaitMillis) {
		this.maxWaitMillis = maxWaitMillis;
	}

	public String getTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(String testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public ShardedJedisPool getShardedJedisPool() {
		return shardedJedisPool;
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}
}
