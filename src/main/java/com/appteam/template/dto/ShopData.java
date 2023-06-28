package com.appteam.template.dto;

import com.appteam.template.data.AuthorizationMethod;
import com.appteam.template.data.Order;
import com.appteam.template.data.Shop;
import com.appteam.template.data.User;

import java.util.*;

public class ShopData {
    private String subdomain;
    private String token;
    private Collection<Order> orders;
    private User user;

    public ShopData() {
        orders = new ArrayList<>();
    }

    public ShopData (String subdomain, String token, User user, Collection<Order> orders) {
        this.subdomain = subdomain;
        this.token = token;
        this.user = user;
        this.orders = orders;
    }

    public ShopData(Shop shop) {
        this.subdomain = shop.getSubdomain();
        this.token = shop.getToken();
        this.user = shop.getUser();
        this.orders = shop.getOrders();
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public String getToken() {
        return subdomain;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserData)) {
            return false;
        }
        ShopData shopData = (ShopData) o;
        return Objects.equals(subdomain, shopData.getSubdomain());
    }

    @Override
    public int hashCode() {
        return Objects.hash(subdomain);
    }
}
