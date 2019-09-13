/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

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
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelRoleCondition;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelRole;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.channel.ChannelRoleService;
import com.hfepay.scancode.service.channel.ChannelRoleUserService;
import com.hfepay.scancode.service.operator.ChannelBaseService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/channel/sys/admin")
public class ChannelAdminController extends BaseController{
	
	@Autowired
	private ChannelAdminService channelAdminService;
	
	@Autowired
    private ChannelRoleService channelRoleService;
	
	@Autowired
	private ChannelRoleUserService channelRoleUserService;
	@Autowired
	private ChannelBaseService channelBaseService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/channel/sysadmin/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,ChannelAdminCondition ChannelAdminCondition){	
		PagingResult<ChannelAdmin> page = channelAdminService.findPagingResult(ChannelAdminCondition,Constants.IDENTITYFLAG_CHANNEL);
		Pager<ChannelAdmin> pager = new Pager<ChannelAdmin>();
		pager.setPageNo(ChannelAdminCondition.getPageNo());
		pager.setPageSize(ChannelAdminCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/channel/sysadmin/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,ChannelAdminCondition ChannelAdminCondition){	
        List<ChannelAdmin> list = channelAdminService.findAll(ChannelAdminCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			ChannelAdmin entity = channelAdminService.findById(id);
			if(entity != null){
				ChannelRoleCondition condition = new ChannelRoleCondition();
				condition.setChannelCode(entity.getChannelCode());
				condition.setRoleLevel(Constants.IDENTITYFLAG_CHANNEL);
				List<ChannelRole> roles = channelRoleService.findAllNoPage(condition);
				model.addAttribute("roles", roles);
			}
			model.addAttribute("Obj", entity);
		}
		model.addAttribute("channelBaseList", channelBaseService.findAll(new ChannelBaseCondition()));
//		ChannelRoleCondition roleCon = new ChannelRoleCondition();
//		roleCon.setChannelCode(Constants.ROLE_TYPE_CHANNEL);
//		model.addAttribute("roles", channelRoleService.findAllNoPage(roleCon));
		model.addAttribute("role",  channelRoleUserService.selectRoleUserByUserId(id));
		return "admin/channel/sysadmin/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,ChannelAdminCondition channelAdminCondition,String roleId) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			channelAdminCondition.setIdentityFlag(Constants.IDENTITYFLAG_CHANNEL);
			channelAdminCondition.setIdentityNo(channelAdminCondition.getChannelCode());
			channelAdminService.updateFix(channelAdminCondition, roleId,channelAdminCondition.getChannelCode());
			
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/channel/sys/admin");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,ChannelAdminCondition ChannelAdminCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			channelAdminService.update(ChannelAdminCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
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
		return "admin/channel/sysadmin/detail";
	}
}

