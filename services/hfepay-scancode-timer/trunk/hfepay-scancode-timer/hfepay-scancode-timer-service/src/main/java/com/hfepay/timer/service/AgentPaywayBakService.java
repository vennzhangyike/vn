package com.hfepay.timer.service;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.AgentPaywayBakCondition;
import com.hfepay.scancode.commons.entity.AgentPaywayBak;

public interface AgentPaywayBakService {
	
	/**
	 * 列表(分页)
	 * @param agentPaywayBakCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	public PagingResult<AgentPaywayBak> findPagingResult(AgentPaywayBakCondition agentPaywayBakCondition);
	
	/**
	 * 列表
	 * @param AgentPaywayBak 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	public List<AgentPaywayBak> findAll(AgentPaywayBakCondition agentPaywayBakCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	AgentPaywayBak findById(String id);
	
	/**
	 * 根据支付通道编号查询
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	AgentPaywayBak findByPayCode(String payCode,String agentNo);
	
	/**
	 * 新增
	 * @param agentPaywayCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	long insert(AgentPaywayBakCondition agentPaywayBakCondition);

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
	long update(AgentPaywayBakCondition agentPaywayBakCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	long updateByCriteria(AgentPaywayBakCondition agentPaywayBakCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	long updateStatus(String id,String status);	
	
	
	/**
	 * 列表(分页)
	 * @param agentPaywayBakCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
	public PagingResult<AgentPaywayBak> findPagingResultByAgentNo(AgentPaywayBakCondition agentPaywayBakCondition,List<String> list);

	/**
	 * 删除数据
	 * @Title: doTruncateTable
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	public void doTruncateTable();

	/**
	 * 备份数据
	 * @Title: doBackUpTable
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	public void doBackUpTable();
}

