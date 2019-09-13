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
import com.hfepay.commons.base.lang.Strings;
import com.hfepay.commons.base.lang.Throwables;
import com.hfepay.commons.utils.Springs;

/**
 * Exception拦截处理器，可以拦截一切WEB产生的错误除了ServletContextListener外。
 * @author Sam
 *
 */
public class ExceptionHandlerFilter implements Filter {
	private Logger logger = LoggerFactory.getLogger(ExceptionHandlerFilter.class);
	protected FilterConfig filterConfig;
	/**
	 * 错误转发页面
	 */
	protected String errorPage;
	
	/**
	 * 错误消息在request中的key
	 */
	protected String errorKey ;
	
	/**
	 * 所有的错误处理都返回json结果集
	 */
	protected boolean onlyJsonResponse;
	

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.errorPage = getInitParameter("errorPage","/common/error.jsp");
		this.setErrorKey(getInitParameter("errorKey","errorInfo"));	
		this.onlyJsonResponse = Boolean.valueOf(getInitParameter("onlyJsonResponse","false"));
	}
	
	private String getInitParameter(String paramsName,String defaultValue) {
		String value = filterConfig.getInitParameter(paramsName);
		return Strings.defaultIfEmpty(value,defaultValue);
	}

	public void doFilter(final ServletRequest request,final ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {	
			
			chain.doFilter(request, response);
		} catch (Exception ex) {
			logger.error("处理异常", ex);
			ExceptionHandler exceptionHandler = Springs.getBean(ExceptionHandler.class);
            if (exceptionHandler == null) {
                throw Throwables.makeThrow("你没有在你的Spring中注入SpringExceptionHandler,这可不是演习啊~");
            }
            
            final String errorPage = this.errorPage;
            final String errorKey  = this.errorKey;
            final boolean onlyJsonResponse = this.onlyJsonResponse;
            //从spring环境查找有没有注入的ExceptionForwardCreator，没有找到默认生成一个
            ExceptionForwardCreator forwardCreator = Springs.getBean(ExceptionForwardCreator.class);            
            if (forwardCreator == null) {
            	forwardCreator = new ExceptionForwardCreator() {
            		public ExceptionForward create(ErrorMessage err, Throwable th) {    
            			WebExceptionForward weForward = new WebExceptionForward(); 
            			weForward.setErrorPage(errorPage);
            			weForward.setErrorKey(errorKey);
            			weForward.setErrorMessage(err);
            			weForward.setThrowable(th);
            			weForward.setOnlyJsonResponse(onlyJsonResponse);
            			return weForward;
            		}            		
            	};
            }
            
        	//处理
            ExceptionForward ef = exceptionHandler.handle(ex,forwardCreator);
            
            //转换异常信息
            ef.forward((HttpServletRequest)request,(HttpServletResponse)response);
            
            
		}
	}
	
	

	public void destroy() {
		this.filterConfig = null;
		this.errorPage = null;
	}

	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}

	public String getErrorKey() {
		return errorKey;
	}

	public boolean isOnlyJsonResponse() {
		return onlyJsonResponse;
	}

	public void setOnlyJsonResponse(boolean onlyJsonResponse) {
		this.onlyJsonResponse = onlyJsonResponse;
	}

}
