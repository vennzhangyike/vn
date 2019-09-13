/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.pay.service.PayWithdrawalsService;
import com.hfepay.pay.utils.OrderIDUtils;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.dao.WithdrawalsDAO;
import com.hfepay.scancode.commons.entity.Withdrawals;

@Service
public class PayWithdrawalsServiceImpl implements PayWithdrawalsService {
	public static final Logger log = LoggerFactory.getLogger(PayWithdrawalsServiceImpl.class);
	@Autowired
    private WithdrawalsDAO withdrawalsDAO;
	@Autowired
	private OrderIDUtils orderUtils;
	
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
	
	@Override
	public Withdrawals findByTradeNo(String tradeNo) {
		CriteriaBuilder cb = Cnd.builder(Withdrawals.class);
		if(Strings.isEmpty(tradeNo)){
			throw new RuntimeException("tradeNo is null");
		}
		cb.andEQ("tradeNo", tradeNo);
		Criteria buildCriteria = cb.buildCriteria();
		return withdrawalsDAO.findOneByCriteria(buildCriteria);
	}
	
}

