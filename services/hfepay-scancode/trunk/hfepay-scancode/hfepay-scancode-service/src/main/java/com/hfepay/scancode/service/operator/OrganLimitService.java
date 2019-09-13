/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.OrganLimitCondition;
import com.hfepay.scancode.commons.entity.OrganLimit;

public interface OrganLimitService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param organLimitCondition
	 * @return: PagingResult<OrganLimit>
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	public PagingResult<OrganLimit> findPagingResult(OrganLimitCondition organLimitCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param organLimitCondition
	 * @return: List<OrganLimit>
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	public List<OrganLimit> findAll(OrganLimitCondition organLimitCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: OrganLimit
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	OrganLimit findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param organLimitCondition
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	long insert(OrganLimitCondition organLimitCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param organLimitCondition
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	long update(OrganLimitCondition organLimitCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param organLimitCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	long updateByCriteria(OrganLimitCondition organLimitCondition,Criteria criteria);
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	long updateStatus(String id,String status);
	
	/** 获得机构限额 */
	public OrganLimit getOrganLimit(OrganLimitCondition organLimitCondition, String merchantNo);
	
	/**
	 * 校验最低、最高限额比上级底比下级高
	 * @param organLimitCondition
	 * @return 
	 */
	public Map<Object, Object> checkLimit(OrganLimitCondition organLimitCondition);	
	
}

