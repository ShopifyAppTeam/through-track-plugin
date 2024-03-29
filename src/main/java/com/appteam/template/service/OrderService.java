package com.appteam.template.service;

import com.appteam.template.dto.OrderData;

import java.util.Collection;
import java.util.List;

public interface OrderService {
    OrderData saveOrder(OrderData orderData);

    Boolean deleteOrder(final Long orderId);

    List<OrderData> getAllOrders();

    OrderData getOrderById(final Long id);
    List<OrderData> getUserOrdersByStatus(String user, String status);
    List<OrderData> getUserOrdersByService(String user, String status);
    List<OrderData> getUserOrdersByStatuses(String user, Collection<String> statuses);
    List<OrderData> getUserOrdersByServices(String user, Collection<String> services);
    List<OrderData> getUserOrders(String user);
}
