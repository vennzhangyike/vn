package com.hfepay.commons.exception;

/**
 * 错误信息接口，用于描述一个错误信息应具备的信息
 * @author Sam
 *
 */
public interface ErrorMessage {
	/**
	 * 错误信息
	 * @return
	 */
	String getError();
	
	/**
	 * 错误代码
	 * @return
	 */
	String getErrorCode();
	
	/**
	 * 异常堆栈信息
	 * @return
	 */
	String getThrowableStack();
	
	/**
	 * 错误详细信息
	 * @return
	 */
	String getErrorDetails();
}
