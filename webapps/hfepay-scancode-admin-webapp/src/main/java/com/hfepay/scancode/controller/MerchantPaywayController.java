/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import com.hfepay.scancode.api.entity.vo.MerchantRateVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.ScanBaseController;
import com.hfepay.scancode.commons.TransCodeEnum;
import com.hfepay.scancode.commons.condition.AgentPaywayCondition;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.entity.ChangeLog;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.service.operator.AgentPaywayService;
import com.hfepay.scancode.service.operator.ChangeLogService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;
import com.hfepay.scancode.service.operator.MerchantPaywayTmpService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantpayway")
public class MerchantPaywayController extends ScanBaseController{
	
	@Autowired
	private MerchantPaywayService merchantPaywayService;
	@Autowired
	private MerchantPaywayTmpService merchantPaywayTmpService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private ChangeLogService changeLogService;
	
	@Autowired
	private AgentPaywayService agentPaywayService;
	
	@Autowired
	private MerchantBusinessService merchantBusinessService;
	
	@Autowired
	private MerchantBankcardService merchantBankcardService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		AgentPaywayCondition agentPaywayCondition = new AgentPaywayCondition();
		request.setAttribute("agentPays", agentPaywayService.findAll(agentPaywayCondition));
		return "admin/merchantpayway/list";
	}
	
	/**
	 * 列表显示
	 * @param request merchantPaywayCondition
	 * 
	 * @author panq
	 * @return page
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantPaywayCondition merchantPaywayCondition){	
		merchantPaywayCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		PagingResult<MerchantPayway> page = merchantPaywayService.findPagingResult(merchantPaywayCondition);
		Pager<MerchantPayway> pager = new Pager<MerchantPayway>();
		pager.setPageNo(merchantPaywayCondition.getPageNo());
		pager.setPageSize(merchantPaywayCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantpayway/listContent";
	}
	
	/**
	 * 列表显示(不分页)
	 * @param request merchantPaywayCondition
	 * 
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantPaywayCondition merchantPaywayCondition){
		merchantPaywayCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
        List<MerchantPayway> list = merchantPaywayService.findAll(merchantPaywayCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/**
	 * 进入新增、更新页面
	 * @param 主键
	 * 
	 * @author panq
	 * @return page
	 */
	@RequestMapping(value="/edit/{id}/{merNo}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id,@PathVariable String merNo) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantPayway entity = merchantPaywayService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		MerchantInfo entity = merchantInfoService.findByMerchantNo(merNo);
		model.addAttribute("mer", entity);
		
		AgentPaywayCondition agentPaywayCondition = new AgentPaywayCondition();
		agentPaywayCondition.setAgentNo(entity.getAgentNo());
		agentPaywayCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		agentPaywayCondition.setStatus(Constants.STATUS_ACTIVE);
		model.addAttribute("agentPays", agentPaywayService.findAll(agentPaywayCondition));
		
		return "admin/merchantpayway/edit";
	}

	/**
	 * 新增、更新
	 * @param merchantPaywayCondition
	 * 
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,MerchantPaywayCondition merchantPaywayCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		merchantPaywayCondition.setOperator(getCurrentUserName());
		try {
			merchantPaywayService.save(merchantPaywayCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantpayway");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}

	
	
	/**
	 * 删除
	 *  
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,MerchantPaywayCondition merchantPaywayCondition){		
		merchantPaywayCondition.setOperator(getCurrentUserName());
		merchantPaywayCondition.setRecordStatus(Constants.RECORD_STATUS_NO);
		merchantPaywayCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantPaywayService.update(merchantPaywayCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 状态变更
	 * @param request
	 * 
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSON updateStatus(HttpServletRequest request,MerchantPaywayCondition merchantPaywayCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantPaywayCondition.setOperator(getCurrentUserName());
			merchantPaywayCondition.setUpdateTime(new Date());
			merchantPaywayService.update(merchantPaywayCondition);
			
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
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
		MerchantPayway entity = merchantPaywayService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantpayway/detail";
	}
	
	/** 商户入网 
	 * @param 主键
	 * 
	 * @author panq
	 * @return page
	 */
	@RequestMapping(value="/join/{id}", method= {RequestMethod.POST})
	@ResponseBody
	public JSON join(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		 Map<Object, Object> map = new HashMap<Object, Object>();
		 MerchantPayway entity = merchantPaywayService.findById(id);
		 MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		 BeanUtils.copyProperties(entity, merchantPaywayCondition);
		 JSON json = merchantPaywayService.paywayJoin(merchantPaywayCondition, map);
		 if(json != null){
			return json;
		 }
		 map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		 return JSONSerializer.toJSON(map);
	}
	
	/** 
	 * 商户费率信息入网
	 * @param merNo 商户编号
	 * @param payCode 支付编码
	 * @throws Exception
	*/
	@RequestMapping(value="/audit/{id}", method= {RequestMethod.POST})
	@ResponseBody
	@Transactional
	public JSON auditPlatform(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		Map<Object, Object> map = null;
		try {
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantinfo");
			MerchantPayway entity = merchantPaywayService.findById(id);
			if(entity == null){
				map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"商户费率信息不存在，请检查相关信息（状态等）");
				return JSONSerializer.toJSON(map);
			}
			MerchantInfo info = merchantInfoService.findByMerchantNo(entity.getMerchantNo());
			if(info == null){
				map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"商户信息不存在，请检查相关信息（状态等）");
				return JSONSerializer.toJSON(map);
			}
			if(Strings.isEmpty(info.getPlatformMerchantNo())){
				map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"商户尚未入网，请检查相关信息（状态等）");
				return JSONSerializer.toJSON(map);
			}
			MerchantRateVo payway = new MerchantRateVo();
			payway.setPayCode(entity.getPayCode());
			payway.setTradeRate(this.objToString(String.valueOf(entity.getT1Rate())));
			payway.setWithdrawAmt(this.objToString(String.valueOf(entity.getRate())));
			payway.setWithdrawRate(this.objToString(String.valueOf(entity.getT0Rate())));
			payway.setSettleAmt(this.objToString(String.valueOf(entity.getRateAmount())));
			
			//调用商户费率变更接口
			Map<String,String> res = merchantBusinessService.merchantUpdateRate(info.getPlatformMerchantNo(), payway);
			//解析返回结果
			Map<Object,Object> res1 = afterMerchantRate(entity,res);
			if(res1 != null) return JSONSerializer.toJSON(res1);	
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 解析商户入驻返回结果
	 * @param res
	 */
	private Map<Object, Object> afterMerchantRate(MerchantPayway entity,Map<String,String> res) {
		String respCode = res.get("respCode");
		String respDesc = res.get("respDesc");
		MerchantPaywayCondition pay = new MerchantPaywayCondition();
		pay.setId(entity.getId());
		pay.setUpdateTime(new Date());
		pay.setOperator(getCurrentUserName());
		if(Constants.INTERFACE_SUCCESS_STATUS.equals(respCode)){
			pay.setAcceptStatus(Constants.STATUS_ACTIVE);
		}else{
			pay.setAcceptStatus(Constants.STATUS_NOT_USE);
		}
		pay.setRemark(respDesc);
		merchantPaywayService.update(pay);
		res.put("values",respDesc);
		return null;
	}
	
	
	
	/** 将null对象转换成空字符串 */
	public String objToString(String obj){
		if(obj == null){
			return "";
		}
		return obj;
	}
	
	

	/** 变更记录 */
	@RequestMapping(value="/link/{id}", method= {RequestMethod.GET})
	public String link(HttpServletRequest request,ModelMap model,@PathVariable String id,MerchantPaywayCondition merchantPaywayCondition) throws Exception {
		ChangeLogCondition changeLogCondition = new ChangeLogCondition();		
		changeLogCondition.setTradeNo(id);
		changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.MERCHANT_PAYWAY_CODE.getValue()));
		changeLogCondition.setPageNo(merchantPaywayCondition.getPageNo());
		PagingResult<ChangeLog> page = changeLogService.findPagingResult(changeLogCondition);
		
		List<MerchantPaywayCondition> list = new ArrayList<MerchantPaywayCondition>();
		for (ChangeLog changeLog : page.getResult()) {
			JSONObject json = JSONObject.parseObject(changeLog.getBefore());
			MerchantPaywayCondition obj = JSONObject.toJavaObject(json,MerchantPaywayCondition.class);
			obj.setCreateTime(changeLog.getCreateTime());
			list.add(obj);
		}
		
		Pager<MerchantPaywayCondition> pager = new Pager<MerchantPaywayCondition>();
		pager.setPageNo(merchantPaywayCondition.getPageNo());
		pager.setPageSize(merchantPaywayCondition.getPageSize());
		pager.setResult(list);
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantpayway/link";
	}
	
	
	/** 校验商户编号与支付方式 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject check(HttpServletRequest request,String merchantNo,String payCode,String id){	
		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		merchantPaywayCondition.setMerchantNo(merchantNo);
		merchantPaywayCondition.setPayCode(payCode);
		merchantPaywayCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
        List<MerchantPayway> list = merchantPaywayService.findAll(merchantPaywayCondition);
        JSONObject json = new JSONObject();
        for (Iterator<MerchantPayway> iterator = list.iterator(); iterator.hasNext();) {
        	MerchantPayway merchantPayway = (MerchantPayway) iterator.next();
			if(!merchantPayway.getId().equals(id)){
				json.put("check", false);
				return json;
			}
		}
        json.put("check", true);
		return json;
	}
	
	@RequestMapping(value = "/addLiquidationFee", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject addLiquidationFee(HttpServletRequest request,String channelNo ,String liquidationFee){	
		JSONObject json = new JSONObject();
		try {
			merchantPaywayService.addLiquidationFee(channelNo, liquidationFee);
		} catch (Exception e) {
			json.put("message", Constants.FAIL_MSG);
        	json.put("status", Constants.FAIL_STATE);
        	return json;
		}
		json.put("message", Constants.SUCCESS_MSG);
    	json.put("status", Constants.SUCCESS_STATE);
		return json;
	}
	
	@RequestMapping(value = "/getLiquidationFee", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject getLiquidationFee(HttpServletRequest request,String channelNo){	
		JSONObject json = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		try {
			params = merchantPaywayService.getLiquidationFee(channelNo);
		} catch (Exception e) {
			json.put("message", Constants.FAIL_MSG);
        	json.put("status", Constants.FAIL_STATE);
        	return json;
		}
		if(null != params){
			json.put("liquidationFee", params.get("liquidationFee"));
		}		
		json.put("message", Constants.SUCCESS_MSG);
    	json.put("status", Constants.SUCCESS_STATE);
		return json;
	}
}