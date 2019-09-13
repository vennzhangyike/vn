package com.hfepay.scancode.controller;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.pay.service.PayCallBackOperatorService;
import com.hfepay.pay.utils.IpUtil.GetCityByIpUtil;
import com.hfepay.pay.utils.IpUtil.LocationInfo;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.ScanCodeGetWayErrorCode;
import com.hfepay.scancode.commons.bo.AdviertismentBo;
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.OrganLimitCondition;
import com.hfepay.scancode.commons.contants.AdviertismentType;
import com.hfepay.scancode.commons.contants.QrStatus;
import com.hfepay.scancode.commons.contants.UseStatus;
import com.hfepay.scancode.commons.entity.AdviertisementInfo;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantQrcode;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.commons.entity.OrganLimit;
import com.hfepay.scancode.commons.entity.PlatformQrcode;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * @ClassName: ScanCodeOperatorController
 * @Description: 扫码的之后的动作：未被注册：注册，已被注册：付款
 * @author: husain
 * @date: 2016年10月24日 下午3:02:13
 */
@Controller
@RequestMapping
public class ScanCodeOperatorController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private PayScanCodeService payScanCodeService;
	@Autowired
	private PayCallBackOperatorService payCallBackOperatorService;
	
	/***二维码扫描，判断是否可以支付，不能支付的话直接跳转到h5执行注册完善资料等操作**/
	@RequestMapping("/scan/operate")
	public String apply2(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		logger.info("in operate........................");
		String browerType = request.getHeader("User-Agent").toLowerCase();
		String qrCode = request.getParameter("qrCode");
		String payCode = "";
		if(Strings.isEmpty(qrCode)){//参数为空
			request.setAttribute("error", "二维码不存在");
			return "scancode/scan_error";
		}
		
		//支付方式识别
		if (browerType.toLowerCase().indexOf("micromessenger")!=-1) {	//微信
			payCode = Constants.WXGZHZF;
		}else if(browerType.toLowerCase().indexOf("alipayclient")!=-1)  {//支付宝
			payCode = Constants.ZFBSMZF;
		}else if(browerType.toLowerCase().indexOf("qq")!=-1)  {//qq支付
			payCode = Constants.QQZF;
		}else {
			request.setAttribute("error", "暂不支持的扫码方式");
			return "scancode/scan_error";
		}
		logger.info("=========payCode：" + payCode+"=========");
		
		PlatformQrcode code = payCallBackOperatorService.findByQrcode(qrCode);
		if(null==code||null==code.getUseStatus()){//码不存在
			logger.info("##########qrCode["+qrCode+"]非平台二维码##########");
			//判定商户子码是否存在
			MerchantQrcode merchantQrcode = payCallBackOperatorService.findByMerchantQrCode(qrCode);
			if (null == merchantQrcode) {
				logger.info("##########qrCode["+qrCode+"]非商户二维码##########");
				request.setAttribute("error", "二维码不存在或状态有问题");
				return "scancode/scan_error";
			}else {//收银员收款商户子二维码收款
				request.setAttribute("qrCode", qrCode);
				MerchantInfo info = payCallBackOperatorService.findByMerchantNo(merchantQrcode.getMerchantNo());
				String identityNo = info.getMerchantNo();
				MerchantCashierQr qr = getMerchantCashierQrByQrcode(qrCode);
				if(qr!=null){//二维码没有绑定收银员,那么默认商户收银
					identityNo=qr.getCashierNo();
				}
				//门店以二维码为准，即使二维码无收银员
				if("3".equals(info.getStatus())){//审核通过支付,
					request.setAttribute("storeName", merchantQrcode.getStoreName());//显示门店
					request.setAttribute("storeNo", merchantQrcode.getStoreId());//门店编号
//					request.setAttribute("browerType", "WXZF");
					request.setAttribute("payCode", payCode);
					request.setAttribute("merchantNo", info.getMerchantNo());//
					request.setAttribute("identityNo", identityNo);//二维码没有绑定收银员,那么默认商户收银,收银员identityNo
					return "scancode/payment";
				}else if("5".equals(info.getStatus())){
					request.setAttribute("error", "商户已被停用");
					return "scancode/scan_error";
				}else{//审核不过，检查是注册流程未完成的用户还是其他用户，其他用户不能支付，未审核完成的用户到个人中心
					JSONObject paramsInfoJson = payCallBackOperatorService.getParamsInfoByDomain(info.getChannelNo());
					logger.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
					String redirecUrl = paramsInfoJson.getString("authUnpass");
					return "redirect:"+redirecUrl+"?qrCode="+qrCode+"&organNo="+code.getAgentNo();
				}
			}
			
		}else{//商户支付
			if (code.getQrStatus().equals(QrStatus.QR_2.getCode())) {
				request.setAttribute("error", "该二维码已失效");
				return "scancode/scan_error";
			}
			
			//使用状态是否为1
			String status = code.getUseStatus();
			logger.info("in operate........................status="+status);
			if(status.equals(UseStatus.UNUSE.value())){//未使用相当于注册操作
				if(browerType.toLowerCase().indexOf("micromessenger")==-1){//注册只能微信浏览器进入
					request.setAttribute("error", "新用户入驻必须使用微信扫描二维码.");
					return "scancode/scan_error";
				}
				JSONObject paramsInfoJson = payCallBackOperatorService.getParamsInfoByDomain(code.getChannelNo());
				logger.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
				String redirecUrl = paramsInfoJson.getString("authNew");
				return "redirect:"+redirecUrl+"?qrCode="+qrCode+"&organNo="+code.getAgentNo();
			}else if(status.equals(UseStatus.USED.value())){//支付页面
				request.setAttribute("qrCode", qrCode);
				MerchantInfo info = payCallBackOperatorService.findByMerchantNo(code.getMerchantNo());
				if("3".equals(info.getStatus())){//审核通过支付
					MerchantStoreCondition storeCondition = new MerchantStoreCondition();
					storeCondition.setMerchantNo(code.getMerchantNo());
					storeCondition.setStoreType("0");//总店
					storeCondition.setRecordStatus("Y");//有效的
					MerchantStore store = payCallBackOperatorService.findByCondition(storeCondition);
					if(null==store){
						request.setAttribute("error", "商户门店为空不能支付");
						return "scancode/scan_error";
					}
					
					request.setAttribute("storeName", store.getStoreName());
					request.setAttribute("storeNo", store.getStoreNo());//门店编号
//					request.setAttribute("browerType", "WXZF");
					request.setAttribute("payCode", payCode);
					request.setAttribute("merchantNo", info.getMerchantNo());
					request.setAttribute("identityNo", info.getMerchantNo());
					return "scancode/payment";
				}else if("5".equals(info.getStatus())){
					request.setAttribute("error", "商户已被停用");
					return "scancode/scan_error";
				}else{//审核不过，检查是注册流程未完成的用户还是其他用户，其他用户不能支付，未审核完成的用户到个人中心
					JSONObject paramsInfoJson = payCallBackOperatorService.getParamsInfoByDomain(info.getChannelNo());
					logger.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
					String redirecUrl = paramsInfoJson.getString("authUnpass");
					return "redirect:"+redirecUrl+"?qrCode="+qrCode+"&organNo="+code.getAgentNo();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 商户收银活动优惠
	 * @param request
	 * @param response
	 * @return returnInfo
	 */
	@RequestMapping("/scan/merchantcashingActive")
	@ResponseBody
	public Map<String,BigDecimal> merchantcashingActive(HttpServletRequest request,OrderPayment pay){
		Map<String,BigDecimal> map = payCallBackOperatorService.calculateCheapCash(pay.getMerchantNo(), pay.getOrderAmt(),BigDecimal.ZERO);
		if(map==null||map.isEmpty()){
			return null;
		}
		return map;
	}
	
	@RequestMapping("/scan/paymentResult")
	public String paymentpage(ModelMap model,HttpServletRequest request){
		Map<String,String> returnInfo = new HashMap<String,String>();
		List<AdviertisementInfo> list = null;
		
		String tradeNo = request.getParameter("tradeNo");
		logger.info("########tradeNo:"+tradeNo+"########");
		
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		} 
		OrderPayment orderPayment = payCallBackOperatorService.findOrderPaymentByTradeNo(tradeNo);
		
//		if (orderPayment.getOrderStatus().equals(OrderStatus.ORDER_SUCCESS.getCode())) {			
		returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_000000.getCode());
		returnInfo.put("returnMsg",ScanCodeGetWayErrorCode.SYSTEM_000000.getDesc());
//		}else {
//			returnInfo.put("returnCode",ScanCodeGetWayErrorCode.SYSTEM_999999.getCode());
//			returnInfo.put("returnDesc",ScanCodeGetWayErrorCode.SYSTEM_999999.getDesc());
//		}
		try {
			//获取广告信息
			LocationInfo locationInfo = GetCityByIpUtil.getCityInfoByIP(orderPayment.getTemp1());
			if (null != locationInfo) {
				logger.info("########locationInfo:"+locationInfo+",state:"+locationInfo.state+",city:"+locationInfo.city+"########");
				AdviertismentBo adviertismentBo = new AdviertismentBo();
				adviertismentBo.setAdviertPosition(AdviertismentType.PAY_BANNEL.getCode());
				adviertismentBo.setMerchantNo(orderPayment.getMerchantNo());
				adviertismentBo.setPname(locationInfo.state);
				
				adviertismentBo.setCity(locationInfo.city);
				list = payCallBackOperatorService.getAdviertisInfoByUser(adviertismentBo);
			}
			if (null != list && list.size() > 0) {			
				request.setAttribute("adviertisementImg",list.get(0).getImgUrl());
				request.setAttribute("adviertisementUrl",list.get(0).getAdviertLink());
			}else {
				request.setAttribute("adviertisementImg","");
				request.setAttribute("adviertisementUrl","");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			//打折信息
			returnInfo.put("orderAmt", String.valueOf(orderPayment.getOrderAmt()));
			returnInfo.put("discountAmt", String.valueOf(orderPayment.getDiscountAmt()));
			returnInfo.put("tradeAmt", String.valueOf(orderPayment.getTradeAmt()));
			returnInfo.put("tradeTime",  Dates.format(Dates.DF.yyyy_MM_dd_HH_mm_ss, orderPayment.getBeginTime()));
			returnInfo.put("tradeNo", orderPayment.getTradeNo());
			returnInfo.put("tradeStatus", orderPayment.getOrderStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		model.addAttribute("returnInfo", returnInfo);
		return "scancode/paymentResult";
	}
	
	@RequestMapping("/agentScan/operate")
	public String paymentpage(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		String agentNo = request.getParameter("agentNo");
		logger.info("###########代理商推广注册agentNo["+agentNo+"]###########");
		
		String browerType = request.getHeader("User-Agent").toLowerCase();
		if(browerType.toLowerCase().indexOf("micromessenger")==-1){//注册只能微信浏览器进入
			request.setAttribute("error", "必须使用微信扫描二维码");
			logger.error("###########代理商推广注册agentNo["+agentNo+"],新用户入驻必须使用微信扫描二维码###########");
			return "scancode/wechat/scan_error";
		}
		//判定代理商是否存在
		AgentBase agentBase = payCallBackOperatorService.findByAgentNo(agentNo);
		if (null == agentBase) {
			request.setAttribute("error", "代理商不存在");
			logger.error("###########代理商推广注册agentNo["+agentNo+"],代理商不存在###########");
			return "scancode/wechat/scan_error";
		}
				
		JSONObject paramsInfoJson = payCallBackOperatorService.getParamsInfoByDomain(agentBase.getChannelNo());
		logger.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
		String redirecUrl = paramsInfoJson.getString("hfepayAgentScanRedirect");
		return "redirect:"+redirecUrl+"?agentNo="+agentNo;
	}
	
	/**
	 * 获得机构限额
	 * @param request
	 * @param organLimitCondition
	 * @return
	 */
	@RequestMapping(value = "/scan/getOrganLimit", method= {RequestMethod.POST})
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
			OrganLimit organLimit = payCallBackOperatorService.getOrganLimit(organLimitCondition,merchantNo);
			logger.info("organLimit：" + JSONSerializer.toJSON(organLimit));
			//将对象转为JSON字符串
			String organLimitJson = com.alibaba.fastjson.JSON.toJSONString(organLimit,filter);
			//将json字符串转为map
			Map<String, Object> organLimitMap = com.alibaba.fastjson.JSON.parseObject(organLimitJson, Map.class);
			json.put("organLimit", organLimitMap);
		} catch (Exception e) {
			json.put(EXECUTE_STATUS, FAILED);
        	json.put(VALUES, e.getMessage());
		}        
		return json;
	}
	
	private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null)
                return "";
            return v;
        }
    };
    
    /**
     * 根据二维码查询出正在收银的收银员编号
     * @param qrCode
     * @return
     */
    private MerchantCashierQr getMerchantCashierQrByQrcode(String qrCode){
    	MerchantCashierQrCondition condition = new MerchantCashierQrCondition();
    	condition.setQrCode(qrCode);
    	condition.setRecordStatus("Y");
    	condition.setIsCashier("2");
    	condition.setStatus("1");
    	List<MerchantCashierQr> qrlist = payCallBackOperatorService.findAll(condition);
    	if(Lists.isEmpty(qrlist)){
    		logger.info("######################qrCode="+qrCode+" 商户子二维码没有绑定收银员....");
    		return null;
    	}
    	if(qrlist.size()>1){
    		throw new RuntimeException("收银状态的二维码不止一个");
    	}
    	return qrlist.get(0);
    }
}
