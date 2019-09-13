/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.timer.service;

import java.text.ParseException;
import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantPaywayTmpCondition;
import com.hfepay.scancode.commons.entity.MerchantPaywayTmp;

public interface MerchantPaywayTmpService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: PagingResult<MerchantPaywayTmp>
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	public PagingResult<MerchantPaywayTmp> findPagingResult(MerchantPaywayTmpCondition merchantPaywayTmpCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: List<MerchantPaywayTmp>
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	public List<MerchantPaywayTmp> findAll(MerchantPaywayTmpCondition merchantPaywayTmpCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MerchantPaywayTmp
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	MerchantPaywayTmp findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	long insert(MerchantPaywayTmpCondition merchantPaywayTmpCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	long update(MerchantPaywayTmpCondition merchantPaywayTmpCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	long updateByCriteria(MerchantPaywayTmpCondition merchantPaywayTmpCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	long updateStatus(String id,String status);

	/**
	 * @Title: save
	 * @Description: 新增、更新
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	void save(MerchantPaywayTmpCondition merchantPaywayTmpCondition);	
	
	/**
	 * 根据支付通道查询
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	MerchantPaywayTmp findByPayCode(String payCode,String merchantNo);

	/** 更新商户费率JOB
	 * @throws ParseException */
	void updatePaywayJob() throws ParseException;
	
}

