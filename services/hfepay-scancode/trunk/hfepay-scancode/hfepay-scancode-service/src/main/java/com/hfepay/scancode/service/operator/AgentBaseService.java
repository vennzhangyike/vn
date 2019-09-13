/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.entity.AgentBase;

public interface AgentBaseService {
	
	/**
	 * 列表(分页)
	 * @param agentBaseCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	public PagingResult<AgentBase> findPagingResult(AgentBaseCondition agentBaseCondition);
	
	/**
	 * 列表
	 * @param agentBase 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	public List<AgentBase> findAll(AgentBaseCondition agentBaseCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	AgentBase findById(String id);
	
	
	/**
	 * 代理商编号查找
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	AgentBase findByAgentNo(String agentNo);
	
	/**
	 * 新增
	 * @param agentBaseCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	long insert(AgentBaseCondition agentBaseCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	long update(AgentBaseCondition agentBaseCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	long updateByCriteria(AgentBaseCondition agentBaseCondition);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	long updateStatus(String id,String status);	
	
	/**
	 * 列表
	 * @param agentBase 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	public List<AgentBase> findAllByAgentNo(AgentBaseCondition agentBaseCondition,String nodeSeq);
	
	
	public void saveAgentBaseAndNode(AgentBaseCondition agentBaseCondition);
	
	/**
	 * 列表(分页)
	 * @param agentBaseCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	public PagingResult<AgentBase> findPagingResultByAgentNo(AgentBaseCondition agentBaseCondition,String ndoeSeq);

	/**
	 * 扫码成功之后修改二维码次数
	 * @Title: updateUsedTimes
	 * @Description: TODO
	 * @author: husain
	 * @param agentNo
	 * @return
	 * @return: long
	 */
	public long updateUsedTimes(String agentNo);

	/**保存公众号*/
	void savePublicNo(ChannelExpandCondition channelExpandCondition) throws Exception;

	/**设置代理商redis
	 * @throws Exception */
	void setAgentRedis() throws Exception;

	/**
	 * 单个实体对象
	 * @param agentBase 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	public AgentBase findByCondition(AgentBaseCondition agentBaseCondition);
}

