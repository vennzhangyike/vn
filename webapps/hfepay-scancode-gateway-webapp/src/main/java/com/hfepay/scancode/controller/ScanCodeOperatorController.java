package com.hfepay.scancode.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.HttpRequestClient;
import com.hfepay.scancode.api.condition.ApiChannelWxParamsCondition;
import com.hfepay.scancode.api.condition.ApiMappingDicionCondition;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.ApiMappingDicion;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.api.service.ApiMappingDicionService;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.ParamsInfoService;

import net.sf.json.JSONObject;

/**
 * @ClassName: ScanCodeOperatorController
 * @Description: 扫码的之后的动作：未被注册：注册，已被注册：付款
 * @author: husain
 * @date: 2016年10月24日 下午3:02:13
 */
@Controller
@RequestMapping("/scan")
public class ScanCodeOperatorController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
//	@Autowired
//	private ScanCodeService scanCodeService;
//	
//	@Autowired
//	private PlatformQrcodeService platformQrcodeService;
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private ApiMappingDicionService apiMappingDicionService;
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private ParamsInfoService paramsInfoService;
	@Autowired
	private AgentBaseService agentBaseService;
	
	/*@RequestMapping("/operate")
	public String apply2(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		logger.info("in operate........................");
		String browerType = request.getHeader("User-Agent").toLowerCase();
		//else if(browerType.toLowerCase().indexOf("alipayclient")!=-1){//支付宝
		String qrCode = request.getParameter("qrCode");
		if(Strings.isEmpty(qrCode)){//参数为空
			request.setAttribute("error", "二维码不存在");
			return "scancode/wechat/scan_error";
		}
		
		PlatformQrcode code = platformQrcodeService.findByQrcode(qrCode);
		if(null==code||null==code.getUseStatus()){//码不存在
			logger.info("##########qrCode["+qrCode+"]非平台二维码##########");
			//判定商户子码是否存在
			MerchantQrcode merchantQrcode = scanCodeService.findByMerchantQrCode(qrCode);
			if (null == merchantQrcode) {
				logger.info("##########qrCode["+qrCode+"]非商户二维码##########");
				request.setAttribute("error", "二维码不存在或状态有问题");
				return "scancode/wechat/scan_error";
			}else {
				request.setAttribute("qrCode", qrCode);
				MerchantInfo info = merchantInfoService.findByMerchantNo(merchantQrcode.getMerchantNo());
				if("3".equals(info.getStatus())){//审核通过支付
					request.setAttribute("name", info.getMerchantName());
					if(browerType.toLowerCase().indexOf("micromessenger")!= -1){//判断浏览器类型
						logger.info("#######browerType:WXZF#######");
						request.setAttribute("browerType", "WXZF");
					}
					request.setAttribute("merchantNo", info.getMerchantNo());
					return "scancode/payment";
				}
				else if("5".equals(info.getStatus())){
					request.setAttribute("error", "商户已被停用");
					return "scancode/wechat/scan_error";
				}
				else{//审核不过，检查是注册流程未完成的用户还是其他用户，其他用户不能支付，未审核完成的用户到个人中心
					request.setAttribute("error", "商户尚未审核通过或者被禁用，请联系管理员");
					return "scancode/wechat/scan_error";
					return "redirect:/scan/authUnpass?qrCode="+qrCode+"&organNo="+code.getAgentNo();
				}
			}
			
		}else{
			if (code.getQrStatus().equals(QrStatus.QR_2.getCode())) {
				request.setAttribute("error", "该二维码已失效");
				return "scancode/wechat/scan_error";
			}
			
			//使用状态是否为1
			String status = code.getUseStatus();
			logger.info("in operate........................status="+status);
			if(status.equals(UseStatus.UNUSE.value())){//未使用相当于注册操作
				if(browerType.toLowerCase().indexOf("micromessenger")==-1){//注册只能微信浏览器进入
					request.setAttribute("error", "新用户入驻必须使用微信扫描二维码.");
					return "scancode/wechat/scan_error";
				}
				return "redirect:/scan/authNew?qrCode="+qrCode+"&organNo="+code.getAgentNo();
			}else if(status.equals(UseStatus.USED.value())){//支付页面
				request.setAttribute("qrCode", qrCode);
				MerchantInfo info = merchantInfoService.findByMerchantNo(code.getMerchantNo());
				if("3".equals(info.getStatus())){//审核通过支付
					request.setAttribute("name", info.getMerchantName());
					if(browerType.toLowerCase().indexOf("micromessenger")!= -1){//判断浏览器类型
						logger.info("#######browerType:WXZF#######");
						request.setAttribute("browerType", "WXZF");
					}
					request.setAttribute("merchantNo", info.getMerchantNo());
					return "scancode/payment";
				}
				else if("5".equals(info.getStatus())){
					request.setAttribute("error", "商户已被停用");
					return "scancode/wechat/scan_error";
				}
				else{//审核不过，检查是注册流程未完成的用户还是其他用户，其他用户不能支付，未审核完成的用户到个人中心
					request.setAttribute("error", "商户尚未审核通过或者被禁用，请联系管理员");
					return "scancode/wechat/scan_error";
					return "redirect:/scan/authUnpass?qrCode="+qrCode+"&organNo="+code.getAgentNo();
				}
			}
		}
		
		return null;
	}
	*/
	
	
	
	//静默授权新注册的用户授权回调信息
	@RequestMapping(value = "/authNew")
	private void handleAuth(HttpServletResponse response,HttpServletRequest request){
		try {
			String organNo = request.getParameter("organNo");
			logger.info("handleAuth authNew organNo is "+organNo);
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(organNo);
			ApiChannelWxParams params = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			logger.info("==========参数配置为："+params.getWxParams());
			JSONObject json = JSONObject.fromObject(params.getWxParams());
			String qrCode = request.getParameter("qrCode");
			
			ChannelExpand channelExpand = channelExpandService.findByChannelNo(organNo);
			AgentBase agentBase = null;
			String organNoChannel = "";
			if (null == channelExpand) {
				agentBase = agentBaseService.findByAgentNo(organNo);
				organNoChannel = agentBase.getChannelNo();
			}else {
				organNoChannel = channelExpand.getChannelNo();
			}
			JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(organNoChannel);
			logger.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
			
//			String backUrl = URLEncoder.encode(wechatConfig.getBackNewUrl(), "UTF-8");
			String backUrl = URLEncoder.encode(paramsInfoJson.getString("backNewUrl"), "UTF-8");
			
			ApiMappingDicionCondition apiCondition = new ApiMappingDicionCondition();
			apiCondition.setKeyNo(HfepayConfig.WECHAT_BACK_GO_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(apiCondition);
			String backgo=dic.getParaVal();
			backgo = backgo.replace("REDIRECTURL", backUrl);
			backgo = backgo.replace("APPID", json.getString("appid"));
			backgo = backgo.replace("ORDERID", qrCode+","+organNo);
			response.sendRedirect(backgo);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("handleAuth error ..."+e);
		}
	}
	
	//静默授权为没审核通的用户
	@RequestMapping(value = "/authUnpass")
	private void handleAuthUnpass(HttpServletResponse response,HttpServletRequest request){
		try {
			String organNo = request.getParameter("organNo");
			logger.info("handleAuth authUnpass organNo is "+organNo);
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(organNo);
			ApiChannelWxParams params = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			logger.info("==========authUnpass 参数配置为："+params.getWxParams());
			JSONObject json = JSONObject.fromObject(params.getWxParams());
			String qrCode = request.getParameter("qrCode");
			
			ChannelExpand channelExpand = channelExpandService.findByChannelNo(organNo);
			AgentBase agentBase = null;
			String organNoChannel = "";
			if (null == channelExpand) {
				agentBase = agentBaseService.findByAgentNo(organNo);
				organNoChannel = agentBase.getChannelNo();
			}else {
				organNoChannel = channelExpand.getChannelNo();
			}
			JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(organNoChannel);
			logger.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
			
//			String backUrl = URLEncoder.encode(wechatConfig.getBackUnPassUrl(), "UTF-8");
			String backUrl = URLEncoder.encode(paramsInfoJson.getString("backUnPassUrl"), "UTF-8");
			
			ApiMappingDicionCondition apiCondition = new ApiMappingDicionCondition();
			apiCondition.setKeyNo(HfepayConfig.WECHAT_BACK_GO_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(apiCondition);
			String backgo=dic.getParaVal();
			backgo = backgo.replace("REDIRECTURL", backUrl);
			backgo = backgo.replace("APPID", json.getString("appid"));
			backgo = backgo.replace("ORDERID", qrCode+","+organNo);
			response.sendRedirect(backgo);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("handleAuthUnpass error ..."+e);
		}
	}
	
	//审核未通过的回调,此时二维码已被使用过
	@RequestMapping(value = "/callbackUnpass")
	public String callbackUnpass(HttpServletRequest request){
		String state = request.getParameter("state");
		String code = request.getParameter("code");
		logger.debug("==========callbackUnpass code:"+code+",state:"+state+"==========");
		logger.info("in callback........................");
		
		String stateParam[] = state.split(",");
		String qrCode = stateParam[0];
		String organNo = stateParam[1];
		
		try {
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(organNo);
			ApiChannelWxParams params = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			logger.info("==========参数配置为："+params.getWxParams());
			JSONObject json = JSONObject.fromObject(params.getWxParams());
			
			ApiMappingDicionCondition condition = new ApiMappingDicionCondition();
			condition.setKeyNo(HfepayConfig.WAUTHOR_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(condition);
			logger.info("==========WAUTHOR_KEY 参数配置为："+dic.getParaVal());
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("appid", json.getString("appid"));
			sParaTemp.put("secret", json.getString("secret"));
			sParaTemp.put("code", code);
			sParaTemp.put("grant_type", "authorization_code");
			String resultJson =HttpRequestClient.invoke_http(dic.getParaVal(), generatNameValuePair(sParaTemp), "utf-8");
			JSONObject result= JSONObject.fromObject(resultJson);
			logger.debug("callbackUnpass 微信获取openId返回json："+resultJson);
			logger.debug(resultJson);
			String openId = result.getString("openid");
			//System.out.println(openId);
			logger.info("callbackUnpass in callback........................openId="+openId);
			//根据openid判断用户是已经注册
			ChannelAdmin admin = findByOpenId(openId);
			if(admin!=null){//已注册
				MerchantInfo info = merchantInfoService.findByMerchantNo(admin.getMerchantNo());
				if(!info.getQrCode().equals(qrCode)){//判断是指扫码还是别人扫码，此处别人扫码
					logger.info("callbackUnpass 二维码已使用，但是商户没有审核通过,是别的商户扫描未通过的商户码==========");
					request.setAttribute("error", "商户尚未审核通过，不能支付");
					return "scancode/wechat/scan_error";
				}
				else{//是自己扫码，直接到个人中心
					logger.info("callbackUnpass 是自己扫码，直接到个人中心==========");
					request.getSession().setAttribute(Constants.CURRENTUSER,admin);
					return "redirect:/scancode/my";
				}
			}
			else{//未注册，说明是新用户直接扫码支付
				logger.info("未注册，说明是新用户直接扫码支付==========");
				request.setAttribute("error", "商户尚未审核通过，不能支付");
				return "scancode/wechat/scan_error";
			}
		} catch (Exception e) {
			logger.error("获取openId异常", e);
			request.setAttribute("error", "授权公众号失败");
			return "scancode/wechat/scan_error";
		}
	}
	
	
	/**
	* @Description 二维码没有被使用过回调
	* @param
	* @author husain 
	* @Date 2016年10月7日 下午7:32:56
	 */
	@RequestMapping(value = "/callbackNew")
	public String callbackNew(HttpServletRequest request){
		String state = request.getParameter("state");
		String code = request.getParameter("code");
		logger.debug("==========callbackUnpass code:"+code+",state:"+state+"==========");
		logger.info("in callback........................");
		
		String stateParam[] = state.split(",");
		String qrCode = stateParam[0];
		String organNo = stateParam[1];
		
		logger.debug("==========callbackNew code:"+code+",qrCode:"+qrCode+"==========");
		logger.info("callbackNew in callback........................");
		try {
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(organNo);
			ApiChannelWxParams params = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			logger.info("==========参数配置为："+params.getWxParams());
			JSONObject json = JSONObject.fromObject(params.getWxParams());
			
			ApiMappingDicionCondition condition = new ApiMappingDicionCondition();
			condition.setKeyNo(HfepayConfig.WAUTHOR_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(condition);
			logger.info("==========WAUTHOR_KEY 参数配置为："+dic.getParaVal());
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("appid", json.getString("appid"));
			sParaTemp.put("secret", json.getString("secret"));
			sParaTemp.put("code", code);
			sParaTemp.put("grant_type", "authorization_code");
			
			String resultJson =HttpRequestClient.invoke_http(dic.getParaVal(), generatNameValuePair(sParaTemp), "utf-8");
			JSONObject result= JSONObject.fromObject(resultJson);
			logger.debug("callbackNew 微信获取openId返回json："+resultJson);
			logger.debug(resultJson);
			String openId = result.getString("openid");
			//System.out.println(openId);
			logger.info("callbackNew in callback........................openId="+openId);
			//当前用户是否是未完成注册，是否已经注册过
			ChannelAdmin admin = findByOpenId(openId);
			if(admin!=null){//已经注册过不能再次注册
				logger.info("callback else==========openid="+openId+" merchantNo="+admin.getMerchantNo());
				DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(admin.getAgentNo());
				ChannelExpand expend = channelExpandService.findByChannelNo(vo.getOrganNo());
				request.setAttribute("img", expend.getIndexWxImg());
				request.setAttribute("error", "您已扫码注册成功,请识别关注此公众号,并尽快完善您的资料");
				return "scancode/wechat/scan_error";
			}else{//新用户直接注册
				logger.info("callback 新用户直接注册==========");
				logger.info("in callbackNew createMerchant start........................openId="+openId);
				long resultCreate = channelAdminService.createMerchantByQrCode(qrCode,openId);
				logger.info("in callbackNew createMerchant end........................openId="+openId+"  result="+result);
				if(resultCreate>0){
					ChannelAdmin adminRE = findByOpenId(openId);
					request.getSession().setAttribute(Constants.CURRENTUSER,adminRE);
				}
				return "redirect:/scancode/my";
				
			}
		} catch (Exception e) {
			logger.error("获取openId异常", e);
			request.setAttribute("error", "授权公众号失败");
			return "scancode/wechat/scan_error";
		}
	}
	
	/**获取openId之后,添加一个商户信息，和一个登录账号*/
	@RequestMapping("/createMerchant")
	public String createMerchant(ModelMap model,HttpServletRequest request){
		String qrCode = request.getParameter("qrCode");
		String openId = request.getParameter("openId");
		logger.info("in createMerchant start........................openId="+openId);
		long result = channelAdminService.createMerchantByQrCode(qrCode,openId);
		logger.info("in createMerchant end........................openId="+openId+"  result="+result);
		//将登录信息放入session
		if(result>0){
			ChannelAdmin admin = findByOpenId(openId);
			request.getSession().setAttribute(Constants.CURRENTUSER,admin);
		}
		return "redirect:/scancode/my";
	}
	
	private static List<NameValuePair> generatNameValuePair(Map<String, String> properties) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
        	NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(),entry.getValue());
        	list.add(nameValuePair);
        }
        return list;
    }
	
	/***根据openId检测用户是否注册过**/
	private ChannelAdmin findByOpenId(String openId){
		ChannelAdminCondition condition = new ChannelAdminCondition();
		condition.setOpenId(openId);
		condition.setIdentityFlag(IdentityType.Identity_Merchant.getCode());
		ChannelAdmin admin = channelAdminService.findByCondition(condition);
		return admin;
	}
}
