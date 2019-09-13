package com.hfepay.commons.criteria.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.criteria.Condition;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.Logic;
import com.hfepay.commons.criteria.OrderBy;
/**
 * Criteria 默认实现类
 * 
 * @author Sam
 *
 */
public class DefaultCriteria implements Criteria,Serializable {
	
 
	private static final long serialVersionUID = 8242145021432772213L;

	protected Boolean limitable = false;
	
	protected Long start;
	
	protected Long end;
	
	protected List<Condition> conditions = Lists.newList();
	
	protected List<OrderBy> orderBys = Lists.newList();
		
	protected Map<Object, Object> params = new HashMap<Object, Object>();
	 

	public Criteria limit(Long start, Long end) {
		this.start = start;
		this.end = end;
		this.limitable = true;
		return this;
	}

	public Criteria add(Condition... conditions) {
		List<Condition> cnds = Lists.of(conditions);
		add(cnds);
		return this;
	}
	
	public Criteria add(List<Condition> conditions) {
		if (conditions !=null && conditions.size() >=1)
			conditions.get(0).setLogic(Logic.NONE);			
		this.conditions.addAll(conditions);
		return this;
	}

	public Criteria orderBy(OrderBy order) {
		orderBys.add(order);
		return this;
	}

	public Criteria useLimitit(Boolean limitable) {
		this.limitable = limitable;
		return this;
	}

	public Boolean isHasLimit() {
		return limitable;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public List<OrderBy> getOrderBys() {
		return orderBys;
	}

	public Boolean isHasConditionon() {
		return conditions !=null && !conditions.isEmpty();
	}

	public Boolean isHasOrderBy() {
		return orderBys!=null && !orderBys.isEmpty();
	}

	public Long getFirst() {
		return null == start?0L:start;
	}

	public Long getLast() { 
		return null == end?0L:end;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getParams() {
		return params;
	}

	@SuppressWarnings("unchecked")
	public Criteria setParams(@SuppressWarnings("rawtypes") Map params) {
		this.params = params;
		return this;
	}

	public Criteria addParam(Object paramName, Object value) {
		this.params.put(paramName, value);
		return this;
	}

	public Criteria addParams(Object... values) {
		Map<?, ?> map = Maps.map(values);
		this.params.putAll(map);
		return this;
	}

}
