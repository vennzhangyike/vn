/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.condition.OrganWithdrawalsCondition;
import com.hfepay.scancode.commons.dao.OrganWithdrawalsDAO;
import com.hfepay.scancode.commons.entity.OrganWithdrawals;

@Repository(value="organWithdrawalsDAO")
@Generated("2016-12-20 11:30:02")
public class OrganWithdrawalsDAOImpl extends MybatisEntityDAO<OrganWithdrawals, String> implements OrganWithdrawalsDAO {

	/** 金额统计 */
	@Override
	public List<OrganWithdrawals> getAmtStatistics(OrganWithdrawalsCondition organWithdrawalsCondition) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(sqlId("getAmtStatistics"), organWithdrawalsCondition);
	}
}