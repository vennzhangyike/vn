/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MessagePushRuleCondition;
import com.hfepay.scancode.commons.entity.MessagePushRule;

public interface MessagePushRuleService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param messagePushRuleCondition
	 * @return: PagingResult<MessagePushRule>
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	public PagingResult<MessagePushRule> findPagingResult(MessagePushRuleCondition messagePushRuleCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param messagePushRuleCondition
	 * @return: List<MessagePushRule>
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	public List<MessagePushRule> findAll(MessagePushRuleCondition messagePushRuleCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MessagePushRule
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	MessagePushRule findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param messagePushRuleCondition
	 * @return: long
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	long insert(MessagePushRuleCondition messagePushRuleCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param messagePushRuleCondition
	 * @return: long
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	long update(MessagePushRuleCondition messagePushRuleCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param messagePushRuleCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	long updateByCriteria(MessagePushRuleCondition messagePushRuleCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	long updateStatus(String id,String status);

	/** 推送消息 */
	void messagePush();	
	
}

