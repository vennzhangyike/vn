package com.hfepay.pay.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.pay.service.PayOrderPaymentService;
import com.hfepay.pay.service.PayOrderQueryService;
import com.hfepay.scancode.api.entity.vo.TradeQueryVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.bo.OrderQueryBo;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.exception.ApiErrorCode;

import net.sf.json.JSONObject;
@Service("payOrderQueryService")
public class PayOrderQueryServiceImpl implements PayOrderQueryService {
	public static final Logger logger = LoggerFactory.getLogger(PayOrderQueryServiceImpl.class);
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private PayOrderPaymentService payOrderPaymentService;
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;

	@Override
	public Map<String, String> queryOrder(OrderQueryBo bo) {
		Map<String, String> returnMap = new HashMap<>();
		returnMap.put("cashierNo", bo.getCashierNo());
		returnMap.put("tradeNo", bo.getTradeNo());
		ChannelAdmin admin = payCallBackOperatorService.findByIdentityNo(bo.getCashierNo());
		if (admin == null) {
			logger.info("queryOrder method admin is null");
			returnMap.put("returnCode", ApiErrorCode.QUERY_200002.getCode());
			returnMap.put("returnMsg", ApiErrorCode.QUERY_200002.getDesc());
			return returnMap;
		}
		logger.info("#######admin[" + JSONObject.fromObject(admin) + "]######");

		OrderPayment orderPayment = payOrderPaymentService.findQueryOrder(bo);
		if (orderPayment == null) {
			returnMap.put("returnCode", ApiErrorCode.QUERY_200003.getCode());
			returnMap.put("returnMsg", ApiErrorCode.QUERY_200003.getDesc());
			return returnMap;
		}
		returnMap.put("userOrderNo", orderPayment.getExtraTradeNo());// 第三方上送的流水号
		// 00:订单创建,01:支付中,02:交易成功,03:交易失败,04:已退款,05:已撤销
		//00：等待付款、01：交易成功、02：交易失败  04已退款 05已撤销
		if ("02".equals(orderPayment.getOrderStatus()) || "01".equals(orderPayment.getOrderStatus())
				|| "04".equals(orderPayment.getOrderStatus())|| "05".equals(orderPayment.getOrderStatus())) {//
			returnMap.put("returnCode", ApiErrorCode.SYSTEM_000000.getCode());
			returnMap.put("returnMsg", ApiErrorCode.SYSTEM_000000.getDesc());
			returnMap.put("orderStatus", orderPayment.getOrderStatus());
			// returnMap.put("payPlatOrderNo",orderPayment.getChannelTradeNo());//微信或者支付宝返回的流水号，暂时没有
		} else {// 支付中
			String orderNo = orderPayment.getPayTradeNo();
			// 没有第三方流水号，同时又是支付中，那么订单有问题
			if (Strings.isEmpty(orderNo)) {// 民生银行需要的查询条件
				returnMap.put("returnCode", ApiErrorCode.QUERY_200004.getCode());
				returnMap.put("returnMsg", ApiErrorCode.QUERY_200004.getDesc());
				return returnMap;
			} else {
				MerchantInfo merchantInfo = payCallBackOperatorService.findByMerchantNo(orderPayment.getMerchantNo());
				TradeQueryVo queryVo = new TradeQueryVo();
				queryVo.setOrderNo(orderPayment.getPayTradeNo());
				queryVo.setUserOrderNo(orderPayment.getTradeNo());
				queryVo.setMerchantNo(merchantInfo.getPlatformMerchantNo());
				Map<String, String> map = new HashMap<>();
				try {
					map = merchantBusinessService.tradeQuery(queryVo);
					logger.info("get order query result map is "+map);
					if (map.get("respCode").equals(ScanCodeConstants.INTERFACE_SUCCESS_STATUS)) {
						returnMap.put("returnCode", ApiErrorCode.SYSTEM_000000.getCode());
						returnMap.put("returnMsg", ApiErrorCode.SYSTEM_000000.getDesc());
						if (Strings.isNotEmpty(map.get("orderStatus"))) {//支付返回码修改将接口返回的码转换为系统内部的
							returnMap.put("orderStatus", transOrderStatus(map.get("orderStatus")));
						}else{
							returnMap.put("orderStatus", orderPayment.getOrderStatus());
						}
						// returnMap.put("payPlatOrderNo",res.getPayPlatOrderNo());//暂时没有
					} else {
						returnMap.put("returnCode", ApiErrorCode.SYSTEM_999999.getCode());
						returnMap.put("returnMsg", ApiErrorCode.SYSTEM_999999.getDesc());
					}

				} catch (Exception e) {
					logger.error("queryOrder call merchantBusiness error.." + e);
					returnMap.put("returnCode", ApiErrorCode.SYSTEM_999999.getCode());
					returnMap.put("returnMsg", ApiErrorCode.SYSTEM_999999.getDesc());
				}
			}
		}
		return returnMap;
	}
	
	/**
	 * 接口返回码和数据库表的转换
	 * @param orderStatus
	 * @return
	 */
	private String transOrderStatus(String orderStatus){
		if("00".equals(orderStatus)||"01".equals(orderStatus)){
			return "00";
		}else if("02".equals(orderStatus)){
			return "01";
		}else if("03".equals(orderStatus)){
			return "02";
		}else{
			return orderStatus;
		}
	}

}
