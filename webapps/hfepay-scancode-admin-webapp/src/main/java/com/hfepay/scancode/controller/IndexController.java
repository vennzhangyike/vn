package com.hfepay.scancode.controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.SysResource;
import com.hfepay.scancode.service.operator.AdminService;
import com.hfepay.scancode.service.operator.SysResourceService;
import com.hfepay.scancode.service.operator.UrlFilterService;
import com.hfepay.scancode.shiro.ShiroFilerChainManager;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;


@Controller
@RequestMapping("/")
public class IndexController  extends BaseController{
	public static final Logger log = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private SysResourceService resourceService;
	@Autowired
	private AdminService adminService;
	@Autowired
    private UrlFilterService urlFilterService;
	@Autowired
	private ShiroFilerChainManager shiroFilerChainManager;
	
	/**
	 * 菜单
	 */
	private void findAllMenus(HttpServletRequest request,Admin info){
		List<SysResource> menuList = resourceService.selectActiveMenu(info.getId());//查询当前用户的菜单
        if(null!=menuList && !menuList.isEmpty()){
        	List<SysResource> rootScanAdminMenu = new ArrayList<SysResource>();//一级菜单
        	Map<String,List<SysResource>> map = new HashMap<String,List<SysResource>>();
        	for (SysResource menu : menuList) {
        		if(menu.getLevel()==1){//一级菜单
        			rootScanAdminMenu.add(menu);
        			continue;
        		}
        		if(map.get(menu.getParentId())==null){
        			List<SysResource> secondScanAdminMenu = new ArrayList<SysResource>(); 
        			secondScanAdminMenu.add(menu);
        			map.put(menu.getParentId(), secondScanAdminMenu);
        		}else{
        			map.get(menu.getParentId()).add(menu);
        		}
    		}
        	/*Collections.sort(rootScanAdminMenu);//排序
        	if(!map.isEmpty()){
        		Set<Entry<Long, List<Menu>>> set = map.entrySet();
        		for (Entry<Long, List<Menu>> entry : set) {
        			Collections.sort(entry.getValue());
				}
        	}*/
        	request.getSession().setAttribute("rootScanAdminMenu",rootScanAdminMenu);
        	request.getSession().setAttribute("secondScanAdminMenu",map);
        }
	}
	/**
	 * 登录
	 */
	@RequestMapping
	public String index(HttpServletRequest request) {
		/**测试接口使用**/
		return "admin/login";
	}
	
	/**
	 * 登录
	 */
	@RequestMapping("index/login")
	public String login(HttpServletRequest request) {
		/**测试接口使用**/
		return "admin/login";
	}
	@RequestMapping("index/dologin")
	public String dologin(HttpServletRequest request,Admin user) {
		if (Strings.isEmpty(user.getUserName())) {
			request.setAttribute("err", "请输入用户名");
			return "admin/login";//登录页面
		}
		String error = "";
		String username = user.getUserName();
		String password = user.getPassword();
		
		Admin admin = findAdmin(request,username);
		if(admin == null){
			error = "用户名/密码错误";
			request.setAttribute("err", error);
			return "admin/login";//登录页面
		}
		Subject subject = SecurityUtils.getSubject();
		StringBuffer userNameToken = new StringBuffer("{\"userName\":\"");
		userNameToken.append(username).append("\"}");
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
	
	private Admin findAdmin(HttpServletRequest request, String username) {
		// TODO Auto-generated method stub
		Admin admin = adminService.findByUsername(username);//用户信息
		if(admin == null){
			return null;
		}
		Set<String> set = urlFilterService.findRoles(username);//角色
		admin.setRoles(set);
		request.getSession().setAttribute("currentScanAdminUser",admin);//当前登录用户放session中
		return admin;
	}
	
	/**
	 * 注销登录
	 * @param request
	 * @return
	 */
	@RequestMapping("index/logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("currentScanAdminUser");
		session.removeAttribute("rootScanAdminMenu");
		session.removeAttribute("secondScanAdminMenu");
		//session.invalidate();
		
		//刷新权限
        shiroFilerChainManager.initFilterChains(urlFilterService.findAll());
        
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
	        shiroFilerChainManager.initFilterChains(urlFilterService.findAll());
	        
	        Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
	        findAllMenus(request,user);
	        
	        map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
			e.printStackTrace();
		}
		return JSONSerializer.toJSON(map);
	}
}
