/*
 * Powered By [华付通]
 * Web Site: http://www.huaepay.com/
 * Since 2016 - 2016
 */
package com.hfepay.scancode.channel.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.common.excel.jxls.DynamicColumnExporter;
import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.Pager;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.BaseErrorMsg;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.commons.LimitModeEnum;
import com.hfepay.scancode.channel.commons.LimitPayCodeEnum;
import com.hfepay.scancode.channel.commons.LimitTypeEnum;
import com.hfepay.scancode.commons.condition.MappingDicionCondition;
import com.hfepay.scancode.commons.condition.OrganLimitCondition;
import com.hfepay.scancode.commons.condition.OrganWalletCondition;
import com.hfepay.scancode.commons.condition.OrganWithdrawalsCondition;
import com.hfepay.scancode.commons.entity.AgentBankcard;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBankcard;
import com.hfepay.scancode.commons.entity.MappingDicion;
import com.hfepay.scancode.commons.entity.OrganLimit;
import com.hfepay.scancode.commons.entity.OrganWallet;
import com.hfepay.scancode.commons.entity.OrganWithdrawals;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.commons.vo.OrganWithdrawalsVo;
import com.hfepay.scancode.service.operator.AgentBankcardService;
import com.hfepay.scancode.service.operator.ChannelBankcardService;
import com.hfepay.scancode.service.operator.MappingDicionService;
import com.hfepay.scancode.service.operator.OrganLimitService;
import com.hfepay.scancode.service.operator.OrganWalletService;
import com.hfepay.scancode.service.operator.OrganWithdrawalsService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

@Controller
@RequestMapping("/adminManage/organwithdrawals")
public class OrganWithdrawalsController extends BaseController{
	
	@Autowired
	private OrganWithdrawalsService organWithdrawalsService;
	@Autowired
	private OrganWalletService organWalletService;
	@Autowired
	private ChannelBankcardService channelBankcardService;
	@Autowired
	private AgentBankcardService agentBankcardService;
	@Autowired
	private MappingDicionService mappingDicionService;
	@Autowired
	private OrganLimitService organLimitService;
	
	/** binder用于bean属性的设置 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH24:mm:ss"), true));  
	}
	   
	/**
	 * 增加了@ModelAttribute的方法可以在本controller方法调用前执行,可以存放一些共享变量,如枚举值,或是一些初始化操作
	 */
	@ModelAttribute
	public void init(ModelMap model) {
		//model.put("now", new java.sql.Timestamp(System.currentTimeMillis()));
	}
	
	/** 列表 */
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public String list(ModelMap model,HttpServletRequest request){
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		//查询钱包余额
		OrganWalletCondition organWalletCondition = new OrganWalletCondition();
		organWalletCondition.setOrganNo(user.getIdentityNo());
		OrganWallet wallet = organWalletService.findByCondition(organWalletCondition);
		if(wallet==null){
			wallet = new OrganWallet();
			wallet.setBalance(new BigDecimal("0"));
			wallet.setFreezesAmt(new BigDecimal("0"));
		}
		request.setAttribute("wallet", wallet);
		String organNo = request.getParameter("organNo");
		if(Strings.isNotEmpty(organNo)){
			model.addAttribute("organNo",organNo);
		}
		return "admin/organwithdrawals/list";
	}
	
	/**
	 * 列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/content", method= {RequestMethod.POST})
	public String listContent(ModelMap model,OrganWithdrawalsCondition organWithdrawalsCondition,HttpServletRequest request){
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		organWithdrawalsCondition.setChannelNo(user.getChannelCode());//渠道编号
		if("2".equals(user.getIdentityFlag())){
			organWithdrawalsCondition.setOrganNo(user.getAgentNo());//代理商查看自己的提现
		}
		PagingResult<OrganWithdrawals> page = organWithdrawalsService.findPagingResult(organWithdrawalsCondition);
		Pager<OrganWithdrawals> pager = new Pager<OrganWithdrawals>();
		pager.setPageNo(organWithdrawalsCondition.getPageNo());
		pager.setPageSize(organWithdrawalsCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
 		
 		model.addAttribute("pager",pager);
 		Map<String, BigDecimal> t2 = organWithdrawalsService.getAmtStatistics(organWithdrawalsCondition);
		model.addAttribute("amtStatic",t2);
 		//model.addAttribute("amtStatic",organWithdrawalsService.getAmtStatic(organWithdrawalsService.findAll(organWithdrawalsCondition)));
		return "admin/organwithdrawals/listContent";
	}
	
	/** 提现审核列表 */
	@RequestMapping(value = "/auditlist",method= {RequestMethod.GET,RequestMethod.POST})
	public String auditlist(HttpServletRequest request){
		return "admin/organwithdrawalsaudit/list";
	}
	
	/**
	 * 提现审核列表列表显示
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/auditlistcontent", method= {RequestMethod.POST})
	public String auditlistcontent(ModelMap model,OrganWithdrawalsCondition organWithdrawalsCondition,HttpServletRequest request){
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		organWithdrawalsCondition.setChannelNo(user.getChannelCode());
		organWithdrawalsCondition.setIsChannel("0");
		//organWithdrawalsCondition.setStatus(Constants.WITHDRAWLS_AUDIT_INIT);
		PagingResult<OrganWithdrawals> page = organWithdrawalsService.findPagingResult(organWithdrawalsCondition);
		Pager<OrganWithdrawals> pager = new Pager<OrganWithdrawals>();
		pager.setPageNo(organWithdrawalsCondition.getPageNo());
		pager.setPageSize(organWithdrawalsCondition.getPageSize());
		pager.setResult(page.getResult());
		pager.setTotalCount(page.getTotalCount());
 		if(pager.getOrder() == null){
			pager.setOrder("");
		}
		model.addAttribute("pager",pager);
		return "admin/organwithdrawalsaudit/listContent";
	}
	
	
	
	/** 添加提现申请记录 */
	@RequestMapping(value="/addWithDraw",method=RequestMethod.POST)
	@ResponseBody
	public BaseErrorMsg addWithDraw(HttpServletRequest request,OrganWithdrawalsCondition organWithdrawalsCondition){	
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		//查询钱包余额
		OrganWalletCondition organWalletCondition = new OrganWalletCondition();
		organWalletCondition.setOrganNo(user.getIdentityNo());
		OrganWallet wallet = organWalletService.findByCondition(organWalletCondition);
		if(wallet==null){//钱包无余额
			return new BaseErrorMsg("钱包不存在");
		}
		if(wallet.getBalance().compareTo(organWithdrawalsCondition.getBalance())==-1){//提现金额大
			return new BaseErrorMsg("提现金额不能大于可用金额");
		}
		
		//根据登录人类型判定提现限额
		if (user.getIdentityFlag().equals(Constants.IDENTITYFLAG_CHANNEL)) {
			//增加提现金额限制
			MappingDicionCondition mappingDicionCondition = new MappingDicionCondition();
			mappingDicionCondition.setKeyNo("WITHDRAWLS_AMT");
			MappingDicion mappingDicion = mappingDicionService.findByCondition(mappingDicionCondition);
			if (null != mappingDicion) {
				if (new BigDecimal(mappingDicion.getParaVal()).compareTo(organWithdrawalsCondition.getBalance()) > 0) {
					return new BaseErrorMsg("提现金额不能小于["+mappingDicion.getParaVal()+"]限制金额");
				}
			}
		}else {
			OrganLimitCondition organLimitCondition = new OrganLimitCondition();
			organLimitCondition.setOrganNo(user.getIdentityNo());
			organLimitCondition.setLimitType(LimitTypeEnum.LIMIT_TYPE_FRTX.getValue());
			organLimitCondition.setLimitMode(LimitModeEnum.LIMIT_MODE_DB.getValue());
			organLimitCondition.setLimitPayCode(LimitPayCodeEnum.LIMIT_TYPE_QB.getValue());
			List<OrganLimit> limitList = organLimitService.findAll(organLimitCondition);
			if (!limitList.isEmpty() && limitList.size() > 0) {
				OrganLimit organLimit = limitList.get(0);
				if (organLimit.getMinLimit().compareTo(organWithdrawalsCondition.getBalance()) > 0) {
					return new BaseErrorMsg("提现金额不能小于["+organLimit.getMinLimit()+"]限制金额");
				}
			}
		}
		
		
		//校验银行卡是否绑定
		if (user.getIdentityFlag().equals("1")) {			
			ChannelBankcard card = channelBankcardService.findByChannelNo(user.getIdentityNo());
			if (null == card) {
				return new BaseErrorMsg("未绑定银行卡，不能提现");
			}
		}else {
			AgentBankcard card = agentBankcardService.findByAgentNo(user.getIdentityNo());
			if (null == card) {
				return new BaseErrorMsg("未绑定银行卡，不能提现");
			}
		}
		organWithdrawalsCondition.setChannelNo(user.getChannelCode());
		organWithdrawalsCondition.setOperator(user.getId());
		organWithdrawalsCondition.setOrganNo(user.getIdentityNo());
		organWithdrawalsCondition.setIsChannel("1".equals(user.getIdentityFlag())?"1":"0");
		//构造参数，分为渠道和代理商
        long result = organWithdrawalsService.saveWithDraw(organWithdrawalsCondition);
        if(result>0){
        	return new BaseErrorMsg("0", "提现申请成功");
        }
        return new BaseErrorMsg("提现申请失败");
	}
	
	
	/** 提现审核 */
	@RequestMapping(value="/addAuditWithDraw",method=RequestMethod.POST)
	@ResponseBody
	public BaseErrorMsg addAuditWithDraw(HttpServletRequest request,OrganWithdrawalsCondition organWithdrawalsCondition){	
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		if(!Arrays.asList(new String[]{"2","3"}).contains(organWithdrawalsCondition.getStatus())){
			return new BaseErrorMsg("0", "审核状态有误");
		}
		organWithdrawalsCondition.setOperator(user.getId());
		//提现审核
        long result = organWithdrawalsService.saveAuditWithDraw(organWithdrawalsCondition);
        if(result>0){
        	return new BaseErrorMsg("0", "提现审核成功");
        }
        return new BaseErrorMsg("提现审核失败");
	}
	
	/** 查看详情 */
	@RequestMapping(value="/detail/{id}", method= {RequestMethod.POST})
	public String show(HttpServletRequest request,ModelMap model,@PathVariable String id) throws Exception {
		OrganWithdrawals entity = organWithdrawalsService.findById(id);
		model.addAttribute("item",entity);
		return "admin/organwithdrawals/detail";
	}
	
	/** 打印交易订单 */
	@RequestMapping(value = "/print/{id}", method = { RequestMethod.GET })
	@ResponseBody
	public JSON print(HttpServletRequest request,  @PathVariable String id) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();		
		try {			
			OrganWithdrawals organWithdrawals = organWithdrawalsService.findById(id);
			map.put("organWithdrawals", organWithdrawals);
			String bankName = organWithdrawals.getBankName();
			map.put("bankName", bankName);
			String status = "";
			if("1".equals(organWithdrawals.getStatus())){
				status = "申请中";
			}else if("2".equals(organWithdrawals.getStatus())){
				status = "审核通过";
			}else if("3".equals(organWithdrawals.getStatus())){
				status = "审核拒接";
			}
			map.put("status",status);
			String orderTimeStr = Dates.format("yyyy-MM-dd HH:mm:ss", organWithdrawals.getCreateTime());
			map.put("orderTimeStr",orderTimeStr);
			return JSONSerializer.toJSON(map);
		} catch (Exception e) {
			e.printStackTrace();
			 map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
			return JSONSerializer.toJSON(map);
		}
		
	}
	
	/**
	 * 导出excel 
	 * @param request
	 * @param response
	 * @param condition
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "serial" })
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void excel(HttpServletRequest request, HttpServletResponse response,OrganWithdrawalsCondition organWithdrawalsCondition) throws Exception {
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			organWithdrawalsCondition.setChannelNo(user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			organWithdrawalsCondition.setOrganNo(user.getAgentNo());
		}
		
		String queryStartDate = (String)request.getParameter("queryStartDate");
		if(queryStartDate != ""){
//			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			organWithdrawalsCondition.setQueryStartDate(queryStartDate);			
		}
		String queryEndDate = (String)request.getParameter("queryEndDate");
		if(queryEndDate != ""){
			queryEndDate = queryEndDate + " 23:59:59";
//			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			organWithdrawalsCondition.setQueryEndDate(queryEndDate);
		}
		List<OrganWithdrawals> list = organWithdrawalsService.findAll(organWithdrawalsCondition);
		//传给模板的数据列表数据
		LinkedList<Map<String,Object>> records = new LinkedList<Map<String,Object>>();
		for (OrganWithdrawals organWithdrawals : list) {
			OrganWithdrawalsVo organWithdrawalsVo = (OrganWithdrawalsVo) organWithdrawals; 
			TreeMap<String,Object> record = new TreeMap<String, Object>();
			record.put("机构编号", organWithdrawals.getOrganNo());
			record.put("机构名称", organWithdrawalsVo.getOrganName());
			record.put("申请提现金额(元)", organWithdrawals.getBalance() == null ? 0:organWithdrawals.getBalance());
			record.put("实际到账金额(元)", organWithdrawals.getActualAmount() == null ? 0:organWithdrawals.getActualAmount());
			String status = "";
			if(Strings.equals("1", organWithdrawals.getStatus())){
				status="申请中";
			}else if(Strings.equals("2", organWithdrawals.getStatus())){
				status="审核通过";
			}else if(Strings.equals("3", organWithdrawals.getStatus())){
				status="审核拒绝";
			}	
			record.put("提现状态", status);
			record.put("操作人", organWithdrawalsVo.getUserName());
			record.put("申请时间", Dates.format("yyyy-MM-dd HH:mm:ss", organWithdrawals.getCreateTime()));			
			records.add(record);
		}
		//表头
		 List<String> columns = Arrays.asList(new String[]{"机构编号","机构名称","申请提现金额(元)","实际到账金额(元)","提现状态","操作人","申请时间"});
		//参数列表数据
		 List<String> props = columns;
		String title = null;// apiName +"详情统计"+Strings.getUUID();
		String downloadName = "提现记录" + "(" + Dates.format("yyyyMMddHHmmss", new Date()) + ").xls";
		DynamicColumnExporter.exportWithDownload(downloadName, title, columns, props, records, response, true);
	}
	
	/** 校验导出excel **/
	@RequestMapping(value = "/export/check", method = RequestMethod.GET)
	@ResponseBody
	public BaseErrorMsg check(HttpServletRequest request, HttpServletResponse response,OrganWithdrawalsCondition organWithdrawalsCondition) throws Exception {
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			organWithdrawalsCondition.setChannelNo(user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			organWithdrawalsCondition.setOrganNo(user.getAgentNo());
		}
		
		String queryStartDate = (String)request.getParameter("queryStartDate");
		if(queryStartDate != ""){
//			Date beginTime = Dates.parse("yyyy-MM-dd", beginTimeStr);
			organWithdrawalsCondition.setQueryStartDate(queryStartDate);			
		}
		String queryEndDate = (String)request.getParameter("queryEndDate");
		if(queryEndDate != ""){
			queryEndDate = queryEndDate + " 23:59:59";
//			Date endTime = Dates.parse("yyyy-MM-dd HH:mm:ss", endTimeStr);
			organWithdrawalsCondition.setQueryEndDate(queryEndDate);
		}
		List<OrganWithdrawals> list = organWithdrawalsService.findAll(organWithdrawalsCondition);
		if(list.size() > 10000){
			return new BaseErrorMsg("提现记录导出数据不能超过10000条");
		}
		return new BaseErrorMsg("0", "提现记录导出校验通过");
	}
	
}

