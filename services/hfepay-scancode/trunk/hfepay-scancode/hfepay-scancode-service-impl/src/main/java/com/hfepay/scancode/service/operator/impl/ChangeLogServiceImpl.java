/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.dao.ChangeLogDAO;
import com.hfepay.scancode.commons.entity.ChangeLog;
import com.hfepay.scancode.service.operator.ChangeLogService;

@Service("changeLogService")
public class ChangeLogServiceImpl implements ChangeLogService {
	
	@Autowired
    private ChangeLogDAO changeLogDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param changeLogCondition
	 * @return: PagingResult<ChangeLog>
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
    @Override
	public PagingResult<ChangeLog> findPagingResult(ChangeLogCondition changeLogCondition){
		CriteriaBuilder cb = Cnd.builder(ChangeLog.class);
		if(!Strings.isEmpty(changeLogCondition.getId())){
			cb.andEQ("id", changeLogCondition.getId());
		}
		if(!Strings.isEmpty(changeLogCondition.getTradeNo())){
			cb.andEQ("tradeNo", changeLogCondition.getTradeNo());
		}
		if(!Strings.isEmpty(changeLogCondition.getBatchNo())){
			cb.andEQ("batchNo", changeLogCondition.getBatchNo());
		}
		if(!Strings.isEmpty(changeLogCondition.getTransCode())){
			cb.andEQ("transCode", changeLogCondition.getTransCode());
		}
		if(!Strings.isEmpty(changeLogCondition.getTransName())){
			cb.andEQ("transName", changeLogCondition.getTransName());
		}
		if(!Strings.isEmpty(changeLogCondition.getOwnersNo())){
			cb.andEQ("ownersNo", changeLogCondition.getOwnersNo());
		}
		if(!Strings.isEmpty(changeLogCondition.getBefore())){
			cb.andEQ("before", changeLogCondition.getBefore());
		}
		if(!Strings.isEmpty(changeLogCondition.getAfter())){
			cb.andEQ("after", changeLogCondition.getAfter());
		}
		if(!Strings.isEmpty(changeLogCondition.getStatus())){
			cb.andEQ("status", changeLogCondition.getStatus());
		}
		if(!Strings.isEmpty(changeLogCondition.getOperator())){
			cb.andEQ("operator", changeLogCondition.getOperator());
		}
		if(null != changeLogCondition.getCreateTime()){
			cb.andEQ("createTime", changeLogCondition.getCreateTime());
		}

		if(!Strings.isEmpty(changeLogCondition.getRemark())){
			cb.andLike("remark", changeLogCondition.getRemark());
		}
		if(!Strings.isEmpty(changeLogCondition.getTemp1())){
			cb.andEQ("temp1", changeLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(changeLogCondition.getTemp2())){
			cb.andEQ("temp2", changeLogCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(changeLogCondition.getFirst()), Long.valueOf(changeLogCondition.getLast()));
		return changeLogDAO.findPagingResult(buildCriteria);
	}
    
    /**
	 * @Title: findAuditMerchantBankcard
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantBankcardCondition
	 * @return: PagingResult<ChangeLog>
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
    @Override
	public PagingResult<ChangeLog> findAuditMerchantBankcard(MerchantBankcardCondition merchantBankcardCondition){
		CriteriaBuilder cb = Cnd.builder(ChangeLog.class);
		if(!Strings.isEmpty(merchantBankcardCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantBankcardCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantBankcardCondition.getMerchantName())){
			cb.andLike("merchantName", merchantBankcardCondition.getMerchantName());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantBankcardCondition.getFirst()), Long.valueOf(merchantBankcardCondition.getLast()));
		return changeLogDAO.findAuditMerchantBankcard(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param changeLogCondition
	 * @return: List<ChangeLog>
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public List<ChangeLog> findAll(ChangeLogCondition changeLogCondition){
		CriteriaBuilder cb = Cnd.builder(ChangeLog.class);
		if(!Strings.isEmpty(changeLogCondition.getId())){
			cb.andEQ("id", changeLogCondition.getId());
		}
		if(!Strings.isEmpty(changeLogCondition.getTradeNo())){
			cb.andEQ("tradeNo", changeLogCondition.getTradeNo());
		}
		if(!Strings.isEmpty(changeLogCondition.getBatchNo())){
			cb.andEQ("batchNo", changeLogCondition.getBatchNo());
		}
		if(!Strings.isEmpty(changeLogCondition.getTransCode())){
			cb.andEQ("transCode", changeLogCondition.getTransCode());
		}
		if(!Strings.isEmpty(changeLogCondition.getTransName())){
			cb.andEQ("transName", changeLogCondition.getTransName());
		}
		if(!Strings.isEmpty(changeLogCondition.getOwnersNo())){
			cb.andEQ("ownersNo", changeLogCondition.getOwnersNo());
		}
		if(!Strings.isEmpty(changeLogCondition.getBefore())){
			cb.andEQ("before", changeLogCondition.getBefore());
		}
		if(!Strings.isEmpty(changeLogCondition.getAfter())){
			cb.andEQ("after", changeLogCondition.getAfter());
		}
		if(!Strings.isEmpty(changeLogCondition.getStatus())){
			cb.andEQ("status", changeLogCondition.getStatus());
		}
		if(!Strings.isEmpty(changeLogCondition.getOperator())){
			cb.andEQ("operator", changeLogCondition.getOperator());
		}
		if(null != changeLogCondition.getCreateTime()){
			cb.andEQ("createTime", changeLogCondition.getCreateTime());
		}

		if(!Strings.isEmpty(changeLogCondition.getRemark())){
			cb.andLike("remark", changeLogCondition.getRemark());
		}
		if(!Strings.isEmpty(changeLogCondition.getTemp1())){
			cb.andEQ("temp1", changeLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(changeLogCondition.getTemp2())){
			cb.andEQ("temp2", changeLogCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return changeLogDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChangeLog
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public ChangeLog findById(String id){
		return changeLogDAO.get(id);
	}
	
	/**
	 * @Title: findByTradeNo
	 * @Description: 商户银行账户主键查找(专用)
	 * @author: Ricky
	 * @param id
	 * @return: ChangeLog
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public ChangeLog findByTradeNo(String id){
		return changeLogDAO.findByTradeNo(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param changeLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public long insert(ChangeLogCondition changeLogCondition){
		ChangeLog changeLog = new ChangeLog();
		BeanUtils.copyProperties(changeLogCondition, changeLog);
		return changeLogDAO.insert(changeLog);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public long deleteById(String id){
		return changeLogDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return changeLogDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return changeLogDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param changeLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public long update(ChangeLogCondition changeLogCondition){
		ChangeLog changeLog = new ChangeLog();
		BeanUtils.copyProperties(changeLogCondition, changeLog);
		return changeLogDAO.update(changeLog);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param changeLogCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public long updateByCriteria(ChangeLogCondition changeLogCondition,Criteria criteria){
		ChangeLog changeLog = new ChangeLog();
		BeanUtils.copyProperties(changeLogCondition, changeLog);
		return changeLogDAO.updateByCriteria(changeLog,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	@Override
	public long updateStatus(String id,String status){
		return changeLogDAO.updateStatus(id,status);
	}	
}

