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
import com.hfepay.scancode.commons.condition.ClearingT0ErrCondition;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.CheckFlag;
import com.hfepay.scancode.commons.contants.OrderPayStatus;
import com.hfepay.scancode.commons.contants.OrderPaymentStatus;
import com.hfepay.scancode.commons.contants.ProcessingStatus;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.contants.WithdrawsStatus;
import com.hfepay.scancode.commons.dao.ClearingT0ErrDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ClearingT0Err;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.operator.ClearingT0ErrService;
import com.hfepay.scancode.service.order.OrderPayService;
import com.hfepay.scancode.service.order.OrderPaymentService;
import com.hfepay.scancode.service.order.WithdrawalsService;
import com.hfepay.scancode.service.order.impl.OrderPayServiceImpl;

@Service("clearingT0ErrService")
public class ClearingT0ErrServiceImpl implements ClearingT0ErrService {
	
	public static final Logger log = LoggerFactory.getLogger(OrderPayServiceImpl.class);
	
	@Autowired
	private RedisSharedCache redisSharedCache;
	
	@Autowired
    private ClearingT0ErrDAO clearingT0ErrDAO;
	
	@Autowired
	private WithdrawalsService  withdrawalsService;

	@Autowired
	private OrderPaymentService orderPaymentService;

	@Autowired
	private OrderPayService orderPayService;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: PagingResult<ClearingT0Err>
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
    @Override
	public PagingResult<ClearingT0Err> findPagingResult(ClearingT0ErrCondition clearingT0ErrCondition){
		CriteriaBuilder cb = Cnd.builder(ClearingT0Err.class);
		if(!Strings.isEmpty(clearingT0ErrCondition.getId())){
			cb.andEQ("id", clearingT0ErrCondition.getId());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getBatchNo())){
			cb.andEQ("batchNo", clearingT0ErrCondition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT0ErrCondition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT0ErrCondition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT0ErrCondition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT0ErrCondition.getMerchantNo());
		}
		if(null != clearingT0ErrCondition.getTransAmt()){
			cb.andEQ("transAmt", clearingT0ErrCondition.getTransAmt());
		}
		if(null != clearingT0ErrCondition.getArrivalAmt()){
			cb.andEQ("arrivalAmt", clearingT0ErrCondition.getArrivalAmt());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getCheckFlag())){
			cb.andEQ("checkFlag", clearingT0ErrCondition.getCheckFlag());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getProcessingStatus())){
			cb.andEQ("processingStatus", clearingT0ErrCondition.getProcessingStatus());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getClearDate())){
			cb.andEQ("clearDate", clearingT0ErrCondition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT0ErrCondition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTemp1())){
			cb.andEQ("temp1", clearingT0ErrCondition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTemp2())){
			cb.andEQ("temp2", clearingT0ErrCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(clearingT0ErrCondition.getFirst()), Long.valueOf(clearingT0ErrCondition.getLast()));
		PagingResult<ClearingT0Err> result = clearingT0ErrDAO.findPagingResult(buildCriteria);
		List<ClearingT0Err> clearingT0Errs=result.getResult();
		for(ClearingT0Err t0:clearingT0Errs){
			try{
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+t0.getChannelNo()));
				if(channelBase != null){
					t0.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+t0.getAgentNo()));
				if(agentBase != null){
					t0.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+t0.getMerchantNo()));
				if(merchantInfo != null){
					t0.setMerchantName(merchantInfo.getMerchantName());
				}
			}catch(Exception e){
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		
		return result;
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: List<ClearingT0Err>
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@Override
	public List<ClearingT0Err> findAll(ClearingT0ErrCondition clearingT0ErrCondition){
		CriteriaBuilder cb = Cnd.builder(ClearingT0Err.class);
		if(!Strings.isEmpty(clearingT0ErrCondition.getId())){
			cb.andEQ("id", clearingT0ErrCondition.getId());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getBatchNo())){
			cb.andEQ("batchNo", clearingT0ErrCondition.getBatchNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTradeNo())){
			cb.andEQ("tradeNo", clearingT0ErrCondition.getTradeNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getHfTradeNo())){
			cb.andEQ("hfTradeNo", clearingT0ErrCondition.getHfTradeNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getHfMerchantNo())){
			cb.andEQ("hfMerchantNo", clearingT0ErrCondition.getHfMerchantNo());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", clearingT0ErrCondition.getMerchantNo());
		}
		if(null != clearingT0ErrCondition.getTransAmt()){
			cb.andEQ("transAmt", clearingT0ErrCondition.getTransAmt());
		}
		if(null != clearingT0ErrCondition.getArrivalAmt()){
			cb.andEQ("arrivalAmt", clearingT0ErrCondition.getArrivalAmt());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getCheckFlag())){
			cb.andEQ("checkFlag", clearingT0ErrCondition.getCheckFlag());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getProcessingStatus())){
			cb.andEQ("processingStatus", clearingT0ErrCondition.getProcessingStatus());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getClearDate())){
			cb.andEQ("clearDate", clearingT0ErrCondition.getClearDate());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTradeDate())){
			cb.andEQ("tradeDate", clearingT0ErrCondition.getTradeDate());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTemp1())){
			cb.andEQ("temp1", clearingT0ErrCondition.getTemp1());
		}
		if(!Strings.isEmpty(clearingT0ErrCondition.getTemp2())){
			cb.andEQ("temp2", clearingT0ErrCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return clearingT0ErrDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ClearingT0Err
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@Override
	public ClearingT0Err findById(String id){
		return clearingT0ErrDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@Override
	public long insert(ClearingT0ErrCondition clearingT0ErrCondition){
		ClearingT0Err clearingT0Err = new ClearingT0Err();
		BeanUtils.copyProperties(clearingT0ErrCondition, clearingT0Err);
		return clearingT0ErrDAO.insert(clearingT0Err);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@Override
	public long deleteById(String id){
		return clearingT0ErrDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return clearingT0ErrDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return clearingT0ErrDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@Override
	public long update(ClearingT0ErrCondition clearingT0ErrCondition){
		ClearingT0Err clearingT0Err = new ClearingT0Err();
		BeanUtils.copyProperties(clearingT0ErrCondition, clearingT0Err);
		return clearingT0ErrDAO.update(clearingT0Err);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param clearingT0ErrCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@Override
	public long updateByCriteria(ClearingT0ErrCondition clearingT0ErrCondition,Criteria criteria){
		ClearingT0Err clearingT0Err = new ClearingT0Err();
		BeanUtils.copyProperties(clearingT0ErrCondition, clearingT0Err);
		return clearingT0ErrDAO.updateByCriteria(clearingT0Err,criteria);
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
			//修改t0对账表的状态
			ClearingT0ErrCondition clearingT0ErrCondition=new ClearingT0ErrCondition();
			clearingT0ErrCondition.setId(id);
			clearingT0ErrCondition.setProcessingStatus(ProcessingStatus.STATUS_CLCG.getCode());
			count=this.update(clearingT0ErrCondition);
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

