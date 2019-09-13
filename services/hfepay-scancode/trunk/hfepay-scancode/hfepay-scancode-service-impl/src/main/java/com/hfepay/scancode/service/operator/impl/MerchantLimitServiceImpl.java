/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantLimitCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MerchantLimitDAO;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantLimit;
import com.hfepay.scancode.service.operator.MerchantLimitService;

@Service("merchantLimitService")
public class MerchantLimitServiceImpl implements MerchantLimitService {
	
	@Autowired
    private MerchantLimitDAO merchantLimitDAO;
	
	@Autowired
	private RedisSharedCache redisSharedCache;
    
    /**
	 * 列表(分页)
	 * @param merchantLimitCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
    @Override
	public PagingResult<MerchantLimit> findPagingResult(MerchantLimitCondition merchantLimitCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantLimit.class);
		if(!Strings.isEmpty(merchantLimitCondition.getId())){
			cb.andEQ("id", merchantLimitCondition.getId());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantLimitCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getLimitPayCode())){
			cb.andEQ("limitPayCode", merchantLimitCondition.getLimitPayCode());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getLimitType())){
			cb.andEQ("limitType", merchantLimitCondition.getLimitType());
		}
		if(null != merchantLimitCondition.getDayTransLimit()){
			cb.andEQ("dayTransLimit", merchantLimitCondition.getDayTransLimit());
		}
		if(null != merchantLimitCondition.getDayWithdrawalsLimit()){
			cb.andEQ("dayWithdrawalsLimit", merchantLimitCondition.getDayWithdrawalsLimit());
		}
		if(null != merchantLimitCondition.getSingleTransLimit()){
			cb.andEQ("singleTransLimit", merchantLimitCondition.getSingleTransLimit());
		}
		if(null != merchantLimitCondition.getSingleWithdrawalsLimit()){
			cb.andEQ("singleWithdrawalsLimit", merchantLimitCondition.getSingleWithdrawalsLimit());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getStatus())){
			cb.andEQ("status", merchantLimitCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantLimitCondition.getRecordStatus());
		}
		if(null != merchantLimitCondition.getCreateTime()){
			cb.andEQ("createTime", merchantLimitCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantLimitCondition.getOperator())){
			cb.andEQ("operator", merchantLimitCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantLimitCondition.getRemark())){
			cb.andLike("remark", merchantLimitCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getTemp1())){
			cb.andEQ("temp1", merchantLimitCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getTemp2())){
			cb.andEQ("temp2", merchantLimitCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantLimitCondition.getOrderBy())){
			if(merchantLimitCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantLimitCondition.getOrderBy().split(",");
				String[] orders = merchantLimitCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantLimitCondition.getOrderBy(), Order.valueOf(merchantLimitCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantLimitCondition.getFirst()), Long.valueOf(merchantLimitCondition.getLast()));
		return merchantLimitDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param merchantLimit 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	@Override
	public List<MerchantLimit> findAll(MerchantLimitCondition merchantLimitCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantLimit.class);
		if(!Strings.isEmpty(merchantLimitCondition.getId())){
			cb.andEQ("id", merchantLimitCondition.getId());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantLimitCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getLimitPayCode())){
			cb.andEQ("limitPayCode", merchantLimitCondition.getLimitPayCode());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getLimitType())){
			cb.andEQ("limitType", merchantLimitCondition.getLimitType());
		}
		if(null != merchantLimitCondition.getDayTransLimit()){
			cb.andEQ("dayTransLimit", merchantLimitCondition.getDayTransLimit());
		}
		if(null != merchantLimitCondition.getDayWithdrawalsLimit()){
			cb.andEQ("dayWithdrawalsLimit", merchantLimitCondition.getDayWithdrawalsLimit());
		}
		if(null != merchantLimitCondition.getSingleTransLimit()){
			cb.andEQ("singleTransLimit", merchantLimitCondition.getSingleTransLimit());
		}
		if(null != merchantLimitCondition.getSingleWithdrawalsLimit()){
			cb.andEQ("singleWithdrawalsLimit", merchantLimitCondition.getSingleWithdrawalsLimit());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getStatus())){
			cb.andEQ("status", merchantLimitCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantLimitCondition.getRecordStatus());
		}
		if(null != merchantLimitCondition.getCreateTime()){
			cb.andEQ("createTime", merchantLimitCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantLimitCondition.getOperator())){
			cb.andEQ("operator", merchantLimitCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantLimitCondition.getRemark())){
			cb.andLike("remark", merchantLimitCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getTemp1())){
			cb.andEQ("temp1", merchantLimitCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantLimitCondition.getTemp2())){
			cb.andEQ("temp2", merchantLimitCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantLimitDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	@Override
	public MerchantLimit findById(String id){
		return merchantLimitDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantLimitCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	@Override
	public long insert(MerchantLimitCondition merchantLimitCondition){
		MerchantLimit merchantLimit = new MerchantLimit();
		try {
			MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantLimitCondition.getMerchantNo()));
			merchantLimitCondition.setChannelNo(merchantInfo.getChannelNo());
			merchantLimitCondition.setAgentNo(merchantInfo.getAgentNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(merchantLimitCondition, merchantLimit);
		return merchantLimitDAO.insert(merchantLimit);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	@Override
	public long deleteById(String id){
		return merchantLimitDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantLimitDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantLimitDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	@Override
	public long update(MerchantLimitCondition merchantLimitCondition){
		MerchantLimit merchantLimit = new MerchantLimit();
		BeanUtils.copyProperties(merchantLimitCondition, merchantLimit);
		return merchantLimitDAO.update(merchantLimit);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	@Override
	public long updateByCriteria(MerchantLimitCondition merchantLimitCondition,Criteria criteria){
		MerchantLimit merchantLimit = new MerchantLimit();
		BeanUtils.copyProperties(merchantLimitCondition, merchantLimit);
		return merchantLimitDAO.updateByCriteria(merchantLimit,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-16 17:20:46
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantLimitDAO.updateStatus(id,status);
	}	
}

