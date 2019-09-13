package com.hfepay.scancode.commons.dao.impl.channel;

import java.util.List;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.channel.ChannelRoleResourceDAO;
import com.hfepay.scancode.commons.entity.ChannelRoleResource;

@org.springframework.stereotype.Repository

    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:43")
public class ChannelRoleResourceDAOImpl extends MybatisEntityDAO<ChannelRoleResource, String> implements ChannelRoleResourceDAO {

	@Override
	public long deleteChannelRoleResourceByRoleId(String roleId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete(sqlId("deleteSysRoleResourceByRoleId"), roleId);
	}

	@Override
	public void insertBatch(List<ChannelRoleResource> list) {
		// TODO Auto-generated method stub
		this.getSqlSession().delete(sqlId("insertBatch"), list);
	}


}