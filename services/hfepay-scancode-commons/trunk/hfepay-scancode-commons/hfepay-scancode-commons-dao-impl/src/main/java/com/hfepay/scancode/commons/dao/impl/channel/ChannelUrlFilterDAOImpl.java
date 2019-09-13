package com.hfepay.scancode.commons.dao.impl.channel;

import java.util.List;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.channel.ChannelUrlFilterDAO;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.UrlFilter;

@org.springframework.stereotype.Repository

    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public class ChannelUrlFilterDAOImpl extends MybatisEntityDAO<ChannelAdmin, String> implements ChannelUrlFilterDAO {

	@Override
	public List<UrlFilter> findRoles(String name) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("ChannelUrlFilter.findRoles", name);
	}

	@Override
	public List<UrlFilter> findAllUrl() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList("ChannelUrlFilter.findAllUrl");
	}


}