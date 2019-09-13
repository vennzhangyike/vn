/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.CityCondition;
import com.hfepay.scancode.commons.entity.City;

public interface CityService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param cityCondition
	 * @return: PagingResult<City>
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	public PagingResult<City> findPagingResult(CityCondition cityCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param cityCondition
	 * @return: List<City>
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	public List<City> findAll(CityCondition cityCondition);
	
	/**
	 * @Title: findByCname
	 * @Description: 列表
	 * @author: Ricky
	 * @param cityCondition
	 * @return: List<City>
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	public List<City> findByCname(CityCondition cityCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: City
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	City findById(Integer cid);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param cityCondition
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	long insert(CityCondition cityCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	long deleteById(Integer cid);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param cityCondition
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	long update(CityCondition cityCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param cityCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	long updateByCriteria(CityCondition cityCondition,Criteria criteria);
	
	
}

