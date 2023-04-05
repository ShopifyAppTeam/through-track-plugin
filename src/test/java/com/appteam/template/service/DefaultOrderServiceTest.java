package com.appteam.template.service;

import com.appteam.template.data.Order;
import com.appteam.template.dto.OrderData;
import com.appteam.template.repository.OrderRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class DefaultOrderServiceTest {
    @Mock
    OrderRepository orderRepositoryMock = Mockito.mock(OrderRepository.class);
    OrderService orderService = new DefaultOrderService(orderRepositoryMock);
    Random gen = new Random();
    List<Order> orderList = new ArrayList<>();

    {
        Mockito.when(orderRepositoryMock.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());
        for (int iter = 0; iter < 100; iter++) {
            OrderData orderData = new OrderData(gen.nextLong(), "");
            Order order = new Order(orderData);
            orderList.add(order);
            Mockito.when(orderRepositoryMock.save(order)).thenReturn(order);
            Mockito.when(orderRepositoryMock.findById(order.getId())).thenReturn(Optional.of(order));
        }
        Mockito.when(orderRepositoryMock.findAll()).thenReturn(orderList);
    }

    @Test
    public void saveOrder() {
        for (Order order : orderList) {
            OrderData data = new OrderData(order);
            assertEquals(data, orderService.saveOrder(data));
        }
    }

    @Test
    public void deleteOrder() {
        for (Order order : orderList) {
            OrderData data = new OrderData(order);
            assertEquals(true, orderService.deleteOrder(data.getId()));
        }
    }

    @Test
    public void getAllOrders() {
        List<OrderData> orders = orderService.getAllOrders();
        assertEquals(orderList.size(), orders.size());
        for (int iter = 0; iter < orderList.size(); iter++) {
            assertEquals(orderList.get(iter), new Order(orders.get(iter)));
        }
    }

    @Test
    public void getOrderById() {
        Map<Long, Long> orderMap = new HashMap<>();
        for (Order order : orderList) {
            orderMap.put(order.getId(), order.getId());
        }
        for (int iter = 0; iter < 50; iter++) {
            Long id = gen.nextLong();
            if (orderMap.containsKey(id)) {
                OrderData data = new OrderData(id, "");
                assertEquals(data, orderService.getOrderById(id));
            } else {
                assertThrows(EntityNotFoundException.class,
                        () -> orderService.getOrderById(id),
                        "Order not found");
            }
        }
    }
}