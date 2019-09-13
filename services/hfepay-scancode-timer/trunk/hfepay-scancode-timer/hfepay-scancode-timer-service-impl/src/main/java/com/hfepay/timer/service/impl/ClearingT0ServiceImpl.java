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
import com.hfepay.scancode.commons.condition.ClearingT0Condition;
import com.hfepay.scancode.commons.dao.ClearingT0DAO;
import com.hfepay.scancode.commons.entity.ClearingT0;
import com.hfepay.timer.service.ClearingT0Service;

@Service("clearingT0Service")
public class ClearingT0ServiceImpl implements ClearingT0Service {
	
	@Autowired
    private ClearingT0DAO clearingT0DAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param clearingT0Condition
	 * @return: PagingResult<ClearingT0>
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
    @Override
	public PagingResult<ClearingT0> findPagingResult(ClearingT0Condition clearingT0Condition){
		CriteriaBuilder cb = Cnd.builder(ClearingT0.class);
		if(!Strings.isEmpty(clearingT0Condition.getId())){
			cb.andEQ("id", clearingT0Condition.getId());
		}
		if(!Strings.isEmpty(clearingT0Condition.getBatchNo())){
			cb.andEQ("batchNo", clearingT0Condition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT0Condition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT0Condition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT0Condition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT0Condition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT0Condition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT0Condition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT0Condition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT0Condition.getMerchantNo());
		}
		if(null != clearingT0Condition.getTransAmt()){
			cb.andEQ("transAmt", clearingT0Condition.getTransAmt());
		}
		if(null != clearingT0Condition.getArrivalAmt()){
			cb.andEQ("arrivalAmt", clearingT0Condition.getArrivalAmt());
		}
		if(null != clearingT0Condition.getFees()){
			cb.andEQ("fees", clearingT0Condition.getFees());
		}
		if(null != clearingT0Condition.getSingleFees()){
			cb.andEQ("singleFees", clearingT0Condition.getSingleFees());
		}
		if(null != clearingT0Condition.getChannelSingleFees()){
			cb.andEQ("channelSingleFees", clearingT0Condition.getChannelSingleFees());
		}
		if(!Strings.isEmpty(clearingT0Condition.getClearDate())){
			cb.andEQ("clearDate", clearingT0Condition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT0Condition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT0Condition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT0Condition.getTemp1())){
			cb.andEQ("temp1", clearingT0Condition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT0Condition.getTemp2())){
			cb.andEQ("temp2", clearingT0Condition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(clearingT0Condition.getFirst()), Long.valueOf(clearingT0Condition.getLast()));
		return clearingT0DAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT0Condition
	 * @return: List<ClearingT0>
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	@Override
	public List<ClearingT0> findAll(ClearingT0Condition clearingT0Condition){
		CriteriaBuilder cb = Cnd.builder(ClearingT0.class);
		if(!Strings.isEmpty(clearingT0Condition.getId())){
			cb.andEQ("id", clearingT0Condition.getId());
		}
		if(!Strings.isEmpty(clearingT0Condition.getBatchNo())){
			cb.andEQ("batchNo", clearingT0Condition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT0Condition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT0Condition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT0Condition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT0Condition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT0Condition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT0Condition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT0Condition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT0Condition.getMerchantNo());
		}
		if(null != clearingT0Condition.getTransAmt()){
			cb.andEQ("transAmt", clearingT0Condition.getTransAmt());
		}
		if(null != clearingT0Condition.getArrivalAmt()){
			cb.andEQ("arrivalAmt", clearingT0Condition.getArrivalAmt());
		}
		if(null != clearingT0Condition.getFees()){
			cb.andEQ("fees", clearingT0Condition.getFees());
		}
		if(null != clearingT0Condition.getSingleFees()){
			cb.andEQ("singleFees", clearingT0Condition.getSingleFees());
		}
		if(null != clearingT0Condition.getChannelSingleFees()){
			cb.andEQ("channelSingleFees", clearingT0Condition.getChannelSingleFees());
		}
		if(!Strings.isEmpty(clearingT0Condition.getClearDate())){
			cb.andEQ("clearDate", clearingT0Condition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT0Condition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT0Condition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT0Condition.getTemp1())){
			cb.andEQ("temp1", clearingT0Condition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT0Condition.getTemp2())){
			cb.andEQ("temp2", clearingT0Condition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return clearingT0DAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT0
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	@Override
	public ClearingT0 findById(String id){
		return clearingT0DAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param clearingT0Condition
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	@Override
	public long insert(ClearingT0Condition clearingT0Condition){
		ClearingT0 clearingT0 = new ClearingT0();
		BeanUtils.copyProperties(clearingT0Condition, clearingT0);
		return clearingT0DAO.insert(clearingT0);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	@Override
	public long deleteById(String id){
		return clearingT0DAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return clearingT0DAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return clearingT0DAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT0Condition
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	@Override
	public long update(ClearingT0Condition clearingT0Condition){
		ClearingT0 clearingT0 = new ClearingT0();
		BeanUtils.copyProperties(clearingT0Condition, clearingT0);
		return clearingT0DAO.update(clearingT0);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT0Condition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:07:10
	 */
	@Override
	public long updateByCriteria(ClearingT0Condition clearingT0Condition,Criteria criteria){
		ClearingT0 clearingT0 = new ClearingT0();
		BeanUtils.copyProperties(clearingT0Condition, clearingT0);
		return clearingT0DAO.updateByCriteria(clearingT0,criteria);
	}

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t0List
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public void batchInsert(List<ClearingT0> t0List) {
		clearingT0DAO.batchInsert(t0List);
		
	}
	
}

