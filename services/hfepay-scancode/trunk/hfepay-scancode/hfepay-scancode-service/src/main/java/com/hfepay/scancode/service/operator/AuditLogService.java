package com.hfepay.scancode.service.operator;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

import java.util.List;
import java.util.Map;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.AuditLogCondition;
import com.hfepay.scancode.commons.entity.AuditLog;

public interface AuditLogService {
	
	/**
	 * 列表(分页)
	 * @param auditLogCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	public PagingResult<AuditLog> findPagingResult(AuditLogCondition auditLogCondition);
	
	/**
	 * 列表
	 * @param auditLog 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	public List<AuditLog> findAll(AuditLogCondition auditLogCondition);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	AuditLog findById(String id);
	
	/**
	 * 新增
	 * @param auditLogCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	long insert(AuditLogCondition auditLogCondition);

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	long deleteById(String id);
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	long update(AuditLogCondition auditLogCondition);
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 10:14:46
	 */
	long updateByCriteria(AuditLogCondition auditLogCondition,Criteria criteria);

	/**商户银行卡审核 
	 * @return */
	Map<String, String> auditPlatform(AuditLogCondition auditLogCondition) throws Exception;
	
	
}

