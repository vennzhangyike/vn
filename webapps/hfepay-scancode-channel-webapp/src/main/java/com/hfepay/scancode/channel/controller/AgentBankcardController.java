/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.util.ArrayList;
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
import com.hfepay.scancode.channel.commons.TransCodeEnum;
import com.hfepay.scancode.commons.condition.AgentBankcardCondition;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.DataNodeCondition;
import com.hfepay.scancode.commons.condition.RemitBankInfoCondition;
import com.hfepay.scancode.commons.entity.AgentBankcard;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChangeLog;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.RemitBankInfo;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.operator.AgentBankcardService;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChangeLogService;
import com.hfepay.scancode.service.operator.RemitBankInfoService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/agentbankcard")
public class AgentBankcardController extends BaseController{
	@Autowired
	private AgentBankcardService agentBankcardService;
	@Autowired
	private AgentBaseService agentBaseService;
	@Autowired
	private RemitBankInfoService remitBankInfoService;
	@Autowired
	private ChangeLogService changeLogService;
	
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/agentbankcard/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,AgentBankcardCondition agentBankcardCondition){
		//根据当前登陆的渠道信息获取到渠道下所有的代理商
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		PagingResult<AgentBankcard> page = agentBankcardService.findPagingResultByAgentNo(agentBankcardCondition,user.getNodeSeq());
		Pager<AgentBankcard> pager = new Pager<AgentBankcard>();
		pager.setPageNo(agentBankcardCondition.getPageNo());
		pager.setPageSize(agentBankcardCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/agentbankcard/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,AgentBankcardCondition agentBankcardCondition){		
        List<AgentBankcard> list = agentBankcardService.findAll(agentBankcardCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String toEdit(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		// 根据当前登陆的渠道信息获取到渠道下所有的代理商
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		DataNodeCondition dataNodeCondition = new DataNodeCondition();
		//List<String> agentNoList = new ArrayList<String>();
		//List<NodeRelation> list = null;
		List<AgentBase> agentList = new ArrayList<AgentBase>(); 
		AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
		if(!"0".equalsIgnoreCase(id)){
			AgentBankcard entity = agentBankcardService.findById(id);
			 model.addAttribute("Obj", entity);
			 AgentBase agentBase = agentBaseService.findByAgentNo(entity.getAgentNo());
			 agentList.add(agentBase);
		}else{
			agentBaseCondition.setStatus("1");
			agentList =  agentBaseService.findAllByAgentNo(agentBaseCondition,user.getNodeSeq());
		}
		
		model.addAttribute("agentList", agentList);
		return "admin/agentbankcard/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,AgentBankcardCondition agentBankcardCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		agentBankcardCondition.setOperator(user.getId());
		try {
			if(Strings.isEmpty(agentBankcardCondition.getId())){
				//新增
				agentBankcardCondition.setCreateTime(new Date());
				agentBankcardCondition.setId(Strings.getUUID());
				agentBankcardCondition.setStatus(Constants.SUCCESS_STATE);
				agentBankcardService.insert(agentBankcardCondition);
			}else{
				agentBankcardCondition.setUpdateTime(new Date());
				agentBankcardService.update(agentBankcardCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/agentbankcard");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,AgentBankcardCondition agentBankcardCondition){		
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		agentBankcardCondition.setOperator(user.getId());
		agentBankcardCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			agentBankcardService.update(agentBankcardCondition);
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
	public JSON updateStatus(HttpServletRequest request,AgentBankcardCondition agentBankcardCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			agentBankcardService.updateStatus(agentBankcardCondition.getId(),agentBankcardCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		AgentBankcard entity = agentBankcardService.findById(id);
		model.addAttribute("item",entity);
		return "admin/agentbankcard/detail";
	}
	
	/** 变更记录 */
	@RequestMapping(value="/link/{id}", method= {RequestMethod.GET})
	public String link(HttpServletRequest request,ModelMap model,@PathVariable String id,AgentBankcardCondition agentBankcardCondition) throws Exception {
		ChangeLogCondition changeLogCondition = new ChangeLogCondition();		
		changeLogCondition.setTradeNo(id);
		changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.CHANNEL_BANKCARD_CODE.getValue()));
		changeLogCondition.setPageNo(agentBankcardCondition.getPageNo());
		PagingResult<ChangeLog> page = changeLogService.findPagingResult(changeLogCondition);
		
		List<AgentBankcardCondition> list = new ArrayList<AgentBankcardCondition>();
		for (ChangeLog changeLog : page.getResult()) {
			JSONObject json = JSONObject.parseObject(changeLog.getBefore());
			AgentBankcardCondition obj = JSONObject.toJavaObject(json,AgentBankcardCondition.class);
			obj.setCreateTime(changeLog.getCreateTime());
			list.add(obj);
		}
		
		Pager<AgentBankcardCondition> pager = new Pager<AgentBankcardCondition>();
		pager.setPageNo(agentBankcardCondition.getPageNo());
		pager.setPageSize(agentBankcardCondition.getPageSize());
		pager.setResult(list);
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/agentbankcard/link";
	}
	
	/** 校验代理商账户是否已经存在 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject check(HttpServletRequest request,String id,String agentNo){	
		AgentBankcardCondition agentBankcardCondition = new AgentBankcardCondition();
		agentBankcardCondition.setAgentNo(agentNo);
        List<AgentBankcard> list = agentBankcardService.findAll(agentBankcardCondition);
        JSONObject json = new JSONObject();
        for (Iterator<AgentBankcard> iterator = list.iterator(); iterator.hasNext();) {
        	AgentBankcard agentBankcard = (AgentBankcard) iterator.next();
			if(!agentBankcard.getId().equals(id)){
				json.put("check", false);
				return json;
			}
		}
        json.put("check", true);
		return json;
	}
	
	/**银行卡名称和联行号获取**/
	@RequestMapping(value="/getBankInfo", method= {RequestMethod.POST})
	@ResponseBody
	public RemitBankInfo getInfo(ModelMap model,RemitBankInfoCondition conditon){
		return remitBankInfoService.getInfoByCardNo(conditon.getBankCard());
	}
}

