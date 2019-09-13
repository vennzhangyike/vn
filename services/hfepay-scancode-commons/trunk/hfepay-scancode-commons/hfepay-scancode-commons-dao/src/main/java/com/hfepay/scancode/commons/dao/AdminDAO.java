package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.Admin;


@com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public interface AdminDAO extends EntityDAO<Admin, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,Integer status);
}