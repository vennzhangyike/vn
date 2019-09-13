/**
 * 修改历史：<br/>
 * =================================================================<br/>
 * 修改人 修改时间 修改原因/内容<br/>
 * =================================================================<br/>
 * sam 20100126 创建新版本<br/>
 */

package com.hfepay.commons.exception;

import java.io.Serializable;

/**
 * 简易错误信息对象。
 * 
 * @author Sam
 * 
 */
public class SimpleErrorMessage implements Serializable, ErrorMessage {
    /**
	 * serialVersionUID
	 *
	 * @since JDK 1.6
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 错误编码
     */
    private String errorCode;
    
    /**
     * 错误信息
     */
    private String errorMessage;
    
    /**
     * 错误详细信息
     */
    private String errorDetails;
    
    /**
     * 异常堆栈信息
     */
    private String throwableStack;
    
    public SimpleErrorMessage() {

    }
    
    public SimpleErrorMessage(String code, String message, String detail) {

        super();
        this.errorCode = code;
        this.errorMessage = message;
        this.errorDetails = detail;
    }
    
    public SimpleErrorMessage(String code, String message, String detail,String throwableStack) {

        this(code,message,detail);
        this.throwableStack = throwableStack;
    }
        
    public String toString() {

        return String.format("错误代码：%s\n错误信息：%s\n错误详细信息：%s\n", errorCode, errorMessage, errorDetails);
    }

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getError() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	public String getThrowableStack() {
		return throwableStack;
	}

	public void setThrowableStack(String throwableStack) {
		this.throwableStack = throwableStack;
	}	
	
}
