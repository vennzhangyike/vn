/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.scancode.service.operator.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.hfepay.scancode.commons.condition.MerchantActivityCondition;
import com.hfepay.scancode.commons.condition.MerchantActivityDiscountCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MerchantActivityDAO;
import com.hfepay.scancode.commons.dao.MerchantActivityDiscountDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantActivity;
import com.hfepay.scancode.commons.entity.MerchantActivityDiscount;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.MerchantActivityDiscountService;
import com.hfepay.scancode.service.operator.MerchantActivityService;

import net.sf.json.JSONObject;

@Service("merchantActivityService")
public class MerchantActivityServiceImpl implements MerchantActivityService {
	public static final Logger log = LoggerFactory.getLogger(MerchantActivityServiceImpl.class);
	
	@Autowired
    private MerchantActivityDAO merchantActivityDAO;
	
	@Autowired
	private MerchantActivityDiscountDAO merchantActivityDiscountDAO;
	
	@Autowired
	private MerchantActivityDiscountService merchantActivityDiscountService;
	
	@Autowired
	private RedisSharedCache redisSharedCache;
    
    /**
	 * 列表(分页)
	 * @param merchantActivityCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
    @Override
	public PagingResult<MerchantActivity> findPagingResult(MerchantActivityCondition merchantActivityCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantActivity.class);
		if(!Strings.isEmpty(merchantActivityCondition.getId())){
			cb.andEQ("id", merchantActivityCondition.getId());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantActivityCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantActivityCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantActivityCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getActivityId())){
			cb.andEQ("activityId", merchantActivityCondition.getActivityId());
		}
		if(null != merchantActivityCondition.getActivityBeginTime()){
			cb.andEQ("activityBeginTime", merchantActivityCondition.getActivityBeginTime());
		}
		if(null != merchantActivityCondition.getActivityEndTime()){
			cb.andEQ("activityEndTime", merchantActivityCondition.getActivityEndTime());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getActivityType())){
			cb.andEQ("activityType", merchantActivityCondition.getActivityType());
		}
		if(null != merchantActivityCondition.getActivityContent()){
			cb.andEQ("activityContent", merchantActivityCondition.getActivityContent());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getStatus())){
			cb.andEQ("status", merchantActivityCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantActivityCondition.getRecordStatus());
		}
		if(null != merchantActivityCondition.getCreateTime()){
			cb.andEQ("createTime", merchantActivityCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantActivityCondition.getOperator())){
			cb.andEQ("operator", merchantActivityCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantActivityCondition.getRemark())){
			cb.andLike("remark", merchantActivityCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getTemp1())){
			cb.andEQ("temp1", merchantActivityCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getTemp2())){
			cb.andEQ("temp2", merchantActivityCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getTemp3())){
			cb.andEQ("temp3", merchantActivityCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getTemp4())){
			cb.andEQ("temp4", merchantActivityCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantActivityCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantActivityCondition.getNodeSeq());
		}
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantActivityCondition.getOrderBy())){
			if(merchantActivityCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantActivityCondition.getOrderBy().split(",");
				String[] orders = merchantActivityCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantActivityCondition.getOrderBy(), Order.valueOf(merchantActivityCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantActivityCondition.getFirst()), Long.valueOf(merchantActivityCondition.getLast()));
		
		PagingResult<MerchantActivity> page = merchantActivityDAO.findPagingResult(buildCriteria);
		for (MerchantActivity merchantActivity : page.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+merchantActivity.getChannelNo()));
				if(channelBase != null){
					merchantActivity.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+merchantActivity.getAgentNo()));
				if(agentBase != null){
					merchantActivity.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantActivity.getMerchantNo()));
				if(merchantInfo != null){
					merchantActivity.setMerchantName(merchantInfo.getMerchantName());
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		
		return page;
	}
	
	/**
	 * 列表
	 * @param merchantActivity 
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public List<MerchantActivity> findAll(MerchantActivityCondition merchantActivityCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantActivity.class);
		if(!Strings.isEmpty(merchantActivityCondition.getId())){
			cb.andEQ("id", merchantActivityCondition.getId());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantActivityCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantActivityCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantActivityCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getActivityId())){
			cb.andEQ("activityId", merchantActivityCondition.getActivityId());
		}
		if(null != merchantActivityCondition.getActivityBeginTime()){
			cb.andEQ("activityBeginTime", merchantActivityCondition.getActivityBeginTime());
		}
		if(null != merchantActivityCondition.getActivityEndTime()){
			cb.andEQ("activityEndTime", merchantActivityCondition.getActivityEndTime());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getActivityType())){
			cb.andEQ("activityType", merchantActivityCondition.getActivityType());
		}
		if(null != merchantActivityCondition.getActivityContent()){
			cb.andEQ("activityContent", merchantActivityCondition.getActivityContent());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getStatus())){
			cb.andEQ("status", merchantActivityCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantActivityCondition.getRecordStatus());
		}
		if(null != merchantActivityCondition.getCreateTime()){
			cb.andEQ("createTime", merchantActivityCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantActivityCondition.getOperator())){
			cb.andEQ("operator", merchantActivityCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantActivityCondition.getRemark())){
			cb.andLike("remark", merchantActivityCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getTemp1())){
			cb.andEQ("temp1", merchantActivityCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getTemp2())){
			cb.andEQ("temp2", merchantActivityCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getTemp3())){
			cb.andEQ("temp3", merchantActivityCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantActivityCondition.getTemp4())){
			cb.andEQ("temp4", merchantActivityCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantActivityCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantActivityCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantActivityDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public MerchantActivity findById(String id){
		return merchantActivityDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantActivityCondition
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public long insert(MerchantActivityCondition merchantActivityCondition){
		MerchantActivity merchantActivity = new MerchantActivity();
		BeanUtils.copyProperties(merchantActivityCondition, merchantActivity);
		return merchantActivityDAO.insert(merchantActivity);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public long deleteById(String id){
		return merchantActivityDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantActivityDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantActivityDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public long update(MerchantActivityCondition merchantActivityCondition){
		MerchantActivity merchantActivity = new MerchantActivity();
		BeanUtils.copyProperties(merchantActivityCondition, merchantActivity);
		return merchantActivityDAO.update(merchantActivity);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public long updateByCriteria(MerchantActivityCondition merchantActivityCondition,Criteria criteria){
		MerchantActivity merchantActivity = new MerchantActivity();
		BeanUtils.copyProperties(merchantActivityCondition, merchantActivity);
		return merchantActivityDAO.updateByCriteria(merchantActivity,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantActivityDAO.updateStatus(id,status);
	}	
	
	/**
	 * 新增
	 * @param merchantActivityCondition
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public void insertMerchantActivity(MerchantActivityCondition merchantActivityCondition,String [] array){
		MerchantActivity merchantActivity = new MerchantActivity();
		BeanUtils.copyProperties(merchantActivityCondition, merchantActivity);
		long count = merchantActivityDAO.insert(merchantActivity);
		if(count > 0){
			List<MerchantActivityDiscount> list = new ArrayList<MerchantActivityDiscount>();
			
			for (int i = 0; i < array.length; i++) {
				 MerchantActivityDiscount merchantActivityDiscount = new MerchantActivityDiscount();
				 JSONObject jsonObject = JSONObject.fromObject(array[i]);
				 String discount = (String) jsonObject.get("from");
				 String condition = (String) jsonObject.get("to");
				 String chance = (String) jsonObject.get("odds");
				 merchantActivityDiscount.setActivityDiscount(discount);
				 merchantActivityDiscount.setActivityCondition(condition);
				 merchantActivityDiscount.setChance(chance);
				 merchantActivityDiscount.setActivityId(merchantActivityCondition.getActivityId());
				 merchantActivityDiscount.setId(Strings.getUUID());
				 list.add(merchantActivityDiscount);
			}
			merchantActivityDiscountDAO.batchInsert(list);
		}
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:45:02
	 */
	@Override
	public void deleteMerchantActivityById(String id,String activityId){
		 long num = merchantActivityDAO.deleteById(id);
		 if(num > 0){
			 merchantActivityDiscountDAO.deleteByActivityId(activityId);
		 }
	}
	
	@Override
	public MerchantActivity findByMerchantNoAndStatus(String merchantNo, String status) {
		return merchantActivityDAO.findByMerchantNoAndStatus(merchantNo,status);
	}
	
	/**
	 * 计算优惠的价格
	 */
	public Map<String,BigDecimal> calculateCheapCash(String merchantNo,BigDecimal payCash,BigDecimal minLimit){
		Map<String,BigDecimal> cashMap = new HashMap<String,BigDecimal>();
		BigDecimal discountCash = BigDecimal.ZERO;//优惠金额
		BigDecimal actualPayCash = BigDecimal.ZERO;//实际支付金额
		MerchantActivity merchantActivity = findByMerchantNoAndStatus(merchantNo,"1");
		if(merchantActivity != null){
			Date currDate = new Date(); //当前日期
			Date activityBeginDate = merchantActivity.getActivityBeginTime();//活动开始时间
			Date activityEndDate = merchantActivity.getActivityEndTime();//活动结束时间
			
			//活动ID
			String activityId = merchantActivity.getActivityId();
			//活动类型
			String activityType = merchantActivity.getActivityType();
			
			MerchantActivityDiscountCondition merchantActivityDiscountCondition = new MerchantActivityDiscountCondition();
			merchantActivityDiscountCondition.setActivityId(activityId);
			//判断当前的支付日期是否在活动规定的时间内
			if(currDate.getTime() >= activityBeginDate.getTime() && currDate.getTime() <= activityEndDate.getTime()){
				List<MerchantActivityDiscount> list = merchantActivityDiscountService.findAll(merchantActivityDiscountCondition);
				if(list != null && list.size() > 0){
					for (Iterator<MerchantActivityDiscount> iterator = list.iterator(); iterator.hasNext();) {
						MerchantActivityDiscount merchantActivityDiscount = (MerchantActivityDiscount) iterator.next();
						//活动优惠 BigDecimal
						BigDecimal discount = BigDecimal.ZERO;
						//活动类型为随机时，活动优惠为String
						String strDiscount = "";
						//活动条件BigDecimal
						BigDecimal condition = new BigDecimal(merchantActivityDiscount.getActivityCondition());
						//活动类型为随机时 活动条件为Integer
						Integer intCondtion = Integer.parseInt(merchantActivityDiscount.getActivityCondition());
						//中奖概率
						double chance = 0;
						if(payCash.compareTo(condition)>=0){
							if(activityType.equals("0")){//活动类型为折扣
								discount = new BigDecimal(merchantActivityDiscount.getActivityDiscount());
								actualPayCash = payCash.multiply(discount).setScale(2, BigDecimal.ROUND_HALF_UP);//支付金额 * 折扣率 = 实际支付金额
								discountCash = payCash.subtract(actualPayCash).setScale(2, BigDecimal.ROUND_HALF_UP);//支付金额-实际支付金额=优惠金额
							}else if(activityType.equals("1")){//活动类型为满减
								discount = new BigDecimal(merchantActivityDiscount.getActivityDiscount());
								discountCash = discount; //活动优惠金额
								actualPayCash = payCash.subtract(discountCash).setScale(2, BigDecimal.ROUND_HALF_UP);//支付金额-优惠金额=实际支付金额
							}else if(activityType.equals("2")){
								chance = Double.valueOf(merchantActivityDiscount.getChance())*intCondtion;
								strDiscount = merchantActivityDiscount.getActivityDiscount();
								Random rand = new Random();
								int count = rand.nextInt(intCondtion);//根据活动条件产生随机数
								if (chance >= count) {
									String [] str= strDiscount.split(",");
									Integer begin = Integer.parseInt(str[0]);
									Integer end = Integer.parseInt(str[1]);
									int a = rand.nextInt(end);
									if(a >= begin && a <= end){
										if(new BigDecimal(a).compareTo(payCash)>=0){
											actualPayCash = payCash;
										}else{
											discountCash = new BigDecimal(a);
											actualPayCash = payCash.subtract(discountCash).setScale(2, BigDecimal.ROUND_HALF_UP);
										}
									}else{
										actualPayCash = payCash;
									}
								}else{
									actualPayCash = payCash;
								}
							}
							break;
						}else{
							actualPayCash = payCash;
						}
					}
				}
				cashMap.put("payCash", payCash);
				cashMap.put("discountCash", discountCash);
				cashMap.put("actualPayCash", actualPayCash);
			}else{
				cashMap.put("payCash", payCash);
				cashMap.put("discountCash", BigDecimal.ZERO);
				cashMap.put("actualPayCash", payCash);
			}
		}else{
			cashMap.put("payCash", payCash);
			cashMap.put("discountCash", BigDecimal.ZERO);
			cashMap.put("actualPayCash", payCash);
		}
		if(cashMap.get("actualPayCash").compareTo(minLimit)==-1){
			cashMap.put("payCash", payCash);
			cashMap.put("discountCash", BigDecimal.ZERO);
			cashMap.put("actualPayCash", payCash);
		}
		return cashMap;
	}

	@Override
	public MerchantActivity findByActivityId(String activityId) {
		return merchantActivityDAO.findByActivityId(activityId);
	}
}

