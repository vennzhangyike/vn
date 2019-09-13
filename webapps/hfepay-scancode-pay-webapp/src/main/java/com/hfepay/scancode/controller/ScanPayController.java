package com.hfepay.scancode.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.lang.IpUtil;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.pay.service.PayService;
import com.hfepay.scancode.commons.BaseErrorMsg;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.OrganStatus;
import com.hfepay.scancode.commons.PayCodeStatus;
import com.hfepay.scancode.commons.ScanCodeGetWayErrorCode;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.ScanPayCondition;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.PayEntryEnum;
import com.hfepay.scancode.commons.contants.QrStatus;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.AgentPayway;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelPayway;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.MerchantQrcode;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.PlatformQrcode;

import net.sf.json.JSONObject;
/***扫码支付***/
@Controller
@RequestMapping
public class ScanPayController  extends BaseController{
	public static final Logger log = LoggerFactory.getLogger(ScanPayController.class);
//	@Autowired
//	private PayScanCodeService payScanCodeService;
	@Autowired
	private PayService payService;
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;
	/**
	 * 生成支付订单
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@RequestMapping("/scan/pay")
	public String scanCodePay(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		Map<String,String> returnInfo = new HashMap<String,String>();
		OrderBo orderBo = new OrderBo();
		String ip = IpUtil.getIpAddr(request);
		
		try {
			String browerType = request.getHeader("User-Agent").toLowerCase();
			log.info("#########browerType:"+browerType+"########");
			String payCode = "";
			
			//扫码浏览器判定
			if (browerType.toLowerCase().indexOf("micromessenger")!=-1) {	//微信
				payCode = Constants.WXGZHZF;
			}else if(browerType.toLowerCase().indexOf("alipayclient")!=-1)  {//支付宝
				payCode = Constants.ZFBSMZF;
			}else if(browerType.toLowerCase().indexOf("qq")!=-1)  {//qq支付
				payCode = Constants.QQZF;
			}else {
				returnInfo = returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100009.getCode(), ScanCodeGetWayErrorCode.VALIDAT_100009.getDesc());
				model.addAttribute("returnInfo",returnInfo);
				return "scancode/paymentResult";
			}
			
			//接收参数
			String qrCode = request.getParameter("qrCode");
			String price = request.getParameter("price");
			String storeNo = request.getParameter("storeNo");
			String identityNo = request.getParameter("identityNo");
			
			orderBo.setCashier(identityNo);//收款人
			orderBo.setStoreNo(storeNo);//门店
			orderBo.setUserOrderNo(request.getParameter("userOrderNo"));
			log.info("########pay:{qrCode["+qrCode+"],payCode["+payCode+"],price["+price+"]}########");
			
			//主码
			PlatformQrcode platformQrcode = payCallBackOperatorService.findPlatfromQrcodeByQrCode(qrCode);
			if (null != platformQrcode) {
				log.info("########pay:{qrCode["+qrCode+"]主码支付########");
				Map<String, String> checkReturn = verificationPayParams(qrCode,payCode,price,platformQrcode);
				log.info("########pay:{qrCode["+qrCode+"]主码支付["+JSONObject.fromObject(checkReturn)+"]########");
				//校验参数
				if(null != checkReturn){
					log.info("########pay:{qrCode["+qrCode+"],checkReturn["+checkReturn+"]}########");
					model.addAttribute("returnInfo",checkReturn);
					return "scancode/paymentResult";
				}
				orderBo.setChannelNo(platformQrcode.getChannelNo());
				orderBo.setAgentNo(platformQrcode.getAgentNo());
				orderBo.setMerchantNo(platformQrcode.getMerchantNo());
				
			}else {	//商户子码	
				log.info("########pay:{qrCode["+qrCode+"]子码支付########");
				MerchantQrcode merchantQrcode = payCallBackOperatorService.findByMerchantQrCode(qrCode);
				Map<String, String> checkReturn = verificationMerchantQrPayParams(qrCode,payCode,price,merchantQrcode);
				log.info("########pay:{qrCode["+qrCode+"]子码支付["+JSONObject.fromObject(checkReturn)+"]########");
				//校验参数
				if(null != checkReturn){
					log.info("########pay:{qrCode["+qrCode+"],checkReturn["+checkReturn+"]}########");
					model.addAttribute("returnInfo",checkReturn);
					return "scancode/paymentResult";
				}
				orderBo.setChannelNo(merchantQrcode.getChannelNo());
				orderBo.setAgentNo(merchantQrcode.getAgentNo());
				orderBo.setMerchantNo(merchantQrcode.getMerchantNo());
			}
			
			//校验二维码对应商户是否唯一
			log.info("########检查是否二维码对商户唯一:{qrCode["+qrCode+"]}########");
			List<MerchantInfo> merchantList = payCallBackOperatorService.findMerchantInfoByQrCode(qrCode);
			if (merchantList == null || merchantList.size() > 1) {
				log.info("########检查是否二维码对商户唯一:{qrCode["+qrCode+"],checkReturn["+ScanCodeGetWayErrorCode.VALIDAT_100018.getDesc()+"]}########");
				returnInfo = returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100018.getCode(), ScanCodeGetWayErrorCode.VALIDAT_100018.getDesc());
				model.addAttribute("returnInfo",returnInfo);
				return "scancode/paymentResult";
			}
			
			/*BigDecimal actualPayCash = new BigDecimal(price);
			BigDecimal tradeAmt = new BigDecimal(price);
			BigDecimal discountAmt = BigDecimal.ZERO;
			try {
				Map<String,BigDecimal> map = payCallBackOperatorService.getActualPayCash(orderBo.getMerchantNo(), actualPayCash, payCode);
				if(map==null||map.isEmpty()){
					actualPayCash = new BigDecimal(price);
				}else{
					actualPayCash = map.get("actualPayCash");
					discountAmt = map.get("discountCash");
				}
			} catch (Exception e) {
				returnInfo = returnInfo(ScanCodeGetWayErrorCode.SYSTEM_999998.getCode(), e.getMessage());
				model.addAttribute("returnInfo",returnInfo);
				return "scancode/paymentResult";
			}*/
			
			String discountStrategy = "";
			
			//调用支付逻辑
			orderBo.setQrCode(qrCode);
			orderBo.setPayCode(payCode);
			//orderBo.setPrice(actualPayCash);
			orderBo.setIp(ip);
			orderBo.setTradeAmt(new BigDecimal(price));
			//orderBo.setDiscountAmt(discountAmt);
			orderBo.setDiscountStrategy(discountStrategy);
			orderBo.setPayStrategy(payCode+"_"+PayEntryEnum.MERCHANTCODE_PAY.getEntryCode());//支付策略
			log.info("########qrCode["+qrCode+"]payCode["+payCode+"] 开始调用支付 ########");
			
			returnInfo = payService.pay(orderBo);
			if (returnInfo.get("returnCode").equals(ScanCodeErrorCode.SYSTEM_000000.getCode())) {
				response.sendRedirect(returnInfo.get("payStr"));
			}else {
				model.addAttribute("returnInfo",returnInfo);
				return "scancode/paymentResult";
			}
			
		} catch (Exception e) {
			log.error("#########系统错误:"+e+"########"+e);
			e.printStackTrace();
			returnInfo = returnInfo(ScanCodeGetWayErrorCode.SYSTEM_999998.getCode(), ScanCodeGetWayErrorCode.SYSTEM_999998.getDesc());
			model.addAttribute("returnInfo",returnInfo);
			return "scancode/paymentResult";
		}
		
		log.info("#########交易成功########");
		
		return "scancode/paymentResult";
	}
	
	/**
	 * 校验支付请求参数
	 * @param qrCode
	 * @param paymentType
	 * @param platformQrcode
	 * @param price
	 */
	private Map<String, String> verificationPayParams(String qrCode,String payCode,String price,PlatformQrcode platformQrcode) {
		if (null == platformQrcode) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"该二维码已失效或者不存在");
		}
		
		if (platformQrcode.getChannelNo().isEmpty()) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"渠道编号不能为空");
		}
		
		if (platformQrcode.getAgentNo().isEmpty()) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"代理商编号不能为空");
		}
		
		if (platformQrcode.getMerchantNo().isEmpty()) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"商户编号不能为空");
		}
		
		if (qrCode.isEmpty()) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"二维码编号不能为空");
		}
		
		if (payCode.isEmpty()) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"支付渠道类型不能为空");
		}

		if (price.isEmpty()) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"支付金额不能为空");
		}
		
		if (platformQrcode.getQrStatus().equals(QrStatus.QR_2.getCode())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),QrStatus.QR_2.getDesc());
		}
		
		Map<String,String> organResult = checkOrgan(platformQrcode.getMerchantNo(),platformQrcode.getAgentNo(),platformQrcode.getChannelNo(),payCode);
		if (null != organResult) {
			return organResult;
		}
		
		return null;
		
	}
	
	/**
	 * 支付前检查机构信息
	 * @param merchantNo
	 * @param agentNo
	 * @param channelNo
	 * @param payCode
	 * @return
	 */
	private Map<String,String> checkOrgan(String merchantNo,String agentNo,String channelNo,String payCode) {
		
		//校验商户是否正常
		MerchantInfo merchantInfo = payCallBackOperatorService.findByMerchantNo(merchantNo);
		if (null != merchantInfo) {
			if (!merchantInfo.getStatus().equals(MerchantStatus.MERCHANT_STATUS_3.getCode())) {
				return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100003.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100003.getDesc());
			}
			//校验代理商支付通道状态
			MerchantPayway merchantPayway = payCallBackOperatorService.findMerchantPaywayByMerchantPayCode(merchantNo, payCode);
			if (null != merchantPayway) {				
				if (!merchantPayway.getStatus().equals(PayCodeStatus.PAY_CODE_STATUS_1.getCode())) {
					return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100010.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100010.getDesc());
				}
			}else {
				return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100011.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100011.getDesc());
			}
		}else {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100006.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100006.getDesc());
		}
		
		//校验代理商是否正常
		AgentBase agentBase = payCallBackOperatorService.findByAgentNo(agentNo);
		if (null != agentBase) {
			if (agentBase.getStatus().equals(OrganStatus.ORGAN_2.getCode())) {
				
				return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100004.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100004.getDesc());
			}
			//校验代理商支付通道状态
			AgentPayway agentPayway = payCallBackOperatorService.findAgentPaywayByPayCode(payCode,agentNo);
			if (null != agentPayway) {				
				if (!agentPayway.getStatus().equals(PayCodeStatus.PAY_CODE_STATUS_1.getCode())) {
					return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100010.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100010.getDesc());
				}
			}else {
				return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100012.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100012.getDesc());
			}
		}else {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100007.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100007.getDesc());
		}
		
		//校验渠道上是否正常
		ChannelBase channelBase = payCallBackOperatorService.findByChannelNo(channelNo);
		if (null != channelBase) {
			if (channelBase.getStatus().equals(OrganStatus.ORGAN_2.getCode())) {
				return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100005.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100005.getDesc());
			}
			//校验渠道支付通道状态
			ChannelPayway channelPayway = payCallBackOperatorService.findChannelPaywayByPayCode(payCode,channelNo);
			if (null != channelPayway) {				
				if (!channelPayway.getStatus().equals(PayCodeStatus.PAY_CODE_STATUS_1.getCode())) {
					return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100010.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100010.getDesc());
				}
			}else {
				return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100013.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100013.getDesc());
			}
		}else {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100008.getCode(),ScanCodeGetWayErrorCode.VALIDAT_100008.getDesc());
		}
		
		return null;
	}
	
	
	/**
	 * 校验支付请求参数
	 * @param qrCode
	 * @param paymentType
	 * @param merchantQrcode
	 * @param price
	 */
	private Map<String, String> verificationMerchantQrPayParams(String qrCode,String payCode,String price,MerchantQrcode merchantQrcode) {
		
		if (qrCode.isEmpty()) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"二维码编号不能为空");
		}
		
		if (payCode.isEmpty()) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"支付渠道类型不能为空");
		}

		if (price.isEmpty()) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"支付金额不能为空");
		}
		
		if (null == merchantQrcode) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),"该二维码已失效或者不存在");
		}
		
		if (merchantQrcode.getQrStatus().equals(QrStatus.QR_2.getCode())) {
			return returnInfo(ScanCodeGetWayErrorCode.VALIDAT_100002.getCode(),QrStatus.QR_2.getDesc());
		}
		
		Map<String,String> organResult = checkOrgan(merchantQrcode.getMerchantNo(),merchantQrcode.getAgentNo(),merchantQrcode.getChannelNo(),payCode);
		if (null != organResult) {
			return organResult;
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
	 * 条码支付生成支付订单
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@RequestMapping("/scanCodePay/pay")
	@ResponseBody
	public Object scanCodePay(ModelMap model,HttpServletRequest request,ScanPayCondition condition){
		String functionName = request.getParameter("callbackFunction");
		MappingJacksonValue value = new MappingJacksonValue(null);
		value.setJsonpFunction(functionName);
		try {
		ChannelAdmin user = payCallBackOperatorService.findPushMsgAdmin(condition.getIdentityNo(),false);
		OrderBo orderBo = new OrderBo();
		if("4".equals(user.getIdentityFlag())){//收银员是否被禁用
			MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
			merchantCashierCondition.setRecordStatus("Y");
			merchantCashierCondition.setOpenId(user.getOpenId());
			MerchantCashier cashier = payCallBackOperatorService.findByCondition(merchantCashierCondition);
			if(cashier==null){
				value.setValue(new BaseErrorMsg("收银员被删除,不能执行该操作"));
				return value;
			}
			if(Strings.isEmpty(cashier.getStatus())||"2".equals(cashier.getStatus())){
				value.setValue(new BaseErrorMsg("收银员状态无效,不能执行该操作"));
				return value;
			}
			if(Strings.isEmpty(cashier.getStoreNo())){
				value.setValue(new BaseErrorMsg("收银员门店尚未绑定,不能执行该操作"));
				return value;
			}
			orderBo.setStoreNo(cashier.getStoreNo());
		}else if("3".equals(user.getIdentityFlag())){//商户是否被禁用
			MerchantInfo info = payCallBackOperatorService.findByMerchantNo(user.getMerchantNo());
			if(!"3".equals(info.getStatus())){
				value.setValue(new BaseErrorMsg("商户状态无效,不能执行该操作"));
				return value;
			}
			//商户给定主门店
			MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
			merchantStoreCondition.setMerchantNo(user.getMerchantNo());
			merchantStoreCondition.setStoreType("0");
			merchantStoreCondition.setRecordStatus("Y");
			MerchantStore store = payCallBackOperatorService.findByCondition(merchantStoreCondition);
			if(null==store){
				value.setValue(new BaseErrorMsg("商户不存在门店"));
				return value;
			}
			orderBo.setStoreNo(store.getStoreNo());
		}
		else{
			value.setValue(new BaseErrorMsg("商户和收银员才能执行收款"));
			return value;
		}
		
		Map<String,String> returnInfo = new HashMap<String,String>();
		
			//检测支付方式是否合法
			BaseErrorMsg msg = checkParams(condition);
			if(msg!=null){
				value.setValue(msg);
				return value;
			}
			//接收参数
			log.info("########pay:"+condition+"########");
			String payCode ="";
			if(Strings.startsWithChar(condition.getAuthCode(), '1')){
				payCode = PayCode.PAYCODE_WXGZHZF.getCode();			
			}else if(Strings.startsWithChar(condition.getAuthCode(), '9')){
				payCode = PayCode.PAYCODE_QQZF.getCode();			
			}else if(Strings.startsWithChar(condition.getAuthCode(), '2')){
				payCode = PayCode.PAYCODE_ZFBSMZF.getCode();
			}
			else{
				returnInfo.put("errorCode", ScanCodeErrorCode.QRCODE_700000.getCode());
				returnInfo.put("errorMsg", ScanCodeErrorCode.QRCODE_700000.getDesc());
				value.setValue(returnInfo);
				return value;
			}
			/*BigDecimal tradeAmt = condition.getOrderAmt();
			BigDecimal discountAmt = BigDecimal.ZERO;
			if(Strings.isNotEmpty(payCode)){
				BigDecimal actualPayCash = condition.getOrderAmt();
				try {
					Map<String,BigDecimal> map = payCallBackOperatorService.getActualPayCash(user.getMerchantNo(), condition.getOrderAmt(), payCode);
					if(map==null||map.isEmpty()){
						actualPayCash = condition.getOrderAmt();
					}else{
						actualPayCash = map.get("actualPayCash");
						discountAmt = map.get("discountCash");
					}
				} catch (Exception e) {
					value.setValue(new BaseErrorMsg(e.getMessage()));
					return value;
				}
				condition.setOrderAmt(actualPayCash);
			}*/
			
			
			orderBo.setChannelNo(user.getChannelCode());
			orderBo.setAgentNo(user.getAgentNo());
			orderBo.setMerchantNo(user.getMerchantNo());
			//调用支付逻辑
			orderBo.setQrCode(null);
			orderBo.setPayCode(payCode);
			//orderBo.setPrice(condition.getOrderAmt());
			orderBo.setAuthCode(condition.getAuthCode());
			orderBo.setScene(condition.getScene());
			orderBo.setCashier(user.getIdentityNo());
			orderBo.setTradeAmt(condition.getOrderAmt());
			//orderBo.setDiscountAmt(discountAmt);
			orderBo.setDiscountStrategy("");
			orderBo.setPayStrategy(payCode+"_"+PayEntryEnum.BARCODE_PAY.getEntryCode());//此处是支付的入口，它最清楚用什么支付方式支付：
			//if(Strings.isEmpty(condition.getFromH5())){//PC端提供了payCode,同时无fromH5参数，无需尝试其他支付方式
				returnInfo = payService.pay(orderBo);
//			}else{
//				returnInfo = executePay(orderBo);//遍历商户的支付方法循环支付
//			}
			
			if (ScanCodeErrorCode.SYSTEM_000000.getCode().equals(returnInfo.get("returnCode"))) {
				returnInfo.put("errorCode", "0");
				returnInfo.put("errorMsg", "支付成功");
				returnInfo.put("orderAmt", condition.getOrderAmt().toString());
				returnInfo.put("tradeAmt", orderBo.getTradeAmt().toString());
				returnInfo.put("discountAmt", returnInfo.get("discountAmt"));//orderBo.getDiscountAmt().toString());
//				value.setValue(new BaseErrorMsg("0", "支付成功"));
				value.setValue(returnInfo);
				return value;
			}else if("200002".equals(returnInfo.get("returnCode"))){
				returnInfo.put("errorCode", "200002");
				returnInfo.put("errorMsg", "支付处理中，等待银行回调");
//				value.setValue(new BaseErrorMsg("0", "支付成功"));
				value.setValue(returnInfo);
				return value;
			}else{
				value.setValue(new BaseErrorMsg(returnInfo.get("returnMsg")));
				return value;
			}
		} catch (Exception e) {
			log.error("#########系统错误:"+e+"########"+e);
			e.printStackTrace();
			value.setValue(new BaseErrorMsg("支付系统错误"));
			return value;
		}
	}
	
	private Map<String,String> executePay(OrderBo orderBo){
		List<String> listScan = Arrays.asList(new String[]{
				PayCode.PAYCODE_ZFBSMZF.getCode(),
				PayCode.PAYCODE_WXGZHZF.getCode(),
				PayCode.PAYCODE_QQZF.getCode()});//微信公众号和支付宝支付QQ支付支持条码支付
		Map<String,String> map = new HashMap<>();
		//查询商户的支付方式
		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		merchantPaywayCondition.setStatus(ScanCodeConstants.STATUS_ACTIVE);//状态正常
		merchantPaywayCondition.setAcceptStatus(ScanCodeConstants.STATUS_ACTIVE);//入网成功
		merchantPaywayCondition.setRecordStatus(ScanCodeConstants.Y);//入网成功
		merchantPaywayCondition.setMerchantNo(orderBo.getMerchantNo());
		List<MerchantPayway> list = payCallBackOperatorService.findAll(merchantPaywayCondition);
		if(Lists.isEmpty(list)){
			map.put("returnCode", "");
			map.put("returnMsg", "支付方式不存在");
			return map;
		}
		for (MerchantPayway merchantPayway : list) {
			String payCode =  merchantPayway.getPayCode();
			if(!listScan.contains(payCode)){//条码支付针对微信而言扫码支付和条码没有区别，使用一种支付即可
				log.info("this payCode is not support.."+payCode);
				continue;
			}
			orderBo.setPayCode(payCode);
			map = payService.pay(orderBo);//200101
			if("200003".equals(map.get("returnCode"))){//支付失败继续尝试
				continue;
			}else{
				break;
			}
		}
		return map;
	}
	
	private BaseErrorMsg checkParams(ScanPayCondition condition){
		Map<String,String> map = PayCode.toMap();
		if(Strings.isEmpty(condition.getFromH5())){//h5的条码不做支付方式控制
			if(Strings.isEmpty(map.get(condition.getPayCode()))){
				return new BaseErrorMsg("支付方式不存在");
			}
		}
		else if(Strings.isEmpty(condition.getAuthCode())){
			return new BaseErrorMsg("支付授权码为空");
		}
		else if(condition.getOrderAmt()==null||condition.getOrderAmt().compareTo(new BigDecimal("0"))<1){
			return new BaseErrorMsg("支付金额有误");
		}
		return null;
	}
	
}
