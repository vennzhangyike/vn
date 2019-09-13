/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.AgreementInfoCondition;
import com.hfepay.scancode.commons.entity.AgreementInfo;

public interface AgreementInfoService {
	
	/**
	 * 列表(分页)
	 * @param agreementInfoCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	public PagingResult<AgreementInfo> findPagingResult(AgreementInfoCondition agreementInfoCondition);
	
	/**
	 * 列表
	 * @param agreementInfo 
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	public List<AgreementInfo> findAll(AgreementInfoCondition agreementInfoCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	AgreementInfo findById(String id);
	
	/**
	 * 新增
	 * @param agreementInfoCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	long insert(AgreementInfoCondition agreementInfoCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	long update(AgreementInfoCondition agreementInfoCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	long updateByCriteria(AgreementInfoCondition agreementInfoCondition);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-12-13 09:57:47
	 */
	long updateStatus(String id,String status);	
	
}

