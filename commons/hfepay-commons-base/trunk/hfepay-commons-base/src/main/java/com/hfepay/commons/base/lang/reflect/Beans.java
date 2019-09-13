package com.hfepay.commons.base.lang.reflect;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.hfepay.commons.base.lang.Assert;
import com.hfepay.commons.base.lang.Throwables;


/**
 * 封装基于BeanUtils的一系列方法，
 * @author Sam
 *
 */
public class Beans  {
	@SuppressWarnings("unchecked")
	public static <T> T convert(String value, Class<T> clazz) {
        return (T) ConvertUtils.convert(value, clazz);
    }
	
	public static Object convert(String[] values, Class<?> clazz) {
		return ConvertUtils.convert(values, clazz);
	}
	
	public static Object convert(Object value, Class<?> targetType) {
		return ConvertUtils.convert(value, targetType);
	}
	
	public static void register(Converter converter, Class<?> clazz) {
		ConvertUtils.register(converter, clazz);
    }
	
	public static String getSimpleProperty(Object bean, String name) {
		Assert.noneNull(bean,name);
		try {
			return BeanUtils.getSimpleProperty(bean, name);
		} catch ( Exception e) {
			throw throwEx(e);
		}
	}
	
	public static void setProperty(Object bean, String name, Object value) {
		try {
			BeanUtils.setProperty(bean, name, value);
		} catch (Exception e) {
			throw throwEx(e);
		}
	}
	
	public static void populate(Object bean, Map<String, ? extends Object> properties) {
		try {
			BeanUtils.populate(bean, properties);
		} catch ( Exception e) {
			throw throwEx(e);
		}		
	}
	
	/**
     * 根据map值设置bean的属性
     * 
     * @param <T>
     * @param t
     * @param properties
     * @return
     */
    public static <T> T setProperties(T t, Map<String, ? extends Object> properties) {

        populate(t, properties);
        return t;
    }
    
    /**
     * 根据指定类型生成一个新的对象，并根据提供的map值设置其属性值
     * 
     * @param <T>
     * @param type
     * @param properties
     * @return
     */
    public static <T> T newBean(Class<T> type, Map<String, ? extends Object> properties) {

        T t = Reflector.of(type).newOne();
        t = setProperties(t, properties);
        return t;
    }
    
     
	
	private static RuntimeException throwEx(Exception e) {
		return Throwables.wrapThrow(e,"invoke BeanUtils error,please check the arguments!");
	}
	
}
