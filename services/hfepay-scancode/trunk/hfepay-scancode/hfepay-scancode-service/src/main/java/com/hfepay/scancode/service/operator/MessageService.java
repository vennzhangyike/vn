/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MessageCondition;
import com.hfepay.scancode.commons.entity.Message;

public interface MessageService {
	
	/**
	 * 列表(分页)
	 * @param messageCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	public PagingResult<Message> findPagingResult(MessageCondition messageCondition);
	
	/**
	 * 列表
	 * @param message 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	public List<Message> findAll(MessageCondition messageCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	Message findById(String id);
	
	/**
	 * 新增
	 * @param messageCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	long insert(MessageCondition messageCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	long update(MessageCondition messageCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	long updateByCriteria(MessageCondition messageCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-26 17:48:59
	 */
	long updateStatus(String id,String status);	
	
	
	public List<Message> findAllByUserType(List<String> list);
	
	public List<Message> findAllByUserTypeAndUserNo(List<String> list,String userNo);
	
	public PagingResult<Message> findPagingResultByUserType(MessageCondition messageCondition,List<String> list);
}

