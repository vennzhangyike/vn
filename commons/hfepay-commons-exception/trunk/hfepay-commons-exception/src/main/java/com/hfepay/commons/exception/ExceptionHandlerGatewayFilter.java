package com.hfepay.commons.exception;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.commons.exception.forward.WebExceptionForward;
import com.hfepay.common.web.Servlets;
import com.hfepay.commons.base.enums.PaymentErrorEnum;
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.base.lang.Throwables;
import com.hfepay.commons.utils.Springs;

/**
 * Exception拦截处理器，可以拦截一切WEB产生的错误除了ServletContextListener外。
 * @author Sam
 *
 */
public class ExceptionHandlerGatewayFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(ExceptionHandlerGatewayFilter.class);
	protected FilterConfig filterConfig;
	
	
	private static final String template = "{\"resultCode\":%1$s,\"resultMsg\":\"%2$s\"}";
	
	/**
	 * 所有的错误处理都返回json结果集
	 */
	protected boolean onlyJsonResponse;
	

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.onlyJsonResponse = Boolean.valueOf(getInitParameter("onlyJsonResponse","false"));
	}
	
	private String getInitParameter(String paramsName,String defaultValue) {
		String value = filterConfig.getInitParameter(paramsName);
		return Strings.defaultIfEmpty(value,defaultValue);
	}

	public void doFilter(final ServletRequest request,final ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {	
			chain.doFilter(request, response);
		}catch (Exception e) {
			if(e instanceof ServicesException) {
				ServicesException se = (ServicesException)e;
				 String s = String.format(template, se.getResultCode(),se.getMessage());
				Servlets.renderJson((HttpServletResponse)response, s);
			}else {
				logger.error("处理异常", e);
				String s = String.format(template, PaymentErrorEnum.SYSTEM_CHANNEL_CODE.getValue(), PaymentErrorEnum.SYSTEM_CHANNEL_CODE.getDesc());
				Servlets.renderJson((HttpServletResponse)response, s);
			}
		}
	}

	public void destroy() {
		this.filterConfig = null;
	}

	public boolean isOnlyJsonResponse() {
		return onlyJsonResponse;
	}

	public void setOnlyJsonResponse(boolean onlyJsonResponse) {
		this.onlyJsonResponse = onlyJsonResponse;
	}
}
