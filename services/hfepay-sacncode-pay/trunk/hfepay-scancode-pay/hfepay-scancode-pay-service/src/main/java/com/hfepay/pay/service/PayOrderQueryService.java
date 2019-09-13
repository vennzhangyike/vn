package com.hfepay.pay.service;

import java.util.Map;

import com.hfepay.scancode.commons.bo.OrderQueryBo;

/**
 * 订单查询
 * @author husain
 *
 */
public interface PayOrderQueryService {
	/**
	 * 订单查询
	 * @param bo
	 * @return
	 */
	public Map<String, String> queryOrder(OrderQueryBo bo);
}
