package com.appteam.template.service;

import com.appteam.template.data.Order;
import com.appteam.template.dto.OrderData;
import com.appteam.template.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import org.mockito.Mockito;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DefaultOrderServiceTest {
    @Mock
    OrderRepository orderRepositoryMock = Mockito.mock(OrderRepository.class);
    OrderService orderService = new DefaultOrderService(orderRepositoryMock);
    Random gen = new Random();
    List<Order> orderList = new ArrayList<>();
    {
        Mockito.when(orderRepositoryMock.findById(Mockito.any(Long.class))).thenReturn(Optional.empty());
        for (int iter = 0; iter < 100; iter++) {
            OrderData orderData = new OrderData(gen.nextLong(), gen.nextLong());
            Order order = new Order(orderData);
            orderList.add(order);
            Mockito.when(orderRepositoryMock.save(order)).thenReturn(order);
            Mockito.when(orderRepositoryMock.findById(order.getId())).thenReturn(Optional.of(order));
        }
        Mockito.when(orderRepositoryMock.findAll()).thenReturn(orderList);
    }
    @Test
    void saveOrder() {
        for (Order order : orderList) {
            OrderData data = new OrderData(order);
            assertEquals(data, orderService.saveOrder(data));
        }
    }

    @Test
    void deleteOrder() {
        for (Order order : orderList) {
            OrderData data = new OrderData(order);
            assertEquals(true, orderService.deleteOrder(data.getId()));
        }
    }

    @Test
    void getAllOrders() {
        List<OrderData> orders = orderService.getAllOrders();
        assertEquals(orderList.size(), orders.size());
        for (int iter = 0; iter < orderList.size(); iter++) {
            assertEquals(orderList.get(iter), new Order(orders.get(iter)));
        }
    }

    @Test
    void getOrderById() {
        Map<Long, Long>orderMap = new HashMap<>();
        for (Order order : orderList) {
            orderMap.put(order.getId(), order.getShipmentId());
        }
        for (int iter = 0; iter < 50; iter++) {
            Long id = gen.nextLong();
            if (orderMap.containsKey(id)) {
                OrderData data = new OrderData(id, orderMap.get(id));
                assertEquals(data, orderService.getOrderById(id));
            } else {
                assertThrows(EntityNotFoundException.class,
                        () -> orderService.getOrderById(id),
                        "Order not found");
            }
        }
    }
}