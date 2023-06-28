package com.appteam.template.dto;

import com.appteam.template.data.Order;
import com.appteam.template.data.Shop;

import java.util.Objects;

public class OrderData {
    private Long id;

    private String service;
    private String merchant;
    private String status;
    private Shop shop;

    public OrderData() {
    }
    public OrderData(Long id, String service, String merchant, String status, Shop shop) {
        this.id = id;
        this.service = service;
        this.merchant = merchant;
        this.status = status;
        this.shop = shop;
    }

    public OrderData(Order order) {
        id = order.getId();
        service = order.getService();
        merchant = order.getMerchant();
        shop = order.getShop();
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

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderData orderData = (OrderData) o;
        return Objects.equals(id, orderData.id) && Objects.equals(service, orderData.service) && Objects.equals(merchant, orderData.merchant) && Objects.equals(status, orderData.status) && Objects.equals(shop, orderData.shop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, service, merchant, status, shop);
    }
}
