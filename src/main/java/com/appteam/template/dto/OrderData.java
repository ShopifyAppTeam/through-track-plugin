package com.appteam.template.dto;

import com.appteam.template.data.Order;

import java.util.Objects;

public class OrderData {
    private Long id;
    private String service;
    public OrderData() {
    }
    public OrderData(Long id) {
        this.id = id;
    }

    public OrderData(Order order) {
        id = order.getId();
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderData orderData = (OrderData) o;
        return Objects.equals(id, orderData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
