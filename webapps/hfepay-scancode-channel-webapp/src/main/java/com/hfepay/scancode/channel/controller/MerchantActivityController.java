/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */
package com.hfepay.scancode.channel.controller;

import java.text.SimpleDateFormat;
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

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.entity.RecordStatus;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.MerchantActivityCondition;
import com.hfepay.scancode.commons.condition.MerchantActivityDiscountCondition;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantActivity;
import com.hfepay.scancode.commons.entity.MerchantActivityDiscount;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.operator.MerchantActivityDiscountService;
import com.hfepay.scancode.service.operator.MerchantActivityService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantactivity")
public class MerchantActivityController extends BaseController{
	
	@Autowired
	private MerchantActivityService merchantActivityService;
	
	@Autowired
	private MerchantActivityDiscountService merchantActivityDiscountService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/merchantactivity/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantActivityCondition merchantActivityCondition){		
		PagingResult<MerchantActivity> page = merchantActivityService.findPagingResult(merchantActivityCondition);
		Pager<MerchantActivity> pager = new Pager<MerchantActivity>();
		pager.setPageNo(merchantActivityCondition.getPageNo());
		pager.setPageSize(merchantActivityCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantactivity/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantActivityCondition merchantActivityCondition){		
        List<MerchantActivity> list = merchantActivityService.findAll(merchantActivityCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantActivity entity = merchantActivityService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟
		Date date = new Date();
		String activityId=sdf.format(date);
		activityId = activityId.replace("-", "").replace(" ", "").replace(":", "");
		
		model.addAttribute("activityId",activityId);
		return "admin/merchantactivity/edit";
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/editDiscount/{activityId}", method= {RequestMethod.GET})
	public String editDiscount(ModelMap model,@PathVariable String activityId) throws Exception {
		MerchantActivityDiscountCondition merchantActivityDiscountCondition = new MerchantActivityDiscountCondition();
		merchantActivityDiscountCondition.setActivityId(activityId);
		List<MerchantActivityDiscount> list = merchantActivityDiscountService.findAll(merchantActivityDiscountCondition);
		model.addAttribute("list", list);
		
		MerchantActivity merchantActivity = merchantActivityService.findByActivityId(activityId);
		model.addAttribute("type", merchantActivity.getActivityType());
		return "admin/merchantactivity/editDiscount";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model, MerchantActivityCondition merchantActivityCondition, String[] array ) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		merchantActivityCondition.setOperator(user.getId());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm");//小写的mm表示的是分钟  

		try {
			if(Strings.isEmpty(merchantActivityCondition.getId())){
				//新增
				merchantActivityCondition.setChannelNo(user.getChannelCode());
				merchantActivityCondition.setAgentNo(user.getAgentNo());
				merchantActivityCondition.setMerchantNo(user.getMerchantNo());
				merchantActivityCondition.setActivityBeginTime(sdf.parse(merchantActivityCondition.getBeginDate()));
				merchantActivityCondition.setActivityEndTime(sdf.parse(merchantActivityCondition.getEndDate()));
				merchantActivityCondition.setCreateTime(new Date());
				merchantActivityCondition.setId(Strings.getUUID());
				merchantActivityCondition.setRecordStatus(Constants.Y);
				merchantActivityCondition.setStatus("2");
				merchantActivityService.insertMerchantActivity(merchantActivityCondition,array);
			}else{
				merchantActivityCondition.setUpdateTime(new Date());
				merchantActivityService.update(merchantActivityCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantactivity");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 新增/更新 */
	@RequestMapping(value="/editDiscount/discountSave", method= {RequestMethod.POST})
	@ResponseBody
	public JSON discountSave(HttpServletRequest request,ModelMap model, String[] array ) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			for (int i = 0; i < array.length; i++) {
				MerchantActivityDiscountCondition merchantActivityDiscountCondition = new MerchantActivityDiscountCondition();
				 JSONObject jsonObject = JSONObject.fromObject(array[i]);
				 String discount = (String) jsonObject.get("from");
				 String condition = (String) jsonObject.get("to");
				 String chance = (String) jsonObject.get("odds");
				 String id = (String)jsonObject.get("id");
				 merchantActivityDiscountCondition.setActivityDiscount(discount);
				 merchantActivityDiscountCondition.setActivityCondition(condition);
				 merchantActivityDiscountCondition.setChance(chance);
				 merchantActivityDiscountCondition.setId(id);
				 merchantActivityDiscountService.update(merchantActivityDiscountCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","merchantactivity");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,MerchantActivityCondition merchantActivityCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantActivityService.deleteMerchantActivityById(merchantActivityCondition.getId(), merchantActivityCondition.getActivityId());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 状态变更
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSON updateStatus(HttpServletRequest request,MerchantActivityCondition merchantActivityCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			if(merchantActivityCondition.getStatus().equals(Constants.SUCCESS_STATE)){
				MerchantActivityCondition activityCondition = new MerchantActivityCondition();
				activityCondition.setStatus(Constants.SUCCESS_STATE);
				activityCondition.setMerchantNo(merchantActivityCondition.getMerchantNo());
				List<MerchantActivity> list = merchantActivityService.findAll(activityCondition);
				if(list != null && list.size() > 0){
					for (Iterator<MerchantActivity> iterator = list.iterator(); iterator.hasNext();) {
						MerchantActivity merchantActivity = (MerchantActivity) iterator.next();
						if(!merchantActivity.getId().equals(merchantActivityCondition.getId())){
							map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"已存在生效状态的市场活动！");
							return JSONSerializer.toJSON(map);
						}
					}
				}else{
					merchantActivityService.updateStatus(merchantActivityCondition.getId(),merchantActivityCondition.getStatus());
					map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
				}
			}else{
				merchantActivityService.updateStatus(merchantActivityCondition.getId(),merchantActivityCondition.getStatus());
				map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
			}
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MerchantActivity entity = merchantActivityService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantactivity/detail";
	}
	@RequestMapping(value="/detail/add/{type}")
	public String toAddPage(HttpServletRequest request,ModelMap model,@PathVariable String type) throws Exception {
		model.addAttribute("type",type);
		return "admin/merchantactivity/addPage";
	}
}

