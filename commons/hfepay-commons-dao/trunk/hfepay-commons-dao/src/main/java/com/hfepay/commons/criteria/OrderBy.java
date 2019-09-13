package com.hfepay.commons.criteria;



/**
 * 查询排序条件
 * 
 * @author Sam
 *
 */
public interface OrderBy {
	public String getName();
	public Order getOrder();
	public String toString();
}
