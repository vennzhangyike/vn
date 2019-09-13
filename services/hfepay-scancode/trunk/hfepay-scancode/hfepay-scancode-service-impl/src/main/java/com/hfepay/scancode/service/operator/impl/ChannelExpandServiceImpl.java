/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.math.BigDecimal;
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
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.AgreementInfoCondition;
import com.hfepay.scancode.commons.condition.ChannelBankcardCondition;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.ChannelWxParamsCondition;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.condition.OrganWalletCondition;
import com.hfepay.scancode.commons.condition.ParamsInfoCondition;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.ChannelExpandDAO;
import com.hfepay.scancode.commons.entity.AgreementInfo;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.ChannelWxParams;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.AgreementInfoService;
import com.hfepay.scancode.service.operator.ChannelBankcardService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.ChannelWxParamsService;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;
import com.hfepay.scancode.service.operator.NodeRelationService;
import com.hfepay.scancode.service.operator.OrganWalletService;
import com.hfepay.scancode.service.operator.ParamsInfoService;

import net.sf.json.JSONSerializer;

@Service("channelExpandService")
public class ChannelExpandServiceImpl implements ChannelExpandService {
	public static final Logger log = LoggerFactory.getLogger(ChannelExpandServiceImpl.class);
	@Autowired
    private ChannelExpandDAO channelExpandDAO;	
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private ChannelBankcardService channelBankcardService;
	@Autowired
	private NodeRelationService nodeRelationService;	
	@Autowired
	private ChannelWxParamsService channelWxParamsService;
	@Autowired
	private RedisSharedCache redisSharedCache;
	
	@Autowired
	private AgentBaseService agentBaseService;
	
	@Autowired
	private IdCreateService idCreateService;
	
	@Autowired
	private ParamsInfoService paramsInfoService;
	
	@Autowired
	private AgreementInfoService agreementInfoService;
	@Autowired
	private OrganWalletService organWalletService;
	@Autowired
	private MerchantPaywayService merchantPaywayService;
    
    /**
	 * @Title: findPagingResult
	 * @Description: 列表(分页)
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @return: PagingResult<ChannelExpand>
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
    @Override
	public PagingResult<ChannelExpand> findPagingResult(ChannelExpandCondition channelExpandCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelExpand.class);
		if(!Strings.isEmpty(channelExpandCondition.getId())){
			cb.andEQ("id", channelExpandCondition.getId());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelNo())){
			cb.andEQ("channelNo", channelExpandCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelName())){
			cb.andLike("channelName", channelExpandCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelCode())){
			cb.andEQ("channelCode", channelExpandCondition.getChannelCode());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelPreCode())){
			cb.andEQ("channelPreCode", channelExpandCondition.getChannelPreCode());
		}
		if(!Strings.isEmpty(channelExpandCondition.getNickName())){
			cb.andEQ("nickName", channelExpandCondition.getNickName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIndexTopImg())){
			cb.andEQ("indexTopImg", channelExpandCondition.getIndexTopImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIndexBottomImg())){
			cb.andEQ("indexBottomImg", channelExpandCondition.getIndexBottomImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIndexWxImg())){
			cb.andEQ("indexWxImg", channelExpandCondition.getIndexWxImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getAddress())){
			cb.andEQ("address", channelExpandCondition.getAddress());
		}
		if(!Strings.isEmpty(channelExpandCondition.getPhone())){
			cb.andEQ("phone", channelExpandCondition.getPhone());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTechnicalSupportEmail())){
			cb.andEQ("technicalSupportEmail", channelExpandCondition.getTechnicalSupportEmail());
		}
		if(!Strings.isEmpty(channelExpandCondition.getQqGroup())){
			cb.andEQ("qqGroup", channelExpandCondition.getQqGroup());
		}
		if(!Strings.isEmpty(channelExpandCondition.getCustomServiceQq())){
			cb.andEQ("customServiceQq", channelExpandCondition.getCustomServiceQq());
		}
		if(!Strings.isEmpty(channelExpandCondition.getBusinessCooperationQq())){
			cb.andEQ("businessCooperationQq", channelExpandCondition.getBusinessCooperationQq());
		}
		if(!Strings.isEmpty(channelExpandCondition.getBusinessCooperationEmail())){
			cb.andEQ("businessCooperationEmail", channelExpandCondition.getBusinessCooperationEmail());
		}
		if(!Strings.isEmpty(channelExpandCondition.getMicroblogUrl())){
			cb.andEQ("microblogUrl", channelExpandCondition.getMicroblogUrl());
		}
		if(!Strings.isEmpty(channelExpandCondition.getContactAddressImg())){
			cb.andEQ("contactAddressImg", channelExpandCondition.getContactAddressImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getLoginBackgroundImg())){
			cb.andEQ("loginBackgroundImg", channelExpandCondition.getLoginBackgroundImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getCenterHeadImg())){
			cb.andEQ("centerHeadImg", channelExpandCondition.getCenterHeadImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getCompanyName())){
			cb.andEQ("companyName", channelExpandCondition.getCompanyName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getPreCode())){
			cb.andEQ("preCode", channelExpandCondition.getPreCode());
		}
		if(!Strings.isEmpty(channelExpandCondition.getRecordNumber())){
			cb.andEQ("recordNumber", channelExpandCondition.getRecordNumber());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIcon())){
			cb.andEQ("icon", channelExpandCondition.getIcon());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTitle())){
			cb.andEQ("title", channelExpandCondition.getTitle());
		}
		if(!Strings.isEmpty(channelExpandCondition.getKeyWords())){
			cb.andEQ("keyWords", channelExpandCondition.getKeyWords());
		}
		if(!Strings.isEmpty(channelExpandCondition.getDescription())){
			cb.andEQ("description", channelExpandCondition.getDescription());
		}
		if(!Strings.isEmpty(channelExpandCondition.getDomainName())){
			cb.andEQ("domainName", channelExpandCondition.getDomainName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTelephone())){
			cb.andEQ("telephone", channelExpandCondition.getTelephone());
		}
		if(!Strings.isEmpty(channelExpandCondition.getAboutUs())){
			cb.andEQ("aboutUs", channelExpandCondition.getAboutUs());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIsActive())){
			cb.andEQ("isActive", channelExpandCondition.getIsActive());
		}
		if(!Strings.isEmpty(channelExpandCondition.getStatus())){
			cb.andEQ("status", channelExpandCondition.getStatus());
		}
		if(null != channelExpandCondition.getCreateTime()){
			cb.andEQ("createTime", channelExpandCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelExpandCondition.getOperator())){
			cb.andEQ("operator", channelExpandCondition.getOperator());
		}

		if(!Strings.isEmpty(channelExpandCondition.getRemark())){
			cb.andLike("remark", channelExpandCondition.getRemark());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTemp1())){
			cb.andEQ("temp1", channelExpandCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTemp2())){
			cb.andEQ("temp2", channelExpandCondition.getTemp2());
		}
		cb.orderBy("createTime", Order.DESC);
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(channelExpandCondition.getFirst()), Long.valueOf(channelExpandCondition.getLast()));
		return channelExpandDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @return: List<ChannelExpand>
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	public List<ChannelExpand> findAll(ChannelExpandCondition channelExpandCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelExpand.class);
		if(!Strings.isEmpty(channelExpandCondition.getId())){
			cb.andEQ("id", channelExpandCondition.getId());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelNo())){
			cb.andEQ("channelNo", channelExpandCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelName())){
			cb.andLike("channelName", channelExpandCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelCode())){
			cb.andEQ("channelCode", channelExpandCondition.getChannelCode());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelPreCode())){
			cb.andEQ("channelPreCode", channelExpandCondition.getChannelPreCode());
		}
		if(!Strings.isEmpty(channelExpandCondition.getNickName())){
			cb.andEQ("nickName", channelExpandCondition.getNickName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIndexTopImg())){
			cb.andEQ("indexTopImg", channelExpandCondition.getIndexTopImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIndexBottomImg())){
			cb.andEQ("indexBottomImg", channelExpandCondition.getIndexBottomImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIndexWxImg())){
			cb.andEQ("indexWxImg", channelExpandCondition.getIndexWxImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getAddress())){
			cb.andEQ("address", channelExpandCondition.getAddress());
		}
		if(!Strings.isEmpty(channelExpandCondition.getPhone())){
			cb.andEQ("phone", channelExpandCondition.getPhone());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTechnicalSupportEmail())){
			cb.andEQ("technicalSupportEmail", channelExpandCondition.getTechnicalSupportEmail());
		}
		if(!Strings.isEmpty(channelExpandCondition.getQqGroup())){
			cb.andEQ("qqGroup", channelExpandCondition.getQqGroup());
		}
		if(!Strings.isEmpty(channelExpandCondition.getCustomServiceQq())){
			cb.andEQ("customServiceQq", channelExpandCondition.getCustomServiceQq());
		}
		if(!Strings.isEmpty(channelExpandCondition.getBusinessCooperationQq())){
			cb.andEQ("businessCooperationQq", channelExpandCondition.getBusinessCooperationQq());
		}
		if(!Strings.isEmpty(channelExpandCondition.getBusinessCooperationEmail())){
			cb.andEQ("businessCooperationEmail", channelExpandCondition.getBusinessCooperationEmail());
		}
		if(!Strings.isEmpty(channelExpandCondition.getMicroblogUrl())){
			cb.andEQ("microblogUrl", channelExpandCondition.getMicroblogUrl());
		}
		if(!Strings.isEmpty(channelExpandCondition.getContactAddressImg())){
			cb.andEQ("contactAddressImg", channelExpandCondition.getContactAddressImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getLoginBackgroundImg())){
			cb.andEQ("loginBackgroundImg", channelExpandCondition.getLoginBackgroundImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getCenterHeadImg())){
			cb.andEQ("centerHeadImg", channelExpandCondition.getCenterHeadImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getCompanyName())){
			cb.andEQ("companyName", channelExpandCondition.getCompanyName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getPreCode())){
			cb.andEQ("preCode", channelExpandCondition.getPreCode());
		}
		if(!Strings.isEmpty(channelExpandCondition.getRecordNumber())){
			cb.andEQ("recordNumber", channelExpandCondition.getRecordNumber());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIcon())){
			cb.andEQ("icon", channelExpandCondition.getIcon());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTitle())){
			cb.andEQ("title", channelExpandCondition.getTitle());
		}
		if(!Strings.isEmpty(channelExpandCondition.getKeyWords())){
			cb.andEQ("keyWords", channelExpandCondition.getKeyWords());
		}
		if(!Strings.isEmpty(channelExpandCondition.getDescription())){
			cb.andEQ("description", channelExpandCondition.getDescription());
		}
		if(!Strings.isEmpty(channelExpandCondition.getDomainName())){
			cb.andEQ("domainName", channelExpandCondition.getDomainName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTelephone())){
			cb.andEQ("telephone", channelExpandCondition.getTelephone());
		}
		if(!Strings.isEmpty(channelExpandCondition.getAboutUs())){
			cb.andEQ("aboutUs", channelExpandCondition.getAboutUs());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIsActive())){
			cb.andEQ("isActive", channelExpandCondition.getIsActive());
		}
		if(!Strings.isEmpty(channelExpandCondition.getStatus())){
			cb.andEQ("status", channelExpandCondition.getStatus());
		}
		if(null != channelExpandCondition.getCreateTime()){
			cb.andEQ("createTime", channelExpandCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelExpandCondition.getOperator())){
			cb.andEQ("operator", channelExpandCondition.getOperator());
		}

		if(!Strings.isEmpty(channelExpandCondition.getRemark())){
			cb.andLike("remark", channelExpandCondition.getRemark());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTemp1())){
			cb.andEQ("temp1", channelExpandCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTemp2())){
			cb.andEQ("temp2", channelExpandCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelExpandDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findById
	 * @Description: 主键查找
	 * @author: Ricky
	 * @param id
	 * @return: ChannelExpand
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	public ChannelExpand findById(String id){
		return channelExpandDAO.get(id);
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	public long insert(ChannelExpandCondition channelExpandCondition){
		ChannelExpand channelExpand = new ChannelExpand();		
		BeanUtils.copyProperties(channelExpandCondition, channelExpand);
		
		try {
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE_EX.getGroup(), RedisKeyEnum.CHANNEL_BASE_EX.getKey()+channelExpandCondition.getChannelNo()), channelExpandCondition);
		} catch (Exception e) {
			log.error("#######保存渠道数据到redis失败######");
		}
		
		return channelExpandDAO.insert(channelExpand);
	}
	
	/**@Override
	 * @Title: save
	 * @Description: 保存
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @return: 
	 * @throws Exception 
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	@Transactional
	public void save(ChannelExpandCondition channelExpandCondition) throws Exception{
		log.info("保存渠道信息："+channelExpandCondition);
		Map<String, String> map = new HashMap<String, String>();
		map.put("appid", channelExpandCondition.getAppid());
		map.put("secret", channelExpandCondition.getSecret());
		map.put("registerTemplateId", channelExpandCondition.getRegisterTemplateId());
		map.put("payTemplateId", channelExpandCondition.getPayTemplateId());
		map.put("withDrawsTemplateId", channelExpandCondition.getWithDrawsTemplateId());
		map.put("unperfectTemplateId", channelExpandCondition.getUnperfectTemplateId());
		String wxParams = JSONSerializer.toJSON(map).toString();
		
		//短信参数
		Map<String, String> smsMap = new HashMap<String, String>();
		smsMap.put("smsuser", channelExpandCondition.getSmsuser());
		smsMap.put("smspassword", channelExpandCondition.getSmspassword());
		smsMap.put("sendUrl", channelExpandCondition.getSendUrl());
		String smsParams = JSONSerializer.toJSON(smsMap).toString();
		
		if(Strings.isEmpty(channelExpandCondition.getId())){			
			channelExpandCondition.setChannelNo(this.nextCode());
			channelExpandCondition.setIsActive(ScanCodeConstants.Y);
			channelExpandCondition.setCreateTime(new Date());
			channelExpandCondition.setStatus(ScanCodeConstants.STATUS_ACTIVE);
			channelExpandCondition.setTemp1(channelExpandCondition.getChannelNo());
			//新增
			this.insert(channelExpandCondition);
			channelExpandCondition.setTemp1(null);
			ChannelBaseCondition channelBaseCondition = new ChannelBaseCondition();
			BeanUtils.copyProperties(channelExpandCondition, channelBaseCondition);
			channelBaseCondition.setRecordStatus(ScanCodeConstants.Y);
			channelBaseCondition.setQrTotal(0);
			channelBaseCondition.setUseTotal(0);
			channelBaseCondition.setLessTotal(0);
			channelBaseService.insert(channelBaseCondition);
			
			//插入银行账户信息
			ChannelBankcardCondition channelBankcardCondition = new ChannelBankcardCondition();
			BeanUtils.copyProperties(channelExpandCondition, channelBankcardCondition);
			channelBankcardService.insert(channelBankcardCondition);
			
			//保存渠道，存放一条关联信息到节点表。只有一条
			NodeRelationCondition dCondition = new NodeRelationCondition();
			dCondition.setIdentityFlag(ScanCodeConstants.IDENTITYFLAG_CHANNEL);//1渠道2代理商3商户：必需参数
			dCondition.setId(Strings.getUUID());
			dCondition.setParentNode("1");//渠道父节点平台为1
			dCondition.setCurrentNode(channelExpandCondition.getChannelNo());//当前节点id//必须参数
			dCondition.setCurrentNodeLevel("0");//当前节点级别：渠道是0级别
			dCondition.setOperator(channelExpandCondition.getOperator());//操作人				
			nodeRelationService.doSaveNodeRelations(dCondition);
			
			//插入公众号信息
			ChannelWxParamsCondition channelWxParamsCondition = new ChannelWxParamsCondition();
			BeanUtils.copyProperties(channelExpandCondition, channelWxParamsCondition);
			channelWxParamsCondition.setOrganNo(channelExpandCondition.getChannelNo());//添加标示符
			channelWxParamsCondition.setRecordStatus(ScanCodeConstants.Y);
			channelWxParamsCondition.setWxParams(wxParams);
			channelWxParamsService.insert(channelWxParamsCondition);
			ChannelWxParams params = channelWxParamsService.findByChannelNo(channelExpandCondition.getChannelNo());
			redisSharedCache.setObj(new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,HfepayConfig.CHANNEL_REDIS_WX_KEY+ channelExpandCondition.getChannelNo()), params);

			//保存短信信息
			saveSms(smsParams, channelBaseCondition);
			
			//初始化一个默认代理商
			saveAgentInfo(channelExpandCondition);
			
			//保存电子协议信息
			saveAgreementInfo(channelExpandCondition);
			
			//新增代理商钱包
			OrganWalletCondition organWalletCondition = new OrganWalletCondition();
			organWalletCondition.setId(Strings.getUUID());
			organWalletCondition.setOrganNo(channelExpandCondition.getChannelNo());//机构编号
			organWalletCondition.setBalance(new BigDecimal(0));//可用余额
			organWalletCondition.setFreezesAmt(new BigDecimal(0));//冻结余额
			organWalletCondition.setStatus("1");//是否有效：1：有效，2：无效
			organWalletCondition.setCreateTime(new Date());//绑定时间
			organWalletCondition.setOperator(channelExpandCondition.getOperator());//操作人账号
			organWalletCondition.setRemark("");//备注
			organWalletCondition.setTemp1("");//备用字段
			organWalletCondition.setTemp2("");//备用字段
			organWalletService.insert(organWalletCondition);

			//新增一条默认的清算手续费记录
			merchantPaywayService.addLiquidationFee(channelExpandCondition.getChannelCode(),"0");
			
		}else{
			this.update(channelExpandCondition);
			ChannelBaseCondition channelBaseCondition = new ChannelBaseCondition();	
			ChannelBase entity = channelBaseService.findByChannelNo(channelExpandCondition.getChannelNo());
			if(entity != null){
				BeanUtils.copyProperties(channelExpandCondition, channelBaseCondition);
				channelBaseCondition.setId(entity.getId());
				channelBaseService.update(channelBaseCondition);
			}
			ChannelWxParamsCondition channelWxParamsCondition = new ChannelWxParamsCondition();
			ChannelWxParams channelWxParams = channelWxParamsService.findByChannelNo(channelExpandCondition.getChannelNo());
			if(channelWxParams != null){
				BeanUtils.copyProperties(channelExpandCondition, channelWxParamsCondition);
				channelWxParamsCondition.setId(channelWxParams.getId());
				channelWxParamsCondition.setWxParams(wxParams);
				channelWxParamsService.update(channelWxParamsCondition);
				channelWxParams.setWxParams(wxParams);
				redisSharedCache.del(new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,HfepayConfig.CHANNEL_REDIS_WX_KEY+ channelExpandCondition.getChannelNo()));
				redisSharedCache.setObj(new RedisKey(HfepayConfig.CHANNEL_REDIS_FAMILY,HfepayConfig.CHANNEL_REDIS_WX_KEY+ channelExpandCondition.getChannelNo()), channelWxParams);
			}
			
			//更新短信信息
			ParamsInfo paramsInfo = paramsInfoService.findParamsKey(channelExpandCondition.getChannelNo(),ParamsType.PARAMS_SMS.getCode());
			if (null != paramsInfo) {
				ParamsInfoCondition paramsInfoCondition = new ParamsInfoCondition();
				paramsInfoCondition.setParamsValue(smsParams);
				paramsInfoCondition.setId(paramsInfo.getId());
				paramsInfoService.update(paramsInfoCondition);
			}else {
				saveSms(smsParams, channelBaseCondition);
			}
			
			AgreementInfoCondition agreementInfoCondition = new AgreementInfoCondition();
			agreementInfoCondition.setOrganNo(channelExpandCondition.getChannelNo());
			List<AgreementInfo> agreementList = agreementInfoService.findAll(agreementInfoCondition);
			if(agreementList != null && agreementList.size() > 0){
				agreementInfoCondition.setAgreementcontent(channelExpandCondition.getAgreement());
				agreementInfoService.updateByCriteria(agreementInfoCondition);
			}else{
				agreementInfoCondition.setAgreementNo(idCreateService.createParamNo(""));
				agreementInfoCondition.setId(Strings.getUUID());
				agreementInfoCondition.setAgreementtype("0");
				agreementInfoCondition.setAgreementcontent(channelExpandCondition.getAgreement());
				agreementInfoCondition.setStatus("1");
				agreementInfoCondition.setCreateTime(new Date());
				agreementInfoCondition.setOperater(channelExpandCondition.getOperator());
				agreementInfoService.insert(agreementInfoCondition);
			}
			
		}
		log.info("保存渠道信息结束。");
	}

	/**
	 * 保存短信信息
	 * @param smsParams
	 * @param channelBaseCondition
	 */
	private void saveSms(String smsParams, ChannelBaseCondition channelBaseCondition) {
		ParamsInfoCondition paramsInfoCondition = new ParamsInfoCondition();
		paramsInfoCondition.setParamsKey(channelBaseCondition.getChannelNo());
		paramsInfoCondition.setParamsType(ParamsType.PARAMS_SMS.getCode());
		paramsInfoCondition.setParamsValue(smsParams);
		paramsInfoService.insert(paramsInfoCondition);
	}

	/**
	 * 保存默认代理商信息
	 * @param channelExpandCondition
	 */
	private void saveAgentInfo(ChannelExpandCondition channelExpandCondition) {
		AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
		agentBaseCondition.setChannelNo(channelExpandCondition.getChannelNo());
		agentBaseCondition.setId(Strings.getUUID());
		agentBaseCondition.setAgentNo(idCreateService.createParamNo(channelExpandCondition.getChannelPreCode()));
		agentBaseCondition.setAgentName(channelExpandCondition.getChannelName()+"默认代理");
		agentBaseCondition.setAgentLevel("1");
		agentBaseCondition.setAgentType("1");	//代理商类型默认为机构
		agentBaseCondition.setAgentPreCode(channelExpandCondition.getChannelPreCode()+"DL");
		agentBaseCondition.setName(channelExpandCondition.getName());
		agentBaseCondition.setMobile(channelExpandCondition.getMobile());
		agentBaseCondition.setParentNo(channelExpandCondition.getChannelNo());
		agentBaseCondition.setQrTotal(0);
		agentBaseCondition.setUseTotal(0);
		agentBaseCondition.setLessTotal(0);
		agentBaseCondition.setCreateTime(new Date());
		agentBaseCondition.setStatus(ScanCodeConstants.STATUS_ACTIVE);
		agentBaseCondition.setRecordStatus(ScanCodeConstants.Y);
		agentBaseCondition.setOperator(channelExpandCondition.getOperator());
		agentBaseCondition.setAgentFlag("0");
		agentBaseService.saveAgentBaseAndNode(agentBaseCondition);
	}
	
	/**
	 * 保存电子协议信息
	 * @param channelExpandCondition
	 */
	private void saveAgreementInfo(ChannelExpandCondition channelExpandCondition) {
		AgreementInfoCondition agreementInfoCondition = new AgreementInfoCondition();
		agreementInfoCondition.setAgreementNo(idCreateService.createParamNo(""));
		agreementInfoCondition.setId(Strings.getUUID());
		agreementInfoCondition.setOrganNo(channelExpandCondition.getChannelNo());
		agreementInfoCondition.setAgreementtype("0");
		agreementInfoCondition.setAgreementcontent(channelExpandCondition.getAgreement());
		agreementInfoCondition.setStatus("1");
		agreementInfoCondition.setCreateTime(new Date());
		agreementInfoCondition.setOperater(channelExpandCondition.getOperator());
		agreementInfoService.insert(agreementInfoCondition);
	}
	
	/**
	 * @Title: nextCode
	 * @Description: 生成渠道编码
	 * @author: Ricky
	 * @return: String
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	public String nextCode() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("namePre", ScanCodeConstants.CHANNEL_CODE_PRE_NAME);
		map.put("newNo", "");
		channelExpandDAO.getChannelCode(map);
		return map.get("newNo");
	}

	/**
	 * @Title: deleteById
	 * @Description: 删除
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	public long deleteById(String id){
		return channelExpandDAO.deleteById(id);
	}
	
	/**
	 * @Title: deleteByCriteria
	 * @Description: 条件删除
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return channelExpandDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * @Title: countByCriteria
	 * @Description: 行数汇总
	 * @author: Ricky
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return channelExpandDAO.countByCriteria(criteria);
	}
	
	/**
	 * @Title: update
	 * @Description: 更新
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	public long update(ChannelExpandCondition channelExpandCondition){
		
		try {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.CHANNEL_BASE_EX.getGroup(), RedisKeyEnum.CHANNEL_BASE_EX.getKey()+channelExpandCondition.getChannelNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE_EX.getGroup(), RedisKeyEnum.CHANNEL_BASE_EX.getKey()+channelExpandCondition.getChannelNo()), channelExpandCondition);
		} catch (Exception e) {
			log.error("#######保存渠道数据到redis失败######");
		}
		
		ChannelExpand channelExpand = new ChannelExpand();
		BeanUtils.copyProperties(channelExpandCondition, channelExpand);
		channelExpand.setUpdateTime(new Date());
		return channelExpandDAO.update(channelExpand);
	}
	
	/**
	 * @Title: updateByCriteria
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param channelExpandCondition
	 * @param Criteria
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	public long updateByCriteria(ChannelExpandCondition channelExpandCondition,Criteria criteria){
		ChannelExpand channelExpand = new ChannelExpand();
		BeanUtils.copyProperties(channelExpandCondition, channelExpand);
		return channelExpandDAO.updateByCriteria(channelExpand,criteria);
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态更新
	 * @author: Ricky
	 * @param id
	 * @return: long
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	@Override
	public long updateStatus(String id,String status){
		return channelExpandDAO.updateStatus(id,status);
	}	
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @author: Ricky
	 * @param channelNo
	 * @return: ChannelExpand
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	public ChannelExpand findByChannelNo(String channelNo){
		if(Strings.isEmpty(channelNo)){
			throw new RuntimeException("渠道编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(ChannelExpand.class);
		cb.andEQ("channelNo", channelNo);
		cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
		Criteria buildCriteria = cb.buildCriteria();
		return channelExpandDAO.findOneByCriteria(buildCriteria);
	}
	
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @author: Ricky
	 * @param channelNo
	 * @return: ChannelExpand
	 * @date CreateDate : 2016-10-13 15:29:52
	 */
	public ChannelExpand findByChannelCode(String channelCode){
		CriteriaBuilder cb = Cnd.builder(ChannelExpand.class);
		cb.andEQ("channelCode", channelCode);
		cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
		Criteria buildCriteria = cb.buildCriteria();
		return channelExpandDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public ChannelExpand findByCondition(ChannelExpandCondition channelExpandCondition) {
		CriteriaBuilder cb = Cnd.builder(ChannelExpand.class);
		if(!Strings.isEmpty(channelExpandCondition.getId())){
			cb.andEQ("id", channelExpandCondition.getId());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelNo())){
			cb.andEQ("channelNo", channelExpandCondition.getChannelNo());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelName())){
			cb.andLike("channelName", channelExpandCondition.getChannelName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelCode())){
			cb.andEQ("channelCode", channelExpandCondition.getChannelCode());
		}
		if(!Strings.isEmpty(channelExpandCondition.getChannelPreCode())){
			cb.andEQ("channelPreCode", channelExpandCondition.getChannelPreCode());
		}
		if(!Strings.isEmpty(channelExpandCondition.getNickName())){
			cb.andEQ("nickName", channelExpandCondition.getNickName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIndexTopImg())){
			cb.andEQ("indexTopImg", channelExpandCondition.getIndexTopImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIndexBottomImg())){
			cb.andEQ("indexBottomImg", channelExpandCondition.getIndexBottomImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIndexWxImg())){
			cb.andEQ("indexWxImg", channelExpandCondition.getIndexWxImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getAddress())){
			cb.andEQ("address", channelExpandCondition.getAddress());
		}
		if(!Strings.isEmpty(channelExpandCondition.getPhone())){
			cb.andEQ("phone", channelExpandCondition.getPhone());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTechnicalSupportEmail())){
			cb.andEQ("technicalSupportEmail", channelExpandCondition.getTechnicalSupportEmail());
		}
		if(!Strings.isEmpty(channelExpandCondition.getQqGroup())){
			cb.andEQ("qqGroup", channelExpandCondition.getQqGroup());
		}
		if(!Strings.isEmpty(channelExpandCondition.getCustomServiceQq())){
			cb.andEQ("customServiceQq", channelExpandCondition.getCustomServiceQq());
		}
		if(!Strings.isEmpty(channelExpandCondition.getBusinessCooperationQq())){
			cb.andEQ("businessCooperationQq", channelExpandCondition.getBusinessCooperationQq());
		}
		if(!Strings.isEmpty(channelExpandCondition.getBusinessCooperationEmail())){
			cb.andEQ("businessCooperationEmail", channelExpandCondition.getBusinessCooperationEmail());
		}
		if(!Strings.isEmpty(channelExpandCondition.getMicroblogUrl())){
			cb.andEQ("microblogUrl", channelExpandCondition.getMicroblogUrl());
		}
		if(!Strings.isEmpty(channelExpandCondition.getContactAddressImg())){
			cb.andEQ("contactAddressImg", channelExpandCondition.getContactAddressImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getLoginBackgroundImg())){
			cb.andEQ("loginBackgroundImg", channelExpandCondition.getLoginBackgroundImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getCenterHeadImg())){
			cb.andEQ("centerHeadImg", channelExpandCondition.getCenterHeadImg());
		}
		if(!Strings.isEmpty(channelExpandCondition.getCompanyName())){
			cb.andEQ("companyName", channelExpandCondition.getCompanyName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getPreCode())){
			cb.andEQ("preCode", channelExpandCondition.getPreCode());
		}
		if(!Strings.isEmpty(channelExpandCondition.getRecordNumber())){
			cb.andEQ("recordNumber", channelExpandCondition.getRecordNumber());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIcon())){
			cb.andEQ("icon", channelExpandCondition.getIcon());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTitle())){
			cb.andEQ("title", channelExpandCondition.getTitle());
		}
		if(!Strings.isEmpty(channelExpandCondition.getKeyWords())){
			cb.andEQ("keyWords", channelExpandCondition.getKeyWords());
		}
		if(!Strings.isEmpty(channelExpandCondition.getDescription())){
			cb.andEQ("description", channelExpandCondition.getDescription());
		}
		if(!Strings.isEmpty(channelExpandCondition.getDomainName())){
			cb.andLike("domainName", channelExpandCondition.getDomainName());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTelephone())){
			cb.andEQ("telephone", channelExpandCondition.getTelephone());
		}
		if(!Strings.isEmpty(channelExpandCondition.getAboutUs())){
			cb.andEQ("aboutUs", channelExpandCondition.getAboutUs());
		}
		if(!Strings.isEmpty(channelExpandCondition.getIsActive())){
			cb.andEQ("isActive", channelExpandCondition.getIsActive());
		}
		if(!Strings.isEmpty(channelExpandCondition.getStatus())){
			cb.andEQ("status", channelExpandCondition.getStatus());
		}
		if(null != channelExpandCondition.getCreateTime()){
			cb.andEQ("createTime", channelExpandCondition.getCreateTime());
		}

		if(!Strings.isEmpty(channelExpandCondition.getOperator())){
			cb.andEQ("operator", channelExpandCondition.getOperator());
		}

		if(!Strings.isEmpty(channelExpandCondition.getRemark())){
			cb.andLike("remark", channelExpandCondition.getRemark());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTemp1())){
			cb.andEQ("temp1", channelExpandCondition.getTemp1());
		}
		if(!Strings.isEmpty(channelExpandCondition.getTemp2())){
			cb.andEQ("temp2", channelExpandCondition.getTemp2());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelExpandDAO.findOneByCriteria(buildCriteria);
	}
	
	/**设置渠道redis
	 * @throws Exception */
	@Override
	public void setChannelRedis() throws Exception{
		List<ChannelBase> list = channelBaseService.findAll(new ChannelBaseCondition());
		for (ChannelBase channelBase : list) {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+channelBase.getChannelNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+channelBase.getChannelNo()), channelBase);
		}
	}
}

