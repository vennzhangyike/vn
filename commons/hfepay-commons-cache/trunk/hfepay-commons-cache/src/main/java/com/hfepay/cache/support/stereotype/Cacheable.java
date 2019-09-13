package com.hfepay.cache.support.stereotype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**   
* @Title: Cacheable.java 
* @Package cn.hfepay.cache.support.stereotype 
* @Description: TODO
* @author maozk
* @date 2016年3月18日 下午4:10:19 
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {
	
	/**
	 * 超时时间 单位（秒）
	 * @return
	 */
	long timeout() default 30;
	
	/**
	 * 缓存器名称
	 * @return
	 */
	String cacherName() default "DEFUALT_CACHER";
	
}
