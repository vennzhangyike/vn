package com.hfepay.scancode.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Dates.DF;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.ScanCodeGetWayErrorCode;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.DateFlagEnu;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.OrderPay;
import com.hfepay.scancode.commons.entity.Withdrawals;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.order.OrderPayService;
import com.hfepay.scancode.service.order.WithdrawalsService;
import com.hfepay.scancode.service.utils.StringUtils;

/**
 * 账单查询
 * @author husain
 *
 */
@Controller
@RequestMapping("/scancode")
public class BillController {
	public static final Logger log = LoggerFactory.getLogger(BillController.class);
	
	@Autowired
	private OrderPayService orderPayService;
	
	@Autowired
	private WithdrawalsService withdrawalsService;
	@Autowired
	private MerchantStoreService merchantStoreService;
	
	@RequestMapping("/bill")
	public String bill(ModelMap model,HttpServletRequest request){
		String  month = Dates.format("yyyy年MM月", new Date());
		String date = Dates.format(DF.yyyy_MM_dd, new Date());
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		request.setAttribute("month", month);
		Map<String,BigDecimal> map = orderPayService.getTotalMoney(date,DateFlagEnu.MONTH.getCode(),admin.getIdentityNo(),admin.getIdentityFlag());//按月统计交易金额
		request.setAttribute("total", map);//交易提现金额汇总
		return "scancode/wechat/bill";
	}
	
	@RequestMapping("/storeList")
	@ResponseBody
	public List getStoreList(HttpServletRequest request){
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
		merchantStoreCondition.setMerchantNo(admin.getMerchantNo());
		List<MerchantStore> storeList = merchantStoreService.findAll(merchantStoreCondition);
		return storeList;
	}
	
	@RequestMapping("/withdrawlsbill")
	public String withdrawlsbill(ModelMap model,HttpServletRequest request){
		String month = Dates.format("yyyy年MM月", new Date());
		String date = Dates.format(DF.yyyy_MM_dd, new Date());
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		//收银员无查看提现明细权限
		if (admin.getIdentityFlag().equals("4")) {
			request.setAttribute("error", ScanCodeGetWayErrorCode.TRADE_400001.getDesc());
			return "scancode/wechat/scan_error";
		}
		
		BigDecimal total = withdrawalsService.getTotalMoney(date,DateFlagEnu.MONTH.getCode(),admin.getMerchantNo());//按月统计交易金额
		log.info("##########提现总金额：[total:"+total+"]##########");
		
		request.setAttribute("month", month);
		request.setAttribute("total", total);
		return "scancode/wechat/withdrawlsbill";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 * @throws Exception  
	 */
	@RequestMapping(value = "/billContent", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,OrderPayCondition orderPayCondition) throws Exception{
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		orderPayCondition.setQueryStartTime(StringUtils.getMonthFirstDay());
		orderPayCondition.setQueryEndTime(StringUtils.getNextMonthFirstDay());
		if(admin.getIdentityFlag().equals(IdentityType.Identity_Merchant.getCode())){
			orderPayCondition.setMerchantNo(admin.getIdentityNo());
		}else if(admin.getIdentityFlag().equals(IdentityType.Identity_Cashier.getCode())){
			orderPayCondition.setCashierNo(admin.getIdentityNo());
		}
		//orderPayCondition.setTradeType("02");//支付
		orderPayCondition.setPageSize(5);
		PagingResult<OrderPay> page = orderPayService.findPagingResult(orderPayCondition);
		Pager<OrderPay> pager = new Pager<OrderPay>();
		pager.setPageNo(orderPayCondition.getPageNo());
		pager.setPageSize(orderPayCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
		int totalPages = (int)(page.getTotalCount()%5==0?page.getTotalCount()/5:page.getTotalCount()/5+1);
		pager.setTotalPages(totalPages);
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		model.addAttribute("pager",pager);
		return "scancode/wechat/billcontent";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 * @throws Exception , method= {RequestMethod.POST}
	 */
	@RequestMapping(value = "/withdrawlsBillContent", method= {RequestMethod.POST})
	public String withdrawlsListContent(HttpServletRequest request,ModelMap model,WithdrawalsCondition withdrawalsCondition) throws Exception{
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		withdrawalsCondition.setQueryStartTime(StringUtils.getMonthFirstDay());
		withdrawalsCondition.setQueryEndTime(StringUtils.getNextMonthFirstDay());
		withdrawalsCondition.setMerchantNo(admin.getIdentityNo());
		withdrawalsCondition.setPageSize(5);
		PagingResult<Withdrawals> page = withdrawalsService.findPagingResult(withdrawalsCondition);
		Pager<Withdrawals> pager = new Pager<Withdrawals>();
		pager.setPageNo(withdrawalsCondition.getPageNo());
		pager.setPageSize(withdrawalsCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
		int totalPages = (int)(page.getTotalCount()%5==0?page.getTotalCount()/5:page.getTotalCount()/5+1);
		pager.setTotalPages(totalPages);
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		model.addAttribute("pager",pager);
		return "scancode/wechat/withdrawbillscontent";
	}
}
