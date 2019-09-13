package com.hfepay.common.sms.mandao;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailAttachment;

import com.hfepay.common.sms.mailentity.MailEntity;
import com.hfepay.common.sms.services.impl.MailServiceImpl;
import com.hfepay.commons.base.io.Streams;

public class MailTest {

	public static void main(String[] args) throws Exception {
		//网络路径
		List<EmailAttachment> attachments = new ArrayList<EmailAttachment>();
		
		//附件
		EmailAttachment attachment2 = new EmailAttachment();
		//本地地址
		attachment2.setPath("D:/data/files/loginBackgroundImg/shebao.jpg");
		attachment2.setName("附件一"+attachment2.getPath().substring(attachment2.getPath().lastIndexOf(".")));
		attachments.add(attachment2);
		
		//邮件实体
		MailEntity mail = new MailEntity();
		mail.setReceiver(new String[]{"duanshijun@vip.qq.com"}); 
		mail.setSubject("号外...号外...发邮件啦!");
		mail.setAttachments(attachments);
		
		//读取模板内容
		InputStream in = MailTest.class.getClassLoader().getResourceAsStream("Template.html");
		Reader reader = Streams.utf8r(in);
		String message = Streams.readAndClose(reader);
		
		mail.setMessage(message);
		new MailServiceImpl().sendMail(mail);
		System.out.println("OK");
	}
}
