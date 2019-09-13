/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.AgentBaseCondition;
import com.hfepay.scancode.commons.condition.AgreementInfoCondition;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.PlatformQrcodeCondition;
import com.hfepay.scancode.commons.contants.ParamsType;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.AgentBase;
import com.hfepay.scancode.commons.entity.AgentPromotion;
import com.hfepay.scancode.commons.entity.AgreementInfo;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.ChannelWxParams;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.ParamsInfo;
import com.hfepay.scancode.commons.entity.PlatformQrcode;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.AgentPromotionService;
import com.hfepay.scancode.service.operator.AgreementInfoService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.ChannelWxParamsService;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.ParamsInfoService;
import com.hfepay.scancode.service.operator.PlatformQrcodeService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/agentbase")
public class AgentBaseController extends BaseController{
	
	@Autowired
	private AgentBaseService agentBaseService;
	
	@Autowired
	private ChannelBaseService channelBaseService;
	
	@Autowired
	private MappingDicionService mappingDicionService;
	
	@Autowired
	private IdCreateService idCreateService;
	
	@Autowired
	private PlatformQrcodeService platformQrcodeService;
	
	@Autowired
	private ChannelExpandService channelExpandService;
	
	@Autowired
	private AgentPromotionService agentPromotionService;
	@Autowired
	private ChannelWxParamsService channelWxParamsService;
	
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private AgreementInfoService agreementInfoService;
	@Autowired
	private ParamsInfoService paramsInfoService;
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request) {
		return "admin/agentbase/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(HttpServletRequest request,ModelMap model,AgentBaseCondition agentBaseCondition){		
		PagingResult<AgentBase> page = agentBaseService.findPagingResult(agentBaseCondition);
		Pager<AgentBase> pager = new Pager<AgentBase>();
		pager.setPageNo(agentBaseCondition.getPageNo());
		pager.setPageSize(agentBaseCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/agentbase/listContent";
	}
	
	/** 列表无分页 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,AgentBaseCondition agentBaseCondition){		
        List<AgentBase> list = agentBaseService.findAll(agentBaseCondition);
        JSONObject json = new JSONObject();
        json.put("objList", list);
		return json;
	}
	

	/**
	 * @Title: list
	 * @Description: 列表查询，无分页
	 * @author: Ricky
	 * @param ChannelBaseCondition
	 * @return: JSONObject
	 * @date CreateDate : 2016-10-13 09:50:44
	 */
	@RequestMapping(value="/promotion/{agentNo}",method=RequestMethod.POST)
	public String findAgentPromotion(HttpServletRequest request,@PathVariable String agentNo){		
		AgentPromotion agentPromotion = agentPromotionService.findByAgentNo(agentNo);
		AgentBase base = agentBaseService.findByAgentNo(agentNo);
		request.setAttribute("agentPromotion", agentPromotion);
		request.setAttribute("agentBase", base);
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(base.getAgentNo());
		String organNo = "";
		if (null != vo) {
			organNo = vo.getOrganNo();
		}else {					
			organNo = base.getChannelNo();
		}
		ChannelExpand channelExpand = channelExpandService.findByChannelNo(organNo);
		request.setAttribute("icon", channelExpand.getIcon());
		
		return "admin/agentbase/agentpromotion";
	}
	
	/** 进入新增/更新 */
	@RequestMapping(value="/{id}", method= {RequestMethod.GET})
	public String show(ModelMap model,@PathVariable String id) throws Exception {
		if(!"0".equalsIgnoreCase(id)){
			AgentBase entity = agentBaseService.findById(id);
			 model.addAttribute("Obj", entity);
		}
		ChannelBaseCondition channelBaseCondition = new ChannelBaseCondition();
		channelBaseCondition.setStatus("1");
		List<ChannelBase> channelList =  channelBaseService.findAll(channelBaseCondition);
		model.addAttribute("channelList", channelList);
		
		MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
		mappingDicionCondition.setKeyNo("DLSJB");
		List<MappingDicion> agentLevelList = mappingDicionService.findAll(mappingDicionCondition);
		model.addAttribute("agentLevelList", agentLevelList);
		
		return "admin/agentbase/edit";
	}

	/** 新增/更新 */
	@RequestMapping(value="/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ModelMap model,AgentBaseCondition agentBaseCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		agentBaseCondition.setOperator(user.getUserName());
		try {
			if(Strings.isEmpty(agentBaseCondition.getId())){
				//新增
				agentBaseCondition.setQrTotal(0);
				agentBaseCondition.setUseTotal(0);
				agentBaseCondition.setLessTotal(0);
				agentBaseCondition.setCreateTime(new Date());
				agentBaseCondition.setId(Strings.getUUID());
				agentBaseCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				agentBaseCondition.setStatus(Constants.SUCCESS_STATE);
				agentBaseCondition.setAgentFlag("1");
				agentBaseService.saveAgentBaseAndNode(agentBaseCondition);
			}else{
				agentBaseCondition.setUpdateTime(new Date());
				agentBaseService.update(agentBaseCondition);
			}
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/agentbase");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/** 删除 */
	@RequestMapping(value="/del",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,AgentBaseCondition agentBaseCondition){		
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		agentBaseCondition.setOperator(user.getUserName());
		agentBaseCondition.setRecordStatus(Constants.RECORD_STATUS_NO);
		agentBaseCondition.setUpdateTime(new Date());
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			agentBaseService.update(agentBaseCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 状态变更
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/updateStatus", method= {RequestMethod.POST})
	@ResponseBody
	public JSON updateStatus(HttpServletRequest request,AgentBaseCondition agentBaseCondition){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			agentBaseService.updateStatus(agentBaseCondition.getId(),agentBaseCondition.getStatus());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}	
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		AgentBase entity = agentBaseService.findById(id);
		model.addAttribute("item",entity);
		return "admin/agentbase/detail";
	}
	
	/**
	 * 查询代理商编号
	 * @param channelNo，agentLevel
	 * @return
	 */
	@RequestMapping(value = "/checkAgentLevel", method= {RequestMethod.POST})
	@ResponseBody
	public JSON checkAgentLevel(HttpServletRequest request,String channelNo,String agentLevel){
		Map<Object, Object> map = new HashMap<Object, Object>();
        AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
        agentBaseCondition.setChannelNo(channelNo);
        agentBaseCondition.setAgentLevel(String.valueOf(Integer.parseInt(agentLevel)-1));
        List<AgentBase> agentBaseList = agentBaseService.findAll(agentBaseCondition);
        if(agentBaseList != null && agentBaseList.size()>0){
        	List<Map<String, String>> baseList = new ArrayList<Map<String, String>>();
			Map<String, String> baseMap = new HashMap<String, String>();
			for (Iterator<AgentBase> iterator = agentBaseList.iterator(); iterator.hasNext();) {
				AgentBase agentBase = (AgentBase) iterator.next();
				baseMap.put(agentBase.getAgentNo(), agentBase.getAgentName());
			}
			baseList.add(baseMap);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,"resultList",baseList);
        }else{
        	map = Maps.mapByAarray(EXECUTE_STATUS,FAILED);
        }
		return JSONSerializer.toJSON(map);
	}
	
	/** 分配二维码 */
	@RequestMapping(value="/assign/{id}", method= {RequestMethod.POST})
	@ResponseBody
	@Transactional
	public JSON assign(HttpServletRequest request,ModelMap model,@PathVariable String id,String qrTotal) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		try{
			//当前代理商信息
			AgentBase agentBase = agentBaseService.findById(id);
			String parentNo = agentBase.getParentNo();
			
			//如果当前代理商的上级机构是渠道
			if(Integer.parseInt(agentBase.getAgentLevel())==1){
				//根据渠道编号查询渠道信息
				ChannelBaseCondition channelBaseCondition = new ChannelBaseCondition();
				channelBaseCondition.setChannelNo(parentNo);
				List<ChannelBase> channelList = channelBaseService.findAll(channelBaseCondition);
				ChannelBase channelBase = (ChannelBase)channelList.get(0);
				//渠道总二维码数量
				int channelQrTotal = channelBase.getQrTotal();
				//渠道剩余的二维码数量
				int channelLessQrTotal = channelBase.getLessTotal();
				//当前代理商的总二维码数量+分配的二维码数量
				int agentQrTotal = 0;
				if(agentBase.getQrTotal()!=null){
					agentQrTotal = agentBase.getQrTotal()+Integer.parseInt(qrTotal);
				}else{
					agentQrTotal = Integer.parseInt(qrTotal);
				}
				//代理商的总二维码数量大于上级机构二维码数量
				if(agentQrTotal > channelQrTotal){
					map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"当前代理商的总二维码数量不能大于上级机构");
				}else if(Integer.parseInt(qrTotal) > channelLessQrTotal){//代理商分配的二维码数量大于上级机构剩余的二维码数量
					map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"当前代理商分配的二维码数量不能大于上级机构剩余的二维码数量");
				}else{
					//渠道已使用二维码数量
					int channel_userTotal = channelBase.getUseTotal()+Integer.parseInt(qrTotal);
					//渠道剩余二维码数量
					int channel_lessTotal = channelBase.getLessTotal()-Integer.parseInt(qrTotal);
					//更新渠道的剩余二维码数量和已使用的二维码数量
					ChannelBaseCondition channelCondition = new ChannelBaseCondition();
					channelCondition.setId(channelBase.getId());
					channelCondition.setUseTotal(channel_userTotal);
					channelCondition.setLessTotal(channel_lessTotal);
					channelBaseService.updateByCriteria(channelCondition);
					
					//更新二维码表，将分配的二维码更新到代理商中
					PlatformQrcodeCondition platformQrcodeCondition = new PlatformQrcodeCondition();
					platformQrcodeCondition.setChannelNo(parentNo);
					platformQrcodeCondition.setQrStatus("1");
					platformQrcodeCondition.setUseStatus("2");
					platformQrcodeCondition.setRecordStatus("Y");
					platformQrcodeCondition.setPageNo(1);
					platformQrcodeCondition.setPageSize(Integer.parseInt(qrTotal));
					PagingResult<PlatformQrcode> page = platformQrcodeService.findPagingResultByChannelNoAndAgentNo(platformQrcodeCondition);
					List<PlatformQrcode> qrcodeList = page.getResult();
					for (Iterator<PlatformQrcode> iterator = qrcodeList.iterator(); iterator.hasNext();) {
						PlatformQrcode platformQrcode = (PlatformQrcode) iterator.next();
						platformQrcode.setAgentNo(agentBase.getAgentNo());
						platformQrcode.setUpdateTime(new Date());
						PlatformQrcodeCondition qrcodeCondition = new PlatformQrcodeCondition();
						BeanUtils.copyProperties(platformQrcode, qrcodeCondition);
						platformQrcodeService.update(qrcodeCondition);
					}
					
					//更新上级渠道后 更新当前代理商的二维码信息
					updateAgentBase(id, qrTotal, user, agentBase);
					map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,"分配成功","url","/adminManage/agentbase");
				}
			}else{
				//如果当前代理商的上级机构是代理商
				AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
				agentBaseCondition.setAgentNo(parentNo);
				//根据上级机构编号查询上级代理商信息
				List<AgentBase> agentList = agentBaseService.findAll(agentBaseCondition);
				AgentBase base = (AgentBase)agentList.get(0);
				//上级代理商总二维码数量
				int parentQrTotal = base.getQrTotal();
				//上级代理商剩余二维码数量
				int parentLessQrTotal = base.getLessTotal();
				//当前代理商的总二维码数量+分配的二维码数量
				int agentQrTotal = 0;
				if(agentBase.getQrTotal()!=null){
					agentQrTotal = agentBase.getQrTotal()+Integer.parseInt(qrTotal);
				}else{
					agentQrTotal = Integer.parseInt(qrTotal);
				}
				//代理商的总二维码数量大于上级机构二维码数量
				if(agentQrTotal > parentQrTotal){
					map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"当前代理商的总二维码数量不能大于上级机构");
				}else if(Integer.parseInt(qrTotal) > parentLessQrTotal){//代理商分配的二维码数量大于上级机构剩余的二维码数量
					map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"当前代理商分配的二维码数量不能大于上级机构剩余的二维码数量");
				}else{
					//上级代理商已使用二维码数量
					int parent_userTotal = 0;
					if(base.getUseTotal()!=null){
						parent_userTotal = base.getUseTotal()+Integer.parseInt(qrTotal);
					}else{
						parent_userTotal = Integer.parseInt(qrTotal); 
					}
					//上级代理商剩余二维码数量
					int parent_lessTotal = 0;
					if(base.getLessTotal()!=null){
						parent_lessTotal = base.getLessTotal()-Integer.parseInt(qrTotal);
					}
					
					//更新上级代理商的剩余二维码数量和已使用的二维码数量
					AgentBaseCondition parentAgentCondition = new AgentBaseCondition();
					parentAgentCondition.setId(base.getId());
					parentAgentCondition.setUseTotal(parent_userTotal);
					parentAgentCondition.setLessTotal(parent_lessTotal);
					parentAgentCondition.setUpdateTime(new Date());
					agentBaseService.updateByCriteria(parentAgentCondition);
					
					//更新二维码表，将分配的二维码更新到代理商中
					PlatformQrcodeCondition platformQrcodeCondition = new PlatformQrcodeCondition();
					platformQrcodeCondition.setChannelNo(agentBase.getChannelNo());
					platformQrcodeCondition.setAgentNo(agentBase.getParentNo());
					platformQrcodeCondition.setQrStatus("1");
					platformQrcodeCondition.setUseStatus("2");
					platformQrcodeCondition.setRecordStatus("Y");
					platformQrcodeCondition.setPageNo(1);
					platformQrcodeCondition.setPageSize(Integer.parseInt(qrTotal));
					PagingResult<PlatformQrcode> page = platformQrcodeService.findPagingResultByChannelNoAndAgentNo(platformQrcodeCondition);
					List<PlatformQrcode> qrcodeList = page.getResult();
					for (Iterator<PlatformQrcode> iterator = qrcodeList.iterator(); iterator.hasNext();) {
						PlatformQrcode platformQrcode = (PlatformQrcode) iterator.next();
						platformQrcode.setAgentNo(agentBase.getAgentNo());
						platformQrcode.setUpdateTime(new Date());
						PlatformQrcodeCondition qrcodeCondition = new PlatformQrcodeCondition();
						BeanUtils.copyProperties(platformQrcode, qrcodeCondition);
						platformQrcodeService.update(qrcodeCondition);
					}
					
					//更新完上级的代理商后 更新当前代理商的二维码信息
					updateAgentBase(id, qrTotal, user, agentBase);
					map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,"分配成功","url","/adminManage/agentbase");
				}
			}
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,"分配失败");
		}
		return JSONSerializer.toJSON(map);
	}

	//当前代理商总二维码信息
	private void updateAgentBase(String id, String qrTotal, Admin user, AgentBase agentBase) {
		int total = 0;
		if(agentBase.getQrTotal()!=null){
			//当前代理商总二维码数量+分配的二维码数量
			total = agentBase.getQrTotal()+Integer.parseInt(qrTotal);
		}else{
			total = Integer.parseInt(qrTotal);
		}
		//当前代理商剩余二维码数量
		int lessTotal = 0;
		if(agentBase.getLessTotal()!=null){
			//当前代理商剩余二维码数量+分配的二维码数量
			lessTotal = agentBase.getLessTotal()+Integer.parseInt(qrTotal);
		}else{
			lessTotal = Integer.parseInt(qrTotal);
		}
		AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
		agentBaseCondition.setId(id);
		agentBaseCondition.setQrTotal(total);
		agentBaseCondition.setLessTotal(lessTotal);
		agentBaseCondition.setUpdateTime(new Date());
		agentBaseCondition.setOperator(user.getUserName());
		agentBaseService.updateByCriteria(agentBaseCondition);
	}
	
	/**
	 * 查询代理商编号
	 * @param channelNo，agentLevel
	 * @return
	 */
	@RequestMapping(value = "/hasPreCode", method= {RequestMethod.POST})
	@ResponseBody
	public JSON hasPreCode(HttpServletRequest request,String parentNo,String agentLevel){
		Map<Object, Object> map = new HashMap<Object, Object>();
		if(Integer.parseInt(agentLevel)>1){
			AgentBaseCondition agentBaseCondition = new AgentBaseCondition();
			agentBaseCondition.setAgentNo(parentNo);
			List<AgentBase> agentBaseList = agentBaseService.findAll(agentBaseCondition);
			if(agentBaseList != null && agentBaseList.size() > 0){
				AgentBase agentBase = agentBaseList.get(0);
				String preCode = agentBase.getAgentPreCode();
				String agentNo = idCreateService.createParamNo(preCode.toUpperCase());
				map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,"result",agentNo);
			}
		}else if(Integer.parseInt(agentLevel)==1){
			ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
			channelExpandCondition.setChannelNo(parentNo);
			List<ChannelExpand> channelExpandList = channelExpandService.findAll(channelExpandCondition);
			if(channelExpandList != null && channelExpandList.size() > 0){
				ChannelExpand channelExpand = channelExpandList.get(0);
				String preCode = channelExpand.getChannelPreCode();
				String agentNo = idCreateService.createParamNo(preCode.toUpperCase());
				map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,"result",agentNo);
			}
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**设置公众号*/
	@RequestMapping(value="/setpublicno/{channelNo}", method= {RequestMethod.GET})
	public String setPublicNo(ModelMap model,@PathVariable String channelNo) throws Exception {
		if(!"0".equalsIgnoreCase(channelNo)){
			ChannelWxParams channelWxParams = channelWxParamsService.findByChannelNo(channelNo);
			ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
			if(channelWxParams != null){
				JSONObject json = JSONObject.parseObject(channelWxParams.getWxParams());
				channelExpandCondition = JSONObject.toJavaObject(json,ChannelExpandCondition.class);
			}
			
			ChannelExpand entity = channelExpandService.findByChannelNo(channelNo);
			if(entity != null){
				BeanUtils.copyProperties(entity, channelExpandCondition);
				model.addAttribute("Obj", channelExpandCondition);
			}	
			AgreementInfoCondition agreementInfoCondition = new AgreementInfoCondition();
			agreementInfoCondition.setOrganNo(channelNo);
			List<AgreementInfo> agreementList = agreementInfoService.findAll(agreementInfoCondition);
			if(agreementList != null && agreementList.size() > 0){
				AgreementInfo agreementInfo = agreementList.get(0);
				model.addAttribute("agreementInfo",agreementInfo);
			}
		}
		ChannelExpandCondition channelExpandConditionSms = new ChannelExpandCondition();
		ParamsInfo paramsInfo = paramsInfoService.findParamsKey(channelNo,ParamsType.PARAMS_SMS.getCode());
		if (paramsInfo != null) {
			JSONObject json = JSONObject.parseObject(paramsInfo.getParamsValue());
			channelExpandConditionSms = JSONObject.toJavaObject(json,ChannelExpandCondition.class);
		}
		model.addAttribute("channelExpandConditionSms", channelExpandConditionSms);
		model.addAttribute("agentBase", agentBaseService.findByAgentNo(channelNo));
		return "admin/agentbase/setpublicno";
	}
	
	/**保存公众号*/
	@RequestMapping(value="/savepublicno", method= {RequestMethod.POST})
	@ResponseBody
	public JSON savePublicNo(HttpServletRequest request,ModelMap model,ChannelExpandCondition channelExpandCondition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
		channelExpandCondition.setOperator(user.getUserName());
		channelExpandCondition.setChannelPreCode(channelExpandCondition.getChannelPreCode().toUpperCase());//编码抬头转换大写
		try {
			agentBaseService.savePublicNo(channelExpandCondition);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/agentbase");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**设置代理商redis*/
	@RequestMapping(value="/set/agentRedis",method=RequestMethod.GET)
	@ResponseBody
	public JSON setAgentRedis(HttpServletRequest request){
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			agentBaseService.setAgentRedis();			
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
}

