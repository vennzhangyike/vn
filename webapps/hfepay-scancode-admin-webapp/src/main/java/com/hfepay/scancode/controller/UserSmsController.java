/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

import java.util.List;

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
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.UserSmsCondition;
import com.hfepay.scancode.commons.entity.UserSms;
import com.hfepay.scancode.service.operator.UserSmsService;

@Controller
@RequestMapping("/adminManage/usersms")
public class UserSmsController extends BaseController{
	
	@Autowired
	private UserSmsService userSmsService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/usersms/list";
	}
	
//	/**
//	 * 列表显示
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "/content", method= {RequestMethod.POST})
//	public String listContent(HttpServletRequest request,ModelMap model,UserSmsCondition userSmsCondition){		
//		PagingResult<UserSms> page = userSmsService.findPagingResult(userSmsCondition);
//		Pager<UserSms> pager = new Pager<UserSms>();
//		pager.setPageNo(userSmsCondition.getPageNo());
//		pager.setPageSize(userSmsCondition.getPageSize());
//		pager.setResult(page.getResult());
//		pager.setTotalCount(page.getTotalCount());
// 		if(pager.getOrder() == null){
//			pager.setOrder("");
//		}
//		model.addAttribute("pager",pager);
//		model.addAttribute("smsMap",userSmsService.getSmsCountDetail(userSmsCondition));
//		return "admin/usersms/listContent";
//	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,UserSmsCondition userSmsCondition){		
        List<UserSms> list = userSmsService.findAll(userSmsCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		UserSms entity = userSmsService.findById(id);
		model.addAttribute("item",entity);
		return "admin/usersms/detail";
	}
}

