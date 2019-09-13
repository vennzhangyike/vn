/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.pay.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.pay.service.PayOrderPayService;
import com.hfepay.pay.utils.OrderIDUtils;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.OrderPayDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;

@Service
public class PayOrderPayServiceImpl implements PayOrderPayService {
	public static final Logger log = LoggerFactory.getLogger(PayOrderPayServiceImpl.class);

	@Autowired
    private OrderPayDAO orderPayDAO;
    
	@Autowired
	private OrderIDUtils orderUtils;
	
	@Autowired
	private RedisSharedCache redisSharedCache;
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	@Override
	public long update(OrderPayCondition orderPayCondition){
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayCondition, orderPay);
		return orderPayDAO.update(orderPay);
	}
	
	@Override
	public OrderPay createPayOrder(OrderPayment orderPayment, OrderPayCondition orderPayCondition) {
		BeanUtils.copyProperties(orderPayment, orderPayCondition);
		orderPayCondition.setPayNo(orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "ZF"));
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayCondition, orderPay);
		orderPayDAO.insert(orderPay);
		return orderPay;
	}
	
	@Override
	public OrderPay createWithdrawsOrder(OrderPayCondition orderPayCondition) {
		orderPayCondition.setPayNo(orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "ZF"));
		orderPayCondition.setBeginTime(new Date());
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayCondition, orderPay);
		orderPayDAO.insert(orderPay);
		return orderPay;
	}

	/**
	 * 根据订单编号查询资金流水信息
	 * @param tradeNo
	 * @return OrderPay
	 */
	@Override
	public OrderPay findOrderPayByTradeNo(String tradeNo) {
		if (Strings.isEmpty(tradeNo)) {
			new RuntimeException("查询订单号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(OrderPay.class);
		cb.andEQ("tradeNo", tradeNo);
		Criteria buildCriteria = cb.buildCriteria();
		return orderPayDAO.findOneByCriteria(buildCriteria);
	}
}

