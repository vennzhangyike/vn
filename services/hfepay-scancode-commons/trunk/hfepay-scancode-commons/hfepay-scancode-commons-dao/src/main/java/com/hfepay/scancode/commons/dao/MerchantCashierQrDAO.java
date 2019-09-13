/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;

@Generated("2016-11-10 16:13:31")
public interface MerchantCashierQrDAO extends EntityDAO<MerchantCashierQr, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);

	void bindCasiher(List<MerchantCashierQr> list);

	void deleteByCashier(String cashierNo);	
	
	public PagingResult<MerchantCashierQr> findPagingResultSelf(Criteria buildCriteria);

	long unbind(String cashierNo);
}
