/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.math.BigDecimal;
import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.dto.WithDrawsTotalDTO;
import com.hfepay.scancode.commons.entity.Withdrawals;

@Generated("2016-11-30 09:32:37")
public interface WithdrawalsDAO extends EntityDAO<Withdrawals, String> {
	/**
	 * 统计已提现金额
	 * @param date
	 * @param merchantNo
	 * @param payCode
	 * @return List<WithDrawsTotalDTO>
	 */
	public List<WithDrawsTotalDTO> getWithDrawsMoneyTotal(String date,String merchantNo,String payCode);
	
	
	/**
	 * @Title: getTotalMoney
	 * @Description: 按照时间统计交易金额
	 * @author: husain
	 * @param date
	 * @return: BigDecimal
	 */
	BigDecimal getTotalMoney(String date,String merchantNo);

	/** 金额统计 */
	public List<Withdrawals> getAmtStatistics(WithdrawalsCondition withdrawalsCondition);
}
