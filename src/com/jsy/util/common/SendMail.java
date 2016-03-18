package com.jsy.util.common;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMail {

	public static final String SMTP = "smtp.qq.com";
	public static final String FROM = "jwchen_tk@qq.com";  
	//String copyto = "sslf_vip@163.com";  
	
	public static final String TITLE = "百居易电子商务有限公司";
	public static final String USERNAME="245484419@qq.com";
	public static final String PASSWORD="Excalibur123";
	public static void send(String mailbox, String title, String content) {
		String mail_from = MailBean.mailAddress; // mailbox 发送到哪 title 标题
		try {
			Properties props = new Properties();

			props.put("nail.smtp.host", MailBean.mailServer);

			props.put("mail.smtp.auth", "true");

			Session s = Session.getInstance(props);

			s.setDebug(true);

			MimeMessage message = new MimeMessage(s);

			InternetAddress from = new InternetAddress(mail_from);

			message.setFrom(from);

			InternetAddress to = new InternetAddress(mailbox);

			message.setRecipient(Message.RecipientType.TO, to);

			message.setSubject(title);

			message.setText(content);

			message.setContent(content, "text/html;charset=gbk");

			message.setSentDate(new Date());

			message.saveChanges();

			Transport transport = s.getTransport("smtp");

			transport.connect(MailBean.mailServer, MailBean.mailCount, MailBean.mailPassword);

			transport.sendMessage(message, message.getAllRecipients());

			transport.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getContext(String key) {
		return "尊敬的用户您好：感谢你注册百居易电子商务平台，您的肯定就是我们前进的动力！<br/><a href='" + PropertiesUtils.localHostVal("localhostPost") + "loginpage.htm'>【百居易电子商务平台】</a>";
	}

}
