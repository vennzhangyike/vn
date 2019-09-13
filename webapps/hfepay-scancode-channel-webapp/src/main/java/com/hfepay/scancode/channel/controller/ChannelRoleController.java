package com.hfepay.scancode.channel.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.channel.commons.Constants;
import com.hfepay.scancode.channel.commons.CustomerPagingResult;
import com.hfepay.scancode.channel.shiro.ShiroFilerChainManager;
import com.hfepay.scancode.commons.condition.ChannelBaseCondition;
import com.hfepay.scancode.commons.condition.ChannelRoleCondition;
import com.hfepay.scancode.commons.entity.ChannelAdmin;
import com.hfepay.scancode.commons.entity.ChannelResource;
import com.hfepay.scancode.commons.entity.ChannelRole;
import com.hfepay.scancode.commons.entity.ChannelRoleUser;
import com.hfepay.scancode.service.channel.ChannelResourceService;
import com.hfepay.scancode.service.channel.ChannelRoleService;
import com.hfepay.scancode.service.channel.ChannelRoleUserService;
import com.hfepay.scancode.service.channel.ChannelUrlFilterService;
import com.hfepay.scancode.service.operator.ChannelBaseService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;



/**
 * 角色管理
 * @author tanbiao
 *
 */
@Controller
@RequestMapping("/adminManage/channel/role")
public class ChannelRoleController extends BaseController{
	@Autowired
    private ChannelRoleService channelRoleService;
	@Autowired
	private ChannelResourceService channelResourceService;
	@Autowired
	private ChannelRoleUserService channelRoleUserService;
	@Autowired
	private ChannelUrlFilterService channelUrlFilterService;
	@Autowired
	private ShiroFilerChainManager shiroFilerChainManager;
	@Autowired
	private ChannelBaseService channelBaseService;
	
	/**
	 * 角色列表菜单入口
	 * @param request
	 * @return
	 */
	@RequestMapping("/roleList")
	public String roleList(ModelMap model,HttpServletRequest request){
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		ChannelBaseCondition channelBaseCondition = new ChannelBaseCondition();
		channelBaseCondition.setChannelNo(user.getChannelCode());
		model.addAttribute("channelBaseList", channelBaseService.findAll(channelBaseCondition));
		return "admin/channel/role/rolelist";
	}
	/**
	 * 角色列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/roleList/content")
	public String roleListContent(HttpServletRequest request,ChannelRoleCondition condition){
		ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentUser");
		condition.setChannelCode(user.getChannelCode());
		PagingResult<ChannelRole> list = channelRoleService.findAll(condition);
		request.setAttribute("roleList", new CustomerPagingResult<ChannelRole>(list, condition));
		request.setAttribute("channelBaseList", channelBaseService.findAll(new ChannelBaseCondition()));
		return "admin/channel/role/rolelistContent";
	}
	/**
	 * 角色编辑显示
	 * @param request
	 * @return
	 */
	@RequestMapping("/roleList/roleEdit")
	public String roleListEdit(HttpServletRequest request,ChannelRole role){
		ChannelRole r = channelRoleService.findRoleById(role.getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", role.getId());
		params.put("channelCode", r.getChannelCode());
		params.put("pid", role.getId());
        List<ChannelResource> list = channelResourceService.selectActiveResource(params);//角色对应的权限
        
        List<ChannelResource> rootScanAdminMenu = new ArrayList<ChannelResource>();//一级菜单
    	Map<String,List<ChannelResource>> secondMap = new HashMap<String,List<ChannelResource>>();//二级菜单
    	Map<String,List<ChannelResource>> thirdMap = new HashMap<String,List<ChannelResource>>();//三级菜单
    	Map<String,List<ChannelResource>> forthMap = new HashMap<String,List<ChannelResource>>();//四级菜单
    	for (ChannelResource resource : list) {
    		if(resource.getLevel()==1){//一级菜单
    			rootScanAdminMenu.add(resource);
    			continue;
    		}
    		if(resource.getLevel()==2){//二级菜单
    			if(secondMap.get(resource.getParentId())==null){
        			List<ChannelResource> secondScanAdminMenu = new ArrayList<ChannelResource>(); 
        			secondScanAdminMenu.add(resource);
        			secondMap.put(resource.getParentId(), secondScanAdminMenu);
        		}else{
        			secondMap.get(resource.getParentId()).add(resource);
        		}
    			continue;
    		}
    		if(resource.getLevel()==3){//三级菜单
    			if(thirdMap.get(resource.getParentId())==null){
        			List<ChannelResource> thirdMenu = new ArrayList<ChannelResource>(); 
        			thirdMenu.add(resource);
        			thirdMap.put(resource.getParentId(), thirdMenu);
        		}else{
        			thirdMap.get(resource.getParentId()).add(resource);
        		}
    			continue;
    		}
    		if(resource.getLevel()==4){//三级菜单
    			if(forthMap.get(resource.getParentId())==null){
        			List<ChannelResource> forthMenu = new ArrayList<ChannelResource>(); 
        			forthMenu.add(resource);
        			forthMap.put(resource.getParentId(), forthMenu);
        		}else{
        			forthMap.get(resource.getParentId()).add(resource);
        		}
    			continue;
    		}
    		
		}
    	request.setAttribute("rootScanAdminMenu1",rootScanAdminMenu);//一级菜单
    	request.setAttribute("secondScanAdminMenu1",secondMap);//二级菜单
    	request.setAttribute("thirdMap1",thirdMap);//三级资源
    	request.setAttribute("forthMap1",forthMap);//四级资源
    	request.setAttribute("roleMsg", r);//角色信息
		return "admin/channel/role/roleEdit";
	}
	
	
	@RequestMapping("/roleList/saveRoles")
	@ResponseBody
	public String saveRoles(HttpServletRequest request,HttpServletResponse response,String ids,String roleId){
		String result = channelRoleService.saveRoles(ids,roleId);
        //刷新权限
//        shiroFilerChainManager.initFilterChains(channelUrlFilterService.findAll());
//        ChannelAdmin user = (ChannelAdmin) request.getSession().getAttribute("currentScanAdminUser");
//        findAllMenus(request,user);
        PrintWriter out =null;
        try {
        	response.setContentType("text/plain; charset=UTF-8");
	   		 out = response.getWriter();
	   		 JSONObject js = new JSONObject();
	   		if(Strings.isNotEmpty(result)){
	                js.put("message", "修改成功");
	   		}else{
	   			 js.put("message", "修改失败");
	   		}
	   		out.print(js);
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			out.flush();
	        out.close();
		}
		return null;
	}
	
	/** 新增/更新 */
	@RequestMapping(value="/roleList/save", method= {RequestMethod.POST})
	@ResponseBody
	public JSON _new(HttpServletRequest request,ChannelRoleCondition condition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			if(Strings.isEmpty(condition.getId())){
				condition.setId(Strings.getUUID());
				condition.setCreateTime(new Date());
				channelRoleService.saveRole(condition);
			}else{
				channelRoleService.update(condition);
			}
			shiroFilerChainManager.initFilterChains(channelUrlFilterService.findAll());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/channel/role/roleList");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	@RequestMapping("/roleList/delRole")
	@ResponseBody
	public JSONObject delRole(HttpServletRequest request,String roleId){
		//检查角色是否绑定了用户
		List<ChannelRoleUser> list = channelRoleUserService.selectRoleUserByRoleId(roleId);
		JSONObject js = new JSONObject();
		if(null==list || list.isEmpty()){
			long result = channelRoleService.deleteRole(roleId);
	   		if(result>0){
	                js.put("message", "删除成功");
	   		}else{
	   			 js.put("message", "删除失败");
	   		}
		}else{
			 js.put("message", "该角色已绑定了用户不能删除");
		}
		return js;
	}
	
	/** 删除 */
	@RequestMapping(value="/isexeits",method=RequestMethod.POST)
	@ResponseBody
	public JSON del(HttpServletRequest request,String column,String value){		
        Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			int count = channelRoleService.findExeits(column,value);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"count",count);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}

	/**
	 * 菜单
	 */
	private void findAllMenus(HttpServletRequest request,ChannelAdmin info){
		List<ChannelResource> menuList = channelResourceService.selectActiveMenu(info.getId());//查询当前用户的菜单
        if(null!=menuList && !menuList.isEmpty()){
        	List<ChannelResource> rootScanAdminMenu = new ArrayList<ChannelResource>();//一级菜单
        	Map<String,List<ChannelResource>> map = new HashMap<String,List<ChannelResource>>();
        	for (ChannelResource menu : menuList) {
        		if(menu.getLevel()==1){//一级菜单
        			rootScanAdminMenu.add(menu);
        			continue;
        		}
        		if(map.get(menu.getParentId())==null){
        			List<ChannelResource> secondScanAdminMenu = new ArrayList<ChannelResource>(); 
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
	
	/** 渠道查询角色 */
	@RequestMapping(value="/roleList/findRoleByChannelCode", method= {RequestMethod.POST})
	@ResponseBody
	public JSON findRoleByChannelCode(HttpServletRequest request,ChannelRoleCondition condition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			List<ChannelRole> roles = channelRoleService.findAllNoPage(condition);
			map.put("roles", roles);
			map.put(EXECUTE_STATUS, SUCCESS);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
}
