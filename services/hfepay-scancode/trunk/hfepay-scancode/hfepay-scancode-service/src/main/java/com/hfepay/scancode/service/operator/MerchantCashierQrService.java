/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;

public interface MerchantCashierQrService {
	
	/**
	 * 列表(分页)
	 * @param merchantCashierQrCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	public PagingResult<MerchantCashierQr> findPagingResult(MerchantCashierQrCondition merchantCashierQrCondition);
	
	/**
	 * 列表
	 * @param merchantCashierQr 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	public List<MerchantCashierQr> findAll(MerchantCashierQrCondition merchantCashierQrCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	MerchantCashierQr findById(String id);
	
	/**
	 * 新增
	 * @param merchantCashierQrCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	long insert(MerchantCashierQrCondition merchantCashierQrCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	long deleteByCriteria(MerchantCashierQrCondition merchantCashierQrCondition);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	long update(MerchantCashierQrCondition merchantCashierQrCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	long updateByCriteria(MerchantCashierQrCondition merchantCashierQrCondition);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	long updateStatus(String id,String status);

	/**
	 * h5绑定收营业员
	 * @Title: bindCasiher
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: long
	 */
	public long bindCasiher(MerchantCashierQrCondition condition);
	/**
	 * 自定义分页，专供h5二维码关联收款页
	 * @Title: findPagingResultSelf
	 * @Description: TODO
	 * @author: husain
	 * @param merchantCashierCondition
	 * @return
	 * @return: PagingResult<MerchantCashier>
	 */
	public PagingResult<MerchantCashierQr> findPagingResultSelf(MerchantCashierQrCondition merchantCashierQrCondition);

	/**
	 * @Title: bindSelectCode
	 * @Description: 选定二维码同时一个人保持只有一个二维码能收款
	 * @author: husain
	 * @param qrCondition
	 * @return
	 * @return: long
	 */
	public long bindSelectCode(MerchantCashierQrCondition qrCondition);

	long updateByCriteria(MerchantCashierQrCondition merchantCashierQrCondition, Criteria buildCriteria);

	public long updateByQrCode(MerchantCashierQrCondition merchantCashierQrCondition);
	
}

