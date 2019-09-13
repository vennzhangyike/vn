/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.commons.dao;

import java.util.List;

import com.hfepay.commons.base.annotation.Generated;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.scancode.commons.entity.TempProfit;

@Generated("2016-12-01 15:05:27")
public interface TempProfitDAO extends EntityDAO<TempProfit, String> {

	void insertBatch(List<TempProfit> listTemp);

	/**
	 * 统计收益总额
	 * @Title: getTotalProfit
	 * @Description: TODO
	 * @author: husain
	 * @return
	 * @return: List<TempProfit>
	 */
	List<TempProfit> getTotalProfitByIdentity();

	/**
	 * @Title: getTotalProfit
	 * @Description: TODO
	 * @author: husain
	 * @return
	 * @return: List<TempProfit>
	 */
	List<TempProfit> getTotalProfit();

	/**
	 * 清空临时数据
	 * @Title: clearTempData
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	void clearTempData();

	/**
	 * @Title: getWithDrawType
	 * @Description: 提现种类
	 * @author: husain
	 * @return
	 * @return: List<TempProfit>
	 */
	List<TempProfit> getWithDrawType();
}
