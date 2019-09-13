/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.api.service;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.condition.ApiMappingDicionCondition;
import com.hfepay.scancode.api.entity.ApiMappingDicion;

public interface ApiMappingDicionService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: PagingResult<ApiMappingDicion>
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	public PagingResult<ApiMappingDicion> findPagingResult(ApiMappingDicionCondition mappingDicionCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: List<ApiMappingDicion>
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	public List<ApiMappingDicion> findAll(ApiMappingDicionCondition mappingDicionCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ApiMappingDicion
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	ApiMappingDicion findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	long insert(ApiMappingDicionCondition mappingDicionCondition);

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
	long update(ApiMappingDicionCondition mappingDicionCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	long updateByCriteria(ApiMappingDicionCondition mappingDicionCondition,Criteria criteria);
	
	/**
	 * 
	 * @Title: findByCondition
	 * @Description: 根据条件查询单个
	 * @author: husain
	 * @param mappingDicionCondition
	 * @return
	 * @return: ApiMappingDicion
	 */
	ApiMappingDicion findByCondition(ApiMappingDicionCondition mappingDicionCondition);
	
	/**
	 * 
	 * @Title: getFromRedis
	 * @Description: 从redis中获取实体，主要是微信相关的接url
	 * @author: husain
	 * @param mappingDicionCondition
	 * @return
	 * @return: ApiMappingDicion
	 */
	ApiMappingDicion getFromRedis(ApiMappingDicionCondition mappingDicionCondition) throws Exception;
}

