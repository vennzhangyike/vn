package com.hfepay.pay.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.hfepay.cache.jedis.RedisKey;
import com.hfepay.cache.jedis.impl.RedisSharedCache;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.commons.criteria.Order;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.scancode.commons.bo.AdviertismentBo;
import com.hfepay.scancode.commons.condition.AdviertisementInfoCondition;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.CityCondition;
import com.hfepay.scancode.commons.condition.HfepayPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantActivityDiscountCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.condition.OrganLimitCondition;
import com.hfepay.scancode.commons.contants.LimitModeEnum;
import com.hfepay.scancode.commons.contants.LimitPayCodeEnum;
import com.hfepay.scancode.commons.contants.LimitTypeEnum;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.RedisKeyEnum;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.dao.AdviertisementInfoDAO;
import com.hfepay.scancode.commons.dao.AgentBaseDAO;
import com.hfepay.scancode.commons.dao.AgentPaywayDAO;
import com.hfepay.scancode.commons.dao.ChannelBaseDAO;
import com.hfepay.scancode.commons.dao.ChannelPaywayDAO;
import com.hfepay.scancode.commons.dao.CityDAO;
import com.hfepay.scancode.commons.dao.HfepayPaywayDAO;
import com.hfepay.scancode.commons.dao.MerchantActivityDAO;
import com.hfepay.scancode.commons.dao.MerchantActivityDiscountDAO;
import com.hfepay.scancode.commons.dao.MerchantCashierDAO;
import com.hfepay.scancode.commons.dao.MerchantCashierQrDAO;
import com.hfepay.scancode.commons.dao.MerchantCostDAO;
import com.hfepay.scancode.commons.dao.MerchantInfoDAO;
import com.hfepay.scancode.commons.dao.MerchantPaywayDAO;
import com.hfepay.scancode.commons.dao.MerchantQrcodeDAO;
import com.hfepay.scancode.commons.dao.MerchantStoreDAO;
import com.hfepay.scancode.commons.dao.NodeRelationDAO;
import com.hfepay.scancode.commons.dao.OrderPaymentDAO;
import com.hfepay.scancode.commons.dao.OrganLimitDAO;
import com.hfepay.scancode.commons.dao.ParamsInfoDAO;
import com.hfepay.scancode.commons.dao.PlatformQrcodeDAO;
import com.hfepay.scancode.commons.dao.QrcodeGoodsDAO;
import com.hfepay.scancode.commons.dao.channel.ChannelAdminDAO;
import com.hfepay.scancode.commons.entity.AdviertisementInfo;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.AgentPayway;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelPayway;
import com.hfepay.scancode.commons.entity.City;
import com.hfepay.scancode.commons.entity.HfepayPayway;
import com.hfepay.scancode.commons.entity.MerchantActivity;
import com.hfepay.scancode.commons.entity.MerchantActivityDiscount;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.MerchantQrcode;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.NodeRelation;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.OrganLimit;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.commons.entity.QrcodeGoods;

import net.sf.json.JSONObject;

@Service
public class PayCallBackOperatorServiceImpl implements PayCallBackOperatorService{
	
	public static final Logger log = LoggerFactory.getLogger(PayCallBackOperatorServiceImpl.class);
	
	@Autowired
    private ChannelAdminDAO channelAdminDAO;
	@Autowired
	private MerchantCashierDAO merchantCashierDAO;
	@Autowired
	private MerchantCostDAO merchantCostDAO;
	@Autowired
	private MerchantPaywayDAO merchantPaywayDAO;
	@Autowired
	private MerchantCashierQrDAO merchantCashierQrDAO;
	@Autowired
	private MerchantInfoDAO merchantInfoDAO;
	@Autowired
	private ParamsInfoDAO paramsInfoDAO;
	@Autowired
	private RedisSharedCache redisSharedCache;
	@Autowired
	private NodeRelationDAO nodeRelationDAO;
	@Autowired
	private HfepayPaywayDAO hfepayPaywayDAO;
	@Autowired
	private AgentPaywayDAO agentPaywayDAO;
	@Autowired
	private ChannelPaywayDAO channelPaywayDAO;
	@Autowired
	private QrcodeGoodsDAO qrcodeGoodsDAO;
	@Autowired
	private PlatformQrcodeDAO platformQrcodeDAO;
	@Autowired
	private MerchantActivityDAO merchantActivityDAO;
	@Autowired
	private MerchantActivityDiscountDAO merchantActivityDiscountDAO;
	@Autowired
	private ChannelBaseDAO channelBaseDAO;
	@Autowired
	private AgentBaseDAO agentBaseDAO;
	@Autowired
	private OrderPaymentDAO orderPaymentDAO;
	@Autowired
	private CityDAO cityDAO;
	@Autowired
	private AdviertisementInfoDAO adviertisementInfoDAO;
	@Autowired
    private OrganLimitDAO organLimitDAO;
	@Autowired
	private MerchantStoreDAO merchantStoreDAO;
	@Autowired
	private MerchantQrcodeDAO merchantQrcodeDAO;
	/**
	 * 列表
	 * @param merchantPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	@Override
	public List<MerchantPayway> findAll(MerchantPaywayCondition merchantPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
		if(!Strings.isEmpty(merchantPaywayCondition.getId())){
			cb.andEQ("id", merchantPaywayCondition.getId());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantPaywayCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayCode())){
			cb.andEQ("payCode", merchantPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getPayName())){
			cb.andLike("payName", merchantPaywayCondition.getPayName());
		}
		if(null != merchantPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", merchantPaywayCondition.getT0Rate());
		}
		if(null != merchantPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", merchantPaywayCondition.getT1Rate());
		}
		if(null != merchantPaywayCondition.getRate()){
			cb.andEQ("rate", merchantPaywayCondition.getRate());
		}
		if(null != merchantPaywayCondition.getRateAmount()){
			cb.andEQ("rateAmount", merchantPaywayCondition.getRateAmount());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantPaywayCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getStatus())){
			cb.andEQ("status", merchantPaywayCondition.getStatus());
		}
		if(null != merchantPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", merchantPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantPaywayCondition.getOperator())){
			cb.andEQ("operator", merchantPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantPaywayCondition.getRemark())){
			cb.andLike("remark", merchantPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getTemp1())){
			cb.andEQ("temp1", merchantPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantPaywayCondition.getTemp2())){
			cb.andEQ("temp2", merchantPaywayCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantPaywayCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantPaywayCondition.getNodeSeq());
		}
		//cb.andLike("createTime", "2016-11-28");
		Criteria buildCriteria = cb.buildCriteria();
		return merchantPaywayDAO.findByCriteria(buildCriteria);
	}
	
	
	/**
	 * 根据商户编号,支付编码查询商户费率
	 * @param merchantNo
	 * @param payCode
	 * @return MerchantPayway
	 */
	@Override
	public MerchantPayway findMerchantPaywayByMerchantPayCode(String merchantNo,String payCode){
		MerchantPayway merchantPayway = null;
		if (Strings.isEmpty(merchantNo)) {
			new RuntimeException("商户编号不能为空");
		}
		if (Strings.isEmpty(payCode)) {
			new RuntimeException("支付方式不能为空");
		}
		
		try {
//			merchantPayway = (MerchantPayway) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_PAYWAY.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+merchantNo+":"+payCode));
			if (merchantPayway == null) {				
				CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
				cb.andEQ("merchantNo", merchantNo);
				cb.andEQ("payCode", payCode);
				cb.andEQ("recordStatus", ScanCodeConstants.Y);
				cb.andEQ("acceptStatus", ScanCodeConstants.STATUS_ACTIVE);
//				cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteria = cb.buildCriteria();
				merchantPayway = merchantPaywayDAO.findOneByCriteria(buildCriteria);
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_PAYWAY.getGroup(), RedisKeyEnum.MERCHANT_PAYWAY.getKey()+merchantNo+":"+payCode),merchantPayway);
				return merchantPayway;
			}
		
		} catch (Exception e) {
			log.error("商户支付通道操作redis失败");
			e.printStackTrace();
		}
		return merchantPayway;
	}
	
	/**
	 * 列表
	 * @param merchantCashierQr 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	@Override
	public List<MerchantCashierQr> findAll(MerchantCashierQrCondition merchantCashierQrCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantCashierQr.class);
		if(!Strings.isEmpty(merchantCashierQrCondition.getId())){
			cb.andEQ("id", merchantCashierQrCondition.getId());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getCashierNo())){
			cb.andEQ("cashierNo", merchantCashierQrCondition.getCashierNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantCashierQrCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getQrCode())){
			cb.andEQ("qrCode", merchantCashierQrCondition.getQrCode());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getIsCashier())){
			cb.andEQ("isCashier", merchantCashierQrCondition.getIsCashier());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getStatus())){
			cb.andEQ("status", merchantCashierQrCondition.getStatus());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getRecordStatus())){
			cb.andEQ("recordStatus", merchantCashierQrCondition.getRecordStatus());
		}
		if(null != merchantCashierQrCondition.getCreateTime()){
			cb.andEQ("createTime", merchantCashierQrCondition.getCreateTime());
		}

		if(!Strings.isEmpty(merchantCashierQrCondition.getOperator())){
			cb.andEQ("operator", merchantCashierQrCondition.getOperator());
		}

		if(!Strings.isEmpty(merchantCashierQrCondition.getRemark())){
			cb.andLike("remark", merchantCashierQrCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getTemp1())){
			cb.andEQ("temp1", merchantCashierQrCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantCashierQrCondition.getTemp2())){
			cb.andEQ("temp2", merchantCashierQrCondition.getTemp2());
		}
		if(Strings.isNotEmpty(merchantCashierQrCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantCashierQrCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantCashierQrDAO.findByCriteria(buildCriteria);
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
	
	
	 //查找openid不等于id的用户，此处用户消息推送
    @Override
    public ChannelAdmin findPushMsgAdmin(String merchantNo,boolean isMerchant){
    	return channelAdminDAO.findPushMsgAdmin(merchantNo,isMerchant);
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
	public MerchantInfo findByMerchantNo(String merchantNo){
		MerchantInfo merchantInfo = null;
		if (Strings.isEmpty(merchantNo)) {
			throw new RuntimeException("商户编号不能为空"); 
		}
		try {
			//商户信息从缓存中获取
//			merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantNo));
			if (merchantInfo == null) {				
				CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
				cb.andEQ("merchantNo", merchantNo);
				Criteria buildCriteria = cb.buildCriteria();
				merchantInfo = merchantInfoDAO.findOneByCriteria(buildCriteria);
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+merchantNo),merchantInfo);
				return merchantInfo;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return merchantInfo;
	}
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	@Override
	public long insert(MerchantCostCondition merchantCostCondition){
		MerchantCost merchantCost = new MerchantCost();
		BeanUtils.copyProperties(merchantCostCondition, merchantCost);
		return merchantCostDAO.insert(merchantCost);
	}
	/**
	 * @Title: getParamsInfoByDomain
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param organNo
	 * @return: JSONObject
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public JSONObject getParamsInfoByDomain(String organNo) {
		if (Strings.isEmpty(organNo)) {
			new RuntimeException("查询机构不能为空");
		}
		JSONObject paramsInfoJson = null;
		ParamsInfo paramsInfo = this.findParamsKey(organNo,ParamsType.PARAMS_DOMAIN_INFO.getCode());
		if (null == paramsInfo) {
			paramsInfo = this.findParamsKey(ScanCodeConstants.SYSTEM,ParamsType.PARAMS_DOMAIN_INFO.getCode());
			paramsInfoJson = JSONObject.fromObject(paramsInfo.getParamsValue());
		}else {
			paramsInfoJson = JSONObject.fromObject(paramsInfo.getParamsValue());
		}
		return paramsInfoJson;
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param paramsInfoCondition
	 * @return: List<ParamsInfo>
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	@Override
	public ParamsInfo findParamsKey(String paramsKey,String keyType){
		ParamsInfo info = null;
		//参数从缓存获取,10分钟失效
		try {
			info = (ParamsInfo)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.PARAMS_INFO.getGroup(), RedisKeyEnum.PARAMS_INFO.getKey()+paramsKey+":"+keyType));
			if (info == null) {
				CriteriaBuilder cb = Cnd.builder(ParamsInfo.class);
				cb.andEQ("paramsKey", paramsKey);
				cb.andEQ("paramsType", keyType);
				Criteria buildCriteria = cb.buildCriteria();
				info = paramsInfoDAO.findOneByCriteria(buildCriteria);
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.PARAMS_INFO.getGroup(), RedisKeyEnum.PARAMS_INFO.getKey()+paramsKey+":"+keyType),info,6000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	@Override
	public List<NodeRelation> getParentsByCurrentNode(String currentNode, String excludeIdentityFlag,boolean isDirect,boolean isIncludeSelf) {
		List<String> parentsSeq = new ArrayList<>();
		String seq = getSeq(currentNode);
		int length = seq.length();
		if(length==4){//渠道不存在父节点
			return null;
		}
		if(isDirect){//直接父节点为子节点的开头到倒数第五位，默认四位为子节点变化位
			parentsSeq.add(seq.substring(0, length-4));
		}else{//所有父节点
			int level = length/4;
			for (int i = 1; i < level; i++) {
				parentsSeq.add(seq.substring(0,4*i));
			}
		}
		if(isIncludeSelf){//包含自身
			parentsSeq.add(seq);
		}
		return nodeRelationDAO.getParentsByCurrentNode(parentsSeq,excludeIdentityFlag);
	}
	
	@Override
	public String getSeq(String currentNode){
		RedisKey key = new RedisKey(RedisKeyEnum.ORGANNO_SEQ.getGroup(), RedisKeyEnum.ORGANNO_SEQ.getKey()+currentNode);
		String seq = null;
		try {
			seq = redisSharedCache.get(key);
			int length = 0;
			if(Strings.isEmpty(seq)){
				NodeRelation node = findByCurrentNode(currentNode);
				if(node==null||Strings.isEmpty(node.getNodeSeq())){
					return null;
				}
				seq = node.getNodeSeq();
				length = seq.length();//seq的长度必须大于=4且是4的倍数
				if(length<4||length%4!=0){
					throw new RuntimeException("seq长度有问题，请检查....");
				}
				redisSharedCache.set(key, seq);
			}
			return seq;
		} catch (Exception e) {
			log.error("get seq error....",e);
		}
		return null;
	}
	
	
	@Override
	public NodeRelation findByCurrentNode(String currentNode) {
		if(Strings.isEmpty(currentNode)){
			return null;
		}
		NodeRelationCondition condition = new NodeRelationCondition();
		condition.setCurrentNode(currentNode);
		return findByCondition(condition);
	}
	/**
	 * 单个实体对象
	 * @param nodeRelationCondition 
	 *
	 * @author husain
	 * @date CreateDate : 2017-01-22 15:16:03
	 */
	@Override
	public NodeRelation findByCondition(NodeRelationCondition nodeRelationCondition){
		CriteriaBuilder cb = Cnd.builder(NodeRelation.class);
		if(!Strings.isEmpty(nodeRelationCondition.getId())){
			cb.andEQ("id", nodeRelationCondition.getId());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getCurrentNode())){
			cb.andEQ("currentNode", nodeRelationCondition.getCurrentNode());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getParentNode())){
			cb.andEQ("parentNode", nodeRelationCondition.getParentNode());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getCurrentNodeLevel())){
			cb.andEQ("currentNodeLevel", nodeRelationCondition.getCurrentNodeLevel());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getIdentityFlag())){
			cb.andEQ("identityFlag", nodeRelationCondition.getIdentityFlag());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getNodeSeq())){
			cb.andEQ("nodeSeq", nodeRelationCondition.getNodeSeq());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getNextNodeSeq())){
			cb.andEQ("nextNodeSeq", nodeRelationCondition.getNextNodeSeq());
		}
		if(!Strings.isEmpty(nodeRelationCondition.getOperator())){
			cb.andEQ("operator", nodeRelationCondition.getOperator());
		}
		if(null != nodeRelationCondition.getCreateTime()){
			cb.andEQ("createTime", nodeRelationCondition.getCreateTime());
		}

		Criteria buildCriteria = cb.buildCriteria();
		return nodeRelationDAO.findOneByCriteria(buildCriteria);
	}
	

	/**
	 * 列表
	 * @param agentPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-18 15:18:22
	 */
//	@Override
//	public List<AgentPayway> findAll(AgentPaywayCondition agentPaywayCondition){
//		CriteriaBuilder cb = Cnd.builder(AgentPayway.class);
//		if(!Strings.isEmpty(agentPaywayCondition.getId())){
//			cb.andEQ("id", agentPaywayCondition.getId());
//		}
//		if(!Strings.isEmpty(agentPaywayCondition.getAgentNo())){
//			cb.andEQ("agentNo", agentPaywayCondition.getAgentNo());
//		}
//		if(!Strings.isEmpty(agentPaywayCondition.getAgentName())){
//			cb.andLike("agentName", agentPaywayCondition.getAgentName());
//		}
//		if(!Strings.isEmpty(agentPaywayCondition.getPayCode())){
//			cb.andEQ("payCode", agentPaywayCondition.getPayCode());
//		}
//		if(!Strings.isEmpty(agentPaywayCondition.getPayName())){
//			cb.andLike("payName", agentPaywayCondition.getPayName());
//		}
//		if(null != agentPaywayCondition.getT0Rate()){
//			cb.andEQ("t0Rate", agentPaywayCondition.getT0Rate());
//		}
//		if(null != agentPaywayCondition.getT1Rate()){
//			cb.andEQ("t1Rate", agentPaywayCondition.getT1Rate());
//		}
//		if(null != agentPaywayCondition.getRate()){
//			cb.andEQ("rate", agentPaywayCondition.getRate());
//		}
//		if(null != agentPaywayCondition.getRateAmount()){
//			cb.andEQ("rateAmount", agentPaywayCondition.getRateAmount());
//		}
//		if(!Strings.isEmpty(agentPaywayCondition.getRecordStatus())){
//			cb.andEQ("recordStatus", agentPaywayCondition.getRecordStatus());
//		}
//		if(!Strings.isEmpty(agentPaywayCondition.getStatus())){
//			cb.andEQ("status", agentPaywayCondition.getStatus());
//		}
//		if(null != agentPaywayCondition.getCreateTime()){
//			cb.andEQ("createTime", agentPaywayCondition.getCreateTime());
//		}
//
//		if(!Strings.isEmpty(agentPaywayCondition.getOperator())){
//			cb.andEQ("operator", agentPaywayCondition.getOperator());
//		}
//
//		if(!Strings.isEmpty(agentPaywayCondition.getRemark())){
//			cb.andLike("remark", agentPaywayCondition.getRemark());
//		}
//		if(!Strings.isEmpty(agentPaywayCondition.getTemp1())){
//			cb.andEQ("temp1", agentPaywayCondition.getTemp1());
//		}
//		if(!Strings.isEmpty(agentPaywayCondition.getTemp2())){
//			cb.andEQ("temp2", agentPaywayCondition.getTemp2());
//		}
//		//cb.andLike("createTime", "2016-11-28");
//		Criteria buildCriteria = cb.buildCriteria();
//		return agentPaywayDAO.findByCriteria(buildCriteria);
//	}
	
	/**
	 * 根据代理商编号,支付方式查询费率信息
	 * @param agentNo
	 * @param payCode
	 * @return AgentPayway
	 */
	public AgentPayway findAgentPayWayByAgentNoPayCode(String agentNo,String payCode){
		AgentPayway agentPayway = null;
		if (Strings.isEmpty(agentNo)) {
			new RuntimeException("代理商编号不能为空");
		}
		if (Strings.isEmpty(payCode)) {
			new RuntimeException("支付方式不能为空");
		}
		
		try {
//			agentPayway = (AgentPayway) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_PAYWAY.getGroup(), RedisKeyEnum.AGENT_PAYWAY.getKey()+agentNo+":"+payCode));
			if (agentPayway == null) {				
				CriteriaBuilder cb = Cnd.builder(AgentPayway.class);
				cb.andEQ("agentNo", agentNo);
				cb.andEQ("payCode", payCode);
				cb.andEQ("recordStatus", ScanCodeConstants.Y);
//				cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteria = cb.buildCriteria();
				agentPayway = agentPaywayDAO.findOneByCriteria(buildCriteria);
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.AGENT_PAYWAY.getGroup(), RedisKeyEnum.AGENT_PAYWAY.getKey()+agentNo+":"+payCode),agentPayway);
				return agentPayway;
			}
		} catch (Exception e) {
			log.error("代理商支付通道操作redis失败");
			e.printStackTrace();
		}
		return agentPayway;
	}
	
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param channelPaywayCondition
	 * @return: List<ChannelPayway>
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
//	@Override
//	public List<ChannelPayway> findAll(ChannelPaywayCondition channelPaywayCondition){
//		CriteriaBuilder cb = Cnd.builder(ChannelPayway.class);
//		if(!Strings.isEmpty(channelPaywayCondition.getId())){
//			cb.andEQ("id", channelPaywayCondition.getId());
//		}
//		if(!Strings.isEmpty(channelPaywayCondition.getChannelNo())){
//			cb.andEQ("channelNo", channelPaywayCondition.getChannelNo());
//		}
//		if(!Strings.isEmpty(channelPaywayCondition.getChannelName())){
//			cb.andLike("channelName", channelPaywayCondition.getChannelName());
//		}
//		if(!Strings.isEmpty(channelPaywayCondition.getPayCode())){
//			cb.andEQ("payCode", channelPaywayCondition.getPayCode());
//		}
//		if(!Strings.isEmpty(channelPaywayCondition.getPayName())){
//			cb.andLike("payName", channelPaywayCondition.getPayName());
//		}
//		if(null != channelPaywayCondition.getT0Rate()){
//			cb.andEQ("t0Rate", channelPaywayCondition.getT0Rate());
//		}
//		if(null != channelPaywayCondition.getT1Rate()){
//			cb.andEQ("t1Rate", channelPaywayCondition.getT1Rate());
//		}
//		if(null != channelPaywayCondition.getRate()){
//			cb.andEQ("rate", channelPaywayCondition.getRate());
//		}
//		if(null != channelPaywayCondition.getRateAmount()){
//			cb.andEQ("rateAmount", channelPaywayCondition.getRateAmount());
//		}
//		if(!Strings.isEmpty(channelPaywayCondition.getStatus())){
//			cb.andEQ("status", channelPaywayCondition.getStatus());
//		}
//		if(!Strings.isEmpty(channelPaywayCondition.getRecordStatus())){
//			cb.andEQ("recordStatus", channelPaywayCondition.getRecordStatus());
//		}
//		if(null != channelPaywayCondition.getCreateTime()){
//			cb.andEQ("createTime", channelPaywayCondition.getCreateTime());
//		}
//
//		if(!Strings.isEmpty(channelPaywayCondition.getOperator())){
//			cb.andEQ("operator", channelPaywayCondition.getOperator());
//		}
//
//		if(!Strings.isEmpty(channelPaywayCondition.getRemark())){
//			cb.andLike("remark", channelPaywayCondition.getRemark());
//		}
//		if(!Strings.isEmpty(channelPaywayCondition.getTemp1())){
//			cb.andEQ("temp1", channelPaywayCondition.getTemp1());
//		}
//		if(!Strings.isEmpty(channelPaywayCondition.getTemp2())){
//			cb.andEQ("temp2", channelPaywayCondition.getTemp2());
//		}
//		Criteria buildCriteria = cb.buildCriteria();
//		return channelPaywayDAO.findByCriteria(buildCriteria);
//	}
	
	/**
	 * 根据渠道编号,支付方式查询费率信息
	 * @param agentNo
	 * @param payCode
	 * @return ChannelPayway
	 */
	public ChannelPayway findChannelPayWayByChannelNoPayCode(String channelNo,String payCode){
		ChannelPayway channelPayway = null;
		if (Strings.isEmpty(channelNo)) {
			new RuntimeException("渠道编号不能为空");
		}
		if (Strings.isEmpty(payCode)) {
			new RuntimeException("支付方式不能为空");
		}
		
		try {
//			channelPayway = (ChannelPayway) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_PAYWAY.getGroup(), RedisKeyEnum.CHANNEL_PAYWAY.getKey()+channelNo+":"+payCode));
			if (channelPayway == null) {				
				CriteriaBuilder cb = Cnd.builder(ChannelPayway.class);
				cb.andEQ("channelNo", channelNo);
				cb.andEQ("payCode", payCode);
				cb.andEQ("recordStatus", ScanCodeConstants.Y);
//				cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteria = cb.buildCriteria();
				channelPayway = channelPaywayDAO.findOneByCriteria(buildCriteria);
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.CHANNEL_PAYWAY.getGroup(), RedisKeyEnum.CHANNEL_PAYWAY.getKey()+channelNo+":"+payCode),channelPayway);
				return channelPayway;
			}
		} catch (Exception e) {
			log.error("渠道支付通道操作redis失败");
			e.printStackTrace();
		}
		return channelPayway;
	}
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @return: List<HfepayPayway>
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@Override
	public List<HfepayPayway> findAll(HfepayPaywayCondition hfepayPaywayCondition){
		CriteriaBuilder cb = Cnd.builder(HfepayPayway.class);
		if(!Strings.isEmpty(hfepayPaywayCondition.getId())){
			cb.andEQ("id", hfepayPaywayCondition.getId());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getServerNo())){
			cb.andEQ("serverNo", hfepayPaywayCondition.getServerNo());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getServerName())){
			cb.andLike("serverName", hfepayPaywayCondition.getServerName());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayCode())){
			cb.andEQ("payCode", hfepayPaywayCondition.getPayCode());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayName())){
			cb.andLike("payName", hfepayPaywayCondition.getPayName());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayType())){
			cb.andEQ("payType", hfepayPaywayCondition.getPayType());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPayDesc())){
			cb.andEQ("payDesc", hfepayPaywayCondition.getPayDesc());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getPaySeq())){
			cb.andEQ("paySeq", hfepayPaywayCondition.getPaySeq());
		}
		if(null != hfepayPaywayCondition.getT0Rate()){
			cb.andEQ("t0Rate", hfepayPaywayCondition.getT0Rate());
		}
		if(null != hfepayPaywayCondition.getT1Rate()){
			cb.andEQ("t1Rate", hfepayPaywayCondition.getT1Rate());
		}
		if(null != hfepayPaywayCondition.getRate()){
			cb.andEQ("rate", hfepayPaywayCondition.getRate());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getRecordStatus())){
			cb.andEQ("recordStatus", hfepayPaywayCondition.getRecordStatus());
		}
		if(null != hfepayPaywayCondition.getCreateTime()){
			cb.andEQ("createTime", hfepayPaywayCondition.getCreateTime());
		}

		if(!Strings.isEmpty(hfepayPaywayCondition.getOperator())){
			cb.andEQ("operator", hfepayPaywayCondition.getOperator());
		}

		if(!Strings.isEmpty(hfepayPaywayCondition.getRemark())){
			cb.andLike("remark", hfepayPaywayCondition.getRemark());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp1())){
			cb.andEQ("temp1", hfepayPaywayCondition.getTemp1());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp2())){
			cb.andEQ("temp2", hfepayPaywayCondition.getTemp2());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp3())){
			cb.andEQ("temp3", hfepayPaywayCondition.getTemp3());
		}
		if(!Strings.isEmpty(hfepayPaywayCondition.getTemp4())){
			cb.andEQ("temp4", hfepayPaywayCondition.getTemp4());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return hfepayPaywayDAO.findByCriteria(buildCriteria);
	}
	
	
	@Override
	public QrcodeGoods findByQrCode(String qrCode) {
		CriteriaBuilder cb = Cnd.builder(QrcodeGoods.class);
		cb.andEQ("qrCode", qrCode);
		cb.andEQ("isDefault", "0");
		cb.andEQ("recordStatus", "Y");
		Criteria buildCriteria = cb.buildCriteria();
		return qrcodeGoodsDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public PlatformQrcode findByQrcode(String qrCode) {
		CriteriaBuilder cb = Cnd.builder(PlatformQrcode.class);
		if(Strings.isEmpty(qrCode)){
			throw new RuntimeException("二维码编号不能为空");
		}
		cb.andEQ("qrCode", qrCode);
		Criteria buildCriteria = cb.buildCriteria();
		return platformQrcodeDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public MerchantActivity findByMerchantNoAndStatus(String merchantNo, String status) {
		return merchantActivityDAO.findByMerchantNoAndStatus(merchantNo,status);
	}
	
	/**
	 * 列表
	 * @param merchantActivityDiscount 
	 *
	 * @author panq
	 * @date CreateDate : 2017-02-14 10:46:19
	 */
	@Override
	public List<MerchantActivityDiscount> findAll(MerchantActivityDiscountCondition merchantActivityDiscountCondition){
		CriteriaBuilder cb = Cnd.builder(MerchantActivityDiscount.class);
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getId())){
			cb.andEQ("id", merchantActivityDiscountCondition.getId());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getActivityId())){
			cb.andEQ("activityId", merchantActivityDiscountCondition.getActivityId());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getActivityDiscount())){
			cb.andEQ("activityDiscount", merchantActivityDiscountCondition.getActivityDiscount());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getActivityCondition())){
			cb.andEQ("activityCondition", merchantActivityDiscountCondition.getActivityCondition());
		}
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getChance())){
			cb.andEQ("chance", merchantActivityDiscountCondition.getChance());
		}
		cb.orderBy("activityCondition", Order.DESC);
		//排序
		if(!Strings.isEmpty(merchantActivityDiscountCondition.getOrderBy())){
			if(merchantActivityDiscountCondition.getOrderBy().indexOf(",")>0){
				String[] orderBys = merchantActivityDiscountCondition.getOrderBy().split(",");
				String[] orders = merchantActivityDiscountCondition.getOrder().split(",");
				for(int i=0; i<orderBys.length && i<orders.length; i++){
					cb.orderBy(orderBys[i], Order.valueOf(orders[i]));
				}
			}else{
				cb.orderBy(merchantActivityDiscountCondition.getOrderBy(), Order.valueOf(merchantActivityDiscountCondition.getOrder()));
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantActivityDiscountDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * 计算优惠的价格
	 */
	public Map<String,BigDecimal> calculateCheapCash(String merchantNo,BigDecimal payCash,BigDecimal minLimit){
		Map<String,BigDecimal> cashMap = new HashMap<String,BigDecimal>();
		BigDecimal discountCash = BigDecimal.ZERO;//优惠金额
		BigDecimal actualPayCash = BigDecimal.ZERO;//实际支付金额
		MerchantActivity merchantActivity = findByMerchantNoAndStatus(merchantNo,"1");
		if(merchantActivity != null){
			Date currDate = new Date(); //当前日期
			Date activityBeginDate = merchantActivity.getActivityBeginTime();//活动开始时间
			Date activityEndDate = merchantActivity.getActivityEndTime();//活动结束时间
			
			//活动ID
			String activityId = merchantActivity.getActivityId();
			//活动类型
			String activityType = merchantActivity.getActivityType();
			
			MerchantActivityDiscountCondition merchantActivityDiscountCondition = new MerchantActivityDiscountCondition();
			merchantActivityDiscountCondition.setActivityId(activityId);
			//判断当前的支付日期是否在活动规定的时间内
			if(currDate.getTime() >= activityBeginDate.getTime() && currDate.getTime() <= activityEndDate.getTime()){
				List<MerchantActivityDiscount> list = findAll(merchantActivityDiscountCondition);
				if(list != null && list.size() > 0){
					for (Iterator<MerchantActivityDiscount> iterator = list.iterator(); iterator.hasNext();) {
						MerchantActivityDiscount merchantActivityDiscount = (MerchantActivityDiscount) iterator.next();
						//活动优惠 BigDecimal
						BigDecimal discount = BigDecimal.ZERO;
						//活动类型为随机时，活动优惠为String
						String strDiscount = "";
						//活动条件BigDecimal
						BigDecimal condition = new BigDecimal(merchantActivityDiscount.getActivityCondition());
						//活动类型为随机时 活动条件为Integer
						Integer intCondtion = Integer.parseInt(merchantActivityDiscount.getActivityCondition());
						//中奖概率
						double chance = 0;
						if(payCash.compareTo(condition)>=0){
							if(activityType.equals("0")){//活动类型为折扣
								discount = new BigDecimal(merchantActivityDiscount.getActivityDiscount());
								actualPayCash = payCash.multiply(discount).setScale(2, BigDecimal.ROUND_HALF_UP);//支付金额 * 折扣率 = 实际支付金额
								discountCash = payCash.subtract(actualPayCash).setScale(2, BigDecimal.ROUND_HALF_UP);//支付金额-实际支付金额=优惠金额
							}else if(activityType.equals("1")){//活动类型为满减
								discount = new BigDecimal(merchantActivityDiscount.getActivityDiscount());
								discountCash = discount; //活动优惠金额
								actualPayCash = payCash.subtract(discountCash).setScale(2, BigDecimal.ROUND_HALF_UP);//支付金额-优惠金额=实际支付金额
							}else if(activityType.equals("2")){
								chance = Double.valueOf(merchantActivityDiscount.getChance())*intCondtion;
								strDiscount = merchantActivityDiscount.getActivityDiscount();
								Random rand = new Random();
								int count = rand.nextInt(intCondtion);//根据活动条件产生随机数
								if (chance >= count) {
									String [] str= strDiscount.split(",");
									Integer begin = Integer.parseInt(str[0]);
									Integer end = Integer.parseInt(str[1]);
									int a = rand.nextInt(end);
									if(a >= begin && a <= end){
										if(new BigDecimal(a).compareTo(payCash)>=0){
											actualPayCash = payCash;
										}else{
											discountCash = new BigDecimal(a);
											actualPayCash = payCash.subtract(discountCash).setScale(2, BigDecimal.ROUND_HALF_UP);
										}
									}else{
										actualPayCash = payCash;
									}
								}else{
									actualPayCash = payCash;
								}
							}
							break;
						}else{
							actualPayCash = payCash;
						}
					}
				}
				cashMap.put("payCash", payCash);
				cashMap.put("discountCash", discountCash);
				cashMap.put("actualPayCash", actualPayCash);
			}else{
				cashMap.put("payCash", payCash);
				cashMap.put("discountCash", BigDecimal.ZERO);
				cashMap.put("actualPayCash", payCash);
			}
		}else{
			cashMap.put("payCash", payCash);
			cashMap.put("discountCash", BigDecimal.ZERO);
			cashMap.put("actualPayCash", payCash);
		}
		if(cashMap.get("actualPayCash").compareTo(minLimit)==-1){
			cashMap.put("payCash", payCash);
			cashMap.put("discountCash", BigDecimal.ZERO);
			cashMap.put("actualPayCash", payCash);
		}
		return cashMap;
	}
	
	/**
	 * 列表
	 * 
	 * @param agentBase
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
//	@Override
//	public List<AgentBase> findAll(AgentBaseCondition agentBaseCondition) {
//		CriteriaBuilder cb = Cnd.builder(AgentBase.class);
//		if (!Strings.isEmpty(agentBaseCondition.getId())) {
//			cb.andEQ("id", agentBaseCondition.getId());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getAgentNo())) {
//			cb.andEQ("agentNo", agentBaseCondition.getAgentNo());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getAgentName())) {
//			cb.andLike("agentName", agentBaseCondition.getAgentName());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getChannelNo())) {
//			cb.andEQ("channelNo", agentBaseCondition.getChannelNo());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getAgentPreCode())) {
//			cb.andEQ("agentPreCode", agentBaseCondition.getAgentPreCode());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getAgentType())) {
//			cb.andEQ("agentType", agentBaseCondition.getAgentType());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getName())) {
//			cb.andEQ("name", agentBaseCondition.getName());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getMobile())) {
//			cb.andEQ("mobile", agentBaseCondition.getMobile());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getParentNo())) {
//			cb.andEQ("parentNo", agentBaseCondition.getParentNo());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getAgentLevel())) {
//			cb.andEQ("agentLevel", agentBaseCondition.getAgentLevel());
//		}
//		if (null != agentBaseCondition.getQrTotal()) {
//			cb.andEQ("qrTotal", agentBaseCondition.getQrTotal());
//		}
//		if (null != agentBaseCondition.getUseTotal()) {
//			cb.andEQ("useTotal", agentBaseCondition.getUseTotal());
//		}
//		if (null != agentBaseCondition.getLessTotal()) {
//			cb.andEQ("lessTotal", agentBaseCondition.getLessTotal());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getAgentFlag())) {
//			cb.andEQ("agentFlag", agentBaseCondition.getAgentFlag());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getStatus())) {
//			cb.andEQ("status", agentBaseCondition.getStatus());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getRecordStatus())) {
//			cb.andEQ("recordStatus", agentBaseCondition.getRecordStatus());
//		}
//		if (null != agentBaseCondition.getCreateTime()) {
//			cb.andEQ("createTime", agentBaseCondition.getCreateTime());
//		}
//
//		if (!Strings.isEmpty(agentBaseCondition.getOperator())) {
//			cb.andEQ("operator", agentBaseCondition.getOperator());
//		}
//
//		if (!Strings.isEmpty(agentBaseCondition.getRemark())) {
//			cb.andLike("remark", agentBaseCondition.getRemark());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getTemp1())) {
//			cb.andEQ("temp1", agentBaseCondition.getTemp1());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getTemp2())) {
//			cb.andEQ("temp2", agentBaseCondition.getTemp2());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getTemp3())) {
//			cb.andEQ("temp3", agentBaseCondition.getTemp3());
//		}
//		if (!Strings.isEmpty(agentBaseCondition.getTemp4())) {
//			cb.andEQ("temp4", agentBaseCondition.getTemp4());
//		}
//		Criteria buildCriteria = cb.buildCriteria();
//		return agentBaseDAO.findByCriteria(buildCriteria);
//	}
	
	@Override
	public AgentPayway findAgentPaywayByPayCode(String payCode, String agentNo) {
		CriteriaBuilder cb = Cnd.builder(AgentPayway.class);
		cb.andEQ("payCode", payCode);
		cb.andEQ("agentNo", agentNo);
		cb.andEQ("recordStatus", "Y");
		Criteria buildCriteria = cb.buildCriteria();
		return agentPaywayDAO.findOneByCriteria(buildCriteria);
	}
	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @author: Ricky
	 * @param channelNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	@Override
	public ChannelBase findByChannelNo(String channelNo){
		ChannelBase channelBase= null;
		if(Strings.isEmpty(channelNo)){
			throw new RuntimeException("渠道编号不能为空");
		}
		
		try {
			//商户信息从缓存中获取
//			channelBase = (ChannelBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+channelNo));
			if (channelBase == null) {				
				CriteriaBuilder cb = Cnd.builder(ChannelBase.class);
				cb.andEQ("channelNo", channelNo);
				cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteria = cb.buildCriteria();
				channelBase = channelBaseDAO.findOneByCriteria(buildCriteria);
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.CHANNEL_BASE.getGroup(), RedisKeyEnum.CHANNEL_BASE.getKey()+channelNo),channelBase);
				return channelBase;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return channelBase;
	}
	
//	@Override
//	public MerchantPayway findMerchantPaywayByPayCode(String payCode, String merchantNo) {
//		CriteriaBuilder cb = Cnd.builder(MerchantPayway.class);
//		cb.andEQ("payCode", payCode);
//		cb.andEQ("merchantNo", merchantNo);
//		cb.andEQ("recordStatus", "Y");
//		Criteria buildCriteria = cb.buildCriteria();
//		return merchantPaywayDAO.findOneByCriteria(buildCriteria);
//	}
	
	@Override
	public List<MerchantInfo> findMerchantInfoByQrCode(String qrCode) {
		if (Strings.isEmpty(qrCode)) {
			new RuntimeException("二维码编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		cb.andEQ("qrCode", qrCode);
		cb.andEQ("recordStatus", ScanCodeConstants.RECORD_STATUS_YES);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantInfoDAO.findByCriteria(buildCriteria);
	}
	@Override
	public ChannelPayway findChannelPaywayByPayCode(String payCode, String channelNo) {
		CriteriaBuilder cb = Cnd.builder(ChannelPayway.class);
		cb.andEQ("payCode", payCode);
		cb.andEQ("channelNo", channelNo);
		cb.andEQ("recordStatus", "Y");
		Criteria buildCriteria = cb.buildCriteria();
		return channelPaywayDAO.findOneByCriteria(buildCriteria);
	}
	

	 //查找openid不等于id的用户，此处用户消息推送
    @Override
    public ChannelAdmin findPushMsgAdminByMerchantNo(String merchantNo,String flag){
    	return channelAdminDAO.findByMerchantNoByIdentityGetOpenId(merchantNo,flag);
    }
    
    @Override
	public AgentBase findByAgentNo(String agentNo) {
    	AgentBase agentBase = null;
    	if (Strings.isEmpty(agentNo)) {
			new RuntimeException("代理商编号不能为空");
		}
    	
    	try {
			//代理商信息从缓存中获取
//			agentBase = (AgentBase) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+agentNo));
			if (agentBase == null) {				
				CriteriaBuilder cb = Cnd.builder(AgentBase.class);
				cb.andEQ("agentNo", agentNo);
				cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteria = cb.buildCriteria();
				agentBase = agentBaseDAO.findOneByCriteria(buildCriteria);
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.AGENT_BASE.getGroup(), RedisKeyEnum.AGENT_BASE.getKey()+agentNo),agentBase);
				return agentBase;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		return agentBase;
	}
    
    /**
	 * 列表
	 * @param orderPayment 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-17 11:37:55
	 */
	@Override
	public List<OrderPayment> findAll(OrderPaymentCondition orderPaymentCondition){
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
		if(!Strings.isEmpty(orderPaymentCondition.getId())){
			cb.andEQ("id", orderPaymentCondition.getId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTradeNo())){
			cb.andEQ("tradeNo", orderPaymentCondition.getTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getChannelNo())){
			cb.andEQ("channelNo", orderPaymentCondition.getChannelNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getAgentNo())){
			cb.andEQ("agentNo", orderPaymentCondition.getAgentNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getMerchantNo())){
			cb.andEQ("merchantNo", orderPaymentCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getQrCode())){
			cb.andEQ("qrCode", orderPaymentCondition.getQrCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPayCode())){
			cb.andEQ("payCode", orderPaymentCondition.getPayCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBusinessType())){
			cb.andEQ("businessType", orderPaymentCondition.getBusinessType());
		}
//		if(null != orderPaymentCondition.getOrderAmt()){
//			cb.andEQ("orderAmt", orderPaymentCondition.getOrderAmt());
//		}
		if(null != orderPaymentCondition.getQueryStartAmt()){
			cb.andGE("orderAmt", orderPaymentCondition.getQueryStartAmt());
		}
		if(null != orderPaymentCondition.getQueryEndAmt()){
			cb.andLE("orderAmt", orderPaymentCondition.getQueryEndAmt());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getProductName())){
			cb.andEQ("productName", orderPaymentCondition.getProductName());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getProductDesc())){
			cb.andEQ("productDesc", orderPaymentCondition.getProductDesc());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBuyerId())){
			cb.andEQ("buyerId", orderPaymentCondition.getBuyerId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBuyerAccount())){
			cb.andEQ("buyerAccount", orderPaymentCondition.getBuyerAccount());
		}
		if(null != orderPaymentCondition.getTotalAmount()){
			cb.andEQ("totalAmount", orderPaymentCondition.getTotalAmount());
		}
		if(null != orderPaymentCondition.getBuyerPayAmount()){
			cb.andEQ("buyerPayAmount", orderPaymentCondition.getBuyerPayAmount());
		}
		if(null != orderPaymentCondition.getPointAmount()){
			cb.andEQ("pointAmount", orderPaymentCondition.getPointAmount());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getFeeStatus())){
			cb.andEQ("feeStatus", orderPaymentCondition.getFeeStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getFeeType())){
			cb.andEQ("feeType", orderPaymentCondition.getFeeType());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPaymentInfo())){
			cb.andEQ("paymentInfo", orderPaymentCondition.getPaymentInfo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBatchId())){
			cb.andEQ("batchId", orderPaymentCondition.getBatchId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getResultCode())){
			cb.andEQ("resultCode", orderPaymentCondition.getResultCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getResultInfo())){
			cb.andEQ("resultInfo", orderPaymentCondition.getResultInfo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPayTradeNo())){
			cb.andEQ("payTradeNo", orderPaymentCondition.getPayTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPaymentType())){
			cb.andEQ("paymentType", orderPaymentCondition.getPaymentType());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getNotifyUrl())){
			cb.andEQ("notifyUrl", orderPaymentCondition.getNotifyUrl());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getReturnUrl())){
			cb.andEQ("returnUrl", orderPaymentCondition.getReturnUrl());
		}
		if(null != orderPaymentCondition.getBeginTime()){
			cb.andGE("beginTime", orderPaymentCondition.getBeginTime());
		}
		if(null != orderPaymentCondition.getEndTime()){
			cb.andLE("beginTime", orderPaymentCondition.getEndTime());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getSettleTime())){
			cb.andEQ("settleTime", orderPaymentCondition.getSettleTime());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getSettleMerchant())){
			cb.andEQ("settleMerchant", orderPaymentCondition.getSettleMerchant());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getRefundStatus())){
			cb.andEQ("refundStatus", orderPaymentCondition.getRefundStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getOrderStatus())){
			cb.andEQ("orderStatus", orderPaymentCondition.getOrderStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getOptCode())){
			cb.andEQ("optCode", orderPaymentCondition.getOptCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getRecordStatus())){
			cb.andEQ("recordStatus", orderPaymentCondition.getRecordStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getCashierNo())){
			cb.andEQ("cashier_no", orderPaymentCondition.getCashierNo());
		}
		if(null != orderPaymentCondition.getMerchantCost()){
			cb.andEQ("merchant_cost", orderPaymentCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getOperator())){
			cb.andEQ("operator", orderPaymentCondition.getOperator());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getStoreNo())){
			cb.andEQ("store_no", orderPaymentCondition.getStoreNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getRemark())){
			cb.andLike("remark", orderPaymentCondition.getRemark());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp1())){
			cb.andEQ("temp1", orderPaymentCondition.getTemp1());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp2())){
			cb.andEQ("temp2", orderPaymentCondition.getTemp2());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp3())){
			cb.andEQ("temp3", orderPaymentCondition.getTemp3());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp4())){
			cb.andEQ("temp4", orderPaymentCondition.getTemp4());
		}
		if(orderPaymentCondition.getOrganNoList() !=null && !orderPaymentCondition.getOrganNoList().isEmpty()){
			cb.andIn("agentNo", orderPaymentCondition.getOrganNoList());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getAccountType())){
			if(orderPaymentCondition.getAccountType().equals("2")){
				cb.andEQ("accountType", orderPaymentCondition.getAccountType());
			}else{
				cb.andNotEQ("accountType", "2");
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		return orderPaymentDAO.findByCriteria(buildCriteria);
	}
    
	/**
	 * 根据登录人信息获取对应广告
	 * @param 用户ID user
	 * @param 广告类型 adviertPosition
	 * @return List<AdviertisementInfo>
	 */
	@Override
	public List<AdviertisementInfo> getAdviertisInfoByUser(AdviertismentBo adviertismentBo){
		MerchantInfo merchantInfo;
		try {
			merchantInfo = (MerchantInfo) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.MERCHANT_BASE.getGroup(), RedisKeyEnum.MERCHANT_BASE.getKey()+adviertismentBo.getMerchantNo()));
			log.info("#######merchantInfoCondition["+JSONObject.fromObject(merchantInfo)+"]######");
			
			//
			CityCondition cityCondition = new CityCondition();
			cityCondition.setCname(adviertismentBo.getCity());
			List<City> cityList = findByCname(cityCondition);
			City city = cityList.get(0);
			
			//平台优先级最高
			List<AdviertisementInfo> adviertList = getAdviertInfo(city.getCid().toString(),ScanCodeConstants.UNLIMITED,adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(city.getPid().toString(),ScanCodeConstants.UNLIMITED,adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(ScanCodeConstants.UNLIMITED,ScanCodeConstants.UNLIMITED,adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			//渠道优先级为次级
			adviertList = getAdviertInfo(city.getCid().toString(),merchantInfo.getChannelNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(city.getPid().toString(),merchantInfo.getChannelNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(ScanCodeConstants.UNLIMITED,merchantInfo.getChannelNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			//代理商优先级最低
			adviertList = getAdviertInfo(city.getCid().toString(),merchantInfo.getAgentNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(city.getPid().toString(),merchantInfo.getAgentNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
			
			adviertList = getAdviertInfo(ScanCodeConstants.UNLIMITED,merchantInfo.getAgentNo(),adviertismentBo.getAdviertPosition());
			if (adviertList != null && adviertList.size() > 0 ) {
				return adviertList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private List<AdviertisementInfo> getAdviertInfo(String cityInfo,String organ,String adviertPosition) {
		AdviertisementInfoCondition adviertisementInfoCondition = new AdviertisementInfoCondition();
		adviertisementInfoCondition.setStartDate(new Date());
		adviertisementInfoCondition.setStartDate(new Date());
		adviertisementInfoCondition.setStatus(ScanCodeConstants.AD_STATUS_SUCCESS);
		adviertisementInfoCondition.setAdviertPosition(adviertPosition);
		adviertisementInfoCondition.setOrganNo(organ);
		adviertisementInfoCondition.setAdviertScope(cityInfo);
		List<AdviertisementInfo> adviertList = this.findByOrgan(adviertisementInfoCondition);
		return adviertList;
	}
	
	@Override
	public List<City> findByCname(CityCondition cityCondition) {
		CriteriaBuilder cb = Cnd.builder(City.class);
		if(null != cityCondition.getCid()){
			cb.andEQ("cid", cityCondition.getCid());
		}
		if(!Strings.isEmpty(cityCondition.getCname())){
			cb.andLike("cname", cityCondition.getCname());
		}
		if(!Strings.isEmpty(cityCondition.getCpostcode())){
			cb.andEQ("cpostcode", cityCondition.getCpostcode());
		}
		if(null != cityCondition.getPid()){
			cb.andEQ("pid", cityCondition.getPid());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return cityDAO.findByCriteria(buildCriteria);
	}
	
	/**
	 * @Title: findByOrgan
	 * @Description: 列表
	 * @author: Ricky
	 * @param adviertisementInfoCondition
	 * @return: List<AdviertisementInfo>
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@Override
	public List<AdviertisementInfo> findByOrgan(AdviertisementInfoCondition adviertisementInfoCondition){
		CriteriaBuilder cb = Cnd.builder(AdviertisementInfo.class);
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertNo())){
			cb.andEQ("adviertNo", adviertisementInfoCondition.getAdviertNo());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getOrganNo())){
			cb.andEQ("organNo", adviertisementInfoCondition.getOrganNo());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertScope())){
			cb.andEQ("adviertScope", adviertisementInfoCondition.getAdviertScope());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getAdviertPosition())){
			cb.andEQ("adviertPosition", adviertisementInfoCondition.getAdviertPosition());
		}
		if(null != adviertisementInfoCondition.getStartDate()){
			cb.andLE("startDate", adviertisementInfoCondition.getStartDate());
			cb.andGE("endDate", adviertisementInfoCondition.getStartDate());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getPriority())){
			cb.andEQ("priority", adviertisementInfoCondition.getPriority());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getStatus())){
			cb.andEQ("status", adviertisementInfoCondition.getStatus());
		}
		if(!Strings.isEmpty(adviertisementInfoCondition.getRecordStatus())){
			cb.andEQ("recordStatus", adviertisementInfoCondition.getRecordStatus());
		}
		if(null != adviertisementInfoCondition.getCreateTime()){
			cb.andEQ("createTime", adviertisementInfoCondition.getCreateTime());
		}

		if(!Strings.isEmpty(adviertisementInfoCondition.getOperator())){
			cb.andEQ("operator", adviertisementInfoCondition.getOperator());
		}

		if(!Strings.isEmpty(adviertisementInfoCondition.getRemark())){
			cb.andLike("remark", adviertisementInfoCondition.getRemark());
		}
		
		Criteria buildCriteria = cb.buildCriteria();
		return adviertisementInfoDAO.findByCriteria(buildCriteria);
	}
	
	@Override
	public ChannelAdmin findByCondition(ChannelAdminCondition SysAdminCondition) {
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
		if(Strings.isNotEmpty(SysAdminCondition.getNodeSeq())){
			cb.addParam("nodeSeq", SysAdminCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return channelAdminDAO.findOneByCriteria(buildCriteria);
	}

	/**
	 * 根据订单号查询商户成本信息
	 * @param tradeNo
	 * @return MerchantCost
	 */
	@Override
	public MerchantCost findMerchantCostByTradeNo(String tradeNo) {
		if (Strings.isEmpty(tradeNo)) {
			new RuntimeException("查询订单编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(MerchantCost.class);
		cb.andEQ("tradeNo", tradeNo);
		Criteria buildCriteria = cb.buildCriteria();
		return merchantCostDAO.findOneByCriteria(buildCriteria);
	}
	
	/** 获得机构限额 */
	@Override
	public OrganLimit getOrganLimit(OrganLimitCondition organLimitCondition, String merchantNo){
		OrganLimit organLimit = null;
		OrganLimit findOrganLimit = null;
		CriteriaBuilder cbMerchantInfo = Cnd.builder(MerchantInfo.class);
		cbMerchantInfo.andEQ("merchantNo", merchantNo);
		Criteria buildCriteriaMerchantInfo = cbMerchantInfo.buildCriteria();
		MerchantInfo merchantInfo = merchantInfoDAO.findOneByCriteria(buildCriteriaMerchantInfo);
		organLimitCondition.setOrganNo(merchantInfo.getAgentNo());
		try {
			organLimit = (OrganLimit) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)));
			if(organLimit == null){
				CriteriaBuilder cb = Cnd.builder(OrganLimit.class);
				cb.andEQ("limitType", organLimitCondition.getLimitType());
				cb.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
				cb.andEQ("limitMode", organLimitCondition.getLimitMode());
				cb.andEQ("organNo", merchantInfo.getAgentNo());
				cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
				Criteria buildCriteria = cb.buildCriteria();
				organLimit = organLimitDAO.findOneByCriteria(buildCriteria);
				findOrganLimit = organLimit;
				
			}
			
			if(organLimit == null){
				organLimitCondition.setOrganNo(merchantInfo.getChannelNo());
				organLimit = (OrganLimit) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)));
				if(organLimit == null){
					CriteriaBuilder cb = Cnd.builder(OrganLimit.class);
					cb.andEQ("limitType", organLimitCondition.getLimitType());
					cb.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
					cb.andEQ("limitMode", organLimitCondition.getLimitMode());
					cb.andEQ("organNo", merchantInfo.getChannelNo());
					cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
					Criteria buildCriteria = cb.buildCriteria();
					organLimit = organLimitDAO.findOneByCriteria(buildCriteria);
					findOrganLimit = organLimit;
				}
			}
			if(organLimit == null){
				organLimitCondition.setOrganNo(ScanCodeConstants.UNLIMITED);
				organLimit = (OrganLimit) redisSharedCache.getObj(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)));
				if(organLimit == null){
					CriteriaBuilder cb = Cnd.builder(OrganLimit.class);
					cb.andEQ("limitType", organLimitCondition.getLimitType());
					cb.andEQ("limitPayCode", organLimitCondition.getLimitPayCode());
					cb.andEQ("limitMode", organLimitCondition.getLimitMode());
					cb.andEQ("organNo", ScanCodeConstants.UNLIMITED);
					cb.andEQ("status", ScanCodeConstants.STATUS_ACTIVE);
					Criteria buildCriteria = cb.buildCriteria();
					organLimit = organLimitDAO.findOneByCriteria(buildCriteria);
					findOrganLimit = organLimit;
				}
			}
		} catch (Exception e) {
			log.info("redis获得机构限额异常:" + e.getMessage());
		}
		if(findOrganLimit != null){
			try {
				redisSharedCache.setObj(new RedisKey(RedisKeyEnum.ORGAN_LIMIT.getGroup(), RedisKeyEnum.ORGAN_LIMIT.getKey()+this.getKey(organLimitCondition)), organLimit);
			} catch (Exception e) {
				log.error("#######保存数据到redis失败######");
			}
		}
		return organLimit;
	}
	
	private String getKey(OrganLimitCondition organLimitCondition){
		StringBuffer sb = new StringBuffer();
		sb.append(organLimitCondition.getOrganNo());
		sb.append(":");
		sb.append(organLimitCondition.getLimitType());
		sb.append(":");
		sb.append(organLimitCondition.getLimitPayCode());
		sb.append(":");
		sb.append(organLimitCondition.getLimitMode());
		return sb.toString();
	}
	
	/** 通过限额、商户优惠活动获取优惠的价格 */
	@Override
	public Map<String,BigDecimal> getActualPayCash(String merchantNo,BigDecimal orderAmt,String payCode){
		BigDecimal payCash = orderAmt;//支付金额
		BigDecimal discountCash = BigDecimal.ZERO;//优惠金额
		BigDecimal actualPayCash = BigDecimal.ZERO;//实际支付金额
		
		BigDecimal minLimit = BigDecimal.ZERO;//最低限额
		OrganLimitCondition organLimitCondition = new OrganLimitCondition();
		String limitPayCode = "";
		OrganLimit organLimit = null;
		if(Strings.equals(payCode, PayCode.PAYCODE_WXGZHZF.getCode())){
			limitPayCode = LimitPayCodeEnum.LIMIT_TYPE_WXGZH.getValue();
		}else if(Strings.equals(payCode, PayCode.PAYCODE_ZFBSMZF.getCode())){
			limitPayCode = LimitPayCodeEnum.LIMIT_TYPE_ZFB.getValue();
		}
		if(Strings.isNotEmpty(limitPayCode)){
			organLimitCondition.setLimitPayCode(limitPayCode);
			organLimitCondition.setLimitType(LimitTypeEnum.LIMIT_TYPE_JY.getValue());
			organLimitCondition.setLimitMode(LimitModeEnum.LIMIT_MODE_DB.getValue());
			organLimit = this.getOrganLimit(organLimitCondition,merchantNo);
		}
		
		if(organLimit != null){
			minLimit = organLimit.getMinLimit();
			if(orderAmt.compareTo(organLimit.getMinLimit()) == -1 || orderAmt.compareTo(organLimit.getMaxLimit()) == 1){
				 throw new RuntimeException("单笔交易金额" + organLimit.getMinLimit() + "元-" + organLimit.getMaxLimit() + "元");
			}
		}
		Map<String,BigDecimal> map = this.calculateCheapCash(merchantNo, payCash,minLimit);
		if(map==null||map.isEmpty()){
			actualPayCash = payCash;
		}else{
			discountCash = map.get("discountCash");
			actualPayCash = map.get("actualPayCash");
		}		
		log.info("支付金额:"+payCash+"，优惠金额" + discountCash +"，实际支付金额" + actualPayCash);
		return map;
	}

	@Override
	public OrderPayment findOrderPaymentByTradeNo(String tradeNo) {
		if (Strings.isEmpty(tradeNo)) {
			new RuntimeException("订单编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
		cb.andEQ("tradeNo", tradeNo);
		Criteria buildCriteria = cb.buildCriteria();
		return orderPaymentDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public MerchantStore findByCondition(MerchantStoreCondition merchantStoreCondition) {
		CriteriaBuilder cb = Cnd.builder(MerchantStore.class);
		if(!Strings.isEmpty(merchantStoreCondition.getId())){
			cb.andEQ("id", merchantStoreCondition.getId());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantNo())){
			cb.andEQ("merchantNo", merchantStoreCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreNo())){
			cb.andEQ("storeNo", merchantStoreCondition.getStoreNo());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreName())){
			cb.andLike("storeName", merchantStoreCondition.getStoreName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreType())){
			cb.andEQ("storeType", merchantStoreCondition.getStoreType());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServicePhone())){
			cb.andEQ("servicePhone", merchantStoreCondition.getServicePhone());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceBegin())){
			cb.andEQ("serviceBegin", merchantStoreCondition.getServiceBegin());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getServiceEnd())){
			cb.andEQ("serviceEnd", merchantStoreCondition.getServiceEnd());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddress())){
			cb.andEQ("storeAddress", merchantStoreCondition.getStoreAddress());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreAddressImg())){
			cb.andEQ("storeAddressImg", merchantStoreCondition.getStoreAddressImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreDesc())){
			cb.andEQ("storeDesc", merchantStoreCondition.getStoreDesc());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getLicenseName())){
			cb.andEQ("licenseName", merchantStoreCondition.getLicenseName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getName())){
			cb.andEQ("name", merchantStoreCondition.getName());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdCard())){
			cb.andEQ("idCard", merchantStoreCondition.getIdCard());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg1())){
			cb.andEQ("idcardImg1", merchantStoreCondition.getIdcardImg1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg2())){
			cb.andEQ("idcardImg2", merchantStoreCondition.getIdcardImg2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIdcardImg3())){
			cb.andEQ("idcardImg3", merchantStoreCondition.getIdcardImg3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicense())){
			cb.andEQ("merchantLicense", merchantStoreCondition.getMerchantLicense());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getMerchantLicenseImg())){
			cb.andEQ("merchantLicenseImg", merchantStoreCondition.getMerchantLicenseImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStorePhotosImg())){
			cb.andEQ("storePhotosImg", merchantStoreCondition.getStorePhotosImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getGroupPhotoImg())){
			cb.andEQ("groupPhotoImg", merchantStoreCondition.getGroupPhotoImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreImg())){
			cb.andEQ("storeImg", merchantStoreCondition.getStoreImg());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getIsPhoto())){
			cb.andEQ("isPhoto", merchantStoreCondition.getIsPhoto());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getOperator())){
			cb.andEQ("operator", merchantStoreCondition.getOperator());
		}
		if(null != merchantStoreCondition.getCreateTime()){
			cb.andEQ("createTime", merchantStoreCondition.getCreateTime());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditOperator())){
			cb.andEQ("auditOperator", merchantStoreCondition.getAuditOperator());
		}
		if(null != merchantStoreCondition.getAuditDate()){
			cb.andEQ("auditDate", merchantStoreCondition.getAuditDate());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getAuditReson())){
			cb.andEQ("auditReson", merchantStoreCondition.getAuditReson());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getStoreStatus())){
			cb.andEQ("storeStatus", merchantStoreCondition.getStoreStatus());
		}

		if(!Strings.isEmpty(merchantStoreCondition.getRemark())){
			cb.andLike("remark", merchantStoreCondition.getRemark());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp1())){
			cb.andEQ("temp1", merchantStoreCondition.getTemp1());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp2())){
			cb.andEQ("temp2", merchantStoreCondition.getTemp2());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp3())){
			cb.andEQ("temp3", merchantStoreCondition.getTemp3());
		}
		if(!Strings.isEmpty(merchantStoreCondition.getTemp4())){
			cb.andEQ("temp4", merchantStoreCondition.getTemp4());
		}
		if(Strings.isNotEmpty(merchantStoreCondition.getNodeSeq())){
			cb.addParam("nodeSeq", merchantStoreCondition.getNodeSeq());
		}
		Criteria buildCriteria = cb.buildCriteria();
		return merchantStoreDAO.findOneByCriteria(buildCriteria);
	}
	
	@Override
	public ChannelAdmin findByIdentityNo(String cashierNo) {
		return channelAdminDAO.findByIdentityNo(cashierNo);
	}

	/**
	 * 根据二维码ID 获取对应实体
	 * @param qrCode
	 * @return PlatformQrcode
	 */
	@Override
	public MerchantQrcode findByMerchantQrCode(String qrCode) {
		log.info("#########qrCode["+qrCode+"]#########");
		if (Strings.isEmpty(qrCode)) {
			new RuntimeException("二维码不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(MerchantQrcode.class);
		cb.andEQ("qrCode", qrCode);
		cb.andEQ("qrStatus", "1");
		cb.andEQ("recordStatus", "Y");
		Criteria criteria = cb.buildCriteria();
		MerchantQrcode code = merchantQrcodeDAO.findOneByCriteria(criteria);
		if(code!=null){
			String storeNo =code.getStoreId();
			try {
				MerchantStore store = (MerchantStore)redisSharedCache.getObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+storeNo));
				if(store!=null){
					code.setStoreName(store.getStoreName());
				}else{
					MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
					merchantStoreCondition.setStoreNo(storeNo);
					merchantStoreCondition.setRecordStatus("Y");
					merchantStoreCondition.setStoreStatus("3");
					store = findByCondition(merchantStoreCondition);
					if(null!=store){
						code.setStoreName(store.getStoreName());
						redisSharedCache.setObj(new RedisKey(RedisKeyEnum.STORE_BASE.getGroup(), RedisKeyEnum.STORE_BASE.getKey()+storeNo), store);
					}else{
						log.error("!!!!!!!!!!!商户二维码对应的门店不存在........");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return code;
	}

	/**
	 * 根据二维码ID 获取对应实体
	 * @param qrCode
	 * @return PlatformQrcode
	 */
	@Override
	public PlatformQrcode findPlatfromQrcodeByQrCode(String qrCode) {
		log.info("#########qrCode["+qrCode+"]#########");
		if (Strings.isEmpty(qrCode)) {
			new RuntimeException("二维码编号不能为空");
		}
		CriteriaBuilder cb = Cnd.builder(PlatformQrcode.class);
		cb.andEQ("qrCode", qrCode);
		Criteria criteria = cb.buildCriteria();
		return platformQrcodeDAO.findOneByCriteria(criteria);
	}
	
	@Override
	public OrderPayment findByCondition(OrderPaymentCondition orderPaymentCondition) {

		CriteriaBuilder cb = Cnd.builder(OrderPayment.class);
		if(!Strings.isEmpty(orderPaymentCondition.getId())){
			cb.andEQ("id", orderPaymentCondition.getId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTradeNo())){
			cb.andLike("tradeNo", orderPaymentCondition.getTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getChannelNo())){
			cb.andEQ("channelNo", orderPaymentCondition.getChannelNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getAgentNo())){
			cb.andEQ("agentNo", orderPaymentCondition.getAgentNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getMerchantNo())){
			cb.andEQ("merchantNo", orderPaymentCondition.getMerchantNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getQrCode())){
			cb.andEQ("qrCode", orderPaymentCondition.getQrCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPayCode())){
			cb.andEQ("payCode", orderPaymentCondition.getPayCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBusinessType())){
			cb.andEQ("businessType", orderPaymentCondition.getBusinessType());
		}
//		if(null != orderPaymentCondition.getOrderAmt()){
//			cb.andEQ("orderAmt", orderPaymentCondition.getOrderAmt());
//		}
		
		if(null != orderPaymentCondition.getQueryStartAmt()){
			cb.andGE("orderAmt", orderPaymentCondition.getQueryStartAmt());
		}
		if(null != orderPaymentCondition.getQueryEndAmt()){
			cb.andLE("orderAmt", orderPaymentCondition.getQueryEndAmt());
		}
		
		if(!Strings.isEmpty(orderPaymentCondition.getProductName())){
			cb.andEQ("productName", orderPaymentCondition.getProductName());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getProductDesc())){
			cb.andEQ("productDesc", orderPaymentCondition.getProductDesc());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBuyerId())){
			cb.andEQ("buyerId", orderPaymentCondition.getBuyerId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBuyerAccount())){
			cb.andEQ("buyerAccount", orderPaymentCondition.getBuyerAccount());
		}
		if(null != orderPaymentCondition.getTotalAmount()){
			cb.andEQ("totalAmount", orderPaymentCondition.getTotalAmount());
		}
		if(null != orderPaymentCondition.getBuyerPayAmount()){
			cb.andEQ("buyerPayAmount", orderPaymentCondition.getBuyerPayAmount());
		}
		if(null != orderPaymentCondition.getPointAmount()){
			cb.andEQ("pointAmount", orderPaymentCondition.getPointAmount());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getFeeStatus())){
			cb.andEQ("feeStatus", orderPaymentCondition.getFeeStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getFeeType())){
			cb.andEQ("feeType", orderPaymentCondition.getFeeType());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPaymentInfo())){
			cb.andEQ("paymentInfo", orderPaymentCondition.getPaymentInfo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getBatchId())){
			cb.andEQ("batchId", orderPaymentCondition.getBatchId());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getResultCode())){
			cb.andEQ("resultCode", orderPaymentCondition.getResultCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getResultInfo())){
			cb.andEQ("resultInfo", orderPaymentCondition.getResultInfo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPayTradeNo())){
			cb.andEQ("payTradeNo", orderPaymentCondition.getPayTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getPaymentType())){
			cb.andEQ("paymentType", orderPaymentCondition.getPaymentType());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getNotifyUrl())){
			cb.andEQ("notifyUrl", orderPaymentCondition.getNotifyUrl());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getReturnUrl())){
			cb.andEQ("returnUrl", orderPaymentCondition.getReturnUrl());
		} 
		if(null != orderPaymentCondition.getBeginTime()){
			cb.andGE("beginTime", orderPaymentCondition.getBeginTime());
		}
		if(null != orderPaymentCondition.getEndTime()){
			cb.andLE("beginTime", orderPaymentCondition.getEndTime());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getSettleTime())){
			cb.andEQ("settleTime", orderPaymentCondition.getSettleTime());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getSettleMerchant())){
			cb.andEQ("settleMerchant", orderPaymentCondition.getSettleMerchant());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getRefundStatus())){
			cb.andEQ("refundStatus", orderPaymentCondition.getRefundStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getOrderStatus())){
			cb.andEQ("orderStatus", orderPaymentCondition.getOrderStatus());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getOptCode())){
			cb.andEQ("optCode", orderPaymentCondition.getOptCode());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getRecordStatus())){
			cb.andEQ("recordStatus", orderPaymentCondition.getRecordStatus());
		}

		if(!Strings.isEmpty(orderPaymentCondition.getOperator())){
			cb.andEQ("operator", orderPaymentCondition.getOperator());
		}

		if(!Strings.isEmpty(orderPaymentCondition.getRemark())){
			cb.andLike("remark", orderPaymentCondition.getRemark());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp1())){
			cb.andEQ("temp1", orderPaymentCondition.getTemp1());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp2())){
			cb.andEQ("temp2", orderPaymentCondition.getTemp2());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp3())){
			cb.andEQ("temp3", orderPaymentCondition.getTemp3());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTemp4())){
			cb.andEQ("temp4", orderPaymentCondition.getTemp4());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getCashierNo())){
			cb.andEQ("cashier_no", orderPaymentCondition.getCashierNo());
		}
		if(null != orderPaymentCondition.getMerchantCost()){
			cb.andEQ("merchant_cost", orderPaymentCondition.getMerchantCost());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getStoreNo())){
			cb.andEQ("store_no", orderPaymentCondition.getStoreNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getTradeSource())){
			cb.andEQ("tradeSource", orderPaymentCondition.getTradeSource());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getExtraTradeNo())){
			cb.andEQ("extraTradeNo", orderPaymentCondition.getExtraTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getChannelTradeNo())){
			cb.andEQ("channelTradeNo", orderPaymentCondition.getChannelTradeNo());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getExtraCallBack())){
			cb.andEQ("extraCallBack", orderPaymentCondition.getExtraCallBack());
		}
		if(!Strings.isEmpty(orderPaymentCondition.getAccountType())){
			if(orderPaymentCondition.getAccountType().equals("2")){
				cb.andEQ("accountType", orderPaymentCondition.getAccountType());
			}else{
				cb.andNotEQ("accountType", "2");
			}
		}
		Criteria buildCriteria = cb.buildCriteria();
		OrderPayment orderPayment = orderPaymentDAO.findOneByCriteria(buildCriteria);
		return orderPayment;
	
	}
}
