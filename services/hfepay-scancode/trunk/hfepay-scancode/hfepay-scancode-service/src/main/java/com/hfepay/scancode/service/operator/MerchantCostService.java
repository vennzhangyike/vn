/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.entity.MerchantCost;

public interface MerchantCostService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @return: PagingResult<MerchantCost>
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	public PagingResult<MerchantCost> findPagingResult(MerchantCostCondition merchantCostCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @return: List<MerchantCost>
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	public List<MerchantCost> findAll(MerchantCostCondition merchantCostCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MerchantCost
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	MerchantCost findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	long insert(MerchantCostCondition merchantCostCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	long update(MerchantCostCondition merchantCostCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	long updateByCriteria(MerchantCostCondition merchantCostCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	long updateStatus(String id,String status);

	/** 金额统计 */
	Map<String, BigDecimal> getAmtStatistics(MerchantCostCondition merchantCostCondition);	
	
}

