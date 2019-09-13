package com.hfepay.pay.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.pay.service.PayCallBackService;
import com.hfepay.pay.service.PayOrderPayService;
import com.hfepay.pay.service.PayOrderPaymentService;
import com.hfepay.pay.service.PayWithdrawalsService;
import com.hfepay.scancode.api.entity.message.PaySuccessMessage;
import com.hfepay.scancode.api.entity.message.WithDrawsSuccessMessage;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.OrderResultCode;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.PayStatus;
import com.hfepay.scancode.commons.contants.RateType;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.contants.TradeType;
import com.hfepay.scancode.commons.contants.WithdrawsStatus;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.Withdrawals;

import net.sf.json.JSONObject;

@Service
public class PayCallBackServiceImpl implements PayCallBackService{
	
	public static final Logger log = LoggerFactory.getLogger(PayCallBackServiceImpl.class);
	@Autowired
	private PayOrderPayService orderPayService;
	@Autowired
	private PayOrderPaymentService payOrderPaymentService;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private PayWithdrawalsService payWithdrawalsService;
	@Autowired
	private PayCallBackOperatorService callBackOperatorService;
	
	@Override
	public void payCallBack(MerchantNotifyMessage bo) throws Exception {
		log.info("###########原交易["+bo+"]数据#############");
		OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
		//获取原交易订单信息
		OrderPayment orderPayment = payOrderPaymentService.findOrderPaymentByTradeNo(bo.getUserOrderNo());
		
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		OrderPay orderPay = orderPayService.findOrderPayByTradeNo(bo.getUserOrderNo());
		BeanUtils.copyProperties(orderPay, orderPayCondition);
		
		if(null != orderPayment){
			//商户订单
			BeanUtils.copyProperties(orderPayment, orderPaymentCondition);
			if (bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
				//防止重复回调
				MerchantCost merchantCost = callBackOperatorService.findMerchantCostByTradeNo(bo.getUserOrderNo());
				if (merchantCost != null) {
					log.info("###########重复的支付回调订单："+bo.getOrderNo()+"，不给与处理#############");
					return ;
				}
				
				orderPaymentCondition.setOrderStatus(OrderStatus.ORDER_SUCCESS.getCode());
				orderPaymentCondition.setResultCode(OrderResultCode.PAY_SUCCESS.getCode());
				orderPayCondition.setErrorCode(PayStatus.PAYSTATUS_SUCCESS.getCode());
				orderPayCondition.setPayStatus(PayStatus.PAYSTATUS_SUCCESS.getCode());
				//借贷标示
				orderPaymentCondition.setAccountType(bo.getAccountType());
				orderPayCondition.setAccountType(bo.getAccountType());
				
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
				orderPayCondition.setPayStatus(PayStatus.PAYSTATUS_FAIL.getCode());
			}
			orderPaymentCondition.setEndTime(new Date());
			orderPaymentCondition.setResultInfo(bo.getErrorMsg());
			orderPaymentCondition.setUpdateTime(new Date());
			orderPaymentCondition.setPayTradeNo(bo.getOrderNo());
			payOrderPaymentService.update(orderPaymentCondition);
			
			//支付订单
			orderPayCondition.setPayTradeNo(bo.getOrderNo());
			orderPayCondition.setEndTime(new Date());
			orderPayCondition.setErrorMsg(bo.getErrorMsg());
			orderPayCondition.setUpdateTime(new Date());
			orderPayService.update(orderPayCondition);
			
		}else {
			log.error("###########原交易["+bo.getOrderNo()+"]数据不存在#############");
		}
	}

	/**
	 * 创建商户成本记录
	 * @param bo
	 * @param orderPay
	 */
	private MerchantCostCondition createMerchantCost(OrderPay orderPay) {
		//获取费率信息
		MerchantPayway merchantPayway = callBackOperatorService.findMerchantPaywayByMerchantPayCode(orderPay.getMerchantNo(),orderPay.getPayCode());
		
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
		callBackOperatorService.insert(merchantCostCondition);
		
		return merchantCostCondition;
	}

	/**
	 * 交易成功消息推送
	 * @param bo
	 * @param orderPayMentList
	 * @throws Exception
	 */
	private void PushMessage(MerchantNotifyMessage bo,OrderPayment orderPayment) throws Exception {
		log.info("#########PushMessage["+orderPayment.getMerchantNo()+"]########");
		//推送支付信息给商户
		PaySuccessMessage message = new PaySuccessMessage();
		
		MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
		merchantCashierQrCondition.setQrCode(orderPayment.getQrCode());
		merchantCashierQrCondition.setIsCashier("2");
		merchantCashierQrCondition.setRecordStatus(ScanCodeConstants.Y);
		merchantCashierQrCondition.setMerchantNo(orderPayment.getMerchantNo());
		List<MerchantCashierQr> qrList = callBackOperatorService.findAll(merchantCashierQrCondition);
		if (null != qrList && qrList.size() > 0) {
			//消息默认推送给收银员
			MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
			merchantCashierCondition.setCashierNo(qrList.get(0).getCashierNo());
			MerchantCashier merchantCashier = callBackOperatorService.findByCondition(merchantCashierCondition);
			if (null != merchantCashier) {
				message.setTouser(merchantCashier.getOpenId());
			}
			log.info("#########PushMessage,推送消息给收银员["+merchantCashier.getCashierNo()+"]########");
		}else {
			//获取商户的OPENID
			ChannelAdmin channelAdmin = callBackOperatorService.findPushMsgAdminByMerchantNo(orderPayment.getMerchantNo(),IdentityType.Identity_Merchant.getCode());
			if (null != channelAdmin) {
				message.setTouser(channelAdmin.getOpenId());
			}
			log.info("#########PushMessage,推送消息给商户["+channelAdmin.getMerchantNo()+"]########");
		}
		
		message.setOrderNo(orderPayment.getTradeNo());
		message.setOrganNo(orderPayment.getAgentNo());
		if (bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
			message.setTitle("收款成功");
			message.setRemark("您出售的商品已成功收款");
		}else {
			message.setTitle("支付失败");
			message.setRemark("你购买的商品已支付失败，请联系商户");
		}
		
		message.setUrl("");
		message.setOrderName(orderPayment.getProductName());
		message.setOrderAmt(String.valueOf(orderPayment.getOrderAmt()));
		message.setBeginTime(orderPayment.getBeginTime());
		if (orderPayment.getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())) {
			message.setPayCode(PayCode.PAYCODE_ZFBSMZF.getDesc());
		}else if(orderPayment.getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_WXSMZF.getDesc());
		}else if(orderPayment.getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_WXGZHZF.getDesc());
		}else if(orderPayment.getPayCode().equals(PayCode.PAYCODE_QQZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_QQZF.getDesc());
		}
		
		log.info("#########message["+JSONObject.fromObject(message)+"]########");
		
		merchantBusinessService.pushPaySuccess(message);
	}

	@Override
	public void withdrawsCallBack(MerchantWithdrowNotifyMessage bo) throws Exception {
		log.info("###########原交易["+JSONObject.fromObject(bo)+"]数据#############");
		
		//获取原交易订单信息
		WithdrawalsCondition withdrawalsCondition = new WithdrawalsCondition();
		withdrawalsCondition.setTradeNo(bo.getUserOrderNo());
		List<Withdrawals> withdrawsList = payWithdrawalsService.findAll(withdrawalsCondition);
		
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		OrderPay orderPay = orderPayService.findOrderPayByTradeNo(bo.getUserOrderNo());
		if (null == orderPay) {
			log.info("###########原交易["+bo.getOrderNo()+"]数据不存在#############");
			return ;
		}
		
		BeanUtils.copyProperties(orderPay, orderPayCondition);
		
		if(null != withdrawsList && withdrawsList.size()>0){
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
				MerchantCost merchantCost = callBackOperatorService.findMerchantCostByTradeNo(bo.getUserOrderNo());
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
			withdrawalsCondition.setId(withdrawsList.get(0).getId());
			withdrawalsCondition.setEndTime(new Date());
			withdrawalsCondition.setPayTradeNo(bo.getOrderNo());
			payWithdrawalsService.update(withdrawalsCondition);
			
			//支付订单
			orderPayCondition.setMerchantCost(merchantCostAmt);
			orderPayCondition.setPayTradeNo(bo.getOrderNo());
			orderPayCondition.setEndTime(new Date());
			orderPayCondition.setUpdateTime(new Date());
			orderPayCondition.setOrderAmt(orderAmt);
			orderPayService.update(orderPayCondition);
			
		}else {
			log.error("###########原交易["+bo.getOrderNo()+"]数据不存在#############");
		}
	}
	
	/**
	 * 交易成功消息推送
	 * @param bo
	 * @param orderPayMentList
	 * @throws Exception
	 */
	private void withDrawsPushMessage(MerchantWithdrowNotifyMessage bo, OrderPay orderPay) throws Exception {
		log.info("#########PushMessage["+orderPay.getMerchantNo()+"]########");
		//推送支付信息给商户
		WithDrawsSuccessMessage message = new WithDrawsSuccessMessage();
		
		//获取商户的OPENID
		ChannelAdmin channelAdmin = callBackOperatorService.findPushMsgAdmin(orderPay.getMerchantNo(),true);
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
		if (orderPay.getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())) {
			message.setPayCode("支付宝钱包");
		}else if(orderPay.getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())){
			message.setPayCode("微信扫码付钱包");
		}else if(orderPay.getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode())){			
			message.setPayCode("微信公众号钱包");
		}else if(orderPay.getPayCode().equals(PayCode.PAYCODE_QQZF.getCode())){			
			message.setPayCode("QQ钱包");
		}
		
		log.info("#########message["+JSONObject.fromObject(message)+"]########");
		
		merchantBusinessService.pushWithdrawsSuccess(message);
	}

}
