/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

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
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.HfepayPaywayCondition;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.HfepayPayway;
import com.hfepay.scancode.service.operator.HfepayPaywayService;
import com.hfepay.scancode.service.operator.MappingDicionService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/hfepaypayway")
public class HfepayPaywayController extends BaseController{
	
	@Autowired
	private HfepayPaywayService hfepayPaywayService;
	@Autowired
	private MappingDicionService mappingDicionService;
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "admin/hfepaypayway/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param HfepayPaywayCondition
	 * @return: String
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,HfepayPaywayCondition hfepayPaywayCondition){		
		PagingResult<HfepayPayway> page = hfepayPaywayService.findPagingResult(hfepayPaywayCondition);
		Pager<HfepayPayway> pager = new Pager<HfepayPayway>();
		pager.setPageNo(hfepayPaywayCondition.getPageNo());
		pager.setPageSize(hfepayPaywayCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/hfepaypayway/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param HfepayPaywayCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,HfepayPaywayCondition hfepayPaywayCondition){		
        List<HfepayPayway> list = hfepayPaywayService.findAll(hfepayPaywayCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param HfepayPaywayCondition
	 * @return: String
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON saveOrUpdateById(HttpServletRequest request,ModelMap model,HfepayPaywayCondition hfepayPaywayCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		hfepayPaywayCondition.setOperator(user.getId());
		try {
			if(Strings.isEmpty(hfepayPaywayCondition.getId())){
				hfepayPaywayCondition.setCreateTime(new Date());
				hfepayPaywayCondition.setRecordStatus(Constants.Y);
				//新增
				hfepayPaywayService.insert(hfepayPaywayCondition);
			}else{
				hfepayPaywayService.update(hfepayPaywayCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/hfepaypayway");
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
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			HfepayPayway entity = hfepayPaywayService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo(Constants.PAYWAY_CODE);
		model.addAttribute("mappingDicionList", mappingDicionService.findAll(mappingDicionCondition));
		return "admin/hfepaypayway/edit";
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param HfepayPaywayCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-19 16:13:23
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,HfepayPaywayCondition hfepayPaywayCondition){		
        long flag = hfepayPaywayService.deleteById(hfepayPaywayCondition.getId());
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
		HfepayPayway entity = hfepayPaywayService.findById(id);
		model.addAttribute("item",entity);
		return "admin/hfepaypayway/detail";
	}
	
	/** 校验通道类型与支付方式 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject check(HttpServletRequest request,String payType,String payCode,String id){	
		HfepayPaywayCondition hfepayPaywayCondition = new HfepayPaywayCondition();
		hfepayPaywayCondition.setPayType(payType);
		hfepayPaywayCondition.setPayCode(payCode);
		
        List<HfepayPayway> list = hfepayPaywayService.findAll(hfepayPaywayCondition);
        JSONObject json = new JSONObject();
        for (Iterator<HfepayPayway> iterator = list.iterator(); iterator.hasNext();) {
        	HfepayPayway hfepayPayway = (HfepayPayway) iterator.next();
			if(!hfepayPayway.getId().equals(id)){
				json.put("check", false);
				return json;
			}
		}
        json.put("check", true);
		return json;
	}
}

