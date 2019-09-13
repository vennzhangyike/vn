package com.hfepay.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.pay.service.PayOrderPayService;
import com.hfepay.pay.service.PayOrderPaymentService;
import com.hfepay.pay.service.PayScanPayService;
import com.hfepay.pay.utils.BatchUtils;
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
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.contants.BusinessType;
import com.hfepay.scancode.commons.contants.CreditPayStatus;
import com.hfepay.scancode.commons.contants.OrderResultCode;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.PayStatus;
import com.hfepay.scancode.commons.contants.PayType;
import com.hfepay.scancode.commons.contants.RateType;
import com.hfepay.scancode.commons.contants.RefundStatus;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.contants.TradeType;
import com.hfepay.scancode.commons.entity.AgentPayway;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelPayway;
import com.hfepay.scancode.commons.entity.HfepayPayway;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.NodeRelation;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.ParamsInfo;

import net.sf.json.JSONObject;

/**
 * 平台网关处理
 * @author Administrator
 *
 */
@Service
public class PayScanPayServiceImpl implements PayScanPayService {

	public static final Logger log = LoggerFactory.getLogger(PayScanPayServiceImpl.class);
	@Autowired
	private PayOrderPayService payOrderPayService;
	@Autowired
	private PayOrderPaymentService payOrderPaymentService;
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private ApiLogService apiLogApiService;
	/**
	 * 条形码支付
	 * @Title: scanCodePay
	 * @Description: TODO
	 * @param orderBo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> scanCodePay(OrderBo orderBo) {
		log.info("#######orderBo["+orderBo.toString()+"]######");
		Map<String, String> returnMap = new HashMap<>();
		Map<String, String> wechatOffreturnMap = new HashMap<>();
		
		MerchantInfo merchantInfo = payCallBackOperatorService.findByMerchantNo(orderBo.getMerchantNo());
		//保存支付订单
		OrderPayment orderPayment = saveOrder(orderBo,merchantInfo);
		
		//支付参数未配置，直接失败
		if (orderPayment.getOrderStatus().equals(OrderStatus.ORDER_FAIL.getCode())) {
			returnMap.put("returnCode", ScanCodeErrorCode.VALIDAT_100003.getCode());
			returnMap.put("returnMsg", ScanCodeErrorCode.VALIDAT_100003.getDesc());
			log.info("#######qrcode["+orderBo.getQrCode()+"]请求参数不合法######");
		}else {
			//调用支付API
			try {
				MerchantPayInfoVo vo = new MerchantPayInfoVo();
				vo.setMerchantNo(merchantInfo.getPlatformMerchantNo());		//商户支付编号
				vo.setUserOrderNo(orderPayment.getTradeNo());	//平台方订单号
				vo.setPayCode(orderBo.getPayCode());			//支付通道编码
				vo.setOrderAmt(orderBo.getPrice().toString());	//交易金额
				vo.setOrderTitle(orderPayment.getProductName());
				vo.setOrderDesc(orderPayment.getProductDesc());
				
				//是否支持信用卡:2禁用信用卡
				if (CreditPayStatus.CREDIT_PAY_STATUS_CLOSE.getCode().equals(merchantInfo.getCreditPayStatus())) {					
					vo.setLimitPay("1");
				}
				ParamsInfo info = payCallBackOperatorService.findParamsKey(ScanCodeConstants.SYSTEM, ParamsType.PARAMS_CALLBACK_INFO.getCode());
				JSONObject callbackJson = JSONObject.fromObject(info.getParamsValue());
				vo.setNotifyUrl(callbackJson.getString("scanPayNotifyUrl"));
				vo.setReturnUrl(callbackJson.getString("returnUrl"));
				//vo.setNotifyUrl(callbackConfig.getScanPayNotifyUrl());//回调地址异步
				//vo.setReturnUrl(callbackConfig.getReturnUrl());//订单完成之后跳转页面
				vo.setScene(orderBo.getScene());
				vo.setAuthCode(orderBo.getAuthCode());
				
				wechatOffreturnMap = merchantBusinessService.merchantScanPay(vo);//分为同步异步，同步返回支付成功或者失败，异步返回支付中等待回调
				Map<String,String> resMap = JSON.parseObject(JSON.toJSONString(wechatOffreturnMap.get("head")), Map.class);//responseHandle(wechatOffreturnMap);
				log.info("response msg map is:"+wechatOffreturnMap);
				if("200002".equals(resMap.get("respCode"))){//交易状态未知，异步回调，存在报文体
					log.info("in 200002 case wait for callback");
					returnMap.put("returnCode", resMap.get("respCode"));
					returnMap.put("returnMsg", "支付处理中，等待银行回调");
					returnMap.put("userOrderNo",vo.getUserOrderNo());
				}
				else{
					log.info("==========同步返回=====================");
					//修改日志
					updateApiLog(vo.getUserOrderNo(), wechatOffreturnMap.toString());
					//支付返回值
					if (ScanCodeErrorCode.SYSTEM_000000.getCode().equals(resMap.get("respCode"))) {//返回000000一定有报文体body
						//处理订单信息：和回调类似的处理
						//String body = (String) responseMap.get("body");
						JSONObject object = JSONObject.fromObject(wechatOffreturnMap);
						String body = object.getString("body");
						JSONObject jsObject = JSONObject.fromObject(body);
						MerchantNotifyMessage merchantNotifyMessage = JSON.parseObject(body, MerchantNotifyMessage.class);
						
						merchantNotifyMessage.setUserReqNo(resMap.get("userReqNo").toString());
						merchantNotifyMessage.setStatus(jsObject.getString("orderStatus"));//订单状态
						try {
							//校验参数
							Map<String,String> returnInfo = checkCallBackOrder(merchantNotifyMessage);
							if (null != returnInfo) {
								log.error("#########支付系统错误:"+JSON.toJSONString(returnInfo)+"########");
								return returnInfo;
							}
							//回调逻辑
							payCallBack(merchantNotifyMessage);
							returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_000000.getCode());
							returnMap.put("userOrderNo",vo.getUserOrderNo());
						} catch (Exception e) {
							log.error("#########handle success situation error:"+e);
						}
					}else {//非成功状态直接返回，如果涉及到异步的情况也无需再做业务处理，在回调总处理，支付失败
						MerchantNotifyMessage merchantNotifyMessage = new MerchantNotifyMessage();
						merchantNotifyMessage.setUserOrderNo(vo.getUserOrderNo());
						merchantNotifyMessage.setErrorMsg(resMap.get("respDesc"));
						merchantNotifyMessage.setErrorCode(resMap.get("respCode"));
						merchantNotifyMessage.setOrderNo("");//不存在
						merchantNotifyMessage.setStatus(OrderStatus.ORDER_FAIL.getCode());
						handleFailed(merchantNotifyMessage);
						returnMap.put("returnCode", resMap.get("respCode"));
						returnMap.put("returnMsg", resMap.get("respDesc"));
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error("#########pay error:"+e);
				returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_999999.getCode());
				returnMap.put("returnMsg", ScanCodeErrorCode.SYSTEM_999999.getDesc());
				return returnMap;
			}
		}
		returnMap.put("tradeNo", orderPayment.getTradeNo());
		log.info("########wechatOfficialMap["+JSON.toJSONString(returnMap)+"]#######");
		
		return returnMap;
	}
	
	/**
	 * @Title: payCallBack
	 * @Description: 消息推送
	 * @param bo
	 * @throws Exception
	 * @see com.hfepay.scancode.service.payway.ScanPayService#payCallBack(com.hfepay.scancode.bo.MerchantNotifyMessage)
	 */
	@Override
	public void payCallBack(MerchantNotifyMessage bo) throws Exception {
		log.info("###########原交易["+bo+"]数据#############");
		
		//获取原交易订单信息
		OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
		orderPaymentCondition.setTradeNo(bo.getUserOrderNo());
		OrderPayment orderPayment = payOrderPaymentService.findOrderPaymentByTradeNo(bo.getUserOrderNo());
		if(orderPayment==null){
			throw new RuntimeException("OrderPayment "+bo.getUserOrderNo()+" is null");
		}
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		OrderPay orderPay = payOrderPayService.findOrderPayByTradeNo(bo.getUserOrderNo());
		if(orderPay==null){
			throw new RuntimeException("OrderPay "+bo.getUserOrderNo()+" is null");
		}
		BeanUtils.copyProperties(orderPay, orderPayCondition);
		
//		if(null != orderPayMentList && orderPayMentList.size()>0){
			//商户订单
			BeanUtils.copyProperties(orderPayment, orderPaymentCondition);
			if (bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
				//防止重复回调
				MerchantCost merchantCost = payCallBackOperatorService.findMerchantCostByTradeNo(bo.getUserOrderNo());
				if (merchantCost != null) {
					log.info("###########重复的支付回调订单："+bo.getOrderNo()+"，不给与处理#############");
					return ;
				}
				
				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_SUCCESS.getCode());
				orderPaymentCondition.setResultCode(OrderResultCode.PAY_SUCCESS.getCode());
				orderPayCondition.setErrorCode(PayStatus.PAYSTATUS_SUCCESS.getCode());
				//借贷标示
				orderPaymentCondition.setAccountType(bo.getAccountType());
				orderPayCondition.setAccountType(bo.getAccountType());
				
				//OrderPay orderPay = orderPayList.get(0);
				
				//创建商户成本记录
				MerchantCostCondition merchantCostCondition = this.createMerchantCost(orderPay);
				orderPaymentCondition.setMerchantCost(merchantCostCondition.getMerchantCost());
				orderPayCondition.setMerchantCost(merchantCostCondition.getMerchantCost());
				
				//支付成功消息推送
				try {
					PushMessage(bo, orderPayment);
				} catch (Exception e) {
					log.error("推送消息失败....",e);
					e.printStackTrace();
				}
			}else {
				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
				orderPaymentCondition.setResultCode(OrderResultCode.PAY_FAIL.getCode());
				orderPayCondition.setErrorCode(PayStatus.PAYSTATUS_FAIL.getCode());
			}
			orderPaymentCondition.setEndTime(new Date());
			orderPaymentCondition.setResultInfo(bo.getErrorMsg());
			orderPaymentCondition.setUpdateTime(new Date());
			orderPaymentCondition.setPayTradeNo(bo.getOrderNo());
			payOrderPaymentService.update(orderPaymentCondition);
			
			//支付订单
			orderPayCondition.setPayStatus(bo.getStatus());
			orderPayCondition.setPayTradeNo(bo.getOrderNo());
			orderPayCondition.setEndTime(new Date());
			orderPayCondition.setErrorMsg(bo.getErrorMsg());
			orderPayCondition.setUpdateTime(new Date());
			payOrderPayService.update(orderPayCondition);
			
			/*}else {
			log.error("###########原交易["+bo.getOrderNo()+"]数据不存在#############");
		}*/
	}
	
	/**
	 * 创建商户成本记录
	 * @param bo
	 * @param orderPay
	 */
	private MerchantCostCondition createMerchantCost(OrderPay orderPay) {
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
		if (orderPay.getTradeType().equals(TradeType.TRADE_TYPE_PAY.getCode())) {
			merchantCostCondition.setRateType(RateType.RATE_TYPE_T1.getCode());
			merchantCostCondition.setMerchantRate(merchantPayway.getT1Rate());
			//交易成本 = 交易金额 *T1费率 
			merchantCostCondition.setMerchantCost(orderPay.getOrderAmt().multiply(merchantPayway.getT1Rate()));
		}else {	//提现
//			merchantCostCondition.setRateType(RateType.RATE_TYPE_T0.getCode());
			//提现成本 = 交易金额 *(T0费率+T1费率 ) + 固定提现手续费
			//orderPay.getOrderAmt().multiply(merchantPayway.getT0Rate().add(merchantPayway.getT1Rate())).add(merchantPayway.getRate())
			merchantCostCondition.setRateType(RateType.RATE_TYPE_T0.getCode());
			merchantCostCondition.setMerchantCost(orderPay.getMerchantCost());
			merchantCostCondition.setMerchantRate(merchantPayway.getT0Rate().add(merchantPayway.getT1Rate()));
		}
		merchantCostCondition.setFixedAmount(merchantPayway.getRate());
		merchantCostCondition.setCreateTime(new Date());
		payCallBackOperatorService.insert(merchantCostCondition);
		
		return merchantCostCondition;
	}
	
	/**
	 * 交易成功消息推送
	 * @param bo
	 * @param orderPayMentList
	 * @throws Exception
	 */
	private void PushMessage(MerchantNotifyMessage bo, OrderPayment payment) throws Exception {
		//OrderPayment payment = orderPayMentList.get(0);
		log.info("#########PushMessage["+payment.getMerchantNo()+"]########");
		//推送支付信息给商户
		PaySuccessMessage message = new PaySuccessMessage();
		String identityNo = "";
		ChannelAdmin channelAdmin = null;
		if(Strings.isNotEmpty(payment.getCashierNo())){
			identityNo = payment.getCashierNo();
			channelAdmin = payCallBackOperatorService.findPushMsgAdmin(identityNo,false);
		}
		else{
			identityNo = payment.getMerchantNo();
			channelAdmin = payCallBackOperatorService.findPushMsgAdmin(identityNo,true);
		}
		if (null != channelAdmin) {
			message.setTouser(channelAdmin.getOpenId());
		}else{
			throw new RuntimeException("openId is null,push error....");
		}
		
		message.setOrderNo(payment.getTradeNo());
		message.setOrganNo(payment.getAgentNo());
		if (bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
			message.setTitle("收款成功");
			message.setRemark("您出售的商品已成功收款");
		}else {
			message.setTitle("支付失败");
			message.setRemark("你购买的商品已支付失败，请联系商户");
		}
		
		message.setUrl("");
		message.setOrderName(payment.getProductName());
		message.setOrderAmt(String.valueOf(payment.getOrderAmt()));
		message.setBeginTime(payment.getBeginTime());
		if (payment.getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())) {
			message.setPayCode(PayCode.PAYCODE_ZFBSMZF.getDesc());
		}else if(payment.getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_WXSMZF.getDesc());
		}else if(payment.getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_WXGZHZF.getDesc());
		}else if(payment.getPayCode().equals(PayCode.PAYCODE_QQZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_QQZF.getDesc());
		}
		
		log.info("#########message["+JSONObject.fromObject(message)+"]########");
		
		merchantBusinessService.pushPaySuccess(message);
	}
	
	private void handleFailed(MerchantNotifyMessage bo){
		//获取原交易订单信息
		OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
		OrderPayment orderPayment = payOrderPaymentService.findOrderPaymentByTradeNo(bo.getUserOrderNo());
		
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		OrderPay orderPay = payOrderPayService.findOrderPayByTradeNo(bo.getUserOrderNo());
		BeanUtils.copyProperties(orderPay, orderPayCondition);
		if(null != orderPayment){
			//商户订单
			BeanUtils.copyProperties(orderPayment, orderPaymentCondition);
			orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
			orderPaymentCondition.setResultCode(OrderResultCode.PAY_FAIL.getCode());
			orderPaymentCondition.setEndTime(new Date());
			orderPaymentCondition.setResultInfo(bo.getErrorMsg());
			orderPaymentCondition.setUpdateTime(new Date());
			orderPaymentCondition.setPayTradeNo(bo.getOrderNo());//失败的时候不存在该字段，此处保留是为了和异步回调保持一致
			payOrderPaymentService.update(orderPaymentCondition);
			//支付订单
			
			orderPayCondition.setErrorCode(bo.getErrorCode());
			orderPayCondition.setPayStatus(PayStatus.PAYSTATUS_FAIL.getCode());
			orderPayCondition.setPayTradeNo(bo.getOrderNo());//失败的时候不存在该字段，此处保留是为了和异步回调保持一致
			orderPayCondition.setEndTime(new Date());
			orderPayCondition.setErrorMsg(bo.getErrorMsg());
			orderPayCondition.setUpdateTime(new Date());
			payOrderPayService.update(orderPayCondition);
		}else {
			log.error("###########原交易 tradeNo=["+bo.getUserOrderNo()+"]数据不存在#############");
		}
	}
	
	
	/**
	 * 修改回调日志
	 * @param userOrderNo
	 * @param notityJson
	 */
	private void updateApiLog(String userOrderNo,String notityJson) {
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
	/**
	 * 支付回调数据校验
	 * @param bo
	 * @return Map<String,String>
	 */
	private Map<String,String> checkCallBackOrder(MerchantNotifyMessage bo) {
		if (Strings.isEmpty(bo.getOrderNo())) {
			return returnInfo("100002","订单编号不能为空");
		}
		if (Strings.isEmpty(bo.getUserOrderNo())) {
			return returnInfo("100002","商户订单号不能为空");
		}
		if (Strings.isEmpty(bo.getStatus())) {
			return returnInfo("100002","支付状态不能为空");
		}
		return null;
	}
	
	private Map<String,String> returnInfo(String returnCode,String returnMsg) {
		Map<String,String> returnInfo = new HashMap<String,String>();
		returnInfo.put("returnCode", returnCode);
		returnInfo.put("returnMsg", returnMsg);
		return returnInfo;
	}
	/**
	 * 保存订单数据
	 * @param orderBo
	 * @return OrderPayment
	 */
	private OrderPayment saveOrder(OrderBo orderBo,MerchantInfo merchantInfo){
		//保存费率信息
		Map<String, BigDecimal> rateMap = new HashMap<String, BigDecimal>();
		OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
		//根据二维码查收银员
		/*MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
		merchantCashierQrCondition.setQrCode(orderBo.getQrCode());
		merchantCashierQrCondition.setIsCashier("2");
		merchantCashierQrCondition.setRecordStatus(ScanCodeConstants.Y);*/
		//List<MerchantCashierQr> qrList = merchantCashierQrService.findAll(merchantCashierQrCondition);
		//查看该支付二维码是否有收银员
		/*if (null != qrList && qrList.size() > 0) {			
			//获取收银员信息
			MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
			merchantCashierCondition.setMerchantNo(orderBo.getMerchantNo());
			merchantCashierCondition.setCashierNo(qrList.get(0).getCashierNo());
			MerchantCashier merchantCashier = merchantCashierService.findByCondition(merchantCashierCondition);
			if (null != merchantCashier) {
				orderPaymentCondition.setCashierNo(merchantCashier.getCashierNo());
				orderPaymentCondition.setStoreNo(merchantCashier.getStoreNo());
			}
		}else {*/
			orderPaymentCondition.setCashierNo(orderBo.getCashier());
			orderPaymentCondition.setStoreNo(orderBo.getStoreNo());
			/*MerchantQrcode merchantQrcode = findByMerchantQrCode(orderBo.getQrCode());
			PlatformQrcode platformQrcode = null;*/
			//String storeId = "";
			/*if (null == merchantQrcode) {
				platformQrcode = findByQrCode(orderBo.getQrCode());
				if (null != platformQrcode) {
					storeId = platformQrcode.getStoreId();
				}
			}else {
				storeId = merchantQrcode.getStoreId();
			}*/
			//orderPaymentCondition.setStoreNo(storeId);
		//}
		
		
		orderPaymentCondition.setId(Strings.getUUID());
		orderPaymentCondition.setBusinessType(BusinessType.BUSINESSTYPE_0.getCode());
		orderPaymentCondition.setChannelNo(orderBo.getChannelNo());
		orderPaymentCondition.setAgentNo(orderBo.getAgentNo());
		orderPaymentCondition.setMerchantNo(orderBo.getMerchantNo());
		orderPaymentCondition.setQrCode(orderBo.getQrCode());
		orderPaymentCondition.setPayCode(orderBo.getPayCode());
		orderPaymentCondition.setOrderAmt(orderBo.getPrice());
		orderPaymentCondition.setTradeAmt(orderBo.getTradeAmt());
		orderPaymentCondition.setDiscountAmt(orderBo.getDiscountAmt());
		orderPaymentCondition.setDiscountStrategy(orderBo.getDiscountStrategy());
		
		//获取二维码对应的产品信息
		/*QrcodeGoods qrcodeGoods = qrcodeGoodsService.findByQrCode(orderBo.getQrCode());
		if (null != qrcodeGoods) {
			orderPaymentCondition.setProductName(qrcodeGoods.getGoodsName());
			orderPaymentCondition.setProductDesc(qrcodeGoods.getGoodsDesc());
		}else {*/
			orderPaymentCondition.setProductName(merchantInfo.getMerchantName());
			orderPaymentCondition.setProductDesc("");
		//}
		
		orderPaymentCondition.setBuyerId("");	//支付的账号id
		orderPaymentCondition.setBuyerAccount("");	//支付账号
		orderPaymentCondition.setTotalAmount(new BigDecimal(0.00));	//支付额度（用户支付的款+积分优惠）
		orderPaymentCondition.setBuyerPayAmount(orderBo.getPrice());	//用户支付的款
		orderPaymentCondition.setPointAmount(new BigDecimal(0.00));		//积分优惠
		
		//获取费率信息
		MerchantPayway merchantPayway = payCallBackOperatorService.findMerchantPaywayByMerchantPayCode(orderBo.getMerchantNo(),orderBo.getPayCode());
		if (null != merchantPayway) {
			//商户交易手续费
			if (!merchantPayway.getStatus().equals("1")) {
				orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200001.getCode());
				orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200001.getDesc());
				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
				log.error("#######merchantNo["+orderBo.getMerchantNo()+"]支付方式已被禁用，不能支付#######");
			}
		}else {
			orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200002.getCode());
			orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200002.getDesc());
			orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
			log.error("#######merchantNo["+orderBo.getMerchantNo()+"]未配置支付方式，不能支付#######");
		}
		
		//代理商交易手续费
		List<NodeRelation> dataNodeList = payCallBackOperatorService.getParentsByCurrentNode(orderBo.getMerchantNo(), "1", false, false);//findByCurrentNode(orderBo.getMerchantNo());
		for (int i = 0; i < dataNodeList.size(); i++) {
			NodeRelation dataNode = dataNodeList.get(i);
			AgentPayway agentPayway = payCallBackOperatorService.findAgentPayWayByAgentNoPayCode(dataNode.getCurrentNode(),orderBo.getPayCode());
			if (null != agentPayway) {
				if (!agentPayway.getStatus().equals("1")) {				
					orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200001.getCode());
					orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200001.getDesc());
					orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
					log.error("#######agentNo["+orderBo.getAgentNo()+"]支付方式已被禁用，不能支付#######");
				}
				rateMap.put("agent_"+dataNode.getParentNode()+"_t0", agentPayway.getT0Rate());
				rateMap.put("agent_"+dataNode.getParentNode()+"_t1", agentPayway.getT1Rate());
				rateMap.put("agent_"+dataNode.getParentNode()+"_rate", agentPayway.getRate());
			}else {
				log.error("#######agentNo["+orderBo.getAgentNo()+"]未配置支付方式，不能支付#######");
				orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200002.getCode());
				orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200002.getDesc());
				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
			}
		}
		
		//渠道交易手续费
		ChannelPayway channelPayway = payCallBackOperatorService.findChannelPayWayByChannelNoPayCode(orderBo.getChannelNo(),orderBo.getPayCode());
		if (null != channelPayway) {
			if (!channelPayway.getStatus().equals("1")) {
				log.error("#######channelNo["+orderBo.getChannelNo()+"]支付方式已被禁用，不能支付#######");
				orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200001.getCode());
				orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200001.getDesc());
				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
			}
		}else {
			orderPaymentCondition.setResultCode(ScanCodeErrorCode.TRADE_200002.getCode());
			orderPaymentCondition.setResultInfo(ScanCodeErrorCode.TRADE_200002.getDesc());
			orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_FAIL.getCode());
			log.error("#######channelNo["+orderBo.getChannelNo()+"]未配置支付方式，不能支付#######");
		}
		
		//成本交易费率
		HfepayPaywayCondition hfepayPaywayCondition = new HfepayPaywayCondition();
		hfepayPaywayCondition.setPayCode(orderBo.getPayCode());
		List<HfepayPayway> hfList = payCallBackOperatorService.findAll(hfepayPaywayCondition);
		
		rateMap.put("merchantRate_t0", merchantPayway.getT0Rate());
		rateMap.put("merchantRate_t1", merchantPayway.getT1Rate());
		rateMap.put("merchantRate_rate", merchantPayway.getRate());
		
		HfepayPayway hfepayPayway = hfList.get(0);
		rateMap.put("platform_t0", hfepayPayway.getT0Rate());
		rateMap.put("platform_t1", hfepayPayway.getT1Rate());
		rateMap.put("platform_rate", hfepayPayway.getRate());
		
		rateMap.put("channel_t0", channelPayway.getT0Rate());
		rateMap.put("channel_t1", channelPayway.getT1Rate());
		rateMap.put("channel_rate", channelPayway.getRate());
		
		orderPaymentCondition.setRateDetail(JSON.toJSONString(rateMap));
		
		orderPaymentCondition.setFeeType("0");	//计费方式 0：无需计费（默认）、1：事前计费、2：事后计费
		orderPaymentCondition.setFeeStatus("0");//计费状态 0：未计费（默认）、1：已计费
		orderPaymentCondition.setPaymentInfo("");
		orderPaymentCondition.setPaymentType("03");	//支付方式 00: 帐务支付、01: 网关支付、02: 快捷支付、03: 扫码支付
		ParamsInfo info = payCallBackOperatorService.findParamsKey(ScanCodeConstants.SYSTEM, ParamsType.PARAMS_CALLBACK_INFO.getCode());
		JSONObject callbackJson = JSONObject.fromObject(info.getParamsValue());
		orderPaymentCondition.setNotifyUrl(callbackJson.getString("scanPayNotifyUrl"));
		orderPaymentCondition.setReturnUrl(callbackJson.getString("returnUrl"));
		
		//orderPaymentCondition.setNotifyUrl(callbackConfig.getScanPayNotifyUrl());
		//orderPaymentCondition.setReturnUrl(callbackConfig.getReturnUrl());
		orderPaymentCondition.setBeginTime(new Date());
		orderPaymentCondition.setBatchId(BatchUtils.createBatchNo());
		
		orderPaymentCondition.setRefundStatus(RefundStatus.REFUND_N.getCode());	//退款状态（Y 已退款，N 未退款）
		orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_TREATE.getCode());	//订单状态 00：等待付款、01：交易成功、03：交易失败
		
		//保存业务订单
		OrderPayment orderPayment = payOrderPaymentService.saveOrderPayment(orderPaymentCondition);
		log.info("save orderPayMent is "+orderPayment);
		saveOrderPay(orderPaymentCondition, orderPayment);
		
		return orderPayment;
	}

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
//		orderPayCondition.setPayType(orderPaymentCondition.getPayCode());
		/*if (orderPaymentCondition.getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode()) || orderPaymentCondition.getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())) {			
			orderPayCondition.setPayType(PayType.PAYTYPE_WXGZH.getCode());
		}else if(orderPaymentCondition.getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())){
			orderPayCondition.setPayType(PayType.PAYTYPE_ZFB.getCode());
		}*/
		
		//支付失败
		if (orderPaymentCondition.getOrderStatus().equals(OrderStatus.ORDER_FAIL.getCode())) {
			orderPayCondition.setErrorCode(orderPaymentCondition.getResultCode());
			orderPayCondition.setErrorMsg(orderPaymentCondition.getResultCode());
		}
		orderPayCondition.setPayStatus(PayStatus.PAYSTATUS_CHANNEL_TREATE.getCode());	//支付状态 00：支付成功、01：支付失败、02：渠道处理中、03：记账处理中
		payOrderPayService.createPayOrder(orderPayment,orderPayCondition);
	}
}
