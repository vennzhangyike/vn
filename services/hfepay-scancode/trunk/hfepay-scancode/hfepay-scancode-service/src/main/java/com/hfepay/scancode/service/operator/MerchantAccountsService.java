/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.math.BigDecimal;
import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantAccountsCondition;
import com.hfepay.scancode.commons.entity.MerchantAccounts;

public interface MerchantAccountsService {
	
	/**
	 * 列表(分页)
	 * @param merchantAccountsCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	public PagingResult<MerchantAccounts> findPagingResult(MerchantAccountsCondition merchantAccountsCondition);
	
	/**
	 * 列表
	 * @param merchantAccounts 
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	public List<MerchantAccounts> findAll(MerchantAccountsCondition merchantAccountsCondition);
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	MerchantAccounts findById(String id);
	
	/**
	 * 新增
	 * @param merchantAccountsCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	long insert(MerchantAccountsCondition merchantAccountsCondition);

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	long update(MerchantAccountsCondition merchantAccountsCondition);
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	long updateByCriteria(MerchantAccountsCondition merchantAccountsCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-22 15:09:24
	 */
	long updateStatus(String id,String status);

	/**
	 * 根据商户号查询
	 * @Title: findByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param merchantNo
	 * @return
	 * @return: MerchantAccounts
	 */
	public MerchantAccounts findByMerchantNo(String merchantNo);

	/**
	 * 根据商户编号变更信息
	 * @Title: updateByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: long
	 */
	public long updateByMerchantNo(MerchantAccountsCondition condition);

	/**
	 * 用户余额修改
	 * @Title: updateBalance
	 * @Description: TODO
	 * @author: husain
	 * @param merchantNo
	 * @param balance
	 * @return
	 * @return: long
	 */
	public long updateBalance(String merchantNo, BigDecimal balance);

	/**
	 * 商户入驻第三部，
	 * @Title: applyStoreStep3
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: long
	 */
	public long applyStoreStep3(MerchantAccountsCondition condition);	
	
}

