package com.hfepay.pay.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.criteria.Cnd;
import com.hfepay.commons.criteria.Criteria;
import com.hfepay.commons.criteria.CriteriaBuilder;
import com.hfepay.pay.service.PayOrderRefundService;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.pay.service.PayOrderPaymentService;
import com.hfepay.scancode.api.entity.vo.MerchantRefundVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.OrderQueryBo;
import com.hfepay.scancode.commons.bo.OrderRefundBo;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.dao.OrderPayDAO;
import com.hfepay.scancode.commons.dao.OrderPaymentDAO;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.exception.ApiErrorCode;
@Service("payOrderRefundService")
public class PayOrderRefundServiceImpl implements PayOrderRefundService {
	public static final Logger log = LoggerFactory.getLogger(PayOrderRefundServiceImpl.class);
	@Autowired
	private OrderPayDAO orderPayDAO;
	@Autowired
	private OrderPaymentDAO orderPaymentDAO;
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private PayOrderPaymentService payOrderPaymentService;

	@Override
	public Map<String, String> refund(OrderRefundBo bo) {
		log.info("##########进入退款+OrderRefundBo:" + bo);
		Map<String, String> returnMap = new HashMap<>();
		returnMap.put("cashierNo", bo.getCashierNo());
		returnMap.put("tradeNo", bo.getTradeNo());
		returnMap.put("userOrderNo", bo.getUserOrderNo());
		OrderQueryBo qyerybo = new OrderQueryBo();
		BeanUtils.copyProperties(bo, qyerybo);
		OrderPayment orderPaymentOld = payOrderPaymentService.findQueryOrder(qyerybo);
		if (null == orderPaymentOld) {
			returnMap.put("returnCode", ApiErrorCode.QUERY_200003.getCode());
			returnMap.put("returnMsg", ApiErrorCode.QUERY_200003.getDesc());
			return returnMap;
		}
		bo.setTradeNo(orderPaymentOld.getTradeNo());
		returnMap.put("userOrderNo", orderPaymentOld.getExtraTradeNo());
		returnMap.put("payPlatOrderNo", orderPaymentOld.getChannelTradeNo());// 在回调的情况下才有
		// 00:订单创建,01:支付中,02:交易成功,03:交易失败,04:已退款,05:已撤销
		// 00：等待付款、01：交易成功、02：交易失败  04已退款05:已撤销
		if("02".equals(orderPaymentOld.getOrderStatus())){
			returnMap.put("returnCode", ApiErrorCode.REFUND_300001.getCode());
			returnMap.put("returnMsg", ApiErrorCode.REFUND_300001.getDesc());
			returnMap.put("orderStatus", orderPaymentOld.getOrderStatus());
			return returnMap;
		}
		if ("04".equals(orderPaymentOld.getOrderStatus())) {// 已退款的情况下不做第三方查询
			returnMap.put("returnCode", ApiErrorCode.REFUND_300002.getCode());
			returnMap.put("returnMsg", ApiErrorCode.REFUND_300002.getDesc());
			returnMap.put("orderStatus", "04");
			return returnMap;
		}
		if ("05".equals(orderPaymentOld.getOrderStatus())) {// 已退款的情况下不做第三方查询
			returnMap.put("returnCode", ApiErrorCode.REFUND_300003.getCode());
			returnMap.put("returnMsg", ApiErrorCode.REFUND_300003.getDesc());
			returnMap.put("orderStatus", "05");
			return returnMap;
		}
		ChannelAdmin admin = payCallBackOperatorService.findByIdentityNo(bo.getCashierNo());
		if (admin == null) {
			log.info("refund method admin is null");
			returnMap.put("returnCode", ApiErrorCode.QUERY_200002.getCode());
			returnMap.put("returnMsg", ApiErrorCode.QUERY_200002.getDesc());
		} else {
			try {
				MerchantInfo merchantInfo = payCallBackOperatorService.findByMerchantNo(orderPaymentOld.getMerchantNo());
				MerchantRefundVo vo = new MerchantRefundVo();
				vo.setMerchantNo(merchantInfo.getPlatformMerchantNo());
				vo.setOrderNo(orderPaymentOld.getPayTradeNo());
				Map<String, String> map = merchantBusinessService.merchantRefund(vo);
				log.info("get order query result map is " + map);
				if (map.get("respCode").equals(ScanCodeConstants.INTERFACE_SUCCESS_STATUS)) {
					returnMap.put("returnCode", ApiErrorCode.SYSTEM_000000.getCode());
					returnMap.put("returnMsg", ApiErrorCode.SYSTEM_000000.getDesc());
					returnMap.put("orderStatus", map.get("orderStatus"));
				} else {
					returnMap.put("returnCode", map.get("respCode"));
					returnMap.put("returnMsg", map.get("respDesc"));
					returnMap.put("orderStatus", orderPaymentOld.getOrderStatus());
				}
				if (Strings.equals(ApiErrorCode.SYSTEM_000000.getCode(), returnMap.get("returnCode"))) {// 如果六个0退款成功，则将表中字段更新为
					// '支付状态 00:订单创建,01:支付中,02:交易成功,03:交易失败,04:已退款,05:已撤销'
					OrderPay orderPay = new OrderPay();
					orderPay.setPayStatus("04");
					orderPay.setRefundStatus("Y");
					CriteriaBuilder cb = Cnd.builder(OrderPay.class);
					cb.andEQ("tradeNo", bo.getTradeNo());
					Criteria c = cb.buildCriteria();
					orderPayDAO.updateByCriteria(orderPay, c);

					OrderPayment orderPayment = new OrderPayment();
					orderPayment.setOrderStatus("04");
					orderPayment.setRefundStatus("Y");
					cb = Cnd.builder(OrderPayment.class);
					cb.andEQ("tradeNo", bo.getTradeNo());
					c = cb.buildCriteria();
					orderPaymentDAO.updateByCriteria(orderPayment, c);

					returnMap.put("orderStatus", "04");
				}
				// 记录到退款表中
			} catch (Exception e) {
				log.error("to call merchantbusiness is error.."+e);
				returnMap.put("returnCode", ApiErrorCode.SYSTEM_999999.getCode());
				returnMap.put("returnMsg", ApiErrorCode.SYSTEM_999999.getDesc());
			}
		}
		log.info("##########返回结果 returnMap:" + returnMap);
		return returnMap;
	}
	
	

}
