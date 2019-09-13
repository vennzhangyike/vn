/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.math.BigDecimal;
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
import com.hfepay.scancode.channel.commons.BatchNoUtil;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.TransCodeEnum;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.AgentPaywayCondition;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.ChannelPaywayCondition;
import com.hfepay.scancode.commons.contants.ScanCodeConstants;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.AgentPayway;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelPayway;
import com.hfepay.scancode.commons.vo.AgentPaywayVo;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.commons.vo.ChannelPaywayVo;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.AgentPaywayService;
import com.hfepay.scancode.service.operator.ChangeLogService;
import com.hfepay.scancode.service.operator.ChannelPaywayService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/agentpayway")
public class AgentPaywayController extends BaseController{
	
	@Autowired
	private AgentPaywayService agentPaywayService;
	
	@Autowired
	private AgentBaseService agentBaseService;
	
	@Autowired
	private ChannelPaywayService channelPaywayService;
	@Autowired
	private MerchantPaywayService merchantPaywayService;
	@Autowired
	private ChangeLogService changeLogService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/agentpayway/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,AgentPaywayCondition agentPaywayCondition){
		//根据当前登陆的渠道信息获取到渠道下所有的代理商
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		PagingResult<AgentPayway> page = agentPaywayService.findPagingResultByAgentNo(agentPaywayCondition,user.getNodeSeq());
		Pager<AgentPayway> pager = new Pager<AgentPayway>();
		pager.setPageNo(agentPaywayCondition.getPageNo());
		pager.setPageSize(agentPaywayCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		
		model.addAttribute("pager",pager);
		model.addAttribute("user",user);
		return "admin/agentpayway/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,AgentPaywayCondition agentPaywayCondition){		
        List<AgentPayway> list = agentPaywayService.findAll(agentPaywayCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
		// 根据当前登陆的渠道信息获取到渠道下所有的代理商
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		List<AgentBase> agentList = new ArrayList<AgentBase>(); 
		if(!"0".equalsIgnoreCase(id)){
			AgentPayway entity = agentPaywayService.findById(id);
			model.addAttribute("Obj", entity);
			AgentBase agentBase = agentBaseService.findByAgentNo(entity.getAgentNo());
			agentList.add(agentBase);
		}else{
			agentBaseCondition.setStatus("1");
			agentList =  agentBaseService.findAllByAgentNo(agentBaseCondition, user.getNodeSeq());
		}
		model.addAttribute("agentList", agentList);
		return "admin/agentpayway/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,AgentPaywayCondition agentPaywayCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		agentPaywayCondition.setOperator(user.getId());
		try {
			if(Strings.isEmpty(agentPaywayCondition.getId())){
				//新增
				agentPaywayCondition.setCreateTime(new Date());
				agentPaywayCondition.setId(Strings.getUUID());
				agentPaywayCondition.setRecordStatus(Constants.Y);
				agentPaywayCondition.setStatus(Constants.SUCCESS_STATE);
				agentPaywayService.insert(agentPaywayCondition);
			}else{
				agentPaywayCondition.setUpdateTime(new Date());
				agentPaywayService.update(agentPaywayCondition);
				
				AgentPayway agentPayway = agentPaywayService.findById(agentPaywayCondition.getId());
				boolean isChange = false ;
				//有变更才写入数据变更日志
				if(!(agentPayway.getT0Rate().equals(agentPaywayCondition.getT0Rate())
								&&agentPayway.getT1Rate().equals(agentPaywayCondition.getT1Rate())
								&&agentPayway.getRate().equals(agentPaywayCondition.getRate()))){
					
					isChange=true;
				}
				if(isChange){
					ChangeLogCondition changeLogCondition = new ChangeLogCondition();
					String batchNo = BatchNoUtil.getBatchNo();
					changeLogCondition.setTradeNo(agentPaywayCondition.getId());
					changeLogCondition.setBatchNo(batchNo);
					changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.AGENT_PAYWAY_CODE.getValue()));
					changeLogCondition.setTransName(TransCodeEnum.AGENT_PAYWAY_CODE.getDesc());
					changeLogCondition.setOwnersNo(agentPayway.getAgentNo());
					changeLogCondition.setBefore(JSONSerializer.toJSON(agentPayway).toString());
					changeLogCondition.setStatus(ScanCodeConstants.APPROVE_STATUS_NEW);
					changeLogCondition.setOperator(agentPaywayCondition.getOperator());
					changeLogCondition.setCreateTime(new Date());
					changeLogService.insert(changeLogCondition);
				}
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/agentpayway");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,AgentPaywayCondition agentPaywayCondition){		
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		agentPaywayCondition.setOperator(user.getId());
		agentPaywayCondition.setRecordStatus(Constants.N);
		agentPaywayCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			agentPaywayService.update(agentPaywayCondition);
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
	public JSON updateStatus(HttpServletRequest request,AgentPaywayCondition agentPaywayCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			agentPaywayService.updateStatus(agentPaywayCondition.getId(),agentPaywayCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String detail(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		AgentPayway entity = agentPaywayService.findById(id);
		model.addAttribute("item",entity);
		return "admin/agentpayway/detail";
	}
	
	/**
	 * 获取上级代理商的支付方式
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/hasParentPayway", method= {RequestMethod.POST})
	@ResponseBody
	public JSON hasParentPayway(HttpServletRequest request,String agentNo){		
		Map<Object, Object> map = new HashMap<Object, Object>();
		AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
		agentBaseCondition.setAgentNo(agentNo);
        List<AgentBase> agentBaseList = agentBaseService.findAll(agentBaseCondition);
        
        if(agentBaseList != null && agentBaseList.size()>0){
        	//查询当前的代理商信息
        	AgentBase agentBase = agentBaseList.get(0);
        	String parentNo = agentBase.getParentNo();
        	//判断代理商层级，如果是1级代理商，则查询渠道的支付方式
        	if(Integer.parseInt(agentBase.getAgentLevel())==1){
        		ChannelPaywayCondition channelPaywayCondition = new ChannelPaywayCondition();
        		channelPaywayCondition.setChannelNo(parentNo);
        		//根据渠道编号查询渠道支付方式
        		List<ChannelPayway> channelPaywayList = channelPaywayService.findAll(channelPaywayCondition);
        		if(channelPaywayList != null && channelPaywayList.size()>0){
        			List<Map<String, String>> paywayList = new ArrayList<Map<String, String>>();
        			Map<String, String> paywayMap = new HashMap<String, String>();
        			ChannelPaywayVo payway = null;
        			for (ChannelPayway channelPayway : channelPaywayList) {
        				payway = (ChannelPaywayVo) channelPayway;
						paywayMap.put(payway.getPayCode(), payway.getPayName());
					}
        			paywayList.add(paywayMap);
        			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,"resultList",paywayList);
        		}else{
        			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED);
        		}
        	}else{
        		//根据上级编号查询代理商支付方式
        		AgentPaywayCondition agentPaywayCondition = new AgentPaywayCondition();
        		agentPaywayCondition.setAgentNo(parentNo);
                List<AgentPayway> baseList = agentPaywayService.findAll(agentPaywayCondition);
                if(baseList != null && baseList.size()>0){
                	List<Map<String, String>> paywayList = new ArrayList<Map<String, String>>();
        			Map<String, String> paywayMap = new HashMap<String, String>();
        			AgentPaywayVo Payway = null;
        			for (AgentPayway agentPayway : baseList) {
        				Payway = (AgentPaywayVo) agentPayway;
						paywayMap.put(Payway.getPayCode(), Payway.getPayName());
					}
        			paywayList.add(paywayMap);
        			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,"resultList",paywayList);
                }else{
                	map = Maps.mapByAarray(EXECUTE_STATUS,FAILED);
                }
        	}
        }
        return JSONSerializer.toJSON(map);
	}
	
	/** 校验代理商与支付方式 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject check(HttpServletRequest request,String agentNo,String payCode,String id){	
		AgentPaywayCondition agentPaywayCondition = new AgentPaywayCondition();
		agentPaywayCondition.setAgentNo(agentNo);
		agentPaywayCondition.setPayCode(payCode);
		
        List<AgentPayway> list = agentPaywayService.findAll(agentPaywayCondition);
        JSONObject json = new JSONObject();
        for (Iterator<AgentPayway> iterator = list.iterator(); iterator.hasNext();) {
        	AgentPayway agentPayway = (AgentPayway) iterator.next();
			if(!agentPayway.getId().equals(id)){
				json.put("check", false);
				return json;
			}
		}
        json.put("check", true);
		return json;
	}

	/**
	 * 获取上级代理商的费率
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/hasParentRate", method= {RequestMethod.POST})
	@ResponseBody
	public JSON hasParentRate(HttpServletRequest request,String agentNo,String payCode){		
		Map<Object, Object> map = new HashMap<Object, Object>();
		AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
		agentBaseCondition.setAgentNo(agentNo);
        List<AgentBase> agentBaseList = agentBaseService.findAll(agentBaseCondition);
        
        if(agentBaseList != null && agentBaseList.size()>0){
        	//查询当前的代理商信息
        	AgentBase agentBase = agentBaseList.get(0);
        	//当前代理商的上级编号
        	String parentNo = agentBase.getParentNo();
        	//判断代理商层级，如果是1级代理商，则查询渠道的支付方式
        	if(Integer.parseInt(agentBase.getAgentLevel())==1){
        		ChannelPaywayCondition channelPaywayCondition = new ChannelPaywayCondition();
        		channelPaywayCondition.setPayCode(payCode);
        		channelPaywayCondition.setChannelNo(parentNo);
        		channelPaywayCondition.setStatus("1");
        		//根据渠道编号查询渠道支付方式
        		List<ChannelPayway> channelPaywayList = channelPaywayService.findAll(channelPaywayCondition);
        		if(channelPaywayList != null && channelPaywayList.size()>0){
        			Map<String, BigDecimal> paywayMap = new HashMap<String, BigDecimal>();
        			ChannelPaywayVo payway = (ChannelPaywayVo) channelPaywayList.get(0);
        			paywayMap.put("t0", payway.getT0Rate());
        			paywayMap.put("t1", payway.getT1Rate());
        			paywayMap.put("rat", payway.getRate());
        			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,"resultMap",paywayMap);
        		}else{
        			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED);
        		}
        	}else{
        		//根据上级编号查询代理商信息
        		AgentPaywayCondition agentPaywayCondition = new AgentPaywayCondition();
        		agentPaywayCondition.setPayCode(payCode);
        		agentPaywayCondition.setAgentNo(parentNo);
        		agentPaywayCondition.setStatus("1");
                List<AgentPayway> baseList = agentPaywayService.findAll(agentPaywayCondition);
                if(baseList != null && baseList.size()>0){
        			Map<String, BigDecimal> paywayMap = new HashMap<String, BigDecimal>();
        			AgentPaywayVo payway = (AgentPaywayVo) baseList.get(0);
        			paywayMap.put("t0", payway.getT0Rate());
        			paywayMap.put("t1", payway.getT1Rate());
        			paywayMap.put("rat", payway.getRate());
        			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,"resultMap",paywayMap);
                }else{
                	map = Maps.mapByAarray(EXECUTE_STATUS,FAILED);
                }
        	}
        }
        return JSONSerializer.toJSON(map);
	}
	
	/** 校验代理商下商户的支付方式 */
	@RequestMapping(value="/checkMerchantRate",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject checkMerchantRate(HttpServletRequest request,String agentNo,String t0Rate,String t1Rate,String rate,String payCode){		
		JSONObject json = new JSONObject();
		List<Map<String, BigDecimal>> resultList = merchantPaywayService.findMerchantPayWayByAgentNo(agentNo,payCode);
		if (resultList != null && resultList.size() > 0) {
			Map<String, BigDecimal> resultMap = resultList.get(0);
			if(resultMap != null && resultMap.size() > 0){
				BigDecimal m_t0_rate = resultMap.get("m_t0_rate");
				BigDecimal m_t1_rate = resultMap.get("m_t1_rate");
				BigDecimal tmp_t0_rate = resultMap.get("tmp_t0_rate");
				BigDecimal tmp_t1_rate = resultMap.get("tmp_t1_rate");
				BigDecimal m_rate = resultMap.get("m_rate");
				BigDecimal tmp_rate = resultMap.get("tmp_rate");
				BigDecimal t0_Rate = new BigDecimal(t0Rate);
				BigDecimal t1_Rate = new BigDecimal(t1Rate);
				BigDecimal t_rate = new BigDecimal(rate);
				if(m_t0_rate != null && t0_Rate.compareTo(m_t0_rate)==1){
					json.put("check", false);
					return json;
				}else if(tmp_t0_rate != null && t0_Rate.compareTo(tmp_t0_rate)==1){
					json.put("check", false);
					return json;
				}else if(m_t1_rate != null && t1_Rate.compareTo(m_t1_rate)==1){
					json.put("check", false);
					return json;
				}else if(tmp_t1_rate != null && t1_Rate.compareTo(tmp_t1_rate)==1){
					json.put("check", false);
					return json;
				}else if(m_rate != null && t_rate.compareTo(m_rate)==1){
					json.put("check", false);
					return json;
				}else if(tmp_rate != null && t_rate.compareTo(tmp_rate)==1){
					json.put("check", false);
					return json;
				}
			}
		}
		json.put("check", true);
		return json;
	}
}

