package com.hfepay.commons.entity;

public enum Gender {
	FEMALE(0),
	MALE(1);
	
	private int code;
	private Gender(int code){
		this.code = code;
	}
	
	public static Gender byCode(Integer code) {
		if(code == null){
			return MALE;
		}else if(code == 0){
			return FEMALE;
		}else{
			return MALE;
		}
	}
	
	public String toString(){
		return String.valueOf(code);
	}
}
