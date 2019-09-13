/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.pay.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.dto.OrderPayStaticDTO;
import com.hfepay.scancode.commons.dto.OrderPayTotalDTO;
import com.hfepay.scancode.commons.entity.OrderPay;

@Generated("2016-10-17 11:37:40")
public interface OrderPayDAO extends EntityDAO<OrderPay, String> {

	/**
	 * @Title: getTotalMoney
	 * @Description: 按照时间统计交易金额
	 * @author: husain
	 * @param date
	 * @return: BigDecimal
	 */
	BigDecimal getTotalMoney(String date,String merchantNo,String identityFlag);

	/**
	 * 当天的交易金额和实际到账金额
	 * @Title: getDealMoney
	 * @Description: TODO
	 * @author: husain
	 * @param format
	 * @param identityNo
	 * @return
	 * @return: Map<String,String>
	 */
	List<OrderPayTotalDTO> getDealMoney(String format, String identityNo,String flag);
	
	/**
	 * 当天的交易金额和实际到账金额
	 * @Title: getDealMoney
	 * @Description: TODO
	 * @author: husain
	 * @param format
	 * @param identityNo
	 * @return
	 * @return: Map<String,String>
	 */
	Map<String, BigDecimal> getDealMoneyTotal(String format, String identityNo,String flag);
	
	/**
	 * 
	 * @Title: getSumOrderAmt
	 * @author: husain
	 * @param format
	 * @param type 
	 * @return
	 * @return: List<OrderPayStaticDTO>
	 */
	List<OrderPayStaticDTO> getSumOrderAmt(String format, String type);

	/**
	 * 提现次数统计
	 * @Title: getWithDrawTimes
	 * @author: husain
	 * @param date
	 * @return
	 * @return: List<OrderPayStaticDTO>
	 */
	List<OrderPayStaticDTO> getWithDrawTimes(String date, String type);

}
