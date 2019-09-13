/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */package com.hfepay.timer.service.impl;

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

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.scancode.api.entity.message.UnperfectMessage;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.MessagePushLogCondition;
import com.hfepay.scancode.commons.condition.MessagePushRuleCondition;
import com.hfepay.scancode.commons.contants.MessagePushType;
import com.hfepay.scancode.commons.contants.MessagePushWay;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.dao.AgentBaseDAO;
import com.hfepay.scancode.commons.dao.MerchantInfoDAO;
import com.hfepay.scancode.commons.dao.MessagePushLogDAO;
import com.hfepay.scancode.commons.dao.MessagePushRuleDAO;
import com.hfepay.scancode.commons.dao.channel.ChannelAdminDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MessagePushLog;
import com.hfepay.scancode.commons.entity.MessagePushRule;
import com.hfepay.timer.service.MessagePushRuleService;
import com.hfepay.timer.service.UserSmsService;

import net.sf.json.JSONSerializer;

@Service("messagePushRuleService")
public class MessagePushRuleServiceImpl implements MessagePushRuleService {
	
	public static final Logger log = LoggerFactory.getLogger(MessagePushRuleServiceImpl.class);
	
	@Autowired
    private MessagePushRuleDAO messagePushRuleDAO;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private ChannelAdminDAO channelAdminDAO;
	@Autowired
	private UserSmsService userSmsService;
	@Autowired
	private AgentBaseDAO agentBaseDAO;
	@Autowired
	private MerchantInfoDAO merchantInfoDAO;
	@Autowired
	private MessagePushLogDAO messagePushLogDAO;
    
    
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param messagePushRuleCondition
	 * @return: List<MessagePushRule>
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	@Override
	public List<MessagePushRule> findAll(MessagePushRuleCondition messagePushRuleCondition){
		CriteriaBuilder cb = Cnd.builder(MessagePushRule.class);
		if(!Strings.isEmpty(messagePushRuleCondition.getId())){
			cb.andEQ("id", messagePushRuleCondition.getId());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getChannelNo())){
			cb.andEQ("channelNo", messagePushRuleCondition.getChannelNo());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getAgentNo())){
			cb.andEQ("agentNo", messagePushRuleCondition.getAgentNo());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getMessageType())){
			cb.andEQ("messageType", messagePushRuleCondition.getMessageType());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getMessageContent())){
			cb.andEQ("messageContent", messagePushRuleCondition.getMessageContent());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getPushRule())){
			cb.andEQ("pushRule", messagePushRuleCondition.getPushRule());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getPushTime())){
			cb.andEQ("pushTime", messagePushRuleCondition.getPushTime());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getPushAuxiliaryRule())){
			cb.andEQ("pushAuxiliaryRule", messagePushRuleCondition.getPushAuxiliaryRule());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getPushWay())){
			cb.andEQ("pushWay", messagePushRuleCondition.getPushWay());
		}
		if(null != messagePushRuleCondition.getFirstPushDate()){
			cb.andEQ("firstPushDate", messagePushRuleCondition.getFirstPushDate());
		}
		if(null != messagePushRuleCondition.getFirstPushBeginDate()){
			cb.andGE("firstPushDate", messagePushRuleCondition.getFirstPushBeginDate());
		}
		if(null != messagePushRuleCondition.getFirstPushEndDate()){
			cb.andLT("firstPushDate", messagePushRuleCondition.getFirstPushEndDate());
		}
		if(null != messagePushRuleCondition.getNextPushDate()){
			cb.andEQ("nextPushDate", messagePushRuleCondition.getNextPushDate());
		}
		if(null != messagePushRuleCondition.getNextPushBeginDate()){
			cb.andGE("nextPushDate", messagePushRuleCondition.getNextPushBeginDate());
		}
		if(null != messagePushRuleCondition.getNextPushEndDate()){
			cb.andLE("nextPushDate", messagePushRuleCondition.getNextPushEndDate());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getStatus())){
			cb.andEQ("status", messagePushRuleCondition.getStatus());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getRecordStatus())){
			cb.andEQ("recordStatus", messagePushRuleCondition.getRecordStatus());
		}
		if(null != messagePushRuleCondition.getCreateTime()){
			cb.andEQ("createTime", messagePushRuleCondition.getCreateTime());
		}

		if(!Strings.isEmpty(messagePushRuleCondition.getOperator())){
			cb.andEQ("operator", messagePushRuleCondition.getOperator());
		}

		if(!Strings.isEmpty(messagePushRuleCondition.getRemark())){
			cb.andLike("remark", messagePushRuleCondition.getRemark());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getTemp1())){
			cb.andEQ("temp1", messagePushRuleCondition.getTemp1());
		}
		if(!Strings.isEmpty(messagePushRuleCondition.getTemp2())){
			cb.andEQ("temp2", messagePushRuleCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return messagePushRuleDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param messagePushRuleCondition
	 * @return: long
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	@Override
	public long update(MessagePushRuleCondition messagePushRuleCondition){
		MessagePushRule messagePushRule = new MessagePushRule();
		BeanUtils.copyProperties(messagePushRuleCondition, messagePushRule);
		messagePushRule.setUpdateTime(new Date());
		return messagePushRuleDAO.update(messagePushRule);
	}
	
	
	/** 推送消息 */
	@Override
	public void messagePush(){
		MessagePushRuleCondition firstMessagePushRule = new MessagePushRuleCondition();
		Date firstPushBeginDate = new Date();
		Calendar firstCal = Calendar.getInstance();
		firstCal.setTime(firstPushBeginDate);
		firstCal.add(Calendar.MINUTE,10);	
		firstMessagePushRule.setFirstPushBeginDate(firstPushBeginDate);
		firstMessagePushRule.setFirstPushEndDate(firstCal.getTime());
		firstMessagePushRule.setStatus(ScanCodeConstants.SUCCESS_STATE);
	    log.info("首次推送消息规则："+JSONSerializer.toJSON(firstMessagePushRule));
		List<MessagePushRule> firstMessagePushRuleList = this.findAll(firstMessagePushRule);
		for (MessagePushRule messagePushRule : firstMessagePushRuleList) {
			try {
				if(Strings.equals(messagePushRule.getPushWay(), MessagePushWay.PUSH_WAY_WXGZH.getValue())){				
					pushWxgzh(messagePushRule);
				}else if(Strings.equals(messagePushRule.getPushWay(), MessagePushWay.PUSH_WAY_SMS.getValue())){
					pushSms(messagePushRule);
				}
			} catch (Exception e) {
				log.error("首次推送消息规则异常："+e.getMessage());
			}
		}
		
		MessagePushRuleCondition messagePushRuleCondition = new MessagePushRuleCondition();
		Date nextPushBeginDate = new Date();
		Calendar cl = Calendar.getInstance();
	    cl.setTime(nextPushBeginDate);
	    cl.add(Calendar.MINUTE,10);	
	    messagePushRuleCondition.setNextPushBeginDate(nextPushBeginDate);
	    messagePushRuleCondition.setNextPushEndDate(cl.getTime());
	    messagePushRuleCondition.setStatus(ScanCodeConstants.SUCCESS_STATE);
	    log.info("下次推送消息规则："+JSONSerializer.toJSON(messagePushRuleCondition));
		List<MessagePushRule> messagePushRuleList = this.findAll(messagePushRuleCondition);
		for (MessagePushRule messagePushRule : messagePushRuleList) {
			try {
				if(Strings.equals(messagePushRule.getPushWay(), MessagePushWay.PUSH_WAY_WXGZH.getValue())){				
					pushWxgzh(messagePushRule);
				}else if(Strings.equals(messagePushRule.getPushWay(), MessagePushWay.PUSH_WAY_SMS.getValue())){
					pushSms(messagePushRule);
				}
			} catch (Exception e) {
				log.error("推送消息规则异常："+e.getMessage());
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(messagePushRule.getNextPushDate());
			cal.add(Calendar.DATE,Integer.valueOf(messagePushRule.getPushTime()));		    
		    messagePushRuleCondition.setNextPushDate(cal.getTime());
		    messagePushRuleCondition.setId(messagePushRule.getId());
		    this.update(messagePushRuleCondition);
		    log.info("更新推送消息规则下次执行时间："+JSONSerializer.toJSON(messagePushRuleCondition));
		}
	}	
	
	/** 推送微信公众号 */
	private void pushWxgzh(MessagePushRule messagePushRule){
		log.info("微信公众号推送消息规则："+JSONSerializer.toJSON(messagePushRule));
		if(Strings.equals(messagePushRule.getMessageType(), MessagePushType.MESSAGE_TYPE_0.getValue())){
			
			Calendar cl = Calendar.getInstance();
		    cl.add(Calendar.DATE, -Integer.valueOf(messagePushRule.getPushRule()));
		    
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channelNo", messagePushRule.getChannelNo());
			map.put("createTime", Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, cl.getTime()));
//			map.put("agentNo", messagePushRule.getAgentNo());
			log.info("微信公众号推送消息查询符合规则的账户入参："+JSONSerializer.toJSON(map));
			List<ChannelAdmin> channelAdminList = channelAdminDAO.findMerchantInfoUnperfect(map);
			for (ChannelAdmin channelAdmin : channelAdminList) {
				if(!Strings.equals(channelAdmin.getId(), channelAdmin.getOpenId())){
					UnperfectMessage message = new UnperfectMessage();
					message.setTouser(channelAdmin.getOpenId());
					message.setUrl("");
					message.setTitle("您申请的商户入驻已经成功：");
					message.setContent("商户入驻");
					message.setRegistTime(channelAdmin.getCreateTime());
					message.setRemark("亲，请尽快完善资料体验在线收款吧！");
					message.setOrganNo(channelAdmin.getChannelCode());
					try {
						log.info("微信公众号推送消息入参："+JSONSerializer.toJSON(message));
						Map<String,String> pushRes = merchantBusinessService.pushUnperfect(message);
						log.info("微信公众号推送消息成功："+JSONSerializer.toJSON(channelAdmin));
						
						MessagePushLogCondition messagePushLogCondition = new MessagePushLogCondition();
						messagePushLogCondition.setChannelNo(channelAdmin.getChannelCode());
						messagePushLogCondition.setAgentNo(channelAdmin.getAgentNo());
						messagePushLogCondition.setMerchantNo(channelAdmin.getMerchantNo());
						messagePushLogCondition.setMessageType(messagePushRule.getMessageType());
						messagePushLogCondition.setPushRule(messagePushRule.getPushRule());
						messagePushLogCondition.setPushTime(messagePushRule.getPushTime());
						messagePushLogCondition.setPushWay(messagePushRule.getPushWay());
						messagePushLogCondition.setPushDate(new Date());
						messagePushLogCondition.setPushResult(JSONSerializer.toJSON(pushRes).toString());
						messagePushLogCondition.setTemp1(messagePushRule.getTemp1());
						StringBuffer sb = new StringBuffer();
						sb.append("您申请的商户入驻已经成功：<br>");
						sb.append("<b>申请事项：</b>商户入驻<br>");
						sb.append("<b>办理时间：</b>");
						sb.append(Dates.format("yyyy年MM月dd日 HH:mm:ss", message.getRegistTime()));
						sb.append("<br>");
						sb.append("亲，请尽快完善资料体验在线收款吧！<br>");
						String content = sb.toString();
						messagePushLogCondition.setMessageContent(content);
						MessagePushLog messagePushLog = new MessagePushLog();
						BeanUtils.copyProperties(messagePushLogCondition, messagePushLog);
						messagePushLogDAO.insert(messagePushLog);
					} catch (Exception e) {
						log.error("推送微信公众号消息异常："+JSONSerializer.toJSON(channelAdmin)+e.getMessage());
					}
				}
				
			}
		}
	}
	
	/** 推送短信 */
	private void pushSms(MessagePushRule messagePushRule){
		log.info("短信推送消息规则："+JSONSerializer.toJSON(messagePushRule));
		if(Strings.equals(messagePushRule.getMessageType(), MessagePushType.MESSAGE_TYPE_0.getValue())){
			log.info("商户资料待完善不支持短信推送消息："+messagePushRule.toString());
		}else if(Strings.equals(messagePushRule.getMessageType(), MessagePushType.MESSAGE_TYPE_2.getValue())){
			AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
			agentBaseCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_YES);
			agentBaseCondition.setStatus(ScanCodeConstants.SUCCESS_STATE);
			agentBaseCondition.setChannelNo(messagePushRule.getChannelNo());
			agentBaseCondition.setAgentLevel(messagePushRule.getTemp1());
			
			CriteriaBuilder cbAgent = Cnd.builder(AgentBase.class);
			cbAgent.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
			cbAgent.andEQ("status", ScanCodeConstants.SUCCESS_STATE);
			cbAgent.andEQ("channelNo", messagePushRule.getChannelNo());
			cbAgent.andEQ("agentLevel", messagePushRule.getTemp1());
			Criteria buildCriteriaAgent = cbAgent.buildCriteria();
			List<AgentBase> agentBaseList = agentBaseDAO.findByCriteria(buildCriteriaAgent);
			for (AgentBase agentBase : agentBaseList) {
				log.info("短信推送消息的代理商："+JSONSerializer.toJSON(agentBase));
				try {
					MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
					merchantInfoCondition.setAgentNo(agentBase.getAgentNo());
					CriteriaBuilder cbMerchant = Cnd.builder(MerchantInfo.class);
					cbMerchant.andEQ("agentNo", agentBase.getAgentNo());
					Criteria buildCriteriaMerchant = cbMerchant.buildCriteria();
					int merchantInfoNum = merchantInfoDAO.countByCriteria(buildCriteriaMerchant);
					if(merchantInfoNum < Integer.valueOf(messagePushRule.getPushRule())){					
						StringBuffer sb = new StringBuffer();
						sb.append("尊敬的");
						sb.append(agentBase.getAgentName());
						sb.append("代理商，您目前代理的总商户数为");
						sb.append(merchantInfoNum);
						sb.append("，未达到您所代理级别的基本标准，请继续努力！");
						String content = sb.toString();
						log.info("短信推送消息代理商的短信内容："+content);
						Boolean flag = userSmsService.sendSms(content, agentBase.getMobile(),agentBase.getChannelNo(), agentBase.getAgentNo(),null);
						log.info("短信推送消息代理商的短信发送结果："+flag);
						
						MessagePushLogCondition messagePushLogCondition = new MessagePushLogCondition();
						messagePushLogCondition.setChannelNo(agentBase.getChannelNo());
						messagePushLogCondition.setAgentNo(agentBase.getAgentNo());
						messagePushLogCondition.setMessageType(messagePushRule.getMessageType());
						messagePushLogCondition.setPushRule(messagePushRule.getPushRule());
						messagePushLogCondition.setPushTime(messagePushRule.getPushTime());
						messagePushLogCondition.setPushWay(messagePushRule.getPushWay());
						messagePushLogCondition.setPushDate(new Date());
						messagePushLogCondition.setPushResult(flag.toString());
						messagePushLogCondition.setTemp1(messagePushRule.getTemp1());
						messagePushLogCondition.setMessageContent(content);
						MessagePushLog messagePushLog = new MessagePushLog();
						BeanUtils.copyProperties(messagePushLogCondition, messagePushLog);
						messagePushLogDAO.insert(messagePushLog);
					}
				} catch (Exception e) {
					log.error("推送短息消息异常："+e.getMessage());
				}
			}
			
		}
	}
	
	
}

