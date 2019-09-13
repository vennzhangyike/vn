/*

 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.math.BigDecimal;
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
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.MarketingPayLogCondition;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.entity.MarketingPayLog;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.service.operator.MarketingPayLogService;
import com.hfepay.scancode.service.operator.MerchantInfoService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;



/*查询全局变量applicationContext
 * 
 * 
 * 
*/

@Controller
@RequestMapping("/adminManage/marketingpaylog")
public class MarketingPayLogController extends BaseController {
	
	
	@Autowired
	private MarketingPayLogService marketingPayLogService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "admin/marketingpaylog/list";
	}
	/**
	 * 分页查询
	 *
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MarketingPayLogCondition marketingPayLogCondition) throws Exception{		
		/*String beginTimeStr = (String)request.getParameter("beginTimeStr");
		if(beginTimeStr != ""){
			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			marketingPayLogCondition.setBeginTime(beginTime);			
		}
		String endTimeStr = (String)request.getParameter("endTimeStr");
		if(endTimeStr != ""){
			endTimeStr = endTimeStr + " 23:59:59";
			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			marketingPayLogCondition.setEndTime(endTime);
		}
		*/
		PagingResult<MarketingPayLog> page = marketingPayLogService.findPagingResult(marketingPayLogCondition);
		Pager<MarketingPayLog> pager = new Pager<MarketingPayLog>();
		pager.setPageNo(marketingPayLogCondition.getPageNo());
		pager.setPageSize(marketingPayLogCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		List<MarketingPayLog> t = null;
 	    t = marketingPayLogService.findAll(marketingPayLogCondition);
 		Map<String, BigDecimal> t2 = marketingPayLogService.getAmtStatic(t);
		model.addAttribute("amtStatic",t2);
		return "admin/marketingpaylog/listContent";
	}
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		MarketingPayLog entity = marketingPayLogService.findById(id);
		model.addAttribute("item",entity);
		return "admin/marketingpaylog/detail";
	}

	/** 打印交易订单 */
	@RequestMapping(value = "/print/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public JSON print(HttpServletRequest request,  @PathVariable String id) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();		
		try {			
			MarketingPayLog marketingPayLog = marketingPayLogService.findById(id);
			map.put("marketingPayLog", marketingPayLog);
			String payName = "";
			if(PayCode.PAYCODE_WXGZHZF.getCode().equals(marketingPayLog.getPayCode())){
				payName = PayCode.PAYCODE_WXGZHZF.getDesc();
			}else if(PayCode.PAYCODE_WXSMZF.getCode().equals(marketingPayLog.getPayCode())){
				payName = PayCode.PAYCODE_WXSMZF.getDesc();
			}else if(PayCode.PAYCODE_ZFBSMZF.getCode().equals(marketingPayLog.getPayCode())){
				payName = PayCode.PAYCODE_ZFBSMZF.getDesc();
			}
			map.put("payName", payName);
			String orderStatus = "";
			if(OrderStatus.ORDER_FAIL.getCode().equals(marketingPayLog.getOrderStatus())){
				orderStatus = OrderStatus.ORDER_FAIL.getDesc();
			}else if(OrderStatus.ORDER_SUCCESS.getCode().equals(marketingPayLog.getOrderStatus())){
				orderStatus = OrderStatus.ORDER_SUCCESS.getDesc();
			}else if(OrderStatus.ORDER_TREATE.getCode().equals(marketingPayLog.getOrderStatus())){
				orderStatus = OrderStatus.ORDER_TREATE.getDesc();
			}
			map.put("orderStatus",orderStatus);
			String orderTimeStr = Dates.format("yyyy-MM-dd HH:mm:ss", marketingPayLog.getBeginTime());
			map.put("orderTimeStr",orderTimeStr);
			String merchantNo = marketingPayLog.getMerchantNo();
			MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantNo);
			map.put("merchantInfo", merchantInfo);
			map.put(EXECUTE_STATUS, SUCCESS);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}

}
