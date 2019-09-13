package com.hfepay.scancode.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import com.hfepay.scancode.commons.ScanCodeGetWayErrorCode;
import com.hfepay.scancode.commons.bo.MerchantBankChangeMessage;
import com.hfepay.scancode.commons.bo.MerchantJoinMessage;
import com.hfepay.scancode.commons.bo.MerchantRateMessage;
import com.hfepay.scancode.service.payway.CallBackService;
import com.hfepay.scancode.signature.EncrypterUtil;

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
	
	@Autowired
	private CallBackService callBackService;
	
	/**
	 * 银行卡变更回调
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/bankChangeCallBack")
	public void bankChangeCallBack(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> returnInfo = new HashMap<String,String>();
		
		log.info("#########开始执行银行卡变更回调#######");
		String inputLine;
		String notityJson = "";
		
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityJson += inputLine;
			}
			request.getReader().close();
			log.info("==>银行卡变更请求数据包:"+notityJson);
			Map responseMap = JSON.parseObject(notityJson, Map.class);
			Map header= JSON.parseObject(responseMap.get("head").toString(), Map.class);
			String content = (String) responseMap.get("body");
			
			String sign = (String) header.get("sign");
			String encryptKey = (String) header.get("encryptKey");
			log.info("==>银行卡变更解密前body:"+content);
			log.info("==>银行卡变更解密前sign:"+sign);
			log.info("==>银行卡变更解密前encryptKey:"+encryptKey);
			
			if (content != null && sign != null && encryptKey != null) {
				responseMap.put("body", EncrypterUtil.decode(content, sign, encryptKey));
			}
			log.info("==>银行卡变更解密后body:"+responseMap.toString());
			
			String body = (String) responseMap.get("body");
			JSONObject object = JSONObject.fromObject(body);
			
			MerchantBankChangeMessage merchantBankChangeMessage = JSON.parseObject(object.getString("body"), MerchantBankChangeMessage.class);
			
			//校验参数
			returnInfo = checkCallBackBankChange(merchantBankChangeMessage);
			if (null != returnInfo) {
				log.info("#########银行卡变更回调系统错误:"+JSON.toJSONString(returnInfo)+"########");
				return ;
			}
			
			//回调逻辑
			callBackService.bankChangeCallBack(merchantBankChangeMessage);
			
			response.getWriter().print(ScanCodeGetWayErrorCode.SYSTEM_000000.getCode());
			response.getWriter().close();
		} catch (Exception e) {
			log.error("#########银行卡变更回调异常#######"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 商户入驻回调
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/merchantJoinCallBack")
	public void merchantJoinCallBack(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> returnInfo = new HashMap<String,String>();
		
		log.info("#########开始执行商户入驻回调#######");
		String inputLine;
		String notityJson = "";
		List<MerchantRateMessage> merchantRateList = new ArrayList<>();
		
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityJson += inputLine;
			}
			request.getReader().close();
			log.info("==>商户入驻请求数据包:"+notityJson);
			Map responseMap = JSON.parseObject(notityJson, Map.class);
			Map header= JSON.parseObject(responseMap.get("head").toString(), Map.class);
			String content = (String) responseMap.get("body");
			
			String sign = (String) header.get("sign");
			String encryptKey = (String) header.get("encryptKey");
			log.info("==>商户入驻解密前body:"+content);
			log.info("==>商户入驻解密前sign:"+sign);
			log.info("==>商户入驻解密前encryptKey:"+encryptKey);
			
			if (content != null && sign != null && encryptKey != null) {
				responseMap.put("body", EncrypterUtil.decode(content, sign, encryptKey));
			}
			log.info("==>商户入驻解密后body:"+responseMap.toString());
			
			String body = (String) responseMap.get("body");
			JSONObject object = JSONObject.fromObject(body);
			JSONObject info = JSONObject.fromObject(object.getString("body"));
			
			MerchantJoinMessage merchantJoinMessage = JSON.parseObject(info.getString("info"), MerchantJoinMessage.class);
			
			com.alibaba.fastjson.JSONArray payTypes = JSON.parseArray(info.getString("payType"));
			for (int i = 0; i < payTypes.size(); i++) {
				MerchantRateMessage merchantRateMessage = JSON.parseObject(payTypes.get(i).toString(), MerchantRateMessage.class);
				merchantRateList.add(merchantRateMessage);
			}
		
			//校验参数
			returnInfo = checkCallBackJoin(merchantJoinMessage);
			if (null != returnInfo) {
				log.info("#########商户入驻回调系统错误:"+JSON.toJSONString(returnInfo)+"########");
				return ;
			}
			
			//回调逻辑
			callBackService.joinCallBack(merchantJoinMessage,merchantRateList);
			
			response.getWriter().print(ScanCodeGetWayErrorCode.SYSTEM_000000.getCode());
			response.getWriter().close();
		} catch (Exception e) {
			log.error("#########商户入驻回调异常#######"+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 支付回调数据校验
	 * @param bo
	 * @return Map<String,String>
	 */
	private Map<String,String> checkCallBackBankChange(MerchantBankChangeMessage bo) {
		if (Strings.isEmpty(bo.getMerchantNo())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"商户编号不能为空");
		}
		if (Strings.isEmpty(bo.getStatus())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"提现状态不能为空");
		}
		
		return null;
	}
	
	/**
	 * 商户回调数据校验
	 * @param bo
	 * @return Map<String,String>
	 */
	private Map<String,String> checkCallBackJoin(MerchantJoinMessage bo) {
		if (Strings.isEmpty(bo.getMerchantNo())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"商户编号不能为空");
		}
		if (Strings.isEmpty(bo.getAuditStatus())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"入驻状态不能为空");
		}
		
		return null;
	}
	
//	/**
//	 * 支付回调数据校验
//	 * @param bo
//	 * @return Map<String,String>
//	 */
//	private Map<String,String> checkCallBackWithdraws(MerchantWithdrowNotifyMessage bo) {
//		if (Strings.isEmpty(bo.getOrderNo())) {
//			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"订单编号不能为空");
//		}
//		if (Strings.isEmpty(bo.getUserOrderNo())) {
//			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"商户订单号不能为空");
//		}
//		if (Strings.isEmpty(bo.getStatus())) {
//			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"提现状态不能为空");
//		}
//		
//		return null;
//	}
//	
//
//	/**
//	 * 支付回调数据校验
//	 * @param bo
//	 * @return Map<String,String>
//	 */
//	private Map<String,String> checkCallBackOrder(MerchantNotifyMessage bo) {
//		if (Strings.isEmpty(bo.getOrderNo())) {
//			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"订单编号不能为空");
//		}
//		if (Strings.isEmpty(bo.getUserOrderNo())) {
//			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"商户订单号不能为空");
//		}
//		if (Strings.isEmpty(bo.getStatus())) {
//			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"支付状态不能为空");
//		}
//		return null;
//	}
	
	private Map<String,String> returnInfo(String returnCode,String returnMsg) {
		Map<String,String> returnInfo = new HashMap<String,String>();
		returnInfo.put("returnCode", returnCode);
		returnInfo.put("returnMsg", returnMsg);
		return returnInfo;
	}
	
//	/**
//	 * 修改回调日志
//	 * @param userOrderNo
//	 * @param notityJson
//	 */
//	private void updateApiLog(String userOrderNo,String notityJson) {
//		ApiLog apiLog = apiLogApiService.findByTradeNo(userOrderNo);
//		if (null != apiLog) {
//			log.info("########提现回调报文更新######");
//			//记录日志
//			ApiLogCondition apiLogCondition = new ApiLogCondition();
//			apiLogCondition.setTradeNo(apiLog.getTradeNo());
//			apiLogCondition.setNotifyParams(notityJson);
//			apiLogCondition.setNotifyTime(new Date());
//			apiLogCondition.setId(apiLog.getId());
//			apiLogApiService.update(apiLogCondition);	
//		}
//	}
}
