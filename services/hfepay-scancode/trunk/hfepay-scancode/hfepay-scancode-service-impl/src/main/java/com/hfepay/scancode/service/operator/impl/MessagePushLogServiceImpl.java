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
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.MessagePushLogCondition;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MessagePushLogDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MessagePushLog;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.MessagePushLogService;

import net.sf.json.JSONObject;

@Service("messagePushLogService")
public class MessagePushLogServiceImpl implements MessagePushLogService {
	public static final Logger log = LoggerFactory.getLogger(MessagePushLogServiceImpl.class);
	
	@Autowired
    private MessagePushLogDAO messagePushLogDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param messagePushLogCondition
	 * @return: PagingResult<MessagePushLog>
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
    @Override
	public PagingResult<MessagePushLog> findPagingResult(MessagePushLogCondition messagePushLogCondition){
		CriteriaBuilder cb = Cnd.builder(MessagePushLog.class);
		if(!Strings.isEmpty(messagePushLogCondition.getId())){
			cb.andEQ("id", messagePushLogCondition.getId());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getChannelNo())){
			cb.andEQ("channelNo", messagePushLogCondition.getChannelNo());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getAgentNo())){
			cb.andEQ("agentNo", messagePushLogCondition.getAgentNo());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getMerchantNo())){
			cb.andEQ("merchantNo", messagePushLogCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getMessageType())){
			cb.andEQ("messageType", messagePushLogCondition.getMessageType());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getMessageContent())){
			cb.andEQ("messageContent", messagePushLogCondition.getMessageContent());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getPushRule())){
			cb.andEQ("pushRule", messagePushLogCondition.getPushRule());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getPushTime())){
			cb.andEQ("pushTime", messagePushLogCondition.getPushTime());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getPushWay())){
			cb.andEQ("pushWay", messagePushLogCondition.getPushWay());
		}
		if(null != messagePushLogCondition.getPushDate()){
			cb.andEQ("pushDate", messagePushLogCondition.getPushDate());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getBeginTimeStr())){
			cb.andGE("pushDate", messagePushLogCondition.getBeginTimeStr());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getEndTimeStr())){
			cb.andLE("pushDate", messagePushLogCondition.getEndTimeStr());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getPushResult())){
			cb.andEQ("pushResult", messagePushLogCondition.getPushResult());
		}

		if(!Strings.isEmpty(messagePushLogCondition.getRemark())){
			cb.andLike("remark", messagePushLogCondition.getRemark());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getTemp1())){
			cb.andEQ("temp1", messagePushLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getTemp2())){
			cb.andEQ("temp2", messagePushLogCondition.getTemp2());
		}
		cb.orderBy("pushDate", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(messagePushLogCondition.getFirst()), Long.valueOf(messagePushLogCondition.getLast()));
		
		PagingResult<MessagePushLog> page = messagePushLogDAO.findPagingResult(buildCriteria);
		for (MessagePushLog messagePushLog : page.getResult()) {			
			try {
				if(Strings.isEmpty(messagePushLog.getTemp1())){
					messagePushLog.setTemp1(ScanCodeConstants.SYSTEM_UNLIMITED);
				}
				ChannelBase channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+messagePushLog.getChannelNo()));
				if(channelBase != null){
					messagePushLog.setChannelName(channelBase.getChannelName());
				}			
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+messagePushLog.getAgentNo()));
				if(agentBase != null){
					messagePushLog.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+messagePushLog.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					messagePushLog.setMerchantName(merchantInfo.getMerchantName());
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param messagePushLogCondition
	 * @return: List<MessagePushLog>
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	@Override
	public List<MessagePushLog> findAll(MessagePushLogCondition messagePushLogCondition){
		CriteriaBuilder cb = Cnd.builder(MessagePushLog.class);
		if(!Strings.isEmpty(messagePushLogCondition.getId())){
			cb.andEQ("id", messagePushLogCondition.getId());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getChannelNo())){
			cb.andEQ("channelNo", messagePushLogCondition.getChannelNo());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getAgentNo())){
			cb.andEQ("agentNo", messagePushLogCondition.getAgentNo());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getMerchantNo())){
			cb.andEQ("merchantNo", messagePushLogCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getMessageType())){
			cb.andEQ("messageType", messagePushLogCondition.getMessageType());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getMessageContent())){
			cb.andEQ("messageContent", messagePushLogCondition.getMessageContent());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getPushRule())){
			cb.andEQ("pushRule", messagePushLogCondition.getPushRule());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getPushTime())){
			cb.andEQ("pushTime", messagePushLogCondition.getPushTime());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getPushWay())){
			cb.andEQ("pushWay", messagePushLogCondition.getPushWay());
		}
		if(null != messagePushLogCondition.getPushDate()){
			cb.andEQ("pushDate", messagePushLogCondition.getPushDate());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getPushResult())){
			cb.andEQ("pushResult", messagePushLogCondition.getPushResult());
		}

		if(!Strings.isEmpty(messagePushLogCondition.getRemark())){
			cb.andLike("remark", messagePushLogCondition.getRemark());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getTemp1())){
			cb.andEQ("temp1", messagePushLogCondition.getTemp1());
		}
		if(!Strings.isEmpty(messagePushLogCondition.getTemp2())){
			cb.andEQ("temp2", messagePushLogCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return messagePushLogDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: MessagePushLog
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	@Override
	public MessagePushLog findById(String id){
		return messagePushLogDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param messagePushLogCondition
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	@Override
	public long insert(MessagePushLogCondition messagePushLogCondition){
		MessagePushLog messagePushLog = new MessagePushLog();
		BeanUtils.copyProperties(messagePushLogCondition, messagePushLog);
		return messagePushLogDAO.insert(messagePushLog);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	@Override
	public long deleteById(String id){
		return messagePushLogDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return messagePushLogDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return messagePushLogDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param messagePushLogCondition
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	@Override
	public long update(MessagePushLogCondition messagePushLogCondition){
		MessagePushLog messagePushLog = new MessagePushLog();
		BeanUtils.copyProperties(messagePushLogCondition, messagePushLog);
		return messagePushLogDAO.update(messagePushLog);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param messagePushLogCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	@Override
	public long updateByCriteria(MessagePushLogCondition messagePushLogCondition,Criteria criteria){
		MessagePushLog messagePushLog = new MessagePushLog();
		BeanUtils.copyProperties(messagePushLogCondition, messagePushLog);
		return messagePushLogDAO.updateByCriteria(messagePushLog,criteria);
	}
	
}

