/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.hfepay.scancode.channel.commons.BatchNoUtil;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.commons.TransCodeEnum;
import com.hfepay.scancode.commons.condition.ChangeLogCondition;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelPaywayCondition;
import com.hfepay.scancode.commons.condition.HfepayPaywayCondition;
import com.hfepay.scancode.commons.entity.ChangeLog;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelPayway;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.operator.ChangeLogService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.ChannelPaywayService;
import com.hfepay.scancode.service.operator.HfepayPaywayService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/channelpayway")
public class ChannelPaywayController extends BaseController{
	
	@Autowired
	private ChannelPaywayService channelPaywayService;
	@Autowired
	private ChangeLogService changeLogService;
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private HfepayPaywayService hfepayPaywayService;
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 查询列表
	 * @author: Ricky
	 * @param 
	 * @return: String
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(ModelMap model) {
		HfepayPaywayCondition hfepayPaywayCondition = new HfepayPaywayCondition();
		hfepayPaywayCondition.setPayType(Constants.PAYWAY_VALUE);
		model.addAttribute("mappingDicionList", hfepayPaywayService.findAll(hfepayPaywayCondition));
		return "admin/channel/channelpayway/list";
	}
	
	/**
	 * @Title: listContent
	 * @Description: 列表查询
	 * @author: Ricky
	 * @param ChannelPaywayCondition
	 * @return: String
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,ChannelPaywayCondition channelPaywayCondition){		
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		channelPaywayCondition.setChannelNo(user.getChannelCode());
		PagingResult<ChannelPayway> page = channelPaywayService.findPagingResult(channelPaywayCondition);
		Pager<ChannelPayway> pager = new Pager<ChannelPayway>();
		pager.setPageNo(channelPaywayCondition.getPageNo());
		pager.setPageSize(channelPaywayCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		
		model.addAttribute("pager",pager);
		return "admin/channel/channelpayway/listContent";
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param ChannelPaywayCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,ChannelPaywayCondition channelPaywayCondition){		
        List<ChannelPayway> list = channelPaywayService.findAll(channelPaywayCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: saveOrUpdateById
	 * @Description: 新增/更新
	 * @author: Ricky
	 * @param ChannelPaywayCondition
	 * @return: String
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON saveOrUpdateById(HttpServletRequest request,ModelMap model,ChannelPaywayCondition channelPaywayCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		channelPaywayCondition.setOperator(user.getId());
		try {
			if(Strings.isEmpty(channelPaywayCondition.getId())){
				channelPaywayCondition.setCreateTime(new Date());
				channelPaywayCondition.setStatus(Constants.STATUS_ACTIVE);
				channelPaywayCondition.setRecordStatus(Constants.Y);
				//新增
				channelPaywayService.insert(channelPaywayCondition);
			}else{
				ChannelPayway entity = channelPaywayService.findById(channelPaywayCondition.getId());
				//有变更才写入数据变更日志
				if(entity != null && 
						!(entity.getPayCode().equals(channelPaywayCondition.getPayCode())
								&&entity.getT0Rate().equals(channelPaywayCondition.getT0Rate())
								&&entity.getT1Rate().equals(channelPaywayCondition.getT1Rate())
								&&entity.getRate().equals(channelPaywayCondition.getRate()))){
					ChangeLogCondition changeLogCondition = new ChangeLogCondition();
					String batchNo = BatchNoUtil.getBatchNo();
					changeLogCondition.setTradeNo(channelPaywayCondition.getId());
					changeLogCondition.setBatchNo(batchNo);
					changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.CHANNEL_PAYWAY_CODE.getValue()));
					changeLogCondition.setTransName(TransCodeEnum.CHANNEL_PAYWAY_CODE.getDesc());
					changeLogCondition.setOwnersNo(channelPaywayCondition.getChannelNo());
					changeLogCondition.setBefore(JSONSerializer.toJSON(entity).toString());
					changeLogCondition.setStatus(Constants.APPROVE_STATUS_NEW);
					changeLogCondition.setOperator(user.getId());
					changeLogCondition.setCreateTime(new Date());
					changeLogService.insert(changeLogCondition);
					
				}
				channelPaywayService.update(channelPaywayCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/channelpayway");
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
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			ChannelPayway entity = channelPaywayService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		ChannelBaseCondition channelBaseCondition = new ChannelBaseCondition();
		channelBaseCondition.setChannelNo(user.getChannelCode());
		model.addAttribute("channelBaseList", channelBaseService.findAll(channelBaseCondition));
		
		HfepayPaywayCondition hfepayPaywayCondition = new HfepayPaywayCondition();
		hfepayPaywayCondition.setPayType(Constants.PAYWAY_VALUE);
		model.addAttribute("mappingDicionList", hfepayPaywayService.findAll(hfepayPaywayCondition));
		return "admin/channel/channelpayway/edit";
	}
	
	/**
	 * @Title: saveOrUpdateById
	 * @Description: 删除
	 * @author: Ricky
	 * @param ChannelPaywayCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject del(HttpServletRequest request,ChannelPaywayCondition channelPaywayCondition){		
        long flag = channelPaywayService.deleteById(channelPaywayCondition.getId());
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
	 * @param ChannelPaywayCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-18 15:28:16
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,ChannelPaywayCondition channelPaywayCondition){		
        long flag = channelPaywayService.updateStatus(channelPaywayCondition.getId(),channelPaywayCondition.getStatus());
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
	public String showDetail(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		ChannelPayway entity = channelPaywayService.findById(id);
		model.addAttribute("item",entity);
		return "admin/channel/channelpayway/detail";
	}
	
	/** 变更记录 */
	@RequestMapping(value="/link/{id}", method= {RequestMethod.GET})
	public String link(HttpServletRequest request,ModelMap model,@PathVariable String id,ChannelPaywayCondition channelPaywayCondition) throws Exception {
		ChangeLogCondition changeLogCondition = new ChangeLogCondition();		
		changeLogCondition.setTradeNo(id);
		changeLogCondition.setTransCode(String.valueOf(TransCodeEnum.CHANNEL_PAYWAY_CODE.getValue()));
		changeLogCondition.setPageNo(channelPaywayCondition.getPageNo());
		PagingResult<ChangeLog> page = changeLogService.findPagingResult(changeLogCondition);
		
		List<ChannelPaywayCondition> list = new ArrayList<ChannelPaywayCondition>();
		for (ChangeLog changeLog : page.getResult()) {
			JSONObject json = JSONObject.parseObject(changeLog.getBefore());
			ChannelPaywayCondition obj = JSONObject.toJavaObject(json,ChannelPaywayCondition.class);
			obj.setCreateTime(changeLog.getCreateTime());
			list.add(obj);
		}
		
		Pager<ChannelPaywayCondition> pager = new Pager<ChannelPaywayCondition>();
		pager.setPageNo(channelPaywayCondition.getPageNo());
		pager.setPageSize(channelPaywayCondition.getPageSize());
		pager.setResult(list);
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/channel/channelpayway/link";
	}
	
	/** 校验渠道编号与支付方式 */
	@RequestMapping(value="/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject check(HttpServletRequest request,String channelNo,String payCode,String id){	
		ChannelPaywayCondition channelPaywayCondition = new ChannelPaywayCondition();
		channelPaywayCondition.setChannelNo(channelNo);
		channelPaywayCondition.setPayCode(payCode);
		
        List<ChannelPayway> list = channelPaywayService.findAll(channelPaywayCondition);
        JSONObject json = new JSONObject();
        for (Iterator<ChannelPayway> iterator = list.iterator(); iterator.hasNext();) {
			ChannelPayway channelPayway = (ChannelPayway) iterator.next();
			if(!channelPayway.getId().equals(id)){
				json.put("check", false);
				return json;
			}
		}
        json.put("check", true);
		return json;
	}
	
}

