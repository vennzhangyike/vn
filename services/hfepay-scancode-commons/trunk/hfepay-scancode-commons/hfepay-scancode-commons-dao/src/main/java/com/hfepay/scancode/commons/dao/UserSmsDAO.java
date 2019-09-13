/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.condition.UserSmsCondition;
import com.hfepay.scancode.commons.entity.UserSms;

@Generated("2016-11-28 17:28:09")
public interface UserSmsDAO extends EntityDAO<UserSms, String> {

	List<UserSms> getSmsCountDetail(UserSmsCondition userSmsCondition);
}
