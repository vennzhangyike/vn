/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator.impl;

import java.util.List;

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
import com.hfepay.scancode.commons.condition.ClearingT1ErrCondition;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.CheckFlag;
import com.hfepay.scancode.commons.contants.OrderPayStatus;
import com.hfepay.scancode.commons.contants.OrderPaymentStatus;
import com.hfepay.scancode.commons.contants.ProcessingStatus;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.contants.WithdrawsStatus;
import com.hfepay.scancode.commons.dao.ClearingT1ErrDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ClearingT1Err;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.operator.ClearingT1ErrService;
import com.hfepay.scancode.service.order.OrderPayService;
import com.hfepay.scancode.service.order.OrderPaymentService;
import com.hfepay.scancode.service.order.WithdrawalsService;

import net.sf.json.JSONObject;

@Service("clearingT1ErrService")
public class ClearingT1ErrServiceImpl implements ClearingT1ErrService {
	
	public static final Logger log = LoggerFactory.getLogger(MerchantCostServiceImpl.class);
	
	@Autowired
	private RedisSharedCache redisSharedCache;
	
	@Autowired
    private ClearingT1ErrDAO clearingT1ErrDAO;
	
	@Autowired
	private OrderPaymentService orderPaymentService;
	
	@Autowired
	private OrderPayService orderPayService;

	@Autowired
	private WithdrawalsService withdrawalsService;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: PagingResult<ClearingT1Err>
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
    @Override
	public PagingResult<ClearingT1Err> findPagingResult(ClearingT1ErrCondition clearingT1ErrCondition){
		CriteriaBuilder cb = Cnd.builder(ClearingT1Err.class);
		if(!Strings.isEmpty(clearingT1ErrCondition.getId())){
			cb.andEQ("id", clearingT1ErrCondition.getId());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getBatchNo())){
			cb.andEQ("batchNo", clearingT1ErrCondition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT1ErrCondition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT1ErrCondition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT1ErrCondition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getChannelNo())){
			cb.andEQ("channelNo", clearingT1ErrCondition.getChannelNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getAgentNo())){
			cb.andEQ("agentNo", clearingT1ErrCondition.getAgentNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT1ErrCondition.getMerchantNo());
		}
		if(null != clearingT1ErrCondition.getTransAmt()){
			cb.andEQ("transAmt", clearingT1ErrCondition.getTransAmt());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeType())){
			cb.andEQ("tradeType", clearingT1ErrCondition.getTradeType());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getPayCode())){
			cb.andEQ("payCode", clearingT1ErrCondition.getPayCode());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getCheckFlag())){
			cb.andEQ("checkFlag", clearingT1ErrCondition.getCheckFlag());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getProcessingStatus())){
			cb.andEQ("processingStatus", clearingT1ErrCondition.getProcessingStatus());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getClearDate())){
			cb.andEQ("clearDate", clearingT1ErrCondition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT1ErrCondition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTemp1())){
			cb.andEQ("temp1", clearingT1ErrCondition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTemp2())){
			cb.andEQ("temp2", clearingT1ErrCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(clearingT1ErrCondition.getFirst()), Long.valueOf(clearingT1ErrCondition.getLast()));
		PagingResult<ClearingT1Err> result = clearingT1ErrDAO.findPagingResult(buildCriteria);
		for (ClearingT1Err t1 : result.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+t1.getChannelNo()));
				if(channelBase != null){
					t1.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+t1.getAgentNo()));
				if(agentBase != null){
					t1.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+t1.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					t1.setMerchantName(merchantInfo.getMerchantName());
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return result;
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: List<ClearingT1Err>
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@Override
	public List<ClearingT1Err> findAll(ClearingT1ErrCondition clearingT1ErrCondition){
		CriteriaBuilder cb = Cnd.builder(ClearingT1Err.class);
		if(!Strings.isEmpty(clearingT1ErrCondition.getId())){
			cb.andEQ("id", clearingT1ErrCondition.getId());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getBatchNo())){
			cb.andEQ("batchNo", clearingT1ErrCondition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT1ErrCondition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT1ErrCondition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT1ErrCondition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getChannelNo())){
			cb.andEQ("channelNo", clearingT1ErrCondition.getChannelNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getAgentNo())){
			cb.andEQ("agentNo", clearingT1ErrCondition.getAgentNo());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT1ErrCondition.getMerchantNo());
		}
		if(null != clearingT1ErrCondition.getTransAmt()){
			cb.andEQ("transAmt", clearingT1ErrCondition.getTransAmt());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeType())){
			cb.andEQ("tradeType", clearingT1ErrCondition.getTradeType());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getPayCode())){
			cb.andEQ("payCode", clearingT1ErrCondition.getPayCode());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getCheckFlag())){
			cb.andEQ("checkFlag", clearingT1ErrCondition.getCheckFlag());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getProcessingStatus())){
			cb.andEQ("processingStatus", clearingT1ErrCondition.getProcessingStatus());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getClearDate())){
			cb.andEQ("clearDate", clearingT1ErrCondition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT1ErrCondition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTemp1())){
			cb.andEQ("temp1", clearingT1ErrCondition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT1ErrCondition.getTemp2())){
			cb.andEQ("temp2", clearingT1ErrCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return clearingT1ErrDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT1Err
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@Override
	public ClearingT1Err findById(String id){
		return clearingT1ErrDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@Override
	public long insert(ClearingT1ErrCondition clearingT1ErrCondition){
		ClearingT1Err clearingT1Err = new ClearingT1Err();
		BeanUtils.copyProperties(clearingT1ErrCondition, clearingT1Err);
		return clearingT1ErrDAO.insert(clearingT1Err);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@Override
	public long deleteById(String id){
		return clearingT1ErrDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return clearingT1ErrDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return clearingT1ErrDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@Override
	public long update(ClearingT1ErrCondition clearingT1ErrCondition){
		ClearingT1Err clearingT1Err = new ClearingT1Err();
		BeanUtils.copyProperties(clearingT1ErrCondition, clearingT1Err);
		return clearingT1ErrDAO.update(clearingT1Err);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT1ErrCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@Override
	public long updateByCriteria(ClearingT1ErrCondition clearingT1ErrCondition,Criteria criteria){
		ClearingT1Err clearingT1Err = new ClearingT1Err();
		BeanUtils.copyProperties(clearingT1ErrCondition, clearingT1Err);
		return clearingT1ErrDAO.updateByCriteria(clearingT1Err,criteria);
	}
	
	/**
	 * 
	 * @author liushuyu
	 * Desc 执行多张表的更新
	 * @return
	 */
	@Override
	public boolean updateMuti(String id,String checkFlag){

		String tradeNo=null;
		boolean flag=true;
		long count=0;
		try{
			tradeNo=this.findById(id).getTradeNo();
			//修改t1对账表的状态
			ClearingT1ErrCondition clearingT1ErrCondition=new ClearingT1ErrCondition();
			clearingT1ErrCondition.setId(id);
			clearingT1ErrCondition.setProcessingStatus(ProcessingStatus.STATUS_CLCG.getCode());
			count=this.update(clearingT1ErrCondition);
			if(count==0){
				flag=false;
				return flag;
			}
		}catch(Exception e){
			flag=false;
			throw e;
		}
		//对方有本地无
		if(CheckFlag.STATUS_BDWDFY.getCode().equals(checkFlag)){
			try{
				//修改订单表状态
				OrderPayCondition orderPayCondition=new OrderPayCondition();
				orderPayCondition.setTradeNo(tradeNo);
				String orderPayId=orderPayService.findAll(orderPayCondition).get(0).getId();
				orderPayCondition.setId(orderPayId);
				orderPayCondition.setPayStatus(OrderPayStatus.STATUS_ZFCG.getCode());
				count=orderPayService.update(orderPayCondition);
				if(count==0){
					flag=false;
					return flag;
				}
				//修改提现表状态
				WithdrawalsCondition withdrawalsCondition=new WithdrawalsCondition();
				withdrawalsCondition.setTradeNo(tradeNo);
				String withdrawalsId=withdrawalsService.findAll(withdrawalsCondition).get(0).getId();
				withdrawalsCondition.setId(withdrawalsId);
				withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_SUCCESS.getCode());
				count=withdrawalsService.update(withdrawalsCondition);
				if(count==0){
					flag=false;
					return flag;
				}
			}catch(Exception e){
				flag=false;
				throw e;
			}
		}
		//本地有对方无
		if(CheckFlag.STATUS_BDYDFW.getCode().equals(checkFlag)){
			try{
				//修改订单表状态
				OrderPayCondition orderPayCondition=new OrderPayCondition();
				orderPayCondition.setTradeNo(tradeNo);
				String orderPayId=orderPayService.findAll(orderPayCondition).get(0).getId();
				orderPayCondition.setId(orderPayId);
				orderPayCondition.setPayStatus(OrderPayStatus.STATUS_ZFSB.getCode());
				count=orderPayService.update(orderPayCondition);
				if(count==0){
					flag=false;
					return flag;
				}
				//修改交易表状态
				OrderPaymentCondition orderPaymentCondition=new OrderPaymentCondition();
				orderPaymentCondition.setTradeNo(tradeNo);
				String orderPaymentId=orderPaymentService.findAll(orderPaymentCondition).get(0).getId();
				orderPaymentCondition.setId(orderPaymentId);
				orderPaymentCondition.setResultCode(OrderPaymentStatus.STATUS_JYSB.getCode());
				count=orderPaymentService.update(orderPaymentCondition);
				if(count==0){
					flag=false;
					return flag;
				}
			}catch(Exception e){
				flag=false;
				throw e;
			}
		}
		//双方不一致
		if(CheckFlag.STATUS_SFBYZ.getCode().equals(checkFlag)){
			//暂时不处理
			flag=false;
			return flag;
		}
		return flag;
	}
	
}

