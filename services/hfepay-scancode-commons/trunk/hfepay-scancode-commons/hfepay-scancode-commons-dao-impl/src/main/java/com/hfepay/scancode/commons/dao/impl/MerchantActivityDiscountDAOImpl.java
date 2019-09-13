/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.MerchantActivityDiscountDAO;
import com.hfepay.scancode.commons.entity.MerchantActivityDiscount;

@Repository(value="merchantActivityDiscountDAO")
@Generated("2017-02-14 10:46:19")
public class MerchantActivityDiscountDAOImpl extends MybatisEntityDAO<MerchantActivityDiscount, String> implements MerchantActivityDiscountDAO {

	@Override
	public void batchInsert(List<MerchantActivityDiscount> insertList) {
		this.getSqlSession().insert(sqlId("batchInsert"), insertList);
	}

	@Override
	public void deleteByActivityId(String activityId) {
		this.getSqlSession().delete(sqlId("deleteByActivityId"), activityId);
	}
}