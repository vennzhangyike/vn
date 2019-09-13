/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.util.Calendar;
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
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantPaywayTmpCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MerchantPaywayTmpDAO;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.MerchantPaywayTmp;
import com.hfepay.scancode.service.operator.MerchantPaywayTmpService;

@Service("merchantPaywayTmpService")
public class MerchantPaywayTmpServiceImpl implements MerchantPaywayTmpService {
	
	@Autowired
    private MerchantPaywayTmpDAO merchantPaywayTmpDAO;
    
	@Autowired
	private RedisSharedCache redisSharedCache;
	
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: PagingResult<MerchantPaywayTmp>
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
    @Override
	public PagingResult<MerchantPaywayTmp> findPagingResult(MerchantPaywayTmpCondition merchantPaywayTmpCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayTmp.class);
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getId())){
			cb.andEQ("id", merchantPaywayTmpCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayTmpCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayTmpCondition.getPayCode());
		}
		if(null != merchantPaywayTmpCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayTmpCondition.getT0Rate());
		}
		if(null != merchantPaywayTmpCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayTmpCondition.getT1Rate());
		}
		if(null != merchantPaywayTmpCondition.getRate()){
			cb.andEQ("rate", merchantPaywayTmpCondition.getRate());
		}
		if(null != merchantPaywayTmpCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayTmpCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayTmpCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getStatus())){
			cb.andEQ("status", merchantPaywayTmpCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getAcceptStatus())){
			cb.andEQ("acceptStatus", merchantPaywayTmpCondition.getAcceptStatus());
		}
		if(null != merchantPaywayTmpCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayTmpCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayTmpCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayTmpCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayTmpCondition.getRemark())){
			cb.andLike("remark", merchantPaywayTmpCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayTmpCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayTmpCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantPaywayTmpCondition.getFirst()), Long.valueOf(merchantPaywayTmpCondition.getLast()));
		return merchantPaywayTmpDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: List<MerchantPaywayTmp>
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public List<MerchantPaywayTmp> findAll(MerchantPaywayTmpCondition merchantPaywayTmpCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayTmp.class);
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getId())){
			cb.andEQ("id", merchantPaywayTmpCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayTmpCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayTmpCondition.getPayCode());
		}
		if(null != merchantPaywayTmpCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayTmpCondition.getT0Rate());
		}
		if(null != merchantPaywayTmpCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayTmpCondition.getT1Rate());
		}
		if(null != merchantPaywayTmpCondition.getRate()){
			cb.andEQ("rate", merchantPaywayTmpCondition.getRate());
		}
		if(null != merchantPaywayTmpCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayTmpCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayTmpCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getStatus())){
			cb.andEQ("status", merchantPaywayTmpCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getAcceptStatus())){
			cb.andEQ("acceptStatus", merchantPaywayTmpCondition.getAcceptStatus());
		}
		if(null != merchantPaywayTmpCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayTmpCondition.getCreateTime());
		}		
		if(null != merchantPaywayTmpCondition.getUpdateTime()){
			cb.andGE("updateTime", merchantPaywayTmpCondition.getUpdateTime());
		}
		if(null != merchantPaywayTmpCondition.getUpdateTime()){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(merchantPaywayTmpCondition.getUpdateTime());
			calendar.add(calendar.DATE, 1);
			cb.andLE("updateTime", calendar.getTime());
		}

		if(!Strings.isEmpty(merchantPaywayTmpCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayTmpCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayTmpCondition.getRemark())){
			cb.andLike("remark", merchantPaywayTmpCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayTmpCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayTmpCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayTmpCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantPaywayTmpDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MerchantPaywayTmp
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public MerchantPaywayTmp findById(String id){
		return merchantPaywayTmpDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long insert(MerchantPaywayTmpCondition merchantPaywayTmpCondition){
		MerchantPaywayTmp merchantPaywayTmp = new MerchantPaywayTmp();
		try {
			MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantPaywayTmpCondition.getMerchantNo()));
			merchantPaywayTmpCondition.setChannelNo(merchantInfo.getChannelNo());
			merchantPaywayTmpCondition.setAgentNo(merchantInfo.getAgentNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(merchantPaywayTmpCondition, merchantPaywayTmp);
		merchantPaywayTmp.setCreateTime(new Date());
		merchantPaywayTmp.setUpdateTime(new Date());
		return merchantPaywayTmpDAO.insert(merchantPaywayTmp);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long deleteById(String id){
		return merchantPaywayTmpDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantPaywayTmpDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantPaywayTmpDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long update(MerchantPaywayTmpCondition merchantPaywayTmpCondition){
		MerchantPaywayTmp merchantPaywayTmp = new MerchantPaywayTmp();
		BeanUtils.copyProperties(merchantPaywayTmpCondition, merchantPaywayTmp);
		merchantPaywayTmp.setUpdateTime(new Date());
		return merchantPaywayTmpDAO.update(merchantPaywayTmp);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long updateByCriteria(MerchantPaywayTmpCondition merchantPaywayTmpCondition,Criteria criteria){
		MerchantPaywayTmp merchantPaywayTmp = new MerchantPaywayTmp();
		BeanUtils.copyProperties(merchantPaywayTmpCondition, merchantPaywayTmp);
		return merchantPaywayTmpDAO.updateByCriteria(merchantPaywayTmp,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantPaywayTmpDAO.updateStatus(id,status);
	}	
	
	/**
	 * @Title: save
	 * @Description: 新增、更新
	 * @author: Ricky
	 * @param merchantPaywayTmpCondition
	 * @date CreateDate : 2016-11-15 17:17:04
	 */
	@Override
	public void save(MerchantPaywayTmpCondition merchantPaywayTmpCondition){
		MerchantPaywayTmp merchantPaywayTmp = this.findByPayCode(merchantPaywayTmpCondition.getPayCode(), merchantPaywayTmpCondition.getMerchantNo());
		if(merchantPaywayTmp == null){			
			this.insert(merchantPaywayTmpCondition);
		}else{
			merchantPaywayTmpCondition.setId(merchantPaywayTmp.getId());
			merchantPaywayTmpCondition.setAcceptStatus("");
			this.update(merchantPaywayTmpCondition);
		}
	}	
	
	/**
	 * 根据支付通道查询
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public MerchantPaywayTmp findByPayCode(String payCode, String merchantNo) {
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayTmp.class);
		cb.andEQ("payCode", payCode);
		cb.andEQ("merchantNo", merchantNo);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantPaywayTmpDAO.findOneByCriteria(buildCriteria);
	}
}

