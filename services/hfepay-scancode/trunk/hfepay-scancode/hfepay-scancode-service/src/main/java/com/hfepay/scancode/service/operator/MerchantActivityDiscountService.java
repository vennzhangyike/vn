/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantActivityDiscountCondition;
import com.hfepay.scancode.commons.entity.MerchantActivityDiscount;

public interface MerchantActivityDiscountService {
	
	/**
	 * 列表(分页)
	 * @param merchantActivityDiscountCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	public PagingResult<MerchantActivityDiscount> findPagingResult(MerchantActivityDiscountCondition merchantActivityDiscountCondition);
	
	/**
	 * 列表
	 * @param merchantActivityDiscount 
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	public List<MerchantActivityDiscount> findAll(MerchantActivityDiscountCondition merchantActivityDiscountCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	MerchantActivityDiscount findById(String id);
	
	/**
	 * 新增
	 * @param merchantActivityDiscountCondition
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	long insert(MerchantActivityDiscountCondition merchantActivityDiscountCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	long update(MerchantActivityDiscountCondition merchantActivityDiscountCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	long updateByCriteria(MerchantActivityDiscountCondition merchantActivityDiscountCondition,Criteria criteria);
	
	public void batchInsert(List<MerchantActivityDiscount> insertList);
}

