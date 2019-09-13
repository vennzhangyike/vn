package com.hfepay.scancode.service.operator;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantWalletCondition;
import com.hfepay.scancode.commons.entity.MerchantWallet;

public interface MerchantWalletService {
	
	/**
	 * 列表(分页)
	 * @param merchantWalletCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	public PagingResult<MerchantWallet> findPagingResult(MerchantWalletCondition merchantWalletCondition);
	
	/**
	 * 列表
	 * @param merchantWallet 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	public List<MerchantWallet> findAll(MerchantWalletCondition merchantWalletCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	MerchantWallet findById(String id);
	
	/**
	 * 新增
	 * @param merchantWalletCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	long insert(MerchantWalletCondition merchantWalletCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	long update(MerchantWalletCondition merchantWalletCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	long updateByCriteria(MerchantWalletCondition merchantWalletCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 18:02:16
	 */
	long updateStatus(String id,String status);

	/**
	 * 根据商户编号查询余额
	 * @Title: findByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param merchantNo
	 * @return
	 * @return: MerchantWallet
	 */
	public MerchantWallet findByMerchantNo(String merchantNo);

	/**
	 * 根据商户号修改用户信息
	 * @Title: updateByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param merchantWalletCondition
	 * @return
	 * @return: long
	 */
	public long updateByMerchantNo(MerchantWalletCondition merchantWalletCondition);

	/**
	 * 修改用户余额
	 * @Title: updateBalanceByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param merchantWalletCondition
	 * @return
	 * @return: long
	 */
	public long updateBalanceByMerchantNo(MerchantWalletCondition merchantWalletCondition);	
	
}

