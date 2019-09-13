package com.hfepay.scancode.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hfepay.common.web.controller.BaseController;
import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.utils.PagingResult;
import com.hfepay.scancode.commons.Constants;
import com.hfepay.scancode.commons.CustomerPagingResult;
import com.hfepay.scancode.commons.condition.ChannelExpandCondition;
import com.hfepay.scancode.commons.condition.SysRoleCondition;
import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.commons.entity.SysResource;
import com.hfepay.scancode.commons.entity.SysRole;
import com.hfepay.scancode.commons.entity.SysRoleUser;
import com.hfepay.scancode.service.operator.ChannelExpandService;
import com.hfepay.scancode.service.operator.SysResourceService;
import com.hfepay.scancode.service.operator.SysRoleService;
import com.hfepay.scancode.service.operator.SysRoleUserService;
import com.hfepay.scancode.service.operator.UrlFilterService;
import com.hfepay.scancode.shiro.ShiroFilerChainManager;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * 角色管理
 * @author tanbiao
 *
 */
@Controller
@RequestMapping("/adminManage/role")
public class RoleController extends BaseController{
	@Autowired
    private SysRoleService roleService;
	@Autowired
	private SysResourceService resourceService;
//	@Autowired
//    private AdminService userService;
	@Autowired
	private SysRoleUserService roleUserService;
	@Autowired
	private UrlFilterService urlFilterService;
	@Autowired
	private ShiroFilerChainManager shiroFilerChainManager;
	
	@Autowired
	private ChannelExpandService channelExpandService;
	
	/**
	 * 角色列表菜单入口
	 * @param request
	 * @return
	 */
	@RequestMapping("/roleList")
	public String roleList(HttpServletRequest request){
		ChannelExpandCondition channelCondition = new ChannelExpandCondition();
		channelCondition.setStatus(Constants.SUCCESS_STATE);
		request.setAttribute("channels",channelExpandService.findAll(channelCondition));
		return "admin/rolelist";
	}
	/**
	 * 角色列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/roleList/content")
	public String roleListContent(HttpServletRequest request,SysRoleCondition condition){
		PagingResult<SysRole> list = roleService.findAll(condition);
		request.setAttribute("roleList", new CustomerPagingResult<SysRole>(list, condition));
		return "admin/rolelistContent";
	}
	/**
	 * 角色编辑显示
	 * @param request
	 * @return
	 */
	@RequestMapping("/roleList/roleEdit")
	public String roleListEdit(HttpServletRequest request,SysRole role){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", role.getId());
        List<SysResource> list = resourceService.selectActiveResource(params);//角色对应的权限
        SysRole r = roleService.findRoleById(role.getId());
        List<SysResource> rootScanAdminMenu = new ArrayList<SysResource>();//一级菜单
    	Map<String,List<SysResource>> secondMap = new HashMap<String,List<SysResource>>();//二级菜单
    	Map<String,List<SysResource>> thirdMap = new HashMap<String,List<SysResource>>();//三级菜单
    	Map<String,List<SysResource>> forthMap = new HashMap<String,List<SysResource>>();//四级菜单
    	for (SysResource resource : list) {
    		if(resource.getLevel()==1){//一级菜单
    			rootScanAdminMenu.add(resource);
    			continue;
    		}
    		if(resource.getLevel()==2){//二级菜单
    			if(secondMap.get(resource.getParentId())==null){
        			List<SysResource> secondScanAdminMenu = new ArrayList<SysResource>(); 
        			secondScanAdminMenu.add(resource);
        			secondMap.put(resource.getParentId(), secondScanAdminMenu);
        		}else{
        			secondMap.get(resource.getParentId()).add(resource);
        		}
    			continue;
    		}
    		if(resource.getLevel()==3){//三级菜单
    			if(thirdMap.get(resource.getParentId())==null){
        			List<SysResource> thirdMenu = new ArrayList<SysResource>(); 
        			thirdMenu.add(resource);
        			thirdMap.put(resource.getParentId(), thirdMenu);
        		}else{
        			thirdMap.get(resource.getParentId()).add(resource);
        		}
    			continue;
    		}
    		if(resource.getLevel()==4){//三级菜单
    			if(forthMap.get(resource.getParentId())==null){
        			List<SysResource> forthMenu = new ArrayList<SysResource>(); 
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
		return "admin/roleEdit";
	}
	
	
	@RequestMapping("/roleList/saveRoles")
	@ResponseBody
	public String saveRoles(HttpServletRequest request,HttpServletResponse response,String ids,String roleId){
		String result = roleService.saveRoles(ids,roleId);
        //刷新权限
        shiroFilerChainManager.initFilterChains(urlFilterService.findAll());
        Admin user = (Admin) request.getSession().getAttribute("currentScanAdminUser");
        findAllMenus(request,user);
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
	public JSON _new(SysRoleCondition condition) throws Exception {
		Map<Object, Object> map = new HashMap<Object, Object>();
		try {
			if(Strings.isEmpty(condition.getId())){
				condition.setId(Strings.getUUID());
				condition.setCreateTime(new Date());
				roleService.saveRole(condition);
			}else{
				roleService.update(condition);
			}
			//刷新权限
	        shiroFilerChainManager.initFilterChains(urlFilterService.findAll());
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"url","/adminManage/role/roleList");
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}
	
	@RequestMapping("/roleList/delRole")
	@ResponseBody
	public JSONObject delRole(HttpServletRequest request,String roleId){
		//检查角色是否绑定了用户
		List<SysRoleUser> list = roleUserService.selectRoleUserByRoleId(roleId);
		JSONObject js = new JSONObject();
		if(null==list || list.isEmpty()){
			long result = roleService.deleteRole(roleId);
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
			int count = roleService.findExeits(column,value);
			map = Maps.mapByAarray(EXECUTE_STATUS,SUCCESS,VALUES,Constants.SUCCESS_MSG,"count",count);
		} catch (Exception e) {
			map = Maps.mapByAarray(EXECUTE_STATUS,FAILED,VALUES,Constants.FAIL_MSG);
		}
		return JSONSerializer.toJSON(map);
	}

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
}
