/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.util.Date;
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
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.ChannelRoleCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.dao.MerchantCashierDAO;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.ChannelRole;
import com.hfepay.scancode.commons.entity.ChannelRoleUser;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.channel.ChannelRoleService;
import com.hfepay.scancode.service.channel.ChannelRoleUserService;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.contants.ConfigPreCode;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.operator.MerchantCashierQrService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.NodeRelationService;
import com.hfepay.scancode.service.operator.UserSmsService;
import com.hfepay.scancode.service.utils.PasswordHelper;

import net.sf.json.JSONObject;

@Service("merchantCashierService")
public class MerchantCashierServiceImpl implements MerchantCashierService {
	
	@Autowired
    private MerchantCashierDAO merchantCashierDAO;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private IdCreateService idCreateService;
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
    private MerchantCashierQrService merchantCashierQrService;
	@Autowired
	private RedisSharedCache redisSharedCache;
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private UserSmsService userSmsService;
	@Autowired
	private ChannelRoleUserService channelRoleUserService;
	@Autowired
	private ChannelRoleService channelRoleService;
	@Autowired
	private NodeRelationService nodeRelationService;
	public static final Logger log = LoggerFactory.getLogger(MerchantCashierServiceImpl.class);
    
    /**
	 * 列表(分页)
	 * @param merchantCashierCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
    @Override
	public PagingResult<MerchantCashier> findPagingResult(MerchantCashierCondition merchantCashierCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantCashier.class);
		if(!Strings.isEmpty(merchantCashierCondition.getId())){
			cb.andEQ("id", merchantCashierCondition.getId());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getCashierNo())){
			cb.andLike("cashierNo", merchantCashierCondition.getCashierNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantCashierCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCashierCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getUserName())){
			cb.andEQ("userName", merchantCashierCondition.getUserName());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getIdCard())){
			cb.andEQ("idCard", merchantCashierCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getMobile())){
			cb.andEQ("mobile", merchantCashierCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getOpenId())){
			cb.andEQ("openId", merchantCashierCondition.getOpenId());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getStatus())){
			cb.andEQ("status", merchantCashierCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantCashierCondition.getRecordStatus());
		}
		if(null != merchantCashierCondition.getCreateTime()){
			cb.andEQ("createTime", merchantCashierCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantCashierCondition.getOperator())){
			cb.andEQ("operator", merchantCashierCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantCashierCondition.getRemark())){
			cb.andLike("remark", merchantCashierCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getTemp1())){
			cb.andEQ("temp1", merchantCashierCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getTemp2())){
			cb.andEQ("temp2", merchantCashierCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantCashierCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantCashierCondition.getNodeSeq());
		}
		//cb.andIsNotNull(merchantCashierCondition.getStatus());//扫码注册的时候状态为空
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantCashierCondition.getOrderBy())){
			if(merchantCashierCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantCashierCondition.getOrderBy().split(",");
				String[] orders = merchantCashierCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantCashierCondition.getOrderBy(), Order.valueOf(merchantCashierCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantCashierCondition.getFirst()), Long.valueOf(merchantCashierCondition.getLast()));
		
		PagingResult<MerchantCashier> page = merchantCashierDAO.findPagingResult(buildCriteria);
		for (MerchantCashier merchantCashier : page.getResult()) {			
			try {
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantCashier.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					merchantCashier.setMerchantName(merchantInfo.getMerchantName());
				}
//				MerchantStore merchantStore = (MerchantStore) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+merchantCashier.getStoreNo()));
//				if(merchantStore != null){
//					merchantCashier.setStoreName(merchantStore.getStoreName());
//				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}
	
	/**
	 * 列表
	 * @param merchantCashier 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
	@Override
	public List<MerchantCashier> findAll(MerchantCashierCondition merchantCashierCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantCashier.class);
		if(!Strings.isEmpty(merchantCashierCondition.getId())){
			cb.andEQ("id", merchantCashierCondition.getId());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getCashierNo())){
			cb.andEQ("cashierNo", merchantCashierCondition.getCashierNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantCashierCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCashierCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getUserName())){
			cb.andEQ("userName", merchantCashierCondition.getUserName());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getIdCard())){
			cb.andEQ("idCard", merchantCashierCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getMobile())){
			cb.andEQ("mobile", merchantCashierCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getOpenId())){
			cb.andEQ("openId", merchantCashierCondition.getOpenId());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getStatus())){
			cb.andEQ("status", merchantCashierCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantCashierCondition.getRecordStatus());
		}
		if(null != merchantCashierCondition.getCreateTime()){
			cb.andEQ("createTime", merchantCashierCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantCashierCondition.getOperator())){
			cb.andEQ("operator", merchantCashierCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantCashierCondition.getRemark())){
			cb.andLike("remark", merchantCashierCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getTemp1())){
			cb.andEQ("temp1", merchantCashierCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getTemp2())){
			cb.andEQ("temp2", merchantCashierCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantCashierCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantCashierCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantCashierDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
	@Override
	public MerchantCashier findById(String id){
		return merchantCashierDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantCashierCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
	@Override
	public long insert(MerchantCashierCondition merchantCashierCondition){
		MerchantCashier merchantCashier = new MerchantCashier();
		try {
			MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantCashierCondition.getMerchantNo()));
			merchantCashierCondition.setChannelNo(merchantInfo.getChannelNo());
			merchantCashierCondition.setAgentNo(merchantInfo.getAgentNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanUtils.copyProperties(merchantCashierCondition, merchantCashier);
		long result = merchantCashierDAO.insert(merchantCashier);
		try {
			List<MerchantCashier> list = this.findAll(merchantCashierCondition);
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_CASHIER_BASE.getGroup(), RedisKeyEnum.MERCHANT_CASHIER_BASE.getKey()+list.get(0).getCashierNo()), list.get(0));
		} catch (Exception e) {
			log.error("#######保存商户数据到redis失败######");
		}
		return result;
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
	@Override
	public long deleteById(String id){
		return merchantCashierDAO.deleteById(id);
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
	@Override
	public long deleteByCriteria(Criteria criteria){
		return merchantCashierDAO.deleteByCriteria(criteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
	@Override
	public long countByCriteria(Criteria criteria){
		return merchantCashierDAO.countByCriteria(criteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
	@Override
	public long update(MerchantCashierCondition merchantCashierCondition){
		MerchantCashier merchantCashier = new MerchantCashier();
		BeanUtils.copyProperties(merchantCashierCondition, merchantCashier);
		long result = merchantCashierDAO.update(merchantCashier);
		try {
			List<MerchantCashier> list = this.findAll(merchantCashierCondition);
			redisSharedCache.del(new RedisKey(RedisKeyEnum.MERCHANT_CASHIER_BASE.getGroup(), RedisKeyEnum.MERCHANT_CASHIER_BASE.getKey()+list.get(0).getCashierNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_CASHIER_BASE.getGroup(), RedisKeyEnum.MERCHANT_CASHIER_BASE.getKey()+list.get(0).getCashierNo()), list.get(0));
		} catch (Exception e) {
			log.error("#######保存商户数据到redis失败######");
		}
		return result;
	}
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
	@Override
	public long updateByCriteria(MerchantCashierCondition merchantCashierCondition,Criteria criteria){
		MerchantCashier merchantCashier = new MerchantCashier();
		BeanUtils.copyProperties(merchantCashierCondition, merchantCashier);
		return merchantCashierDAO.updateByCriteria(merchantCashier,criteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantCashierDAO.updateStatus(id,status);
	}
	/**
	 * 状态更新时更新相对应的二维码
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 10:49:07
	 */
	@Override
	public long updateStatus(String id,String status,String cashierNo){
		MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
		merchantCashierQrCondition.setCashierNo(cashierNo);
		merchantCashierQrCondition.setStatus(status);
		merchantCashierQrCondition.setIsCashier("1");
		merchantCashierQrService.updateByCriteria(merchantCashierQrCondition);
		return merchantCashierDAO.updateStatus(id,status);
	}
	
	@Override
	public long delCashierByCashierNo(String cashierNo,String opreator) {
		//删除渠道用户表
		CriteriaBuilder cb = Cnd.builder(ChannelAdmin.class);
		cb.andEQ("identityNo", cashierNo);
		Criteria buildCriteria = cb.buildCriteria();
		long result = channelAdminService.deleteByCriteria(buildCriteria);
		if(result<0){
			throw new RuntimeException("删除注册商户channeladmin失败");
		}
		result = merchantCashierDAO.delCashierByCashierNo(cashierNo,opreator);
		if(result<0){
			throw new RuntimeException("删除merchantCashier失败");
		}
		return result;
	}
	
	@Override
	public long createCashier(String merchantNo, String openId) {
		String cashierNo = idCreateService.createParamNo(ConfigPreCode.CASHIER_PRE_CODE);
		//创建收银登录账户
		MerchantInfo info = merchantInfoService.findByMerchantNo(merchantNo);
    	ChannelAdminCondition conditon = new ChannelAdminCondition();
		conditon.setIdentityFlag(IdentityType.Identity_Cashier.getCode());
		conditon.setMerchantNo(merchantNo);//需要生成商户之后传递过来
		conditon.setAgentNo(info.getAgentNo());
		conditon.setCreateTime(new Date());
		conditon.setId(Strings.getUUID());
		conditon.setChannelCode(info.getChannelNo());
		conditon.setStatus(Integer.parseInt(ScanCodeConstants.STATUS_ACTIVE));
		conditon.setIdentityNo(cashierNo);
		conditon.setOpenId(openId);
		long result = channelAdminService.insert(conditon);
		/*if(result>0){
			MerchantCashierCondition condition = new MerchantCashierCondition();
			condition.setId(Strings.getUUID());
			condition.setCashierNo(cashierNo);
			condition.setCreateTime(new Date());
			condition.setOpenId(openId);
			condition.setStatus(null);
			result = this.insert(condition);
			if(result<0){
				throw new RuntimeException("创建收银员失败...");
			}
		}else{
			throw new RuntimeException("创建账号失败...");
		}*/
		//获取微信用户信息并保存
		channelAdminService.toSaveWechatUser(conditon);
    	return result;
	}
	
	@Override
	public long doSaveCashier(MerchantCashierCondition condition) {
		ChannelAdminCondition adminCondition = new ChannelAdminCondition();
		adminCondition.setOpenId(condition.getOpenId());
		adminCondition.setIdentityFlag(IdentityType.Identity_Cashier.getCode());
		ChannelAdmin admin = channelAdminService.findByCondition(adminCondition);
		condition.setId(Strings.getUUID());
		condition.setMerchantNo(admin.getMerchantNo());
		condition.setCashierNo(admin.getIdentityNo());
		condition.setCreateTime(new Date());
		condition.setStatus("1");
		condition.setRecordStatus("Y");
		long result = this.insert(condition);
		if(result>0){
			//商户随机密码
			String loginPassword = String.valueOf((int)((Math.random()*9+1)*100000));
			String salt = PasswordHelper.getSalt();
	    	String pwd = PasswordHelper.getEncryptPassword(condition.getMobile(), loginPassword, salt);
	    	admin.setSalt(salt);
	    	admin.setPassword(pwd);
	    	admin.setUserName(condition.getMobile());
	    	admin.setPhone(condition.getMobile());
	    	ChannelAdminCondition channelAdminCondition = new ChannelAdminCondition();
	    	BeanUtils.copyProperties(admin, channelAdminCondition);
	    	result = channelAdminService.update(channelAdminCondition);
	    	if(result>0){//在t_channel_role_user表插入对应关系
	    		ChannelRoleCondition  rCondition = new ChannelRoleCondition();
	    		rCondition.setChannelCode("cashier");//默认收银员角色
	    		rCondition.setRoleLevel("4");
	    		ChannelRole role = channelRoleService.findByCondition(rCondition);
	    		if(role==null){
	    			throw new RuntimeException("代理商角色没有创建.....");
	    		}
	    		ChannelRoleUser roleUser = new ChannelRoleUser();
	    		roleUser.setId(Strings.getUUID());
	    		roleUser.setAccountId(admin.getId());
	    		roleUser.setRoleId(role.getId());//收银员的角色id
	    		roleUser.setCreateTime(new Date());
	    		result = channelRoleUserService.insert(roleUser);
	    	}
	    	if(result>0){
	    		//保存渠道，存放一条关联信息到节点表。只有一条
				NodeRelationCondition dCondition = new NodeRelationCondition();
				dCondition.setIdentityFlag(IdentityType.Identity_Cashier.getCode());//1渠道2代理商3商户：必需参数
				dCondition.setId(Strings.getUUID());
				dCondition.setParentNode(admin.getMerchantNo());
				dCondition.setCurrentNode(admin.getIdentityNo());//当前节点id//必须参数
				dCondition.setCurrentNodeLevel("0");//当前节点级别：渠道是0级别
				dCondition.setOperator(admin.getUserName());//操作人
				
				result = nodeRelationService.doSaveNodeRelations(dCondition);
	    	}
	    	if(result>0){
	    		log.info("#########merchant["+admin.getMerchantNo()+"]审核通过发送短信########");
				//短信通知商户
				String url = "";
				DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(admin.getMerchantNo());
				String organNo = "";
				if (null != vo) {
					organNo = vo.getOrganNo();
				}else {					
					organNo = admin.getChannelCode();
				}
				
				ChannelExpand channelExpand = channelExpandService.findByChannelNo(organNo);
				if (null != channelExpand.getDomainName() && !Strings.isEmpty(channelExpand.getDomainName())) {
					url = channelExpand.getDomainName();//使用客户自定义域名
				}else {
					url = channelExpand.getChannelCode()+".qd.huaepay.com";//使用我们自动装配的域名
				}
				
//				String content = "您的商户入驻申请已审核通过。用户名:"+merchantInfoCondition.getMobile()+"，初始密码："+merchantInfoCondition.getTemp1()+"，请尽快登录并修改初始密码！请不要把账户信息泄露给其他人,如有疑问请联系："+channelExpand.getPhone()+"。如非本人操作，可不用理会！";
				String content="您已完成收银员注册，可通过电脑使用"+url+"地址登录，用户名:"+condition.getMobile()+"，初始密码："+loginPassword+"，请尽快登录并修改初始密码！请不要把账户信息泄露给其他人。如非本人操作，可不用理会！";
				log.info("########收银员注册成功，给["+condition.getMobile()+"]发送内容["+content+"]#######");
				try {
//					smsService.sendSms(content, merchantInfoCondition.getMobile());
//					userSmsService.sendSms(content, condition.getMobile(), condition.getMerchantNo());
				} catch (Exception e) {
					log.error("#########merchant["+admin.getMerchantNo()+"]审核通过发送短信失败########");
					e.printStackTrace();
				}
	    	}
		}
		return result;
	}
	
	@Override
	public MerchantCashier findByCondition(MerchantCashierCondition merchantCashierCondition) {
		CriteriaBuilder cb = Cnd.builder(MerchantCashier.class);
		if(!Strings.isEmpty(merchantCashierCondition.getId())){
			cb.andEQ("id", merchantCashierCondition.getId());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getCashierNo())){
			cb.andEQ("cashierNo", merchantCashierCondition.getCashierNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantCashierCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCashierCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getUserName())){
			cb.andEQ("userName", merchantCashierCondition.getUserName());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getIdCard())){
			cb.andEQ("idCard", merchantCashierCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getMobile())){
			cb.andEQ("mobile", merchantCashierCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getOpenId())){
			cb.andEQ("openId", merchantCashierCondition.getOpenId());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getStatus())){
			cb.andEQ("status", merchantCashierCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantCashierCondition.getRecordStatus());
		}
		if(null != merchantCashierCondition.getCreateTime()){
			cb.andEQ("createTime", merchantCashierCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantCashierCondition.getOperator())){
			cb.andEQ("operator", merchantCashierCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantCashierCondition.getRemark())){
			cb.andLike("remark", merchantCashierCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getTemp1())){
			cb.andEQ("temp1", merchantCashierCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getTemp2())){
			cb.andEQ("temp2", merchantCashierCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantCashierCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantCashierCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantCashierDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public MerchantCashier findByOpenId(String openId) {
		MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
		merchantCashierCondition.setOpenId(openId);
		merchantCashierCondition.setRecordStatus("Y");
		MerchantCashier cashier = findByCondition(merchantCashierCondition); 
		return cashier;
	}
	
	@Override
	public long bindStore(String storeNo, String cashierNo) {
		// TODO Auto-generated method stub
		return merchantCashierDAO.bindStore(storeNo, cashierNo);
	}
	
	@Override
	public PagingResult<MerchantCashier> findPagingResultSelf(MerchantCashierCondition merchantCashierCondition) {
		CriteriaBuilder cb = Cnd.builder(MerchantCashier.class);
		if(!Strings.isEmpty(merchantCashierCondition.getId())){
			cb.andEQ("id", merchantCashierCondition.getId());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getCashierNo())){
			cb.andEQ("cashierNo", merchantCashierCondition.getCashierNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantCashierCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCashierCondition.getMerchantNo());
			cb.addParam("cashierMerchantNo",merchantCashierCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getUserName())){
			cb.andEQ("userName", merchantCashierCondition.getUserName());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getIdCard())){
			cb.andEQ("idCard", merchantCashierCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getMobile())){
			cb.andEQ("mobile", merchantCashierCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getOpenId())){
			cb.andEQ("openId", merchantCashierCondition.getOpenId());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getStatus())){
			cb.andEQ("status", merchantCashierCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantCashierCondition.getRecordStatus());
		}
		if(null != merchantCashierCondition.getCreateTime()){
			cb.andEQ("createTime", merchantCashierCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantCashierCondition.getOperator())){
			cb.andEQ("operator", merchantCashierCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantCashierCondition.getRemark())){
			cb.andLike("remark", merchantCashierCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getTemp1())){
			cb.andEQ("temp1", merchantCashierCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantCashierCondition.getTemp2())){
			cb.andEQ("temp2", merchantCashierCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantCashierCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantCashierCondition.getNodeSeq());
		}
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantCashierCondition.getOrderBy())){
			if(merchantCashierCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantCashierCondition.getOrderBy().split(",");
				String[] orders = merchantCashierCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantCashierCondition.getOrderBy(), Order.valueOf(merchantCashierCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantCashierCondition.getFirst()), Long.valueOf(merchantCashierCondition.getLast()));
		
		PagingResult<MerchantCashier> page = merchantCashierDAO.findPagingResultSelf(buildCriteria);
		for (MerchantCashier merchantCashier : page.getResult()) {			
			try {
				MerchantInfo merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantCashier.getMerchantNo()));
				log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
				if(merchantInfo != null){
					merchantCashier.setMerchantName(merchantInfo.getMerchantName());
				}
			} catch (Exception e) {
				log.error("获取redis数据异常："+e.getMessage());
			}
		}
		return page;
	}
	
	
	/**设置商户redis
	 * @throws Exception */
	@Override
	public void setMerchantCashierRedis() throws Exception{
		List<MerchantCashier> list = this.findAll(new MerchantCashierCondition());
		for (MerchantCashier merchantCashier : list) {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.MERCHANT_CASHIER_BASE.getGroup(), RedisKeyEnum.MERCHANT_CASHIER_BASE.getKey()+merchantCashier.getCashierNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_CASHIER_BASE.getGroup(), RedisKeyEnum.MERCHANT_CASHIER_BASE.getKey()+merchantCashier.getCashierNo()), merchantCashier);
		}
	}
}

