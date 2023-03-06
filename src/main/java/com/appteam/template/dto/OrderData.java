package com.appteam.template.dto;

import com.appteam.template.data.Order;

public class OrderData {
    private Long id;
    private Long shipmentId;
    public OrderData() {
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
}
