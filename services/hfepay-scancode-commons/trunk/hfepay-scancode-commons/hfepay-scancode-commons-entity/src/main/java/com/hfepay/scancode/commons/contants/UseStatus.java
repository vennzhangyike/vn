package com.hfepay.scancode.commons.contants;

public enum UseStatus {
	USED("1"),//已使用
	UNUSE("2");//未使用
	private String type;

	UseStatus(String type){
		this.type = type;
	}
	
	public String value(){
		return type;
	}
}
