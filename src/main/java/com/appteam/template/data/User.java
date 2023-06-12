package com.appteam.template.data;

import com.appteam.template.dto.UserData;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name="user", schema = "through-track-plugin_db")
public class User {

    @Column(name="id_shopify")
    private Long idShopify;

    @Id
    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private Boolean enabled = false;

    @Enumerated(EnumType.STRING)
    @Column(name="auth_method")
    private AuthorizationMethod authorizationMethod;

    @Column(name="update_time")
    private int updateTime;

    @Column(name="orders_send_time")
    private int ordersSendTime;
    @OneToMany(fetch=FetchType.EAGER, mappedBy="user")
    private Collection<Shop> shops = new ArrayList<>();

    /*
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
*/
    public User() {
    }

    public User(UserData user) {

    }

    public Long getIdShopify() {
        return this.idShopify;
    }

    public void setIdShopify(Long idShopify) {
        this.idShopify = idShopify;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public AuthorizationMethod getProvider() {
        return authorizationMethod;
    }

    public void setProvider(AuthorizationMethod provider) {
        this.authorizationMethod = provider;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public int getOrdersSendTime() {
        return ordersSendTime;
    }

    public void setOrdersSendTime(int ordersSendTime) {
        this.ordersSendTime = ordersSendTime;
    }

    public List<Shop> getShops() {
        return new ArrayList<>(shops);
    }

    public void setShops(Collection<Shop> shops) {
        this.shops = shops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.getEmail());
    }
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}