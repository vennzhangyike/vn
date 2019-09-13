package com.hfepay.cache.jedis.exceptions;

public class JedisException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7845681604565340714L;

	/**
	 * 自定义的异常码
	 */
	private String errorCode;
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public JedisException(String message) {
		super(message);
	}
	
	public JedisException(final String code, final String message) {
		this(message);
		this.errorCode = code;
    }
}
