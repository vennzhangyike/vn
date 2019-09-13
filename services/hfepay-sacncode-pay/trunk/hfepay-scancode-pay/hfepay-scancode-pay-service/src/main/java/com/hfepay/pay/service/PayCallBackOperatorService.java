package com.hfepay.pay.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

public interface PayCallBackOperatorService {
	
	/**
	 * 根据订单号查询商户成本信息
	 * @param tradeNo
	 * @return MerchantCost
	 */
	public MerchantCost findMerchantCostByTradeNo(String tradeNo);
	
	/**
	 * 列表
	 * @param merchantPayway 
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-21 10:21:59
	 */
	public List<MerchantPayway> findAll(MerchantPaywayCondition merchantPaywayCondition);
	
	/**
	 * 根据商户编号，支付方式查询费率信息
	 * @param merchantNo
	 * @param payCode
	 * @return MerchantPayway
	 */
	public MerchantPayway findMerchantPaywayByMerchantPayCode(String merchantNo,String payCode);
	
	/**
	 * 列表
	 * @param merchantCashierQr 
	 *
	 * @author panq
	 * @date CreateDate : 2016-11-10 16:13:31
	 */
	public List<MerchantCashierQr> findAll(MerchantCashierQrCondition merchantCashierQrCondition);
	
	/**
	 * 条件查询单个收银员
	 * @Title: findByCondition
	 * @Description: TODO
	 * @author: husain
	 * @param merchantCashierCondition
	 * @return
	 * @return: MerchantCashier
	 */
	public MerchantCashier findByCondition(MerchantCashierCondition merchantCashierCondition);
	
	/**
	 * 查询可以推送消息的商户，也就是open_id!=id
	 * @param merchantNo
	 * @return
	 */
	public ChannelAdmin findPushMsgAdmin(String merchantNo,boolean isMerchant);
	
	/**
	 * 查询可以推送消息的商户，也就是open_id!=id
	 * @param merchantNo
	 * @return
	 */
	public ChannelAdmin findPushMsgAdminByMerchantNo(String merchantNo,String flag);
	
	/**
	 * @Title: findByChannelNo
	 * @Description: 渠道编码查找
	 * @author: Ricky
	 * @param channelNo
	 * @return: ChannelBase
	 * @date CreateDate : 2016-10-13 15:19:03
	 */
	public MerchantInfo findByMerchantNo(String MerchantNo);
	
	/**
	 * @Title: insert
	 * @Description: 新增
	 * @author: Ricky
	 * @param merchantCostCondition
	 * @return: long
	 * @date CreateDate : 2016-11-10 13:59:57
	 */
	long insert(MerchantCostCondition merchantCostCondition);
	
	/**
	 * @Title: getParamsInfoByDomain
	 * @Description: 条件更新
	 * @author: Ricky
	 * @param organNo
	 * @return: JSONObject
	 * @date CreateDate : 2016-12-02 13:42:35
	 */
	JSONObject getParamsInfoByDomain(String organ);
	
	/**
	 * 当前节点查找父节点
	 * @param currentNode 当前节点
	 * @param excludeIdentityFlag 要排除的节点标示，如不需要找到渠道，那么excludeIdentityFlag=1
	 * @param isDirect 是否是直接父接点（true seq=substring(长度为自身seq长度-4)；false 迭代自身seq找出父节点）
	 * @param isIncludeSelf是否包含自身
	 * @author husain
	 * @return
	 */
	public List<NodeRelation> getParentsByCurrentNode(String currentNode,String excludeIdentityFlag,boolean isDirect,boolean isIncludeSelf);

	/**
	 * 根据代理商编号,支付方式查询费率信息
	 * @param agentNo
	 * @param payCode
	 * @return AgentPayway
	 */
	public AgentPayway findAgentPayWayByAgentNoPayCode(String agentNo,String payCode);
	
	/**
	 * 根据渠道编号,支付方式查询费率信息
	 * @param channelNo
	 * @param payCode
	 * @return AgentPayway
	 */
	public ChannelPayway findChannelPayWayByChannelNoPayCode(String channelNo,String payCode);
	
	/**
	 * @Title: findAll
	 * @Description: 列表
	 * @author: Ricky
	 * @param hfepayPaywayCondition
	 * @return: List<HfepayPayway>
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	public List<HfepayPayway> findAll(HfepayPaywayCondition hfepayPaywayCondition);
	
	public ParamsInfo findParamsKey(String paramsKey,String keyType);
	
	public NodeRelation findByCurrentNode(String currentNode);
	
	public NodeRelation findByCondition(NodeRelationCondition nodeRelationCondition);
	
	public String getSeq(String currentNode);
	
	/**
	 * 主键查找
	 *
	 * @author panq
	 * @date CreateDate : 2016-10-25 17:49:38
	 */
	QrcodeGoods findByQrCode(String qrCode);
	
	
	/**
	 * @Title: findByQrcode
	 * @Description: 根据二维码编号查找
	 * @author: husain
	 * @param qrCode
	 * @return
	 * @return: PlatformQrcode
	 */
	public PlatformQrcode findByQrcode(String qrCode);

	
	public Map<String, BigDecimal> calculateCheapCash(String merchantNo, BigDecimal orderAmt,BigDecimal minLimit);
	public MerchantActivity findByMerchantNoAndStatus(String merchantNo, String status);
	public List<MerchantActivityDiscount> findAll(MerchantActivityDiscountCondition merchantActivityDiscountCondition);

//	public List<AgentBase> findAll(AgentBaseCondition agentBaseCondition);

	public AgentPayway findAgentPaywayByPayCode(String payCode, String agentNo);

	public ChannelBase findByChannelNo(String channelNo);

//	public MerchantPayway findMerchantPaywayByPayCode(String payCode, String merchantNo);

	public List<MerchantInfo> findMerchantInfoByQrCode(String qrCode);

	public ChannelPayway findChannelPaywayByPayCode(String payCode, String channelNo);
	
	/**
	 * 代理商编号查找
	 * @author panq
	 * @date CreateDate : 2016-10-19 16:04:33
	 */
	AgentBase findByAgentNo(String agentNo);

	/**
	 * 
	 * @param orderPaymentCondition
	 * @return
	 */
	public List<OrderPayment> findAll(OrderPaymentCondition orderPaymentCondition);
	
	/**
	 * 根据订单编号查询订单信息
	 * @param tradeNo
	 * @return OrderPayment
	 */
	public OrderPayment findOrderPaymentByTradeNo(String tradeNo);

	/**
	 * 
	 * @param adviertismentBo
	 * @return
	 */
	public List<AdviertisementInfo> getAdviertisInfoByUser(AdviertismentBo adviertismentBo);
	
	/**
	 * @Title: findByCname
	 * @Description: 列表
	 * @author: Ricky
	 * @param cityCondition
	 * @return: List<City>
	 * @date CreateDate : 2017-01-22 11:18:09
	 */
	public List<City> findByCname(CityCondition cityCondition);
	
	public List<AdviertisementInfo> findByOrgan(AdviertisementInfoCondition adviertisementInfoCondition);

	public ChannelAdmin findByCondition(ChannelAdminCondition adminCondition);

	/** 获得机构限额 */
	OrganLimit getOrganLimit(OrganLimitCondition organLimitCondition, String merchantNo);

	/** 通过限额、商户优惠活动获取优惠的价格 */
	Map<String,BigDecimal> getActualPayCash(String merchantNo,BigDecimal orderAmt,String payCode);

	//根据条件查询商户门店信息
	public MerchantStore findByCondition(MerchantStoreCondition storeCondition);

	public ChannelAdmin findByIdentityNo(String cashierNo);

	//根据商户二维码编号查询商户二维码
	public MerchantQrcode findByMerchantQrCode(String qrCode);

	//根据二维码编号查询平台二维码
	public PlatformQrcode findPlatfromQrcodeByQrCode(String qrCode);

	public OrderPayment findByCondition(OrderPaymentCondition condition);

}
