package com.appteam.template.data;



import com.appteam.template.dto.OrderData;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long shipmentId;

    public Order() {
    }
    public Order(Long shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Order(OrderData data) {
        id = data.getId();
        shipmentId = data.getShipmentId();
    }

    public Long getId() {
        return id;
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
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(shipmentId, order.shipmentId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, shipmentId);
    }
}
