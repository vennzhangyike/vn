/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.HfepayPaywayCondition;
import com.hfepay.scancode.commons.entity.HfepayPayway;

public interface HfepayPaywayService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @return: PagingResult<HfepayPayway>
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	public PagingResult<HfepayPayway> findPagingResult(HfepayPaywayCondition hfepayPaywayCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @return: List<HfepayPayway>
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	public List<HfepayPayway> findAll(HfepayPaywayCondition hfepayPaywayCondition);
	
	
	/**
	 * @Title: findByPayCode
	 * @Description: 列表
	 * @author: Ricky
	 * @param payCode
	 * @return: HfepayPayway
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	public HfepayPayway findByPayCode(String payCode);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: HfepayPayway
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	HfepayPayway findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	long insert(HfepayPaywayCondition hfepayPaywayCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	long update(HfepayPaywayCondition hfepayPaywayCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	long updateByCriteria(HfepayPaywayCondition hfepayPaywayCondition,Criteria criteria);
	
	
}

