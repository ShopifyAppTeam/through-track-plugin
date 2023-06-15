package com.appteam.template.dto;

import com.appteam.template.data.Token;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class TokenData {
    private String key;
    private String email;

    public TokenData(){
    }

    public TokenData(String key, String email) {
        this.key = key;
        this.email = email;
    }

    public TokenData(Token token) {
        this.key = token.getKey();
        this.email = token.getEmail();
    }

    public static TokenData generate(String email) {
        //TODO
        return new TokenData("123", email);
    }

    public JSONObject toJSON() {
        Map<String, String> map = new HashMap<>();
        map.put("token", key);
        map.put("email", email);
        return new JSONObject(map);
    }

    public static TokenData fromJSON(String valueFromCookie) throws UnsupportedEncodingException {
        String value = URLDecoder.decode(valueFromCookie, "UTF-8");
        JSONObject jsonObject = new JSONObject(value);
        return new TokenData(jsonObject.getString("token"), jsonObject.getString("email"));
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
