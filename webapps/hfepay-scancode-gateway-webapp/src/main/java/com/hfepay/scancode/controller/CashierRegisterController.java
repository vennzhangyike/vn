package com.hfepay.scancode.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.lang.HttpRequestClient;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.api.condition.ApiChannelWxParamsCondition;
import com.hfepay.scancode.api.condition.ApiMappingDicionCondition;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.ApiMappingDicion;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.api.service.ApiMappingDicionService;
import com.hfepay.scancode.api.service.config.HfepayConfig;
import com.hfepay.scancode.commons.BaseErrorMsg;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.ParamsInfoService;

import net.sf.json.JSONObject;

/**
 * 收银员相关，收银员扫码相关
 * @author husain
 *
 */
@Controller
@RequestMapping("/cashier")
public class CashierRegisterController {
	public static final Logger logger = LoggerFactory.getLogger(CashierRegisterController.class);
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private ApiMappingDicionService apiMappingDicionService;
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
	private MerchantCashierService merchantCashierService;
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private ParamsInfoService paramsInfoService;

	
	/***扫码进行授权等完成之后到资料完善界面***/
	@RequestMapping("/toregister/{organNo}/{merchantNo}")
	public String toregister(HttpServletRequest request,@PathVariable String organNo,@PathVariable String merchantNo,HttpServletResponse response){
		//必须提出来作为非过滤的链接，扫码之后进入注册页面
		String browerType = request.getHeader("User-Agent").toLowerCase();
		if(browerType.toLowerCase().indexOf("micromessenger")==-1){//注册只能微信浏览器进入
			request.setAttribute("error", "注册收银员请使用微信扫描二维码.");
			return "scancode/wechat/scan_error";
		}
		MerchantInfo info = merchantInfoService.findByMerchantNo(merchantNo);
		if(info==null||!info.getStatus().equals(MerchantStatus.MERCHANT_STATUS_3.getCode())){
			request.setAttribute("error", "商户尚未审核通过不能添加收银员.");
			return "scancode/wechat/scan_error";
		}
		/*if(info!=null&&info.getStatus().equals(MerchantStatus.MERCHANT_STATUS_3.getCode())){//商户存在且审核通过
			return "redirect:/cashier/auth?merchantNo="+merchantNo+"&organNo="+organNo;
		}
		
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
			logger.info("==========注册收银员参数配置为："+params.getWxParams());
			JSONObject json = JSONObject.fromObject(params.getWxParams());
			
			JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(info.getChannelNo());
			logger.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
			
			//String merchantNo=request.getParameter("merchantNo");
//			String backUrl = URLEncoder.encode(wechatConfig.getBackCashierUrl(), "UTF-8");
			ApiMappingDicionCondition apiCondition = new ApiMappingDicionCondition();
			apiCondition.setKeyNo(HfepayConfig.WECHAT_BACK_GO_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(apiCondition);
			String backgo=dic.getParaVal();//"https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECTURL&response_type=code&scope=snsapi_base#wechat_redirect";
			String backUrl = URLEncoder.encode(paramsInfoJson.getString("backCashierUrl"), "UTF-8");
			
			backgo = backgo.replace("REDIRECTURL", backUrl);
			backgo = backgo.replace("APPID", json.getString("appid"));
			backgo = backgo.replace("ORDERID", merchantNo+","+organNo);
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
		logger.debug("==========注册收银员 state:"+state+"==========");
		String stateParam[] = state.split(",");
		String merchantNo = stateParam[0];
		String organNo = stateParam[1];
		String code = request.getParameter("code");
		logger.debug("==========code:"+code+"==========");
		try {
			ApiMappingDicionCondition apiCondition = new ApiMappingDicionCondition();
			apiCondition.setKeyNo(HfepayConfig.WAUTHOR_KEY);
			ApiMappingDicion dic = apiMappingDicionService.getFromRedis(apiCondition);
			logger.info("==========注册收银员 WAUTHOR_KEY 参数配置为："+dic.getParaVal());
			Map<String,String> sParaTemp = getparamMap(code,organNo);
			String resultJson =HttpRequestClient.invoke_http(dic.getParaVal(), generatNameValuePair(sParaTemp), "utf-8");
			JSONObject result= JSONObject.fromObject(resultJson);
			logger.debug("注册收银员微信获取openId返回json："+resultJson);
			String openId = result.getString("openid");
			logger.debug("注册收银员公众号openid="+openId);
			if(Strings.isNotEmpty(openId)){
				ChannelAdmin admin = findByOpenId(openId);
				if(admin==null){//还没有注册
					logger.info("注册收银员  createCashier start........................openId="+openId);
					long resultCreate = merchantCashierService.createCashier(merchantNo,openId);
					logger.info("注册收银员  createCashier end........................openId="+openId+"  result="+result+" merchantNo="+merchantNo+" organNo="+organNo);
					if(resultCreate>0){//新用户注册成功
						logger.debug("微信公账号进入注册成功========resultCreate="+resultCreate);
						return "redirect:/cashier/registerCashier/"+openId;
					}
					else{//新用户注册失败
						logger.debug("收银员注册失败========resultCreate="+resultCreate);
						request.setAttribute("error", "内部错误，请联系管理员.");
						return "scancode/wechat/scan_error";
					}
				}
				else{//已注册用户
					//商户扫描自己的收银员注册二维码不允许，一个微信号在一个渠道只能有一个角色，否则无法区分是收银员还是商户
					if(IdentityType.Identity_Merchant.getCode().equals(admin.getIdentityFlag())){
						logger.debug("商户扫描自己的收银员二维码========identityFlag="+admin.getIdentityFlag()+" merchantNo is "+admin.getMerchantNo());
						request.setAttribute("error", "商户不能成为收银员.");
						return "scancode/wechat/scan_error";
					}
					//查询用户的资料是否完善，完善之后到"我的"界面
					MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
					merchantCashierCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
					merchantCashierCondition.setOpenId(openId);
					MerchantCashier casher = merchantCashierService.findByCondition(merchantCashierCondition);
					if(null!=casher&&Strings.isNotEmpty(casher.getMobile())){
						if(!casher.getMerchantNo().equals(merchantNo)){//当前收银员存在但是扫描的二维码不是自己商户的
							logger.debug("收银员已注册微信公众号进入========casher.merchantNo="+casher.getMerchantNo()+" code merchantNo is "+merchantNo);
							request.setAttribute("error", "当前您扫描的二维码不是您注册商户提供的二维码");
							return "scancode/wechat/scan_error";
						}
						logger.debug("收银员已注册微信公众号进入========admin.identityNo="+admin.getIdentityNo());
						request.getSession().setAttribute(Constants.CURRENTUSER,admin);
						return "redirect:/scancode/my";
					}
					else{//资料未完善，再次扫码到资料完善界面
						return "redirect:/cashier/registerCashier/"+openId;
					}
				}
			}
			else{
				logger.debug("收银员微信公账号为空========");
				request.setAttribute("error", "系统获取公众号信息失败.");
				return "scancode/wechat/scan_error";
			}
		} catch (Exception e) {
			logger.error("收银员获取openId异常", e);
			request.setAttribute("error", "系统获取公众号信息失败.");
			return "scancode/wechat/scan_error";
		}
	}
	
	@RequestMapping("/registerCashier/{openId}")
	public String registerCashier(HttpServletRequest request,@PathVariable String openId){
		//必须提出来作为非过滤的链接，扫码之后进入注册页面
		request.setAttribute("openId", openId);
		ChannelAdminCondition adminCondition = new ChannelAdminCondition();
		adminCondition.setOpenId(openId);
		adminCondition.setIdentityFlag(IdentityType.Identity_Cashier.getCode());
		ChannelAdmin admin = channelAdminService.findByCondition(adminCondition);
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(admin.getAgentNo());
		String organNo=vo.getOrganNo();
		request.setAttribute("organNo", organNo);//具有公众号的父级
		return "scancode/wechat/registerCashier";
	}
	
	private static List<NameValuePair> generatNameValuePair(Map<String, String> properties) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
        	NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(),entry.getValue());
        	list.add(nameValuePair);
        }
        return list;
    }
	
	private Map<String, String> getparamMap(String code,String organNo){
		ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
		channelWxParamsCondition.setOrganNo(organNo);
		Map<String, String> sParaTemp = new HashMap<String, String>();
		try {
			ApiChannelWxParams params = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			logger.info("==========注册收银员参数配置为："+params.getWxParams());
			JSONObject json = JSONObject.fromObject(params.getWxParams());
			sParaTemp.put("appid", json.getString("appid"));
			sParaTemp.put("secret", json.getString("secret"));
			sParaTemp.put("code", code);
			sParaTemp.put("grant_type", "authorization_code");
		} catch (Exception e) {
			logger.error("params参数获取异常", e);
			e.printStackTrace();
		}
		return sParaTemp;
	}
	/***根据openId检测用户是否注册过**/
	private ChannelAdmin findByOpenId(String openId){
		ChannelAdminCondition condition = new ChannelAdminCondition();
		condition.setOpenId(openId);
		//condition.setIdentityFlag(IdentityType.Identity_Cashier.value());
		ChannelAdmin admin = channelAdminService.findByCondition(condition);
		return admin;
	}
	
	@RequestMapping("/registerCashierOperator")
	@ResponseBody
	public BaseErrorMsg registerCashierOperator(ModelMap model,HttpServletRequest request,MerchantCashierCondition condition){
		//必须提出来作为非过滤的链接，扫码之后进入注册页面
		BaseErrorMsg msg = new BaseErrorMsg();
		if(Strings.isEmpty(condition.getIdCard())){
			msg.setErrorMsg("身份证不能为空");
			return msg;
		}
		Pattern p = Pattern.compile("^1\\d{10}$");
		Matcher m = p.matcher(condition.getMobile());
		if(!m.matches()){
			msg.setErrorMsg("手机号码有误");
			return msg;
		}
		if(Strings.isEmpty(condition.getUserName())){
			msg.setErrorMsg("姓名不能为空");
			return msg;
		}
		if(Strings.isEmpty(condition.getOpenId())){
			msg.setErrorMsg("微信参数有误");
			return msg;
		}
		
		//校验手机号码不能重复
		ChannelAdminCondition channelAdminCondition = new ChannelAdminCondition();
		channelAdminCondition.setPhone(condition.getMobile());
		List<ChannelAdmin> list = channelAdminService.findAll(channelAdminCondition);
		if (Lists.isNotEmpty(list)) {
			msg.setErrorMsg("手机号码已存在");
			return msg;
		}
		MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
		merchantCashierCondition.setOpenId(condition.getOpenId());
		merchantCashierCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		MerchantCashier cashier = merchantCashierService.findByCondition(merchantCashierCondition);
		if(null!=cashier){
			msg.setErrorMsg("不允许重复注册");
			return msg;
		}
		long result = merchantCashierService.doSaveCashier(condition);
		if(result>0){
			msg.setErrorCode("0");
			msg.setErrorMsg("创建成功");
		}
		return msg;
	}
	
	
	@RequestMapping("/cashierSuccess/{organNo}")
	public String cashierSuccess(HttpServletRequest request,@PathVariable String organNo){
		ChannelExpand expend = channelExpandService.findByChannelNo(organNo);
		request.setAttribute("img", expend.getIndexWxImg());
		//渠道二维码，关注
		return "scancode/wechat/cashierSuccess";
	}
	
}
