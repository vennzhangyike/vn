/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.math.BigDecimal;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.MerchantAccounts;

@Generated("2016-10-22 15:09:24")
public interface MerchantAccountsDAO extends EntityDAO<MerchantAccounts, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);

	/**
	 * 根据商户编号修改信息
	 * @Title: updateByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param merchantAccounts
	 * @return
	 * @return: long
	 */
	long updateByMerchantNo(MerchantAccounts merchantAccounts);

	/**
	 * @Title: updateBalance
	 * @Description: 修改用户余额信息
	 * @author: husain
	 * @param merchantNo
	 * @param balance
	 * @return: long
	 */
	long updateBalance(String merchantNo, BigDecimal balance);	
}
