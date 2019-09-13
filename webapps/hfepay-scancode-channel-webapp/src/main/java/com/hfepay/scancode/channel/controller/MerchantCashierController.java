/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
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
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.condition.MerchantQrcodeCondition;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;
import com.hfepay.scancode.commons.entity.MerchantQrcode;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.operator.MerchantCashierQrService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantQrcodeService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantcashier")
public class MerchantCashierController extends BaseController{
	
	@Autowired
	private MerchantCashierService merchantCashierService;
	
	@Autowired
	private MerchantQrcodeService merchantQrcodeService;
	
	@Autowired
	private MerchantCashierQrService merchantCashierQrService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/merchantcashier/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantCashierCondition merchantCashierCondition){
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		merchantCashierCondition.setNodeSeq(user.getNodeSeq());
		merchantCashierCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		PagingResult<MerchantCashier> page = merchantCashierService.findPagingResult(merchantCashierCondition);
		Pager<MerchantCashier> pager = new Pager<MerchantCashier>();
		pager.setPageNo(merchantCashierCondition.getPageNo());
		pager.setPageSize(merchantCashierCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantcashier/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantCashierCondition merchantCashierCondition){		
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		merchantCashierCondition.setNodeSeq(user.getNodeSeq());
		merchantCashierCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		List<MerchantCashier> list = merchantCashierService.findAll(merchantCashierCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantCashier entity = merchantCashierService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/merchantcashier/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,MerchantCashierCondition merchantCashierCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		merchantCashierCondition.setOperator(user.getId());
		try {
			if(Strings.isEmpty(merchantCashierCondition.getId())){
				//新增
				merchantCashierCondition.setCreateTime(new Date());
				merchantCashierCondition.setId(Strings.getUUID());
				merchantCashierCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				merchantCashierCondition.setStatus(Constants.SUCCESS_STATE);
				merchantCashierService.insert(merchantCashierCondition);
			}else{
				merchantCashierCondition.setUpdateTime(new Date());
				merchantCashierService.update(merchantCashierCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantcashier");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,String cashierNo){		
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantCashierService.delCashierByCashierNo(cashierNo, user.getId());
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
	public JSON updateStatus(HttpServletRequest request,MerchantCashierCondition merchantCashierCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantCashierService.updateStatus(merchantCashierCondition.getId(),merchantCashierCondition.getStatus(),merchantCashierCondition.getCashierNo());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MerchantCashier entity = merchantCashierService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantcashier/detail";
	}
	/** 设置收款码 */
	@RequestMapping(value="/setting/{cashierNo}", method= {RequestMethod.POST})
	public String setting(HttpServletRequest request,ModelMap model,String storeNo,@PathVariable String cashierNo,String merchantNo) throws Exception {
		MerchantQrcodeCondition merchantQrcodeCondition = new MerchantQrcodeCondition();
		merchantQrcodeCondition.setStoreId(storeNo);
		merchantQrcodeCondition.setMerchantNo(merchantNo);
		List<MerchantQrcode> list = merchantQrcodeService.findAllMerchantQrcode(merchantQrcodeCondition);
		model.addAttribute("objList", list);
		
		MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
		merchantCashierQrCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		merchantCashierQrCondition.setCashierNo(cashierNo);
		merchantCashierQrCondition.setMerchantNo(merchantNo);
		List<MerchantCashierQr> qrList = merchantCashierQrService.findAll(merchantCashierQrCondition);
		model.addAttribute("qrList", qrList);
		model.addAttribute("storeNo", storeNo);
		model.addAttribute("cashierNo", cashierNo);
		model.addAttribute("merchantNo", merchantNo);
		return "admin/merchantcashier/setting";
	}
}

