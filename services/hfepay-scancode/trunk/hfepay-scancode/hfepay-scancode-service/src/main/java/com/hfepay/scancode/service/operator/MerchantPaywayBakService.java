/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantPaywayBakCondition;
import com.hfepay.scancode.commons.entity.MerchantPaywayBak;

import net.sf.json.JSON;

public interface MerchantPaywayBakService {
	
	/**
	 * 列表(分页)
	 * @param merchantPaywayBakCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	public PagingResult<MerchantPaywayBak> findPagingResult(MerchantPaywayBakCondition merchantPaywayBakCondition);
	
	/**
	 * 列表
	 * @param merchantPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	public List<MerchantPaywayBak> findAll(MerchantPaywayBakCondition merchantPaywayBakCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	MerchantPaywayBak findById(String id);
	
	/**
	 * 根据支付通道查询
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	MerchantPaywayBak findByPayCode(String payCode,String merchantNo);
	
	/**
	 * 新增
	 * @param merchantPaywayBakCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long insert(MerchantPaywayBakCondition merchantPaywayBakCondition);

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
	long deleteByCriteria(MerchantPaywayBakCondition merchantPaywayBakCondition);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long countByCriteria(MerchantPaywayBakCondition merchantPaywayBakCondition);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long update(MerchantPaywayBakCondition merchantPaywayBakCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long updateByCriteria(MerchantPaywayBakCondition merchantPaywayBakCondition);
		
	/**
	 * 设置清算手续费
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	public void addLiquidationFee(String channelNo ,String zfbFee,String wxsmFee,String wxgzhFee) throws Exception;
	
	/** redis中获取清算手续费*/
	public Map<String, String> getLiquidationFee(String channelNo)  throws Exception;
	
	public List<Map<String, BigDecimal>> findMerchantPayWayByAgentNo(String agentNo);

	/** 保存费率 
	 * @throws Exception */
	Map<String, String> save(MerchantPaywayBakCondition merchantPaywayBakCondition) throws Exception;

	/**
	 * 新费率入网
	 * @param merchantPaywayBakCondition
	 * @param map
	 * @return
	 * @throws Exception
	 */
	JSON paywayJoin(MerchantPaywayBakCondition merchantPaywayBakCondition, Map<Object, Object> map) throws Exception;

	/**
	 * 删除数据
	 * @Title: doTruncateTable
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	public void doTruncateTable();

	/**
	 * 备份数据
	 * @Title: doBackUpTable
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	public void doBackUpTable();
	
}

