/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */
package com.hfepay.scancode.controller;

import java.util.Collections;
import java.util.Comparator;
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
import com.hfepay.scancode.commons.MessagePushTime;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.MessagePushLogCondition;
import com.hfepay.scancode.commons.contants.MessagePushType;
import com.hfepay.scancode.commons.contants.MessagePushWay;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.MessagePushLog;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.MessagePushLogService;

@Controller
@RequestMapping("/adminManage/messagepushlog")
public class MessagePushLogController extends BaseController{
	
	@Autowired
	private MessagePushLogService messagePushLogService;
	@Autowired
	private MappingDicionService mappingDicionService;
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2017-02-21 19:46:12
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
		return "admin/messagepushlog/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param MessagePushLogCondition
	 * @return: String
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,MessagePushLogCondition messagePushLogCondition){		
		PagingResult<MessagePushLog> page = messagePushLogService.findPagingResult(messagePushLogCondition);
		Pager<MessagePushLog> pager = new Pager<MessagePushLog>();
		pager.setPageNo(messagePushLogCondition.getPageNo());
		pager.setPageSize(messagePushLogCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/messagepushlog/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param MessagePushLogCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MessagePushLogCondition messagePushLogCondition){		
        List<MessagePushLog> list = messagePushLogService.findAll(messagePushLogCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param MessagePushLogCondition
	 * @return: String
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
//	@RequestMapping(value="/save", method= {RequestMethod.POST})
//	public String saveOrUpdateById(ModelMap model,MessagePushLogCondition messagePushLogCondition) throws Exception {
//		long flag = 0;
//		if(Strings.isEmpty(messagePushLogCondition.getId())){
//			//新增
//			flag = messagePushLogService.insert(messagePushLogCondition);
//		}else{
//			flag = messagePushLogService.update(messagePushLogCondition);
//		}
//        
//        if(flag > 0){
//        	return LIST_ACTION;
//        }
//		return "user/messagepushlog/edit";
//	}
	
	/**
	 * @Title: show
	 * @Description: 查询单个实体
	 * @author: Ricky
	 * @param id
	 * @return: String
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
//	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
//	public String show(ModelMap model,@PathVariable String id) throws Exception {
//		if(!"0".equalsIgnoreCase(id)){
//			MessagePushLog entity = messagePushLogService.findById(id);
//			 model.addAttribute("Obj", entity);
//		}
//		return "admin/messagepushlog/edit";
//	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param MessagePushLogCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-02-21 19:46:12
	 */
//	@RequestMapping(value="/del",method=RequestMethod.POST)
//	@ResponseBody
//	public JSONObject del(HttpServletRequest request,MessagePushLogCondition messagePushLogCondition){		
//        long flag = messagePushLogService.deleteById(messagePushLogCondition.getId());
//        JSONObject json = new JSONObject();
//        if(flag == 0){
//        	json.put("message", Constants.FAIL_MSG);
//        	json.put("status", Constants.FAIL_STATE);
//        }else{
//        	json.put("message", Constants.SUCCESS_MSG);
//        	json.put("status", Constants.SUCCESS_STATE);
//        }
//		return json;
//	}
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MessagePushLog entity = messagePushLogService.findById(id);
		model.addAttribute("item",entity);
		model.addAttribute("messageType", MessagePushType.toList());  
		model.addAttribute("pushWay", MessagePushWay.toList());  
		model.addAttribute("pushTime", MessagePushTime.toList());  
		return "admin/messagepushlog/detail";
	}
}

