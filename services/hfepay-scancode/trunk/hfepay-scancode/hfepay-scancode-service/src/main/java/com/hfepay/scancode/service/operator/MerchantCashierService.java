/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.entity.MerchantCashier;

public interface MerchantCashierService {
	
	/**
	 * 列表(分页)
	 * @param merchantCashierCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-08 09:37:19
	 */
	public PagingResult<MerchantCashier> findPagingResult(MerchantCashierCondition merchantCashierCondition);
	
	/**
	 * 列表
	 * @param merchantCashier 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-08 09:37:19
	 */
	public List<MerchantCashier> findAll(MerchantCashierCondition merchantCashierCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-08 09:37:19
	 */
	MerchantCashier findById(String id);
	
	/**
	 * 新增
	 * @param merchantCashierCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-08 09:37:19
	 */
	long insert(MerchantCashierCondition merchantCashierCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-08 09:37:19
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-08 09:37:19
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-08 09:37:19
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-08 09:37:19
	 */
	long update(MerchantCashierCondition merchantCashierCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-08 09:37:19
	 */
	long updateByCriteria(MerchantCashierCondition merchantCashierCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-08 09:37:19
	 */
	long updateStatus(String id,String status);
	

	/**
	 * 软删除收银员
	 * @Title: delCashier
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: long
	 */
	public long delCashierByCashierNo(String cashierNo,String opreator);

	/**
	 * @Title: createCashier
	 * @Description: 创建收银员
	 * @author: husain
	 * @param merchantNo
	 * @param openId
	 * @return
	 * @return: long
	 */
	public long createCashier(String merchantNo, String openId);

	/**
	 * 条件查询单个收银员
	 * @Title: findByCondition
	 * @Description: TODO
	 * @author: husain
	 * @param merchantCashierCondition
	 * @return
	 * @return: MerchantCashier
	 */
	public MerchantCashier findByCondition(MerchantCashierCondition merchantCashierCondition);
	
	/**
	 * 条件查询单个收银员根据openid
	 * @Title: findByCondition
	 * @Description: TODO
	 * @author: husain
	 * @param merchantCashierCondition
	 * @return
	 * @return: MerchantCashier
	 */
	public MerchantCashier findByOpenId(String openId);

	/**
	 * 创建收银员，录入收银员资料
	 * @Title: doSaveCashier
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: long
	 */
	public long doSaveCashier(MerchantCashierCondition condition);

	/**
	 * 绑定门店
	 * @Title: bindStore
	 * @Description: TODO
	 * @author: husain
	 * @param storeNo
	 * @param cashierNo
	 * @return
	 * @return: long
	 */
	public long bindStore(String storeNo, String cashierNo);

	/**
	 * 更新状态时，更新相对应的收款码
	 *  id
	 * @param status
	 * @param cashierNo
	 * @return
	 * @return: long
	 */
	public long updateStatus(String id,String status,String cashierNo);

	//自定义分页，针对h5
	public PagingResult<MerchantCashier> findPagingResultSelf(MerchantCashierCondition merchantCashierCondition);
	
	//刷新redis总收银员
	public void setMerchantCashierRedis() throws Exception;
}

