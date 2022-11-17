package com.assignment.api.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SendEmail {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;
	
	public void sendSimpleMail(String recipirnt, String message, String subject) {

		try {

			SimpleMailMessage mailMessage = new SimpleMailMessage();

			// Setting up necessary details
			mailMessage.setFrom(sender);
			mailMessage.setTo(recipirnt);
			mailMessage.setText(message);
			mailMessage.setSubject(subject);

			// Sending the mail
			javaMailSender.send(mailMessage);
			
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}
}
