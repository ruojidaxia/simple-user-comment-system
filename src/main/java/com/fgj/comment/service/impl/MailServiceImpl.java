package com.fgj.comment.service.impl;

import com.fgj.comment.service.MailService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    private final MailSender mailSender;
    public MailServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendSimpleMail(String to, String subject,String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("1320524236@qq.com");
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        mailSender.send(msg);
    }
}
