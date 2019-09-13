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
import com.hfepay.scancode.commons.condition.PlatformSecretKeyCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.PlatformSecretKey;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.PlatformSecretKeyService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/platformsecretkey")
public class PlatformSecretKeyController extends BaseController{
	
	@Autowired
	private PlatformSecretKeyService platformSecretKeyService;
	
	@Autowired
	private MappingDicionService mappingDicionService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/platformsecretkey/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,PlatformSecretKeyCondition platformSecretKeyCondition){		
		PagingResult<PlatformSecretKey> page = platformSecretKeyService.findPagingResult(platformSecretKeyCondition);
		Pager<PlatformSecretKey> pager = new Pager<PlatformSecretKey>();
		pager.setPageNo(platformSecretKeyCondition.getPageNo());
		pager.setPageSize(platformSecretKeyCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/platformsecretkey/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,PlatformSecretKeyCondition platformSecretKeyCondition){		
        List<PlatformSecretKey> list = platformSecretKeyService.findAll(platformSecretKeyCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			PlatformSecretKey entity = platformSecretKeyService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo("ZFTD");
		List<MappingDicion> list = mappingDicionService.findAll(mappingDicionCondition);
		model.addAttribute("payCodeList", list);
		return "admin/platformsecretkey/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,PlatformSecretKeyCondition platformSecretKeyCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		platformSecretKeyCondition.setOperatorId(user.getId());
		try {
			if(Strings.isEmpty(platformSecretKeyCondition.getId())){
				//新增
				platformSecretKeyCondition.setCreateTime(new Date());
				platformSecretKeyCondition.setId(Strings.getUUID());
				//platformSecretKeyCondition.setRecordStatus(Constants.Y);
				platformSecretKeyCondition.setStatus(Constants.SUCCESS_STATE);
				platformSecretKeyService.insert(platformSecretKeyCondition);
			}else{
				platformSecretKeyCondition.setUpdateTime(new Date());
				platformSecretKeyService.update(platformSecretKeyCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/platformsecretkey");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,PlatformSecretKeyCondition platformSecretKeyCondition){		
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		platformSecretKeyCondition.setOperatorId(user.getId());
		platformSecretKeyCondition.setRecordStatus(Constants.N);
		platformSecretKeyCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			platformSecretKeyService.update(platformSecretKeyCondition);
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
	public JSON updateStatus(HttpServletRequest request,PlatformSecretKeyCondition platformSecretKeyCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			platformSecretKeyService.updateStatus(platformSecretKeyCondition.getId(),platformSecretKeyCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		PlatformSecretKey entity = platformSecretKeyService.findById(id);
		model.addAttribute("item",entity);
		return "admin/platformsecretkey/detail";
	}
	
	/** 校验支付通道 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject check(HttpServletRequest request,String payCode,String id){	
		PlatformSecretKeyCondition platformSecretKeyCondition = new PlatformSecretKeyCondition();
		platformSecretKeyCondition.setPayCode(payCode);
		
        List<PlatformSecretKey> list = platformSecretKeyService.findAll(platformSecretKeyCondition);
        JSONObject json = new JSONObject();
        for (Iterator<PlatformSecretKey> iterator = list.iterator(); iterator.hasNext();) {
        	PlatformSecretKey platformSecretKey = (PlatformSecretKey) iterator.next();
			if(!platformSecretKey.getId().equals(id)){
				json.put("check", false);
				return json;
			}
		}
        json.put("check", true);
		return json;
	}
}

