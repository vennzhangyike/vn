/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.entity.vo.MerchantAccountsVo;
import com.hfepay.scancode.api.entity.vo.MerchantInfoVo;
import com.hfepay.scancode.api.entity.vo.MerchantRateVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.commons.BatchNoUtil;
import com.hfepay.scancode.commons.TransCodeEnum;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayTmpCondition;
import com.hfepay.scancode.commons.condition.ParamsInfoCondition;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MerchantPaywayDAO;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.ChangeLogService;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;
import com.hfepay.scancode.service.operator.MerchantPaywayTmpService;
import com.hfepay.scancode.service.operator.ParamsInfoService;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Service("merchantPaywayService")
public class MerchantPaywayServiceImpl implements MerchantPaywayService {
	
	public static final Logger log = LoggerFactory.getLogger(MerchantPaywayServiceImpl.class);
	
	@Autowired
    private MerchantPaywayDAO merchantPaywayDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
	@Autowired
	private ParamsInfoService paramsInfoService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private MerchantPaywayTmpService merchantPaywayTmpService;
	@Autowired
	private MerchantBankcardService merchantBankcardService;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private ChangeLogService changeLogService;
	@Autowired
	private MappingDicionService mappingDicionService;
    
    /**
	 * 列表(分页)
	 * @param merchantPaywayCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
    @Override
	public PagingResult<MerchantPayway> findPagingResult(MerchantPaywayCondition merchantPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
		if(!Strings.isEmpty(merchantPaywayCondition.getId())){
			cb.andEQ("id", merchantPaywayCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayName())){
			cb.andLike("payName", merchantPaywayCondition.getPayName());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getAcceptStatus())){
			cb.andLike("acceptStatus", merchantPaywayCondition.getAcceptStatus());
		}
		if(null != merchantPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayCondition.getT0Rate());
		}
		if(null != merchantPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayCondition.getT1Rate());
		}
		if(null != merchantPaywayCondition.getRate()){
			cb.andEQ("rate", merchantPaywayCondition.getRate());
		}
		if(null != merchantPaywayCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getStatus())){
			cb.andEQ("status", merchantPaywayCondition.getStatus());
		}
		if(null != merchantPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayCondition.getRemark())){
			cb.andLike("remark", merchantPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantPaywayCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantPaywayCondition.getNodeSeq());
		}
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantPaywayCondition.getOrderBy())){
			if(merchantPaywayCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantPaywayCondition.getOrderBy().split(",");
				String[] orders = merchantPaywayCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantPaywayCondition.getOrderBy(), Order.valueOf(merchantPaywayCondition.getOrder()));
			}
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantPaywayCondition.getFirst()), Long.valueOf(merchantPaywayCondition.getLast()));
		return merchantPaywayDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param merchantPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public List<MerchantPayway> findAll(MerchantPaywayCondition merchantPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
		if(!Strings.isEmpty(merchantPaywayCondition.getId())){
			cb.andEQ("id", merchantPaywayCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayName())){
			cb.andLike("payName", merchantPaywayCondition.getPayName());
		}
		if(null != merchantPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayCondition.getT0Rate());
		}
		if(null != merchantPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayCondition.getT1Rate());
		}
		if(null != merchantPaywayCondition.getRate()){
			cb.andEQ("rate", merchantPaywayCondition.getRate());
		}
		if(null != merchantPaywayCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getStatus())){
			cb.andEQ("status", merchantPaywayCondition.getStatus());
		}
		if(null != merchantPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayCondition.getRemark())){
			cb.andLike("remark", merchantPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantPaywayCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantPaywayCondition.getNodeSeq());
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		return merchantPaywayDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public MerchantPayway findById(String id){
		return merchantPaywayDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantPaywayCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long insert(MerchantPaywayCondition merchantPaywayCondition){
		MerchantPayway merchantPayway = new MerchantPayway();
		try {
			MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantPaywayCondition.getMerchantNo()));
			merchantPaywayCondition.setChannelNo(merchantInfo.getChannelNo());
			merchantPaywayCondition.setAgentNo(merchantInfo.getAgentNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(merchantPaywayCondition, merchantPayway);
		return merchantPaywayDAO.insert(merchantPayway);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long deleteById(String id){
		return merchantPaywayDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long deleteByCriteria(MerchantPaywayCondition merchantPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
		if(!Strings.isEmpty(merchantPaywayCondition.getId())){
			cb.andEQ("id", merchantPaywayCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayName())){
			cb.andEQ("payName", merchantPaywayCondition.getPayName());
		}
		if(null != merchantPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayCondition.getT0Rate());
		}
		if(null != merchantPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayCondition.getT1Rate());
		}
		if(null != merchantPaywayCondition.getRate()){
			cb.andEQ("rate", merchantPaywayCondition.getRate());
		}
		if(null != merchantPaywayCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getStatus())){
			cb.andEQ("status", merchantPaywayCondition.getStatus());
		}
		if(null != merchantPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayCondition.getRemark())){
			cb.andLike("remark", merchantPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantPayway merchantPayway = new MerchantPayway();
		BeanUtils.copyProperties(merchantPaywayCondition, merchantPayway);
		return merchantPaywayDAO.deleteByCriteria(buildCriteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long countByCriteria(MerchantPaywayCondition merchantPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
		if(!Strings.isEmpty(merchantPaywayCondition.getId())){
			cb.andEQ("id", merchantPaywayCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayName())){
			cb.andEQ("payName", merchantPaywayCondition.getPayName());
		}
		if(null != merchantPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayCondition.getT0Rate());
		}
		if(null != merchantPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayCondition.getT1Rate());
		}
		if(null != merchantPaywayCondition.getRate()){
			cb.andEQ("rate", merchantPaywayCondition.getRate());
		}
		if(null != merchantPaywayCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getStatus())){
			cb.andEQ("status", merchantPaywayCondition.getStatus());
		}
		if(null != merchantPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayCondition.getRemark())){
			cb.andLike("remark", merchantPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayCondition.getTemp2());
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		MerchantPayway merchantPayway = new MerchantPayway();
		BeanUtils.copyProperties(merchantPaywayCondition, merchantPayway);
		return merchantPaywayDAO.countByCriteria(buildCriteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long update(MerchantPaywayCondition merchantPaywayCondition){
		MerchantPayway merchantPayway = new MerchantPayway();
		BeanUtils.copyProperties(merchantPaywayCondition, merchantPayway);
		return merchantPaywayDAO.update(merchantPayway);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long updateByCriteria(MerchantPaywayCondition merchantPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
		if(!Strings.isEmpty(merchantPaywayCondition.getId())){
			cb.andEQ("id", merchantPaywayCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayCondition.getRecordStatus());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantPayway merchantPayway = new MerchantPayway();
		BeanUtils.copyProperties(merchantPaywayCondition, merchantPayway);
		return merchantPaywayDAO.updateByCriteria(merchantPayway,buildCriteria);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long updateByCriteria(MerchantPaywayCondition merchantPaywayCondition,Criteria buildCriteria){
		MerchantPayway merchantPayway = new MerchantPayway();
		BeanUtils.copyProperties(merchantPaywayCondition, merchantPayway);
		return merchantPaywayDAO.updateByCriteria(merchantPayway,buildCriteria);
	}

	/** 根据支付通道查询 */
	@Override
	public MerchantPayway findByPayCode(String payCode, String merchantNo) {
		CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
		cb.andEQ("payCode", payCode);
		cb.andEQ("merchantNo", merchantNo);
		cb.andEQ("recordStatus", "Y");
		Criteria buildCriteria = cb.buildCriteria();
		return merchantPaywayDAO.findOneByCriteria(buildCriteria);
	}
	
	/** 添加清算手续费
	 * @throws Exception */
	@Override
	@Transactional
	public void addLiquidationFee(String channelNo ,String liquidationFee) throws Exception{	
		redisSharedCache.del(new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,HfepayConfig.CHANNEL_REDIS_LF_KEY+ channelNo));
		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, -1);		
		String time = Dates.format(Dates.DF.yyyy_MM_dd, cal.getTime());
		Map<String, String> params = new HashMap<String, String>();
		params.put("liquidationFee", liquidationFee);
		params.put("time", time);//此时间用于判断跑定时任务时前一天是否一致
		//结算费率
		String settleAmtParams = JSONSerializer.toJSON(params).toString();
		
		//保存结算费率
		this.saveSettleamt(settleAmtParams,channelNo);
		redisSharedCache.setObj(new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,HfepayConfig.CHANNEL_REDIS_LF_KEY+ channelNo), params);
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
		log.info("批量调用商户费率变更接口,channelNo:" + channelNo);
		try {
			MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
			merchantInfoCondition.setChannelNo(channelNo);
			merchantInfoCondition.setStatus(MerchantStatus.MERCHANT_STATUS_3.getCode());
			List<MerchantInfo> list = merchantInfoService.findAll(merchantInfoCondition);
			for (MerchantInfo merchantInfo : list) {
				log.info("批量调用商户费率变更接口,merchantNo:" + merchantInfo.getMerchantNo());
				MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
				merchantPaywayCondition.setMerchantNo(merchantInfo.getMerchantNo());
			    List<MerchantPayway> merchantPaywayList = this.findAll(merchantPaywayCondition);
			    for (MerchantPayway merchantPayway : merchantPaywayList) {
			    	log.info("批量调用商户费率变更接口,merchantNo:" + merchantPayway.getMerchantNo() + ",payCode:" + merchantPayway.getPayCode());
			    	MerchantRateVo payway = new MerchantRateVo();
					payway.setPayCode(merchantPayway.getPayCode());
					payway.setTradeRate(this.objToString(String.valueOf(merchantPayway.getT1Rate())));
					payway.setWithdrawAmt(this.objToString(String.valueOf(merchantPayway.getRate())));
					payway.setWithdrawRate(this.objToString(String.valueOf(merchantPayway.getT0Rate())));
					payway.setSettleAmt(this.objToString(String.valueOf(merchantPayway.getRateAmount())));
					//调用商户费率变更接口
					Map<String,String> res = merchantBusinessService.merchantUpdateRate(merchantInfo.getPlatformMerchantNo(), payway);
					log.info("批量调用商户费率变更接口成功,结果：" + JSONSerializer.toJSON(res) + ",merchantNo:" + merchantPayway.getMerchantNo() + ",payCode:" + merchantPayway.getPayCode());
				}
			}
			log.info("批量调用商户费率变更接口完成,channelNo:" + channelNo);
		} catch (Exception e) {
			log.error("批量调用商户费率变更接口,channelNo:" + channelNo + ",message:" + e.getMessage());
		}
        
		
	}
	
	/**
	 * 保存结算费率
	 * @param smsParams
	 * @param channelBaseCondition
	 */
	private void saveSettleamt(String settleAmtParams, String channelNo) {
		ParamsInfoCondition paramsInfoCondition = new ParamsInfoCondition();
		paramsInfoCondition.setParamsKey(channelNo);
		paramsInfoCondition.setParamsType(ParamsType.PARAMS_SETTLEAMT.getCode());
		List<ParamsInfo> list = paramsInfoService.findAll(paramsInfoCondition);
		paramsInfoCondition.setParamsValue(settleAmtParams);
		if (null == list || list.size() <= 0) {
			paramsInfoCondition.setParamsKey(channelNo);
			paramsInfoCondition.setParamsType(ParamsType.PARAMS_SETTLEAMT.getCode());
			paramsInfoService.insert(paramsInfoCondition);			
		}else {
			paramsInfoCondition.setId(list.get(0).getId());
			paramsInfoService.update(paramsInfoCondition);
		}
	}
	
	/** redis中获取清算手续费
	 * @throws Exception */
	public Map<String, String> getLiquidationFee(String channelNo) throws Exception{
		Map<String, String> map = (Map<String, String>) redisSharedCache.getObj(new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,HfepayConfig.CHANNEL_REDIS_LF_KEY+ channelNo));
		if (null == map) {			
			ParamsInfoCondition paramsInfoCondition = new ParamsInfoCondition();
			paramsInfoCondition.setParamsKey(channelNo);
			paramsInfoCondition.setParamsType(ParamsType.PARAMS_SETTLEAMT.getCode());
			List<ParamsInfo> list = paramsInfoService.findAll(paramsInfoCondition);
			if (list != null && !list.isEmpty()) {
				map = new HashMap<>();
				ParamsInfo paramsInfo = list.get(0);
				Object liquidationFee = JSONObject.fromObject(paramsInfo.getParamsValue()).get("liquidationFee");
				if(liquidationFee != null){
					
					map.put("liquidationFee", String.valueOf(liquidationFee));
				}
			}
		}
		return map;
	}

	@Override
	public List<Map<String, BigDecimal>> findMerchantPayWayByAgentNo(String agentNo,String payCode) {
		
		return merchantPaywayDAO.findMerchantPayWayByAgentNo(agentNo,payCode);
	}
	
	/** 保存费率 
	 * @throws Exception */
	@Override
	@Transactional
	public Map<String,String> save(MerchantPaywayCondition merchantPaywayCondition) throws Exception{
		Map<Object, Object> map = new HashMap<Object, Object>();
		MerchantInfo entity = merchantInfoService.findByMerchantNo(merchantPaywayCondition.getMerchantNo());
		if(Strings.isEmpty(merchantPaywayCondition.getId())){			
			Map<String, String> params = this.getLiquidationFee(entity.getChannelNo());
			if(null != params && !params.isEmpty()){
				BigDecimal rateAmount = new BigDecimal(0);
				String liquidationFee = params.get("liquidationFee");
				if(Strings.isNotEmpty(liquidationFee)){
					rateAmount = new BigDecimal(liquidationFee);
				}
				merchantPaywayCondition.setRateAmount(rateAmount);
			}else{
				merchantPaywayCondition.setRateAmount(new BigDecimal(0));
			}
			
			//新增
			merchantPaywayCondition.setCreateTime(new Date());
			merchantPaywayCondition.setId(Strings.getUUID());
			merchantPaywayCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_YES);
			merchantPaywayCondition.setStatus(ScanCodeConstants.SUCCESS_STATE);
			merchantPaywayCondition.setAcceptStatus(ScanCodeConstants.FAIL_STATE);				
			this.insert(merchantPaywayCondition);
			if (MerchantStatus.MERCHANT_STATUS_3.getCode().equals(entity.getStatus()) ||
					MerchantStatus.MERCHANT_STATUS_6.getCode().equals(entity.getStatus()) ||
					MerchantStatus.MERCHANT_STATUS_7.getCode().equals(entity.getStatus())) {
				paywayJoin(merchantPaywayCondition, map);
			}
		}else{
			MerchantPayway merchantPayway = this.findById(merchantPaywayCondition.getId());
			JSON res = null;
			if(!MerchantStatus.MERCHANT_STATUS_3.getCode().equals(entity.getStatus())){
				merchantPaywayCondition.setRateAmount(merchantPayway.getRateAmount());
				merchantPaywayCondition.setPayCode(merchantPayway.getPayCode());
				merchantPaywayCondition.setUpdateTime(new Date());
				this.update(merchantPaywayCondition);
			}else{
				
				if(ScanCodeConstants.STATUS_NOT_USE.equals(merchantPayway.getAcceptStatus()) 
						|| ScanCodeConstants.STATUS_INIT.equals(merchantPayway.getAcceptStatus())){
					merchantPaywayCondition.setRateAmount(merchantPayway.getRateAmount());
					merchantPaywayCondition.setPayCode(merchantPayway.getPayCode());
					res = paywayJoin(merchantPaywayCondition, map);
					merchantPaywayCondition.setUpdateTime(new Date());
					this.update(merchantPaywayCondition);
				}else{
					if(ScanCodeConstants.STATUS_USE.equals(merchantPayway.getAcceptStatus())
							|| res == null){
						MerchantRateVo payway = new MerchantRateVo();
						payway.setPayCode(merchantPayway.getPayCode());
						payway.setTradeRate(this.objToString(String.valueOf(merchantPaywayCondition.getT1Rate())));
						payway.setWithdrawAmt(this.objToString(String.valueOf(merchantPaywayCondition.getRate())));
						payway.setWithdrawRate(this.objToString(String.valueOf(merchantPaywayCondition.getT0Rate())));
						payway.setSettleAmt(this.objToString(String.valueOf(merchantPaywayCondition.getRateAmount())));
						//调用商户费率变更接口
//					merchantBusinessService.merchantUpdateRate(merchantPaywayCondition.getMerchantNo(), payway);
					}
					
					MerchantPaywayTmpCondition merchantPaywayTmpCondition = new MerchantPaywayTmpCondition();
					BeanUtils.copyProperties(merchantPaywayCondition, merchantPaywayTmpCondition);
					merchantPaywayTmpCondition.setPayCode(merchantPayway.getPayCode());
					merchantPaywayTmpService.save(merchantPaywayTmpCondition);
				}
				
			}
			boolean isChange = false ;
			//有变更才写入数据变更日志
			if(!(merchantPayway.getT0Rate().equals(merchantPaywayCondition.getT0Rate())
							&&merchantPayway.getT1Rate().equals(merchantPaywayCondition.getT1Rate())
							&&merchantPayway.getRate().equals(merchantPaywayCondition.getRate()))){
				
				isChange=true;
			}
			if(isChange){
				ChangeLogCondition changeLogCondition = new ChangeLogCondition();
				String batchNo = BatchNoUtil.getBatchNo();
				changeLogCondition.setTradeNo(merchantPaywayCondition.getId());
				changeLogCondition.setBatchNo(batchNo);
				changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.MERCHANT_PAYWAY_CODE.getValue()));
				changeLogCondition.setTransName(TransCodeEnum.MERCHANT_PAYWAY_CODE.getDesc());
				changeLogCondition.setOwnersNo(merchantPaywayCondition.getMerchantNo());
				changeLogCondition.setBefore(JSONSerializer.toJSON(merchantPayway).toString());
				changeLogCondition.setStatus(ScanCodeConstants.APPROVE_STATUS_NEW);
				changeLogCondition.setOperator(merchantPaywayCondition.getOperator());
				changeLogCondition.setCreateTime(new Date());
				changeLogService.insert(changeLogCondition);
			}
			
		}
		return null;
	}
	
	/** 将null对象转换成空字符串 */
	public String objToString(String obj){
		if(obj == null){
			return "";
		}
		return obj;
	}
	
	/**
	 * 新费率入网
	 * @param merchantPaywayCondition
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSON paywayJoin(MerchantPaywayCondition merchantPaywayCondition, Map<Object, Object> map) throws Exception {
		MerchantInfo info = merchantInfoService.findByMerchantNo(merchantPaywayCondition.getMerchantNo());
		if(info != null && Strings.isNotEmpty(info.getPlatformMerchantNo())){
			//获取费率列表
			MerchantPayway pay = new MerchantPayway();
			BeanUtils.copyProperties(merchantPaywayCondition, pay);
			List<MerchantPayway> payways = new ArrayList<MerchantPayway>();
			payways.add(pay);
			//商户入网
			info.setOperator(merchantPaywayCondition.getOperator());//设置操作人
			JSON res = merchantJoin(map, info, payways);
			if(res != null){
				return res;
			}
		}
		return null;
	}
	
	/**
	 * 调用商户入网接口
	 * @param map 返回web的响应对象map
	 * @param entity 商户基本信息
	 * @param payways 商户费率信息对象列表
	 * 
	 * @throws Exception
	 */
	private JSON merchantJoin(Map<Object, Object> map, MerchantInfo entity,List<MerchantPayway> payways) throws Exception {
		if(entity == null || Strings.isEmpty(entity.getMerchantNo())){
			map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"商户信息不完整，请检查相关信息");
			return JSONSerializer.toJSON(map);
		}
		
		//获取银行卡列表
		MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
		merchantBankcardCondition.setMerchantNo(entity.getMerchantNo());
//		merchantBankcardCondition.setStatus(Constants.STATUS_ACTIVE);
		List<MerchantBankcard> bankcards =  merchantBankcardService.findAll(merchantBankcardCondition);
		if(bankcards.size() == 0){
			map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"商户银行卡信息不存在，请检查相关信息");
			return JSONSerializer.toJSON(map);
		}
		
		MerchantAccountsVo account = new MerchantAccountsVo();
		MerchantBankcard card = bankcards.get(0);
		account.setAccountName(this.objToString(card.getName()));
		account.setBankCard(this.objToString(card.getBankCard()));
		account.setBankCode(this.objToString(card.getBankCode()));
		account.setBankName(this.objToString(card.getBankName()));
		account.setIsRealAccount(this.objToString(card.getIsRealAccount()));
		account.setAccountType(card.getAccountType());
		
		List<MerchantRateVo> paywayList = Lists.newList();
		MerchantRateVo payway = null;
		for (MerchantPayway pay:payways) {
			payway = new MerchantRateVo();
			payway.setPayCode(pay.getPayCode());
			payway.setTradeRate(this.objToString(String.valueOf(pay.getT1Rate())));
			payway.setWithdrawAmt(this.objToString(String.valueOf(pay.getRate())));
			payway.setWithdrawRate(this.objToString(String.valueOf(pay.getT0Rate())));
			payway.setSettleAmt(this.objToString(String.valueOf(pay.getRateAmount())));
			paywayList.add(payway);
		}
		
		MerchantInfoVo info = new MerchantInfoVo();
		info.setMerchantNo(this.objToString(entity.getMerchantNo()));
		info.setMerchantName(this.objToString(entity.getMerchantName()));
		info.setShortName(this.objToString(entity.getShortName()));
		info.setAddress(this.objToString(entity.getAddress()));
		info.setSerPhone(this.objToString(entity.getPhone()));
		info.setCategory(this.objToString(entity.getBusType()));
		info.setIdCard(this.objToString(entity.getIdCard()));
		info.setName(this.objToString(entity.getName()));
		info.setMerchantLicense(this.objToString(entity.getMerchantLicense()));
		info.setPhone(this.objToString(entity.getPhone()));
		info.setMobile(this.objToString(entity.getMobile()));
		info.setEmail(this.objToString(entity.getEmail()));
		info.setRemark(this.objToString(entity.getRemark()));
		
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo("JOINCALLBACKURL");
		MappingDicion mappingDicion = mappingDicionService.findByCondition(mappingDicionCondition);
		info.setNotifyUrl(mappingDicion.getParaVal());
		info.setTerminalId("");
		info.setPositionCode("");
		info.setMainBusiness("");
		info.setBusinessRange("");
		info.setMerchantTypeCode("");
		info.setMerchantNature("");
		info.setDistrict("");
		
		//调用商户入网接口
		Map<String,String> res = merchantBusinessService.merchantJoin(info,account,paywayList);
		//解析返回结果
		Map<Object,Object> res1 = afterMerchantJoin(entity,res);
		if(res1 != null) return JSONSerializer.toJSON(res1);
		return null;
	}
	
	/**
	 * 解析商户入驻返回结果
	 * @param res
	 */
	private Map<Object, Object> afterMerchantJoin(MerchantInfo info,Map<String,String> res) {
		String respCode = res.get("respCode");
		if(ScanCodeConstants.INTERFACE_SUCCESS_STATUS.equals(respCode)){
			String merchantNo = res.get("merchantNo");
			String auditStatus = res.get("auditStatus");
			if (auditStatus.equals(ScanCodeConstants.INTERFACE_SUCCESS_STATUS)) {
				JSONArray paytypes = JSONArray.fromObject(res.get("payType"));
				MerchantPaywayCondition pay = null;
				for (Object string : paytypes) {
					JSONObject map = JSONObject.fromObject(string);
					String payCode = map.getString("payCode");
					String status = map.getString("status");
					String respDesc = map.getString("respDesc");
					//System.err.println(merchantNo+"=="+payCode+"=="+status+"=="+respDesc);
					pay = new MerchantPaywayCondition();
					pay.setMerchantNo(merchantNo);
					pay.setPayCode(payCode);
					pay.setUpdateTime(new Date());
					pay.setOperator(info.getOperator());
					pay.setRemark(respDesc);
					if(ScanCodeConstants.INTERFACE_SUCCESS_STATUS.equals(status)){
						pay.setAcceptStatus(ScanCodeConstants.STATUS_ACTIVE);
					}else{
						//pay.setAcceptStatus(Constants.STATUS_NOT_USE);
					}
					this.updateByCriteria(pay);
				}
			}
		}else{
			return Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,res.get("respDesc"));
		}
		return null;
	}
	
}

