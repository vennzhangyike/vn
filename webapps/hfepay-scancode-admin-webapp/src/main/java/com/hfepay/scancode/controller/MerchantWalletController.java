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
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.MerchantWalletCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.MerchantWallet;
import com.hfepay.scancode.service.operator.MerchantWalletService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantwallet")
public class MerchantWalletController extends BaseController{
	
	@Autowired
	private MerchantWalletService merchantWalletService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/merchantwallet/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantWalletCondition merchantWalletCondition){		
		PagingResult<MerchantWallet> page = merchantWalletService.findPagingResult(merchantWalletCondition);
		Pager<MerchantWallet> pager = new Pager<MerchantWallet>();
		pager.setPageNo(merchantWalletCondition.getPageNo());
		pager.setPageSize(merchantWalletCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantwallet/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantWalletCondition merchantWalletCondition){		
        List<MerchantWallet> list = merchantWalletService.findAll(merchantWalletCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantWallet entity = merchantWalletService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/merchantwallet/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,MerchantWalletCondition merchantWalletCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		merchantWalletCondition.setOperator(user.getUserName());
		try {
			if(Strings.isEmpty(merchantWalletCondition.getId())){
				//新增
				merchantWalletCondition.setCreateTime(new Date());
				merchantWalletCondition.setId(Strings.getUUID());
				//merchantWalletCondition.setRecordStatus(Constants.Y);
				merchantWalletCondition.setStatus(Constants.SUCCESS_STATE);
				merchantWalletService.insert(merchantWalletCondition);
			}else{
				merchantWalletCondition.setUpdateTime(new Date());
				merchantWalletService.update(merchantWalletCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantwallet");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,MerchantWalletCondition merchantWalletCondition){		
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		merchantWalletCondition.setOperator(user.getUserName());
		//merchantWalletCondition.setRecordStatus(Constants.N);
		merchantWalletCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantWalletService.update(merchantWalletCondition);
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
	public JSON updateStatus(HttpServletRequest request,MerchantWalletCondition merchantWalletCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantWalletService.updateStatus(merchantWalletCondition.getId(),merchantWalletCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MerchantWallet entity = merchantWalletService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantwallet/detail";
	}
}

