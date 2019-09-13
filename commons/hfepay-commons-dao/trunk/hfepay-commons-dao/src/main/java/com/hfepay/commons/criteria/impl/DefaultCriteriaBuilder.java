package com.hfepay.commons.criteria.impl;

import java.util.List;
import java.util.Map;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Assert;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Condition;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.CriterionGroup;
import com.hfepay.commons.criteria.Logic;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.criteria.OrderBy;
import com.hfepay.commons.utils.Pagination;


public class DefaultCriteriaBuilder implements CriteriaBuilder {
	protected Class<?> entityClass;
	private List<Condition> cnds = Lists.newList();
	private List<OrderBy> orderbys = Lists.newList();
	private Map<Object, Object> params = Maps.newMap();
	protected Pagination pagination ;
	
	
	public Class<?> getEntityClass() {
		return entityClass;
	}
	
	public CriteriaBuilder pagination(Pagination pagination) {
		this.pagination = pagination;
		return this;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	
	public CriteriaBuilder add(Condition cnd) {
		cnds.add(cnd);
		return this;
	}
	
	public CriteriaBuilder andEQ(String name , Object value) {
		add(Cnd.eq(Logic.AND, name, value));
		return this;
	}
	
	public CriteriaBuilder andNotEQ(String name , Object value) {
		add(Cnd.notEq(Logic.AND, name, value));
		return this;
	}
	
	public CriteriaBuilder orEQ(String name , Object value) {
		add(Cnd.eq(Logic.OR, name, value));
		return this;
	}
	
	public CriteriaBuilder orNotEQ(String name , Object value) {
		add(Cnd.notEq(Logic.OR, name, value));
		return this;
	}
	
	public CriteriaBuilder andGE(String name, Object value) {
		add(Cnd.ge(Logic.AND, name, value));
		return this;
	}
	
	public CriteriaBuilder orGE(String name, Object value) {
		add(Cnd.ge(Logic.OR, name, value));
		return this;
	}
	
	public CriteriaBuilder andGT(String name, Object value) {
		add(Cnd.gt(Logic.AND, name, value));
		return this;
	}
	
	public CriteriaBuilder orGT(String name, Object value) {
		add(Cnd.gt(Logic.OR, name, value));
		return this;
	}
	
	public CriteriaBuilder andLE(String name, Object value) {
		add(Cnd.le(Logic.AND, name, value));
		return this;
	}
	
	public CriteriaBuilder orLE(String name, Object value) {
		add(Cnd.le(Logic.OR, name, value));
		return this;
	}
	
	public CriteriaBuilder andLT(String name, Object value) {
		add(Cnd.lt(Logic.AND, name, value));
		return this;
	}
	
	public CriteriaBuilder orLT(String name, Object value) {
		add(Cnd.lt(Logic.OR, name, value));
		return this;
	}
	
	public CriteriaBuilder andLike(String name,String value) {
		add(Cnd.likeAll(Logic.AND, name, value));
		return this;
	}
	
	public CriteriaBuilder orLike(String name,String value) {
		add(Cnd.likeAll(Logic.OR, name, value));
		return this;
	}
	
	public CriteriaBuilder andLikeLeft(String name,String value) {
		add(Cnd.likeLeft(Logic.AND, name, value));
		return this;
	}
	
	public CriteriaBuilder orLikeLeft(String name,String value) {
		add(Cnd.likeLeft(Logic.OR, name, value));
		return this;
	}
	
	public CriteriaBuilder andLikeRight(String name,String value) {
		add(Cnd.likeRight(Logic.AND, name, value));
		return this;
	}
	
	public CriteriaBuilder orLikeRight(String name,String value) {
		add(Cnd.likeRight(Logic.OR, name, value));
		return this;
	}
	
	public CriteriaBuilder andIsNull(String name) {
		add(Cnd.isNull(Logic.AND, name));
		return this;
	}
	
	public CriteriaBuilder orIsNull(String name) {
		add(Cnd.isNull(Logic.OR, name));
		return this;
	}
	
	public CriteriaBuilder andIsNotNull(String name) {
		add(Cnd.isNotNull(Logic.AND, name));
		return this;
	}
	
	public CriteriaBuilder orIsNotNull(String name) {
		add(Cnd.isNotNull(Logic.OR, name));
		return this;
	}
	
	public CriteriaBuilder andBetween(String name,Object start,Object end) {
		add(Cnd.between(Logic.AND, name, start, end));
		return this;
	}
	
	public CriteriaBuilder orBetween(String name,Object start,Object end) {
		add(Cnd.between(Logic.OR, name, start, end));
		return this;
	}
	
	public CriteriaBuilder andNotBetween(String name,Object start,Object end) {
		add(Cnd.notBetween(Logic.AND, name, start, end));
		return this;
	}
	
	public CriteriaBuilder orNotBetween(String name,Object start,Object end) {
		add(Cnd.notBetween(Logic.OR, name, start, end));
		return this;
	}
	
	public CriteriaBuilder andIn(String name,List<?> values) {
		add(Cnd.in(Logic.AND, name, values));
		return this;
	}
	
	public CriteriaBuilder orIn(String name,List<?> values) {
		add(Cnd.in(Logic.OR, name, values));
		return this;
	}
	
	public CriteriaBuilder andNotIn(String name, List<?> values) {
		add(Cnd.notIn(Logic.AND, name, values));
		return this;
	}
	
	public CriteriaBuilder orNotIn(String name, List<?> values) {
		add(Cnd.notIn(Logic.OR, name, values));
		return this;
	}
	
	public CriteriaBuilder andGroup(CriteriaBuilder builder) {
		Assert.notNull(builder);
		add(builder.buildCriterionGroup());
		return this;
	}
		
	
	public CriteriaBuilder orGroup(CriteriaBuilder builder) {
		Assert.notNull(builder);
		CriterionGroup cg = builder.buildCriterionGroup();
		cg.setLogic(Logic.OR);
		add(cg);
		return this;
	}
	
	public CriteriaBuilder andBitEQ(String name,Object value) {
		return add(Cnd.bitEQ(Logic.AND, name, value));		
	}
	
	public CriteriaBuilder andBitEQWithoutName(Object value,Object value2) {
		return add(Cnd.bitEQWithoutName(Logic.AND, value, value2));		
	}
	
	public CriteriaBuilder andBitOR(String name,Object value) {
		return add(Cnd.bitOR(Logic.AND, name, value));		
	}
	
	public CriteriaBuilder andBitORWithoutName(Object value,Object value2) {
		return add(Cnd.bitORWithoutName(Logic.AND, value, value2));		
	}
	
	public CriteriaBuilder orBitEQ(String name,Object value) {
		return add(Cnd.bitEQ(Logic.OR, name, value));		
	}
	
	public CriteriaBuilder orBitEQWithoutName(Object value,Object value2) {
		return add(Cnd.bitEQWithoutName(Logic.OR, value, value2));		
	}
	
	public CriteriaBuilder orBitOR(String name,Object value) {
		return add(Cnd.bitOR(Logic.OR, name, value));		
	}
	
	public CriteriaBuilder orBitORWithoutName(Object value,Object value2) {
		return add(Cnd.bitORWithoutName(Logic.OR, value, value2));		
	}
	
	
	public CriterionGroup buildCriterionGroup() {
		return Cnd.createCriterionGroup().add(cnds);
	}
	
	public Criteria buildCriteria() {
		Criteria criteria = Cnd.createCriteria().add(cnds);

		if (pagination != null) {
			criteria.limit(new Long(pagination.getFirst()),new Long(pagination.getLast()));
			if (pagination.isOrderBySetted()) {
				add(new DefaultOrderBy(pagination.getOrderBy(),Order.valueOf(pagination.getOrder())));
			}
		}
		
		if (orderbys != null) {
			for (OrderBy orderby : orderbys) {
				criteria.orderBy(orderby);
			}
		}
		if (!params.isEmpty() ) {
			criteria.setParams(params);
		}
		
		return criteria;
	}
	
	public CriteriaBuilder orderBy(String orderby,Order order) {
		add(new DefaultOrderBy(orderby,order));
		return this;
	}
	
	protected CriteriaBuilder add(OrderBy orderby) {
		this.orderbys.add(orderby);
		return this;
	}
	
	public CriteriaBuilder addParam(String name,Object value) {
		this.params.put(name, value);
		return this;
	}
	
	public CriteriaBuilder addParams(Object...keyValues) {
		this.params.putAll(Maps.map(keyValues));
		return this;
	}
	  
}
