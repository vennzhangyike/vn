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

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.AuditLogCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.AuditLog;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.service.operator.AuditLogService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/auditlog")
public class AuditLogController extends BaseController{
	
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private MerchantBankcardService merchantBankcardService;
//	@Autowired
//	private MerchantBusinessService merchantBusinessService;
//	@Autowired
//	private MerchantInfoService merchantInfoService;
//	@Autowired
//	private MerchantAuthDetailService merchantAuthDetailService;
	
	/** 列表 */
	@RequestMapping(value = "/bankList",method= {RequestMethod.GET})
	public String bankList(HttpServletRequest request) {
		return "admin/merchantaudit/list";
	}
	
	/**
	 * 列表显示 //显示提取审核中的数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bankContent", method= {RequestMethod.POST})
	public String bankListContent(HttpServletRequest request,ModelMap model,MerchantBankcardCondition merchantBankcardCondition){	
		merchantBankcardCondition.setStatus(Constants.APPROVE_STATUSING);//查询审核中的数据
		PagingResult<MerchantBankcard> page = merchantBankcardService.findPagingResult(merchantBankcardCondition);
		Pager<MerchantBankcard> pager = new Pager<MerchantBankcard>();
		pager.setPageNo(merchantBankcardCondition.getPageNo());
		pager.setPageSize(merchantBankcardCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantaudit/listContent";
	}
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/auditlog/list";
	}
	
	/**
	 * 列表显示  
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,AuditLogCondition auditLogCondition){		
		PagingResult<AuditLog> page = auditLogService.findPagingResult(auditLogCondition);
		Pager<AuditLog> pager = new Pager<AuditLog>();
		pager.setPageNo(auditLogCondition.getPageNo());
		pager.setPageSize(auditLogCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/auditlog/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,AuditLogCondition auditLogCondition){		
        List<AuditLog> list = auditLogService.findAll(auditLogCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入结算审核新增页面/更新 */
	@RequestMapping(value="id/{id}", method= {RequestMethod.POST})
	public String addShow(ModelMap model,@PathVariable String id) throws Exception {
		model.addAttribute("id", id);
		return "admin/merchantaudit/edit";
	}
	
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			AuditLog entity = auditLogService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/auditlog/edit";
	}
	/** 
	 * 商户审核（平台审核）
	 * @param merchantInfoCondition
	 * @throws Exception
	*/
//	@RequestMapping(value="/audit/platform", method= {RequestMethod.POST})
//	@ResponseBody
//	@Transactional
//	public JSON auditPlatform(HttpServletRequest request,ModelMap model,AuditLogCondition auditLogCondition) throws Exception {
//		Map<Object, Object> map = new HashMap<Object, Object>();
//		map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantaudit");
//		MerchantBankcard merchantBankcard = merchantBankcardService.findById(auditLogCondition.getId());
//		Admin user = (Admin) request.getSession().getAttribute("currentUser");
//		auditLogCondition.setOperator(user.getId());
//		MerchantBankcardCondition merchantBankcardCondition =new MerchantBankcardCondition();
//		merchantBankcardCondition.setId(auditLogCondition.getId());
//		auditLogCondition.setOperator(user.getId());
//		try {
//			//通过审核，修改银行账户表字段
//			if(("3").equals(auditLogCondition.getAuditStatus())){
//				//商户基本信息
//				MerchantInfo entity = merchantInfoService.findByMerchantNo(merchantBankcard.getMerchantNo());
//				merchantBankcardCondition.setStatus(Constants.APPROVE_STATUS_SUCCESS);//审核通过变成有效状态
//				merchantBankcardCondition.setUpdateTime(new Date());
//				//审核通过，调用银行帐号变更接口
//				MerchantUpdateCardVo merchantUpdateCardVo =new MerchantUpdateCardVo();
//				merchantUpdateCardVo.setMerchantNo(entity.getPlatformMerchantNo());
//				merchantUpdateCardVo.setMobile(merchantBankcard.getMobile());
//				merchantUpdateCardVo.setNewBankCode(merchantBankcard.getBankCode());
//				merchantUpdateCardVo.setNewBankCard(merchantBankcard.getBankCard());
//				merchantUpdateCardVo.setNewBankName(merchantBankcard.getBankName());
//				merchantUpdateCardVo.setReturnURL("");//回调地址
//				//调用商户账户变更接口
//				Map<String, String> returnMap = merchantBusinessService.merchantUpdateCard(merchantUpdateCardVo);
//				String respCode = returnMap.get("respCode");
//				if("000000".equals(respCode)){
//					map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","admin/merchantaudit");
//				}else{
//					String respDesc = returnMap.get("respDesc");//响应描述
//					map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,respDesc);
//				}
//				//商户四要素验证
//				JSON res = merchantBankCard4(map, entity);
//				if(res != null){
//					return res;
//				}
//			}else{
//				//不通过，则
//				merchantBankcardCondition.setStatus("4");//审核不通过
//				merchantBankcardCondition.setUpdateTime(new Date());
//			}
//			merchantBankcardService.update(merchantBankcardCondition);
//			//通过不通过，新增审核信息表数据
//			//新增
//			auditLogCondition.setCreateTime(new Date());
//			auditLogCondition.setAuditId(auditLogCondition.getId());
//			auditLogCondition.setAuditType("1");//银行账户变更
//			auditLogCondition.setMerchantNo(merchantBankcard.getMerchantNo());
//			auditLogCondition.setId(Strings.getUUID());
//			auditLogService.insert(auditLogCondition);
//		  }catch (Exception e) {
//			  map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
//		}
//		return JSONSerializer.toJSON(map);
//	}
	
	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,AuditLogCondition auditLogCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentUser");
		auditLogCondition.setOperator(user.getId());
		try {
			if(Strings.isEmpty(auditLogCondition.getId())){
				//新增
				auditLogCondition.setCreateTime(new Date());
				auditLogCondition.setId(Strings.getUUID());
				//auditLogCondition.setRecordStatus(RecordStatus.ACTIVE);
				//auditLogCondition.setStatus(Constants.SUCCESS_STATE);
				auditLogService.insert(auditLogCondition);
			}else{
				//auditLogCondition.setUpdateTime(new Date());
				auditLogService.update(auditLogCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/auditlog");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,AuditLogCondition auditLogCondition){		
		Admin user = (Admin) request.getSession().getAttribute("currentUser");
		auditLogCondition.setOperator(user.getId());
//		auditLogCondition.setRecordStatus(Constants.N);
//		auditLogCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			auditLogService.update(auditLogCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	
//	/** 查看详情 */
//	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
//	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
//		AuditLog entity = auditLogService.findById(id);
//		model.addAttribute("item",entity);
//		return "admin/auditlog/detail";
//	}
	
	/** 查看商户银行账户详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show1(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MerchantBankcard entity = merchantBankcardService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantaudit/detail";
	}
	
	
	/**
	 * 四要素调用接口
	 * 
	 * @throws Exception
	 */
//	private JSON merchantBankCard4(Map<Object, Object> map, MerchantInfo entity) throws Exception {
//		//获取银行卡列表
//		MerchantBankcardCondition merchantBankcardCondition = new MerchantBankcardCondition();
//		merchantBankcardCondition.setMerchantNo(entity.getMerchantNo());
//		merchantBankcardCondition.setStatus(Constants.APPROVE_STATUSING);
//		List<MerchantBankcard> bankcard =  merchantBankcardService.findAll(merchantBankcardCondition);
//		BankCardAuthVo auth = new BankCardAuthVo();
//		
//		if(bankcard.size() > 0){
//			auth.setRealName(bankcard.get(0).getName());
//			auth.setBankCard(bankcard.get(0).getBankCard());
//			auth.setIdCard(bankcard.get(0).getIdCard());
//			auth.setMobile(bankcard.get(0).getMobile());
//		}
//
//		Map<String,String> res = merchantBusinessService.bankCardAuth(auth,"3");
//		String respCode = res.get("respCode");
//		String respDesc = res.get("respDesc");
//		if(Constants.INTERFACE_SUCCESS_STATUS.equals(respCode)){
//			entity.setAuthenStatus(Integer.parseInt(Constants.STATUS_ACTIVE));
//		}else{
//			entity.setAuthenStatus(Integer.parseInt(Constants.STATUS_NOT_USE));
//		}
//		
//		this.saveMerchantAutoInfo(auth,entity,respCode,respDesc);
//		
//		String remark = entity.getRemark().split("==")[0];
//		entity.setRemark(remark + "==auth--"+Dates.yyyyMMddHHmmss(new Date())+":"+respDesc);
//		MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
//		BeanUtils.copyProperties(entity, merchantInfoCondition);
//		merchantInfoService.update(merchantInfoCondition);
//		if(!Constants.INTERFACE_SUCCESS_STATUS.equals(respCode)){
//			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,respDesc);
//			return JSONSerializer.toJSON(map);
//		}
//		return null;
//	}
	
	/**
	 * 保存商户认证信息
	 * @param BankCardAuthVo
	 * @param entity
	 */
//	public void saveMerchantAutoInfo(BankCardAuthVo bankCardAuthVo,MerchantInfo entity,String respCode,String respDesc){
//		MerchantAuthDetailCondition merchantAuthDetailCondition = new MerchantAuthDetailCondition();
//		merchantAuthDetailCondition.setId(Strings.getUUID());
//		merchantAuthDetailCondition.setChannelNo(entity.getChannelNo());
//		merchantAuthDetailCondition.setAgentNo(entity.getAgentNo());
//		merchantAuthDetailCondition.setMerchantNo(entity.getMerchantNo());
//		merchantAuthDetailCondition.setAuthInterface("bank_auth");
//		merchantAuthDetailCondition.setAuthParams(JSONSerializer.toJSON(bankCardAuthVo).toString());
//		merchantAuthDetailCondition.setReturnAuthCode(respCode);
//		merchantAuthDetailCondition.setReturnAuthMsg(respDesc);
//		merchantAuthDetailCondition.setCreateTime(new Date());
//		
//		merchantAuthDetailService.insert(merchantAuthDetailCondition);
//	}
}

