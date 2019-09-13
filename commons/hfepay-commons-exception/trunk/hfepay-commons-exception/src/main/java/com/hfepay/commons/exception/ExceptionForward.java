package com.hfepay.commons.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hfepay.commons.exception.forward.SimpleExceptionForward;
/**
 * 异常转发器
 * @author Sam
 *
 */
public abstract class ExceptionForward {
	protected Throwable throwable;
	protected ErrorMessage errorMessage;
	
	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	public abstract void forward(HttpServletRequest request,HttpServletResponse response);  
	
	public static ExceptionForward create(Throwable th,ErrorMessage err) {
		ExceptionForward ef = new SimpleExceptionForward();
		ef.setThrowable(th);
		ef.setErrorMessage(err);
		return ef;
	}
}
