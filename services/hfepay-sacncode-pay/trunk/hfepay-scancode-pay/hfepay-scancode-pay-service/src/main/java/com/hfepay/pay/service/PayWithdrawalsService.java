/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.pay.service;

import java.util.List;

import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.entity.Withdrawals;

public interface PayWithdrawalsService {
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: List<Withdrawals>
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	public List<Withdrawals> findAll(WithdrawalsCondition withdrawalsCondition);
	
	
	/**
	 * @Title: createWithdraws
	 * @Description: 新增
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	public Withdrawals createWithdraws(WithdrawalsCondition withdrawalsCondition);

	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	long update(WithdrawalsCondition withdrawalsCondition);
	
	/**
	 * @Title: findByTradeNo
	 * @Description: 列表
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: List<Withdrawals>
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	public Withdrawals findByTradeNo(String tradeNo);
	
}

