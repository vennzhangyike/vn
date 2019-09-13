/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.dao.impl;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.api.entity.ApiLog;
import com.hfepay.scancode.api.dao.ApiLogDAO;

import com.hfepay.commons.base.annotation.Generated;

import org.springframework.stereotype.Repository;

@Repository(value="apiLogDAO")
@Generated("2016-10-28 14:51:39")
public class ApiLogDAOImpl extends MybatisEntityDAO<ApiLog, String> implements ApiLogDAO {

	@Override
	public long updateByTradeNo(ApiLog apiLog) {
		return this.getSqlSession().insert("updateByTradeNo", apiLog);
	}
	
}