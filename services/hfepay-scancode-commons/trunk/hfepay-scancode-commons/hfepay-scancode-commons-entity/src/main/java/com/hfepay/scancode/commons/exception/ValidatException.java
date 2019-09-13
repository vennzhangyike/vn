package com.hfepay.scancode.commons.exception;

public class ValidatException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -853429040211762091L;
	
	private String code;

	private String message;

	public ValidatException() {
		super();
	}

	public ValidatException(String code,String message) {
		super(message);
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
