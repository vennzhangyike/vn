/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.Message;

@Generated("2016-11-26 17:48:59")
public interface MessageDAO extends EntityDAO<Message, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);	
	
	public List<Message> findAllByUserType(List<String> list);
	
	public List<Message> findAllByUserTypeAndUserNo(List<String> list,String userNo);
}
