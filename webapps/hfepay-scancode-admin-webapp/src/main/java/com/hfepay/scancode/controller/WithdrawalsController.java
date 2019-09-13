/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.WithdrawalsCondition;
import com.hfepay.scancode.commons.entity.Withdrawals;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.order.WithdrawalsService;

@Controller
@RequestMapping("/adminManage/withdrawals")
public class WithdrawalsController extends BaseController{
	
	@Autowired
	private WithdrawalsService withdrawalsService;
	@Autowired
	private MappingDicionService mappingDicionService;
		
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2016-12-04 14:15:32
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(ModelMap model) {
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo(Constants.PAYWAY_CODE);
		model.addAttribute("mappingDicionList", mappingDicionService.findAll(mappingDicionCondition));
		return "admin/withdrawals/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param WithdrawalsCondition
	 * @return: String
	 * @throws Exception 
	 * @date CreateDate : 2016-12-04 14:15:32
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,WithdrawalsCondition withdrawalsCondition) throws Exception{	
		String beginTimeStr = (String)request.getParameter("beginTimeStr");
		if(beginTimeStr != ""){
			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			withdrawalsCondition.setBeginTime(beginTime);			
		}
		String endTimeStr = (String)request.getParameter("endTimeStr");
		if(endTimeStr != ""){
			endTimeStr = endTimeStr + " 23:59:59";
			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			withdrawalsCondition.setEndTime(endTime);
		}
		PagingResult<Withdrawals> page = withdrawalsService.findPagingResult(withdrawalsCondition);
		Pager<Withdrawals> pager = new Pager<Withdrawals>();
		pager.setPageNo(withdrawalsCondition.getPageNo());
		pager.setPageSize(withdrawalsCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		model.addAttribute("amtStatic",withdrawalsService.getAmtStatistics(withdrawalsCondition));
		return "admin/withdrawals/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param WithdrawalsCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-12-04 14:15:32
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,WithdrawalsCondition withdrawalsCondition){		
        List<Withdrawals> list = withdrawalsService.findAll(withdrawalsCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		Withdrawals entity = withdrawalsService.findById(id);
		model.addAttribute("item",entity);
		return "admin/withdrawals/detail";
	}
	
}

