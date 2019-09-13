/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.QrcodeAssignedLogCondition;
import com.hfepay.scancode.commons.dao.QrcodeAssignedLogDAO;
import com.hfepay.scancode.commons.entity.QrcodeAssignedLog;
import com.hfepay.scancode.service.operator.QrcodeAssignedLogService;

@Service("qrcodeAssignedLogService")
public class QrcodeAssignedLogServiceImpl implements QrcodeAssignedLogService {
	
	@Autowired
    private QrcodeAssignedLogDAO qrcodeAssignedLogDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param qrcodeAssignedLogCondition
	 * @return: PagingResult<QrcodeAssignedLog>
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
    @Override
	public PagingResult<QrcodeAssignedLog> findPagingResult(QrcodeAssignedLogCondition qrcodeAssignedLogCondition){
		CriteriaBuilder cb = Cnd.builder(QrcodeAssignedLog.class);
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getId())){
			cb.andEQ("id", qrcodeAssignedLogCondition.getId());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getAssignment())){
			cb.andEQ("assignment", qrcodeAssignedLogCondition.getAssignment());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getRecipients())){
			cb.andEQ("recipients", qrcodeAssignedLogCondition.getRecipients());
		}
		if(null != qrcodeAssignedLogCondition.getQrNumber()){
			cb.andEQ("qrNumber", qrcodeAssignedLogCondition.getQrNumber());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getAssignedType())){
			cb.andEQ("assignedType", qrcodeAssignedLogCondition.getAssignedType());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getReason())){
			cb.andEQ("reason", qrcodeAssignedLogCondition.getReason());
		}
		if(null != qrcodeAssignedLogCondition.getCreateTime()){
			cb.andEQ("createTime", qrcodeAssignedLogCondition.getCreateTime());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getOperator())){
			cb.andEQ("operator", qrcodeAssignedLogCondition.getOperator());
		}

		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getRemark())){
			cb.andLike("remark", qrcodeAssignedLogCondition.getRemark());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getTemp1())){
			cb.andEQ("temp1", qrcodeAssignedLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getTemp2())){
			cb.andEQ("temp2", qrcodeAssignedLogCondition.getTemp2());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getTemp3())){
			cb.andEQ("temp3", qrcodeAssignedLogCondition.getTemp3());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getTemp4())){
			cb.andEQ("temp4", qrcodeAssignedLogCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(qrcodeAssignedLogCondition.getFirst()), Long.valueOf(qrcodeAssignedLogCondition.getLast()));
		return qrcodeAssignedLogDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param qrcodeAssignedLogCondition
	 * @return: List<QrcodeAssignedLog>
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	@Override
	public List<QrcodeAssignedLog> findAll(QrcodeAssignedLogCondition qrcodeAssignedLogCondition){
		CriteriaBuilder cb = Cnd.builder(QrcodeAssignedLog.class);
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getId())){
			cb.andEQ("id", qrcodeAssignedLogCondition.getId());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getAssignment())){
			cb.andEQ("assignment", qrcodeAssignedLogCondition.getAssignment());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getRecipients())){
			cb.andEQ("recipients", qrcodeAssignedLogCondition.getRecipients());
		}
		if(null != qrcodeAssignedLogCondition.getQrNumber()){
			cb.andEQ("qrNumber", qrcodeAssignedLogCondition.getQrNumber());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getAssignedType())){
			cb.andEQ("assignedType", qrcodeAssignedLogCondition.getAssignedType());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getReason())){
			cb.andEQ("reason", qrcodeAssignedLogCondition.getReason());
		}
		if(null != qrcodeAssignedLogCondition.getCreateTime()){
			cb.andEQ("createTime", qrcodeAssignedLogCondition.getCreateTime());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getOperator())){
			cb.andEQ("operator", qrcodeAssignedLogCondition.getOperator());
		}

		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getRemark())){
			cb.andLike("remark", qrcodeAssignedLogCondition.getRemark());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getTemp1())){
			cb.andEQ("temp1", qrcodeAssignedLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getTemp2())){
			cb.andEQ("temp2", qrcodeAssignedLogCondition.getTemp2());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getTemp3())){
			cb.andEQ("temp3", qrcodeAssignedLogCondition.getTemp3());
		}
		if(!Strings.isEmpty(qrcodeAssignedLogCondition.getTemp4())){
			cb.andEQ("temp4", qrcodeAssignedLogCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return qrcodeAssignedLogDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: QrcodeAssignedLog
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	@Override
	public QrcodeAssignedLog findById(String id){
		return qrcodeAssignedLogDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param qrcodeAssignedLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	@Override
	public long insert(QrcodeAssignedLogCondition qrcodeAssignedLogCondition){
		QrcodeAssignedLog qrcodeAssignedLog = new QrcodeAssignedLog();
		BeanUtils.copyProperties(qrcodeAssignedLogCondition, qrcodeAssignedLog);
		return qrcodeAssignedLogDAO.insert(qrcodeAssignedLog);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	@Override
	public long deleteById(String id){
		return qrcodeAssignedLogDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return qrcodeAssignedLogDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return qrcodeAssignedLogDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param qrcodeAssignedLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	@Override
	public long update(QrcodeAssignedLogCondition qrcodeAssignedLogCondition){
		QrcodeAssignedLog qrcodeAssignedLog = new QrcodeAssignedLog();
		BeanUtils.copyProperties(qrcodeAssignedLogCondition, qrcodeAssignedLog);
		return qrcodeAssignedLogDAO.update(qrcodeAssignedLog);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param qrcodeAssignedLogCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 19:17:16
	 */
	@Override
	public long updateByCriteria(QrcodeAssignedLogCondition qrcodeAssignedLogCondition,Criteria criteria){
		QrcodeAssignedLog qrcodeAssignedLog = new QrcodeAssignedLog();
		BeanUtils.copyProperties(qrcodeAssignedLogCondition, qrcodeAssignedLog);
		return qrcodeAssignedLogDAO.updateByCriteria(qrcodeAssignedLog,criteria);
	}
	
}

