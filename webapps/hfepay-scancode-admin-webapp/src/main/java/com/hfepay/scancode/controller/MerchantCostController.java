/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.common.excel.jxls.DynamicColumnExporter;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.BaseErrorMsg;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.entity.MerchantCost;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.MerchantCostService;

@Controller
@RequestMapping("/adminManage/merchantcost")
public class MerchantCostController extends BaseController{
	
	@Autowired
	private MerchantCostService merchantCostService;
	@Autowired
	private MappingDicionService mappingDicionService;
	
	private final String LIST_ACTION = "redirect:/merchantcost";
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2016-11-26 16:44:22
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(ModelMap model) {
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo(Constants.PAYWAY_CODE);
		model.addAttribute("mappingDicionList", mappingDicionService.findAll(mappingDicionCondition));
		return "admin/merchantcost/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param MerchantCostCondition
	 * @return: String
	 * @date CreateDate : 2016-11-26 16:44:22
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,MerchantCostCondition merchantCostCondition){		
		PagingResult<MerchantCost> page = merchantCostService.findPagingResult(merchantCostCondition);
		Pager<MerchantCost> pager = new Pager<MerchantCost>();
		pager.setPageNo(merchantCostCondition.getPageNo());
		pager.setPageSize(merchantCostCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		model.addAttribute("amtStatic",merchantCostService.getAmtStatistics(merchantCostCondition));
		return "admin/merchantcost/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param MerchantCostCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-11-26 16:44:22
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantCostCondition merchantCostCondition){		
        List<MerchantCost> list = merchantCostService.findAll(merchantCostCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param MerchantCostCondition
	 * @return: String
	 * @date CreateDate : 2016-11-26 16:44:22
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	public String saveOrUpdateById(ModelMap model,MerchantCostCondition merchantCostCondition) throws Exception {
		long flag = 0;
		if(Strings.isEmpty(merchantCostCondition.getId())){
			//新增
			flag = merchantCostService.insert(merchantCostCondition);
		}else{
			flag = merchantCostService.update(merchantCostCondition);
		}
        
        if(flag > 0){
        	return LIST_ACTION;
        }
		return "admin/merchantcost/edit";
	}
	
	/**
	 * @Title: show
	 * @Description: 查询单个实体
	 * @author: Ricky
	 * @param id
	 * @return: String
	 * @date CreateDate : 2016-11-26 16:44:22
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantCost entity = merchantCostService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/merchantcost/edit";
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param MerchantCostCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-11-26 16:44:22
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,MerchantCostCondition merchantCostCondition){		
        long flag = merchantCostService.deleteById(merchantCostCondition.getId());
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
	


	/**
	 * @Title: saveOrUpdateById
	 * @Description: 状态变更
	 * @author: wh
	 * @param MerchantCostCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-11-26 16:44:22
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,MerchantCostCondition merchantCostCondition){		
        long flag = merchantCostService.updateStatus(merchantCostCondition.getId(),merchantCostCondition.getStatus());
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
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MerchantCost entity = merchantCostService.findById(id);
		model.addAttribute("item",entity);
		return "admin/merchantcost/detail";
	}
	
	/**
	 * 导出excel 
	 * @param request
	 * @param response
	 * @param condition
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "serial" })
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void excel(HttpServletRequest request, HttpServletResponse response,MerchantCostCondition merchantCostCondition) throws Exception {
				
		String beginTime = (String)request.getParameter("beginTimeStr");
		if(beginTime != ""){
//			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			merchantCostCondition.setBeginTimeStr(beginTime);			
		}
		String endTime = (String)request.getParameter("endTimeStr");
		if(endTime != ""){
			endTime = endTime + " 23:59:59";
//			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			merchantCostCondition.setEndTimeStr(endTime);
		}
		List<MerchantCost> list = merchantCostService.findAll(merchantCostCondition);
		//传给模板的数据列表数据
		LinkedList<Map<String,Object>> records = new LinkedList<Map<String,Object>>();
		for (MerchantCost merchantCost : list) {
			TreeMap<String,Object> record = new TreeMap<String, Object>();
			record.put("进出账流水号", merchantCost.getTradeNo());
			record.put("渠道名称", merchantCost.getChannelName());
			record.put("代理商名称", merchantCost.getAgentName());
			record.put("商户名称", merchantCost.getMerchantName());
			record.put("支付通道", PayCode.toMap().get(merchantCost.getPayCode()));
			record.put("进出账类型", "1".equals(merchantCost.getType())?"交易":"提现");
			record.put("进出账金额(元)", merchantCost.getAmount() == null ? 0:merchantCost.getAmount());
			record.put("交易成本(元)", merchantCost.getMerchantCost() == null ? 0:merchantCost.getMerchantCost());
			record.put("进出账时间", Dates.format("yyyy-MM-dd HH:mm:ss", merchantCost.getCreateTime()));			
			records.add(record);
		}
		//表头
		 List<String> columns = Arrays.asList(new String[]{"进出账流水号","渠道名称","代理商名称","商户名称","支付通道","进出账类型","进出账金额(元)","交易成本(元)","进出账时间"});
		//参数列表数据
		 List<String> props = columns;
		String title = null;// apiName +"详情统计"+Strings.getUUID();
		String downloadName = "商户成本" + "(" + Dates.format("yyyyMMddHHmmss", new Date()) + ").xls";
		DynamicColumnExporter.exportWithDownload(downloadName, title, columns, props, records, response, true);
	}
	
	/** 校验导出excel **/
	@RequestMapping(value = "/export/check", method = RequestMethod.GET)
	@ResponseBody
	public BaseErrorMsg check(HttpServletRequest request, HttpServletResponse response,MerchantCostCondition merchantCostCondition) throws Exception {
				
		String beginTime = (String)request.getParameter("beginTimeStr");
		if(beginTime != ""){
//			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			merchantCostCondition.setBeginTimeStr(beginTime);			
		}
		String endTime = (String)request.getParameter("endTimeStr");
		if(endTime != ""){
			endTime = endTime + " 23:59:59";
//			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			merchantCostCondition.setEndTimeStr(endTime);
		}
		List<MerchantCost> list = merchantCostService.findAll(merchantCostCondition);
		if(list.size() > 10000){
			return new BaseErrorMsg("商户成本导出数据不能超过10000条");
		}
		return new BaseErrorMsg("0", "商户成本导出校验通过");
	}
	
}

