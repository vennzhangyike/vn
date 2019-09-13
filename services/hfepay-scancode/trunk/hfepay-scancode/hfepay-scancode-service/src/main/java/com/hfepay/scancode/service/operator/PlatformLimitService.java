/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.PlatformLimitCondition;
import com.hfepay.scancode.commons.entity.PlatformLimit;

public interface PlatformLimitService {
	
	/**
	 * 列表(分页)
	 * @param platformLimitCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	public PagingResult<PlatformLimit> findPagingResult(PlatformLimitCondition platformLimitCondition);
	
	/**
	 * 列表
	 * @param platformLimit 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	public List<PlatformLimit> findAll(PlatformLimitCondition platformLimitCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	PlatformLimit findById(String id);
	
	/**
	 * 新增
	 * @param platformLimitCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	long insert(PlatformLimitCondition platformLimitCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	long update(PlatformLimitCondition platformLimitCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	long updateByCriteria(PlatformLimitCondition platformLimitCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	long updateStatus(String id,String status);	
	
}

