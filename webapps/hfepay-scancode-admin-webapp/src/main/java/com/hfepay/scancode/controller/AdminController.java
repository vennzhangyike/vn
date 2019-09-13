package com.hfepay.scancode.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.SysResourceCondition;
import com.hfepay.scancode.commons.condition.UserMessageCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.Message;
import com.hfepay.scancode.commons.entity.SysResource;
import com.hfepay.scancode.commons.entity.UserMessage;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MessageService;
import com.hfepay.scancode.service.operator.PageService;
import com.hfepay.scancode.service.operator.UserMessageService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;


@Controller
@RequestMapping("/adminManage/index")
public class AdminController {
	
	public static final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private PageService pageService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private UserMessageService userMessageService;
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping
	public String toSignin(HttpServletRequest request,String returnUrl) {
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		MerchantInfoCondition condition = new MerchantInfoCondition();
		condition.setStatus("0");
		long merchantCount = merchantInfoService.countByCriteria(condition);
		condition.setStatus("1");
		merchantCount = merchantCount+merchantInfoService.countByCriteria(condition);
		request.getSession().setAttribute("merchantList",merchantCount);
		
		List<String> userList = new ArrayList<String>();
		userList.add("0");
		userList.add("9");
		List<Message> messageList = messageService.findAllByUserType(userList);
		if(messageList != null && messageList.size() > 0){
			for (Iterator<Message> iterator = messageList.iterator(); iterator.hasNext();) {
				Message message = (Message) iterator.next();
				UserMessageCondition userMessageCondition = new UserMessageCondition();
				userMessageCondition.setId(Strings.getUUID());
				userMessageCondition.setMessageId(message.getId());
				userMessageCondition.setUserNo(user.getUserName());
				userMessageCondition.setReadStatus("1");
				userMessageCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				userMessageCondition.setOperator(user.getUserName());
				userMessageCondition.setCreateTime(new Date());
				userMessageService.insert(userMessageCondition);
			}
		}
		return "admin/index";
	}
	@RequestMapping("/dashbord1")
	public String toDash1(HttpServletRequest request,String returnUrl) {
		return "admin/index_2";
	}
	@RequestMapping("/dashbord2")
	public String toDash2(HttpServletRequest request,String returnUrl) {
		return "admin/index_3";
	}
	
	@RequestMapping("/page")
	public String toPage(HttpServletRequest request,String returnUrl) {
		return "admin/table_advanced";
	}
	@RequestMapping("/showPages")
	public String showPages(HttpServletRequest request,SysResourceCondition condition){
		PagingResult<SysResource> list = pageService.findAllMenu(condition);
        request.setAttribute("pageData",list.getResult());
		return "admin/tableContent"; 
	}
	
	@RequestMapping("/page/viewDetail")
	public String viewDetail(HttpServletRequest request){
		return "admin/viewDetail"; 
	}
	
	@RequestMapping(value="/message",method=RequestMethod.POST)
	@ResponseBody
	public JSON message(HttpServletRequest request){
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		if(user != null){
			List<String> userList = new ArrayList<String>();
			userList.add("0");
			userList.add("9");
			List<Message> messageList = messageService.findAllByUserTypeAndUserNo(userList, user.getUserName());
			if(messageList != null && messageList.size() > 0){
				for (Iterator<Message> iterator = messageList.iterator(); iterator.hasNext();) {
					Message message = (Message) iterator.next();
					UserMessageCondition userMessageCondition = new UserMessageCondition();
					userMessageCondition.setId(Strings.getUUID());
					userMessageCondition.setMessageId(message.getId());
					userMessageCondition.setUserNo(user.getUserName());
					userMessageCondition.setReadStatus("1");
					userMessageCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
					userMessageCondition.setOperator(user.getUserName());
					userMessageCondition.setCreateTime(new Date());
					userMessageService.insert(userMessageCondition);
				}
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			UserMessageCondition userMessageCondition = new UserMessageCondition();
			userMessageCondition.setReadStatus("1");
			userMessageCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
			userMessageCondition.setUserNo(user.getUserName());
			List<String> list = new ArrayList<String>();
			list.add("0");
			list.add("9");
			PagingResult<UserMessage> page = userMessageService.findByUserType(userMessageCondition,list);
			List<UserMessage> userMessageList = page.getResult();
			int messageSize = 0;
			if(userMessageList.size() > 0){
				messageSize = userMessageList.size();
			}
	        map = Maps.mapByAarray("messageSize",messageSize);
	        return JSONSerializer.toJSON(map);
		}
		return null;
	}
	
}
