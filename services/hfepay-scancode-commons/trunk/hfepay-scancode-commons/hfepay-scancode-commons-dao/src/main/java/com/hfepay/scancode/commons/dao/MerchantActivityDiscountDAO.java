/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.MerchantActivityDiscount;

@Generated("2017-02-14 10:46:19")
public interface MerchantActivityDiscountDAO extends EntityDAO<MerchantActivityDiscount, String> {
	
	/**
	 * @Title: batchInsert
	 * @Description: 级联关系批量插入
	 * @author: husain
	 * @param insertList
	 * @return: void
	 */
	void batchInsert(List<MerchantActivityDiscount> insertList);
	
	void deleteByActivityId(String activityId);
}
