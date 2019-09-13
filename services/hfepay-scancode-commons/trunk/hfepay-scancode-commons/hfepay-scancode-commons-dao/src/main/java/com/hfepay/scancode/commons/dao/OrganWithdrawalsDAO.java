/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.condition.OrganWithdrawalsCondition;
import com.hfepay.scancode.commons.entity.OrganWithdrawals;

@Generated("2016-12-20 11:30:02")
public interface OrganWithdrawalsDAO extends EntityDAO<OrganWithdrawals, String> {

	/** 金额统计 */
	List<OrganWithdrawals> getAmtStatistics(OrganWithdrawalsCondition organWithdrawalsCondition);
}
