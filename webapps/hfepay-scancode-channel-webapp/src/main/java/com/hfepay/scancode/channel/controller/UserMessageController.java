/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

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
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.UserMessageCondition;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.UserMessage;
import com.hfepay.scancode.service.operator.UserMessageService;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/usermessage")
public class UserMessageController extends BaseController{
	
	@Autowired
	private UserMessageService userMessageService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/usermessage/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,UserMessageCondition userMessageCondition){	
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		userMessageCondition.setUserNo(user.getUserName());
		userMessageCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		PagingResult<UserMessage> page = userMessageService.findPagingResult(userMessageCondition);
		Pager<UserMessage> pager = new Pager<UserMessage>();
		pager.setPageNo(userMessageCondition.getPageNo());
		pager.setPageSize(userMessageCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		
		model.addAttribute("pager",pager);
		return "admin/usermessage/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,UserMessageCondition userMessageCondition){		
        List<UserMessage> list = userMessageService.findAll(userMessageCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/messagedetail/{messageId}", method= {RequestMethod.GET})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String messageId) throws Exception {
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		UserMessage entity = userMessageService.findByMessageIdByUserNo(messageId,user.getUserName());
		model.addAttribute("Obj", entity);
		UserMessageCondition userMessageCondition = new UserMessageCondition();
		userMessageCondition.setId(entity.getId());
		userMessageCondition.setReadStatus("2");
		userMessageService.update(userMessageCondition);
		return "admin/usermessage/edit";
	}


	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,String list){
		JSONArray jsonArray=JSONArray.fromObject(list);
		List<String> jsonList = JSONArray.toList(jsonArray);
		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			if(jsonList != null){
				for (Iterator<String> iterator = jsonList.iterator(); iterator.hasNext();) {
					String id = (String) iterator.next();
					UserMessageCondition userMessageCondition = new UserMessageCondition();
					userMessageCondition.setId(id);
					userMessageCondition.setRecordStatus(Constants.RECORD_STATUS_NO);
					userMessageService.update(userMessageCondition);
				}
				map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
			}else{
				map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
			}
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
}

