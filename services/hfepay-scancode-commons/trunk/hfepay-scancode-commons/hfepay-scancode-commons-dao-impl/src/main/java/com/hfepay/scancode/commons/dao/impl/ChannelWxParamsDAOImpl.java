/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.ChannelWxParamsDAO;
import com.hfepay.scancode.commons.entity.ChannelWxParams;

@Repository(value="channelWxParamsDAO")
@Generated("2016-11-07 14:47:12")
public class ChannelWxParamsDAOImpl extends MybatisEntityDAO<ChannelWxParams, String> implements ChannelWxParamsDAO {

	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-07 14:47:12
	 */
	@Override
	public long updateStatus(String id,String status){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return getSqlSession().update(sqlId("updateStatus"),map);
	}	
}