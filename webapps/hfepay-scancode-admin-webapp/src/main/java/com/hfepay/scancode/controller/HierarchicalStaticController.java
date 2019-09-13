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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.common.excel.jxls.DynamicColumnExporter;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.BaseErrorMsg;
import com.hfepay.scancode.commons.condition.HierarchicalSettlementTotalCondition;
import com.hfepay.scancode.commons.condition.HierarchicalStaticCondition;
import com.hfepay.scancode.commons.entity.HierarchicalSettlementTotal;
import com.hfepay.scancode.commons.entity.HierarchicalStatic;
import com.hfepay.scancode.service.contants.ConfigPreCode;
import com.hfepay.scancode.service.operator.HierarchicalSettlementTotalService;

/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

@Controller
@RequestMapping("/adminManage/hierarchicalstatic")
public class HierarchicalStaticController extends BaseController{
	
	@Autowired
	private HierarchicalSettlementTotalService hierarchicalSettlementTotalService;
	
	
	private Logger logger = LoggerFactory.getLogger(HierarchicalStaticController.class);
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "admin/hierarchicalstatic/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,HierarchicalStaticCondition hierarchicalStaticCondition,HttpServletRequest request){
		//List<String> agentList = new ArrayList<String>();
		PagingResult<HierarchicalStatic> page = hierarchicalSettlementTotalService.findHierarchicalStatic(hierarchicalStaticCondition,ConfigPreCode.SYSTEM_NODE_SEQ);
		Pager<HierarchicalStatic> pager = new Pager<HierarchicalStatic>();
		pager.setPageNo(hierarchicalStaticCondition.getPageNo());
		pager.setPageSize(hierarchicalStaticCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
	
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/hierarchicalstatic/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition){		
        List<HierarchicalSettlementTotal> list = hierarchicalSettlementTotalService.findAll(hierarchicalSettlementTotalCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
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
	public void excel(HttpServletRequest request, HttpServletResponse response,HierarchicalStaticCondition hierarchicalStaticCondition) throws Exception {
		List<HierarchicalStatic> list = hierarchicalSettlementTotalService.findAllHierarchicalStatic(hierarchicalStaticCondition,ConfigPreCode.SYSTEM_NODE_SEQ).getResult();
		//传给模板的数据列表数据
		LinkedList<Map<String,Object>> records = new LinkedList<Map<String,Object>>();
		for (HierarchicalStatic hierarchicalStatic : list) {
			TreeMap<String,Object> record = new TreeMap<String, Object>();
			record.put("机构编号", hierarchicalStatic.getOrganNo());
			record.put("机构名称", hierarchicalStatic.getOrganName());
			record.put("利润总额(元)", hierarchicalStatic.getTotalProfit());
			record.put("提现总额(元)", hierarchicalStatic.getTotalActualAmount());
			record.put("钱包余额(元)", hierarchicalStatic.getBalance());
			records.add(record);
		}
		//表头
		 List<String> columns = Arrays.asList(new String[]{"机构编号","机构名称","利润总额(元)","提现总额(元)","钱包余额(元)"});
		//参数列表数据
		 List<String> props = columns;
		String title = null;// apiName +"详情统计"+Strings.getUUID();
		String downloadName = "分润统计记录" + "(" + Dates.format("yyyyMMddHHmmss", new Date()) + ").xls";
		DynamicColumnExporter.exportWithDownload(downloadName, title, columns, props, records, response, true);
	}
	
	/** 校验导出excel **/
	@RequestMapping(value = "/export/check", method = RequestMethod.GET)
	@ResponseBody
	public BaseErrorMsg check(HttpServletRequest request, HttpServletResponse response,HierarchicalStaticCondition hierarchicalStaticCondition) throws Exception {
		List<HierarchicalStatic> list = hierarchicalSettlementTotalService.findAllHierarchicalStatic(hierarchicalStaticCondition,ConfigPreCode.SYSTEM_NODE_SEQ).getResult();
		if(list.size() > 10000){
			return new BaseErrorMsg("分润统计记录导出数据不能超过10000条");
		}
		return new BaseErrorMsg("0", "分润统计记录导出校验通过");
	}
	
}

