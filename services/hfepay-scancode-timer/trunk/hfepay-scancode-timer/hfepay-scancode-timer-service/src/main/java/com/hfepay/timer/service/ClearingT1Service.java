/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ClearingT1Condition;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.entity.ClearingT1;

public interface ClearingT1Service {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param clearingT1Condition
	 * @return: PagingResult<ClearingT1>
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	public PagingResult<ClearingT1> findPagingResult(ClearingT1Condition clearingT1Condition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT1Condition
	 * @return: List<ClearingT1>
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	public List<ClearingT1> findAll(ClearingT1Condition clearingT1Condition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT1
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	ClearingT1 findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param clearingT1Condition
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	long insert(ClearingT1Condition clearingT1Condition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT1Condition
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	long update(ClearingT1Condition clearingT1Condition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT1Condition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	long updateByCriteria(ClearingT1Condition clearingT1Condition,Criteria criteria);
	
	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t1List
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	void batchInsert(List<ClearingT1> t1List);
}

