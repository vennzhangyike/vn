/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.impl.MybatisEntityDAO;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.dao.OrderPaymentDAO;
import com.hfepay.scancode.commons.entity.OrderPayment;

@Repository(value="orderPaymentDAO")
@Generated("2016-10-17 11:37:55")
public class OrderPaymentDAOImpl extends MybatisEntityDAO<OrderPayment, String> implements OrderPaymentDAO {

	@Override
	public List<OrderPayment> getAmtStaticMoney(OrderPaymentCondition orderPaymentCondition) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(sqlId("getAmtStaticMoney"), orderPaymentCondition);
	}
}