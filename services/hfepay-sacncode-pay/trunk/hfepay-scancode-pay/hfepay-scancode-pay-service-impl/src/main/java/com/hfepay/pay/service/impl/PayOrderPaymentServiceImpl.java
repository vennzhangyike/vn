/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.pay.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.pay.service.PayOrderPaymentService;
import com.hfepay.pay.utils.OrderIDUtils;
import com.hfepay.scancode.commons.bo.OrderQueryBo;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.dao.OrderPaymentDAO;
import com.hfepay.scancode.commons.entity.OrderPayment;

@Service
public class PayOrderPaymentServiceImpl implements PayOrderPaymentService {
	
	public static final Logger log = LoggerFactory.getLogger(PayOrderPaymentServiceImpl.class);
	
	@Autowired
    private OrderPaymentDAO orderPaymentDAO;
	
	@Autowired
	private OrderIDUtils orderUtils;
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public long update(OrderPaymentCondition orderPaymentCondition){
		OrderPayment orderPayment = new OrderPayment();
		BeanUtils.copyProperties(orderPaymentCondition, orderPayment);
		return orderPaymentDAO.update(orderPayment);
	}
	

	//保存交易订单
	@Override
	public OrderPayment saveOrderPayment(OrderPaymentCondition orderPaymentCondition) {
		//生成交易订单编号
		orderPaymentCondition.setTradeNo(orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "ZF"));
		
		OrderPayment orderPayment = new OrderPayment();
		BeanUtils.copyProperties(orderPaymentCondition, orderPayment);
		orderPaymentDAO.insert(orderPayment);
		return orderPayment;
	}

	/**
	 * 根据订单编号查询订单明细
	 * @param tradeNo
	 * @return OrderPayment
	 */
	@Override
	public OrderPayment findOrderPaymentByTradeNo(String tradeNo) {
		if (Strings.isEmpty(tradeNo)) {
			new RuntimeException("查询订单编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
		cb.andEQ("tradeNo", tradeNo);
		Criteria buildCriteria = cb.buildCriteria();
		return orderPaymentDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public OrderPayment findQueryOrder(OrderQueryBo bo) {
		CriteriaBuilder cb1 = Cnd.builder(OrderPayment.class);
		cb1.andEQ("cashierNo", bo.getCashierNo());
		if(Strings.isNotEmpty(bo.getTradeNo())){
			cb1.andEQ("tradeNo", bo.getTradeNo());
		}
		if(Strings.isNotEmpty(bo.getUserOrderNo())){
			cb1.andEQ("extraTradeNo", bo.getUserOrderNo());
		}
//		if(Strings.isNotEmpty(bo.getTransNo())){
//			cb1.andEQ("channelTradeNo", bo.getTransNo());
//		}
		Criteria buildCriteria1 = cb1.buildCriteria();
		OrderPayment orderPayment =  orderPaymentDAO.findOneByCriteria(buildCriteria1);
		return orderPayment;
	}
}

