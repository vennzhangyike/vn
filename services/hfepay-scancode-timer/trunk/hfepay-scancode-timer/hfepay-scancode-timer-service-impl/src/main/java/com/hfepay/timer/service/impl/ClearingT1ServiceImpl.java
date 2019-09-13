/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ClearingT1Condition;
import com.hfepay.scancode.commons.dao.ClearingT1DAO;
import com.hfepay.scancode.commons.entity.ClearingT1;
import com.hfepay.timer.service.ClearingT1Service;

@Service("clearingT1Service")
public class ClearingT1ServiceImpl implements ClearingT1Service {
	
	@Autowired
    private ClearingT1DAO clearingT1DAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param clearingT1Condition
	 * @return: PagingResult<ClearingT1>
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
    @Override
	public PagingResult<ClearingT1> findPagingResult(ClearingT1Condition clearingT1Condition){
		CriteriaBuilder cb = Cnd.builder(ClearingT1.class);
		if(!Strings.isEmpty(clearingT1Condition.getId())){
			cb.andEQ("id", clearingT1Condition.getId());
		}
		if(!Strings.isEmpty(clearingT1Condition.getBatchNo())){
			cb.andEQ("batchNo", clearingT1Condition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT1Condition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT1Condition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT1Condition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT1Condition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT1Condition.getClearDate())){
			cb.andEQ("clearDate", clearingT1Condition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT1Condition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT1Condition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT1Condition.getTradeType())){
			cb.andEQ("tradeType", clearingT1Condition.getTradeType());
		}
		if(!Strings.isEmpty(clearingT1Condition.getPayCode())){
			cb.andEQ("payCode", clearingT1Condition.getPayCode());
		}
		if(!Strings.isEmpty(clearingT1Condition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT1Condition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT1Condition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT1Condition.getMerchantNo());
		}
		if(null != clearingT1Condition.getTransAmt()){
			cb.andEQ("transAmt", clearingT1Condition.getTransAmt());
		}
		if(null != clearingT1Condition.getMerchantFees()){
			cb.andEQ("merchantFees", clearingT1Condition.getMerchantFees());
		}
		if(null != clearingT1Condition.getChannelFees()){
			cb.andEQ("channelFees", clearingT1Condition.getChannelFees());
		}
		if(!Strings.isEmpty(clearingT1Condition.getAccountType())){
			cb.andEQ("accountType", clearingT1Condition.getAccountType());
		}
		if(!Strings.isEmpty(clearingT1Condition.getTemp1())){
			cb.andEQ("temp1", clearingT1Condition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT1Condition.getTemp2())){
			cb.andEQ("temp2", clearingT1Condition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(clearingT1Condition.getFirst()), Long.valueOf(clearingT1Condition.getLast()));
		return clearingT1DAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT1Condition
	 * @return: List<ClearingT1>
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public List<ClearingT1> findAll(ClearingT1Condition clearingT1Condition){
		CriteriaBuilder cb = Cnd.builder(ClearingT1.class);
		if(!Strings.isEmpty(clearingT1Condition.getId())){
			cb.andEQ("id", clearingT1Condition.getId());
		}
		if(!Strings.isEmpty(clearingT1Condition.getBatchNo())){
			cb.andEQ("batchNo", clearingT1Condition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT1Condition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT1Condition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT1Condition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT1Condition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT1Condition.getClearDate())){
			cb.andEQ("clearDate", clearingT1Condition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT1Condition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT1Condition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT1Condition.getTradeType())){
			cb.andEQ("tradeType", clearingT1Condition.getTradeType());
		}
		if(!Strings.isEmpty(clearingT1Condition.getPayCode())){
			cb.andEQ("payCode", clearingT1Condition.getPayCode());
		}
		if(!Strings.isEmpty(clearingT1Condition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT1Condition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT1Condition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT1Condition.getMerchantNo());
		}
		if(null != clearingT1Condition.getTransAmt()){
			cb.andEQ("transAmt", clearingT1Condition.getTransAmt());
		}
		if(null != clearingT1Condition.getMerchantFees()){
			cb.andEQ("merchantFees", clearingT1Condition.getMerchantFees());
		}
		if(null != clearingT1Condition.getChannelFees()){
			cb.andEQ("channelFees", clearingT1Condition.getChannelFees());
		}
		if(!Strings.isEmpty(clearingT1Condition.getAccountType())){
			cb.andEQ("accountType", clearingT1Condition.getAccountType());
		}
		if(!Strings.isEmpty(clearingT1Condition.getTemp1())){
			cb.andEQ("temp1", clearingT1Condition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT1Condition.getTemp2())){
			cb.andEQ("temp2", clearingT1Condition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return clearingT1DAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT1
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public ClearingT1 findById(String id){
		return clearingT1DAO.get(id);
	}
	
	/* (non-Javadoc)
	 * @see com.hfepay.timer.service.impl.ClearingT1Service#insert(com.hfepay.scancode.commons.condition.ClearingT1Condition)
	 */
	@Override
	public long insert(ClearingT1Condition clearingT1Condition){
		ClearingT1 clearingT1 = new ClearingT1();
		BeanUtils.copyProperties(clearingT1Condition, clearingT1);
		return clearingT1DAO.insert(clearingT1);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public long deleteById(String id){
		return clearingT1DAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return clearingT1DAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return clearingT1DAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT1Condition
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public long update(ClearingT1Condition clearingT1Condition){
		ClearingT1 clearingT1 = new ClearingT1();
		BeanUtils.copyProperties(clearingT1Condition, clearingT1);
		return clearingT1DAO.update(clearingT1);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT1Condition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public long updateByCriteria(ClearingT1Condition clearingT1Condition,Criteria criteria){
		ClearingT1 clearingT1 = new ClearingT1();
		BeanUtils.copyProperties(clearingT1Condition, clearingT1);
		return clearingT1DAO.updateByCriteria(clearingT1,criteria);
	}
	
	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t1List
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public void batchInsert(List<ClearingT1> t1List){
		clearingT1DAO.batchInsert(t1List);
	}
	
}

