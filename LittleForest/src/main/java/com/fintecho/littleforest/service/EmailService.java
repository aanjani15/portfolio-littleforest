package com.fintecho.littleforest.service;

import java.io.UnsupportedEncodingException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public interface EmailService {
    MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException;
    String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException;
    String getAuthNum();
}
