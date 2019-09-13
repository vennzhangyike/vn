package com.hfepay.commons.criteria.impl;

import java.util.List;

import com.hfepay.commons.criteria.Condition;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.CriterionGroup;
import com.hfepay.commons.criteria.Order;

/**
 * 基于Annonation的实体属性<->查询列的映射查询分页条件构建器实现
 * 
 * @author Sam
 *
 */
public class EntityCriteriaBuilder extends DefaultCriteriaBuilder {	

	ColumnMapper columnMapper;
	
	@SuppressWarnings("rawtypes")
	public EntityCriteriaBuilder(Class entityClass) {
		this.entityClass = entityClass;
		this.columnMapper = new AnnotationColumnMapper();
	}
	
	public CriteriaBuilder add(Condition cnd) {
		if (cnd.isGroup()) {
			CriterionGroup group = (CriterionGroup)cnd;
			List<Condition> all = group.getAll();
			for (Condition c : all) {
				if (!c.isGroup()) {
					DefaultCriterion dc = (DefaultCriterion) c;
					if(dc.getColumn() == null){
						dc.setColumn(columnMapper.getColumn(entityClass, dc.getName()));
					}
				}					
			}			
		}  else {
			DefaultCriterion dc = (DefaultCriterion)cnd;
			dc.setColumn(columnMapper.getColumn(entityClass, dc.getName()));
		}
		return super.add(cnd);
		
	}

	
	public CriteriaBuilder orderBy(String orderby, Order order) {
		add(new DefaultOrderBy(orderby,order,columnMapper.getColumn(entityClass, orderby)));
		return this;
	}

	public ColumnMapper getColumnMapper() {
		return columnMapper;
	}

	public void setColumnMapper(ColumnMapper columnMapper) {
		this.columnMapper = columnMapper;
	}
}
