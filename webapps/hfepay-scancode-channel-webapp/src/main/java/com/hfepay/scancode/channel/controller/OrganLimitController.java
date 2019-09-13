/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */
package com.hfepay.scancode.channel.controller;

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
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.OrganLimitCondition;
import com.hfepay.scancode.commons.entity.OrganLimit;
import com.hfepay.scancode.service.operator.OrganLimitService;

@Controller
@RequestMapping("/adminManage/organlimit")
public class OrganLimitController extends BaseController{
	
	@Autowired
	private OrganLimitService organLimitService;
	
	private final String LIST_ACTION = "redirect:/organlimit";
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "user/organlimit/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param OrganLimitCondition
	 * @return: String
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,OrganLimitCondition organLimitCondition){		
		PagingResult<OrganLimit> page = organLimitService.findPagingResult(organLimitCondition);
		Pager<OrganLimit> pager = new Pager<OrganLimit>();
		pager.setPageNo(organLimitCondition.getPageNo());
		pager.setPageSize(organLimitCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "user/organlimit/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param OrganLimitCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,OrganLimitCondition organLimitCondition){		
        List<OrganLimit> list = organLimitService.findAll(organLimitCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param OrganLimitCondition
	 * @return: String
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	public String saveOrUpdateById(ModelMap model,OrganLimitCondition organLimitCondition) throws Exception {
		long flag = 0;
		if(Strings.isEmpty(organLimitCondition.getId())){
			//新增
			flag = organLimitService.insert(organLimitCondition);
		}else{
			flag = organLimitService.update(organLimitCondition);
		}
        
        if(flag > 0){
        	return LIST_ACTION;
        }
		return "user/organlimit/edit";
	}
	
	/**
	 * @Title: show
	 * @Description: 查询单个实体
	 * @author: Ricky
	 * @param id
	 * @return: String
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			OrganLimit entity = organLimitService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/organlimit/edit";
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param OrganLimitCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,OrganLimitCondition organLimitCondition){		
        long flag = organLimitService.deleteById(organLimitCondition.getId());
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
	 * @param OrganLimitCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,OrganLimitCondition organLimitCondition){		
        long flag = organLimitService.updateStatus(organLimitCondition.getId(),organLimitCondition.getStatus());
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
	
}

