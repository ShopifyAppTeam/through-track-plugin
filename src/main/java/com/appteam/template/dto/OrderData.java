package com.appteam.template.dto;

import com.appteam.template.data.Order;

import java.util.Objects;

public class OrderData {
    private Long id;
    private Long shipmentId;
    public OrderData() {
    }
    public OrderData(Long id, Long shipmentId) {
        this.id = id;
        this.shipmentId = shipmentId;
    }

    public OrderData(Order order) {
        id = order.getId();
        shipmentId = order.getShipmentId();
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderData orderData = (OrderData) o;
        return Objects.equals(id, orderData.id) && Objects.equals(shipmentId, orderData.shipmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shipmentId);
    }
}
