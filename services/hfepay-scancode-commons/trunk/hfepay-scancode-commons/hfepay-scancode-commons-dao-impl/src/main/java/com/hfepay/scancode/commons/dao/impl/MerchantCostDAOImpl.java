/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.dao.MerchantCostDAO;
import com.hfepay.scancode.commons.entity.MerchantCost;

@Repository(value="merchantCostDAO")
@Generated("2016-11-10 13:59:57")
public class MerchantCostDAOImpl extends MybatisEntityDAO<MerchantCost, String> implements MerchantCostDAO {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public long updateStatus(String id,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlSession().update(sqlId("updateStatus"),map);
	}

	/** 金额统计 */
	@Override
	public List<MerchantCost> getAmtStatistics(MerchantCostCondition merchantCostCondition) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(sqlId("getAmtStatistics"), merchantCostCondition);
	}	
}