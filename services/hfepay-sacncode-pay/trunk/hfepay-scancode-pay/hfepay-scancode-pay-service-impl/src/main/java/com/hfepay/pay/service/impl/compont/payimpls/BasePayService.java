package com.hfepay.pay.service.impl.compont.payimpls;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.pay.service.exception.PayException;
import com.hfepay.pay.service.impl.compont.BasePayQueryBiz;
import com.hfepay.pay.utils.BatchUtils;
import com.hfepay.pay.utils.OrderIDUtils;
import com.hfepay.scancode.api.condition.ApiLogCondition;
import com.hfepay.scancode.api.entity.ApiLog;
import com.hfepay.scancode.api.entity.message.PaySuccessMessage;
import com.hfepay.scancode.api.entity.vo.MerchantPayInfoVo;
import com.hfepay.scancode.api.service.ApiLogService;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.condition.HfepayPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.contants.BusinessType;
import com.hfepay.scancode.commons.contants.CreditPayStatus;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.contants.OrderResultCode;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.PayStatus;
import com.hfepay.scancode.commons.contants.RateType;
import com.hfepay.scancode.commons.contants.RefundStatus;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.contants.TradeType;
import com.hfepay.scancode.commons.dao.OrderPayDAO;
import com.hfepay.scancode.commons.dao.OrderPaymentDAO;
import com.hfepay.scancode.commons.entity.AgentPayway;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelPayway;
import com.hfepay.scancode.commons.entity.HfepayPayway;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.NodeRelation;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.commons.entity.QrcodeGoods;

import net.sf.json.JSONObject;
/**
 * 支付业务的基类
 * @author husain
 *
 */
@Component
public class BasePayService {
	public static final Logger log = LoggerFactory.getLogger(BasePayService.class);
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;
	@Autowired
	private OrderIDUtils orderUtils;
	@Autowired
	private OrderPaymentDAO orderPaymentDAO;
	@Autowired
	private OrderPayDAO orderPayDAO;
	@Autowired
	private BasePayQueryBiz basePayQueryBiz;
	@Autowired
	private ApiLogService apiLogApiService;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	
	//调用支付接口，不同的支付方式调用的接口不同，如微信支付和微信公众号支付的地址不同
	protected Map<String, String> executePay(MerchantPayInfoVo vo) throws Exception{
		return merchantBusinessService.merchantOrder(vo);
	}
	
	/**
	 * 限额处理
	 * @param orderBo
	 * @return
	 */
	protected void limitCash(OrderBo orderBo) throws PayException{
		BigDecimal tradeAmt = orderBo.getTradeAmt();
		try {
			Map<String,BigDecimal> map = payCallBackOperatorService.getActualPayCash(orderBo.getMerchantNo(), tradeAmt, orderBo.getPayCode());
			if(map!=null&&!map.isEmpty()){
				orderBo.setDiscountAmt(map.get("discountCash"));
				orderBo.setPrice(map.get("actualPayCash"));
			}else{//不存在打折
				orderBo.setDiscountAmt(new BigDecimal("0"));
				orderBo.setPrice(orderBo.getTradeAmt());
			}
		} catch (Exception e) {
			throw new PayException(ScanCodeErrorCode.PAY_500002.getCode(), ScanCodeErrorCode.PAY_500002.getDesc());
		}
	}
	
	/**
	 * 保存订单数据
	 * @param orderBo
	 * @return OrderPayment
	 */
	protected OrderPayment saveOrder(OrderBo orderBo) throws PayException{
		OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
		//设置收银人和门店编号,该参数一定存在
		orderPaymentCondition.setCashierNo(orderBo.getCashier());
		orderPaymentCondition.setStoreNo(orderBo.getStoreNo());
		orderPaymentCondition.setId(Strings.getUUID());
		orderPaymentCondition.setBusinessType(BusinessType.BUSINESSTYPE_0.getCode());
		orderPaymentCondition.setChannelNo(orderBo.getChannelNo());
		orderPaymentCondition.setAgentNo(orderBo.getAgentNo());
		orderPaymentCondition.setMerchantNo(orderBo.getMerchantNo());
		orderPaymentCondition.setQrCode(orderBo.getQrCode());
		orderPaymentCondition.setPayCode(orderBo.getPayCode());
		orderPaymentCondition.setOrderAmt(orderBo.getPrice());
		orderPaymentCondition.setTemp1(orderBo.getIp());
		orderPaymentCondition.setTradeAmt(orderBo.getTradeAmt());
		orderPaymentCondition.setDiscountAmt(orderBo.getDiscountAmt());
		orderPaymentCondition.setDiscountStrategy(orderBo.getDiscountStrategy());
		orderPaymentCondition.setTemp3(orderBo.getPayStrategy());//支付策略
		//设置默认的产品名称支付参数不可缺失
		orderPaymentCondition.setProductName(orderBo.getProductName());
		orderPaymentCondition.setProductDesc("");
		//获取二维码对应的产品信息
		setProduct(orderPaymentCondition, orderBo);
		
		orderPaymentCondition.setBuyerId("");	//支付的账号id
		orderPaymentCondition.setBuyerAccount("");	//支付账号
		orderPaymentCondition.setTotalAmount(new BigDecimal(0.00));	//支付额度（用户支付的款+积分优惠）
		orderPaymentCondition.setBuyerPayAmount(orderBo.getPrice());	//用户支付的款
		orderPaymentCondition.setPointAmount(new BigDecimal(0.00));		//积分优惠
		//费率信息
		setRate(orderBo,orderPaymentCondition);
		orderPaymentCondition.setFeeType("0");	//计费方式 0：无需计费（默认）、1：事前计费、2：事后计费
		orderPaymentCondition.setFeeStatus("0");//计费状态 0：未计费（默认）、1：已计费
		orderPaymentCondition.setPaymentInfo("");
		orderPaymentCondition.setPaymentType("03");	//支付方式 00: 帐务支付、01: 网关支付、02: 快捷支付、03: 扫码支付
		//设置回调地址信息
		setCallbackUrl(orderPaymentCondition);
		orderPaymentCondition.setBeginTime(new Date());
		orderPaymentCondition.setBatchId(BatchUtils.createBatchNo());
		
		orderPaymentCondition.setRefundStatus(RefundStatus.REFUND_N.getCode());	//退款状态（Y 已退款，N 未退款）
		if(Strings.isEmpty(orderPaymentCondition.getOrderStatus())){
			orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_TREATE.getCode());	//订单状态 00：等待付款、01：交易成功、03：交易失败
		}
		
		orderPaymentCondition.setTradeNo(orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "ZF"));
		//对外接口相关
		orderPaymentCondition.setTradeSource(orderBo.getRequestSource());//交易来源默认网页
		orderPaymentCondition.setExtraTradeNo(orderBo.getUserOrderNo());//接口上送的流水号
		orderPaymentCondition.setExtraCallBack(orderBo.getNotifyUrl());//接口上送的回调地址
		orderPaymentCondition.setChannelTradeNo(null);//微信或者支付宝返回的流水号
		
		//保存业务订单
		OrderPayment orderPayment = new OrderPayment();
		BeanUtils.copyProperties(orderPaymentCondition, orderPayment);
		orderPaymentDAO.insert(orderPayment);
		
		OrderPay orderPay = new OrderPay();
		BeanUtils.copyProperties(orderPayment, orderPay);
		//支付失败
		if (orderPayment.getOrderStatus().equals(OrderStatus.ORDER_FAIL.getCode())) {
			orderPay.setErrorCode(orderPayment.getResultCode());
			orderPay.setErrorMsg(orderPayment.getResultCode());
		}
		saveOrderPay(orderPay);
		
		return orderPayment;
	}
	
	/**
	 * 必须参数校验
	 * @param orderBo
	 * @throws PayException
	 */
	protected void checkParams(OrderBo orderBo) throws PayException{
		//新增参数校验
		if (Strings.isEmpty(orderBo.getChannelNo()) || Strings.isEmpty(orderBo.getAgentNo()) 
				|| Strings.isEmpty(orderBo.getMerchantNo())||Strings.isEmpty(orderBo.getCashier())
				||Strings.isEmpty(orderBo.getStoreNo())||null==orderBo.getTradeAmt()
				||orderBo.getTradeAmt().compareTo(new BigDecimal("0"))<=0
				||Strings.isEmpty(orderBo.getPayCode())) {
			log.info("####### checkParams ["+orderBo+"]请求参数不合法######");
			throw new PayException(ScanCodeErrorCode.VALIDAT_100003.getCode(), ScanCodeErrorCode.VALIDAT_100003.getDesc());
		}
	}
	
	/**
	 * 保存支付订单
	 * @param orderPay
	 */
	protected void saveOrderPay(OrderPay orderPay) {
		orderPay.setId(Strings.getUUID());
		orderPay.setTradeType(TradeType.TRADE_TYPE_PAY.getCode());	//交易订单类型 01:提现、02:支付
		orderPay.setBeginTime(new Date());
		orderPay.setPayStatus(PayStatus.PAYSTATUS_CHANNEL_TREATE.getCode());	//支付状态 00：支付成功、01：支付失败、02：渠道处理中、03：记账处理中
		orderPay.setPayNo(orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "ZF"));
		orderPayDAO.insert(orderPay);
	}
	
	/**
	 * 支付参数组装
	 */
	protected MerchantPayInfoVo getPayParams(MerchantInfo merchantInfo,OrderBo orderBo,OrderPayment orderPayment) {
		MerchantPayInfoVo vo = new MerchantPayInfoVo();
		vo.setMerchantNo(merchantInfo.getPlatformMerchantNo());		//商户支付编号
		vo.setUserOrderNo(orderPayment.getTradeNo());	//平台方订单号
		vo.setPayCode(orderBo.getPayCode());			//支付通道编码
		vo.setOrderAmt(orderBo.getPrice().toString());	//交易金额
		vo.setOrderTitle(orderPayment.getProductName());
		vo.setOrderDesc(orderPayment.getProductDesc());
		
		//是否支持信用卡:2禁用信用卡
		if (merchantInfo.getCreditPayStatus().equals(CreditPayStatus.CREDIT_PAY_STATUS_CLOSE.getCode())) {					
			vo.setLimitPay("1");
		}
		vo.setNotifyUrl(orderPayment.getNotifyUrl());
		log.info("==========NotifyUrl：" + vo.getNotifyUrl());
		vo.setReturnUrl(orderPayment.getReturnUrl()+"?tradeNo="+orderPayment.getTradeNo());
		log.info("##########returnUrl：" + vo.getReturnUrl()+"##########");
		return vo;
	}
	
	/**
	 *	处理返回结果
	 * @param wechatOffreturnMap
	 * @param returnMap
	 */
	protected void handleReturnMap(Map<String, String> wechatOffreturnMap,Map<String, String> returnMap) {
		if (wechatOffreturnMap.get("respCode").equals(ScanCodeErrorCode.SYSTEM_000000.getCode())) {
			returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_000000.getCode());
			returnMap.put("payStr", wechatOffreturnMap.get("payStr"));
		}else {
			returnMap.put("returnCode", ScanCodeErrorCode.TRADE_200004.getCode());
			returnMap.put("returnMsg", wechatOffreturnMap.get("respDesc"));
		}
	}
	
	/**
	 * 创建商户成本记录
	 * @param bo
	 * @param orderPay
	 */
	protected MerchantCostCondition createMerchantCost(OrderPay orderPay) {
		//获取费率信息
		MerchantPayway merchantPayway = payCallBackOperatorService.findMerchantPaywayByMerchantPayCode(orderPay.getMerchantNo(),orderPay.getPayCode());
		
		//支付成功，记录商户成本
		MerchantCostCondition merchantCostCondition = new MerchantCostCondition();
		merchantCostCondition.setId(Strings.getUUID());
		merchantCostCondition.setTradeNo(orderPay.getTradeNo());
		merchantCostCondition.setChannelNo(orderPay.getChannelNo());
		merchantCostCondition.setAgentNo(orderPay.getAgentNo());
		merchantCostCondition.setMerchantNo(orderPay.getMerchantNo());
		merchantCostCondition.setQrCode(orderPay.getQrCode());
		merchantCostCondition.setPayCode(orderPay.getPayCode());
		merchantCostCondition.setType(orderPay.getTradeType());
		merchantCostCondition.setAmount(orderPay.getOrderAmt());
		
		//支付
		//if (orderPay.getTradeType().equals(TradeType.TRADE_TYPE_PAY.getCode())) {
			merchantCostCondition.setRateType(RateType.RATE_TYPE_T1.getCode());
			merchantCostCondition.setMerchantRate(merchantPayway.getT1Rate());
			//交易成本 = 交易金额 *T1费率 
			merchantCostCondition.setMerchantCost(orderPay.getOrderAmt().multiply(merchantPayway.getT1Rate()));
//		}else {	//提现
//			merchantCostCondition.setRateType(RateType.RATE_TYPE_T0.getCode());
			//提现成本 = 交易金额 *(T0费率+T1费率 ) + 固定提现手续费
			//orderPay.getOrderAmt().multiply(merchantPayway.getT0Rate().add(merchantPayway.getT1Rate())).add(merchantPayway.getRate())
//			merchantCostCondition.setRateType(RateType.RATE_TYPE_T0.getCode());
//			merchantCostCondition.setMerchantCost(orderPay.getMerchantCost());
//			merchantCostCondition.setMerchantRate(merchantPayway.getT0Rate().add(merchantPayway.getT1Rate()));
//		}
		merchantCostCondition.setFixedAmount(merchantPayway.getRate());
		merchantCostCondition.setCreateTime(new Date());
		basePayQueryBiz.insert(merchantCostCondition);
		
		return merchantCostCondition;
	}

	/**
	 * 交易成功消息推送
	 * @param bo
	 * @param orderPayMentList
	 * @throws Exception
	 */
	protected PaySuccessMessage getPushMessage(String status,OrderPayment orderPayment){
		log.info("#########PushMessage["+orderPayment.getMerchantNo()+"]########");
		//推送支付信息给商户
		PaySuccessMessage message = new PaySuccessMessage();
		
		//收银员或者商户一定有openID
		ChannelAdmin channelAdmin = payCallBackOperatorService.findByIdentityNo(orderPayment.getCashierNo());
		if(null!=channelAdmin){
			message.setTouser(channelAdmin.getOpenId());
		}
		message.setOrderNo(orderPayment.getTradeNo());
		message.setOrganNo(orderPayment.getAgentNo());
//		if (bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
//			message.setTitle("收款成功");
//			message.setRemark("您出售的商品已成功收款");
//		}else {
//			message.setTitle("支付失败");
//			message.setRemark("你购买的商品已支付失败，请联系商户");
//		}
		setMessageContent(status, message);
		message.setUrl("");
		message.setOrderName(orderPayment.getProductName());
		message.setOrderAmt(String.valueOf(orderPayment.getOrderAmt()));
		message.setBeginTime(orderPayment.getBeginTime());
		/*if (orderPayment.getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())) {
			message.setPayCode(PayCode.PAYCODE_ZFBSMZF.getDesc());
		}else if(orderPayment.getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_WXSMZF.getDesc());
		}else if(orderPayment.getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_WXGZHZF.getDesc());
		}else if(orderPayment.getPayCode().equals(PayCode.PAYCODE_QQZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_QQZF.getDesc());
		}*/
		//根据支付方式确定message的payCode
		fixupPushMessageBo(message);
		log.info("#########message["+JSONObject.fromObject(message)+"]########");
		
		return message;
		//merchantBusinessService.pushPaySuccess(message);
	}
	
	
	//根据状态和支付方式组装推送消息的bo
	protected void fixupPushMessageBo(PaySuccessMessage message){
		throw new PayException(ScanCodeErrorCode.PAY_500000.getCode(),ScanCodeErrorCode.PAY_500000.getDesc());
	}
	//修改支付流水信息
	protected void updateOrderPay(MerchantNotifyMessage bo,OrderPay orderPay){
		//给定默认失败状态
		orderPay.setErrorCode(PayStatus.PAYSTATUS_FAIL.getCode());
		orderPay.setPayStatus(PayStatus.PAYSTATUS_FAIL.getCode());
		//成功赋值
		if(bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())){
			orderPay.setErrorCode(PayStatus.PAYSTATUS_SUCCESS.getCode());
			orderPay.setPayStatus(PayStatus.PAYSTATUS_SUCCESS.getCode());
			//借贷标示
			orderPay.setAccountType(bo.getAccountType());
		}
		orderPay.setPayTradeNo(bo.getOrderNo());
		orderPay.setEndTime(new Date());
		orderPay.setErrorMsg(bo.getErrorMsg());
		orderPay.setUpdateTime(new Date());
		
		orderPayDAO.update(orderPay);
	}
	
	//修改支付订单信息
	protected void updateOrderPayment(MerchantNotifyMessage bo,OrderPayment orderPayment){
		//先给定默认失败状态
		orderPayment.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
		orderPayment.setResultCode(OrderResultCode.PAY_FAIL.getCode());
		//成功赋值
		if(bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())){
			orderPayment.setOrderStatus(OrderStatus.ORDER_SUCCESS.getCode());
			orderPayment.setResultCode(OrderResultCode.PAY_SUCCESS.getCode());
			//借贷标示
			orderPayment.setAccountType(bo.getAccountType());
		}
		orderPayment.setEndTime(new Date());
		orderPayment.setResultInfo(bo.getErrorMsg());
		orderPayment.setUpdateTime(new Date());
		orderPayment.setPayTradeNo(bo.getOrderNo());
		orderPaymentDAO.update(orderPayment);
	}
	
	//消息推送
	protected void pushMessage(MerchantNotifyMessage bo,OrderPayment orderPayment){
		if (bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
			try {
				PaySuccessMessage message = getPushMessage(bo.getStatus(),orderPayment);
				toPushMessage(message);
			} catch (Exception e) {
				log.error("推送消息失败....",e);
				e.printStackTrace();
			}
		}
		else{
			log.info("失败状态的订单暂不推送消息");
		}
	}
	
	/**
	 * 消息推送子类可以覆盖具体的推送方式
	 * @param message
	 */
	protected void toPushMessage(PaySuccessMessage message) {
		try {
			merchantBusinessService.pushPaySuccess(message);
		} catch (Exception e) {
			log.error("toPushMessage error",e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改回调日志
	 * @param userOrderNo
	 * @param notityJson
	 */
	protected void updateApiLog(String userOrderNo,String notityJson) {
		ApiLog apiLog = apiLogApiService.findByTradeNo(userOrderNo);
		if (null != apiLog) {
			log.info("########提现回调报文更新######");
			//记录日志
			ApiLogCondition apiLogCondition = new ApiLogCondition();
			apiLogCondition.setTradeNo(apiLog.getTradeNo());
			apiLogCondition.setNotifyParams(notityJson);
			apiLogCondition.setNotifyTime(new Date());
			apiLogCondition.setId(apiLog.getId());
			apiLogApiService.updateByTradeNo(apiLogCondition);			
		}
	}
	
	//设置回调地址
	private void setCallbackUrl(OrderPaymentCondition orderPaymentCondition){
		ParamsInfo info = payCallBackOperatorService.findParamsKey(ScanCodeConstants.SYSTEM, ParamsType.PARAMS_CALLBACK_INFO.getCode());
		JSONObject callbackJson = JSONObject.fromObject(info.getParamsValue());
		orderPaymentCondition.setNotifyUrl(callbackJson.getString("notifyUrl"));
		orderPaymentCondition.setReturnUrl(callbackJson.getString("returnUrl"));
		updateNotifyUrl(orderPaymentCondition,callbackJson);//针对回调地址不同的情况下，在这里修改回调地址
	}
	
	protected void updateNotifyUrl(OrderPaymentCondition orderPaymentCondition,JSONObject callbackJson){
		
	}
	
	/**
	 * 这是商品信息:默认是通过二维码查询的，如果不带二维码编号那么需要复写该方法
	 * @param orderPaymentCondition
	 * @param orderBo
	 */
	protected void setProduct(OrderPaymentCondition orderPaymentCondition,OrderBo orderBo){
		//获取二维码对应的产品信息
		QrcodeGoods qrcodeGoods = payCallBackOperatorService.findByQrCode(orderBo.getQrCode());
		if (null != qrcodeGoods) {
			orderPaymentCondition.setProductName(qrcodeGoods.getGoodsName());
			orderPaymentCondition.setProductDesc(qrcodeGoods.getGoodsDesc());
		}
	}

	private void setRate(OrderBo orderBo,OrderPaymentCondition orderPaymentCondition) {
		Map<String,BigDecimal> rateMap = new HashMap<>();
		//获取费率信息
		MerchantPayway merchantPayway = payCallBackOperatorService.findMerchantPaywayByMerchantPayCode(orderBo.getMerchantNo(),orderBo.getPayCode());
		if (null != merchantPayway) {
			//商户交易手续费
			if (!merchantPayway.getStatus().equals("1")) {
				unablePayWay(orderPaymentCondition);
				log.error("#######merchantNo["+orderBo.getMerchantNo()+"]支付方式已被禁用，不能支付#######");
			}else {
				rateMap.put("merchantRate_t0", merchantPayway.getT0Rate());
				rateMap.put("merchantRate_t1", merchantPayway.getT1Rate());
				rateMap.put("merchantRate_rate", merchantPayway.getRate());
			}
		}else {
			log.error("#######merchantNo["+orderBo.getMerchantNo()+"]未配置支付方式，不能支付#######");
			emptyPayWay(orderPaymentCondition);
		}
		
		//代理商交易手续费
		List<NodeRelation> dataNodeList = payCallBackOperatorService.getParentsByCurrentNode(orderBo.getMerchantNo(), "1", false, false);//findByCurrentNode(orderBo.getMerchantNo());
		for (int i = 0; i < dataNodeList.size(); i++) {
			NodeRelation dataNode = dataNodeList.get(i);
			AgentPayway agentPayway = payCallBackOperatorService.findAgentPayWayByAgentNoPayCode(dataNode.getCurrentNode(),orderBo.getPayCode());
			if (null != agentPayway) {
				if (!agentPayway.getStatus().equals("1")) {				
					log.error("#######agentNo["+orderBo.getAgentNo()+"]支付方式已被禁用，不能支付#######");
					unablePayWay(orderPaymentCondition);
				}else {
					rateMap.put("agent_"+dataNode.getParentNode()+"_t0", agentPayway.getT0Rate());
					rateMap.put("agent_"+dataNode.getParentNode()+"_t1", agentPayway.getT1Rate());
					rateMap.put("agent_"+dataNode.getParentNode()+"_rate", agentPayway.getRate());
				}
			}else {
				log.error("#######agentNo["+orderBo.getAgentNo()+"]未配置支付方式，不能支付#######");
				emptyPayWay(orderPaymentCondition);
			}
		}
		
		//渠道交易手续费
		ChannelPayway channelPayway = payCallBackOperatorService.findChannelPayWayByChannelNoPayCode(orderBo.getChannelNo(),orderBo.getPayCode());
		if (null != channelPayway) {
			if (!channelPayway.getStatus().equals("1")) {
				log.error("#######channelNo["+orderBo.getChannelNo()+"]支付方式已被禁用，不能支付#######");
				unablePayWay(orderPaymentCondition);
			}else {
				rateMap.put("channel_t0", channelPayway.getT0Rate());
				rateMap.put("channel_t1", channelPayway.getT1Rate());
				rateMap.put("channel_rate", channelPayway.getRate());
			}
		}else {
			log.error("#######channelNo["+orderBo.getChannelNo()+"]未配置支付方式，不能支付#######");
			emptyPayWay(orderPaymentCondition);
		}
		
		//成本交易费率
		HfepayPaywayCondition hfepayPaywayCondition = new HfepayPaywayCondition();
		hfepayPaywayCondition.setPayCode(orderBo.getPayCode());
		List<HfepayPayway> hfList = payCallBackOperatorService.findAll(hfepayPaywayCondition);
		if (Lists.isNotEmpty(hfList)) {			
			HfepayPayway hfepayPayway = hfList.get(0);
			rateMap.put("platform_t0", hfepayPayway.getT0Rate());
			rateMap.put("platform_t1", hfepayPayway.getT1Rate());
			rateMap.put("platform_rate", hfepayPayway.getRate());
		}
		orderPaymentCondition.setRateDetail(JSON.toJSONString(rateMap));
	}

	//支付方式被禁用
	private void unablePayWay(OrderPaymentCondition orderPaymentCondition){
		orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200001.getCode());
		orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200001.getDesc());
		orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
	}
	
	//支付方式为空
	private void emptyPayWay(OrderPaymentCondition orderPaymentCondition){
		orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200002.getCode());
		orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200002.getDesc());
		orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
	}
	
	//根据支付状态设置消息体内容
	private void setMessageContent(String status,PaySuccessMessage message){
		if (status.equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
			message.setTitle("收款成功");
			message.setRemark("您出售的商品已成功收款");
		}else {
			message.setTitle("支付失败");
			message.setRemark("你购买的商品已支付失败，请联系商户");
		}
	}
}
