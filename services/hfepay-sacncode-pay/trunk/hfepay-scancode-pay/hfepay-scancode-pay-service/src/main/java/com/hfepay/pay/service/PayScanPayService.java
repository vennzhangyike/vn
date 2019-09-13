package com.hfepay.pay.service;

import java.util.Map;

import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;

/**
 * 平台网关处理
 * @author Administrator
 *
 */
public interface PayScanPayService {
	/**
	 * 条形码支付
	 * @Title: scanCodePay
	 * @Description: TODO
	 * @author: husain
	 * @param orderBo
	 * @return
	 * @return: Map<String,String>
	 */
	Map<String, String> scanCodePay(OrderBo orderBo);
	
	/**
	 * 条码支付回调
	 * @Title: payCallBack
	 * @Description: TODO
	 * @author: husain
	 * @param bo
	 * @throws Exception
	 * @return: void
	 */
	public void payCallBack(MerchantNotifyMessage bo) throws Exception;
}
