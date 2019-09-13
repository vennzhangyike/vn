package com.hfepay.pay.service.exception;

public class PayException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 763236671443451524L;
	private String code;
	private String message;

	public PayException() {
		super();
	}

	public PayException(String code,String message) {
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
