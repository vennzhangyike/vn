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
import com.hfepay.scancode.commons.entity.MerchantPaywayBak;

@Generated("2016-10-21 10:21:59")
public interface MerchantPaywayBakDAO extends EntityDAO<MerchantPaywayBak, String> {

	/**
	 * @Title: updateRateAmount
	 * @Description: 清算手续费更新
	 * @author: Ricky
	 * @param channelNo 渠道编号
	 * @param rateAmount 清算手续费
	 * @param payCode 支付方式
	 * @return
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	long updateRateAmount(String channelNo,String rateAmount,String payCode);	
	
	List<Map<String, BigDecimal>> findMerchantPayWayByAgentNo(String agentNo);

	/**
	 * 删除数据
	 * @Title: doTruncateTable
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	void doTruncateTable();

	/**
	 * 插入当天最新数据
	 * @Title: doBackUpTable
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	void doBackUpTable();	
}
