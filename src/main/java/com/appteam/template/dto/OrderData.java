package com.appteam.template.dto;

import com.appteam.template.data.Order;

import java.util.Objects;

public class OrderData {
    private Long id;
    private String service;
    public OrderData() {
    }
    public OrderData(Long id, String service) {
        this.id = id;
        this.service = service;
    }

    public OrderData(Order order) {
        id = order.getId();
        service = order.getService();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderData orderData = (OrderData) o;
        return Objects.equals(id, orderData.id) && Objects.equals(service, orderData.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, service);
    }
}
