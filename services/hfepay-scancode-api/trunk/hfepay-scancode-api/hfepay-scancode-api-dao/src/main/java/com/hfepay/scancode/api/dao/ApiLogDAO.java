/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.api.entity.ApiLog;

@Generated("2016-10-28 14:51:39")
public interface ApiLogDAO extends EntityDAO<ApiLog, String> {
	
	/**
	 * 更新APIlog数据
	 * @param ApiLog
	 * @return long
	 */
	long updateByTradeNo(ApiLog apiLog);
}
