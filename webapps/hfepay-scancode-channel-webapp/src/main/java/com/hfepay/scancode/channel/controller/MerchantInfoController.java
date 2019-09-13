/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.commons.ScanBaseController;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.HfepayCategoryCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.condition.NodeRelationCondition;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.HfepayCategory;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.contants.ConfigPreCode;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.HfepayCategoryService;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.operator.NodeRelationService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


@Controller
@RequestMapping("/adminManage/merchantinfo")
public class MerchantInfoController extends ScanBaseController{
	Logger logger = LoggerFactory.getLogger(MerchantInfoController.class);
	@Autowired
	private MerchantInfoService merchantInfoService;
	
	@Autowired
	private AgentBaseService agentBaseService;
	
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	
	@Autowired
	private IdCreateService idCreateService;
	
	@Autowired
	private MerchantStoreService merchantStoreService;
	
	@Autowired
	private NodeRelationService nodeRelationService;
	@Autowired
	private HfepayCategoryService hfepayCategoryService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		//渠道列表
		ChannelBaseCondition channelCondition = new ChannelBaseCondition();
		channelCondition.setChannelNo(user.getChannelCode());
		channelCondition.setStatus(Constants.SUCCESS_STATE);
		request.setAttribute("channels",channelBaseService.findAll(channelCondition));
		
		AgentBaseCondition agentBaseCondition =new AgentBaseCondition();
		agentBaseCondition.setStatus(Constants.SUCCESS_STATE);
		agentBaseCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		request.setAttribute("agents",agentBaseService.findAllByAgentNo(agentBaseCondition, user.getNodeSeq()));
		return "admin/merchantinfo/list";		
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,MerchantInfoCondition merchantInfoCondition) throws ParseException{
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		
		merchantInfoCondition.setNodeSeq(user.getNodeSeq());
		
		String beginTimeStr = (String)request.getParameter("beginTimeStr");
		if(beginTimeStr != ""){
			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			merchantInfoCondition.setQueryStartTime(beginTime);
		}
		String endTimeStr = (String)request.getParameter("endTimeStr");
		if(endTimeStr != ""){
			endTimeStr = endTimeStr + " 23:59:59";
			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			merchantInfoCondition.setQueryEndTime(endTime);
		}
		
		PagingResult<MerchantInfo> page = merchantInfoService.findPagingResult(merchantInfoCondition);
		Pager<MerchantInfo> pager = new Pager<MerchantInfo>();
		pager.setPageNo(merchantInfoCondition.getPageNo());
		pager.setPageSize(merchantInfoCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		model.addAttribute("user",user);
		return "admin/merchantinfo/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,MerchantInfoCondition merchantInfoCondition){	
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		merchantInfoCondition.setNodeSeq(user.getNodeSeq());
        List<MerchantInfo> list = merchantInfoService.findAll(merchantInfoCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	
	/** 获取商户信息 */
	@RequestMapping(value="/getMerchantInfo",method=RequestMethod.POST)
	@ResponseBody
	public MerchantInfo getMerchantInfo(HttpServletRequest request,String merchantNo){		
        MerchantInfo merchantInfo = merchantInfoService.findByMerchantNo(merchantNo);
		return merchantInfo;
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			MerchantInfo entity = merchantInfoService.findById(id);
			 model.addAttribute("Obj", entity);
			 
				if(Strings.isNotEmpty(entity.getBusType())){
					HfepayCategory category = hfepayCategoryService.findByCategoryNo(entity.getBusType());
					if(category != null){
						model.addAttribute("busTypeStr", category.getName() + "(" + category.getCategoryNo() +  ")");
					}
				}
			 
			//经营科目一级
			HfepayCategoryCondition hfepayCategoryCondition = new HfepayCategoryCondition();
			hfepayCategoryCondition.setLevel(Constants.CATEGORY_LEVEL_ONE);
			List<HfepayCategory> categoryOne = hfepayCategoryService.findAll(hfepayCategoryCondition);
			model.addAttribute("categoryOne",categoryOne);
		}
		return "admin/merchantinfo/edit";
	}
	
	
	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	@Transactional
	public JSON _new(HttpServletRequest request,ModelMap model,MerchantInfoCondition merchantInfoCondition,MerchantStoreCondition merchantStoreCondition) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		merchantInfoCondition.setOperator(getCurrentUserId());
		try {
			String redirectUrl = (String)request.getParameter("redirectUrl");
			if(Strings.isNotEmpty(redirectUrl)){
				map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url",redirectUrl);
			}else{
				map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantinfo");
			}
			
			if(Strings.isEmpty(merchantInfoCondition.getId())){
				ChannelAdmin user = getCurrentUser();
				merchantInfoCondition.setCreateTime(new Date());
				merchantInfoCondition.setId(Strings.getUUID());
				merchantInfoCondition.setStatus(Constants.SUCCESS_STATE);
				merchantInfoCondition.setChannelNo(user.getChannelCode());
				merchantInfoCondition.setAgentNo(user.getAgentNo());
				merchantInfoCondition.setAuthenStatus(Integer.parseInt(Constants.FAIL_STATE));
				
				//设置商户编号
				ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
				channelExpandCondition.setChannelNo(merchantInfoCondition.getChannelNo());
				ChannelExpand channelInfo = channelExpandService.findAll(channelExpandCondition).get(0);
				merchantInfoCondition.setMerchantNo(idCreateService.createParamNo(channelInfo.getChannelPreCode() + ConfigPreCode.MERCHANT_PRE_CODE));
				merchantStoreCondition.setMerchantNo(merchantInfoCondition.getMerchantNo());
				//创建商户的同时创建门店
				insertStore(merchantStoreCondition,merchantInfoCondition.getChannelNo());
				
				//给商户绑定主二维码
				Map<Object, Object> bindmap = bindQrcode(merchantInfoCondition,map,merchantStoreCondition.getStoreNo());
				//新增商户
				merchantInfoService.insert(merchantInfoCondition);
				
				//节点级联关系
				NodeRelationCondition dCondition = new NodeRelationCondition();
				dCondition.setIdentityFlag(Constants.IDENTITYFLAG_MERCHANT);//1渠道2代理商3商户：必需参数
				dCondition.setId(Strings.getUUID());
				dCondition.setParentNode(merchantInfoCondition.getAgentNo());
				dCondition.setCurrentNode(merchantInfoCondition.getMerchantNo());//当前节点id//必须参数
				//dCondition.setCurrentNodeLevel("0");//当前节点级别：渠道是0级别
				dCondition.setOperator(getCurrentUserId());//操作人
				nodeRelationService.doSaveNodeRelations(dCondition);
				
				if(bindmap != null) return JSONSerializer.toJSON(bindmap);
			}else{
				merchantInfoCondition.setUpdateTime(new Date());
				if(MerchantStatus.MERCHANT_STATUS_4.getCode().equals(merchantInfoCondition.getStatus()) ||
						MerchantStatus.MERCHANT_STATUS_2.getCode().equals(merchantInfoCondition.getStatus())){
					merchantInfoCondition.setStatus(MerchantStatus.MERCHANT_STATUS_0.getCode());
				}else if(MerchantStatus.MERCHANT_STATUS_3.getCode().equals(merchantInfoCondition.getStatus())){
					Map<String, String> res = merchantInfoService.merchantJoinChange(merchantInfoCondition);
					if(!Strings.equals(Constants.INTERFACE_SUCCESS_STATUS, res.get("respCode"))){
						map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,res.get("respDesc"));
						return JSONSerializer.toJSON(map);
					}
				}
				merchantInfoService.update(merchantInfoCondition);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 创建门店
	 * @param merchantStoreCondition
	 */
	private void insertStore(MerchantStoreCondition merchantStoreCondition,String channelNo){
		//新增
		MerchantStoreCondition store = new MerchantStoreCondition();
		store.setCreateTime(new Date());
		store.setId(Strings.getUUID());
		store.setStoreStatus(Constants.SUCCESS_STATE);
		store.setStoreAddress(merchantStoreCondition.getStoreAddress());
		store.setStoreName(merchantStoreCondition.getStoreName());
		store.setServicePhone(merchantStoreCondition.getServicePhone());
		store.setMerchantNo(merchantStoreCondition.getMerchantNo());
		store.setRecordStatus(ScanCodeConstants.Y);
		
		//设置门店编号
		ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
		channelExpandCondition.setChannelNo(channelNo);
		ChannelExpand channelExpand = channelExpandService.findAll(channelExpandCondition).get(0);
		store.setStoreNo(idCreateService.createParamNo(channelExpand.getChannelPreCode() + ConfigPreCode.STORE_PRE_CODE));
		
		merchantStoreService.insert(store);
	}

	/**
	 * 给商户绑定二维码，同时更改平台二维码的商户及门店信息，再修改代理商的剩余二维码数量
	 * @param merchantInfoCondition
	 * @param map
	 */
	private Map<Object, Object> bindQrcode(MerchantInfoCondition merchantInfoCondition,Map<Object, Object> map,String storeNo) {
		//调用查询该代理商下的二维码
		PlatformQrcode platformQrcode = platformQrcodeService.findPlatformQrcode(merchantInfoCondition.getAgentNo());
		if(platformQrcode != null){
			merchantInfoCondition.setQrCode(platformQrcode.getQrCode());
			//将使用过的二维码绑定给商户
			PlatformQrcodeCondition platformQrcodeCondition=new PlatformQrcodeCondition();
			platformQrcodeCondition.setId(platformQrcode.getId());
			platformQrcodeCondition.setMerchantNo(merchantInfoCondition.getMerchantNo());
			platformQrcodeCondition.setStoreId(storeNo);
			platformQrcodeCondition.setUpdateTime(new Date());
			platformQrcodeCondition.setUseStatus(Constants.STATUS_ACTIVE);
			platformQrcodeService.update(platformQrcodeCondition);
			//修改代理商二维码剩余数量
			AgentBaseCondition agentBaseCondition =new AgentBaseCondition();
			agentBaseCondition.setAgentNo(merchantInfoCondition.getAgentNo());
			List<AgentBase> agentBase = agentBaseService.findAll(agentBaseCondition);
			if(agentBase.size() > 0){
				if(agentBase.get(0).getLessTotal() == 0){
					map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,"商户已创建，代理商二维码已用完，请联系上级代理商!","url","/adminManage/merchantinfo");
					return map;
				}else{
					agentBaseCondition.setId(agentBase.get(0).getId());
					if(agentBase.get(0).getUseTotal() == null){
						agentBaseCondition.setUseTotal(1);
					}else{
						agentBaseCondition.setUseTotal(agentBase.get(0).getUseTotal()+1);
					}
					agentBaseCondition.setLessTotal(agentBase.get(0).getLessTotal()-1);
					agentBaseCondition.setUpdateTime(new Date());
					agentBaseService.update(agentBaseCondition);
				}
			}
		}else{
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,"商户已创建，代理商二维码已用完，请联系上级代理商!","url","/adminManage/merchantinfo");
			return map;
		}
		
		return null;
	}
	
	/**
	 * 状态变更
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSON updateStatus(HttpServletRequest request,MerchantInfoCondition merchantInfoCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {

			merchantInfoService.update(merchantInfoCondition);
			merchantInfoCondition.setOperator(getCurrentUserId());
			merchantInfoCondition.setUpdateTime(new Date());
			merchantInfoService.update(merchantInfoCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 绑定二维码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bindqrcode", method= {RequestMethod.POST})
	@ResponseBody
	public JSON bindqrcode(HttpServletRequest request,MerchantInfoCondition merchantInfoCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			//判定商户是否有二维码
			MerchantInfo entity = merchantInfoService.findByMerchantNo(merchantInfoCondition.getMerchantNo());
			if(entity != null && Strings.isNotEmpty(entity.getQrCode())){
				map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"商户已有二维码,请知悉");
				return JSONSerializer.toJSON(map);
			}
			merchantInfoCondition.setOperator(getCurrentUserId());
			merchantInfoCondition.setUpdateTime(new Date());
			MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
			merchantStoreCondition.setMerchantNo(merchantInfoCondition.getMerchantNo());
			List<MerchantStore> stores = merchantStoreService.findAll(merchantStoreCondition);
			if(stores.size() == 0){
				map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"商户门店信息不存在，请检查相关信息");
				return JSONSerializer.toJSON(map);
			}
			String StoreNo = stores.get(0).getStoreNo();
			Map<Object, Object> bindmap = bindQrcode(merchantInfoCondition,map,StoreNo);
			
			if(bindmap != null) return JSONSerializer.toJSON(bindmap);
			merchantInfoService.update(merchantInfoCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/merchantinfo");
		} catch (Exception e) {
			e.printStackTrace();
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 
	 * 商户审核（平台审核）
	 * @param merchantInfoCondition
	 * @throws Exception
	*/
	@RequestMapping(value="/audit/platform", method= {RequestMethod.POST})
	@ResponseBody
	@Transactional
	public JSON auditPlatform(HttpServletRequest request,ModelMap model,MerchantInfoCondition merchantInfoCondition,String storeNo) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		JSON json = null;
		logger.info("审核参数："+JSONObject.fromObject(merchantInfoCondition));
		
		try {
			if (user.getIdentityFlag().equals(Constants.IDENTITYFLAG_CHANNEL)) {				
				logger.info("平台审核merchantNo："+merchantInfoCondition.getId());
				merchantInfoCondition.setOperator(getCurrentUserId());
				merchantInfoCondition.setUpdateTime(new Date());
				String isRealAccount = request.getParameter("isRealAccount");
				json = merchantInfoService.auditPlatform(merchantInfoCondition, isRealAccount, storeNo);
				logger.info("平台审核完成merchantNo："+merchantInfoCondition.getId());
			}else if (user.getIdentityFlag().equals(Constants.IDENTITYFLAG_AGENT)){
				logger.info("上级审核merchantNo："+merchantInfoCondition.getId());
				merchantInfoCondition.setOperator(getCurrentUserId());
				merchantInfoCondition.setUpdateTime(new Date());
				String isRealAccount = request.getParameter("isRealAccount");
				json = merchantInfoService.auditOrgan(merchantInfoCondition, isRealAccount, storeNo);
				logger.info("上级审核完成merchantNo："+merchantInfoCondition.getId());
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{pageType}/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String pageType,@PathVariable String id) throws Exception {
		MerchantInfo entity = merchantInfoService.findById(id);
		if(entity != null && Strings.isNotEmpty(entity.getQrCode())){
			PlatformQrcode qrcode = platformQrcodeService.findByQrcode(entity.getQrCode());
			if(qrcode != null){
				if (!Strings.isBlank(qrcode.getStoreId())) {					
					MerchantStoreCondition store = new MerchantStoreCondition();
					store.setStoreNo(qrcode.getStoreId());
					List<MerchantStore> stores = merchantStoreService.findAll(store);
					if(stores.size() != 0){
						model.addAttribute("store",stores.get(0));					
					}
				}
			}
			if(Strings.isNotEmpty(entity.getBusType())){
				HfepayCategory category = hfepayCategoryService.findByCategoryNo(entity.getBusType());
				if(category != null){
					entity.setBusType(category.getName() + "(" + category.getCategoryNo() +  ")");
				}
			}
		}
		HfepayCategoryCondition hfepayCategoryCondition = new HfepayCategoryCondition();
		hfepayCategoryCondition.setLevel(Constants.CATEGORY_LEVEL_ONE);
		List<HfepayCategory> categoryOne = hfepayCategoryService.findAll(hfepayCategoryCondition);
		model.addAttribute("categoryOne",categoryOne);
		model.addAttribute("item",entity);
		model.addAttribute("pageType",pageType);
		return "admin/merchantinfo/detail";
	}
	
	/** 打印二维码 */
	@RequestMapping(value="/print/{qrCode}", method= {RequestMethod.GET})
	public String print(HttpServletRequest request,ModelMap model,@PathVariable String qrCode) throws Exception {
		PlatformQrcode entity = platformQrcodeService.findByQrcode(qrCode);
		if(entity == null){
			model.addAttribute("item",null);
		}else{
			model.addAttribute("item",entity);
		}
		return "admin/createQrcode";
	}
	
	/** 手机号码是否存在 */
	@RequestMapping(value="/existMobile",method=RequestMethod.POST)
	@ResponseBody
	public Boolean existMobile(HttpServletRequest request,String mobile,String id){	
		MerchantInfoCondition merchantInfoCondition = new MerchantInfoCondition();
		merchantInfoCondition.setMobile(mobile);
        List<MerchantInfo> list = merchantInfoService.findAll(merchantInfoCondition);
        if(list.isEmpty()){
        	return false;
        }
        if(Strings.isNotEmpty(id)){
        	for (MerchantInfo merchantInfo : list) {
    			if(!merchantInfo.getId().equals(id)){
    				return true;
    			}
    		}
        	return false;
        }
        
        return true;
	}
	
	@RequestMapping(value = "/freshImg/{merchantNo}/{type}")
	@ResponseBody
	public JSON freshImg(HttpServletRequest request,@PathVariable String merchantNo,@PathVariable String type){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			String img = merchantInfoService.freshImg(merchantNo,type);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
			map.put("img", img);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**删除*/
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,String id){
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			merchantInfoService.deleteById(id);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	@RequestMapping(value="/merchantinfo/list",method=RequestMethod.POST)
	@ResponseBody
	public List<MerchantInfo> merchantinfoList(HttpServletRequest request,MerchantInfoCondition merchantInfoCondition){	
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		merchantInfoCondition.setNodeSeq(user.getNodeSeq());
        List<MerchantInfo> list = merchantInfoService.findAll(merchantInfoCondition);
		return list;
	}
	
}

