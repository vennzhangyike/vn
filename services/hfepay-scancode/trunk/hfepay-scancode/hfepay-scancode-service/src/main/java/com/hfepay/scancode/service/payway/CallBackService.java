package com.hfepay.scancode.service.payway;

import java.util.List;

import com.hfepay.scancode.commons.bo.MerchantBankChangeMessage;
import com.hfepay.scancode.commons.bo.MerchantJoinMessage;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.MerchantRateMessage;
import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;

public interface CallBackService {

	/**
	 * 支付回调:重构之后只针对定时轮训
	 * @param MerchantNotifyMessage
	 * @return
	 */
	void payCallBack(MerchantNotifyMessage bo) throws Exception;
	
	/**
	 * 提现回调:重构之后只针对定时轮训
	 * @param MerchantNotifyMessage
	 * @return
	 */
	void withdrawsCallBack(MerchantWithdrowNotifyMessage bo) throws Exception;
	
	/**
	 * 银行卡变更回调
	 * @param MerchantNotifyMessage
	 * @return
	 */
	void bankChangeCallBack(MerchantBankChangeMessage bo) throws Exception;
	
	/**
	 * 商户入驻回调
	 * @param MerchantJoinMessage
	 * @return
	 */
	void joinCallBack(MerchantJoinMessage bo,List<MerchantRateMessage> merchantRateList) throws Exception;
}
