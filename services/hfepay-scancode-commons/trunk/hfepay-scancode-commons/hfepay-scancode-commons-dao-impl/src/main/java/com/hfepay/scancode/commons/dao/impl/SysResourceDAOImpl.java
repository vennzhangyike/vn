package com.hfepay.scancode.commons.dao.impl;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.SysResourceDAO;
import com.hfepay.scancode.commons.entity.SysResource;

@org.springframework.stereotype.Repository

    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public class SysResourceDAOImpl extends MybatisEntityDAO<SysResource, String> implements SysResourceDAO {

	@Override
	public List<SysResource> selectActiveMenu(String userId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("SysResource.selectActiveMenu",userId);
	}

	@Override
	public List<SysResource> selectActiveResource(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("SysResource.selectActiveResource",params);
	}
}