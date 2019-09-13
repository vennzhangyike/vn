/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */

package com.hfepay.scancode.channel.controller;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.condition.HierarchicalSettlementTotalCondition;
import com.hfepay.scancode.commons.condition.HierarchicalStaticCondition;
import com.hfepay.scancode.commons.contants.IdentityType;
import com.hfepay.scancode.commons.entity.HierarchicalSettlementTotal;
import com.hfepay.scancode.commons.entity.HierarchicalStatic;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
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
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(IdentityType.Identity_Channel.getCode().equals(user.getIdentityFlag())){
			hierarchicalStaticCondition.setIdentityFlag(IdentityType.Identity_Channel.getCode());
		}
		else if(IdentityType.Identity_Agent.getCode().equals(user.getIdentityFlag())){
			hierarchicalStaticCondition.setIdentityFlag(IdentityType.Identity_Agent.getCode());
		}
		PagingResult<HierarchicalStatic> page = hierarchicalSettlementTotalService.findHierarchicalStatic(hierarchicalStaticCondition, user.getNodeSeq());
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
	
	/** 列表无分页 
	 * @throws ParseException */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,HierarchicalSettlementTotalCondition hierarchicalSettlementTotalCondition) throws ParseException{		
        List<HierarchicalSettlementTotal> list = hierarchicalSettlementTotalService.findAll(hierarchicalSettlementTotalCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
}

