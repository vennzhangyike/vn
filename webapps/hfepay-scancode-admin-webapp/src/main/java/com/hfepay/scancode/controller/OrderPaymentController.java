/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.serializer.ValueFilter;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.contants.OrderStatus;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.order.OrderPaymentService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/orderpayment")
public class OrderPaymentController extends BaseController{
	
	@Autowired
	private OrderPaymentService orderPaymentService;
	@Autowired
	private MappingDicionService mappingDicionService;
	@Autowired
	private MerchantStoreService merchantStoreService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request,ModelMap model) {
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo(Constants.PAYWAY_CODE);
		model.addAttribute("mappingDicionList", mappingDicionService.findAll(mappingDicionCondition));
		return "admin/orderpayment/list";
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
	public String listContent(HttpServletRequest request,ModelMap model,OrderPaymentCondition orderPaymentCondition) throws Exception{
		String beginTimeStr = (String)request.getParameter("beginTimeStr");
		if(beginTimeStr != ""){
			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			orderPaymentCondition.setBeginTime(beginTime);			
		}
		String endTimeStr = (String)request.getParameter("endTimeStr");
		if(endTimeStr != ""){			
			endTimeStr = endTimeStr + " 23:59:59";
			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			orderPaymentCondition.setEndTime(endTime);
		}
		PagingResult<OrderPayment> page = orderPaymentService.findPagingResult(orderPaymentCondition);
		Pager<OrderPayment> pager = new Pager<OrderPayment>();
		pager.setPageNo(orderPaymentCondition.getPageNo());
		pager.setPageSize(orderPaymentCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		model.addAttribute("amtStatic",orderPaymentService.getAmtStaticMoney(orderPaymentCondition));
		return "admin/orderpayment/listContent";
	}
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		OrderPayment entity = orderPaymentService.findById(id);
		model.addAttribute("item",entity);
		return "admin/orderpayment/detail";
	}
	
	/** 打印交易订单 */
	@RequestMapping(value = "/print/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public JSON print(HttpServletRequest request,  @PathVariable String id) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();		
		try {			
			OrderPayment orderPayment = orderPaymentService.findById(id);
			//将对象转为JSON字符串
			String orderPaymentJson = com.alibaba.fastjson.JSON.toJSONString(orderPayment,filter);
			//将json字符串转为map
			Map<String, Object> orderPaymentMap = com.alibaba.fastjson.JSON.parseObject(orderPaymentJson, Map.class);
			map.put("orderPayment", orderPaymentMap);
			String payName = "";
			if(PayCode.PAYCODE_WXGZHZF.getCode().equals(orderPayment.getPayCode())){
				payName = PayCode.PAYCODE_WXGZHZF.getDesc();
			}else if(PayCode.PAYCODE_WXSMZF.getCode().equals(orderPayment.getPayCode())){
				payName = PayCode.PAYCODE_WXSMZF.getDesc();
			}else if(PayCode.PAYCODE_ZFBSMZF.getCode().equals(orderPayment.getPayCode())){
				payName = PayCode.PAYCODE_ZFBSMZF.getDesc();
			}
			map.put("payName", payName);
			String orderStatus = "";
			if(OrderStatus.ORDER_FAIL.getCode().equals(orderPayment.getOrderStatus())){
				orderStatus = OrderStatus.ORDER_FAIL.getDesc();
			}else if(OrderStatus.ORDER_SUCCESS.getCode().equals(orderPayment.getOrderStatus())){
				orderStatus = OrderStatus.ORDER_SUCCESS.getDesc();
			}else if(OrderStatus.ORDER_TREATE.getCode().equals(orderPayment.getOrderStatus())){
				orderStatus = OrderStatus.ORDER_TREATE.getDesc();
			}
			map.put("orderStatus",orderStatus);
			String orderTimeStr = Dates.format("yyyy-MM-dd HH:mm:ss", orderPayment.getBeginTime());
			map.put("orderTimeStr",orderTimeStr);
			String merchantNo = orderPayment.getMerchantNo();
			MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantNo);
			//将对象转为JSON字符串
			String merchantInfoJson = com.alibaba.fastjson.JSON.toJSONString(merchantInfo,filter);
			//将json字符串转为map
			Map<String, Object> merchantMap = com.alibaba.fastjson.JSON.parseObject(merchantInfoJson, Map.class);
			
			map.put("merchantInfo", merchantMap);
			map.put(EXECUTE_STATUS, SUCCESS);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (v == null)
                return "";
            return v;
        }
    };
}

