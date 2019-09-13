/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */
package com.hfepay.scancode.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.MessagePushTime;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.MessagePushRuleCondition;
import com.hfepay.scancode.commons.contants.MessagePushType;
import com.hfepay.scancode.commons.contants.MessagePushWay;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.MessagePushRule;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.MessagePushRuleService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/messagepushrule")
public class MessagePushRuleController extends BaseController{
	
	@Autowired
	private MessagePushRuleService messagePushRuleService;
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private AgentBaseService agentBaseService;
	@Autowired
	private MappingDicionService mappingDicionService;
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(ModelMap model) {
		model.addAttribute("messageType", MessagePushType.toList());  
		model.addAttribute("pushWay", MessagePushWay.toList());  
		model.addAttribute("pushTime", MessagePushTime.toList());  
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo("DLSJB");
		List<MappingDicion> agentLevelList = mappingDicionService.findAll(mappingDicionCondition);
		Collections.sort(agentLevelList,new Comparator<MappingDicion>(){
            public int compare(MappingDicion arg0, MappingDicion arg1) {
                return arg0.getParaVal().compareTo(arg1.getParaVal());
            }
        });
		model.addAttribute("temp1", agentLevelList);
		return "admin/messagepushrule/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param MessagePushRuleCondition
	 * @return: String
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,MessagePushRuleCondition messagePushRuleCondition){		
		PagingResult<MessagePushRule> page = messagePushRuleService.findPagingResult(messagePushRuleCondition);
		Pager<MessagePushRule> pager = new Pager<MessagePushRule>();
		pager.setPageNo(messagePushRuleCondition.getPageNo());
		pager.setPageSize(messagePushRuleCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/messagepushrule/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param MessagePushRuleCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MessagePushRuleCondition messagePushRuleCondition){		
        List<MessagePushRule> list = messagePushRuleService.findAll(messagePushRuleCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param MessagePushRuleCondition
	 * @return: String
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON saveOrUpdateById(HttpServletRequest request,ModelMap model,MessagePushRuleCondition messagePushRuleCondition) throws Exception {
		
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		messagePushRuleCondition.setOperator(user.getUserName());
		
		String firstPushDateStr = (String)request.getParameter("firstPushDateStr");
		if(firstPushDateStr != ""){
			Date firstPushDate = Dates.parse("yyyy-MM-dd HH:mm", firstPushDateStr);
			messagePushRuleCondition.setFirstPushDate(firstPushDate);
			Calendar cl = Calendar.getInstance();
		    cl.setTime(firstPushDate);
		    cl.add(Calendar.DATE,Integer.valueOf(messagePushRuleCondition.getPushTime()));		    
		    messagePushRuleCondition.setNextPushDate(cl.getTime());
		    
		}
		
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			if(Strings.isEmpty(messagePushRuleCondition.getId())){
				messagePushRuleCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
			    messagePushRuleCondition.setStatus(Constants.SUCCESS_STATE);
			    messagePushRuleCondition.setCreateTime(new Date());
				//新增
				messagePushRuleService.insert(messagePushRuleCondition);
			}else{
				messagePushRuleService.update(messagePushRuleCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/messagepushrule");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * @Title: show
	 * @Description: 查询单个实体
	 * @author: Ricky
	 * @param id
	 * @return: String
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MessagePushRule entity = messagePushRuleService.findById(id);
			model.addAttribute("Obj", entity);
			ChannelBase channelBase = channelBaseService.findByChannelNo(entity.getChannelNo());
			List<ChannelBase> channelList = new ArrayList<ChannelBase>();
			channelList.add(channelBase);
			model.addAttribute("channelList", channelList);
			if(Strings.isNotEmpty(entity.getAgentNo())){
				AgentBase agentBase = agentBaseService.findByAgentNo(entity.getAgentNo());
				List<AgentBase> agentList = new ArrayList<AgentBase>();
				agentList.add(agentBase);
				model.addAttribute("agentList", agentList);
			}
			
		}
		model.addAttribute("messageType", MessagePushType.toList());  
		model.addAttribute("pushWay", MessagePushWay.toList());  
		model.addAttribute("pushTime", MessagePushTime.toList());  
		
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo("DLSJB");
		List<MappingDicion> agentLevelList = mappingDicionService.findAll(mappingDicionCondition);
		Collections.sort(agentLevelList,new Comparator<MappingDicion>(){
            public int compare(MappingDicion arg0, MappingDicion arg1) {
                return arg0.getParaVal().compareTo(arg1.getParaVal());
            }
        });
		model.addAttribute("temp1", agentLevelList);
		return "admin/messagepushrule/edit";
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param MessagePushRuleCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,MessagePushRuleCondition messagePushRuleCondition){		
        long flag = messagePushRuleService.deleteById(messagePushRuleCondition.getId());
        JSONObject json = new JSONObject();
        if(flag == 0){
        	json.put("message", Constants.FAIL_MSG);
        	json.put("status", Constants.FAIL_STATE);
        }else{
        	json.put("message", Constants.SUCCESS_MSG);
        	json.put("status", Constants.SUCCESS_STATE);
        }
		return json;
	}
	


	/**
	 * @Title: saveOrUpdateById
	 * @Description: 状态变更
	 * @author: wh
	 * @param MessagePushRuleCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-02-16 18:11:14
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,MessagePushRuleCondition messagePushRuleCondition){		
        long flag = messagePushRuleService.updateStatus(messagePushRuleCondition.getId(),messagePushRuleCondition.getStatus());
        JSONObject json = new JSONObject();
        if(flag == 0){
        	json.put("message", Constants.FAIL_MSG);
        	json.put("status", Constants.FAIL_STATE);
        }else{
        	json.put("message", Constants.SUCCESS_MSG);
        	json.put("status", Constants.SUCCESS_STATE);
        }
		return json;
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MessagePushRule entity = messagePushRuleService.findById(id);
		model.addAttribute("item",entity);
		return "admin/messagepushrule/detail";
	}
	
}

