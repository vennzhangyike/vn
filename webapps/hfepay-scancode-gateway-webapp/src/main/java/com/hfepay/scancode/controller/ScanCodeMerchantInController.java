package com.hfepay.scancode.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.api.condition.ApiChannelWxParamsCondition;
import com.hfepay.scancode.api.entity.ApiChannelWxParams;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.BaseErrorMsg;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.AgreementInfoCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.RemitBankInfoCondition;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.entity.AgreementInfo;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.RemitBankInfo;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.operator.AgreementInfoService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.operator.ParamsInfoService;
import com.hfepay.scancode.service.operator.RemitBankInfoService;
import com.hfepay.scancode.signature.WechatSign;

import net.sf.json.JSONObject;

/**
 * 商户入驻三部曲
 * @author husain
 *
 */
@Controller
@RequestMapping("/scancode")
public class ScanCodeMerchantInController {
	public static final Logger log = LoggerFactory.getLogger(ScanCodeMerchantInController.class);
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
	private MerchantStoreService merchantStoreService;
	@Autowired
	private MerchantBankcardService merchantBankcardService;
	@Autowired
	private RemitBankInfoService remitBankInfoService;
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private AgreementInfoService agreementInfoService;
	@Autowired
	private ParamsInfoService paramsInfoService;
	
	/**入驻第一步页面**/
	@RequestMapping("/apply1")
	public String apply1(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		String ticket=null;
		JSONObject json = null;
		try {
			ticket = merchantBusinessService.getJsApiTicket(channelAdmin.getAgentNo());
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(channelAdmin.getAgentNo());
			ApiChannelWxParams param = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			json = JSONObject.fromObject(param.getWxParams());
			
		} catch (Exception e) {
			log.error("ticket get error",e);
			e.printStackTrace();
		}
		MerchantInfo info = merchantInfoService.findByMerchantNo(channelAdmin.getMerchantNo());
		info=info==null?new MerchantInfo():info;
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(channelAdmin.getAgentNo());
		
		JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(channelAdmin.getChannelCode());
		log.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
		
//		String url=wechatImgLoadConfig.getApply1Url();
		String url=paramsInfoJson.getString("apply1Url");
		
		Map<String, String> map = WechatSign.sign(ticket, url);
		map.put("appId", json.getString("appid"));
		request.setAttribute("map", map);
		request.setAttribute("channelName", vo.getOrganName());
		request.setAttribute("info", info);//回显信息
		return "scancode/wechat/apply1";
	}
	/**入驻第二步页面**/
	@RequestMapping("/apply2")
	public String apply2(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		MerchantInfo info = merchantInfoService.findByMerchantNo(channelAdmin.getMerchantNo());
		MerchantStore store = null;
		List<MerchantStore> storeList = merchantStoreService.findMerchantStore(channelAdmin.getMerchantNo());//查询第一个入驻的时候的门店
		if(Lists.isNotEmpty(storeList)){
			store = storeList.get(0);
		}
		store=store==null?new MerchantStore():store;
		if(info==null || Strings.isEmpty(info.getName())){
			return "redirect:/scancode/apply1";
		}else{
			request.setAttribute("merchantName", info.getMerchantName());//显示商户名称
		}
		JSONObject json = null;
		String ticket=null;
		try {
			ticket = merchantBusinessService.getJsApiTicket(channelAdmin.getAgentNo());
			ApiChannelWxParamsCondition channelWxParamsCondition = new ApiChannelWxParamsCondition();
			channelWxParamsCondition.setOrganNo(channelAdmin.getAgentNo());
			ApiChannelWxParams param = apiChannelWxParamsService.getFromRedis(channelWxParamsCondition);
			json = JSONObject.fromObject(param.getWxParams());
		} catch (Exception e) {
			log.error("ticket get error",e);
			e.printStackTrace();
		}
		
		JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(channelAdmin.getChannelCode());
		log.info("==========渠道域名参数paramsInfoJson：" + paramsInfoJson);
		
		String url=paramsInfoJson.getString("apply2Url");
//		String url=wechatImgLoadConfig.getApply2Url();
		Map<String, String> map = WechatSign.sign(ticket, url);
		map.put("appId", json.getString("appid"));
		request.setAttribute("map", map);
		request.setAttribute("store", store);//回显信息
		return "scancode/wechat/apply2";
	}
	/**入驻第三步页面**/
	@RequestMapping("/apply3")
	public String apply3(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		MerchantInfo info = merchantInfoService.findByMerchantNo(channelAdmin.getMerchantNo());
		
		MerchantBankcard card = merchantBankcardService.findByMerchantNo(channelAdmin.getMerchantNo());
		card=card==null?new MerchantBankcard():card;
		/*if(info==null || Strings.isEmpty(info.getBusType())){
			return "redirect:/scancode/apply2";
		}*/
		AgreementInfoCondition agreementInfoCondition = new AgreementInfoCondition();
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(channelAdmin.getAgentNo());
		agreementInfoCondition.setOrganNo(vo.getOrganNo());
		List<AgreementInfo> list = agreementInfoService.findAll(agreementInfoCondition);
		if (null != list && list.size() > 0) {			
			request.setAttribute("agreement", list.get(0).getAgreementcontent());
		}else {
			request.setAttribute("agreement", "渠道未配置协议");
		}
		
		request.setAttribute("info", info);
		request.setAttribute("card", card);//回显信息
		return "scancode/wechat/apply3";
	}
	
	/**资料填写第一步
	 * @throws Exception **/
	@RequestMapping("/apply/step1")
	@ResponseBody
	public BaseErrorMsg step1(ModelMap model,MerchantInfoCondition condition,HttpServletRequest request) throws Exception{
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		String ticket="";
		try {
			ticket = merchantBusinessService.getJsApiTicket(channelAdmin.getAgentNo());
		} catch (Exception e) {
			log.error("step1 get ticket to check error.",e);
			e.printStackTrace();
		}
		if(condition.getJsapiTicket()==null||!condition.getJsapiTicket().equals(ticket)){//检查tocken是否过期
			return new BaseErrorMsg("令牌已过期，请刷新重试");
		}
		BaseErrorMsg msg = validateStep1(condition);
		if(msg.getErrorCode().equals("1")){
			return msg;
		}
		condition.setMerchantNo(channelAdmin.getMerchantNo());
		long result = merchantInfoService.updateByMerchantNo(condition);
		if(result<1){
			return new BaseErrorMsg("入驻信息录入失败");
		}
		return new BaseErrorMsg("0", "入驻信息录入成功");
	}
	
	/**资料填写第二步**/
	@RequestMapping("/apply/step2")
	@ResponseBody
	public BaseErrorMsg step2(ModelMap model,MerchantStoreCondition condition,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		String ticket="";
		try {
			ticket = merchantBusinessService.getJsApiTicket(channelAdmin.getAgentNo());
		} catch (Exception e) {
			log.error("step1 get ticket to check error.",e);
			e.printStackTrace();
		}
		if(condition.getJsapiTicket()==null||!condition.getJsapiTicket().equals(ticket)){//检查tocken是否过期
			return new BaseErrorMsg("令牌已过期，请刷新重试");
		}
		BaseErrorMsg msg = validateStep2(condition);
		if(msg.getErrorCode().equals("1")){
			return msg;
		}
		condition.setMerchantNo(channelAdmin.getMerchantNo());
		condition.setChannelNo(channelAdmin.getChannelCode());
		long result = merchantStoreService.applyStoreStep2(condition);
		if(result<1){
			return new BaseErrorMsg("入驻信息录入失败");
		}
		return new BaseErrorMsg("0", "入驻信息录入成功");
	}
	
	/**资料填写第三步**/	
	@RequestMapping("/apply/step3")
	@ResponseBody
	public BaseErrorMsg step3(ModelMap model,MerchantBankcardCondition condition,String validateCode,String agree,HttpServletRequest request){
//		boolean flag = channelAdminService.validateRedisValidateCode(condition.getMobile(), validateCode);
//		if(!flag){
//			log.info("-------------验证码错误------------");
//			return new BaseErrorMsg("验证码错误");
//		}
		if(Strings.isEmpty(agree)||!agree.equals("true")){
			return new BaseErrorMsg("请阅读并同意协议");
		}
		BaseErrorMsg msg = validateStep3(condition);
		if(msg.getErrorCode().equals("1")){
			return msg;
		}
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		condition.setMerchantNo(channelAdmin.getMerchantNo());
		long result = merchantBankcardService.applyStoreStep3(condition);//licence图片
		if(result<1){
			return new BaseErrorMsg("入驻信息录入失败");
		}
		return new BaseErrorMsg("0", "入驻信息录入成功");
	}
	
	/**审核进度：商户扫码的时候已经有一条商户的初始化信息，此处商户一定存在，这是资料是否合法的问题**/
	@RequestMapping("/progress")
	public String progress(ModelMap model,HttpServletRequest request){
		ChannelAdmin channelAdmin = (ChannelAdmin)request.getSession().getAttribute(Constants.CURRENTUSER);
		MerchantInfo info = merchantInfoService.findByMerchantNo(channelAdmin.getMerchantNo());
		//MerchantStore merchantStore = merchantStoreService.findByMerchantNo(channelAdmin.getMerchantNo());
		//MerchantBankcard accounts = merchantBankcardService.findByMerchantNo(channelAdmin.getMerchantNo());
		request.setAttribute("basic", info.getStatus());
		if(MerchantStatus.MERCHANT_STATUS_4.getCode().equals(info.getStatus())||MerchantStatus.MERCHANT_STATUS_2.getCode().equals(info.getStatus())){
			request.setAttribute("errorMsg", info.getRemark());//审核拒绝显示拒绝信息
		}
		//request.setAttribute("shop", merchantStore.getStoreStatus());//不通过
		//request.setAttribute("identity", accounts.getStatus());//审核中
		return "scancode/wechat/progress";
	}
	
	/**银行卡名称和联行号获取**/
	@RequestMapping("/getBankInfo")
	@ResponseBody
	public RemitBankInfo getInfo(ModelMap model,RemitBankInfoCondition conditon){
		return remitBankInfoService.getInfoByCardNo(conditon.getBankCard());
	}
	
	private BaseErrorMsg validateStep1(MerchantInfoCondition condition) throws Exception{
		BaseErrorMsg msg = new BaseErrorMsg("0","pass");
		if(Strings.isEmpty(condition.getName())){//姓名
			return new BaseErrorMsg("姓名不能为空");
		}
		if(Strings.isEmpty(condition.getIdCard())){//身份证号码
			return new BaseErrorMsg("身份证号码不能为空");
		}
		if(Strings.isEmpty(condition.getIdcardImg1())){//正面
			return new BaseErrorMsg("正面照片不能为空");
		}
		if(Strings.isEmpty(condition.getIdcardImg2())){//反面
			return new BaseErrorMsg("反面照片不能为空");
		}
		if(Strings.isEmpty(condition.getIdcardImg3())){//手持
			return new BaseErrorMsg("手持照片不能为空");
		}
		
		Map<String,String> resultMap = null;
		resultMap = merchantInfoService.validateIdCard2(condition.getName(),condition.getIdCard());
		log.info("########################二要素验证结果resultMap : " + resultMap);
		//进行身份证二要素验证
		if(resultMap !=null && !Strings.equals("00", resultMap.get("resCode").trim())){
			return new BaseErrorMsg(resultMap.get("resDesc"));
		}
		
		return msg;
	}
	
	private BaseErrorMsg validateStep2(MerchantStoreCondition condition){
		BaseErrorMsg msg = new BaseErrorMsg("0","pass");
		if(Strings.isEmpty(condition.getMerchantName())){//商户名称
			return new BaseErrorMsg("商户名称不能为空");
		}
		if(Strings.isEmpty(condition.getStoreName())){//门店名称
			return new BaseErrorMsg("门店名称不能为空");
		}
		if(Strings.isEmpty(condition.getServicePhone())){//服务电话
			return new BaseErrorMsg("服务电话不能为空");
		}
		return msg;
	}
	
	private BaseErrorMsg validateStep3(MerchantBankcardCondition condition){
		BaseErrorMsg msg = new BaseErrorMsg("0","pass");
		if(Strings.isEmpty(condition.getName())){
			return new BaseErrorMsg("法人名称不能为空");
		}
		String accountType=condition.getAccountType();
		if(Strings.isEmpty(accountType)){
			return new BaseErrorMsg("请选择账户类型");
		}
		if("0".equals(accountType)&&Strings.isEmpty(condition.getIdCard())){//个人
			return new BaseErrorMsg("身份证号码不能为空");
		}
		if("1".equals(accountType)&&Strings.isEmpty(condition.getTemp1())){//企业
			return new BaseErrorMsg("企业名称不能为空");
		}
		if(Strings.isEmpty(condition.getBankName())){
			return new BaseErrorMsg("银行名称不能为空");
		}
		if(Strings.isEmpty(condition.getBankCard())){
			return new BaseErrorMsg("银行卡号码不能为空");
		}
		if(Strings.isEmpty(condition.getMobile())){
			return new BaseErrorMsg("手机号码不能为空");
		}
		return msg;
	}
	
	
	
}
