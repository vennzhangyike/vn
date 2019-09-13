package com.hfepay.scancode.api.entity.message;

import java.util.Date;

/**
 * 商户资料待完善推送消息
 * @author lemon
 *
 */
@SuppressWarnings("serial")
public class UnperfectMessage extends PushMessage{

	
	private String content;//内容
	
	private Date registTime;//注册时间

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

}
