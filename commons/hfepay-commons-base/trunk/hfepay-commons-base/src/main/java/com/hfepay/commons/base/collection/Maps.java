package com.hfepay.commons.base.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;


/**
 * 基于Map的操作工具类
 * @author Sam
 *
 */
public class Maps extends MapUtils {
	/**
     * 获取Map的第一个Entry键值对对象
     * 
     * @param <K> key
     * @param <V> value
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Entry<K, V> first(Map<K, V> map) {

        if (isEmpty(map))
            return null;
        return (Entry<K, V>) (map.entrySet().toArray()[0]);
    }
    
    /**
     * 获取Map第一个Entry键值对对象的值
     * 
     * @param <V>
     * @param map
     * @return Entry键值对对象的值
     */
    public static <V> V valueOfFirst(Map<?, V> map) {

        Entry<?, V> e = first(map);
        return e.getValue();
    }
    
    /**
     * 创建一个空的HashMap
     * 
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> newMap() {

        return new HashMap<K, V>();
    }
    
    /**
     * 根据一组可变参数的数组对象生成一个Map，用法如下：
     * 
     * <pre>
	 * 	Maps.map(key,value,key,value....);
	 * </pre>
     * 
     * @param keyValues 可变参数数，如果为单数，则最后一个被忽略,如果长度小于2,则返回Null
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> map(Object... keyValues) {

        if (Arrays.isNotEmpty(keyValues) && keyValues.length > 1) {
            Class<?> kClass = keyValues[0].getClass();
            Class<?> vClass = keyValues[1].getClass();
            return (Map<K, V>) map(kClass, vClass, keyValues);
        }
        return null;
    }
    
    /**
     * 根据一组可变参数的数组对象生成一个Map，并且有强制类型化，用法如下：
     * 
     * <pre>
	 * Maps.map(key.class,value.class,key,value,key,value,key,value......);
	 * </pre>
     * 
     * @param kClass key类型
     * @param vClass value类型
     * @param keyValues 可变参数数，如果为单数，则最后一个被忽略,如果长度小于2,则返回Null
     * @return
     */
    public static <K, V> Map<K, V> map(Class<K> kClass, Class<V> vClass, Object... keyValues) {

        Map<K, V> m = newMap();
        int i = 1;
        Object preObj = null;
        for (Object o : keyValues) {
            if (i % 2 == 0) {
                K k = kClass.cast(preObj);
                V v = vClass.cast(o);
                m.put(k, v);
            }
            preObj = o;
            i++;
        }
        return m;
    }
    
	/**
	 * 根据一组可变参数的数组对象生成一个Map，但不会对K，V使用泛型，用法如下：
	 * 
	 * <pre>
	 * Maps.map(key,value,key,value,key,value......);
	 * </pre>
	 * 
	 * @param keyValues
	 *            可变参数数，如果为单数，则最后一个被忽略,如果长度小于2,则返回Null
	 * @return
	 */
	public static Map<Object, Object> mapByAarray(Object... keyValues) {
		Map<Object, Object> m = Maps.newMap();
		int i = 1;
		Object key = null;
		for (Object value : keyValues) {
			if (i % 2 == 0) {
				m.put(key, value);
			}
			key = value;
			i++;
		}
		return m;
	}
	
}
