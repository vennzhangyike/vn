/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.dao.MappingDicionDAO;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.service.operator.MappingDicionService;

@Service("mappingDicionService")
public class MappingDicionServiceImpl implements MappingDicionService {
	
	@Autowired
    private MappingDicionDAO mappingDicionDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: PagingResult<ApiMappingDicion>
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
    @Override
	public PagingResult<MappingDicion> findPagingResult(MappingDicionCondition mappingDicionCondition){
		CriteriaBuilder cb = Cnd.builder(MappingDicion.class);
		if(!Strings.isEmpty(mappingDicionCondition.getId())){
			cb.andEQ("id", mappingDicionCondition.getId());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getName())){
			cb.andLike("name", mappingDicionCondition.getName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getKeyNo())){
			cb.andEQ("keyNo", mappingDicionCondition.getKeyNo());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaName())){
			cb.andLike("paraName", mappingDicionCondition.getParaName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaVal())){
			cb.andEQ("paraVal", mappingDicionCondition.getParaVal());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getRecordStatus())){
			cb.andEQ("recordStatus", mappingDicionCondition.getRecordStatus());
		}
		if(null != mappingDicionCondition.getCreateTime()){
			cb.andEQ("createTime", mappingDicionCondition.getCreateTime());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getOperator())){
			cb.andEQ("operatorId", mappingDicionCondition.getOperator());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getRemark())){
			cb.andLike("remark", mappingDicionCondition.getRemark());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(mappingDicionCondition.getFirst()), Long.valueOf(mappingDicionCondition.getLast()));
		return mappingDicionDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: List<ApiMappingDicion>
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public List<MappingDicion> findAll(MappingDicionCondition mappingDicionCondition){
		CriteriaBuilder cb = Cnd.builder(MappingDicion.class);
		if(!Strings.isEmpty(mappingDicionCondition.getId())){
			cb.andEQ("id", mappingDicionCondition.getId());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getName())){
			cb.andEQ("name", mappingDicionCondition.getName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getKeyNo())){
			cb.andEQ("keyNo", mappingDicionCondition.getKeyNo());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaName())){
			cb.andEQ("paraName", mappingDicionCondition.getParaName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaVal())){
			cb.andEQ("paraVal", mappingDicionCondition.getParaVal());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getRecordStatus())){
			cb.andEQ("recordStatus", mappingDicionCondition.getRecordStatus());
		}
		if(null != mappingDicionCondition.getCreateTime()){
			cb.andEQ("createTime", mappingDicionCondition.getCreateTime());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getOperator())){
			cb.andEQ("operatorId", mappingDicionCondition.getOperator());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getRemark())){
			cb.andLike("remark", mappingDicionCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return mappingDicionDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ApiMappingDicion
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public MappingDicion findById(String id){
		return mappingDicionDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long insert(MappingDicionCondition mappingDicionCondition){
		MappingDicion mappingDicion = new MappingDicion();
		BeanUtils.copyProperties(mappingDicionCondition, mappingDicion);
		return mappingDicionDAO.insert(mappingDicion);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long deleteById(String id){
		return mappingDicionDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return mappingDicionDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return mappingDicionDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long update(MappingDicionCondition mappingDicionCondition){
		MappingDicion mappingDicion = new MappingDicion();
		BeanUtils.copyProperties(mappingDicionCondition, mappingDicion);
		mappingDicion.setUpdateTime(new Date());
		return mappingDicionDAO.update(mappingDicion);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param mappingDicionCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-18 17:36:07
	 */
	@Override
	public long updateByCriteria(MappingDicionCondition mappingDicionCondition,Criteria criteria){
		MappingDicion mappingDicion = new MappingDicion();
		BeanUtils.copyProperties(mappingDicionCondition, mappingDicion);
		return mappingDicionDAO.updateByCriteria(mappingDicion,criteria);
	}

	/**
	 * @Title: findByCondition
	 * @Description: 查询单个字典信息
	 * @param mappingDicionCondition
	 * @return
	 * @see com.hfepay.scancode.service.operator.MappingDicionService#findByCondition(com.hfepay.scancode.condition.MappingDicionCondition)
	 */
	@Override
	public MappingDicion findByCondition(MappingDicionCondition mappingDicionCondition) {
		CriteriaBuilder cb = Cnd.builder(MappingDicion.class);
		if(!Strings.isEmpty(mappingDicionCondition.getId())){
			cb.andEQ("id", mappingDicionCondition.getId());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getName())){
			cb.andEQ("name", mappingDicionCondition.getName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getKeyNo())){
			cb.andEQ("keyNo", mappingDicionCondition.getKeyNo());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaName())){
			cb.andEQ("paraName", mappingDicionCondition.getParaName());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getParaVal())){
			cb.andEQ("paraVal", mappingDicionCondition.getParaVal());
		}
		if(!Strings.isEmpty(mappingDicionCondition.getRecordStatus())){
			cb.andEQ("recordStatus", mappingDicionCondition.getRecordStatus());
		}
		if(null != mappingDicionCondition.getCreateTime()){
			cb.andEQ("createTime", mappingDicionCondition.getCreateTime());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getOperator())){
			cb.andEQ("operatorId", mappingDicionCondition.getOperator());
		}

		if(!Strings.isEmpty(mappingDicionCondition.getRemark())){
			cb.andLike("remark", mappingDicionCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return mappingDicionDAO.findOneByCriteria(buildCriteria);
	}
	
}

