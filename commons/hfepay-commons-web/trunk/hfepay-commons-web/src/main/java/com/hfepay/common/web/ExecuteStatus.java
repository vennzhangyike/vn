package com.hfepay.common.web;

public enum ExecuteStatus {
	SUCCESS("0"),
	FAILED("1"),
	SESSION_TIMEOUT("2");
	
	String code;
	ExecuteStatus(String code) {
		this.code = code;
	}
	
	public String code() {
		return code;
	}
		
	public String toString(){
		return code;
	}
}
