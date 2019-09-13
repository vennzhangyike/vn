/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.commons.ScanBaseController;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.ChannelRoleCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.channel.ChannelRoleService;
import com.hfepay.scancode.service.channel.ChannelRoleUserService;
import com.hfepay.scancode.service.operator.MerchantInfoService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantadmin")
public class MerchantAdminController extends ScanBaseController{
	
	@Autowired
	private ChannelAdminService channelAdminService;
	
	@Autowired
    private ChannelRoleService channelRoleService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private ChannelRoleUserService channelRoleUserService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/merchantadmin/sysadmin/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,ChannelAdminCondition ChannelAdminCondition){	
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			ChannelAdminCondition.setChannelCode(user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			ChannelAdminCondition.setAgentNo(user.getAgentNo());
		}else if(Constants.IDENTITYFLAG_MERCHANT.equals(user.getIdentityFlag())){
			ChannelAdminCondition.setMerchantNo(user.getMerchantNo());
		}
		PagingResult<ChannelAdmin> page = channelAdminService.findPagingResult(ChannelAdminCondition,Constants.IDENTITYFLAG_MERCHANT);
		Pager<ChannelAdmin> pager = new Pager<ChannelAdmin>();
		pager.setPageNo(ChannelAdminCondition.getPageNo());
		pager.setPageSize(ChannelAdminCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantadmin/sysadmin/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,ChannelAdminCondition ChannelAdminCondition){	
		ChannelAdminVo vo =(ChannelAdminVo)getCurrentUser();
		ChannelAdminCondition.setNodeSeq(vo.getNodeSeq());
        List<ChannelAdmin> list = channelAdminService.findAll(ChannelAdminCondition);
        JSONObject json = new JSONObject();
        json.put("objs", list.size());
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(!"0".equalsIgnoreCase(id)){
			ChannelAdmin entity = channelAdminService.findById(id);
			model.addAttribute("Obj", entity);
		}else{
			String merchantNo = request.getParameter("merchantNo");
			MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
			merchantInfoCondition.setNodeSeq(user.getNodeSeq());
			merchantInfoCondition.setStatus(Constants.APPROVE_STATUSING);//在商户表中，3表示审核通过
			merchantInfoCondition.setMerchantNo(merchantNo);
			List<MerchantInfo> list = merchantInfoService.findAll(merchantInfoCondition);
			model.addAttribute("mers", list);
		}
		//只查询商户角色
		ChannelRoleCondition roleCon = new ChannelRoleCondition();
		roleCon.setChannelCode(user.getChannelCode());
		roleCon.setRoleLevel(Constants.IDENTITYFLAG_MERCHANT);
		model.addAttribute("roles", channelRoleService.findAllNoPage(roleCon));
		model.addAttribute("role",  channelRoleUserService.selectRoleUserByUserId(id));
		return "admin/merchantadmin/sysadmin/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,ChannelAdminCondition channelAdminCondition,String roleId) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			channelAdminCondition.setIdentityFlag(Constants.IDENTITYFLAG_MERCHANT);
			if(Strings.isEmpty(channelAdminCondition.getId())){				
				MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(channelAdminCondition.getMerchantNo());
				channelAdminCondition.setChannelCode(merchantInfo.getChannelNo());
				channelAdminCondition.setAgentNo(merchantInfo.getAgentNo());
				channelAdminCondition.setIdentityNo(merchantInfo.getMerchantNo());
			}
			if(Strings.isNotEmpty(channelAdminCondition.getMerchantNo())){
				channelAdminCondition.setIdentityNo(channelAdminCondition.getMerchantNo());
			}
			channelAdminService.updateFix(channelAdminCondition, roleId,channelAdminCondition.getChannelCode());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantadmin");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 新增/更新 */
	@RequestMapping(value="/saveCasher", method= {RequestMethod.POST})
	@ResponseBody
	public JSON saveCasher(HttpServletRequest request,ModelMap model,ChannelAdminCondition channelAdminCondition,String roleId) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			//channelAdminCondition.setIdentityNo(channelAdminCondition.getIdentityNo());
			channelAdminService.updateFix(channelAdminCondition, roleId,channelAdminCondition.getChannelCode());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantadmin");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	
	/**
	 * 状态变更
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSON updateStatus(HttpServletRequest request,ChannelAdminCondition ChannelAdminCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			channelAdminService.updateStatus(ChannelAdminCondition.getId(),ChannelAdminCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String showDetail(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		ChannelAdmin entity = channelAdminService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantadmin/sysadmin/detail";
	}
}

