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
import com.hfepay.scancode.commons.condition.PlatformLimitCondition;
import com.hfepay.scancode.commons.dao.PlatformLimitDAO;
import com.hfepay.scancode.commons.entity.PlatformLimit;
import com.hfepay.scancode.service.operator.PlatformLimitService;

@Service("platformLimitService")
public class PlatformLimitServiceImpl implements PlatformLimitService {
	
	@Autowired
    private PlatformLimitDAO platformLimitDAO;
    
    /**
	 * 列表(分页)
	 * @param platformLimitCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
    @Override
	public PagingResult<PlatformLimit> findPagingResult(PlatformLimitCondition platformLimitCondition){
		CriteriaBuilder cb = Cnd.builder(PlatformLimit.class);
		if(!Strings.isEmpty(platformLimitCondition.getId())){
			cb.andEQ("id", platformLimitCondition.getId());
		}
		if(!Strings.isEmpty(platformLimitCondition.getLimitPayCode())){
			cb.andEQ("limitPayCode", platformLimitCondition.getLimitPayCode());
		}
		if(!Strings.isEmpty(platformLimitCondition.getLimitType())){
			cb.andEQ("limitType", platformLimitCondition.getLimitType());
		}
		if(null != platformLimitCondition.getDayTransLimit()){
			cb.andEQ("dayTransLimit", platformLimitCondition.getDayTransLimit());
		}
		if(null != platformLimitCondition.getDayWithdrawalsLimit()){
			cb.andEQ("dayWithdrawalsLimit", platformLimitCondition.getDayWithdrawalsLimit());
		}
		if(null != platformLimitCondition.getSingleTransLimit()){
			cb.andEQ("singleTransLimit", platformLimitCondition.getSingleTransLimit());
		}
		if(null != platformLimitCondition.getSingleWithdrawalsLimit()){
			cb.andEQ("singleWithdrawalsLimit", platformLimitCondition.getSingleWithdrawalsLimit());
		}
		if(!Strings.isEmpty(platformLimitCondition.getStatus())){
			cb.andEQ("status", platformLimitCondition.getStatus());
		}
		if(!Strings.isEmpty(platformLimitCondition.getRecordStatus())){
			cb.andEQ("recordStatus", platformLimitCondition.getRecordStatus());
		}
		if(null != platformLimitCondition.getCreateTime()){
			cb.andEQ("createTime", platformLimitCondition.getCreateTime());
		}

		if(!Strings.isEmpty(platformLimitCondition.getOperator())){
			cb.andEQ("operator", platformLimitCondition.getOperator());
		}

		if(!Strings.isEmpty(platformLimitCondition.getRemark())){
			cb.andLike("remark", platformLimitCondition.getRemark());
		}
		if(!Strings.isEmpty(platformLimitCondition.getTemp1())){
			cb.andEQ("temp1", platformLimitCondition.getTemp1());
		}
		if(!Strings.isEmpty(platformLimitCondition.getTemp2())){
			cb.andEQ("temp2", platformLimitCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(platformLimitCondition.getOrderBy())){
			if(platformLimitCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = platformLimitCondition.getOrderBy().split(",");
				String[] orders = platformLimitCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(platformLimitCondition.getOrderBy(), Order.valueOf(platformLimitCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(platformLimitCondition.getFirst()), Long.valueOf(platformLimitCondition.getLast()));
		return platformLimitDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param platformLimit 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	@Override
	public List<PlatformLimit> findAll(PlatformLimitCondition platformLimitCondition){
		CriteriaBuilder cb = Cnd.builder(PlatformLimit.class);
		if(!Strings.isEmpty(platformLimitCondition.getId())){
			cb.andEQ("id", platformLimitCondition.getId());
		}
		if(!Strings.isEmpty(platformLimitCondition.getLimitPayCode())){
			cb.andEQ("limitPayCode", platformLimitCondition.getLimitPayCode());
		}
		if(!Strings.isEmpty(platformLimitCondition.getLimitType())){
			cb.andEQ("limitType", platformLimitCondition.getLimitType());
		}
		if(null != platformLimitCondition.getDayTransLimit()){
			cb.andEQ("dayTransLimit", platformLimitCondition.getDayTransLimit());
		}
		if(null != platformLimitCondition.getDayWithdrawalsLimit()){
			cb.andEQ("dayWithdrawalsLimit", platformLimitCondition.getDayWithdrawalsLimit());
		}
		if(null != platformLimitCondition.getSingleTransLimit()){
			cb.andEQ("singleTransLimit", platformLimitCondition.getSingleTransLimit());
		}
		if(null != platformLimitCondition.getSingleWithdrawalsLimit()){
			cb.andEQ("singleWithdrawalsLimit", platformLimitCondition.getSingleWithdrawalsLimit());
		}
		if(!Strings.isEmpty(platformLimitCondition.getStatus())){
			cb.andEQ("status", platformLimitCondition.getStatus());
		}
		if(!Strings.isEmpty(platformLimitCondition.getRecordStatus())){
			cb.andEQ("recordStatus", platformLimitCondition.getRecordStatus());
		}
		if(null != platformLimitCondition.getCreateTime()){
			cb.andEQ("createTime", platformLimitCondition.getCreateTime());
		}

		if(!Strings.isEmpty(platformLimitCondition.getOperator())){
			cb.andEQ("operator", platformLimitCondition.getOperator());
		}

		if(!Strings.isEmpty(platformLimitCondition.getRemark())){
			cb.andLike("remark", platformLimitCondition.getRemark());
		}
		if(!Strings.isEmpty(platformLimitCondition.getTemp1())){
			cb.andEQ("temp1", platformLimitCondition.getTemp1());
		}
		if(!Strings.isEmpty(platformLimitCondition.getTemp2())){
			cb.andEQ("temp2", platformLimitCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return platformLimitDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	@Override
	public PlatformLimit findById(String id){
		return platformLimitDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param platformLimitCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	@Override
	public long insert(PlatformLimitCondition platformLimitCondition){
		PlatformLimit platformLimit = new PlatformLimit();
		BeanUtils.copyProperties(platformLimitCondition, platformLimit);
		return platformLimitDAO.insert(platformLimit);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	@Override
	public long deleteById(String id){
		return platformLimitDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return platformLimitDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return platformLimitDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	@Override
	public long update(PlatformLimitCondition platformLimitCondition){
		PlatformLimit platformLimit = new PlatformLimit();
		BeanUtils.copyProperties(platformLimitCondition, platformLimit);
		return platformLimitDAO.update(platformLimit);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	@Override
	public long updateByCriteria(PlatformLimitCondition platformLimitCondition,Criteria criteria){
		PlatformLimit platformLimit = new PlatformLimit();
		BeanUtils.copyProperties(platformLimitCondition, platformLimit);
		return platformLimitDAO.updateByCriteria(platformLimit,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-18 15:02:00
	 */
	@Override
	public long updateStatus(String id,String status){
		return platformLimitDAO.updateStatus(id,status);
	}	
}

