/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.AgentPromotionCondition;
import com.hfepay.scancode.commons.entity.AgentPromotion;

public interface AgentPromotionService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param agentPromotionCondition
	 * @return: PagingResult<AgentPromotion>
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	public PagingResult<AgentPromotion> findPagingResult(AgentPromotionCondition agentPromotionCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param agentPromotionCondition
	 * @return: List<AgentPromotion>
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	public List<AgentPromotion> findAll(AgentPromotionCondition agentPromotionCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: AgentPromotion
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	AgentPromotion findById(String id);
	
	/**
	 * @Title: findByAgentNo
	 * @Description: 代理商编号查找
	 * @author: Ricky
	 * @param agentNo
	 * @return: AgentPromotion
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	public AgentPromotion findByAgentNo(String agentNo);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param agentPromotionCondition
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	long insert(AgentPromotionCondition agentPromotionCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param agentPromotionCondition
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	long update(AgentPromotionCondition agentPromotionCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param agentPromotionCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 09:57:59
	 */
	long updateByCriteria(AgentPromotionCondition agentPromotionCondition,Criteria criteria);
	
	
}

