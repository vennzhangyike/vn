/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
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
import com.hfepay.scancode.commons.condition.HfepayCategoryCondition;
import com.hfepay.scancode.commons.dao.HfepayCategoryDAO;
import com.hfepay.scancode.commons.entity.HfepayCategory;
import com.hfepay.scancode.service.operator.HfepayCategoryService;

@Service("hfepayCategoryService")
public class HfepayCategoryServiceImpl implements HfepayCategoryService {
	
	@Autowired
    private HfepayCategoryDAO hfepayCategoryDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param hfepayCategoryCondition
	 * @return: PagingResult<HfepayCategory>
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
    @Override
	public PagingResult<HfepayCategory> findPagingResult(HfepayCategoryCondition hfepayCategoryCondition){
		CriteriaBuilder cb = Cnd.builder(HfepayCategory.class);
		if(!Strings.isEmpty(hfepayCategoryCondition.getId())){
			cb.andEQ("id", hfepayCategoryCondition.getId());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getCode())){
			cb.andEQ("code", hfepayCategoryCondition.getCode());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getPayType())){
			cb.andEQ("payType", hfepayCategoryCondition.getPayType());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getName())){
			cb.andEQ("name", hfepayCategoryCondition.getName());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getParentId())){
			cb.andEQ("parentId", hfepayCategoryCondition.getParentId());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getLevel())){
			cb.andEQ("level", hfepayCategoryCondition.getLevel());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getCertType())){
			cb.andEQ("certType", hfepayCategoryCondition.getCertType());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getCategoryNo())){
			cb.andEQ("categoryNo", hfepayCategoryCondition.getCategoryNo());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getMappingCategoryNo())){
			cb.andEQ("mappingCategoryNo", hfepayCategoryCondition.getMappingCategoryNo());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getStatus())){
			cb.andEQ("status", hfepayCategoryCondition.getStatus());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getRecordStatus())){
			cb.andEQ("recordStatus", hfepayCategoryCondition.getRecordStatus());
		}
		if(null != hfepayCategoryCondition.getCreateTime()){
			cb.andEQ("createTime", hfepayCategoryCondition.getCreateTime());
		}

		if(!Strings.isEmpty(hfepayCategoryCondition.getOperater())){
			cb.andEQ("operater", hfepayCategoryCondition.getOperater());
		}

		if(!Strings.isEmpty(hfepayCategoryCondition.getRemark())){
			cb.andLike("remark", hfepayCategoryCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(hfepayCategoryCondition.getFirst()), Long.valueOf(hfepayCategoryCondition.getLast()));
		return hfepayCategoryDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param hfepayCategoryCondition
	 * @return: List<HfepayCategory>
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@Override
	public List<HfepayCategory> findAll(HfepayCategoryCondition hfepayCategoryCondition){
		CriteriaBuilder cb = Cnd.builder(HfepayCategory.class);
		if(!Strings.isEmpty(hfepayCategoryCondition.getId())){
			cb.andEQ("id", hfepayCategoryCondition.getId());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getCode())){
			cb.andEQ("code", hfepayCategoryCondition.getCode());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getPayType())){
			cb.andEQ("payType", hfepayCategoryCondition.getPayType());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getName())){
			cb.andEQ("name", hfepayCategoryCondition.getName());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getParentId())){
			cb.andEQ("parentId", hfepayCategoryCondition.getParentId());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getLevel())){
			cb.andEQ("level", hfepayCategoryCondition.getLevel());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getCertType())){
			cb.andEQ("certType", hfepayCategoryCondition.getCertType());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getCategoryNo())){
			cb.andEQ("categoryNo", hfepayCategoryCondition.getCategoryNo());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getMappingCategoryNo())){
			cb.andEQ("mappingCategoryNo", hfepayCategoryCondition.getMappingCategoryNo());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getStatus())){
			cb.andEQ("status", hfepayCategoryCondition.getStatus());
		}
		if(!Strings.isEmpty(hfepayCategoryCondition.getRecordStatus())){
			cb.andEQ("recordStatus", hfepayCategoryCondition.getRecordStatus());
		}
		if(null != hfepayCategoryCondition.getCreateTime()){
			cb.andEQ("createTime", hfepayCategoryCondition.getCreateTime());
		}

		if(!Strings.isEmpty(hfepayCategoryCondition.getOperater())){
			cb.andEQ("operater", hfepayCategoryCondition.getOperater());
		}

		if(!Strings.isEmpty(hfepayCategoryCondition.getRemark())){
			cb.andLike("remark", hfepayCategoryCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return hfepayCategoryDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: HfepayCategory
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@Override
	public HfepayCategory findById(String id){
		return hfepayCategoryDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param hfepayCategoryCondition
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@Override
	public long insert(HfepayCategoryCondition hfepayCategoryCondition){
		HfepayCategory hfepayCategory = new HfepayCategory();
		BeanUtils.copyProperties(hfepayCategoryCondition, hfepayCategory);
		return hfepayCategoryDAO.insert(hfepayCategory);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@Override
	public long deleteById(String id){
		return hfepayCategoryDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return hfepayCategoryDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return hfepayCategoryDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param hfepayCategoryCondition
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@Override
	public long update(HfepayCategoryCondition hfepayCategoryCondition){
		HfepayCategory hfepayCategory = new HfepayCategory();
		BeanUtils.copyProperties(hfepayCategoryCondition, hfepayCategory);
		return hfepayCategoryDAO.update(hfepayCategory);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param hfepayCategoryCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@Override
	public long updateByCriteria(HfepayCategoryCondition hfepayCategoryCondition,Criteria criteria){
		HfepayCategory hfepayCategory = new HfepayCategory();
		BeanUtils.copyProperties(hfepayCategoryCondition, hfepayCategory);
		return hfepayCategoryDAO.updateByCriteria(hfepayCategory,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@Override
	public long updateStatus(String id,String status){
		return hfepayCategoryDAO.updateStatus(id,status);
	}	
	
	
	/**
	 * @Title: findByCategoryNo
	 * @Description: categoryNo查找
	 * @author: Ricky
	 * @param categoryNo
	 * @return: HfepayCategory
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@Override
	public HfepayCategory findByCategoryNo(String categoryNo){
		HfepayCategoryCondition hfepayCategoryCondition = new HfepayCategoryCondition();
		hfepayCategoryCondition.setCategoryNo(categoryNo);
		List<HfepayCategory> hfepayCategorys = this.findAll(hfepayCategoryCondition);
		if(null!=hfepayCategorys&&!hfepayCategorys.isEmpty()){
			return hfepayCategorys.get(0);
		}
		return null;
	}
}

