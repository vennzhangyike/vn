/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ClearingT0Condition;
import com.hfepay.scancode.commons.entity.ClearingT0;

public interface ClearingT0Service {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param clearingT0Condition
	 * @return: PagingResult<ClearingT0>
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	public PagingResult<ClearingT0> findPagingResult(ClearingT0Condition clearingT0Condition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT0Condition
	 * @return: List<ClearingT0>
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	public List<ClearingT0> findAll(ClearingT0Condition clearingT0Condition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT0
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	ClearingT0 findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param clearingT0Condition
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	long insert(ClearingT0Condition clearingT0Condition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT0Condition
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	long update(ClearingT0Condition clearingT0Condition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT0Condition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	long updateByCriteria(ClearingT0Condition clearingT0Condition,Criteria criteria);

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t0List
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	public void batchInsert(List<ClearingT0> t0List);
	
	
}

