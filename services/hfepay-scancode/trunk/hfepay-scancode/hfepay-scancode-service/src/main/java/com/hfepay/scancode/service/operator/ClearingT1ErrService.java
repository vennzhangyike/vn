/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ClearingT1ErrCondition;
import com.hfepay.scancode.commons.entity.ClearingT1Err;

public interface ClearingT1ErrService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: PagingResult<ClearingT1Err>
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	public PagingResult<ClearingT1Err> findPagingResult(ClearingT1ErrCondition clearingT1ErrCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: List<ClearingT1Err>
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	public List<ClearingT1Err> findAll(ClearingT1ErrCondition clearingT1ErrCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT1Err
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	ClearingT1Err findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	long insert(ClearingT1ErrCondition clearingT1ErrCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	long update(ClearingT1ErrCondition clearingT1ErrCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	long updateByCriteria(ClearingT1ErrCondition clearingT1ErrCondition,Criteria criteria);

	
	/**
	 * 
	 * @author liushuyu
	 * Desc 进行多张表的更新 
	 * @param id
	 * @param checkFlag
	 * @return
	 */
	boolean updateMuti(String id, String checkFlag);
	
	
}

