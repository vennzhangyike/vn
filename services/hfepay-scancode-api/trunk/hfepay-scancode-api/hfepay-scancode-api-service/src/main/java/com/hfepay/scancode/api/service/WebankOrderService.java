/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.api.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.condition.WebankOrderCondition;
import com.hfepay.scancode.api.entity.WebankOrder;

public interface WebankOrderService {
	
	/**
	 * 列表(分页)
	 * @param webankOrderCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	public PagingResult<WebankOrder> findPagingResult(WebankOrderCondition webankOrderCondition);
	
	/**
	 * 列表
	 * @param webankOrder 
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	public List<WebankOrder> findAll(WebankOrderCondition webankOrderCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	WebankOrder findById(String id);
	
	/**
	 * 新增
	 * @param webankOrderCondition
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	long insert(WebankOrderCondition webankOrderCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	long update(WebankOrderCondition webankOrderCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-03-13 17:55:20
	 */
	long updateByCriteria(WebankOrderCondition webankOrderCondition,Criteria criteria);
	
	public WebankOrder findByTradeNo(String tradeNo);
}

