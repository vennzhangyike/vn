/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.MerchantInfo;

@Generated("2016-10-21 14:04:47")
public interface MerchantInfoDAO extends EntityDAO<MerchantInfo, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);

	/**
	 * 商户信息录入
	 * @Title: updateByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param condition
	 * @return
	 * @return: long
	 */
	long updateByMerchantNo(MerchantInfo condition);	

}
