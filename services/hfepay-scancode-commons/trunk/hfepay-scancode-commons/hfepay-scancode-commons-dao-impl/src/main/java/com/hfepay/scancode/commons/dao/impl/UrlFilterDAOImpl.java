package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.UrlFilterDAO;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.UrlFilter;

@org.springframework.stereotype.Repository

    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public class UrlFilterDAOImpl extends MybatisEntityDAO<Admin, String> implements UrlFilterDAO {

	@Override
	public List<UrlFilter> findRoles(String name) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("UrlFilter.findRoles", name);
	}

	@Override
	public List<UrlFilter> findAllUrl() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("UrlFilter.findAllUrl");
	}


}