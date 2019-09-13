package com.hfepay.commons.base.collection;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.SetUtils;


/**
 * set 工具集
 * @author Sam
 *
 */
public class Sets extends SetUtils {
	public static <T>Set<T> newSet() {
		return new HashSet<T>();
	}
}
