/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.OrganLimitCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.dao.OrganLimitDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrganLimit;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.OrganLimitService;

@Service("organLimitService")
public class OrganLimitServiceImpl implements OrganLimitService {
	
	public static final Logger log = LoggerFactory.getLogger(OrganLimitServiceImpl.class);
	@Autowired
	private RedisSharedCache redisSharedCache;	
	@Autowired
    private OrganLimitDAO organLimitDAO;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private AgentBaseService agentBaseService;
	
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param organLimitCondition
	 * @return: PagingResult<OrganLimit>
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
    @Override
	public PagingResult<OrganLimit> findPagingResult(OrganLimitCondition organLimitCondition){
		CriteriaBuilder cb = Cnd.builder(OrganLimit.class);
		if(!Strings.isEmpty(organLimitCondition.getId())){
			cb.andEQ("id", organLimitCondition.getId());
		}
		if(!Strings.isEmpty(organLimitCondition.getOrganNo())){
			cb.andEQ("organNo", organLimitCondition.getOrganNo());
		}
		if(!Strings.isEmpty(organLimitCondition.getLimitType())){
			cb.andEQ("limitType", organLimitCondition.getLimitType());
		}
		if(!Strings.isEmpty(organLimitCondition.getLimitPayCode())){
			cb.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
		}
		if(!Strings.isEmpty(organLimitCondition.getLimitMode())){
			cb.andEQ("limitMode", organLimitCondition.getLimitMode());
		}
		if(null != organLimitCondition.getMinLimit()){
			cb.andEQ("minLimit", organLimitCondition.getMinLimit());
		}
		if(null != organLimitCondition.getMaxLimit()){
			cb.andEQ("maxLimit", organLimitCondition.getMaxLimit());
		}
		if(!Strings.isEmpty(organLimitCondition.getStatus())){
			cb.andEQ("status", organLimitCondition.getStatus());
		}
		if(!Strings.isEmpty(organLimitCondition.getRecordStatus())){
			cb.andEQ("recordStatus", organLimitCondition.getRecordStatus());
		}
		if(null != organLimitCondition.getCreateTime()){
			cb.andEQ("createTime", organLimitCondition.getCreateTime());
		}

		if(!Strings.isEmpty(organLimitCondition.getOperator())){
			cb.andEQ("operator", organLimitCondition.getOperator());
		}

		if(!Strings.isEmpty(organLimitCondition.getRemark())){
			cb.andLike("remark", organLimitCondition.getRemark());
		}
		if(!Strings.isEmpty(organLimitCondition.getTemp1())){
			cb.andEQ("temp1", organLimitCondition.getTemp1());
		}
		if(!Strings.isEmpty(organLimitCondition.getTemp2())){
			cb.andEQ("temp2", organLimitCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(organLimitCondition.getFirst()), Long.valueOf(organLimitCondition.getLast()));
		PagingResult<OrganLimit> page= organLimitDAO.findPagingResult(buildCriteria);
		
		for (OrganLimit organLimit : page.getResult()) {
			try {
				if (Strings.isNotEmpty(organLimit.getOrganNo()) && !Strings.equals(organLimit.getOrganNo(), ScanCodeConstants.UNLIMITED)) {
					ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+organLimit.getOrganNo()));
					if(channelBase != null){
						organLimit.setOrganName(channelBase.getChannelName());
					}			
					AgentBase agentBase = (AgentBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+organLimit.getOrganNo()));
					if(agentBase != null){
						organLimit.setOrganName(agentBase.getAgentName());
					}
				}else{
					organLimit.setOrganName(ScanCodeConstants.SYSTEM_UNLIMITED);
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param organLimitCondition
	 * @return: List<OrganLimit>
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@Override
	public List<OrganLimit> findAll(OrganLimitCondition organLimitCondition){
		CriteriaBuilder cb = Cnd.builder(OrganLimit.class);
		if(!Strings.isEmpty(organLimitCondition.getId())){
			cb.andEQ("id", organLimitCondition.getId());
		}
		if(!Strings.isEmpty(organLimitCondition.getOrganNo())){
			cb.andEQ("organNo", organLimitCondition.getOrganNo());
		}
		if(!Strings.isEmpty(organLimitCondition.getLimitType())){
			cb.andEQ("limitType", organLimitCondition.getLimitType());
		}
		if(!Strings.isEmpty(organLimitCondition.getLimitPayCode())){
			cb.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
		}
		if(!Strings.isEmpty(organLimitCondition.getLimitMode())){
			cb.andEQ("limitMode", organLimitCondition.getLimitMode());
		}
		if(null != organLimitCondition.getMinLimit()){
			cb.andEQ("minLimit", organLimitCondition.getMinLimit());
		}
		if(null != organLimitCondition.getMaxLimit()){
			cb.andEQ("maxLimit", organLimitCondition.getMaxLimit());
		}
		if(!Strings.isEmpty(organLimitCondition.getStatus())){
			cb.andEQ("status", organLimitCondition.getStatus());
		}
		if(!Strings.isEmpty(organLimitCondition.getRecordStatus())){
			cb.andEQ("recordStatus", organLimitCondition.getRecordStatus());
		}
		if(null != organLimitCondition.getCreateTime()){
			cb.andEQ("createTime", organLimitCondition.getCreateTime());
		}

		if(!Strings.isEmpty(organLimitCondition.getOperator())){
			cb.andEQ("operator", organLimitCondition.getOperator());
		}

		if(!Strings.isEmpty(organLimitCondition.getRemark())){
			cb.andLike("remark", organLimitCondition.getRemark());
		}
		if(!Strings.isEmpty(organLimitCondition.getTemp1())){
			cb.andEQ("temp1", organLimitCondition.getTemp1());
		}
		if(!Strings.isEmpty(organLimitCondition.getTemp2())){
			cb.andEQ("temp2", organLimitCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return organLimitDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: OrganLimit
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@Override
	public OrganLimit findById(String id){
		return organLimitDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param organLimitCondition
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@Override
	public long insert(OrganLimitCondition organLimitCondition){
		OrganLimit organLimit = new OrganLimit();
		BeanUtils.copyProperties(organLimitCondition, organLimit);
		try {
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)), organLimit);
		} catch (Exception e) {
			log.error("#######保存数据到redis失败######");
		}
		return organLimitDAO.insert(organLimit);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@Override
	public long deleteById(String id){
		return organLimitDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return organLimitDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return organLimitDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param organLimitCondition
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@Override
	public long update(OrganLimitCondition organLimitCondition){
		OrganLimit organLimit = new OrganLimit();
		BeanUtils.copyProperties(organLimitCondition, organLimit);
		organLimit.setUpdateTime(new Date());
		try {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)), organLimit);
		} catch (Exception e) {
			log.error("#######保存数据到redis失败######");
		}
		return organLimitDAO.update(organLimit);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param organLimitCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@Override
	public long updateByCriteria(OrganLimitCondition organLimitCondition,Criteria criteria){
		OrganLimit organLimit = new OrganLimit();
		BeanUtils.copyProperties(organLimitCondition, organLimit);
		return organLimitDAO.updateByCriteria(organLimit,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@Override
	public long updateStatus(String id,String status){
		return organLimitDAO.updateStatus(id,status);
	}	
	
	private String getKey(OrganLimitCondition organLimitCondition){
		StringBuffer sb = new StringBuffer();
		sb.append(organLimitCondition.getOrganNo());
		sb.append(":");
		sb.append(organLimitCondition.getLimitType());
		sb.append(":");
		sb.append(organLimitCondition.getLimitPayCode());
		sb.append(":");
		sb.append(organLimitCondition.getLimitMode());
		return sb.toString();
	}
	
	/** 获得机构限额 */
	@Override
	public OrganLimit getOrganLimit(OrganLimitCondition organLimitCondition, String merchantNo){
		OrganLimit organLimit = null;
		OrganLimit findOrganLimit = null;
		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantNo);
		organLimitCondition.setOrganNo(merchantInfo.getAgentNo());
		try {
			organLimit = (OrganLimit) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)));
			if(organLimit == null){
				CriteriaBuilder cb = Cnd.builder(OrganLimit.class);
				cb.andEQ("limitType", organLimitCondition.getLimitType());
				cb.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
				cb.andEQ("limitMode", organLimitCondition.getLimitMode());
				cb.andEQ("organNo", merchantInfo.getAgentNo());
				cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteria = cb.buildCriteria();
				organLimit = organLimitDAO.findOneByCriteria(buildCriteria);
				findOrganLimit = organLimit;
				
			}
			
			if(organLimit == null){
				organLimitCondition.setOrganNo(merchantInfo.getChannelNo());
				organLimit = (OrganLimit) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)));
				if(organLimit == null){
					CriteriaBuilder cb = Cnd.builder(OrganLimit.class);
					cb.andEQ("limitType", organLimitCondition.getLimitType());
					cb.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
					cb.andEQ("limitMode", organLimitCondition.getLimitMode());
					cb.andEQ("organNo", merchantInfo.getChannelNo());
					cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
					Criteria buildCriteria = cb.buildCriteria();
					organLimit = organLimitDAO.findOneByCriteria(buildCriteria);
					findOrganLimit = organLimit;
				}
			}
			if(organLimit == null){
				organLimitCondition.setOrganNo(ScanCodeConstants.UNLIMITED);
				organLimit = (OrganLimit) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)));
				if(organLimit == null){
					CriteriaBuilder cb = Cnd.builder(OrganLimit.class);
					cb.andEQ("limitType", organLimitCondition.getLimitType());
					cb.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
					cb.andEQ("limitMode", organLimitCondition.getLimitMode());
					cb.andEQ("organNo", ScanCodeConstants.UNLIMITED);
					cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
					Criteria buildCriteria = cb.buildCriteria();
					organLimit = organLimitDAO.findOneByCriteria(buildCriteria);
					findOrganLimit = organLimit;
				}
			}
		} catch (Exception e) {
			log.info("redis获得机构限额异常:" + e.getMessage());
		}
		if(findOrganLimit != null){
			try {
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)), organLimit);
			} catch (Exception e) {
				log.error("#######保存数据到redis失败######");
			}
		}
		return organLimit;
	}
	
	/**
	 * 校验最低、最高限额比上级底比下级高
	 * @param organLimitCondition
	 */
	public Map<Object, Object> checkLimit(OrganLimitCondition organLimitCondition){
		ChannelBase channelBase = channelBaseService.findByChannelNo(organLimitCondition.getOrganNo());
		if(channelBase != null){
			CriteriaBuilder cb = Cnd.builder(OrganLimit.class);
			cb.andEQ("organNo", ScanCodeConstants.UNLIMITED);
			cb.andEQ("limitType", organLimitCondition.getLimitType());
			cb.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
			cb.andEQ("limitMode", organLimitCondition.getLimitMode());
			cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
			Criteria buildCriteria = cb.buildCriteria();
			OrganLimit organLimit = organLimitDAO.findOneByCriteria(buildCriteria);//平台限额
			if(organLimit != null && (organLimit.getMinLimit().compareTo(organLimitCondition.getMinLimit())== -1 || 
					organLimit.getMaxLimit().compareTo(organLimitCondition.getMaxLimit())==-1)){
				return Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"渠道限额不能比平台限额高");
			}
		}else{
			AgentBase agentBase = agentBaseService.findByAgentNo(organLimitCondition.getOrganNo());
			if(agentBase != null){
				CriteriaBuilder cbAgent = Cnd.builder(OrganLimit.class);
				cbAgent.andEQ("organNo", agentBase.getChannelNo());
				cbAgent.andEQ("limitType", organLimitCondition.getLimitType());
				cbAgent.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
				cbAgent.andEQ("limitMode", organLimitCondition.getLimitMode());
				cbAgent.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteriaAgent = cbAgent.buildCriteria();
				OrganLimit agentLimit = organLimitDAO.findOneByCriteria(buildCriteriaAgent);//渠道限额
				if(agentLimit != null && (agentLimit.getMinLimit().compareTo(organLimitCondition.getMinLimit())== -1 || 
						agentLimit.getMaxLimit().compareTo(organLimitCondition.getMaxLimit())==-1)){
					return Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"代理商限额不能比渠道限额高");
				}
				CriteriaBuilder cb = Cnd.builder(OrganLimit.class);
				cb.andEQ("organNo", ScanCodeConstants.UNLIMITED);
				cb.andEQ("limitType", organLimitCondition.getLimitType());
				cb.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
				cb.andEQ("limitMode", organLimitCondition.getLimitMode());
				cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteria = cb.buildCriteria();
				OrganLimit organLimit = organLimitDAO.findOneByCriteria(buildCriteria);//平台限额
				if(organLimit != null && (organLimit.getMinLimit().compareTo(organLimitCondition.getMinLimit())== -1 || 
						organLimit.getMaxLimit().compareTo(organLimitCondition.getMaxLimit())==-1)){
					return Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"代理商限额不能比平台限额高");
				}
			}
		}
		return Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.SUCCESS,ScanCodeConstants.VALUES,ScanCodeConstants.SUCCESS_MSG);
	}
}

