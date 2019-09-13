/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.api.dao;

import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.api.entity.WebankOrder;
import com.hfepay.commons.base.annotation.Generated;

@Generated("2017-03-13 17:55:20")
public interface WebankOrderDAO extends EntityDAO<WebankOrder, String> {
	
	/**
	 * 查询订单信息
	 */
	WebankOrder findByTradeNo(String tradeNo);
}
