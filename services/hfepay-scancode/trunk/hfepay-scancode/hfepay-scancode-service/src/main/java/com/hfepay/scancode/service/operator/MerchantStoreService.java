/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.entity.MerchantStore;

public interface MerchantStoreService {
	
	/**
	 * 列表(分页)
	 * @param merchantStoreCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	public PagingResult<MerchantStore> findPagingResult(MerchantStoreCondition merchantStoreCondition);
	
	/**
	 * 列表
	 * @param merchantStore 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	public List<MerchantStore> findAll(MerchantStoreCondition merchantStoreCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	MerchantStore findById(String id);
	
	/**
	 * 新增
	 * @param merchantStoreCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	long insert(MerchantStoreCondition merchantStoreCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	long deleteByCriteria(MerchantStoreCondition merchantStoreCondition);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	long countByCriteria(MerchantStoreCondition merchantStoreCondition);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	long update(MerchantStoreCondition merchantStoreCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:58
	 */
	long updateByCriteria(MerchantStoreCondition merchantStoreCondition);

	/**
	 * 申请入驻第三部：门店信息
	 * @Title: applyStoreStep3
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: long
	 */
	public long applyStoreStep2(MerchantStoreCondition condition);

	/**
	 * 根据商户编号查找
	 * @Title: findByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param merchantNo
	 * @return
	 * @return: MerchantStore
	 */
	public MerchantStore findByMerchantNo(String merchantNo);

	/**
	 * 根据商户编号修改信息
	 * @Title: updateByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param store
	 * @return
	 * @return: long
	 */
	public long updateByMerchantNo(MerchantStoreCondition store);

	/**
	 * 查询所有的商户
	 * @Title: findAllMerchantStore
	 * @Description: TODO
	 * @author: husain
	 * @param merchantStoreCondition
	 * @return
	 * @return: List<MerchantStore>
	 */
	public List<MerchantStore> findAllMerchantStore(MerchantStoreCondition merchantStoreCondition);

	/**设置门店redis
	 * @throws Exception */
	void setStoreRedis() throws Exception;

	long updateByCriteria(MerchantStoreCondition merchantStoreCondition, Criteria buildCriteria);

	List<MerchantStore> findMerchantStore(String merchantNo);

	public MerchantStore findByCondition(MerchantStoreCondition storeCondition);
	
	
}

