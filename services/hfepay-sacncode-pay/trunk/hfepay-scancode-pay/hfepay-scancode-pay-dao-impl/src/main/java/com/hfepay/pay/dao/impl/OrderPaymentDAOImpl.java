/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.pay.dao.impl;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.pay.dao.OrderPaymentDAO;
import com.hfepay.scancode.commons.entity.OrderPayment;

@Repository(value="orderPaymentDAO")
@Generated("2016-10-17 11:37:55")
public class OrderPaymentDAOImpl extends MybatisEntityDAO<OrderPayment, String> implements OrderPaymentDAO {
}