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

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.lang.HttpRequestClient;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.api.condition.ApiChannelWxParamsCondition;
import com.hfepay.scancode.api.condition.ApiMappingDicionCondition;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.ApiMappingDicion;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.api.service.ApiMappingDicionService;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.UseStatus;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.ParamsInfoService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;

import net.sf.json.JSONObject;

/**
 * @ClassName: WechatLoginController
 * @Description: 微信公众号进入
 * @author: husain
 * @date: 2016年10月25日 上午9:53:32
 */
@Controller
@RequestMapping("/user")
public class WechatLoginController {
	public static final Logger logger = LoggerFactory.getLogger(WechatLoginController.class);
	
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private ApiMappingDicionService apiMappingDicionService;
	@Autowired
	private AgentBaseService agentBaseService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private MerchantCashierService merchantCashierService;
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private ParamsInfoService paramsInfoService;
	@Autowired
	private ChannelExpandService channelExpandService;
	
	
	@RequestMapping("/wechat/in")
	public String wechatIn(ModelMap model,HttpServletRequest request,HttpServletResponse response){
		String type=request.getParameter("type");
		String organNo=request.getParameter("organNo");
		organNo=Strings.isEmpty(organNo)?request.getParameter("channelNo"):organNo;//为了和老版本兼容，这里配置channelNo参数也可行
		if(Strings.isEmpty(organNo)){
			request.setAttribute("error", "微信菜单参数配置错误，请指定机构编号.");
			return "scancode/wechat/scan_error";
		}
		String browerType = request.getHeader("User-Agent").toLowerCase();
		if(browerType.toLowerCase().indexOf("micromessenger")==-1){//注册只能微信浏览器进入
			request.setAttribute("error", "新用户入驻必须使用微信扫描二维码.");
			return "scancode/wechat/scan_error";
		}
		/*return "redirect:/user/auth?type="+type+"&organNo="+organNo;
	}
	
	//静默授权
	@RequestMapping(value = "/auth")
	private String handleAuth(HttpServletResponse response,HttpServletRequest request){*/
		try {
			//String organNo =  request.getParameter("organNo");
			logger.info("organNo is "+organNo);
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(organNo);
			ApiChannelWxParams params = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			logger.info("==========参数配置为："+params.getWxParams());
			JSONObject json = JSONObject.fromObject(params.getWxParams());
			
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
			
			//String type=request.getParameter("type");
//			String backUrl = URLEncoder.encode(wechatConfig.getBackPublicCallback(), "UTF-8");
			String backUrl = URLEncoder.encode(paramsInfoJson.getString("backPublicCallback"), "UTF-8");
			
			ApiMappingDicionCondition apiCondition = new ApiMappingDicionCondition();
			apiCondition.setKeyNo(HfepayConfig.WECHAT_BACK_GO_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(apiCondition);
			String backgo=dic.getParaVal();//"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECTURL&response_type=code&scope=snsapi_base#wechat_redirect";
			backgo = backgo.replace("REDIRECTURL", backUrl);
			backgo = backgo.replace("APPID", json.getString("appid"));
			backgo = backgo.replace("ORDERID", type+","+organNo);
			//response.sendRedirect(backgo);
			return "redirect:"+backgo;
		}catch(Exception e){
			logger.error("handleAuth error..... "+e);
			e.printStackTrace();
		}
		request.setAttribute("error", "内部错误.");
		return "scancode/wechat/scan_error";
	}
	
	/**
	* @Description 微信授权回调
	* @param
	* @author husain 
	* @Date 2016年10月7日 下午7:32:56
	 */
	@RequestMapping(value = "/callback")
	public String callback(HttpServletRequest request){
		String state = request.getParameter("state");//微信进入的类型：账单，我的，收款
		logger.debug("==========state:"+state+"==========");
		String stateParam[] = state.split(",");
		String type = stateParam[0];
		String organNo = stateParam[1];
		
		String code = request.getParameter("code");
		logger.debug("==========code:"+code+"==========");
		try {
			
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(organNo);
			ApiChannelWxParams params = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			logger.info("==========参数配置为："+params.getWxParams());
			JSONObject json = JSONObject.fromObject(params.getWxParams());
			
			ApiMappingDicionCondition apiCondition = new ApiMappingDicionCondition();
			apiCondition.setKeyNo(HfepayConfig.WAUTHOR_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(apiCondition);
			logger.info("==========WAUTHOR_KEY 参数配置为："+dic.getParaVal());
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("appid", json.getString("appid"));
			sParaTemp.put("secret", json.getString("secret"));
			sParaTemp.put("code", code);
			sParaTemp.put("grant_type", "authorization_code");
			
			String resultJson =HttpRequestClient.invoke_http(dic.getParaVal(), generatNameValuePair(sParaTemp), "utf-8");
			JSONObject result= JSONObject.fromObject(resultJson);
			logger.debug("微信获取openId返回json："+resultJson);
			String openId = result.getString("openid");
			logger.debug("公众号进入openid="+openId);
			if(Strings.isNotEmpty(openId)){
				ChannelAdminCondition condition = new ChannelAdminCondition();
				condition.setOpenId(openId);
				ChannelAdmin admin = channelAdminService.findByCondition(condition);
				if(admin==null){//还没有注册
					logger.debug("微信公账号进入还没有注册========admin=null"+" type="+type);
					PlatformQrcodeCondition platformQrcodeCondition = new PlatformQrcodeCondition();
					platformQrcodeCondition.setUseStatus(UseStatus.UNUSE.value());
					ChannelBase base = getOrganChannel(organNo);
					if(base!=null){//是渠道
						platformQrcodeCondition.setAgentNo(getDefaultAgent(organNo));
						platformQrcodeCondition.setChannelNo(organNo);
					}else{
						AgentBase baseAgent = agentBaseService.findByAgentNo(organNo);
						platformQrcodeCondition.setAgentNo(organNo);
						platformQrcodeCondition.setChannelNo(baseAgent.getChannelNo());
					}
					
					List<PlatformQrcode> codePlateList = platformQrcodeService.findAll(platformQrcodeCondition);
					if(Lists.isEmpty(codePlateList)){
						logger.debug("微信公账号进入注册,二维码已用完type="+type);
						request.setAttribute("error", "二维码已用完，请联系管理员.");
						return "scancode/wechat/scan_error";
					}
					PlatformQrcode codePlate = codePlateList.get(0);
					String qrCode = codePlate.getQrCode();
					
					logger.info("in WechatLoginController callback createMerchant start........................openId="+openId);
					long resultCreate = channelAdminService.createMerchantByQrCode(qrCode,openId);
					logger.info("in WechatLoginController callback createMerchant end........................openId="+openId+"  result="+result);
					if(resultCreate>0){//新用户注册成功
						logger.debug("微信公账号进入注册成功========resultCreate="+resultCreate+" type="+type);
						ChannelAdmin adminRE = findByOpenId(openId);
						request.getSession().setAttribute(Constants.CURRENTUSER,adminRE);
						return "redirect:/scancode/my?type="+type;
					}
					else{//新用户注册失败
						logger.debug("微信公账号进入注册成功========resultCreate="+resultCreate+" type="+type);
						request.setAttribute("error", "内部错误，请联系管理员.");
						return "scancode/wechat/scan_error";
					}
				}
				else{//已注册用户
					//检测用户是否被禁用
					String status=checkStatusOk(admin);
					if("1".equals(status)){
						request.setAttribute("error", "您已经被禁用，请联系管理员");
						return "scancode/wechat/scan_error";
					}else if("2".equals(status)){//收银员完善信息
						return "redirect:/cashier/registerCashier/"+openId;
					}
					logger.debug("已注册用户微信公众号进入========admin.merchantNo="+admin.getMerchantNo()+" type="+type);
					request.getSession().setAttribute(Constants.CURRENTUSER,admin);
					return "redirect:/scancode/my?type="+type;
				}
			}
			else{
				logger.debug("微信公账号为空========"+" type="+type);
				request.setAttribute("error", "系统获取公众号信息失败.");
				return "scancode/wechat/scan_error";
			}
		} catch (Exception e) {
			logger.error("获取openId异常"+" type="+type, e);
			request.setAttribute("error", "系统获取公众号信息失败.");
			return "scancode/wechat/scan_error";
		}
	}
	
	/**商户是否被被禁用**/
	private String checkStatusOk(ChannelAdmin admin){
		logger.debug("#######判定商户是否被禁用admin："+JSONObject.fromObject(admin));
		String flag = admin.getIdentityFlag();
		logger.info("check merchant status:merchantNo is "+admin.getMerchantNo()+" merchant IdentityFlag="+flag);
		if(flag.equals(IdentityType.Identity_Merchant.getCode())){//商户
			MerchantInfo info = merchantInfoService.findByMerchantNo(admin.getMerchantNo());
			if(null==info||info.getStatus().equals(MerchantStatus.MERCHANT_STATUS_5.getCode())){//停用
				return "1";
			}
		}else if(flag.equals(IdentityType.Identity_Cashier.getCode())){//收银员
			MerchantCashier cashier = merchantCashierService.findByOpenId(admin.getOpenId()); 
			if(cashier==null){//收银员完善信息
				return "2";
			}
			if(null!=cashier&&cashier.getStatus().equals("2")){//禁用
				return "1";
			}
		}
		logger.info("............status check is ok...................................");
		return "";
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
		//condition.setIdentityFlag(IdentityType.Identity_Merchant.value());
		ChannelAdmin admin = channelAdminService.findByCondition(condition);
		return admin;
	}
	
	private String getDefaultAgent(String channelNo){
		AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
		agentBaseCondition.setAgentFlag("0");
		agentBaseCondition.setChannelNo(channelNo);
		List<AgentBase> list = agentBaseService.findAll(agentBaseCondition);
		if(null!=list&&!list.isEmpty()&&list.size()>0){
			if(list.size()>1){
				throw new RuntimeException("渠道	"+channelNo+"	默认代理商存在多个....");
			}
			AgentBase base = list.get(0);
			return base.getAgentNo();
		}
		else
			throw new RuntimeException("渠道没有设置默认代理商，二维码获取失败");
	}
	
	private ChannelBase getOrganChannel(String organNo){
		return channelBaseService.findByChannelNo(organNo);
	}
	
}
