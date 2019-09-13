/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */
package com.hfepay.scancode.controller;

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
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.OrganLimitCondition;
import com.hfepay.scancode.commons.contants.LimitModeEnum;
import com.hfepay.scancode.commons.contants.LimitPayCodeEnum;
import com.hfepay.scancode.commons.contants.LimitTypeEnum;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.AdviertisementInfo;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.OrganLimit;
import com.hfepay.scancode.service.operator.AdviertisementInfoService;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.OrganLimitService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/organlimit")
public class OrganLimitController extends BaseController{
	
	@Autowired
	private OrganLimitService organLimitService;
	@Autowired
	private AdviertisementInfoService adviertisementInfoService;
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(ModelMap model) {
		model.addAttribute("limitType", LimitTypeEnum.toList()); 
		model.addAttribute("limitPayCode", LimitPayCodeEnum.toList()); 
		model.addAttribute("limitMode", LimitModeEnum.toList()); 
		return "admin/organlimit/list";
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
		return "admin/organlimit/listContent";
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
	@ResponseBody
	public JSON saveOrUpdateById(HttpServletRequest request,ModelMap model,OrganLimitCondition organLimitCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		organLimitCondition.setOperator(user.getUserName());
		try {
			if(Strings.isEmpty(organLimitCondition.getId())){
				organLimitCondition.setId(Strings.getUUID());
				organLimitCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				organLimitCondition.setStatus(Constants.SUCCESS_STATE);
				organLimitCondition.setCreateTime(new Date());
				//新增
				organLimitService.insert(organLimitCondition);
			}else{
				organLimitService.update(organLimitCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/organlimit");
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
	 * @date CreateDate : 2017-01-17 15:03:25
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			OrganLimit entity = organLimitService.findById(id);
			 model.addAttribute("Obj", entity);
			 if (Strings.isNotEmpty(entity.getOrganNo())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("organNo", entity.getOrganNo());
				List<AdviertisementInfo> organList = adviertisementInfoService.getOrganList(map);
				model.addAttribute("organList", organList);
			}
		}
		model.addAttribute("limitType", LimitTypeEnum.toList()); 
		model.addAttribute("limitPayCode", LimitPayCodeEnum.toList()); 
		model.addAttribute("limitMode", LimitModeEnum.toList()); 
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
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		OrganLimit entity = organLimitService.findById(id);
		model.addAttribute("item",entity);
		return "admin/organlimit/detail";
	}
	
	/**
	 * 校验最低、最高限额比上级底比下级高
	 * @param request
	 * @param model
	 * @param organLimitCondition
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/checkLimit", method= {RequestMethod.POST})
	@ResponseBody
	public JSON checkLimit(HttpServletRequest request,ModelMap model,OrganLimitCondition organLimitCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		organLimitCondition.setOperator(user.getUserName());
		try {
			map = organLimitService.checkLimit(organLimitCondition);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
        
		return JSONSerializer.toJSON(map);
	}
}

