package com.hfepay.scancode.api.entity.message;

import java.io.Serializable;

/**
 * 公众号消息推送
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class PushMessage implements Serializable{

	private String touser;//指定用户的OPENID;
	
	private String url;//详情url
	
	private String title;//标题
	
	private String remark;//备注
	
	private String organNo;//渠道编号

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrganNo() {
		return organNo;
	}

	public void setOrganNo(String organNo) {
		this.organNo = organNo;
	}

	
	
	
}
