package com.appteam.template.service;

import com.appteam.template.dto.OrderData;
import net.bytebuddy.build.Plugin;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;

import javax.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.argThat;

class OrderStatusNotificationServiceTest {

    private final int SEED = 1234;

    private final String replyTo = "noreply@test.com";

    @Mock
    private JavaMailSender jms = Mockito.mock(JavaMailSender.class);

    @Mock
    private OrderService os = Mockito.mock(OrderService.class);

    OrderStatusNotificationService service = new OrderStatusNotificationService(jms, os);
    List<SimpleMailMessage> mailReceiver = new ArrayList<>();

    private SimpleMailMessage getMsgTemplate() {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setReplyTo(replyTo);
        return msg;
    }

    static private class IsSimpleMailMsg implements ArgumentMatcher<SimpleMailMessage> {
        @Override
        public boolean matches(SimpleMailMessage argument) {
            return true;
        }
    }

    {
        Mockito.doAnswer(i -> mailReceiver.add((SimpleMailMessage) i.getArguments()[0])).when(jms).send(argThat(new IsSimpleMailMsg()));
        Mockito.when(os.getUserOrdersByStatus("user", "status1")).thenReturn(List.of());
        Mockito.when(os.getUserOrdersByStatus("user", "status2")).thenReturn(
                List.of(new OrderData(1L, "service1", "user", "status2", null),
                        new OrderData(1L, "service2", "user", "status2", null),
                        new OrderData(1L, "service3", "user", "status2", null))
        );
    }
    @Test
    void sendSimpleMessage() {
        List<SimpleMailMessage> expected = new ArrayList<>();
        mailReceiver = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String to = "test receiver" + i;
            String subject = "test subject" + i;
            String text = "test content" + i;
            SimpleMailMessage msg = getMsgTemplate();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            expected.add(msg);
            service.sendSimpleMessage(to, subject, text);
        }
        assertEquals(expected, mailReceiver);
    }

    @Test
    void sendOrderStatuses() {
        List<SimpleMailMessage> expected = new ArrayList<>();
        mailReceiver = new ArrayList<>();

        service.sendOrderStatuses("user", "status1");
        assertEquals(expected, mailReceiver);

        List<OrderData> data = os.getUserOrdersByStatus("user", "status2");
        SimpleMailMessage msg = getMsgTemplate();
        msg.setTo("user");
        msg.setSubject("Orders with status: " + "status2" + "\n");
        StringBuilder bld = new StringBuilder();
        data.forEach(order -> bld.append(order.getId()).append("\n"));
        msg.setText(bld.toString());
        expected.add(msg);

        service.sendOrderStatuses("user", "status2");
        assertEquals(expected, mailReceiver);

    }
}