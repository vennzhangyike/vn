package com.hfepay.cache.jedis;

import java.util.ArrayList;
import java.util.List;

import com.hfepay.cache.jedis.exceptions.JedisException;
import com.hfepay.cache.utils.PropertiesUtils;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @Title: JedisShardedPoolTool
 * @Description: Jedis 模板
 * @param 
 * @return 
 * @exception JedisException
 */
public abstract  class JedisShardedPoolTool {
	public ShardedJedisPool shardedJedisPool;
	
	public JedisShardedPoolTool(){
		initialShardedPool(); 
	}

	/**
	 * 初始化切片池
	 */
	private void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		
		PropertiesUtils pro =PropertiesUtils.getInstance("commons-cache-hfepay.properties");
		
		//空闲对象最大数
        config.setMaxIdle(Integer.valueOf(pro.getValue("hfepay.jedis.pool.maxIdle")));
        
        //最大链接数
        config.setMaxTotal(Integer.valueOf(pro.getValue("hfepay.jedis.pool.maxTotal")));
        
        //最长等待时间
        config.setMaxWaitMillis(Integer.valueOf(pro.getValue("hfepay.jedis.pool.maxWaitMillis")));
        
        //验证链接是否有效，如果失效则被丢弃
        config.setTestOnBorrow(Boolean.valueOf(pro.getValue("hfepay.jedis.pool.testOnBorrow")));
        
		config.setTestOnBorrow(false);
		
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		
		String[] ips = pro.getValue("hfepay.jedis.pool.ip").split(",");
        String[] ports = pro.getValue("hfepay.jedis.pool.port").split(",");
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
	
}
