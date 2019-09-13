package com.hfepay.scancode.service.channel.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.HttpRequestClient;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.condition.ApiMappingDicionCondition;
import com.hfepay.scancode.api.entity.ApiMappingDicion;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.api.service.ApiMappingDicionService;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.condition.WechatUserCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.QrStatus;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.contants.UseStatus;
import com.hfepay.scancode.commons.dao.channel.ChannelAdminDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.ChannelRole;
import com.hfepay.scancode.commons.entity.ChannelRoleUser;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.NodeRelation;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.commons.entity.WechatUser;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.channel.ChannelRoleUserService;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.contants.ConfigPreCode;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.NodeRelationService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;
import com.hfepay.scancode.service.operator.UserSmsService;
import com.hfepay.scancode.service.operator.WechatUserService;
import com.hfepay.scancode.service.operator.impl.MerchantQrcodeServiceImpl;
import com.hfepay.scancode.service.utils.PasswordHelper;
import com.hfepay.scancode.service.utils.StringUtils;

import net.sf.json.JSONObject;

/**
 * 账号管理类
* @author ssd
* @date 2015-4-30 下午4:21:17
 */
@Service
public class ChannelAdminServiceImpl implements ChannelAdminService{
	public static final Logger logger = LoggerFactory.getLogger(MerchantQrcodeServiceImpl.class);
	@Autowired
    private ChannelAdminDAO channelAdminDAO;
	
	@Autowired
	private ChannelRoleUserService channelRoleUserService;
	
	@Autowired
	private UserSmsService userSmsService;
	
	@Autowired
	private RedisSharedCache redisSharedCache;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	
	@Autowired
	private NodeRelationService nodeRelationService;
	
	@Autowired
	private IdCreateService idCreateService;
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private AgentBaseService agentBaseService;
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private ApiMappingDicionService apiMappingDicionService;
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     * @throws Exception 
     */
    public ChannelAdmin findByUsername(String username,String channelCode) {
		try {
			CriteriaBuilder cb = Cnd.builder(ChannelAdmin.class);
			cb.andEQ("userName", username);
			if(Strings.isNotEmpty(channelCode)){
				cb.andEQ("channelCode", channelCode);
			}
			Criteria buildCriteria = cb.buildCriteria();
			ChannelAdmin info = channelAdminDAO.findOneByCriteria(buildCriteria);
			if(info!=null){
				ChannelAdminVo vo = new ChannelAdminVo();
				BeanUtils.copyProperties(info, vo);
				NodeRelation node = nodeRelationService.findByCurrentNode(info.getIdentityNo());
				vo.setNodeSeq(node.getNodeSeq());//将当前节点的seq放入session中
				return vo;
			}
			
			//ChannelAdmin info = adminMapper.findByUsername(username);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("findByUsername error。。。"+e);
		}
		return null;
    }
    
    /**
     * 根据用户名查找用户
     * @param username
     * @return
     * @throws Exception 
     */
    public ChannelAdmin findByUsernameAndAgentNo(String username,String agentNo) {
		try {
			CriteriaBuilder cb = Cnd.builder(ChannelAdmin.class);
			cb.andEQ("userName", username);
			if(Strings.isNotEmpty(agentNo)){
				cb.andEQ("agentNo", agentNo);
			}
			Criteria buildCriteria = cb.buildCriteria();
			ChannelAdmin info = channelAdminDAO.findOneByCriteria(buildCriteria);
			//ChannelAdmin info = adminMapper.findByUsername(username);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("findByUsername error。。。"+e);
		}
		return null;
    }
    
    
    /**
     * 根据渠道编号查询渠道信息
     * @param channelNo
     * @return
     * @throws Exception 
     */
    public ChannelBase findByChannelBaseChannelNo(String channelNo) {
		try {
			return channelBaseService.findByChannelNo(channelNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("findByUsername error。。。"+e);
		}
		return null;
    }
    
    /**
     * 根据代理商编号查询代理商信息
     * @param channelNo
     * @return
     * @throws Exception 
     */
    public AgentBase findByAgentBaseAgentNo(String agentNo) {
		try {
			AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
			agentBaseCondition.setAgentNo(agentNo);
			return agentBaseService.findAll(agentBaseCondition).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("findByUsername error。。。"+e);
		}
		return null;
    }
    
    /**
     * 根据商户编号查询商户信息
     * @param merchantNo
     * @return
     * @throws Exception 
     */
    public MerchantInfo findByMerchantInfoMerchantNo(String merchantNo) {
		try {
			return merchantInfoService.findByMerchantNo(merchantNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("findByUsername error。。。"+e);
		}
		return null;
    }
    
    /**
	 * 列表(分页)
	 * @param SysAdminCondition 
	 * @param roleLevel  角色类型
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
    @Override
	public PagingResult<ChannelAdmin> findPagingResult(ChannelAdminCondition SysAdminCondition,String roleLevel){
		CriteriaBuilder cb = Cnd.builder(ChannelAdmin.class);
		if(!Strings.isEmpty(SysAdminCondition.getId())){
			cb.andEQ("id", SysAdminCondition.getId());
		}
		if(!Strings.isEmpty(SysAdminCondition.getUserName())){
			cb.andLike("userName", SysAdminCondition.getUserName());
		}else{
			cb.andIsNotNull("userName");
		}
		if(!Strings.isEmpty(SysAdminCondition.getPassword())){
			cb.andEQ("password", SysAdminCondition.getPassword());
		}
		if(!Strings.isEmpty(SysAdminCondition.getSalt())){
			cb.andEQ("salt", SysAdminCondition.getSalt());
		}
		if(!Strings.isEmpty(SysAdminCondition.getEmail())){
			cb.andEQ("email", SysAdminCondition.getEmail());
		}
		if(!Strings.isEmpty(SysAdminCondition.getPhone())){
			cb.andLike("phone", SysAdminCondition.getPhone());
		}
		if(!Strings.isEmpty(SysAdminCondition.getShortName())){
			cb.andLike("shortName", SysAdminCondition.getShortName());
		}
		if(!Strings.isEmpty(SysAdminCondition.getOpenId())){
			cb.andEQ("openId", SysAdminCondition.getOpenId());
		}
		if(!Strings.isEmpty(SysAdminCondition.getIdentityFlag())){
			cb.andEQ("identityFlag", SysAdminCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(SysAdminCondition.getIdentityNo())){
			cb.andEQ("identityNo", SysAdminCondition.getIdentityNo());
		}
		if(!Strings.isEmpty(SysAdminCondition.getChannelCode())){
			cb.andEQ("channelCode", SysAdminCondition.getChannelCode());
		}
		if(!Strings.isEmpty(SysAdminCondition.getAgentNo())){
			cb.andEQ("agentNo", SysAdminCondition.getAgentNo());
		}
		if(!Strings.isEmpty(SysAdminCondition.getMerchantNo())){
			cb.andEQ("merchantNo", SysAdminCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(roleLevel)){			
			cb.andGroup(Cnd.builder(ChannelRole.class).andEQ("roleLevel2", roleLevel));
			cb.addParam("identityFlag", roleLevel);
		}
		if(null != SysAdminCondition.getStatus()){
			cb.andEQ("status", SysAdminCondition.getStatus());
		}
		if(null != SysAdminCondition.getCreateTime()){
			cb.andEQ("createTime", SysAdminCondition.getCreateTime());
		}
		if(Strings.isNotEmpty(SysAdminCondition.getNodeSeq())){
			cb.addParam("nodeSeq", SysAdminCondition.getNodeSeq());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(SysAdminCondition.getOrderBy())){
			if(SysAdminCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = SysAdminCondition.getOrderBy().split(",");
				String[] orders = SysAdminCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(SysAdminCondition.getOrderBy(), Order.valueOf(SysAdminCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(SysAdminCondition.getFirst()), Long.valueOf(SysAdminCondition.getLast()));
		PagingResult<ChannelAdmin> page =  channelAdminDAO.findPagingResult(buildCriteria);
		List<ChannelAdmin> pageList = page.getResult();
		for (ChannelAdmin channelAdmin : pageList) {
			try {
				ChannelAdminVo vo = (ChannelAdminVo)channelAdmin;
				if(Strings.isNotEmpty(vo.getChannelCode())){
					ChannelBase channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+vo.getChannelCode()));
					if(channelBase != null){
						vo.setChannelName(channelBase.getChannelName());
					}	
				}
				if(Strings.isNotEmpty(vo.getAgentNo())){
					AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+vo.getAgentNo()));
					if(agentBase != null){
						vo.setAgentName(agentBase.getAgentName());
					}
				}
				if(Strings.isNotEmpty(vo.getMerchantNo())){
					MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+vo.getMerchantNo()));
					if(merchantInfo != null){
						vo.setMerchantName(merchantInfo.getMerchantName());
					}
				}
			}catch (Exception e) {
				logger.error("",e);
			}
		}
		return page;
	}
	
	/**
	 * 列表
	 * @param ChannelAdmin 
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public List<ChannelAdmin> findAll(ChannelAdminCondition SysAdminCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelAdmin.class);
		if(!Strings.isEmpty(SysAdminCondition.getId())){
			cb.andEQ("id", SysAdminCondition.getId());
		}
		if(!Strings.isEmpty(SysAdminCondition.getUserName())){
			cb.andEQ("userName", SysAdminCondition.getUserName());
		}
		if(!Strings.isEmpty(SysAdminCondition.getPassword())){
			cb.andEQ("password", SysAdminCondition.getPassword());
		}
		if(!Strings.isEmpty(SysAdminCondition.getSalt())){
			cb.andEQ("salt", SysAdminCondition.getSalt());
		}
		if(!Strings.isEmpty(SysAdminCondition.getMerchantNo())){
			cb.andEQ("merchantNo", SysAdminCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(SysAdminCondition.getIdentityFlag())){
			cb.andEQ("identityFlag", SysAdminCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(SysAdminCondition.getIdentityNo())){
			cb.andEQ("identityNo", SysAdminCondition.getIdentityNo());
		}
		if(null != SysAdminCondition.getStatus()){
			cb.andEQ("status", SysAdminCondition.getStatus());
		}
		if(null != SysAdminCondition.getCreateTime()){
			cb.andEQ("createTime", SysAdminCondition.getCreateTime());
		}
		if(null != SysAdminCondition.getOpenId()){
			cb.andEQ("openId", SysAdminCondition.getOpenId());
		}
		if(null != SysAdminCondition.getChannelCode()){
			cb.andEQ("channelCode", SysAdminCondition.getChannelCode());
		}
		if(Strings.isNotEmpty(SysAdminCondition.getAgentNo())){
			cb.andEQ("agentNo", SysAdminCondition.getAgentNo());
		}
		if(!Strings.isEmpty(SysAdminCondition.getPhone())){
			cb.andLike("phone", SysAdminCondition.getPhone());
		}
		if(Strings.isNotEmpty(SysAdminCondition.getNodeSeq())){
			cb.addParam("nodeSeq", SysAdminCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelAdminDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public ChannelAdmin findById(String id){
		return channelAdminDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param SysAdminCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long insert(ChannelAdminCondition SysAdminCondition){
		ChannelAdmin ChannelAdmin = new ChannelAdmin();
		BeanUtils.copyProperties(SysAdminCondition, ChannelAdmin);
		if(Strings.isEmpty(SysAdminCondition.getOpenId())){
			ChannelAdmin.setOpenId(ChannelAdmin.getId());
		}
		return channelAdminDAO.insert(ChannelAdmin);
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long deleteById(String id){
		return channelAdminDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return channelAdminDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return channelAdminDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long update(ChannelAdminCondition SysAdminCondition){
		ChannelAdmin ChannelAdmin = new ChannelAdmin();
		BeanUtils.copyProperties(SysAdminCondition, ChannelAdmin);
		return channelAdminDAO.update(ChannelAdmin);
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long updateByCriteria(ChannelAdminCondition SysAdminCondition,Criteria criteria){
		ChannelAdmin ChannelAdmin = new ChannelAdmin();
		BeanUtils.copyProperties(SysAdminCondition, ChannelAdmin);
		return channelAdminDAO.updateByCriteria(ChannelAdmin,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
	@Override
	public long updateStatus(String id, Integer status){
		return channelAdminDAO.updateStatus(id,status);
	}

	@Override
	@Transactional
	public long updateFix(ChannelAdminCondition SysAdminCondition, String roleId,String number) {
		int nums = 0;
		//判断是否需要更新密码
		if(!Strings.isEmpty(SysAdminCondition.getPassword())){
			PasswordHelper pa = new PasswordHelper();
			ChannelAdmin ad = new ChannelAdmin();
			ad.setPassword(SysAdminCondition.getPassword());
			ad.setUserName(SysAdminCondition.getUserName());
			pa.encryptPassword(ad);
			
			SysAdminCondition.setPassword(ad.getPassword());
			SysAdminCondition.setSalt(ad.getSalt());
		}
		//是否新增用户
		if(Strings.isEmpty(SysAdminCondition.getId())){
			//新增
			SysAdminCondition.setCreateTime(new Date());
			SysAdminCondition.setId(Strings.getUUID());
			SysAdminCondition.setChannelCode(number);
			
			SysAdminCondition.setStatus(Integer.parseInt(ScanCodeConstants.STATUS_ACTIVE));
			this.insert(SysAdminCondition);
		}else{
			this.update(SysAdminCondition);
		}
		if(Strings.isNotEmpty(roleId)){
			//判断是否需要新增用户角色对应关系或者仅更新对应关系
			ChannelRoleUser ChannelAdmin = channelRoleUserService.selectRoleUserByUserId(SysAdminCondition.getId());
			if(ChannelAdmin == null){
				ChannelRoleUser admin = new ChannelRoleUser();
				admin.setAccountId(SysAdminCondition.getId());
				admin.setCreateTime(new Date());
				admin.setId(Strings.getUUID());
				admin.setRoleId(roleId);
				channelRoleUserService.insert(admin);
			}else{
				ChannelAdmin.setRoleId(roleId);
				channelRoleUserService.update(ChannelAdmin);
			}
		}
		return nums;
	}


//
//	@Override
//	public boolean getDynamicVerificationVode(String phone,String merchantNo) {
//		boolean sendResult = false;
//		try {
//			String dynamicCode = StringUtils.getRandom();
//			logger.info("phone:"+phone+" 短信动态码："+dynamicCode);
//			
//			String sentMsg = "您的验证码是："+dynamicCode+"。请不要把验证码泄露给其他人。如非本人操作，可不用理会！";
////			sendResult = smsService.sendSms(sentMsg, phone);
//			sendResult = userSmsService.sendSms(sentMsg, phone, merchantNo);
//			logger.info("phone:"+phone+" 短信发送："+sendResult);
//			
//			if (sendResult) {
//				//将验证码保存至redis中
//				redisSharedCache.set(new RedisKey(ScanCodeConstants.REDIS_KEY_MODULE, phone), dynamicCode, 1800);
//				
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("getDynamicVerificationVode error。。。"+e);
//		}
//		return sendResult;
//	}



	@Override
	public boolean validateRedisValidateCode(String key, String value) {
		logger.info("*******校验验证码,key:"+key+",value:"+value+"*******");
		
		if(Strings.isBlank(value)){
			return false;
		}
		String validateCode;
		try {
			validateCode = redisSharedCache.get(new RedisKey(ScanCodeConstants.REDIS_KEY_MODULE, key));
			logger.info("*******校验验证码,redisValue:"+validateCode+"*******");
			if(validateCode == null || !validateCode.equals(value)){
				return false;
			}
			//校验成功则清楚缓存中的验证码
			redisSharedCache.del(new RedisKey(ScanCodeConstants.REDIS_KEY_MODULE, key));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("validateRedisValidateCode error。。。"+e);
			return false;
		}
		return true;
	}

	@Override
	public ChannelAdmin findByPhone(String phone,String channelCode) {
		CriteriaBuilder cb = Cnd.builder(ChannelAdmin.class);
		cb.andEQ("phone", phone);
		cb.andEQ("channelCode", channelCode);
		Criteria buildCriteria = cb.buildCriteria();
		return channelAdminDAO.findOneByCriteria(buildCriteria);
	}


	/**
	 * 
	 * @Title: doRegister
	 * @Description: TODO
	 * @param user
	 * @see com.hfepay.scancode.service.channel.ChannelAdminService#doRegister(com.hfepay.scancode.condition.ChannelAdminCondition)
	 */
	@Override
	public void doRegister(ChannelAdminCondition user) {
		//商户保存
		MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
		
		ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
		channelExpandCondition.setChannelNo(user.getChannelCode());
		ChannelExpand channelInfo = channelExpandService.findAll(channelExpandCondition).get(0);
		String merchantNo = idCreateService.createParamNo(channelInfo.getChannelPreCode() + ConfigPreCode.MERCHANT_PRE_CODE);
		logger.info("----------merchantNo====-----"+merchantNo);
		merchantInfoCondition.setMerchantNo(merchantNo);
		merchantInfoCondition.setChannelNo(user.getChannelCode());
		merchantInfoCondition.setChannelName(ScanCodeConstants.DEFAULT_CHANNELNAME);
		//merchantInfoCondition.setAgentName(ScanCodeConstants.DEFAULT_AGENTNAME);
		merchantInfoCondition.setAgentNo(user.getAgentNo());
		merchantInfoCondition.setId(Strings.getUUID());
		merchantInfoCondition.setCreateTime(new Date());
		merchantInfoService.insert(merchantInfoCondition);
		user.setUserName(user.getPhone());
		user.setIdentityFlag(IdentityType.Identity_Merchant.getCode());
		user.setMerchantNo(merchantNo);//需要生成商户之后传递过来
		user.setAgentNo(ScanCodeConstants.DEFAULE_AGENTNO);
		user.setCreateTime(new Date());
		updateFix(user, ScanCodeConstants.DEFAULE_ROLE,user.getChannelCode());
		
		//节点级联关系
		NodeRelationCondition dCondition = new NodeRelationCondition();
		dCondition.setIdentityFlag(IdentityType.Identity_Merchant.getCode());//1渠道2代理商3商户：必需参数
		dCondition.setId(Strings.getUUID());
		dCondition.setParentNode(ScanCodeConstants.DEFAULE_AGENTNO);
		dCondition.setCurrentNode(merchantNo);//当前节点id//必须参数
		//dCondition.setCurrentNodeLevel("0");//当前节点级别：渠道是0级别
		dCondition.setOperator(merchantNo);//操作人
		nodeRelationService.doSaveNodeRelations(dCondition);
	}
	
	/**
	 * @Title: updatePassword
	 * @Description: 重置密码
	 * @param channelAdminCondition
	 * @return
	 * @see com.hfepay.scancode.service.channel.ChannelAdminService#updatePassword(com.hfepay.scancode.condition.ChannelAdminCondition)
	 */
	@Override
	public long updatePassword(ChannelAdminCondition channelAdminCondition) {
		long result = -1;
		PasswordHelper pa = new PasswordHelper();
		ChannelAdmin ad = new ChannelAdmin();
		ad.setPassword(channelAdminCondition.getPassword());
		ad.setUserName(channelAdminCondition.getUserName());
		pa.encryptPassword(ad);
		
		channelAdminCondition.setPassword(ad.getPassword());
		channelAdminCondition.setSalt(ad.getSalt());
		
		result = this.update(channelAdminCondition);
		return result;
	}
	
	@Override
	public ChannelAdmin findByCondition(ChannelAdminCondition channelAdminCondition) {
		CriteriaBuilder cb = Cnd.builder(ChannelAdmin.class);
		if(!Strings.isEmpty(channelAdminCondition.getId())){
			cb.andEQ("id", channelAdminCondition.getId());
		}
		if(!Strings.isEmpty(channelAdminCondition.getUserName())){
			cb.andLike("userName", channelAdminCondition.getUserName());
		}
		if(!Strings.isEmpty(channelAdminCondition.getPassword())){
			cb.andEQ("password", channelAdminCondition.getPassword());
		}
		if(!Strings.isEmpty(channelAdminCondition.getSalt())){
			cb.andEQ("salt", channelAdminCondition.getSalt());
		}
		if(!Strings.isEmpty(channelAdminCondition.getEmail())){
			cb.andEQ("email", channelAdminCondition.getEmail());
		}
		if(!Strings.isEmpty(channelAdminCondition.getPhone())){
			cb.andLike("phone", channelAdminCondition.getPhone());
		}
		if(!Strings.isEmpty(channelAdminCondition.getShortName())){
			cb.andEQ("shortName", channelAdminCondition.getShortName());
		}
		if(!Strings.isEmpty(channelAdminCondition.getOpenId())){
			cb.andEQ("openId", channelAdminCondition.getOpenId());
		}
		if(!Strings.isEmpty(channelAdminCondition.getChannelCode())){
			cb.andEQ("channelCode", channelAdminCondition.getChannelCode());
		}
		if(null != channelAdminCondition.getStatus()){
			cb.andEQ("status", channelAdminCondition.getStatus());
		}
		if(null != channelAdminCondition.getCreateTime()){
			cb.andEQ("createTime", channelAdminCondition.getCreateTime());
		}
		
		Criteria buildCriteria = cb.buildCriteria();
		return channelAdminDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public long createMerchantByQrCode(String qrCode, String openId) {
		PlatformQrcode code = platformQrcodeService.findByQrcode(qrCode);//查询该码是否是是有效状态
		if(code==null||QrStatus.QR_2.getCode().equals(code.getQrStatus())||UseStatus.USED.value().equals(code.getUseStatus())){
			throw new RuntimeException("二维码"+qrCode+"状态不可用....");
		}
		ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
		channelExpandCondition.setChannelNo(code.getChannelNo());
		ChannelExpand channelInfo = channelExpandService.findByCondition(channelExpandCondition);
		String merchantNo = idCreateService.createParamNo(channelInfo.getChannelPreCode() + ConfigPreCode.MERCHANT_PRE_CODE);
		logger.info("----------merchantNo====-----"+merchantNo);
		//1.登录账号创建
		//String merchantNo = StringUtils.getMerchantNo(code.getChannelNo());
		logger.info("in createMerchantByQrCode start........................openId="+openId);
		//新增
		ChannelAdminCondition conditon = new ChannelAdminCondition();
		conditon.setIdentityFlag(IdentityType.Identity_Merchant.getCode());
		conditon.setMerchantNo(merchantNo);//需要生成商户之后传递过来
		conditon.setAgentNo(code.getAgentNo());
		conditon.setCreateTime(new Date());
		conditon.setId(Strings.getUUID());
		conditon.setChannelCode(code.getChannelNo());
		conditon.setStatus(Integer.parseInt(ScanCodeConstants.STATUS_ACTIVE));
		conditon.setIdentityNo(merchantNo);
		conditon.setOpenId(openId);
		long result = this.insert(conditon);
		if(result>0){
			//2商户创建
			MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
			merchantInfoCondition.setMerchantNo(merchantNo);
			merchantInfoCondition.setChannelNo(code.getChannelNo());
			merchantInfoCondition.setQrCode(qrCode);
			//merchantInfoCondition.setMerchantName(ScanCodeConstants.DEFAULT_CHANNELNAME);
			//merchantInfoCondition.setAgentName(ScanCodeConstants.DEFAULT_AGENTNAME);
			merchantInfoCondition.setAgentNo(code.getAgentNo());
			merchantInfoCondition.setStatus(MerchantStatus.MERCHANT_STATUS_8.getCode());//初始状态
			merchantInfoCondition.setId(Strings.getUUID());
			merchantInfoCondition.setCreateTime(new Date());
			merchantInfoCondition.setAuthenStatus(0);
			result = merchantInfoService.insert(merchantInfoCondition);
		}
		//3.修改二维码状态为已使用
		if(result>0){
			code.setUseStatus(UseStatus.USED.value());
			code.setMerchantNo(merchantNo);
			code.setUpdateTime(new Date());
			result = platformQrcodeService.update(code);
		}
		//修改二维码数目，
		if(result>0){
			result = agentBaseService.updateUsedTimes(code.getAgentNo());
		}
		//节点级联关系
		NodeRelationCondition dCondition = new NodeRelationCondition();
		dCondition.setIdentityFlag(IdentityType.Identity_Merchant.getCode());//1渠道2代理商3商户：必需参数
		dCondition.setId(Strings.getUUID());
		dCondition.setParentNode(code.getAgentNo());
		dCondition.setCurrentNode(merchantNo);//当前节点id//必须参数
		//dCondition.setCurrentNodeLevel("0");//当前节点级别：渠道是0级别
		dCondition.setOperator(merchantNo);//操作人
		nodeRelationService.doSaveNodeRelations(dCondition);
		//将微信用户信息存放到数据库中,有可能用户先关注了公众号，此时已获得用户的微信相关信息，只需要更新信息即可（主要是修改时间）
		toSaveWechatUser(conditon);
		if(result<=0){
			throw new RuntimeException("扫码注册商户失败");
		}
		return result;
	}
	
	/**获取用户信息保存
	 * @throws Exception **/
	public void toSaveWechatUser(ChannelAdminCondition conditon){
		try {
			String openId = conditon.getOpenId();
			WechatUserCondition wechatUserCondition = new WechatUserCondition();
			wechatUserCondition.setOpenid(openId);
			WechatUser user = wechatUserService.findByCondition(wechatUserCondition);
			
			String organNo = apiChannelWxParamsService.getWechatConfigEntity(conditon.getAgentNo()).getOrganNo();
			String token = merchantBusinessService.getAccessToken(organNo);
			ApiMappingDicionCondition apiCondition = new ApiMappingDicionCondition();
			apiCondition.setKeyNo(HfepayConfig.WUNIONID_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(apiCondition);
			
			String uri =dic.getParaVal();//"https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			logger.info("=============== get url is "+uri);
			String access_token_uri = uri.replace("ACCESS_TOKEN", token).replace("OPENID", openId);
			byte[] res = HttpRequestClient.doGet(access_token_uri);
			String resultJson = new String(res, "utf-8");
			JSONObject jsonObject = JSONObject.fromObject(resultJson);
			wechatUserCondition = JSON.parseObject(resultJson, WechatUserCondition.class);
			Object list_id = jsonObject.get("tagid_list");
			String list ="";
			if(list_id!=null && Strings.isNotEmpty(list_id.toString())){
				list = list_id.toString().replaceAll("[\\[\\]]", "");
			}
			Object subscribe_time = jsonObject.get("subscribe_time");
			Date subDate =null;
			if(subscribe_time!=null){
				Long time=0L;
				try {
					time = Long.parseLong(subscribe_time.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(time!=0){
					subDate = new Date(time*1000);
				}
			}
			wechatUserCondition.setTagidList(list);
			wechatUserCondition.setSubscribeTime(subDate);
			wechatUserCondition.setIdentityNo(conditon.getIdentityNo());
			wechatUserCondition.setUserType("0");//0商户相关，1用户相关
			
			if(user==null){//新增
				wechatUserCondition.setCreateTime(new Date());
				wechatUserCondition.setId(Strings.getUUID());
				wechatUserService.insert(wechatUserCondition);
			}
			else{
				wechatUserCondition.setUpdateTime(new Date());
				wechatUserCondition.setId(user.getId());
				wechatUserService.update(wechatUserCondition);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("通过openid获取用户信息失败",e);
		}
	}

	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @param channelNo
	 * @return: ChannelAdmin
	 */
	public ChannelAdmin findByChannelNo(String channelNo){
		if(Strings.isEmpty(channelNo)){
			throw new RuntimeException("渠道编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(ChannelAdmin.class);
		cb.andEQ("channelCode", channelNo);
		cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
		Criteria buildCriteria = cb.buildCriteria();
		return channelAdminDAO.findOneByCriteria(buildCriteria);
	}



	@Override
	public ChannelAdmin findByMerchantNo(String merchantNo) {
		if(Strings.isEmpty(merchantNo)){
			throw new RuntimeException("商户编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(ChannelAdmin.class);
		cb.andEQ("merchantNo", merchantNo);
		cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
		Criteria buildCriteria = cb.buildCriteria();
		return channelAdminDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public ChannelAdmin findByMerchantNoByIdentity(String merchantNo) {
		return channelAdminDAO.findByMerchantNoByIdentity(merchantNo, ScanCodeConstants.IDENTITYFLAG_MERCHANT);
	}
	
	/**
	 * 列表(分页)
	 * @param SysAdminCondition 
	 * @param channelCode  角色类型
	 *
	 * @author panq
	 * @date CreateDate : 2016-06-02 15:35:32
	 */
    @Override
	public PagingResult<ChannelAdmin> findPagingResultByAgentNo(ChannelAdminCondition SysAdminCondition){
		CriteriaBuilder cb = Cnd.builder(ChannelAdmin.class);
		if(!Strings.isEmpty(SysAdminCondition.getId())){
			cb.andEQ("id", SysAdminCondition.getId());
		}
		if(!Strings.isEmpty(SysAdminCondition.getAgentNo())){
			cb.andEQ("agentNo", SysAdminCondition.getAgentNo());
		}
		if(!Strings.isEmpty(SysAdminCondition.getUserName())){
			cb.andLike("userName", SysAdminCondition.getUserName());
		}
		if(!Strings.isEmpty(SysAdminCondition.getPassword())){
			cb.andEQ("password", SysAdminCondition.getPassword());
		}
		if(!Strings.isEmpty(SysAdminCondition.getSalt())){
			cb.andEQ("salt", SysAdminCondition.getSalt());
		}
		if(!Strings.isEmpty(SysAdminCondition.getEmail())){
			cb.andEQ("email", SysAdminCondition.getEmail());
		}
		if(!Strings.isEmpty(SysAdminCondition.getPhone())){
			cb.andLike("phone", SysAdminCondition.getPhone());
		}
		if(!Strings.isEmpty(SysAdminCondition.getShortName())){
			cb.andLike("shortName", SysAdminCondition.getShortName());
		}
		if(!Strings.isEmpty(SysAdminCondition.getOpenId())){
			cb.andEQ("openId", SysAdminCondition.getOpenId());
		}
		if(!Strings.isEmpty(SysAdminCondition.getIdentityFlag())){
			cb.andEQ("identityFlag", SysAdminCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(SysAdminCondition.getIdentityNo())){
			cb.andEQ("identityNo", SysAdminCondition.getIdentityNo());
		}
		if(!Strings.isEmpty(SysAdminCondition.getChannelCode())){
			cb.andEQ("channelCode", SysAdminCondition.getChannelCode());
		}
		if(null != SysAdminCondition.getStatus()){
			cb.andEQ("status", SysAdminCondition.getStatus());
		}
		if(null != SysAdminCondition.getCreateTime()){
			cb.andEQ("createTime", SysAdminCondition.getCreateTime());
		}
		if(Strings.isNotEmpty(SysAdminCondition.getNodeSeq())){
			cb.addParam("nodeSeq", SysAdminCondition.getNodeSeq());
			cb.addParam("identityFlag", SysAdminCondition.getIdentityFlag());
		}
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(SysAdminCondition.getOrderBy())){
			if(SysAdminCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = SysAdminCondition.getOrderBy().split(",");
				String[] orders = SysAdminCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(SysAdminCondition.getOrderBy(), Order.valueOf(SysAdminCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(SysAdminCondition.getFirst()), Long.valueOf(SysAdminCondition.getLast()));
		PagingResult<ChannelAdmin> page =  channelAdminDAO.findPagingResult(buildCriteria);
		List<ChannelAdmin> pageList = page.getResult();
		for (ChannelAdmin channelAdmin : pageList) {
			try {
				ChannelAdminVo vo = (ChannelAdminVo)channelAdmin;
				ChannelBase channelBase = (ChannelBase)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+vo.getChannelCode()));
				if(channelBase==null){
					channelExpandService.setChannelRedis();
					channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+vo.getChannelCode()));
				}
				if(channelBase != null){
					vo.setChannelName(channelBase.getChannelName());
				}	
				if(Strings.isNotEmpty(vo.getAgentNo())){
					AgentBase agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+vo.getAgentNo()));
					if(agentBase == null){
						agentBaseService.setAgentRedis();
						agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+vo.getAgentNo()));
					}
					if(agentBase != null){
						vo.setAgentName(agentBase.getAgentName());
					}
				}
				if(Strings.isNotEmpty(vo.getMerchantNo())){
					MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+vo.getMerchantNo()));
					if(merchantInfo == null){
						merchantInfoService.setMerchantRedis();
						merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+vo.getMerchantNo()));
					}
					if(merchantInfo != null){
						vo.setMerchantName(merchantInfo.getMerchantName());
					}
				}
			}catch (Exception e) {
				logger.error("",e);
			}
		}
		return page;
	}
	
    @Override
    public List<ChannelAdmin> findUnregsit(int start,int end) {
    	// TODO Auto-generated method stub
    	return channelAdminDAO.findUnregsit(start,end);
    }
    @Override
    public int countUnregsit() {
    	// TODO Auto-generated method stub
    	return channelAdminDAO.countUnregsit();
    }
    
    @Override
	public List<ChannelAdmin> findMerchantInfoUnperfect(Map<String, Object> map) {
    	return channelAdminDAO.findMerchantInfoUnperfect(map);
    }
    
    //查找openid不等于id的用户，此处用户消息推送
    @Override
    public ChannelAdmin findPushMsgAdmin(String merchantNo,boolean isMerchant){
    	return channelAdminDAO.findPushMsgAdmin(merchantNo,isMerchant);
    }
}
