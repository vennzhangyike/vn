package com.hfepay.common.sms.services;

import com.hfepay.common.sms.mailentity.MailEntity;

public interface MailService {

	/**
	 * 发送邮件
	 * @param mailEntity
	 * @return
	 * @throws Exception
	 */
	public boolean sendMail(MailEntity mailEntity)throws Exception;
}
