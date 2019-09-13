/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ProvinceCondition;
import com.hfepay.scancode.commons.entity.Province;

public interface ProvinceService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param provinceCondition
	 * @return: PagingResult<Province>
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	public PagingResult<Province> findPagingResult(ProvinceCondition provinceCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param provinceCondition
	 * @return: List<Province>
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	public List<Province> findAll(ProvinceCondition provinceCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: Province
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	Province findById(Integer pid);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param provinceCondition
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	long insert(ProvinceCondition provinceCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	long deleteById(Integer pid);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param provinceCondition
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	long update(ProvinceCondition provinceCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param provinceCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	long updateByCriteria(ProvinceCondition provinceCondition,Criteria criteria);
	
	
}

