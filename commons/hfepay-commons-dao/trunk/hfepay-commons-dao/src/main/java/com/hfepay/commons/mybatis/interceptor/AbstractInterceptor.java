package com.hfepay.commons.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 抽象拦截器，提供一个基础
 * @author Sam
 *
 */
public abstract class AbstractInterceptor implements Interceptor {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected Properties properties; 

	public void setProperties(Properties properties) {
		this.properties = properties;
		afterSetProperties();
	}
	
	protected void afterSetProperties(){}
	
	public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
