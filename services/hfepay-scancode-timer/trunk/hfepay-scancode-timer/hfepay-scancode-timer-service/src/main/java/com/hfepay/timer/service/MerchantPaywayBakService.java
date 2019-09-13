/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.timer.service;

import java.util.List;

import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantPaywayBakCondition;
import com.hfepay.scancode.commons.entity.MerchantPaywayBak;

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
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	long countByCriteria(MerchantPaywayBakCondition merchantPaywayBakCondition);
	
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

