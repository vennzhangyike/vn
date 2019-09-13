package com.hfepay.commons.exception;

public interface ErrorMessageSupport {
	public void setErrorMessage(ErrorMessage em);
	
	public ErrorMessage getErrorMessage();
	
	public boolean hasErrorMessage();
}
