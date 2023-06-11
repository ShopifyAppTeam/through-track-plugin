package com.appteam.template.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="shop", schema = "through-track-plugin_db")
public class Shop {
    @Id
    @Column(name="subdomain")
    private String subdomain;

    @Column(name="token")
    private String token;

    @OneToMany(fetch=FetchType.EAGER, mappedBy="shop")
    private Collection<Order> orders = new ArrayList<>();

    @ManyToOne(optional=false, cascade=CascadeType.ALL)
    @JoinColumn (name="shop_user")
    private User user;

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
}
