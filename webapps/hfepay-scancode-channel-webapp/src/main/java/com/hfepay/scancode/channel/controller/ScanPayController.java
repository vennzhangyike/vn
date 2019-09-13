package com.hfepay.scancode.channel.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayScanCodeService;
import com.hfepay.pay.service.PayScanPayService;
import com.hfepay.scancode.api.condition.ApiLogCondition;
import com.hfepay.scancode.api.entity.ApiLog;
import com.hfepay.scancode.api.service.ApiLogService;
import com.hfepay.scancode.channel.commons.BaseErrorMsg;
import com.hfepay.scancode.channel.commons.signature.EncrypterUtil;
import com.hfepay.scancode.commons.bo.MerchantNotifyMessage;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.ScanPayCondition;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.service.operator.MerchantActivityService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping
public class ScanPayController  extends BaseController{
	public static final Logger log = LoggerFactory.getLogger(ScanPayController.class);
	@Autowired
	private PayScanPayService payScanPayService;
	@Autowired
	private PayScanCodeService payScanCodeService;
	@Autowired
	private ApiLogService apiLogApiService;
	@Autowired
	private MerchantPaywayService merchantPaywayService;
	@Autowired
	private MerchantCashierService merchantCashierService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private MerchantActivityService merchantcashingActive;
	/**
	 * 生成支付订单
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@RequestMapping("/adminManage/pay")
	@ResponseBody
	public BaseErrorMsg scanCodePay(ModelMap model,HttpServletRequest request,ScanPayCondition condition){
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		OrderBo orderBo = new OrderBo();
		if("4".equals(user.getIdentityFlag())){//收银员是否被禁用
			MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
			merchantCashierCondition.setRecordStatus("Y");
			merchantCashierCondition.setOpenId(user.getOpenId());
			MerchantCashier cashier = merchantCashierService.findByCondition(merchantCashierCondition);
			if(cashier==null){
				return new BaseErrorMsg("收银员被删除,不能执行该操作");
			}
			if(Strings.isEmpty(cashier.getStatus())||"2".equals(cashier.getStatus())){
				return new BaseErrorMsg("收银员状态无效,不能执行该操作");
			}
			if(Strings.isEmpty(cashier.getStoreNo())){
				return new BaseErrorMsg("收银员门店尚未绑定,不能执行该操作");
			}
			orderBo.setStoreNo(cashier.getStoreNo());
		}else if("3".equals(user.getIdentityFlag())){//商户是否被禁用
			MerchantInfo info = merchantInfoService.findByMerchantNo(user.getMerchantNo());
			if(!"3".equals(info.getStatus())){
				return new BaseErrorMsg("商户状态无效,不能执行该操作");
			}
		}
		else{
			return new BaseErrorMsg("商户和收银员才能执行收款");
		}
		
		Map<String,String> returnInfo = new HashMap<String,String>();
		try {
			//检测支付方式是否合法
			BaseErrorMsg msg = checkParams(condition);
			if(msg!=null){
				return msg;
			}
			//接收参数
			log.info("########pay:"+condition+"########");
			orderBo.setChannelNo(user.getChannelCode());
			orderBo.setAgentNo(user.getAgentNo());
			orderBo.setMerchantNo(user.getMerchantNo());
			//调用支付逻辑
			orderBo.setQrCode(null);
			orderBo.setPayCode(condition.getPayCode());
			orderBo.setPrice(condition.getOrderAmt());
			orderBo.setAuthCode(condition.getAuthCode());
			orderBo.setScene(condition.getScene());
			orderBo.setCashier(user.getIdentityNo());
			returnInfo = payScanPayService.scanCodePay(orderBo);
			if (ScanCodeErrorCode.SYSTEM_000000.getCode().equals(returnInfo.get("returnCode"))) {
				return new BaseErrorMsg("0", "支付成功");
			}else {
				model.addAttribute("returnInfo",returnInfo);
				return new BaseErrorMsg(returnInfo.get("returnMsg"));
			}
		} catch (Exception e) {
			log.error("#########系统错误:"+e+"########"+e);
			e.printStackTrace();
			return new BaseErrorMsg("支付系统错误");
		}
	}
	
	private BaseErrorMsg checkParams(ScanPayCondition condition){
		Map<String,String> map = PayCode.toMap();
		if(Strings.isEmpty(map.get(condition.getPayCode()))){
			return new BaseErrorMsg("支付方式不存在");
		}
		else if(Strings.isEmpty(condition.getAuthCode())){
			return new BaseErrorMsg("支付授权码为空");
		}
		else if(condition.getOrderAmt()==null||condition.getOrderAmt().compareTo(new BigDecimal("0"))<1){
			return new BaseErrorMsg("支付金额有误");
		}
		return null;
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
			
			//回调逻辑
			payScanPayService.payCallBack(merchantNotifyMessage);
			
			response.getWriter().print("000000");
			response.getWriter().close();
		} catch (Exception e) {
			log.error("#########支付回调异常#######"+e);
			e.printStackTrace();
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
	 * 商户收银菜单
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@RequestMapping("/adminManage/merchantcashing")
	public String merchantcashing(ModelMap model,HttpServletRequest request,ScanPayCondition condition){
		Map<String,String> map = PayCode.toMap();
		Map<String,String> paywayMap = new HashMap<>();
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		//查询商户的支付方式
		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		merchantPaywayCondition.setStatus("1");//状态正常
		merchantPaywayCondition.setAcceptStatus("1");//入网成功
		merchantPaywayCondition.setMerchantNo(user.getMerchantNo());
		List<MerchantPayway> list = merchantPaywayService.findAll(merchantPaywayCondition);
		for (MerchantPayway merchantPayway : list) {
			String payCode =  merchantPayway.getPayCode();
			paywayMap.put(payCode, map.get(payCode));//商户配置的可用的支付方式
		}
		request.setAttribute("payWays", paywayMap);
		return "admin/merchantcashing/list"; 
	}
	
//	/**
//	 * 商户收银活动优惠
//	 * @param request
//	 * @param response
//	 * @return returnInfo
//	 */
//	@RequestMapping("/adminManage/merchantcashingActive")
//	@ResponseBody
//	public Map<String,BigDecimal> merchantcashingActive(HttpServletRequest request){
//		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
//		BigDecimal money = new BigDecimal(request.getParameter("money"));
//		Map<String,BigDecimal> map = merchantcashingActive.calculateCheapCash(user.getMerchantNo(), money);
//		if(map==null||map.isEmpty()){
//			return null;
//		}
//		return map;
//	}
	
	
	/**
	 * 商户扫码收款
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@RequestMapping("/adminManage/sweepcollection")
	public String sweepcollection(ModelMap model,HttpServletRequest request,ScanPayCondition condition){
		Map<String,String> map = PayCode.toMap();
		Map<String,String> paywayMap = new HashMap<>();
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		//查询商户的支付方式
		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		merchantPaywayCondition.setStatus("1");//状态正常
		merchantPaywayCondition.setAcceptStatus("1");//入网成功
		merchantPaywayCondition.setMerchantNo(user.getMerchantNo());
		List<MerchantPayway> list = merchantPaywayService.findAll(merchantPaywayCondition);
		for (MerchantPayway merchantPayway : list) {
			String payCode =  merchantPayway.getPayCode();
			paywayMap.put(payCode, map.get(payCode));//商户配置的可用的支付方式
		}
		request.setAttribute("payWays", paywayMap);
		return "admin/sweepcollection/list"; 
	}
	
	/**
	 * 生成支付扫码收款二维码
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@RequestMapping("/adminManage/pay/sweepcollection")
	@ResponseBody
	public net.sf.json.JSON paySweepcollection(ModelMap model,HttpServletRequest request,ScanPayCondition condition){
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		log.info("扫码收款用户信息:"+JSONSerializer.toJSON(user));
		Map<String,String> returnInfo = new HashMap<String,String>();
		OrderBo orderBo = new OrderBo();
		
		if(!("4".equals(user.getIdentityFlag()) || "3".equals(user.getIdentityFlag()))){
			returnInfo.put(EXECUTE_STATUS, String.valueOf(FAILED));
			returnInfo.put(VALUES,"该角色不支持扫码收款");
			return JSONSerializer.toJSON(returnInfo);
		}
		
		try {
			//检测支付方式是否合法
			String msg = checkSweepcollectionParams(condition);
			if(msg!=null){
				returnInfo.put(EXECUTE_STATUS, String.valueOf(FAILED));
				returnInfo.put(VALUES, msg);
				return JSONSerializer.toJSON(returnInfo);
			}
			//接收参数
			log.info("########pay:"+condition+"########");
			orderBo.setChannelNo(user.getChannelCode());
			orderBo.setAgentNo(user.getAgentNo());
			orderBo.setMerchantNo(user.getMerchantNo());
			orderBo.setCashier(user.getIdentityNo());
			
			//如果说收银员则获取门店信息传入
			if("4".equals(user.getIdentityFlag())){//收银员是否被禁用
				MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
				merchantCashierCondition.setRecordStatus("Y");
				merchantCashierCondition.setOpenId(user.getOpenId());
				MerchantCashier cashier = merchantCashierService.findByCondition(merchantCashierCondition);
				if(cashier==null){
					returnInfo.put(EXECUTE_STATUS, String.valueOf(FAILED));
					returnInfo.put(VALUES, "收银员被删除,不能执行该操作");
					return JSONSerializer.toJSON(returnInfo);
				}
				if(Strings.isEmpty(cashier.getStatus())||"2".equals(cashier.getStatus())){
					returnInfo.put(EXECUTE_STATUS, String.valueOf(FAILED));
					returnInfo.put(VALUES, "收银员状态无效,不能执行该操作");
					return JSONSerializer.toJSON(returnInfo);
				}
				if(Strings.isEmpty(cashier.getStoreNo())){
					returnInfo.put(EXECUTE_STATUS, String.valueOf(FAILED));
					returnInfo.put(VALUES, "收银员门店尚未绑定,不能执行该操作");
					return JSONSerializer.toJSON(returnInfo);
				}
				orderBo.setStoreNo(cashier.getStoreNo());
			}
			
			//调用支付逻辑
			orderBo.setQrCode(null);
			orderBo.setPayCode(condition.getPayCode());
			orderBo.setPrice(condition.getOrderAmt());
			returnInfo = payScanCodeService.pay(orderBo);
			if (ScanCodeErrorCode.SYSTEM_000000.getCode().equals(returnInfo.get("returnCode"))) {
			}else {
			}
		} catch (Exception e) {
			log.error("#########系统错误:"+e+"########"+e);
			returnInfo.put(EXECUTE_STATUS, String.valueOf(FAILED));
			returnInfo.put(VALUES, e.getMessage());
			return JSONSerializer.toJSON(returnInfo);
		}
		returnInfo.put(EXECUTE_STATUS, String.valueOf(SUCCESS));
		return JSONSerializer.toJSON(returnInfo);
	}
	
	private String checkSweepcollectionParams(ScanPayCondition condition){
		Map<String,String> map = PayCode.toMap();
		if(Strings.isEmpty(map.get(condition.getPayCode()))){
			return "支付方式不存在";
		}else if(condition.getOrderAmt()==null||condition.getOrderAmt().compareTo(new BigDecimal("0"))<1){
			return "支付金额有误";
		}
		return null;
	}
}
