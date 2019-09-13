package com.hfepay.commons.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.reflect.Reflector;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.dao.EntityDAO;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.entity.RecordStatus;
import com.hfepay.commons.utils.PagingResult;


public abstract class MybatisEntityDAO<E extends IdEntity<ID> ,ID extends Serializable> extends MybatisDaoSupport implements EntityDAO<E,ID>, MybatisDaoTemplate{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 实体对象类型
	 */
	protected Class<E> entityClass; 
	
	@SuppressWarnings("unchecked")
	public MybatisEntityDAO() {		
		this.entityClass = Reflector.of(getClass()).getSuperClassGenricType(0);
	}
	
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
	    super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	protected void beforeInsert(E entity){
		
	}
	
	protected void afterInsert(E entity){
		
	}

	public int insert(E entity) {
		beforeInsert(entity);
		int count = getSqlSession().insert(sqlId(INSERT), entity);
		afterInsert(entity);
		return count;
	}

	public int insert(List<E> entities) {
		if (entities != null) {
			for (E e : entities){
				insert(e);
			}
			return entities.size();
		}
		return 0;
	}

	protected void beforeUpdate(E entity){
		
	}
	
	protected void afterUpdate(E entity){
		
	}

	public int update(E entity) {
		beforeUpdate(entity);
		int count = getSqlSession().update(sqlId(UPDATE),entity);
		afterUpdate(entity);
		return count;
	}

	public int updateByCriteria(E entity, Criteria criteria){
		return getSqlSession().update(sqlId(UPDATE_BY_CRITERIA), Maps.map(String.class, Object.class, "record", entity,"params",criteria.getParams(), "conditions", criteria.getConditions()));
	}
	
	public int updateByCriteria(@SuppressWarnings("rawtypes") Map map,Criteria criteria){
		return getSqlSession().update(sqlId(UPDATE_BY_CRITERIA), Maps.map(String.class, Object.class, "record", map,"params",criteria.getParams(), "conditions", criteria.getConditions()));
	}

	
	@SuppressWarnings("rawtypes")
	protected int softDelete(List<ID> ids){
		CriteriaBuilder cb = Cnd.builder(entityClass);
		cb.andIn("id", ids);
		E entity = null;
		try {
			entity = entityClass.newInstance();
			((RecordStatus)entity).setRecordStatus(RecordStatus.INACTIVE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}		
		return  this.updateByCriteria(entity, cb.buildCriteria());	
	}
	
	protected int hardDelete(List<ID> ids){
		int count = 0;
		for (ID id : ids) {
			getSqlSession().delete(sqlId(DELETE_BY_ID),id);
			count++;
		}
		return count;		
	}
	
	protected void beforeDelete(List<ID> ids){
		
	}
	
	protected void afterDelete(List<ID> ids){
		
	}
	
	private int delete(List<ID> ids){
		beforeDelete(ids);
		int count = 0;

		if(Reflector.isChildOf(RecordStatus.class, entityClass)){			
			count = softDelete(ids);
		}else{
			count = hardDelete(ids);
		}
		afterDelete(ids);
		return count;		
	}
	
	public int delete(E... entities) {
		List<ID> ids = new ArrayList<ID>(entities.length);
		for (E e : entities) {
			ids.add(e.getId());
		}
		return delete(ids);
	}
	
	public int deleteById(ID... ids) {
		return delete(Lists.of(ids));
	}

	@SuppressWarnings("rawtypes")
	public int deleteByCriteria(Criteria criteria) {
		if(Reflector.isChildOf(RecordStatus.class, entityClass)){
			E entity = null;
			try {
				entity = entityClass.newInstance();
				((RecordStatus)entity).setRecordStatus(RecordStatus.INACTIVE);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			return this.updateByCriteria(entity, criteria);
		}else{
			return getSqlSession().delete(sqlId(DELETE_BY_CRITERIA), criteria);
		}
	}

	public E get(ID id) {
		if(Reflector.isChildOf(RecordStatus.class, entityClass)){
			CriteriaBuilder cb = Cnd.builder(entityClass);
			cb.andEQ("id", id).andEQ("recordStatus", RecordStatus.ACTIVE);
			Criteria criteria = cb.buildCriteria();
			return getSqlSession().selectOne(sqlId(FIND_BY_CRITERIA),criteria);
		}
		return getSqlSession().selectOne(sqlId(FIND_BY_ID),id);
	}

	public List<E> findAll() {
		if(Reflector.isChildOf(RecordStatus.class, entityClass)){
			CriteriaBuilder cb = Cnd.builder(entityClass);
			Criteria criteria = cb.buildCriteria();
			criteria.addParam("recordStatus", RecordStatus.ACTIVE);//假如实现了RecordStatus接口，则只查询活动的记录
			return getSqlSession().selectList(sqlId(FIND_BY_CRITERIA), criteria);
		}
		return getSqlSession().selectList(sqlId(FIND_BY_CRITERIA),null);
	}

	public List<E> findByCriteria(Criteria criteria) {
		if(Reflector.isChildOf(RecordStatus.class, entityClass)){
			criteria.addParam("recordStatus", RecordStatus.ACTIVE);//假如实现了RecordStatus接口，则只查询活动的记录
		}
		return getSqlSession().selectList(sqlId(FIND_BY_CRITERIA),criteria);
	}
	
	public E findOneByCriteria(Criteria criteria) {
		List<E> findObjects = findByCriteria(criteria);
		if (Lists.isNotEmpty(findObjects)){
			if(findObjects.size()>1){
				throw new RuntimeException("there are more than one result in findOneByCriteria......");
			}
			return findObjects.get(0);
		}
		return null;
	}
	

	public int countAll() {
		if(Reflector.isChildOf(RecordStatus.class, entityClass)){
			CriteriaBuilder cb = Cnd.builder(entityClass);
			Criteria criteria = cb.buildCriteria();
			return countByCriteria(criteria);
		}
		return countByCriteria(null);
	}

	public int countByCriteria(Criteria criteria) {
		if(Reflector.isChildOf(RecordStatus.class, entityClass)){
			criteria.addParam("recordStatus", RecordStatus.ACTIVE);//假如实现了RecordStatus接口，则只查询活动的记录
		}
		return (Integer) getSqlSession().selectOne(sqlId(COUNT_BY_CRITERIA), criteria);
	}

	public PagingResult<E> findPagingResult(Criteria criteria) {
		if(Reflector.isChildOf(RecordStatus.class, entityClass)){
			criteria.addParam("recordStatus", RecordStatus.ACTIVE);//假如实现了RecordStatus接口，则只查询活动的记录
		}
		return pagingQuery(sqlId(COUNT_BY_CRITERIA),sqlId(FIND_BY_CRITERIA),criteria);
	}

	public List<E> findByMap(Map<?, ?> parameter) {
		return getSqlSession().selectList(sqlId(FIND_BY_MAP),parameter);
	}
	
	protected String sqlId(String suffix) {
		return entityClass.getSimpleName() + "." + suffix;
	}

}
