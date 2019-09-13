package com.hfepay.pay.service.compont;

import java.util.Map;

import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;

public interface PayOperatorService {
	/**
	 * 支付
	 * @Title: scanCodePay
	 * @Description: TODO
	 * @author: husain
	 * @param orderBo
	 * @return
	 * @return: Map<String,String>
	 */
	Map<String, String> doPay(OrderBo orderBo);
	
	/**
	 * 回调
	 * @Title: payCallBack
	 * @Description: TODO
	 * @author: husain
	 * @param bo
	 * @throws Exception
	 * @return: void
	 */
	public void doPayCallBack(MerchantNotifyMessage bo) throws Exception;
	
	/**
	 * 策略代号
	 * @return
	 */
	public String strategyCode();
}
