package com.lps.qa.util;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtil {
	public static void sendEmail(String reportCompleteName, String receiver, final String sender,
			final String password, String emailReportSubject, String emailReportBody) {
		String recipient = receiver;
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(sender, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject(emailReportSubject);

			// creates message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(emailReportBody, "text/html");

			// creates multi-part
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// adds attachments

			MimeBodyPart attachPart = new MimeBodyPart();

			try {
				System.out.println("Result File to be attached ::" +reportCompleteName);
				attachPart.attachFile(reportCompleteName);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			multipart.addBodyPart(attachPart);
			message.setContent(multipart);
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/*
	public static void main(String[] args) throws IOException {
		String reportCompleteName = System.getProperty("user.dir") + System.getProperty("file.separator")	+ "Report" + System.getProperty("file.separator") + "Report" + ".html";
		System.out.println("reportCompleteName=" + reportCompleteName);
		
		PropertiesUtil.loadConstantFile("Constant.cfg");
		
		EmailUtil.sendEmail(reportCompleteName,
				PropertiesUtil.getConstantProperty("EmailReciever"),
				PropertiesUtil.getConstantProperty("EmailSender"),
				PropertiesUtil.getConstantProperty("EmailPassword"),
				PropertiesUtil.getConstantProperty("EmailReportSubject"),
				PropertiesUtil.getConstantProperty("EmailReportBody"));
	}
*/
}
