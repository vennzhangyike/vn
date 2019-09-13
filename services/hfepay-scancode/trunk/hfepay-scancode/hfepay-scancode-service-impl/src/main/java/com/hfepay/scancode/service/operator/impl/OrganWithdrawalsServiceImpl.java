/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.hfepay.scancode.commons.condition.AuditLogCondition;
import com.hfepay.scancode.commons.condition.OrganWalletCondition;
import com.hfepay.scancode.commons.condition.OrganWithdrawalsCondition;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.OrganWithdrawalsDAO;
import com.hfepay.scancode.commons.entity.AgentBankcard;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBankcard;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.OrganWithdrawals;
import com.hfepay.scancode.commons.vo.OrganWithdrawalsVo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.AgentBankcardService;
import com.hfepay.scancode.service.operator.AuditLogService;
import com.hfepay.scancode.service.operator.ChannelBankcardService;
import com.hfepay.scancode.service.operator.OrganWalletService;
import com.hfepay.scancode.service.operator.OrganWithdrawalsService;
import com.hfepay.scancode.service.utils.OrderIDUtils;

@Service("organWithdrawalsService")
public class OrganWithdrawalsServiceImpl implements OrganWithdrawalsService {
	public static final Logger log = LoggerFactory.getLogger(OrganWithdrawalsServiceImpl.class);
	@Autowired
    private OrganWithdrawalsDAO organWithdrawalsDAO;
	@Autowired
	private ChannelBankcardService channelBankcardService;
	@Autowired
	private AgentBankcardService agentBankcardService;
	@Autowired
	private OrganWalletService organWalletService;
	@Autowired
	private OrderIDUtils orderUtils;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private RedisSharedCache redisSharedCache;
    /**
	 * 列表(分页)
	 * @param organWithdrawalsCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
    @Override
	public PagingResult<OrganWithdrawals> findPagingResult(OrganWithdrawalsCondition organWithdrawalsCondition){
    	CriteriaBuilder cb = Cnd.builder(OrganWithdrawals.class);
		if(!Strings.isEmpty(organWithdrawalsCondition.getId())){
			cb.andEQ("id", organWithdrawalsCondition.getId());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getWithdrawalsNo())){
			cb.andEQ("withdrawalsNo", organWithdrawalsCondition.getWithdrawalsNo());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getChannelNo())){
			cb.andEQ("channelNo", organWithdrawalsCondition.getChannelNo());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getOrganNo())){
			cb.andEQ("organNo", organWithdrawalsCondition.getOrganNo());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getIsChannel())){
			cb.andEQ("isChannel", organWithdrawalsCondition.getIsChannel());
		}
		if(null != organWithdrawalsCondition.getBalance()){
			cb.andEQ("balance", organWithdrawalsCondition.getBalance());
		}
		if(null != organWithdrawalsCondition.getActualAmount()){
			cb.andEQ("actualAmount", organWithdrawalsCondition.getActualAmount());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getStatus())){
			cb.andEQ("status", organWithdrawalsCondition.getStatus());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getBankCode())){
			cb.andEQ("bankCode", organWithdrawalsCondition.getBankCode());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getBankName())){
			cb.andEQ("bankName", organWithdrawalsCondition.getBankName());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getBankCard())){
			cb.andEQ("bankCard", organWithdrawalsCondition.getBankCard());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getIdCard())){
			cb.andEQ("idCard", organWithdrawalsCondition.getIdCard());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getName())){
			cb.andEQ("name", organWithdrawalsCondition.getName());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getMobile())){
			cb.andEQ("mobile", organWithdrawalsCondition.getMobile());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getAccountType())){
			cb.andEQ("accountType", organWithdrawalsCondition.getAccountType());
		}
		
		if(null != organWithdrawalsCondition.getCreateTime()){
			cb.andEQ("createTime", organWithdrawalsCondition.getCreateTime());
		}
		if(null != organWithdrawalsCondition.getUpdateTime()){
			cb.andEQ("updateTime", organWithdrawalsCondition.getUpdateTime());
		}

		if(!Strings.isEmpty(organWithdrawalsCondition.getOperator())){
			cb.andEQ("operator", organWithdrawalsCondition.getOperator());
		}

		if(!Strings.isEmpty(organWithdrawalsCondition.getRemark())){
			cb.andLike("remark", organWithdrawalsCondition.getRemark());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getTemp1())){
			cb.andEQ("temp1", organWithdrawalsCondition.getTemp1());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getTemp2())){
			cb.andEQ("temp2", organWithdrawalsCondition.getTemp2());
		}
		try {
			if(!Strings.isEmpty(organWithdrawalsCondition.getQueryStartDate())){
				cb.andGE("createTime", Dates.parse("yyyy-MM-dd", organWithdrawalsCondition.getQueryStartDate()));
			}
			if(!Strings.isEmpty(organWithdrawalsCondition.getQueryEndDate())){
				cb.andLT("createTime", Dates.parse("yyyy-MM-dd HH:mm:ss", organWithdrawalsCondition.getQueryEndDate() + " 23:59:59"));
			}
		} catch (ParseException e) {
			throw new RuntimeException("时间转换异常");
		}
		//cb.orderBy("status", Order.ASC);
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(organWithdrawalsCondition.getFirst()), Long.valueOf(organWithdrawalsCondition.getLast()));
		
		PagingResult<OrganWithdrawals> page = organWithdrawalsDAO.findPagingResult(buildCriteria);
		for (OrganWithdrawals organWithdrawals : page.getResult()) {
			try {
				OrganWithdrawalsVo vo = (OrganWithdrawalsVo)organWithdrawals;
				if(ScanCodeConstants.IDENTITYFLAG_CHANNEL.equals(organWithdrawals.getIsChannel())){
					ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+organWithdrawals.getOrganNo()));
					if(channelBase != null){
						vo.setOrganName(channelBase.getChannelName());
					}	
				}
				else{
					AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+organWithdrawals.getOrganNo()));
					if(agentBase != null){
						vo.setOrganName(agentBase.getAgentName());
					}
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
	 * @param organWithdrawals 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public List<OrganWithdrawals> findAll(OrganWithdrawalsCondition organWithdrawalsCondition){
		CriteriaBuilder cb = Cnd.builder(OrganWithdrawals.class);
		if(!Strings.isEmpty(organWithdrawalsCondition.getId())){
			cb.andEQ("id", organWithdrawalsCondition.getId());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getWithdrawalsNo())){
			cb.andEQ("withdrawalsNo", organWithdrawalsCondition.getWithdrawalsNo());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getChannelNo())){
			cb.andEQ("channelNo", organWithdrawalsCondition.getChannelNo());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getOrganNo())){
			cb.andEQ("organNo", organWithdrawalsCondition.getOrganNo());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getIsChannel())){
			cb.andEQ("isChannel", organWithdrawalsCondition.getIsChannel());
		}
		if(null != organWithdrawalsCondition.getBalance()){
			cb.andEQ("balance", organWithdrawalsCondition.getBalance());
		}
		if(null != organWithdrawalsCondition.getActualAmount()){
			cb.andEQ("actualAmount", organWithdrawalsCondition.getActualAmount());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getStatus())){
			cb.andEQ("status", organWithdrawalsCondition.getStatus());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getBankCode())){
			cb.andEQ("bankCode", organWithdrawalsCondition.getBankCode());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getBankName())){
			cb.andEQ("bankName", organWithdrawalsCondition.getBankName());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getBankCard())){
			cb.andEQ("bankCard", organWithdrawalsCondition.getBankCard());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getIdCard())){
			cb.andEQ("idCard", organWithdrawalsCondition.getIdCard());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getName())){
			cb.andEQ("name", organWithdrawalsCondition.getName());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getMobile())){
			cb.andEQ("mobile", organWithdrawalsCondition.getMobile());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getAccountType())){
			cb.andEQ("accountType", organWithdrawalsCondition.getAccountType());
		}
		
		if(null != organWithdrawalsCondition.getCreateTime()){
			cb.andEQ("createTime", organWithdrawalsCondition.getCreateTime());
		}
		if(null != organWithdrawalsCondition.getUpdateTime()){
			cb.andEQ("updateTime", organWithdrawalsCondition.getUpdateTime());
		}

		if(!Strings.isEmpty(organWithdrawalsCondition.getOperator())){
			cb.andEQ("operator", organWithdrawalsCondition.getOperator());
		}

		if(!Strings.isEmpty(organWithdrawalsCondition.getRemark())){
			cb.andLike("remark", organWithdrawalsCondition.getRemark());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getTemp1())){
			cb.andEQ("temp1", organWithdrawalsCondition.getTemp1());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getTemp2())){
			cb.andEQ("temp2", organWithdrawalsCondition.getTemp2());
		}
		try {
			if(!Strings.isEmpty(organWithdrawalsCondition.getQueryStartDate())){
				cb.andGE("createTime", Dates.parse("yyyy-MM-dd", organWithdrawalsCondition.getQueryStartDate()));
			}
			if(!Strings.isEmpty(organWithdrawalsCondition.getQueryEndDate())){
				cb.andLT("createTime", Dates.parse("yyyy-MM-dd HH:mm:ss", organWithdrawalsCondition.getQueryEndDate()));
			}
		} catch (ParseException e) {
			throw new RuntimeException("时间转换异常");
		}

		cb.orderBy("status", Order.ASC);
		Criteria buildCriteria = cb.buildCriteria();
		List<OrganWithdrawals> list = organWithdrawalsDAO.findByCriteria(buildCriteria);
		for (OrganWithdrawals organWithdrawals : list) {
			try {
				OrganWithdrawalsVo vo = (OrganWithdrawalsVo)organWithdrawals;
				if(ScanCodeConstants.IDENTITYFLAG_CHANNEL.equals(organWithdrawals.getIsChannel())){
					ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+organWithdrawals.getOrganNo()));
					if(channelBase != null){
						vo.setOrganName(channelBase.getChannelName());
					}	
				}
				else{
					AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+organWithdrawals.getOrganNo()));
					if(agentBase != null){
						vo.setOrganName(agentBase.getAgentName());
					}
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
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public OrganWithdrawals findById(String id){
		return organWithdrawalsDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param organWithdrawalsCondition
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long insert(OrganWithdrawalsCondition organWithdrawalsCondition){
		OrganWithdrawals organWithdrawals = new OrganWithdrawals();
		BeanUtils.copyProperties(organWithdrawalsCondition, organWithdrawals);
		if(Strings.isEmpty(organWithdrawalsCondition.getId())){
			organWithdrawals.setId(Strings.getUUID());
		}
		return organWithdrawalsDAO.insert(organWithdrawals);
	}

	/**
	 * 删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long deleteById(String id){
		return organWithdrawalsDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return organWithdrawalsDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return organWithdrawalsDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long update(OrganWithdrawalsCondition organWithdrawalsCondition){
		OrganWithdrawals organWithdrawals = new OrganWithdrawals();
		BeanUtils.copyProperties(organWithdrawalsCondition, organWithdrawals);
		return organWithdrawalsDAO.update(organWithdrawals);
	}
	
	/**
	 * 条件更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public long updateByCriteria(OrganWithdrawalsCondition organWithdrawalsCondition,Criteria criteria){
		OrganWithdrawals organWithdrawals = new OrganWithdrawals();
		BeanUtils.copyProperties(organWithdrawalsCondition, organWithdrawals);
		return organWithdrawalsDAO.updateByCriteria(organWithdrawals,criteria);
	}
	
	/**
	 * 单个实体对象
	 * @param organWithdrawalsCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2016-12-20 11:30:02
	 */
	@Override
	public OrganWithdrawals findByCondition(OrganWithdrawalsCondition organWithdrawalsCondition){
		CriteriaBuilder cb = Cnd.builder(OrganWithdrawals.class);
		if(!Strings.isEmpty(organWithdrawalsCondition.getId())){
			cb.andEQ("id", organWithdrawalsCondition.getId());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getWithdrawalsNo())){
			cb.andEQ("withdrawalsNo", organWithdrawalsCondition.getWithdrawalsNo());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getChannelNo())){
			cb.andEQ("channelNo", organWithdrawalsCondition.getChannelNo());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getOrganNo())){
			cb.andEQ("organNo", organWithdrawalsCondition.getOrganNo());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getIsChannel())){
			cb.andEQ("isChannel", organWithdrawalsCondition.getIsChannel());
		}
		if(null != organWithdrawalsCondition.getBalance()){
			cb.andEQ("balance", organWithdrawalsCondition.getBalance());
		}
		if(null != organWithdrawalsCondition.getActualAmount()){
			cb.andEQ("actualAmount", organWithdrawalsCondition.getActualAmount());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getStatus())){
			cb.andEQ("status", organWithdrawalsCondition.getStatus());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getBankCode())){
			cb.andEQ("bankCode", organWithdrawalsCondition.getBankCode());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getBankName())){
			cb.andEQ("bankName", organWithdrawalsCondition.getBankName());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getBankCard())){
			cb.andEQ("bankCard", organWithdrawalsCondition.getBankCard());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getIdCard())){
			cb.andEQ("idCard", organWithdrawalsCondition.getIdCard());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getName())){
			cb.andEQ("name", organWithdrawalsCondition.getName());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getMobile())){
			cb.andEQ("mobile", organWithdrawalsCondition.getMobile());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getAccountType())){
			cb.andEQ("accountType", organWithdrawalsCondition.getAccountType());
		}
		
		if(null != organWithdrawalsCondition.getCreateTime()){
			cb.andEQ("createTime", organWithdrawalsCondition.getCreateTime());
		}
		if(null != organWithdrawalsCondition.getUpdateTime()){
			cb.andEQ("updateTime", organWithdrawalsCondition.getUpdateTime());
		}

		if(!Strings.isEmpty(organWithdrawalsCondition.getOperator())){
			cb.andEQ("operator", organWithdrawalsCondition.getOperator());
		}

		if(!Strings.isEmpty(organWithdrawalsCondition.getRemark())){
			cb.andLike("remark", organWithdrawalsCondition.getRemark());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getTemp1())){
			cb.andEQ("temp1", organWithdrawalsCondition.getTemp1());
		}
		if(!Strings.isEmpty(organWithdrawalsCondition.getTemp2())){
			cb.andEQ("temp2", organWithdrawalsCondition.getTemp2());
		}
		try {
			if(!Strings.isEmpty(organWithdrawalsCondition.getQueryStartDate())){
				cb.andGE("createTime", Dates.parse("yyyy-MM-dd", organWithdrawalsCondition.getQueryStartDate()));
			}
			if(!Strings.isEmpty(organWithdrawalsCondition.getQueryEndDate())){
				cb.andLT("createTime", Dates.parse("yyyy-MM-dd HH:mm:ss", organWithdrawalsCondition.getQueryEndDate()));
			}
		} catch (ParseException e) {
			throw new RuntimeException("时间转换异常");
		}
		Criteria buildCriteria = cb.buildCriteria();
		return organWithdrawalsDAO.findOneByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: saveWithDraw
	 * @Description: 保存提现申请
	 * @param organWithdrawalsCondition
	 * @return
	 */
	@Override
	public long saveWithDraw(OrganWithdrawalsCondition organWithdrawalsCondition) {
		wrapCondition(organWithdrawalsCondition);//构造参数
		
		long result =  insert(organWithdrawalsCondition);
		//钱包金额变化：可用金额减少，冻结金额增加
		if(result>0){
			OrganWalletCondition organWalletCondition = new OrganWalletCondition();
			organWalletCondition.setOrganNo(organWithdrawalsCondition.getOrganNo());
			organWalletCondition.setModifyBalance(organWithdrawalsCondition.getBalance());
			organWalletCondition.setOperator(organWithdrawalsCondition.getOperator());
			organWalletCondition.setUpdateTime(new Date());
			result = organWalletService.updateBalance(organWalletCondition);
			if(result<=0){
				throw new RuntimeException("提现申请失败");
			}
		}
		return result;
	}

	private void wrapCondition(OrganWithdrawalsCondition organWithdrawalsCondition) {
		organWithdrawalsCondition.setId(Strings.getUUID());
		String withdrawalsNo = orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "TX");
		organWithdrawalsCondition.setWithdrawalsNo(withdrawalsNo);
		organWithdrawalsCondition.setStatus("1");//申请中
		organWithdrawalsCondition.setCreateTime(new Date());
		if("1".equals(organWithdrawalsCondition.getIsChannel())){//渠道
//			ChannelBankcard card = channelBankcardService.findByChannelNo(organWithdrawalsCondition.getOrganNo());
//			if (null == card) {
//				return 0;
//			}
//			organWithdrawalsCondition.setBankCode(card.getBankCode());
//			organWithdrawalsCondition.setBankName(card.getBankName());
//			organWithdrawalsCondition.setBankCard(card.getBankCard());
//			organWithdrawalsCondition.setIdCard(card.getIdCard());
//			organWithdrawalsCondition.setName(card.getName());
//			organWithdrawalsCondition.setMobile(card.getMobile());
//			organWithdrawalsCondition.setAccountType(card.getAccountType());
			organWithdrawalsCondition.setIsChannel("1");
		}
		else{//代理商
//			AgentBankcard card = agentBankcardService.findByAgentNo(organWithdrawalsCondition.getOrganNo());
//			if (null == card) {
//				return 0;
//			}
//			organWithdrawalsCondition.setBankCode(card.getBankCode());
//			organWithdrawalsCondition.setBankName(card.getBankName());
//			organWithdrawalsCondition.setBankCard(card.getBankCard());
//			organWithdrawalsCondition.setIdCard(card.getIdCard());
//			organWithdrawalsCondition.setName(card.getName());
//			organWithdrawalsCondition.setMobile(card.getMobile());
//			organWithdrawalsCondition.setAccountType(card.getAccountType());
			organWithdrawalsCondition.setIsChannel("0");
		}
	}
	
	/**
	 * @Title: saveWithDraw
	 * @Description: 提现审核
	 * @param organWithdrawalsCondition
	 * @return
	 */
	@Override
	public long saveAuditWithDraw(OrganWithdrawalsCondition organWithdrawalsCondition) {
		OrganWithdrawals organWithdrawals = this.findById(organWithdrawalsCondition.getId());
		String operator = organWithdrawalsCondition.getOperator();
		//修改钱包冻结金额，修改申请记录状态
		if("1".equals(organWithdrawals.getIsChannel())){//渠道
			ChannelBankcard card = channelBankcardService.findByChannelNo(organWithdrawals.getOrganNo());
			organWithdrawalsCondition.setBankCode(card.getBankCode());
			organWithdrawalsCondition.setBankName(card.getBankName());
			organWithdrawalsCondition.setBankCard(card.getBankCard());
			organWithdrawalsCondition.setIdCard(card.getIdCard());
			organWithdrawalsCondition.setName(card.getName());
			organWithdrawalsCondition.setMobile(card.getMobile());
			organWithdrawalsCondition.setAccountType(card.getAccountType());
		}
		else{//代理商
			AgentBankcard card = agentBankcardService.findByAgentNo(organWithdrawals.getOrganNo());
			organWithdrawalsCondition.setBankCode(card.getBankCode());
			organWithdrawalsCondition.setBankName(card.getBankName());
			organWithdrawalsCondition.setBankCard(card.getBankCard());
			organWithdrawalsCondition.setIdCard(card.getIdCard());
			organWithdrawalsCondition.setName(card.getName());
			organWithdrawalsCondition.setMobile(card.getMobile());
			organWithdrawalsCondition.setAccountType(card.getAccountType());
		}
		OrganWithdrawals draws = findById(organWithdrawalsCondition.getId());
		OrganWalletCondition organWalletCondition = new OrganWalletCondition();
		organWalletCondition.setOrganNo(draws.getOrganNo());
		if("2".equals(organWithdrawalsCondition.getStatus())){//审核通过
			organWalletCondition.setFreezenBalance(draws.getBalance());
			organWithdrawalsCondition.setActualAmount(draws.getBalance());
		}else{//不通过
			organWalletCondition.setModifyBalance(new BigDecimal("0").subtract(draws.getBalance()));//还原金额
			organWithdrawalsCondition.setActualAmount(new BigDecimal("0"));
		}
		organWithdrawalsCondition.setOperator(null);//不修改提现记录操作人id
		long result = update(organWithdrawalsCondition);//修改申请记录状态
		checkResult(result);
		
		organWalletCondition.setOperator(operator);
		organWalletCondition.setUpdateTime(new Date());
		result = organWalletService.updateBalance(organWalletCondition);
		checkResult(result);
		
		//提现审核记录
		AuditLogCondition log = new AuditLogCondition();
		log.setId(Strings.getUUID());
		log.setAuditId(organWithdrawalsCondition.getId());
		log.setAuditType("3");//提现审核
		log.setAuditStatus(organWithdrawalsCondition.getStatus());
		log.setReason(organWithdrawalsCondition.getRemark());
		log.setCreateTime(new Date());
		log.setOperator(operator);
		result = auditLogService.insert(log);
		checkResult(result);
		return result;
	}
	
	private void checkResult(long result){
		if(result<=0){
			throw new RuntimeException("审核失败");
		}
	}
	
	/** 金额统计 */
	@Override
	public Map<String, BigDecimal> getAmtStatistics(OrganWithdrawalsCondition organWithdrawalsCondition) {
		if(!Strings.isEmpty(organWithdrawalsCondition.getQueryEndDate())){
			organWithdrawalsCondition.setQueryEndDate(organWithdrawalsCondition.getQueryEndDate()  + " 23:59:59");
		}
		List<OrganWithdrawals> organWithdrawalses = organWithdrawalsDAO.getAmtStatistics(organWithdrawalsCondition);
		
		Map<String, BigDecimal> amtStatic = new HashMap<String, BigDecimal>();
		BigDecimal sqtxTotalAmt = BigDecimal.ZERO;//申请提现金额
		BigDecimal sjdzTotalAmt = BigDecimal.ZERO;//实际到账金额
		
		for (OrganWithdrawals organWithdrawals : organWithdrawalses) {
			if(organWithdrawals.getBalance() !=null){
				sqtxTotalAmt = sqtxTotalAmt.add(organWithdrawals.getBalance());//申请提现金额
			}
			if(organWithdrawals.getActualAmount() != null){
				sjdzTotalAmt = sjdzTotalAmt.add(organWithdrawals.getActualAmount());//实际到账金额
			}
		}
		amtStatic.put("sqtxTotalAmt", sqtxTotalAmt);
		amtStatic.put("sjdzTotalAmt", sjdzTotalAmt);
		return amtStatic;
	}
}

