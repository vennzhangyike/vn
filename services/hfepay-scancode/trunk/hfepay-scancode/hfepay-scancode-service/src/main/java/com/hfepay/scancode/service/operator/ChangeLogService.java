/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.entity.ChangeLog;

public interface ChangeLogService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param changeLogCondition
	 * @return: PagingResult<ChangeLog>
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	public PagingResult<ChangeLog> findPagingResult(ChangeLogCondition changeLogCondition);
	/**
	 * @Title: findAuditMerchantBankcard
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantBankcardCondition
	 * @return: PagingResult<ChangeLog>
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	public PagingResult<ChangeLog> findAuditMerchantBankcard(MerchantBankcardCondition merchantBankcardCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param changeLogCondition
	 * @return: List<ChangeLog>
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	public List<ChangeLog> findAll(ChangeLogCondition changeLogCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChangeLog
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	ChangeLog findById(String id);
	/**
	 * @Title: findByTradeNo
	 * @Description: 商户银行账户主键查找(专用)
	 * @author: Ricky
	 * @param id
	 * @return: ChangeLog
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	ChangeLog findByTradeNo(String id);
	
	
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param changeLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	long insert(ChangeLogCondition changeLogCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param changeLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	long update(ChangeLogCondition changeLogCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param changeLogCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	long updateByCriteria(ChangeLogCondition changeLogCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	long updateStatus(String id,String status);	
	
}

