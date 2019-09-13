package com.hfepay.scancode.commons.contants;


/**
 * 
 * @author liushuyu
 * Desc错账类型
 */
public enum CheckFlag {
	STATUS_BDYDFW("本地有对方无","10"),
	STATUS_BDWDFY("本地无对方有","01"),
	STATUS_SFBYZ("双方不一致","11");
	
	private String desc;
	private String code;
	
	private CheckFlag(String desc,String code){
		this.desc=desc;
		this.code=code;
	}
	
	@Override
	public String toString() {
		return this.code+"_"+this.desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
