package com.fintecho.littleforest.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    private String authNum; // 인증번호 저장 변수

    // 인증번호 생성 (영문 + 숫자 8자리)
    private void createCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int idx = random.nextInt(3);
            switch (idx) {
                case 0: key.append((char) (random.nextInt(26) + 97)); break; // a-z
                case 1: key.append((char) (random.nextInt(26) + 65)); break; // A-Z
                case 2: key.append(random.nextInt(10)); break;              // 0-9
            }
        }
        authNum = key.toString();
    }

    @Override
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        createCode(); // 인증번호 생성
        String setFrom = "testtest12@gmail.com"; // 보내는 사람 (Gmail)
        String title = "LittleForest가 보낸 인증번호";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, email); // 받는 사람
        message.setSubject(title); // 제목
        message.setFrom(new InternetAddress(setFrom, "Little Forest")); // 보내는 사람 표시

        // HTML 내용
        String msgOfEmail = "";
        msgOfEmail += "<div style='margin:20px;'>";
        msgOfEmail += "<h1>안녕하세요 LittleForest 입니다.</h1>";
        msgOfEmail += "<p>아래 코드를 입력해주세요.</p>";
        msgOfEmail += "<div style='border:1px solid black; padding:10px; display:inline-block;'>";
        msgOfEmail += "<h3 style='color:blue;'>회원가입 인증 코드</h3>";
        msgOfEmail += "<strong style='font-size:18px;'>" + authNum + "</strong>";
        msgOfEmail += "</div></div>";

        message.setText(msgOfEmail, "utf-8", "html");
        return message;
    }

    @Override
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        MimeMessage emailForm = createEmailForm(toEmail);
        emailSender.send(emailForm);
        return authNum; // 인증번호 반환
    }

    @Override
    public String getAuthNum() {
        return authNum;
    }
}
