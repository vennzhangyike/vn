/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.util.Arrays;
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
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.operator.MerchantCashierQrService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantcashierqr")
public class MerchantCashierQrController extends BaseController{
	
	@Autowired
	private MerchantCashierQrService merchantCashierQrService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/merchantcashierqr/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantCashierQrCondition merchantCashierQrCondition){
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		merchantCashierQrCondition.setNodeSeq(user.getNodeSeq());
		merchantCashierQrCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		PagingResult<MerchantCashierQr> page = merchantCashierQrService.findPagingResult(merchantCashierQrCondition);
		Pager<MerchantCashierQr> pager = new Pager<MerchantCashierQr>();
		pager.setPageNo(merchantCashierQrCondition.getPageNo());
		pager.setPageSize(merchantCashierQrCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantcashierqr/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantCashierQrCondition merchantCashierQrCondition){
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		merchantCashierQrCondition.setNodeSeq(user.getNodeSeq());
		merchantCashierQrCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
        List<MerchantCashierQr> list = merchantCashierQrService.findAll(merchantCashierQrCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantCashierQr entity = merchantCashierQrService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/merchantcashierqr/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,MerchantCashierQrCondition merchantCashierQrCondition,String ids) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		merchantCashierQrService.deleteByCriteria(merchantCashierQrCondition);
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		merchantCashierQrCondition.setOperator(user.getId());
		try {
			if(!Strings.isEmpty(ids)){
				if(ids.indexOf(",")>0){
					String[] subIds = ids.split(",");
					List<String> subList = Arrays.asList(subIds);
					for (Iterator<String> iterator = subList.iterator(); iterator.hasNext();) {
						String qrcode = (String) iterator.next();
						//新增
						merchantCashierQrCondition.setQrCode(qrcode);
						merchantCashierQrCondition.setIsCashier("1");
						merchantCashierQrCondition.setCreateTime(new Date());
						merchantCashierQrCondition.setId(Strings.getUUID());
						merchantCashierQrCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
						merchantCashierQrCondition.setStatus(Constants.SUCCESS_STATE);
						merchantCashierQrService.insert(merchantCashierQrCondition);
						map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantcashierqr");
					}
				}else{
					//新增
					merchantCashierQrCondition.setQrCode(ids);
					merchantCashierQrCondition.setIsCashier("1");
					merchantCashierQrCondition.setCreateTime(new Date());
					merchantCashierQrCondition.setId(Strings.getUUID());
					merchantCashierQrCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
					merchantCashierQrCondition.setStatus(Constants.SUCCESS_STATE);
					merchantCashierQrService.insert(merchantCashierQrCondition);
					map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantcashierqr");
				}
			}
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,MerchantCashierQrCondition merchantCashierQrCondition){		
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		merchantCashierQrCondition.setOperator(user.getId());
		merchantCashierQrCondition.setRecordStatus(Constants.RECORD_STATUS_NO);
		merchantCashierQrCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantCashierQrService.updateByCriteria(merchantCashierQrCondition);
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
	public JSON updateStatus(HttpServletRequest request,MerchantCashierQrCondition merchantCashierQrCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantCashierQrService.updateStatus(merchantCashierQrCondition.getId(),merchantCashierQrCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MerchantCashierQr entity = merchantCashierQrService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantcashierqr/detail";
	}
}

