/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

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
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.commons.condition.MerchantBankcardChangeCondition;
import com.hfepay.scancode.commons.entity.MerchantBankcardChange;
import com.hfepay.scancode.service.operator.MerchantBankcardChangeService;

@Controller
@RequestMapping("/adminManage/merchantbankcardchange")
public class MerchantBankcardChangeController extends BaseController{
	
	@Autowired
	private MerchantBankcardChangeService merchantBankcardChangeService;
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "user/merchantbankcardchange/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 商户银行卡变更列表
	 * @author: Ricky
	 * @param MerchantBankcardChangeCondition
	 * @return: String
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.GET})
	public String listContent(ModelMap model,MerchantBankcardChangeCondition merchantBankcardChangeCondition){		
		PagingResult<MerchantBankcardChange> page = merchantBankcardChangeService.findPagingResult(merchantBankcardChangeCondition);
		Pager<MerchantBankcardChange> pager = new Pager<MerchantBankcardChange>();
		pager.setPageNo(merchantBankcardChangeCondition.getPageNo());
		pager.setPageSize(merchantBankcardChangeCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantbankcard/link";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 审核列表查询
	 * @author: Ricky
	 * @param MerchantBankcardChangeCondition
	 * @return: String
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@RequestMapping(value = "/bankContent", method= {RequestMethod.POST})
	public String findAuditMerchantBankcard(ModelMap model,MerchantBankcardChangeCondition merchantBankcardChangeCondition){		
		PagingResult<MerchantBankcardChange> page = merchantBankcardChangeService.findAuditMerchantBankcard(merchantBankcardChangeCondition);
		Pager<MerchantBankcardChange> pager = new Pager<MerchantBankcardChange>();
		pager.setPageNo(merchantBankcardChangeCondition.getPageNo());
		pager.setPageSize(merchantBankcardChangeCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantaudit/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param MerchantBankcardChangeCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantBankcardChangeCondition merchantBankcardChangeCondition){		
        List<MerchantBankcardChange> list = merchantBankcardChangeService.findAll(merchantBankcardChangeCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/**
	 * @Title: show
	 * @Description: 查询单个实体
	 * @author: Ricky
	 * @param id
	 * @return: String
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantBankcardChange entity = merchantBankcardChangeService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/merchantbankcardchange/edit";
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param MerchantBankcardChangeCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,MerchantBankcardChangeCondition merchantBankcardChangeCondition){		
        long flag = merchantBankcardChangeService.deleteById(merchantBankcardChangeCondition.getId());
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
	 * @Title: saveOrUpdateById
	 * @Description: 状态变更
	 * @author: wh
	 * @param MerchantBankcardChangeCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-12-06 14:19:53
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,MerchantBankcardChangeCondition merchantBankcardChangeCondition){		
        long flag = merchantBankcardChangeService.updateStatus(merchantBankcardChangeCondition.getId(),merchantBankcardChangeCondition.getStatus());
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
	
}

