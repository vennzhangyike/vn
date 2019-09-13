package com.hfepay.cache.support.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**   
* @Title: ReflectUtil.java 
* @Package cn.hfepay.cache.support.utils 
* @Description: TODO
* @author maozk
* @date 2016年3月21日 上午11:44:03 
*/
@SuppressWarnings("all")
public class ReflectUtil {
	
	public static boolean objectHasAnnotation(Object object,Class annotation){
		Class clazz = object.getClass();
		Method[] classMethods = clazz.getDeclaredMethods();
		boolean classMethodBeAnnotated = false;
		for (Method method : classMethods) {
			classMethodBeAnnotated = classMethodBeAnnotated || method.isAnnotationPresent(annotation);
		}
		if(classMethodBeAnnotated){
			return true;
		}
		Class[] classInterfaces = clazz.getInterfaces();
		boolean interfaceMethodBeAnnotated = false;
		for (Class classInterface : classInterfaces) {
			Method[] interfaceMethods = classInterface.getDeclaredMethods();
			for (Method method : interfaceMethods) {
				interfaceMethodBeAnnotated = interfaceMethodBeAnnotated || method.isAnnotationPresent(annotation);
			}
		}
		return interfaceMethodBeAnnotated;
	}
	
	public static <T extends Annotation> T methodAnnotation(Object object,Method method,Class<T> annotationClass){
		T annotation = method.getAnnotation(annotationClass);
		return annotation;
	}
}
