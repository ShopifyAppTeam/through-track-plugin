package com.appteam.template.service;

import com.appteam.template.dto.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("emailService")
@EnableScheduling
public class OrderStatusNotificationService implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String NOREPLY_TO = "noreply@test.com";
    @Resource(name = "orderService")
    private OrderService orderService;
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setReplyTo(NOREPLY_TO);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);
    }

    public void sendOrderStatuses(String user, String status) {
        List<OrderData> orders = orderService.getUserOrdersByStatus(user, status);
        StringBuilder textBuilder = new StringBuilder();
        if (orders != null) {
            orders.forEach(order -> textBuilder.append(order.getId()).append("\n"));
        }
        sendSimpleMessage(user, "Orders with status: " + status + "\n", textBuilder.toString());
    }

    @Value("${test.receiver}")
    private String testUser;
    @Scheduled(fixedRate = 1000000)
    public void notifyUserWithNotSent() {
        String status = "unshipped";
        sendOrderStatuses(testUser, status);
    }

}
