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
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.QrStatus;
import com.hfepay.scancode.commons.contants.UseStatus;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.ParamsInfoService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;

import net.sf.json.JSONObject;

/**
 * 扫码支付、商户入驻页面
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/agentScan")
public class ScanCodeAgentPromotionController {
	
	public static final Logger log = LoggerFactory.getLogger(ScanCodeAgentPromotionController.class);
	
	@Autowired
	private AgentBaseService agentBaseService;
	
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private ApiMappingDicionService apiMappingDicionService;
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
	private ParamsInfoService paramsInfoService;
	
	@RequestMapping("/operate")
	public String paymentpage(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		String agentNo = request.getParameter("agentNo");
		log.info("###########代理商推广注册agentNo["+agentNo+"]###########");
		
		String browerType = request.getHeader("User-Agent").toLowerCase();
		if(browerType.toLowerCase().indexOf("micromessenger")==-1){//注册只能微信浏览器进入
			request.setAttribute("error", "必须使用微信扫描二维码");
			log.error("###########代理商推广注册agentNo["+agentNo+"],新用户入驻必须使用微信扫描二维码###########");
			return "scancode/wechat/scan_error";
		}
		
		//判定代理商是否存在
		AgentBase agentBase = agentBaseService.findByAgentNo(agentNo);
		if (null == agentBase) {
			request.setAttribute("error", "代理商不存在");
			log.error("###########代理商推广注册agentNo["+agentNo+"],代理商不存在###########");
			return "scancode/wechat/scan_error";
		}
		try {
			log.info("handleAuth authNew organNo is "+agentNo);
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(agentNo);
			ApiChannelWxParams params = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			log.info("==========参数配置为："+params.getWxParams());
			JSONObject json = JSONObject.fromObject(params.getWxParams());
			
			JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(agentBase.getChannelNo());
			log.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
			
			
			String backUrl = URLEncoder.encode(paramsInfoJson.getString("backAgentPromoteUrl"), "UTF-8");
			ApiMappingDicionCondition apiCondition = new ApiMappingDicionCondition();
			apiCondition.setKeyNo(HfepayConfig.WECHAT_BACK_GO_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(apiCondition);
			String backgo=dic.getParaVal();
			backgo = backgo.replace("REDIRECTURL", backUrl);
			backgo = backgo.replace("APPID", json.getString("appid"));
			backgo = backgo.replace("ORDERID", agentNo);
			//response.sendRedirect(backgo);
			return "redirect:"+backgo;
		}catch(Exception e){
			e.printStackTrace();
			log.error("handleAuth error ..."+e);
		}
		request.setAttribute("error", "内部错误.");
		return "scancode/wechat/scan_error";
	}
		
	/**
	* @Description 二维码没有被使用过回调
	* @param
	* @author husain 
	* @Date 2016年10月7日 下午7:32:56
	 */
	@RequestMapping(value = "/callbackNew")
	public String callbackNew(HttpServletRequest request){
		String organNo = request.getParameter("state");
		String code = request.getParameter("code");
		log.debug("========== agentpromote callbackUnpass code:"+code+",state:"+organNo+"==========");
		try {
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(organNo);
			ApiChannelWxParams params = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			log.info("==========agentpromote 参数配置为："+params.getWxParams());
			JSONObject json = JSONObject.fromObject(params.getWxParams());
			ApiMappingDicionCondition condition = new ApiMappingDicionCondition();
			condition.setKeyNo(HfepayConfig.WAUTHOR_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(condition);
			log.info("==========agentpromote WAUTHOR_KEY 参数配置为："+dic.getParaVal());
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("appid", json.getString("appid"));
			sParaTemp.put("secret", json.getString("secret"));
			sParaTemp.put("code", code);
			sParaTemp.put("grant_type", "authorization_code");
			
			String resultJson =HttpRequestClient.invoke_http(dic.getParaVal(), generatNameValuePair(sParaTemp), "utf-8");
			JSONObject result= JSONObject.fromObject(resultJson);
			log.debug("agentpromote callbackNew 微信获取openId返回json："+resultJson);
			log.debug(resultJson);
			String openId = result.getString("openid");
			log.info("agentpromote callbackNew in callback........................openId="+openId);
			//当前用户是否是未完成注册，是否已经注册过
			ChannelAdmin admin = findByOpenId(openId);
			if(admin!=null){//已经注册过不能再次注册
				log.info("agentpromote callback else==========openid="+openId+" merchantNo="+admin.getMerchantNo());
				DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(admin.getAgentNo());
				ChannelExpand expend = channelExpandService.findByChannelNo(vo.getOrganNo());
				request.setAttribute("img", expend.getIndexWxImg());
				request.setAttribute("error", "您已扫码注册,请关注公众号,请尽快完善您的资料");
				return "scancode/wechat/scan_error";
			}else{//新用户直接注册
				log.info("callback 新用户直接注册==========");
				AgentBase agentBase = agentBaseService.findByAgentNo(organNo);
				//判定代理商二维码
				if (agentBase.getLessTotal() <= 0) {
					request.setAttribute("error", "代理商无可用二维码");
					log.error("###########agentpromote 代理商推广注册agentNo["+organNo+"],代理商无可用二维码###########");
					return "scancode/wechat/scan_error";
				}
				//获取代理商二维码
				PlatformQrcodeCondition platformQrcodeCondition = new PlatformQrcodeCondition();
				platformQrcodeCondition.setAgentNo(organNo);
				platformQrcodeCondition.setQrStatus(QrStatus.QR_1.getCode());
				platformQrcodeCondition.setUseStatus(UseStatus.UNUSE.value());
				List<PlatformQrcode> platfromQrCodeList = platformQrcodeService.findAll(platformQrcodeCondition);
				if (null == platfromQrCodeList || platfromQrCodeList.size() < 0) {
					request.setAttribute("error", "代理商无可用二维码");
					log.error("###########agentpromote 代理商推广注册agentNo["+organNo+"],平台二维码中无代理商对应二维码,代码有问题###########");
					return "scancode/wechat/scan_error";
				}
				
				String qrCode = platfromQrCodeList.get(0).getQrCode();
				log.info("###########agentpromote 代理商推广注册agentNo["+organNo+"],qrCode["+qrCode+"]开始注册###########");
				
				log.info("in callbackNew createMerchant start........................openId="+openId);
				long resultCreate = channelAdminService.createMerchantByQrCode(qrCode,openId);
				log.info("in callbackNew createMerchant end........................openId="+openId+"  result="+result);
				if(resultCreate>0){
					ChannelAdmin adminRE = findByOpenId(openId);
					request.getSession().setAttribute(Constants.CURRENTUSER,adminRE);
				}
				return "redirect:/scancode/my";
				
			}
		} catch (Exception e) {
			log.error("获取openId异常", e);
			request.setAttribute("error", "授权公众号失败");
			return "scancode/wechat/scan_error";
		}
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
