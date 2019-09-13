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
import com.hfepay.scancode.commons.entity.ClearingT0Err;
import com.hfepay.scancode.commons.entity.Withdrawals;
import com.hfepay.scancode.service.operator.ClearingT0ErrService;
import com.hfepay.scancode.service.order.WithdrawalsService;

@Controller
@RequestMapping("/adminManage/clearingt0err")
public class ClearingT0ErrController extends BaseController{

	@Autowired
	private ClearingT0ErrService clearingT0ErrService;

	@Autowired
	private WithdrawalsService  withdrawalsService;


	private final String LIST_ACTION = "redirect:/clearingt0err";

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "admin/clearingt0err/list";
	}

	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param ClearingT0ErrCondition
	 * @return: String
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,ClearingT0ErrCondition clearingT0ErrCondition){		
		PagingResult<ClearingT0Err> page = clearingT0ErrService.findPagingResult(clearingT0ErrCondition);
		Pager<ClearingT0Err> pager = new Pager<ClearingT0Err>();
		pager.setPageNo(clearingT0ErrCondition.getPageNo());
		pager.setPageSize(clearingT0ErrCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/clearingt0err/listContent";
	}


	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param ClearingT0ErrCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,ClearingT0ErrCondition clearingT0ErrCondition){		
		List<ClearingT0Err> list = clearingT0ErrService.findAll(clearingT0ErrCondition);
		JSONObject json = new JSONObject();
		json.put("objList", list);
		return json;
	}


	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param ClearingT0ErrCondition
	 * @return: String
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	public String saveOrUpdateById(ModelMap model,ClearingT0ErrCondition clearingT0ErrCondition) throws Exception {
		long flag = 0;
		if(Strings.isEmpty(clearingT0ErrCondition.getId())){
			//新增
			flag = clearingT0ErrService.insert(clearingT0ErrCondition);
		}else{
			flag = clearingT0ErrService.update(clearingT0ErrCondition);
		}

		if(flag > 0){
			return LIST_ACTION;
		}
		return "admin/clearingt0err/edit";
	}

	/**
	 * @Title: show
	 * @Description: 查询单个实体
	 * @author: Ricky
	 * @param id
	 * @return: String
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			ClearingT0Err entity = clearingT0ErrService.findById(id);
			model.addAttribute("Obj", entity);
		}
		return "admin/clearingt0err/edit";
	}

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param ClearingT0ErrCondition
	 * @return: JSONObject
	 * @date CreateDate : 2017-03-20 09:55:50
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,ClearingT0ErrCondition clearingT0ErrCondition){		
		long flag = clearingT0ErrService.deleteById(clearingT0ErrCondition.getId());
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
		ClearingT0Err t0 = clearingT0ErrService.findById(id);
		String tradeNo=t0.getTradeNo();
		Withdrawals withdrawals=new Withdrawals();
		withdrawals.setTradeNo(tradeNo);
		Withdrawals entity=withdrawalsService.queryWithdrawals(withdrawals);
		model.addAttribute("item",entity);
		return "admin/clearingt0err/detail";
	}

	/**
	 * 
	 * @author liushuyu
	 * Desc 调账
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject update(String id,String checkFlag){
		boolean flag=true;
		try{
			flag=clearingT0ErrService.updateMuti(id, checkFlag);
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

