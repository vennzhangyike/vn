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
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.OrganProfitCondition;
import com.hfepay.scancode.commons.dao.OrganProfitDAO;
import com.hfepay.scancode.commons.dto.HierarchicalSettlementTotalDTO;
import com.hfepay.scancode.commons.entity.OrganProfit;
import com.hfepay.scancode.service.operator.OrganProfitService;

@Service("organProfitService")
public class OrganProfitServiceImpl implements OrganProfitService {
	
	@Autowired
    private OrganProfitDAO organProfitDAO;
    
    /**
	 * 列表(分页)
	 * @param organProfitCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
    @Override
	public PagingResult<OrganProfit> findPagingResult(OrganProfitCondition organProfitCondition){
		CriteriaBuilder cb = Cnd.builder(OrganProfit.class);
		if(!Strings.isEmpty(organProfitCondition.getId())){
			cb.andEQ("id", organProfitCondition.getId());
		}
		if(!Strings.isEmpty(organProfitCondition.getOrganNo())){
			cb.andEQ("organNo", organProfitCondition.getOrganNo());
		}
		if(!Strings.isEmpty(organProfitCondition.getOrganLevel())){
			cb.andEQ("organLevel", organProfitCondition.getOrganLevel());
		}
		if(!Strings.isEmpty(organProfitCondition.getPayCode())){
			cb.andEQ("payCode", organProfitCondition.getPayCode());
		}
		if(!Strings.isEmpty(organProfitCondition.getType())){
			cb.andEQ("type", organProfitCondition.getType());
		}
		if(null != organProfitCondition.getAmount()){
			cb.andEQ("amount", organProfitCondition.getAmount());
		}
		if(!Strings.isEmpty(organProfitCondition.getRateType())){
			cb.andEQ("rateType", organProfitCondition.getRateType());
		}
		if(null != organProfitCondition.getRate()){
			cb.andEQ("rate", organProfitCondition.getRate());
		}
		if(null != organProfitCondition.getFixedAmount()){
			cb.andEQ("fixedAmount", organProfitCondition.getFixedAmount());
		}
		if(null != organProfitCondition.getProfitAmount()){
			cb.andEQ("profitAmount", organProfitCondition.getProfitAmount());
		}
		if(null != organProfitCondition.getCostAmount()){
			cb.andEQ("costAmount", organProfitCondition.getCostAmount());
		}
		if(!Strings.isEmpty(organProfitCondition.getSettleStatus())){
			cb.andEQ("settleStatus", organProfitCondition.getSettleStatus());
		}
		if(!Strings.isEmpty(organProfitCondition.getSettleDate())){
			cb.andEQ("settleDate", organProfitCondition.getSettleDate());
		}
		if(!Strings.isEmpty(organProfitCondition.getTransDate())){
			cb.andEQ("transDate", organProfitCondition.getTransDate());
		}
		if(!Strings.isEmpty(organProfitCondition.getTransDateBegin())){
			cb.andGE("transDate", organProfitCondition.getTransDateBegin());
		}
		if(!Strings.isEmpty(organProfitCondition.getTransDateEnd())){
			cb.andLE("transDate", organProfitCondition.getTransDateEnd()  + " 23:59:59");
		}

		if(!Strings.isEmpty(organProfitCondition.getRemark())){
			cb.andLike("remark", organProfitCondition.getRemark());
		}
		if(!Strings.isEmpty(organProfitCondition.getTemp1())){
			cb.andEQ("temp1", organProfitCondition.getTemp1());
		}
		if(!Strings.isEmpty(organProfitCondition.getTemp2())){
			cb.andEQ("temp2", organProfitCondition.getTemp2());
		}
		
		if(!Strings.isEmpty(organProfitCondition.getIdentityFlag())){
			cb.andEQ("identityFlag", organProfitCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(organProfitCondition.getOrgName())){
			cb.andLike("orgName", organProfitCondition.getOrgName());
		}
		
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(organProfitCondition.getFirst()), Long.valueOf(organProfitCondition.getLast()));
		return organProfitDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param organProfit 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	@Override
	public List<OrganProfit> findAll(OrganProfitCondition organProfitCondition){
		CriteriaBuilder cb = Cnd.builder(OrganProfit.class);
		if(!Strings.isEmpty(organProfitCondition.getId())){
			cb.andEQ("id", organProfitCondition.getId());
		}
		if(!Strings.isEmpty(organProfitCondition.getOrganNo())){
			cb.andEQ("organNo", organProfitCondition.getOrganNo());
		}
		if(!Strings.isEmpty(organProfitCondition.getOrganLevel())){
			cb.andEQ("organLevel", organProfitCondition.getOrganLevel());
		}
		if(!Strings.isEmpty(organProfitCondition.getPayCode())){
			cb.andEQ("payCode", organProfitCondition.getPayCode());
		}
		if(!Strings.isEmpty(organProfitCondition.getType())){
			cb.andEQ("type", organProfitCondition.getType());
		}
		if(null != organProfitCondition.getAmount()){
			cb.andEQ("amount", organProfitCondition.getAmount());
		}
		if(!Strings.isEmpty(organProfitCondition.getRateType())){
			cb.andEQ("rateType", organProfitCondition.getRateType());
		}
		if(null != organProfitCondition.getRate()){
			cb.andEQ("rate", organProfitCondition.getRate());
		}
		if(null != organProfitCondition.getFixedAmount()){
			cb.andEQ("fixedAmount", organProfitCondition.getFixedAmount());
		}
		if(null != organProfitCondition.getProfitAmount()){
			cb.andEQ("profitAmount", organProfitCondition.getProfitAmount());
		}
		if(null != organProfitCondition.getCostAmount()){
			cb.andEQ("costAmount", organProfitCondition.getCostAmount());
		}
		if(!Strings.isEmpty(organProfitCondition.getSettleStatus())){
			cb.andEQ("settleStatus", organProfitCondition.getSettleStatus());
		}
		if(!Strings.isEmpty(organProfitCondition.getSettleDate())){
			cb.andEQ("settleDate", organProfitCondition.getSettleDate());
		}
		if(!Strings.isEmpty(organProfitCondition.getTransDate())){
			cb.andEQ("transDate", organProfitCondition.getTransDate());
		}

		if(!Strings.isEmpty(organProfitCondition.getRemark())){
			cb.andLike("remark", organProfitCondition.getRemark());
		}
		if(!Strings.isEmpty(organProfitCondition.getTemp1())){
			cb.andEQ("temp1", organProfitCondition.getTemp1());
		}
		if(!Strings.isEmpty(organProfitCondition.getTemp2())){
			cb.andEQ("temp2", organProfitCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return organProfitDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	@Override
	public OrganProfit findById(String id){
		return organProfitDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param organProfitCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	@Override
	public long insert(OrganProfitCondition organProfitCondition){
		OrganProfit organProfit = new OrganProfit();
		BeanUtils.copyProperties(organProfitCondition, organProfit);
		if(Strings.isEmpty(organProfitCondition.getId())){
			organProfit.setId(Strings.getUUID());
		}
		return organProfitDAO.insert(organProfit);
	}

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	@Override
	public long deleteById(String id){
		return organProfitDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return organProfitDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return organProfitDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	@Override
	public long update(OrganProfitCondition organProfitCondition){
		OrganProfit organProfit = new OrganProfit();
		BeanUtils.copyProperties(organProfitCondition, organProfit);
		return organProfitDAO.update(organProfit);
	}
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	@Override
	public long updateByCriteria(OrganProfitCondition organProfitCondition,Criteria criteria){
		OrganProfit organProfit = new OrganProfit();
		BeanUtils.copyProperties(organProfitCondition, organProfit);
		return organProfitDAO.updateByCriteria(organProfit,criteria);
	}
	
	/**
	 * 单个实体对象
	 * @param organProfitCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-11-30 17:50:55
	 */
	@Override
	public OrganProfit findByCondition(OrganProfitCondition organProfitCondition){
		CriteriaBuilder cb = Cnd.builder(OrganProfit.class);
		if(!Strings.isEmpty(organProfitCondition.getId())){
			cb.andEQ("id", organProfitCondition.getId());
		}
		if(!Strings.isEmpty(organProfitCondition.getOrganNo())){
			cb.andEQ("organNo", organProfitCondition.getOrganNo());
		}
		if(!Strings.isEmpty(organProfitCondition.getOrganLevel())){
			cb.andEQ("organLevel", organProfitCondition.getOrganLevel());
		}
		if(!Strings.isEmpty(organProfitCondition.getPayCode())){
			cb.andEQ("payCode", organProfitCondition.getPayCode());
		}
		if(!Strings.isEmpty(organProfitCondition.getType())){
			cb.andEQ("type", organProfitCondition.getType());
		}
		if(null != organProfitCondition.getAmount()){
			cb.andEQ("amount", organProfitCondition.getAmount());
		}
		if(!Strings.isEmpty(organProfitCondition.getRateType())){
			cb.andEQ("rateType", organProfitCondition.getRateType());
		}
		if(null != organProfitCondition.getRate()){
			cb.andEQ("rate", organProfitCondition.getRate());
		}
		if(null != organProfitCondition.getFixedAmount()){
			cb.andEQ("fixedAmount", organProfitCondition.getFixedAmount());
		}
		if(null != organProfitCondition.getProfitAmount()){
			cb.andEQ("profitAmount", organProfitCondition.getProfitAmount());
		}
		if(null != organProfitCondition.getCostAmount()){
			cb.andEQ("costAmount", organProfitCondition.getCostAmount());
		}
		if(!Strings.isEmpty(organProfitCondition.getSettleStatus())){
			cb.andEQ("settleStatus", organProfitCondition.getSettleStatus());
		}
		if(!Strings.isEmpty(organProfitCondition.getSettleDate())){
			cb.andEQ("settleDate", organProfitCondition.getSettleDate());
		}
		if(!Strings.isEmpty(organProfitCondition.getTransDate())){
			cb.andEQ("transDate", organProfitCondition.getTransDate());
		}

		if(!Strings.isEmpty(organProfitCondition.getRemark())){
			cb.andLike("remark", organProfitCondition.getRemark());
		}
		if(!Strings.isEmpty(organProfitCondition.getTemp1())){
			cb.andEQ("temp1", organProfitCondition.getTemp1());
		}
		if(!Strings.isEmpty(organProfitCondition.getTemp2())){
			cb.andEQ("temp2", organProfitCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return organProfitDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public void insertBatch(List<OrganProfit> list) {
		// TODO Auto-generated method stub
		organProfitDAO.insertBatch(list);
	}
	
	@Override
	public void updateMoney(OrganProfitCondition organProfitCondition) {
		// TODO Auto-generated method stub
		organProfitDAO.updateMoney(organProfitCondition);
	}
	
	/**
	 * 汇总交易提现信息
	 * @Title: queryTotalProfit
	 * @Description: TODO
	 * @param date
	 * @return
	 */
	@Override
	public List<HierarchicalSettlementTotalDTO> queryTotalProfit(String date) {
		return organProfitDAO.queryTotalProfit(date);
	}
}

