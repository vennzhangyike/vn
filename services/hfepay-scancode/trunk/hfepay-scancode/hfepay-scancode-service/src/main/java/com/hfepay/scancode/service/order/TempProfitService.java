/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.order;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.TempProfitCondition;
import com.hfepay.scancode.commons.entity.TempProfit;

public interface TempProfitService {
	
	/**
	 * 列表(分页)
	 * @param tempProfitCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	public PagingResult<TempProfit> findPagingResult(TempProfitCondition tempProfitCondition);
	
	/**
	 * 列表
	 * @param tempProfit 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	public List<TempProfit> findAll(TempProfitCondition tempProfitCondition);
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	TempProfit findById(String id);
	
	/**
	 * 新增
	 * @param tempProfitCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	long insert(TempProfitCondition tempProfitCondition);

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	long update(TempProfitCondition tempProfitCondition);
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	long updateByCriteria(TempProfitCondition tempProfitCondition,Criteria criteria);
	
	/**
	 * 单个实体对象
	 * @param tempProfitCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	public TempProfit findByCondition(TempProfitCondition tempProfitCondition);

	/**
	 * 批量插入
	 * @Title: insertBatch
	 * @Description: TODO
	 * @author: husain
	 * @param listTemp
	 * @return: void
	 */
	public void insertBatch(List<TempProfit> listTemp);

	/**
	 * 统计当天收益
	 * @Title: getTotalProfit
	 * @Description: TODO
	 * @author: husain
	 * @return
	 * @return: List<TempProfit>
	 */
	public List<TempProfit> getTotalProfitByIdentity();

	/**
	 * 代理商渠道总收益
	 * @Title: getTotalProfit
	 * @Description: TODO
	 * @author: husain
	 * @return
	 * @return: List<TempProfit>
	 */
	public List<TempProfit> getTotalProfit();

	/**
	 * 清除临时表数据
	 * @Title: clearTempData
	 * @Description: TODO
	 * @author: husain
	 * @return: void
	 */
	public void clearTempData();

	/**
	 * 
	 * @Title: getWithDrawType
	 * @Description: 获取提现的种类
	 * @author: husain
	 * @return
	 * @return: List<TempProfit>
	 */
	public List<TempProfit> getWithDrawType();
	
}

