/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ParamsInfoCondition;
import com.hfepay.scancode.commons.entity.ParamsInfo;

import net.sf.json.JSONObject;

public interface ParamsInfoService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @return: PagingResult<ParamsInfo>
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	public PagingResult<ParamsInfo> findPagingResult(ParamsInfoCondition paramsInfoCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @return: List<ParamsInfo>
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	public List<ParamsInfo> findAll(ParamsInfoCondition paramsInfoCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ParamsInfo
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	ParamsInfo findById(String id);
	
	/**
	 * @Title: findParamsKey
	 * @Description: 根据key查询
	 * @author: Ricky
	 * @param paramsKey
	 * @param keyType
	 * @return: ParamsInfo
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	public ParamsInfo findParamsKey(String paramsKey,String keyType);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	long insert(ParamsInfoCondition paramsInfoCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	long update(ParamsInfoCondition paramsInfoCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	long updateByCriteria(ParamsInfoCondition paramsInfoCondition,Criteria criteria);
	
	/**
	 * @Title: getParamsInfoByDomain
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param organNo
	 * @return: JSONObject
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	JSONObject getParamsInfoByDomain(String organ);
}

