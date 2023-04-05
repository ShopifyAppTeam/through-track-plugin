package com.appteam.template.data;

import com.appteam.template.dto.OrderData;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="order", schema = "through-track-plugin_db")
public class Order {
    @Id
    @Column(name="id")
    private Long id;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Column(name="service")
    private String service;
    public Order() {
    }
    public Order(Long id) {
        this.id = id;
    }

    public Order(OrderData data) {
        id = data.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
