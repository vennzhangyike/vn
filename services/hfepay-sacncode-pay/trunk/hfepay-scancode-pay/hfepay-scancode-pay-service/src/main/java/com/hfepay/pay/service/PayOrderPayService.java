/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.pay.service;

import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;

public interface PayOrderPayService {
	
	/**
	 * 根据订单编号查询资金流水信息
	 * @param tradeNo
	 * @return OrderPay
	 */
	public OrderPay findOrderPayByTradeNo(String tradeNo);
	
	/**
	 * 更新
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	long update(OrderPayCondition orderPayCondition);
	
	/**
	 * 创建资金流水订单
	 * @param order
	 * @param orderPayCondition
	 * @return OrderPay
	 */
	OrderPay createPayOrder(OrderPayment order,OrderPayCondition orderPayCondition);
	
	/**
	 * 创建提现订单
	 * @param orderPayCondition
	 * @return OrderPay
	 */
	OrderPay createWithdrawsOrder(OrderPayCondition orderPayCondition);

}

