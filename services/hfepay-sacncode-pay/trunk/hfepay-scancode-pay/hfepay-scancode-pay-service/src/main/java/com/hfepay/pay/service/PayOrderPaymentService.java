/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.pay.service;

import com.hfepay.scancode.commons.bo.OrderQueryBo;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.entity.OrderPayment;

public interface PayOrderPaymentService {
	
	/**
	 * 根据订单编号查询订单明细
	 * @param tradeNo
	 * @return OrderPayment
	 */
	public OrderPayment findOrderPaymentByTradeNo(String tradeNo);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	long update(OrderPaymentCondition orderPaymentCondition);
	
	/**
	 * 交易订单的保存
	 * @Title: saveOrder
	 * @Description: 保存订单信息
	 * @author: 
	 * @param order
	 * @return: void
	 * @throws  
	 */
	OrderPayment saveOrderPayment(OrderPaymentCondition orderPaymentCondition) ;
	
	/**
	 * 接口针对订单查询的参数查询订单信息
	 * @param OrderQueryBo
	 * @return
	 */
	OrderPayment findQueryOrder(OrderQueryBo bo);
	
	
}

