/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.UserMessageCondition;
import com.hfepay.scancode.commons.entity.UserMessage;

public interface UserMessageService {
	
	/**
	 * 列表(分页)
	 * @param userMessageCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	public PagingResult<UserMessage> findPagingResult(UserMessageCondition userMessageCondition);
	
	/**
	 * 列表
	 * @param userMessage 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	public List<UserMessage> findAll(UserMessageCondition userMessageCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	UserMessage findById(String id);
	
	/**
	 * 新增
	 * @param userMessageCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	long insert(UserMessageCondition userMessageCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 批量删除
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	public long batchDeleteById(List<String> list);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	long update(UserMessageCondition userMessageCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:49:05
	 */
	long updateByCriteria(UserMessageCondition userMessageCondition,Criteria criteria);
	
	public UserMessage findByMessageId(String messageId);
	
	public UserMessage findByMessageIdByUserNo(String messageId,String userName);
	
	public PagingResult<UserMessage> findByUserType(UserMessageCondition userMessageCondition,List<String> list);
}

