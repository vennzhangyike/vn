
package com.hfepay.commons.base.lang;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.hfepay.commons.base.collection.Collections;


/**
 * 继承common-lang3的ObjectUtils扩展一些自定义的方法
 * @author Sam
 *
 */
public final class Objects extends ObjectUtils{
	
	/**
	 * 将某个对象toString
	 * @param object
	 * @param style
	 * @return
	 */
	public static String toString(final Object object, final ToStringStyle style) {
		return ReflectionToStringBuilder.toString(object,style);
	}
	
	/**
	 * 判断某个JAVA对象是否为空
	 * @param o 任何JAVA对象
	 * @return true表示对象不为空，不为空包含了空引用及空值,如String s=""，这时的对象应该是空的；false反之
	 */
    public static boolean isEmpty(Object o) {

        if (o == null)
            return true;
        if (isCharSequence(o))
            return Strings.isEmpty((CharSequence) o);
        else if (isCollection(o))
            return Collections.isEmpty(Collection.class.cast(o));
        else if (isMap(o))
            return MapUtils.isEmpty(Map.class.cast(o));
        else { 
            if (isArray(o))
                return Array.getLength(o) <= 0;
            else
                return false;
        }
        
    }
    
    /**
	 * 判断某个对象是否是数组类型
	 * @param o 
	 * @return
	 */
    public static boolean isArray(Object o) {
    	Class<?> t = o.getClass();
    	return t.isArray();
    }
    
    
    /**
	 * 判断某个对象是否是Map类型
	 * @param o 要判断的对象
	 * @return true为map类型,否则不是。
	 */
	public static boolean isMap(Object o) {
		return o instanceof Map<?,?>;
	}
	
	/**
	 * 判断某个对象是否是Collection类型
	 * @param o 
	 * @return
	 */
	public static boolean isCollection(Object o) {
		return o instanceof Collection<?>;
	}
	
	/**
	 * 判断某个对象是否是CharSequence类型
	 * @param o
	 * @return
	 */
	public static boolean isCharSequence(Object o) {
		return o instanceof CharSequence;
	}
}
