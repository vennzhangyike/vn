package com.hfepay.scancode.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayOrderPaymentService;
import com.hfepay.pay.service.PayService;
import com.hfepay.pay.service.PayWithdrawalsService;
import com.hfepay.pay.service.WithdrawService;
import com.hfepay.pay.service.exception.PayException;
import com.hfepay.scancode.api.condition.ApiLogCondition;
import com.hfepay.scancode.api.entity.ApiLog;
import com.hfepay.scancode.api.service.ApiLogService;
import com.hfepay.scancode.commons.ScanCodeGetWayErrorCode;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.MerchantWithdrowNotifyMessage;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.Withdrawals;
import com.hfepay.scancode.signature.EncrypterUtil;
import com.hfepay.scancode.utils.NotifyUtils;

import net.sf.json.JSONObject;

/**
 * 回调
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/scancodecallback")
public class CallbackController {
	
	public static final Logger log = LoggerFactory.getLogger(CallbackController.class);
	
//	@Autowired
//	private PayCallBackService payCallBackService;
	@Autowired
	private PayOrderPaymentService payOrderPaymentService;
	@Autowired
	private ApiLogService apiLogApiService;
	@Autowired
	private PayService payService;
	@Autowired
	private WithdrawService withdrawService;
	@Autowired
	private PayWithdrawalsService payWithdrawalsService;
	/**
	 * 支付回调
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/payCallBack")
	public void scanCodePayCallBack(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> returnInfo = new HashMap<String,String>();
		
		log.info("#########开始执行支付回调#######");
		String inputLine;
		String notityJson = "";
		
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityJson += inputLine;
			}
			request.getReader().close();
			log.info("==>支付请求数据包:"+notityJson);
			Map responseMap = JSON.parseObject(notityJson, Map.class);
//			Map responseMap = Maps.newMap();
			Map header= JSON.parseObject(responseMap.get("head").toString(), Map.class);
			String content = (String) responseMap.get("body");
//			String content = "bqpIZxHBzulhm1+juHdMth+0eGLPlfnoxbq0SOl9TtBga5wWvCNch/y9B3vQUexJQhHZX3owG3UXw1I5uMRCAsTNTWtN1xpzIMwyXEnBJXg5FVQ4nDOTiVk49oUysFQdGsaysrnK0YHvRF/t/DjVyutvzlggMEtfR+VpFPTZDM5pFNYktYscoHEfvoB1ABE9mcXZwn5yXqBi3Pg1vH6d8tENk54SzcDErds/7c3YuMkifxHy1Zva6XWYHEIqRoWWcfZwYlRSi5UaK42VYXD+AJGrlPL+OZ53gPBUn87pUsrhyfw14inMv/aCmF7+pJD4AOhz8oENmG8/nNwnU/Izp3WeG4RbW+OJuDCSBBR0b8kMXdKZ4zYMcvwlcNhjM1ZtH+DOA32bdDrKTsTRdJUMbA==";
			
			String sign = (String) header.get("sign");
//			String sign = "HHffGR4Oqv4P1yv7c86si4BwsTCalMQnP6PacOjIUC2VqRpIgWIp4GSilg4P+R7ZJ18FXnAD/IOXbWMv6L7PMeEDvTqIhg7EXbk5LLhpkLeewwyOn3MUK6/f8bGgKYnx9o1t3+zniTze8JIid26THV0VI3UKyeQCwG7xxjTz0XuWa/CNpcaZexth1kBYlSos6hlSP4dKiA4LTayroydxF1GxfQA7e9O5ed5+pzVCuEGFyd5K3GBOCnlct6QXptMqzbz+PS/msgGtoFuegsyi13e7FyltJ8KqnnbAieAygMUuhGlzYifyyHpcCC0S4SG0xbj6cmY3YWgoA0N4ksqIvw==";
			String encryptKey = (String) header.get("encryptKey");
//			String encryptKey="CEHGtDmqNX1slEsiohBeKpM+6v+m2leWnrFL208/yq0t4qu4V8ijPBjZQxDkairbEvVtpiuovr0Fosv4b6qmIc3QonFSfh2JK/m+/Bzx+FzIDA/mwTNy+Ha5dBmcVt4b2kjdxjSnWUJHv7rLClghf4Rz+FYIwoIBjIP5q6D4PUcR1EktDzMi6wQwLkvVfVJ9PAzc29WjzVrHyhJCadC5PtAuCiDYaCXRpwWXh3vItxDTKaGrKAzF6mI++NRr31eaTFQ0tEfCZ9yIYwx1k4UJl4Cu0yFzG7+E0h+AImhJMuu8yXcLl9qlSGi29HJaCVH3HVRQaXjC3L3ijOoLwCaTyg==";
			log.info("==>支付解密前body:"+content);
			log.info("==>支付解密前sign:"+sign);
			log.info("==>支付解密前encryptKey:"+encryptKey);
			
			if (content != null && sign != null && encryptKey != null) {
				responseMap.put("body", EncrypterUtil.decode(content, sign, encryptKey));
			}
			log.info("==>支付解密后body:"+responseMap.toString());
			
			String body = (String) responseMap.get("body");
			JSONObject object = JSONObject.fromObject(body);
			
			MerchantNotifyMessage merchantNotifyMessage = JSON.parseObject(object.getString("body"), MerchantNotifyMessage.class);
			//修改日志
			updateApiLog(merchantNotifyMessage.getUserOrderNo(), responseMap.toString());
			
			merchantNotifyMessage.setUserReqNo(header.get("userReqNo").toString());
			//校验参数
			returnInfo = checkCallBackOrder(merchantNotifyMessage);
			if (null != returnInfo) {
				log.error("#########支付系统错误:"+JSON.toJSONString(returnInfo)+"########");
				return ;
			}
			//获取原交易订单信息
			OrderPayment orderPayment = payOrderPaymentService.findOrderPaymentByTradeNo(merchantNotifyMessage.getUserOrderNo());
			if(null==orderPayment){
				throw new PayException(ScanCodeErrorCode.PAY_500005.getCode(), ScanCodeErrorCode.PAY_500005.getDesc());
			}
			merchantNotifyMessage.setPayStrategy(orderPayment.getTemp3());//指定回调策略
			NotifyUtils.setToQueue(merchantNotifyMessage);
			//回调逻辑
			payService.payCallBack(merchantNotifyMessage);
			
			response.getWriter().print(ScanCodeGetWayErrorCode.SYSTEM_000000.getCode());
			response.getWriter().close();
		} catch (Exception e) {
			log.error("#########支付回调异常#######"+e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 提现回调
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/withdrawsCallBack")
	public void scanCodewithdrawsCallBack(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> returnInfo = new HashMap<String,String>();
		
		log.info("#########开始执行提现回调#######");
		String inputLine;
		String notityJson = "";
		
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityJson += inputLine;
			}
			request.getReader().close();
			log.info("==>提现请求数据包:"+notityJson);
			Map responseMap = JSON.parseObject(notityJson, Map.class);
			Map header= JSON.parseObject(responseMap.get("head").toString(), Map.class);
			String content = (String) responseMap.get("body");
			
			String sign = (String) header.get("sign");
			String encryptKey = (String) header.get("encryptKey");
			log.info("==>提现解密前body:"+content);
			log.info("==>提现解密前sign:"+sign);
			log.info("==>解密前encryptKey:"+encryptKey);
			
			if (content != null && sign != null && encryptKey != null) {
				responseMap.put("body", EncrypterUtil.decode(content, sign, encryptKey));
			}
			log.info("==>提现解密后body:"+responseMap.toString());
			
			String body = (String) responseMap.get("body");
			JSONObject object = JSONObject.fromObject(body);
			
			MerchantWithdrowNotifyMessage merchantWithdrowNotifyMessage = JSON.parseObject(object.getString("body"), MerchantWithdrowNotifyMessage.class);
			merchantWithdrowNotifyMessage.setUserReqNo(header.get("userReqNo").toString());
			if (null == merchantWithdrowNotifyMessage.getDrawFee()) {
				merchantWithdrowNotifyMessage.setDrawFee(new BigDecimal(0));
			}
			if (null == merchantWithdrowNotifyMessage.getTradeFee()) {
				merchantWithdrowNotifyMessage.setTradeFee(new BigDecimal(0));
			}
			if (null == merchantWithdrowNotifyMessage.getDrawAmount()) {
				merchantWithdrowNotifyMessage.setDrawAmount(new BigDecimal(0));
			}
			
			//修改日志
			updateApiLog(merchantWithdrowNotifyMessage.getUserOrderNo(), responseMap.toString());
			
			//校验参数
			returnInfo = checkCallBackWithdraws(merchantWithdrowNotifyMessage);
			if (null != returnInfo) {
				log.error("#########提现系统错误:"+JSON.toJSONString(returnInfo)+"########");
				return ;
			}
			Withdrawals vo = payWithdrawalsService.findByTradeNo(merchantWithdrowNotifyMessage.getUserOrderNo());
			if(null==vo){
				log.error("######## 提现记录不存在########");
				return ;
			}
			merchantWithdrowNotifyMessage.setWithDrawId(vo.getId());
			//回调逻辑
			withdrawService.withdrawsCallBack(merchantWithdrowNotifyMessage);
			
			response.getWriter().print(ScanCodeGetWayErrorCode.SYSTEM_000000.getCode());
			response.getWriter().close();
		} catch (Exception e) {
			log.error("#########提现回调异常#######"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 支付回调数据校验
	 * @param bo
	 * @return Map<String,String>
	 */
	private Map<String,String> checkCallBackWithdraws(MerchantWithdrowNotifyMessage bo) {
		if (Strings.isEmpty(bo.getOrderNo())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"订单编号不能为空");
		}
		if (Strings.isEmpty(bo.getUserOrderNo())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"商户订单号不能为空");
		}
		if (Strings.isEmpty(bo.getStatus())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"提现状态不能为空");
		}
		
		return null;
	}
	

	/**
	 * 支付回调数据校验
	 * @param bo
	 * @return Map<String,String>
	 */
	private Map<String,String> checkCallBackOrder(MerchantNotifyMessage bo) {
		if (Strings.isEmpty(bo.getOrderNo())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"订单编号不能为空");
		}
		if (Strings.isEmpty(bo.getUserOrderNo())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"商户订单号不能为空");
		}
		if (Strings.isEmpty(bo.getStatus())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"支付状态不能为空");
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
			apiLogApiService.update(apiLogCondition);	
		}
	}
	
	
	
	/**
	 * 条码支付回调
	 * @Title: payCallBack
	 * @author: husain
	 * @param model
	 * @param request
	 * @param condition
	 * @return
	 * @return: String
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/scanpay/payCallBack")
	public void payCallBack(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> returnInfo = new HashMap<String,String>();
		log.info("#########开始执行支付回调#######");
		String inputLine;
		String notityJson = "";
		
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityJson += inputLine;
			}
			request.getReader().close();
			log.info("==>支付请求数据包:"+notityJson);
			Map responseMap = JSON.parseObject(notityJson, Map.class);
//			Map responseMap = Maps.newMap();
			Map header= JSON.parseObject(responseMap.get("head").toString(), Map.class);
			String content = (String) responseMap.get("body");
//			String content = "bqpIZxHBzulhm1+juHdMth+0eGLPlfnoxbq0SOl9TtBga5wWvCNch/y9B3vQUexJQhHZX3owG3UXw1I5uMRCAsTNTWtN1xpzIMwyXEnBJXg5FVQ4nDOTiVk49oUysFQdGsaysrnK0YHvRF/t/DjVyutvzlggMEtfR+VpFPTZDM5pFNYktYscoHEfvoB1ABE9mcXZwn5yXqBi3Pg1vH6d8tENk54SzcDErds/7c3YuMkifxHy1Zva6XWYHEIqRoWWcfZwYlRSi5UaK42VYXD+AJGrlPL+OZ53gPBUn87pUsrhyfw14inMv/aCmF7+pJD4AOhz8oENmG8/nNwnU/Izp3WeG4RbW+OJuDCSBBR0b8kMXdKZ4zYMcvwlcNhjM1ZtH+DOA32bdDrKTsTRdJUMbA==";
			
			String sign = (String) header.get("sign");
//			String sign = "HHffGR4Oqv4P1yv7c86si4BwsTCalMQnP6PacOjIUC2VqRpIgWIp4GSilg4P+R7ZJ18FXnAD/IOXbWMv6L7PMeEDvTqIhg7EXbk5LLhpkLeewwyOn3MUK6/f8bGgKYnx9o1t3+zniTze8JIid26THV0VI3UKyeQCwG7xxjTz0XuWa/CNpcaZexth1kBYlSos6hlSP4dKiA4LTayroydxF1GxfQA7e9O5ed5+pzVCuEGFyd5K3GBOCnlct6QXptMqzbz+PS/msgGtoFuegsyi13e7FyltJ8KqnnbAieAygMUuhGlzYifyyHpcCC0S4SG0xbj6cmY3YWgoA0N4ksqIvw==";
			String encryptKey = (String) header.get("encryptKey");
//			String encryptKey="CEHGtDmqNX1slEsiohBeKpM+6v+m2leWnrFL208/yq0t4qu4V8ijPBjZQxDkairbEvVtpiuovr0Fosv4b6qmIc3QonFSfh2JK/m+/Bzx+FzIDA/mwTNy+Ha5dBmcVt4b2kjdxjSnWUJHv7rLClghf4Rz+FYIwoIBjIP5q6D4PUcR1EktDzMi6wQwLkvVfVJ9PAzc29WjzVrHyhJCadC5PtAuCiDYaCXRpwWXh3vItxDTKaGrKAzF6mI++NRr31eaTFQ0tEfCZ9yIYwx1k4UJl4Cu0yFzG7+E0h+AImhJMuu8yXcLl9qlSGi29HJaCVH3HVRQaXjC3L3ijOoLwCaTyg==";
			log.info("==>支付解密前body:"+content);
			log.info("==>支付解密前sign:"+sign);
			log.info("==>支付解密前encryptKey:"+encryptKey);
			
			if (content != null && sign != null && encryptKey != null) {
				responseMap.put("body", EncrypterUtil.decode(content, sign, encryptKey));
			}
			log.info("==>支付解密后body:"+responseMap.toString());
			
			String body = (String) responseMap.get("body");
			JSONObject object = JSONObject.fromObject(body);
			
			MerchantNotifyMessage merchantNotifyMessage = JSON.parseObject(object.getString("body"), MerchantNotifyMessage.class);
			//修改日志
			updateApiLog(merchantNotifyMessage.getUserOrderNo(), responseMap.toString());
			
			merchantNotifyMessage.setUserReqNo(header.get("userReqNo").toString());
			//校验参数
			returnInfo = checkCallBackOrder(merchantNotifyMessage);
			if (null != returnInfo) {
				log.error("#########支付系统错误:"+JSON.toJSONString(returnInfo)+"########");
				return ;
			}
			//获取原交易订单信息
			OrderPayment orderPayment = payOrderPaymentService.findOrderPaymentByTradeNo(merchantNotifyMessage.getUserOrderNo());
			if(null==orderPayment){
				throw new PayException(ScanCodeErrorCode.PAY_500005.getCode(), ScanCodeErrorCode.PAY_500005.getDesc());
			}
			merchantNotifyMessage.setPayStrategy(orderPayment.getTemp3());//指定回调策略
			NotifyUtils.setToQueue(merchantNotifyMessage);
			//回调逻辑
			payService.payCallBack(merchantNotifyMessage);
			
			response.getWriter().print("000000");
			response.getWriter().close();
		} catch (Exception e) {
			log.error("#########支付回调异常#######"+e);
			e.printStackTrace();
		}
	}
}
