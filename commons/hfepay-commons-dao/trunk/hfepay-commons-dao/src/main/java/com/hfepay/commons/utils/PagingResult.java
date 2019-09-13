package com.hfepay.commons.utils;

import java.util.List;

/**
 * 分页查询返回的查询结果集
 * 
 * @author Sam
 *
 */
public interface PagingResult<E> {
	/**
	 * 分页查询时的总记录条数
	 * @return 
	 */
	Long getTotalCount();
	
	/**
	 * 分页查询时某一页的查询结果
	 * @return
	 */
	List<E> getResult();
}
