/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.AccountUserRalation;

@Generated("2016-09-21 16:21:30")
public interface AccountUserRalationDAO extends EntityDAO<AccountUserRalation, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);	
}
