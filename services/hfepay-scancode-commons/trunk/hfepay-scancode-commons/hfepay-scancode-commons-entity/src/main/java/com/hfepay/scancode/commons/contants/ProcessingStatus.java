package com.hfepay.scancode.commons.contants;

/**
 * 
 * @author liushuyu
 * Desc处理标示：0 未处理 1处理成功 2 处理失败
 */
public enum ProcessingStatus {
	
	STATUS_WCL("未处理","0"),
	STATUS_CLCG("处理成功","1"),
	STATUS_CLSB("处理失败","2");
	
	private String desc;
	
	private String code;

	private ProcessingStatus(String desc,String code){
		this.desc=desc;
		this.code=code;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.code+"_"+ this.desc;
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
