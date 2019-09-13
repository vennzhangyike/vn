/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2017
 */
package com.hfepay.scancode.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.ClearingT0ErrCondition;
import com.hfepay.scancode.commons.condition.ClearingT1ErrCondition;
import com.hfepay.scancode.commons.condition.OrderPayCondition;
import com.hfepay.scancode.commons.condition.OrderPaymentCondition;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.contants.CheckFlag;
import com.hfepay.scancode.commons.contants.OrderPayStatus;
import com.hfepay.scancode.commons.contants.OrderPaymentStatus;
import com.hfepay.scancode.commons.contants.ProcessingStatus;
import com.hfepay.scancode.commons.contants.WithdrawsStatus;
import com.hfepay.scancode.commons.entity.ClearingT1Err;
import com.hfepay.scancode.commons.entity.OrderPayment;
import com.hfepay.scancode.service.operator.ClearingT1ErrService;
import com.hfepay.scancode.service.order.OrderPayService;
import com.hfepay.scancode.service.order.OrderPaymentService;
import com.hfepay.scancode.service.order.WithdrawalsService;

@Controller
@RequestMapping("/adminManage/clearingt1err")
public class ClearingT1ErrController extends BaseController{
	
	@Autowired
	private ClearingT1ErrService clearingT1ErrService;
	
	@Autowired
	private OrderPaymentService orderPaymentService;
	
	@Autowired
	private OrderPayService orderPayService;

	@Autowired
	private WithdrawalsService withdrawalsService;

	private final String LIST_ACTION = "redirect:/clearingt1err";

	
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "admin/clearingt1err/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param ClearingT1ErrCondition
	 * @return: String
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,ClearingT1ErrCondition clearingT1ErrCondition){		
		PagingResult<ClearingT1Err> page = clearingT1ErrService.findPagingResult(clearingT1ErrCondition);
		Pager<ClearingT1Err> pager = new Pager<ClearingT1Err>();
		pager.setPageNo(clearingT1ErrCondition.getPageNo());
		pager.setPageSize(clearingT1ErrCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/clearingt1err/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param ClearingT1ErrCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,ClearingT1ErrCondition clearingT1ErrCondition){		
        List<ClearingT1Err> list = clearingT1ErrService.findAll(clearingT1ErrCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param ClearingT1ErrCondition
	 * @return: String
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	public String saveOrUpdateById(ModelMap model,ClearingT1ErrCondition clearingT1ErrCondition) throws Exception {
		long flag = 0;
		if(Strings.isEmpty(clearingT1ErrCondition.getId())){
			//新增
			flag = clearingT1ErrService.insert(clearingT1ErrCondition);
		}else{
			flag = clearingT1ErrService.update(clearingT1ErrCondition);
		}
        
        if(flag > 0){
        	return LIST_ACTION;
        }
		return "admin/clearingt1err/edit";
	}
	
	/**
	 * @Title: show
	 * @Description: 查询单个实体
	 * @author: Ricky
	 * @param id
	 * @return: String
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			ClearingT1Err entity = clearingT1ErrService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/clearingt1err/edit";
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param ClearingT1ErrCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-03-20 09:55:49
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,ClearingT1ErrCondition clearingT1ErrCondition){		
        long flag = clearingT1ErrService.deleteById(clearingT1ErrCondition.getId());
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
	 * @author liushuyu
	 * Desc 查看交易订单详情
	 * @return
	 */
	@RequestMapping(value="/detail/{id}")
	public String showDetail(@PathVariable("id")String id,Model model){
		ClearingT1Err t1 = clearingT1ErrService.findById(id);
		String tradeNo=t1.getTradeNo();
		OrderPayment orderPayment=new OrderPayment();
		orderPayment.setTradeNo(tradeNo);
		OrderPayment entity = orderPaymentService.queryOrderPayment(orderPayment);
		model.addAttribute("item",entity);
		return "admin/clearingt1err/detail";
	}
	
	/**
	 * 
	 * @author liushuyu
	 * Desc 调账
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject update(String id,String checkFlag){
		boolean flag=true;
		try{
			flag=clearingT1ErrService.updateMuti(id, checkFlag);
		}catch(Exception e){
			flag=false;
		}
		return getJson(flag);
	}
	
	/**
	 * 
	 * @author liushuyu
	 * Desc 如果flag获取一个成功的json
	 * @param flag
	 * @return
	 */
	private JSONObject getJson(boolean flag){
		JSONObject json = new JSONObject();
		if(flag){ 
			json.put("message", Constants.SUCCESS_MSG);
			json.put("status", Constants.SUCCESS_STATE);
		}else{
			json.put("message", Constants.FAIL_MSG);
			json.put("status", Constants.FAIL_STATE);
		}
		
		return json;
	}

}

