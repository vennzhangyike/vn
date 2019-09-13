package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.SysRoleResource;


@com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
public interface SysRoleResourceDAO extends EntityDAO<SysRoleResource, String> {

	long deleteSysRoleResourceByRoleId(String roleId);

	void insertBatch(List<SysRoleResource> list);

}