package com.hfepay.scancode.commons.dao.impl.channel;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.channel.ChannelResourceDAO;
import com.hfepay.scancode.commons.entity.ChannelResource;

@org.springframework.stereotype.Repository

    @com.hfepay.commons.base.annotation.Generated("2016-04-14 09:42")
public class ChannelResourceDAOImpl extends MybatisEntityDAO<ChannelResource, String> implements ChannelResourceDAO {

	@Override
	public List<ChannelResource> selectActiveMenu(String userId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(sqlId("selectActiveMenu"),userId);
	}

	@Override
	public List<ChannelResource> selectActiveResource(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(sqlId("selectActiveResource"),params);
	}


}