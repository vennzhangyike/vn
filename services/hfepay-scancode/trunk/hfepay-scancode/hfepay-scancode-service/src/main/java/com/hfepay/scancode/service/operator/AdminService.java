package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.SysAdminCondition;
import com.hfepay.scancode.commons.entity.Admin;

public interface AdminService {
	Admin findByUsername(String username);
	
	/**
	 * 列表(分页)
	 * @param SysAdminCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	public PagingResult<Admin> findPagingResult(SysAdminCondition SysAdminCondition);
	
	/**
	 * 列表
	 * @param Admin 
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	public List<Admin> findAll(SysAdminCondition SysAdminCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	Admin findById(String id);
	
	/**
	 * 新增
	 * @param SysAdminCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long insert(SysAdminCondition SysAdminCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long update(SysAdminCondition SysAdminCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long updateByCriteria(SysAdminCondition SysAdminCondition,Criteria criteria);
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	long updateStatus(String id,Integer status);
	
	
	/**
	 * 新增或者更新账号信息，并修改角色关系
	 *
	 * @author panq
	 * @param number 
	 * @date CreateDate : 2016-06-27 15:35:32
	 */
	long updateFix(SysAdminCondition SysAdminCondition, String roleId);
}
