package com.hfepay.scancode.service.payway.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.api.entity.message.PaySuccessMessage;
import com.hfepay.scancode.api.entity.message.WithDrawsSuccessMessage;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.MerchantBankChangeMessage;
import com.hfepay.scancode.commons.bo.MerchantJoinMessage;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.MerchantRateMessage;
import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;
import com.hfepay.scancode.commons.condition.MerchantBankcardChangeCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.MerchantBankcardStatus;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.OrderResultCode;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.PayStatus;
import com.hfepay.scancode.commons.contants.RateType;
import com.hfepay.scancode.commons.contants.TradeType;
import com.hfepay.scancode.commons.contants.WithdrawsStatus;
import com.hfepay.scancode.commons.dao.MerchantInfoDAO;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.commons.entity.MerchantBankcardChange;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.Withdrawals;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.MerchantBankcardChangeService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantCashierQrService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantCostService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;
import com.hfepay.scancode.service.order.OrderPayService;
import com.hfepay.scancode.service.order.OrderPaymentService;
import com.hfepay.scancode.service.order.WithdrawalsService;
import com.hfepay.scancode.service.payway.CallBackService;

import net.sf.json.JSONObject;

@Service
public class CallBackServiceImpl implements CallBackService{
	
	public static final Logger log = LoggerFactory.getLogger(CallBackServiceImpl.class);
	
	@Autowired
	private OrderPayService orderPayService;
	
	@Autowired
	private OrderPaymentService orderPaymentService;
	
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	
	@Autowired
	private ChannelAdminService channelAdminService;
	
	@Autowired
	private MerchantCashierService merchantCashierService;
	
	@Autowired
	
	private MerchantCostService merchantCostService;
	
	@Autowired
	private MerchantPaywayService merchantPaywayService;
	
	@Autowired
	private MerchantCashierQrService merchantCashierQrService;
	
	@Autowired
	private WithdrawalsService withdrawalsService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private MerchantBankcardService merchantBankcardService;
	
	@Autowired
	private MerchantBankcardChangeService merchantBankcardChangeService;
	
	@Autowired
    private MerchantInfoDAO merchantInfoDAO;
	
	private final static String auditStatus_1 ="1";
	private final static String auditStatus_2 ="2";
	
	@Override
	public void payCallBack(MerchantNotifyMessage bo) throws Exception {
		log.info("###########原交易["+bo+"]数据#############");
		
		//获取原交易订单信息
		OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
		orderPaymentCondition.setTradeNo(bo.getUserOrderNo());
		List<OrderPayment> orderPayMentList = orderPaymentService.findAll(orderPaymentCondition);
		
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		orderPayCondition.setTradeNo(bo.getUserOrderNo());
		List<OrderPay> orderPayList = orderPayService.findAll(orderPayCondition);
		BeanUtils.copyProperties(orderPayList.get(0), orderPayCondition);
		
		if(null != orderPayMentList && orderPayMentList.size()>0){
			//商户订单
			BeanUtils.copyProperties(orderPayMentList.get(0), orderPaymentCondition);
			if (bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
				//防止重复回调
				MerchantCostCondition costCondition = new MerchantCostCondition();
				costCondition.setTradeNo(bo.getUserOrderNo());
				List<MerchantCost> list = merchantCostService.findAll(costCondition);
				if (list.size() > 0) {
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
				
				OrderPay orderPay = orderPayList.get(0);
				
				//创建商户成本记录
				MerchantCostCondition merchantCostCondition = this.createMerchantCost(orderPay);
				orderPaymentCondition.setMerchantCost(merchantCostCondition.getMerchantCost());
				orderPayCondition.setMerchantCost(merchantCostCondition.getMerchantCost());
				
				//支付成功消息推送
				try {
					PushMessage(bo, orderPayMentList);
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
			orderPaymentService.update(orderPaymentCondition);
			
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
		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		merchantPaywayCondition.setPayCode(orderPay.getPayCode());
		merchantPaywayCondition.setMerchantNo(orderPay.getMerchantNo());
		List<MerchantPayway> merchantList = merchantPaywayService.findAll(merchantPaywayCondition);
		MerchantPayway merchantPayway = merchantList.get(0);
		
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
		merchantCostService.insert(merchantCostCondition);
		
		return merchantCostCondition;
	}

	/**
	 * 交易成功消息推送
	 * @param bo
	 * @param orderPayMentList
	 * @throws Exception
	 */
	private void PushMessage(MerchantNotifyMessage bo, List<OrderPayment> orderPayMentList) throws Exception {
		log.info("#########PushMessage["+orderPayMentList.get(0).getMerchantNo()+"]########");
		//推送支付信息给商户
		PaySuccessMessage message = new PaySuccessMessage();
		
		MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
		merchantCashierQrCondition.setQrCode(orderPayMentList.get(0).getQrCode());
		merchantCashierQrCondition.setIsCashier("2");
		merchantCashierQrCondition.setRecordStatus(ScanCodeConstants.Y);
		merchantCashierQrCondition.setMerchantNo(orderPayMentList.get(0).getMerchantNo());
		List<MerchantCashierQr> qrList = merchantCashierQrService.findAll(merchantCashierQrCondition);
		if (null != qrList && qrList.size() > 0) {
			//消息默认推送给收银员
			MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
			merchantCashierCondition.setCashierNo(qrList.get(0).getCashierNo());
			MerchantCashier merchantCashier = merchantCashierService.findByCondition(merchantCashierCondition);
			if (null != merchantCashier) {
				message.setTouser(merchantCashier.getOpenId());
			}
			log.info("#########PushMessage,推送消息给收银员["+merchantCashier.getCashierNo()+"]########");
		}else {
			//获取商户的OPENID
			ChannelAdmin channelAdmin = channelAdminService.findPushMsgAdmin(orderPayMentList.get(0).getMerchantNo(),true);
			if (null != channelAdmin) {
				message.setTouser(channelAdmin.getOpenId());
			}
			log.info("#########PushMessage,推送消息给商户["+channelAdmin.getMerchantNo()+"]########");
		}
		
		message.setOrderNo(orderPayMentList.get(0).getTradeNo());
		message.setOrganNo(orderPayMentList.get(0).getAgentNo());
		if (bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
			message.setTitle("收款成功");
			message.setRemark("您出售的商品已成功收款");
		}else {
			message.setTitle("支付失败");
			message.setRemark("你购买的商品已支付失败，请联系商户");
		}
		
		message.setUrl("");
		message.setOrderName(orderPayMentList.get(0).getProductName());
		message.setOrderAmt(String.valueOf(orderPayMentList.get(0).getOrderAmt()));
		message.setBeginTime(orderPayMentList.get(0).getBeginTime());
		if (orderPayMentList.get(0).getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())) {
			message.setPayCode(PayCode.PAYCODE_ZFBSMZF.getDesc());
		}else if(orderPayMentList.get(0).getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_WXSMZF.getDesc());
		}else if(orderPayMentList.get(0).getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode())){
			message.setPayCode(PayCode.PAYCODE_WXGZHZF.getDesc());
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
		List<Withdrawals> withdrawsList = withdrawalsService.findAll(withdrawalsCondition);
		
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		orderPayCondition.setTradeNo(bo.getUserOrderNo());
		List<OrderPay> orderPayList = orderPayService.findAll(orderPayCondition);
		if (null == orderPayList || orderPayList.size() <= 0) {
			log.info("###########原交易["+bo.getOrderNo()+"]数据不存在#############");
			return ;
		}
		
		BeanUtils.copyProperties(orderPayList.get(0), orderPayCondition);
		
		if(null != withdrawsList && withdrawsList.size()>0){
			BigDecimal merchantCost = new BigDecimal(0);
			BigDecimal orderAmt = new BigDecimal(0);
			OrderPay orderPay  = orderPayList.get(0);
			
			//商户提现成本
			log.info("########DrawFee["+bo.getDrawFee()+"],TradeFee["+bo.getTradeFee()+"]#######");
			if (bo.getDrawAmount().compareTo(new BigDecimal(0)) > 0) {
				//提现成本 = 交易金额 * (交易费率+垫资费率) + 提现手续费
				merchantCost = bo.getDrawFee().add(bo.getTradeFee());
				
				orderAmt = bo.getDrawAmount().add(bo.getDrawFee()).add(bo.getTradeFee());
			}
			orderPay.setOrderAmt(orderAmt);
			orderPay.setMerchantCost(merchantCost);
			
			//处理成功
			if (bo.getStatus().equals(WithdrawsStatus.WD_STATUS_SUCCESS.getCode())) {
				//防止重复回调
				MerchantCostCondition costCondition = new MerchantCostCondition();
				costCondition.setTradeNo(bo.getUserOrderNo());
				List<MerchantCost> list = merchantCostService.findAll(costCondition);
				if (list.size() > 0) {
					log.info("###########重复的提现回调订单："+bo.getOrderNo()+"，不给与处理#############");
					return ;
				}
				
				withdrawalsCondition.setResultCode(WithdrawsStatus.WD_STATUS_SUCCESS.getCode());
				withdrawalsCondition.setResultInfo(WithdrawsStatus.WD_STATUS_SUCCESS.getDesc());
				orderPayCondition.setPayStatus(PayStatus.PAYSTATUS_SUCCESS.getCode());
				orderPayCondition.setErrorCode(bo.getStatus());
				orderPayCondition.setErrorMsg("");
				orderPayCondition.setOrderAmt(bo.getDrawAmount());
				
				this.createMerchantCost(orderPayList.get(0));
				
				//提现成功消息推送
				withDrawsPushMessage(bo, orderPayList);
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
			withdrawalsCondition.setMerchantCost(merchantCost);
			withdrawalsCondition.setId(withdrawsList.get(0).getId());
			withdrawalsCondition.setEndTime(new Date());
			withdrawalsCondition.setPayTradeNo(bo.getOrderNo());
			withdrawalsService.update(withdrawalsCondition);
			
			//支付订单
			orderPayCondition.setMerchantCost(merchantCost);
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
	private void withDrawsPushMessage(MerchantWithdrowNotifyMessage bo, List<OrderPay> orderPayList) throws Exception {
		log.info("#########PushMessage["+orderPayList.get(0).getMerchantNo()+"]########");
		//推送支付信息给商户
		WithDrawsSuccessMessage message = new WithDrawsSuccessMessage();
		
		//获取商户的OPENID
		ChannelAdmin channelAdmin = channelAdminService.findPushMsgAdmin(orderPayList.get(0).getMerchantNo(),true);
		if (null != channelAdmin) {
			message.setTouser(channelAdmin.getOpenId());
		}
		log.info("#########PushMessage,推送消息给商户["+channelAdmin.getMerchantNo()+"]########");
		
		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(channelAdmin.getMerchantNo());
		
		if (bo.getStatus().equals(WithdrawsStatus.WD_STATUS_SUCCESS.getCode())) {
			message.setTitle("提现成功");
			message.setRemark("您申请的提现已处理成功");
		}
		message.setOrganNo(channelAdmin.getAgentNo());
		message.setUrl("");
		message.setMerchantName(merchantInfo.getMerchantName());
		message.setOrderAmt(String.valueOf(bo.getDrawAmount()));
		message.setBeginTime(orderPayList.get(0).getBeginTime());
		if (orderPayList.get(0).getPayCode().equals(PayCode.PAYCODE_ZFBSMZF.getCode())) {
			message.setPayCode("支付宝钱包");
		}else if(orderPayList.get(0).getPayCode().equals(PayCode.PAYCODE_WXSMZF.getCode())){
			message.setPayCode("微信扫码付钱包");
		}else if(orderPayList.get(0).getPayCode().equals(PayCode.PAYCODE_WXGZHZF.getCode())){			
			message.setPayCode("微信公众号钱包");
		}
		
		log.info("#########message["+JSONObject.fromObject(message)+"]########");
		
		merchantBusinessService.pushWithdrawsSuccess(message);
	}

	/**
	 * 银行卡变更回调
	 * @param MerchantBankChangeMessage
	 * @return void
	 * @Date 2016-11-05
	 */
	@Override
	public void bankChangeCallBack(MerchantBankChangeMessage bo) throws Exception {
		log.info("#########bankChangeCallBack["+JSONObject.fromObject(bo)+"]########");
		//查询原交易数据
		MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
		merchantInfoCondition.setPlatformMerchantNo(bo.getMerchantNo());
		List<MerchantInfo> merList = merchantInfoService.findAll(merchantInfoCondition);
		if (null == merList || merList.size() <= 0) {
			log.info("#########bankChangeCallBack商户不存在########");
			return ;
		}
		MerchantInfo merchantInfo = merList.get(0);
		
		MerchantBankcardChangeCondition merchantBankcardChangeCondition = new MerchantBankcardChangeCondition();
		merchantBankcardChangeCondition.setMerchantNo(merchantInfo.getMerchantNo());
		merchantBankcardChangeCondition.setBankCard(bo.getBankCard());
		merchantBankcardChangeCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_5.getCode());
		List<MerchantBankcardChange> list = merchantBankcardChangeService.findAll(merchantBankcardChangeCondition);
		
		MerchantBankcard merchantBankcard = merchantBankcardService.findByMerchantNo(merchantInfo.getMerchantNo());
		if (null == merchantBankcard) {
			log.info("#########bankChangeCallBack商户银行卡不存在########");
			return ;
		}
		
		if (null != list && list.size() > 0) {
			log.info("#########银行卡变更回调["+merchantInfo.getMerchantNo()+"]########");
			MerchantBankcardChange merchantBankcardChange = list.get(0);
			
			if (bo.getStatus().equals(ScanCodeConstants.STATUS_ACTIVE)) {
				log.info("#########银行卡变更回调["+merchantInfo.getMerchantNo()+"]渠道审核通过########");
				merchantBankcardChange.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_1.getCode());
				
				//更新原交易卡信息
				MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
				merchantBankcardCondition.setBankCard(bo.getBankCard());
				merchantBankcardCondition.setMobile(bo.getMobile());
				merchantBankcardCondition.setBankName(bo.getBankName());
				merchantBankcardCondition.setBankCode(bo.getBankCode());
				merchantBankcardCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_1.getCode());
				merchantBankcardCondition.setId(merchantBankcard.getId());
				merchantBankcardService.update(merchantBankcardCondition);
			}else {
				log.info("#########银行卡变更回调["+merchantInfo.getMerchantNo()+"]渠道审核拒绝########");
				//审核不通过
				merchantBankcardChange.setRemark(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_4.getDesc());
				merchantBankcardChange.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_4.getCode());
				
				//更新原交易卡状态
				MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
				merchantBankcardCondition.setStatus(MerchantBankcardStatus.MERCHANTBANKCARD_STATUS_1.getCode());
				merchantBankcardCondition.setId(merchantBankcard.getId());
				merchantBankcardService.update(merchantBankcardCondition);
			}
			//更新新卡审核状态
			BeanUtils.copyProperties(merchantBankcardChange, merchantBankcardChangeCondition);
			merchantBankcardChangeService.update(merchantBankcardChangeCondition);
		}else {
			log.info("#########银行卡变更回调["+merchantInfo.getMerchantNo()+"]未找到申请变更卡信息########");
		}
	}
	
	/**
	 * 银行卡变更回调
	 * @param MerchantBankChangeMessage
	 * @return void
	 * @Date 2016-11-05
	 */
	@Override
	public void joinCallBack(MerchantJoinMessage bo,List<MerchantRateMessage> merchantRateList) throws Exception {
		log.info("#########joinCallBack["+JSONObject.fromObject(bo)+"]########");
		
		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(bo.getMerchantNo());
		if (null == merchantInfo) {
			log.info("#########joinCallBack商户不存在########");
			return ;
		}
		if (bo.getAuditStatus().equals(ScanCodeConstants.INTERFACE_SUCCESS_STATUS)) {
			merchantInfo.setStatus(MerchantStatus.MERCHANT_STATUS_3.getCode());//平台审核通过后改为第三方审核中，等待对方进行商户入驻回调
		}else {
			if (bo.getAuditStatus().equals(auditStatus_1)||bo.getAuditStatus().equals(auditStatus_2)) {
				//上游审核中，我们不处理
				log.info("#########"+bo.getMerchantNo()+"上游审核中，我们不处理########");
				return;
			}else {				
				merchantInfo.setStatus(MerchantStatus.MERCHANT_STATUS_7.getCode());
			}
		}
		long result = merchantInfoDAO.update(merchantInfo);
		if (result < 1) {
			log.error("#########joinCallBack审核失败########");
		}
		MerchantPaywayCondition pay = null;
		for (int i = 0; i < merchantRateList.size(); i++) {
			MerchantRateMessage merchantRateMessage = merchantRateList.get(i);
			String payCode = merchantRateMessage.getPayCode();
			String status =merchantRateMessage.getStatus();
			pay = new MerchantPaywayCondition();
			pay.setMerchantNo(merchantInfo.getMerchantNo());
			pay.setPayCode(payCode);
			pay.setUpdateTime(new Date());
			pay.setOperator(merchantInfo.getOperator());
			if(ScanCodeConstants.INTERFACE_SUCCESS_STATUS.equals(status)){
				pay.setAcceptStatus(ScanCodeConstants.STATUS_ACTIVE);
			}else{
				//pay.setAcceptStatus(Constants.STATUS_NOT_USE);
			}
			merchantPaywayService.updateByCriteria(pay);
		}
	}

}
