/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.PlatformLimitCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.PlatformLimit;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.PlatformLimitService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/platformlimit")
public class PlatformLimitController extends BaseController{
	
	@Autowired
	private PlatformLimitService platformLimitService;
	
	@Autowired
	private MappingDicionService mappingDicionService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo(Constants.PAYWAY_CODE);
		List<MappingDicion> list = mappingDicionService.findAll(mappingDicionCondition);
		request.setAttribute("payCodeList", list);
		return "admin/platformlimit/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,PlatformLimitCondition platformLimitCondition){	
		platformLimitCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		PagingResult<PlatformLimit> page = platformLimitService.findPagingResult(platformLimitCondition);
		Pager<PlatformLimit> pager = new Pager<PlatformLimit>();
		pager.setPageNo(platformLimitCondition.getPageNo());
		pager.setPageSize(platformLimitCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		
		model.addAttribute("pager",pager);
		return "admin/platformlimit/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,PlatformLimitCondition platformLimitCondition){	
		platformLimitCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
        List<PlatformLimit> list = platformLimitService.findAll(platformLimitCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			PlatformLimit entity = platformLimitService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo(Constants.PAYWAY_CODE);
		List<MappingDicion> list = mappingDicionService.findAll(mappingDicionCondition);
		model.addAttribute("payCodeList", list);
		return "admin/platformlimit/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,PlatformLimitCondition platformLimitCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		platformLimitCondition.setOperator(user.getUserName());
		try {
			if(Strings.isEmpty(platformLimitCondition.getId())){
				//新增
				platformLimitCondition.setCreateTime(new Date());
				platformLimitCondition.setId(Strings.getUUID());
				platformLimitCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				platformLimitCondition.setStatus(Constants.SUCCESS_STATE);
				platformLimitService.insert(platformLimitCondition);
			}else{
				platformLimitCondition.setUpdateTime(new Date());
				platformLimitService.update(platformLimitCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/platformlimit");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,PlatformLimitCondition platformLimitCondition){		
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		platformLimitCondition.setOperator(user.getUserName());
		platformLimitCondition.setRecordStatus(Constants.RECORD_STATUS_NO);
		platformLimitCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			platformLimitService.update(platformLimitCondition);
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
	public JSON updateStatus(HttpServletRequest request,PlatformLimitCondition platformLimitCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			platformLimitService.updateStatus(platformLimitCondition.getId(),platformLimitCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		PlatformLimit entity = platformLimitService.findById(id);
		model.addAttribute("item",entity);
		return "admin/platformlimit/detail";
	}
	
	/** 校验限额通道和限额类型 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject check(HttpServletRequest request,String limitPayCode,String limitType,String id){	
		PlatformLimitCondition platformLimitCondition = new PlatformLimitCondition();
		platformLimitCondition.setLimitPayCode(limitPayCode);
		platformLimitCondition.setLimitType(limitType);
		
        List<PlatformLimit> list = platformLimitService.findAll(platformLimitCondition);
        JSONObject json = new JSONObject();
        for (Iterator<PlatformLimit> iterator = list.iterator(); iterator.hasNext();) {
        	PlatformLimit platformLimit = (PlatformLimit) iterator.next();
			if(!platformLimit.getId().equals(id)){
				json.put("check", false);
				return json;
			}
		}
        json.put("check", true);
		return json;
	}
}

