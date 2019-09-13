/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */package com.hfepay.scancode.service.operator.impl;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.HttpRequestClient;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.condition.ApiMappingDicionCondition;
import com.hfepay.scancode.api.entity.ApiMappingDicion;
import com.hfepay.scancode.api.entity.message.RegistSuccessMessage;
import com.hfepay.scancode.api.entity.vo.BankCardAuthVo;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.entity.vo.MerchantAccountsVo;
import com.hfepay.scancode.api.entity.vo.MerchantInfoChangeVo;
import com.hfepay.scancode.api.entity.vo.MerchantInfoVo;
import com.hfepay.scancode.api.entity.vo.MerchantRateVo;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.api.service.ApiMappingDicionService;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.commons.BatchNoUtil;
import com.hfepay.scancode.commons.TransCodeEnum;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.AuditLogCondition;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.MerchantActivityCondition;
import com.hfepay.scancode.commons.condition.MerchantAuthDetailCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantQrcodeCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.condition.QrcodeGoodsCondition;
import com.hfepay.scancode.commons.contants.AuditType;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.dao.MerchantInfoDAO;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.MerchantActivity;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.MerchantQrcode;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.QrcodeGoods;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.channel.ChannelRoleService;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.contants.ConfigPreCode;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.AuditLogService;
import com.hfepay.scancode.service.operator.ChangeLogService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.MerchantActivityService;
import com.hfepay.scancode.service.operator.MerchantAuthDetailService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantCashierQrService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;
import com.hfepay.scancode.service.operator.MerchantQrcodeService;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.operator.NodeRelationService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;
import com.hfepay.scancode.service.operator.QrcodeGoodsService;
import com.hfepay.scancode.service.operator.UserSmsService;
import com.hfepay.scancode.service.order.OrderPaymentService;
import com.hfepay.scancode.service.utils.PhotoUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import redis.clients.jedis.Jedis;

@Service("merchantInfoService")
public class MerchantInfoServiceImpl implements MerchantInfoService {
	
	public static final Logger log = LoggerFactory.getLogger(MerchantInfoServiceImpl.class);
	
	@Autowired
    private MerchantInfoDAO merchantInfoDAO;
	
	@Autowired
	private UserSmsService userSmsService;
	
	@Autowired
	private ChannelExpandService channelExpandService;
	
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	
	@Autowired
	private ApiMappingDicionService apiMappingDicionService;
	
	@Autowired
	private RedisSharedCache redisSharedCache;
	
	@Autowired
	private MerchantStoreService merchantStoreService;
	
	@Autowired
	private NodeRelationService nodeRelationService;
	
	@Autowired
	private ChannelAdminService channelAdminService;
	
	@Autowired
	private MerchantPaywayService merchantPaywayService;
	
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	
	@Autowired
	private AgentBaseService agentBaseService;
	
	@Autowired
	private MerchantBankcardService merchantBankcardService;
	
	@Autowired
    private ChannelRoleService channelRoleService;
	
	@Autowired
	private ChangeLogService changeLogService; 
	
	@Autowired
	private MerchantAuthDetailService merchantAuthDetailService;
	
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	
	@Autowired
	private AuditLogService auditLogService;
	
	@Autowired
	private MerchantQrcodeService merchantQrcodeService;

	@Autowired
	private MerchantCashierService merchantCashierService;
	
	@Autowired
    private MerchantCashierQrService merchantCashierQrService;
	
	@Autowired
	private QrcodeGoodsService qrcodeGoodsService;
	
	@Autowired
	private MerchantActivityService merchantActivityService;
	
	@Autowired
	private OrderPaymentService orderPaymentService;
	
	@Autowired
	private MappingDicionService mappingDicionService;
	
    /**
	 * 列表(分页)
	 * @param merchantInfoCondition 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
    @Override
	public PagingResult<MerchantInfo> findPagingResult(MerchantInfoCondition merchantInfoCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		if(!Strings.isEmpty(merchantInfoCondition.getId())){
			cb.andEQ("id", merchantInfoCondition.getId());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantInfoCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelName())){
			cb.andLike("channelName", merchantInfoCondition.getChannelName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantInfoCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentName())){
			cb.andLike("agentName", merchantInfoCondition.getAgentName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantInfoCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantName())){
			cb.andLike("merchantName", merchantInfoCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getShortName())){
			cb.andLike("shortName", merchantInfoCondition.getShortName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPlatformMerchantNo())){
			cb.andEQ("platformMerchantNo", merchantInfoCondition.getPlatformMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getBusType())){
			cb.andEQ("busType", merchantInfoCondition.getBusType());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getName())){
			cb.andLike("name", merchantInfoCondition.getName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdCard())){
			cb.andLike("idCard", merchantInfoCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantInfoCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantInfoCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantInfoCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMobile())){
			cb.andEQ("mobile", merchantInfoCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPhone())){
			cb.andEQ("phone", merchantInfoCondition.getPhone());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getEmail())){
			cb.andEQ("email", merchantInfoCondition.getEmail());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAddress())){
			cb.andEQ("address", merchantInfoCondition.getAddress());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantInfoCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantInfoCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxNo())){
			cb.andEQ("taxNo", merchantInfoCondition.getTaxNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxImg())){
			cb.andEQ("taxImg", merchantInfoCondition.getTaxImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getOrgNo())){
			cb.andEQ("orgNo", merchantInfoCondition.getOrgNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getQrCode())){
			cb.andEQ("qrCode", merchantInfoCondition.getQrCode());
		}
		if(null != merchantInfoCondition.getAuthenStatus()){
			cb.andEQ("authenStatus", merchantInfoCondition.getAuthenStatus());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getStatus())){
			cb.andEQ("status", merchantInfoCondition.getStatus());
		}
//		if(null != merchantInfoCondition.getCreateTime()){
//			cb.andEQ("createTime", merchantInfoCondition.getCreateTime());
//		}
//		
		if(null != merchantInfoCondition.getQueryStartTime()){
			cb.andGE("createTime", merchantInfoCondition.getQueryStartTime());
		}
		if(null != merchantInfoCondition.getQueryEndTime()){
			cb.andLE("createTime", merchantInfoCondition.getQueryEndTime());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getOperator())){
			cb.andEQ("operator", merchantInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getRemark())){
			cb.andLike("remark", merchantInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp1())){
			cb.andEQ("temp1", merchantInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp2())){
			cb.andEQ("temp2", merchantInfoCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp3())){
			cb.andEQ("temp3", merchantInfoCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp4())){
			cb.andEQ("temp4", merchantInfoCondition.getTemp4());
		}
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		if(Strings.isNotEmpty(merchantInfoCondition.getNodeSeq())){
			cb.addParam("identityFlag", IdentityType.Identity_Merchant.getCode());
			cb.addParam("nodeSeq", merchantInfoCondition.getNodeSeq());
		}
		cb.orderBy("status", Order.ASC);
		cb.orderBy("createTime", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantInfoCondition.getOrderBy())){
			if(merchantInfoCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantInfoCondition.getOrderBy().split(",");
				String[] orders = merchantInfoCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantInfoCondition.getOrderBy(), Order.valueOf(merchantInfoCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		buildCriteria.limit(Long.valueOf(merchantInfoCondition.getFirst()), Long.valueOf(merchantInfoCondition.getLast()));
		return merchantInfoDAO.findPagingResult(buildCriteria);
	}
	
	/**
	 * 列表
	 * @param merchantInfo 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	@Override
	public List<MerchantInfo> findAll(MerchantInfoCondition merchantInfoCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		if(!Strings.isEmpty(merchantInfoCondition.getId())){
			cb.andEQ("id", merchantInfoCondition.getId());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantInfoCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelName())){
			cb.andLike("channelName", merchantInfoCondition.getChannelName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantInfoCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentName())){
			cb.andLike("agentName", merchantInfoCondition.getAgentName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantInfoCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantName())){
			cb.andLike("merchantName", merchantInfoCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getShortName())){
			cb.andLike("shortName", merchantInfoCondition.getShortName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPlatformMerchantNo())){
			cb.andEQ("platformMerchantNo", merchantInfoCondition.getPlatformMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getBusType())){
			cb.andEQ("busType", merchantInfoCondition.getBusType());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getName())){
			cb.andEQ("name", merchantInfoCondition.getName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdCard())){
			cb.andEQ("idCard", merchantInfoCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantInfoCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantInfoCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantInfoCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMobile())){
			cb.andEQ("mobile", merchantInfoCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPhone())){
			cb.andEQ("phone", merchantInfoCondition.getPhone());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getEmail())){
			cb.andEQ("email", merchantInfoCondition.getEmail());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAddress())){
			cb.andEQ("address", merchantInfoCondition.getAddress());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantInfoCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantInfoCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxNo())){
			cb.andEQ("taxNo", merchantInfoCondition.getTaxNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxImg())){
			cb.andEQ("taxImg", merchantInfoCondition.getTaxImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getOrgNo())){
			cb.andEQ("orgNo", merchantInfoCondition.getOrgNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getQrCode())){
			cb.andEQ("qrCode", merchantInfoCondition.getQrCode());
		}
		if(null != merchantInfoCondition.getAuthenStatus()){
			cb.andEQ("authenStatus", merchantInfoCondition.getAuthenStatus());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getStatus())){
			cb.andEQ("status", merchantInfoCondition.getStatus());
		}
		if(null != merchantInfoCondition.getCreateTime()){
			cb.andEQ("createTime", merchantInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getOperator())){
			cb.andEQ("operator", merchantInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getRemark())){
			cb.andLike("remark", merchantInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp1())){
			cb.andEQ("temp1", merchantInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp2())){
			cb.andEQ("temp2", merchantInfoCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp3())){
			cb.andEQ("temp3", merchantInfoCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp4())){
			cb.andEQ("temp4", merchantInfoCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantInfoCondition.getNodeSeq())){
			cb.addParam("identityFlag", IdentityType.Identity_Merchant.getCode());
			cb.addParam("nodeSeq", merchantInfoCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantInfoDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	@Override
	public MerchantInfo findById(String id){
		return merchantInfoDAO.get(id);
	}
	
	/**
	 * 新增
	 * @param merchantInfoCondition
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	@Override
	public long insert(MerchantInfoCondition merchantInfoCondition){
		log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfoCondition)+"]######");
		MerchantInfo merchantInfo = new MerchantInfo();
		BeanUtils.copyProperties(merchantInfoCondition, merchantInfo);
		merchantInfo.setRecordStatus(ScanCodeConstants.RECORD_STATUS_YES);
		long result = merchantInfoDAO.insert(merchantInfo);
		try {
			List<MerchantInfo> list = this.findAll(merchantInfoCondition);
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+list.get(0).getMerchantNo()), list.get(0));
		} catch (Exception e) {
			log.error("#######保存商户数据到redis失败######");
		}
		return result;
	}

	/**
	 * 删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	@Override
	@Transactional
	public Map<String, String> deleteById(String id){
		MerchantInfo merchantInfo = this.findById(id);
		String merchantNo = merchantInfo.getMerchantNo();
		HashMap<String, String> returnMap = new HashMap<>();
		
		//删除商户前检测商户是否当日产生资金交易，如果产生将不允许删除
		OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
		orderPaymentCondition.setMerchantNo(merchantNo);
		orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_SUCCESS.getCode());
		List<OrderPayment> orderlist = orderPaymentService.findAll(orderPaymentCondition);
		if (orderlist.size() > 0) {
			returnMap.put("returnCode", ScanCodeErrorCode.TRADE_200006.getCode());
			returnMap.put("returnMsg", ScanCodeErrorCode.TRADE_200006.getDesc());
			return returnMap;
		}
		
		log.info("删除商户操作:" + JSONSerializer.toJSON(merchantInfo));
		if(Strings.isNotEmpty(merchantNo) && 
				!(MerchantStatus.MERCHANT_STATUS_3.getCode().equals(merchantInfo.getStatus()) ||
				MerchantStatus.MERCHANT_STATUS_5.getCode().equals(merchantInfo.getStatus()))){
			NodeRelationCondition dCondition = new NodeRelationCondition();
			dCondition.setCurrentNode(merchantNo);
			nodeRelationService.deleteByCurrentNode(dCondition);//删除商户节点数据
			
			CriteriaBuilder cbChannelAdmin = Cnd.builder(ChannelAdmin.class);
			cbChannelAdmin.andEQ("merchantNo", merchantNo);
			Criteria bcChannelAdmin = cbChannelAdmin.buildCriteria();
			channelAdminService.deleteByCriteria(bcChannelAdmin);//删除商户账户数据
			
			MerchantStore merchantStore = merchantStoreService.findByMerchantNo(merchantNo);
			if(merchantStore!=null){
				merchantStoreService.deleteById(merchantStore.getId());//删除商户门店数据
			}
			
			MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
			merchantPaywayCondition.setMerchantNo(merchantNo);
			merchantPaywayService.deleteByCriteria(merchantPaywayCondition);//删除商户费率数据
			
			MerchantBankcard merchantBankcard = merchantBankcardService.findByMerchantNo(merchantNo);
			if(merchantBankcard != null){
				merchantBankcardService.deleteById(merchantBankcard.getId());//删除商户银行卡数据
			}
			
			platformQrcodeService.updateByMerchantNo(merchantNo);//删除商户更新二维码状态
			
			AgentBase agentBase = agentBaseService.findByAgentNo(merchantInfo.getAgentNo());
			agentBase.setUseTotal(agentBase.getUseTotal()-1);
			agentBase.setLessTotal(agentBase.getLessTotal()+1);
			AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
			BeanUtils.copyProperties(agentBase, agentBaseCondition);
			agentBaseService.update(agentBaseCondition);//删除商户更新代理商二维码数量
			
			merchantInfoDAO.deleteById(id);//删除商户
		}else if(Strings.isNotEmpty(merchantNo)){
			ChannelAdminCondition channelAdminCondition = new ChannelAdminCondition();
			channelAdminCondition.setMerchantNo(merchantNo);
			List<ChannelAdmin> list = channelAdminService.findAll(channelAdminCondition);
			for (ChannelAdmin channelAdmin : list) {
				channelAdminService.deleteById(channelAdmin.getId());
			}
			log.info("删除的商户编号：" + merchantNo + "，账户："+JSONSerializer.toJSON(list));
			
			platformQrcodeService.updateByMerchantNo(merchantNo);//删除商户更新二维码状态
			
			AgentBase agentBase = agentBaseService.findByAgentNo(merchantInfo.getAgentNo());
			agentBase.setUseTotal(agentBase.getUseTotal()-1);
			agentBase.setLessTotal(agentBase.getLessTotal()+1);
			AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
			BeanUtils.copyProperties(agentBase, agentBaseCondition);
			agentBaseService.update(agentBaseCondition);//删除商户更新代理商二维码数量
			
			//删除商户收银员
			CriteriaBuilder cbCashier = Cnd.builder(MerchantCashier.class);
			cbCashier.andEQ("merchantNo", merchantNo);
			Criteria buildCriteriaCashier = cbCashier.buildCriteria();
			MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
			merchantCashierCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_NO);
			merchantCashierCondition.setStatus(ScanCodeConstants.STATUS_DISABLE);
			merchantCashierService.updateByCriteria(merchantCashierCondition,buildCriteriaCashier);
			//删除商户收银员二维码
			CriteriaBuilder cbCashierQr = Cnd.builder(MerchantCashier.class);
			cbCashierQr.andEQ("merchantNo", merchantNo);
			Criteria buildCriteriaCashierQr = cbCashierQr.buildCriteria();
			MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
			merchantCashierQrCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_NO);
			merchantCashierQrCondition.setStatus(ScanCodeConstants.STATUS_DISABLE);
			merchantCashierQrService.updateByCriteria(merchantCashierQrCondition,buildCriteriaCashierQr);
			
			//删除商户支付方式
			CriteriaBuilder cbPayway = Cnd.builder(MerchantPayway.class);
			cbPayway.andEQ("merchantNo", merchantNo);
			Criteria buildCriteriaPayway = cbPayway.buildCriteria();
			MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
			merchantPaywayCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_NO);
			merchantPaywayCondition.setStatus(ScanCodeConstants.STATUS_DISABLE);
			merchantPaywayService.updateByCriteria(merchantPaywayCondition,buildCriteriaPayway);
			//删除商户子二维码
			CriteriaBuilder cbQrcode = Cnd.builder(MerchantQrcode.class);
			cbQrcode.andEQ("merchantNo", merchantNo);
			Criteria buildCriteriaQrcode = cbQrcode.buildCriteria();
			MerchantQrcodeCondition merchantQrcodeCondition = new MerchantQrcodeCondition();
			merchantQrcodeCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_NO);
			merchantQrcodeCondition.setQrStatus(ScanCodeConstants.STATUS_DISABLE);
			merchantQrcodeService.updateByCriteria(merchantQrcodeCondition,buildCriteriaQrcode);
			//删除商户子二维码商品
			CriteriaBuilder cbGoods = Cnd.builder(QrcodeGoods.class);
			cbGoods.andEQ("merchantNo", merchantNo);
			Criteria buildCriteriaGoods = cbGoods.buildCriteria();
			QrcodeGoodsCondition qrcodeGoodsCondition = new QrcodeGoodsCondition();
			qrcodeGoodsCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_NO);
			qrcodeGoodsService.updateByCriteria(qrcodeGoodsCondition,buildCriteriaGoods);			
			//删除商户活动
			CriteriaBuilder cbAc = Cnd.builder(MerchantActivity.class);
			cbAc.andEQ("merchantNo", merchantNo);
			Criteria buildCriteriaAc = cbAc.buildCriteria();
			MerchantActivityCondition merchantActivityCondition = new MerchantActivityCondition();
			merchantActivityCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_NO);
			merchantActivityCondition.setStatus(ScanCodeConstants.STATUS_DISABLE);
			merchantActivityService.updateByCriteria(merchantActivityCondition,buildCriteriaAc);
			//删除商户银行卡
			CriteriaBuilder cbBankcard = Cnd.builder(MerchantBankcard.class);
			cbBankcard.andEQ("merchantNo", merchantNo);
			Criteria buildCriteriaBankcard = cbBankcard.buildCriteria();
			MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
			merchantBankcardCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_NO);
			merchantBankcardCondition.setStatus(ScanCodeConstants.STATUS_DISABLE);
			merchantBankcardService.updateByCriteria(merchantBankcardCondition,buildCriteriaBankcard);
			//删除商户门店
			CriteriaBuilder cbStore = Cnd.builder(MerchantStore.class);
			cbStore.andEQ("merchantNo", merchantNo);
			Criteria buildCriteriaStore = cbStore.buildCriteria();
			MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
			merchantStoreCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_NO);
			merchantStoreService.updateByCriteria(merchantStoreCondition,buildCriteriaStore);		
			
			merchantInfo.setMobile("");
			merchantInfo.setRecordStatus(ScanCodeConstants.RECORD_STATUS_NO);
			merchantInfo.setStatus(MerchantStatus.MERCHANT_STATUS_5.getCode());
			MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
			BeanUtils.copyProperties(merchantInfo, merchantInfoCondition);
			this.update(merchantInfoCondition);
		}
		returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_000000.getCode());
		return returnMap;
	}
	
	/**
	 * 条件删除
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	@Override
	public long deleteByCriteria(MerchantInfoCondition merchantInfoCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		if(!Strings.isEmpty(merchantInfoCondition.getId())){
			cb.andEQ("id", merchantInfoCondition.getId());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantInfoCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelName())){
			cb.andEQ("channelName", merchantInfoCondition.getChannelName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantInfoCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentName())){
			cb.andEQ("agentName", merchantInfoCondition.getAgentName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantInfoCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantName())){
			cb.andEQ("merchantName", merchantInfoCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getShortName())){
			cb.andEQ("shortName", merchantInfoCondition.getShortName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPlatformMerchantNo())){
			cb.andEQ("platformMerchantNo", merchantInfoCondition.getPlatformMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getBusType())){
			cb.andEQ("busType", merchantInfoCondition.getBusType());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getName())){
			cb.andEQ("name", merchantInfoCondition.getName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdCard())){
			cb.andEQ("idCard", merchantInfoCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantInfoCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantInfoCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantInfoCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMobile())){
			cb.andEQ("mobile", merchantInfoCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPhone())){
			cb.andEQ("phone", merchantInfoCondition.getPhone());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getEmail())){
			cb.andEQ("email", merchantInfoCondition.getEmail());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAddress())){
			cb.andEQ("address", merchantInfoCondition.getAddress());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantInfoCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantInfoCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxNo())){
			cb.andEQ("taxNo", merchantInfoCondition.getTaxNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxImg())){
			cb.andEQ("taxImg", merchantInfoCondition.getTaxImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getOrgNo())){
			cb.andEQ("orgNo", merchantInfoCondition.getOrgNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getQrCode())){
			cb.andEQ("qrCode", merchantInfoCondition.getQrCode());
		}
		if(null != merchantInfoCondition.getAuthenStatus()){
			cb.andEQ("authenStatus", merchantInfoCondition.getAuthenStatus());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getStatus())){
			cb.andEQ("status", merchantInfoCondition.getStatus());
		}
		if(null != merchantInfoCondition.getCreateTime()){
			cb.andEQ("createTime", merchantInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getOperator())){
			cb.andEQ("operator", merchantInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getRemark())){
			cb.andLike("remark", merchantInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp1())){
			cb.andEQ("temp1", merchantInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp2())){
			cb.andEQ("temp2", merchantInfoCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp3())){
			cb.andEQ("temp3", merchantInfoCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp4())){
			cb.andEQ("temp4", merchantInfoCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantInfo merchantInfo = new MerchantInfo();
		BeanUtils.copyProperties(merchantInfoCondition, merchantInfo);
		return merchantInfoDAO.deleteByCriteria(buildCriteria);
	}
	
	/**
	 * 行数汇总
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	@Override
	public long countByCriteria(MerchantInfoCondition merchantInfoCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		if(!Strings.isEmpty(merchantInfoCondition.getId())){
			cb.andEQ("id", merchantInfoCondition.getId());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantInfoCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelName())){
			cb.andEQ("channelName", merchantInfoCondition.getChannelName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantInfoCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentName())){
			cb.andEQ("agentName", merchantInfoCondition.getAgentName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantInfoCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantName())){
			cb.andEQ("merchantName", merchantInfoCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getShortName())){
			cb.andEQ("shortName", merchantInfoCondition.getShortName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPlatformMerchantNo())){
			cb.andEQ("platformMerchantNo", merchantInfoCondition.getPlatformMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getBusType())){
			cb.andEQ("busType", merchantInfoCondition.getBusType());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getName())){
			cb.andEQ("name", merchantInfoCondition.getName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdCard())){
			cb.andEQ("idCard", merchantInfoCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantInfoCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantInfoCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantInfoCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMobile())){
			cb.andEQ("mobile", merchantInfoCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPhone())){
			cb.andEQ("phone", merchantInfoCondition.getPhone());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getEmail())){
			cb.andEQ("email", merchantInfoCondition.getEmail());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAddress())){
			cb.andEQ("address", merchantInfoCondition.getAddress());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantInfoCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantInfoCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxNo())){
			cb.andEQ("taxNo", merchantInfoCondition.getTaxNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxImg())){
			cb.andEQ("taxImg", merchantInfoCondition.getTaxImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getOrgNo())){
			cb.andEQ("orgNo", merchantInfoCondition.getOrgNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getQrCode())){
			cb.andEQ("qrCode", merchantInfoCondition.getQrCode());
		}
		if(null != merchantInfoCondition.getAuthenStatus()){
			cb.andEQ("authenStatus", merchantInfoCondition.getAuthenStatus());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getStatus())){
			cb.andEQ("status", merchantInfoCondition.getStatus());
		}
		if(null != merchantInfoCondition.getCreateTime()){
			cb.andEQ("createTime", merchantInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getOperator())){
			cb.andEQ("operator", merchantInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getRemark())){
			cb.andLike("remark", merchantInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp1())){
			cb.andEQ("temp1", merchantInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp2())){
			cb.andEQ("temp2", merchantInfoCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp3())){
			cb.andEQ("temp3", merchantInfoCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp4())){
			cb.andEQ("temp4", merchantInfoCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantInfoCondition.getNodeSeq())){
			cb.addParam("identityFlag", IdentityType.Identity_Merchant.getCode());
			cb.addParam("nodeSeq", merchantInfoCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantInfo merchantInfo = new MerchantInfo();
		BeanUtils.copyProperties(merchantInfoCondition, merchantInfo);
		return merchantInfoDAO.countByCriteria(buildCriteria);
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	@Override
	public long update(MerchantInfoCondition merchantInfoCondition){
		log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfoCondition)+"]######");
		MerchantInfo merchantInfo = new MerchantInfo();
		BeanUtils.copyProperties(merchantInfoCondition, merchantInfo);
		long result = merchantInfoDAO.update(merchantInfo);
		try {
			List<MerchantInfo> list = this.findAll(merchantInfoCondition);
			redisSharedCache.del(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+list.get(0).getMerchantNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+list.get(0).getMerchantNo()), list.get(0));
		} catch (Exception e) {
			log.error("#######保存商户数据到redis失败######");
		}
		return result;
	}
	
	/**
	 * 更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	@Override
	public long updateByAudit(MerchantInfoCondition merchantInfoCondition){
		MerchantInfo merchantInfo = new MerchantInfo();
		
		//审核通过发送短信
		if (merchantInfoCondition.getStatus().equals(MerchantStatus.MERCHANT_STATUS_3.getCode())) {
			log.info("#########merchant["+merchantInfoCondition.getMerchantNo()+"]审核通过发送短信########");
			//短信通知商户
			String url = "";
			DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(merchantInfoCondition.getMerchantNo());
			String organNo = "";
			if (null != vo) {
				organNo = vo.getOrganNo();
			}else {					
				organNo = merchantInfo.getChannelNo();
			}
			
			ChannelExpand channelExpand = channelExpandService.findByChannelNo(organNo);
			if (null != channelExpand.getDomainName() && !Strings.isEmpty(channelExpand.getDomainName())) {
				url = channelExpand.getDomainName();//使用客户自定义域名
			}else {
				url = channelExpand.getChannelCode()+".qd.huaepay.com";//使用我们自动装配的域名
			}
			
//			String content = "您的商户入驻申请已审核通过。用户名:"+merchantInfoCondition.getMobile()+"，初始密码："+merchantInfoCondition.getTemp1()+"，请尽快登录并修改初始密码！请不要把账户信息泄露给其他人,如有疑问请联系："+channelExpand.getPhone()+"。如非本人操作，可不用理会！";
			String content = "您的商户入驻申请已审核通过。您可通过电脑使用"+url+"地址登录，用户名:"+merchantInfoCondition.getMobile()+"，初始密码："+merchantInfoCondition.getTemp1()+"，请尽快登录并修改初始密码！请不要把账户信息泄露给其他人。如非本人操作，可不用理会！";
			log.info("########商户入驻成功，给["+merchantInfoCondition.getMobile()+"]发送内容["+content+"]#######");
			try {
//				smsService.sendSms(content, merchantInfoCondition.getMobile());
//				userSmsService.sendSms(content, merchantInfoCondition.getMobile(), merchantInfoCondition.getMerchantNo());
			} catch (Exception e) {
				log.error("#########merchant["+merchantInfoCondition.getMerchantNo()+"]审核通过发送短信失败########");
				e.printStackTrace();
			}
			merchantInfo.setStatus(MerchantStatus.MERCHANT_STATUS_6.getCode());//平台审核通过后改为第三方审核中，等待对方进行商户入驻回调
		}
		
		BeanUtils.copyProperties(merchantInfoCondition, merchantInfo);
		long result = merchantInfoDAO.update(merchantInfo);
		try {
			log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfoCondition)+"]######");
			List<MerchantInfo> list = this.findAll(merchantInfoCondition);
			redisSharedCache.del(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantInfoCondition.getMerchantNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantInfoCondition.getMerchantNo()), list.get(0));
		} catch (Exception e) {
			log.error("#######保存商户数据到redis失败######");
		}
		return result;
	}
	
	
	/**
	 * 条件更新
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	@Override
	public long updateByCriteria(MerchantInfoCondition merchantInfoCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		if(!Strings.isEmpty(merchantInfoCondition.getId())){
			cb.andEQ("id", merchantInfoCondition.getId());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantInfoCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelName())){
			cb.andEQ("channelName", merchantInfoCondition.getChannelName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentNo())){
			cb.andEQ("agentNo", merchantInfoCondition.getAgentNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentName())){
			cb.andEQ("agentName", merchantInfoCondition.getAgentName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantInfoCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantName())){
			cb.andEQ("merchantName", merchantInfoCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getShortName())){
			cb.andEQ("shortName", merchantInfoCondition.getShortName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPlatformMerchantNo())){
			cb.andEQ("platformMerchantNo", merchantInfoCondition.getPlatformMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getBusType())){
			cb.andEQ("busType", merchantInfoCondition.getBusType());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getName())){
			cb.andEQ("name", merchantInfoCondition.getName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdCard())){
			cb.andEQ("idCard", merchantInfoCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantInfoCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantInfoCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantInfoCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMobile())){
			cb.andEQ("mobile", merchantInfoCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPhone())){
			cb.andEQ("phone", merchantInfoCondition.getPhone());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getEmail())){
			cb.andEQ("email", merchantInfoCondition.getEmail());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAddress())){
			cb.andEQ("address", merchantInfoCondition.getAddress());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantInfoCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantInfoCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxNo())){
			cb.andEQ("taxNo", merchantInfoCondition.getTaxNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxImg())){
			cb.andEQ("taxImg", merchantInfoCondition.getTaxImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getOrgNo())){
			cb.andEQ("orgNo", merchantInfoCondition.getOrgNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getQrCode())){
			cb.andEQ("qrCode", merchantInfoCondition.getQrCode());
		}
		if(null != merchantInfoCondition.getAuthenStatus()){
			cb.andEQ("authenStatus", merchantInfoCondition.getAuthenStatus());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getStatus())){
			cb.andEQ("status", merchantInfoCondition.getStatus());
		}
		if(null != merchantInfoCondition.getCreateTime()){
			cb.andEQ("createTime", merchantInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getOperator())){
			cb.andEQ("operator", merchantInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getRemark())){
			cb.andLike("remark", merchantInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp1())){
			cb.andEQ("temp1", merchantInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp2())){
			cb.andEQ("temp2", merchantInfoCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp3())){
			cb.andEQ("temp3", merchantInfoCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp4())){
			cb.andEQ("temp4", merchantInfoCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		MerchantInfo merchantInfo = new MerchantInfo();
		BeanUtils.copyProperties(merchantInfoCondition, merchantInfo);
		return merchantInfoDAO.updateByCriteria(merchantInfo,buildCriteria);
	}
	
	/**
	 * 状态更新
	 *
	 * @author husain
	 * @date CreateDate : 2016-10-21 14:04:47
	 */
	@Override
	public long updateStatus(String id,String status){
		return merchantInfoDAO.updateStatus(id,status);
	}	
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 商户编码查找
	 * @author: Ricky
	 * @param MerchantNo
	 * @return: MerchantInfo
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public MerchantInfo findByMerchantNo(String MerchantNo){
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		cb.andEQ("merchantNo", MerchantNo);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantInfoDAO.findOneByCriteria(buildCriteria);
	}
	/**
	 * 
	 * @Title: updateByMerchantNo
	 * @Description: 商户申请录入商户信息
	 * @param condition
	 * @return
	 */
	@Override
	public long updateByMerchantNo(MerchantInfoCondition condition) {
		
		// TODO Auto-generated method stub
		//检查是否需要更新
		if(condition.isCheck()){//更新检查
			try {
				if(!isUpdate(condition)){
					return 1;
				}
			} catch (Exception e) {
				log.error("updateByMerchantNo get imag from wechat error...",e);
				e.printStackTrace();
			}
		}
		MerchantInfo merchantInfo = new MerchantInfo();
		BeanUtils.copyProperties(condition, merchantInfo);
		//merchantInfo.setShortName(condition.getMerchantName());
		//merchantInfo.setStatus("6");
		merchantInfo.setAuthenStatus(0);
		long result = merchantInfoDAO.updateByMerchantNo(merchantInfo);
		try {
			log.info("#######merchantInfoCondition["+JSONObject.fromObject(condition)+"]######");
			List<MerchantInfo> list = this.findAll(condition);
			redisSharedCache.del(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+condition.getMerchantNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+condition.getMerchantNo()), list.get(0));
		} catch (Exception e) {
			log.error("#######保存商户数据到redis失败######");
		}
		return result;
	}
	
	private boolean isUpdate(MerchantInfoCondition condition) throws Exception{
		String filePath = ScanCodeConstants.FILE_UPLOAD_ROOT_PATH+ScanCodeConstants.SPT+
				ScanCodeConstants.WECHAT_IMGUPLOAD_TYPE+ScanCodeConstants.SPT+condition.getMerchantNo()+ScanCodeConstants.SPT;
		boolean isUpdate = false;
		MerchantInfo merchantInfo = findByMerchantNo(condition.getMerchantNo());
		if(!condition.getName().equals(merchantInfo.getName())){
			isUpdate = true;
		}
//		if(!condition.getMerchantName().equals(merchantInfo.getMerchantName())){
//			isUpdate = true;
//		}
		if(!condition.getIdCard().equals(merchantInfo.getIdCard())){
			isUpdate = true;
		}
		if(!condition.getIdcardImg1().equals(merchantInfo.getIdcardImg1())){
			log.info("condition.getIdcardImg1() == "+condition.getIdcardImg1());
			String keyTail = RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getKey()+":"+condition.getMerchantNo()+ConfigPreCode.KEY_IDCARDIMG1;
			RedisKey imgKey = new RedisKey(RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getGroup(), keyTail);
			redisSharedCache.set(imgKey, condition.getIdcardImg1(),ConfigPreCode.KEY_LIVE_TIME);
			String img = downLoadImg(condition.getIdcardImg1(), filePath, merchantInfo.getAgentNo());
			condition.setIdcardImg1(img);
			isUpdate = true;
		}
		if(!condition.getIdcardImg2().equals(merchantInfo.getIdcardImg2())){
			log.info("condition.getIdcardImg2() == "+condition.getIdcardImg2());
			String keyTail = RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getKey()+":"+condition.getMerchantNo()+ConfigPreCode.KEY_IDCARDIMG2;
			RedisKey imgKey = new RedisKey(RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getGroup(), keyTail);
			redisSharedCache.set(imgKey, condition.getIdcardImg2(),ConfigPreCode.KEY_LIVE_TIME);
			String img = downLoadImg(condition.getIdcardImg2(), filePath, merchantInfo.getAgentNo());
			condition.setIdcardImg2(img);
			isUpdate = true;
		}
		if(!condition.getIdcardImg3().equals(merchantInfo.getIdcardImg3())){
			log.info("condition.getIdcardImg3() == "+condition.getIdcardImg3());
			String keyTail = RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getKey()+":"+condition.getMerchantNo()+ConfigPreCode.KEY_IDCARDIMG3;
			RedisKey imgKey = new RedisKey(RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getGroup(), keyTail);
			redisSharedCache.set(imgKey, condition.getIdcardImg3(),ConfigPreCode.KEY_LIVE_TIME);
			String img = downLoadImg(condition.getIdcardImg3(), filePath, merchantInfo.getAgentNo());
			condition.setIdcardImg3(img);
			isUpdate = true;
		}
		return isUpdate;
	}

	/**
	 * 列表
	 * @param merchantInfo 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	@Override
	public List<MerchantInfo> findAllByAgentNo(MerchantInfoCondition merchantInfoCondition,List<String> list){
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		if(!Strings.isEmpty(merchantInfoCondition.getId())){
			cb.andEQ("id", merchantInfoCondition.getId());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelNo())){
			cb.andEQ("channelNo", merchantInfoCondition.getChannelNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getChannelName())){
			cb.andLike("channelName", merchantInfoCondition.getChannelName());
		}
		if(list != null && list.size() > 0){
			cb.andIn("agentNo", list);
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAgentName())){
			cb.andLike("agentName", merchantInfoCondition.getAgentName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantInfoCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantName())){
			cb.andLike("merchantName", merchantInfoCondition.getMerchantName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getShortName())){
			cb.andLike("shortName", merchantInfoCondition.getShortName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPlatformMerchantNo())){
			cb.andEQ("platformMerchantNo", merchantInfoCondition.getPlatformMerchantNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getBusType())){
			cb.andEQ("busType", merchantInfoCondition.getBusType());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getName())){
			cb.andEQ("name", merchantInfoCondition.getName());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdCard())){
			cb.andEQ("idCard", merchantInfoCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantInfoCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantInfoCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantInfoCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMobile())){
			cb.andEQ("mobile", merchantInfoCondition.getMobile());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getPhone())){
			cb.andEQ("phone", merchantInfoCondition.getPhone());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getEmail())){
			cb.andEQ("email", merchantInfoCondition.getEmail());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getAddress())){
			cb.andEQ("address", merchantInfoCondition.getAddress());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantInfoCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantInfoCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxNo())){
			cb.andEQ("taxNo", merchantInfoCondition.getTaxNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTaxImg())){
			cb.andEQ("taxImg", merchantInfoCondition.getTaxImg());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getOrgNo())){
			cb.andEQ("orgNo", merchantInfoCondition.getOrgNo());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getQrCode())){
			cb.andEQ("qrCode", merchantInfoCondition.getQrCode());
		}
		if(null != merchantInfoCondition.getAuthenStatus()){
			cb.andEQ("authenStatus", merchantInfoCondition.getAuthenStatus());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getStatus())){
			cb.andEQ("status", merchantInfoCondition.getStatus());
		}
		if(null != merchantInfoCondition.getCreateTime()){
			cb.andEQ("createTime", merchantInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getOperator())){
			cb.andEQ("operator", merchantInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantInfoCondition.getRemark())){
			cb.andLike("remark", merchantInfoCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp1())){
			cb.andEQ("temp1", merchantInfoCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp2())){
			cb.andEQ("temp2", merchantInfoCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp3())){
			cb.andEQ("temp3", merchantInfoCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantInfoCondition.getTemp4())){
			cb.andEQ("temp4", merchantInfoCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantInfoCondition.getNodeSeq())){
			cb.addParam("identityFlag", IdentityType.Identity_Merchant.getCode());
			cb.addParam("nodeSeq", merchantInfoCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantInfoDAO.findByCriteria(buildCriteria);
	}
	
	
	/**下载文件到本地**/
	@Override
	public String downLoadImg(String mediaId, String filePath,String organNo) {
		//检测文件目录是否存在
		File fileDir = new File(filePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		String accessToken;
		try {
			accessToken = merchantBusinessService.getAccessToken(organNo);
			log.info("download img accessToken is "+accessToken+" ===============");
			ApiMappingDicionCondition condition = new ApiMappingDicionCondition();
			condition.setKeyNo(HfepayConfig.WDOWNLOADURL_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(condition);
			if(dic==null||Strings.isEmpty(dic.getParaVal())){
				throw new RuntimeException("getJsApiTicket 获取 access_token url失败");
			}
			//https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APP_ID&secret=SECRET
			String downUrl= dic.getParaVal();
			log.info("download img downUrl is "+downUrl);
			//String downUrl="https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
			downUrl = downUrl.replace("ACCESS_TOKEN", accessToken);//.replace("MEDIA_ID", mediaId);
			return getImgUrl(downUrl, mediaId,filePath);
		} catch (Exception e) {
			log.error("downLoadImg error...",e);
			e.printStackTrace();
		}
		return null;
	}
	
	private String getImgUrl(String downUrl,String mediaId,String filePath){
		log.info("download img getImgUrl mediaId is "+mediaId+" =============== filepath="+filePath);
		if(Strings.isEmpty(mediaId)){
			return null;
		}
		String imgUrl = "";
		String fileName=Strings.getUUID()+ScanCodeConstants.IMG_EXTEND;
		downUrl = downUrl.replace("MEDIA_ID", mediaId);
		try {
			 byte[] stream = HttpRequestClient.doGet(downUrl);
			 PhotoUtil.writeFile(stream, filePath, fileName);//文件到硬盘
			 imgUrl= filePath+fileName;//返回nginx映射的文件路径，供查看
		} catch (Exception e) {
			log.error("read file failed.......",e);
			e.printStackTrace();
		}
		return imgUrl;
	}
	
	@Override
	public String freshImg(String merchantNo, String type) {
		MerchantInfo info = findByMerchantNo(merchantNo);
		String filePath = ScanCodeConstants.FILE_UPLOAD_ROOT_PATH+ScanCodeConstants.SPT+
				ScanCodeConstants.WECHAT_IMGUPLOAD_TYPE+ScanCodeConstants.SPT+info.getMerchantNo()+ScanCodeConstants.SPT;
		String key = ":"+type;
		String keyTail = RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getKey()+":"+info.getMerchantNo()+key;
		RedisKey imgKey = new RedisKey(RedisKeyEnum.MERCHANT_IMAGE_WECHAT.getGroup(), keyTail);
		String mediaId = "";
		try {
			mediaId = redisSharedCache.get(imgKey);
			log.info("=========================freshImg get mediaId is "+mediaId+"==============================");
		} catch (Exception e) {
			log.error("freshImg error get img mediaid from redis error.....");
			throw new RuntimeException(e);
		}
		//redis到期了查不到用户
		if(Strings.isEmpty(mediaId)){
			throw new RuntimeException("redis get merdiaId is null,maybe it was out of time");
		}
		String img = downLoadImg(mediaId, filePath, info.getAgentNo());
		//修改图片
		if(key.equals(ConfigPreCode.KEY_IDCARDIMG1)){//身份证
			info.setIdcardImg1(img);
			updateMerchantImg(info);
		}
		else if(key.equals(ConfigPreCode.KEY_IDCARDIMG2)){//身份证
			info.setIdcardImg2(img);
			updateMerchantImg(info);
		}
		else if(key.equals(ConfigPreCode.KEY_IDCARDIMG3)){//身份证
			info.setIdcardImg3(img);
			updateMerchantImg(info);		
		}
		else{//门店信息
			MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
			merchantStoreCondition.setMerchantNo(info.getMerchantNo());
			if(key.equals(ConfigPreCode.KEY_GROUPPHONEIMG)){//门店
				merchantStoreCondition.setGroupPhotoImg(img);
				updateStoreImg(merchantStoreCondition);
			}
			else if(key.equals(ConfigPreCode.KEY_STOREIMG)){//门店
				merchantStoreCondition.setStoreImg(img);
				updateStoreImg(merchantStoreCondition);
			}
			else if(key.equals(ConfigPreCode.KEY_STOREPHONESIMG)){//门店
				merchantStoreCondition.setStorePhotosImg(img);
				updateStoreImg(merchantStoreCondition);
			}
			else if(key.equals(ConfigPreCode.KEY_MERCHANTLICENSEIMG)){//门店，同时修改商户的执照信息
				merchantStoreCondition.setMerchantLicenseImg(img);//门店执照
				updateStoreImg(merchantStoreCondition);
				
				info.setMerchantLicenseImg(img);//商户回调
				updateMerchantImg(info);
			}
		}
		return img;
	}
	
	/**
	 * 更新商户图片
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	public long updateMerchantImg(MerchantInfo merchantInfo){
		return merchantInfoDAO.update(merchantInfo);
	}
	
	/**
	 * 更新门店图片
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-20 17:39:26
	 */
	public long updateStoreImg(MerchantStoreCondition merchantStoreCondition){
		return merchantStoreService.updateByMerchantNo(merchantStoreCondition);
	}
		
	/**设置商户redis
	 * @throws Exception */
	@Override
	public void setMerchantRedis() throws Exception{
		MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
		List<MerchantInfo> list = this.findAll(merchantInfoCondition);
		for (MerchantInfo merchantInfo : list) {
			redisSharedCache.del(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantInfo.getMerchantNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantInfo.getMerchantNo()), merchantInfo);
		}
	}
	
	/**
	 * 商户入网信息变更
	 * @param info 商户基本信息
	 * @return
	 * 			status	VARCHAR(2)	是	状态	1:成功，0:失败
				msg	VARCHAR(100)	是	提示信息	

	 * @throws Exception
	 * @author lemon
	 */
	@Override
	public Map<String, String> merchantJoinChange(MerchantInfoCondition merchantInfoCondition) throws Exception{
		MerchantInfo MerchantInfo = this.findById(merchantInfoCondition.getId());
		MerchantInfoChangeVo info = new MerchantInfoChangeVo();
		info.setMerchantNo(MerchantInfo.getPlatformMerchantNo());
		info.setMerchantName(merchantInfoCondition.getMerchantName());
		info.setShortName(merchantInfoCondition.getShortName());
		info.setAddress(merchantInfoCondition.getAddress());
		info.setSerPhone(merchantInfoCondition.getPhone());
		info.setCategory(merchantInfoCondition.getBusType());
		info.setPhone(merchantInfoCondition.getPhone());
		info.setMobile(merchantInfoCondition.getMobile());
		info.setEmail(merchantInfoCondition.getEmail());
		info.setMerchantLicense(merchantInfoCondition.getMerchantLicense());
		return merchantBusinessService.merchantJoinChange(info);
	}
	
	/**商户审核（平台审核）
	 * @throws Exception */
	@Override
	public JSON auditPlatform(MerchantInfoCondition merchantInfoCondition,String isRealAccount,String storeNo) throws Exception{
		Map<Object, Object> map = new HashMap<Object, Object>();
		map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.SUCCESS,ScanCodeConstants.VALUES,ScanCodeConstants.SUCCESS_MSG,"url","/adminManage/merchantinfo");
		
		boolean flag = false;
		//商户随机密码
		String merchantPassword = String.valueOf((int)((Math.random()*9+1)*100000));
		
		MerchantInfo entity = this.findById(merchantInfoCondition.getId());
		entity.setOperator(merchantInfoCondition.getOperator());
		
		//获取银行卡列表
		MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
		merchantBankcardCondition.setMerchantNo(entity.getMerchantNo());
		List<MerchantBankcard> bankcards =  merchantBankcardService.findAll(merchantBankcardCondition);
		
		if(bankcards.size() == 0 || bankcards.size() > 1){
			map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"商户银行卡信息不存在或异常（例：多个银行卡），请检查相关信息");
			return JSONSerializer.toJSON(map);
		}
		MerchantBankcard bankcard = bankcards.get(0);
		
		//平台认证通过
		if(merchantInfoCondition.getStatus().equals("3")){
			log.info("平台审核平台认证通过merchantNo："+entity.getMerchantNo());
			//校验银行卡是否开通提现
			
			if(ScanCodeConstants.ACCOUNTTYPE_PUBLIC.equals(bankcard.getAccountType())){					
				if(ScanCodeConstants.Y.equals(isRealAccount)){
					map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"账户类型为公司不支持提现");
					return JSONSerializer.toJSON(map);
				}
			}
			//商户基本信息
			entity.setBusType(merchantInfoCondition.getBusType());//经营类目编号
			entity.setAddress(merchantInfoCondition.getAddress());//地址
			
			//获取费率列表
			MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
			merchantPaywayCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_YES);
			merchantPaywayCondition.setStatus(ScanCodeConstants.STATUS_ACTIVE);
			merchantPaywayCondition.setAcceptStatus(ScanCodeConstants.FAIL_STATE);
			merchantPaywayCondition.setMerchantNo(entity.getMerchantNo());
			List<MerchantPayway> payways = merchantPaywayService.findAll(merchantPaywayCondition);
			if(payways.size() == 0){
				map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"商户费率信息不存在，请检查相关信息");
				return JSONSerializer.toJSON(map);
			}
			
			log.info("平台审核商户四要素验证merchantNo："+entity.getMerchantNo());
			//商户四要素验证
			JSON res = null;
			if(ScanCodeConstants.ACCOUNTTYPE_PRIVATE.equals(bankcard.getAccountType())){	
				res = merchantBankCard4(map, entity);
				if(res != null){
					log.error("平台审核实名认证失败merchantNo："+entity.getMerchantNo());
					return res;
				}
			}
			
			log.info("平台审核商户入网merchantNo："+entity.getMerchantNo());
			
			//更新商户账户结算是否开通提现状态
			BeanUtils.copyProperties(bankcard, merchantBankcardCondition);
			merchantBankcardCondition.setIsRealAccount(isRealAccount);
			merchantBankcardService.update(merchantBankcardCondition);
			
			//商户入网
			res = merchantJoin(map, entity, payways);
			if(res != null){
				log.error("平台审核商户入网失败merchantNo："+entity.getMerchantNo());
				return res;
			}
			log.info("平台审核更新商户帐号merchantNo："+entity.getMerchantNo());
			//更新商户帐号
			ChannelAdmin obj = channelAdminService.findByMerchantNo(entity.getMerchantNo());
			if(obj != null){
				ChannelAdminCondition channelAdminCondition = new ChannelAdminCondition();
				channelAdminCondition.setId(obj.getId());
				channelAdminCondition.setUserName(entity.getMobile());
				channelAdminCondition.setPassword(merchantPassword);
				channelAdminCondition.setChannelCode(entity.getChannelNo());
				channelAdminCondition.setAgentNo(entity.getAgentNo());
				channelAdminCondition.setMerchantNo(entity.getMerchantNo());
				channelAdminCondition.setStatus(Integer.parseInt(ScanCodeConstants.STATUS_ACTIVE));
				channelAdminCondition.setIdentityFlag(ScanCodeConstants.IDENTITYFLAG_MERCHANT);
				channelAdminCondition.setIdentityNo(entity.getMerchantNo());
				String roleId = channelRoleService.findRoleByChannelCode(ScanCodeConstants.ROLE_TYPE_MERCHANT).getId();
				channelAdminService.updateFix(channelAdminCondition, roleId,entity.getChannelNo());
			}
			
			log.info("平台审核通知商户merchantNo："+entity.getMerchantNo());
			//通知商户（微信商户）
			ChannelAdmin channelAdmin = channelAdminService.findPushMsgAdmin(entity.getMerchantNo(),true);
			if(channelAdmin != null && Strings.isNotEmpty(channelAdmin.getOpenId())){
				RegistSuccessMessage message = new RegistSuccessMessage();
				message.setTouser(channelAdmin.getOpenId());
				message.setUrl("");
				message.setRemark("");
				message.setRegistTime(new Date());
				message.setTitle("入驻成功");
				message.setCompanyName(entity.getMerchantName());
				message.setCompanyType(entity.getBusType());
				message.setAddress(entity.getAddress());
				message.setName(entity.getName());
				message.setOrganNo(entity.getAgentNo());
				message.setMobile(entity.getMobile());
				try {
					//Map<String,String> pushRes = merchantBusinessService.pushRegistSuccess(message);
					merchantBusinessService.pushRegistSuccess(message);
					//+",通知商户结果:" + pushRes.get("respDesc")\
					//delete by wh 推送消息结果不显示到前端，以免出现系统繁忙 现象
					map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.SUCCESS,ScanCodeConstants.VALUES,ScanCodeConstants.SUCCESS_MSG,"url","/adminManage/merchantinfo");
				} catch (Exception e) {
					log.error("平台审核通知商户失败merchantNo："+entity.getMerchantNo());
				}
			}
			//更新商户账户结算状态
			BeanUtils.copyProperties(bankcard, merchantBankcardCondition);
			merchantBankcardCondition.setStatus("1");
			merchantBankcardCondition.setIsRealAccount(isRealAccount);
			merchantBankcardService.update(merchantBankcardCondition);
			
			ChangeLogCondition changeLogCondition = new ChangeLogCondition();
			String tradeNo = merchantBankcardCondition.getId();//MerchantBankcard表Id关联
			String batchNo = BatchNoUtil.getBatchNo();
			changeLogCondition.setTradeNo(tradeNo);
			changeLogCondition.setBatchNo(batchNo);
			changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.MERCHANT_BANKCARD_CODE.getValue()));
			changeLogCondition.setTransName(TransCodeEnum.MERCHANT_BANKCARD_CODE.getDesc());
			changeLogCondition.setOwnersNo(merchantBankcardCondition.getMerchantNo());
			changeLogCondition.setBefore(JSONSerializer.toJSON(merchantBankcardCondition).toString());
			changeLogCondition.setStatus(ScanCodeConstants.APPROVE_STATUS_NEW);
			changeLogCondition.setOperator(merchantInfoCondition.getOperator());
			changeLogCondition.setCreateTime(new Date());
			changeLogService.insert(changeLogCondition);
			
			flag = true;
		}else if(merchantInfoCondition.getStatus().equals("4")){
			log.info("平台审核平台认证不通过merchantNo："+entity.getMerchantNo());
			flag = true;
			
			//更新商户账户结算信息
			merchantBankcardService.updateStatus(bankcard.getId(), merchantInfoCondition.getStatus());
		}
		if(Strings.isNotEmpty(storeNo)){
			log.info("平台审核更新门店merchantNo："+merchantInfoCondition.getMerchantNo()+",StoreNo:"+storeNo);
			//门店改为对应审核状态
			MerchantStoreCondition store = new MerchantStoreCondition();
			store.setStoreNo(storeNo);
			store.setStoreStatus(merchantInfoCondition.getStatus());
			store.setAuditOperator(merchantInfoCondition.getOperator());
			store.setAuditDate(new Date());
			store.setAuditReson(merchantInfoCondition.getRemark());
			merchantStoreService.updateByCriteria(store);
		}
		//只能修改为3、4（平台通过或拒绝）
		if(flag) {
			log.info("平台审核更新商户merchantNo："+merchantInfoCondition.getMerchantNo());
			merchantInfoCondition.setMerchantNo(entity.getMerchantNo());
			merchantInfoCondition.setChannelNo(entity.getChannelNo());
			merchantInfoCondition.setMobile(entity.getMobile());
			merchantInfoCondition.setTemp1(merchantPassword);
			this.updateByAudit(merchantInfoCondition);
			
			//新增审核信息表数据
			AuditLogCondition auditLogCondition = new AuditLogCondition();
			auditLogCondition.setCreateTime(new Date());
			auditLogCondition.setAuditId(entity.getId());
			auditLogCondition.setAuditType(AuditType.MERCHANT_INFO.getCode());
			auditLogCondition.setMerchantNo(entity.getMerchantNo());
			auditLogCondition.setId(Strings.getUUID());
			auditLogCondition.setAuditStatus(merchantInfoCondition.getStatus());
			auditLogCondition.setReason(merchantInfoCondition.getRemark());
			auditLogCondition.setOperator(merchantInfoCondition.getOperator());
			auditLogService.insert(auditLogCondition);
		}
		return JSONSerializer.toJSON(map);
	}
	
	
	/**
	 * 
	 * @author liushuyu
	 * Desc : 商户二要素校验
	 * DATE: 2017年6月13日
	 * @param entity
	 * @throws Exception
	 */
	@Override
	public Map<String,String> validateIdCard2(String name, String idCard) throws Exception {
		
		log.info("#####商户注册身份证二要素验证name："+ name + "idCard:" + idCard);
		String isBankCardAuth2 = ScanCodeConstants.N;
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo("BANKCARDAUTH2");
		List<MappingDicion> configMappingDicions = mappingDicionService.findAll(mappingDicionCondition);
		if(configMappingDicions.size() == 1){
			MappingDicion configMappingDicion = configMappingDicions.get(0);
			isBankCardAuth2 = configMappingDicion.getParaVal();
		}
		log.info("################二要素开关状态 ：" + isBankCardAuth2);
		if(Strings.equals(isBankCardAuth2, ScanCodeConstants.N)){//二要素开关状态为N则不进行二要素验证
			return null;
		}
		Map<String, String> resultMap = merchantBusinessService.bankCardAuth2(name, idCard);
		return resultMap;
	}
	
	
	/**
	 * 调用商户入网接口
	 * @param map 返回web的响应对象map
	 * @param entity 商户基本信息
	 * @param payways 商户费率信息对象列表
	 * 
	 * @throws Exception
	 */
	private JSON merchantBankCard4(Map<Object, Object> map, MerchantInfo entity) throws Exception {
		//获取银行卡列表
		MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
		merchantBankcardCondition.setMerchantNo(entity.getMerchantNo());
		List<MerchantBankcard> bankcards =  merchantBankcardService.findAll(merchantBankcardCondition);
		
		if(bankcards.size() == 0 || bankcards.size() > 1){
			map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"商户银行卡信息不存在或异常（例：多个银行卡），请检查相关信息");
			return JSONSerializer.toJSON(map);
		}
		MerchantBankcard bankcard = bankcards.get(0);
		//如果银行卡信息为有效，则无需四要素校验
		if(ScanCodeConstants.STATUS_ACTIVE.equals(bankcard.getStatus())){
			return null;
		}
		BankCardAuthVo auth = new BankCardAuthVo();
		auth.setRealName(bankcard.getName());
		auth.setBankCard(bankcard.getBankCard());
		auth.setIdCard(bankcard.getIdCard());
		auth.setMobile(bankcard.getMobile());
		
		Map<String,String> res = merchantBusinessService.bankCardAuth(auth,"3");
		String respCode = res.get("respCode");
		String respDesc = res.get("respDesc");
		if(ScanCodeConstants.INTERFACE_SUCCESS_STATUS.equals(respCode)){
			entity.setAuthenStatus(Integer.parseInt(ScanCodeConstants.STATUS_ACTIVE));
			bankcard.setStatus(ScanCodeConstants.STATUS_ACTIVE);
		}else{
			entity.setAuthenStatus(Integer.parseInt(ScanCodeConstants.STATUS_NOT_USE));
			bankcard.setStatus(ScanCodeConstants.STATUS_NOT_USE);
		}
		
		this.saveMerchantAutoInfo(auth,entity,respCode,respDesc);
		
		entity.setUpdateTime(new Date());
		bankcard.setOperator(entity.getOperator());
		bankcard.setUpdateTime(new Date());
		
//		String remark = "";
//		if(Strings.isNotEmpty(entity.getRemark())){
//			remark = entity.getRemark().split("==")[0];
//		}
		bankcard.setRemark("==auth--"+Dates.yyyyMMddHHmmss(new Date())+":"+respDesc);
//		entity.setRemark(remark + "==auth--"+Dates.yyyyMMddHHmmss(new Date())+":"+respDesc);
		//更新商户认证状态
		MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
		BeanUtils.copyProperties(entity, merchantInfoCondition);
		this.update(merchantInfoCondition);

		//更新银行卡认证信息
		merchantBankcardCondition = new MerchantBankcardCondition();
		BeanUtils.copyProperties(bankcard, merchantBankcardCondition);
		this.update(merchantInfoCondition);
		
		//更新商户账户结算信息		
		merchantBankcardService.updateStatus(bankcard.getId(), bankcard.getStatus());
		
		if(!ScanCodeConstants.INTERFACE_SUCCESS_STATUS.equals(respCode)){
			map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,respDesc);
			return JSONSerializer.toJSON(map);
		}
		return null;
	}
	
	/**
	 * 保存商户认证信息
	 * @param BankCardAuthVo
	 * @param entity
	 */
	public void saveMerchantAutoInfo(BankCardAuthVo bankCardAuthVo,MerchantInfo entity,String respCode,String respDesc){
		MerchantAuthDetailCondition merchantAuthDetailCondition = new MerchantAuthDetailCondition();
		merchantAuthDetailCondition.setId(Strings.getUUID());
		merchantAuthDetailCondition.setChannelNo(entity.getChannelNo());
		merchantAuthDetailCondition.setAgentNo(entity.getAgentNo());
		merchantAuthDetailCondition.setMerchantNo(entity.getMerchantNo());
		merchantAuthDetailCondition.setAuthInterface("bank_auth");
		merchantAuthDetailCondition.setAuthParams(JSONSerializer.toJSON(bankCardAuthVo).toString());
		merchantAuthDetailCondition.setReturnAuthCode(respCode);
		merchantAuthDetailCondition.setReturnAuthMsg(respDesc);
		merchantAuthDetailCondition.setCreateTime(new Date());
		
		merchantAuthDetailService.insert(merchantAuthDetailCondition);
	}
	
	/**
	 * 调用商户入网接口
	 * @param map 返回web的响应对象map
	 * @param entity 商户基本信息
	 * @param payways 商户费率信息对象列表
	 * 
	 * @throws Exception
	 */
	private JSON merchantJoin(Map<Object, Object> map, MerchantInfo entity,List<MerchantPayway> payways) throws Exception {
		if(entity == null || Strings.isEmpty(entity.getMerchantNo())){
			map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"商户信息不完整，请检查相关信息");
			return JSONSerializer.toJSON(map);
		}
		
		//获取银行卡列表
		MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
		merchantBankcardCondition.setMerchantNo(entity.getMerchantNo());
//		merchantBankcardCondition.setStatus(Constants.STATUS_ACTIVE);
		List<MerchantBankcard> bankcards =  merchantBankcardService.findAll(merchantBankcardCondition);
		if(bankcards.size() == 0){
			map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"商户银行卡信息不存在，请检查相关信息");
			return JSONSerializer.toJSON(map);
		}
		
		//add by wh 检查联行号
		if(null == bankcards.get(0).getBankCode() || "".equals(bankcards.get(0).getBankCode())){
			map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"商户联行号不能为空，请在：商户结算账户 交易中设置");
			return JSONSerializer.toJSON(map);
		}
		
		MerchantAccountsVo account = new MerchantAccountsVo();
		MerchantBankcard card = bankcards.get(0);
		String accountName = this.objToString(card.getName());
		if(Strings.equals(card.getAccountType(), ScanCodeConstants.ACCOUNTTYPE_PUBLIC)){
			accountName = this.objToString(card.getTemp1());
		}
		account.setAccountName(accountName);
		account.setBankCard(this.objToString(card.getBankCard()));
		account.setBankCode(this.objToString(card.getBankCode()));
		account.setBankName(this.objToString(card.getBankName()));
		account.setIsRealAccount(this.objToString(card.getIsRealAccount()));
		account.setAccountType(card.getAccountType());
		
		List<MerchantRateVo> paywayList = Lists.newList();
		MerchantRateVo payway = null;
		for (MerchantPayway pay:payways) {
			payway = new MerchantRateVo();
			payway.setPayCode(this.objToString(pay.getPayCode()));
			payway.setTradeRate(this.objToString(String.valueOf(pay.getT1Rate())));
			payway.setWithdrawAmt(this.objToString(String.valueOf(pay.getRate())));
			payway.setWithdrawRate(this.objToString(String.valueOf(pay.getT0Rate())));
			payway.setSettleAmt(this.objToString(String.valueOf(pay.getRateAmount())));
			paywayList.add(payway);
		}
		
		MerchantInfoVo info = new MerchantInfoVo();
		info.setMerchantNo(this.objToString(entity.getMerchantNo()));
		info.setMerchantName(this.objToString(entity.getMerchantName()));
		info.setShortName(this.objToString(entity.getShortName()));
		info.setAddress(this.objToString(entity.getAddress()));
		info.setSerPhone(this.objToString(entity.getPhone()));
		info.setCategory(this.objToString(entity.getBusType()));
		info.setIdCard(this.objToString(entity.getIdCard()));
		info.setName(this.objToString(entity.getName()));
		info.setMerchantLicense(this.objToString(entity.getMerchantLicense()));
		info.setPhone(this.objToString(entity.getPhone()));
		info.setMobile(this.objToString(entity.getMobile()));
		info.setEmail(this.objToString(entity.getEmail()));
		info.setRemark(this.objToString(entity.getRemark()));
		
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo("JOINCALLBACKURL");
		MappingDicion mappingDicion = mappingDicionService.findByCondition(mappingDicionCondition);
		
		//商户名称规范
		
		
		info.setNotifyUrl(mappingDicion.getParaVal());
		info.setTerminalId("");
		info.setPositionCode("");
		info.setMainBusiness("");
		info.setBusinessRange("");
		info.setMerchantTypeCode("");
		info.setMerchantNature("");
		info.setDistrict("");
		
		//调用商户入网接口
		Map<String,String> res = merchantBusinessService.merchantJoin(info,account,paywayList);
		//解析返回结果
		Map<Object,Object> res1 = afterMerchantJoin(entity,res);
		if(res1 != null) return JSONSerializer.toJSON(res1);
		return null;
	}
	/** 将null对象转换成空字符串 */
	public String objToString(String obj){
		if(obj == null){
			return "";
		}
		return obj;
	}
	
	/**
	 * 解析商户入驻返回结果
	 * @param res
	 */
	private Map<Object, Object> afterMerchantJoin(MerchantInfo info,Map<String,String> res) {
		String respCode = res.get("respCode");
		if(ScanCodeConstants.INTERFACE_SUCCESS_STATUS.equals(respCode)){
			String merchantNo = res.get("merchantNo");
			String hfMerchantNo = res.get("hfMerchantNo");
			String auditStatus = res.get("auditStatus");
			if (auditStatus.equals(ScanCodeConstants.INTERFACE_SUCCESS_STATUS)) {				
				JSONArray paytypes = JSONArray.fromObject(res.get("payType"));
				MerchantPaywayCondition pay = null;
				for (Object string : paytypes) {
					JSONObject map = JSONObject.fromObject(string);
					String payCode = map.getString("payCode");
					String status = map.getString("status");
					String respDesc = map.getString("respDesc");
					//System.err.println(merchantNo+"=="+payCode+"=="+status+"=="+respDesc);
					pay = new MerchantPaywayCondition();
					pay.setMerchantNo(merchantNo);
					pay.setPayCode(payCode);
					pay.setUpdateTime(new Date());
					pay.setOperator(info.getOperator());
					pay.setRemark(respDesc);
					if(ScanCodeConstants.INTERFACE_SUCCESS_STATUS.equals(status)){
						pay.setAcceptStatus(ScanCodeConstants.STATUS_ACTIVE);
					}else{
						//pay.setAcceptStatus(Constants.STATUS_NOT_USE);
					}
					merchantPaywayService.updateByCriteria(pay);
				}
			}
			MerchantInfoCondition mer = new MerchantInfoCondition();
			mer.setId(info.getId());
			mer.setPlatformMerchantNo(hfMerchantNo);
			this.update(mer);
		}else{
			return Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,res.get("respDesc"));
		}
		return null;
		
	}

	/**
	 * 上级机构审核
	 * @param merchantInfoCondition
	 * @param isRealAccount
	 * @param storeNo
	 * @return
	 */
	@Override
	public JSON auditOrgan(MerchantInfoCondition merchantInfoCondition, String isRealAccount, String storeNo)
			throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.SUCCESS,ScanCodeConstants.VALUES,ScanCodeConstants.SUCCESS_MSG,"url","/adminManage/merchantinfo");
		
		boolean flag = false;
		
		MerchantStoreCondition store = new MerchantStoreCondition();
		store.setStoreNo(storeNo);
		
		//商户基本信息
		MerchantInfo entity = this.findById(merchantInfoCondition.getId());
		if(MerchantStatus.MERCHANT_STATUS_3.getCode().equals(entity.getStatus())){
			map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.SUCCESS,ScanCodeConstants.VALUES,"该商户渠道管理员平台审核已通过","url","/adminManage/merchantinfo");
			return JSONSerializer.toJSON(map);
		}
		
		//认证通过
		if(merchantInfoCondition.getStatus().equals("1")){			
			//获取费率列表
			MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
			merchantPaywayCondition.setRecordStatus(ScanCodeConstants.RECORD_STATUS_YES);
			merchantPaywayCondition.setStatus(ScanCodeConstants.STATUS_ACTIVE);
			merchantPaywayCondition.setMerchantNo(entity.getMerchantNo());
			List<MerchantPayway> payways = merchantPaywayService.findAll(merchantPaywayCondition);
			if(payways.size() == 0){
				map = Maps.mapByAarray(ScanCodeConstants.EXECUTE_STATUS,ScanCodeConstants.FAILED,ScanCodeConstants.VALUES,"商户费率信息不存在，请检查相关信息");
				return JSONSerializer.toJSON(map);
			}
			
			flag = true;
			store.setStoreStatus("3");
		}else if(merchantInfoCondition.getStatus().equals("2")){
			flag = true;
			store.setStoreStatus("4");
		}
		store.setAuditOperator(merchantInfoCondition.getOperator());
		store.setAuditDate(new Date());
		store.setAuditReson(merchantInfoCondition.getRemark());
		if(Strings.isNotEmpty(storeNo)){
			//门店改为对应审核状态
			merchantStoreService.updateByCriteria(store);
		}
		//只能修改为1、2（通过或拒绝）
		if(flag){
			this.update(merchantInfoCondition);
			
			//新增审核信息表数据
			AuditLogCondition auditLogCondition = new AuditLogCondition();
			auditLogCondition.setCreateTime(new Date());
			auditLogCondition.setAuditId(entity.getId());
			auditLogCondition.setAuditType(AuditType.MERCHANT_INFO.getCode());
			auditLogCondition.setMerchantNo(entity.getMerchantNo());
			auditLogCondition.setId(Strings.getUUID());
			auditLogCondition.setAuditStatus(merchantInfoCondition.getStatus());
			auditLogCondition.setReason(merchantInfoCondition.getRemark());
			auditLogCondition.setOperator(merchantInfoCondition.getOperator());
			auditLogService.insert(auditLogCondition);
		}
		
		return JSONSerializer.toJSON(map);
	}
	
	
	@Override
	public void delReiskey(String redisKey) {
		// TODO Auto-generated method stub
		redisSharedCache.initRedis();
		 Collection<Jedis> jedisC = redisSharedCache.getResource().getAllShards();  
	     Iterator<Jedis> iter = jedisC.iterator();  
	     while (iter.hasNext()) {  
	         Jedis _jedis = iter.next();  
	         Set<String> keys = _jedis.keys(redisKey + "*"); 
	        _jedis.del(keys.toArray(new String[keys.size()]));  
	     }
	}

	@Override
	public List<MerchantInfo> findMerchantInfoByQrCode(String qrCode) {
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		cb.andEQ("qrCode", qrCode);
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantInfoDAO.findByCriteria(buildCriteria);
	}
	
	@Override
	public long updateInfoByMerchantNo(MerchantInfoCondition condition) {
		MerchantInfo merchantInfo = new MerchantInfo();
		BeanUtils.copyProperties(condition, merchantInfo);
		//merchantInfo.setShortName(condition.getMerchantName());
		//merchantInfo.setStatus("6");
		merchantInfo.setAuthenStatus(0);
		long result = merchantInfoDAO.updateByMerchantNo(merchantInfo);
		try {
			log.info("#######merchantInfoCondition["+JSONObject.fromObject(condition)+"]######");
			List<MerchantInfo> list = this.findAll(condition);
			redisSharedCache.del(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+condition.getMerchantNo()));
			redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+condition.getMerchantNo()), list.get(0));
		} catch (Exception e) {
			log.error("#######保存商户数据到redis失败######");
		}
		return result;
	}
}

