package com.hfepay.commons.criteria;


import java.util.List;
import java.util.Map;
 

/**
 * 封装页面查询条件
 * 
 * @author Sam
 *
 */
public interface Criteria {
	/**
	 * 设置分页查询的起止索引值
	 * @param start 分页查询开始索引
	 * @param end 分页查询结束索引
	 */
	Criteria limit(Long start, Long end);
	
	/**
	 * 设置分页查询开始索引
	 * @param start 分页查询开始索引
	 */
	Long getFirst();
	
	/**
	 * 设置结束查询开始索引
	 * @param end 分页查询结束索引
	 */
	Long getLast();
	
	/**
	 * 添加一个或多个过滤条件
	 * @param conditions 过滤条件
	 */
	Criteria add(Condition...conditions); 
	
	/**
	 *  添加一个或多个过滤条件
	 *  @param conditions 过滤条件
	 */
	Criteria add(List<Condition> conditions);
	
	/**
	 * 添加一个查询排序条件
	 * @param order 排序条件
	 */
	Criteria orderBy(OrderBy order);
	
	/**
	 * 是否要使用分页查询,如果设置为false，就算设置了分页查询起止索引也不会执行分页查询。
	 * @param limitable 分页查询标志
	 */
	Criteria useLimitit(Boolean limitable);
	
	/**
	 * 是否要使用分页查询
	 */
	Boolean isHasLimit();
	
	/**
	 * 是否有过滤条件
	 * @return true表示有，否则没有。
	 */
	Boolean isHasConditionon();
	
	/**
	 * 是否有排序条件
	 * @return true表示有，否则没有。
	 */
	Boolean isHasOrderBy();
	
	
	/**
	 * 返回所有的查询过滤条件
	 */
	List<Condition> getConditions();
	
	/**
	 * 返回所有的排序条件
	 *
	 * @return
	 */
	List<OrderBy> getOrderBys();	
	
	/**
	 * 返回所有扩展的属性，用于Condition无法满足的情况下，可以自定义一些参数传给Dao
	 * @return
	 */
	Map<?, ?> getParams();
	
	/**
	 * 设定义参数，　做为过滤条件
	 * @param params
	 * @return
	 */
	Criteria setParams(Map<?, ?> params);
	
	/**
	 * 增加自定义条件参数
	 * @param paramName
	 * @param value
	 * @return
	 */
	Criteria addParam(Object paramName, Object value);
	
	/**
	 * 增加自定义条件参数
	 * @param values
	 * @return
	 */
	Criteria addParams(Object...values);
	
	String toString();
}
