/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.MerchantActivity;

@Generated("2017-02-14 10:45:02")
public interface MerchantActivityDAO extends EntityDAO<MerchantActivity, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);	
	
	public MerchantActivity findByMerchantNoAndStatus(String merchantNo,String status);
	
	public MerchantActivity findByActivityId(String activityId);
}
