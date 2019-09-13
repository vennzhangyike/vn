package com.hfepay.cache.support.proxy;

import java.lang.reflect.Proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.hfepay.cache.support.stereotype.Cacheable;
import com.hfepay.cache.support.utils.ReflectUtil;

/**   
* @Title: CacheableBeanProccessor.java 
* @Package cn.hfepay.cache.support.proxy 
* @Description: TODO
* @author maozk
* @date 2016年3月18日 下午4:28:29 
*/
@Component
public class CacheableBeanProccessor implements BeanPostProcessor{

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(ReflectUtil.objectHasAnnotation(bean, Cacheable.class)){
			return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new CacheMethodProxy(bean));
		}
		return bean;
	}

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}
