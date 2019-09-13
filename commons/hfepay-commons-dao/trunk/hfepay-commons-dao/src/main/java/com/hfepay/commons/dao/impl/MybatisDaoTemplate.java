package com.hfepay.commons.dao.impl;

public interface MybatisDaoTemplate {
	public static final String INSERT = "insert";

	public static final String UPDATE = "update";
	
	public static final String UPDATE_BY_CRITERIA = "updateByCriteria";

	public static final String DELETE = "delete";
	
	public static final String DELETE_BY_ID = "deleteById";
	
	public static final String DELETE_BY_CRITERIA = "deleteByCriteria";

	public static final String COUNT = "count";

	public static final String FIND_BY_ID = "findById";
	
	public static final String FIND_BY_MAP = "findByMap";
	
	public static final String COUNT_BY_CRITERIA = "countByCriteria";
	
	public static final String FIND_BY_CRITERIA = "findByCriteria";
	
	public static final String FIND_PAGING_RESULT = "findPagingList";
}
