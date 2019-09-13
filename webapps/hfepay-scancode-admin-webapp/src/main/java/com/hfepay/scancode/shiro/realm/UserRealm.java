package com.hfepay.scancode.shiro.realm;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hfepay.scancode.commons.entity.Admin;
import com.hfepay.scancode.service.operator.AdminService;
import com.hfepay.scancode.service.operator.UrlFilterService;

import net.sf.json.JSONObject;
/**
 *自定义用户登录验证和权限验证的Realm
* @author ssd
* @date 2015-4-30 下午3:44:28
 */
@Service
public class UserRealm extends AuthorizingRealm {
	private static final Log log = LogFactory.getLog(UserRealm.class);

    @Autowired
    private AdminService adminService;
    @Autowired
    private UrlFilterService urlFilterService;
    @Autowired
    private  HttpServletRequest request;

    /**
     * 验证用户权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        Subject subject = SecurityUtils.getSubject();
        Admin info = (Admin)request.getSession().getAttribute("currentScanAdminUser");
        if(null==info){
        	authorizationInfo.addRoles(urlFilterService.findRoles(username));
        }
        else{
        	authorizationInfo.addRoles(info.getRoles());
        }
        //authorizationInfo.setStringPermissions(userService.findPermissions(username));
        return authorizationInfo;
    }

    /**
     * 验证用户登录信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String username = (String)token.getPrincipal();

        JSONObject obj = JSONObject.fromObject(username);
        Admin user = adminService.findByUsername(obj.getString("userName"));

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(user.getStatus() == 0) {
            throw new LockedAccountException(); //帐号锁定
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

    /**
     * 清除权限验证缓存
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除登录验证缓存
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    /**
     * 清除用户信息缓存
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 
    *
    * @Description: 清除所有权限信息
    * @param     设定文件 
    * @author ssd
    * @date 2015-4-30 下午3:48:14 
    * @return void    返回类型 
    * @throws
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 
    *
    * @Description: 清除所有登录信息
    * @param     设定文件 
    * @author ssd
    * @date 2015-4-30 下午3:48:41 
    * @return void    返回类型 
    * @throws
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 
    *
    * @Description: 清楚所有权限缓存
    * @param     设定文件 
    * @author ssd
    * @date 2015-4-30 下午3:46:22 
    * @return void    返回类型 
    * @throws
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
