package com.appteam.template.service;

import com.appteam.template.data.Order;
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

    public OrderStatusNotificationService(JavaMailSender javaMailSenderMock, OrderService orderServiceMock) {
        javaMailSender = javaMailSenderMock;
        orderService = orderServiceMock;
    }

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
        if (orders.isEmpty()) {
            return;
        }
        StringBuilder textBuilder = new StringBuilder();
        orders.forEach(order -> textBuilder.append(order.getId()).append("\n"));
        sendSimpleMessage(user, "Orders with status: " + status + "\n", textBuilder.toString());
    }

    @Value("${test.receiver}")
    private String testUser;
    // @Scheduled(fixedRate = )
    public void notifyUserWithNotSent() {
        List<String> users = null; // getting users emails from User Table
        String status = "unshipped";
        users.forEach(user -> sendOrderStatuses(user, status));
    }

}
