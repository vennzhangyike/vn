/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
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
import com.hfepay.scancode.commons.condition.MerchantActivityDiscountCondition;
import com.hfepay.scancode.commons.dao.MerchantActivityDiscountDAO;
import com.hfepay.scancode.commons.entity.MerchantActivityDiscount;
import com.hfepay.scancode.service.operator.MerchantActivityDiscountService;

@Service("merchantActivityDiscountService")
public class MerchantActivityDiscountServiceImpl implements MerchantActivityDiscountService {
	
	@Autowired
    private MerchantActivityDiscountDAO merchantActivityDiscountDAO;
    
    /**
	 * 列表(分页)
	 * @param merchantActivityDiscountCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
    @Override
	public PagingResult<MerchantActivityDiscount> findPagingResult(MerchantActivityDiscountCondition merchantActivityDiscountCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantActivityDiscount.class);
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getId())){
			cb.andEQ("id", merchantActivityDiscountCondition.getId());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getActivityId())){
			cb.andEQ("activityId", merchantActivityDiscountCondition.getActivityId());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getActivityDiscount())){
			cb.andEQ("activityDiscount", merchantActivityDiscountCondition.getActivityDiscount());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getActivityCondition())){
			cb.andEQ("activityCondition", merchantActivityDiscountCondition.getActivityCondition());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getChance())){
			cb.andEQ("chance", merchantActivityDiscountCondition.getChance());
		}
		cb.orderBy("activityCondition", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getOrderBy())){
			if(merchantActivityDiscountCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantActivityDiscountCondition.getOrderBy().split(",");
				String[] orders = merchantActivityDiscountCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantActivityDiscountCondition.getOrderBy(), Order.valueOf(merchantActivityDiscountCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantActivityDiscountCondition.getFirst()), Long.valueOf(merchantActivityDiscountCondition.getLast()));
		return merchantActivityDiscountDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param merchantActivityDiscount 
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	@Override
	public List<MerchantActivityDiscount> findAll(MerchantActivityDiscountCondition merchantActivityDiscountCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantActivityDiscount.class);
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getId())){
			cb.andEQ("id", merchantActivityDiscountCondition.getId());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getActivityId())){
			cb.andEQ("activityId", merchantActivityDiscountCondition.getActivityId());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getActivityDiscount())){
			cb.andEQ("activityDiscount", merchantActivityDiscountCondition.getActivityDiscount());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getActivityCondition())){
			cb.andEQ("activityCondition", merchantActivityDiscountCondition.getActivityCondition());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getChance())){
			cb.andEQ("chance", merchantActivityDiscountCondition.getChance());
		}
		cb.orderBy("activityCondition", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getOrderBy())){
			if(merchantActivityDiscountCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantActivityDiscountCondition.getOrderBy().split(",");
				String[] orders = merchantActivityDiscountCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantActivityDiscountCondition.getOrderBy(), Order.valueOf(merchantActivityDiscountCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantActivityDiscountDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	@Override
	public MerchantActivityDiscount findById(String id){
		return merchantActivityDiscountDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantActivityDiscountCondition
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	@Override
	public long insert(MerchantActivityDiscountCondition merchantActivityDiscountCondition){
		MerchantActivityDiscount merchantActivityDiscount = new MerchantActivityDiscount();
		BeanUtils.copyProperties(merchantActivityDiscountCondition, merchantActivityDiscount);
		return merchantActivityDiscountDAO.insert(merchantActivityDiscount);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	@Override
	public long deleteById(String id){
		return merchantActivityDiscountDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantActivityDiscountDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantActivityDiscountDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	@Override
	public long update(MerchantActivityDiscountCondition merchantActivityDiscountCondition){
		MerchantActivityDiscount merchantActivityDiscount = new MerchantActivityDiscount();
		BeanUtils.copyProperties(merchantActivityDiscountCondition, merchantActivityDiscount);
		return merchantActivityDiscountDAO.update(merchantActivityDiscount);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	@Override
	public long updateByCriteria(MerchantActivityDiscountCondition merchantActivityDiscountCondition,Criteria criteria){
		MerchantActivityDiscount merchantActivityDiscount = new MerchantActivityDiscount();
		BeanUtils.copyProperties(merchantActivityDiscountCondition, merchantActivityDiscount);
		return merchantActivityDiscountDAO.updateByCriteria(merchantActivityDiscount,criteria);
	}

	@Override
	public void batchInsert(List<MerchantActivityDiscount> insertList) {
		 merchantActivityDiscountDAO.batchInsert(insertList);
	}
	
}

