package com.hfepay.commons.utils;

import java.io.Serializable;

/**
 * 搜索条件，用于服务层接口多条件查询时传输时使用
 * @author Sam
 *
 */
public class PagingCondition extends AbstractPagination implements Pagination,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8254943719409050750L;

	/**
	 * 关联分页查询，因为有可能要用到这个条件进行查询，但又不想分页
	 */
	protected boolean disabledPagingQuery = false;

	public boolean isDisabledPagingQuery() {
		return disabledPagingQuery;
	}

	public void setDisabledPagingQuery(boolean disabledPagingQuery) {
		this.disabledPagingQuery = disabledPagingQuery;
	}
	
	
	 
}
