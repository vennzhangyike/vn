/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.CityCondition;
import com.hfepay.scancode.commons.dao.CityDAO;
import com.hfepay.scancode.commons.entity.City;
import com.hfepay.scancode.service.operator.CityService;

@Service("cityService")
public class CityServiceImpl implements CityService {
	
	@Autowired
    private CityDAO cityDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param cityCondition
	 * @return: PagingResult<City>
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
    @Override
	public PagingResult<City> findPagingResult(CityCondition cityCondition){
		CriteriaBuilder cb = Cnd.builder(City.class);
		if(null != cityCondition.getCid()){
			cb.andEQ("cid", cityCondition.getCid());
		}
		if(!Strings.isEmpty(cityCondition.getCname())){
			cb.andEQ("cname", cityCondition.getCname());
		}
		if(!Strings.isEmpty(cityCondition.getCpostcode())){
			cb.andEQ("cpostcode", cityCondition.getCpostcode());
		}
		if(null != cityCondition.getPid()){
			cb.andEQ("pid", cityCondition.getPid());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(cityCondition.getFirst()), Long.valueOf(cityCondition.getLast()));
		return cityDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param cityCondition
	 * @return: List<City>
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	@Override
	public List<City> findAll(CityCondition cityCondition){
		CriteriaBuilder cb = Cnd.builder(City.class);
		if(null != cityCondition.getCid()){
			cb.andEQ("cid", cityCondition.getCid());
		}
		if(!Strings.isEmpty(cityCondition.getCname())){
			cb.andEQ("cname", cityCondition.getCname());
		}
		if(!Strings.isEmpty(cityCondition.getCpostcode())){
			cb.andEQ("cpostcode", cityCondition.getCpostcode());
		}
		if(null != cityCondition.getPid()){
			cb.andEQ("pid", cityCondition.getPid());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return cityDAO.findByCriteria(buildCriteria);
	}
	
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param cityCondition
	 * @return: List<City>
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	@Override
	public List<City> findByCname(CityCondition cityCondition){
		CriteriaBuilder cb = Cnd.builder(City.class);
		if(null != cityCondition.getCid()){
			cb.andEQ("cid", cityCondition.getCid());
		}
		if(!Strings.isEmpty(cityCondition.getCname())){
			cb.andLike("cname", cityCondition.getCname());
		}
		if(!Strings.isEmpty(cityCondition.getCpostcode())){
			cb.andEQ("cpostcode", cityCondition.getCpostcode());
		}
		if(null != cityCondition.getPid()){
			cb.andEQ("pid", cityCondition.getPid());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return cityDAO.findByCriteria(buildCriteria);
	}
	
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: City
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	@Override
	public City findById(Integer cid){
		return cityDAO.get(cid);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param cityCondition
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	@Override
	public long insert(CityCondition cityCondition){
		City city = new City();
		BeanUtils.copyProperties(cityCondition, city);
		return cityDAO.insert(city);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	@Override
	public long deleteById(Integer cid){
		return cityDAO.deleteById(cid);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return cityDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return cityDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param cityCondition
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	@Override
	public long update(CityCondition cityCondition){
		City city = new City();
		BeanUtils.copyProperties(cityCondition, city);
		return cityDAO.update(city);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param cityCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	@Override
	public long updateByCriteria(CityCondition cityCondition,Criteria criteria){
		City city = new City();
		BeanUtils.copyProperties(cityCondition, city);
		return cityDAO.updateByCriteria(city,criteria);
	}
	
}

