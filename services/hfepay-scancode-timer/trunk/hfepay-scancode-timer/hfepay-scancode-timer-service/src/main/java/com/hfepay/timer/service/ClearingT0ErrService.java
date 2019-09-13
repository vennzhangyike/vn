/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ClearingT0ErrCondition;
import com.hfepay.scancode.commons.entity.ClearingT0Err;

public interface ClearingT0ErrService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: PagingResult<ClearingT0Err>
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	public PagingResult<ClearingT0Err> findPagingResult(ClearingT0ErrCondition clearingT0ErrCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: List<ClearingT0Err>
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	public List<ClearingT0Err> findAll(ClearingT0ErrCondition clearingT0ErrCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT0Err
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	ClearingT0Err findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	long insert(ClearingT0ErrCondition clearingT0ErrCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	long update(ClearingT0ErrCondition clearingT0ErrCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	long updateByCriteria(ClearingT0ErrCondition clearingT0ErrCondition,Criteria criteria);

	/** t0对账数据对比 */
	List<ClearingT0Err> checkT0Data();

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t0Err
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	void batchInsert(List<ClearingT0Err> t0ErrList);
	
	
}

