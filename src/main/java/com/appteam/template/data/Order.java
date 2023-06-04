package com.appteam.template.data;

import com.appteam.template.dto.OrderData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "order", schema = "through-track-plugin_db")
public class Order {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="status")
    private String status;
    @Column(name="service")
    private String service;
    @Column(name="merchant")
    private String merchant;

    public Order() {
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
    public String getMerchant() {
        return merchant;
    }
    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(status, order.status) && Objects.equals(service, order.service) && Objects.equals(merchant, order.merchant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, service, merchant);
    }
}
