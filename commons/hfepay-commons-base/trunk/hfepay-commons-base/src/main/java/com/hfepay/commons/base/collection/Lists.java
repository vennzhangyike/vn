package com.hfepay.commons.base.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;


/**
 * 基于List类型的集合处理类
 * 
 * @author Sam
 * 
 */
public abstract class Lists extends ListUtils {
	
	public static <E> ArrayList<E> newList() {
		return new ArrayList<E>();
	}
	
	public static <E> LinkedList<E> newLinkedList() {
		return new LinkedList<E>();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> of(T... objs) {
		List<T> list = newList();
        CollectionUtils.addAll(list, objs);
        return list;
    }
	
    /**
     * 检测一个Collection是否为空
     * 
     * @param c
     * @return
     */
    public static boolean isEmpty(Collection<?> c) {

        return c == null || c.size() == 0;
    }
    
    /**
     * ' 检测一个Collection是否不为空
     * 
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Collection<?> c) {

        return !isEmpty(c);
    }
    
    public static <T> T[] toArray(List<T> c) {
    	return Collections.toArray(c);
    }
}
