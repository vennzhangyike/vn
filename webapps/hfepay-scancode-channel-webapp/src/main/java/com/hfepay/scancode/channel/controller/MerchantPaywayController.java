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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.entity.vo.MerchantAccountsVo;
import com.hfepay.scancode.api.entity.vo.MerchantInfoVo;
import com.hfepay.scancode.api.entity.vo.MerchantRateVo;
import com.hfepay.scancode.api.service.MerchantBusinessService;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.commons.ScanBaseController;
import com.hfepay.scancode.commons.condition.AgentPaywayCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.operator.AgentPaywayService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantpayway")
public class MerchantPaywayController extends ScanBaseController{
	
	@Autowired
	private MerchantPaywayService merchantPaywayService;
//	@Autowired
//	private MerchantPaywayTmpService merchantPaywayTmpService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
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
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		merchantPaywayCondition.setNodeSeq(user.getNodeSeq());
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
		//ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		model.addAttribute("user",user);
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
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		merchantPaywayCondition.setNodeSeq(user.getNodeSeq());
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
		merchantPaywayCondition.setOperator(getCurrentUserId());
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
		merchantPaywayCondition.setOperator(getCurrentUserId());
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
			merchantPaywayCondition.setOperator(getCurrentUserId());
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
		 JSON json = paywayJoin(merchantPaywayCondition, map);
		 if(json != null){
			return json;
		 }
		 map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		 return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 新费率入网
	 * @param merchantPaywayCondition
	 * @param map
	 * @return
	 * @throws Exception
	 */
	private JSON paywayJoin(MerchantPaywayCondition merchantPaywayCondition, Map<Object, Object> map) throws Exception {
		MerchantInfo info = merchantInfoService.findByMerchantNo(merchantPaywayCondition.getMerchantNo());
		if(info != null && Strings.isNotEmpty(info.getPlatformMerchantNo())){
			//获取费率列表
			MerchantPayway pay = new MerchantPayway();
			BeanUtils.copyProperties(merchantPaywayCondition, pay);
			List<MerchantPayway> payways = new ArrayList<MerchantPayway>();
			payways.add(pay);
			//商户入网
			JSON res = merchantJoin(map, info, payways);
			if(res != null){
				return res;
			}
		}
		return null;
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
		pay.setOperator(getCurrentUserId());
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
	
	/**
	 * 调用商户入网接口
	 * @param map 返回web的响应对象map
	 * @param entity 商户基本信息
	 * @param payways 商户费率信息对象列表
	 * 
	 * @throws Exception
	 */
	private JSON merchantJoin(Map<Object, Object> map, MerchantInfo entity,List<MerchantPayway> payways) throws Exception {
		if(entity == null || Strings.isEmpty(entity.getMerchantNo())){
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"商户信息不完整，请检查相关信息");
			return JSONSerializer.toJSON(map);
		}
		
		//获取银行卡列表
		MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
		merchantBankcardCondition.setMerchantNo(entity.getMerchantNo());
//		merchantBankcardCondition.setStatus(Constants.STATUS_ACTIVE);
		List<MerchantBankcard> bankcards =  merchantBankcardService.findAll(merchantBankcardCondition);
		if(bankcards.size() == 0){
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"商户银行卡信息不存在，请检查相关信息");
			return JSONSerializer.toJSON(map);
		}
		
		MerchantAccountsVo account = new MerchantAccountsVo();
		MerchantBankcard card = bankcards.get(0);
		account.setAccountName(this.objToString(card.getName()));
		account.setBankCard(this.objToString(card.getBankCard()));
		account.setBankCode(this.objToString(card.getBankCode()));
		account.setBankName(this.objToString(card.getBankName()));
		account.setIsRealAccount(this.objToString(card.getIsRealAccount()));
		account.setAccountType(card.getAccountType());
		
		List<MerchantRateVo> paywayList = Lists.newList();
		MerchantRateVo payway = null;
		for (MerchantPayway pay:payways) {
			payway = new MerchantRateVo();
			payway.setPayCode(pay.getPayCode());
			payway.setTradeRate(this.objToString(String.valueOf(pay.getT1Rate())));
			payway.setWithdrawAmt(this.objToString(String.valueOf(pay.getRate())));
			payway.setWithdrawRate(this.objToString(String.valueOf(pay.getT0Rate())));
			payway.setSettleAmt(this.objToString(String.valueOf(pay.getRateAmount())));
			paywayList.add(payway);
		}
		
		MerchantInfoVo info = new MerchantInfoVo();
		info.setMerchantNo(this.objToString(entity.getMerchantNo()));
		info.setMerchantName(this.objToString(entity.getMerchantName()));
		info.setShortName(this.objToString(entity.getShortName()));
		info.setAddress(this.objToString(entity.getAddress()));
		info.setSerPhone(this.objToString(entity.getPhone()));
		info.setCategory(this.objToString(entity.getBusType()));
		info.setIdCard(this.objToString(entity.getIdCard()));
		info.setName(this.objToString(entity.getName()));
		info.setMerchantLicense(this.objToString(entity.getMerchantLicense()));
		info.setPhone(this.objToString(entity.getPhone()));
		info.setMobile(this.objToString(entity.getMobile()));
		info.setEmail(this.objToString(entity.getEmail()));
		info.setRemark(this.objToString(entity.getRemark()));
		
		//调用商户入网接口
		Map<String,String> res = merchantBusinessService.merchantJoin(info,account,paywayList);
		//解析返回结果
		Map<Object,Object> res1 = afterMerchantJoin(entity,res);
		if(res1 != null) return JSONSerializer.toJSON(res1);
		return null;
	}
	
	/** 将null对象转换成空字符串 */
	public String objToString(String obj){
		if(obj == null){
			return "";
		}
		return obj;
	}
	
	/**
	 * 解析商户入驻返回结果
	 * @param res
	 */
	private Map<Object, Object> afterMerchantJoin(MerchantInfo info,Map<String,String> res) {
		String respCode = res.get("respCode");
		if(Constants.INTERFACE_SUCCESS_STATUS.equals(respCode)){
			String merchantNo = res.get("merchantNo");
			JSONArray paytypes = JSONArray.fromObject(res.get("payType"));
			MerchantPaywayCondition pay = null;
			for (Object string : paytypes) {
				JSONObject map = JSONObject.fromObject(string);
				String payCode = map.getString("payCode");
				String status = map.getString("status");
				String respDesc = map.getString("respDesc");
				//System.err.println(merchantNo+"=="+payCode+"=="+status+"=="+respDesc);
				pay = new MerchantPaywayCondition();
				pay.setMerchantNo(merchantNo);
				pay.setPayCode(payCode);
				pay.setUpdateTime(new Date());
				pay.setOperator(getCurrentUserId());
				pay.setRemark(respDesc);
				if(Constants.INTERFACE_SUCCESS_STATUS.equals(status)){
					pay.setAcceptStatus(Constants.STATUS_ACTIVE);
				}else{
					//pay.setAcceptStatus(Constants.STATUS_NOT_USE);
				}
				merchantPaywayService.updateByCriteria(pay);
			}
		}else{
			return Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,res.get("respDesc"));
		}
		return null;
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
}