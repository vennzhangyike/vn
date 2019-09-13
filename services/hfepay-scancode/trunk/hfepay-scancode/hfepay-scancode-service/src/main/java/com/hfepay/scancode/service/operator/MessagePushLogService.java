/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MessagePushLogCondition;
import com.hfepay.scancode.commons.entity.MessagePushLog;

public interface MessagePushLogService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param messagePushLogCondition
	 * @return: PagingResult<MessagePushLog>
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	public PagingResult<MessagePushLog> findPagingResult(MessagePushLogCondition messagePushLogCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param messagePushLogCondition
	 * @return: List<MessagePushLog>
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	public List<MessagePushLog> findAll(MessagePushLogCondition messagePushLogCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MessagePushLog
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	MessagePushLog findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param messagePushLogCondition
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	long insert(MessagePushLogCondition messagePushLogCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param messagePushLogCondition
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	long update(MessagePushLogCondition messagePushLogCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param messagePushLogCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	long updateByCriteria(MessagePushLogCondition messagePushLogCondition,Criteria criteria);
	
	
}

