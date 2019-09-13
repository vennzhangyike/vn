/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.AgentBankcardCondition;
import com.hfepay.scancode.commons.entity.AgentBankcard;

public interface AgentBankcardService {
	
	/**
	 * 列表(分页)
	 * @param agentBankcardCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	public PagingResult<AgentBankcard> findPagingResult(AgentBankcardCondition agentBankcardCondition);
	
	/**
	 * 列表(分页)
	 * @param agentBankcardCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	public PagingResult<AgentBankcard> findPagingResultByAgentNo(AgentBankcardCondition agentBankcardCondition,String nodeSeq);
	
	/**
	 * 列表
	 * @param agentBankcard 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	public List<AgentBankcard> findAll(AgentBankcardCondition agentBankcardCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	AgentBankcard findById(String id);
	
	/**
	 * 新增
	 * @param agentBankcardCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	long insert(AgentBankcardCondition agentBankcardCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	long update(AgentBankcardCondition agentBankcardCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	long updateByCriteria(AgentBankcardCondition agentBankcardCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-24 17:12:31
	 */
	long updateStatus(String id,String status);

	/**
	 * @Title: findByAgentNo
	 * @Description: TODO
	 * @author: husain
	 * @param agentNo
	 * @return: AgentBankcard
	 */
	public AgentBankcard findByAgentNo(String agentNo);	
	
}

