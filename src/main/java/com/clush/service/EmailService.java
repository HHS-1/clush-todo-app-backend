package com.clush.service;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.clush.entity.UserEntity;
import com.clush.respository.UserRepository;
import com.clush.util.AESUtil;
import com.clush.util.UserUtil;

@Service
public class EmailService {
	
	@Value("${smtp.host}")
	private String host;
	@Value("${smtp.port}")
	private int port;
	@Value("${smtp.user}")
	private String user;
	@Value("${smtp.password}")
	private String password;
	
	@Autowired
	private UserRepository userRepository;
		
	public ResponseEntity<Void> sendEmailService(String email, String id) throws Exception{
		UserEntity userEntity = userRepository.findByUserId(email);
		if(userEntity == null) {
			return ResponseEntity.status(404).build();
		}
		
		
		String userName = UserUtil.getUserId().split("@")[0];
		//String userName = "testID"; //테스트용
		String calendarInviteUrl = "http:34.22.66.2:8081/shared/invite?accept="+AESUtil.encrypt(id);
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.naver.com");
		props.put("mail.smtp.port", 587);  // TLS 포트
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); // TLS 활성화
		props.put("mail.smtp.starttls.required", "true"); // TLS 강제
		props.put("mail.smtp.ssl.trust", "smtp.naver.com");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("qazplm1021@naver.com", "6Y71S7RVVU4Q");
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("qazplm1021@naver.com"));  // Set sender's email address
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); 
        message.setSubject(userName + "님의 공유캘린더 초대");
        String htmlContent = "<html>" +
                "<body >" +
                "<h1>공유 캘린더 초대</h1>" +
                "<p style='margin-bottom:30px;'>안녕하세요, 공유 캘린더 초대를 수락하려면 아래 버튼을 클릭하세요.</p>" +
                "<a href='" + calendarInviteUrl + "' style='margin:20px 0; padding:10px 20px; color:white; background-color:blue; text-decoration:none; border-radius:5px;'>수락</a>" +
                "</body>" +
                "</html>";

        message.setContent(htmlContent, "text/html; charset=UTF-8");

        Transport.send(message);
		
		return ResponseEntity.ok().build();
	}
}
