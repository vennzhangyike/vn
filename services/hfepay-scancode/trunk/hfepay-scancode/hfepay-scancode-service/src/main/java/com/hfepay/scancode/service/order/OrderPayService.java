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
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.dto.OrderPayStaticDTO;
import com.hfepay.scancode.commons.dto.OrderPayTotalDTO;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;

public interface OrderPayService {
	
	/**
	 * 列表(分页)
	 * @param orderPayCondition 
	 *
	 * @author panq
	 * @throws Exception 
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	public PagingResult<OrderPay> findPagingResult(OrderPayCondition orderPayCondition) throws Exception;
	
	/**
	 * 列表
	 * @param orderPay 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	public List<OrderPay> findAll(OrderPayCondition orderPayCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	OrderPay findById(String id);
	
	/**
	 * 新增
	 * @param orderPayCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	long insert(OrderPayCondition orderPayCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	long update(OrderPayCondition orderPayCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	long updateByCriteria(OrderPayCondition orderPayCondition,Criteria criteria);
	
	OrderPay createPayOrder(OrderPayment order,OrderPayCondition orderPayCondition);
	
	OrderPay createWithdrawsOrder(OrderPayCondition orderPayCondition);

	/**
	 * @Title: getTotalMoney
	 * @Description: 统计交易金额
	 * @author: husain
	 * @param type:D天，M月，Y年
	 * @param date：YYYY-MM-DD
	 * @return: BigDecimal
	 */
	public Map<String,BigDecimal> getTotalMoney(String date,String type,String cashierNo,String identityFlag);
	
	/**
	 * 当天交易金额和到账金额
	 * @Title: getDealMoney
	 * @Description: TODO
	 * @author: husain
	 * @param identityNo
	 * @return
	 * @return: Map<String,String>
	 */
	public List<OrderPayTotalDTO> getDealMoney(String identityNo,String flag);
	
	/**
	 * 当天交易金额和到账金额
	 * @Title: getDealMoney
	 * @Description: TODO
	 * @author: husain
	 * @param identityNo
	 * @return
	 * @return: Map<String,String>
	 */
	public Map<String, BigDecimal> getDealMoneyTotal(String identityNo,String flag);

	/**
	 * @Title: getSumOrderAmt
	 * @Description: 统计
	 * @author: husain
	 * @param date
	 * @param type 
	 * @return
	 * @return: List<OrderPay>
	 */
	public List<OrderPayStaticDTO> getSumOrderAmt(String date, String type);

	/**
	 * @Title: getWithDrawTimes
	 * @Description: 提现次数
	 * @author: husain
	 * @param date
	 * @return
	 * @return: List<OrderPayStaticDTO>
	 */
	public List<OrderPayStaticDTO> getWithDrawTimes(String date, String type);

	/**
	 * @Title: countByCriteria
	 * @Description: 计算中记录数
	 * @author: husain
	 * @param date
	 * @return int
	 */
	public int countByCriteria(OrderPayCondition orderPayCondition);

	/**
	 * 按时间维度统计收款金额
	 * @param date
	 * @param code
	 * @param identityNo
	 * @param identityFlag
	 * @return
	 */
	public BigDecimal getTotalInMoney(String date, String code, String identityNo, String identityFlag);
}

