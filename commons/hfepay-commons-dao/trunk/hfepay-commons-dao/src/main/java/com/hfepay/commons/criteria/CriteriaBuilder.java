package com.hfepay.commons.criteria;

import java.util.List;

import com.hfepay.commons.utils.Pagination;

public interface CriteriaBuilder {
	public Class<?> getEntityClass();

	public void setEntityClass(Class<?> entityClass);
	
	public CriteriaBuilder andEQ(String name , Object value);
	
	public CriteriaBuilder andNotEQ(String name , Object value);
	
	public CriteriaBuilder orEQ(String name , Object value);
	
	public CriteriaBuilder orNotEQ(String name , Object value);
	
	public CriteriaBuilder andGE(String name, Object value);
	
	public CriteriaBuilder orGE(String name, Object value);
	
	public CriteriaBuilder andGT(String name, Object value);
	
	public CriteriaBuilder orGT(String name, Object value);
	
	public CriteriaBuilder andLE(String name, Object value);
	
	public CriteriaBuilder orLE(String name, Object value);
	
	public CriteriaBuilder andLT(String name, Object value);
	
	public CriteriaBuilder orLT(String name, Object value);
	
	public CriteriaBuilder andLike(String name,String value);
	
	public CriteriaBuilder orLike(String name,String value);
	
	public CriteriaBuilder andLikeLeft(String name,String value);
	
	public CriteriaBuilder orLikeLeft(String name,String value);
	
	public CriteriaBuilder andLikeRight(String name,String value);
	
	public CriteriaBuilder orLikeRight(String name,String value);
	
	public CriteriaBuilder andIsNull(String name);
	
	public CriteriaBuilder orIsNull(String name);
	
	public CriteriaBuilder andIsNotNull(String name);
	
	public CriteriaBuilder orIsNotNull(String name);
	
	public CriteriaBuilder andBetween(String name,Object start,Object end);
	
	public CriteriaBuilder orBetween(String name,Object start,Object end);
	
	public CriteriaBuilder andNotBetween(String name,Object start,Object end);
	
	public CriteriaBuilder orNotBetween(String name,Object start,Object end);
	
	public CriteriaBuilder andIn(String name,List<?> values);
	
	public CriteriaBuilder orIn(String name,List<?> values);
	
	public CriteriaBuilder andNotIn(String name,List<?> values);
	
	public CriteriaBuilder orNotIn(String name,List<?> values);
	
	public CriteriaBuilder andGroup(CriteriaBuilder builder);		
	
	public CriteriaBuilder orGroup(CriteriaBuilder builder);
	
	public CriteriaBuilder andBitEQ(String name,Object value);
	
	public CriteriaBuilder andBitEQWithoutName(Object value,Object value2);
	
	public CriteriaBuilder andBitOR(String name,Object value);
	
	public CriteriaBuilder andBitORWithoutName(Object value,Object value2);
	
	public CriteriaBuilder orBitEQ(String name,Object value);
	
	public CriteriaBuilder orBitEQWithoutName(Object value,Object value2);
	
	public CriteriaBuilder orBitOR(String name,Object value);
	
	public CriteriaBuilder orBitORWithoutName(Object value,Object value2); 
	
	public CriteriaBuilder pagination(Pagination pagination);
	
	public CriteriaBuilder orderBy(String orderby,Order order);
	
	public CriteriaBuilder addParam(String name,Object value);
	
	public CriteriaBuilder addParams(Object...keyValues);
	
	public CriterionGroup buildCriterionGroup();
	
	public Criteria buildCriteria();
}
