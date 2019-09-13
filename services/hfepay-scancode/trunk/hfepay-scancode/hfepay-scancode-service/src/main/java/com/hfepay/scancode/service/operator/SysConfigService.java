package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.SysConfigCondition;
import com.hfepay.scancode.commons.entity.SysConfig;

public interface SysConfigService {

	public List<SysConfig> getSysConfig(String keyNo) throws Exception;
	
	public SysConfig getSyconfigByKeyNoAndValue(String keyNo,String paraVal)throws Exception;
	
	/**
	 * 列表(分页)
	 * @param sysConfigCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	public PagingResult<SysConfig> findPagingResult(SysConfigCondition sysConfigCondition);
	
	/**
	 * 列表
	 * @param sysConfig 
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	public List<SysConfig> findAll(SysConfigCondition sysConfigCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	SysConfig findById(String id);
	
	/**
	 * 新增
	 * @param sysConfigCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	long insert(SysConfigCondition sysConfigCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	long update(SysConfigCondition sysConfigCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 17:11:29
	 */
	long updateByCriteria(SysConfigCondition sysConfigCondition,Criteria criteria);
}
