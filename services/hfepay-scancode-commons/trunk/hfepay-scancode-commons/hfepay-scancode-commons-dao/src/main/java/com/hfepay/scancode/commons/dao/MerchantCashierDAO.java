/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.entity.MerchantCashier;

@Generated("2016-11-08 09:37:19")
public interface MerchantCashierDAO extends EntityDAO<MerchantCashier, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);

	long delCashierByCashierNo(String cashierNo, String opreator);

	long bindStore(String storeNo, String cashierNo);

	//自定义分页
	PagingResult<MerchantCashier> findPagingResultSelf(Criteria buildCriteria);
}
