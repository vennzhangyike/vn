/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantLimitCondition;
import com.hfepay.scancode.commons.entity.MerchantLimit;

public interface MerchantLimitService {
	
	/**
	 * 列表(分页)
	 * @param merchantLimitCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	public PagingResult<MerchantLimit> findPagingResult(MerchantLimitCondition merchantLimitCondition);
	
	/**
	 * 列表
	 * @param merchantLimit 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	public List<MerchantLimit> findAll(MerchantLimitCondition merchantLimitCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	MerchantLimit findById(String id);
	
	/**
	 * 新增
	 * @param merchantLimitCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	long insert(MerchantLimitCondition merchantLimitCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	long update(MerchantLimitCondition merchantLimitCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	long updateByCriteria(MerchantLimitCondition merchantLimitCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	long updateStatus(String id,String status);	
	
}

