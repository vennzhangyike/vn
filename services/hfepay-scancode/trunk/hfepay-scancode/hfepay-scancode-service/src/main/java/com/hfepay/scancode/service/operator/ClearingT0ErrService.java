/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

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
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	public PagingResult<ClearingT0Err> findPagingResult(ClearingT0ErrCondition clearingT0ErrCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: List<ClearingT0Err>
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	public List<ClearingT0Err> findAll(ClearingT0ErrCondition clearingT0ErrCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT0Err
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	ClearingT0Err findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	long insert(ClearingT0ErrCondition clearingT0ErrCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	long update(ClearingT0ErrCondition clearingT0ErrCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	long updateByCriteria(ClearingT0ErrCondition clearingT0ErrCondition,Criteria criteria);
	
	/**
	 * 
	 * @author liushuyu
	 * Desc进行多张表的更新 
	 * @param id
	 * @param checkFlag
	 * @return
	 * @throws Exception 
	 */
	boolean updateMuti(String id, String checkFlag);
	
}

