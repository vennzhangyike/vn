/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator;

import java.util.List;

import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.QrcodeAssignedLogCondition;
import com.hfepay.scancode.commons.entity.QrcodeAssignedLog;

public interface QrcodeAssignedLogService {
	
	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param qrcodeAssignedLogCondition
	 * @return: PagingResult<QrcodeAssignedLog>
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	public PagingResult<QrcodeAssignedLog> findPagingResult(QrcodeAssignedLogCondition qrcodeAssignedLogCondition);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param qrcodeAssignedLogCondition
	 * @return: List<QrcodeAssignedLog>
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	public List<QrcodeAssignedLog> findAll(QrcodeAssignedLogCondition qrcodeAssignedLogCondition);
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: QrcodeAssignedLog
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	QrcodeAssignedLog findById(String id);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param qrcodeAssignedLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	long insert(QrcodeAssignedLogCondition qrcodeAssignedLogCondition);

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	long deleteById(String id);
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	long deleteByCriteria(Criteria criteria);
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	long countByCriteria(Criteria criteria);
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param qrcodeAssignedLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	long update(QrcodeAssignedLogCondition qrcodeAssignedLogCondition);
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param qrcodeAssignedLogCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	long updateByCriteria(QrcodeAssignedLogCondition qrcodeAssignedLogCondition,Criteria criteria);
	
	
}

