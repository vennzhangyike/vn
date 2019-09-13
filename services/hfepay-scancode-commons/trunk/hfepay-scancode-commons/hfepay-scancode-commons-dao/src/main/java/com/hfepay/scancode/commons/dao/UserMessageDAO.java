/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.entity.UserMessage;

@Generated("2016-11-26 17:49:05")
public interface UserMessageDAO extends EntityDAO<UserMessage, String> {
	
	UserMessage findByMessageId(String messageId);
	
	UserMessage findByMessageIdAndUserNo(String messageId,String userName);
	
	public PagingResult<UserMessage> findByUserType(Criteria criteria);
}
