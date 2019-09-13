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
import com.hfepay.scancode.commons.dao.AgentPaywayBakDAO;
import com.hfepay.scancode.commons.entity.AgentPaywayBak;

@Repository(value="agentPaywayBakDAO")
@Generated("2016-10-18 15:18:22")
public class AgentPaywayBakDAOImpl extends MybatisEntityDAO<AgentPaywayBak, String> implements AgentPaywayBakDAO {
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
	public void doTruncateTable() {
		// TODO Auto-generated method stub
		this.getSqlSession().update(sqlId("doTruncateTable"));
	}

	@Override
	public void doBackUpTable() {
		// TODO Auto-generated method stub
		this.getSqlSession().insert(sqlId("doBackUpTable"));
	}	
}