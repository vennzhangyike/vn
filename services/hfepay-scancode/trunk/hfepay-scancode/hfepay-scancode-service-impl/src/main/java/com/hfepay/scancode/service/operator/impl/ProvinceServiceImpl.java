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
import com.hfepay.scancode.commons.condition.ProvinceCondition;
import com.hfepay.scancode.commons.dao.ProvinceDAO;
import com.hfepay.scancode.commons.entity.Province;
import com.hfepay.scancode.service.operator.ProvinceService;

@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService {
	
	@Autowired
    private ProvinceDAO provinceDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param provinceCondition
	 * @return: PagingResult<Province>
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
    @Override
	public PagingResult<Province> findPagingResult(ProvinceCondition provinceCondition){
		CriteriaBuilder cb = Cnd.builder(Province.class);
		if(null != provinceCondition.getPid()){
			cb.andEQ("pid", provinceCondition.getPid());
		}
		if(!Strings.isEmpty(provinceCondition.getPname())){
			cb.andEQ("pname", provinceCondition.getPname());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(provinceCondition.getFirst()), Long.valueOf(provinceCondition.getLast()));
		return provinceDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param provinceCondition
	 * @return: List<Province>
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	@Override
	public List<Province> findAll(ProvinceCondition provinceCondition){
		CriteriaBuilder cb = Cnd.builder(Province.class);
		if(null != provinceCondition.getPid()){
			cb.andEQ("pid", provinceCondition.getPid());
		}
		if(!Strings.isEmpty(provinceCondition.getPname())){
			cb.andEQ("pname", provinceCondition.getPname());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return provinceDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: Province
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	@Override
	public Province findById(Integer pid){
		return provinceDAO.get(pid);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param provinceCondition
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	@Override
	public long insert(ProvinceCondition provinceCondition){
		Province province = new Province();
		BeanUtils.copyProperties(provinceCondition, province);
		return provinceDAO.insert(province);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	@Override
	public long deleteById(Integer pid){
		return provinceDAO.deleteById(pid);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return provinceDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return provinceDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param provinceCondition
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	@Override
	public long update(ProvinceCondition provinceCondition){
		Province province = new Province();
		BeanUtils.copyProperties(provinceCondition, province);
		return provinceDAO.update(province);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param provinceCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-22 14:03:17
	 */
	@Override
	public long updateByCriteria(ProvinceCondition provinceCondition,Criteria criteria){
		Province province = new Province();
		BeanUtils.copyProperties(provinceCondition, province);
		return provinceDAO.updateByCriteria(province,criteria);
	}
	
}

