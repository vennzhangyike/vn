package com.hfepay.scancode.channel.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.hfepay.scancode.channel.commons.WebUtil;
import com.hfepay.scancode.commons.entity.Admin;

import net.sf.json.JSONObject;

public class LoginFilter implements Filter{
	/** 
	* 需要排除的页面 
	*/  
	private String excludedPages;  
	  
	private String[] excludedPageArray; 

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		excludedPages = filterConfig.getInitParameter("excludedFiles");  
		if (StringUtils.isNotEmpty(excludedPages)) {  
		excludedPageArray = excludedPages.split(",");  
		}  
		return;  
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		String path = req.getContextPath();
		StringBuffer url = req.getRequestURL();
		//如果登出，那么session通过req.getSession()会抛异常
		try{
			for (String page : excludedPageArray) {//判断是否在过滤url之外  
				if(url.indexOf(page)!=-1){  
					chain.doFilter(request, response);
					return;
				}  
			}  
			HttpSession session = req.getSession();
			Admin info = (Admin)session.getAttribute("currentUser");
			if(info!=null){
				chain.doFilter(request, response);
			}else{
				boolean isAjax = WebUtil.isAjax(req);
				if(isAjax){
					JSONObject js = new JSONObject();
					js.put("isNotLogin", true);
		             js.put("message", "登录超时,请重新登录");
					WebUtil.printJson(response, js);
					return;
				}else{
					resp.sendRedirect(path+"/index/login");
				}
			}
		}catch(Exception e){
			//resp.sendRedirect(path+"/index/login");
			throw new RuntimeException(e);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
