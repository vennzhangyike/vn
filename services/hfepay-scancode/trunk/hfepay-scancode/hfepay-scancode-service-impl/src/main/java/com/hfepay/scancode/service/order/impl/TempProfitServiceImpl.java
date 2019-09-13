/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.order.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.TempProfitCondition;
import com.hfepay.scancode.commons.dao.TempProfitDAO;
import com.hfepay.scancode.commons.entity.TempProfit;
import com.hfepay.scancode.service.order.TempProfitService;

@Service("tempProfitService")
public class TempProfitServiceImpl implements TempProfitService {
	
	@Autowired
    private TempProfitDAO tempProfitDAO;
    
    /**
	 * 列表(分页)
	 * @param tempProfitCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
    @Override
	public PagingResult<TempProfit> findPagingResult(TempProfitCondition tempProfitCondition){
		CriteriaBuilder cb = Cnd.builder(TempProfit.class);
		if(!Strings.isEmpty(tempProfitCondition.getId())){
			cb.andEQ("id", tempProfitCondition.getId());
		}
		if(!Strings.isEmpty(tempProfitCondition.getIdentityNo())){
			cb.andEQ("identityNo", tempProfitCondition.getIdentityNo());
		}
		if(null != tempProfitCondition.getProfit()){
			cb.andEQ("profit", tempProfitCondition.getProfit());
		}
		if(!Strings.isEmpty(tempProfitCondition.getPayCode())){
			cb.andEQ("payCode", tempProfitCondition.getPayCode());
		}
		if(null != tempProfitCondition.getTradeType()){
			cb.andEQ("tradeType", tempProfitCondition.getTradeType());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(tempProfitCondition.getFirst()), Long.valueOf(tempProfitCondition.getLast()));
		return tempProfitDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param tempProfit 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	@Override
	public List<TempProfit> findAll(TempProfitCondition tempProfitCondition){
		CriteriaBuilder cb = Cnd.builder(TempProfit.class);
		if(!Strings.isEmpty(tempProfitCondition.getId())){
			cb.andEQ("id", tempProfitCondition.getId());
		}
		if(!Strings.isEmpty(tempProfitCondition.getIdentityNo())){
			cb.andEQ("identityNo", tempProfitCondition.getIdentityNo());
		}
		if(null != tempProfitCondition.getProfit()){
			cb.andEQ("profit", tempProfitCondition.getProfit());
		}
		if(!Strings.isEmpty(tempProfitCondition.getPayCode())){
			cb.andEQ("payCode", tempProfitCondition.getPayCode());
		}
		if(null != tempProfitCondition.getTradeType()){
			cb.andEQ("tradeType", tempProfitCondition.getTradeType());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return tempProfitDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	@Override
	public TempProfit findById(String id){
		return tempProfitDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param tempProfitCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	@Override
	public long insert(TempProfitCondition tempProfitCondition){
		TempProfit tempProfit = new TempProfit();
		BeanUtils.copyProperties(tempProfitCondition, tempProfit);
		if(Strings.isEmpty(tempProfitCondition.getId())){
			tempProfit.setId(Strings.getUUID());
		}
		return tempProfitDAO.insert(tempProfit);
	}

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	@Override
	public long deleteById(String id){
		return tempProfitDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return tempProfitDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return tempProfitDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	@Override
	public long update(TempProfitCondition tempProfitCondition){
		TempProfit tempProfit = new TempProfit();
		BeanUtils.copyProperties(tempProfitCondition, tempProfit);
		return tempProfitDAO.update(tempProfit);
	}
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	@Override
	public long updateByCriteria(TempProfitCondition tempProfitCondition,Criteria criteria){
		TempProfit tempProfit = new TempProfit();
		BeanUtils.copyProperties(tempProfitCondition, tempProfit);
		return tempProfitDAO.updateByCriteria(tempProfit,criteria);
	}
	
	/**
	 * 单个实体对象
	 * @param tempProfitCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-01 15:05:27
	 */
	@Override
	public TempProfit findByCondition(TempProfitCondition tempProfitCondition){
		CriteriaBuilder cb = Cnd.builder(TempProfit.class);
		if(!Strings.isEmpty(tempProfitCondition.getId())){
			cb.andEQ("id", tempProfitCondition.getId());
		}
		if(!Strings.isEmpty(tempProfitCondition.getIdentityNo())){
			cb.andEQ("identityNo", tempProfitCondition.getIdentityNo());
		}
		if(null != tempProfitCondition.getProfit()){
			cb.andEQ("profit", tempProfitCondition.getProfit());
		}
		if(!Strings.isEmpty(tempProfitCondition.getPayCode())){
			cb.andEQ("payCode", tempProfitCondition.getPayCode());
		}
		if(null != tempProfitCondition.getTradeType()){
			cb.andEQ("tradeType", tempProfitCondition.getTradeType());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return tempProfitDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public void insertBatch(List<TempProfit> listTemp) {
		// TODO Auto-generated method stub
		tempProfitDAO.insertBatch(listTemp);
	}
	
	@Override
	public List<TempProfit> getTotalProfitByIdentity() {
		// TODO Auto-generated method stub
		return tempProfitDAO.getTotalProfitByIdentity();
	}
	
	@Override
	public List<TempProfit> getTotalProfit() {
		// TODO Auto-generated method stub
		return tempProfitDAO.getTotalProfit();
	}
	
	@Override
	public void clearTempData() {
		// TODO Auto-generated method stub
		tempProfitDAO.clearTempData();
	}
	
	@Override
	public List<TempProfit> getWithDrawType() {
		// TODO Auto-generated method stub
		return tempProfitDAO.getWithDrawType();
	}
}

