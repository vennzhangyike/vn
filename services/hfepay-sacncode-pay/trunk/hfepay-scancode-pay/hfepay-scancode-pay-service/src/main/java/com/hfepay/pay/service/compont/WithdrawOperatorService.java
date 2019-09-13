package com.hfepay.pay.service.compont;

import java.util.Map;

import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.entity.MerchantInfo;

public interface WithdrawOperatorService {
	/**
	 * 提现
	 * @param OrderBo
	 * @return
	 */
	Map<String, String> doWithdraws(OrderBo orderBo);
	
	/**
	 * 提现回调
	 * @param MerchantNotifyMessage
	 * @return
	 */
	void doWithdrawsCallBack(MerchantWithdrowNotifyMessage bo) throws Exception;
}
