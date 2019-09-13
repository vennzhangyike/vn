package com.hfepay.scancode.service.operator;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.AgentPaywayCondition;
import com.hfepay.scancode.commons.entity.AgentPayway;

public interface AgentPaywayService {
	
	/**
	 * 列表(分页)
	 * @param agentPaywayCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	public PagingResult<AgentPayway> findPagingResult(AgentPaywayCondition agentPaywayCondition);
	
	/**
	 * 列表
	 * @param agentPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	public List<AgentPayway> findAll(AgentPaywayCondition agentPaywayCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	AgentPayway findById(String id);
	
	/**
	 * 根据支付通道编号查询
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	AgentPayway findByPayCode(String payCode,String agentNo);
	
	/**
	 * 新增
	 * @param agentPaywayCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	long insert(AgentPaywayCondition agentPaywayCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	long update(AgentPaywayCondition agentPaywayCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	long updateByCriteria(AgentPaywayCondition agentPaywayCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	long updateStatus(String id,String status);	
	
	
	/**
	 * 列表(分页)
	 * @param agentPaywayCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	public PagingResult<AgentPayway> findPagingResultByAgentNo(AgentPaywayCondition agentPaywayCondition,String nodeSeq);
}

