/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.MerchantPaywayTmp;

@Generated("2016-11-15 17:17:04")
public interface MerchantPaywayTmpDAO extends EntityDAO<MerchantPaywayTmp, String> {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	long updateStatus(String id,String status);	
}
