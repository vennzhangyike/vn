/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
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
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.ScanBaseController;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.service.contants.ConfigPreCode;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantStoreService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantstore")
public class MerchantStoreController extends ScanBaseController{
	
	@Autowired
	private MerchantStoreService merchantStoreService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private ChannelExpandService channelExpandService;
	
	@Autowired
	private IdCreateService idCreateService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/merchantstore/list";
	}
	
	/**
	 * 列表显示
	 * @param request merchantStoreCondition
	 * 
	 * @author panq
	 * @return page
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantStoreCondition merchantStoreCondition){	
		PagingResult<MerchantStore> page = merchantStoreService.findPagingResult(merchantStoreCondition);
		Pager<MerchantStore> pager = new Pager<MerchantStore>();
		pager.setPageNo(merchantStoreCondition.getPageNo());
		pager.setPageSize(merchantStoreCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantstore/listContent";
	}
	
	/**
	 * 列表显示(不分页)
	 * @param request merchantStoreCondition
	 * 
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantStoreCondition merchantStoreCondition){
        List<MerchantStore> list = merchantStoreService.findAll(merchantStoreCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/**
	 * 进入新增、更新页面
	 * @param 主键,商户编号
	 * 
	 * @author panq
	 * @return page
	 */
	@RequestMapping(value="/edit/{id}/{merNo}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id,@PathVariable String merNo) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantStore entity = merchantStoreService.findById(id);
			model.addAttribute("Obj", entity);
		}
		
		MerchantInfo entity = merchantInfoService.findByMerchantNo(merNo);
		model.addAttribute("mer", entity);
		return "admin/merchantstore/edit";
	}

	/**
	 * 新增、更新
	 * @param merchantStoreCondition
	 * 
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,MerchantStoreCondition merchantStoreCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(Strings.isNotEmpty(merchantStoreCondition.getAuditOperator())){
			merchantStoreCondition.setAuditOperator(getCurrentUserName());
		}else{
			merchantStoreCondition.setOperator(getCurrentUserName());
		}
		try {
			if(Strings.isEmpty(merchantStoreCondition.getId())){
				//新增
				merchantStoreCondition.setCreateTime(new Date());
				merchantStoreCondition.setId(Strings.getUUID());
				merchantStoreCondition.setStoreStatus(Constants.TREATMENT_STATUS_SUCCESS);
				merchantStoreCondition.setRecordStatus(Constants.Y);
				
				//设置门店编号
				MerchantInfo entity = merchantInfoService.findByMerchantNo(merchantStoreCondition.getMerchantNo());
				ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
				channelExpandCondition.setChannelNo(entity.getChannelNo());
				ChannelExpand channelExpand = channelExpandService.findAll(channelExpandCondition).get(0);
				merchantStoreCondition.setStoreNo(idCreateService.createParamNo(channelExpand.getChannelPreCode() + ConfigPreCode.STORE_PRE_CODE));
				
				merchantStoreService.insert(merchantStoreCondition);
			}else{
				merchantStoreService.update(merchantStoreCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantstore");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 门店审核
	 * @param merchantStoreCondition
	 * 
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/audit", method= {RequestMethod.POST})
	@ResponseBody
	public JSON audit(HttpServletRequest request,ModelMap model,MerchantStoreCondition merchantStoreCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		merchantStoreCondition.setAuditOperator(getCurrentUserName());
		try {
			merchantStoreCondition.setAuditDate(new Date());
			merchantStoreService.update(merchantStoreCondition);
			
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantstore");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 查看详情 
	 * @param 主键
	 * 
	 * @author panq
	 * @return page
	 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MerchantStore entity = merchantStoreService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantstore/detail";
	}
	
	/**设置门店redis*/
	@RequestMapping(value="/set/storeRedis",method=RequestMethod.GET)
	@ResponseBody
	public JSON setStoreRedis(HttpServletRequest request){
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantStoreService.setStoreRedis();			
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
}

