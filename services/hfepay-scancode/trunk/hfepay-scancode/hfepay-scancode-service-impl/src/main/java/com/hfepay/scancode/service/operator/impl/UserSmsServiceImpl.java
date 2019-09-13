/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

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
import com.hfepay.common.sms.services.SmsService;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.commons.condition.UserSmsCondition;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.UserSmsDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.commons.entity.UserSms;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.ParamsInfoService;
import com.hfepay.scancode.service.operator.UserSmsService;

import net.sf.json.JSONObject;

@Service("userSmsService")
public class UserSmsServiceImpl implements UserSmsService {
	public static final Logger log = LoggerFactory.getLogger(UserSmsServiceImpl.class);
	
	@Autowired
    private UserSmsDAO userSmsDAO;
	
	@Autowired
	private SmsService smsService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private ParamsInfoService paramsInfoService;
	
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private RedisSharedCache redisSharedCache;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param userSmsCondition
	 * @return: PagingResult<UserSms>
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
    @Override
	public PagingResult<UserSms> findPagingResult(UserSmsCondition userSmsCondition){
		CriteriaBuilder cb = Cnd.builder(UserSms.class);
		if(!Strings.isEmpty(userSmsCondition.getId())){
			cb.andEQ("id", userSmsCondition.getId());
		}
		if(!Strings.isEmpty(userSmsCondition.getChannelNo())){
			cb.andEQ("channelNo", userSmsCondition.getChannelNo());
		}
		if(!Strings.isEmpty(userSmsCondition.getAgentNo())){
			cb.andEQ("agentNo", userSmsCondition.getAgentNo());
		}
		if(!Strings.isEmpty(userSmsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", userSmsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(userSmsCondition.getPhone())){
			cb.andEQ("phone", userSmsCondition.getPhone());
		}
		if(!Strings.isEmpty(userSmsCondition.getContent())){
			cb.andEQ("content", userSmsCondition.getContent());
		}
		if(!Strings.isEmpty(userSmsCondition.getSendResult())){
			cb.andEQ("sendResult", userSmsCondition.getSendResult());
		}
		if(null != userSmsCondition.getCreateTime()){
			cb.andEQ("createTime", userSmsCondition.getCreateTime());
		}
		if(!Strings.isEmpty(userSmsCondition.getBeginTimeStr())){
			cb.andGE("createTime", userSmsCondition.getBeginTimeStr());
		}
		if(!Strings.isEmpty(userSmsCondition.getEndTimeStr())){
			cb.andLE("createTime", userSmsCondition.getEndTimeStr()  + " 23:59:59");
		}
		if(!Strings.isEmpty(userSmsCondition.getRemark())){
			cb.andLike("remark", userSmsCondition.getRemark());
		}
		cb.orderBy("createTime", Order.DESC);
		if(Strings.isNotEmpty(userSmsCondition.getNodeSeq())){
			cb.addParam("nodeSeq", userSmsCondition.getNodeSeq());
			cb.addParam("identityFlag", userSmsCondition.getIdentityFlag());
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(userSmsCondition.getFirst()), Long.valueOf(userSmsCondition.getLast()));
		 PagingResult<UserSms> page  = userSmsDAO.findPagingResult(buildCriteria);
		for (UserSms userSms : page.getResult()) {			
			try {
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+userSms.getChannelNo()));
				if(channelBase != null){
					userSms.setChannelName(channelBase.getChannelName());
				}	
				AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+userSms.getAgentNo()));
				if(agentBase != null){
					userSms.setAgentName(agentBase.getAgentName());
				}
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+userSms.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					userSms.setMerchantName(merchantInfo.getMerchantName());
				}
				String str = userSms.getContent().substring(0, 6);
				StringBuffer buffer = new StringBuffer();
				buffer.append(str);
				buffer.append("******");
				String str2 = userSms.getContent().substring(userSms.getContent().length()-6, userSms.getContent().length());
				buffer.append(str2);
				userSms.setContent(buffer.toString());
				
				String phone = userSms.getPhone().substring(0,userSms.getPhone().length()-(userSms.getPhone().substring(3)).length())+"****"+userSms.getPhone().substring(7);
				userSms.setPhone(phone);
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
	 * @param userSmsCondition
	 * @return: List<UserSms>
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	@Override
	public List<UserSms> findAll(UserSmsCondition userSmsCondition){
		CriteriaBuilder cb = Cnd.builder(UserSms.class);
		if(!Strings.isEmpty(userSmsCondition.getId())){
			cb.andEQ("id", userSmsCondition.getId());
		}
		if(!Strings.isEmpty(userSmsCondition.getChannelNo())){
			cb.andEQ("channelNo", userSmsCondition.getChannelNo());
		}
		if(!Strings.isEmpty(userSmsCondition.getAgentNo())){
			cb.andEQ("agentNo", userSmsCondition.getAgentNo());
		}
		if(!Strings.isEmpty(userSmsCondition.getMerchantNo())){
			cb.andEQ("merchantNo", userSmsCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(userSmsCondition.getPhone())){
			cb.andEQ("phone", userSmsCondition.getPhone());
		}
		if(!Strings.isEmpty(userSmsCondition.getContent())){
			cb.andEQ("content", userSmsCondition.getContent());
		}
		if(!Strings.isEmpty(userSmsCondition.getSendResult())){
			cb.andEQ("sendResult", userSmsCondition.getSendResult());
		}
		if(null != userSmsCondition.getCreateTime()){
			cb.andEQ("createTime", userSmsCondition.getCreateTime());
		}
		if(!Strings.isEmpty(userSmsCondition.getBeginTimeStr())){
			cb.andGE("createTime", userSmsCondition.getBeginTimeStr());
		}
		if(!Strings.isEmpty(userSmsCondition.getEndTimeStr())){
			cb.andLE("createTime", userSmsCondition.getEndTimeStr()  + " 23:59:59");
		}
		if(!Strings.isEmpty(userSmsCondition.getRemark())){
			cb.andLike("remark", userSmsCondition.getRemark());
		}
		if(Strings.isNotEmpty(userSmsCondition.getNodeSeq())){
			cb.addParam("nodeSeq", userSmsCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return userSmsDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: UserSms
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	@Override
	public UserSms findById(String id){
		UserSms userSms = userSmsDAO.get(id);
		String str = userSms.getContent().substring(0, 6);
		StringBuffer buffer = new StringBuffer();
		buffer.append(str);
		buffer.append("******");
		String str2 = userSms.getContent().substring(userSms.getContent().length()-6, userSms.getContent().length());
		buffer.append(str2);
		userSms.setContent(buffer.toString());
		
		String phone = userSms.getPhone().substring(0,userSms.getPhone().length()-(userSms.getPhone().substring(3)).length())+"****"+userSms.getPhone().substring(7);
		userSms.setPhone(phone);
		return userSms;
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param userSmsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	@Override
	public long insert(UserSmsCondition userSmsCondition){
		UserSms userSms = new UserSms();
		BeanUtils.copyProperties(userSmsCondition, userSms);
		return userSmsDAO.insert(userSms);
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	@Override
	public long deleteById(String id){
		return userSmsDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return userSmsDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return userSmsDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param userSmsCondition
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	@Override
	public long update(UserSmsCondition userSmsCondition){
		UserSms userSms = new UserSms();
		BeanUtils.copyProperties(userSmsCondition, userSms);
		return userSmsDAO.update(userSms);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param userSmsCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-11-28 17:28:09
	 */
	@Override
	public long updateByCriteria(UserSmsCondition userSmsCondition,Criteria criteria){
		UserSms userSms = new UserSms();
		BeanUtils.copyProperties(userSmsCondition, userSms);
		return userSmsDAO.updateByCriteria(userSms,criteria);
	}
	
//	/** 商户发送短信并记录*/
//	@Override
//	public boolean sendSms(String content, String mobile,String merchantNo) throws Exception {
//		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantNo);
//		Boolean isSuccess = this.sendSms(content, mobile, merchantInfo.getChannelNo(), merchantInfo.getAgentNo(), merchantInfo.getMerchantNo());
//		return isSuccess;
//	}
	
//	/** 发送短信并记录*/
//	@Override
//	public boolean sendSms(String content, String mobile,String channelNo,String agentNo,String merchantNo) throws Exception{
//		String user= "";
//		String password = "";
//		String sendUrl = "";
//		
//		String organ = "";
//		if(Strings.isNotEmpty(merchantNo)){
//			organ = merchantNo;
//		}else if(Strings.isNotEmpty(agentNo)){
//			organ = agentNo;
//		}else if(Strings.isNotEmpty(channelNo)){
//			organ = channelNo;
//		}
//		
//		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(organ);
//		String organNo = "";
//		if (null != vo) {
//			organNo = vo.getOrganNo();
//		}else {					
//			organNo = channelNo;
//		}			
//		ParamsInfo paramsInfo = paramsInfoService.findParamsKey(organNo,ParamsType.PARAMS_SMS.getCode());
//		
//		if (null == paramsInfo) {
//			ParamsInfo paramsInfoInit = paramsInfoService.findParamsKey(ScanCodeConstants.INIT_SMS_SYSTEM,ParamsType.PARAMS_SMS.getCode());
//			JSONObject json = JSONObject.fromObject(paramsInfoInit.getParamsValue());
//			user= json.getString("smsuser");
//			password = json.getString("smspassword");
//			sendUrl = json.getString("sendUrl");
//		}else {
//			log.info("#########["+paramsInfo+"]########");
//			JSONObject json = JSONObject.fromObject(paramsInfo.getParamsValue());
//			user= json.getString("smsuser");
//			password = json.getString("smspassword");
//			sendUrl = json.getString("sendUrl");
//			if ("".equals(user) || "".equals(password) || "".equals(sendUrl)) {
//				log.info("#########user["+user+"],password["+password+"],sendUrl["+sendUrl+"]########");
//				ParamsInfo paramsInfoInit = paramsInfoService.findParamsKey(ScanCodeConstants.INIT_SMS_SYSTEM,ParamsType.PARAMS_SMS.getCode());
//				JSONObject jsonInit = JSONObject.fromObject(paramsInfoInit.getParamsValue());
//				user= jsonInit.getString("smsuser");
//				password = jsonInit.getString("smspassword");
//				sendUrl = jsonInit.getString("sendUrl");
//			}
//		}
//		log.info("#########user["+user+"],password["+password+"],sendUrl["+sendUrl+"]########");
//		
//		Boolean isSuccess = true;
//		isSuccess = smsService.sendSmsByCustom(content, mobile, user, password, sendUrl);//发送短信
//		UserSmsCondition userSmsCondition = new UserSmsCondition();
//		userSmsCondition.setChannelNo(channelNo);
//		userSmsCondition.setAgentNo(agentNo);
//		userSmsCondition.setMerchantNo(merchantNo);
//		userSmsCondition.setPhone(mobile);
//		userSmsCondition.setContent(content);
//		userSmsCondition.setSendResult(isSuccess == true ? "1":"0");
//		userSmsCondition.setCreateTime(new Date());
//		this.insert(userSmsCondition);//记录发送短信
//		return isSuccess;
//	}

//	@Override
//	public Map<String, Integer> getSmsCountDetail(UserSmsCondition userSmsCondition) {
//		Map<String, Integer> smsMap = new HashMap<String, Integer>();
//		smsMap.put("smsTotal", 0);
//		smsMap.put("smsFailTotal", 0);
//		smsMap.put("smsSuccessTotal", 0);
//		Integer total = 0;
//		List<UserSms> userSmsList = userSmsDAO.getSmsCountDetail(userSmsCondition);
//		for (UserSms userSms : userSmsList) {
//			int result = Integer.parseInt(userSms.getRemark());
//			total = total+result;
//			if("1".equals(userSms.getSendResult())){
//				smsMap.put("smsSuccessTotal", result);
//			}
//			if("0".equals(userSms.getSendResult())){
//				smsMap.put("smsFailTotal", result);
//			}
//		}
//		smsMap.put("smsTotal", total);
//		return smsMap;
//	}
}

