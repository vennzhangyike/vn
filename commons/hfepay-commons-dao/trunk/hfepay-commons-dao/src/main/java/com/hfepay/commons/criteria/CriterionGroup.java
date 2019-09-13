package com.hfepay.commons.criteria;

import java.util.List;
/**
 * 组合条件
 * 
 * @author Sam
 *
 */
public interface CriterionGroup extends Condition{
	/**
	 * 添加一个或多个子条件
	 * @param conditions 条件
	 * @return
	 */
	public CriterionGroup add(Condition...conditions);
	
	/**
	 * 添加多个子条件
	 * @param conditions
	 * @return
	 */
	public CriterionGroup add(List<Condition> conditions);
	
	/**
	 * 获得这个条件组的所有子条件
	 * @return
	 */
	public List<Condition> getAll();
	 
}
