/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.AgreementInfo;

@Generated("2016-12-13 09:57:47")
public interface AgreementInfoDAO extends EntityDAO<AgreementInfo, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);	
}
