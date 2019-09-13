package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.AdminDAO;
import com.hfepay.scancode.commons.entity.Admin;

@org.springframework.stereotype.Repository

@com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public class AdminDAOImpl extends MybatisEntityDAO<Admin, String> implements AdminDAO {

	/**
	 * 状态更新
	 */
	@Override
	public long updateStatus(String id,Integer status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlSession().update(sqlId("updateStatus"),map);
	}
}