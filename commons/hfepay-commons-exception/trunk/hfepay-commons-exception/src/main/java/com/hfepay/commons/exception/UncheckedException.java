package com.hfepay.commons.exception;

import java.io.Serializable;

/**
 * 基于UnChecked类型的异常信息对象
 * 
 * @author Sam
 *
 */
public class UncheckedException extends RuntimeException implements ErrorMessageSupport ,Serializable {

 
	private static final long serialVersionUID = 2604025899291107399L;

	
	private ErrorMessage errorMessage ;
 
	public UncheckedException(){}
 
    public UncheckedException(Throwable e) {
        super(e);
    }
    
 
    public UncheckedException(String msg, Throwable e) {
        super(msg, e);
    }
    
    public UncheckedException(String msg) {
        super(msg);
    }

	public void setErrorMessage(ErrorMessage em) {
		this.errorMessage = em;		
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public boolean hasErrorMessage() {
		return (errorMessage != null);
	}
      

}
