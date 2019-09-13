package com.hfepay.timer.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
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
import com.hfepay.scancode.commons.dao.MerchantCashierDAO;
import com.hfepay.scancode.commons.dao.MerchantCashierQrDAO;
import com.hfepay.scancode.commons.dao.MerchantCostDAO;
import com.hfepay.scancode.commons.dao.MerchantInfoDAO;
import com.hfepay.scancode.commons.dao.MerchantPaywayDAO;
import com.hfepay.scancode.commons.dao.OrderPayDAO;
import com.hfepay.scancode.commons.dao.OrderPaymentDAO;
import com.hfepay.scancode.commons.dao.WithdrawalsDAO;
import com.hfepay.scancode.commons.dao.channel.ChannelAdminDAO;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.Withdrawals;
import com.hfepay.timer.service.CallBackService;

import net.sf.json.JSONObject;

@Service("callBackService")
public class CallBackServiceImpl implements CallBackService{
	
	public static final Logger log = LoggerFactory.getLogger(CallBackServiceImpl.class);
	@Autowired
	private OrderPayDAO orderPayDAO;
	@Autowired
	private OrderPaymentDAO orderPaymentDAO;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private MerchantCostDAO merchantCostDAO;
	@Autowired
	private MerchantPaywayDAO merchantPaywayDAO;
	@Autowired
	private MerchantCashierQrDAO merchantCashierQrDAO;
	@Autowired
	private MerchantCashierDAO merchantCashierDAO;
	@Autowired
	private ChannelAdminDAO channelAdminDAO;
	@Autowired
	private WithdrawalsDAO withdrawalsDAO;
	@Autowired
	private MerchantInfoDAO merchantInfoDAO;
	
	@Override
	public void payCallBack(MerchantNotifyMessage bo) throws Exception {
		log.info("###########原交易["+bo+"]数据#############");
		
		//获取原交易订单信息
		OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
		orderPaymentCondition.setTradeNo(bo.getUserOrderNo());
		CriteriaBuilder cbPayment = Cnd.builder(OrderPayment.class);
		cbPayment.andEQ("tradeNo", bo.getUserOrderNo());
		Criteria buildCriteriaPayment = cbPayment.buildCriteria();
		List<OrderPayment> orderPayMentList = orderPaymentDAO.findByCriteria(buildCriteriaPayment);
		
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		orderPayCondition.setTradeNo(bo.getUserOrderNo());
		
		CriteriaBuilder cbPay = Cnd.builder(OrderPay.class);
		cbPay.andEQ("tradeNo", bo.getUserOrderNo());
		Criteria buildCriteriaPay = cbPay.buildCriteria();
		List<OrderPay> orderPayList = orderPayDAO.findByCriteria(buildCriteriaPay);
		BeanUtils.copyProperties(orderPayList.get(0), orderPayCondition);
		
		if(null != orderPayMentList && orderPayMentList.size()>0){
			//商户订单
			BeanUtils.copyProperties(orderPayMentList.get(0), orderPaymentCondition);
			if (bo.getStatus().equals(PayStatus.PAYSTATUS_SUCCESS.getCode())) {
				//防止重复回调
				MerchantCostCondition costCondition = new MerchantCostCondition();
				costCondition.setTradeNo(bo.getUserOrderNo());
				CriteriaBuilder cbCost = Cnd.builder(MerchantCost.class);
				cbCost.andEQ("tradeNo", bo.getUserOrderNo());
				Criteria buildCriteriaCost = cbCost.buildCriteria();
				List<MerchantCost> list = merchantCostDAO.findByCriteria(buildCriteriaCost);
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
			
			OrderPayment orderPayment = new OrderPayment();
			BeanUtils.copyProperties(orderPaymentCondition, orderPayment);
			orderPaymentDAO.update(orderPayment);
			
			//支付订单
			orderPayCondition.setPayTradeNo(bo.getOrderNo());
			orderPayCondition.setEndTime(new Date());
			orderPayCondition.setErrorMsg(bo.getErrorMsg());
			orderPayCondition.setUpdateTime(new Date());
			
			OrderPay orderPay = new OrderPay();
			BeanUtils.copyProperties(orderPayCondition, orderPay);
			orderPayDAO.update(orderPay);
			
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
		CriteriaBuilder cbPayway = Cnd.builder(MerchantPayway.class);
		cbPayway.andEQ("payCode", orderPay.getPayCode());
		cbPayway.andEQ("merchantNo", orderPay.getMerchantNo());
		Criteria buildCriteriaPayway = cbPayway.buildCriteria();
		List<MerchantPayway> merchantList = merchantPaywayDAO.findByCriteria(buildCriteriaPayway);
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
		MerchantCost merchantCost = new MerchantCost();
		BeanUtils.copyProperties(merchantCostCondition, merchantCost);
		merchantCostDAO.insert(merchantCost);
		
		return merchantCostCondition;
	}
	
	@Override
	public void withdrawsCallBack(MerchantWithdrowNotifyMessage bo) throws Exception {
		log.info("###########原交易["+JSONObject.fromObject(bo)+"]数据#############");
		
		//获取原交易订单信息
		WithdrawalsCondition withdrawalsCondition = new WithdrawalsCondition();
		withdrawalsCondition.setTradeNo(bo.getUserOrderNo());
		
		CriteriaBuilder cb = Cnd.builder(MerchantCost.class);
		cb.andEQ("tradeNo", bo.getUserOrderNo());
		Criteria buildCriteria = cb.buildCriteria();
		List<Withdrawals> withdrawsList = withdrawalsDAO.findByCriteria(buildCriteria);
		
		OrderPayCondition orderPayCondition = new OrderPayCondition();
		orderPayCondition.setTradeNo(bo.getUserOrderNo());
		CriteriaBuilder cbPay = Cnd.builder(OrderPay.class);
		cbPay.andEQ("tradeNo", bo.getUserOrderNo());
		Criteria buildCriteriaPay = cbPay.buildCriteria();
		List<OrderPay> orderPayList = orderPayDAO.findByCriteria(buildCriteriaPay);
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
				CriteriaBuilder cbCost = Cnd.builder(MerchantCost.class);
				cbCost.andEQ("tradeNo", bo.getUserOrderNo());
				Criteria buildCriteriaCost = cbCost.buildCriteria();
				List<MerchantCost> list = merchantCostDAO.findByCriteria(buildCriteriaCost);
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
			
			Withdrawals withdrawals = new Withdrawals();
			BeanUtils.copyProperties(withdrawalsCondition, withdrawals);
			withdrawalsDAO.update(withdrawals);
			
			
			//支付订单
			orderPayCondition.setMerchantCost(merchantCost);
			orderPayCondition.setPayTradeNo(bo.getOrderNo());
			orderPayCondition.setEndTime(new Date());
			orderPayCondition.setUpdateTime(new Date());
			orderPayCondition.setOrderAmt(orderAmt);
			
			OrderPay updateOrderPay = new OrderPay();
			BeanUtils.copyProperties(orderPayCondition, updateOrderPay);
			orderPayDAO.update(updateOrderPay);
			
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
	private void PushMessage(MerchantNotifyMessage bo, List<OrderPayment> orderPayMentList) throws Exception {
		log.info("#########PushMessage["+orderPayMentList.get(0).getMerchantNo()+"]########");
		//推送支付信息给商户
		PaySuccessMessage message = new PaySuccessMessage();
		
		MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
		merchantCashierQrCondition.setQrCode(orderPayMentList.get(0).getQrCode());
		merchantCashierQrCondition.setIsCashier("2");
		merchantCashierQrCondition.setRecordStatus(ScanCodeConstants.Y);
		merchantCashierQrCondition.setMerchantNo(orderPayMentList.get(0).getMerchantNo());
		
		CriteriaBuilder cbCashierQr = Cnd.builder(MerchantCost.class);
		cbCashierQr.andEQ("qrCode", orderPayMentList.get(0).getQrCode());
		cbCashierQr.andEQ("isCashier", "2");
		cbCashierQr.andEQ("recordStatus", ScanCodeConstants.Y);
		cbCashierQr.andEQ("merchantNo", orderPayMentList.get(0).getMerchantNo());
		Criteria buildCriteriaCashierQr = cbCashierQr.buildCriteria();
		List<MerchantCashierQr> qrList = merchantCashierQrDAO.findByCriteria(buildCriteriaCashierQr);
		if (null != qrList && qrList.size() > 0) {
			//消息默认推送给收银员
			MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
			merchantCashierCondition.setCashierNo(qrList.get(0).getCashierNo());
			CriteriaBuilder cbCashier = Cnd.builder(MerchantCashier.class);
			cbCashier.andEQ("cashierNo", qrList.get(0).getCashierNo());
			Criteria buildCriteriaCashier = cbCashier.buildCriteria();
			MerchantCashier merchantCashier = merchantCashierDAO.findOneByCriteria(buildCriteriaCashier);
			if (null != merchantCashier) {
				message.setTouser(merchantCashier.getOpenId());
			}
			log.info("#########PushMessage,推送消息给收银员["+merchantCashier.getCashierNo()+"]########");
		}else {
			//获取商户的OPENID
			ChannelAdmin channelAdmin = channelAdminDAO.findByMerchantNoByIdentityGetOpenId(orderPayMentList.get(0).getMerchantNo(),IdentityType.Identity_Merchant.getCode());
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
		ChannelAdmin channelAdmin = channelAdminDAO.findPushMsgAdmin(orderPayList.get(0).getMerchantNo(),true);
		if (null != channelAdmin) {
			message.setTouser(channelAdmin.getOpenId());
		}
		log.info("#########PushMessage,推送消息给商户["+channelAdmin.getMerchantNo()+"]########");
		
		CriteriaBuilder cb = Cnd.builder(MerchantInfo.class);
		cb.andEQ("merchantNo", channelAdmin.getMerchantNo());
		Criteria buildCriteria = cb.buildCriteria();
		MerchantInfo merchantInfo = merchantInfoDAO.findOneByCriteria(buildCriteria);
		
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
}
