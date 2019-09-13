/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MarketingPayLogCondition;
import com.hfepay.scancode.commons.entity.MarketingPayLog;

public interface MarketingPayLogService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param marketingPayLogCondition
	 * @return: PagingResult<MarketingPayLog>
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	public PagingResult<MarketingPayLog> findPagingResult(MarketingPayLogCondition marketingPayLogCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param marketingPayLogCondition
	 * @return: List<MarketingPayLog>
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	public List<MarketingPayLog> findAll(MarketingPayLogCondition marketingPayLogCondition);
	
	
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MarketingPayLog
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	MarketingPayLog findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param marketingPayLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	long insert(MarketingPayLogCondition marketingPayLogCondition);

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
	 * @param marketingPayLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	long update(MarketingPayLogCondition marketingPayLogCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param marketingPayLogCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	long updateByCriteria(MarketingPayLogCondition marketingPayLogCondition,Criteria criteria);
	/** 金额统计 */
	Map<String, BigDecimal> getAmtStatic(List<MarketingPayLog> marketingPayLogs);	
	
}

