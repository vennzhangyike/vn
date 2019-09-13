/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */
package com.hfepay.scancode.channel.controller;

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
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.AdviertisementInfoCondition;
import com.hfepay.scancode.commons.condition.CityCondition;
import com.hfepay.scancode.commons.condition.ProvinceCondition;
import com.hfepay.scancode.commons.entity.AdviertisementInfo;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.City;
import com.hfepay.scancode.commons.entity.Province;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.operator.AdviertisementInfoService;
import com.hfepay.scancode.service.operator.CityService;
import com.hfepay.scancode.service.operator.ProvinceService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/adviertisementinfo")
public class AdviertisementInfoController extends BaseController{
	
	@Autowired
	private AdviertisementInfoService adviertisementInfoService;
	@Autowired
	private ProvinceService provinceService;
	@Autowired
	private CityService cityService;
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "admin/adviertisementinfo/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param AdviertisementInfoCondition
	 * @return: String
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,AdviertisementInfoCondition adviertisementInfoCondition){	
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			adviertisementInfoCondition.setOrganNo(user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			adviertisementInfoCondition.setOrganNo(user.getAgentNo());
		}else if(Constants.IDENTITYFLAG_MERCHANT.equals(user.getIdentityFlag())){
			adviertisementInfoCondition.setOrganNo(user.getMerchantNo());
		}
		PagingResult<AdviertisementInfo> page = adviertisementInfoService.findPagingResult(adviertisementInfoCondition);
		Pager<AdviertisementInfo> pager = new Pager<AdviertisementInfo>();
		pager.setPageNo(adviertisementInfoCondition.getPageNo());
		pager.setPageSize(adviertisementInfoCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/adviertisementinfo/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param AdviertisementInfoCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,AdviertisementInfoCondition adviertisementInfoCondition){		
        List<AdviertisementInfo> list = adviertisementInfoService.findAll(adviertisementInfoCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param AdviertisementInfoCondition
	 * @return: String
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON saveOrUpdateById(HttpServletRequest request,ModelMap model,AdviertisementInfoCondition adviertisementInfoCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		adviertisementInfoCondition.setOperator(user.getUserName());
		
		String beginTimeStr = (String)request.getParameter("beginTimeStr");
		if(beginTimeStr != ""){
			Date beginTime = Dates.parse("yyyy-MM-dd HH:mm", beginTimeStr);
			adviertisementInfoCondition.setStartDate(beginTime);
		}
		String endTimeStr = (String)request.getParameter("endTimeStr");
		if(endTimeStr != ""){
			Date endTime = Dates.parse("yyyy-MM-dd HH:mm", endTimeStr);
			adviertisementInfoCondition.setEndDate(endTime);
		}
		if(Strings.isEmpty(adviertisementInfoCondition.getOrganNo())){
			adviertisementInfoCondition.setOrganNo(Constants.UNLIMITED);
		}
		if(Strings.isEmpty(adviertisementInfoCondition.getAdviertScope())){
			adviertisementInfoCondition.setAdviertScope(Constants.UNLIMITED);
		}
		
		try {
			if(Strings.isEmpty(adviertisementInfoCondition.getId())){
				adviertisementInfoCondition.setAdviertNo(adviertisementInfoService.getAdviertNo());
				adviertisementInfoCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				adviertisementInfoCondition.setStatus(Constants.SUCCESS_STATE);
				adviertisementInfoCondition.setCreateTime(new Date());
				//新增
				adviertisementInfoService.insert(adviertisementInfoCondition);
			}else{
				adviertisementInfoCondition.setStatus(Constants.SUCCESS_STATE);
				adviertisementInfoService.update(adviertisementInfoCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/adviertisementinfo");
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
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			AdviertisementInfo entity = adviertisementInfoService.findById(id);
			model.addAttribute("Obj", entity);

			if (Strings.isNotEmpty(entity.getOrganNo())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("organNo", entity.getOrganNo());
				List<AdviertisementInfo> organList = adviertisementInfoService.getOrganList(map);
				model.addAttribute("organList", organList);
			}
			if (Strings.isNotEmpty(entity.getAdviertScope())) {
				City city = cityService.findById(Integer.parseInt(entity.getAdviertScope()));
				CityCondition cityCondition = new CityCondition();
				if(city != null){
					model.addAttribute("city", city);	
					cityCondition.setPid(city.getPid());
					Province province = provinceService.findById(city.getPid());
					model.addAttribute("province", province);	
				}else{
					Province province = provinceService.findById(Integer.parseInt(entity.getAdviertScope()));
					model.addAttribute("province", province);	
					cityCondition.setPid(Integer.parseInt(entity.getAdviertScope()));
				}
				List<City> cityList = cityService.findAll(cityCondition);
		        model.addAttribute("cityList", cityList);
			}
		}
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		String organNo = "";
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			organNo = user.getChannelCode();
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			organNo = user.getAgentNo();
		}else if(Constants.IDENTITYFLAG_MERCHANT.equals(user.getIdentityFlag())){
			organNo = user.getMerchantNo();
		}
		Map<String, String> map = new HashMap<String, String>();
		model.addAttribute("organNo",organNo);
		map.put("organNo", organNo);
		List<AdviertisementInfo> list = adviertisementInfoService.getOrganList(map);
		if(list.size() == 1){
			model.addAttribute("organName", list.get(0).getOrganName());
		}
		ProvinceCondition provinceCondition = new ProvinceCondition();
		List<Province> provinceList = provinceService.findAll(provinceCondition);
		model.addAttribute("provinceList", provinceList);		
		return "admin/adviertisementinfo/edit";
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param AdviertisementInfoCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,AdviertisementInfoCondition adviertisementInfoCondition){		
        long flag = adviertisementInfoService.deleteById(adviertisementInfoCondition.getId());
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
	 * @param AdviertisementInfoCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-01-06 10:31:38
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,AdviertisementInfoCondition adviertisementInfoCondition){		
        long flag = adviertisementInfoService.updateStatus(adviertisementInfoCondition.getId(),adviertisementInfoCondition.getStatus());
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
	
	/** 通过机构名称获取机构列表 */
	@RequestMapping(value="/getorganlist",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getOrganList(HttpServletRequest request,String organName){	
		Map<String, String> map = new HashMap<String, String>();
		map.put("organName", organName);
        List<AdviertisementInfo> list = adviertisementInfoService.getOrganList(map);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String showDetail(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		AdviertisementInfo entity = adviertisementInfoService.findById(id);
		model.addAttribute("item",entity);
		if (Strings.isNotEmpty(entity.getOrganNo())) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("organNo", entity.getOrganNo());
			List<AdviertisementInfo> organList = adviertisementInfoService.getOrganList(map);
			if(organList.size() == 1){
				model.addAttribute("organ", organList.get(0));
			}
		}
		if (Strings.isNotEmpty(entity.getAdviertScope())) {
			City city = cityService.findById(Integer.parseInt(entity.getAdviertScope()));
			CityCondition cityCondition = new CityCondition();
			if(city != null){
				model.addAttribute("city", city);	
				cityCondition.setPid(city.getPid());
				Province province = provinceService.findById(city.getPid());
				model.addAttribute("province", province);	
			}else{
				Province province = provinceService.findById(Integer.parseInt(entity.getAdviertScope()));
				model.addAttribute("province", province);	
				cityCondition.setPid(Integer.parseInt(entity.getAdviertScope()));
			}
			List<City> cityList = cityService.findAll(cityCondition);
	        model.addAttribute("cityList", cityList);
		}
		return "admin/adviertisementinfo/detail";
	}
	
	/** 通过机构名称获取机构列表 */
	@RequestMapping(value="/getcitylist",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getCityList(HttpServletRequest request,String pid){	
		JSONObject json = new JSONObject();
		if(Strings.isNotEmpty(pid)){
			CityCondition cityCondition = new CityCondition();
			cityCondition.setPid(Integer.parseInt(pid));
	        List<City> cityList = cityService.findAll(cityCondition);
	        json.put("cityList", cityList);
		}        
		return json;
	}
	
}

