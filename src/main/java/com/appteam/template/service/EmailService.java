package com.appteam.template.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
