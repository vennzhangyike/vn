/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.OrganWithdrawalsCondition;
import com.hfepay.scancode.commons.entity.OrganWithdrawals;

public interface OrganWithdrawalsService {
	
	/**
	 * 列表(分页)
	 * @param organWithdrawalsCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	public PagingResult<OrganWithdrawals> findPagingResult(OrganWithdrawalsCondition organWithdrawalsCondition);
	
	/**
	 * 列表
	 * @param organWithdrawals 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	public List<OrganWithdrawals> findAll(OrganWithdrawalsCondition organWithdrawalsCondition);
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	OrganWithdrawals findById(String id);
	
	/**
	 * 新增
	 * @param organWithdrawalsCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long insert(OrganWithdrawalsCondition organWithdrawalsCondition);

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long update(OrganWithdrawalsCondition organWithdrawalsCondition);
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	long updateByCriteria(OrganWithdrawalsCondition organWithdrawalsCondition,Criteria criteria);
	
	/**
	 * 单个实体对象
	 * @param organWithdrawalsCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	public OrganWithdrawals findByCondition(OrganWithdrawalsCondition organWithdrawalsCondition);

	/**
	 * 提现申请
	 * @Title: addWithDraw
	 * @Description: TODO
	 * @author: husain
	 * @param organWithdrawalsCondition
	 * @return
	 * @return: long
	 */
	public long saveWithDraw(OrganWithdrawalsCondition organWithdrawalsCondition);
	/**
	 * 提现审核
	 * @Title: addWithDraw
	 * @Description: TODO
	 * @author: husain
	 * @param organWithdrawalsCondition
	 * @return
	 * @return: long
	 */
	public long saveAuditWithDraw(OrganWithdrawalsCondition organWithdrawalsCondition);
	
	/** 金额统计 */
	Map<String, BigDecimal> getAmtStatistics(OrganWithdrawalsCondition organWithdrawalsCondition);	
	
}

