/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.condition.OrganWalletCondition;
import com.hfepay.scancode.commons.dao.OrganWalletDAO;
import com.hfepay.scancode.commons.entity.OrganWallet;

@Repository(value="organWalletDAO")
@Generated("2016-12-20 11:30:02")
public class OrganWalletDAOImpl extends MybatisEntityDAO<OrganWallet, String> implements OrganWalletDAO {

	@Override
	public long updateBalance(OrganWalletCondition organWalletCondition) {
		return this.getSqlSession().update(sqlId("updateBalance"),organWalletCondition);
	}

	@Override
	public long updateBalanceToWallet(String date) {
		return this.getSqlSession().update(sqlId("updateBalanceToWallet"),date);
	}

	@Override
	public long updateBalanceToChannel(String date) {
		return this.getSqlSession().update(sqlId("updateBalanceToChannel"),date);
	}
}