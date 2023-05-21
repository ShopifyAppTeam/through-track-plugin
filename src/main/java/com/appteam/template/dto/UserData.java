package com.appteam.template.dto;

import com.appteam.template.data.AuthorizationMethod;
import com.appteam.template.data.User;
import com.appteam.template.data.Role;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserData {
    private Long id;
    private Long idShopify;
    private String email;
    private String password;
    private boolean enabled = false;
    private AuthorizationMethod authorizationMethod;
    private int updateTime;
    private int ordersSendTime;

    private Set<Role> roles = new HashSet<>();
    public UserData() {
    }

    public UserData(Long idShopify, String email, String password, AuthorizationMethod authorizationMethod, int updateTime, int ordersSendTime, Set<Role> roles) {
        this.idShopify = idShopify;
        this.email = email;
        this.password = password;
        this.authorizationMethod = authorizationMethod;
        this.updateTime = updateTime;
        this.ordersSendTime = ordersSendTime;
        this.roles = roles;
    }

    public UserData(User user) {
        id = user.getId();
        idShopify = user.getIdShopify();
        email = user.getEmail();
        password = user.getPassword();
        enabled = user.isEnabled();
        authorizationMethod = user.getProvider();
        updateTime = user.getUpdateTime();
        ordersSendTime = user.getOrdersSendTime();
        roles = user.getRoles();
    }
    public Long getId() {
        return this.id;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
        return Objects.equals(id, userData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
