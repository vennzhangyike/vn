/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.timer.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.scancode.api.entity.vo.MerchantRateVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.dao.ChannelBaseDAO;
import com.hfepay.scancode.commons.dao.MerchantInfoDAO;
import com.hfepay.scancode.commons.dao.MerchantPaywayDAO;
import com.hfepay.scancode.commons.dao.ParamsInfoDAO;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.timer.service.MerchantPaywayService;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Service("merchantPaywayService")
public class MerchantPaywayServiceImpl implements MerchantPaywayService {
	
	public static final Logger logger = LoggerFactory.getLogger(MerchantPaywayServiceImpl.class);
	
	@Autowired
    private MerchantPaywayDAO merchantPaywayDAO;
	@Autowired
	private MerchantInfoDAO merchantInfoDAO;
	@Autowired
	private ChannelBaseDAO channelBaseDAO;
	@Autowired
    private ParamsInfoDAO paramsInfoDAO;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
    
	/**
	 * 清算手续费job
	 */
	@Override
	@Transactional
	public void liquidationFeeJob(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);		
		String dateStr = Dates.format(Dates.DF.yyyy_MM_dd, cal.getTime());
		logger.info("开始执行清算手续费任务,执行日期：" + dateStr);
		List<ChannelBase> channelBases = channelBaseDAO.findAll();
		for (ChannelBase channelBase : channelBases) {
			CriteriaBuilder cb = Cnd.builder(ParamsInfo.class);
			cb.andEQ("paramsKey", channelBase.getChannelNo());
			cb.andEQ("paramsType", ParamsType.PARAMS_SETTLEAMT.getCode());
			Criteria buildCriteria = cb.buildCriteria();
			ParamsInfo paramsInfo = paramsInfoDAO.findOneByCriteria(buildCriteria);
			logger.info("开始执行清算手续费任务,paramsInfo：" + JSONSerializer.toJSON(paramsInfo));
			if(paramsInfo != null){
				Object time = JSONObject.fromObject(paramsInfo.getParamsValue()).get("time");
				Object liquidationFee = JSONObject.fromObject(paramsInfo.getParamsValue()).get("liquidationFee");
				if(time != null && Strings.equals(dateStr, String.valueOf(time)) && liquidationFee != null){
					try {
						this.setLiquidationFee(channelBase.getChannelNo(), String.valueOf(liquidationFee));
					} catch (Exception e) {
						logger.info("设置清算手续费失败:" + e.getMessage());
						throw new RuntimeException(e.getMessage());
					}
				}
			 }
		}
	}
	
	/**
	 * 设置清算手续费
	 * @param channelNo
	 * @param liquidationFee
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void setLiquidationFee(String channelNo ,String liquidationFee) throws Exception{	
		merchantPaywayDAO.updateRateAmount(channelNo,liquidationFee);
		this.batchUpdateRate(channelNo);
	}
	
	/** 批量调用商户费率变更接口*/
	private void batchUpdateRate(String channelNo){
		logger.info("批量调用商户费率变更接口,channelNo:" + channelNo);
		try {
			CriteriaBuilder cbMerchant = Cnd.builder(MerchantInfo.class);
			cbMerchant.andEQ("channelNo", channelNo);
			cbMerchant.andEQ("status", MerchantStatus.MERCHANT_STATUS_3.getCode());
			Criteria buildCriteriaMerchant = cbMerchant.buildCriteria();
			List<MerchantInfo> list = merchantInfoDAO.findByCriteria(buildCriteriaMerchant);
			for (MerchantInfo merchantInfo : list) {
				logger.info("批量调用商户费率变更接口,merchantNo:" + merchantInfo.getMerchantNo());
				CriteriaBuilder cbPayway = Cnd.builder(MerchantPayway.class);
				cbPayway.andEQ("merchantNo", merchantInfo.getMerchantNo());
				Criteria buildCriteriaPayway = cbPayway.buildCriteria();
				List<MerchantPayway> merchantPaywayList = merchantPaywayDAO.findByCriteria(buildCriteriaPayway);
				
			    for (MerchantPayway merchantPayway : merchantPaywayList) {
			    	logger.info("批量调用商户费率变更接口,merchantNo:" + merchantPayway.getMerchantNo() + ",payCode:" + merchantPayway.getPayCode());
			    	MerchantRateVo payway = new MerchantRateVo();
					payway.setPayCode(merchantPayway.getPayCode());
					payway.setTradeRate(this.objToString(String.valueOf(merchantPayway.getT1Rate())));
					payway.setWithdrawAmt(this.objToString(String.valueOf(merchantPayway.getRate())));
					payway.setWithdrawRate(this.objToString(String.valueOf(merchantPayway.getT0Rate())));
					payway.setSettleAmt(this.objToString(String.valueOf(merchantPayway.getRateAmount())));
					//调用商户费率变更接口
					try {
						Map<String,String> res = merchantBusinessService.merchantUpdateRate(merchantInfo.getPlatformMerchantNo(), payway);
						logger.info("批量调用商户费率变更接口成功,结果：" + JSONSerializer.toJSON(res) + ",merchantNo:" + merchantPayway.getMerchantNo() + ",payCode:" + merchantPayway.getPayCode());
					} catch (Exception e) {
						logger.info("单个调用商户费率变更接口失败 ,merchantNo:" + merchantPayway.getMerchantNo()  + ",payCode:" + merchantPayway.getPayCode() + ",message:" + e.getMessage());
					}
				}
			}
			logger.info("批量调用商户费率变更接口完成,channelNo:" + channelNo);
		} catch (Exception e) {
			logger.info("批量调用商户费率变更接口失败,channelNo:" + channelNo + ",message:" + e.getMessage());
		}
	}
	
	/** 将null对象转换成空字符串 */
	public String objToString(String obj){
		if(obj == null){
			return "";
		}
		return obj;
	}
}

