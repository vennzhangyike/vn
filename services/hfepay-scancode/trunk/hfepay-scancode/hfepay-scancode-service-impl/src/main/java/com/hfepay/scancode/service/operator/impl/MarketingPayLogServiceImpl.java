/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.math.BigDecimal;
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
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MarketingPayLogCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MarketingPayLogDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MarketingPayLog;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.operator.MarketingPayLogService;

import net.sf.json.JSONObject;

@Service("MarketingPayLogService")
public class MarketingPayLogServiceImpl implements MarketingPayLogService {
	public static final Logger log = LoggerFactory.getLogger(MarketingPayLogService.class);
	
	@Autowired
    private MarketingPayLogDAO marketingPayLogDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;

	/**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param marketingPayLogCondition
	 * @return: PagingResult<MarketingPayLog>
	 * @date CreateDate : 2016-10-19 16:13:23
	 */

	@Override
	public PagingResult<MarketingPayLog> findPagingResult(MarketingPayLogCondition marketingPayLogCondition) {
		CriteriaBuilder cb = Cnd.builder(MarketingPayLog.class);
		if(!Strings.isEmpty(marketingPayLogCondition.getId())){
			cb.andEQ("id", marketingPayLogCondition.getId());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getChannelNo())){
			cb.andEQ("channelNo", marketingPayLogCondition.getChannelNo());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getOrganNo())){
			cb.andLike("organNo", marketingPayLogCondition.getOrganNo());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getMerchantNo())){
			cb.andEQ("merchantNo", marketingPayLogCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getMarkTradeNo())){
			cb.andEQ("markTradeNo", marketingPayLogCondition.getMarkTradeNo());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getTradeNo())){
			cb.andLike("tradeNo", marketingPayLogCondition.getTradeNo());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getPayCode())){
			cb.andEQ("payCode", marketingPayLogCondition.getPayCode());
		}
		if(null != marketingPayLogCondition.getTradeAmt()){
			cb.andEQ("tradeAmt", marketingPayLogCondition.getTradeAmt());
		}
		if(null != marketingPayLogCondition.getDiscountAmt()){
			cb.andEQ("discountAmt", marketingPayLogCondition.getDiscountAmt());
		}
		if(null != marketingPayLogCondition.getPaidAmt()){
			cb.andEQ("paidAmt", marketingPayLogCondition.getPaidAmt());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getDiscountType())){
			cb.andEQ("discountType", marketingPayLogCondition.getDiscountType());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getDiscountStrategy())){
			cb.andEQ("discountStrategy", marketingPayLogCondition.getDiscountStrategy());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getOrderStatus())){
			cb.andEQ("orderStatus", marketingPayLogCondition.getOrderStatus());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getCashierNo())){
			cb.andEQ("cashierNo", marketingPayLogCondition.getCashierNo());
		}
		if(null != marketingPayLogCondition.getBeginTime()){
			cb.andEQ("beginTime", marketingPayLogCondition.getBeginTime());
		}
		if(null != marketingPayLogCondition.getEndTime()){
			cb.andEQ("endTime", marketingPayLogCondition.getEndTime());
		}
		if(null != marketingPayLogCondition.getCreateTime()){
			cb.andEQ("createTime", marketingPayLogCondition.getCreateTime());
		}
		if(null != marketingPayLogCondition.getUpdateTime()){
			cb.andEQ("updateTime", marketingPayLogCondition.getUpdateTime());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getOperator())){
			cb.andEQ("operator", marketingPayLogCondition.getOperator());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getTemp1())){
			cb.andEQ("temp1", marketingPayLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getTemp2())){
			cb.andEQ("temp2", marketingPayLogCondition.getTemp2());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getTemp3())){
			cb.andEQ("temp3", marketingPayLogCondition.getTemp3());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getTemp4())){
			cb.andEQ("temp4", marketingPayLogCondition.getTemp4());
		}
		
		
		
		
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(marketingPayLogCondition.getFirst()), Long.valueOf(marketingPayLogCondition.getLast()));
		
		PagingResult<MarketingPayLog> page = marketingPayLogDAO.findPagingResult(buildCriteria);
		for (MarketingPayLog marketingPayLog : page.getResult()) {
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+marketingPayLog.getChannelNo()));
				if(channelBase != null){
					marketingPayLog.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+marketingPayLog.getOrganNo()));
				if(agentBase != null){
					marketingPayLog.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+marketingPayLog.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					marketingPayLog.setMerchantName(merchantInfo.getMerchantName());
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
	 * @param marketingPayLogCondition
	 * @return: List<MarketingPayLog>
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	
	@Override
	public List<MarketingPayLog> findAll(MarketingPayLogCondition marketingPayLogCondition) {
		CriteriaBuilder cb = Cnd.builder(MarketingPayLog.class);
		if(!Strings.isEmpty(marketingPayLogCondition.getId())){
			cb.andEQ("id", marketingPayLogCondition.getId());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getChannelNo())){
			cb.andEQ("channelNo", marketingPayLogCondition.getChannelNo());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getOrganNo())){
			cb.andLike("organNo", marketingPayLogCondition.getOrganNo());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getMerchantNo())){
			cb.andEQ("merchantNo", marketingPayLogCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getMarkTradeNo())){
			cb.andEQ("markTradeNo", marketingPayLogCondition.getMarkTradeNo());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getTradeNo())){
			cb.andLike("tradeNo", marketingPayLogCondition.getTradeNo());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getPayCode())){
			cb.andEQ("payCode", marketingPayLogCondition.getPayCode());
		}
		if(null != marketingPayLogCondition.getTradeAmt()){
			cb.andEQ("tradeAmt", marketingPayLogCondition.getTradeAmt());
		}
		if(null != marketingPayLogCondition.getDiscountAmt()){
			cb.andEQ("discountAmt", marketingPayLogCondition.getDiscountAmt());
		}
		if(null != marketingPayLogCondition.getPaidAmt()){
			cb.andEQ("paidAmt", marketingPayLogCondition.getPaidAmt());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getDiscountType())){
			cb.andEQ("discountType", marketingPayLogCondition.getDiscountType());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getDiscountStrategy())){
			cb.andEQ("discountStrategy", marketingPayLogCondition.getDiscountStrategy());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getOrderStatus())){
			cb.andEQ("orderStatus", marketingPayLogCondition.getOrderStatus());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getCashierNo())){
			cb.andEQ("cashierNo", marketingPayLogCondition.getCashierNo());
		}
		if(null != marketingPayLogCondition.getBeginTime()){
			cb.andEQ("beginTime", marketingPayLogCondition.getBeginTime());
		}
		if(null != marketingPayLogCondition.getEndTime()){
			cb.andEQ("endTime", marketingPayLogCondition.getEndTime());
		}
		if(null != marketingPayLogCondition.getCreateTime()){
			cb.andEQ("createTime", marketingPayLogCondition.getCreateTime());
		}
		if(null != marketingPayLogCondition.getUpdateTime()){
			cb.andEQ("updateTime", marketingPayLogCondition.getUpdateTime());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getOperator())){
			cb.andEQ("operator", marketingPayLogCondition.getOperator());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getTemp1())){
			cb.andEQ("temp1", marketingPayLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getTemp2())){
			cb.andEQ("temp2", marketingPayLogCondition.getTemp2());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getTemp3())){
			cb.andEQ("temp3", marketingPayLogCondition.getTemp3());
		}
		if(!Strings.isEmpty(marketingPayLogCondition.getTemp4())){
			cb.andEQ("temp4", marketingPayLogCondition.getTemp4());
		}
		
		Criteria buildCriteria = cb.buildCriteria();
		return marketingPayLogDAO.findByCriteria(buildCriteria);
	}

	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MarketingPayLog
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	
	@Override
	public MarketingPayLog findById(String id) {
		return marketingPayLogDAO.get(id);
	}

	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param marketingPayLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	
	@Override
	public long insert(MarketingPayLogCondition marketingPayLogCondition) {
		MarketingPayLog marketingPayLog = new MarketingPayLog();
		BeanUtils.copyProperties(marketingPayLogCondition, marketingPayLog);
		return marketingPayLogDAO.insert(marketingPayLog);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	
	@Override
	public long deleteById(String id) {
		return marketingPayLogDAO.deleteById(id);
	}

	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	
	@Override
	public long deleteByCriteria(Criteria criteria) {
		return marketingPayLogDAO.deleteByCriteria(criteria);
	}

	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	
	@Override
	public long countByCriteria(Criteria criteria) {
		return marketingPayLogDAO.countByCriteria(criteria);
	}

	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param marketingPayLogCondition
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	
	@Override
	public long update(MarketingPayLogCondition marketingPayLogCondition) {
		MarketingPayLog marketingPayLog = new MarketingPayLog();
		BeanUtils.copyProperties(marketingPayLogCondition, marketingPayLog);
		return marketingPayLogDAO.update(marketingPayLog);
	}

	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param marketingPayLogCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	
	@Override
	public long updateByCriteria(MarketingPayLogCondition marketingPayLogCondition, Criteria criteria) {
		MarketingPayLog marketingPayLog = new MarketingPayLog();
		BeanUtils.copyProperties(marketingPayLogCondition, marketingPayLog);
		return marketingPayLogDAO.updateByCriteria(marketingPayLog,criteria);
	}

	/** 金额统计 */
	@Override
	public Map<String, BigDecimal> getAmtStatic(List<MarketingPayLog> marketingPayLogs) {
		Map<String, BigDecimal> amtStatic = new HashMap<String, BigDecimal>();
		BigDecimal orderTotalAmt = BigDecimal.ZERO;//交易总金额
		BigDecimal yhTotalAmt = BigDecimal.ZERO;//优惠金额
		BigDecimal sfTotalAmt = BigDecimal.ZERO;//实付金额
		for (MarketingPayLog marketingPayLog : marketingPayLogs) {
			if(marketingPayLog.getTradeAmt()!=null){
				orderTotalAmt = orderTotalAmt.add(marketingPayLog.getTradeAmt());//交易总金额
			}
			if(marketingPayLog.getDiscountAmt() !=null){
				yhTotalAmt = yhTotalAmt.add(marketingPayLog.getDiscountAmt());//优惠金额
			}
			if(marketingPayLog.getPaidAmt() !=null){
				sfTotalAmt = sfTotalAmt.add(marketingPayLog.getPaidAmt());//实付金额
			}
			
		}
		amtStatic.put("orderTotalAmt", orderTotalAmt);
		amtStatic.put("yhTotalAmt", yhTotalAmt);
		amtStatic.put("sfTotalAmt", sfTotalAmt);
		return amtStatic;
	}
	
}

