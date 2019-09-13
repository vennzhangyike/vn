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
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/platformqrcode")
public class PlatformQrcodeController extends BaseController{
	
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	
	@Autowired
	private ChannelExpandService channelExpandService;
	
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/platformqrcode/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,PlatformQrcodeCondition platformQrcodeCondition){		
		PagingResult<PlatformQrcode> page = platformQrcodeService.findPagingResult(platformQrcodeCondition);
		Pager<PlatformQrcode> pager = new Pager<PlatformQrcode>();
		pager.setPageNo(platformQrcodeCondition.getPageNo());
		pager.setPageSize(platformQrcodeCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/platformqrcode/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,PlatformQrcodeCondition platformQrcodeCondition){		
        List<PlatformQrcode> list = platformQrcodeService.findAll(platformQrcodeCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			PlatformQrcode entity = platformQrcodeService.findById(id);
			String organNo = "";
			if (Strings.isNotEmpty(entity.getAgentNo())) {				
				DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(entity.getAgentNo());
				if (null != vo) {
					organNo = vo.getOrganNo();
				}
			}else {
				organNo = entity.getChannelNo();
			}
			ChannelExpand channelExpand = channelExpandService.findByChannelNo(organNo);
			model.addAttribute("Obj", entity);
			model.addAttribute("icon", channelExpand.getIcon());
		}
		return "admin/platformqrcode/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,PlatformQrcodeCondition platformQrcodeCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		platformQrcodeCondition.setOperator(user.getUserName());
		try {
			if(Strings.isEmpty(platformQrcodeCondition.getId())){
				//新增
				platformQrcodeCondition.setCreateTime(new Date());
				platformQrcodeCondition.setId(Strings.getUUID());
				platformQrcodeCondition.setRecordStatus(Constants.Y);
				//platformQrcodeCondition.setStatus(Constants.SUCCESS_STATE);
				platformQrcodeService.insert(platformQrcodeCondition);
			}else{
				platformQrcodeCondition.setUpdateTime(new Date());
				platformQrcodeService.update(platformQrcodeCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/platformqrcode");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,PlatformQrcodeCondition platformQrcodeCondition){		
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		platformQrcodeCondition.setOperator(user.getUserName());
		platformQrcodeCondition.setRecordStatus(Constants.N);
		platformQrcodeCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			platformQrcodeService.update(platformQrcodeCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		PlatformQrcode entity = platformQrcodeService.findById(id);
		String organNo = "";
		if (Strings.isNotEmpty(entity.getAgentNo())) {				
			DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(entity.getAgentNo());
			if (null != vo) {
				organNo = vo.getOrganNo();
			}
		}else {
			organNo = entity.getChannelNo();
		}
		ChannelExpand channelExpand = channelExpandService.findByChannelNo(organNo);
		model.addAttribute("icon", channelExpand.getIcon());
		model.addAttribute("item",entity);
		return "admin/platformqrcode/detail";
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态变更
	 * @author: wh
	 * @param ChannelPaywayCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,PlatformQrcodeCondition platformQrcodeCondition){	
		PlatformQrcodeCondition platformQrcode = new PlatformQrcodeCondition();
		platformQrcode.setId(platformQrcodeCondition.getId());
		platformQrcode.setQrStatus(platformQrcodeCondition.getQrStatus());
        long flag = platformQrcodeService.update(platformQrcode);
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
	
	/** 重置二维码 */
	@RequestMapping(value="/reset/qrcode",method=RequestMethod.GET)
	@ResponseBody
	public JSON resetQrcode(HttpServletRequest request){			
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			platformQrcodeService.resetQrcode();
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 测试 */
	@RequestMapping(value="/reset/test",method=RequestMethod.GET)
	@ResponseBody
	public JSON test(HttpServletRequest request){			
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
}

