package com.hfepay.commons.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.hfepay.commons.utils.Springs;

/**
 * 基于SpringMVC的异常全局处理工具
 * @author Sam
 *
 */
public class SpringmvcExceptionResolver extends SimpleMappingExceptionResolver {
	
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, 
											  HttpServletResponse response,
											  Object handler, 
											  Exception ex) {
		ExceptionHandler exHandler = Springs.getBean(ExceptionHandler.class);
		//TODO 暂时不想做，发现原来用Filter也可以解决一切问题
		return null;
	}
}
