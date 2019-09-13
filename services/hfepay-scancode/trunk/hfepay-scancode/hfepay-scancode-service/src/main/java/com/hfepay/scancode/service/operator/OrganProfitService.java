/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.OrganProfitCondition;
import com.hfepay.scancode.commons.dto.HierarchicalSettlementTotalDTO;
import com.hfepay.scancode.commons.entity.OrganProfit;

public interface OrganProfitService {
	
	/**
	 * 列表(分页)
	 * @param organProfitCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	public PagingResult<OrganProfit> findPagingResult(OrganProfitCondition organProfitCondition);
	
	/**
	 * 列表
	 * @param organProfit 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	public List<OrganProfit> findAll(OrganProfitCondition organProfitCondition);
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	OrganProfit findById(String id);
	
	/**
	 * 新增
	 * @param organProfitCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	long insert(OrganProfitCondition organProfitCondition);

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	long update(OrganProfitCondition organProfitCondition);
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	long updateByCriteria(OrganProfitCondition organProfitCondition,Criteria criteria);
	
	/**
	 * 单个实体对象
	 * @param organProfitCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	public OrganProfit findByCondition(OrganProfitCondition organProfitCondition);

	/**
	 * @Title: insertBatch
	 * @Description: 批量插入原始数据
	 * @author: husain
	 * @param list
	 * @return: void
	 */
	public void insertBatch(List<OrganProfit> list);

	/**
	 * @Title: updateMoney
	 * @Description: 按次数统计的利润金额修改到提现利润中去
	 * @author: husain
	 * @param organProfitCondition
	 * @return: void
	 */
	public void updateMoney(OrganProfitCondition organProfitCondition);

	/**
	 * 
	 * @Title: queryTotalProfit
	 * @Description: TODO
	 * @author: husain
	 * @param date
	 * @return
	 * @return: List<HierarchicalSettlementTotalDTO>
	 */
	public List<HierarchicalSettlementTotalDTO> queryTotalProfit(String date);
	
}

