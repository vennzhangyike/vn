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
import com.hfepay.scancode.commons.condition.SysAdminCondition;
import com.hfepay.scancode.commons.condition.SysRoleCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.service.operator.AdminService;
import com.hfepay.scancode.service.operator.SysRoleService;
import com.hfepay.scancode.service.operator.SysRoleUserService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/sys/admin")
public class SysAdminController extends BaseController{
	
	@Autowired
	private AdminService AdminService;
	
	@Autowired
    private SysRoleService roleService;
	
	@Autowired
	private SysRoleUserService roleUserService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/sysadmin/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,SysAdminCondition SysAdminCondition){		
		PagingResult<Admin> page = AdminService.findPagingResult(SysAdminCondition);
		Pager<Admin> pager = new Pager<Admin>();
		pager.setPageNo(SysAdminCondition.getPageNo());
		pager.setPageSize(SysAdminCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/sysadmin/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,SysAdminCondition SysAdminCondition){		
        List<Admin> list = AdminService.findAll(SysAdminCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			Admin entity = AdminService.findById(id);
			model.addAttribute("Obj", entity);
		}
		model.addAttribute("roles", roleService.findAllNoPage(new SysRoleCondition()));
		model.addAttribute("role",  roleUserService.selectRoleUserByUserId(id));
		return "admin/sysadmin/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,SysAdminCondition SysAdminCondition,String roleId) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			AdminService.updateFix(SysAdminCondition, roleId);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/sys/admin");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,SysAdminCondition SysAdminCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			AdminService.update(SysAdminCondition);
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
	public JSON updateStatus(HttpServletRequest request,SysAdminCondition SysAdminCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			AdminService.updateStatus(SysAdminCondition.getId(),SysAdminCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		Admin entity = AdminService.findById(id);
		model.addAttribute("item",entity);
		return "admin/sysadmin/detail";
	}
}

