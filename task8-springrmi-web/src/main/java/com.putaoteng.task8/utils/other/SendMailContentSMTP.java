package com.putaoteng.task8.utils.other;

import com.sun.mail.smtp.SMTPTransport;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class SendMailContentSMTP {
	private static Logger logger = Logger.getLogger(SendMailContentSMTP.class.getName());

	private static final String SENDCLOUD_SMTP_HOST = "smtp.sendcloud.net";
	private static final int SENDCLOUD_SMTP_PORT = 25;
	

	private String url;

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	private static String getMessage(String reply) {
		String[] arr = reply.split("#");
		String messageId = null;

		if (arr[0].equalsIgnoreCase("250 ")) {
			messageId = arr[1];
		}
		return messageId;
	}

	public static void send(final String apiUser, final String apiKey, String address) {
		logger.info("send() 被执行...");
		// 配置javamail
		Properties props = System.getProperties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", SENDCLOUD_SMTP_HOST);
		props.put("mail.smtp.port", SENDCLOUD_SMTP_PORT);
		props.setProperty("mail.smtp.auth", "true");
		props.put("mail.smtp.connectiontimeout", 180);
		props.put("mail.smtp.timeout", 600);
		props.setProperty("mail.mime.encodefilename", "true");
		//和sendcloud服务器建立会话
		Session mailSession = Session.getInstance(props, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(apiUser, apiKey);
			}
		});

		//设置发信信息
		MimeMessage message = new MimeMessage(mailSession);
		try {
			//设置发信人,发信地址,发信编码等.
			message.setFrom(new InternetAddress("from@sendcloud.org", "任务7", "UTF-8"));
			//收信人地址
			message.addRecipient(RecipientType.TO, new InternetAddress(address));
			//邮件主题
			message.setSubject("验证邮件", "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("普通邮件发送异常(SMTP): "+address+"无效的编码格式: "+e.getMessage());
		} catch (AddressException e) {
			logger.error("普通邮件发送异常(SMTP): "+address+"收信人地址异常: "+e.getMessage());
		} catch (MessagingException e) {
			logger.error("普通邮件发送异常(SMTP): "+address+"Message异常: "+e.getMessage());
		}

		// 添加html形式的邮件正文
		Multipart multipart = new MimeMultipart("alternative");
		String html = "<html><head></head><body>"
				+ "<p><a href='http://localhost:8080/task7/verification?email="+address+"'>点击</a>链接验证邮箱</p>" + "</body></html> ";
		BodyPart contentPart = new MimeBodyPart();
		try {
			//设置请求类型
			contentPart.setHeader("Content-Type", "text/html;charset=UTF-8");
			//设置传输时编码
			contentPart.setHeader("Content-Transfer-Encoding", "base64");
			//设置正文类型及编码
			contentPart.setContent(html, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);
			message.setContent(multipart);
		} catch (MessagingException e) {
			logger.error("普通邮件发送异常(SMTP): "+address+"Message异常(setContent()方法): "+e.getMessage());
		}

		// 建立传输端口，发送邮件
		SMTPTransport transport;
		try {
			transport = (SMTPTransport) mailSession.getTransport("smtp");
			transport.connect();
			transport.sendMessage(message, message.getRecipients(RecipientType.TO));
			
			//记录信息到日志
			String messageId = getMessage(transport.getLastServerResponse());
			String emailId = messageId + "0$" + address;
			logger.debug("messageId:" + messageId);
			logger.debug("emailId:" + emailId);
			logger.debug("response:" + transport.getLastServerResponse());

			transport.close();
		} catch (SendFailedException e) {
			logger.error("普通邮件发送异常(SMTP): "+address+"发送失败异常: "+e.getMessage());
		} catch (NoSuchProviderException e) {
			logger.error("普通邮件发送异常(SMTP): "+address+"无提供者异常: "+e.getMessage());
		} catch (MessagingException e) {
			logger.error("普通邮件发送异常(SMTP): "+address+"Message异常(getRecipients()方法): "+e.getMessage());
		}
	}
}
