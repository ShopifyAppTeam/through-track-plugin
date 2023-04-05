package com.appteam.template.data;

import com.appteam.template.dto.OrderData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="order", schema = "through-track-plugin_db")
public class Order {
    @Id
    @Column(name="id")
    private Long id;

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
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(service, order.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, service);
    }
}
