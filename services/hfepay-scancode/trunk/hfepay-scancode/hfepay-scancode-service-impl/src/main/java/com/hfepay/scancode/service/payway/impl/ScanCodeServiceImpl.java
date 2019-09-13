package com.hfepay.scancode.service.payway.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.BusinessType;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.PayStatus;
import com.hfepay.scancode.commons.contants.PayType;
import com.hfepay.scancode.commons.contants.RefundStatus;
import com.hfepay.scancode.commons.contants.TradeType;
import com.hfepay.scancode.commons.contants.WithdrawsStatus;
import com.hfepay.scancode.commons.dao.MerchantQrcodeDAO;
import com.hfepay.scancode.commons.dao.PlatformQrcodeDAO;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.commons.entity.Withdrawals;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.AgentPaywayService;
import com.hfepay.scancode.service.operator.ChannelPaywayService;
import com.hfepay.scancode.service.operator.HfepayPaywayService;
import com.hfepay.scancode.service.operator.MerchantCashierQrService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;
import com.hfepay.scancode.service.operator.NodeRelationService;
import com.hfepay.scancode.service.operator.ParamsInfoService;
import com.hfepay.scancode.service.operator.QrcodeGoodsService;
import com.hfepay.scancode.service.order.OrderPayService;
import com.hfepay.scancode.service.order.OrderPaymentService;
import com.hfepay.scancode.service.order.WithdrawalsService;
import com.hfepay.scancode.service.payway.ScanCodeService;
import com.hfepay.scancode.service.utils.BatchUtils;

import net.sf.json.JSONObject;

/**
 * 平台网关处理
 * @author Administrator
 *
 */
@Service
public class ScanCodeServiceImpl implements ScanCodeService {

	public static final Logger log = LoggerFactory.getLogger(ScanCodeServiceImpl.class);
	
	@Autowired
	private PlatformQrcodeDAO platformQrcodeDAO;
	
	@Autowired
	private MerchantQrcodeDAO merchantQrcodeDAO;
	
	@Autowired
	private OrderPayService orderPayService;
	
	@Autowired
	private OrderPaymentService orderPaymentService;
	
	@Autowired
	private QrcodeGoodsService qrcodeGoodsService;
	
	@Autowired
	private MerchantPaywayService merchantPaywayService;
	
	@Autowired
	private AgentPaywayService agentPaywayService;
	
	@Autowired
	private ChannelPaywayService channelPaywayService;
	
	@Autowired
	private HfepayPaywayService hfepayPaywayService;
	
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private NodeRelationService nodeRelationService;
	
	@Autowired
	private MerchantCashierService merchantCashierService;
	
	@Autowired
	private MerchantCashierQrService merchantCashierQrService;
	
	@Autowired
	private WithdrawalsService withdrawalsService;
	
	@Autowired
	private ParamsInfoService paramsInfoService;
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;
	
	/**
	 * 根据二维码ID 获取对应实体
	 * @param qrCode
	 * @return PlatformQrcode
	 */
//	@Override
//	public PlatformQrcode findByQrCode(String qrCode) {
//		log.info("#########qrCode["+qrCode+"]#########");
//		CriteriaBuilder cb = Cnd.builder(PlatformQrcode.class);
//		cb.andEQ("qrCode", qrCode);
//		Criteria criteria = cb.buildCriteria();
//		return platformQrcodeDAO.findOneByCriteria(criteria);
//	}
	
	/**
	 * 根据二维码ID 获取对应实体
	 * @param qrCode
	 * @return PlatformQrcode
	 */
//	@Override
//	public MerchantQrcode findByMerchantQrCode(String qrCode) {
//		log.info("#########qrCode["+qrCode+"]#########");
//		CriteriaBuilder cb = Cnd.builder(MerchantQrcode.class);
//		cb.andEQ("qrCode", qrCode);
//		Criteria criteria = cb.buildCriteria();
//		return merchantQrcodeDAO.findOneByCriteria(criteria);
//	}

//	@Override
//	public Map<String, String> pay(OrderBo orderBo) {
//		log.info("#######orderBo["+orderBo.toString()+"]######");
//		Map<String, String> returnMap = new HashMap<>();
//		Map<String, String> wechatOffreturnMap = new HashMap<>();
//		
//		//新增参数校验
//		if (Strings.isEmpty(orderBo.getChannelNo()) || Strings.isEmpty(orderBo.getAgentNo()) || Strings.isEmpty(orderBo.getMerchantNo())) {
//			returnMap.put("returnCode", ScanCodeErrorCode.VALIDAT_100003.getCode());
//			returnMap.put("returnMsg", ScanCodeErrorCode.VALIDAT_100003.getDesc());
//			log.info("#######["+orderBo.getMerchantNo()+"]请求参数不合法######");
//			return returnMap;
//		}
//		
//		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(orderBo.getMerchantNo());
//		//保存支付订单
//		OrderPayment orderPayment = saveOrder(orderBo,merchantInfo);
//		
//		//支付参数未配置，直接失败
//		if (orderPayment.getOrderStatus().equals(OrderStatus.ORDER_FAIL.getCode())) {
//			returnMap.put("returnCode", ScanCodeErrorCode.VALIDAT_100003.getCode());
//			returnMap.put("returnMsg", ScanCodeErrorCode.VALIDAT_100003.getDesc());
//			log.info("#######qrcode["+orderBo.getQrCode()+"]请求参数不合法######");
//		}else {
//			//调用支付API
//			try {
//				
//				MerchantPayInfoVo vo = new MerchantPayInfoVo();
//				vo.setMerchantNo(merchantInfo.getPlatformMerchantNo());		//商户支付编号
//				vo.setUserOrderNo(orderPayment.getTradeNo());	//平台方订单号
//				vo.setPayCode(orderBo.getPayCode());			//支付通道编码
//				vo.setOrderAmt(orderBo.getPrice().toString());	//交易金额
//				vo.setOrderTitle(orderPayment.getProductName());
//				vo.setOrderDesc(orderPayment.getProductDesc());
//				
//				//是否支持信用卡:2禁用信用卡
//				if (merchantInfo.getCreditPayStatus().equals(CreditPayStatus.CREDIT_PAY_STATUS_CLOSE.getCode())) {					
//					vo.setLimitPay("1");
//				}
//				vo.setNotifyUrl(callbackConfig.getNotifyUrl());
//				
//				JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(orderPayment.getChannelNo());
//				log.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
//				vo.setReturnUrl(paramsInfoJson.getString("returnUrl")+"?tradeNo="+orderPayment.getTradeNo());
//				log.info("##########returnUrl：" + vo.getReturnUrl()+"##########");
////				vo.setReturnUrl(callbackConfig.getReturnUrl());
//				
//				//微信公众号支付
//				if (orderBo.getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode())) {
//					vo.setReturnType("02");							//返回类型 01:微信jsapi字符串 02:微信授权地址
//					//返回支付信息
//					wechatOffreturnMap = merchantBusinessService.merchantWechatOfficial(vo);					
//				}else if(orderBo.getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())){
//					//支付宝支付
//					wechatOffreturnMap = merchantBusinessService.merchantOrder(vo);
//				}else if(orderBo.getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())){
//					//微信扫码支付
//					wechatOffreturnMap = merchantBusinessService.merchantOrder(vo);
//				}else {
//					log.info("#######qrcode["+orderBo.getQrCode()+"]暂不支付的支付方式######");
//					returnMap.put("returnCode", ScanCodeErrorCode.TRADE_300003.getCode());
//					returnMap.put("returnMsg", ScanCodeErrorCode.TRADE_300003.getDesc());
//					return returnMap;
//				}
//				
//				//支付返回值
//				if (wechatOffreturnMap.get("respCode").equals(ScanCodeErrorCode.SYSTEM_000000.getCode())) {
//					returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_000000.getCode());
//					returnMap.put("payStr", wechatOffreturnMap.get("payStr"));
//				}else {
//					returnMap.put("returnCode", ScanCodeErrorCode.TRADE_200004.getCode());
//					returnMap.put("returnMsg", wechatOffreturnMap.get("respDesc"));
//				}
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				log.error("#########pay error:"+e);
//				returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_999999.getCode());
//				returnMap.put("returnMsg", ScanCodeErrorCode.SYSTEM_999999.getDesc());
//				return returnMap;
//			}
//		}
//		returnMap.put("tradeNo", orderPayment.getTradeNo());
//		log.info("########wechatOfficialMap["+JSON.toJSONString(returnMap)+"]#######");
//		
//		return returnMap;
//	}
	
	/**
	 * 保存订单数据
	 * @param orderBo
	 * @return OrderPayment
	 */
//	private OrderPayment saveOrder(OrderBo orderBo,MerchantInfo merchantInfo){
//		//保存费率信息
//		Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();
//		OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
//		
//		//根据二维码查收银员
//		MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
//		merchantCashierQrCondition.setQrCode(orderBo.getQrCode());
//		merchantCashierQrCondition.setIsCashier("2");
//		merchantCashierQrCondition.setRecordStatus(ScanCodeConstants.Y);
//		merchantCashierQrCondition.setMerchantNo(orderBo.getMerchantNo());
//		List<MerchantCashierQr> qrList = merchantCashierQrService.findAll(merchantCashierQrCondition);
//		//查看该支付二维码是否有收银员
//		if (null != qrList && qrList.size() > 0) {			
//			//获取收银员信息
//			MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
//			merchantCashierCondition.setMerchantNo(orderBo.getMerchantNo());
//			merchantCashierCondition.setCashierNo(qrList.get(0).getCashierNo());
//			MerchantCashier merchantCashier = merchantCashierService.findByCondition(merchantCashierCondition);
//			if (null != merchantCashier) {
//				orderPaymentCondition.setCashierNo(merchantCashier.getCashierNo());
//				orderPaymentCondition.setStoreNo(merchantCashier.getStoreNo());
//			}
//		}else {
//			orderPaymentCondition.setCashierNo(orderBo.getMerchantNo());
//			MerchantQrcode merchantQrcode = findByMerchantQrCode(orderBo.getQrCode());
//			PlatformQrcode platformQrcode = null;
//			String storeId = "";
//			if (null == merchantQrcode) {
//				platformQrcode = findByQrCode(orderBo.getQrCode());
//				if (null != platformQrcode) {
//					storeId = platformQrcode.getStoreId();
//				}
//			}else {
//				storeId = merchantQrcode.getStoreId();
//				
//			}
//			orderPaymentCondition.setStoreNo(storeId);
//		}
//		
//		orderPaymentCondition.setId(Strings.getUUID());
//		orderPaymentCondition.setBusinessType(BusinessType.BUSINESSTYPE_0.getCode());
//		orderPaymentCondition.setChannelNo(orderBo.getChannelNo());
//		orderPaymentCondition.setAgentNo(orderBo.getAgentNo());
//		orderPaymentCondition.setMerchantNo(orderBo.getMerchantNo());
//		orderPaymentCondition.setQrCode(orderBo.getQrCode());
//		orderPaymentCondition.setPayCode(orderBo.getPayCode());
//		orderPaymentCondition.setOrderAmt(orderBo.getPrice());
//		orderPaymentCondition.setTemp1(orderBo.getIp());
//		//获取二维码对应的产品信息
//		QrcodeGoods qrcodeGoods = qrcodeGoodsService.findByQrCode(orderBo.getQrCode());
//		if (null != qrcodeGoods) {
//			orderPaymentCondition.setProductName(qrcodeGoods.getGoodsName());
//			orderPaymentCondition.setProductDesc(qrcodeGoods.getGoodsDesc());
//		}else {
//			orderPaymentCondition.setProductName(merchantInfo.getMerchantName());
//			orderPaymentCondition.setProductDesc("");
//		}
//		
//		orderPaymentCondition.setBuyerId("");	//支付的账号id
//		orderPaymentCondition.setBuyerAccount("");	//支付账号
//		orderPaymentCondition.setTotalAmount(new BigDecimal(0.00));	//支付额度（用户支付的款+积分优惠）
//		orderPaymentCondition.setBuyerPayAmount(orderBo.getPrice());	//用户支付的款
//		orderPaymentCondition.setPointAmount(new BigDecimal(0.00));		//积分优惠
//		
//		//获取费率信息
//		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
//		merchantPaywayCondition.setPayCode(orderBo.getPayCode());
//		merchantPaywayCondition.setMerchantNo(orderBo.getMerchantNo());
//		List<MerchantPayway> merchantList = merchantPaywayService.findAll(merchantPaywayCondition);
//		if (null != merchantList && merchantList.size() > 0) {
//			//商户交易手续费
//			if (!merchantList.get(0).getStatus().equals("1")) {
//				orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200001.getCode());
//				orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200001.getDesc());
//				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
//				
//				log.error("#######merchantNo["+orderBo.getMerchantNo()+"]支付方式已被禁用，不能支付#######");
//			}
//		}else {
//			orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200002.getCode());
//			orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200002.getDesc());
//			orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
//			log.error("#######merchantNo["+orderBo.getMerchantNo()+"]未配置支付方式，不能支付#######");
//		}
//		
//		//代理商交易手续费
//		List<NodeRelation> dataNodeList = nodeRelationService.getParentsByCurrentNode(orderBo.getMerchantNo(), "1", false, false);//findByCurrentNode(orderBo.getMerchantNo());
//		for (int i = 0; i < dataNodeList.size(); i++) {
//			NodeRelation dataNode = dataNodeList.get(i);
//			AgentPaywayCondition agentPaywayCondition = new AgentPaywayCondition();
//			agentPaywayCondition.setPayCode(orderBo.getPayCode());
//			agentPaywayCondition.setAgentNo(dataNode.getCurrentNode());
//			List<AgentPayway> agentList = agentPaywayService.findAll(agentPaywayCondition);
//			if (null != agentList && agentList.size() >0) {
//				if (!agentList.get(0).getStatus().equals("1")) {				
//					orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200001.getCode());
//					orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200001.getDesc());
//					orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
//					log.error("#######agentNo["+orderBo.getAgentNo()+"]支付方式已被禁用，不能支付#######");
//				}
//				
//				rateMap.put("agent_"+dataNode.getParentNode()+"_t0", agentList.get(0).getT0Rate());
//				rateMap.put("agent_"+dataNode.getParentNode()+"_t1", agentList.get(0).getT1Rate());
//				rateMap.put("agent_"+dataNode.getParentNode()+"_rate", agentList.get(0).getRate());
//			}else {
//				log.error("#######agentNo["+orderBo.getAgentNo()+"]未配置支付方式，不能支付#######");
//				orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200002.getCode());
//				orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200002.getDesc());
//				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
//			}
//		}
//		
//		//渠道交易手续费
//		ChannelPaywayCondition channelPaywayCondition = new ChannelPaywayCondition();
//		channelPaywayCondition.setPayCode(orderBo.getPayCode());
//		channelPaywayCondition.setChannelNo(orderBo.getChannelNo());
//		List<ChannelPayway> channelList = channelPaywayService.findAll(channelPaywayCondition);
//		if (null != channelList && channelList.size() >0) {
//			if (!channelList.get(0).getStatus().equals("1")) {
//				log.error("#######channelNo["+orderBo.getChannelNo()+"]支付方式已被禁用，不能支付#######");
//				orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200001.getCode());
//				orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200001.getDesc());
//				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
//			}
//		}else {
//			orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200002.getCode());
//			orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200002.getDesc());
//			orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
//			log.error("#######channelNo["+orderBo.getChannelNo()+"]未配置支付方式，不能支付#######");
//		}
//		
//		//成本交易费率
//		HfepayPaywayCondition hfepayPaywayCondition = new HfepayPaywayCondition();
//		hfepayPaywayCondition.setPayCode(orderBo.getPayCode());
//		List<HfepayPayway> hfList = hfepayPaywayService.findAll(hfepayPaywayCondition);
//		
//		MerchantPayway merchantPayway = merchantList.get(0);
//		rateMap.put("merchantRate_t0", merchantPayway.getT0Rate());
//		rateMap.put("merchantRate_t1", merchantPayway.getT1Rate());
//		rateMap.put("merchantRate_rate", merchantPayway.getRate());
//		
//		HfepayPayway hfepayPayway = hfList.get(0);
//		rateMap.put("platform_t0", hfepayPayway.getT0Rate());
//		rateMap.put("platform_t1", hfepayPayway.getT1Rate());
//		rateMap.put("platform_rate", hfepayPayway.getRate());
//		
//		ChannelPayway channelPayway = channelList.get(0);
//		rateMap.put("channel_t0", channelPayway.getT0Rate());
//		rateMap.put("channel_t1", channelPayway.getT1Rate());
//		rateMap.put("channel_rate", channelPayway.getRate());
//		
//		orderPaymentCondition.setRateDetail(JSON.toJSONString(rateMap));
//		
//		orderPaymentCondition.setFeeType("0");	//计费方式 0：无需计费（默认）、1：事前计费、2：事后计费
//		orderPaymentCondition.setFeeStatus("0");//计费状态 0：未计费（默认）、1：已计费
//		orderPaymentCondition.setPaymentInfo("");
//		orderPaymentCondition.setPaymentType("03");	//支付方式 00: 帐务支付、01: 网关支付、02: 快捷支付、03: 扫码支付
//		orderPaymentCondition.setNotifyUrl(callbackConfig.getNotifyUrl());
//		orderPaymentCondition.setReturnUrl(callbackConfig.getReturnUrl());
//		orderPaymentCondition.setBeginTime(new Date());
//		orderPaymentCondition.setBatchId(BatchUtils.createBatchNo());
//		
//		orderPaymentCondition.setRefundStatus(RefundStatus.REFUND_N.getCode());	//退款状态（Y 已退款，N 未退款）
//		orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_TREATE.getCode());	//订单状态 00：等待付款、01：交易成功、03：交易失败
//		
//		//保存业务订单
//		OrderPayment orderPayment = orderPaymentService.saveOrderPayment(orderPaymentCondition);
//		
//		saveOrderPay(orderPaymentCondition, orderPayment);
//		
//		return orderPayment;
//	}

	/**
	 * 保存支付订单
	 * @param orderPaymentCondition
	 * @param orderPayment
	 */
	private void saveOrderPay(OrderPaymentCondition orderPaymentCondition, OrderPayment orderPayment) {
		//保存支付订单
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		orderPayCondition.setId(Strings.getUUID());
		orderPayCondition.setTradeType(TradeType.TRADE_TYPE_PAY.getCode());	//交易订单类型 01:提现、02:支付
		orderPayCondition.setBeginTime(new Date());
		if (orderPaymentCondition.getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode()) || orderPaymentCondition.getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())) {			
			orderPayCondition.setPayType(PayType.PAYTYPE_WXGZH.getCode());
		}else if(orderPaymentCondition.getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())){
			orderPayCondition.setPayType(PayType.PAYTYPE_ZFB.getCode());
		}
		
		//支付失败
		if (orderPaymentCondition.getOrderStatus().equals(OrderStatus.ORDER_FAIL.getCode())) {
			orderPayCondition.setErrorCode(orderPaymentCondition.getResultCode());
			orderPayCondition.setErrorMsg(orderPaymentCondition.getResultCode());
		}
		orderPayCondition.setPayStatus(PayStatus.PAYSTATUS_CHANNEL_TREATE.getCode());	//支付状态 00：支付成功、01：支付失败、02：渠道处理中、03：记账处理中
		orderPayService.createPayOrder(orderPayment,orderPayCondition);
	}

//	@Override
//	public Map<String, String> withdraws(OrderBo orderBo,MerchantInfo merchantInfo) {
//		log.info("#######orderBo["+orderBo.toString()+"]#######");
//		Map<String, String> returnMap = new HashMap<>();
//		Map<String, String> withdrawsReturnMap = new HashMap<>();
//		WithdrawalsCondition withdrawalsCondition = new WithdrawalsCondition();
//		
//		//保存订单信息
//		Withdrawals withdrawals = this.saveWithdraws(orderBo);
//		
//		//构造提现
//		MerchantWithdrawsInfoVo withdrawsInfo = new MerchantWithdrawsInfoVo();
//		try {
//			withdrawsInfo.setUserOrderNo(withdrawals.getTradeNo());
//			withdrawsInfo.setPayCode(withdrawals.getPayCode());
//			withdrawsInfo.setReturnURL(callbackConfig.getWithdrawsNotifyUrl());
//			withdrawsInfo.setMerchantNo(merchantInfo.getPlatformMerchantNo());
//			withdrawsInfo.setRemark("");
//			withdrawsReturnMap = merchantBusinessService.merchantWithdraws(withdrawsInfo);
//			
//			//通讯成功
//			if (withdrawsReturnMap.get("respCode").equals(ScanCodeErrorCode.SYSTEM_000000.getCode())) {
//				log.info("#######respCode:"+withdrawsReturnMap.get("respCode")+"#######");
//				log.info("#######status:"+withdrawsReturnMap.get("status")+"#######");
//				//受理成功
//				if (withdrawsReturnMap.get("status").equals("0")) {
//					returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_000000.getCode());
//					returnMap.put("returnMsg", ScanCodeErrorCode.SYSTEM_000000.getDesc());
//					
//					withdrawalsCondition.setPayTradeNo(withdrawsReturnMap.get("orderNo"));
//					
//					withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_CHANNEL_TREATE.getCode());
//					withdrawalsCondition.setResultInfo(WithdrawsStatus.WD_STATUS_CHANNEL_TREATE.getDesc());
//				}else {
//					withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_FAIL.getCode());
//					withdrawalsCondition.setResultInfo(WithdrawsStatus.WD_STATUS_FAIL.getDesc());
//					returnMap.put("returnCode", ScanCodeErrorCode.TRADE_400001.getCode());
//					returnMap.put("returnMsg", ScanCodeErrorCode.TRADE_400001.getDesc());
//				}
//			}else {
//				log.info("#######respCode:"+withdrawsReturnMap.get("respCode")+"#######");
//				withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_FAIL.getCode());
//				withdrawalsCondition.setResultInfo(withdrawsReturnMap.get("respDesc"));
//				returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_999998.getCode());
//				returnMap.put("returnMsg", withdrawsReturnMap.get("respDesc"));
//			}
//			withdrawalsCondition.setId(withdrawals.getId());
//			withdrawalsService.update(withdrawalsCondition);
//		} catch (Exception e) {
//			log.error("#########withdraws error:"+e);
//			returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_999999.getCode());
//			returnMap.put("returnMsg", ScanCodeErrorCode.SYSTEM_999999.getDesc());
//			
//			withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_FAIL.getCode());
//			withdrawalsCondition.setResultInfo(ScanCodeErrorCode.SYSTEM_999999.getDesc());
//			withdrawalsCondition.setId(withdrawals.getId());
//			withdrawalsService.update(withdrawalsCondition);
//			return returnMap;
//		}
//		
//		return returnMap;
//	}

	private Withdrawals saveWithdraws(OrderBo orderBo) {
		String bathId = BatchUtils.createBatchNo();
		WithdrawalsCondition withdrawalsCondition = new WithdrawalsCondition();
		withdrawalsCondition.setId(Strings.getUUID());
		withdrawalsCondition.setBatchId(bathId);
		withdrawalsCondition.setChannelNo(orderBo.getChannelNo());
		withdrawalsCondition.setAgentNo(orderBo.getAgentNo());
		withdrawalsCondition.setMerchantNo(orderBo.getMerchantNo());
		withdrawalsCondition.setPayCode(orderBo.getPayCode());
		withdrawalsCondition.setBeginTime(new Date());
		withdrawalsCondition.setRefundStatus(RefundStatus.REFUND_N.getCode());
		withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_CREATE.getCode());
		withdrawalsCondition.setResultInfo(WithdrawsStatus.WD_STATUS_CREATE.getDesc());
		Withdrawals withdrawals = withdrawalsService.createWithdraws(withdrawalsCondition);
		
		//保存支付订单
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		orderPayCondition.setId(Strings.getUUID());
		orderPayCondition.setChannelNo(orderBo.getChannelNo());
		orderPayCondition.setAgentNo(orderBo.getAgentNo());
		orderPayCondition.setMerchantNo(orderBo.getMerchantNo());
		orderPayCondition.setTradeNo(withdrawals.getTradeNo());
		orderPayCondition.setPayCode(orderBo.getPayCode());
		orderPayCondition.setTradeType(TradeType.TRADE_TYPE_WITHDRAW.getCode());
		orderPayCondition.setBusinessType(BusinessType.BUSINESSTYPE_0.getCode());
		orderPayCondition.setPayOrderType("0");
		
		ParamsInfo info = payCallBackOperatorService.findParamsKey(ScanCodeConstants.SYSTEM, ParamsType.PARAMS_CALLBACK_INFO.getCode());
		JSONObject callbackJson = JSONObject.fromObject(info.getParamsValue());
		orderPayCondition.setNotifyUrl(callbackJson.getString("withdrawsNotifyUrl"));
		
		//orderPayCondition.setNotifyUrl(callbackConfig.getWithdrawsNotifyUrl());
		orderPayCondition.setBatchId(bathId);
		orderPayCondition.setRefundStatus(RefundStatus.REFUND_N.getCode());
		orderPayCondition.setPayStatus(PayStatus.PAYSTATUS_ACCOUNTS_WAIT.getCode());
		orderPayCondition.setOrderAmt(new BigDecimal(0));
		if (orderBo.getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode()) || orderBo.getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())) {			
			orderPayCondition.setPayType(PayType.PAYTYPE_WXGZH.getCode());
		}else if(orderBo.getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())){
			orderPayCondition.setPayType(PayType.PAYTYPE_ZFB.getCode());
		}
		
		orderPayService.createWithdrawsOrder(orderPayCondition);
		
		return withdrawals;
	}

}
