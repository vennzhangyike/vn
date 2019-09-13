/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.base.lang.reflect.Reflector;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.commons.entity.RecordStatus;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.dao.MerchantCashierDAO;
import com.hfepay.scancode.commons.entity.MerchantCashier;

@Repository(value="merchantCashierDAO")
@Generated("2016-11-08 09:37:19")
public class MerchantCashierDAOImpl extends MybatisEntityDAO<MerchantCashier, String> implements MerchantCashierDAO {
	/**
	 * 状态更新
	 */
	@Override
	public long updateStatus(String id,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlSession().update(sqlId("updateStatus"),map);
	}

	@Override
	public long delCashierByCashierNo(String cashierNo, String opreator) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cashierNo", cashierNo);
		map.put("opreator", opreator);
		return getSqlSession().update(sqlId("delCashierByCashierNo"),map);
	}	
	
	@Override
	public long bindStore(String storeNo, String cashierNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cashierNo", cashierNo);
		map.put("storeNo", storeNo);
		return getSqlSession().update(sqlId("bindStore"),map);
	}
	
	@Override
	public PagingResult<MerchantCashier> findPagingResultSelf(Criteria criteria) {
		if(Reflector.isChildOf(RecordStatus.class, entityClass)){
			criteria.addParam("recordStatus", RecordStatus.ACTIVE);//假如实现了RecordStatus接口，则只查询活动的记录
		}
		return pagingQuery(sqlId("countPagingResultSelf"),sqlId("findPagingResultSelf"),criteria);
	}
	
}