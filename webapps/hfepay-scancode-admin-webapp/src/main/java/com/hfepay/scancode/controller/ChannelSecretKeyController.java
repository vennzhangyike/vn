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
import com.hfepay.commons.entity.RecordStatus;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.pay.service.ChannelSecretKeyService;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelSecretKeyCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelSecretKey;
import com.hfepay.scancode.service.operator.ChannelBaseService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/channelsecretkey")
public class ChannelSecretKeyController extends BaseController{
	
	@Autowired
	private ChannelSecretKeyService channelSecretKeyService;
	
	@Autowired
	private ChannelBaseService channelBaseService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		List<ChannelBase> channelList = channelBaseService.findAll(new ChannelBaseCondition());
		request.setAttribute("channelList", channelList);
		return "admin/channelsecretkey/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,ChannelSecretKeyCondition channelSecretKeyCondition){		
		if(Strings.isBlank(channelSecretKeyCondition.getChannelNo())){
			channelSecretKeyCondition.setChannelNo(channelSecretKeyCondition.getCompanyName());
		}
		//channelSecretKeyCondition.setCompanyName("");
		PagingResult<ChannelSecretKey> page = channelSecretKeyService.findPagingResult(channelSecretKeyCondition);
		Pager<ChannelSecretKey> pager = new Pager<ChannelSecretKey>();
		pager.setPageNo(channelSecretKeyCondition.getPageNo());
		pager.setPageSize(channelSecretKeyCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/channelsecretkey/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,ChannelSecretKeyCondition channelSecretKeyCondition){		
        List<ChannelSecretKey> list = channelSecretKeyService.findAll(channelSecretKeyCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			ChannelSecretKey entity = channelSecretKeyService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		List<ChannelBase> channelInfo = channelBaseService.findAll(new ChannelBaseCondition());
		model.addAttribute("channelInfo", channelInfo);
//		List payCodeList = InterfaceCode.toList();
//		model.addAttribute("payCodeList", payCodeList);
		return "admin/channelsecretkey/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,ChannelSecretKeyCondition channelSecretKeyCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		channelSecretKeyCondition.setOperatorId(user.getId());
		try {
			if(Strings.isEmpty(channelSecretKeyCondition.getId())){
				//新增
				channelSecretKeyCondition.setCreateTime(new Date());
				channelSecretKeyCondition.setId(Strings.getUUID());
				channelSecretKeyCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				channelSecretKeyCondition.setStatus(Constants.SUCCESS_STATE);
				channelSecretKeyService.insert(channelSecretKeyCondition);
			}else{
				channelSecretKeyCondition.setUpdateTime(new Date());
				channelSecretKeyService.update(channelSecretKeyCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/channelsecretkey");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
			e.printStackTrace();
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,ChannelSecretKeyCondition channelSecretKeyCondition){		
		Admin user = (Admin) request.getSession().getAttribute("currentUser");
		channelSecretKeyCondition.setOperatorId(user.getId());
		channelSecretKeyCondition.setRecordStatus(RecordStatus.LOCKED);
		channelSecretKeyCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			channelSecretKeyService.update(channelSecretKeyCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
			e.printStackTrace();
		}
		return JSONSerializer.toJSON(map);
	}
	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		ChannelSecretKey entity = channelSecretKeyService.findById(id);
		model.addAttribute("item",entity);
		return "admin/channelsecretkey/detail";
	}
	
	/**
	 * 状态变更
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSON updateStatus(HttpServletRequest request,ChannelSecretKeyCondition channelSecretKeyCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			channelSecretKeyService.updateStatus(channelSecretKeyCondition.getId(),channelSecretKeyCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
			e.printStackTrace();
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 校验渠道编号是否已经存在秘钥 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject check(HttpServletRequest request,String channelNo,String id){	
		ChannelSecretKeyCondition channelSecretKeyCondition = new ChannelSecretKeyCondition();
		channelSecretKeyCondition.setChannelNo(channelNo);
        List<ChannelSecretKey> list = channelSecretKeyService.findAll(channelSecretKeyCondition);
        JSONObject json = new JSONObject();
        for (Iterator<ChannelSecretKey> iterator = list.iterator(); iterator.hasNext();) {
        	ChannelSecretKey channelSecretKey = (ChannelSecretKey) iterator.next();
			if(!channelSecretKey.getId().equals(id)){
				json.put("check", false);
				return json;
			}
		}
        json.put("check", true);
		return json;
	}
}

