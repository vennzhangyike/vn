/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.AgreementInfoCondition;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.HfepayCategoryCondition;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.AgreementInfo;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.ChannelWxParams;
import com.hfepay.scancode.commons.entity.HfepayCategory;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.AgreementInfoService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.ChannelWxParamsService;
import com.hfepay.scancode.service.operator.HfepayCategoryService;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.ParamsInfoService;
import com.hfepay.scancode.service.utils.PasswordHelper;

@Controller
@RequestMapping("/adminManage/personalcenter")
public class PersonalCenterController extends BaseController{
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private AgentBaseService agentBaseService;
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private ChannelWxParamsService channelWxParamsService;
	@Autowired
	private ParamsInfoService paramsInfoService;
	@Autowired
	private ChannelBaseService channelBaseService;	
	@Autowired
	private MappingDicionService mappingDicionService;
	@Autowired
	private AgreementInfoService agreementInfoService;
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
	private HfepayCategoryService hfepayCategoryService;
	
	/** 列表 */
	@RequestMapping(value = "/userbaseinfo", method= {RequestMethod.GET})
	public String list(HttpServletRequest request,ModelMap model) {
		String redirectUrl = "/adminManage/personalcenter/userbaseinfo";
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			this.setChannelExpandData(model, user);
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			this.setAgentBaseData(model, user);
		}else if(Constants.IDENTITYFLAG_MERCHANT.equals(user.getIdentityFlag())){
			this.setMerchantInfoData(model, user);
		}
		model.addAttribute("redirectUrl", redirectUrl);
		return "admin/personalcenter/userbaseinfo";
	}
	
	/** 设置渠道基本信息 */
	private void setChannelExpandData(ModelMap model,ChannelAdminVo user){
		ChannelWxParams channelWxParams = channelWxParamsService.findByChannelNo(user.getChannelCode());
		ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
		if(channelWxParams != null){
			JSONObject json = JSONObject.parseObject(channelWxParams.getWxParams());
			channelExpandCondition = JSONObject.toJavaObject(json,ChannelExpandCondition.class);
		}
		
		ChannelExpandCondition channelExpandConditionSms = new ChannelExpandCondition();
		ParamsInfo paramsInfo = paramsInfoService.findParamsKey(user.getChannelCode(),ParamsType.PARAMS_SMS.getCode());
		if (paramsInfo != null) {
			JSONObject json = JSONObject.parseObject(paramsInfo.getParamsValue());
			channelExpandConditionSms = JSONObject.toJavaObject(json,ChannelExpandCondition.class);
		}
		AgreementInfoCondition agreementInfoCondition = new AgreementInfoCondition();
		agreementInfoCondition.setOrganNo(user.getChannelCode());
		List<AgreementInfo> agreementList = agreementInfoService.findAll(agreementInfoCondition);
		if(agreementList != null && agreementList.size() > 0){
			AgreementInfo agreementInfo = agreementList.get(0);
			model.addAttribute("agreementInfo",agreementInfo);
		}
		
		ChannelExpand entity = channelExpandService.findByChannelNo(user.getChannelCode());
		BeanUtils.copyProperties(entity, channelExpandCondition);
		model.addAttribute("Obj", channelExpandCondition);
		model.addAttribute("channelExpandConditionSms", channelExpandConditionSms);
		model.addAttribute("channelPage", true);
	}
	
	/** 设置代理商基本信息 */
	private void setAgentBaseData(ModelMap model,ChannelAdminVo user){
		AgentBase entity = agentBaseService.findByAgentNo(user.getAgentNo());
		model.addAttribute("Obj", entity);
		ChannelBaseCondition channelBaseCondition = new ChannelBaseCondition();
		channelBaseCondition.setStatus("1");
		channelBaseCondition.setChannelNo(user.getChannelCode());
		List<ChannelBase> channelList =  channelBaseService.findAll(channelBaseCondition);
		model.addAttribute("channelList", channelList);
		
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo("DLSJB");
		List<MappingDicion> agentLevelList = mappingDicionService.findAll(mappingDicionCondition);
		model.addAttribute("agentLevelList", agentLevelList);		
		
		model.addAttribute("agentPage", true);
	}
	
	/** 设置商户基本信息 */
	private void setMerchantInfoData(ModelMap model,ChannelAdminVo user){
		MerchantInfo entity = merchantInfoService.findByMerchantNo(user.getMerchantNo());
		model.addAttribute("Obj", entity);
		if(Strings.isNotEmpty(entity.getBusType())){
			HfepayCategory category = hfepayCategoryService.findByCategoryNo(entity.getBusType());
			if(category != null){
				model.addAttribute("busTypeStr", category.getName() + "(" + category.getCategoryNo() +  ")");
			}
		}
	 
		//经营科目一级
		HfepayCategoryCondition hfepayCategoryCondition = new HfepayCategoryCondition();
		hfepayCategoryCondition.setLevel(Constants.CATEGORY_LEVEL_ONE);
		List<HfepayCategory> categoryOne = hfepayCategoryService.findAll(hfepayCategoryCondition);
		model.addAttribute("categoryOne",categoryOne);
		model.addAttribute("merchantPage", true);
	}
	
	/** 密码修改 */
	@RequestMapping(value = "/updatepassword", method= {RequestMethod.GET})
	public String updatePassword(HttpServletRequest request,ModelMap model) {
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		String saveUrl="/adminManage/merchantadmin/save";
		if(Strings.equals(user.getIdentityFlag(), Constants.IDENTITYFLAG_CHANNEL)){
			saveUrl = "/adminManage/channel/sys/admin/save";
		}else if(Strings.equals(user.getIdentityFlag(), Constants.IDENTITYFLAG_AGENT)){
			saveUrl = "/adminManage/agentadmin/save";
		}else if(Strings.equals(user.getIdentityFlag(), Constants.IDENTITYFLAG_MERCHANT)){
			saveUrl = "/adminManage/merchantadmin/save";
		}
		else if(Strings.equals(user.getIdentityFlag(), Constants.IDENTITYFLAG_CASHIER)){
			saveUrl = "/adminManage/merchantadmin/saveCasher";
		}
		model.addAttribute("saveUrl", saveUrl);
		model.addAttribute("Obj", user);
		return "admin/personalcenter/updatepassword";
	}
	
	/** 密码校验 */
	@RequestMapping(value = "/checkPassword", method= {RequestMethod.POST})
	@ResponseBody
	public boolean checkPassword(HttpServletRequest request,ModelMap model) {
		boolean flag = false;
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		String userName = (String)request.getParameter("userName");
		String oldPassword = (String)request.getParameter("oldPassword");
		PasswordHelper pa = new PasswordHelper();
		
		ChannelAdmin channelAdmin = channelAdminService.findByUsername(userName, user.getChannelCode());
		if(channelAdmin!= null){
			String password = pa.getEncryptPassword(userName, oldPassword, channelAdmin.getSalt());
			if(Strings.equals(channelAdmin.getPassword(), password)){
				flag = true;
			}
		}
		return flag;
	}
}

