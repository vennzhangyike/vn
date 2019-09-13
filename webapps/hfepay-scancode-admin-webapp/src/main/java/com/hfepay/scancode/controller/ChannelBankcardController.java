/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.alibaba.fastjson.JSONObject;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.BatchNoUtil;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.TransCodeEnum;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.ChannelBankcardCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.ChangeLog;
import com.hfepay.scancode.commons.entity.ChannelBankcard;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.service.operator.ChangeLogService;
import com.hfepay.scancode.service.operator.ChannelBankcardService;
import com.hfepay.scancode.service.operator.ChannelBaseService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/channelbankcard")
public class ChannelBankcardController extends BaseController{
	
	@Autowired
	private ChannelBankcardService channelBankcardService;
	@Autowired
	private ChangeLogService changeLogService;
	@Autowired
	private ChannelBaseService channelBaseService;
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list() {
		return "admin/channel/channelbankcard/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param ChannelBankcardCondition
	 * @return: String
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,ChannelBankcardCondition channelBankcardCondition){		
		PagingResult<ChannelBankcard> page = channelBankcardService.findPagingResult(channelBankcardCondition);
		Pager<ChannelBankcard> pager = new Pager<ChannelBankcard>();
		pager.setPageNo(channelBankcardCondition.getPageNo());
		pager.setPageSize(channelBankcardCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/channel/channelbankcard/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param ChannelBankcardCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,ChannelBankcardCondition channelBankcardCondition){		
        List<ChannelBankcard> list = channelBankcardService.findAll(channelBankcardCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param ChannelBankcardCondition
	 * @return: String
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON saveOrUpdateById(HttpServletRequest request,ModelMap model,ChannelBankcardCondition channelBankcardCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		channelBankcardCondition.setOperator(user.getUserName());
		try {
			if(Strings.isEmpty(channelBankcardCondition.getId())){
				//新增
				channelBankcardService.insert(channelBankcardCondition);
			}else{				
				ChannelBankcard entity = channelBankcardService.findById(channelBankcardCondition.getId());
				//有变更才写入数据变更日志
				if(entity != null && 
						!(Strings.equals(entity.getBankCode(), channelBankcardCondition.getBankCode())
								&&Strings.equals(entity.getBankName(), channelBankcardCondition.getBankName())
								&&Strings.equals(entity.getBankCard(), channelBankcardCondition.getBankCard())
								&&Strings.equals(entity.getIdCard(), channelBankcardCondition.getIdCard())
								&&Strings.equals(entity.getName(), channelBankcardCondition.getName())
								&&Strings.equals(entity.getMobile(), channelBankcardCondition.getMobile())
								&&Strings.equals(entity.getAccountType(), channelBankcardCondition.getAccountType()))){
					ChannelBase channelBase = channelBaseService.findByChannelNo(channelBankcardCondition.getChannelNo());
					if(channelBase != null){
						ChangeLogCondition changeLogCondition = new ChangeLogCondition();
						String tradeNo = channelBase.getId();//t_channel_base表Id关联
						String batchNo = BatchNoUtil.getBatchNo();
						changeLogCondition.setTradeNo(tradeNo);
						changeLogCondition.setBatchNo(batchNo);
						changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.CHANNEL_BANKCARD_CODE.getValue()));
						changeLogCondition.setTransName(TransCodeEnum.CHANNEL_BANKCARD_CODE.getDesc());
						changeLogCondition.setOwnersNo(channelBankcardCondition.getChannelNo());
						changeLogCondition.setBefore(JSONSerializer.toJSON(entity).toString());
						changeLogCondition.setStatus(Constants.APPROVE_STATUS_NEW);
						changeLogCondition.setOperator(user.getUserName());
						changeLogCondition.setCreateTime(new Date());
						changeLogService.insert(changeLogCondition);
					}
					
				}
				channelBankcardService.update(channelBankcardCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/channelbankcard");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * @Title: show
	 * @Description: 查询单个实体
	 * @author: Ricky
	 * @param id
	 * @return: String
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			ChannelBankcard entity = channelBankcardService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		return "admin/channel/channelbankcard/edit";
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param ChannelBankcardCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,ChannelBankcardCondition channelBankcardCondition){		
        long flag = channelBankcardService.deleteById(channelBankcardCondition.getId());
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
	 * @param ChannelBankcardCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-17 10:20:23
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,ChannelBankcardCondition channelBankcardCondition){		
        long flag = channelBankcardService.updateStatus(channelBankcardCondition.getId(),channelBankcardCondition.getStatus());
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
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		ChannelBankcard entity = channelBankcardService.findById(id);
		model.addAttribute("item",entity);
		return "admin/channel/channelbankcard/detail";
	}
	
	/** 变更记录 */
	@RequestMapping(value="/link/{id}/{channelNo}", method= {RequestMethod.GET})
	public String link(HttpServletRequest request,ModelMap model,@PathVariable String id,@PathVariable String channelNo,ChannelBankcardCondition channelBankcardCondition) throws Exception {
		ChangeLogCondition changeLogCondition = new ChangeLogCondition();
		ChannelBase channelBase = channelBaseService.findByChannelNo(channelNo);
		if(channelBase != null){
			String tradeNo = channelBase.getId();//t_channel_base表Id关联
			changeLogCondition.setTradeNo(tradeNo);
			changeLogCondition.setPageNo(channelBankcardCondition.getPageNo());
			changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.CHANNEL_BANKCARD_CODE.getValue()));
			
			PagingResult<ChangeLog> page = changeLogService.findPagingResult(changeLogCondition);
			
			List<ChannelBankcardCondition> list = new ArrayList<ChannelBankcardCondition>();
			for (ChangeLog changeLog : page.getResult()) {
				JSONObject json = JSONObject.parseObject(changeLog.getBefore());
				ChannelBankcardCondition obj = JSONObject.toJavaObject(json,ChannelBankcardCondition.class);
				obj.setCreateTime(changeLog.getCreateTime());
				list.add(obj);
			}
			
			Pager<ChannelBankcardCondition> pager = new Pager<ChannelBankcardCondition>();
			pager.setPageNo(channelBankcardCondition.getPageNo());
			pager.setPageSize(channelBankcardCondition.getPageSize());
			pager.setResult(list);
			pager.setTotalCount(page.getTotalCount());
	 		if(pager.getOrder() == null){
				pager.setOrder("");
			}
			model.addAttribute("pager",pager);
		}
		
		
		return "admin/channel/channelbankcard/link";
	}
	
}

