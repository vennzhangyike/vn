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
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.MerchantLimitCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantLimit;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantLimitService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantlimit")
public class MerchantLimitController extends BaseController{
	
	@Autowired
	private MerchantLimitService merchantLimitService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private MappingDicionService mappingDicionService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/merchantlimit/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantLimitCondition merchantLimitCondition){	
		merchantLimitCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		PagingResult<MerchantLimit> page = merchantLimitService.findPagingResult(merchantLimitCondition);
		Pager<MerchantLimit> pager = new Pager<MerchantLimit>();
		pager.setPageNo(merchantLimitCondition.getPageNo());
		pager.setPageSize(merchantLimitCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantlimit/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantLimitCondition merchantLimitCondition){	
		merchantLimitCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
        List<MerchantLimit> list = merchantLimitService.findAll(merchantLimitCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantLimit entity = merchantLimitService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo(Constants.PAYWAY_CODE);
		List<MappingDicion> list = mappingDicionService.findAll(mappingDicionCondition);
		model.addAttribute("payCodeList", list);
		
		return "admin/merchantlimit/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,MerchantLimitCondition merchantLimitCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		merchantLimitCondition.setOperator(user.getUserName());
		try {
			if(Strings.isEmpty(merchantLimitCondition.getId())){
				//新增
				merchantLimitCondition.setCreateTime(new Date());
				merchantLimitCondition.setId(Strings.getUUID());
				merchantLimitCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				merchantLimitCondition.setStatus(Constants.SUCCESS_STATE);
				merchantLimitService.insert(merchantLimitCondition);
			}else{
				merchantLimitCondition.setUpdateTime(new Date());
				merchantLimitService.update(merchantLimitCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantlimit");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,MerchantLimitCondition merchantLimitCondition){		
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		merchantLimitCondition.setOperator(user.getUserName());
		merchantLimitCondition.setRecordStatus(Constants.RECORD_STATUS_NO);
		merchantLimitCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantLimitService.update(merchantLimitCondition);
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
	public JSON updateStatus(HttpServletRequest request,MerchantLimitCondition merchantLimitCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantLimitService.updateStatus(merchantLimitCondition.getId(),merchantLimitCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MerchantLimit entity = merchantLimitService.findById(id);
		model.addAttribute("item",entity);
	
		return "admin/merchantlimit/detail";
	}
	
	/** 校验商户编号 */
	@RequestMapping(value="/checkMerchantNo",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject checkMerchantNo(HttpServletRequest request,String merchantNo){	
		MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
		merchantInfoCondition.setMerchantNo(merchantNo);
		List<MerchantInfo> merchantList =  merchantInfoService.findAll(merchantInfoCondition);
		JSONObject json = new JSONObject();
		if(merchantList.size() > 0){
        	json.put("check", true);
        }else{
        	json.put("check", false);
        }
		return json;
	}
	/** 校验商户的限额通道和限额类型 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject check(HttpServletRequest request,String merchantNo,String limitPayCode,String limitType,String id){	
		MerchantLimitCondition merchantLimitCondition = new MerchantLimitCondition();
		merchantLimitCondition.setMerchantNo(merchantNo);
		merchantLimitCondition.setLimitPayCode(limitPayCode);
		merchantLimitCondition.setLimitType(limitType);
		
        List<MerchantLimit> list = merchantLimitService.findAll(merchantLimitCondition);
        JSONObject json = new JSONObject();
        for (Iterator<MerchantLimit> iterator = list.iterator(); iterator.hasNext();) {
        	MerchantLimit merchantLimit = (MerchantLimit) iterator.next();
			if(!merchantLimit.getId().equals(id)){
				json.put("check", false);
				return json;
			}
		}
        json.put("check", true);
		return json;
	}
}

