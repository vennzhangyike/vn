package com.hfepay.cache.jedis;

import java.util.HashSet;
import java.util.Set;

import com.hfepay.cache.jedis.exceptions.JedisException;
import com.hfepay.cache.utils.PropertiesUtils;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Title: JedisPoolsCacheTool
 * @Description: Jedis 模板
 * @param 
 * @return 
 * @exception JedisException
 */
public abstract  class JedisPoolsCacheTool {
	public JedisCluster jc;
	
	public JedisPoolsCacheTool(){
		jedisInit(); 
	}

	/**
	 * 初始化连接
	 */
	private void jedisInit() {
		JedisPoolConfig config = new JedisPoolConfig(); 
		PropertiesUtils pro =PropertiesUtils.getInstance("application-hfepay.properties");
		
		//空闲对象最大数
        config.setMaxIdle(Integer.valueOf(pro.getValue("hfepay.jedis.pool.maxIdle")));
        
        //最大链接数
        config.setMaxTotal(Integer.valueOf(pro.getValue("hfepay.jedis.pool.maxTotal")));
        
        //最长等待时间
        config.setMaxWaitMillis(Integer.valueOf(pro.getValue("hfepay.jedis.pool.maxWaitMillis")));
        
        //验证链接是否有效，如果失效则被丢弃
        config.setTestOnBorrow(Boolean.valueOf(pro.getValue("hfepay.jedis.pool.testOnBorrow")));
        
        // slave链接 
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        
        String[] ips = pro.getValue("hfepay.jedis.pool.ip").split(",");
        String[] ports = pro.getValue("hfepay.jedis.pool.port").split(",");
        if (ips.length != ports.length) {
        	throw new JedisException("redis ip,prot 未正确配置！");
		}
        for (int i = 0; i < ports.length; i++) {
        	jedisClusterNodes.add(new HostAndPort(ips[i], Integer.valueOf(ports[i])));
		}
        
		jc = new JedisCluster(jedisClusterNodes);
	}
	
	
	/**
	 * @Description: 从链接池获取链接
	 * @param 
	 * @return ShardedJedis
	 * @exception JedisException
	 */
	public JedisCluster getResource(){
		if (null == jc) {
			throw new JedisException("无法获取连接！");
		}
		return jc;
	}
	
}
