/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * Sam 20100111 增加修改历史注释<br/>
 */

package com.hfepay.commons.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.entity.IdEntity;
import com.hfepay.commons.utils.PagingResult;

/**
 * 实体CRUD DAO接口。
 * 
 * @author Sam
 * 
 * @param <E>实体类型
 * @param <ID>实体ID类型
 */
public interface EntityDAO<E extends IdEntity<ID>, ID extends Serializable> extends DAO {
    
    /**
     * 新增一个实体
     * 
     * @param entity
     */
    public int insert(E entity);
    
    /**
     * 新增多个实体
     * @param entities
     * @return
     */
    public int insert(List<E> entities);
    
    /**
     * 更新一个实体
     * 
     * @param entity
     */
    public int update(E entity);
    
    /**
     * 根据自定义条件更新多个实体， 值为null的属性不会更新。
     * @param entity
     * @param criteria
     * @return
     */
    public int updateByCriteria(E entity, Criteria criteria);
    
    /**
     * 根据自定义条件更新多个实体
     * @param map
     * @param criteria
     * @return
     */
    public int updateByCriteria(@SuppressWarnings("rawtypes") Map map,Criteria criteria);
    
    /**
     * 删除一个或多个实体
     * 
     * @param entities
     */
    public int delete(E... entities);
    
    /**
     * 删除一个或多个实体
     * 
     * @param ids 实体ID集
     */
    public int deleteById(ID... ids);
    
    /**
     * 根据自定义条件删除实体
     * @param criteria
     * @return
     */
    public int deleteByCriteria(Criteria criteria) ;
    
    /**
     * 根据实体ID查找实体
     * 
     * @param id
     * @return
     */
    public E get(ID id);
    
    
    /**
     * 查找所有实体
     * 
     * @return
     */
    public List<E> findAll();
    
    /**
     * 根据criteria查询实体集
     * 
     * @param criteria 查询条件集
     * @return
     */
    public List<E> findByCriteria(Criteria criteria);
    
    /**
     * 根据条件集查找某个实体
     * @param criteria
     * @return
     */
    public E findOneByCriteria(Criteria criteria);
    
    /**
     * 统计所有的实体数
     */
    public int countAll();
    
    /**
     * 根据criteria统计实体数
     * @param criteria
     * @return
     */
    public int countByCriteria(Criteria criteria);
    
    /**
     * 分页查询
     * @param criteria 查询条件集
     * @return
     */
    public PagingResult<E> findPagingResult(Criteria criteria);
    
    /**
     * 根据Map参数查询实体
     * 
     * @param parameter
     * @return
     */
    public List<E> findByMap(Map<?, ?> parameter);
}
