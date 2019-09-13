/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.condition.UserSmsCondition;
import com.hfepay.scancode.commons.dao.UserSmsDAO;
import com.hfepay.scancode.commons.entity.UserSms;

@Repository(value="userSmsDAO")
@Generated("2016-11-28 17:28:09")
public class UserSmsDAOImpl extends MybatisEntityDAO<UserSms, String> implements UserSmsDAO {

	@Override
	public List<UserSms> getSmsCountDetail(UserSmsCondition userSmsCondition) {
		return this.getSqlSession().selectList(sqlId("getSmsCountDetail"),userSmsCondition);
	}
}