/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.condition.ApiLogCondition;
import com.hfepay.scancode.api.entity.ApiLog;

public interface ApiLogService {
	
	/**
	 * 列表(分页)
	 * @param apiLogCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	public PagingResult<ApiLog> findPagingResult(ApiLogCondition apiLogCondition);
	
	/**
	 * 列表
	 * @param apiLog 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	public List<ApiLog> findAll(ApiLogCondition apiLogCondition);
	
	/**
	 * 列表
	 * @param apiLog 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	public List<ApiLog> findByTradeNo(ApiLogCondition apiLogCondition);
	
	
	/**
	 * 列表
	 * @param apiLog 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	public ApiLog findByTradeNo(String tradeNo);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	ApiLog findById(String id);
	
	/**
	 * 新增
	 * @param apiLogCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	long insert(ApiLogCondition apiLogCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	long update(ApiLogCondition apiLogCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	long updateByCriteria(ApiLogCondition apiLogCondition,Criteria criteria);
	
	/**
	 * 条件更新
	 * @author panq
	 * @date CreateDate : 2016-10-28 14:51:39
	 */
	long updateByTradeNo(ApiLogCondition apiLogCondition);
	
}

