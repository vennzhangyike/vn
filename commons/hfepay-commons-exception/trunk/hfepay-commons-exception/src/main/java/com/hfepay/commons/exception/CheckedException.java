package com.hfepay.commons.exception;

import java.io.Serializable;

/**
 * Checked类型异常，对于服务层的接口应该抛出此异常，告知客户端有可能会抛出的异常
 * @author Sam
 *
 */
public class CheckedException extends Exception implements ErrorMessageSupport, Serializable {

 
	private static final long serialVersionUID = 2604025899291107399L;

	
	private ErrorMessage errorMessage;


	public void setErrorMessage(ErrorMessage em) {
		this.errorMessage = em;
	}


	public ErrorMessage getErrorMessage() {
		return this.errorMessage;
	}


	public boolean hasErrorMessage() {
		return (this.errorMessage != null);
	}
	
	 
	

}
