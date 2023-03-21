package com.appteam.template.service;

import com.appteam.template.dto.OrderData;

import java.util.List;

public interface OrderService {
    OrderData saveOrder(OrderData orderData);
    Boolean deleteOrder(final Long orderId);
    List<OrderData> getAllOrders();
    OrderData getOrderById(final Long id);
}
