/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
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
import com.hfepay.scancode.commons.condition.HfepayCategoryCondition;
import com.hfepay.scancode.commons.entity.HfepayCategory;
import com.hfepay.scancode.service.operator.HfepayCategoryService;

@Controller
@RequestMapping("/adminManage/hfepaycategory")
public class HfepayCategoryController extends BaseController{
	
	@Autowired
	private HfepayCategoryService hfepayCategoryService;
	
	private final String LIST_ACTION = "redirect:/hfepaycategory";
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "user/hfepaycategory/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param HfepayCategoryCondition
	 * @return: String
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,HfepayCategoryCondition hfepayCategoryCondition){		
		PagingResult<HfepayCategory> page = hfepayCategoryService.findPagingResult(hfepayCategoryCondition);
		Pager<HfepayCategory> pager = new Pager<HfepayCategory>();
		pager.setPageNo(hfepayCategoryCondition.getPageNo());
		pager.setPageSize(hfepayCategoryCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "user/hfepaycategory/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param HfepayCategoryCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,HfepayCategoryCondition hfepayCategoryCondition){		
        List<HfepayCategory> list = hfepayCategoryService.findAll(hfepayCategoryCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param HfepayCategoryCondition
	 * @return: String
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	public String saveOrUpdateById(ModelMap model,HfepayCategoryCondition hfepayCategoryCondition) throws Exception {
		long flag = 0;
		if(Strings.isEmpty(hfepayCategoryCondition.getId())){
			//新增
			flag = hfepayCategoryService.insert(hfepayCategoryCondition);
		}else{
			flag = hfepayCategoryService.update(hfepayCategoryCondition);
		}
        
        if(flag > 0){
        	return LIST_ACTION;
        }
		return "user/hfepaycategory/edit";
	}
	
	/**
	 * @Title: show
	 * @Description: 查询单个实体
	 * @author: Ricky
	 * @param id
	 * @return: String
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			HfepayCategory entity = hfepayCategoryService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/hfepaycategory/edit";
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param HfepayCategoryCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,HfepayCategoryCondition hfepayCategoryCondition){		
        long flag = hfepayCategoryService.deleteById(hfepayCategoryCondition.getId());
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
	 * @param HfepayCategoryCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-11-01 11:39:57
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,HfepayCategoryCondition hfepayCategoryCondition){		
        long flag = hfepayCategoryService.updateStatus(hfepayCategoryCondition.getId(),hfepayCategoryCondition.getStatus());
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

