/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.MerchantWallet;

@Generated("2016-10-18 18:02:16")
public interface MerchantWalletDAO extends EntityDAO<MerchantWallet, String> {
	/**
	 * 状态更新
	 */
	long updateStatus(String id,String status);

	/**
	 * 根据商户编号修改信息
	 * @Title: updateByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param merchantWallet
	 * @return
	 * @return: long
	 */
	long updateByMerchantNo(MerchantWallet merchantWallet);

	/**
	 * 用户余额修改
	 * @Title: updateBalanceByMerchantNo
	 * @Description: TODO
	 * @author: husain
	 * @param merchantWalletCondition
	 * @return
	 * @return: long
	 */
	long updateBalanceByMerchantNo(MerchantWallet merchantWalletCondition);	
}
