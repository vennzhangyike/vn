package com.hfepay.common.sms.services.impl;


import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hfepay.common.sms.contants.MailContants;
import com.hfepay.common.sms.mailentity.MailEntity;
import com.hfepay.common.sms.services.MailService;
import com.hfepay.commons.base.lang.Strings;

public class MailServiceImpl implements MailService{

	Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Override
	public boolean sendMail(MailEntity mailEntity) throws Exception {
		if(null == mailEntity){
			logger.info("**********传个null是想怎么样?**********");
			throw new Exception("**********邮件实体为空**********");
		}
		//有无内容
		if(Strings.isEmpty(mailEntity.getMessage())){
			logger.info("**********邮件内容都没有,你想干什么?**********");
			throw new Exception("**********邮件内容为空**********");
		}
		HtmlEmail email = new HtmlEmail();
		email.setHostName(MailContants.SMTP);
		email.setCharset(MailContants.ENCODEING);
		email.addTo(mailEntity.getReceiver());
		email.setFrom(MailContants.USERNAME,MailContants.NICKNAME);
		email.setAuthentication(MailContants.USERNAME, MailContants.PASSWORD);
		email.setSubject(mailEntity.getSubject());
		email.setMsg(mailEntity.getMessage());
		//附件啦
		if(null != mailEntity.getAttachments() && mailEntity.getAttachments().size() > 0){
			for (EmailAttachment attachment : mailEntity.getAttachments()) {
				email.attach(attachment);
			}
		}
		//发送
		email.send();
		return true;
	}
		

}
