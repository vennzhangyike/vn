/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.order.impl;

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
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.contants.DateFlagEnu;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.OrderPayDAO;
import com.hfepay.scancode.commons.dto.OrderPayStaticDTO;
import com.hfepay.scancode.commons.dto.OrderPayTotalDTO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.service.order.OrderPayService;
import com.hfepay.scancode.service.utils.OrderIDUtils;

@Service("orderPayService")
public class OrderPayServiceImpl implements OrderPayService {
	public static final Logger log = LoggerFactory.getLogger(OrderPayServiceImpl.class);

	@Autowired
    private OrderPayDAO orderPayDAO;
    
	@Autowired
	private OrderIDUtils orderUtils;
	
	@Autowired
	private RedisSharedCache redisSharedCache;
    /**
	 * 列表(分页)
	 * @param orderPayCondition 
	 *
	 * @author panq
     * @throws Exception 
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
    @Override
	public PagingResult<OrderPay> findPagingResult(OrderPayCondition orderPayCondition) throws Exception{
		CriteriaBuilder cb = Cnd.builder(OrderPay.class);
		if(!Strings.isEmpty(orderPayCondition.getId())){
			cb.andEQ("id", orderPayCondition.getId());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayNo())){
			cb.andLike("payNo", orderPayCondition.getPayNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getTradeNo())){
			cb.andLike("tradeNo", orderPayCondition.getTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getChannelNo())){
			cb.andEQ("channelNo", orderPayCondition.getChannelNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getAgentNo())){
			cb.andEQ("agentNo", orderPayCondition.getAgentNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getMerchantNo())){
			cb.andEQ("merchantNo", orderPayCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getQrCode())){
			cb.andEQ("qrCode", orderPayCondition.getQrCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayCode())){
			cb.andEQ("payCode", orderPayCondition.getPayCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getTradeType())){
			cb.andEQ("tradeType", orderPayCondition.getTradeType());
		}
		if(null != orderPayCondition.getOrderAmt()){
			cb.andEQ("orderAmt", orderPayCondition.getOrderAmt());
		}
		if(!Strings.isEmpty(orderPayCondition.getProductName())){
			cb.andEQ("productName", orderPayCondition.getProductName());
		}
		if(!Strings.isEmpty(orderPayCondition.getProductDesc())){
			cb.andEQ("productDesc", orderPayCondition.getProductDesc());
		}
		if(!Strings.isEmpty(orderPayCondition.getBuyerId())){
			cb.andEQ("buyerId", orderPayCondition.getBuyerId());
		}
		if(!Strings.isEmpty(orderPayCondition.getBuyerAccount())){
			cb.andEQ("buyerAccount", orderPayCondition.getBuyerAccount());
		}
		if(null != orderPayCondition.getTotalAmount()){
			cb.andEQ("totalAmount", orderPayCondition.getTotalAmount());
		}
		if(null != orderPayCondition.getBuyerPayAmount()){
			cb.andEQ("buyerPayAmount", orderPayCondition.getBuyerPayAmount());
		}
		if(null != orderPayCondition.getPointAmount()){
			cb.andEQ("pointAmount", orderPayCondition.getPointAmount());
		}
		if(!Strings.isEmpty(orderPayCondition.getBusinessType())){
			cb.andEQ("businessType", orderPayCondition.getBusinessType());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayType())){
			cb.andEQ("payType", orderPayCondition.getPayType());
		}
		if(!Strings.isEmpty(orderPayCondition.getIsMainPay())){
			cb.andEQ("isMainPay", orderPayCondition.getIsMainPay());
		}
		if(!Strings.isEmpty(orderPayCondition.getAccountTime())){
			cb.andEQ("accountTime", orderPayCondition.getAccountTime());
		}
		if(!Strings.isEmpty(orderPayCondition.getErrorCode())){
			cb.andEQ("errorCode", orderPayCondition.getErrorCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getErrorMsg())){
			cb.andEQ("errorMsg", orderPayCondition.getErrorMsg());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayTradeNo())){
			cb.andEQ("payTradeNo", orderPayCondition.getPayTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayOrderType())){
			cb.andEQ("payOrderType", orderPayCondition.getPayOrderType());
		}
		if(!Strings.isEmpty(orderPayCondition.getNotifyUrl())){
			cb.andEQ("notifyUrl", orderPayCondition.getNotifyUrl());
		}
		if(!Strings.isEmpty(orderPayCondition.getReturnUrl())){
			cb.andEQ("returnUrl", orderPayCondition.getReturnUrl());
		}
		if(!Strings.isEmpty(orderPayCondition.getRefundStatus())){
			cb.andEQ("refundStatus", orderPayCondition.getRefundStatus());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayStatus())){
			cb.andEQ("payStatus", orderPayCondition.getPayStatus());
		}
		if(!Strings.isEmpty(orderPayCondition.getBatchId())){
			cb.andEQ("batchId", orderPayCondition.getBatchId());
		}
		if(!Strings.isEmpty(orderPayCondition.getOptCode())){
			cb.andEQ("optCode", orderPayCondition.getOptCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", orderPayCondition.getRecordStatus());
		}
		if(null != orderPayCondition.getBeginTime()){
			cb.andGE("beginTime", orderPayCondition.getBeginTime());
		}
		if(null != orderPayCondition.getEndTime()){
			cb.andLE("beginTime", orderPayCondition.getEndTime());
		}

		if(!Strings.isEmpty(orderPayCondition.getOperator())){
			cb.andEQ("operator", orderPayCondition.getOperator());
		}

		if(!Strings.isEmpty(orderPayCondition.getRemark())){
			cb.andLike("remark", orderPayCondition.getRemark());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp1())){
			cb.andEQ("temp1", orderPayCondition.getTemp1());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp2())){
			cb.andEQ("temp2", orderPayCondition.getTemp2());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp3())){
			cb.andEQ("temp3", orderPayCondition.getTemp3());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp4())){
			cb.andEQ("temp4", orderPayCondition.getTemp4());
		}
		if(!Strings.isEmpty(orderPayCondition.getCashierNo())){
			cb.andEQ("cashier_no", orderPayCondition.getCashierNo());
		}
		if(null != orderPayCondition.getMerchantCost()){
			cb.andEQ("merchant_cost", orderPayCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(orderPayCondition.getStoreNo())){
			cb.andEQ("store_no", orderPayCondition.getStoreNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getQueryStartTime())){
			cb.andGE("beginTime", orderPayCondition.getQueryStartTime());
		}
		if(!Strings.isEmpty(orderPayCondition.getQueryEndTime())){
			cb.andLE("beginTime", orderPayCondition.getQueryEndTime());
		}
		
		if(!Strings.isEmpty(orderPayCondition.getTradeSource())){
			cb.andEQ("tradeSource", orderPayCondition.getTradeSource());
		}
		if(!Strings.isEmpty(orderPayCondition.getExtraTradeNo())){
			cb.andEQ("extraTradeNo", orderPayCondition.getExtraTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getChannelTradeNo())){
			cb.andEQ("channelTradeNo", orderPayCondition.getChannelTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getExtraCallBack())){
			cb.andEQ("extraCallBack", orderPayCondition.getExtraCallBack());
		}
		
		cb.orderBy("beginTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(orderPayCondition.getOrderBy())){
			if(orderPayCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = orderPayCondition.getOrderBy().split(",");
				String[] orders = orderPayCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(orderPayCondition.getOrderBy(), Order.valueOf(orderPayCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(orderPayCondition.getFirst()), Long.valueOf(orderPayCondition.getLast()));
		PagingResult<OrderPay> page = orderPayDAO.findPagingResult(buildCriteria);
		for (OrderPay orderPay : page.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+orderPay.getChannelNo()));
				if(channelBase != null){
					orderPay.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+orderPay.getAgentNo()));
				if(agentBase != null){
					orderPay.setAgentName(agentBase.getAgentName());
				}
				
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+orderPay.getMerchantNo()));
				if(merchantInfo != null){
					orderPay.setMerchantName(merchantInfo.getMerchantName());
				}
				if(Strings.isNotEmpty(orderPay.getStoreNo())){
					MerchantStore store = (MerchantStore) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+orderPay.getStoreNo()));
					if(store != null){
						orderPay.setStoreName(store.getStoreName());
					}
				}
				if(Strings.isNotEmpty(orderPay.getCashierNo())){
					MerchantCashier merchantCashier = (MerchantCashier) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_CASHIER_BASE.getGroup(), RedisKeyEnum.MERCHANT_CASHIER_BASE.getKey()+orderPay.getCashierNo()));
					if(merchantCashier != null){//收银人有可能是商户，收银员
						orderPay.setCashierName(merchantCashier.getUserName());
					}else{
						orderPay.setCashierName(orderPay.getMerchantName());
					}
					
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}
	
	/**
	 * 列表
	 * @param orderPay 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	@Override
	public List<OrderPay> findAll(OrderPayCondition orderPayCondition){
		CriteriaBuilder cb = Cnd.builder(OrderPay.class);
		if(!Strings.isEmpty(orderPayCondition.getId())){
			cb.andEQ("id", orderPayCondition.getId());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayNo())){
			cb.andEQ("payNo", orderPayCondition.getPayNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getTradeNo())){
			cb.andEQ("tradeNo", orderPayCondition.getTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getChannelNo())){
			cb.andEQ("channelNo", orderPayCondition.getChannelNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getAgentNo())){
			cb.andEQ("agentNo", orderPayCondition.getAgentNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getMerchantNo())){
			cb.andEQ("merchantNo", orderPayCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getQrCode())){
			cb.andEQ("qrCode", orderPayCondition.getQrCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayCode())){
			cb.andEQ("payCode", orderPayCondition.getPayCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getTradeType())){
			cb.andEQ("tradeType", orderPayCondition.getTradeType());
		}
		if(null != orderPayCondition.getOrderAmt()){
			cb.andEQ("orderAmt", orderPayCondition.getOrderAmt());
		}
		if(!Strings.isEmpty(orderPayCondition.getProductName())){
			cb.andEQ("productName", orderPayCondition.getProductName());
		}
		if(!Strings.isEmpty(orderPayCondition.getProductDesc())){
			cb.andEQ("productDesc", orderPayCondition.getProductDesc());
		}
		if(!Strings.isEmpty(orderPayCondition.getBuyerId())){
			cb.andEQ("buyerId", orderPayCondition.getBuyerId());
		}
		if(!Strings.isEmpty(orderPayCondition.getBuyerAccount())){
			cb.andEQ("buyerAccount", orderPayCondition.getBuyerAccount());
		}
		if(null != orderPayCondition.getTotalAmount()){
			cb.andEQ("totalAmount", orderPayCondition.getTotalAmount());
		}
		if(null != orderPayCondition.getBuyerPayAmount()){
			cb.andEQ("buyerPayAmount", orderPayCondition.getBuyerPayAmount());
		}
		if(null != orderPayCondition.getPointAmount()){
			cb.andEQ("pointAmount", orderPayCondition.getPointAmount());
		}
		if(!Strings.isEmpty(orderPayCondition.getBusinessType())){
			cb.andEQ("businessType", orderPayCondition.getBusinessType());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayType())){
			cb.andEQ("payType", orderPayCondition.getPayType());
		}
		if(!Strings.isEmpty(orderPayCondition.getIsMainPay())){
			cb.andEQ("isMainPay", orderPayCondition.getIsMainPay());
		}
		if(!Strings.isEmpty(orderPayCondition.getAccountTime())){
			cb.andEQ("accountTime", orderPayCondition.getAccountTime());
		}
		if(!Strings.isEmpty(orderPayCondition.getErrorCode())){
			cb.andEQ("errorCode", orderPayCondition.getErrorCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getErrorMsg())){
			cb.andEQ("errorMsg", orderPayCondition.getErrorMsg());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayTradeNo())){
			cb.andEQ("payTradeNo", orderPayCondition.getPayTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayOrderType())){
			cb.andEQ("payOrderType", orderPayCondition.getPayOrderType());
		}
		if(!Strings.isEmpty(orderPayCondition.getNotifyUrl())){
			cb.andEQ("notifyUrl", orderPayCondition.getNotifyUrl());
		}
		if(!Strings.isEmpty(orderPayCondition.getReturnUrl())){
			cb.andEQ("returnUrl", orderPayCondition.getReturnUrl());
		}
		if(!Strings.isEmpty(orderPayCondition.getRefundStatus())){
			cb.andEQ("refundStatus", orderPayCondition.getRefundStatus());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayStatus())){
			cb.andEQ("payStatus", orderPayCondition.getPayStatus());
		}
		if(!Strings.isEmpty(orderPayCondition.getBatchId())){
			cb.andEQ("batchId", orderPayCondition.getBatchId());
		}
		if(!Strings.isEmpty(orderPayCondition.getOptCode())){
			cb.andEQ("optCode", orderPayCondition.getOptCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", orderPayCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(orderPayCondition.getCashierNo())){
			cb.andEQ("cashier_no", orderPayCondition.getCashierNo());
		}
		if(null != orderPayCondition.getMerchantCost()){
			cb.andEQ("merchant_cost", orderPayCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(orderPayCondition.getOperator())){
			cb.andEQ("operator", orderPayCondition.getOperator());
		}
		if(!Strings.isEmpty(orderPayCondition.getStoreNo())){
			cb.andEQ("store_no", orderPayCondition.getStoreNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getRemark())){
			cb.andLike("remark", orderPayCondition.getRemark());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp1())){
			cb.andEQ("temp1", orderPayCondition.getTemp1());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp2())){
			cb.andEQ("temp2", orderPayCondition.getTemp2());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp3())){
			cb.andEQ("temp3", orderPayCondition.getTemp3());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp4())){
			cb.andEQ("temp4", orderPayCondition.getTemp4());
		}
		if(null != orderPayCondition.getBeginTime()){
			cb.andGE("beginTime", orderPayCondition.getBeginTime());
		}
		if(null != orderPayCondition.getEndTime()){
			cb.andLE("beginTime", orderPayCondition.getEndTime());
		}
		if(!Strings.isEmpty(orderPayCondition.getTradeSource())){
			cb.andEQ("tradeSource", orderPayCondition.getTradeSource());
		}
		if(!Strings.isEmpty(orderPayCondition.getExtraTradeNo())){
			cb.andEQ("extraTradeNo", orderPayCondition.getExtraTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getChannelTradeNo())){
			cb.andEQ("channelTradeNo", orderPayCondition.getChannelTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getExtraCallBack())){
			cb.andEQ("extraCallBack", orderPayCondition.getExtraCallBack());
		}
		Criteria buildCriteria = cb.buildCriteria();
		List<OrderPay> orderPayList = orderPayDAO.findByCriteria(buildCriteria);
		for (OrderPay orderPay : orderPayList) {			
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+orderPay.getChannelNo()));
				if(channelBase != null){
					orderPay.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+orderPay.getAgentNo()));
				if(agentBase != null){
					orderPay.setAgentName(agentBase.getAgentName());
				}
				
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+orderPay.getMerchantNo()));
				if(merchantInfo != null){
					orderPay.setMerchantName(merchantInfo.getMerchantName());
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return orderPayList;
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	@Override
	public OrderPay findById(String id){
		return orderPayDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param orderPayCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	@Override
	public long insert(OrderPayCondition orderPayCondition){
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayCondition, orderPay);
		return orderPayDAO.insert(orderPay);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	@Override
	public long deleteById(String id){
		return orderPayDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return orderPayDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return orderPayDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	@Override
	public long update(OrderPayCondition orderPayCondition){
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayCondition, orderPay);
		return orderPayDAO.update(orderPay);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:40
	 */
	@Override
	public long updateByCriteria(OrderPayCondition orderPayCondition,Criteria criteria){
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayCondition, orderPay);
		return orderPayDAO.updateByCriteria(orderPay,criteria);
	}

	@Override
	public OrderPay createPayOrder(OrderPayment orderPayment, OrderPayCondition orderPayCondition) {
		BeanUtils.copyProperties(orderPayment, orderPayCondition);
		orderPayCondition.setPayNo(orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "ZF"));
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayCondition, orderPay);
		orderPayDAO.insert(orderPay);
		return orderPay;
	}
	
	
	@Override
	public Map<String,BigDecimal> getTotalMoney(String date, String type,String cashierNo,String identityFlag) {
		log.info("getTotalMoney get params is "+identityFlag);
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
		List<OrderPay> list= orderPayDAO.getTotalMoney(date.substring(0, dateLength),cashierNo,identityFlag);
		Map<String,BigDecimal> map = new HashMap<>();
		map.put("trade", new BigDecimal("0"));//交易
		map.put("withdraw", new BigDecimal("0"));//提现
		for (OrderPay orderPay : list) {
			if("01".equals(orderPay.getTradeType())){
				map.put("withdraw", orderPay.getOrderAmt());
			}else if("02".equals(orderPay.getTradeType())){
				map.put("trade", orderPay.getOrderAmt());
			}
		}
		return map;
	}
	
	@Override
	public List<OrderPayTotalDTO> getDealMoney(String identityNo,String flag) {
		log.info("getDealMoney get params is "+flag);
		// TODO Auto-generated method stub
		return orderPayDAO.getDealMoney(Dates.format(DF.yyyy_MM_dd, new Date()),identityNo,flag);
	}
	
	@Override
	public Map<String, BigDecimal> getDealMoneyTotal(String identityNo,String flag) {
		log.info("getDealMoney get params is "+flag);
		// TODO Auto-generated method stub
		return orderPayDAO.getDealMoneyTotal(Dates.format(DF.yyyy_MM_dd, new Date()),identityNo,flag);
	}
	

	@Override
	public OrderPay createWithdrawsOrder(OrderPayCondition orderPayCondition) {
		orderPayCondition.setPayNo(orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "ZF"));
		orderPayCondition.setBeginTime(new Date());
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayCondition, orderPay);
		orderPayDAO.insert(orderPay);
		return orderPay;
	}
	
	@Override
	public List<OrderPayStaticDTO> getSumOrderAmt(String date,String type) {
		// TODO Auto-generated method stub
		return orderPayDAO.getSumOrderAmt(date,type);
	}
	
	@Override
	public List<OrderPayStaticDTO> getWithDrawTimes(String date, String type) {
		// TODO Auto-generated method stub
		return orderPayDAO.getWithDrawTimes(date,type);
	}

	@Override
	public int countByCriteria(OrderPayCondition orderPayCondition) {
		CriteriaBuilder cb = Cnd.builder(OrderPay.class);
		if(!Strings.isEmpty(orderPayCondition.getId())){
			cb.andEQ("id", orderPayCondition.getId());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayNo())){
			cb.andEQ("payNo", orderPayCondition.getPayNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getTradeNo())){
			cb.andEQ("tradeNo", orderPayCondition.getTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getChannelNo())){
			cb.andEQ("channelNo", orderPayCondition.getChannelNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getAgentNo())){
			cb.andEQ("agentNo", orderPayCondition.getAgentNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getMerchantNo())){
			cb.andEQ("merchantNo", orderPayCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getQrCode())){
			cb.andEQ("qrCode", orderPayCondition.getQrCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayCode())){
			cb.andEQ("payCode", orderPayCondition.getPayCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getTradeType())){
			cb.andEQ("tradeType", orderPayCondition.getTradeType());
		}
		if(null != orderPayCondition.getOrderAmt()){
			cb.andEQ("orderAmt", orderPayCondition.getOrderAmt());
		}
		if(!Strings.isEmpty(orderPayCondition.getProductName())){
			cb.andEQ("productName", orderPayCondition.getProductName());
		}
		if(!Strings.isEmpty(orderPayCondition.getProductDesc())){
			cb.andEQ("productDesc", orderPayCondition.getProductDesc());
		}
		if(!Strings.isEmpty(orderPayCondition.getBuyerId())){
			cb.andEQ("buyerId", orderPayCondition.getBuyerId());
		}
		if(!Strings.isEmpty(orderPayCondition.getBuyerAccount())){
			cb.andEQ("buyerAccount", orderPayCondition.getBuyerAccount());
		}
		if(null != orderPayCondition.getTotalAmount()){
			cb.andEQ("totalAmount", orderPayCondition.getTotalAmount());
		}
		if(null != orderPayCondition.getBuyerPayAmount()){
			cb.andEQ("buyerPayAmount", orderPayCondition.getBuyerPayAmount());
		}
		if(null != orderPayCondition.getPointAmount()){
			cb.andEQ("pointAmount", orderPayCondition.getPointAmount());
		}
		if(!Strings.isEmpty(orderPayCondition.getBusinessType())){
			cb.andEQ("businessType", orderPayCondition.getBusinessType());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayType())){
			cb.andEQ("payType", orderPayCondition.getPayType());
		}
		if(!Strings.isEmpty(orderPayCondition.getIsMainPay())){
			cb.andEQ("isMainPay", orderPayCondition.getIsMainPay());
		}
		if(!Strings.isEmpty(orderPayCondition.getAccountTime())){
			cb.andEQ("accountTime", orderPayCondition.getAccountTime());
		}
		if(!Strings.isEmpty(orderPayCondition.getErrorCode())){
			cb.andEQ("errorCode", orderPayCondition.getErrorCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getErrorMsg())){
			cb.andEQ("errorMsg", orderPayCondition.getErrorMsg());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayTradeNo())){
			cb.andEQ("payTradeNo", orderPayCondition.getPayTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayOrderType())){
			cb.andEQ("payOrderType", orderPayCondition.getPayOrderType());
		}
		if(!Strings.isEmpty(orderPayCondition.getNotifyUrl())){
			cb.andEQ("notifyUrl", orderPayCondition.getNotifyUrl());
		}
		if(!Strings.isEmpty(orderPayCondition.getReturnUrl())){
			cb.andEQ("returnUrl", orderPayCondition.getReturnUrl());
		}
		if(!Strings.isEmpty(orderPayCondition.getRefundStatus())){
			cb.andEQ("refundStatus", orderPayCondition.getRefundStatus());
		}
		if(!Strings.isEmpty(orderPayCondition.getPayStatus())){
			cb.andEQ("payStatus", orderPayCondition.getPayStatus());
		}
		if(!Strings.isEmpty(orderPayCondition.getBatchId())){
			cb.andEQ("batchId", orderPayCondition.getBatchId());
		}
		if(!Strings.isEmpty(orderPayCondition.getOptCode())){
			cb.andEQ("optCode", orderPayCondition.getOptCode());
		}
		if(!Strings.isEmpty(orderPayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", orderPayCondition.getRecordStatus());
		}
		if(null != orderPayCondition.getBeginTime()){
			cb.andGE("beginTime", orderPayCondition.getBeginTime());
		}
		if(null != orderPayCondition.getEndTime()){
			cb.andLE("beginTime", orderPayCondition.getEndTime());
		}
		if(!Strings.isEmpty(orderPayCondition.getOperator())){
			cb.andEQ("operator", orderPayCondition.getOperator());
		}

		if(!Strings.isEmpty(orderPayCondition.getRemark())){
			cb.andLike("remark", orderPayCondition.getRemark());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp1())){
			cb.andEQ("temp1", orderPayCondition.getTemp1());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp2())){
			cb.andEQ("temp2", orderPayCondition.getTemp2());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp3())){
			cb.andEQ("temp3", orderPayCondition.getTemp3());
		}
		if(!Strings.isEmpty(orderPayCondition.getTemp4())){
			cb.andEQ("temp4", orderPayCondition.getTemp4());
		}
		if(!Strings.isEmpty(orderPayCondition.getCashierNo())){
			cb.andEQ("cashier_no", orderPayCondition.getCashierNo());
		}
		if(null != orderPayCondition.getMerchantCost()){
			cb.andEQ("merchant_cost", orderPayCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(orderPayCondition.getStoreNo())){
			cb.andEQ("store_no", orderPayCondition.getStoreNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getQueryStartTime())){
			cb.andGE("beginTime", orderPayCondition.getQueryStartTime());
		}
		if(!Strings.isEmpty(orderPayCondition.getQueryEndTime())){
			cb.andLE("beginTime", orderPayCondition.getQueryEndTime());
		}
		if(!Strings.isEmpty(orderPayCondition.getTradeSource())){
			cb.andEQ("tradeSource", orderPayCondition.getTradeSource());
		}
		if(!Strings.isEmpty(orderPayCondition.getExtraTradeNo())){
			cb.andEQ("extraTradeNo", orderPayCondition.getExtraTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getChannelTradeNo())){
			cb.andEQ("channelTradeNo", orderPayCondition.getChannelTradeNo());
		}
		if(!Strings.isEmpty(orderPayCondition.getExtraCallBack())){
			cb.andEQ("extraCallBack", orderPayCondition.getExtraCallBack());
		}
		Criteria buildCriteria = cb.buildCriteria();
		int counts = orderPayDAO.countByCriteria(buildCriteria);
		return counts;
	}
	/**
	 * 按时间维度统计收款金额
	 * @param date
	 * @param code
	 * @param identityNo
	 * @param identityFlag
	 * @return
	 */
	public BigDecimal getTotalInMoney(String date, String type, String identityNo, String identityFlag){
		log.info("getTotalInMoney get params is "+identityFlag);
		int dateLength = 4;//默认年的长度 YYYY-MM-DD
		String dateQuery = "";
		if(Strings.isEmpty(type)){//不指定时间则查询所有
			dateQuery = "%";
		}
		else{
			if(type.equals(DateFlagEnu.DAY.getCode())){
				dateLength=10;
			}
			else if(type.equals(DateFlagEnu.MONTH.getCode())){
				dateLength=7;		
			}
			else if(type.equals(DateFlagEnu.YEAR.getCode())){
				dateLength=4;
			}
			dateQuery = date.substring(0, dateLength);
		}
		return orderPayDAO.getTotalInMoney(dateQuery, identityNo, identityFlag);
	}
}

