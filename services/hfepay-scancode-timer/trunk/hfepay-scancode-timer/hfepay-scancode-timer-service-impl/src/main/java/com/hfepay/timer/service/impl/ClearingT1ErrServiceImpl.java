/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service.impl;

import java.util.Calendar;
import java.util.Date;
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
import com.hfepay.scancode.commons.condition.ClearingT1ErrCondition;
import com.hfepay.scancode.commons.dao.ClearingT1ErrDAO;
import com.hfepay.scancode.commons.entity.ClearingT1Err;
import com.hfepay.timer.service.ClearingT1ErrService;

@Service("clearingT1ErrService")
public class ClearingT1ErrServiceImpl implements ClearingT1ErrService {
	
	@Autowired
    private ClearingT1ErrDAO clearingT1ErrDAO;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: PagingResult<ClearingT1Err>
	 * @date CreateDate : 2017-03-17 18:04:17
	 */
    @Override
	public PagingResult<ClearingT1Err> findPagingResult(ClearingT1ErrCondition clearingT1ErrCondition){
		CriteriaBuilder cb = Cnd.builder(ClearingT1Err.class);
		if(!Strings.isEmpty(clearingT1ErrCondition.getId())){
			cb.andEQ("id", clearingT1ErrCondition.getId());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getBatchNo())){
			cb.andEQ("batchNo", clearingT1ErrCondition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT1ErrCondition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT1ErrCondition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT1ErrCondition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getChannelNo())){
			cb.andEQ("channelNo", clearingT1ErrCondition.getChannelNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getAgentNo())){
			cb.andEQ("agentNo", clearingT1ErrCondition.getAgentNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT1ErrCondition.getMerchantNo());
		}
		if(null != clearingT1ErrCondition.getTransAmt()){
			cb.andEQ("transAmt", clearingT1ErrCondition.getTransAmt());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeType())){
			cb.andEQ("tradeType", clearingT1ErrCondition.getTradeType());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getPayCode())){
			cb.andEQ("payCode", clearingT1ErrCondition.getPayCode());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getCheckFlag())){
			cb.andEQ("checkFlag", clearingT1ErrCondition.getCheckFlag());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getProcessingStatus())){
			cb.andEQ("processingStatus", clearingT1ErrCondition.getProcessingStatus());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getClearDate())){
			cb.andEQ("clearDate", clearingT1ErrCondition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT1ErrCondition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTemp1())){
			cb.andEQ("temp1", clearingT1ErrCondition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTemp2())){
			cb.andEQ("temp2", clearingT1ErrCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(clearingT1ErrCondition.getFirst()), Long.valueOf(clearingT1ErrCondition.getLast()));
		return clearingT1ErrDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: List<ClearingT1Err>
	 * @date CreateDate : 2017-03-17 18:04:17
	 */
	@Override
	public List<ClearingT1Err> findAll(ClearingT1ErrCondition clearingT1ErrCondition){
		CriteriaBuilder cb = Cnd.builder(ClearingT1Err.class);
		if(!Strings.isEmpty(clearingT1ErrCondition.getId())){
			cb.andEQ("id", clearingT1ErrCondition.getId());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getBatchNo())){
			cb.andEQ("batchNo", clearingT1ErrCondition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT1ErrCondition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT1ErrCondition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT1ErrCondition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getChannelNo())){
			cb.andEQ("channelNo", clearingT1ErrCondition.getChannelNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getAgentNo())){
			cb.andEQ("agentNo", clearingT1ErrCondition.getAgentNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT1ErrCondition.getMerchantNo());
		}
		if(null != clearingT1ErrCondition.getTransAmt()){
			cb.andEQ("transAmt", clearingT1ErrCondition.getTransAmt());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeType())){
			cb.andEQ("tradeType", clearingT1ErrCondition.getTradeType());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getPayCode())){
			cb.andEQ("payCode", clearingT1ErrCondition.getPayCode());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getCheckFlag())){
			cb.andEQ("checkFlag", clearingT1ErrCondition.getCheckFlag());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getProcessingStatus())){
			cb.andEQ("processingStatus", clearingT1ErrCondition.getProcessingStatus());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getClearDate())){
			cb.andEQ("clearDate", clearingT1ErrCondition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT1ErrCondition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTemp1())){
			cb.andEQ("temp1", clearingT1ErrCondition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTemp2())){
			cb.andEQ("temp2", clearingT1ErrCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return clearingT1ErrDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT1Err
	 * @date CreateDate : 2017-03-17 18:04:17
	 */
	@Override
	public ClearingT1Err findById(String id){
		return clearingT1ErrDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-17 18:04:17
	 */
	@Override
	public long insert(ClearingT1ErrCondition clearingT1ErrCondition){
		ClearingT1Err clearingT1Err = new ClearingT1Err();
		BeanUtils.copyProperties(clearingT1ErrCondition, clearingT1Err);
		return clearingT1ErrDAO.insert(clearingT1Err);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-03-17 18:04:17
	 */
	@Override
	public long deleteById(String id){
		return clearingT1ErrDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-17 18:04:17
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return clearingT1ErrDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-17 18:04:17
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return clearingT1ErrDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-17 18:04:17
	 */
	@Override
	public long update(ClearingT1ErrCondition clearingT1ErrCondition){
		ClearingT1Err clearingT1Err = new ClearingT1Err();
		BeanUtils.copyProperties(clearingT1ErrCondition, clearingT1Err);
		return clearingT1ErrDAO.update(clearingT1Err);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-17 18:04:17
	 */
	@Override
	public long updateByCriteria(ClearingT1ErrCondition clearingT1ErrCondition,Criteria criteria){
		ClearingT1Err clearingT1Err = new ClearingT1Err();
		BeanUtils.copyProperties(clearingT1ErrCondition, clearingT1Err);
		return clearingT1ErrDAO.updateByCriteria(clearingT1Err,criteria);
	}
	
	/** t1对账数据对比 */
	@Override
	public List<ClearingT1Err> checkT1Data(){
		Map<String, Object> map = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);		
		String tradeDate = Dates.format(Dates.DF.yyyyMMdd, cal.getTime());
		String beginTimeStr = Dates.format(Dates.DF.yyyy_MM_dd,cal.getTime() );
		String endTimeStr = Dates.format(Dates.DF.yyyy_MM_dd,cal.getTime() ) + " 23:59:59";
		map.put("tradeDate", tradeDate);
		map.put("beginTimeStr", beginTimeStr);
		map.put("endTimeStr", endTimeStr);
		return clearingT1ErrDAO.checkT1Data(map);
	}

	/**
	 * @Title: batchInsert
	 * @Description: 批量新增
	 * @author: Ricky
	 * @param t1Err
	 * @return: void
	 * @date CreateDate : 2017-02-09 16:20:29
	 */
	@Override
	public void batchInsert(List<ClearingT1Err> t1ErrList){
		clearingT1ErrDAO.batchInsert(t1ErrList);
	}
	
}

