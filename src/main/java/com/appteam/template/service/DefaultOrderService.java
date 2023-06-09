package com.appteam.template.service;

import com.appteam.template.data.Order;
import com.appteam.template.dto.OrderData;
import com.appteam.template.exception.ResourceNotFoundException;
import com.appteam.template.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service("orderService")
public class DefaultOrderService implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepositoryMock) {
        orderRepository = orderRepositoryMock;
    }

    @Override
    public OrderData saveOrder(OrderData orderData) {
        Order order = populateOrderEntity(orderData);
        return populateOrderData(orderRepository.save(order)); // концептуально
    }

    @Override
    public Boolean deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        return true;
    }

    public List<OrderData> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return convertOrderList(orders);
    }

    @Override
    public OrderData getOrderById(Long id) {
        return populateOrderData(orderRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Order not found"))
        );
    }

    @Override
    public List<OrderData> getUserOrdersByStatus(String user, String status) {
        List<Order> orders = orderRepository.findUserOrdersByStatus(user, status);
        return convertOrderList(orders);
    }

    private List<OrderData> convertOrderList(List<Order> orders) {
        List<OrderData> data = new ArrayList<>();
        orders.forEach(order -> data.add(populateOrderData(order)));
        return data;
    }
    private Order populateOrderEntity(final OrderData data) {
        Order order = new Order();
        order.setId(data.getId());
        order.setService(data.getService());
        order.setMerchant(data.getMerchant());
        order.setStatus(data.getStatus());
        order.setShop(data.getShop());
        return order;
    }

    private OrderData populateOrderData(final Order order) {
        OrderData data = new OrderData();
        data.setId(order.getId());
        data.setService(order.getService());
        data.setStatus(order.getStatus());
        data.setMerchant(order.getMerchant());
        data.setShop(order.getShop());
        return data;
    }

}
