package com.appteam.template.dto;

import com.appteam.template.data.User;
import com.appteam.template.data.Provider;

import java.util.Objects;

public class UserData {
    private Long id;
    private String userName;
    private String email;
    private Provider provider;
    private int updateTime;
    private int ordersSendTime;
    public UserData() {
    }
    public UserData(Long id, String userName, String email, Provider provider, int updateTime, int ordersSendTime) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.provider = provider;
        this.updateTime = updateTime;
        this.ordersSendTime = ordersSendTime;
    }

    public UserData(User user) {
        id = user.getId();
        userName = user.getUserName();
        email = user.getEmail();
        provider = user.getProvider();
        updateTime = user.getUpdateTime();
        ordersSendTime = user.getOrdersSendTime();
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(id, userData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
