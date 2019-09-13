/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.MerchantActivityDAO;
import com.hfepay.scancode.commons.entity.MerchantActivity;

@Repository(value="merchantActivityDAO")
@Generated("2017-02-14 10:45:02")
public class MerchantActivityDAOImpl extends MybatisEntityDAO<MerchantActivity, String> implements MerchantActivityDAO {
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
	public MerchantActivity findByMerchantNoAndStatus(String merchantNo,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		map.put("status", status);
		return getSqlSession().selectOne(sqlId("findByMerchantNoAndStatus"), map);
	}

	@Override
	public MerchantActivity findByActivityId(String activityId) {
		return getSqlSession().selectOne(sqlId("findByActivityId"),activityId);
	}
}