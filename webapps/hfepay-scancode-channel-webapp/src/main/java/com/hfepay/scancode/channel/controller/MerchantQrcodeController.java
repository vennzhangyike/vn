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
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.commons.ErrorCode;
import com.hfepay.scancode.channel.commons.ScanBaseController;
import com.hfepay.scancode.channel.commons.StoreStatusEnum;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.MerchantQrcodeCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantQrcode;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.commons.vo.MerchantQrcodeVo;
import com.hfepay.scancode.service.contants.ConfigPreCode;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantQrcodeService;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.operator.ParamsInfoService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/merchantqrcode")
public class MerchantQrcodeController extends ScanBaseController{
	
	@Autowired
	private MerchantQrcodeService merchantQrcodeService;
	
	@Autowired
	private ChannelExpandService channelExpandService;
	
	@Autowired
	private IdCreateService idCreateService;
	
	@Autowired
	private ParamsInfoService paramsInfoService;
	
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private MerchantStoreService merchantStoreService;
	
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/merchantqrcode/list";
	}
	
	/**
	 * 列表显示
	 * @param request merchantQrcodeCondition
	 * 
	 * @author panq
	 * @return page
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantQrcodeCondition merchantQrcodeCondition){
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		merchantQrcodeCondition.setNodeSeq(user.getNodeSeq());
		merchantQrcodeCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		PagingResult<MerchantQrcode> page = merchantQrcodeService.findPagingResult(merchantQrcodeCondition);
		Pager<MerchantQrcode> pager = new Pager<MerchantQrcode>();
		pager.setPageNo(merchantQrcodeCondition.getPageNo());
		pager.setPageSize(merchantQrcodeCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/merchantqrcode/listContent";
	}
	
	/**
	 * 列表显示(不分页)
	 * @param request merchantQrcodeCondition
	 * 
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantQrcodeCondition merchantQrcodeCondition){
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		merchantQrcodeCondition.setNodeSeq(user.getNodeSeq());
		merchantQrcodeCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
        List<MerchantQrcode> list = merchantQrcodeService.findAll(merchantQrcodeCondition);
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
	@RequestMapping(value="/edit/{id}/{storeNo}/{qrType}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id,@PathVariable String storeNo,@PathVariable String qrType) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantQrcode entity = new MerchantQrcodeVo();
			if(Constants.QR_TYPE.equals(qrType)){
				PlatformQrcode platformQrcode = platformQrcodeService.findById(id);
				BeanUtils.copyProperties(platformQrcode, entity);
			}else{
				entity = merchantQrcodeService.findById(id);
			}
			model.addAttribute("Obj", entity);
			model.addAttribute("qrType", qrType);
		}
		
		MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition(); 
		merchantStoreCondition.setStoreNo(storeNo);
		MerchantStore store = merchantStoreService.findAll(merchantStoreCondition).get(0);
		MerchantInfo entity = merchantInfoService.findByMerchantNo(store.getMerchantNo());
		model.addAttribute("mer", entity);
		model.addAttribute("store", store);
		
		return "admin/merchantqrcode/edit";
	}

	/**
	 * 新增、更新
	 * @param merchantQrcodeCondition
	 * 
	 * @author panq
	 * @return json
	 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,MerchantQrcodeCondition merchantQrcodeCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		merchantQrcodeCondition.setOperator(getCurrentUserId());
		try {
			if(Strings.isEmpty(merchantQrcodeCondition.getId())){
				//门店必须审核通过，否则不予以创建二维码
				MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
				merchantStoreCondition.setStoreNo(merchantQrcodeCondition.getStoreId());
				MerchantStore merchantStore = merchantStoreService.findAll(merchantStoreCondition).get(0);
				if (!merchantStore.getStoreStatus().equals(StoreStatusEnum.STORE_STATUS_3.getValue())) {
					map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,ErrorCode.VALIDAT_100017.getDesc());
					return JSONSerializer.toJSON(map);
				}
				
				//新增
				merchantQrcodeCondition.setCreateTime(new Date());
				merchantQrcodeCondition.setId(Strings.getUUID());
				merchantQrcodeCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				merchantQrcodeCondition.setQrStatus(Constants.SUCCESS_STATE);
				
				ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
				DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(merchantStore.getMerchantNo());
				if (null != vo) {
					channelExpandCondition.setChannelNo(vo.getOrganNo());
				}else {					
					channelExpandCondition.setChannelNo(merchantQrcodeCondition.getChannelNo());
				}
				//设置二维码编号
				ChannelExpand channelExpand = channelExpandService.findAll(channelExpandCondition).get(0);
				merchantQrcodeCondition.setQrCode(idCreateService.createParamNo(channelExpand.getChannelPreCode() + ConfigPreCode.QRCODE_PRE_CODE));
				
				//二维码指定的url，域名+渠道编码+二维码编码
				StringBuffer content = new StringBuffer();
				
				net.sf.json.JSONObject paramsInfoJson = paramsInfoService.getParamsInfoByDomain(channelExpand.getChannelNo());
				content.append(paramsInfoJson.getString("qrcodePath"));
//				content.append(fileUploadConfig.getQrcodePath());
				content.append("?channelNo=");
				content.append(channelExpand.getChannelNo());
				content.append("&qrCode=");
				content.append(merchantQrcodeCondition.getQrCode());
				merchantQrcodeCondition.setImage(content.toString());
				
				//生成二维码的三个参数
				Map<String,String> tempmap = Maps.newMap();
				
				merchantQrcodeService.insert(merchantQrcodeCondition,tempmap);
			}else{
				merchantQrcodeCondition.setUpdateTime(new Date());
				String qrType = merchantQrcodeCondition.getQrType();
				if(Constants.QR_TYPE.equals(qrType)){
					PlatformQrcodeCondition platformQrcodeCondition = new PlatformQrcodeCondition();
					BeanUtils.copyProperties(merchantQrcodeCondition, platformQrcodeCondition);
					platformQrcodeService.update(platformQrcodeCondition);
				}else{					
					merchantQrcodeService.update(merchantQrcodeCondition);
				}
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantqrcode");
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
	public JSON del(HttpServletRequest request,MerchantQrcodeCondition merchantQrcodeCondition){		
		merchantQrcodeCondition.setOperator(getCurrentUserId());
		merchantQrcodeCondition.setRecordStatus(Constants.RECORD_STATUS_NO);
		merchantQrcodeCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantQrcodeService.update(merchantQrcodeCondition);
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
	@RequestMapping(value="/detail/{id}/{qrType}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id,@PathVariable String qrType) throws Exception {
		MerchantQrcode entity = new MerchantQrcodeVo();
		if(Constants.QR_TYPE.equals(qrType)){
			PlatformQrcode platformQrcode = platformQrcodeService.findById(id);
			BeanUtils.copyProperties(platformQrcode, entity);
		}else{
			entity = merchantQrcodeService.findById(id);
		}
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(entity.getMerchantNo());
		String organNo = "";
		if (null != vo) {
			organNo = vo.getOrganNo();
		}else {					
			organNo = entity.getChannelNo();
		}
		ChannelExpand  channelExpand = channelExpandService.findByChannelNo(organNo);
		model.addAttribute("icon",channelExpand.getIcon());
		model.addAttribute("item",entity);
		return "admin/merchantqrcode/detail";
	}
	
	/** 打印二维码 */
	@RequestMapping(value="/print/{qrCode}", method= {RequestMethod.GET})
	public String print(HttpServletRequest request,ModelMap model,@PathVariable String qrCode) throws Exception {
		MerchantQrcodeCondition merchantQrcodeCondition = new MerchantQrcodeCondition();
		merchantQrcodeCondition.setQrCode(qrCode);
		List<MerchantQrcode> entity = merchantQrcodeService.findAll(merchantQrcodeCondition);
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(entity.get(0).getAgentNo());
		String organNo = "";
		if (null != vo) {
			organNo = vo.getOrganNo();
		}else {					
			organNo = entity.get(0).getChannelNo();
		}
		ChannelExpand  channelExpand = channelExpandService.findByChannelNo(organNo);
		MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
		if(entity.size() == 0){
			PlatformQrcode platformQrcode = platformQrcodeService.findByQrcode(qrCode);
			model.addAttribute("qrCode",platformQrcode.getQrCode());
			model.addAttribute("image",platformQrcode.getImage());
			merchantStoreCondition.setStoreNo(platformQrcode.getStoreId());
		}else {
			model.addAttribute("qrCode",entity.get(0).getQrCode());
			model.addAttribute("image",entity.get(0).getImage());
			merchantStoreCondition.setStoreNo(entity.get(0).getStoreId());
		}
		
		List<MerchantStore> list = merchantStoreService.findAllMerchantStore(merchantStoreCondition);
		model.addAttribute("storeName",list.get(0).getStoreName());
		
		model.addAttribute("channelLogo",channelExpand.getIndexTopImg());
		model.addAttribute("icon", channelExpand.getIcon());
		
		return "admin/createQrcode";
	}
	
	/**
	 * @Title: updateStatus
	 * @Description: 状态变更
	 * @author: wh
	 * @param ChannelBaseCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSONObject updateStatus(HttpServletRequest request,MerchantQrcodeCondition merchantQrcodeCondition){	
		long flag = 0;
		if(Constants.QR_TYPE.equals(merchantQrcodeCondition.getQrType())){
			PlatformQrcodeCondition platformQrcode = new PlatformQrcodeCondition();
			platformQrcode.setId(merchantQrcodeCondition.getId());
			platformQrcode.setQrStatus(merchantQrcodeCondition.getQrStatus());
	        flag = platformQrcodeService.update(platformQrcode);
		}else{			
			flag = merchantQrcodeService.update(merchantQrcodeCondition);
		}
        
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

