/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.OrganWalletCondition;
import com.hfepay.scancode.commons.entity.OrganWallet;

public interface OrganWalletService {
	
	/**
	 * 列表(分页)
	 * @param organWalletCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	public PagingResult<OrganWallet> findPagingResult(OrganWalletCondition organWalletCondition);
	
	/**
	 * 列表
	 * @param organWallet 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	public List<OrganWallet> findAll(OrganWalletCondition organWalletCondition);
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	OrganWallet findById(String id);
	
	/**
	 * 新增
	 * @param organWalletCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long insert(OrganWalletCondition organWalletCondition);

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long update(OrganWalletCondition organWalletCondition);
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long updateByCriteria(OrganWalletCondition organWalletCondition,Criteria criteria);
	
	/**
	 * 单个实体对象
	 * @param organWalletCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	public OrganWallet findByCondition(OrganWalletCondition organWalletCondition);

	/**
	 * 
	 * @Title: updateBalance
	 * @Description: TODO
	 * @author: husain
	 * @param organWalletCondition
	 * @return
	 * @return: long
	 */
	public long updateBalance(OrganWalletCondition organWalletCondition);

	/**
	 * 结算之后修改钱包余额
	 * @Title: updateBalanceToWallet
	 * @Description: TODO
	 * @author: husain
	 * @param date
	 * @return: void
	 */
	public long updateBalanceToWallet(String date);

	/**
	 * 结算之后修改钱包余额:渠道月等于相应的代理商利润和+自己的利润
	 * @Title: updateBalanceToWallet
	 * @Description: TODO
	 * @author: husain
	 * @param date
	 * @return: void
	 */
	public long updateBalanceToChannel(String date);
	
}

