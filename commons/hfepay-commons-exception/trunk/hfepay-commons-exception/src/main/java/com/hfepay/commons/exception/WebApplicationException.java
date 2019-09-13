package com.hfepay.commons.exception;

/**
 * 基于Web环境的异常信息，主要用于非mvc环境下的web错误处理，可以自定义一个错误信息显示页面
 * @author Sam
 *
 */
public class WebApplicationException extends ApplicationException implements WebFlow {

 

	private static final long serialVersionUID = -3169740036078757198L;
	
	/**
	 * 出错了要转发到的资源
	 */
	private String forward;
	
	/**
	 * 是否要重定向到这个资源
	 */
	private boolean isRedirect;
	
	public WebApplicationException(){} 
	
	public WebApplicationException(String msg, Throwable e) {
		super(msg, e);
		 
	}
	
	public WebApplicationException(Throwable e) {
		super(e);		
	}

	public void setForward(String forwardPage) {
		this.forward = forwardPage;
	}

	public String getForward() {
		return forward;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public boolean isRedirect() {
		return isRedirect;
	}
	

}
