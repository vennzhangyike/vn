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

import com.alibaba.fastjson.serializer.ValueFilter;
import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.OrderPaymentDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.order.OrderPaymentService;
import com.hfepay.scancode.service.utils.OrderIDUtils;

@Service("orderPaymentService")
public class OrderPaymentServiceImpl implements OrderPaymentService {
	
	public static final Logger log = LoggerFactory.getLogger(OrderPaymentServiceImpl.class);
	
	@Autowired
    private OrderPaymentDAO orderPaymentDAO;
	
	@Autowired
	private OrderIDUtils orderUtils;
	
	@Autowired
	private RedisSharedCache redisSharedCache;

	@Autowired
	private MerchantInfoService merchantInfoService;
    
    /**
	 * 列表(分页)
	 * @param orderPaymentCondition 
	 *
	 * @author panq
     * @throws Exception 
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
    @Override
	public PagingResult<OrderPayment> findPagingResult(OrderPaymentCondition orderPaymentCondition) throws Exception{
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
		if(!Strings.isEmpty(orderPaymentCondition.getId())){
			cb.andEQ("id", orderPaymentCondition.getId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTradeNo())){
			cb.andLike("tradeNo", orderPaymentCondition.getTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getChannelNo())){
			cb.andEQ("channelNo", orderPaymentCondition.getChannelNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getAgentNo())){
			cb.andEQ("agentNo", orderPaymentCondition.getAgentNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getMerchantNo())){
			cb.andEQ("merchantNo", orderPaymentCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getQrCode())){
			cb.andEQ("qrCode", orderPaymentCondition.getQrCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPayCode())){
			cb.andEQ("payCode", orderPaymentCondition.getPayCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBusinessType())){
			cb.andEQ("businessType", orderPaymentCondition.getBusinessType());
		}
//		if(null != orderPaymentCondition.getOrderAmt()){
//			cb.andEQ("orderAmt", orderPaymentCondition.getOrderAmt());
//		}
		
		if(null != orderPaymentCondition.getQueryStartAmt()){
			cb.andGE("orderAmt", orderPaymentCondition.getQueryStartAmt());
		}
		if(null != orderPaymentCondition.getQueryEndAmt()){
			cb.andLE("orderAmt", orderPaymentCondition.getQueryEndAmt());
		}
		
		if(!Strings.isEmpty(orderPaymentCondition.getProductName())){
			cb.andEQ("productName", orderPaymentCondition.getProductName());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getProductDesc())){
			cb.andEQ("productDesc", orderPaymentCondition.getProductDesc());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBuyerId())){
			cb.andEQ("buyerId", orderPaymentCondition.getBuyerId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBuyerAccount())){
			cb.andEQ("buyerAccount", orderPaymentCondition.getBuyerAccount());
		}
		if(null != orderPaymentCondition.getTotalAmount()){
			cb.andEQ("totalAmount", orderPaymentCondition.getTotalAmount());
		}
		if(null != orderPaymentCondition.getBuyerPayAmount()){
			cb.andEQ("buyerPayAmount", orderPaymentCondition.getBuyerPayAmount());
		}
		if(null != orderPaymentCondition.getPointAmount()){
			cb.andEQ("pointAmount", orderPaymentCondition.getPointAmount());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getFeeStatus())){
			cb.andEQ("feeStatus", orderPaymentCondition.getFeeStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getFeeType())){
			cb.andEQ("feeType", orderPaymentCondition.getFeeType());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPaymentInfo())){
			cb.andEQ("paymentInfo", orderPaymentCondition.getPaymentInfo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBatchId())){
			cb.andEQ("batchId", orderPaymentCondition.getBatchId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getResultCode())){
			cb.andEQ("resultCode", orderPaymentCondition.getResultCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getResultInfo())){
			cb.andEQ("resultInfo", orderPaymentCondition.getResultInfo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPayTradeNo())){
			cb.andEQ("payTradeNo", orderPaymentCondition.getPayTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPaymentType())){
			cb.andEQ("paymentType", orderPaymentCondition.getPaymentType());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getNotifyUrl())){
			cb.andEQ("notifyUrl", orderPaymentCondition.getNotifyUrl());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getReturnUrl())){
			cb.andEQ("returnUrl", orderPaymentCondition.getReturnUrl());
		} 
		if(null != orderPaymentCondition.getBeginTime()){
			cb.andGE("beginTime", orderPaymentCondition.getBeginTime());
		}
		if(null != orderPaymentCondition.getEndTime()){
			cb.andLE("beginTime", orderPaymentCondition.getEndTime());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getSettleTime())){
			cb.andEQ("settleTime", orderPaymentCondition.getSettleTime());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getSettleMerchant())){
			cb.andEQ("settleMerchant", orderPaymentCondition.getSettleMerchant());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getRefundStatus())){
			cb.andEQ("refundStatus", orderPaymentCondition.getRefundStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getOrderStatus())){
			cb.andEQ("orderStatus", orderPaymentCondition.getOrderStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getOptCode())){
			cb.andEQ("optCode", orderPaymentCondition.getOptCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getRecordStatus())){
			cb.andEQ("recordStatus", orderPaymentCondition.getRecordStatus());
		}

		if(!Strings.isEmpty(orderPaymentCondition.getOperator())){
			cb.andEQ("operator", orderPaymentCondition.getOperator());
		}

		if(!Strings.isEmpty(orderPaymentCondition.getRemark())){
			cb.andLike("remark", orderPaymentCondition.getRemark());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp1())){
			cb.andEQ("temp1", orderPaymentCondition.getTemp1());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp2())){
			cb.andEQ("temp2", orderPaymentCondition.getTemp2());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp3())){
			cb.andEQ("temp3", orderPaymentCondition.getTemp3());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp4())){
			cb.andEQ("temp4", orderPaymentCondition.getTemp4());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getCashierNo())){
			cb.andEQ("cashier_no", orderPaymentCondition.getCashierNo());
		}
		if(null != orderPaymentCondition.getMerchantCost()){
			cb.andEQ("merchant_cost", orderPaymentCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getStoreNo())){
			cb.andEQ("store_no", orderPaymentCondition.getStoreNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTradeSource())){
			cb.andEQ("tradeSource", orderPaymentCondition.getTradeSource());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getExtraTradeNo())){
			cb.andEQ("extraTradeNo", orderPaymentCondition.getExtraTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getChannelTradeNo())){
			cb.andEQ("channelTradeNo", orderPaymentCondition.getChannelTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getExtraCallBack())){
			cb.andEQ("extraCallBack", orderPaymentCondition.getExtraCallBack());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getAccountType())){
			if(orderPaymentCondition.getAccountType().equals("2")){
				cb.andEQ("accountType", orderPaymentCondition.getAccountType());
			}else{
				cb.andNotEQ("accountType", "2");
			}
		}
		cb.orderBy("beginTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(orderPaymentCondition.getOrderBy())){
			if(orderPaymentCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = orderPaymentCondition.getOrderBy().split(",");
				String[] orders = orderPaymentCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(orderPaymentCondition.getOrderBy(), Order.valueOf(orderPaymentCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(orderPaymentCondition.getFirst()), Long.valueOf(orderPaymentCondition.getLast()));
		
		PagingResult<OrderPayment> page = orderPaymentDAO.findPagingResult(buildCriteria);
		for (OrderPayment orderPayment : page.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+orderPayment.getChannelNo()));
				if(channelBase != null){
					orderPayment.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+orderPayment.getAgentNo()));
				if(agentBase != null){
					orderPayment.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+orderPayment.getMerchantNo()));
				if(merchantInfo != null){
					orderPayment.setMerchantName(merchantInfo.getMerchantName());
				}
				MerchantStore merchantStore = (MerchantStore) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+orderPayment.getStoreNo()));
				if(merchantStore != null){
					orderPayment.setStoreName(merchantStore.getStoreName());
				}
				if(Strings.isNotEmpty(orderPayment.getCashierNo())){
					MerchantCashier merchantCashier = (MerchantCashier) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_CASHIER_BASE.getGroup(), RedisKeyEnum.MERCHANT_CASHIER_BASE.getKey()+orderPayment.getCashierNo()));
					if(merchantCashier != null){//收银人有可能是商户，收银员
						orderPayment.setCashierName(merchantCashier.getUserName());
					}else{
						orderPayment.setCashierName(orderPayment.getMerchantName());
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
	 * @param orderPayment 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public List<OrderPayment> findAll(OrderPaymentCondition orderPaymentCondition){
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
		if(!Strings.isEmpty(orderPaymentCondition.getId())){
			cb.andEQ("id", orderPaymentCondition.getId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTradeNo())){
			cb.andEQ("tradeNo", orderPaymentCondition.getTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getChannelNo())){
			cb.andEQ("channelNo", orderPaymentCondition.getChannelNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getAgentNo())){
			cb.andEQ("agentNo", orderPaymentCondition.getAgentNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getMerchantNo())){
			cb.andEQ("merchantNo", orderPaymentCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getQrCode())){
			cb.andEQ("qrCode", orderPaymentCondition.getQrCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPayCode())){
			cb.andEQ("payCode", orderPaymentCondition.getPayCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBusinessType())){
			cb.andEQ("businessType", orderPaymentCondition.getBusinessType());
		}
//		if(null != orderPaymentCondition.getOrderAmt()){
//			cb.andEQ("orderAmt", orderPaymentCondition.getOrderAmt());
//		}
		if(null != orderPaymentCondition.getQueryStartAmt()){
			cb.andGE("orderAmt", orderPaymentCondition.getQueryStartAmt());
		}
		if(null != orderPaymentCondition.getQueryEndAmt()){
			cb.andLE("orderAmt", orderPaymentCondition.getQueryEndAmt());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getProductName())){
			cb.andEQ("productName", orderPaymentCondition.getProductName());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getProductDesc())){
			cb.andEQ("productDesc", orderPaymentCondition.getProductDesc());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBuyerId())){
			cb.andEQ("buyerId", orderPaymentCondition.getBuyerId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBuyerAccount())){
			cb.andEQ("buyerAccount", orderPaymentCondition.getBuyerAccount());
		}
		if(null != orderPaymentCondition.getTotalAmount()){
			cb.andEQ("totalAmount", orderPaymentCondition.getTotalAmount());
		}
		if(null != orderPaymentCondition.getBuyerPayAmount()){
			cb.andEQ("buyerPayAmount", orderPaymentCondition.getBuyerPayAmount());
		}
		if(null != orderPaymentCondition.getPointAmount()){
			cb.andEQ("pointAmount", orderPaymentCondition.getPointAmount());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getFeeStatus())){
			cb.andEQ("feeStatus", orderPaymentCondition.getFeeStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getFeeType())){
			cb.andEQ("feeType", orderPaymentCondition.getFeeType());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPaymentInfo())){
			cb.andEQ("paymentInfo", orderPaymentCondition.getPaymentInfo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBatchId())){
			cb.andEQ("batchId", orderPaymentCondition.getBatchId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getResultCode())){
			cb.andEQ("resultCode", orderPaymentCondition.getResultCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getResultInfo())){
			cb.andEQ("resultInfo", orderPaymentCondition.getResultInfo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPayTradeNo())){
			cb.andEQ("payTradeNo", orderPaymentCondition.getPayTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPaymentType())){
			cb.andEQ("paymentType", orderPaymentCondition.getPaymentType());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getNotifyUrl())){
			cb.andEQ("notifyUrl", orderPaymentCondition.getNotifyUrl());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getReturnUrl())){
			cb.andEQ("returnUrl", orderPaymentCondition.getReturnUrl());
		}
		if(null != orderPaymentCondition.getBeginTime()){
			cb.andGE("beginTime", orderPaymentCondition.getBeginTime());
		}
		if(null != orderPaymentCondition.getEndTime()){
			cb.andLE("beginTime", orderPaymentCondition.getEndTime());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getSettleTime())){
			cb.andEQ("settleTime", orderPaymentCondition.getSettleTime());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getSettleMerchant())){
			cb.andEQ("settleMerchant", orderPaymentCondition.getSettleMerchant());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getRefundStatus())){
			cb.andEQ("refundStatus", orderPaymentCondition.getRefundStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getOrderStatus())){
			cb.andEQ("orderStatus", orderPaymentCondition.getOrderStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getOptCode())){
			cb.andEQ("optCode", orderPaymentCondition.getOptCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getRecordStatus())){
			cb.andEQ("recordStatus", orderPaymentCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getCashierNo())){
			cb.andEQ("cashier_no", orderPaymentCondition.getCashierNo());
		}
		if(null != orderPaymentCondition.getMerchantCost()){
			cb.andEQ("merchant_cost", orderPaymentCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getOperator())){
			cb.andEQ("operator", orderPaymentCondition.getOperator());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getStoreNo())){
			cb.andEQ("store_no", orderPaymentCondition.getStoreNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getRemark())){
			cb.andLike("remark", orderPaymentCondition.getRemark());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp1())){
			cb.andEQ("temp1", orderPaymentCondition.getTemp1());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp2())){
			cb.andEQ("temp2", orderPaymentCondition.getTemp2());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp3())){
			cb.andEQ("temp3", orderPaymentCondition.getTemp3());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp4())){
			cb.andEQ("temp4", orderPaymentCondition.getTemp4());
		}
		if(orderPaymentCondition.getOrganNoList() !=null && !orderPaymentCondition.getOrganNoList().isEmpty()){
			cb.andIn("agentNo", orderPaymentCondition.getOrganNoList());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTradeSource())){
			cb.andEQ("tradeSource", orderPaymentCondition.getTradeSource());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getExtraTradeNo())){
			cb.andEQ("extraTradeNo", orderPaymentCondition.getExtraTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getChannelTradeNo())){
			cb.andEQ("channelTradeNo", orderPaymentCondition.getChannelTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getExtraCallBack())){
			cb.andEQ("extraCallBack", orderPaymentCondition.getExtraCallBack());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getAccountType())){
			if(orderPaymentCondition.getAccountType().equals("2")){
				cb.andEQ("accountType", orderPaymentCondition.getAccountType());
			}else{
				cb.andNotEQ("accountType", "2");
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		return orderPaymentDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param orderPayment 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public List<OrderPayment> findAllByInit(OrderPaymentCondition orderPaymentCondition){
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);

		if(null != orderPaymentCondition.getBeginTime()){
			cb.andLT("beginTime", orderPaymentCondition.getBeginTime());
		}

		if(!Strings.isEmpty(orderPaymentCondition.getOrderStatus())){
			cb.andEQ("orderStatus", orderPaymentCondition.getOrderStatus());
		}
		
		Criteria buildCriteria = cb.buildCriteria();
		return orderPaymentDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public OrderPayment findById(String id){
		return orderPaymentDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param orderPaymentCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public long insert(OrderPaymentCondition orderPaymentCondition){
		OrderPayment orderPayment = new OrderPayment();
		BeanUtils.copyProperties(orderPaymentCondition, orderPayment);
		return orderPaymentDAO.insert(orderPayment);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public long deleteById(String id){
		return orderPaymentDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return orderPaymentDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return orderPaymentDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public long update(OrderPaymentCondition orderPaymentCondition){
		OrderPayment orderPayment = new OrderPayment();
		BeanUtils.copyProperties(orderPaymentCondition, orderPayment);
		return orderPaymentDAO.update(orderPayment);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public long updateByCriteria(OrderPaymentCondition orderPaymentCondition,Criteria criteria){
		OrderPayment orderPayment = new OrderPayment();
		BeanUtils.copyProperties(orderPaymentCondition, orderPayment);
		return orderPaymentDAO.updateByCriteria(orderPayment,criteria);
	}

	//保存交易订单
	@Override
	public OrderPayment saveOrderPayment(OrderPaymentCondition orderPaymentCondition) {
		//生成交易订单编号
		orderPaymentCondition.setTradeNo(orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "ZF"));
		
		OrderPayment orderPayment = new OrderPayment();
		BeanUtils.copyProperties(orderPaymentCondition, orderPayment);
		orderPaymentDAO.insert(orderPayment);
		return orderPayment;
	}
	
	/** 金额统计 */
	@Override	
	public Map<String, BigDecimal> getAmtStatic(List<OrderPayment> orderPayments){
		Map<String, BigDecimal> amtStatic = new HashMap<String, BigDecimal>();
		BigDecimal orderTotalAmt = BigDecimal.ZERO;//交易总金额
		BigDecimal zfbTotalAmt = BigDecimal.ZERO;//支付宝
		BigDecimal wxgzhTotalAmt = BigDecimal.ZERO;//微信公众号
		BigDecimal wxTotalAmt = BigDecimal.ZERO;//微信
		BigDecimal cardTotalAmt = BigDecimal.ZERO;//信用卡支付
		BigDecimal otherTotalAmt = BigDecimal.ZERO;//其他
		for (OrderPayment orderPayment : orderPayments) {
			orderTotalAmt = orderTotalAmt.add(orderPayment.getOrderAmt());
			if(PayCode.PAYCODE_ZFBSMZF.getCode().equals(orderPayment.getPayCode())){				
				zfbTotalAmt = zfbTotalAmt.add(orderPayment.getOrderAmt());
			}else if(PayCode.PAYCODE_WXGZHZF.getCode().equals(orderPayment.getPayCode())){
				wxgzhTotalAmt = wxgzhTotalAmt.add(orderPayment.getOrderAmt());
			}else if(PayCode.PAYCODE_WXSMZF.getCode().equals(orderPayment.getPayCode())){
				wxTotalAmt = wxTotalAmt.add(orderPayment.getOrderAmt());
			}
			if("2".equals(orderPayment.getAccountType())){
				cardTotalAmt = cardTotalAmt.add(orderPayment.getOrderAmt());
			}else{
				otherTotalAmt = otherTotalAmt.add(orderPayment.getOrderAmt());
			}
		}
		amtStatic.put("orderTotalAmt", orderTotalAmt);
		amtStatic.put("zfbTotalAmt", zfbTotalAmt);
		amtStatic.put("wxgzhTotalAmt", wxgzhTotalAmt);
		amtStatic.put("wxTotalAmt", wxTotalAmt);
		amtStatic.put("cardTotalAmt", cardTotalAmt);
		amtStatic.put("otherTotalAmt", otherTotalAmt);
		return amtStatic;
	}
	
	/** 按代理商交易总金额统计 */
	@Override	
	public Map<String, BigDecimal> getTotalAmtStatic(List<OrderPayment> orderPayments){
		Map<String, BigDecimal> agentTotalAmt = new HashMap<String, BigDecimal>();
		BigDecimal orderTotalAmt = BigDecimal.ZERO;//交易总金额
		for (OrderPayment orderPayment : orderPayments) {
			if(agentTotalAmt.containsKey(orderPayment.getAgentNo())){
				agentTotalAmt.put(orderPayment.getAgentNo(), agentTotalAmt.get(orderPayment.getAgentNo()).add(orderPayment.getOrderAmt()));
			}else{
				agentTotalAmt.put(orderPayment.getAgentNo(), orderPayment.getOrderAmt());
			}			
		}
		return agentTotalAmt;
	}
	
	@Override
	public Map<String, BigDecimal> getAmtStaticMoney(OrderPaymentCondition orderPaymentCondition) {
		List<OrderPayment> orderPayments = orderPaymentDAO.getAmtStaticMoney(orderPaymentCondition);
		Map<String, BigDecimal> amtStatic = new HashMap<String, BigDecimal>();
		BigDecimal orderTotalAmt = BigDecimal.ZERO;//交易总金额
		BigDecimal zfbTotalAmt = BigDecimal.ZERO;//支付宝
		BigDecimal wxgzhTotalAmt = BigDecimal.ZERO;//微信公众号
		BigDecimal wxTotalAmt = BigDecimal.ZERO;//微信
		BigDecimal qqTotalAmt = BigDecimal.ZERO;//QQ
		BigDecimal cardTotalAmt = BigDecimal.ZERO;//信用卡支付
		BigDecimal otherTotalAmt = BigDecimal.ZERO;//其他
		for (OrderPayment orderPayment : orderPayments) {
			orderTotalAmt = orderTotalAmt.add(orderPayment.getOrderAmt());
			if(PayCode.PAYCODE_ZFBSMZF.getCode().equals(orderPayment.getPayCode())){				
				zfbTotalAmt = zfbTotalAmt.add(orderPayment.getOrderAmt());
			}else if(PayCode.PAYCODE_WXGZHZF.getCode().equals(orderPayment.getPayCode())){
				wxgzhTotalAmt = wxgzhTotalAmt.add(orderPayment.getOrderAmt());
			}else if(PayCode.PAYCODE_WXSMZF.getCode().equals(orderPayment.getPayCode())){
				wxTotalAmt = wxTotalAmt.add(orderPayment.getOrderAmt());
			}else if(PayCode.PAYCODE_QQZF.getCode().equals(orderPayment.getPayCode())){
				qqTotalAmt = qqTotalAmt.add(orderPayment.getOrderAmt());
			}
			if("2".equals(orderPayment.getAccountType())){
				cardTotalAmt = cardTotalAmt.add(orderPayment.getOrderAmt());
			}else{
				otherTotalAmt = otherTotalAmt.add(orderPayment.getOrderAmt());
			}
		}
		amtStatic.put("orderTotalAmt", orderTotalAmt);
		amtStatic.put("zfbTotalAmt", zfbTotalAmt);
		amtStatic.put("wxgzhTotalAmt", wxgzhTotalAmt);
		amtStatic.put("wxTotalAmt", wxTotalAmt);
		amtStatic.put("cardTotalAmt", cardTotalAmt);
		amtStatic.put("otherTotalAmt", otherTotalAmt);
		amtStatic.put("qqTotalAmt", qqTotalAmt);
		return amtStatic;
	}
	/** 交易订单打印 */
	@Override
	public Map<Object, Object> print(String tradeNo){
		Map<Object, Object> map = new HashMap<Object, Object>();
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
		cb.andEQ("tradeNo", tradeNo);
		Criteria buildCriteria = cb.buildCriteria();
		OrderPayment orderPayment = orderPaymentDAO.findOneByCriteria(buildCriteria);
		//将对象转为JSON字符串
		String orderPaymentJson = com.alibaba.fastjson.JSON.toJSONString(orderPayment,filter);
		//将json字符串转为map
		Map<String, Object> orderPaymentMap = com.alibaba.fastjson.JSON.parseObject(orderPaymentJson, Map.class);
		map.put("orderPayment", orderPaymentMap);
		String payName = "";
		if(PayCode.PAYCODE_WXGZHZF.getCode().equals(orderPayment.getPayCode())){
			payName = PayCode.PAYCODE_WXGZHZF.getDesc();
		}else if(PayCode.PAYCODE_WXSMZF.getCode().equals(orderPayment.getPayCode())){
			payName = PayCode.PAYCODE_WXSMZF.getDesc();
		}else if(PayCode.PAYCODE_ZFBSMZF.getCode().equals(orderPayment.getPayCode())){
			payName = PayCode.PAYCODE_ZFBSMZF.getDesc();
		}
		map.put("payName", payName);
		String orderStatus = "";
		if(OrderStatus.ORDER_FAIL.getCode().equals(orderPayment.getOrderStatus())){
			orderStatus = OrderStatus.ORDER_FAIL.getDesc();
		}else if(OrderStatus.ORDER_SUCCESS.getCode().equals(orderPayment.getOrderStatus())){
			orderStatus = OrderStatus.ORDER_SUCCESS.getDesc();
		}else if(OrderStatus.ORDER_TREATE.getCode().equals(orderPayment.getOrderStatus())){
			orderStatus = OrderStatus.ORDER_TREATE.getDesc();
		}
		map.put("orderStatus",orderStatus);
		String orderTimeStr = Dates.format("yyyy-MM-dd HH:mm:ss", orderPayment.getBeginTime());
		map.put("orderTimeStr",orderTimeStr);
		String merchantNo = orderPayment.getMerchantNo();
		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantNo);
		//将对象转为JSON字符串
		String merchantInfoJson = com.alibaba.fastjson.JSON.toJSONString(merchantInfo,filter);
		//将json字符串转为map
		Map<String, Object> merchantMap = com.alibaba.fastjson.JSON.parseObject(merchantInfoJson, Map.class);
		map.put("merchantInfo", merchantInfo);
		return map;
	}
	
	private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null)
                return "";
            return v;
        }
    };
    
    public Map<Object, Object> getOrderPayment(String tradeNo){
    	Map<Object, Object> map = new HashMap<Object, Object>();
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
		cb.andEQ("tradeNo", tradeNo);
		Criteria buildCriteria = cb.buildCriteria();
		OrderPayment orderPayment = orderPaymentDAO.findOneByCriteria(buildCriteria);
		//将对象转为JSON字符串
		String orderPaymentJson = com.alibaba.fastjson.JSON.toJSONString(orderPayment,filter);
		//将json字符串转为map
		Map<String, Object> orderPaymentMap = com.alibaba.fastjson.JSON.parseObject(orderPaymentJson, Map.class);
		map.put("orderPayment", orderPaymentMap);
		return map;
    }

    /**
     * 
     * @author liushuyu
     * Desc 按条件查询 
     * @param payment
     * @return
     */
    @Override
	public OrderPayment queryOrderPayment(OrderPayment payment) {
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
		if(!Strings.isEmpty(payment.getId())){
			cb.andEQ("id", payment.getId());
		}
		if(!Strings.isEmpty(payment.getTradeNo())){
			cb.andEQ("tradeNo", payment.getTradeNo());
		}
		if(!Strings.isEmpty(payment.getChannelNo())){
			cb.andEQ("channelNo", payment.getChannelNo());
		}
		if(!Strings.isEmpty(payment.getAgentNo())){
			cb.andEQ("agentNo", payment.getAgentNo());
		}
		if(!Strings.isEmpty(payment.getMerchantNo())){
			cb.andEQ("merchantNo", payment.getMerchantNo());
		}
		if(!Strings.isEmpty(payment.getQrCode())){
			cb.andEQ("qrCode", payment.getQrCode());
		}
		if(!Strings.isEmpty(payment.getPayCode())){
			cb.andEQ("payCode", payment.getPayCode());
		}
		if(!Strings.isEmpty(payment.getBusinessType())){
			cb.andEQ("businessType", payment.getBusinessType());
		}
		if(null != payment.getOrderAmt()){
			cb.andEQ("orderAmt", payment.getOrderAmt());
		}
		
		if(!Strings.isEmpty(payment.getProductName())){
			cb.andEQ("productName", payment.getProductName());
		}
		if(!Strings.isEmpty(payment.getProductDesc())){
			cb.andEQ("productDesc", payment.getProductDesc());
		}
		if(!Strings.isEmpty(payment.getBuyerId())){
			cb.andEQ("buyerId", payment.getBuyerId());
		}
		if(!Strings.isEmpty(payment.getBuyerAccount())){
			cb.andEQ("buyerAccount", payment.getBuyerAccount());
		}
		if(null != payment.getTotalAmount()){
			cb.andEQ("totalAmount", payment.getTotalAmount());
		}
		if(null != payment.getBuyerPayAmount()){
			cb.andEQ("buyerPayAmount", payment.getBuyerPayAmount());
		}
		if(null != payment.getPointAmount()){
			cb.andEQ("pointAmount", payment.getPointAmount());
		}
		if(!Strings.isEmpty(payment.getFeeStatus())){
			cb.andEQ("feeStatus", payment.getFeeStatus());
		}
		if(!Strings.isEmpty(payment.getFeeType())){
			cb.andEQ("feeType", payment.getFeeType());
		}
		if(!Strings.isEmpty(payment.getPaymentInfo())){
			cb.andEQ("paymentInfo", payment.getPaymentInfo());
		}
		if(!Strings.isEmpty(payment.getBatchId())){
			cb.andEQ("batchId", payment.getBatchId());
		}
		if(!Strings.isEmpty(payment.getResultCode())){
			cb.andEQ("resultCode", payment.getResultCode());
		}
		if(!Strings.isEmpty(payment.getResultInfo())){
			cb.andEQ("resultInfo", payment.getResultInfo());
		}
		if(!Strings.isEmpty(payment.getPayTradeNo())){
			cb.andEQ("payTradeNo", payment.getPayTradeNo());
		}
		if(!Strings.isEmpty(payment.getPaymentType())){
			cb.andEQ("paymentType", payment.getPaymentType());
		}
		if(!Strings.isEmpty(payment.getNotifyUrl())){
			cb.andEQ("notifyUrl", payment.getNotifyUrl());
		}
		if(!Strings.isEmpty(payment.getReturnUrl())){
			cb.andEQ("returnUrl", payment.getReturnUrl());
		} 
		if(null != payment.getBeginTime()){
			cb.andGE("beginTime", payment.getBeginTime());
		}
		if(null != payment.getEndTime()){
			cb.andLE("beginTime", payment.getEndTime());
		}
		if(!Strings.isEmpty(payment.getSettleTime())){
			cb.andEQ("settleTime", payment.getSettleTime());
		}
		if(!Strings.isEmpty(payment.getSettleMerchant())){
			cb.andEQ("settleMerchant", payment.getSettleMerchant());
		}
		if(!Strings.isEmpty(payment.getRefundStatus())){
			cb.andEQ("refundStatus", payment.getRefundStatus());
		}
		if(!Strings.isEmpty(payment.getOrderStatus())){
			cb.andEQ("orderStatus", payment.getOrderStatus());
		}
		if(!Strings.isEmpty(payment.getOptCode())){
			cb.andEQ("optCode", payment.getOptCode());
		}
		if(!Strings.isEmpty(payment.getRecordStatus())){
			cb.andEQ("recordStatus", payment.getRecordStatus());
		}

		if(!Strings.isEmpty(payment.getOperator())){
			cb.andEQ("operator", payment.getOperator());
		}

		if(!Strings.isEmpty(payment.getRemark())){
			cb.andLike("remark", payment.getRemark());
		}
		if(!Strings.isEmpty(payment.getTemp1())){
			cb.andEQ("temp1", payment.getTemp1());
		}
		if(!Strings.isEmpty(payment.getTemp2())){
			cb.andEQ("temp2", payment.getTemp2());
		}
		if(!Strings.isEmpty(payment.getTemp3())){
			cb.andEQ("temp3", payment.getTemp3());
		}
		if(!Strings.isEmpty(payment.getTemp4())){
			cb.andEQ("temp4", payment.getTemp4());
		}
		if(!Strings.isEmpty(payment.getCashierNo())){
			cb.andEQ("cashier_no", payment.getCashierNo());
		}
		if(null != payment.getMerchantCost()){
			cb.andEQ("merchant_cost", payment.getMerchantCost());
		}
		if(!Strings.isEmpty(payment.getStoreNo())){
			cb.andEQ("store_no", payment.getStoreNo());
		}
		if(!Strings.isEmpty(payment.getAccountType())){
			if(payment.getAccountType().equals("2")){
				cb.andEQ("accountType", payment.getAccountType());
			}else{
				cb.andNotEQ("accountType", "2");
			}
		}
		if(!Strings.isEmpty(payment.getTradeSource())){
			cb.andEQ("tradeSource", payment.getTradeSource());
		}
		if(!Strings.isEmpty(payment.getExtraTradeNo())){
			cb.andEQ("extraTradeNo", payment.getExtraTradeNo());
		}
		if(!Strings.isEmpty(payment.getChannelTradeNo())){
			cb.andEQ("channelTradeNo", payment.getChannelTradeNo());
		}
		if(!Strings.isEmpty(payment.getExtraCallBack())){
			cb.andEQ("extraCallBack", payment.getExtraCallBack());
		}
		cb.orderBy("beginTime", Order.DESC);
		Criteria criteria = cb.buildCriteria();
		List<OrderPayment> orderPayment = orderPaymentDAO.findByCriteria(criteria);
		return orderPayment.get(0);
	}
}

