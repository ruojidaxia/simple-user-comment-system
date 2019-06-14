package com.fgj.comment.service;

public interface MailService {
    void sendSimpleMail(String to,String subject,String text);
}
