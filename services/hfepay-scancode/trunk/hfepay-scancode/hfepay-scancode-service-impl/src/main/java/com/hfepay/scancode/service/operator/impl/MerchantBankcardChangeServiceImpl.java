/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator.impl;

import java.util.Date;
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
import com.hfepay.scancode.commons.condition.MerchantBankcardChangeCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MerchantBankcardChangeDAO;
import com.hfepay.scancode.commons.entity.ChangeLog;
import com.hfepay.scancode.commons.entity.MerchantBankcardChange;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.operator.MerchantBankcardChangeService;

@Service("merchantBankcardChangeService")
public class MerchantBankcardChangeServiceImpl implements MerchantBankcardChangeService {
	
	@Autowired
    private MerchantBankcardChangeDAO merchantBankcardChangeDAO;
    
	@Autowired
	private RedisSharedCache redisSharedCache;
	
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantBankcardChangeCondition
	 * @return: PagingResult<MerchantBankcardChange>
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
    @Override
	public PagingResult<MerchantBankcardChange> findPagingResult(MerchantBankcardChangeCondition merchantBankcardChangeCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantBankcardChange.class);
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getId())){
			cb.andEQ("id", merchantBankcardChangeCondition.getId());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantBankcardChangeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getMerchantName())){
			cb.andLike("merchantName", merchantBankcardChangeCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getIdCard())){
			cb.andEQ("idCard", merchantBankcardChangeCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getName())){
			cb.andEQ("name", merchantBankcardChangeCondition.getName());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getBankCode())){
			cb.andEQ("bankCode", merchantBankcardChangeCondition.getBankCode());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getBankName())){
			cb.andEQ("bankName", merchantBankcardChangeCondition.getBankName());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getBankCard())){
			cb.andEQ("bankCard", merchantBankcardChangeCondition.getBankCard());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getMobile())){
			cb.andEQ("mobile", merchantBankcardChangeCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", merchantBankcardChangeCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getStatus())){
			cb.andEQ("status", merchantBankcardChangeCondition.getStatus());
		}
		if(null != merchantBankcardChangeCondition.getCreateTime()){
			cb.andEQ("createTime", merchantBankcardChangeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantBankcardChangeCondition.getOperator())){
			cb.andEQ("operator", merchantBankcardChangeCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantBankcardChangeCondition.getRemark())){
			cb.andLike("remark", merchantBankcardChangeCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getTemp1())){
			cb.andEQ("temp1", merchantBankcardChangeCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getTemp2())){
			cb.andEQ("temp2", merchantBankcardChangeCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getAccountType())){
			cb.andEQ("accountType", merchantBankcardChangeCondition.getAccountType());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantBankcardChangeCondition.getFirst()), Long.valueOf(merchantBankcardChangeCondition.getLast()));
		return merchantBankcardChangeDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param merchantBankcardChangeCondition
	 * @return: List<MerchantBankcardChange>
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@Override
	public List<MerchantBankcardChange> findAll(MerchantBankcardChangeCondition merchantBankcardChangeCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantBankcardChange.class);
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getId())){
			cb.andEQ("id", merchantBankcardChangeCondition.getId());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantBankcardChangeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getIdCard())){
			cb.andEQ("idCard", merchantBankcardChangeCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getName())){
			cb.andEQ("name", merchantBankcardChangeCondition.getName());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getBankCode())){
			cb.andEQ("bankCode", merchantBankcardChangeCondition.getBankCode());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getBankName())){
			cb.andEQ("bankName", merchantBankcardChangeCondition.getBankName());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getBankCard())){
			cb.andEQ("bankCard", merchantBankcardChangeCondition.getBankCard());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getMobile())){
			cb.andEQ("mobile", merchantBankcardChangeCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getIsRealAccount())){
			cb.andEQ("isRealAccount", merchantBankcardChangeCondition.getIsRealAccount());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getStatus())){
			cb.andEQ("status", merchantBankcardChangeCondition.getStatus());
		}
		if(null != merchantBankcardChangeCondition.getCreateTime()){
			cb.andEQ("createTime", merchantBankcardChangeCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantBankcardChangeCondition.getOperator())){
			cb.andEQ("operator", merchantBankcardChangeCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantBankcardChangeCondition.getRemark())){
			cb.andLike("remark", merchantBankcardChangeCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getTemp1())){
			cb.andEQ("temp1", merchantBankcardChangeCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getTemp2())){
			cb.andEQ("temp2", merchantBankcardChangeCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getAccountType())){
			cb.andEQ("accountType", merchantBankcardChangeCondition.getAccountType());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantBankcardChangeDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MerchantBankcardChange
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@Override
	public MerchantBankcardChange findById(String id){
		return merchantBankcardChangeDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantBankcardChangeCondition
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@Override
	public long insert(MerchantBankcardChangeCondition merchantBankcardChangeCondition){
		MerchantBankcardChange merchantBankcardChange = new MerchantBankcardChange();
		try {
			MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantBankcardChangeCondition.getMerchantNo()));
			merchantBankcardChangeCondition.setChannelNo(merchantInfo.getChannelNo());
			merchantBankcardChangeCondition.setAgentNo(merchantInfo.getAgentNo());
			BeanUtils.copyProperties(merchantBankcardChangeCondition, merchantBankcardChange);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return merchantBankcardChangeDAO.insert(merchantBankcardChange);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@Override
	public long deleteById(String id){
		return merchantBankcardChangeDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantBankcardChangeDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantBankcardChangeDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param merchantBankcardChangeCondition
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@Override
	public long update(MerchantBankcardChangeCondition merchantBankcardChangeCondition){
		MerchantBankcardChange merchantBankcardChange = new MerchantBankcardChange();
		BeanUtils.copyProperties(merchantBankcardChangeCondition, merchantBankcardChange);
		merchantBankcardChange.setUpdateTime(new Date());
		return merchantBankcardChangeDAO.update(merchantBankcardChange);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param merchantBankcardChangeCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@Override
	public long updateByCriteria(MerchantBankcardChangeCondition merchantBankcardChangeCondition,Criteria criteria){
		MerchantBankcardChange merchantBankcardChange = new MerchantBankcardChange();
		BeanUtils.copyProperties(merchantBankcardChangeCondition, merchantBankcardChange);
		return merchantBankcardChangeDAO.updateByCriteria(merchantBankcardChange,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantBankcardChangeDAO.updateStatus(id,status);
	}	
	
	/** 商户编号查找 */
	@Override
	public MerchantBankcardChange findByMerchantNo(String merchantNo) {
		CriteriaBuilder cb = Cnd.builder(MerchantBankcardChange.class);
		cb.andEQ("merchantNo", merchantNo);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantBankcardChangeDAO.findOneByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findAuditMerchantBankcard
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param MerchantBankcardChangeCondition
	 * @return: PagingResult<MerchantBankcardChange>
	 * @date CreateDate : 2016-10-17 15:21:26
	 */
	public PagingResult<MerchantBankcardChange> findAuditMerchantBankcard(MerchantBankcardChangeCondition merchantBankcardChangeCondition){
		CriteriaBuilder cb = Cnd.builder(ChangeLog.class);
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantBankcardChangeCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantBankcardChangeCondition.getMerchantName())){
			cb.andLike("merchantName", merchantBankcardChangeCondition.getMerchantName());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantBankcardChangeCondition.getFirst()), Long.valueOf(merchantBankcardChangeCondition.getLast()));
		return merchantBankcardChangeDAO.findAuditMerchantBankcard(buildCriteria);
	}
}

