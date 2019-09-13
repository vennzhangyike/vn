/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.condition.OrganWalletCondition;
import com.hfepay.scancode.commons.entity.OrganWallet;

@Generated("2016-12-20 11:30:02")
public interface OrganWalletDAO extends EntityDAO<OrganWallet, String> {

	long updateBalance(OrganWalletCondition organWalletCondition);

	long updateBalanceToWallet(String date);

	long updateBalanceToChannel(String date);
}
