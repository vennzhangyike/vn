/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.operator.impl;

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
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.contants.TradeType;
import com.hfepay.scancode.commons.dao.MerchantCostDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.operator.MerchantCostService;

import net.sf.json.JSONObject;

@Service("merchantCostService")
public class MerchantCostServiceImpl implements MerchantCostService {
	public static final Logger log = LoggerFactory.getLogger(MerchantCostServiceImpl.class);
	
	@Autowired
    private MerchantCostDAO merchantCostDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @return: PagingResult<MerchantCost>
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
    @Override
	public PagingResult<MerchantCost> findPagingResult(MerchantCostCondition merchantCostCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantCost.class);
		if(!Strings.isEmpty(merchantCostCondition.getId())){
			cb.andEQ("id", merchantCostCondition.getId());
		}
		if(!Strings.isEmpty(merchantCostCondition.getTradeNo())){
			cb.andEQ("tradeNo", merchantCostCondition.getTradeNo());
		}
		if(!Strings.isEmpty(merchantCostCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantCostCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantCostCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantCostCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantCostCondition.getAgentLevel())){
			cb.andEQ("agentLevel", merchantCostCondition.getAgentLevel());
		}
		if(!Strings.isEmpty(merchantCostCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCostCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCostCondition.getQrCode())){
			cb.andEQ("qrCode", merchantCostCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantCostCondition.getPayCode())){
			cb.andEQ("payCode", merchantCostCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantCostCondition.getType())){
			cb.andEQ("type", merchantCostCondition.getType());
		}
		if(null != merchantCostCondition.getAmount()){
			cb.andEQ("amount", merchantCostCondition.getAmount());
		}
		if(null != merchantCostCondition.getMerchantRate()){
			cb.andEQ("merchantRate", merchantCostCondition.getMerchantRate());
		}
		if(null != merchantCostCondition.getFixedAmount()){
			cb.andEQ("fixedAmount", merchantCostCondition.getFixedAmount());
		}
		if(null != merchantCostCondition.getMerchantCost()){
			cb.andEQ("merchantCost", merchantCostCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(merchantCostCondition.getStatus())){
			cb.andEQ("status", merchantCostCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantCostCondition.getBeginTimeStr())){
			cb.andGE("createTime", merchantCostCondition.getBeginTimeStr());
		}
		if(!Strings.isEmpty(merchantCostCondition.getEndTimeStr())){
			cb.andLE("createTime", merchantCostCondition.getEndTimeStr()  + " 23:59:59");
		}

		if(!Strings.isEmpty(merchantCostCondition.getRemark())){
			cb.andLike("remark", merchantCostCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantCostCondition.getTemp1())){
			cb.andEQ("temp1", merchantCostCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantCostCondition.getTemp2())){
			cb.andEQ("temp2", merchantCostCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantCostCondition.getFirst()), Long.valueOf(merchantCostCondition.getLast()));
		PagingResult<MerchantCost> page = merchantCostDAO.findPagingResult(buildCriteria);
		for (MerchantCost merchantCost : page.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+merchantCost.getChannelNo()));
				if(channelBase != null){
					merchantCost.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+merchantCost.getAgentNo()));
				if(agentBase != null){
					merchantCost.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantCost.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					merchantCost.setMerchantName(merchantInfo.getMerchantName());
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
	 * @param merchantCostCondition
	 * @return: List<MerchantCost>
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public List<MerchantCost> findAll(MerchantCostCondition merchantCostCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantCost.class);
		if(!Strings.isEmpty(merchantCostCondition.getId())){
			cb.andEQ("id", merchantCostCondition.getId());
		}
		if(!Strings.isEmpty(merchantCostCondition.getTradeNo())){
			cb.andEQ("tradeNo", merchantCostCondition.getTradeNo());
		}
		if(!Strings.isEmpty(merchantCostCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantCostCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantCostCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantCostCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantCostCondition.getAgentLevel())){
			cb.andEQ("agentLevel", merchantCostCondition.getAgentLevel());
		}
		if(!Strings.isEmpty(merchantCostCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCostCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCostCondition.getQrCode())){
			cb.andEQ("qrCode", merchantCostCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantCostCondition.getPayCode())){
			cb.andEQ("payCode", merchantCostCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantCostCondition.getType())){
			cb.andEQ("type", merchantCostCondition.getType());
		}
		if(null != merchantCostCondition.getAmount()){
			cb.andEQ("amount", merchantCostCondition.getAmount());
		}
		if(null != merchantCostCondition.getMerchantRate()){
			cb.andEQ("merchantRate", merchantCostCondition.getMerchantRate());
		}
		if(null != merchantCostCondition.getFixedAmount()){
			cb.andEQ("fixedAmount", merchantCostCondition.getFixedAmount());
		}
		if(null != merchantCostCondition.getMerchantCost()){
			cb.andEQ("merchantCost", merchantCostCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(merchantCostCondition.getStatus())){
			cb.andEQ("status", merchantCostCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantCostCondition.getBeginTimeStr())){
			cb.andGE("createTime", merchantCostCondition.getBeginTimeStr());
		}
		if(!Strings.isEmpty(merchantCostCondition.getEndTimeStr())){
			cb.andLE("createTime", merchantCostCondition.getEndTimeStr()  + " 23:59:59");
		}

		if(!Strings.isEmpty(merchantCostCondition.getRemark())){
			cb.andLike("remark", merchantCostCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantCostCondition.getTemp1())){
			cb.andEQ("temp1", merchantCostCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantCostCondition.getTemp2())){
			cb.andEQ("temp2", merchantCostCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantCostDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MerchantCost
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public MerchantCost findById(String id){
		return merchantCostDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public long insert(MerchantCostCondition merchantCostCondition){
		MerchantCost merchantCost = new MerchantCost();
		BeanUtils.copyProperties(merchantCostCondition, merchantCost);
		return merchantCostDAO.insert(merchantCost);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public long deleteById(String id){
		return merchantCostDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantCostDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantCostDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public long update(MerchantCostCondition merchantCostCondition){
		MerchantCost merchantCost = new MerchantCost();
		BeanUtils.copyProperties(merchantCostCondition, merchantCost);
		return merchantCostDAO.update(merchantCost);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public long updateByCriteria(MerchantCostCondition merchantCostCondition,Criteria criteria){
		MerchantCost merchantCost = new MerchantCost();
		BeanUtils.copyProperties(merchantCostCondition, merchantCost);
		return merchantCostDAO.updateByCriteria(merchantCost,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantCostDAO.updateStatus(id,status);
	}	
		
	
	/**
	 * 金额统计
	 * @param merchantCosts
	 * @param type 进出账类型，01:提现、02:支付
	 * @return
	 */
	@Override	
	public Map<String, BigDecimal> getAmtStatistics(MerchantCostCondition merchantCostCondition){
		if(!Strings.isEmpty(merchantCostCondition.getEndTimeStr())){
			merchantCostCondition.setEndTimeStr(merchantCostCondition.getEndTimeStr()  + " 23:59:59");
		}
		List<MerchantCost> merchantCosts = merchantCostDAO.getAmtStatistics(merchantCostCondition);
		Map<String, BigDecimal> amtStatic = new HashMap<String, BigDecimal>();
		BigDecimal orderTotalAmt = BigDecimal.ZERO;//交易总金额
		BigDecimal txTotalAmt = BigDecimal.ZERO;//提现总金额
		BigDecimal zfbTotalAmt = BigDecimal.ZERO;//支付宝
		BigDecimal wxgzhTotalAmt = BigDecimal.ZERO;//微信公众号
		BigDecimal qqTotalAmt = BigDecimal.ZERO;//微信公众号
		BigDecimal wxTotalAmt = BigDecimal.ZERO;//微信
		BigDecimal totalCost = BigDecimal.ZERO;//总成本
		for (MerchantCost merchantCost : merchantCosts) {
			if(TradeType.TRADE_TYPE_PAY.getCode().equals(merchantCost.getType())){				
				orderTotalAmt = orderTotalAmt.add(merchantCost.getAmount());
			}else if(TradeType.TRADE_TYPE_WITHDRAW.getCode().equals(merchantCost.getType())){
				txTotalAmt = txTotalAmt.add(merchantCost.getAmount());
			}
			if(PayCode.PAYCODE_ZFBSMZF.getCode().equals(merchantCost.getPayCode())){				
				zfbTotalAmt = zfbTotalAmt.add(merchantCost.getAmount());
			}else if(PayCode.PAYCODE_WXGZHZF.getCode().equals(merchantCost.getPayCode())){
				wxgzhTotalAmt = wxgzhTotalAmt.add(merchantCost.getAmount());
			}else if(PayCode.PAYCODE_WXSMZF.getCode().equals(merchantCost.getPayCode())){
				wxTotalAmt = wxTotalAmt.add(merchantCost.getAmount());
			}else if(PayCode.PAYCODE_QQZF.getCode().equals(merchantCost.getPayCode())){
				qqTotalAmt = qqTotalAmt.add(merchantCost.getAmount());
			}
			totalCost = totalCost.add(merchantCost.getMerchantCost());
		}
		amtStatic.put("orderTotalAmt", orderTotalAmt);
		amtStatic.put("txTotalAmt", txTotalAmt);
		amtStatic.put("zfbTotalAmt", zfbTotalAmt);
		amtStatic.put("wxgzhTotalAmt", wxgzhTotalAmt);
		amtStatic.put("wxTotalAmt", wxTotalAmt);
		amtStatic.put("totalCost", totalCost);
		amtStatic.put("qqTotalAmt", qqTotalAmt);
		return amtStatic;
	}
	
}

