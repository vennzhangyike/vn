/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.commons.ScanBaseController;
import com.hfepay.scancode.commons.condition.QrcodeGoodsCondition;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.QrcodeGoods;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.QrcodeGoodsService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/qrcodegoods")
public class QrcodeGoodsController extends ScanBaseController{
	
	@Autowired
	private QrcodeGoodsService qrcodeGoodsService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/qrcodegoods/list";
	}
	
	/**
	 * 列表显示
	 * @param request qrcodeGoodsCondition
	 * 
	 * @author panq
	 * @return page
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,QrcodeGoodsCondition qrcodeGoodsCondition){	
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		qrcodeGoodsCondition.setNodeSeq(user.getNodeSeq());
		qrcodeGoodsCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		PagingResult<QrcodeGoods> page = qrcodeGoodsService.findPagingResult(qrcodeGoodsCondition);
		Pager<QrcodeGoods> pager = new Pager<QrcodeGoods>();
		pager.setPageNo(qrcodeGoodsCondition.getPageNo());
		pager.setPageSize(qrcodeGoodsCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/qrcodegoods/listContent";
	}
	
	/**
	 * 列表显示(不分页)
	 * @param request qrcodeGoodsCondition
	 * 
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,QrcodeGoodsCondition qrcodeGoodsCondition){
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		qrcodeGoodsCondition.setNodeSeq(user.getNodeSeq());
		qrcodeGoodsCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
        List<QrcodeGoods> list = qrcodeGoodsService.findAll(qrcodeGoodsCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/**
	 * 进入新增、更新页面
	 * @param 主键
	 * 
	 * @author panq
	 * @return page
	 */
	@RequestMapping(value="/edit/{id}/{merNo}/{qrcode}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id,@PathVariable String merNo,@PathVariable String qrcode) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			QrcodeGoods entity = qrcodeGoodsService.findById(id);
			model.addAttribute("Obj", entity);
		}
		MerchantInfo entity = merchantInfoService.findByMerchantNo(merNo);
		model.addAttribute("mer", entity);
		model.addAttribute("qrcode", qrcode);
		return "admin/qrcodegoods/edit";
	}

	/**
	 * 新增、更新
	 * @param qrcodeGoodsCondition
	 * 
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,QrcodeGoodsCondition qrcodeGoodsCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		qrcodeGoodsCondition.setOperator(getCurrentUserId());
		try {
			//判断默认
			QrcodeGoodsCondition good = new QrcodeGoodsCondition();
			good.setRecordStatus(Constants.RECORD_STATUS_YES);
			good.setMerchantNo(qrcodeGoodsCondition.getMerchantNo());
			good.setQrCode(qrcodeGoodsCondition.getQrCode());
			good.setIsDefault(Constants.FAIL_STATE);
	        List<QrcodeGoods> list = qrcodeGoodsService.findAll(good);
	        if(Constants.FAIL_STATE.equals(qrcodeGoodsCondition.getIsDefault())){
				for (QrcodeGoods qrcodeGoods : list) {
					BeanUtils.copyProperties(qrcodeGoods, good);
					good.setIsDefault(Constants.STATUS_ACTIVE);
					qrcodeGoodsService.update(good);
				}
			}
			if(Strings.isEmpty(qrcodeGoodsCondition.getId())){
				//如果库中不存在默认的商品且新建的产品不是默认，则将不默认改为默认
				if(Constants.STATUS_ACTIVE.equals(qrcodeGoodsCondition.getIsDefault())
						&& list.size() == 0){
					qrcodeGoodsCondition.setIsDefault(Constants.FAIL_STATE);
				}
				//新增
				qrcodeGoodsCondition.setCreateTime(new Date());
				qrcodeGoodsCondition.setId(Strings.getUUID());
				qrcodeGoodsCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				qrcodeGoodsService.insert(qrcodeGoodsCondition);
			}else{
				if(Constants.STATUS_ACTIVE.equals(qrcodeGoodsCondition.getIsDefault())
						&& list.size() == 1){
					map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"当前商品不可改为不默认状态，请检查后确定！");
					return JSONSerializer.toJSON(map);
				}
				qrcodeGoodsCondition.setUpdateTime(new Date());
				qrcodeGoodsService.update(qrcodeGoodsCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/qrcodegoods");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 删除
	 *  
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,QrcodeGoodsCondition qrcodeGoodsCondition){		
		qrcodeGoodsCondition.setOperator(getCurrentUserId());
		qrcodeGoodsCondition.setRecordStatus(Constants.RECORD_STATUS_NO);
		qrcodeGoodsCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			qrcodeGoodsService.update(qrcodeGoodsCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	
	/** 查看详情 
	 * @param 主键
	 * 
	 * @author panq
	 * @return page
	 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		QrcodeGoods entity = qrcodeGoodsService.findById(id);
		model.addAttribute("item",entity);
		return "admin/qrcodegoods/detail";
	}
}

