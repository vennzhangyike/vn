package com.hfepay.cache.support.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.hfepay.cache.support.Cacher;
import com.hfepay.cache.support.CacherResourceHolder;
import com.hfepay.cache.support.stereotype.Cacheable;
import com.hfepay.cache.support.utils.ReflectUtil;

/**   
* @Title: CacheMethodProxy.java 
* @Package cn.hfepay.cache.support.proxy 
* @Description: TODO
* @author maozk
* @date 2016年3月18日 下午4:16:07 
*/
@SuppressWarnings("all")
public class CacheMethodProxy implements InvocationHandler{
	
	Object sourceObject;
	
	public CacheMethodProxy(Object sourceObject){
		this.sourceObject = sourceObject;
	}
	
	public Object invoke(Object proxyObject, Method method, Object[] params) throws Throwable {
		Cacheable annotation = ReflectUtil.methodAnnotation(sourceObject, method, Cacheable.class);
		if(annotation != null){
			Object key = uniqueKeyOfMethod(method.getDeclaringClass(), method, params);
			Cacher cacher = CacherResourceHolder.get("annotation_cache");
			Object cachedResult = cacher.get(key);
			if(cachedResult != null){
				return cachedResult;
			}else{
				Object invokeResult = method.invoke(sourceObject, params);
				cacher.put(key, invokeResult, annotation.timeout());
				return invokeResult;
			}
		}
		return method.invoke(sourceObject, params);
	}
	
	protected Object uniqueKeyOfMethod(Class clazz,Method method,Object[] params){
		String className = clazz.getName();
		String methodName = method.getName();
		Class[] paramTypes = method.getParameterTypes();
		return new MethodUniqueKey(className, methodName, paramTypes, params);
	}
}
