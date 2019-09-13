package com.hfepay.commons.criteria.impl;
/**
 * 数据表列与实体属性映射器，用于根据VO属取得某个数据表列，这个是用于Criteria内部中一个可以动态插入的映射器，
 * Criteria会根据映射器，在渲染查询条件时将属性替换成实际的数据表列名。
 * 
 * @author Sam
 *
 */
public interface ColumnMapper {
	/**
	 * 获取某个映射好的数据表列名
	 * @param property
	 * @return
	 */
	public Column getColumn(Class<?> entityClass,String property);
}
