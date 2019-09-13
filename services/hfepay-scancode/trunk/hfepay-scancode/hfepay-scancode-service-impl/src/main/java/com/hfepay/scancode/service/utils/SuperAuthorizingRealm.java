package com.hfepay.scancode.service.utils;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SuperAuthorizingRealm extends AuthorizingRealm {
	
	
	Logger logger = LoggerFactory.getLogger(SuperAuthorizingRealm.class);
	
//	@Autowired
//	UserRegisterService userRegisterService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection authcToken) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
//        UserRegister account = null;
//        try {
//        	JSONObject obj = JSONObject.fromObject(token.getUsername());
//			account = userRegisterService.findByUsername(obj.getString("userName"),obj.getString("channelCode"));
//		} catch (Exception e) {
//			logger.error("根据商户账号查询账号信息异常～",e);
//		}
//        return new SimpleAuthenticationInfo(account.getUserName(),
//        		account.getPassWord(), getName());
        return null;
	}

}
