package com.appteam.template.dto;

import com.appteam.template.data.AuthorizationMethod;
import com.appteam.template.data.Shop;
import com.appteam.template.data.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserData {
    private Long idShopify;
    private String email;
    private String password;
    private boolean enabled = false;
    private AuthorizationMethod authorizationMethod;
    private int updateTime;
    private int ordersSendTime;
    private Collection<Shop> shops;
    public UserData() {
    }
    public UserData(Long idShopify, String email, String password, AuthorizationMethod authorizationMethod,
                    int updateTime, int ordersSendTime, Collection<Shop> shops) {
        this.idShopify = idShopify;
        this.email = email;
        this.password = password;
        this.authorizationMethod = authorizationMethod;
        this.updateTime = updateTime;
        this.ordersSendTime = ordersSendTime;
        this.shops = shops;
    }

    public UserData(User user) {
        idShopify = user.getIdShopify();
        email = user.getEmail();
        password = user.getPassword();
        enabled = user.isEnabled();
        authorizationMethod = user.getProvider();
        updateTime = user.getUpdateTime();
        ordersSendTime = user.getOrdersSendTime();
        shops = user.getShops();
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

    public AuthorizationMethod getAuthorizationMethod() {
        return authorizationMethod;
    }

    public void setAuthorizationMethod(AuthorizationMethod authorizationMethod) {
        this.authorizationMethod = authorizationMethod;
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
        if (!(o instanceof UserData)) {
            return false;
        }
        UserData userData = (UserData) o;
        return Objects.equals(email, userData.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
