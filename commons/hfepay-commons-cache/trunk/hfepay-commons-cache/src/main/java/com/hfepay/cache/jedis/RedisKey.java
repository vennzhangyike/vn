package com.hfepay.cache.jedis;

import java.io.Serializable;

public class RedisKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5399411746999150166L;

	 //每个业务不同的family
    private String module;
      
    private String key;
          
    public RedisKey(String module, String key) {
		super();
		this.module = module;
		this.key = key;
	}

	//物理保存在Redis上的key为经过MurmurHash之后的值 
//    private String makeRedisHashKey(){
//        return String.valueOf(makeRedisKeyString().hashCode());
//    }

   
	public void setModule(String module) {
		this.module = module;
	}

	public void setKey(String key) {
		this.key = key;
	}

	//ReidsKey由family.key组成
    private String makeRedisKeyString(){
        return module +":"+ key;
    }

    //返回用户的经过Hash之后RedisKey
    public String getRedisKey(){
        return makeRedisKeyString();
    }
}
