/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.math.BigDecimal;
import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.MerchantBankcard;

@Generated("2016-10-17 13:50:11")
public interface MerchantBankcardDAO extends EntityDAO<MerchantBankcard, String> {
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
	long updateByMerchantNo(MerchantBankcard merchantBankcard);

	/**
	 * @Title: updateBalance
	 * @Description: 修改用户余额信息
	 * @author: husain
	 * @param merchantNo
	 * @param balance
	 * @return: long
	 */
	long updateBalance(String merchantNo, BigDecimal balance);

	/**
	 * 根据手机号码查询银行卡信息
	 * @Title: findByPhone
	 * @Description: TODO
	 * @author: husain
	 * @param mobile
	 * @return
	 * @return: MerchantBankcard
	 */
	List<MerchantBankcard> findByPhone(String mobile);	
}
