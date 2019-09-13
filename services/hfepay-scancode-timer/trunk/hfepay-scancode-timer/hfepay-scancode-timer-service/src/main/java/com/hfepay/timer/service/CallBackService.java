package com.hfepay.timer.service;

import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;

public interface CallBackService {

	/**
	 * 支付回调
	 * @param MerchantNotifyMessage
	 * @return
	 */
	void payCallBack(MerchantNotifyMessage bo) throws Exception;
	
	/**
	 * 提现回调
	 * @param MerchantNotifyMessage
	 * @return
	 */
	void withdrawsCallBack(MerchantWithdrowNotifyMessage bo) throws Exception;
	
	
}
