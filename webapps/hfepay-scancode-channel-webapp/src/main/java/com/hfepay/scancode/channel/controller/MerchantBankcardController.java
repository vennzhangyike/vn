/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.util.ArrayList;
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
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.commons.ScanBaseController;
import com.hfepay.scancode.channel.commons.TransCodeEnum;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.MerchantBankcardCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.RemitBankInfoCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.ChangeLog;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantBankcard;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.RemitBankInfo;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.operator.ChangeLogService;
import com.hfepay.scancode.service.operator.MerchantBankcardService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.RemitBankInfoService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantbankcard")
public class MerchantBankcardController extends ScanBaseController{
	
	@Autowired
	private MerchantBankcardService merchantBankcardService;
	@Autowired
	private ChangeLogService changeLogService; 
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private RemitBankInfoService remitBankInfoService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/merchantbankcard/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantBankcardCondition merchantBankcardCondition){
		ChannelAdminVo vo = (ChannelAdminVo)getCurrentUser();
		merchantBankcardCondition.setNodeSeq(vo.getNodeSeq());
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
		return "admin/merchantbankcard/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantBankcardCondition merchantBankcardCondition){		
		ChannelAdminVo vo = (ChannelAdminVo)getCurrentUser();
		merchantBankcardCondition.setNodeSeq(vo.getNodeSeq());
        List<MerchantBankcard> list = merchantBankcardService.findAll(merchantBankcardCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show1(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		
		if(!"0".equalsIgnoreCase(id)){
			MerchantBankcard entity = merchantBankcardService.findById(id);
			 model.addAttribute("Obj", entity);
		}else{
			//新增商户银行账户为申请中的商户
			MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
			ChannelAdminVo vo = (ChannelAdminVo)getCurrentUser();
			merchantInfoCondition.setNodeSeq(vo.getNodeSeq());
			//merchantInfoCondition.setStatus("0");
			List<MerchantInfo> merchantList =  merchantInfoService.findAll(merchantInfoCondition);
			model.addAttribute("merchantList", merchantList);
		}
		
		return "admin/merchantbankcard/edit";
	}
	
	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,MerchantBankcardCondition merchantBankcardCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		merchantBankcardCondition.setOperator(user.getId());
		try {
			Map<String, String> res =merchantBankcardService.save(merchantBankcardCondition);
			if(res!=null && !Constants.INTERFACE_SUCCESS_STATUS.equals(res.get("respCode"))){
				map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,res.get("respDesc"));
				return JSONSerializer.toJSON(map);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantbankcard");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,MerchantBankcardCondition merchantBankcardCondition){		
		Admin user = (Admin) request.getSession().getAttribute("currentUser");
		merchantBankcardCondition.setOperator(user.getId());
		//merchantBankcardCondition.setRecordStatus(Constants.N);
		merchantBankcardCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantBankcardService.update(merchantBankcardCondition);
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
	public JSON updateStatus(HttpServletRequest request,MerchantBankcardCondition merchantBankcardCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantBankcardService.updateStatus(merchantBankcardCondition.getId(),merchantBankcardCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MerchantBankcard entity = merchantBankcardService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantbankcard/detail";
	}
	
	/**银行卡名称和联行号获取**/
	@RequestMapping(value="/getBankInfo", method= {RequestMethod.POST})
	@ResponseBody
	public RemitBankInfo getInfo(ModelMap model,RemitBankInfoCondition conditon){
		return remitBankInfoService.getInfoByCardNo(conditon.getBankCard());
	}
	
	/** 变更记录查询 */
	@RequestMapping(value="/link/{id}/{merchantNo}", method= {RequestMethod.GET})
	public String link(HttpServletRequest request,ModelMap model,@PathVariable String id,@PathVariable String merchantNo,MerchantBankcardCondition merchantBankcardCondition) throws Exception {
		ChangeLogCondition changeLogCondition = new ChangeLogCondition();
		MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantNo);
		if(merchantInfo != null){
//			String tradeNo = merchantInfo.getId();//t_channel_base表Id关联
			changeLogCondition.setTradeNo(merchantBankcardCondition.getId());
			changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.MERCHANT_BANKCARD_CODE.getValue()));
			
			PagingResult<ChangeLog> page = changeLogService.findPagingResult(changeLogCondition);
			
			List<MerchantBankcardCondition> list = new ArrayList<MerchantBankcardCondition>();
			for (ChangeLog changeLog : page.getResult()) {
				JSONObject json = JSONObject.parseObject(changeLog.getBefore());
				MerchantBankcardCondition obj = JSONObject.toJavaObject(json,MerchantBankcardCondition.class);
				obj.setCreateTime(changeLog.getCreateTime());
				list.add(obj);
			}
			
			Pager<MerchantBankcardCondition> pager = new Pager<MerchantBankcardCondition>();
			pager.setPageNo(merchantBankcardCondition.getPageNo());
			pager.setPageSize(merchantBankcardCondition.getPageSize());
			pager.setResult(list);
			pager.setTotalCount(page.getTotalCount());
	 		if(pager.getOrder() == null){
				pager.setOrder("");
			}
			model.addAttribute("pager",pager);
		}
		
		return "admin/merchantbankcard/link";
	}
}

