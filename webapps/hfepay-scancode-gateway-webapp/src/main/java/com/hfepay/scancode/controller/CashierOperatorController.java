package com.hfepay.scancode.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.commons.base.collection.Lists;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.api.entity.vo.DataNodeVO;
import com.hfepay.scancode.api.service.ApiChannelWxParamsService;
import com.hfepay.scancode.commons.BaseErrorMsg;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierCondition;
import com.hfepay.scancode.commons.condition.MerchantCashierQrCondition;
import com.hfepay.scancode.commons.condition.MerchantQrcodeCondition;
import com.hfepay.scancode.commons.condition.MerchantStoreCondition;
import com.hfepay.scancode.commons.contants.MerchantStatus;
import com.hfepay.scancode.commons.contants.QrStatus;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.MerchantCashier;
import com.hfepay.scancode.commons.entity.MerchantCashierQr;
import com.hfepay.scancode.commons.entity.MerchantInfo;
import com.hfepay.scancode.commons.entity.MerchantQrcode;
import com.hfepay.scancode.commons.entity.MerchantStore;
import com.hfepay.scancode.commons.vo.MerchantCashierQrVo;
import com.hfepay.scancode.commons.vo.MerchantCashierVo;
import com.hfepay.scancode.service.commons.ScanCodeConstants;
import com.hfepay.scancode.service.contants.ConfigPreCode;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.IdCreateService;
import com.hfepay.scancode.service.operator.MerchantCashierQrService;
import com.hfepay.scancode.service.operator.MerchantCashierService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantQrcodeService;
import com.hfepay.scancode.service.operator.MerchantStoreService;
import com.hfepay.scancode.service.operator.ParamsInfoService;

/**
 * 收银员相关，商户操作端，
 * @author husain
 *
 */
@Controller
@RequestMapping("/scancode")
public class CashierOperatorController {
	public static final Logger log = LoggerFactory.getLogger(CashierOperatorController.class);
	@Autowired
	private MerchantCashierService merchantCashierService;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private MerchantStoreService merchantStoreService;
	@Autowired
	private MerchantQrcodeService merchantQrcodeService;
	@Autowired
	private MerchantCashierQrService merchantCashierQrService;
	@Autowired
	private ChannelExpandService channelExpandService;
	@Autowired
	private ApiChannelWxParamsService apiChannelWxParamsService;
	@Autowired
	private IdCreateService idCreateService;
	@Autowired
	private ParamsInfoService paramsInfoService;
	
	@RequestMapping("/bindingCashier/{cashierNo}")
	public String bindingCashier(ModelMap model,HttpServletRequest request,@PathVariable String cashierNo){
		log.info("bindingCashier cashierNo is "+cashierNo);
		//已绑定的码
		MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
		merchantCashierQrCondition.setCashierNo(cashierNo);
		merchantCashierQrCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		List<MerchantCashierQr> list = merchantCashierQrService.findAll(merchantCashierQrCondition);
		//已绑定的门店
		MerchantCashierCondition merchantCashierCondition = new MerchantCashierCondition();
		merchantCashierCondition.setCashierNo(cashierNo);
		merchantCashierCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		List<MerchantCashier> lister = merchantCashierService.findAll(merchantCashierCondition);
		
		StringBuffer storeName = new StringBuffer();
		StringBuffer storeNo = new StringBuffer();
		StringBuffer qrcode = new StringBuffer();
		StringBuffer qrcodeName = new StringBuffer();
		for (MerchantCashier merchantCashier : lister) {//门店名称和门店值
			MerchantCashierVo vo =(MerchantCashierVo)merchantCashier;
			storeName.append(Strings.isEmpty(vo.getStoreName())?"":vo.getStoreName()).append(",");
			storeNo.append(Strings.isEmpty(vo.getStoreNo())?"":vo.getStoreNo()).append(",");
		}
		for (MerchantCashierQr merchantCashierQr : list) {//二维码编号
			MerchantCashierQrVo vo = (MerchantCashierQrVo)merchantCashierQr;
			qrcode.append(Strings.isEmpty(vo.getQrCode())?"":vo.getQrCode()).append(",");
			qrcodeName.append(Strings.isEmpty(vo.getQrName())?"":vo.getQrName()).append(",");
		}
		//删除末尾的,
		if(storeName.length()>0){
			storeName = storeName.delete(storeName.length()-1, storeName.length());
		}
		if(storeNo.length()>0){
			storeNo = storeNo.delete(storeNo.length()-1, storeNo.length());
		}
		if(qrcode.length()>0){
			qrcode = qrcode.delete(qrcode.length()-1, qrcode.length());
		}
		if(qrcodeName.length()>0){
			qrcodeName = qrcodeName.delete(qrcodeName.length()-1, qrcodeName.length());
		}
		log.info("storeName is "+storeName+" storeNo is "+storeNo+" qrcode is "+qrcode+" qrcodeName is "+qrcodeName);
		request.setAttribute("storeName", storeName);
		request.setAttribute("storeNo", storeNo);
		request.setAttribute("qrcode", qrcode);
		request.setAttribute("qrcodeName", qrcodeName);
		request.setAttribute("cashier", cashierNo);
		return "scancode/wechat/bindingCashier";
	}
	
	@RequestMapping("/getStoreQrcodes/{cashierNo}")
	@ResponseBody
	public Map<String,List> getDataList(HttpServletRequest request,@PathVariable String cashierNo){
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		log.info("getDataList cashierNo is "+cashierNo+" admin is "+admin.getIdentityNo());
		//门店
		MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
		merchantStoreCondition.setMerchantNo(admin.getMerchantNo());
		merchantStoreCondition.setStoreStatus("3");//审核通过的门店信息
		List<MerchantStore> storeList = merchantStoreService.findAllMerchantStore(merchantStoreCondition);
		//二维码
		MerchantQrcodeCondition merchantQrcodeCondition = new MerchantQrcodeCondition();
		merchantQrcodeCondition.setMerchantNo(admin.getMerchantNo());
		merchantQrcodeCondition.setQrStatus(QrStatus.QR_1.getCode());//有效的码
		merchantQrcodeCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		List<MerchantQrcode> codeList = merchantQrcodeService.findAllMerchantQrcode(merchantQrcodeCondition);
		Map<String,List> map = new HashMap<String, List>();
		map.put("storeList", storeList);
		map.put("codeList", codeList);
		return map;
	}
	
	@RequestMapping("/bindCasiherOperator")
	@ResponseBody
	public BaseErrorMsg bindCasiherOperator(HttpServletRequest request,MerchantCashierQrCondition condition){
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		log.info("admin is "+admin.getIdentityNo());
		BaseErrorMsg msg = new BaseErrorMsg();
		condition.setMerchantNo(admin.getMerchantNo());
		long result = merchantCashierQrService.bindCasiher(condition);
		msg.setErrorMsg("绑定失败");
		if(result>0){
			msg.setErrorCode("0");
			msg.setErrorMsg("绑定成功");
		}
		return msg;
	}
	
	
	@RequestMapping("/cashier")
	public String cashier(ModelMap model,HttpServletRequest request){
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		MerchantInfo info = merchantInfoService.findByMerchantNo(admin.getMerchantNo());
		log.info("====================admin identityNo is "+admin.getIdentityNo());
		model.addAttribute("info",info);
		DataNodeVO vo = apiChannelWxParamsService.getWechatConfigEntity(admin.getAgentNo());
		ChannelExpand channelExpand = channelExpandService.findByChannelNo(vo.getOrganNo());
		//生一个带有商户编号门店编号的二维码
		request.setAttribute("icon", channelExpand.getIcon());
		return "scancode/wechat/cashier";
	}
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cashierContent", method= {RequestMethod.POST})
	@ResponseBody
	public Pager<MerchantCashier> listContent(HttpServletRequest request,ModelMap model,MerchantCashierCondition merchantCashierCondition){
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		merchantCashierCondition.setMerchantNo(admin.getIdentityNo());
		//merchantCashierCondition.setStatus("1");
		merchantCashierCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		merchantCashierCondition.setPageSize(5);//每次加载五个
		PagingResult<MerchantCashier> page = merchantCashierService.findPagingResultSelf(merchantCashierCondition);
		Pager<MerchantCashier> pager = new Pager<MerchantCashier>();
		pager.setPageNo(merchantCashierCondition.getPageNo());
		pager.setPageSize(merchantCashierCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
		int totalPages = (int)(page.getTotalCount()%5==0?page.getTotalCount()/5:page.getTotalCount()/5+1);
		pager.setTotalPages(totalPages);
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		return pager;
	}
	
	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/delCashier/{cashierNo}", method= {RequestMethod.POST})
	@ResponseBody
	public BaseErrorMsg delCashier(HttpServletRequest request,ModelMap model,@PathVariable String cashierNo){
		log.info(" delCashier cashierNo is "+cashierNo);
		BaseErrorMsg errorMsg = new BaseErrorMsg();
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		long result = merchantCashierService.delCashierByCashierNo(cashierNo,admin.getId());
		errorMsg.setErrorMsg("删除失败!");
		if(result>0){
			errorMsg.setErrorCode("0");
			errorMsg.setErrorMsg("删除成功");
		}
		return errorMsg; 
	}
	
	/***选择二维码菜单，同时加载数据**/
	@RequestMapping("/selectCode")
	public String selectCode(ModelMap model,HttpServletRequest request){
		//收银员绑定二维码
		return "scancode/wechat/selectCode";
	}
	
	@RequestMapping(value = "/cashierQrContent", method= {RequestMethod.POST})
	public String listQrContent(HttpServletRequest request,MerchantCashierQrCondition merchantCashierQrCondition){
		int currentPage = merchantCashierQrCondition.getPageNo();
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		merchantCashierQrCondition.setMerchantNo(admin.getMerchantNo());
		merchantCashierQrCondition.setCashierNo(admin.getIdentityNo());
		merchantCashierQrCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		merchantCashierQrCondition.setPageSize(5);//每次加载五个
		PagingResult<MerchantCashierQr> page = merchantCashierQrService.findPagingResultSelf(merchantCashierQrCondition);
		Pager<MerchantCashierQr> pager = new Pager<MerchantCashierQr>();
		pager.setPageNo(merchantCashierQrCondition.getPageNo());
		pager.setPageSize(merchantCashierQrCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
		int totalPages = (int)(page.getTotalCount()%5==0?page.getTotalCount()/5:page.getTotalCount()/5+1);
		pager.setTotalPages(totalPages);
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		request.setAttribute("pager", pager);
 		String flag = "0";
 		if(currentPage>=pager.getTotalPages()){//当前页是最后一页，不出现“更多”
 			flag="1";
 		}
 		request.setAttribute("flag", flag);
 		return "scancode/wechat/selectCodePage";
	}
	
	/***文本框切换***/
	@RequestMapping("/bindSelectCode")
	@ResponseBody
	public BaseErrorMsg bindSelectCode(HttpServletRequest request,MerchantCashierQrCondition qrCondition){
		//iscashier,id
		//收银员绑定二维码:只能点自己绑定的收银状态或者没有被别人使用的同一个绑定的二维码
		if("2".equals(qrCondition.getIsCashier())){//如果是开启状态，那么查询是否存在同一个嘛已开启，如果是，则不能再次开启
			MerchantCashierQrCondition cond = new MerchantCashierQrCondition();
			cond.setQrCode(qrCondition.getQrCode());
			cond.setIsCashier("2");
			List<MerchantCashierQr> list = merchantCashierQrService.findAll(cond);
			if(Lists.isNotEmpty(list)){//存在同一个已开启的码，不允许再次开启
				return new BaseErrorMsg("该二维码已被其他收银员开启");
			}
		}
		BaseErrorMsg msg = new BaseErrorMsg();
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		qrCondition.setOperator(admin.getUserName());//修改人
		qrCondition.setCashierNo(admin.getIdentityNo());
		if(Strings.isEmpty(qrCondition.getId())||Strings.isEmpty(qrCondition.getIsCashier())){
			msg.setErrorMsg("参数有误");
			return msg;
		}
		long result = merchantCashierQrService.bindSelectCode(qrCondition);
		if(result>0){
			msg.setErrorCode("0");
			msg.setErrorMsg("绑定成功");
		}
		return msg;
	}
	
	/***查看收银员绑定的二维码**/
	@RequestMapping("/seeBindedQrCode")
	@ResponseBody
	public Map seeBindedQrCode(HttpServletRequest request,MerchantCashierQrCondition qrCondition){
		Map<String,String> map= new HashMap<>();
		MerchantCashierQrCondition merchantCashierQrCondition = new MerchantCashierQrCondition();
		merchantCashierQrCondition.setCashierNo(qrCondition.getCashierNo());
		merchantCashierQrCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
		List<MerchantCashierQr> list = merchantCashierQrService.findAll(merchantCashierQrCondition);
		StringBuffer qrcode = new StringBuffer();
		StringBuffer qrcodeName = new StringBuffer();
		for (MerchantCashierQr merchantCashierQr : list) {//二维码编号
			MerchantCashierQrVo vo = (MerchantCashierQrVo)merchantCashierQr;
			qrcode.append(Strings.isEmpty(vo.getQrCode())?"":vo.getQrCode()).append(",");
			qrcodeName.append(Strings.isEmpty(vo.getQrName())?"":vo.getQrName()).append(",");
		}
		if(qrcode.length()>0){
			qrcode = qrcode.delete(qrcode.length()-1, qrcode.length());
		}
		if(qrcodeName.length()>0){
			qrcodeName = qrcodeName.delete(qrcodeName.length()-1, qrcodeName.length());
		}
		map.put("qrcode", qrcode.toString());
		map.put("qrcodeName", qrcodeName.toString());
		return map;
	}
	
	//门店添加页面
	@RequestMapping("/store")
	public String storePage(HttpServletRequest request){
		return "scancode/wechat/store";
	}
	@RequestMapping("/storeContent")
	public String storeContent(HttpServletRequest request,MerchantStoreCondition condition){
		int currentPage = condition.getPageNo();
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		condition.setMerchantNo(admin.getMerchantNo());
		condition.setRecordStatus(Constants.RECORD_STATUS_YES);
		condition.setPageSize(5);//每次加载五个
		PagingResult<MerchantStore> page = merchantStoreService.findPagingResult(condition);
		Pager<MerchantStore> pager = new Pager<MerchantStore>();
		pager.setPageNo(condition.getPageNo());
		pager.setPageSize(condition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
		int totalPages = (int)(page.getTotalCount()%5==0?page.getTotalCount()/5:page.getTotalCount()/5+1);
		pager.setTotalPages(totalPages);
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		request.setAttribute("pager", pager);
 		String flag = "0";
 		if(currentPage>=pager.getTotalPages()){//当前页是最后一页，不出现“更多”
 			flag="1";
 		}
 		request.setAttribute("flag", flag);
 		return "scancode/wechat/storecontent";
	}
	
	//查看门店的二维码
	@RequestMapping("/seeStoreQrCode")
	@ResponseBody
	public Map seeStoreQrCode(HttpServletRequest request,MerchantQrcodeCondition condition){
		Map<String,String> map= new HashMap<>();
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		condition.setMerchantNo(admin.getMerchantNo());
		List<MerchantQrcode> qrList = merchantQrcodeService.findAll(condition);
		StringBuffer qrcode = new StringBuffer();
		StringBuffer qrcodeName = new StringBuffer();
		for (MerchantQrcode vo : qrList) {
			qrcode.append(Strings.isEmpty(vo.getQrCode())?"":vo.getQrCode()).append(",");
			qrcodeName.append(Strings.isEmpty(vo.getQrName())?"":vo.getQrName()).append(",");
		}
		if(qrcode.length()>0){
			qrcode = qrcode.delete(qrcode.length()-1, qrcode.length());
		}
		if(qrcodeName.length()>0){
			qrcodeName = qrcodeName.delete(qrcodeName.length()-1, qrcodeName.length());
		}
		map.put("qrcode", qrcode.toString());
		map.put("qrcodeName", qrcodeName.toString());
		return map;
	}
	//修改或者添加门店
	@RequestMapping("/saveOrUpdateStore")
	@ResponseBody
	public BaseErrorMsg saveOrUpdateStore(HttpServletRequest request,MerchantStoreCondition merchantStoreCondition){
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		merchantStoreCondition.setOperator(admin.getUserName());
		try {
			if(Strings.isEmpty(merchantStoreCondition.getId())){
				MerchantInfo info = merchantInfoService.findByMerchantNo(admin.getMerchantNo());
				if(!MerchantStatus.MERCHANT_STATUS_3.getCode().equals(info.getStatus())){
					return new BaseErrorMsg("商户未审核通过不能添加门店");
				}
				//新增
				merchantStoreCondition.setCreateTime(new Date());
				merchantStoreCondition.setId(Strings.getUUID());
				merchantStoreCondition.setStoreStatus("2");
				merchantStoreCondition.setRecordStatus(ScanCodeConstants.Y);
				merchantStoreCondition.setMerchantNo(admin.getMerchantNo());
				merchantStoreCondition.setStoreType("1");//设置为分店
				//设置门店编号
				ChannelExpandCondition channelExpandCondition = new ChannelExpandCondition();
				channelExpandCondition.setChannelNo(admin.getChannelCode());
				ChannelExpand channelExpand = channelExpandService.findAll(channelExpandCondition).get(0);
				merchantStoreCondition.setStoreNo(idCreateService.createParamNo(channelExpand.getChannelPreCode() + ConfigPreCode.STORE_PRE_CODE));
				
				merchantStoreService.insert(merchantStoreCondition);
			}else{
				merchantStoreService.update(merchantStoreCondition);
			}
			return new BaseErrorMsg("0","操作成功");
		}catch(Exception e){
			log.error("saveOrUpdateStore",e);
			return new BaseErrorMsg("0","操作成功");
		}
	}
	
	//删除门店
	@RequestMapping("/delStore")
	@ResponseBody
	public BaseErrorMsg delStore(HttpServletRequest request,MerchantStoreCondition merchantStoreCondition){
		merchantStoreCondition.setStoreNo(merchantStoreCondition.getStoreNo());
		MerchantStore merchantStore = merchantStoreService.findAll(merchantStoreCondition).get(0);
		if ("3".equals(merchantStore.getStoreStatus())) {
			return new BaseErrorMsg("门店审核通过不允许删除");
		}
		else if("0".equals(merchantStore.getStoreType())){//总店不允许删除
			return new BaseErrorMsg("总店不允许删除");
		}
		else{
			merchantStoreService.deleteById(merchantStore.getId());
		}
		return new BaseErrorMsg("0","操作成功");
	}
	
	//添加修改二维码
	@RequestMapping("/addOrUpdateMerchantQrcode")
	@ResponseBody
	public BaseErrorMsg addOrUpdateMerchantQrcode(HttpServletRequest request,MerchantQrcodeCondition merchantQrcodeCondition){
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		merchantQrcodeCondition.setOperator(admin.getUserName());
		try {
			if(Strings.isEmpty(merchantQrcodeCondition.getId())){
				//门店必须审核通过，否则不予以创建二维码
				MerchantStoreCondition merchantStoreCondition = new MerchantStoreCondition();
				merchantStoreCondition.setStoreNo(merchantQrcodeCondition.getStoreId());
				MerchantStore merchantStore = merchantStoreService.findAll(merchantStoreCondition).get(0);
				if (!merchantStore.getStoreStatus().equals("3")) {
					return new BaseErrorMsg("门店尚未审核通过不能添加二维码");
				}
				//新增
				merchantQrcodeCondition.setCreateTime(new Date());
				merchantQrcodeCondition.setId(Strings.getUUID());
				merchantQrcodeCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				merchantQrcodeCondition.setQrStatus("1");
				merchantQrcodeCondition.setQrType("2");//子二维码
				merchantQrcodeCondition.setMerchantNo(admin.getMerchantNo());
				merchantQrcodeCondition.setAgentNo(admin.getAgentNo());
				merchantQrcodeCondition.setChannelNo(admin.getChannelCode());
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
				merchantQrcodeService.update(merchantQrcodeCondition);
			}
			return new BaseErrorMsg("0","操作成功");
		} catch (Exception e) {
			return new BaseErrorMsg("0","操作失败");
		}
	}
	
	//删除二维码
	@RequestMapping("/delMerchantQrcode")
	@ResponseBody
	public BaseErrorMsg delMerchantQrcode(HttpServletRequest request,MerchantQrcodeCondition merchantQrcodeCondition){
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		merchantQrcodeCondition.setOperator(admin.getUserName());
		try {
			merchantQrcodeService.delMerchantQrcode(merchantQrcodeCondition);//修改二维码为删除，同时修改二维码的收银状态为未收银，删除状态
			return new BaseErrorMsg("0","操作成功");
		} catch (Exception e) {
			return new BaseErrorMsg("0","操作失败");
		}
	}
	
	//编辑二维码页面
	@RequestMapping("/editcode/{storeId}")
	public String editcode(HttpServletRequest request,@PathVariable String storeId){
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		MerchantStoreCondition storeCondition = new MerchantStoreCondition();
		storeCondition.setStoreNo(storeId);
		storeCondition.setMerchantNo(admin.getMerchantNo());
		MerchantStore store = merchantStoreService.findByCondition(storeCondition);//一个商户对应的某个门店
		request.setAttribute("storeName",store.getStoreName());
		request.setAttribute("storeId", storeId);
		return "scancode/wechat/editCode";
	}
	//编辑二维码页面分页
	@RequestMapping("/editcodeContent/{storeId}")
	public String editcodeContent(HttpServletRequest request,@PathVariable String storeId,MerchantQrcodeCondition condition){
		//查询门店所有二维码
		int currentPage = condition.getPageNo();
		ChannelAdmin admin = (ChannelAdmin) request.getSession().getAttribute(Constants.CURRENTUSER);
		condition.setMerchantNo(admin.getMerchantNo());
		condition.setRecordStatus(Constants.RECORD_STATUS_YES);
		condition.setStoreId(storeId);
		condition.setPageSize(5);//每次加载五个
		PagingResult<MerchantQrcode> page = merchantQrcodeService.findPagingResultSelf(condition);
		Pager<MerchantQrcode> pager = new Pager<MerchantQrcode>();
		pager.setPageNo(condition.getPageNo());
		pager.setPageSize(condition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
		int totalPages = (int)(page.getTotalCount()%5==0?page.getTotalCount()/5:page.getTotalCount()/5+1);
		pager.setTotalPages(totalPages);
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		request.setAttribute("pager", pager);
 		String flag = "0";
 		if(currentPage>=pager.getTotalPages()){//当前页是最后一页，不出现“更多”
 			flag="1";
 		}
 		request.setAttribute("flag", flag);
 		return "scancode/wechat/editCodeContent";
	}
	
	
}
