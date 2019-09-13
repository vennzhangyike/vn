package com.hfepay.pay.service;

import java.util.Map;

import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;

/**
 * 支付相应接口
 * @author:husain
 */
public interface PayService {
	/**
	 * 支付
	 * @Title: scanCodePay
	 * @Description: TODO
	 * @author: husain
	 * @param orderBo
	 * @return
	 * @return: Map<String,String>
	 */
	Map<String, String> pay(OrderBo orderBo);
	
	/**
	 * 回调
	 * @Title: payCallBack
	 * @Description: TODO
	 * @author: husain
	 * @param bo
	 * @throws Exception
	 * @return: void
	 */
	public void payCallBack(MerchantNotifyMessage bo) throws Exception;
}
