/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.dao.MessageDAO;
import com.hfepay.scancode.commons.entity.Message;

@Repository(value="messageDAO")
@Generated("2016-11-26 17:48:59")
public class MessageDAOImpl extends MybatisEntityDAO<Message, String> implements MessageDAO {
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
	public List<Message> findAllByUserType(List<String> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return getSqlSession().selectList(sqlId("findAllByUserType"),map);
	}
	@Override
	public List<Message> findAllByUserTypeAndUserNo(List<String> list,String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("userNo", userNo);
		return getSqlSession().selectList(sqlId("findAllByUserTypeAndUserNo"),map);
	}	
}