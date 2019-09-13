/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.entity.MerchantCost;

@Generated("2016-11-10 13:59:57")
public interface MerchantCostDAO extends EntityDAO<MerchantCost, String> {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	long updateStatus(String id,String status);

	/** 金额统计 */
	List<MerchantCost> getAmtStatistics(MerchantCostCondition merchantCostCondition);	
}
