package com.hfepay.scancode.channel.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import com.hfepay.scancode.channel.commons.WebUtil;

import net.sf.json.JSONObject;

public class CustomRolesAuthorizationFilter extends AuthorizationFilter { 

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException { 

        Subject subject = getSubject(request, response); 
        String[] rolesArray = (String[]) mappedValue; 

        if (rolesArray == null || rolesArray.length == 0) { 
            //no roles specified, so nothing to check - allow access. 
            return true; 
        } 

        for(int i=0;i<rolesArray.length;i++)
        {
            if(subject.hasRole(rolesArray[i]))
            {  
                return true;  
            }  
        }  
        return false; 
    }
    
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
    	// TODO Auto-generated method stub
    	boolean b = WebUtil.isAjax((HttpServletRequest)request);
    	if(b){//ajax請求，没有权限操作的
             JSONObject js = new JSONObject();
             js.put("isDenied", true);
             js.put("message", "沒有权限执行该操作");
             WebUtil.printJson(response, js);
             return false;
    	}
    	return super.onAccessDenied(request, response);
    }

} 
