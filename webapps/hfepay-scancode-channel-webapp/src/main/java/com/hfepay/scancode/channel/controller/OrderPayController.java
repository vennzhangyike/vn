/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

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

import com.hfepay.common.excel.jxls.DynamicColumnExporter;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.BaseErrorMsg;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.PayStatus;
import com.hfepay.scancode.commons.contants.TradeType;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.order.OrderPayService;

@Controller
@RequestMapping("/adminManage/orderpay")
public class OrderPayController extends BaseController{
	
	@Autowired
	private OrderPayService orderPayService;
	@Autowired
	private MappingDicionService mappingDicionService;
	
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
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo(Constants.PAYWAY_CODE);
		model.addAttribute("mappingDicionList", mappingDicionService.findAll(mappingDicionCondition));
		return "admin/orderpay/list";
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
	public String listContent(HttpServletRequest request,ModelMap model,OrderPayCondition orderPayCondition) throws Exception{	
		
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			orderPayCondition.setChannelNo(user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			orderPayCondition.setAgentNo(user.getAgentNo());
		}else if(Constants.IDENTITYFLAG_MERCHANT.equals(user.getIdentityFlag())){
			orderPayCondition.setMerchantNo(user.getMerchantNo());
		}else if(Constants.IDENTITYFLAG_CASHIER.equals(user.getIdentityFlag())){
			orderPayCondition.setCashierNo(user.getIdentityNo());
		}
		
		String beginTimeStr = (String)request.getParameter("beginTimeStr");
		if(beginTimeStr != ""){
			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			orderPayCondition.setBeginTime(beginTime);			
		}
		String endTimeStr = (String)request.getParameter("endTimeStr");
		if(endTimeStr != ""){
			endTimeStr = endTimeStr + " 23:59:59";
			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			orderPayCondition.setEndTime(endTime);
		}
		PagingResult<OrderPay> page = orderPayService.findPagingResult(orderPayCondition);
		Pager<OrderPay> pager = new Pager<OrderPay>();
		pager.setPageNo(orderPayCondition.getPageNo());
		pager.setPageSize(orderPayCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		
		model.addAttribute("pager",pager);
		return "admin/orderpay/listContent";
	}
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		OrderPay entity = orderPayService.findById(id);
		model.addAttribute("item",entity);
		return "admin/orderpay/detail";
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
	public void excel(HttpServletRequest request, HttpServletResponse response,OrderPayCondition orderPayCondition) throws Exception {
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			orderPayCondition.setChannelNo(user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			orderPayCondition.setAgentNo(user.getAgentNo());
		}else if(Constants.IDENTITYFLAG_MERCHANT.equals(user.getIdentityFlag())){
			orderPayCondition.setMerchantNo(user.getMerchantNo());
		}else if(Constants.IDENTITYFLAG_CASHIER.equals(user.getIdentityFlag())){
			orderPayCondition.setCashierNo(user.getIdentityNo());
		}
		String beginTimeStr = (String)request.getParameter("beginTimeStr");
		if(beginTimeStr != ""){
			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			orderPayCondition.setBeginTime(beginTime);			
		}
		String endTimeStr = (String)request.getParameter("endTimeStr");
		if(endTimeStr != ""){
			endTimeStr = endTimeStr + " 23:59:59";
			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			orderPayCondition.setEndTime(endTime);
		}
		List<OrderPay> orderPayList = orderPayService.findAll(orderPayCondition);
		//传给模板的数据列表数据
		LinkedList<Map<String,Object>> records = new LinkedList<Map<String,Object>>();
		for (OrderPay orderPay : orderPayList) {
			TreeMap<String,Object> record = new TreeMap<String, Object>();
			record.put("支付订单编号", orderPay.getPayNo());
			record.put("交易订单编号", orderPay.getTradeNo());
			record.put("渠道名称", orderPay.getChannelName());
			record.put("代理商名称", orderPay.getAgentName());
			record.put("商户名称", orderPay.getMerchantName());
			String tradeType="";
			if(Strings.equals(TradeType.TRADE_TYPE_PAY.getCode(), orderPay.getTradeType())){
				tradeType=TradeType.TRADE_TYPE_PAY.getDesc();
			}else if(Strings.equals(TradeType.TRADE_TYPE_WITHDRAW.getCode(), orderPay.getTradeType())){
				tradeType=TradeType.TRADE_TYPE_WITHDRAW.getDesc();
			}
			record.put("交易类型", tradeType);
			String payCode="";
			if(Strings.equals(PayCode.PAYCODE_WXGZHZF.getCode(), orderPay.getPayCode())){
				payCode=PayCode.PAYCODE_WXGZHZF.getDesc();
			}else if(Strings.equals(PayCode.PAYCODE_ZFBSMZF.getCode(), orderPay.getPayCode())){
				payCode=PayCode.PAYCODE_ZFBSMZF.getDesc();
			}else if(Strings.equals(PayCode.PAYCODE_WXSMZF.getCode(), orderPay.getPayCode())){
				payCode=PayCode.PAYCODE_WXSMZF.getDesc();
			}
			record.put("交易通道", payCode);
			record.put("订单金额(元)", orderPay.getOrderAmt());
			String payStatus="";
			if(Strings.equals(PayStatus.PAYSTATUS_ACCOUNTS_WAIT.getCode(), orderPay.getPayStatus())){
				payStatus=PayStatus.PAYSTATUS_ACCOUNTS_WAIT.getDesc();
			}else if(Strings.equals(PayStatus.PAYSTATUS_CHANNEL_TREATE.getCode(), orderPay.getPayStatus())){
				payStatus=PayStatus.PAYSTATUS_CHANNEL_TREATE.getDesc();
			}else if(Strings.equals(PayStatus.PAYSTATUS_FAIL.getCode(), orderPay.getPayStatus())){
				payStatus=PayStatus.PAYSTATUS_FAIL.getDesc();
			}else if(Strings.equals(PayStatus.PAYSTATUS_SUCCESS.getCode(), orderPay.getPayStatus())){
				payStatus=PayStatus.PAYSTATUS_SUCCESS.getDesc();
			}			
			record.put("支付状态", payStatus);
			record.put("交易时间", Dates.format("yyyy-MM-dd HH:mm:ss", orderPay.getBeginTime()));
			records.add(record);
		}
		//表头
		 List<String> columns = Arrays.asList(new String[]{"支付订单编号","交易订单编号","渠道名称","代理商名称","商户名称","交易类型","交易通道","订单金额(元)","支付状态","交易时间"});
		//参数列表数据
		 List<String> props = columns;
		String title = null;// apiName +"详情统计"+Strings.getUUID();
		String downloadName = "资金流水" + "(" + Dates.format("yyyyMMddHHmmss", new Date()) + ").xls";
		DynamicColumnExporter.exportWithDownload(downloadName, title, columns, props, records, response, true);
	}
	
	/** 校验导出excel **/
	@RequestMapping(value = "/export/check", method = RequestMethod.GET)
	@ResponseBody
	public BaseErrorMsg check(HttpServletRequest request, HttpServletResponse response,OrderPayCondition orderPayCondition) throws Exception {
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			orderPayCondition.setChannelNo(user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			orderPayCondition.setAgentNo(user.getAgentNo());
		}else if(Constants.IDENTITYFLAG_MERCHANT.equals(user.getIdentityFlag())){
			orderPayCondition.setMerchantNo(user.getMerchantNo());
		}else if(Constants.IDENTITYFLAG_CASHIER.equals(user.getIdentityFlag())){
			orderPayCondition.setCashierNo(user.getIdentityNo());
		}
		
		String beginTimeStr = (String)request.getParameter("beginTimeStr");
		if(beginTimeStr != ""){
			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			orderPayCondition.setBeginTime(beginTime);			
		}
		String endTimeStr = (String)request.getParameter("endTimeStr");
		if(endTimeStr != ""){
			endTimeStr = endTimeStr + " 23:59:59";
			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			orderPayCondition.setEndTime(endTime);
		}
		List<OrderPay> orderPayList = orderPayService.findAll(orderPayCondition);
		if(orderPayList.size() > 10000){
			return new BaseErrorMsg("资金流水导出数据不能超过10000条");
		}
		return new BaseErrorMsg("0", "资金流水导出校验通过");
	}
}

