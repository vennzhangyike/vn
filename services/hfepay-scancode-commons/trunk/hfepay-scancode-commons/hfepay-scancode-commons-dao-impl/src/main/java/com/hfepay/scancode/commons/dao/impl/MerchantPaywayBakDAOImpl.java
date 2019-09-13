/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.MerchantPaywayBakDAO;
import com.hfepay.scancode.commons.entity.MerchantPaywayBak;

@Repository(value="merchantPaywayBakDAO")
@Generated("2016-10-21 10:21:59")
public class MerchantPaywayBakDAOImpl extends MybatisEntityDAO<MerchantPaywayBak, String> implements MerchantPaywayBakDAO {
	
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
	@Override
	public long updateRateAmount(String channelNo,String rateAmount,String payCode){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelNo", channelNo);
		map.put("rateAmount", rateAmount);
		map.put("payCode", payCode);
		return getSqlSession().update(sqlId("updateRateAmount"),map);
	}

	@Override
	public List<Map<String, BigDecimal>> findMerchantPayWayByAgentNo(String agentNo) {
		
		return getSqlSession().selectList(sqlId("findMerchantPayWayByAgentNo"), agentNo);
	}
	
	@Override
	public void doTruncateTable() {
		// TODO Auto-generated method stub
		this.getSqlSession().update(sqlId("doTruncateTable"));
	}

	@Override
	public void doBackUpTable() {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(sqlId("doBackUpTable"));
	}	
}