/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.dao.UserMessageDAO;
import com.hfepay.scancode.commons.entity.UserMessage;

@Repository(value="userMessageDAO")
@Generated("2016-11-26 17:49:05")
public class UserMessageDAOImpl extends MybatisEntityDAO<UserMessage, String> implements UserMessageDAO {

	@Override
	public UserMessage findByMessageId(String messageId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("messageId", messageId);
		return this.getSqlSession().selectOne("findByMessageId",map);
	}
	
	@Override
	public UserMessage findByMessageIdAndUserNo(String messageId,String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("messageId", messageId);
		map.put("userName", userName);
		return this.getSqlSession().selectOne("findByMessageIdAndUserNo",map);
	}
	
	@Override
	public PagingResult<UserMessage> findByUserType(Criteria criteria) {
		
		return pagingQuery(sqlId("countByUserType"),sqlId("findByUserType"),criteria);
	}
}