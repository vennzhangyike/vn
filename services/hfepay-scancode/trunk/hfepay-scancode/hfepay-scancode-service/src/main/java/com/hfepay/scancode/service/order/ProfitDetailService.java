/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.order;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ProfitDetailCondition;
import com.hfepay.scancode.commons.entity.ProfitDetail;

public interface ProfitDetailService {
	
	/**
	 * 列表(分页)
	 * @param profitDetailCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	public PagingResult<ProfitDetail> findPagingResult(ProfitDetailCondition profitDetailCondition);
	
	/**
	 * 列表
	 * @param profitDetail 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	public List<ProfitDetail> findAll(ProfitDetailCondition profitDetailCondition);
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	ProfitDetail findById(String id);
	
	/**
	 * 新增
	 * @param profitDetailCondition
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	long insert(ProfitDetailCondition profitDetailCondition);

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	long update(ProfitDetailCondition profitDetailCondition);
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	long updateByCriteria(ProfitDetailCondition profitDetailCondition,Criteria criteria);
	
	/**
	 * 单个实体对象
	 * @param profitDetailCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-11 14:21:58
	 */
	public ProfitDetail findByCondition(ProfitDetailCondition profitDetailCondition);

	/**
	 * 批量插入数据
	 * @param profitDetailCondition 
	 * @author husain
	 */
	public void inserBatch(List<ProfitDetail> listDetail);
	
}

