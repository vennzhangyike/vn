/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.hfepay.scancode.commons.condition.MerchantAuthDetailCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.dao.MerchantAuthDetailDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantAuthDetail;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.operator.MerchantAuthDetailService;

import net.sf.json.JSONObject;

@Service("merchantAuthDetailService")
public class MerchantAuthDetailServiceImpl implements MerchantAuthDetailService {
	public static final Logger log = LoggerFactory.getLogger(MerchantAuthDetailService.class);
	@Autowired
    private MerchantAuthDetailDAO merchantAuthDetailDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantAuthDetailCondition
	 * @return: PagingResult<MerchantAuthDetail>
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
    @Override
	public PagingResult<MerchantAuthDetail> findPagingResult(MerchantAuthDetailCondition merchantAuthDetailCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantAuthDetail.class);
		if(!Strings.isEmpty(merchantAuthDetailCondition.getId())){
			cb.andEQ("id", merchantAuthDetailCondition.getId());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantAuthDetailCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantAuthDetailCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantAuthDetailCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getAuthInterface())){
			cb.andEQ("authInterface", merchantAuthDetailCondition.getAuthInterface());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getAuthParams())){
			cb.andEQ("authParams", merchantAuthDetailCondition.getAuthParams());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getReturnAuthCode())){
			cb.andEQ("returnAuthCode", merchantAuthDetailCondition.getReturnAuthCode());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getReturnAuthMsg())){
			cb.andEQ("returnAuthMsg", merchantAuthDetailCondition.getReturnAuthMsg());
		}
		if(null != merchantAuthDetailCondition.getCreateTime()){
			cb.andEQ("createTime", merchantAuthDetailCondition.getCreateTime());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getBeginTimeStr())){
			cb.andGE("createTime", merchantAuthDetailCondition.getBeginTimeStr());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getEndTimeStr())){
			cb.andLE("createTime", merchantAuthDetailCondition.getEndTimeStr()  + " 23:59:59");
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getRemark())){
			cb.andLike("remark", merchantAuthDetailCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getTemp1())){
			cb.andEQ("temp1", merchantAuthDetailCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getTemp2())){
			cb.andEQ("temp2", merchantAuthDetailCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		if(Strings.isNotEmpty(merchantAuthDetailCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantAuthDetailCondition.getNodeSeq());
		}

		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantAuthDetailCondition.getFirst()), Long.valueOf(merchantAuthDetailCondition.getLast()));
		PagingResult<MerchantAuthDetail> page = merchantAuthDetailDAO.findPagingResult(buildCriteria);
		for (MerchantAuthDetail merchantAuthDetail : page.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+merchantAuthDetail.getChannelNo()));
				if(channelBase != null){
					merchantAuthDetail.setChannelName(channelBase.getChannelName());
				}	
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+merchantAuthDetail.getAgentNo()));
				if(agentBase != null){
					merchantAuthDetail.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantAuthDetail.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					merchantAuthDetail.setMerchantName(merchantInfo.getMerchantName());
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
	 * @param merchantAuthDetailCondition
	 * @return: List<MerchantAuthDetail>
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	@Override
	public List<MerchantAuthDetail> findAll(MerchantAuthDetailCondition merchantAuthDetailCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantAuthDetail.class);
		if(!Strings.isEmpty(merchantAuthDetailCondition.getId())){
			cb.andEQ("id", merchantAuthDetailCondition.getId());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantAuthDetailCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantAuthDetailCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantAuthDetailCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getAuthInterface())){
			cb.andEQ("authInterface", merchantAuthDetailCondition.getAuthInterface());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getAuthParams())){
			cb.andEQ("authParams", merchantAuthDetailCondition.getAuthParams());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getReturnAuthCode())){
			cb.andEQ("returnAuthCode", merchantAuthDetailCondition.getReturnAuthCode());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getReturnAuthMsg())){
			cb.andEQ("returnAuthMsg", merchantAuthDetailCondition.getReturnAuthMsg());
		}
		if(null != merchantAuthDetailCondition.getCreateTime()){
			cb.andEQ("createTime", merchantAuthDetailCondition.getCreateTime());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getBeginTimeStr())){
			cb.andGE("createTime", merchantAuthDetailCondition.getBeginTimeStr());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getEndTimeStr())){
			cb.andLE("createTime", merchantAuthDetailCondition.getEndTimeStr()  + " 23:59:59");
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getRemark())){
			cb.andLike("remark", merchantAuthDetailCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getTemp1())){
			cb.andEQ("temp1", merchantAuthDetailCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantAuthDetailCondition.getTemp2())){
			cb.andEQ("temp2", merchantAuthDetailCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantAuthDetailCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantAuthDetailCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantAuthDetailDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MerchantAuthDetail
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	@Override
	public MerchantAuthDetail findById(String id){
		return merchantAuthDetailDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantAuthDetailCondition
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	@Override
	public long insert(MerchantAuthDetailCondition merchantAuthDetailCondition){
		MerchantAuthDetail merchantAuthDetail = new MerchantAuthDetail();
		BeanUtils.copyProperties(merchantAuthDetailCondition, merchantAuthDetail);
		return merchantAuthDetailDAO.insert(merchantAuthDetail);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	@Override
	public long deleteById(String id){
		return merchantAuthDetailDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantAuthDetailDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantAuthDetailDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param merchantAuthDetailCondition
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	@Override
	public long update(MerchantAuthDetailCondition merchantAuthDetailCondition){
		MerchantAuthDetail merchantAuthDetail = new MerchantAuthDetail();
		BeanUtils.copyProperties(merchantAuthDetailCondition, merchantAuthDetail);
		return merchantAuthDetailDAO.update(merchantAuthDetail);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param merchantAuthDetailCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-21 17:19:45
	 */
	@Override
	public long updateByCriteria(MerchantAuthDetailCondition merchantAuthDetailCondition,Criteria criteria){
		MerchantAuthDetail merchantAuthDetail = new MerchantAuthDetail();
		BeanUtils.copyProperties(merchantAuthDetailCondition, merchantAuthDetail);
		return merchantAuthDetailDAO.updateByCriteria(merchantAuthDetail,criteria);
	}
	
	/** 记录统计 */
	@Override	
	public Map<String, Integer> getAuthDetail(MerchantAuthDetailCondition merchantAuthDetailCondition){
		Map<String, Integer> authMap = new HashMap<String, Integer>();
		authMap.put("authTotal", 0);
		authMap.put("autoFailTotal", 0);
		authMap.put("autoSuccessTotal", 0);
		int total = 0;
		List<MerchantAuthDetail> list = merchantAuthDetailDAO.getAuthDetail(merchantAuthDetailCondition);
		for (MerchantAuthDetail merchantAuthDetail : list) {
			int number = Integer.parseInt(merchantAuthDetail.getTemp1());
			total = total + number;
			if(ScanCodeErrorCode.SYSTEM_000000.getCode().equals(merchantAuthDetail.getReturnAuthCode())){
				authMap.put("autoSuccessTotal", number);
			}else{
				authMap.put("autoFailTotal", number);
			}
		}
		authMap.put("authTotal", total);
		return authMap;
	}
}

