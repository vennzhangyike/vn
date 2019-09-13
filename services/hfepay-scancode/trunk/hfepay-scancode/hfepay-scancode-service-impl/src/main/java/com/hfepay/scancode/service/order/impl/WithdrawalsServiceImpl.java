/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.service.order.impl;

import java.math.BigDecimal;
import java.util.Date;
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
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Dates.DF;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.DateFlagEnu;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.WithdrawalsDAO;
import com.hfepay.scancode.commons.dto.WithDrawsTotalDTO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.Withdrawals;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.impl.MerchantInfoServiceImpl;
import com.hfepay.scancode.service.order.WithdrawalsService;
import com.hfepay.scancode.service.utils.OrderIDUtils;

@Service("withdrawalsService")
public class WithdrawalsServiceImpl implements WithdrawalsService {
	public static final Logger log = LoggerFactory.getLogger(MerchantInfoServiceImpl.class);
	
	@Autowired
    private WithdrawalsDAO withdrawalsDAO;
    
	@Autowired
	private OrderIDUtils orderUtils;
	
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private RedisSharedCache redisSharedCache;
	
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: PagingResult<Withdrawals>
     * @throws Exception 
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
    @Override
	public PagingResult<Withdrawals> findPagingResult(WithdrawalsCondition withdrawalsCondition) throws Exception{
		CriteriaBuilder cb = Cnd.builder(Withdrawals.class);
		if(!Strings.isEmpty(withdrawalsCondition.getId())){
			cb.andEQ("id", withdrawalsCondition.getId());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getTradeNo())){
			cb.andLike("tradeNo", withdrawalsCondition.getTradeNo());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getChannelNo())){
			cb.andEQ("channelNo", withdrawalsCondition.getChannelNo());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getAgentNo())){
			cb.andEQ("agentNo", withdrawalsCondition.getAgentNo());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", withdrawalsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getPayCode())){
			cb.andEQ("payCode", withdrawalsCondition.getPayCode());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getRateDetail())){
			cb.andEQ("rateDetail", withdrawalsCondition.getRateDetail());
		}
		if(null != withdrawalsCondition.getOrderAmt()){
			cb.andEQ("orderAmt", withdrawalsCondition.getOrderAmt());
		}
		if(null != withdrawalsCondition.getDrawFee()){
			cb.andEQ("drawFee", withdrawalsCondition.getDrawFee());
		}
		if(null != withdrawalsCondition.getTradeFee()){
			cb.andEQ("tradeFee", withdrawalsCondition.getTradeFee());
		}
		if(null != withdrawalsCondition.getMerchantCost()){
			cb.andEQ("merchantCost", withdrawalsCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getPayTradeNo())){
			cb.andEQ("payTradeNo", withdrawalsCondition.getPayTradeNo());
		}
		if(null != withdrawalsCondition.getBeginTime()){
			cb.andGE("beginTime", withdrawalsCondition.getBeginTime());
		}
		if(null != withdrawalsCondition.getEndTime()){
			cb.andLE("beginTime", withdrawalsCondition.getEndTime());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getSettleTime())){
			cb.andEQ("settleTime", withdrawalsCondition.getSettleTime());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getSettleMerchant())){
			cb.andEQ("settleMerchant", withdrawalsCondition.getSettleMerchant());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getRefundStatus())){
			cb.andEQ("refundStatus", withdrawalsCondition.getRefundStatus());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getBatchId())){
			cb.andEQ("batchId", withdrawalsCondition.getBatchId());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getResultCode())){
			cb.andEQ("resultCode", withdrawalsCondition.getResultCode());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getResultInfo())){
			cb.andEQ("resultInfo", withdrawalsCondition.getResultInfo());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getOptCode())){
			cb.andEQ("optCode", withdrawalsCondition.getOptCode());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getRecordStatus())){
			cb.andEQ("recordStatus", withdrawalsCondition.getRecordStatus());
		}

		if(!Strings.isEmpty(withdrawalsCondition.getOperator())){
			cb.andEQ("operator", withdrawalsCondition.getOperator());
		}

		if(!Strings.isEmpty(withdrawalsCondition.getRemark())){
			cb.andLike("remark", withdrawalsCondition.getRemark());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getTemp1())){
			cb.andEQ("temp1", withdrawalsCondition.getTemp1());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getTemp2())){
			cb.andEQ("temp2", withdrawalsCondition.getTemp2());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getTemp3())){
			cb.andEQ("temp3", withdrawalsCondition.getTemp3());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getTemp4())){
			cb.andEQ("temp4", withdrawalsCondition.getTemp4());
		}
		cb.orderBy("beginTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(withdrawalsCondition.getFirst()), Long.valueOf(withdrawalsCondition.getLast()));
		
		PagingResult<Withdrawals> page = withdrawalsDAO.findPagingResult(buildCriteria);
		for (Withdrawals withdrawals : page.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+withdrawals.getChannelNo()));
				if(channelBase != null){
					withdrawals.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+withdrawals.getAgentNo()));
				if(agentBase != null){
					withdrawals.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+withdrawals.getMerchantNo()));
				if(merchantInfo != null){
					withdrawals.setMerchantName(merchantInfo.getMerchantName());
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
	 * @param withdrawalsCondition
	 * @return: List<Withdrawals>
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	@Override
	public List<Withdrawals> findAll(WithdrawalsCondition withdrawalsCondition){
		CriteriaBuilder cb = Cnd.builder(Withdrawals.class);
		if(!Strings.isEmpty(withdrawalsCondition.getId())){
			cb.andEQ("id", withdrawalsCondition.getId());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getTradeNo())){
			cb.andEQ("tradeNo", withdrawalsCondition.getTradeNo());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getChannelNo())){
			cb.andEQ("channelNo", withdrawalsCondition.getChannelNo());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getAgentNo())){
			cb.andEQ("agentNo", withdrawalsCondition.getAgentNo());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", withdrawalsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getPayCode())){
			cb.andEQ("payCode", withdrawalsCondition.getPayCode());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getRateDetail())){
			cb.andEQ("rateDetail", withdrawalsCondition.getRateDetail());
		}
		if(null != withdrawalsCondition.getOrderAmt()){
			cb.andEQ("orderAmt", withdrawalsCondition.getOrderAmt());
		}
		if(null != withdrawalsCondition.getDrawFee()){
			cb.andEQ("drawFee", withdrawalsCondition.getDrawFee());
		}
		if(null != withdrawalsCondition.getTradeFee()){
			cb.andEQ("tradeFee", withdrawalsCondition.getTradeFee());
		}
		if(null != withdrawalsCondition.getMerchantCost()){
			cb.andEQ("merchantCost", withdrawalsCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getPayTradeNo())){
			cb.andEQ("payTradeNo", withdrawalsCondition.getPayTradeNo());
		}
		if(null != withdrawalsCondition.getBeginTime()){
			cb.andGE("beginTime", withdrawalsCondition.getBeginTime());
		}
		if(null != withdrawalsCondition.getEndTime()){
			cb.andLE("beginTime", withdrawalsCondition.getEndTime());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getSettleTime())){
			cb.andEQ("settleTime", withdrawalsCondition.getSettleTime());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getSettleMerchant())){
			cb.andEQ("settleMerchant", withdrawalsCondition.getSettleMerchant());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getRefundStatus())){
			cb.andEQ("refundStatus", withdrawalsCondition.getRefundStatus());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getBatchId())){
			cb.andEQ("batchId", withdrawalsCondition.getBatchId());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getResultCode())){
			cb.andEQ("resultCode", withdrawalsCondition.getResultCode());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getResultInfo())){
			cb.andEQ("resultInfo", withdrawalsCondition.getResultInfo());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getOptCode())){
			cb.andEQ("optCode", withdrawalsCondition.getOptCode());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getRecordStatus())){
			cb.andEQ("recordStatus", withdrawalsCondition.getRecordStatus());
		}

		if(!Strings.isEmpty(withdrawalsCondition.getOperator())){
			cb.andEQ("operator", withdrawalsCondition.getOperator());
		}

		if(!Strings.isEmpty(withdrawalsCondition.getRemark())){
			cb.andLike("remark", withdrawalsCondition.getRemark());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getTemp1())){
			cb.andEQ("temp1", withdrawalsCondition.getTemp1());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getTemp2())){
			cb.andEQ("temp2", withdrawalsCondition.getTemp2());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getTemp3())){
			cb.andEQ("temp3", withdrawalsCondition.getTemp3());
		}
		if(!Strings.isEmpty(withdrawalsCondition.getTemp4())){
			cb.andEQ("temp4", withdrawalsCondition.getTemp4());
		}
		cb.orderBy("beginTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		return withdrawalsDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: Withdrawals
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	@Override
	public Withdrawals findById(String id){
		return withdrawalsDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	@Override
	public long insert(WithdrawalsCondition withdrawalsCondition){
		Withdrawals withdrawals = new Withdrawals();
		BeanUtils.copyProperties(withdrawalsCondition, withdrawals);
		return withdrawalsDAO.insert(withdrawals);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	@Override
	public Withdrawals createWithdraws(WithdrawalsCondition withdrawalsCondition){
		Withdrawals withdrawals = new Withdrawals();
		withdrawalsCondition.setTradeNo(orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "TX"));
		withdrawalsCondition.setOrderAmt(new BigDecimal(0));
		withdrawalsCondition.setDrawFee(new BigDecimal(0));
		withdrawalsCondition.setTradeFee(new BigDecimal(0));
		withdrawalsCondition.setMerchantCost(new BigDecimal(0));
		BeanUtils.copyProperties(withdrawalsCondition, withdrawals);
		withdrawalsDAO.insert(withdrawals);
		return withdrawals;
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	@Override
	public long deleteById(String id){
		return withdrawalsDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return withdrawalsDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return withdrawalsDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	@Override
	public long update(WithdrawalsCondition withdrawalsCondition){
		Withdrawals withdrawals = new Withdrawals();
		BeanUtils.copyProperties(withdrawalsCondition, withdrawals);
		return withdrawalsDAO.update(withdrawals);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param withdrawalsCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-30 09:32:37
	 */
	@Override
	public long updateByCriteria(WithdrawalsCondition withdrawalsCondition,Criteria criteria){
		Withdrawals withdrawals = new Withdrawals();
		BeanUtils.copyProperties(withdrawalsCondition, withdrawals);
		return withdrawalsDAO.updateByCriteria(withdrawals,criteria);
	}

	@Override
	public Map<String, String> getWalleInfo(String merchantNo) throws Exception {
		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantNo);
		Map<String, String> walleMap = merchantBusinessService.merchantWallet(merchantInfo.getPlatformMerchantNo());
		if (null != walleMap) {
			
		}
		return null;
	}
	
	/** 金额统计 */
	@Override
	public Map<String, BigDecimal> getAmtStatistics(WithdrawalsCondition withdrawalsCondition) {
		List<Withdrawals> withdrawalsList = withdrawalsDAO.getAmtStatistics(withdrawalsCondition);
		Map<String, BigDecimal> amtStatic = new HashMap<String, BigDecimal>();
		BigDecimal txTotalAmt = BigDecimal.ZERO;//提现总金额
		BigDecimal zfbTotalAmt = BigDecimal.ZERO;//支付宝
		BigDecimal wxgzhTotalAmt = BigDecimal.ZERO;//微信公众号
		BigDecimal qqTotalAmt = BigDecimal.ZERO;//微信公众号
		BigDecimal wxTotalAmt = BigDecimal.ZERO;//微信
		BigDecimal totalCost = BigDecimal.ZERO;//总成本
		for (Withdrawals withdrawals : withdrawalsList) {
			txTotalAmt = txTotalAmt.add(withdrawals.getOrderAmt());
			if(PayCode.PAYCODE_ZFBSMZF.getCode().equals(withdrawals.getPayCode())){				
				zfbTotalAmt = zfbTotalAmt.add(withdrawals.getOrderAmt());
			}else if(PayCode.PAYCODE_WXGZHZF.getCode().equals(withdrawals.getPayCode())){
				wxgzhTotalAmt = wxgzhTotalAmt.add(withdrawals.getOrderAmt());
			}else if(PayCode.PAYCODE_WXSMZF.getCode().equals(withdrawals.getPayCode())){
				wxTotalAmt = wxTotalAmt.add(withdrawals.getOrderAmt());
			}
			else if(PayCode.PAYCODE_QQZF.getCode().equals(withdrawals.getPayCode())){
				qqTotalAmt = qqTotalAmt.add(withdrawals.getOrderAmt());
			}
			totalCost = totalCost.add(withdrawals.getMerchantCost());
		}
		amtStatic.put("txTotalAmt", txTotalAmt);
		amtStatic.put("zfbTotalAmt", zfbTotalAmt);
		amtStatic.put("wxgzhTotalAmt", wxgzhTotalAmt);
		amtStatic.put("wxTotalAmt", wxTotalAmt);
		amtStatic.put("totalCost", totalCost);
		amtStatic.put("qqTotalAmt", qqTotalAmt);
		return amtStatic;
	}

	@Override
	public List<WithDrawsTotalDTO> getWithDrawsMoneyTotal(String merchantNo, String payCode) {
		log.info("###########提现金额统计 [merchantNo:"+merchantNo+",payCode:"+payCode+"]###########");
		
		return withdrawalsDAO.getWithDrawsMoneyTotal(Dates.format(DF.yyyy_MM_dd, new Date()), merchantNo, payCode);
	}

	@Override
	public BigDecimal getTotalMoney(String date, String type, String merchantNo) {
		int length = date.length();
		int dateLength = 4;//默认年的长度 YYYY-MM-DD
		if(type.equals(DateFlagEnu.DAY.getCode())){
			dateLength=10;
		}
		else if(type.equals(DateFlagEnu.MONTH.getCode())){
			dateLength=7;		
		}
		else if(type.equals(DateFlagEnu.YEAR.getCode())){
			dateLength=4;
		}
		if(length<dateLength){
			throw new RuntimeException("时间格式有误."+type+" 至少需要的长度为"+dateLength);
		}
		return withdrawalsDAO.getTotalMoney(date.substring(0, dateLength),merchantNo);
	}

	@Override
	public List<Withdrawals> findAllByInit(WithdrawalsCondition withdrawalsCondition) {
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);

		if(null != withdrawalsCondition.getBeginTime()){
			cb.andLT("beginTime", withdrawalsCondition.getBeginTime());
		}

		if(!Strings.isEmpty(withdrawalsCondition.getResultCode())){
			cb.andEQ("resultCode", withdrawalsCondition.getResultCode());
		}
		
		Criteria buildCriteria = cb.buildCriteria();
		return withdrawalsDAO.findByCriteria(buildCriteria);
	}

	
	/**
	 * 
	 * @author liushuyu
	 * Desc 按条件查询 
	 * @param withdrawals
	 * @return
	 */
	@Override
	public Withdrawals queryWithdrawals(Withdrawals withdrawals) {
		CriteriaBuilder cb = Cnd.builder(Withdrawals.class);
		if(!Strings.isEmpty(withdrawals.getId())){
			cb.andEQ("id", withdrawals.getId());
		}
		if(!Strings.isEmpty(withdrawals.getTradeNo())){
			cb.andEQ("tradeNo", withdrawals.getTradeNo());
		}
		if(!Strings.isEmpty(withdrawals.getChannelNo())){
			cb.andEQ("channelNo", withdrawals.getChannelNo());
		}
		if(!Strings.isEmpty(withdrawals.getAgentNo())){
			cb.andEQ("agentNo", withdrawals.getAgentNo());
		}
		if(!Strings.isEmpty(withdrawals.getMerchantNo())){
			cb.andEQ("merchantNo", withdrawals.getMerchantNo());
		}
		if(!Strings.isEmpty(withdrawals.getPayCode())){
			cb.andEQ("payCode", withdrawals.getPayCode());
		}
		if(!Strings.isEmpty(withdrawals.getRateDetail())){
			cb.andEQ("rateDetail", withdrawals.getRateDetail());
		}
		if(null != withdrawals.getOrderAmt()){
			cb.andEQ("orderAmt", withdrawals.getOrderAmt());
		}
		if(null != withdrawals.getDrawFee()){
			cb.andEQ("drawFee", withdrawals.getDrawFee());
		}
		if(null != withdrawals.getTradeFee()){
			cb.andEQ("tradeFee", withdrawals.getTradeFee());
		}
		if(null != withdrawals.getMerchantCost()){
			cb.andEQ("merchantCost", withdrawals.getMerchantCost());
		}
		if(!Strings.isEmpty(withdrawals.getPayTradeNo())){
			cb.andEQ("payTradeNo", withdrawals.getPayTradeNo());
		}
		if(null != withdrawals.getBeginTime()){
			cb.andGE("beginTime", withdrawals.getBeginTime());
		}
		if(null != withdrawals.getEndTime()){
			cb.andLE("beginTime", withdrawals.getEndTime());
		}
		if(!Strings.isEmpty(withdrawals.getSettleTime())){
			cb.andEQ("settleTime", withdrawals.getSettleTime());
		}
		if(!Strings.isEmpty(withdrawals.getSettleMerchant())){
			cb.andEQ("settleMerchant", withdrawals.getSettleMerchant());
		}
		if(!Strings.isEmpty(withdrawals.getRefundStatus())){
			cb.andEQ("refundStatus", withdrawals.getRefundStatus());
		}
		if(!Strings.isEmpty(withdrawals.getBatchId())){
			cb.andEQ("batchId", withdrawals.getBatchId());
		}
		if(!Strings.isEmpty(withdrawals.getResultCode())){
			cb.andEQ("resultCode", withdrawals.getResultCode());
		}
		if(!Strings.isEmpty(withdrawals.getResultInfo())){
			cb.andEQ("resultInfo", withdrawals.getResultInfo());
		}
		if(!Strings.isEmpty(withdrawals.getOptCode())){
			cb.andEQ("optCode", withdrawals.getOptCode());
		}
		if(!Strings.isEmpty(withdrawals.getRecordStatus())){
			cb.andEQ("recordStatus", withdrawals.getRecordStatus());
		}

		if(!Strings.isEmpty(withdrawals.getOperator())){
			cb.andEQ("operator", withdrawals.getOperator());
		}

		if(!Strings.isEmpty(withdrawals.getRemark())){
			cb.andLike("remark", withdrawals.getRemark());
		}
		if(!Strings.isEmpty(withdrawals.getTemp1())){
			cb.andEQ("temp1", withdrawals.getTemp1());
		}
		if(!Strings.isEmpty(withdrawals.getTemp2())){
			cb.andEQ("temp2", withdrawals.getTemp2());
		}
		if(!Strings.isEmpty(withdrawals.getTemp3())){
			cb.andEQ("temp3", withdrawals.getTemp3());
		}
		if(!Strings.isEmpty(withdrawals.getTemp4())){
			cb.andEQ("temp4", withdrawals.getTemp4());
		}
		cb.orderBy("beginTime", Order.DESC);
		Criteria criteria = cb.buildCriteria();
		List<Withdrawals> withdrawalss=withdrawalsDAO.findByCriteria(criteria);
		if(withdrawalss!=null&&withdrawalss.size()>0){
			return withdrawalss.get(0);
		}
		return null;
	}
	
	
}

