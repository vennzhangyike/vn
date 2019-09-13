/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantAuthDetailCondition;
import com.hfepay.scancode.commons.entity.MerchantAuthDetail;

public interface MerchantAuthDetailService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantAuthDetailCondition
	 * @return: PagingResult<MerchantAuthDetail>
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	public PagingResult<MerchantAuthDetail> findPagingResult(MerchantAuthDetailCondition merchantAuthDetailCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param merchantAuthDetailCondition
	 * @return: List<MerchantAuthDetail>
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	public List<MerchantAuthDetail> findAll(MerchantAuthDetailCondition merchantAuthDetailCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MerchantAuthDetail
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	MerchantAuthDetail findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantAuthDetailCondition
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	long insert(MerchantAuthDetailCondition merchantAuthDetailCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param merchantAuthDetailCondition
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	long update(MerchantAuthDetailCondition merchantAuthDetailCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param merchantAuthDetailCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	long updateByCriteria(MerchantAuthDetailCondition merchantAuthDetailCondition,Criteria criteria);
	
	public Map<String, Integer> getAuthDetail(MerchantAuthDetailCondition merchantAuthDetailCondition);
	
}

