package com.hfepay.scancode.channel.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Dates;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.shiro.ShiroFilerChainManager;
import com.hfepay.scancode.commons.condition.ChannelAdminCondition;
import com.hfepay.scancode.commons.condition.MerchantCostCondition;
import com.hfepay.scancode.commons.condition.MerchantInfoCondition;
import com.hfepay.scancode.commons.condition.MerchantPaywayCondition;
import com.hfepay.scancode.commons.condition.OrganWalletCondition;
import com.hfepay.scancode.commons.condition.OrganWithdrawalsCondition;
import com.hfepay.scancode.commons.condition.UserMessageCondition;
import com.hfepay.scancode.commons.contants.PayCode;
import com.hfepay.scancode.commons.contants.TradeType;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelBase;
import com.hfepay.scancode.commons.entity.ChannelExpand;
import com.hfepay.scancode.commons.entity.ChannelResource;
import com.hfepay.scancode.commons.entity.MerchantPayway;
import com.hfepay.scancode.commons.entity.Message;
import com.hfepay.scancode.commons.entity.OrganWallet;
import com.hfepay.scancode.commons.entity.OrganWithdrawals;
import com.hfepay.scancode.commons.entity.UserMessage;
import com.hfepay.scancode.commons.vo.ChannelAdminVo;
import com.hfepay.scancode.service.channel.ChannelAdminService;
import com.hfepay.scancode.service.channel.ChannelResourceService;
import com.hfepay.scancode.service.channel.ChannelUrlFilterService;
import com.hfepay.scancode.service.operator.AgentBaseService;
import com.hfepay.scancode.service.operator.ChannelBaseService;
import com.hfepay.scancode.service.operator.MerchantCostService;
import com.hfepay.scancode.service.operator.MerchantInfoService;
import com.hfepay.scancode.service.operator.MerchantPaywayService;
import com.hfepay.scancode.service.operator.MessageService;
import com.hfepay.scancode.service.operator.OrganWalletService;
import com.hfepay.scancode.service.operator.OrganWithdrawalsService;
import com.hfepay.scancode.service.operator.UserMessageService;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;


@Controller
@RequestMapping
public class IndexController extends BaseController{
	public static final Logger log = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private ChannelResourceService channelResourceService;
	@Autowired
	private ChannelAdminService channelAdminService;
	@Autowired
    private ChannelUrlFilterService channelUrlFilterService;
	@Autowired
	private ShiroFilerChainManager shiroFilerChainManager;
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private UserMessageService userMessageService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private MerchantCostService merchantCostService;
	@Autowired
	private ChannelBaseService channelBaseService;
	@Autowired
	private AgentBaseService agentBaseService;
	@Autowired
	private OrganWalletService organWalletService;
	@Autowired
	private MerchantPaywayService merchantPaywayService;
	@Autowired
	private OrganWithdrawalsService organWithdrawalsService;
	private final int BATCH_SIZE=200;
	/**
	 * 菜单
	 */
	private void findAllMenus(HttpServletRequest request,ChannelAdmin info){
		List<ChannelResource> menuList = channelResourceService.selectActiveMenu(info.getId());//查询当前用户的菜单
        if(null!=menuList && !menuList.isEmpty()){
        	List<ChannelResource> rootMenu = new ArrayList<ChannelResource>();//一级菜单
        	Map<String,List<ChannelResource>> map = new HashMap<String,List<ChannelResource>>();
        	for (ChannelResource menu : menuList) {
        		if(menu.getLevel()==1){//一级菜单
        			rootMenu.add(menu);
        			continue;
        		}
        		if(map.get(menu.getParentId())==null){
        			List<ChannelResource> secondMenu = new ArrayList<ChannelResource>(); 
        			secondMenu.add(menu);
        			map.put(menu.getParentId(), secondMenu);
        		}else{
        			map.get(menu.getParentId()).add(menu);
        		}
    		}
        	/*Collections.sort(rootMenu);//排序
        	if(!map.isEmpty()){
        		Set<Entry<Long, List<Menu>>> set = map.entrySet();
        		for (Entry<Long, List<Menu>> entry : set) {
        			Collections.sort(entry.getValue());
				}
        	}*/
        	request.getSession().setAttribute("rootMenu",rootMenu);
        	request.getSession().setAttribute("secondMenu",map);
        }
        
	}
	/**
	 * 登录
	 */
	@RequestMapping("/")
	public String index(HttpServletRequest request,ModelMap model) {
		Object obj = request.getSession().getAttribute("currentChannelInfo");
		ChannelExpand channelExpand = (ChannelExpand)obj;
		ChannelBase channelBase = channelBaseService.findByChannelNo(channelExpand.getChannelNo());
		model.addAttribute("channelName", channelBase.getChannelName());
		return "admin/login";
	}
	
	/**
	 * 登录
	 */
	@RequestMapping("/index/login")
	public String login(HttpServletRequest request,ModelMap model) {
		return "admin/login";
	}
	
	
	@RequestMapping("/index/dologin")
	public String dologin(HttpServletRequest request,Admin user,ModelMap model) {
//		Object obj = request.getSession().getAttribute("currentChannelInfo");
//		ChannelExpand channelExpand = (ChannelExpand)obj;
//		ChannelBase channelBase = channelBaseService.findByChannelNo(channelExpand.getChannelNo());
//		model.addAttribute("channelName", channelBase.getChannelName());
		if (Strings.isEmpty(user.getUserName())) {
			request.setAttribute("err", "请输入用户名");
			return "admin/login";//登录页面
		}
		String error = "";
		String username = user.getUserName();
		String password = user.getPassword();
		
		//ChannelExpand channel = (ChannelExpand) request.getSession().getAttribute("currentChannelInfo");
		ChannelAdmin admin = findAdmin(request,username,"");
		if(admin == null){
			error = "用户名/密码错误";
			request.setAttribute("err", error);
			return "admin/login";//登录页面
		}
		Subject subject = SecurityUtils.getSubject();
		StringBuffer userNameToken = new StringBuffer("{\"userName\":\"");
		userNameToken.append(username).append("\"}");
//		userNameToken.append(username).append("\",\"channelCode\":\"").append(channel.getTemp1()).append("\"}");
		UsernamePasswordToken token = new UsernamePasswordToken(userNameToken.toString(), password);
		token.setRememberMe(false);
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			error = "用户名/密码错误";
			request.setAttribute("err", error);
			return "admin/login";//登录页面
		} catch (IncorrectCredentialsException e) {
			error = "用户名/密码错误";
			request.setAttribute("err", error);
			return "admin/login";//登录页面
		} catch (LockedAccountException e) {
			error = "账号被锁定";
			request.setAttribute("err", error);
			return "admin/login";//登录页面
		} catch (ExcessiveAttemptsException e) {
			error = "登录失败次数过多";
			request.setAttribute("err", error);
			return "admin/login";//登录页面
		}catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		}
		
		findAllMenus(request,admin);		
		return "redirect:/adminManage/index";
	}
	
	/** 初始化主页数据*/
	private void initPageData(HttpServletRequest request){
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		Map<String, BigDecimal> amtStatic = new HashMap<String, BigDecimal>();
		MerchantCostCondition merchantCostCondition = new MerchantCostCondition();
		
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
		request.getSession().removeAttribute("organNo");
		if(Constants.IDENTITYFLAG_CHANNEL.equals(user.getIdentityFlag())){
			merchantCostCondition.setChannelNo(user.getChannelCode());
			request.setAttribute("obj",channelBaseService.findByChannelNo(user.getChannelCode()));
			request.setAttribute("organNo",user.getChannelCode());
		}else if(Constants.IDENTITYFLAG_AGENT.equals(user.getIdentityFlag())){
			merchantCostCondition.setAgentNo(user.getAgentNo());
			request.setAttribute("obj",agentBaseService.findByAgentNo(user.getAgentNo()));
			request.setAttribute("organNo",user.getAgentNo());
		}else if(Constants.IDENTITYFLAG_MERCHANT.equals(user.getIdentityFlag())){
			merchantCostCondition.setMerchantNo(user.getMerchantNo());
		}
		Date date = new Date();
		String beginTimeStr = Dates.format("yyyy-MM-dd", date);
		merchantCostCondition.setBeginTimeStr(beginTimeStr);		
//		String endTimeStr = Dates.format("yyyy-MM-dd", date);
		merchantCostCondition.setEndTimeStr(beginTimeStr);
		merchantCostCondition.setType(TradeType.TRADE_TYPE_PAY.getCode());
		amtStatic = merchantCostService.getAmtStatistics(merchantCostCondition);
		request.setAttribute("user",user);
		request.setAttribute("amtStatic",amtStatic);
		
		OrganWithdrawalsCondition organWithdrawalsCondition = new OrganWithdrawalsCondition();
		organWithdrawalsCondition.setChannelNo(user.getChannelCode());
		organWithdrawalsCondition.setIsChannel("0");
		organWithdrawalsCondition.setStatus(Constants.WITHDRAWLS_AUDIT_INIT);
		List<OrganWithdrawals> organWithdrawals = organWithdrawalsService.findAll(organWithdrawalsCondition);
		request.setAttribute("organWithdrawalNumber",organWithdrawals.size());
	}
	

	private ChannelAdmin findAdmin(HttpServletRequest request, String username,String channelCode) {
		// TODO Auto-generated method stub
		ChannelAdmin admin = channelAdminService.findByUsername(username,channelCode);//用户信息
		if(admin == null) return null;
		Set<String> set = channelUrlFilterService.findRoles(username);//角色
		admin.setRoles(set);
		request.getSession().setAttribute("currentUser",admin);//当前登录用户放session中
		return admin;
	}
	
	/**
	 * 注销登录
	 * @param request
	 * @return
	 */
	@RequestMapping("/index/logout")
	public ModelAndView logout(HttpServletRequest request,ModelMap model) {
		Object obj = request.getSession().getAttribute("currentChannelInfo");
		ChannelExpand channelExpand = (ChannelExpand)obj;
		ChannelBase channelBase = channelBaseService.findByChannelNo(channelExpand.getChannelNo());
		model.addAttribute("channelName", channelBase.getChannelName());
		
		HttpSession session = request.getSession();
		session.removeAttribute("currentUser");
		session.removeAttribute("rootMenu");
		session.removeAttribute("secondMenu");
		//刷新权限
        shiroFilerChainManager.initFilterChains(channelUrlFilterService.findAll());
		//session.invalidate();
		return new ModelAndView("redirect:/index/login");//登录页面
	}
	
	/**
	 * 仅刷新角色缓存
	 * @param request
	 * @return
	 */
	@RequestMapping("index/refreshRole")
	@ResponseBody
	public JSON refreshRole(HttpServletRequest request) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try{
			//刷新权限
	        shiroFilerChainManager.initFilterChains(channelUrlFilterService.findAll());
	        
	        ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
	        findAllMenus(request,user);
	        
	        map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
			e.printStackTrace();
		}
		return JSONSerializer.toJSON(map);
	}
	
	/**
	 * 登录首页
	 * @param request
	 * @param returnUrl
	 * @return
	 */
	@RequestMapping("/adminManage/index")
	public String toSignin(HttpServletRequest request,String returnUrl,ModelMap model) {
		ChannelAdminVo user = (ChannelAdminVo) request.getSession().getAttribute("currentUser");
		MerchantInfoCondition condition  = new MerchantInfoCondition();
		
		List<String> userList = new ArrayList<String>();
		if (user.getIdentityFlag().equals("1")) {
			userList.add("3");
			userList.add("9");
			condition.setChannelNo(user.getChannelCode());
		}else if (user.getIdentityFlag().equals("2")) {
			userList.add("2");
			userList.add("9");
			condition.setAgentNo(user.getAgentNo());
		}else if (user.getIdentityFlag().equals("3")) {
			userList.add("1");
			userList.add("9");
			condition.setMerchantNo(user.getMerchantNo());
		}else{
			userList.add("1");
		}
		
		condition.setStatus("0");
		long merchantCount = merchantInfoService.countByCriteria(condition);
		condition.setStatus("1");
		merchantCount = merchantCount+merchantInfoService.countByCriteria(condition);
		request.getSession().setAttribute("merchantList",merchantCount);
		
		
		List<Message> messageList = messageService.findAllByUserTypeAndUserNo(userList,user.getUserName());
		if(messageList != null && messageList.size() > 0){
			for (Iterator<Message> iterator = messageList.iterator(); iterator.hasNext();) {
				Message message = (Message) iterator.next();
				UserMessageCondition userMessageCondition = new UserMessageCondition();
				userMessageCondition.setId(Strings.getUUID());
				userMessageCondition.setMessageId(message.getId());
				userMessageCondition.setUserNo(user.getUserName());
				userMessageCondition.setReadStatus("1");
				userMessageCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
				userMessageCondition.setOperator(user.getId());
				userMessageCondition.setCreateTime(new Date());
				userMessageService.insert(userMessageCondition);
			}
		}
		
		ChannelBase channelBase = channelBaseService.findByChannelNo(user.getChannelCode());
		model.addAttribute("channelName", channelBase.getChannelName());
		
		return "admin/index";
	}
	
	/**
	 * 我的账户
	 * @param request
	 * @param returnUrl
	 * @return
	 */
	@RequestMapping("/adminManage/mainTabs")
	public String mainTabs(HttpServletRequest request,String returnUrl) {
		this.initPageData(request);
		return "admin/mainTabs";
	}
	
	@RequestMapping(value="/index/message",method=RequestMethod.POST)
	@ResponseBody
	public JSON message(HttpServletRequest request){
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		if(user != null){
			List<String> userList = new ArrayList<String>();
			if (user.getIdentityFlag().equals("1")) {
				userList.add("3");
				userList.add("9");
			}else if (user.getIdentityFlag().equals("2")) {
				userList.add("2");
				userList.add("9");
			}else if (user.getIdentityFlag().equals("3")) {
				userList.add("1");
				userList.add("9");
			}
			List<Message> messageList = messageService.findAllByUserTypeAndUserNo(userList,user.getUserName());
			if(messageList != null && messageList.size() > 0){
				for (Iterator<Message> iterator = messageList.iterator(); iterator.hasNext();) {
					Message message = (Message) iterator.next();
					UserMessageCondition userMessageCondition = new UserMessageCondition();
					userMessageCondition.setId(Strings.getUUID());
					userMessageCondition.setMessageId(message.getId());
					userMessageCondition.setUserNo(user.getUserName());
					userMessageCondition.setReadStatus("1");
					userMessageCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
					userMessageCondition.setOperator(user.getId());
					userMessageCondition.setCreateTime(new Date());
					userMessageService.insert(userMessageCondition);
				}
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			UserMessageCondition userMessageCondition = new UserMessageCondition();
			userMessageCondition.setUserNo(user.getUserName());
			userMessageCondition.setReadStatus("1");
			userMessageCondition.setRecordStatus(Constants.RECORD_STATUS_YES);
			PagingResult<UserMessage> page = userMessageService.findByUserType(userMessageCondition,userList);
			List<UserMessage> userMessageList = page.getResult();
			int messageSize = 0;
			if(userMessageList.size() > 0){
				messageSize = userMessageList.size();
			}
			map = Maps.mapByAarray("messageSize",messageSize);
	        return JSONSerializer.toJSON(map);
		}
        return null;
	}
	
	/**
	 * @Title: toscan
	 * @Description: 扫码收款页面
	 * @author: husain
	 * @param request
	 * @param returnUrl
	 * @return
	 * @return: String
	 */
	@RequestMapping("/adminManage/toscan")
	public String toscan(HttpServletRequest request) {
		Map<String,String> map = PayCode.toMap();
		Map<String,String> paywayMap = new HashMap<>();
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		//查询商户的支付方式
		MerchantPaywayCondition merchantPaywayCondition = new MerchantPaywayCondition();
		merchantPaywayCondition.setStatus("1");//状态正常
		merchantPaywayCondition.setAcceptStatus("1");//入网成功
		merchantPaywayCondition.setMerchantNo(user.getMerchantNo());
		List<MerchantPayway> list = merchantPaywayService.findAll(merchantPaywayCondition);
		for (MerchantPayway merchantPayway : list) {
			String payCode =  merchantPayway.getPayCode();
			paywayMap.put(payCode, map.get(payCode));//商户配置的可用的支付方式
		}
		request.setAttribute("payWays", paywayMap);
		return "admin/toscan";
	}
	
	/**
	 * 获取微信用户基本信息
	 * @param request
	 * @return
	 */
	@RequestMapping("index/wechatUser")
	@ResponseBody
	public JSON wechatUser(HttpServletRequest request) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try{
			int count = channelAdminService.countUnregsit();
			if(count>BATCH_SIZE){//需要分页查询
				int page = count%BATCH_SIZE==0?count/BATCH_SIZE:count/BATCH_SIZE+1;
				for (int i = 0; i < page; i++) {
					List<ChannelAdmin> list = channelAdminService.findUnregsit(i*BATCH_SIZE,BATCH_SIZE);
					saveWechatUser(list);
				}
			}else{
				List<ChannelAdmin> list = channelAdminService.findUnregsit(0,BATCH_SIZE);
				saveWechatUser(list);
			}
	        map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
			e.printStackTrace();
		}
		return JSONSerializer.toJSON(map);
	}
	
	private void saveWechatUser(List<ChannelAdmin> list){
		for (ChannelAdmin channelAdmin : list) {
			if(Strings.isNotEmpty(channelAdmin.getOpenId())){
				ChannelAdminCondition condition = new ChannelAdminCondition();
				condition.setOpenId(channelAdmin.getOpenId());
				condition.setIdentityNo(channelAdmin.getIdentityNo());
				condition.setAgentNo(channelAdmin.getAgentNo());
				try {
					channelAdminService.toSaveWechatUser(condition);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 根据key删除redis
	 * @param request
	 * @return
	 */
	@RequestMapping("index/delRedis/{redisKey}")
	@ResponseBody
	public Map delReiskey(@PathVariable String redisKey){
		Map<Object, Object> map = new HashMap<Object, Object>();
		merchantInfoService.delReiskey(redisKey);
		map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		return map;
	}
}
