package com.appteam.template.data;

import com.appteam.template.dto.TokenData;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "token", schema = "through-track-plugin_db")
public class Token {
    @Id
    @Column(name="key")
    private String key;

    @Column(name="email")
    private String email;

    public Token(){
    }

    public Token(TokenData tokenData) {
        key = tokenData.getKey();
        email = tokenData.getEmail();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
