/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.HierarchicalSettlementTotalCondition;
import com.hfepay.scancode.commons.condition.HierarchicalStaticCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.HierarchicalSettlementTotalDAO;
import com.hfepay.scancode.commons.dto.HierarchicalSettlementTotalDTO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.HierarchicalSettlementTotal;
import com.hfepay.scancode.commons.entity.HierarchicalStatic;
import com.hfepay.scancode.service.operator.HierarchicalSettlementTotalService;
import com.hfepay.scancode.service.operator.OrganProfitService;
import com.hfepay.scancode.service.operator.OrganWalletService;
import com.hfepay.scancode.service.utils.OrderIDUtils;


@Service("hierarchicalSettlementTotalService")
public class HierarchicalSettlementTotalServiceImpl implements HierarchicalSettlementTotalService {
	
	public static final Logger log = LoggerFactory.getLogger(HierarchicalSettlementTotalServiceImpl.class);
	
	@Autowired
    private HierarchicalSettlementTotalDAO hierarchicalSettlementTotalDAO;
	@Autowired
	private OrganWalletService organWalletService;
	@Autowired
	private OrganProfitService organProfitService;
	@Autowired
	private OrderIDUtils orderUtils;
	@Autowired
	private RedisSharedCache redisSharedCache;
    /**
	 * 列表(分页)
	 * @param hierarchicalSettlementTotalCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
    @Override
	public PagingResult<HierarchicalSettlementTotal> findPagingResult(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition){
		CriteriaBuilder cb = Cnd.builder(HierarchicalSettlementTotal.class);
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getId())){
			cb.andEQ("id", hierarchicalSettlementTotalCondition.getId());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getBatchNo())){
			cb.andEQ("batchNo", hierarchicalSettlementTotalCondition.getBatchNo());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getChannelNo())){
			cb.andEQ("channelNo", hierarchicalSettlementTotalCondition.getChannelNo());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getAgentNo())){
			cb.andEQ("agentNo", hierarchicalSettlementTotalCondition.getAgentNo());
		}
		if(Strings.isNotEmpty(hierarchicalSettlementTotalCondition.getNodeSeq())){
			cb.addParam("nodeSeq", hierarchicalSettlementTotalCondition.getNodeSeq());
		}
		if(Strings.isNotEmpty(hierarchicalSettlementTotalCondition.getIdentityFlag())){
			cb.addParam("identityFlag", hierarchicalSettlementTotalCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getAgentLevel())){
			cb.andEQ("agentLevel", hierarchicalSettlementTotalCondition.getAgentLevel());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getParentNo())){
			cb.andEQ("parentNo", hierarchicalSettlementTotalCondition.getParentNo());
		}
		if(null != hierarchicalSettlementTotalCondition.getTranTotalAmount()){
			cb.andEQ("tranTotalAmount", hierarchicalSettlementTotalCondition.getTranTotalAmount());
		}
		if(null != hierarchicalSettlementTotalCondition.getTranProfit()){
			cb.andEQ("tranProfit", hierarchicalSettlementTotalCondition.getTranProfit());
		}
		if(null != hierarchicalSettlementTotalCondition.getOutTotalAmount()){
			cb.andEQ("outTotalAmount", hierarchicalSettlementTotalCondition.getOutTotalAmount());
		}
		if(null != hierarchicalSettlementTotalCondition.getOutProfit()){
			cb.andEQ("outProfit", hierarchicalSettlementTotalCondition.getOutProfit());
		}
		if(null != hierarchicalSettlementTotalCondition.getTotalProfit()){
			cb.andEQ("totalProfit", hierarchicalSettlementTotalCondition.getTotalProfit());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getSettleDate())){
			cb.andEQ("settleDate", hierarchicalSettlementTotalCondition.getSettleDate());
		}
		if(null != hierarchicalSettlementTotalCondition.getCreateTime()){
			cb.andEQ("createTime", hierarchicalSettlementTotalCondition.getCreateTime());
		}

		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getRemark())){
			cb.andLike("remark", hierarchicalSettlementTotalCondition.getRemark());
		}
		
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getQueryStartDate())){
			cb.andGE("createTime", getDate(hierarchicalSettlementTotalCondition.getQueryStartDate(),0));
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getQueryEndDate())){
			cb.andLT("createTime", getDate(hierarchicalSettlementTotalCondition.getQueryEndDate(),1));
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getOrganNo())){
			cb.andGroup(Cnd.builder(HierarchicalSettlementTotal.class).orEQ("channelNo", hierarchicalSettlementTotalCondition.getOrganNo()).orEQ("agentNo", hierarchicalSettlementTotalCondition.getOrganNo()));
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(hierarchicalSettlementTotalCondition.getFirst()), Long.valueOf(hierarchicalSettlementTotalCondition.getLast()));
		
		PagingResult<HierarchicalSettlementTotal> page = hierarchicalSettlementTotalDAO.findPagingResult(buildCriteria);
		for (HierarchicalSettlementTotal hierarchicalSettlementTotal : page.getResult()) {			
			try {
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+hierarchicalSettlementTotal.getAgentNo()));
				if(agentBase != null){
					hierarchicalSettlementTotal.setAgentName(agentBase.getAgentName());
				}
				ChannelBase channelBaseCondition = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+hierarchicalSettlementTotal.getChannelNo()));
				if(channelBaseCondition != null){
					hierarchicalSettlementTotal.setChannelName(channelBaseCondition.getChannelName());
				}
				ChannelBase channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+hierarchicalSettlementTotal.getParentNo()));
				if(channelBase != null){
					hierarchicalSettlementTotal.setParentName(channelBase.getChannelName());
				}
				AgentBase agentBaseCache = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+hierarchicalSettlementTotal.getParentNo()));
				if(agentBaseCache != null){
					hierarchicalSettlementTotal.setParentName(agentBaseCache.getAgentName());
				}
				
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}
    
    private String getDate(String date,int step){
		Date d = null;
		Calendar ca = Calendar.getInstance();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			d = format.parse(date);
			ca.setTime(d);
			if(step!=0){
				 ca.add(Calendar.DAY_OF_MONTH, step);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ca.getTime());
    }
	/**
	 * 列表
	 * @param hierarchicalSettlementTotal 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	@Override
	public List<HierarchicalSettlementTotal> findAll(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition){
		CriteriaBuilder cb = Cnd.builder(HierarchicalSettlementTotal.class);
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getId())){
			cb.andEQ("id", hierarchicalSettlementTotalCondition.getId());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getBatchNo())){
			cb.andEQ("batchNo", hierarchicalSettlementTotalCondition.getBatchNo());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getChannelNo())){
			cb.andEQ("channelNo", hierarchicalSettlementTotalCondition.getChannelNo());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getAgentNo())){
			cb.andEQ("agentNo", hierarchicalSettlementTotalCondition.getAgentNo());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getAgentLevel())){
			cb.andEQ("agentLevel", hierarchicalSettlementTotalCondition.getAgentLevel());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getParentNo())){
			cb.andEQ("parentNo", hierarchicalSettlementTotalCondition.getParentNo());
		}
		if(null != hierarchicalSettlementTotalCondition.getTranTotalAmount()){
			cb.andEQ("tranTotalAmount", hierarchicalSettlementTotalCondition.getTranTotalAmount());
		}
		if(null != hierarchicalSettlementTotalCondition.getTranProfit()){
			cb.andEQ("tranProfit", hierarchicalSettlementTotalCondition.getTranProfit());
		}
		if(null != hierarchicalSettlementTotalCondition.getOutTotalAmount()){
			cb.andEQ("outTotalAmount", hierarchicalSettlementTotalCondition.getOutTotalAmount());
		}
		if(null != hierarchicalSettlementTotalCondition.getOutProfit()){
			cb.andEQ("outProfit", hierarchicalSettlementTotalCondition.getOutProfit());
		}
		if(null != hierarchicalSettlementTotalCondition.getTotalProfit()){
			cb.andEQ("totalProfit", hierarchicalSettlementTotalCondition.getTotalProfit());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getSettleDate())){
			cb.andEQ("settleDate", hierarchicalSettlementTotalCondition.getSettleDate());
		}
		if(null != hierarchicalSettlementTotalCondition.getCreateTime()){
			cb.andEQ("createTime", hierarchicalSettlementTotalCondition.getCreateTime());
		}
		try {
			if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getQueryStartDate())){
				cb.andGE("createTime", Dates.parse("yyyy-MM-dd", hierarchicalSettlementTotalCondition.getQueryStartDate()));
			}
			if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getQueryEndDate())){
				cb.andLT("createTime", Dates.parse("yyyy-MM-dd HH:mm:ss", hierarchicalSettlementTotalCondition.getQueryEndDate()));
			}
		} catch (Exception e) {
			throw new RuntimeException("时间转换异常");
		}

		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getRemark())){
			cb.andLike("remark", hierarchicalSettlementTotalCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		 List<HierarchicalSettlementTotal> list = hierarchicalSettlementTotalDAO.findByCriteria(buildCriteria);
		for (HierarchicalSettlementTotal hierarchicalSettlementTotal : list) {			
			try {
				AgentBase agentBaseCondition = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+hierarchicalSettlementTotal.getAgentNo()));
				if(agentBaseCondition != null){
					hierarchicalSettlementTotal.setAgentName(agentBaseCondition.getAgentName());
				}
				ChannelBase channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+hierarchicalSettlementTotal.getChannelNo()));
				if(channelBase != null){
					hierarchicalSettlementTotal.setChannelName(channelBase.getChannelName());
				}
				ChannelBase channelBaseCon = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+hierarchicalSettlementTotal.getParentNo()));
				if(channelBaseCon != null){
					hierarchicalSettlementTotal.setParentName(channelBaseCon.getChannelName());
				}
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+hierarchicalSettlementTotal.getParentNo()));
				if(agentBase != null){
					hierarchicalSettlementTotal.setParentName(agentBase.getAgentName());
				}
				
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return list;
	}
	
	/**
	 * 主键查找
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	@Override
	public HierarchicalSettlementTotal findById(String id){
		return hierarchicalSettlementTotalDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param hierarchicalSettlementTotalCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	@Override
	public long insert(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition){
		HierarchicalSettlementTotal hierarchicalSettlementTotal = new HierarchicalSettlementTotal();
		BeanUtils.copyProperties(hierarchicalSettlementTotalCondition, hierarchicalSettlementTotal);
		if(Strings.isEmpty(hierarchicalSettlementTotalCondition.getId())){
			hierarchicalSettlementTotal.setId(Strings.getUUID());
		}
		return hierarchicalSettlementTotalDAO.insert(hierarchicalSettlementTotal);
	}

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	@Override
	public long deleteById(String id){
		return hierarchicalSettlementTotalDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return hierarchicalSettlementTotalDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return hierarchicalSettlementTotalDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	@Override
	public long update(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition){
		HierarchicalSettlementTotal hierarchicalSettlementTotal = new HierarchicalSettlementTotal();
		BeanUtils.copyProperties(hierarchicalSettlementTotalCondition, hierarchicalSettlementTotal);
		return hierarchicalSettlementTotalDAO.update(hierarchicalSettlementTotal);
	}
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	@Override
	public long updateByCriteria(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition,Criteria criteria){
		HierarchicalSettlementTotal hierarchicalSettlementTotal = new HierarchicalSettlementTotal();
		BeanUtils.copyProperties(hierarchicalSettlementTotalCondition, hierarchicalSettlementTotal);
		return hierarchicalSettlementTotalDAO.updateByCriteria(hierarchicalSettlementTotal,criteria);
	}
	
	/**
	 * 单个实体对象
	 * @param hierarchicalSettlementTotalCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:01
	 */
	@Override
	public HierarchicalSettlementTotal findByCondition(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition){
		CriteriaBuilder cb = Cnd.builder(HierarchicalSettlementTotal.class);
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getId())){
			cb.andEQ("id", hierarchicalSettlementTotalCondition.getId());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getBatchNo())){
			cb.andEQ("batchNo", hierarchicalSettlementTotalCondition.getBatchNo());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getChannelNo())){
			cb.andEQ("channelNo", hierarchicalSettlementTotalCondition.getChannelNo());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getAgentNo())){
			cb.andEQ("agentNo", hierarchicalSettlementTotalCondition.getAgentNo());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getAgentLevel())){
			cb.andEQ("agentLevel", hierarchicalSettlementTotalCondition.getAgentLevel());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getParentNo())){
			cb.andEQ("parentNo", hierarchicalSettlementTotalCondition.getParentNo());
		}
		if(null != hierarchicalSettlementTotalCondition.getTranTotalAmount()){
			cb.andEQ("tranTotalAmount", hierarchicalSettlementTotalCondition.getTranTotalAmount());
		}
		if(null != hierarchicalSettlementTotalCondition.getTranProfit()){
			cb.andEQ("tranProfit", hierarchicalSettlementTotalCondition.getTranProfit());
		}
		if(null != hierarchicalSettlementTotalCondition.getOutTotalAmount()){
			cb.andEQ("outTotalAmount", hierarchicalSettlementTotalCondition.getOutTotalAmount());
		}
		if(null != hierarchicalSettlementTotalCondition.getOutProfit()){
			cb.andEQ("outProfit", hierarchicalSettlementTotalCondition.getOutProfit());
		}
		if(null != hierarchicalSettlementTotalCondition.getTotalProfit()){
			cb.andEQ("totalProfit", hierarchicalSettlementTotalCondition.getTotalProfit());
		}
		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getSettleDate())){
			cb.andEQ("settleDate", hierarchicalSettlementTotalCondition.getSettleDate());
		}
		if(null != hierarchicalSettlementTotalCondition.getCreateTime()){
			cb.andEQ("createTime", hierarchicalSettlementTotalCondition.getCreateTime());
		}

		if(!Strings.isEmpty(hierarchicalSettlementTotalCondition.getRemark())){
			cb.andLike("remark", hierarchicalSettlementTotalCondition.getRemark());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return hierarchicalSettlementTotalDAO.findOneByCriteria(buildCriteria);
	}
	
	/**
	 * 根据利润表统计数据，同时更新钱包金额
	 * @Title: saveSummaryProfit
	 * @Description: TODO
	 * @param date
	 * @see com.hfepay.scancode.service.operator.HierarchicalSettlementTotalService#saveSummaryProfit(java.lang.String)
	 */
	@Override
	public void saveSummaryProfit(String date) {
		log.info("saveSummaryProfit执行开始："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
		Map<String,HierarchicalSettlementTotal> map = new HashMap<>();
		String batchNo = orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "HST");
		Date createTime = new Date();
		//利润汇总
		List<HierarchicalSettlementTotalDTO> listDto = organProfitService.queryTotalProfit(date);
		for (HierarchicalSettlementTotalDTO dto : listDto) {
			String organNo = dto.getOrganNo();
			if(map.get(organNo)==null){//新的数据
				HierarchicalSettlementTotal total =new HierarchicalSettlementTotal();
				total.setBatchNo(batchNo);
				total.setSettleDate(date);
				total.setCreateTime(createTime);
				generateSettlement(total, dto);
				map.put(organNo, total);
			}
			else{
				HierarchicalSettlementTotal total = map.get(organNo);
				generateSettlementExist(total, dto);
			}
		}
		//将数据批量插入到汇总数据表中，批量插入
		List<HierarchicalSettlementTotal> list = new ArrayList<HierarchicalSettlementTotal>(map.values());
		hierarchicalSettlementTotalDAO.inserBatch(list);//批量插入到层级利润表
		//钱包金额修改
		organWalletService.updateBalanceToWallet(date);//修改钱包余额
		//将代理商的利润汇总添加到对应渠道
		organWalletService.updateBalanceToChannel(date);//修改钱包余额
		log.info("saveSummaryProfit执行结束："+Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, new Date()));
	}
	
	/**
	 * @Title: generateSettlementExist
	 * @Description: map中实体已存在，需要重新对原有实体进行改装
	 * @author: husain
	 * @param total
	 * @param dto
	 * @return: void
	 */
	private void generateSettlementExist(HierarchicalSettlementTotal total, HierarchicalSettlementTotalDTO dto) {
		BigDecimal totalAmount = dto.getTotalAmount()==null?new BigDecimal("0"):dto.getTotalAmount();
		BigDecimal totalProfit = dto.getTotalProfit()==null?new BigDecimal("0"):dto.getTotalProfit();
		if("1".equals(dto.getTradeType())){//交易
			total.setTranTotalAmount(totalAmount);
			total.setTranProfit(totalProfit);
		}else if("2".equals(dto.getTradeType())){//提现
			total.setOutTotalAmount(totalAmount);
			total.setOutProfit(totalProfit);
		}
		total.setTotalProfit(total.getTotalProfit().add(totalProfit));//累加，最终的利润总额
	}

	/**
	 * 
	 * @Title: generateSettlement
	 * @Description: 对于新添加到map的实体对象
	 * @author: husain
	 * @param total
	 * @param dto
	 * @return: void
	 */
	private void generateSettlement(HierarchicalSettlementTotal total,HierarchicalSettlementTotalDTO dto){
		total.setId(Strings.getUUID());
		total.setChannelNo(dto.getChannelNo());
		total.setAgentNo(dto.getAgentNo());
		total.setAgentLevel(dto.getAgentLevel());
		total.setParentNo(dto.getParentNo());
		BigDecimal totalAmount = dto.getTotalAmount()==null?new BigDecimal("0"):dto.getTotalAmount();
		BigDecimal totalProfit = dto.getTotalProfit()==null?new BigDecimal("0"):dto.getTotalProfit();
		if("1".equals(dto.getTradeType())){//交易
			total.setTranTotalAmount(totalAmount);
			total.setTranProfit(totalProfit);
		}else if("2".equals(dto.getTradeType())){//提现
			total.setOutTotalAmount(totalAmount);
			total.setOutProfit(totalProfit);
		}
		total.setTotalProfit(totalProfit);//如果不为空则累加，此处不是最终的利润总额，中间值而已
	}
	
	/** 分润统计查询*/
	@Override
	public PagingResult<HierarchicalStatic> findHierarchicalStatic(
			HierarchicalStaticCondition hierarchicalStaticCondition,String nodeSeq){
		CriteriaBuilder cb = Cnd.builder(HierarchicalStatic.class);
		if(!Strings.isEmpty(hierarchicalStaticCondition.getId())){
			cb.andEQ("id", hierarchicalStaticCondition.getId());
		}
		if(!Strings.isEmpty(hierarchicalStaticCondition.getOrganNo())){
			cb.andEQ("organNo", hierarchicalStaticCondition.getOrganNo());
		}
		if(IdentityType.Identity_Channel.getCode().equals(hierarchicalStaticCondition.getIdentityFlag())){
			cb.addParam("identityFlag", IdentityType.Identity_Channel.getCode());
		}
		else if(IdentityType.Identity_Agent.getCode().equals(hierarchicalStaticCondition.getIdentityFlag())){
			cb.addParam("identityFlag", IdentityType.Identity_Agent.getCode());
		}
		if(Strings.isNotEmpty(nodeSeq)){
			cb.addParam("nodeSeq", nodeSeq);
		}
		cb.orderBy("totalProfit", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(hierarchicalStaticCondition.getFirst()), Long.valueOf(hierarchicalStaticCondition.getLast()));
		
		PagingResult<HierarchicalStatic> page = hierarchicalSettlementTotalDAO.findHierarchicalStatic(buildCriteria);
		for (HierarchicalStatic hierarchicalStatic : page.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+hierarchicalStatic.getOrganNo()));
				if(channelBase != null){
					hierarchicalStatic.setOrganName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+hierarchicalStatic.getOrganNo()));
				if(agentBase != null){
					hierarchicalStatic.setOrganName(agentBase.getAgentName());
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}

	@Override
	public Map<String, BigDecimal> getAmtStatic(HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition) {
		Map<String, BigDecimal> amtStatic = new HashMap<String, BigDecimal>();
		BigDecimal zfrTotalAmt = BigDecimal.ZERO;//总分润
		BigDecimal t1qsjeTotalAmt = BigDecimal.ZERO;//T1清算金额
		BigDecimal t1jyfrTotalAmt = BigDecimal.ZERO;//T1交易分润
		BigDecimal t0qsjeTotalAmt = BigDecimal.ZERO;//T0清算金额
		BigDecimal t0jyfrTotalAmt = BigDecimal.ZERO;//T0交易分润
		
		HierarchicalSettlementTotal hierarchicalSettlementTotal = hierarchicalSettlementTotalDAO.getAmtStatic(hierarchicalSettlementTotalCondition);
		if(hierarchicalSettlementTotal.getTotalProfit() !=null){
			zfrTotalAmt = zfrTotalAmt.add(hierarchicalSettlementTotal.getTotalProfit());//总分润
		}
		if(hierarchicalSettlementTotal.getTranTotalAmount() !=null){
			t1qsjeTotalAmt = t1qsjeTotalAmt.add(hierarchicalSettlementTotal.getTranTotalAmount());//T1清算金额
		}
		if(hierarchicalSettlementTotal.getTranProfit() !=null){
			t1jyfrTotalAmt = t1jyfrTotalAmt.add(hierarchicalSettlementTotal.getTranProfit());//T1交易分润
		}
		if(hierarchicalSettlementTotal.getOutTotalAmount() !=null){
			t0qsjeTotalAmt = t0qsjeTotalAmt.add(hierarchicalSettlementTotal.getOutTotalAmount());//T0清算金额
		}
		if(hierarchicalSettlementTotal.getOutProfit() !=null){
			t0jyfrTotalAmt = t0jyfrTotalAmt.add(hierarchicalSettlementTotal.getOutProfit());//T0交易分润
		}
		amtStatic.put("zfrTotalAmt", zfrTotalAmt);
		amtStatic.put("t1qsjeTotalAmt", t1qsjeTotalAmt);
		amtStatic.put("t1jyfrTotalAmt", t1jyfrTotalAmt);
		amtStatic.put("t0qsjeTotalAmt", t0qsjeTotalAmt);
		amtStatic.put("t0jyfrTotalAmt", t0jyfrTotalAmt);
		return amtStatic;
	}
	
	/**
	 * 
	 * @author liushuyu
	 * Desc 获取hierarchicalStatic的总数
	 * @return
	 */
	private int getHierarchicalStaticCount(Criteria criteria){
		return hierarchicalSettlementTotalDAO.getCountHierarchicalStatic(criteria);
	}
	
	/** 分润统计查询*/
	@Override
	public PagingResult<HierarchicalStatic> findAllHierarchicalStatic(
			HierarchicalStaticCondition hierarchicalStaticCondition,String nodeSeq){
		CriteriaBuilder cb = Cnd.builder(HierarchicalStatic.class);
		if(!Strings.isEmpty(hierarchicalStaticCondition.getId())){
			cb.andEQ("id", hierarchicalStaticCondition.getId());
		}
		if(!Strings.isEmpty(hierarchicalStaticCondition.getOrganNo())){
			cb.andEQ("organNo", hierarchicalStaticCondition.getOrganNo());
		}
		if(IdentityType.Identity_Channel.getCode().equals(hierarchicalStaticCondition.getIdentityFlag())){
			cb.addParam("identityFlag", IdentityType.Identity_Channel.getCode());
		}
		else if(IdentityType.Identity_Agent.getCode().equals(hierarchicalStaticCondition.getIdentityFlag())){
			cb.addParam("identityFlag", IdentityType.Identity_Agent.getCode());
		}
		if(Strings.isNotEmpty(nodeSeq)){
			cb.addParam("nodeSeq", nodeSeq);
		}
		cb.orderBy("totalProfit", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		
		int count=getHierarchicalStaticCount(buildCriteria);
		buildCriteria.limit(Long.valueOf(0), Long.valueOf(count));
		
		PagingResult<HierarchicalStatic> page = hierarchicalSettlementTotalDAO.findHierarchicalStatic(buildCriteria);
		for (HierarchicalStatic hierarchicalStatic : page.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+hierarchicalStatic.getOrganNo()));
				if(channelBase != null){
					hierarchicalStatic.setOrganName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+hierarchicalStatic.getOrganNo()));
				if(agentBase != null){
					hierarchicalStatic.setOrganName(agentBase.getAgentName());
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}
	
}

