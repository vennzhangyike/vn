/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.hfepay.scancode.channel.commons.BaseErrorMsg;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.commons.WebUtil;
import com.hfepay.scancode.commons.condition.HierarchicalSettlementTotalCondition;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.ProfitDetailCondition;
import com.hfepay.scancode.commons.entity.HierarchicalSettlementTotal;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.ProfitDetail;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.operator.HierarchicalSettlementTotalService;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.order.ProfitDetailService;

@Controller
@RequestMapping("/adminManage/hierarchicalsettlementtotal")
public class HierarchicalSettlementTotalController extends BaseController{
	
	@Autowired
	private HierarchicalSettlementTotalService hierarchicalSettlementTotalService;
	@Autowired
	private ProfitDetailService profitDetailService;
	@Autowired
	private MappingDicionService mappingDicionService;
	
	private final String LIST_ACTION = "redirect:/hierarchicalsettlementtotal";
	
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH24:mm:ss"), true));  
	}
	   
	/**
	 * 增加了@ModelAttribute的方法可以在本controller方法调用前执行,可以存放一些共享变量,如枚举值,或是一些初始化操作
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		//model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(ModelMap model,HttpServletRequest request) {
		String organNo = request.getParameter("organNo");
		if(Strings.isNotEmpty(organNo)){
			model.addAttribute("organNo",organNo);
		}	
		return "admin/hierarchicalsettlementtotal/list";
	}
	
	/**
	 * 列表显示             并按创建时间由早到晚排序
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition,HttpServletRequest request){
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		hierarchicalSettlementTotalCondition.setNodeSeq(user.getNodeSeq());
		hierarchicalSettlementTotalCondition.setIdentityFlag(user.getIdentityFlag());
		PagingResult<HierarchicalSettlementTotal> page = hierarchicalSettlementTotalService.findPagingResult(hierarchicalSettlementTotalCondition);
		Pager<HierarchicalSettlementTotal> pager = new Pager<HierarchicalSettlementTotal>();
		pager.setPageNo(hierarchicalSettlementTotalCondition.getPageNo());
		pager.setPageSize(hierarchicalSettlementTotalCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		
		model.addAttribute("pager",pager);
 		Map<String, BigDecimal> t2 = hierarchicalSettlementTotalService.getAmtStatic(hierarchicalSettlementTotalCondition);
		model.addAttribute("amtStatic",t2);
		return "admin/hierarchicalsettlementtotal/listContent";
	}
	
	/** 列表无分页 
	 * @throws ParseException */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition) throws ParseException{		
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			hierarchicalSettlementTotalCondition.setChannelNo(user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			hierarchicalSettlementTotalCondition.setAgentNo(user.getAgentNo());
		}
		List<HierarchicalSettlementTotal> list = hierarchicalSettlementTotalService.findAll(hierarchicalSettlementTotalCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			HierarchicalSettlementTotal entity = hierarchicalSettlementTotalService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/hierarchicalsettlementtotal/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	public String _new(ModelMap model,HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition) throws Exception {
		long flag = 0;
		if(Strings.isEmpty(hierarchicalSettlementTotalCondition.getId())){
			//新增
			flag = hierarchicalSettlementTotalService.insert(hierarchicalSettlementTotalCondition);
		}else{
			flag = hierarchicalSettlementTotalService.update(hierarchicalSettlementTotalCondition);
		}
        
        if(flag > 0){
        	return LIST_ACTION;
        }
		return "admin/hierarchicalsettlementtotal/edit";
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition){		
        long flag = hierarchicalSettlementTotalService.deleteById(hierarchicalSettlementTotalCondition.getId());
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
	 * 
	 * @param request
	 * @param 20160910 shewei
	 * @param 导出交易流水
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/download/exportflowContents")
	public void exportflowContents(HttpServletRequest request,HttpServletResponse response, HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition) throws ParseException {
		//日数据
		hierarchicalSettlementTotalCondition.setDisabledPagingQuery(true);
		List<HierarchicalSettlementTotal> list = hierarchicalSettlementTotalService.findAll(hierarchicalSettlementTotalCondition);
		//拼接报表结构数据
		List<Map<String,Object>> records = new ArrayList<Map<String,Object>>();
		Map<String,Object> record = null;
		//填充有效次数数据
//				Map<String,Object[]> datas = new LinkedHashMap<String, Object[]>();
		for (int i = 0; i < list.size(); i++) {
			HierarchicalSettlementTotal vo =  list.get(i);
			record = new HashMap<String, Object>();
			record.put("代理商编号", vo.getAgentNo());
			/*record.put("平台交易流水", vo.getOutNo());
			record.put("渠道编号", vo.getChannelNo());
			record.put("商户编号", vo.getMerchantNo());
			record.put("商户名称", vo.getMerchantName());
			String payName = "";
			if(vo.getPayCode().equals("WXSMZF")){
				payName = "微信支付扫码";
			} else if(vo.getPayCode().equals("ZFBSMZF")){
				payName = "支付宝扫码";
			} else if(vo.getPayCode().equals("WXGZHZF")){
				payName = "公众号二维码";
			}
			record.put("支付类型", payName);
			record.put("提现交易金额", vo.getOutAmount());
			record.put("通道提现手续费(元)", vo.getFeeAmount());
			record.put("商户提现手续费(元)", vo.getPayerFee());
			record.put("渠道提现手续费(元)", vo.getChannelDrawfee());
			record.put("实际到账金额", vo.getDrawamount());
			record.put("实际交易手续费", vo.getTradefee());
			record.put("实际单笔交易手续费", vo.getDrawfee());
			String Status = "";
			if(vo.getStatus().equals("0")){
				Status = "待出款";
			} else if(vo.getStatus().equals("1")){
				Status = "银行处理中";
			} else if(vo.getStatus().equals("2")){
				Status = "出款成功";
			}else if(vo.getStatus().equals("3")){
				Status = "出款失败";
			}
			record.put("订单状态", Status);
			record.put("交易时间", Dates.format("yyyy/MM/dd HH:mm", vo.getCreateTime()));*/
			records.add(record);
		}
		
		List<String> columns = new ArrayList<String>(){{
			add("代理商编号");
			/*add("平台交易流水");
	        add("渠道名称");
	        add("商户编号");
	        add("商户名称");
	        add("支付类型");
	        add("提现交易金额");
	        add("通道提现手续费(元)");
	        add("商户提现手续费(元)");
	        add("渠道提现手续费(元)");
	        add("实际到账金额");
	        add("实际交易手续费");
	        add("实际单笔交易手续费");
	        add("状态");
	        add("提现时间");*/
	    }};
	    
	    List<String> props = columns;
	    SimpleDateFormat sdfNo = new SimpleDateFormat("yyyyMMddHHmmss");
	    String title = "商户提现流水统计";
	    String downloadName = "商户提现流水统计" + "_" + sdfNo.format(new Date())+".xls";
	    try {
			downloadName = WebUtil.rechangeFileName(request, downloadName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    DynamicColumnExporter.exportWithDownload(downloadName, title, columns, props, records, response, true);
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
	public void excel(HttpServletRequest request, HttpServletResponse response,HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition) throws Exception {
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			hierarchicalSettlementTotalCondition.setChannelNo(user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			hierarchicalSettlementTotalCondition.setAgentNo(user.getAgentNo());
		}
		
		String queryStartDate = (String)request.getParameter("queryStartDate");
		if(queryStartDate != ""){
//			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			hierarchicalSettlementTotalCondition.setQueryStartDate(queryStartDate);			
		}
		String queryEndDate = (String)request.getParameter("queryEndDate");
		if(queryEndDate != ""){
			queryEndDate = queryEndDate + " 23:59:59";
//			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			hierarchicalSettlementTotalCondition.setQueryEndDate(queryEndDate);
		}
		List<HierarchicalSettlementTotal> list = hierarchicalSettlementTotalService.findAll(hierarchicalSettlementTotalCondition);
		//传给模板的数据列表数据
		LinkedList<Map<String,Object>> records = new LinkedList<Map<String,Object>>();
		for (HierarchicalSettlementTotal hierarchicalSettlementTotal : list) {
			TreeMap<String,Object> record = new TreeMap<String, Object>();
			record.put("结算批次号", hierarchicalSettlementTotal.getBatchNo());
			record.put("代理商编号", hierarchicalSettlementTotal.getAgentNo());
			record.put("代理商名称", hierarchicalSettlementTotal.getAgentName());
			record.put("代理商级别", hierarchicalSettlementTotal.getAgentLevel());
			record.put("上级编号", hierarchicalSettlementTotal.getParentNo());
			record.put("上级名称", hierarchicalSettlementTotal.getParentName());
			record.put("交易汇总(元)", hierarchicalSettlementTotal.getTranTotalAmount() == null ? 0:hierarchicalSettlementTotal.getTranTotalAmount());
			record.put("交易分润(元)", hierarchicalSettlementTotal.getTranProfit() == null ? 0:hierarchicalSettlementTotal.getTranProfit());
			record.put("提现汇总(元)", hierarchicalSettlementTotal.getOutTotalAmount() == null ? 0:hierarchicalSettlementTotal.getOutTotalAmount());
			record.put("提现分润(元)", hierarchicalSettlementTotal.getOutProfit() == null ? 0:hierarchicalSettlementTotal.getOutProfit());
			record.put("总利润(元)", hierarchicalSettlementTotal.getTotalProfit() == null ? 0:hierarchicalSettlementTotal.getTotalProfit());
			record.put("结算日期", hierarchicalSettlementTotal.getSettleDate());
			record.put("创建日期", Dates.format("yyyy-MM-dd HH:mm:ss", hierarchicalSettlementTotal.getCreateTime()));			
			records.add(record);
		}
		//表头
		 List<String> columns = Arrays.asList(new String[]{"结算批次号","代理商编号","代理商名称","代理商级别","上级编号","上级名称","交易汇总(元)","交易分润(元)","提现汇总(元)","提现分润(元)","总利润(元)","结算日期","创建日期"});
		//参数列表数据
		 List<String> props = columns;
		String title = null;// apiName +"详情统计"+Strings.getUUID();
		String downloadName = "分润记录" + "(" + Dates.format("yyyyMMddHHmmss", new Date()) + ").xls";
		DynamicColumnExporter.exportWithDownload(downloadName, title, columns, props, records, response, true);
	}
	
	/** 校验导出excel **/
	@RequestMapping(value = "/export/check", method = RequestMethod.GET)
	@ResponseBody
	public BaseErrorMsg check(HttpServletRequest request, HttpServletResponse response,HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition) throws Exception {
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			hierarchicalSettlementTotalCondition.setChannelNo(user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			hierarchicalSettlementTotalCondition.setAgentNo(user.getAgentNo());
		}
		
		String queryStartDate = (String)request.getParameter("queryStartDate");
		if(queryStartDate != ""){
//			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			hierarchicalSettlementTotalCondition.setQueryStartDate(queryStartDate);			
		}
		String queryEndDate = (String)request.getParameter("queryEndDate");
		if(queryEndDate != ""){
			queryEndDate = queryEndDate + " 23:59:59";
//			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			hierarchicalSettlementTotalCondition.setQueryEndDate(queryEndDate);
		}
		List<HierarchicalSettlementTotal> list = hierarchicalSettlementTotalService.findAll(hierarchicalSettlementTotalCondition);
		if(list.size() > 10000){
			return new BaseErrorMsg("分润记录导出数据不能超过10000条");
		}
		return new BaseErrorMsg("0", "分润记录导出校验通过");
	}
	
	/** 分润明细 */
	@RequestMapping(value="/list/detail", method= {RequestMethod.POST})
	public String listDetail(HttpServletRequest request,ModelMap model) throws Exception {
		String organNo = request.getParameter("organNo");
		if(Strings.isNotEmpty(organNo)){
			model.addAttribute("organNo",organNo);
		}	
		String settleDate = request.getParameter("settleDate");
		if(Strings.isNotEmpty(settleDate)){
			model.addAttribute("settleDate",settleDate);
		}	
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo(Constants.PAYWAY_CODE);
		Map<String,String> map = new HashMap<>();
		List<MappingDicion> list = mappingDicionService.findAll(mappingDicionCondition);
		for (MappingDicion mappingDicion : list) {
			map.put(mappingDicion.getParaVal(), mappingDicion.getParaName());
		}
		model.addAttribute("map", map);
		return "admin/hierarchicalsettlementtotal/listDetail";
	}
	
	/** 分润明细 */
	@RequestMapping(value="/content/detail", method= {RequestMethod.POST})
	public String contentDetail(HttpServletRequest request,ModelMap model,ProfitDetailCondition profitDetailCondition) throws Exception {
		PagingResult<ProfitDetail> page = profitDetailService.findPagingResult(profitDetailCondition);
		Pager<ProfitDetail> pager = new Pager<ProfitDetail>();
		pager.setPageNo(profitDetailCondition.getPageNo());
		pager.setPageSize(profitDetailCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/hierarchicalsettlementtotal/listDetailContent";
	}
}

