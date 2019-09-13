package com.hfepay.pay.service;

import java.util.Map;

import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;

/**
 * 提现接口
 * @author husain
 *
 */
public interface WithdrawService {
	/**
	 * 提现
	 * @param OrderBo
	 * @return
	 */
	Map<String, String> withdraws(OrderBo orderBo);
	
	/**
	 * 提现回调
	 * @param MerchantNotifyMessage
	 * @return
	 */
	void withdrawsCallBack(MerchantWithdrowNotifyMessage bo) throws Exception;
}
