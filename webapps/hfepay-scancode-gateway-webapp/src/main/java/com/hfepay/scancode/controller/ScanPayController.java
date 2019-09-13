package com.hfepay.scancode.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.IpUtil;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayService;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.ScanCodeGetWayErrorCode;
import com.hfepay.scancode.commons.bo.OrderBo;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.condition.OrganLimitCondition;
import com.hfepay.scancode.commons.condition.ScanPayCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.PayEntryEnum;
import com.hfepay.scancode.commons.contants.ScanCodeErrorCode;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.OrganLimit;
import com.hfepay.scancode.commons.vo.MerchantCashierVo;
import com.hfepay.scancode.service.operator.MerchantActivityService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.operator.OrganLimitService;
import com.hfepay.scancode.service.order.OrderPaymentService;

import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/scancode")
public class ScanPayController  extends BaseController{
	public static final Logger log = LoggerFactory.getLogger(ScanPayController.class);
	@Autowired
	private PayService payService;
	@Autowired
	private MerchantPaywayService merchantPaywayService;
	@Autowired
	private OrderPaymentService orderPaymentService;
	@Autowired
	private MerchantCashierService merchantCashierService;
	@Autowired
	private MerchantActivityService merchantcashingActive;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private OrganLimitService organLimitService;
	@Autowired
	private MerchantStoreService merchantStoreService;
	
	/**
	 * 商户扫码收款
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping("/sweepcollection")
	public String sweepCollection(ModelMap model,HttpServletRequest request,ScanPayCondition condition){
		Map<String,String> map = PayCode.toMap();
		Map<String,String> paywayMap = new HashMap<>();
		Map<String,String> returnInfo = new HashMap<String,String>();
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		
		//查询商户的支付方式
		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		merchantPaywayCondition.setStatus("1");//状态正常
		merchantPaywayCondition.setAcceptStatus("1");//入网成功
		merchantPaywayCondition.setRecordStatus(Constants.Y);
		merchantPaywayCondition.setMerchantNo(admin.getMerchantNo());
		List<MerchantPayway> list = merchantPaywayService.findAll(merchantPaywayCondition);
		
		if(Strings.isNotEmpty(admin.getMerchantNo())){
			MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(admin.getMerchantNo());
			if(!Strings.equals(merchantInfo.getStatus(), MerchantStatus.MERCHANT_STATUS_3.getCode())){
				returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
				returnInfo.put("returnMsg","商户平台审核未通过");
			}
			if(list.isEmpty()){
				returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
				returnInfo.put("returnMsg","商户信息不完整");
			}
			//request.setAttribute("name", merchantInfo.getMerchantName());
		}else{
			returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
			returnInfo.put("returnMsg","商户编号不能为空");
			
		}
		if(!returnInfo.isEmpty()){
			model.addAttribute("returnInfo", returnInfo);
			return "scancode/paymentResult";
		}
		
		String storeName = "";
		if(IdentityType.Identity_Merchant.getCode().equals(admin.getIdentityFlag())){
			MerchantStoreCondition storeCondition = new MerchantStoreCondition();
			storeCondition.setMerchantNo(admin.getMerchantNo());
			storeCondition.setStoreType("0");//总店
			storeCondition.setRecordStatus("Y");//有效的
			MerchantStore store = merchantStoreService.findByCondition(storeCondition);
			if(null==store){
				returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
				returnInfo.put("returnMsg","商户不存在门店，不能收款");
				model.addAttribute("returnInfo", returnInfo);
				return "scancode/paymentResult";
			}
			storeName=store.getStoreName();
		}
		else{
			MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
			merchantCashierCondition.setCashierNo(admin.getIdentityNo());
			merchantCashierCondition.setRecordStatus("Y");//有效的
			MerchantCashierVo vo = (MerchantCashierVo)merchantCashierService.findByCondition(merchantCashierCondition);
			if(null==vo||Strings.isEmpty(vo.getStoreName())){
				returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
				returnInfo.put("returnMsg","收银员没绑定门店，不能收款");
				model.addAttribute("returnInfo", returnInfo);
				return "scancode/paymentResult";
			}
			storeName = vo.getStoreName();
		}
		
		
		for (MerchantPayway merchantPayway : list) {
			String payCode =  merchantPayway.getPayCode();
			paywayMap.put(payCode, map.get(payCode));//商户配置的可用的支付方式
		}
		request.setAttribute("storeName", storeName);
		request.setAttribute("payWays", paywayMap);
		return "scancode/wechat/sweepCollection"; 
	}
	
	/**
	 * 生成付款二维码
	 * @param request
	 * @param response
	 * @return String
	 */
	@RequestMapping("/sweeppay")
	public String sweepPay(ModelMap model,HttpServletRequest request,ScanPayCondition condition){
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		log.info("扫码收款用户信息:"+JSONSerializer.toJSON(admin));
		Map<String,String> returnInfo = new HashMap<String,String>();
		String ip = IpUtil.getIpAddr(request);
		OrderBo orderBo = new OrderBo();
		BigDecimal payCash = condition.getOrderAmt();//支付金额
		BigDecimal discountCash = BigDecimal.ZERO;//优惠金额
		BigDecimal actualPayCash = BigDecimal.ZERO;//实际支付金额
		
		BigDecimal minLimit = new BigDecimal(request.getParameter("minLimit"));//最低限额
		log.info("扫码收款最低限额:"+minLimit);		
		try {
			
			if(!("4".equals(admin.getIdentityFlag()) || "3".equals(admin.getIdentityFlag()))){
				returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
				returnInfo.put("returnMsg","该角色不支持扫码收款");
				model.addAttribute("returnInfo", returnInfo);
				return "scancode/paymentResult";
			}
			
			Map<String,BigDecimal> map = merchantcashingActive.calculateCheapCash(admin.getMerchantNo(), condition.getOrderAmt(),minLimit);
			if(map==null||map.isEmpty()){
				actualPayCash = payCash;
			}else{
				discountCash = map.get("discountCash");
				actualPayCash = map.get("actualPayCash");
			}
			log.info("支付金额:"+payCash+"，优惠金额" + discountCash +"，实际支付金额" + actualPayCash);
			
			//接收参数
			log.info("########pay:"+condition+"########");
			orderBo.setChannelNo(admin.getChannelCode());
			orderBo.setAgentNo(admin.getAgentNo());
			orderBo.setMerchantNo(admin.getMerchantNo());
			orderBo.setCashier(admin.getIdentityNo());
			orderBo.setIp(ip);
			
			//如果说收银员则获取门店信息传入
			if("4".equals(admin.getIdentityFlag())){//收银员是否被禁用
				MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
				merchantCashierCondition.setRecordStatus("Y");
				merchantCashierCondition.setOpenId(admin.getOpenId());
				MerchantCashier cashier = merchantCashierService.findByCondition(merchantCashierCondition);
				if(cashier==null){
					returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
					returnInfo.put("returnMsg","收银员被删除,不能执行该操作");
					model.addAttribute("returnInfo", returnInfo);
					return "scancode/paymentResult";
				}
				if(Strings.isEmpty(cashier.getStatus())||"2".equals(cashier.getStatus())){
					returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
					returnInfo.put("returnMsg","收银员状态无效,不能执行该操作");
					model.addAttribute("returnInfo", returnInfo);
					return "scancode/paymentResult";
				}
				if(Strings.isEmpty(cashier.getStoreNo())){
					returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
					returnInfo.put("returnMsg","收银员门店尚未绑定,不能执行该操作");
					model.addAttribute("returnInfo", returnInfo);
					return "scancode/paymentResult";
				}
				orderBo.setStoreNo(cashier.getStoreNo());
			}else if("3".equals(admin.getIdentityFlag())){//商户的情况，获取默认主门店
				MerchantInfo info = merchantInfoService.findByMerchantNo(admin.getMerchantNo());
				if(!"3".equals(info.getStatus())){
					returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
					returnInfo.put("returnMsg","商户未审核通过不能执行该操作");
					model.addAttribute("returnInfo", returnInfo);
					return "scancode/paymentResult";
				}
				MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
				merchantStoreCondition.setMerchantNo(admin.getMerchantNo());
				merchantStoreCondition.setStoreType("0");
				merchantStoreCondition.setRecordStatus("Y");
				MerchantStore store = merchantStoreService.findByCondition(merchantStoreCondition);
				if(null==store){
					returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
					returnInfo.put("returnMsg","商户不存在主门店不能执行该操作");
					model.addAttribute("returnInfo", returnInfo);
					return "scancode/paymentResult";
				}
				orderBo.setStoreNo(store.getStoreNo());
			}
			
			//调用支付逻辑
			orderBo.setQrCode(null);
			orderBo.setPayCode(condition.getPayCode());
			orderBo.setPrice(actualPayCash);
			orderBo.setTradeAmt(payCash);
			orderBo.setDiscountAmt(discountCash);
			orderBo.setDiscountStrategy("");
			orderBo.setPayStrategy(condition.getPayCode()+"_"+PayEntryEnum.AMTCODE_PAY.getEntryCode());//支付策略
			returnInfo = payService.pay(orderBo);
			if (!ScanCodeErrorCode.SYSTEM_000000.getCode().equals(returnInfo.get("returnCode"))) {
				model.addAttribute("returnInfo", returnInfo);
				return "scancode/paymentResult";
			}
			
			
		} catch (Exception e) {
			log.error("#########系统错误:"+e+"########"+e);
			returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
			returnInfo.put("returnMsg",e.getMessage());
			model.addAttribute("returnInfo", returnInfo);
			return "scancode/paymentResult";
		}
		request.setAttribute("payCode", condition.getPayCode());
		request.setAttribute("payCash", payCash);
		request.setAttribute("discountCash", discountCash);
		request.setAttribute("actualPayCash", actualPayCash);
		request.setAttribute("returnInfo", returnInfo);
		return "scancode/wechat/sweepPay"; 
	}
	
	/**
	 * 获取订单状态
	 * @param request
	 * @param response
	 * @return OrderPayment
	 */
	@RequestMapping("/payresult")
	@ResponseBody
	public OrderPayment payResult(ModelMap model,HttpServletRequest request){
		OrderPaymentCondition orderPaymentCondition = new OrderPaymentCondition();
		orderPaymentCondition.setTradeNo(request.getParameter("tradeNo"));
		List<OrderPayment> list = orderPaymentService.findAll(orderPaymentCondition);
		if(list.size() == 1){
			return list.get(0);
		}
		return null;
	}
	
	/** 获得机构限额 */
	@RequestMapping(value = "/getOrganLimit", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject getOrganLimit(HttpServletRequest request,OrganLimitCondition organLimitCondition){	
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		String merchantNo = request.getParameter("merchantNo");//若页面传了商户编号则查该商户的机构限额
		if(Strings.isEmpty(merchantNo)){
			merchantNo = user.getMerchantNo();
		}
		JSONObject json = new JSONObject();
        json.put(EXECUTE_STATUS, SUCCESS);
		try {
			OrganLimit organLimit = organLimitService.getOrganLimit(organLimitCondition,merchantNo);
			json.put("organLimit", organLimit);
		} catch (Exception e) {
			json.put(EXECUTE_STATUS, FAILED);
        	json.put(VALUES, e.getMessage());
		}        
		return json;
	}
}
