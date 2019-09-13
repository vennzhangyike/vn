/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.MerchantPayway;

@Generated("2016-10-21 10:21:59")
public interface MerchantPaywayDAO extends EntityDAO<MerchantPayway, String> {

	/**
	 * @Title: updateRateAmount
	 * @Description: 清算手续费更新
	 * @author: Ricky
	 * @param channelNo 渠道编号
	 * @param rateAmount 清算手续费
	 * @return
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	long updateRateAmount(String channelNo,String rateAmount);	
	
	List<Map<String, BigDecimal>> findMerchantPayWayByAgentNo(String agentNo,String payCode);
}
