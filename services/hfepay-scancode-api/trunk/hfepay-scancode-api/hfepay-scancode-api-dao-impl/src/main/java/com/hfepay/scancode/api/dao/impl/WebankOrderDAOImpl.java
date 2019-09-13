/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.api.dao.impl;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.api.dao.WebankOrderDAO;
import com.hfepay.scancode.api.entity.WebankOrder;
import com.hfepay.commons.base.annotation.Generated;

import org.springframework.stereotype.Repository;

@Repository(value="webankOrderDAO")
@Generated("2017-03-13 17:55:20")
public class WebankOrderDAOImpl extends MybatisEntityDAO<WebankOrder, String> implements WebankOrderDAO {

	@Override
	public WebankOrder findByTradeNo(String tradeNo) {
		return getSqlSession().selectOne(sqlId("findByTradeNo"), tradeNo);
	}
}