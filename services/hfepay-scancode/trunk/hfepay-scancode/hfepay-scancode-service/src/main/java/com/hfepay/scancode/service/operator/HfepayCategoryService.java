/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.HfepayCategoryCondition;
import com.hfepay.scancode.commons.entity.HfepayCategory;

public interface HfepayCategoryService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param hfepayCategoryCondition
	 * @return: PagingResult<HfepayCategory>
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	public PagingResult<HfepayCategory> findPagingResult(HfepayCategoryCondition hfepayCategoryCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param hfepayCategoryCondition
	 * @return: List<HfepayCategory>
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	public List<HfepayCategory> findAll(HfepayCategoryCondition hfepayCategoryCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: HfepayCategory
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	HfepayCategory findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param hfepayCategoryCondition
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	long insert(HfepayCategoryCondition hfepayCategoryCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param hfepayCategoryCondition
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	long update(HfepayCategoryCondition hfepayCategoryCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param hfepayCategoryCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	long updateByCriteria(HfepayCategoryCondition hfepayCategoryCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	long updateStatus(String id,String status);

	/**
	 * @Title: findByCategoryNo
	 * @Description: categoryNo查找
	 * @author: Ricky
	 * @param categoryNo
	 * @return: HfepayCategory
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	HfepayCategory findByCategoryNo(String categoryNo);	
	
}

