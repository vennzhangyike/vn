/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantActivityCondition;
import com.hfepay.scancode.commons.entity.MerchantActivity;

public interface MerchantActivityService {
	
	/**
	 * 列表(分页)
	 * @param merchantActivityCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	public PagingResult<MerchantActivity> findPagingResult(MerchantActivityCondition merchantActivityCondition);
	
	/**
	 * 列表
	 * @param merchantActivity 
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	public List<MerchantActivity> findAll(MerchantActivityCondition merchantActivityCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	MerchantActivity findById(String id);
	
	/**
	 * 新增
	 * @param merchantActivityCondition
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	long insert(MerchantActivityCondition merchantActivityCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	long update(MerchantActivityCondition merchantActivityCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	long updateByCriteria(MerchantActivityCondition merchantActivityCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	long updateStatus(String id,String status);	
	
	public void insertMerchantActivity(MerchantActivityCondition merchantActivityCondition,String [] array);
	
	public void deleteMerchantActivityById(String id,String activityId);
	
	/**
	 * 计算优惠的价格
	 */
	public Map<String,BigDecimal> calculateCheapCash(String merchantNo,BigDecimal payCash,BigDecimal minLimit);
	
	/**
	 * 根据商户号和状态查询活动
	 * @param merchantNo
	 * @param status
	 * @return
	 */
	public MerchantActivity findByMerchantNoAndStatus(String merchantNo,String status);
	
	/**
	 * 根据活动ID查询活动
	 * @param activityId
	 * @return
	 */
	public MerchantActivity findByActivityId(String activityId);
}

