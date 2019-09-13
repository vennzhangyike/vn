/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.entity.OrderPayment;

public interface OrderPaymentService {
	
	/**
	 * 列表(分页)
	 * @param orderPaymentCondition 
	 *
	 * @author panq
	 * @throws Exception 
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	public PagingResult<OrderPayment> findPagingResult(OrderPaymentCondition orderPaymentCondition) throws Exception;
	
	/**
	 * 列表
	 * @param orderPayment 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	public List<OrderPayment> findAll(OrderPaymentCondition orderPaymentCondition);
	
	/**
	 * 查询所有初始化的交易
	 * @param orderPayment 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	public List<OrderPayment> findAllByInit(OrderPaymentCondition orderPaymentCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	OrderPayment findById(String id);
	
	/**
	 * 新增
	 * @param orderPaymentCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	long insert(OrderPaymentCondition orderPaymentCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	long update(OrderPaymentCondition orderPaymentCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	long updateByCriteria(OrderPaymentCondition orderPaymentCondition,Criteria criteria);
	
	
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
	
	/** 金额统计 */
	Map<String, BigDecimal> getAmtStatic(List<OrderPayment> orderPayments);	
	
	/** 按代理商交易总金额统计 */
	Map<String, BigDecimal> getTotalAmtStatic(List<OrderPayment> orderPayments);

	public Map<String, BigDecimal> getAmtStaticMoney(OrderPaymentCondition orderPaymentCondition);

	/** 交易订单打印 */
	Map<Object, Object> print(String tradeNo);

	/** 获得交易订单 */
	public Map<Object, Object> getOrderPayment(String tradeNo);

	/**
	 * 
	 * @author liushuyu
	 * Desc 按条件查询 
	 * @param orderPayment
	 * @return
	 */
	public OrderPayment queryOrderPayment(OrderPayment orderPayment);
	
}

