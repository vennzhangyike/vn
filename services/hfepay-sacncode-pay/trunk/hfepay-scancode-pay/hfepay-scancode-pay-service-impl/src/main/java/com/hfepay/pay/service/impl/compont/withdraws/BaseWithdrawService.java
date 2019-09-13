package com.hfepay.pay.service.impl.compont.withdraws;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.pay.service.PayWithdrawalsService;
import com.hfepay.pay.service.exception.WithdrawException;
import com.hfepay.pay.service.impl.compont.BasePayQueryBiz;
import com.hfepay.pay.utils.BatchUtils;
import com.hfepay.pay.utils.OrderIDUtils;
import com.hfepay.scancode.api.entity.message.WithDrawsSuccessMessage;
import com.hfepay.scancode.api.entity.vo.MerchantWithdrawsInfoVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.BusinessType;
import com.hfepay.scancode.commons.contants.IndexType;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.contants.PayStatus;
import com.hfepay.scancode.commons.contants.RateType;
import com.hfepay.scancode.commons.contants.RefundStatus;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.contants.TradeType;
import com.hfepay.scancode.commons.contants.WithdrawsStatus;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.commons.entity.Withdrawals;

import net.sf.json.JSONObject;

@Component
public class BaseWithdrawService {
	public static final Logger log = LoggerFactory.getLogger(BaseWithdrawService.class);
	
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private BasePayQueryBiz basePayQueryBiz;
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;
	@Autowired
	private PayWithdrawalsService payWithdrawalsService;
	@Autowired
	private OrderIDUtils orderUtils;
	@Autowired
	private PayCallBackOperatorService callBackOperatorService;
	
	protected Map<String, String> withdraws(OrderBo orderBo) {
		log.info("#######orderBo["+orderBo.toString()+"]#######");
		Map<String, String> returnMap = new HashMap<>();
		Map<String, String> withdrawsReturnMap = new HashMap<>();
		WithdrawalsCondition withdrawalsCondition = new WithdrawalsCondition();
		
		//保存订单信息
		Withdrawals withdrawals = this.saveWithdraws(orderBo);
		
		//构造提现
		MerchantWithdrawsInfoVo withdrawsInfo = new MerchantWithdrawsInfoVo();
		try {
			withdrawsInfo.setUserOrderNo(withdrawals.getTradeNo());
			withdrawsInfo.setPayCode(withdrawals.getPayCode());
			withdrawsInfo.setReturnURL(withdrawals.getTemp4());//回调地址saveWithdraws中和此处是同一个，借助于temp4传递减少查询次数
			withdrawals.setTemp4(null);//重置避免更新该字段
			withdrawsInfo.setMerchantNo(orderBo.getPlatformMerchantNo());
			withdrawsInfo.setRemark("");
			withdrawsReturnMap = merchantBusinessService.merchantWithdraws(withdrawsInfo);
			
			//通讯成功
			if (withdrawsReturnMap.get("respCode").equals(ScanCodeErrorCode.SYSTEM_000000.getCode())) {
				log.info("#######respCode:"+withdrawsReturnMap.get("respCode")+"#######");
				log.info("#######status:"+withdrawsReturnMap.get("status")+"#######");
				//受理成功
				if (withdrawsReturnMap.get("status").equals("0")) {
					returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_000000.getCode());
					returnMap.put("returnMsg", ScanCodeErrorCode.SYSTEM_000000.getDesc());
					
					withdrawalsCondition.setPayTradeNo(withdrawsReturnMap.get("orderNo"));
					
					withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_CHANNEL_TREATE.getCode());
					withdrawalsCondition.setResultInfo(WithdrawsStatus.WD_STATUS_CHANNEL_TREATE.getDesc());
				}else {
					withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_FAIL.getCode());
					withdrawalsCondition.setResultInfo(WithdrawsStatus.WD_STATUS_FAIL.getDesc());
					returnMap.put("returnCode", ScanCodeErrorCode.TRADE_400001.getCode());
					returnMap.put("returnMsg", ScanCodeErrorCode.TRADE_400001.getDesc());
				}
			}else {
				log.info("#######respCode:"+withdrawsReturnMap.get("respCode")+"#######");
				withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_FAIL.getCode());
				withdrawalsCondition.setResultInfo(withdrawsReturnMap.get("respDesc"));
				returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_999998.getCode());
				returnMap.put("returnMsg", withdrawsReturnMap.get("respDesc"));
			}
			withdrawalsCondition.setId(withdrawals.getId());
			payWithdrawalsService.update(withdrawalsCondition);
		} catch (Exception e) {
			log.error("#########withdraws error:"+e);
			returnMap.put("returnCode", ScanCodeErrorCode.SYSTEM_999999.getCode());
			returnMap.put("returnMsg", ScanCodeErrorCode.SYSTEM_999999.getDesc());
			
			withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_FAIL.getCode());
			withdrawalsCondition.setResultInfo(ScanCodeErrorCode.SYSTEM_999999.getDesc());
			withdrawalsCondition.setId(withdrawals.getId());
			payWithdrawalsService.update(withdrawalsCondition);
			return returnMap;
		}
		
		return returnMap;
	}

	protected Withdrawals saveWithdraws(OrderBo orderBo) {
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
		Withdrawals withdrawals = payWithdrawalsService.createWithdraws(withdrawalsCondition);
		
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
		
		//设置Temp4为回调地址减少查询次数
		withdrawals.setTemp4(orderPayCondition.getNotifyUrl());
		
		//orderPayCondition.setNotifyUrl(callbackConfig.getWithdrawsNotifyUrl());
		orderPayCondition.setBatchId(bathId);
		orderPayCondition.setRefundStatus(RefundStatus.REFUND_N.getCode());
		orderPayCondition.setPayStatus(PayStatus.PAYSTATUS_ACCOUNTS_WAIT.getCode());
		orderPayCondition.setOrderAmt(new BigDecimal(0));
		/*if (orderBo.getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode()) || orderBo.getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())) {			
			orderPayCondition.setPayType(PayType.PAYTYPE_WXGZH.getCode());
		}else if(orderBo.getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())){
			orderPayCondition.setPayType(PayType.PAYTYPE_ZFB.getCode());
		}*/
		setPayType(orderPayCondition);
		
		orderPayCondition.setPayNo(orderUtils.getOrderID(Dates.format(Dates.DF.yyyyMMdd,new Date()), IndexType.Index32.value(), "ZF"));
		orderPayCondition.setBeginTime(new Date());
		
		basePayQueryBiz.createWithdrawsOrder(orderPayCondition);
		
		return withdrawals;
	}
	
	//paytype
	protected void setPayType(OrderPayCondition orderPayCondition){
		throw new WithdrawException(ScanCodeErrorCode.WITHDRAW_600001.getCode(), ScanCodeErrorCode.WITHDRAW_600001.getDesc());
	}
	
	
	protected void withdrawsCallBack(MerchantWithdrowNotifyMessage bo) throws Exception {
		log.info("###########原交易["+JSONObject.fromObject(bo)+"]数据#############");
		
		//获取原交易订单信息
		WithdrawalsCondition withdrawalsCondition = new WithdrawalsCondition();
		withdrawalsCondition.setTradeNo(bo.getUserOrderNo());
		//List<Withdrawals> withdrawsList = payWithdrawalsService.findAll(withdrawalsCondition);
		
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		OrderPay orderPay = basePayQueryBiz.findOrderPayByTradeNo(bo.getUserOrderNo());
		if (null == orderPay) {
			log.info("###########原交易["+bo.getOrderNo()+"]数据不存在#############");
			return ;
		}
		
		BeanUtils.copyProperties(orderPay, orderPayCondition);
		
		//if(null != withdrawsList && withdrawsList.size()>0){
			BigDecimal merchantCostAmt = new BigDecimal(0);
			BigDecimal orderAmt = new BigDecimal(0);
			
			//商户提现成本
			log.info("########DrawFee["+bo.getDrawFee()+"],TradeFee["+bo.getTradeFee()+"]#######");
			//处理成功
			if (bo.getStatus().equals(WithdrawsStatus.WD_STATUS_SUCCESS.getCode())) {
				if (bo.getDrawAmount().compareTo(new BigDecimal(0)) > 0) {
					//提现成本 = 交易金额 * (交易费率+垫资费率) + 提现手续费
					merchantCostAmt = bo.getDrawFee().add(bo.getTradeFee());
					orderAmt = bo.getDrawAmount().add(bo.getDrawFee()).add(bo.getTradeFee());
				}
				
				//防止重复回调
				MerchantCost merchantCost = basePayQueryBiz.findMerchantCostByTradeNo(bo.getUserOrderNo());
				if (merchantCost != null) {
					log.info("###########重复的提现回调订单："+bo.getOrderNo()+"，不给与处理#############");
					return ;
				}
				
				withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_SUCCESS.getCode());
				withdrawalsCondition.setResultInfo(WithdrawsStatus.WD_STATUS_SUCCESS.getDesc());
				orderPayCondition.setPayStatus(PayStatus.PAYSTATUS_SUCCESS.getCode());
				orderPayCondition.setErrorCode(bo.getStatus());
				orderPayCondition.setErrorMsg("");
				orderPayCondition.setOrderAmt(bo.getDrawAmount());
				
				this.createMerchantCost(orderPay);
				
				//提现成功消息推送
				withDrawsPushMessage(bo, orderPay);
			}else {
				orderPayCondition.setErrorCode(bo.getStatus());
				orderPayCondition.setErrorMsg(WithdrawsStatus.WD_STATUS_FAIL.getDesc());
				
				orderPayCondition.setPayStatus(PayStatus.PAYSTATUS_FAIL.getCode());
				
				withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_FAIL.getCode());
				withdrawalsCondition.setResultInfo(WithdrawsStatus.WD_STATUS_FAIL.getDesc());
			}
			
			withdrawalsCondition.setOrderAmt(orderAmt);
			withdrawalsCondition.setDrawFee(bo.getDrawFee());
			withdrawalsCondition.setTradeFee(bo.getTradeFee());
			withdrawalsCondition.setSettleTime(bo.getSettleDate());
			withdrawalsCondition.setMerchantCost(merchantCostAmt);
			withdrawalsCondition.setId(bo.getWithDrawId());
			withdrawalsCondition.setEndTime(new Date());
			withdrawalsCondition.setPayTradeNo(bo.getOrderNo());
			payWithdrawalsService.update(withdrawalsCondition);
			
			//支付订单
			orderPayCondition.setMerchantCost(merchantCostAmt);
			orderPayCondition.setPayTradeNo(bo.getOrderNo());
			orderPayCondition.setEndTime(new Date());
			orderPayCondition.setUpdateTime(new Date());
			orderPayCondition.setOrderAmt(orderAmt);
			basePayQueryBiz.update(orderPayCondition);
			
//		}else {
//			log.error("###########原交易["+bo.getOrderNo()+"]数据不存在#############");
//		}
	}
	
	/**
	 * 交易成功消息推送
	 * @param bo
	 * @param orderPayMentList
	 * @throws Exception
	 */
	protected void withDrawsPushMessage(MerchantWithdrowNotifyMessage bo, OrderPay orderPay) throws Exception {
		log.info("#########PushMessage["+orderPay.getMerchantNo()+"]########");
		//推送支付信息给商户
		WithDrawsSuccessMessage message = new WithDrawsSuccessMessage();
		BigDecimal dramt = bo.getDrawAmount()==null?new BigDecimal("0"):bo.getDrawAmount();
		if(dramt.compareTo(new BigDecimal("0"))<=0){
			log.info("##########################金额为0不推送消息##################");
			return;
		}
		//获取商户的OPENID
		ChannelAdmin channelAdmin = callBackOperatorService.findByIdentityNo(orderPay.getMerchantNo());
		if (null != channelAdmin) {
			message.setTouser(channelAdmin.getOpenId());
		}
		log.info("#########PushMessage,推送消息给商户["+channelAdmin.getMerchantNo()+"]########");
		
		MerchantInfo merchantInfo = callBackOperatorService.findByMerchantNo(channelAdmin.getMerchantNo());
		
		if (bo.getStatus().equals(WithdrawsStatus.WD_STATUS_SUCCESS.getCode())) {
			message.setTitle("提现成功");
			message.setRemark("您申请的提现已处理成功");
		}
		message.setOrganNo(channelAdmin.getAgentNo());
		message.setUrl("");
		message.setMerchantName(merchantInfo.getMerchantName());
		message.setOrderAmt(String.valueOf(bo.getDrawAmount()));
		message.setBeginTime(orderPay.getBeginTime());
//		if (orderPay.getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())) {
//			message.setPayCode("支付宝钱包");
//		}else if(orderPay.getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())){
//			message.setPayCode("微信扫码付钱包");
//		}else if(orderPay.getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode())){			
//			message.setPayCode("微信公众号钱包");
//		}else if(orderPay.getPayCode().equals(PayCode.PAYCODE_QQZF.getCode())){			
//			message.setPayCode("QQ钱包");
//		}
		setMessagePayCode(message);
		
		log.info("#########message["+JSONObject.fromObject(message)+"]########");
		
		merchantBusinessService.pushWithdrawsSuccess(message);
	}
	
	protected void setMessagePayCode(WithDrawsSuccessMessage message){
		 throw new WithdrawException(ScanCodeErrorCode.WITHDRAW_600002.getCode(),ScanCodeErrorCode.WITHDRAW_600002.getDesc());
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
//		if (orderPay.getTradeType().equals(TradeType.TRADE_TYPE_PAY.getCode())) {
//			merchantCostCondition.setRateType(RateType.RATE_TYPE_T1.getCode());
//			merchantCostCondition.setMerchantRate(merchantPayway.getT1Rate());
//			//交易成本 = 交易金额 *T1费率 
//			merchantCostCondition.setMerchantCost(orderPay.getOrderAmt().multiply(merchantPayway.getT1Rate()));
//		}else {	//提现
//			merchantCostCondition.setRateType(RateType.RATE_TYPE_T0.getCode());
			//提现成本 = 交易金额 *(T0费率+T1费率 ) + 固定提现手续费
			//orderPay.getOrderAmt().multiply(merchantPayway.getT0Rate().add(merchantPayway.getT1Rate())).add(merchantPayway.getRate())
			merchantCostCondition.setRateType(RateType.RATE_TYPE_T0.getCode());
			merchantCostCondition.setMerchantCost(orderPay.getMerchantCost());
			merchantCostCondition.setMerchantRate(merchantPayway.getT0Rate().add(merchantPayway.getT1Rate()));
//		}
		merchantCostCondition.setFixedAmount(merchantPayway.getRate());
		merchantCostCondition.setCreateTime(new Date());
		basePayQueryBiz.insert(merchantCostCondition);
		
		return merchantCostCondition;
	}

}
