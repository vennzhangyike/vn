package com.hfepay.commons.base.collection;

import java.lang.reflect.Array;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

/**
 * 基于Collection类型集合工具处理类
 * 
 * @author Sam
 *
 */
public abstract class Collections extends CollectionUtils{
	 
    
    /**
     * 获取Collection中的第一个对象
     * 
     * @param <T>
     * @param c
     * @return
     */ 
    @SuppressWarnings("unchecked")
	public static <T> T first(Collection<T> c) { 
        return isNotEmpty(c) ? (T) get(c, 0) : null;
    }
    
    /**
     * 将某个Collection转成数组
     * 
     * @param <T>
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> c) {

        if (isEmpty(c))
            return null;
        T t = first(c);
        T[] tArray = (T[]) Array.newInstance(t.getClass(), c.size());
        c.toArray(tArray);
        return tArray;
    }
}
