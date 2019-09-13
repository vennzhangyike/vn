/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.entity.MappingDicion;

public interface MappingDicionService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: PagingResult<MappingDicion>
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	public PagingResult<MappingDicion> findPagingResult(MappingDicionCondition mappingDicionCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: List<MappingDicion>
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	public List<MappingDicion> findAll(MappingDicionCondition mappingDicionCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MappingDicion
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	MappingDicion findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	long insert(MappingDicionCondition mappingDicionCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	long update(MappingDicionCondition mappingDicionCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	long updateByCriteria(MappingDicionCondition mappingDicionCondition,Criteria criteria);
	
	/**
	 * 
	 * @Title: findByCondition
	 * @Description: 根据条件查询单个
	 * @author: husain
	 * @param mappingDicionCondition
	 * @return
	 * @return: MappingDicion
	 */
	MappingDicion findByCondition(MappingDicionCondition mappingDicionCondition);
}

