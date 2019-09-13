/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantBankcardChangeCondition;
import com.hfepay.scancode.commons.entity.MerchantBankcardChange;

public interface MerchantBankcardChangeService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantBankcardChangeCondition
	 * @return: PagingResult<MerchantBankcardChange>
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	public PagingResult<MerchantBankcardChange> findPagingResult(MerchantBankcardChangeCondition merchantBankcardChangeCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param merchantBankcardChangeCondition
	 * @return: List<MerchantBankcardChange>
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	public List<MerchantBankcardChange> findAll(MerchantBankcardChangeCondition merchantBankcardChangeCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MerchantBankcardChange
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	MerchantBankcardChange findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantBankcardChangeCondition
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	long insert(MerchantBankcardChangeCondition merchantBankcardChangeCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param merchantBankcardChangeCondition
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	long update(MerchantBankcardChangeCondition merchantBankcardChangeCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param merchantBankcardChangeCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	long updateByCriteria(MerchantBankcardChangeCondition merchantBankcardChangeCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	long updateStatus(String id,String status);

	/** 商户编号查找 */
	MerchantBankcardChange findByMerchantNo(String merchantNo);	
	
	/**
	 * @Title: findAuditMerchantBankcard
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param MerchantBankcardChangeCondition
	 * @return: PagingResult<MerchantBankcardChange>
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	public PagingResult<MerchantBankcardChange> findAuditMerchantBankcard(MerchantBankcardChangeCondition merchantBankcardChangeCondition);
	
}

