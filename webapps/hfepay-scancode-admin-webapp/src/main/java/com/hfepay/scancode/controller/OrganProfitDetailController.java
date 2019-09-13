/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.common.excel.jxls.DynamicColumnExporter;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.BaseErrorMsg;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.ProfitDetailCondition;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.entity.ProfitDetail;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.order.ProfitDetailService;

@Controller
@RequestMapping("/adminManage/profitdetail")
public class OrganProfitDetailController extends BaseController{
	
	@Autowired
	private ProfitDetailService profitDetailService;
	@Autowired
	private MappingDicionService mappingDicionService;
	
	private Map<String,String> map = new HashMap<>();
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(ModelMap model) {
		map = transPayCode();
		model.addAttribute("map", map);
		return "admin/profitdetail/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param ChannelBaseCondition
	 * @return: String
	 * @throws Exception 
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,ProfitDetailCondition profitDetailCondition) throws Exception{
		//Map<String,String> map = transPayCode();
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
		return "admin/profitdetail/listContent";
	}
	
	private Map<String,String> transPayCode(){
		Map<String,String> map = new HashMap<String, String>();
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo(Constants.PAYWAY_CODE);
		List<MappingDicion> list = mappingDicionService.findAll(mappingDicionCondition);
		for (MappingDicion mappingDicion : list) {
			map.put(mappingDicion.getParaVal(), mappingDicion.getParaName());
		}
		return map;
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
	public void excel(HttpServletRequest request, HttpServletResponse response,ProfitDetailCondition ProfitDetailCondition) throws Exception {
				
		String transDate = (String)request.getParameter("transDate");
		if(transDate != ""){
//			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			ProfitDetailCondition.setTransDate(transDate);			
		}
		String transDateEnd = (String)request.getParameter("transDateEnd");
		if(transDateEnd != ""){
			transDateEnd = transDateEnd + " 23:59:59";
//			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			ProfitDetailCondition.setTransDateEnd(transDateEnd);
		}
		List<ProfitDetail> list = profitDetailService.findAll(ProfitDetailCondition);
		//传给模板的数据列表数据
		LinkedList<Map<String,Object>> records = new LinkedList<Map<String,Object>>();
		for (ProfitDetail ProfitDetail : list) {
			TreeMap<String,Object> record = new TreeMap<String, Object>();
			record.put("机构编号", ProfitDetail.getIdentityNo());
			record.put("机构名称", "");
			record.put("商户编号", ProfitDetail.getMerchantNo());
			record.put("商户名称", "");
			record.put("支付通道", PayCode.toMap().get(ProfitDetail.getPayCode()));
			record.put("交易类型", "1".equals(ProfitDetail.getTradeType())?"提现":"交易");
			record.put("费率类型", "T0".equals(ProfitDetail.getTradeType()) ? "T0":"T1");
			record.put("分润依据",ProfitDetail.getProfitBase() );
			record.put("分润类型", ProfitDetail.getProfitType());
			record.put("利率差", ProfitDetail.getRateDiff());
			record.put("分润金额", ProfitDetail.getProfit());
			record.put("结算日期",Dates.format("yyyy-MM-dd HH:mm:ss", ProfitDetail.getCreateTime()));
			records.add(record);
		}
		//表头
		 List<String> columns = Arrays.asList(new String[]{"机构编号","机构名称","商户编号","商户名称","支付通道","交易类型","费率类型","分润依据","分润类型","利率差","分润金额","结算日期"});
		//参数列表数据
		 List<String> props = columns;
		String title = null;// apiName +"详情统计"+Strings.getUUID();
		String downloadName = "利润明细" + "(" + Dates.format("yyyyMMddHHmmss", new Date()) + ").xls";
		DynamicColumnExporter.exportWithDownload(downloadName, title, columns, props, records, response, true);
	}
	
	/** 校验导出excel **/
	@RequestMapping(value = "/export/check", method = RequestMethod.GET)
	@ResponseBody
	public BaseErrorMsg check(HttpServletRequest request, HttpServletResponse response,ProfitDetailCondition ProfitDetailCondition) throws Exception {
				
		String transDate = (String)request.getParameter("transDate");
		if(transDate != ""){
//			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			ProfitDetailCondition.setTransDate(transDate);			
		}
		String transDateEnd = (String)request.getParameter("transDateEnd");
		if(transDateEnd != ""){
			transDateEnd = transDateEnd + " 23:59:59";
//			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			ProfitDetailCondition.setTransDateEnd(transDateEnd);
		}
		List<ProfitDetail> list = profitDetailService.findAll(ProfitDetailCondition);
		if(list.size() > 10000){
			return new BaseErrorMsg("分润记录导出数据不能超过10000条");
		}
		return new BaseErrorMsg("0", "分润记录导出校验通过");
	}
}

