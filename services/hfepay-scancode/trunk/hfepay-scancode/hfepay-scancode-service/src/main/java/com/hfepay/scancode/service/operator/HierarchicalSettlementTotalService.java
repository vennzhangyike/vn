/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.HierarchicalSettlementTotalCondition;
import com.hfepay.scancode.commons.condition.HierarchicalStaticCondition;
import com.hfepay.scancode.commons.entity.HierarchicalSettlementTotal;
import com.hfepay.scancode.commons.entity.HierarchicalStatic;

public interface HierarchicalSettlementTotalService {
	
	/**
	 * 列表(分页)
	 * @param hierarchicalSettlementTotalCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	public PagingResult<HierarchicalSettlementTotal> findPagingResult(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition);
	
	/**
	 * 列表
	 * @param hierarchicalSettlementTotal 
	 *
	 * @author husain
	 * @throws ParseException 
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	public List<HierarchicalSettlementTotal> findAll(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition);
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	HierarchicalSettlementTotal findById(String id);
	
	/**
	 * 新增
	 * @param hierarchicalSettlementTotalCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	long insert(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition);

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	long update(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition);
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	long updateByCriteria(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition,Criteria criteria);
	
	/**
	 * 单个实体对象
	 * @param hierarchicalSettlementTotalCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	public HierarchicalSettlementTotal findByCondition(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition);
	
	
	/**
	 * 汇总利润数据
	 * @param hierarchicalSettlementTotalCondition 
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	public void saveSummaryProfit(String date);

	/** 分润统计查询*/
	public PagingResult<HierarchicalStatic> findHierarchicalStatic(
			HierarchicalStaticCondition hierarchicalStaticCondition,String nodeSeq);
	
	/** 金额统计 */
	Map<String, BigDecimal> getAmtStatic(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalsCondition);

	/**
	 * 
	 * @author liushuyu
	 * Desc 获取所有的hierarchicalStaticCondition
	 * @param hierarchicalStaticCondition
	 * @param nodeSeq
	 * @return
	 */
	PagingResult<HierarchicalStatic> findAllHierarchicalStatic(HierarchicalStaticCondition hierarchicalStaticCondition,
			String nodeSeq);	
	
}

