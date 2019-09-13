/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.entity.OrderPayment;

@Generated("2016-10-17 11:37:55")
public interface OrderPaymentDAO extends EntityDAO<OrderPayment, String> {

	List<OrderPayment> getAmtStaticMoney(OrderPaymentCondition orderPaymentCondition);
}
