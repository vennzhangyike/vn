/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.ClearingT0ErrCondition;
import com.hfepay.scancode.commons.dao.ClearingT0ErrDAO;
import com.hfepay.scancode.commons.entity.ClearingT0Err;
import com.hfepay.timer.service.ClearingT0ErrService;

@Service("clearingT0ErrService")
public class ClearingT0ErrServiceImpl implements ClearingT0ErrService {
	
	@Autowired
    private ClearingT0ErrDAO clearingT0ErrDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: PagingResult<ClearingT0Err>
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
    @Override
	public PagingResult<ClearingT0Err> findPagingResult(ClearingT0ErrCondition clearingT0ErrCondition){
		CriteriaBuilder cb = Cnd.builder(ClearingT0Err.class);
		if(!Strings.isEmpty(clearingT0ErrCondition.getId())){
			cb.andEQ("id", clearingT0ErrCondition.getId());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getBatchNo())){
			cb.andEQ("batchNo", clearingT0ErrCondition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT0ErrCondition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT0ErrCondition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT0ErrCondition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT0ErrCondition.getMerchantNo());
		}
		if(null != clearingT0ErrCondition.getTransAmt()){
			cb.andEQ("transAmt", clearingT0ErrCondition.getTransAmt());
		}
		if(null != clearingT0ErrCondition.getArrivalAmt()){
			cb.andEQ("arrivalAmt", clearingT0ErrCondition.getArrivalAmt());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getCheckFlag())){
			cb.andEQ("checkFlag", clearingT0ErrCondition.getCheckFlag());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getProcessingStatus())){
			cb.andEQ("processingStatus", clearingT0ErrCondition.getProcessingStatus());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getClearDate())){
			cb.andEQ("clearDate", clearingT0ErrCondition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT0ErrCondition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTemp1())){
			cb.andEQ("temp1", clearingT0ErrCondition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTemp2())){
			cb.andEQ("temp2", clearingT0ErrCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(clearingT0ErrCondition.getFirst()), Long.valueOf(clearingT0ErrCondition.getLast()));
		return clearingT0ErrDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: List<ClearingT0Err>
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	@Override
	public List<ClearingT0Err> findAll(ClearingT0ErrCondition clearingT0ErrCondition){
		CriteriaBuilder cb = Cnd.builder(ClearingT0Err.class);
		if(!Strings.isEmpty(clearingT0ErrCondition.getId())){
			cb.andEQ("id", clearingT0ErrCondition.getId());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getBatchNo())){
			cb.andEQ("batchNo", clearingT0ErrCondition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT0ErrCondition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT0ErrCondition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT0ErrCondition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT0ErrCondition.getMerchantNo());
		}
		if(null != clearingT0ErrCondition.getTransAmt()){
			cb.andEQ("transAmt", clearingT0ErrCondition.getTransAmt());
		}
		if(null != clearingT0ErrCondition.getArrivalAmt()){
			cb.andEQ("arrivalAmt", clearingT0ErrCondition.getArrivalAmt());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getCheckFlag())){
			cb.andEQ("checkFlag", clearingT0ErrCondition.getCheckFlag());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getProcessingStatus())){
			cb.andEQ("processingStatus", clearingT0ErrCondition.getProcessingStatus());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getClearDate())){
			cb.andEQ("clearDate", clearingT0ErrCondition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT0ErrCondition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTemp1())){
			cb.andEQ("temp1", clearingT0ErrCondition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTemp2())){
			cb.andEQ("temp2", clearingT0ErrCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return clearingT0ErrDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT0Err
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	@Override
	public ClearingT0Err findById(String id){
		return clearingT0ErrDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	@Override
	public long insert(ClearingT0ErrCondition clearingT0ErrCondition){
		ClearingT0Err clearingT0Err = new ClearingT0Err();
		BeanUtils.copyProperties(clearingT0ErrCondition, clearingT0Err);
		return clearingT0ErrDAO.insert(clearingT0Err);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	@Override
	public long deleteById(String id){
		return clearingT0ErrDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return clearingT0ErrDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return clearingT0ErrDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	@Override
	public long update(ClearingT0ErrCondition clearingT0ErrCondition){
		ClearingT0Err clearingT0Err = new ClearingT0Err();
		BeanUtils.copyProperties(clearingT0ErrCondition, clearingT0Err);
		return clearingT0ErrDAO.update(clearingT0Err);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 19:49:25
	 */
	@Override
	public long updateByCriteria(ClearingT0ErrCondition clearingT0ErrCondition,Criteria criteria){
		ClearingT0Err clearingT0Err = new ClearingT0Err();
		BeanUtils.copyProperties(clearingT0ErrCondition, clearingT0Err);
		return clearingT0ErrDAO.updateByCriteria(clearingT0Err,criteria);
	}
	
	/** t0对账数据对比 */
	@Override
	public List<ClearingT0Err> checkT0Data(){
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);		
		String tradeDate = Dates.format(Dates.DF.yyyyMMdd, cal.getTime());
		String beginTimeStr = Dates.format(Dates.DF.yyyy_MM_dd,cal.getTime() );
		String endTimeStr = Dates.format(Dates.DF.yyyy_MM_dd,cal.getTime() ) + " 23:59:59";
		map.put("tradeDate", tradeDate);
		map.put("beginTimeStr", beginTimeStr);
		map.put("endTimeStr", endTimeStr);
		return clearingT0ErrDAO.checkT0Data(map);
	}

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t0Err
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public void batchInsert(List<ClearingT0Err> t0ErrList){
		clearingT0ErrDAO.batchInsert(t0ErrList);
	}
	
}

