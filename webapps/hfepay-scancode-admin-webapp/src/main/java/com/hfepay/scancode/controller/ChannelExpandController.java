/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
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
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.AgreementInfoCondition;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.AgreementInfo;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.ChannelWxParams;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.service.operator.AgreementInfoService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.ChannelWxParamsService;
import com.hfepay.scancode.service.operator.ParamsInfoService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/channelexpand")
public class ChannelExpandController extends BaseController{
	
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private ChannelWxParamsService channelWxParamsService;
	@Autowired
	private ParamsInfoService paramsInfoService;
	@Autowired
	private AgreementInfoService agreementInfoService;
	
	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param ChannelBaseCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,ChannelExpandCondition channelExpandCondition){		
        List<ChannelExpand> list = channelExpandService.findAll(channelExpandCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param ChannelExpandCondition
	 * @return: String
	 * @date CreateDate : 2016-10-13 13:44:26
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON saveOrUpdateById(HttpServletRequest request,ModelMap model,ChannelExpandCondition channelExpandCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		channelExpandCondition.setOperator(user.getUserName());
		channelExpandCondition.setChannelPreCode(channelExpandCondition.getChannelPreCode().toUpperCase());//编码抬头转换大写
		try {
			channelExpandService.save(channelExpandCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/channelbase");
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
	 * @date CreateDate : 2016-10-13 13:44:26
	 */
	@RequestMapping(value="/{channelNo}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String channelNo) throws Exception {
		if(!"0".equalsIgnoreCase(channelNo)){
			ChannelWxParams channelWxParams = channelWxParamsService.findByChannelNo(channelNo);
			ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
			if(channelWxParams != null){
				JSONObject json = JSONObject.parseObject(channelWxParams.getWxParams());
				channelExpandCondition = JSONObject.toJavaObject(json,ChannelExpandCondition.class);
			}
			
			ChannelExpandCondition channelExpandConditionSms = new ChannelExpandCondition();
			ParamsInfo paramsInfo = paramsInfoService.findParamsKey(channelNo,ParamsType.PARAMS_SMS.getCode());
			if (paramsInfo != null) {
				JSONObject json = JSONObject.parseObject(paramsInfo.getParamsValue());
				channelExpandConditionSms = JSONObject.toJavaObject(json,ChannelExpandCondition.class);
			}
			AgreementInfoCondition agreementInfoCondition = new AgreementInfoCondition();
			agreementInfoCondition.setOrganNo(channelNo);
			List<AgreementInfo> agreementList = agreementInfoService.findAll(agreementInfoCondition);
			if(agreementList != null && agreementList.size() > 0){
				AgreementInfo agreementInfo = agreementList.get(0);
				model.addAttribute("agreementInfo",agreementInfo);
			}
			ChannelExpand entity = channelExpandService.findByChannelNo(channelNo);
			BeanUtils.copyProperties(entity, channelExpandCondition);
			model.addAttribute("Obj", channelExpandCondition);
			model.addAttribute("channelExpandConditionSms", channelExpandConditionSms);
		}
		return "admin/channel/channelexpand/edit";
	}


	/**
	 * @Title: saveOrUpdateById
	 * @Description: 状态变更
	 * @author: wh
	 * @param ChannelExpandCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-13 13:44:26
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,ChannelExpandCondition channelExpandCondition){		
        long flag = channelExpandService.updateStatus(channelExpandCondition.getId(),channelExpandCondition.getStatus());
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
	@RequestMapping(value="/detail/{channelNo}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String channelNo) throws Exception {
		ChannelExpand entity = channelExpandService.findByChannelNo(channelNo);
		ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
		ChannelWxParams channelWxParams = channelWxParamsService.findByChannelNo(channelNo);
		if(channelWxParams != null){			
			JSONObject json = JSONObject.parseObject(channelWxParams.getWxParams());
			channelExpandCondition = JSONObject.toJavaObject(json,ChannelExpandCondition.class);
		}
		ChannelExpandCondition channelExpandConditionSms = new ChannelExpandCondition();
		ParamsInfo paramsInfo = paramsInfoService.findParamsKey(channelNo,ParamsType.PARAMS_SMS.getCode());
		if (paramsInfo != null) {
			JSONObject json = JSONObject.parseObject(paramsInfo.getParamsValue());
			channelExpandConditionSms = JSONObject.toJavaObject(json,ChannelExpandCondition.class);
		}
		AgreementInfoCondition agreementInfoCondition = new AgreementInfoCondition();
		agreementInfoCondition.setOrganNo(channelNo);
		List<AgreementInfo> agreementList = agreementInfoService.findAll(agreementInfoCondition);
		if(agreementList != null && agreementList.size() > 0){
			AgreementInfo agreementInfo = agreementList.get(0);
			model.addAttribute("agreementInfo",agreementInfo);
		}
		model.addAttribute("item",entity);
		model.addAttribute("channelExpandCondition",channelExpandCondition);
		model.addAttribute("channelExpandConditionSms",channelExpandConditionSms);
		return "admin/channel/channelexpand/detail";
	}
	

	
	/**设置渠道redis*/
	@RequestMapping(value="/set/channelRedis",method=RequestMethod.GET)
	@ResponseBody
	public JSON setChannelRedis(HttpServletRequest request){
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			channelExpandService.setChannelRedis();			
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
}

