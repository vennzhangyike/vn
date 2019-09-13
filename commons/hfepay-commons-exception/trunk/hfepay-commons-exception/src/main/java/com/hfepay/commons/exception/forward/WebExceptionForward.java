package com.hfepay.commons.exception.forward;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hfepay.commons.base.collection.Maps;
import com.hfepay.commons.base.lang.Throwables;
import com.hfepay.commons.exception.WebFlow;
import com.hfepay.common.web.ExecuteStatus;
import com.hfepay.common.web.RequestType;
import com.hfepay.common.web.Servlets;

public class WebExceptionForward extends SimpleExceptionForward { 

	/**
	 * 默认的全局错误转发页面，对于非json请求的情况下，需要一个用于全局处理或者显示错误的页面
	 */
	protected String errorPage;

	/**
	 * 对于json请求产生的错误，在转到具体的错误页面之前要把错误消息存于request.attr中，便于具体的页面进行错误处理
	 */
	protected String errorKey = "errorMessage";
	
	/**
	 * 基于json请求的错误处理中的错误代码的json属性名称
	 */
	protected String errorCodeName = "errorCode";
	
	/**
	 * 基于json请求的错误处理中的错误消息的json属性名称
	 */
	protected String errorMessageName = "errorMsg";
	
	/**
	 * 基于json请求的错误处理中的错误状态的json属性名称
	 */
	protected String executeStstusName = "executeStatus";
	
	/**
	 * 错误结果全部使用json进行回写
	 */
	protected boolean onlyJsonResponse = false;
	

	public void forward(HttpServletRequest request,HttpServletResponse response) {

		request.setAttribute(errorMessage.getErrorCode(), errorMessage);

		String forwardPage = errorPage;
		Boolean isRedirect = false;
		
		//如果是基于WebFlow的话，则查看是否要做转发什么的进行转发
		if (throwable instanceof WebFlow) {
			forwardPage = ((WebFlow) throwable).getForward();
			isRedirect = ((WebFlow) throwable).isRedirect();
			try {
				if (isRedirect) {
					request.getSession().setAttribute(errorMessage.getErrorCode(), errorMessage);
					response.sendRedirect(getRedirectPath(request, forwardPage));
				} else {
					request.getRequestDispatcher(forwardPage).forward(request,response);
				}
			} catch (Exception ex) {
				throw Throwables.wrapThrow(ex);
			}
			return;
		}
		
		//非WebFlow的话，则根据请求的类型转发错误页面，或者直接回写进Response中		
		RequestType requestType = Servlets.getRequestType(request);
		if (requestType == RequestType.JSON || onlyJsonResponse) {
			Map<String,String> errorMessages = 
					Maps.map(errorCodeName,errorMessage.getErrorCode(),
							 errorMessageName,errorMessage.getError(),
							 executeStstusName,ExecuteStatus.FAILED.code());
			Servlets.renderJson(response, errorMessages);
		} else {
			try {
				request.setAttribute(errorKey, errorMessage);
				request.getRequestDispatcher(forwardPage).forward(request,response);
			} catch (Exception ex) {
				throw Throwables.wrapThrow(ex);
			}
		}

	}

	private String getRedirectPath(HttpServletRequest request, String forward) {
		String contextPath = request.getContextPath();
		if (forward.trim().startsWith("/")) {
			return contextPath + forward;
		}
		return contextPath + "/" + forward;
	}

	public String getErrorKey() {
		return errorKey;
	}

	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getErrorCodeName() {
		return errorCodeName;
	}

	public void setErrorCodeName(String errorCodeName) {
		this.errorCodeName = errorCodeName;
	}

	public String getErrorMessageName() {
		return errorMessageName;
	}

	public void setErrorMessageName(String errorMessageName) {
		this.errorMessageName = errorMessageName;
	}

	public String getExecuteStstusName() {
		return executeStstusName;
	}

	public void setExecuteStstusName(String executeStstusName) {
		this.executeStstusName = executeStstusName;
	}

	public boolean isOnlyJsonResponse() {
		return onlyJsonResponse;
	}

	public void setOnlyJsonResponse(boolean onlyJsonResponse) {
		this.onlyJsonResponse = onlyJsonResponse;
	}
}
