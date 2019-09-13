/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.timer.service.impl;

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

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.bo.ProfitBo;
import com.hfepay.scancode.commons.condition.AgentPaywayBakCondition;
import com.hfepay.scancode.commons.condition.ChannelPaywayBakCondition;
import com.hfepay.scancode.commons.condition.HfepayPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayBakCondition;
import com.hfepay.scancode.commons.dao.HfepayPaywayDAO;
import com.hfepay.scancode.commons.dao.OrderPayDAO;
import com.hfepay.scancode.commons.dao.OrganProfitDAO;
import com.hfepay.scancode.commons.dao.ProfitDetailDAO;
import com.hfepay.scancode.commons.dao.TempProfitDAO;
import com.hfepay.scancode.commons.dto.OrderPayStaticDTO;
import com.hfepay.scancode.commons.entity.AgentPaywayBak;
import com.hfepay.scancode.commons.entity.ChannelPaywayBak;
import com.hfepay.scancode.commons.entity.HfepayPayway;
import com.hfepay.scancode.commons.entity.MerchantPaywayBak;
import com.hfepay.scancode.commons.entity.NodeRelation;
import com.hfepay.scancode.commons.entity.OrganProfit;
import com.hfepay.scancode.commons.entity.ProfitDetail;
import com.hfepay.scancode.commons.entity.TempProfit;
import com.hfepay.timer.service.AgentPaywayBakService;
import com.hfepay.timer.service.ChannelPaywayBakService;
import com.hfepay.timer.service.MerchantPaywayBakService;
import com.hfepay.timer.service.NodeRelationService;
import com.hfepay.timer.service.ProfitService;

@Service("profitService")
public class ProfitServiceImpl implements ProfitService {
	public static final Logger log = LoggerFactory.getLogger(ProfitServiceImpl.class);
	private final String MERCHANT_RATE_KEY_FAMILY="profit:merchant:rate:key:";//REDIS存放商户费率的key前缀
	private final String CHANNEL_RATE_KEY_FAMILY="profit:channel:rate:key:";//REDIS存放渠道费率的key前缀
	private final String AGENT_RATE_KEY_FAMILY="profit:agent:rate:key:";//REDIS存放代理商费率的key前缀
	private final String RATEDIFFER_KEY_FAMILY="profit:rate:differ:key:";//REDIS存放费率差的key前缀
	private final String AGENT_ORDER_AMT_KEY = "profit:agent:amt:key:";//代理商交易总金额：交易或者提现
	private final String TOTAL_ORDER_PROFIT_KEY = "profit:order:profit:key:";//总利润金额：交易或者提现
	private final String TOTAL_ORDER_AMT_KEY = "profit:order:amt:key:";//交易总金额：交易或者提现
	private final String TOTAL_WITHDRAW_RATE_KEY = "profit:order:withdraw:key:";//商户提现次数总手续费
	private final String TOTAL_WITHDRAW_PROFIR = "profit:order:withdraw:single:";//分级利润之后的数据统计，用于最后按次数统计总金额的减法
	private final String DEFAULT_NO ="@@@@@@@DDDXXXX";//平台没有标示符指定一个默认值
	private final String DATA_LEVEL_KEY = "profit:level:key:";//以商户为基准的层级关系
	private final Integer LIVE_TIME=18000;//存放到redis的生存时间
	private final Integer BATCHSIZE = 5000;//批量插入的条数5000
	private final String PAY_TYPE=":pay";//支付key前缀
	private final String WITHDRAW_TYPE=":withdraw";//提现key前缀
	
	@Autowired
	private ChannelPaywayBakService channelPaywayBakService;
	@Autowired
	private AgentPaywayBakService agentPaywayBakService;
	@Autowired
	private MerchantPaywayBakService merchantPaywayBakService;
	@Autowired
	private RedisSharedCache redisSharedCache;
	@Autowired
	private NodeRelationService nodeRelationService;
	@Autowired
	private TempProfitDAO tempProfitDAO;
	@Autowired
	private OrderPayDAO orderPayDAO;
	@Autowired
	private ProfitDetailDAO profitDetailDAO;
	@Autowired
	private OrganProfitDAO organProfitDAO;
	@Autowired
	private HfepayPaywayDAO hfepayPaywayDAO;

	/**
	 * 计算分润金额，按照商户进行分润，采取先计算出商户和上级的利率差，然后根据商户的交易总额来逐级递归
	 * 平台利润=总金额*商户费率-(代理商+渠道总利润)
	 * @Title: doSaveProfit
	 * @author: husain
	 * @param date
	 * @return: void
	 */
	@Override
	public void doSaveProfit() {
		log.info("doSaveProfit执行开始："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		try {
			clearTempData();//清空临时数据
			String date = getYesTodayDate();
			getProfitRate();//构造商户费率差
			initStatics(date);//构造交易统计数据 getYesTodayDate() TODO 2016-11-28 agentPayservice merchantPayservice
			doSaveWithdrawProfit(date);//提现数据金额分成
			doSaveWithDrawTimesProfits(date);//次数分成
			generateStaticData();//最终利润
		} catch (Exception e) {
			e.printStackTrace();
			log.error("to save error.",e);
			throw new RuntimeException("统计失败..",e);
		}
		log.info("doSaveProfit执行结束："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
	}
	
	//获取费率差值
	private void getProfitRate() throws Exception{
		//getChannelRateDiff();//渠道平台费率差
		getAgentPayRate();//代理商费率
		getChannelPayRate();//渠道费率
		getMerchantPayRate();//商户费率
		
		//商户费率差计算
		MerchantPaywayBakCondition cond = new MerchantPaywayBakCondition();
		cond.setPageSize(BATCHSIZE);
		cond.setRecordStatus("Y");
		int startPageNo = cond.getPageNo();//当前页
		long records = merchantPaywayBakService.countByCriteria(cond);
		if(records>BATCHSIZE){//记录太多分页查询
			long pages = records%BATCHSIZE==0?records/BATCHSIZE:records/BATCHSIZE+1;
			for (int i = startPageNo; i <= pages; i++) {
				cond.setPageNo(i);
				PagingResult<MerchantPaywayBak> page = merchantPaywayBakService.findPagingResult(cond);//渠道费率
				List<MerchantPaywayBak> list = page.getResult();
				getRateDiff(list);//计算商户费率差
			}
		}
		else{
			List<MerchantPaywayBak> list = merchantPaywayBakService.findAll(cond);//渠道费率
			getRateDiff(list);//计算商户费率差
		}
	}

	//渠道和平台费率差
	@SuppressWarnings("all")
	private void getChannelRateDiff() throws Exception {
		List<ProfitBo> list = channelPaywayBakService.getChannelRateDiff();
		for (ProfitBo profitBo : list) {
			String identity = profitBo.getIdentityNo();
			String payCode = profitBo.getPayCode();
			
			RedisKey redisKeyRate = new RedisKey(RATEDIFFER_KEY_FAMILY, identity+":"+payCode);//商户费率
			List mapList = (List)redisSharedCache.getObj(redisKeyRate);
			if(mapList==null){//第一层级为空说明不存在下级关系
				List<ProfitBo> listsBo = new ArrayList<>();
				listsBo.add(profitBo);
				redisSharedCache.setObj(redisKeyRate, listsBo, LIVE_TIME);
			}
			else{//第一层级不为空
				mapList.add(profitBo);
			}
		}
	}
	
	//代理商和渠道费率差,代理商和代理商费率差，商户和代理商费率差
	@SuppressWarnings("all")
	private void getRateDiff(List<MerchantPaywayBak> list) throws Exception {
		//根据商户编号递归查询层级关系并计算费率差；
		for (MerchantPaywayBak merchantPayway : list) {
			RedisKey redisKeyRate = new RedisKey(RATEDIFFER_KEY_FAMILY, merchantPayway.getMerchantNo()+":"+merchantPayway.getPayCode());//商户费率
			List merchantRateDiffList = (List)redisSharedCache.getObj(redisKeyRate);
			if(merchantRateDiffList!=null){//当前节点数据不为空
				log.info("----------------"+redisKeyRate+":"+merchantRateDiffList);
				continue;
			}
			else{
				//查询出层级关系
				RedisKey redisKeyLevel = new RedisKey(DATA_LEVEL_KEY, merchantPayway.getMerchantNo());//层级关系
				List listNodes = (List)redisSharedCache.getObj(redisKeyLevel);
				if(listNodes==null){
					listNodes = nodeRelationService.getParentsByCurrentNode(merchantPayway.getMerchantNo(), "0", false, true);//已按照层级关系排序，顺序为：渠道--一级代理商--二级代理商.....
					redisSharedCache.setObj(redisKeyLevel,listNodes);
				}
				for (int i = 0; i < listNodes.size()-1; i++) {
					NodeRelation first = (NodeRelation)listNodes.get(i);
					NodeRelation second = (NodeRelation)listNodes.get(i+1);
					RedisKey secondKey = new RedisKey(RATEDIFFER_KEY_FAMILY, second.getCurrentNode()+":"+merchantPayway.getPayCode());
					List listSecBo = (List)redisSharedCache.getObj(secondKey);//map.get(secondKey);
					if(listSecBo==null){
						listSecBo = new ArrayList<>();
						//计算费率差
						ProfitBo bo = new ProfitBo();
						BigDecimal t0Crate = new BigDecimal("0");
						BigDecimal t1Crate = new BigDecimal("0");
						BigDecimal t0Arate = new BigDecimal("0");
						BigDecimal t1Arate = new BigDecimal("0");
						BigDecimal cFixedrate = new BigDecimal("0");//提现固定费率
						BigDecimal aFixedrate = new BigDecimal("0");//提现固定费率
						log.info("first redis key is "+first.getCurrentNode()+":"+merchantPayway.getPayCode());
						log.info("second key is "+second.getCurrentNode()+":"+merchantPayway.getPayCode());
						if("1".equals(first.getIdentityFlag()))//渠道
						{
							RedisKey redisKeyChannel = new RedisKey(CHANNEL_RATE_KEY_FAMILY, first.getCurrentNode()+":"+merchantPayway.getPayCode());
							ChannelPaywayBak firstPayWay = (ChannelPaywayBak)redisSharedCache.getObj(redisKeyChannel);
							t0Crate = firstPayWay.getT0Rate()==null?t0Crate:firstPayWay.getT0Rate();
							t1Crate = firstPayWay.getT1Rate()==null?t1Crate:firstPayWay.getT1Rate();
							cFixedrate=firstPayWay.getRate()==null?cFixedrate:firstPayWay.getRate();
						}else if("2".equals(first.getIdentityFlag())){//代理商
							RedisKey redisKeyChannel = new RedisKey(AGENT_RATE_KEY_FAMILY, first.getCurrentNode()+":"+merchantPayway.getPayCode());
							AgentPaywayBak firstPayWay = (AgentPaywayBak)redisSharedCache.getObj(redisKeyChannel);
							t0Crate = firstPayWay.getT0Rate()==null?t0Crate:firstPayWay.getT0Rate();
							t1Crate = firstPayWay.getT1Rate()==null?t1Crate:firstPayWay.getT1Rate();
							cFixedrate=firstPayWay.getRate()==null?cFixedrate:firstPayWay.getRate();
						}
						else{
							log.info("first is not channel or agent,error "+first.getCurrentNode()+":"+first.getIdentityFlag());
						}
						
						if("2".equals(second.getIdentityFlag())){//代理商
							RedisKey redisKeyAgent = new RedisKey(AGENT_RATE_KEY_FAMILY, second.getCurrentNode()+":"+merchantPayway.getPayCode());
							AgentPaywayBak secondPayWay = (AgentPaywayBak)redisSharedCache.getObj(redisKeyAgent);
							t0Arate = secondPayWay.getT0Rate()==null?t0Arate:secondPayWay.getT0Rate();
							t1Arate = secondPayWay.getT1Rate()==null?t1Arate:secondPayWay.getT1Rate();
							aFixedrate=secondPayWay.getRate()==null?aFixedrate:secondPayWay.getRate();
						}else if("3".equals(second.getIdentityFlag())){//商户
							RedisKey redisKeyMerchant = new RedisKey(MERCHANT_RATE_KEY_FAMILY, second.getCurrentNode()+":"+merchantPayway.getPayCode());
							MerchantPaywayBak secondPayWay = (MerchantPaywayBak)redisSharedCache.getObj(redisKeyMerchant);
							t0Arate = secondPayWay.getT0Rate()==null?t0Arate:secondPayWay.getT0Rate();
							t1Arate = secondPayWay.getT1Rate()==null?t1Arate:secondPayWay.getT1Rate();
							aFixedrate=secondPayWay.getRate()==null?aFixedrate:secondPayWay.getRate();
						}
						else{
							log.info("second is not merchant or agent,error "+second.getCurrentNode()+":"+second.getIdentityFlag());
						}
						BigDecimal t0RateDifference=t0Arate.subtract(t0Crate);
						BigDecimal t1RateDifference=t1Arate.subtract(t1Crate);
						bo.setIdentityNo(second.getCurrentNode());//当前节点
						bo.setParentIdentityNo(first.getCurrentNode());//父节点
						bo.setPayCode(merchantPayway.getPayCode());//支付方式
						bo.setT1RateDifference(t1RateDifference);//t1费率差
						bo.setT0RateDifference(t0RateDifference);//t0费率差
						bo.setRateDifference(aFixedrate.subtract(cFixedrate));//提现固定费率差
						bo.setParentT0Rate(t0Crate);//当前节点父节点的费率
						bo.setParentT1Rate(t1Crate);
						bo.setParentRate(cFixedrate);
						bo.setWithDrawRate(t0RateDifference.add(t1RateDifference));//在数据有误的情况下方便定位费率的问题
						listSecBo.add(bo);//当前费率差，同时加上上级的原有费率差
						
						RedisKey parentKey = new RedisKey(RATEDIFFER_KEY_FAMILY, first.getCurrentNode()+":"+merchantPayway.getPayCode());
						List parentList = (List)redisSharedCache.getObj(parentKey);
						if(null!=parentList){
							listSecBo.addAll(parentList);//同时加上上级的原有费率差
						}
						redisSharedCache.setObj(secondKey, listSecBo,LIVE_TIME);//当前商户的费率差
					}
					else{
						continue;
					}
					
				}
			}
			
		}
		
	}

	
	//查询出所有的代理商费率备用
	private void getAgentPayRate() throws Exception {
		AgentPaywayBakCondition condition = new AgentPaywayBakCondition();
		int startPageNo = condition.getPageNo();//当前页码
		condition.setPageSize(BATCHSIZE);
		condition.setRecordStatus("Y");
		CriteriaBuilder cb = Cnd.builder(AgentPaywayBak.class);
		Criteria buildCriteria = cb.buildCriteria();
		long records = agentPaywayBakService.countByCriteria(buildCriteria);
		if(records>BATCHSIZE){//记录太多分页查询
			long pages = records%BATCHSIZE==0?records/BATCHSIZE:records/BATCHSIZE+1;
			for (int i = startPageNo; i <= pages; i++) {
				condition.setPageNo(i);
				 PagingResult<AgentPaywayBak> pageResult = agentPaywayBakService.findPagingResult(condition);
				 List<AgentPaywayBak> list = pageResult.getResult();//渠道费率
				for (AgentPaywayBak agentPayway : list) {//存在多个支付方式
					RedisKey redisKeyAgent = new RedisKey(AGENT_RATE_KEY_FAMILY, agentPayway.getAgentNo()+":"+agentPayway.getPayCode());
					redisSharedCache.setObj(redisKeyAgent, agentPayway,LIVE_TIME);
				}
			}
		}
		else{
			List<AgentPaywayBak> list = agentPaywayBakService.findAll(condition);//渠道费率
			for (AgentPaywayBak agentPayway : list) {//存在多个支付方式
				RedisKey redisKeyAgent = new RedisKey(AGENT_RATE_KEY_FAMILY, agentPayway.getAgentNo()+":"+agentPayway.getPayCode());
				redisSharedCache.setObj(redisKeyAgent, agentPayway,LIVE_TIME);
			}
		}
				
		
	}
	
	//查询出所有的代理商费率备用
	private void getChannelPayRate() throws Exception {
		ChannelPaywayBakCondition conditon = new ChannelPaywayBakCondition();
		int startPageNo = conditon.getPageNo();//当前页码
		conditon.setRecordStatus("Y");
		CriteriaBuilder cb = Cnd.builder(ChannelPaywayBak.class);
		Criteria buildCriteria = cb.buildCriteria();
		long records = channelPaywayBakService.countByCriteria(buildCriteria);
		if(records>BATCHSIZE){//记录太多分页查询
			long pages = records%BATCHSIZE==0?records/BATCHSIZE:records/BATCHSIZE+1;
			for (int i = startPageNo; i <= pages; i++) {
				conditon.setPageNo(i);
				 PagingResult<ChannelPaywayBak> pageResult = channelPaywayBakService.findPagingResult(conditon);
				 List<ChannelPaywayBak> list = pageResult.getResult();//渠道费率
				 for (ChannelPaywayBak channelPayway : list) {//存在多个支付方式
						RedisKey redisKeyAgent = new RedisKey(CHANNEL_RATE_KEY_FAMILY, channelPayway.getChannelNo()+":"+channelPayway.getPayCode());
						redisSharedCache.setObj(redisKeyAgent, channelPayway,LIVE_TIME);
					}
			}
		}
		else{
			List<ChannelPaywayBak> list = channelPaywayBakService.findAll(conditon);
			for (ChannelPaywayBak channelPayway : list) {//存在多个支付方式
				RedisKey redisKeyAgent = new RedisKey(CHANNEL_RATE_KEY_FAMILY, channelPayway.getChannelNo()+":"+channelPayway.getPayCode());
				redisSharedCache.setObj(redisKeyAgent, channelPayway,LIVE_TIME);
			}
		}
		
	}
	
	//商户费率
	private void getMerchantPayRate() throws Exception {
		MerchantPaywayBakCondition cond = new MerchantPaywayBakCondition();
		cond.setPageSize(BATCHSIZE);
		cond.setRecordStatus("Y");
		int startPageNo = cond.getPageNo();//当前页
		long records = merchantPaywayBakService.countByCriteria(cond);
		if(records>BATCHSIZE){//记录太多分页查询
			long pages = records%BATCHSIZE==0?records/BATCHSIZE:records/BATCHSIZE+1;
			for (int i = startPageNo; i <= pages; i++) {
				cond.setPageNo(i);
				PagingResult<MerchantPaywayBak> page = merchantPaywayBakService.findPagingResult(cond);//商户费率
				List<MerchantPaywayBak> list = page.getResult();
				for (MerchantPaywayBak merchantPayway : list) {//存在多个支付方式
					RedisKey redisKeyMerchant = new RedisKey(MERCHANT_RATE_KEY_FAMILY, merchantPayway.getMerchantNo()+":"+merchantPayway.getPayCode());
					redisSharedCache.setObj(redisKeyMerchant, merchantPayway,LIVE_TIME);
				}
			}
		}
		else{
			List<MerchantPaywayBak> list = merchantPaywayBakService.findAll(cond);//商户费率
			for (MerchantPaywayBak merchantPayway : list) {//存在多个支付方式
				RedisKey redisKeyMerchant = new RedisKey(MERCHANT_RATE_KEY_FAMILY, merchantPayway.getMerchantNo()+":"+merchantPayway.getPayCode());
				redisSharedCache.setObj(redisKeyMerchant, merchantPayway,LIVE_TIME);
			}
		}
	}
	
	//构造交易统计数据
	@SuppressWarnings("all")
	private void initStatics(String date) throws Exception{
		//计算交易记录，同时建数据插入临时表
		List<OrderPayStaticDTO> list = orderPayDAO.getSumOrderAmt(date,"02");//此处数据量太大的情况暂时没有考虑，主要是分页去统计数据不好操作,02交易01提现
		//临时表List用于批量插入
		List<TempProfit> listTemp = new ArrayList<>();
		//分润明细表，临时表的扩充，同时是持久性数据
		List<ProfitDetail> listDetail = new ArrayList<>();
		
		//清算手续费计算
		//临时表List用于批量插入
		List<TempProfit> listSettleTemp = new ArrayList<>();
		//分润明细表，临时表的扩充，同时是持久性数据
		List<ProfitDetail> listSettleDetail = new ArrayList<>();
		
		
		//利润计算时间
		Date calDate = new Date();
		for (OrderPayStaticDTO orderPayStaticDTO : list) {//统计费率数据
			//总交易数据
			getTotalAmt(orderPayStaticDTO);
			
			RedisKey redisKeyTotalProfit = new RedisKey(TOTAL_ORDER_PROFIT_KEY, orderPayStaticDTO.getPayCode()+PAY_TYPE);//总利润金额key
			//计算商户交易的总手续费，也就是各级最终分成的利润，用于最后计算平台利润的被减数
			RedisKey redisKeyMerchant = new RedisKey(MERCHANT_RATE_KEY_FAMILY, orderPayStaticDTO.getMerchantNo()+":"+orderPayStaticDTO.getPayCode());
			MerchantPaywayBak merchantPayway = (MerchantPaywayBak)redisSharedCache.getObj(redisKeyMerchant);
			//商户交易总手续费
			BigDecimal feesTotal = orderPayStaticDTO.getOrderAmt().multiply(merchantPayway.getT1Rate()).setScale(2, BigDecimal.ROUND_HALF_UP);
			//加上清算手续费
			feesTotal = feesTotal.add(merchantPayway.getRateAmount());//单笔清算手续费
			String payCode=orderPayStaticDTO.getPayCode();
			if(redisSharedCache.get(redisKeyTotalProfit)==null){//不存在
				redisSharedCache.set(redisKeyTotalProfit, feesTotal.toString(),LIVE_TIME);
			}
			else{
				String profit = redisSharedCache.get(redisKeyTotalProfit);
				redisSharedCache.set(redisKeyTotalProfit, feesTotal.add(new BigDecimal(profit)).toString(),LIVE_TIME);
			}
			
			//代理商交易总额
			RedisKey redisKeyTotalAgent = new RedisKey(AGENT_ORDER_AMT_KEY, orderPayStaticDTO.getAgentNo()+":"+orderPayStaticDTO.getPayCode()+PAY_TYPE);//代理商交易累计
			if(redisSharedCache.get(redisKeyTotalAgent)==null){//不存在
				redisSharedCache.set(redisKeyTotalAgent, orderPayStaticDTO.getOrderAmt().toString(),LIVE_TIME);
			}
			else{
				String agentAmt = redisSharedCache.get(redisKeyTotalAgent);
				redisSharedCache.set(redisKeyTotalAgent, orderPayStaticDTO.getOrderAmt().add(new BigDecimal(agentAmt)).toString(),LIVE_TIME);
			}
			
			RedisKey redisKeyRateDiff = new RedisKey(RATEDIFFER_KEY_FAMILY, orderPayStaticDTO.getMerchantNo()+":"+payCode);
			List rateList = (List)redisSharedCache.getObj(redisKeyRateDiff);//费率差
			if(null==rateList||list.isEmpty()){
				log.info("============ rate differ is null "+orderPayStaticDTO.getMerchantNo()+":"+payCode);
			}
			else{
				//结算手续费利润计算
				BigDecimal channelRateAmount = getRate(orderPayStaticDTO.getChannelNo()+":"+payCode).get("rateAmount");//渠道清算手续费
				BigDecimal merchantRateAmount = merchantPayway.getRateAmount()==null?new BigDecimal(0):merchantPayway.getRateAmount();//商户清算手续费
				TempProfit boSettle = new TempProfit();//
				ProfitDetail detailSettle = new ProfitDetail();
				boSettle.setId(Strings.getUUID());
				boSettle.setIdentityNo(orderPayStaticDTO.getChannelNo());//只有渠道具有清算手续费
				boSettle.setPayCode(payCode);
				boSettle.setProfit(merchantRateAmount.subtract(channelRateAmount).setScale(2, BigDecimal.ROUND_HALF_UP));//商户的清算手续费和渠道的清算手续费的差，一次清算收一次费用
				boSettle.setTradeType("02");//交易
				BeanUtils.copyProperties(boSettle, detailSettle);
				detailSettle.setMerchantNo(orderPayStaticDTO.getMerchantNo());
				detailSettle.setProfitType("04");//清算手续费
				detailSettle.setProfitBase(new BigDecimal(1));
				detailSettle.setChildLevelNo(orderPayStaticDTO.getMerchantNo());
				detailSettle.setRate(merchantRateAmount);//商户清算手续费
				detailSettle.setRateDiff(merchantRateAmount.subtract(channelRateAmount).setScale(2, BigDecimal.ROUND_HALF_UP));//商户的清算手续费和渠道的清算手续费的差，一次清算收一次费用
				detailSettle.setCreateTime(calDate);
				
				listSettleTemp.add(boSettle);
				listSettleDetail.add(detailSettle);
				if(listSettleTemp.size()==BATCHSIZE){
					//批量插入数据到临时表
					tempProfitDAO.insertBatch(listSettleTemp);
					profitDetailDAO.insertBatch(listSettleDetail);
					listSettleTemp.clear();
					listSettleDetail.clear();
				}
				
				
				
				
				for (Object rateObject : rateList) {//根据费率差计算分级利润
					ProfitBo boDiff = (ProfitBo)rateObject;
					TempProfit bo = new TempProfit();//
					ProfitDetail detail = new ProfitDetail();
					bo.setId(Strings.getUUID());
					bo.setIdentityNo(boDiff.getParentIdentityNo());
					bo.setPayCode(payCode);
					bo.setProfit(orderPayStaticDTO.getOrderAmt().multiply(boDiff.getT1RateDifference()).setScale(2, BigDecimal.ROUND_HALF_UP));
					bo.setTradeType("02");//交易
					BeanUtils.copyProperties(bo, detail);
					detail.setMerchantNo(orderPayStaticDTO.getMerchantNo());
					detail.setProfitType("02");
					detail.setProfitBase(orderPayStaticDTO.getOrderAmt());
					detail.setChildLevelNo(boDiff.getIdentityNo());
					detail.setRate(boDiff.getParentT1Rate());//交易费率是T1费率
					detail.setRateDiff(boDiff.getT1RateDifference());
					detail.setCreateTime(calDate);
					
					listTemp.add(bo);
					listDetail.add(detail);
					if(listTemp.size()==BATCHSIZE){
						//批量插入数据到临时表
						tempProfitDAO.insertBatch(listTemp);
						profitDetailDAO.insertBatch(listDetail);
						listTemp.clear();
						listDetail.clear();
					}
				}
			}
		}
		
		//不足一个批次的数据在外部一次性提交
		if(!listTemp.isEmpty()){
			tempProfitDAO.insertBatch(listTemp);
			profitDetailDAO.insertBatch(listDetail);
			listTemp.clear();
			listDetail.clear();
		}
		//不足一个批次的数据在外部一次性提交
		if(!listSettleTemp.isEmpty()){
			tempProfitDAO.insertBatch(listSettleTemp);
			profitDetailDAO.insertBatch(listSettleDetail);
			listSettleTemp.clear();
			listSettleDetail.clear();
		}
	}
	
	//统计交易总金额
	private void getTotalAmt(OrderPayStaticDTO orderPayStaticDTO) throws Exception{
		//总金额放入redis
		RedisKey totalAmtKey = new RedisKey(TOTAL_ORDER_AMT_KEY,orderPayStaticDTO.getPayCode()+PAY_TYPE);
		String total = redisSharedCache.get(totalAmtKey);
		total = total==null?"0":total;
		BigDecimal nextTotal = orderPayStaticDTO.getOrderAmt().add(new BigDecimal(total));
		redisSharedCache.set(totalAmtKey, nextTotal.toString(),LIVE_TIME);
	}
	
	//从临时表中查出汇总数据生成最终的分成利润
	private void generateStaticData() throws Exception{
		List<TempProfit> proList = tempProfitDAO.getTotalProfitByIdentity();
		List<OrganProfit> list = new ArrayList<>();
		String settleDate = Dates.yyyyMMdd(new Date());//当天
		String transDate = getYesTodayDate();//前一天
		//统计最后收益，此时唯一欠缺的是平台收益
		for (TempProfit tempProfit : proList) {
			//tradetype 01 提现 02 交易
			String type = "";
			OrganProfit pfo = new OrganProfit();
			pfo.setId(Strings.getUUID());
			pfo.setOrganNo(tempProfit.getIdentityNo());
			pfo.setPayCode(tempProfit.getPayCode());
			pfo.setProfitAmount(tempProfit.getProfit());
			Map<String,BigDecimal> map = getRate(tempProfit.getIdentityNo()+":"+tempProfit.getPayCode());
			if("02".equals(tempProfit.getTradeType())){//交易
				pfo.setType("1");//交易类型，1：交易，2：提现
				pfo.setRateType("2");//'费率类型，1：T0，2：T1',
				type=PAY_TYPE;
				//当前级别获取费率
				pfo.setRate(map.get("t1Rate"));//当前级别的交易费率,可能是代理商可能是渠道，这里渠道的概率大一些
			}
			else{//提现
				pfo.setType("2");//交易类型，1：交易，2：提现
				pfo.setRateType("1");//'费率类型，1：T0，2：T1',
				type=WITHDRAW_TYPE;
				pfo.setFixedAmount(map.get("rate"));
				//当前级别获取费率
				pfo.setRate(map.get("rateTotal"));//当前级别的交易费率,可能是代理商可能是渠道，这里渠道的概率大一些
			}
			pfo.setSettleStatus("0");
			pfo.setSettleDate(settleDate);
			pfo.setTransDate(transDate);
			pfo.setOrganLevel(null);
			//从redis中获取当前代理商的交易金额
			RedisKey redisKeyTotalAgent = new RedisKey(AGENT_ORDER_AMT_KEY, tempProfit.getIdentityNo()+":"+tempProfit.getPayCode()+type);
			String total = redisSharedCache.get(redisKeyTotalAgent)==null?"0":redisSharedCache.get(redisKeyTotalAgent);
			pfo.setAmount(new BigDecimal(total));//当前等级的交易总额
			list.add(pfo);
			if(list.size()==BATCHSIZE){
				organProfitDAO.insertBatch(list);
				list.clear();
			}
		}
		
		//处理不足一批次的数据
		if(!list.isEmpty()){
			organProfitDAO.insertBatch(list);
			list.clear();
		}
		
		//平台收益计算 平台收益 = 总利润-各级利润总和
		generatePlateProfit();
	}
	
	private void generatePlateProfit() throws Exception{
		Map<String,HfepayPayway> map = getPlateFeeRate();
		String settleDate = Dates.yyyyMMdd(new Date());//当天
		String transDate = getYesTodayDate();//前一天
		List<TempProfit> totalList = tempProfitDAO.getTotalProfit();
		List<OrganProfit> list = new ArrayList<>();
		for (TempProfit tempProfit : totalList) {
			String type="";
			OrganProfit pfo = new OrganProfit();
			pfo.setId(Strings.getUUID());
			pfo.setOrganNo(DEFAULT_NO);//平台没有标示符
			pfo.setPayCode(tempProfit.getPayCode());
			pfo.setSettleStatus("0");
			pfo.setSettleDate(settleDate);
			pfo.setTransDate(transDate);
			pfo.setOrganLevel(null);
			
			if("02".equals(tempProfit.getTradeType())){//交易
				type=PAY_TYPE;
				pfo.setType("1");
				pfo.setRateType("2");
				//当前级别获取费率
				pfo.setRate(map.get(tempProfit.getPayCode()).getT1Rate());
			}else{//提现金额分成
				type=WITHDRAW_TYPE;
				pfo.setType("2");
				pfo.setRateType("1");
				pfo.setRate(map.get(tempProfit.getPayCode()).getT1Rate().add(map.get(tempProfit.getPayCode()).getT0Rate()));//T0+T1
				pfo.setFixedAmount(map.get(tempProfit.getPayCode()).getRate());//rate
			}
			
			//总额
			RedisKey redisKeyTotalProfit = new RedisKey(TOTAL_ORDER_PROFIT_KEY, tempProfit.getPayCode()+type);//总利润金额key
			String total = redisSharedCache.get(redisKeyTotalProfit);
			total = total==null?"0":total;
			pfo.setProfitAmount(new BigDecimal(total).subtract(tempProfit.getProfit()));//做减法
			//从redis中获取当前代理商的交易金额
			RedisKey redisKeyTotalAmt = new RedisKey(TOTAL_ORDER_AMT_KEY, tempProfit.getPayCode()+type);
			String totalamt = redisSharedCache.get(redisKeyTotalAmt)==null?"0":redisSharedCache.get(redisKeyTotalAmt);
			pfo.setAmount(new BigDecimal(totalamt));//当前等级的交易总额
			
			list.add(pfo);
		}
		if(!list.isEmpty()){
			organProfitDAO.insertBatch(list);
			list.clear();
		}
	}

	private Map<String,BigDecimal> getRate(String key) throws Exception{
		Map<String,BigDecimal> map = new HashMap<>();
		map.put("t0Rate", new BigDecimal("0"));
		map.put("t1Rate", new BigDecimal("0"));
		map.put("rate", new BigDecimal("0"));//
		map.put("rateAmount", new BigDecimal("0"));
		map.put("rateTotal", new BigDecimal("0"));//T0+T1
		//当前级别获取费率
		RedisKey redisKeyAgent = new RedisKey(AGENT_RATE_KEY_FAMILY, key);
		Object agentRateBo = redisSharedCache.getObj(redisKeyAgent);
		if(null==agentRateBo){//代理商不存在查看渠道信息
			RedisKey redisKeyChannel = new RedisKey(CHANNEL_RATE_KEY_FAMILY, key);
			Object ChannelRateBo = redisSharedCache.getObj(redisKeyChannel);
			if(ChannelRateBo==null){
				return map;
			}
			ChannelPaywayBak pay =  (ChannelPaywayBak)ChannelRateBo;
			map.put("t0Rate", pay.getT0Rate());
			map.put("t1Rate", pay.getT1Rate());
			map.put("rate", pay.getRate());//
			map.put("rateAmount", pay.getRateAmount());
			map.put("rateTotal", pay.getT1Rate().add(pay.getT0Rate()));//T0+T1
			return map;
		}else{
			AgentPaywayBak pay =  (AgentPaywayBak)agentRateBo;
			map.put("t0Rate", pay.getT0Rate());
			map.put("t1Rate", pay.getT1Rate());
			map.put("rate", pay.getRate());//
			map.put("rateTotal", pay.getT1Rate().add(pay.getT0Rate()));//T0+T1
			return map;
		}
	}
	
	//获取前一天日期
	private String getYesTodayDate(){
		Date date = new Date();//获取当前时间    
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(date);    
		calendar.add(Calendar.DAY_OF_MONTH, -1);//当前时间减去一年，即一年前的时间    
		Date yestoday = calendar.getTime();//获取一年前的时间，或者一个月前的时间
		return Dates.yyyyMMdd(yestoday);
	}
	
	private Map<String,HfepayPayway> getPlateFeeRate(){
		 Map<String,HfepayPayway> map = new HashMap<>();
		HfepayPaywayCondition hfepayPaywayCondition = new HfepayPaywayCondition();
		hfepayPaywayCondition.setPayType("2");
		CriteriaBuilder cb = Cnd.builder(HfepayPayway.class);
		cb.andEQ("payType", "2");
		Criteria buildCriteria = cb.buildCriteria();
		List<HfepayPayway> payWayList = hfepayPaywayDAO.findByCriteria(buildCriteria);
		for (HfepayPayway hfepayPayway : payWayList) {
			map.put(hfepayPayway.getPayCode(), hfepayPayway);
		}
		return map;
	}
	
	/**清除临时表数据**/
	private void clearTempData() {
		// TODO Auto-generated method stub
		tempProfitDAO.clearTempData();
	}
	
	/***redis中商户的费率差记录***/
	@Override
	public void showRedisRateDiff() {
		// TODO Auto-generated method stub
		//作为打印日志用，没有实际的意义
		List<MerchantPaywayBak> list = merchantPaywayBakService.findAll(new MerchantPaywayBakCondition());//渠道费率
		log.info("==========================================start to read rate differ");
		try {
			for (MerchantPaywayBak merchantPayway : list) {
				RedisKey parentKey = new RedisKey("profit:rate:differ:key:", merchantPayway.getMerchantNo()+":"+merchantPayway.getPayCode());
				log.info(redisSharedCache.getObj(parentKey)+"");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getKeyValue error",e);
		}
		
		log.info("==========================================end to read rate differ");
	}

	
	/***计算提现利润，包括两部分：金额部分利润(利率差)，提现次数的固定利润（固定金额部分差值）
	 * @throws Exception **/
	//@Override
	public void doSaveWithdrawProfit(String date) throws Exception {
		//计算交易记录，同时建数据插入临时表
		List<OrderPayStaticDTO> list = orderPayDAO.getSumOrderAmt(date,"01");//此处数据量太大的情况暂时没有考虑，主要是分页去统计数据不好操作提现操作01，交易02
		//临时表List用于批量插入
		List<TempProfit> listTemp = new ArrayList<>();
		//分润明细表，临时表的扩充，同时是持久性数据
		List<ProfitDetail> listDetail = new ArrayList<>();
		//利润计算时间
		Date calDate = new Date();
		for (OrderPayStaticDTO orderPayStaticDTO : list) {//统计费率数据
			//总交易数据
			getTotalWithdrawAmt(orderPayStaticDTO);
			
			RedisKey redisKeyTotalProfit = new RedisKey(TOTAL_ORDER_PROFIT_KEY, orderPayStaticDTO.getPayCode()+WITHDRAW_TYPE);//总利润金额key
			//计算商户提现的总手续费，也就是各级最终分成的利润，用于最后计算平台利润的被减数
			RedisKey redisKeyMerchant = new RedisKey(MERCHANT_RATE_KEY_FAMILY, orderPayStaticDTO.getMerchantNo()+":"+orderPayStaticDTO.getPayCode());
			MerchantPaywayBak merchantPayway = (MerchantPaywayBak)redisSharedCache.getObj(redisKeyMerchant);
			//商户提现总手续费
			BigDecimal feesTotal = orderPayStaticDTO.getOrderAmt().multiply(merchantPayway.getT1Rate().add(merchantPayway.getT0Rate())).setScale(2, BigDecimal.ROUND_HALF_UP);
			String payCode=orderPayStaticDTO.getPayCode();
			if(redisSharedCache.get(redisKeyTotalProfit)==null){//不存在
				redisSharedCache.set(redisKeyTotalProfit, feesTotal.toString(),LIVE_TIME);
			}
			else{
				String profit = redisSharedCache.get(redisKeyTotalProfit);
				redisSharedCache.set(redisKeyTotalProfit, feesTotal.add(new BigDecimal(profit)).toString(),LIVE_TIME);
			}
			
			//代理商提现总额
			RedisKey redisKeyTotalAgent = new RedisKey(AGENT_ORDER_AMT_KEY, orderPayStaticDTO.getAgentNo()+":"+orderPayStaticDTO.getPayCode()+WITHDRAW_TYPE);//代理商交易累计
			if(redisSharedCache.get(redisKeyTotalAgent)==null){//不存在
				redisSharedCache.set(redisKeyTotalAgent, orderPayStaticDTO.getOrderAmt().toString(),LIVE_TIME);
			}
			else{
				String agentAmt = redisSharedCache.get(redisKeyTotalAgent);
				redisSharedCache.set(redisKeyTotalAgent, orderPayStaticDTO.getOrderAmt().add(new BigDecimal(agentAmt)).toString(),LIVE_TIME);
			}
			
			RedisKey redisKeyRateDiff = new RedisKey(RATEDIFFER_KEY_FAMILY, orderPayStaticDTO.getMerchantNo()+":"+payCode);
			List rateList = (List)redisSharedCache.getObj(redisKeyRateDiff);//费率差
			if(null==rateList||list.isEmpty()){
				log.info("============ rate differ is null "+orderPayStaticDTO.getMerchantNo()+":"+payCode);
			}
			else{
				for (Object rateObject : rateList) {//根据费率差计算分级利润
					ProfitBo boDiff = (ProfitBo)rateObject;
					TempProfit bo = new TempProfit();//
					ProfitDetail detail = new ProfitDetail();
					bo.setId(Strings.getUUID());
					bo.setIdentityNo(boDiff.getParentIdentityNo());
					bo.setPayCode(payCode);
					bo.setProfit(orderPayStaticDTO.getOrderAmt().multiply(boDiff.getWithDrawRate()).setScale(2, BigDecimal.ROUND_HALF_UP));
					bo.setTradeType("01");//提现
					BeanUtils.copyProperties(bo, detail);
					detail.setMerchantNo(orderPayStaticDTO.getMerchantNo());
					detail.setProfitType("01");
					detail.setProfitBase(orderPayStaticDTO.getOrderAmt());
					detail.setChildLevelNo(boDiff.getIdentityNo());
					detail.setRate(boDiff.getParentT0Rate().add(boDiff.getParentT1Rate()));//交易费率是T0+T1费率
					detail.setRateDiff(boDiff.getWithDrawRate());//T0+T1的费率差
					detail.setCreateTime(calDate);
					
					listTemp.add(bo);
					listDetail.add(detail);
					if(listTemp.size()==BATCHSIZE){
						//批量插入数据到临时表
						tempProfitDAO.insertBatch(listTemp);
						profitDetailDAO.insertBatch(listDetail);
						listTemp.clear();
						listDetail.clear();
					}
				}
			}
		}
		
		//不足一个批次的数据在外部一次性提交
		if(!listTemp.isEmpty()){
			tempProfitDAO.insertBatch(listTemp);
			profitDetailDAO.insertBatch(listDetail);
			listTemp.clear();
			listDetail.clear();
		}

	}

	private void getTotalWithdrawAmt(OrderPayStaticDTO orderPayStaticDTO) throws Exception {
		//总金额放入redis
		RedisKey totalAmtKey = new RedisKey(TOTAL_ORDER_AMT_KEY,orderPayStaticDTO.getPayCode()+WITHDRAW_TYPE);
		String total = redisSharedCache.get(totalAmtKey);
		total = total==null?"0":total;
		BigDecimal nextTotal = orderPayStaticDTO.getOrderAmt().add(new BigDecimal(total));
		redisSharedCache.set(totalAmtKey, nextTotal.toString(),LIVE_TIME);
	}
	
	//按次数统计利润金额
	private void doSaveWithDrawTimesProfits(String date) throws Exception {
		// TODO Auto-generated method stub
		List<OrderPayStaticDTO> list = orderPayDAO.getWithDrawTimes(date,"01");//提现次数分成计算
		//临时表List用于批量插入
		List<TempProfit> listTemp = new ArrayList<>();
		//分润明细表，临时表的扩充，同时是持久性数据
		List<ProfitDetail> listDetail = new ArrayList<>();
		//利润计算时间
		Date calDate = new Date();
		for (OrderPayStaticDTO orderPayStaticDTO : list) {//统计费率数据
			//将次数的利润总额加入到金额的利润总额，因为在最终算利润的时候实际上已经存在这两总情况的汇总了
			RedisKey redisKeyTotalProfitWithDraw = new RedisKey(TOTAL_ORDER_PROFIT_KEY, orderPayStaticDTO.getPayCode()+WITHDRAW_TYPE);//总利润金额key
			String total = redisSharedCache.get(redisKeyTotalProfitWithDraw);
			total = total==null?"0":total;
			
			RedisKey redisKeyTotalProfit = new RedisKey(TOTAL_WITHDRAW_RATE_KEY, orderPayStaticDTO.getPayCode());//总利润金额key
			
			RedisKey redisKeyLevelProfit = new RedisKey(TOTAL_WITHDRAW_PROFIR, orderPayStaticDTO.getPayCode());//分级利润的统计
			
			//计算商户提现的总手续费，也就是各级最终分成的利润，用于最后计算平台利润的被减数
			RedisKey redisKeyMerchant = new RedisKey(MERCHANT_RATE_KEY_FAMILY, orderPayStaticDTO.getMerchantNo()+":"+orderPayStaticDTO.getPayCode());
			MerchantPaywayBak merchantPayway = (MerchantPaywayBak)redisSharedCache.getObj(redisKeyMerchant);
			//商户提现总手续费
			BigDecimal feesTotal = orderPayStaticDTO.getTradeTimes().multiply(merchantPayway.getRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
			String payCode=orderPayStaticDTO.getPayCode();
			String profit = redisSharedCache.get(redisKeyTotalProfit);
			if(profit==null){//不存在
				redisSharedCache.set(redisKeyTotalProfit, feesTotal.toString(),LIVE_TIME);
			}
			else{
				redisSharedCache.set(redisKeyTotalProfit, feesTotal.add(new BigDecimal(profit)).toString(),LIVE_TIME);
			}
			//总利润
			redisSharedCache.set(redisKeyTotalProfitWithDraw, new BigDecimal(total).add(feesTotal).toString(),LIVE_TIME);
			
			RedisKey redisKeyRateDiff = new RedisKey(RATEDIFFER_KEY_FAMILY, orderPayStaticDTO.getMerchantNo()+":"+payCode);
			List rateList = (List)redisSharedCache.getObj(redisKeyRateDiff);//费率差
			if(null==rateList||list.isEmpty()){
				log.info("============ rate differ is null "+orderPayStaticDTO.getMerchantNo()+":"+payCode);
			}
			else{
				for (Object rateObject : rateList) {//根据费率差计算分级利润
					ProfitBo boDiff = (ProfitBo)rateObject;
					TempProfit bo = new TempProfit();//
					ProfitDetail detail = new ProfitDetail();
					bo.setId(Strings.getUUID());
					bo.setIdentityNo(boDiff.getParentIdentityNo());
					bo.setPayCode(payCode);
					BigDecimal money = orderPayStaticDTO.getTradeTimes().multiply(boDiff.getRateDifference()).setScale(2, BigDecimal.ROUND_HALF_UP);
					
					String profitLevel = redisSharedCache.get(redisKeyLevelProfit);//分级利润的统计
					if(profitLevel==null){//不存在
						redisSharedCache.set(redisKeyLevelProfit, money.toString(),LIVE_TIME);
					}
					else{
						redisSharedCache.set(redisKeyLevelProfit, money.add(new BigDecimal(profitLevel)).toString(),LIVE_TIME);
					}
					
					bo.setProfit(money);
					bo.setTradeType("01");//提现
					BeanUtils.copyProperties(bo, detail);
					detail.setMerchantNo(orderPayStaticDTO.getMerchantNo());
					detail.setProfitType("03");
					detail.setProfitBase(orderPayStaticDTO.getTradeTimes());
					detail.setChildLevelNo(boDiff.getIdentityNo());
					detail.setRate(boDiff.getParentRate());//交易费率是T1费率
					detail.setRateDiff(boDiff.getRateDifference());
					detail.setCreateTime(calDate);
					
					listTemp.add(bo);
					listDetail.add(detail);
					if(listTemp.size()==BATCHSIZE){
						//批量插入数据到临时表
						tempProfitDAO.insertBatch(listTemp);
						profitDetailDAO.insertBatch(listDetail);
						listTemp.clear();
						listDetail.clear();
					}
				}
			}
		}
		
		//不足一个批次的数据在外部一次性提交
		if(!listTemp.isEmpty()){
			tempProfitDAO.insertBatch(listTemp);
			profitDetailDAO.insertBatch(listDetail);
			listTemp.clear();
			listDetail.clear();
		}
	}
	
	/**
	 * @Title: bakupPayways
	 * @Description: 备份支付费率数据
	 * @see com.hfepay.scancode.service.order.ProfitService#saveBakupPayways()
	 */
	@Override
	public void saveBakupPayways() {
		//清空前一天的数据
		merchantPaywayBakService.doTruncateTable();
		channelPaywayBakService.doTruncateTable();
		agentPaywayBakService.doTruncateTable();
		//更新新数据
		merchantPaywayBakService.doBackUpTable();
		channelPaywayBakService.doBackUpTable();
		agentPaywayBakService.doBackUpTable();
	}
	
}

