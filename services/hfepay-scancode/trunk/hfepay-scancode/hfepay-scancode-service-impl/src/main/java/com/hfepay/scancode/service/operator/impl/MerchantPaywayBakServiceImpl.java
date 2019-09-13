/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;
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
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayBakCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayTmpCondition;
import com.hfepay.scancode.commons.condition.ParamsInfoCondition;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MerchantPaywayBakDAO;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPaywayBak;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayBakService;
import com.hfepay.scancode.service.operator.MerchantPaywayTmpService;
import com.hfepay.scancode.service.operator.ParamsInfoService;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Service("merchantPaywayBakService")
public class MerchantPaywayBakServiceImpl implements MerchantPaywayBakService {
	
	@Autowired
    private MerchantPaywayBakDAO merchantPaywayBakDAO;
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
    
    /**
	 * 列表(分页)
	 * @param merchantPaywayBakCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
    @Override
	public PagingResult<MerchantPaywayBak> findPagingResult(MerchantPaywayBakCondition merchantPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayBak.class);
		if(!Strings.isEmpty(merchantPaywayBakCondition.getId())){
			cb.andEQ("id", merchantPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayBakCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayName())){
			cb.andLike("payName", merchantPaywayBakCondition.getPayName());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getAcceptStatus())){
			cb.andLike("acceptStatus", merchantPaywayBakCondition.getAcceptStatus());
		}
		if(null != merchantPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayBakCondition.getT0Rate());
		}
		if(null != merchantPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayBakCondition.getT1Rate());
		}
		if(null != merchantPaywayBakCondition.getRate()){
			cb.andEQ("rate", merchantPaywayBakCondition.getRate());
		}
		if(null != merchantPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayBakCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getStatus())){
			cb.andEQ("status", merchantPaywayBakCondition.getStatus());
		}
		if(null != merchantPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getRemark())){
			cb.andLike("remark", merchantPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayBakCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantPaywayBakCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantPaywayBakCondition.getNodeSeq());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantPaywayBakCondition.getOrderBy())){
			if(merchantPaywayBakCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantPaywayBakCondition.getOrderBy().split(",");
				String[] orders = merchantPaywayBakCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantPaywayBakCondition.getOrderBy(), Order.valueOf(merchantPaywayBakCondition.getOrder()));
			}
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantPaywayBakCondition.getFirst()), Long.valueOf(merchantPaywayBakCondition.getLast()));
		return merchantPaywayBakDAO.findPagingResult(buildCriteria);
	}
	
    
	/**
	 * 列表
	 * @param merchantPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public List<MerchantPaywayBak> findAll(MerchantPaywayBakCondition merchantPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayBak.class);
		if(!Strings.isEmpty(merchantPaywayBakCondition.getId())){
			cb.andEQ("id", merchantPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayBakCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayName())){
			cb.andLike("payName", merchantPaywayBakCondition.getPayName());
		}
		if(null != merchantPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayBakCondition.getT0Rate());
		}
		if(null != merchantPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayBakCondition.getT1Rate());
		}
		if(null != merchantPaywayBakCondition.getRate()){
			cb.andEQ("rate", merchantPaywayBakCondition.getRate());
		}
		if(null != merchantPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayBakCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getStatus())){
			cb.andEQ("status", merchantPaywayBakCondition.getStatus());
		}
		if(null != merchantPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getRemark())){
			cb.andLike("remark", merchantPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayBakCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantPaywayBakCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantPaywayBakCondition.getNodeSeq());
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		return merchantPaywayBakDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public MerchantPaywayBak findById(String id){
		return merchantPaywayBakDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantPaywayBakCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long insert(MerchantPaywayBakCondition merchantPaywayBakCondition){
		MerchantPaywayBak merchantPayway = new MerchantPaywayBak();
		try {
			MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantPaywayBakCondition.getMerchantNo()));
			merchantPaywayBakCondition.setChannelNo(merchantInfo.getChannelNo());
			merchantPaywayBakCondition.setAgentNo(merchantInfo.getAgentNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(merchantPaywayBakCondition, merchantPayway);
		return merchantPaywayBakDAO.insert(merchantPayway);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long deleteById(String id){
		return merchantPaywayBakDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long deleteByCriteria(MerchantPaywayBakCondition merchantPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayBak.class);
		if(!Strings.isEmpty(merchantPaywayBakCondition.getId())){
			cb.andEQ("id", merchantPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayBakCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayName())){
			cb.andEQ("payName", merchantPaywayBakCondition.getPayName());
		}
		if(null != merchantPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayBakCondition.getT0Rate());
		}
		if(null != merchantPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayBakCondition.getT1Rate());
		}
		if(null != merchantPaywayBakCondition.getRate()){
			cb.andEQ("rate", merchantPaywayBakCondition.getRate());
		}
		if(null != merchantPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayBakCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getStatus())){
			cb.andEQ("status", merchantPaywayBakCondition.getStatus());
		}
		if(null != merchantPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getRemark())){
			cb.andLike("remark", merchantPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayBakCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantPaywayBak merchantPayway = new MerchantPaywayBak();
		BeanUtils.copyProperties(merchantPaywayBakCondition, merchantPayway);
		return merchantPaywayBakDAO.deleteByCriteria(buildCriteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long countByCriteria(MerchantPaywayBakCondition merchantPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayBak.class);
		if(!Strings.isEmpty(merchantPaywayBakCondition.getId())){
			cb.andEQ("id", merchantPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayBakCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayName())){
			cb.andEQ("payName", merchantPaywayBakCondition.getPayName());
		}
		if(null != merchantPaywayBakCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayBakCondition.getT0Rate());
		}
		if(null != merchantPaywayBakCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayBakCondition.getT1Rate());
		}
		if(null != merchantPaywayBakCondition.getRate()){
			cb.andEQ("rate", merchantPaywayBakCondition.getRate());
		}
		if(null != merchantPaywayBakCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayBakCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayBakCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getStatus())){
			cb.andEQ("status", merchantPaywayBakCondition.getStatus());
		}
		if(null != merchantPaywayBakCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayBakCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayBakCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayBakCondition.getRemark())){
			cb.andLike("remark", merchantPaywayBakCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayBakCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayBakCondition.getTemp2());
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		MerchantPaywayBak merchantPayway = new MerchantPaywayBak();
		BeanUtils.copyProperties(merchantPaywayBakCondition, merchantPayway);
		return merchantPaywayBakDAO.countByCriteria(buildCriteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long update(MerchantPaywayBakCondition merchantPaywayBakCondition){
		MerchantPaywayBak merchantPayway = new MerchantPaywayBak();
		BeanUtils.copyProperties(merchantPaywayBakCondition, merchantPayway);
		return merchantPaywayBakDAO.update(merchantPayway);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public long updateByCriteria(MerchantPaywayBakCondition merchantPaywayBakCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayBak.class);
		if(!Strings.isEmpty(merchantPaywayBakCondition.getId())){
			cb.andEQ("id", merchantPaywayBakCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayBakCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayBakCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayBakCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayBakCondition.getRecordStatus());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantPaywayBak merchantPayway = new MerchantPaywayBak();
		BeanUtils.copyProperties(merchantPaywayBakCondition, merchantPayway);
		return merchantPaywayBakDAO.updateByCriteria(merchantPayway,buildCriteria);
	}

	/** 根据支付通道查询 */
	@Override
	public MerchantPaywayBak findByPayCode(String payCode, String merchantNo) {
		CriteriaBuilder cb = Cnd.builder(MerchantPaywayBak.class);
		cb.andEQ("payCode", payCode);
		cb.andEQ("merchantNo", merchantNo);
		cb.andEQ("recordStatus", "Y");
		Criteria buildCriteria = cb.buildCriteria();
		return merchantPaywayBakDAO.findOneByCriteria(buildCriteria);
	}
	
	/** 设置清算手续费
	 * @throws Exception */
	@Override
	@Transactional
	public void addLiquidationFee(String channelNo ,String zfbFee,String wxsmFee,String wxgzhFee) throws Exception{	
		redisSharedCache.del(new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,HfepayConfig.CHANNEL_REDIS_LF_KEY+ channelNo));
		Map<String, String> params = new HashMap<String, String>();
		params.put("zfbFee", zfbFee);
		params.put("wxsmFee", wxsmFee);
		params.put("wxgzhFee", wxgzhFee);
		redisSharedCache.setObj(new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,HfepayConfig.CHANNEL_REDIS_LF_KEY+ channelNo), params);
		merchantPaywayBakDAO.updateRateAmount(channelNo, zfbFee, PayCode.PAYCODE_ZFBSMZF.getCode());
		merchantPaywayBakDAO.updateRateAmount(channelNo, wxsmFee, PayCode.PAYCODE_WXSMZF.getCode());
		merchantPaywayBakDAO.updateRateAmount(channelNo, wxgzhFee, PayCode.PAYCODE_WXGZHZF.getCode());
		
		//结算费率
		String settleAmtParams = JSONSerializer.toJSON(params).toString();
		
		//保存结算费率
		this.saveSettleamt(settleAmtParams,channelNo);
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
				ParamsInfo paramsInfo = list.get(0);
				map.put("zfbFee", JSONObject.fromObject(paramsInfo).get("zfbFee").toString());
				map.put("wxsmFee", JSONObject.fromObject(paramsInfo).get("wxsmFee").toString());
				map.put("wxgzhFee", JSONObject.fromObject(paramsInfo).get("wxgzhFee").toString());
			}
		}
		return map;
	}

	@Override
	public List<Map<String, BigDecimal>> findMerchantPayWayByAgentNo(String agentNo) {
		
		return merchantPaywayBakDAO.findMerchantPayWayByAgentNo(agentNo);
	}
	
	/** 保存费率 
	 * @throws Exception */
	@Override
	@Transactional
	public Map<String,String> save(MerchantPaywayBakCondition merchantPaywayBakCondition) throws Exception{
		Map<Object, Object> map = new HashMap<Object, Object>();
		MerchantInfo entity = merchantInfoService.findByMerchantNo(merchantPaywayBakCondition.getMerchantNo());
		if(Strings.isEmpty(merchantPaywayBakCondition.getId())){			
			Map<String, String> params = this.getLiquidationFee(entity.getChannelNo());
			if(null != params){
				BigDecimal rateAmount = new BigDecimal(0);
				if("ZFBSMZF".equals(merchantPaywayBakCondition.getPayCode())){
					rateAmount = new BigDecimal(params.get("zfbFee"));
				}else if("WXSMZF".equals(merchantPaywayBakCondition.getPayCode())){
					rateAmount = new BigDecimal(params.get("wxsmFee"));
				}else if("WXGZHZF".equals(merchantPaywayBakCondition.getPayCode())){
					rateAmount = new BigDecimal(params.get("wxgzhFee"));
				}
				merchantPaywayBakCondition.setRateAmount(rateAmount);
			}else{
				merchantPaywayBakCondition.setRateAmount(new BigDecimal(0));
			}
			
			//新增
			merchantPaywayBakCondition.setCreateTime(new Date());
			merchantPaywayBakCondition.setId(Strings.getUUID());
			merchantPaywayBakCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_YES);
			merchantPaywayBakCondition.setStatus(ScanCodeConstants.SUCCESS_STATE);
			merchantPaywayBakCondition.setAcceptStatus(ScanCodeConstants.FAIL_STATE);				
			this.insert(merchantPaywayBakCondition);
			
			paywayJoin(merchantPaywayBakCondition, map);
		}else{
			MerchantPaywayBak merchantPayway = this.findById(merchantPaywayBakCondition.getId());
			JSON res = null;
			if(!MerchantStatus.MERCHANT_STATUS_3.getCode().equals(entity.getStatus())){
				merchantPaywayBakCondition.setRateAmount(merchantPayway.getRateAmount());
				merchantPaywayBakCondition.setPayCode(merchantPayway.getPayCode());
				merchantPaywayBakCondition.setUpdateTime(new Date());
				this.update(merchantPaywayBakCondition);
			}
			if(ScanCodeConstants.STATUS_NOT_USE.equals(merchantPayway.getAcceptStatus()) 
					|| ScanCodeConstants.STATUS_INIT.equals(merchantPayway.getAcceptStatus())){
				merchantPaywayBakCondition.setRateAmount(merchantPayway.getRateAmount());
				merchantPaywayBakCondition.setPayCode(merchantPayway.getPayCode());
				res = paywayJoin(merchantPaywayBakCondition, map);
				merchantPaywayBakCondition.setUpdateTime(new Date());
				this.update(merchantPaywayBakCondition);
			}else{
				if(ScanCodeConstants.STATUS_USE.equals(merchantPayway.getAcceptStatus())
						|| res == null){
					MerchantRateVo payway = new MerchantRateVo();
					payway.setPayCode(merchantPayway.getPayCode());
					payway.setTradeRate(this.objToString(String.valueOf(merchantPaywayBakCondition.getT1Rate())));
					payway.setWithdrawAmt(this.objToString(String.valueOf(merchantPaywayBakCondition.getRate())));
					payway.setWithdrawRate(this.objToString(String.valueOf(merchantPaywayBakCondition.getT0Rate())));
					payway.setSettleAmt(this.objToString(String.valueOf(merchantPaywayBakCondition.getRateAmount())));
					//调用商户费率变更接口
//					merchantBusinessService.merchantUpdateRate(merchantPaywayBakCondition.getMerchantNo(), payway);
				}
				
				MerchantPaywayTmpCondition merchantPaywayTmpCondition = new MerchantPaywayTmpCondition();
				BeanUtils.copyProperties(merchantPaywayBakCondition, merchantPaywayTmpCondition);
				merchantPaywayTmpCondition.setPayCode(merchantPayway.getPayCode());
				merchantPaywayTmpService.save(merchantPaywayTmpCondition);
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
	 * @param merchantPaywayBakCondition
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public JSON paywayJoin(MerchantPaywayBakCondition merchantPaywayBakCondition, Map<Object, Object> map) throws Exception {
		MerchantInfo info = merchantInfoService.findByMerchantNo(merchantPaywayBakCondition.getMerchantNo());
		if(info != null && Strings.isNotEmpty(info.getPlatformMerchantNo())){
			//获取费率列表
			MerchantPaywayBak pay = new MerchantPaywayBak();
			BeanUtils.copyProperties(merchantPaywayBakCondition, pay);
			List<MerchantPaywayBak> payways = new ArrayList<MerchantPaywayBak>();
			payways.add(pay);
			//商户入网
			info.setOperator(merchantPaywayBakCondition.getOperator());//设置操作人
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
	private JSON merchantJoin(Map<Object, Object> map, MerchantInfo entity,List<MerchantPaywayBak> payways) throws Exception {
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
		for (MerchantPaywayBak pay:payways) {
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
			JSONArray paytypes = JSONArray.fromObject(res.get("payType"));
			MerchantPaywayBakCondition pay = null;
			for (Object string : paytypes) {
				JSONObject map = JSONObject.fromObject(string);
				String payCode = map.getString("payCode");
				String status = map.getString("status");
				String respDesc = map.getString("respDesc");
				//System.err.println(merchantNo+"=="+payCode+"=="+status+"=="+respDesc);
				pay = new MerchantPaywayBakCondition();
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
		}else{
			return Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,res.get("respDesc"));
		}
		return null;
	}

	@Override
	public void doTruncateTable() {
		// TODO Auto-generated method stub
		merchantPaywayBakDAO.doTruncateTable();
	}

	@Override
	public void doBackUpTable() {
		// TODO Auto-generated method stub
		merchantPaywayBakDAO.doBackUpTable();
	}
	
}

