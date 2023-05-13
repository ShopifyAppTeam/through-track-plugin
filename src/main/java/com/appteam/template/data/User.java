package com.appteam.template.data;

import com.appteam.template.dto.UserData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Entity
@Table(name="user", schema = "through-track-plugin_db")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="id_shopify")
    private Long idShopify;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="enabled")
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    @Column(name="provider")
    private AuthorizationMethod authorizationMethod;

    @Column(name="update_time")
    private int updateTime;

    @Column(name="orders_send_time")
    private int ordersSendTime;

    public User() {
    }

    public User(UserData user) {
        id = user.getId(); // something strange, shouldn't be all fields?
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isEnabled() {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
