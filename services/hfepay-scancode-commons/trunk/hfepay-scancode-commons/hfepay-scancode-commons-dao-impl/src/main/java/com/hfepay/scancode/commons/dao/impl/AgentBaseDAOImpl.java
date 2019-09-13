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
import com.hfepay.scancode.commons.dao.AgentBaseDAO;
import com.hfepay.scancode.commons.entity.AgentBase;

@Repository(value="agentBaseDAO")
@Generated("2016-10-19 16:04:33")
public class AgentBaseDAOImpl extends MybatisEntityDAO<AgentBase, String> implements AgentBaseDAO {
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

	@Override
	public long updateUsedTimes(String agentNo) {
		// TODO Auto-generated method stub
		return getSqlSession().update(sqlId("updateUsedTimes"),agentNo);
	}	
}