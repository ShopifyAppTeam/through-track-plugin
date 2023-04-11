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
@Table(name="users", schema = "through-track-plugin_db")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="username")
    private String userName;

    @Column(name="email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name="provider")
    private Provider provider;

    public User() {
    }

    public User(UserData user) {
        id = user.getId();
    }

    public Long getId() {
        return id;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
