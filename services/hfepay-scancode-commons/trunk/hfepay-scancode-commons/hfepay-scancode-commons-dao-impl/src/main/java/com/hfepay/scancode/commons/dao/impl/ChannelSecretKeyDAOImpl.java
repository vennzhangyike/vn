package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.ChannelSecretKeyDAO;
import com.hfepay.scancode.commons.entity.ChannelSecretKey;

@org.springframework.stereotype.Repository

    @com.hfepay.commons.base.annotation.Generated("2016-09-18 19:24")
public class ChannelSecretKeyDAOImpl extends MybatisEntityDAO<ChannelSecretKey, String> implements ChannelSecretKeyDAO {

	/**
	 * 状态更新
	 */
	@Override
	public long updateStatus(String id,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlSession().update(sqlId("updateStatus"),map);
	}	
}