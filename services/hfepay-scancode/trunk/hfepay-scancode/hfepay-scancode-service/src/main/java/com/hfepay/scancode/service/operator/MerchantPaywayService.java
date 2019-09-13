/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.entity.MerchantPayway;

import net.sf.json.JSON;

public interface MerchantPaywayService {
	
	/**
	 * 列表(分页)
	 * @param merchantPaywayCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	public PagingResult<MerchantPayway> findPagingResult(MerchantPaywayCondition merchantPaywayCondition);
	
	/**
	 * 列表
	 * @param merchantPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	public List<MerchantPayway> findAll(MerchantPaywayCondition merchantPaywayCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	MerchantPayway findById(String id);
	
	/**
	 * 根据支付通道查询
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	MerchantPayway findByPayCode(String payCode,String merchantNo);
	
	/**
	 * 新增
	 * @param merchantPaywayCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long insert(MerchantPaywayCondition merchantPaywayCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long deleteByCriteria(MerchantPaywayCondition merchantPaywayCondition);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long countByCriteria(MerchantPaywayCondition merchantPaywayCondition);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long update(MerchantPaywayCondition merchantPaywayCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long updateByCriteria(MerchantPaywayCondition merchantPaywayCondition);
		
	/**
	 * 
	 *添加清算手续费
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	public void addLiquidationFee(String channelNo ,String liquidationFee) throws Exception;
	
	/**
	 * 设置清算手续费
	 * @param channelNo
	 * @param liquidationFee
	 * @throws Exception
	 */
	void setLiquidationFee(String channelNo, String liquidationFee) throws Exception;
	
	/** redis中获取清算手续费*/
	public Map<String, String> getLiquidationFee(String channelNo)  throws Exception;
	
	public List<Map<String, BigDecimal>> findMerchantPayWayByAgentNo(String agentNo,String payCode);

	/** 保存费率 
	 * @throws Exception */
	Map<String, String> save(MerchantPaywayCondition merchantPaywayCondition) throws Exception;

	/**
	 * 新费率入网
	 * @param merchantPaywayCondition
	 * @param map
	 * @return
	 * @throws Exception
	 */
	JSON paywayJoin(MerchantPaywayCondition merchantPaywayCondition, Map<Object, Object> map) throws Exception;

	long updateByCriteria(MerchantPaywayCondition merchantPaywayCondition, Criteria buildCriteria);

}

