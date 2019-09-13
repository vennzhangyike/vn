package com.hfepay.common.sms.mailentity;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.mail.EmailAttachment;

public class MailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 收件人
	 */
	private String[] receiver;

	/**
	 * 主题
	 */
	private String subject;

	/**
	 * 内容
	 */
	private String message;

	/**
	 * 附件
	 */
	private List<EmailAttachment> attachments;

	public List<EmailAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<EmailAttachment> attachments) {
		this.attachments = attachments;
	}

	public String[] getReceiver() {
		return receiver;
	}

	public void setReceiver(String[] receiver) {
		this.receiver = receiver;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
