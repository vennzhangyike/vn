package com.hfepay.pay.service;

import java.util.Map;

import com.hfepay.scancode.commons.bo.OrderRefundBo;

/**
 * 退款
 * @author husain
 *
 */
public interface PayOrderRefundService {
	/**
	 * 退款
	 * @param bo
	 * @return
	 */
	public Map<String, String> refund(OrderRefundBo bo);
}
